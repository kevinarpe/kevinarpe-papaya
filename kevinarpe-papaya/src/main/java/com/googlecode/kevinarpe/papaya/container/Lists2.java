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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.AbstractList;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Lists2 {

    private Lists2() {
        // disabled
    }

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
