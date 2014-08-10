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

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ObjectArrays;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ArrayBuilder<TValue>
extends ForwardingList<TValue>
implements ContainerBuilder<TValue, TValue[], ArrayBuilder<TValue>> {

    /**
     * Constructs a new builder.
     */
    public static <TValue> ArrayBuilder<TValue> create(Class<TValue> componentType) {
        ArrayBuilder<TValue> x = new ArrayBuilder<TValue>(componentType);
        return x;
    }

    private final Class<TValue> _componentType;
    private final ArrayListBuilder<TValue> _arrayListBuilder;

    private ArrayBuilder(Class<TValue> componentType) {
        _componentType = ObjectArgs.checkNotNull(componentType, "componentType");
        _arrayListBuilder = ArrayListBuilder.create();
    }

    public Class<TValue> getComponentType() {
        return _componentType;
    }

    @Override
    public ArrayBuilder<TValue> addMany(TValue value, TValue... moreValueArr) {
        _arrayListBuilder.addMany(value, moreValueArr);
        return this;
    }

    @Override
    public ArrayBuilder<TValue> addMany(TValue[] valueArr) {
        _arrayListBuilder.addMany(valueArr);
        return this;
    }

    @Override
    public ArrayBuilder<TValue> addMany(Iterable<? extends TValue> valueIterable) {
        _arrayListBuilder.addMany(valueIterable);
        return this;
    }

    @Override
    public ArrayBuilder<TValue> addMany(Iterator<? extends TValue> valueIter) {
        _arrayListBuilder.addMany(valueIter);
        return this;
    }

    @Override
    public TValue[] build() {
        ArrayList<TValue> list = _arrayListBuilder.build();
        TValue[] arr = ObjectArrays.newArray(_componentType, list.size());
        arr = list.toArray(arr);
        return arr;
    }

    @Override
    protected List<TValue> delegate() {
        return _arrayListBuilder;
    }
}
