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

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all implementations of {@code ListBuilder}.  The underlying map is an instance of
 * {@link ArrayList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TList>
 *        type of list to build: {@code extends List<TValue>}
 * @param <TValue>
 *        type of value for list elements
 *
 * @see ForwardingList
 * @see ListBuilder
 * @see ArrayListBuilder
 * @see LinkedListBuilder
 * @see ImmutableListBuilder
 */
@FullyTested
public abstract class AbstractListBuilder
    <
        TList extends List<TValue>,
        TValue
    >
extends ForwardingList<TValue>
implements ListBuilder<TList, TValue> {

    private final ArrayList<TValue> _list;

    protected AbstractListBuilder() {
        _list = Lists.newArrayList();
    }

    @Override
    protected final List<TValue> delegate() {
        return _list;
    }

    @Override
    public abstract TList build();
}
