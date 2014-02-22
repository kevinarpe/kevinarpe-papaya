package com.googlecode.kevinarpe.papaya.container;

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

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.AbstractList;
import java.util.List;

/**
 * More static utility methods for {@link List}.  Extends those from Google Guava: {@link Lists}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Convert to interface so this can be more easily mocked?  Lists2Impl.
@FullyTested
public final class Lists2 {

    private Lists2() {
        // disabled
    }

    /**
     * Creates a list from at least one value.  This method is useful to transform arguments of
     * style {@code (T value1, T... moreValueArr)} to a list.  This pattern is an extension of pure
     * variable arguments, where the minimum argument count is one.  Normally, pure variable
     * arguments allow zero arguments.
     * <p>
     * Currently, this implementation uses {@link AbstractList}, but this is subject to change in
     * future versions.
     *
     * @param value1
     *        first value.  Can be {@code null}.
     * @param valueArr
     *        other values.  Can be empty, but not {@code null}.
     *
     * @return random access list view backed by at least one value
     *
     * @throws NullPointerException
     *         if {@code valueArr} is {@code null}, e.g., {@code (Object[]) null}
     *
     * @see #newUnmodifiableListFromTwoOrMoreValues(Object, Object, Object[])
     * @see AbstractList
     */
    public static <T> List<T> newUnmodifiableListFromOneOrMoreValues(
            final T value1, final T... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        return new AbstractList<T>() {
            @Override
            public T get(int index) {
                _checkAccessIndex(valueArr, index, 1);
                if (0 == index) {
                    return value1;
                }
                else {
                    return valueArr[index - 1];
                }
            }

            @Override
            public int size() {
                int x = 1 + valueArr.length;
                return x;
            }
        };
    }

    /**
     * Creates a list from at least two values.  This method is useful to transform arguments of
     * style {@code (T value1, T value2, T... moreValueArr)} to a list.  This pattern is an
     * extension of pure variable arguments, where the minimum argument count is two.  Normally,
     * pure variable arguments allow zero arguments.
     * <p>
     * Currently, the implementation uses {@link AbstractList}, but this may be subject to change
     * in future versions.
     *
     * @param value1
     *        first value.  Can be {@code null}.
     * @param value2
     *        second value.  Can be {@code null}.
     * @param valueArr
     *        other values.  Can be empty, but not {@code null}.
     *
     * @return random access list view backed by at least two values
     *
     * @throws NullPointerException
     *         if {@code valueArr} is {@code null}, e.g., {@code (Object[]) null}
     *
     * @see #newUnmodifiableListFromOneOrMoreValues(Object, Object[])
     * @see AbstractList
     */
    public static <T> List<T> newUnmodifiableListFromTwoOrMoreValues(
            final T value1, final T value2, final T... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        return new AbstractList<T>() {
            @Override
            public T get(int index) {
                _checkAccessIndex(valueArr, index, 2);
                if (0 == index) {
                    return value1;
                }
                else if (1 == index) {
                    return value2;
                }
                else {
                    return valueArr[index - 2];
                }
            }

            @Override
            public int size() {
                int x = 2 + valueArr.length;
                return x;
            }
        };
    }

    private static <T> void _checkAccessIndex(T[] valueArr, int index, int minSize) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(
                String.format("Index is negative: %d", index));
        }
        if (index >= minSize + valueArr.length) {
            throw new IndexOutOfBoundsException(
                String.format("index %d >= length %d", index, minSize + valueArr.length));
        }
    }
}
