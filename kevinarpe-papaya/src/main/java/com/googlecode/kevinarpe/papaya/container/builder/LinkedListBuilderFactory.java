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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Arrays;
import java.util.Collection;
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
 * @see ListBuilderFactory
 * @see LinkedListBuilder
 * @see ArrayListBuilderFactory
 * @see ImmutableListBuilderFactory
 */
@FullyTested
public final class LinkedListBuilderFactory<TValue>
extends StatelessObject
implements ListBuilderFactory<TValue, LinkedList<TValue>, LinkedListBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> LinkedListBuilderFactory<TValue> create() {
        LinkedListBuilderFactory<TValue> x = new LinkedListBuilderFactory<TValue>();
        return x;
    }

    private LinkedListBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public LinkedListBuilder<TValue> builder() {
        LinkedListBuilder<TValue> x = LinkedListBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedList<TValue> copyOf(TValue... elementArr) {
        ObjectArgs.checkNotNull(elementArr, "elementArr");

        LinkedList<TValue> x = Lists.newLinkedList(Arrays.asList(elementArr));
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedList<TValue> copyOf(Collection<? extends TValue> collection) {
        ObjectArgs.checkNotNull(collection, "collection");

        LinkedList<TValue> x = new LinkedList<TValue>(collection);
        return x;
    }
}
