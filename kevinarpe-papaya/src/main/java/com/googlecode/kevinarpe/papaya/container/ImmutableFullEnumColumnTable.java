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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;

import java.util.List;
import java.util.Map;

/**
 * A simple wrapper around {@link ImmutableFullEnumMap} and {@link ImmutableList} where each sub-list represents
 * column-wise data.  This structure guarantees each sub-list will have equal size.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableFullEnumColumnTableBuilder
 * @see ImmutableColumnTable
 */
@FullyTested
public final class ImmutableFullEnumColumnTable<TEnumKey extends Enum<TEnumKey>, TValue> {

    /** Be careful: This can be zero if each sub-list of {@link #keyToColumnListMap} is empty. */
    public final int rowCount;

    /** Each sub-list must have size equal to {@link #rowCount}. */
    public final ImmutableFullEnumMap<TEnumKey, ImmutableList<TValue>> keyToColumnListMap;

    /**
     * @param rowCount
     *        may be zero if each sub-list of {@code keyToColumnListMap} is empty
     *
     * @param keyToColumnListMap
     *        each sub-list must have size equal to {@code rowCount}
     *
     * @throws IllegalArgumentException
     *         if {@code rowCount} is invalid
     */
    public ImmutableFullEnumColumnTable(final int rowCount,
                                        ImmutableFullEnumMap<TEnumKey, ? extends List<? extends TValue>>
                                            keyToColumnListMap) {

        this.rowCount = IntArgs.checkNotNegative(rowCount, "rowCount");
        // In theory, ImmutableFullEnumMap allows empty when the enum has zero constants.  However, for a table,
        // this makes no sense lah.
        MapArgs.checkNotEmpty(keyToColumnListMap, "keyToColumnListMap");

        for (final Map.Entry<TEnumKey, ? extends List<? extends TValue>> entry : keyToColumnListMap.entrySet()) {

            final TEnumKey key = entry.getKey();
            final List<? extends TValue> columnList = entry.getValue();
            if (columnList.size() != rowCount) {

                throw new IllegalArgumentException(String.format(
                    "Argument 'rowCount' is %d, but key [%s] has %d items", rowCount, key, columnList.size()));
            }
        }
        this.keyToColumnListMap =
            ImmutableFullEnumMap.ofKeys(
                keyToColumnListMap.getEnumClass(),
                k -> ImmutableList.copyOf(keyToColumnListMap.get(k)));
    }
}
