package com.googlecode.kevinarpe.papaya.container;

/*
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.List;

/**
 * This class combines features of {@link FixedSizeForwardingList} with
 * {@link Arrays#asList(Object[])}.  Access to the underlying array is available via
 * {@link #getArrayRef()}.
 * <p>
 * The class constructor is private.  Instead, use static method {@link #copyOf(Object[])} or
 * {@link #referenceTo(Object[])}.
 * <p>
 * This class is closely related to {@link ArrayAsUnmodifiableList} and
 * {@link ArrayAsImmutableList}.  To understand the difference between unmodifiable and immutable,
 * learn more here:
 * <a href="http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection"
 * >http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection</a>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        element type
 *
 * @see #copyOf(Object[])
 * @see #referenceTo(Object[])
 * @see #getArrayRef()
 * @see FixedSizeForwardingList
 * @see Arrays#asList(Object[])
 * @see ArrayAsUnmodifiableList
 * @see ArrayAsImmutableList
 */
@FullyTested
public final class ArrayAsFixedSizeList<TValue>
extends FixedSizeForwardingList<TValue> {

    /**
     * Constructs a new instance with a <i>copy</i> of the input array.  This will create an
     * immutable collection based upon the input array.
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
     *
     * @see #referenceTo(Object[])
     */
    public static <TValue> ArrayAsFixedSizeList<TValue> copyOf(TValue[] arr) {
        final boolean shouldCloneArr = true;
        return new ArrayAsFixedSizeList<TValue>(arr, shouldCloneArr);
    }

    /**
     * Constructs a new instance with a <i>reference</i> to the input array.  This will create a
     * unmodifiable view of the input array.
     *
     * @param arr
     *        array of values to be referenced.  Must not be {@code null}, but can by empty or
     *        contain {@code null} values.
     * @param <TValue>
     *        element type
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     *
     * @see #copyOf(Object[])
     */
    public static <TValue> ArrayAsFixedSizeList<TValue> referenceTo(TValue[] arr) {
        final boolean shouldCloneArr = false;
        return new ArrayAsFixedSizeList<TValue>(arr, shouldCloneArr);
    }

    private final TValue[] arr;
    private final List<TValue> arrAsList;

    private ArrayAsFixedSizeList(TValue[] arr, boolean shouldCloneArr) {
        ObjectArgs.checkNotNull(arr, "arr");

        if (shouldCloneArr) {
            arr = arr.clone();
        }
        this.arr = arr;
        arrAsList = Arrays.asList(this.arr);
    }

    @Override
    protected List<TValue> delegate() {
        return arrAsList;
    }

    /**
     * @return reference to underlying array
     */
    public TValue[] getArrayRef() {
        return arr;
    }
}
