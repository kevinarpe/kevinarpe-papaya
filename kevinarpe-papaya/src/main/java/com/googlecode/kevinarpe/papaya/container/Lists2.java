package com.googlecode.kevinarpe.papaya.container;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.AbstractList;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class Lists2 {

    public static <T> List<T> newUnmodifiableListFromOneOrMore(final T value1, final T... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");  // TODO: Possible?

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
                int x = 2 + valueArr.length;
                return x;
            }
        };
    }

    public static <T> List<T> newUnmodifiableListFromTwoOrMore(
            final T value1, final T value2, final T... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");  // TODO: Possible?

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
