package com.googlecode.kevinarpe.papaya.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import java.util.Collection;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ComparatorUtils {

    /**
     * Converts a result from {@link Comparator#compare(Object, Object)} to -1, 0, or +1.
     *
     * @param result
     *        raw result, e.g., -17, 0, +17, etc.
     *
     * @return normalized compare result: -1, 0, or +1
     */
    @FullyTested
    public static int normalizeCompareResult(int result) {
        if (0 != result) {
            result /= Math.abs(result);
        }
        return result;
    }

    /**
     * Creates a new {@link Comparator} from a list of {@code Comparator}s which returns the first
     * non-zero compare result.  As a special case, if the input list has exactly one element, it is
     * returned unchanged.
     *
     * @param comparatorCollection
     *        collection of comparators to chain
     * @param <TValue>
     *        type of object compared by each {@link Comparator}
     *
     * @return new {@code Comparator} chaining the input list of {@code Comparator}s
     *
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code ref} is zero
     */
    @NotFullyTested
    public static <TValue> Comparator<TValue> chain(
            final Collection<Comparator<TValue>> comparatorCollection) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(comparatorCollection, "comparatorCollection");

        if (1 == comparatorCollection.size()) {
            for (Comparator<TValue> comparator : comparatorCollection) {
                return comparator;
            }
        }

        return new Comparator<TValue>() {
            @Override
            public int compare(TValue left, TValue right) {
                for (Comparator<TValue> comparator : comparatorCollection) {
                    int result = comparator.compare(left, right);
                    if (0 != result) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }
}
