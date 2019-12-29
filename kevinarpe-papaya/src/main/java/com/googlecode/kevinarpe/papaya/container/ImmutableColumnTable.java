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
import com.google.common.collect.ImmutableListMultimap;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

/**
 * A simple wrapper around {@link ImmutableListMultimap} where each sub-list represents column-wise data.
 * This structure guarantees each sub-list will have equal size.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ImmutableColumnTableBuilder
 * @see ImmutableFullEnumColumnTable
 */
@FullyTested
public final class ImmutableColumnTable<TKey, TValue> {

    /** Be careful: This can be zero if {@link #listMultimap} is empty. */
    public final int rowCount;

    /** Each sub-list must have size equal to {@link #rowCount}. */
    @EmptyContainerAllowed
    public final ImmutableListMultimap<TKey, TValue> listMultimap;

    /**
     * @param rowCount
     *        may be zero if {@code listMultimap} is empty
     *
     * @param listMultimap
     *        each sub-list must have size equal to {@code rowCount}
     *
     * @throws IllegalArgumentException
     *         if {@code rowCount} is invalid
     */
    public ImmutableColumnTable(final int rowCount,
                                @EmptyContainerAllowed
                                ImmutableListMultimap<TKey, TValue> listMultimap) {

        this.rowCount = IntArgs.checkNotNegative(rowCount, "rowCount");
        this.listMultimap = ObjectArgs.checkNotNull(listMultimap, "listMultimap");

        if (listMultimap.isEmpty()) {

            if (0 != rowCount) {
                throw new IllegalArgumentException(
                    "Argument 'rowCount' is zero but argument 'listMultimap' is not empty");
            }
        }
        else {
            for (final TKey key : listMultimap.keySet()) {

                final ImmutableList<TValue> valueList = listMultimap.get(key);
                if (valueList.size() != rowCount) {

                    throw new IllegalArgumentException(String.format(
                        "Argument 'rowCount' is %d, but key [%s] has %d items", rowCount, key, valueList.size()));
                }
            }
        }
    }
}
