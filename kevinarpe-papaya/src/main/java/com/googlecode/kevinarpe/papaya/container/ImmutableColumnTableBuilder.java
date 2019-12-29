package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
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
    createFullEnum(Class<TEnumKey> enumKeyClass) {

        @ReadOnlyContainer
        @Nullable
        final TEnumKey[] enumValueArr = enumKeyClass.getEnumConstants();
        if (null == enumValueArr) {
            throw new IllegalStateException("Unreachable code");
        }
        final ImmutableColumnTableBuilder<TEnumKey, TValue2> x =
            new ImmutableColumnTableBuilder<>(Sets.immutableEnumSet(Arrays.<TEnumKey>asList(enumValueArr)));
        return x;
    }

    public final ImmutableSet<TKey> keySet;
    private int rowCount;
    private final ImmutableListMultimap.Builder<TKey, TValue> listMultimapBuilder;

    public ImmutableColumnTableBuilder(Set<? extends TKey> keySet) {

        // Zero rows makes sense (empty table), but zero keys is insane.
        // Can you imagine an Excel worksheet with zero columns?
        CollectionArgs.checkNotEmpty(keySet, "keySet");
        this.keySet = ImmutableSet.copyOf(keySet);
        this.rowCount = 0;
        this.listMultimapBuilder = ImmutableListMultimap.builder();
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
        ++rowCount;
        listMultimapBuilder.putAll(map.entrySet());
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
        final int columnSize = _getColumnSize(multimap);
        rowCount += columnSize;
        listMultimapBuilder.putAll(multimap);
        return this;
    }

    private int
    _getColumnSize(Multimap<? extends TKey, ? extends TValue> multimap) {

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
        return columnSize;
    }

    /**
     * Builds a new instance of {@link ImmutableColumnTable}.
     */
    public ImmutableColumnTable<TKey, TValue>
    build() {

        final ImmutableListMultimap<TKey, TValue> listMultimap = listMultimapBuilder.build();
        final ImmutableColumnTable<TKey, TValue> x = new ImmutableColumnTable<>(rowCount, listMultimap);
        return x;
    }
}
