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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * A simple wrapper around {@link ImmutableFullEnumMap} and {@link ImmutableList} where each sub-list represents
 * a column of data.  This data structure guarantees each sub-list will have equal size.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableFullEnumColumnTableBuilder
 * @see ImmutableColumnTable
 */
@FullyTested
public final class ImmutableFullEnumColumnTable<TEnumKey extends Enum<TEnumKey>, TValue> {

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
    public static <TEnumKey extends Enum<TEnumKey>, TValue>
    ImmutableFullEnumColumnTable<TEnumKey, TValue>
    of(ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> listMap) {

        final ImmutableFullEnumColumnTable<TEnumKey, TValue> x = new ImmutableFullEnumColumnTable<>(listMap);
        return x;
    }

    /**
     * Copies the map and each sub-list to create new instance.  To avoid copies, see: {@link #of(ImmutableFullEnumMap)}.
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
     * @see #of(ImmutableFullEnumMap)
     */
    public static <TEnumKey extends Enum<TEnumKey>, TValue>
    ImmutableFullEnumColumnTable<TEnumKey, TValue>
    copyOf(Map<TEnumKey, ? extends List<? extends TValue>> listMap) {

        MapArgs.checkNotEmpty(listMap, "listMap");
        @SuppressWarnings("unchecked")
        final Class<TEnumKey> enumClass = (Class<TEnumKey>) listMap.keySet().iterator().next().getClass();

        final EnumSet<TEnumKey> keySet = EnumSet.allOf(enumClass);
        if (false == keySet.equals(listMap.keySet())) {

            keySet.removeAll(listMap.keySet());
            throw new IllegalArgumentException(String.format("Missing %d keys: %s", keySet.size(), keySet));
        }
        final ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> listMap2 =
            ImmutableFullEnumMap.ofKeys(enumClass,
                k -> ImmutableList.copyOf(listMap.get(k)));

        final ImmutableFullEnumColumnTable<TEnumKey, TValue> x = new ImmutableFullEnumColumnTable<>(listMap2);
        return x;
    }

    /** Be careful: This can be zero if each sub-list of {@link #listMap} is empty. */
    public final int rowCount;

    /** Each sub-list is guaranteed to have size equal to {@link #rowCount}. */
    public final ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> listMap;

    private ImmutableFullEnumColumnTable(ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> listMap) {

        // In theory, ImmutableFullEnumMap allows empty when the enum has zero constants.
        // However, for a table, makes no sense lah.
        this.listMap = MapArgs.checkNotEmpty(listMap, "listMap");
        this.rowCount = ImmutableColumnTable._getRowCount(listMap);
    }
}
