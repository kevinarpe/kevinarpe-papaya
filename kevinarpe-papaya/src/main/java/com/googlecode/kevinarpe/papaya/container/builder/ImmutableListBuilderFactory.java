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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * Creates {@code ImmutableListBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 *
 * @see #create()
 * @see ListBuilderFactory
 * @see ImmutableListBuilder
 * @see ArrayListBuilderFactory
 * @see LinkedListBuilderFactory
 */
@FullyTested
public final class ImmutableListBuilderFactory<TValue>
extends StatelessObject
implements ListBuilderFactory<ImmutableListBuilder<TValue>> {

    /**
     * Constructs a new builder factory.
     */
    public static <TValue> ImmutableListBuilderFactory<TValue> create() {
        ImmutableListBuilderFactory<TValue> x = new ImmutableListBuilderFactory<TValue>();
        return x;
    }

    private ImmutableListBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableListBuilder<TValue> newInstance() {
        ImmutableListBuilder<TValue> x = ImmutableListBuilder.create();
        return x;
    }
}
