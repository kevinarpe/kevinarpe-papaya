package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/**
 * Builds {@code ImmutableList} collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 *
 * @see #create()
 * @see ListBuilder
 * @see ImmutableList
 * @see ImmutableListBuilderFactory
 * @see ArrayListBuilder
 * @see LinkedListBuilder
 */
@FullyTested
public final class ImmutableListBuilder<TValue>
extends AbstractListBuilder
            <
                ImmutableList<TValue>,
                TValue
            > {

    /**
     * Constructs a new builder.
     */
    public static <TValue> ImmutableListBuilder<TValue> create() {
        ImmutableListBuilder<TValue> x = new ImmutableListBuilder<TValue>();
        return x;
    }

    private ImmutableListBuilder() {
        // Empty
    }

    /**
     * Builds a new {@code ArrayList} from values stored in the builder.
     */
    @Override
    public ImmutableList<TValue> build() {
        ImmutableList<TValue> x = ImmutableList.copyOf(delegate());
        return x;
    }
}
