package com.googlecode.kevinarpe.papaya.container;

/*
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class has the same effect as:
 * {@code Collections.unmodifiableList(Arrays.asList(arr))}.  This collection is
 * unmodifiable, so no access is provided to the underlying array.
 * <p>
 * The class constructor is private.  Instead, use static method {@link #referenceTo(Object[])}.
 * <p>
 * This class is closely related to {@link ArrayAsFixedSizeList} and
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
 * @see #referenceTo(Object[])
 * @see UnmodifiableForwardingList
 * @see Collections#unmodifiableList(List)
 * @see Arrays#asList(Object[])
 * @see ArrayAsFixedSizeList
 * @see ArrayAsImmutableList
 */
@FullyTested
public final class ArrayAsUnmodifiableList<TValue>
extends UnmodifiableForwardingList<TValue> {

    /**
     * Constructs a new instance with a <i>reference</i> to the input array.
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
     */
    public static <TValue> ArrayAsUnmodifiableList<TValue> referenceTo(TValue[] arr) {
        return new ArrayAsUnmodifiableList<TValue>(arr);
    }

    private final TValue[] _arr;
    private final List<TValue> _arrAsList;

    private ArrayAsUnmodifiableList(TValue[] arr) {
        ObjectArgs.checkNotNull(arr, "arr");

        _arr = arr;
        _arrAsList = Arrays.asList(arr);
    }

    @Override
    protected List<TValue> delegate() {
        return _arrAsList;
    }
}
