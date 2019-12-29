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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Builder class to safely build {@link ImmutableFullEnumColumnTable}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableFullEnumColumnTable
 * @see ImmutableColumnTable
 * @see ImmutableColumnTableBuilder
 */
@FullyTested
public final class ImmutableFullEnumColumnTableBuilder<TEnumKey extends Enum<TEnumKey>, TValue> {

    public final Class<TEnumKey> enumKeyClass;
    private int rowCount;
    private final ImmutableFullEnumMap<TEnumKey, ImmutableList.Builder<TValue>> builderMap;

    public ImmutableFullEnumColumnTableBuilder(Class<TEnumKey> enumKeyClass) {

        this.enumKeyClass = ObjectArgs.checkNotNull(enumKeyClass, "enumKeyClass");
        this.rowCount = 0;
        this.builderMap = ImmutableFullEnumMap.ofKeys(enumKeyClass, any -> ImmutableList.builder());
    }

    /**
     * Adds one new row to this table.
     *
     * @return {@code this} for method chaining (builder pattern)
     *
     * @throws IllegalArgumentException
     *         if keys of {@code map} do not match keys of {@link #enumKeyClass}
     */
    public ImmutableFullEnumColumnTableBuilder<TEnumKey, TValue>
    put(Map<? extends TEnumKey, ? extends TValue> map) {

        _checkKeys(map.keySet());
        ++rowCount;

        for (final Map.Entry<? extends TEnumKey, ? extends TValue> entry : map.entrySet()) {

            final TEnumKey key = entry.getKey();
            final TValue value = entry.getValue();
            final ImmutableList.Builder<TValue> b = builderMap.get(key);
            b.add(value);
        }
        return this;
    }

    private void
    _checkKeys(Set<? extends TEnumKey> keySet) {

        if (false == keySet.equals(builderMap.keySet())) {

            throw new IllegalArgumentException(String.format(
                "Expected keys: %s"
                    + "%nFound keys   : %s",
                builderMap.keySet(), keySet));
        }
    }

    /**
     * Adds one or more new rows to this table.
     *
     * @return {@code this} for method chaining (builder pattern)
     *
     * @throws IllegalArgumentException
     *         if keys of {@code multimap} do not match keys of {@link #enumKeyClass}
     *         <br>if size of each sub-list of {@code multimap} do not match
     */
    public ImmutableFullEnumColumnTableBuilder<TEnumKey, TValue>
    putAll(Multimap<? extends TEnumKey, ? extends TValue> multimap) {

        _checkKeys(multimap.keySet());
        final int columnSize = _getColumnSize(multimap);
        rowCount += columnSize;

        final Map<? extends TEnumKey, ? extends Collection<? extends TValue>> map = multimap.asMap();
        for (final Map.Entry<? extends TEnumKey, ? extends Collection<? extends TValue>> entry : map.entrySet()) {

            final TEnumKey key = entry.getKey();
            final Collection<? extends TValue> c = entry.getValue();
            final ImmutableList.Builder<TValue> b = builderMap.get(key);
            b.addAll(c);
        }
        return this;
    }

    private int
    _getColumnSize(Multimap<? extends TEnumKey, ? extends TValue> multimap) {

        TEnumKey key0 = null;
        int columnSize = -1;
        final Map<? extends TEnumKey, ? extends Collection<? extends TValue>> map = multimap.asMap();

        for (final Map.Entry<? extends TEnumKey, ? extends Collection<? extends TValue>> entry : map.entrySet()) {

            final TEnumKey key = entry.getKey();
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
     * Builds a new instance of {@link ImmutableFullEnumColumnTable}.
     */
    public ImmutableFullEnumColumnTable<TEnumKey, TValue>
    build() {

        final ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> map =
            ImmutableFullEnumMap.ofKeys(builderMap.getEnumClass(), k -> builderMap.get(k).build());

        final ImmutableFullEnumColumnTable<TEnumKey, TValue> x = new ImmutableFullEnumColumnTable<>(rowCount, map);
        return x;
    }
}
