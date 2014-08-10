package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Iterator;

/**
 * Creates {@code ImmutableListBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 *
 * @see #create()
 * @see ListFactory
 * @see ImmutableListBuilder
 * @see ArrayListFactory
 * @see LinkedListFactory
 */
public final class ImmutableListFactory<TValue>
extends StatelessObject
implements ListFactory<TValue, ImmutableList<TValue>, ImmutableListBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> ImmutableListFactory<TValue> create() {
        ImmutableListFactory<TValue> x = new ImmutableListFactory<TValue>();
        return x;
    }

    private ImmutableListFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableListBuilder<TValue> builder() {
        ImmutableListBuilder<TValue> x = ImmutableListBuilder.create();
        return x;
    }

    @Override
    public ImmutableList<TValue> copyOf(TValue value, TValue... moreValueArr) {
        return null;
    }

    /**
     * Immutable lists do not allow {@code null} elements.
     * <hr/>
     * {@inheritDoc}
     *
     * @throws NullPointerException
     *         if {@code collection} (or any element) is {@code null}
     */
    @Override
    public ImmutableList<TValue> copyOf(TValue... moreValueArr) {
        ArrayArgs.checkElementsNotNull(moreValueArr, "elementArr");

        ImmutableList<TValue> x = ImmutableList.copyOf(moreValueArr);
        return x;
    }

    /**
     * Immutable lists do not allow {@code null} elements.
     * <hr/>
     * {@inheritDoc}
     *
     * @throws NullPointerException
     *         if {@code collection} (or any element) is {@code null}
     * @param iterable
     */
    @Override
    public ImmutableList<TValue> copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        ImmutableList<TValue> x = ImmutableList.copyOf(iterable);
        return x;
    }

    @Override
    public ImmutableList<TValue> copyOf(Iterator<? extends TValue> iter) {
        return null;
    }
}
