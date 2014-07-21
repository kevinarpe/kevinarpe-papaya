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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Collection;
import java.util.HashSet;

/**
 * Creates {@code HashMapBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for set elements
 *
 * @see #create()
 * @see SetBuilderFactory
 * @see HashSetBuilder
 * @see LinkedHashSetBuilderFactory
 * @see ImmutableSetBuilderFactory
 */
@FullyTested
public final class HashSetBuilderFactory<TValue>
extends StatelessObject
implements SetBuilderFactory<TValue, HashSet<TValue>, HashSetBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> HashSetBuilderFactory<TValue> create() {
        HashSetBuilderFactory<TValue> x = new HashSetBuilderFactory<TValue>();
        return x;
    }

    private HashSetBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public HashSetBuilder<TValue> builder() {
        HashSetBuilder<TValue> x = HashSetBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public HashSet<TValue> copyOf(TValue... elementArr) {
        ObjectArgs.checkNotNull(elementArr, "elementArr");

        HashSet<TValue> x = Sets.newHashSet(elementArr);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public HashSet<TValue> copyOf(Collection<? extends TValue> collection) {
        ObjectArgs.checkNotNull(collection, "collection");

        HashSet<TValue> x = new HashSet<TValue>(collection);
        return x;
    }
}
