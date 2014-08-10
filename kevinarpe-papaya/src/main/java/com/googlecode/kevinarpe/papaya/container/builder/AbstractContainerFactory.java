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

import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractContainerFactory
    <
        TValue,
        TContainer,
        TContainerBuilder extends Builder<TContainer>
    >
extends StatelessObject
implements ContainerFactory<TValue, TContainer, TContainerBuilder> {

    private final MinimalistContainerBuilderFactory<TValue, TContainer> _builderFactory;

    protected AbstractContainerFactory(
            MinimalistContainerBuilderFactory<TValue, TContainer> builderFactory) {
        _builderFactory = builderFactory;
    }

    @Override
    public TContainer copyOf(TValue value, TValue... moreValueArr) {
        return null;
    }

    @Override
    public TContainer copyOf(TValue[] valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(valueArr.length);
        Iterator<TValue> iter = Iterators.forArray(valueArr);
        TContainer x = _addAll(builder, iter);
        return x;
    }

    @Override
    public TContainer copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        MinimalistContainerBuilder<TValue, TContainer> builder = null;
        if (iterable instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<? extends TValue> c = (Collection<? extends TValue>) iterable;
            builder = _builderFactory.newInstanceWithCapacity(c.size());
        }
        else {
            builder = _builderFactory.newInstance();
        }
        Iterator<? extends TValue> iter = iterable.iterator();
        TContainer x = _addAll(builder, iter);
        return x;
    }

    @Override
    public TContainer copyOf(Iterator<? extends TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        MinimalistContainerBuilder<TValue, TContainer> builder = _builderFactory.newInstance();
        TContainer x = _addAll(builder, iter);
        return x;
    }

    private TContainer _addAll(
            MinimalistContainerBuilder<TValue, TContainer> builder, Iterator<? extends TValue> iter) {
        while (iter.hasNext()) {
            TValue value = iter.next();
            builder.add(value);
        }
        TContainer x = builder.build();
        return x;
    }
}
