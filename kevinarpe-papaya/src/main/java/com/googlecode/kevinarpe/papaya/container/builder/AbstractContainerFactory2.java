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
public abstract class AbstractContainerFactory2
    <
        TValue,
        TContainer,
        TContainerBuilder extends Builder<TContainer>
    >
extends StatelessObject
implements ContainerFactory2<TValue, TContainer, TContainerBuilder> {

    private final MinimalistContainerBuilderFactory<TValue, TContainer> _builderFactory;

    protected AbstractContainerFactory2(
            MinimalistContainerBuilderFactory<TValue, TContainer> builderFactory) {
        super();
        _builderFactory = ObjectArgs.checkNotNull(builderFactory, "builderFactory");
    }

    @Override
    public final TContainer of() {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(0);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(1);
        builder.add(v);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(2);
        builder.add(v);
        builder.add(v2);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(3);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(4);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(5);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(6);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(7);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(8);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        builder.add(v8);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(9);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        builder.add(v8);
        builder.add(v9);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(10);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        builder.add(v8);
        builder.add(v9);
        builder.add(v10);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10, TValue v11) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(11);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        builder.add(v8);
        builder.add(v9);
        builder.add(v10);
        builder.add(v11);
        TContainer x = builder.build();
        return x;
    }

    @Override
    public final TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10, TValue v11, TValue v12, TValue... moreValueArr) {
        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(12);
        builder.add(v);
        builder.add(v2);
        builder.add(v3);
        builder.add(v4);
        builder.add(v5);
        builder.add(v6);
        builder.add(v7);
        builder.add(v8);
        builder.add(v9);
        builder.add(v10);
        builder.add(v11);
        builder.add(v12);
        Iterator<TValue> iter = Iterators.forArray(moreValueArr);
        TContainer x = _addAll(builder, iter);
        return x;
    }

    @Override
    public final TContainer copyOf(TValue[] valueArr) {
        ObjectArgs.checkNotNull(valueArr, "valueArr");

        MinimalistContainerBuilder<TValue, TContainer> builder =
            _builderFactory.newInstanceWithCapacity(valueArr.length);
        Iterator<TValue> iter = Iterators.forArray(valueArr);
        TContainer x = _addAll(builder, iter);
        return x;
    }

    @Override
    public final TContainer copyOf(Iterable<? extends TValue> iterable) {
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
    public final TContainer copyOf(Iterator<? extends TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        MinimalistContainerBuilder<TValue, TContainer> builder = _builderFactory.newInstance();
        TContainer x = _addAll(builder, iter);
        return x;
    }

    private TContainer _addAll(
            MinimalistContainerBuilder<TValue, TContainer> builder,
            Iterator<? extends TValue> iter) {
        while (iter.hasNext()) {
            TValue value = iter.next();
            builder.add(value);
        }
        TContainer x = builder.build();
        return x;
    }
}
