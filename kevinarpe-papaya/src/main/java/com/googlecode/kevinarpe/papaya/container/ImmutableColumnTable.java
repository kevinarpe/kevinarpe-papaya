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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;

import java.util.List;
import java.util.Map;

/**
 * A simple wrapper around {@link ImmutableMap} and {@link ImmutableList} where each sub-list represents a column of
 * data.  This data structure guarantees each sub-list will have equal size.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableColumnTableBuilder
 * @see ImmutableFullEnumColumnTable
 */
@FullyTested
public final class ImmutableColumnTable<TKey, TValue> {

    /**
     * Creates a new instance without any copies.
     *
     * @param listMap
     *        <ul>
     *            <li>must not be empty -- a table must have at least one column</li>
     *            <li>each sub-list must have equal size</li>
     *            <li>all empty sub-lists is considered an empty table and will have {@link #rowCount} equal zero</li>
     *        </ul>
     *
     * @throws IllegalArgumentException
     *         if any two sub-lists have different size
     *
     * @see #copyOf(Map)
     */
    public static <TKey, TValue>
    ImmutableColumnTable<TKey, TValue>
    of(ImmutableMap<TKey, ImmutableList<TValue>> listMap) {

        final ImmutableColumnTable<TKey, TValue> x = new ImmutableColumnTable<>(listMap);
        return x;
    }

    /**
     * Copies the map and each sub-list to create new instance.  To avoid copies, see: {@link #of(ImmutableMap)}.
     *
     * @param listMap
     *        <ul>
     *            <li>must not be empty -- a table must have at least one column</li>
     *            <li>each sub-list must have equal size</li>
     *            <li>all empty sub-lists is considered an empty table and will have {@link #rowCount} equal zero</li>
     *        </ul>
     *
     * @throws IllegalArgumentException
     *         if any two sub-lists have different size
     *
     * @see #of(ImmutableMap)
     */
    public static <TKey, TValue>
    ImmutableColumnTable<TKey, TValue>
    copyOf(Map<? extends TKey, ? extends List<? extends TValue>> listMap) {

        MapArgs.checkNotEmpty(listMap, "listMap");
        final ImmutableMap.Builder<TKey, ImmutableList<TValue>> b = ImmutableMap.builder();
        for (final Map.Entry<? extends TKey, ? extends List<? extends TValue>> entry : listMap.entrySet()) {

            final TKey key = entry.getKey();
            final List<? extends TValue> list = entry.getValue();
            b.put(key, ImmutableList.copyOf(list));
        }
        final ImmutableMap<TKey, ImmutableList<TValue>> listMap2 = b.build();
        final ImmutableColumnTable<TKey, TValue> x = new ImmutableColumnTable<>(listMap2);
        return x;
    }

    /** Be careful: This can be zero if each sub-list of {@link #listMap} is empty. */
    public final int rowCount;

    /** Each sub-list is guaranteed to have size equal to {@link #rowCount}. */
    public final ImmutableMap<TKey, ImmutableList<TValue>> listMap;

    private ImmutableColumnTable(ImmutableMap<TKey, ImmutableList<TValue>> listMap) {

        this.listMap = MapArgs.checkNotEmpty(listMap, "listMap");
        this.rowCount = _getRowCount(listMap);
    }

    // Intentional: Package-private to be called from ImmutableFullEnumColumnTable.
    static <TKey, TValue>
    int _getRowCount(Map<TKey, ? extends List<? extends TValue>> listMap) {

        TKey key0 = null;
        int size0 = -1;
        for (final Map.Entry<TKey, ? extends List<? extends TValue>> entry : listMap.entrySet()) {

            final TKey key = entry.getKey();
            final List<? extends TValue> list = entry.getValue();
            if (null == key0) {
                key0 = key;
                size0 = list.size();
            }
            else {
                final int size = list.size();
                if (size != size0) {
                    throw new IllegalArgumentException(String.format(
                        "Key [%s] has sub-list size %d, but key [%s] has sub-list size %d",
                        key0, size0, key, size));
                }
            }
        }
        return size0;
    }
}
