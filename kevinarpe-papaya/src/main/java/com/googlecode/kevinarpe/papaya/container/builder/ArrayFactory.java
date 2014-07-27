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

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public final class ArrayFactory<TValue>
implements ContainerFactory<TValue, TValue[], ArrayBuilder<TValue>> {

    public static<TValue> ArrayFactory<TValue> create(Class<TValue> componentType) {
        ArrayFactory<TValue> x = new ArrayFactory<TValue>(componentType);
        return x;
    }

    private final Class<TValue> _componentType;

    private ArrayFactory(Class<TValue> componentType) {
        _componentType = ObjectArgs.checkNotNull(componentType, "componentType");
    }

    /** {@inheritDoc} */
    @Override
    public TValue[] copyOf(TValue... elementArr) {
        ObjectArgs.checkNotNull(elementArr, "elementArr");

        TValue[] x = Arrays.copyOf(elementArr, elementArr.length);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public TValue[] copyOf(Iterable<? extends TValue> iterable) {
        ObjectArgs.checkNotNull(iterable, "iterable");

        if (iterable instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<? extends TValue> collection = (Collection<? extends TValue>) iterable;
            TValue[] arr = ObjectArrays.newArray(_componentType, collection.size());
            arr = collection.toArray(arr);
            return arr;
        }
        else {
            ArrayList<? extends TValue> list = Lists.newArrayList(iterable);
            TValue[] arr = ObjectArrays.newArray(_componentType, list.size());
            arr = list.toArray(arr);
            return arr;
        }
    }

    /** {@inheritDoc} */
    @Override
    public ArrayBuilder<TValue> builder() {
        ArrayBuilder<TValue> x = ArrayBuilder.create(_componentType);
        return x;
    }
}
