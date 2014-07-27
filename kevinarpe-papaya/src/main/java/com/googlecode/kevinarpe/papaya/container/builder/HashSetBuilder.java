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

import java.util.HashSet;

/**
 * Builds {@code HashSet} collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for set elements
 *
 * @see SetBuilder
 * @see HashSet
 * @see HashSetFactory
 * @see LinkedHashSetBuilder
 * @see ImmutableSetBuilder
 */
@FullyTested
public final class HashSetBuilder<TValue>
extends AbstractSetBuilder
            <
                TValue,
                HashSet<TValue>,
                HashSetBuilder<TValue>
            > {

    /**
     * Constructs a new builder.
     */
    public static <TValue> HashSetBuilder<TValue> create() {
        HashSetBuilder<TValue> x = new HashSetBuilder<TValue>();
        return x;
    }

    private HashSetBuilder() {
        // Empty
    }

    /**
     * Builds a new {@code HashSet} from values stored in the builder.
     */
    @Override
    public HashSet<TValue> build() {
        HashSet<TValue> x = Sets.newHashSet(delegate());
        return x;
    }

    @Override
    protected HashSetBuilder<TValue> self() {
        return this;
    }
}
