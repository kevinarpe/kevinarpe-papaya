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

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Base class for all implementations of {@code SetBuilder}.  The underlying map is an instance of
 * {@link LinkedHashSet}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TSet>
 *        type of set to build: extends Set&lt;TValue>
 * @param <TValue>
 *        type of value for set elements
 *
 * @see ForwardingSet
 * @see SetBuilder
 * @see HashSetBuilder
 * @see LinkedHashSetBuilder
 * @see ImmutableSetBuilder
 */
@FullyTested
public abstract class AbstractSetBuilder
    <
        TSet extends Set<TValue>,
        TValue
    >
extends ForwardingSet<TValue>
implements SetBuilder<TSet, TValue> {

    private final LinkedHashSet<TValue> _list;

    protected AbstractSetBuilder() {
        _list = Sets.newLinkedHashSet();
    }

    @Override
    protected final Set<TValue> delegate() {
        return _list;
    }

    @Override
    public abstract TSet build();
}
