package com.googlecode.kevinarpe.papaya.container;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.*;

/**
 * This class has the same effect as:
 * {@code Collections.unmodifiableList(Arrays.asList(arr.clone()))}.  This collection is
 * immutable, so no access is provided to the underlying array.
 * <p>
 * The class constructor is private.  Instead, use static method {@link #copyOf(Object[])}.
 * <p>
 * This class is closely related to {@link ArrayAsFixedSizeList} and
 * {@link ArrayAsUnmodifiableList}.  To understand the difference between unmodifiable and
 * immutable, learn more here:
 * <a href="http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection"
 * >http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection</a>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        element type
 *
 * @see #copyOf(Object[])
 * @see UnmodifiableForwardingList
 * @see Collections#unmodifiableList(List)
 * @see Arrays#asList(Object[])
 * @see ArrayAsFixedSizeList
 * @see ArrayAsUnmodifiableList
 */
@FullyTested
public final class ArrayAsImmutableList<TValue>
extends UnmodifiableForwardingList<TValue> {

    /**
     * Constructs a new instance with a <i>copy</i> of the input array.
     *
     * @param arr
     *        array of values to be copied.  Must not be {@code null}, but can by empty or contain
     *        {@code null} values.
     * @param <TValue>
     *        element type
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     */
    public static <TValue> ArrayAsImmutableList<TValue> copyOf(TValue[] arr) {
        return new ArrayAsImmutableList<TValue>(arr);
    }

    private final TValue[] _arr;
    private final List<TValue> _arrAsList;

    private ArrayAsImmutableList(TValue[] arr) {
        ObjectArgs.checkNotNull(arr, "arr");

        _arr = arr.clone();
        _arrAsList = Arrays.asList(arr);
    }

    @Override
    protected List<TValue> delegate() {
        return _arrAsList;
    }
}
