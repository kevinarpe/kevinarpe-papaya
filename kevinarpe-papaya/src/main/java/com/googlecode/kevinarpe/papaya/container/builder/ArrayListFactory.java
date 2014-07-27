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

import java.util.ArrayList;

/**
 * Creates {@code ArrayListBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 *
 * @see #create()
 * @see ListFactory
 * @see ArrayListBuilder
 * @see LinkedListFactory
 * @see ImmutableListFactory
 */
@FullyTested
public final class ArrayListFactory<TValue>
extends StatelessObject
implements ListFactory<TValue, ArrayList<TValue>, ArrayListBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> ArrayListFactory<TValue> create() {
        ArrayListFactory<TValue> x = new ArrayListFactory<TValue>();
        return x;
    }

    private ArrayListFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public ArrayListBuilder<TValue> builder() {
        ArrayListBuilder<TValue> x = ArrayListBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public ArrayList<TValue> copyOf(TValue... elementArr) {
        ObjectArgs.checkNotNull(elementArr, "elementArr");

        ArrayList<TValue> x = Lists.newArrayList(elementArr);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public ArrayList<TValue> copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        ArrayList<TValue> x = Lists.newArrayList(iterable);
        return x;
    }
}
