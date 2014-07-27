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
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.Iterator;
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
        TValue,
        TSet extends Set<TValue>,
        TSelf extends AbstractSetBuilder<TValue, TSet, TSelf>
    >
extends ForwardingSet<TValue>
implements SetBuilder<TValue, TSet, TSelf> {

    private final LinkedHashSet<TValue> _set;

    protected AbstractSetBuilder() {
        _set = Sets.newLinkedHashSet();
    }

    @Override
    protected final Set<TValue> delegate() {
        return _set;
    }

    @Override
    public abstract TSet build();

    protected abstract TSelf self();

    @Override
    public final TSelf addMany(TValue... valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        if (0 != valueArr.length) {
            addMany(Arrays.asList(valueArr));
        }
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf addMany(Iterable<? extends TValue> valueIterable) {
        ObjectArgs.checkNotNull(valueIterable, "valueIterable");

        Iterator<? extends TValue> valueIter = valueIterable.iterator();
        addMany(valueIter);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf addMany(Iterator<? extends TValue> valueIter) {
        ObjectArgs.checkNotNull(valueIter, "valueIter");

        Iterators.addAll(_set, valueIter);
        TSelf x = self();
        return x;
    }
}
