package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableColumnTable
 * @see ImmutableFullEnumColumnTable
 * @see ImmutableFullEnumColumnTableBuilder
 */
@FullyTested
public final class ImmutableColumnTableBuilder<TKey, TValue> {

    /**
     * Creates a new builder with all keys of an enumeration class.
     *
     * @see ImmutableFullEnumColumnTable
     * @see ImmutableFullEnumColumnTableBuilder
     */
    public static <TEnumKey extends Enum<TEnumKey>, TValue2>
    ImmutableColumnTableBuilder<TEnumKey, TValue2>
    allOf(Class<TEnumKey> enumKeyClass) {

        final ImmutableSet<TEnumKey> keySet = Sets.immutableEnumSet(EnumSet.allOf(enumKeyClass));
        final ImmutableColumnTableBuilder<TEnumKey, TValue2> x = new ImmutableColumnTableBuilder<>(keySet);
        return x;
    }

    /**
     * Remember: {@link ImmutableSet} is special: Insert and iteration order are guaranteed to match.
     * <p>
     * Thus, the order of keys passed to ctor will be preserved here.  Be careful if column order is important.
     */
    public final ImmutableSet<TKey> keySet;

    private final LinkedHashMap<TKey, ImmutableList.Builder<TValue>> listMap;

    /**
     * Creates a new builder with zero rows.
     * <p>
     * To be clear, it is valid to build an empty table with zero rows.
     *
     * @param keySet
     *        <ul>
     *            <li>must not be empty</li>
     *            <li>order of columns will match iteration order</li>
     *            <li>hint: if order is important, use {@link ImmutableSet}</li>
     *        </ul>
     *
     * @throws IllegalArgumentException
     *         if {@code keySet} is empty
     *
     * @see #ImmutableColumnTableBuilder(List)
     */
    public ImmutableColumnTableBuilder(Set<? extends TKey> keySet) {

        // Zero rows makes sense (empty table), but zero keys is insane.
        // Can you imagine an Excel worksheet with zero columns?
        CollectionArgs.checkNotEmpty(keySet, "keySet");
        this.keySet = ImmutableSet.copyOf(keySet);
        this.listMap = new LinkedHashMap<>();
        for (final TKey key : this.keySet) {
            this.listMap.put(key, ImmutableList.builder());
        }
    }

    /**
     * Creates a new builder with zero rows.
     * <p>
     * To be clear, it is valid to build an empty table with zero rows.
     *
     * @param keyList
     *        must not be empty
     *
     * @throws IllegalArgumentException
     *         <ul>
     *             <li>if {@code keyList} is empty</li>
     *             <li>if elements of {@code keyList} are not unique</li>
     *         </ul>
     *
     * @see #ImmutableColumnTableBuilder(Set)
     */
    public ImmutableColumnTableBuilder(List<? extends TKey> keyList) {

        this(_copyUniqueList(keyList));
    }

    private static <TKey>
    ImmutableSet<TKey>
    _copyUniqueList(List<? extends TKey> keyList) {

        final ImmutableSet<TKey> keySet = ImmutableSet.copyOf(keyList);
        if (keySet.size() == keyList.size()) {
            return keySet;
        }
        final ArrayList<TKey> dupList = new ArrayList<>(keyList);

        for (final TKey key : keySet) {
            // Only removes first!
            dupList.remove(key);
        }
        throw new IllegalArgumentException(String.format("Found %d duplicate keys: %s", dupList.size(), dupList));
    }

    /**
     * Adds one new row to this table.
     *
     * @return {@code this} for method chaining (builder pattern)
     *
     * @throws IllegalArgumentException
     *         if keys of {@code map} do not match {@link #keySet}
     */
    public ImmutableColumnTableBuilder<TKey, TValue>
    put(Map<? extends TKey, ? extends TValue> map) {

        _checkKeys(map.keySet());
        for (final Map.Entry<? extends TKey, ? extends TValue> entry : map.entrySet()) {

            listMap.get(entry.getKey()).add(entry.getValue());
        }
        return this;
    }

    private void
    _checkKeys(Set<? extends TKey> keySet2) {

        if (false == keySet2.equals(keySet)) {

            throw new IllegalArgumentException(String.format(
                "Expected keys: %s"
                    + "%nFound keys   : %s",
                keySet, keySet2));
        }
    }

    /**
     * Adds one or more new rows to this table.
     *
     * @return {@code this} for method chaining (builder pattern)
     *
     * @throws IllegalArgumentException
     *         if keys of {@code multimap} do not match {@link #keySet}
     *         <br>if size of each sub-list of {@code multimap} do not match
     */
    public ImmutableColumnTableBuilder<TKey, TValue>
    putAll(Multimap<? extends TKey, ? extends TValue> multimap) {

        _checkKeys(multimap.keySet());
        _checkColumnSize(multimap);
        final Map<? extends TKey, ? extends Collection<? extends TValue>> map = multimap.asMap();

        for (final Map.Entry<? extends TKey, ? extends Collection<? extends TValue>> entry : map.entrySet()) {

            final TKey key = entry.getKey();
            final Collection<? extends TValue> c = entry.getValue();
            listMap.get(key).addAll(c);
        }
        return this;
    }

    // Intentional: Package-private to be called from ImmutableFullEnumColumnTableBuilder.
    static <TKey, TValue>
    void _checkColumnSize(Multimap<? extends TKey, ? extends TValue> multimap) {

        TKey key0 = null;
        int columnSize = -1;
        final Map<? extends TKey, ? extends Collection<? extends TValue>> map = multimap.asMap();

        for (final Map.Entry<? extends TKey, ? extends Collection<? extends TValue>> entry : map.entrySet()) {

            final TKey key = entry.getKey();
            final Collection<? extends TValue> c = entry.getValue();
            if (-1 == columnSize) {
                key0 = key;
                columnSize = c.size();
            }
            else if (c.size() != columnSize) {
                throw new IllegalArgumentException(String.format("Key [%s] has %d items, but key [%s] has %d items",
                    key0, columnSize, key, c.size()));
            }
        }
    }

    /**
     * Builds a new instance of {@link ImmutableColumnTable}.
     */
    public ImmutableColumnTable<TKey, TValue>
    build() {

        final ImmutableMap.Builder<TKey, ImmutableList<TValue>> b = ImmutableMap.builder();
        for (final Map.Entry<TKey, ImmutableList.Builder<TValue>> entry : listMap.entrySet()) {

            b.put(entry.getKey(), entry.getValue().build());
        }
        final ImmutableMap<TKey, ImmutableList<TValue>> listMap = b.build();
        final ImmutableColumnTable<TKey, TValue> x = ImmutableColumnTable.of(listMap);
        return x;
    }
}
