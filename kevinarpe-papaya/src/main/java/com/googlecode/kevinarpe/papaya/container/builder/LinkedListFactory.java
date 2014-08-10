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

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Creates {@code LinkedListBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 *
 * @see #create()
 * @see ListFactory
 * @see LinkedListBuilder
 * @see ArrayListFactory
 * @see ImmutableListFactory
 */
public final class LinkedListFactory<TValue>
extends StatelessObject
implements ListFactory<TValue, LinkedList<TValue>, LinkedListBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> LinkedListFactory<TValue> create() {
        LinkedListFactory<TValue> x = new LinkedListFactory<TValue>();
        return x;
    }

    private LinkedListFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public LinkedListBuilder<TValue> builder() {
        LinkedListBuilder<TValue> x = LinkedListBuilder.create();
        return x;
    }

    @Override
    public LinkedList<TValue> copyOf(TValue value, TValue... moreValueArr) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedList<TValue> copyOf(TValue... moreValueArr) {
        ObjectArgs.checkNotNull(moreValueArr, "elementArr");

        LinkedList<TValue> x = Lists.newLinkedList(Arrays.asList(moreValueArr));
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedList<TValue> copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        LinkedList<TValue> x = Lists.newLinkedList(iterable);
        return x;
    }

    @Override
    public LinkedList<TValue> copyOf(Iterator<? extends TValue> iter) {
        return null;
    }
}
