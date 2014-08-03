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

import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Creates {@code LinkedHashMapBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for set elements
 *
 * @see #create()
 * @see SetFactory
 * @see LinkedHashSetBuilder
 * @see HashSetFactory
 * @see ImmutableSetFactory
 */
public final class LinkedHashSetFactory<TValue>
extends StatelessObject
implements SetFactory<TValue, LinkedHashSet<TValue>, LinkedHashSetBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> LinkedHashSetFactory<TValue> create() {
        LinkedHashSetFactory<TValue> x = new LinkedHashSetFactory<TValue>();
        return x;
    }

    private LinkedHashSetFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public LinkedHashSetBuilder<TValue> builder() {
        LinkedHashSetBuilder<TValue> x = LinkedHashSetBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedHashSet<TValue> copyOf(TValue... elementArr) {
        ObjectArgs.checkNotNull(elementArr, "elementArr");

        LinkedHashSet<TValue> x = new LinkedHashSet<TValue>(Arrays.asList(elementArr));
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedHashSet<TValue> copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        LinkedHashSet<TValue> x = Sets.newLinkedHashSet(iterable);
        return x;
    }
}
