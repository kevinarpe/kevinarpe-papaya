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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public abstract class ForwardingMapFactoryHelper
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>
    >
implements MapFactoryHelper<TKey, TValue, TMap> {

    protected abstract MapFactoryHelper<TKey, TValue, TMap> delegate();

    @Override
    public TMap copyOf(Class<TKey> keyClass, Class<TValue> valueClass, Object... keysAndValuesArr) {
        MapFactoryHelper<TKey, TValue, TMap> delegate = delegate();
        TMap x = delegate.copyOf(keyClass, valueClass, keysAndValuesArr);
        return x;
    }

    @Override
    public TMap copyOf(
            Iterable<? extends TKey> keyIterable,
            Iterable<? extends TValue> valueIterable) {
        MapFactoryHelper<TKey, TValue, TMap> delegate = delegate();
        TMap x = delegate.copyOf(keyIterable, valueIterable);
        return x;
    }

    @Override
    public TMap copyOf(Map.Entry<? extends TKey, ? extends TValue>... entryArr) {
        MapFactoryHelper<TKey, TValue, TMap> delegate = delegate();
        TMap x = delegate.copyOf(entryArr);
        return x;
    }

    @Override
    public TMap copyOf(
            Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable) {
        MapFactoryHelper<TKey, TValue, TMap> delegate = delegate();
        TMap x = delegate.copyOf(entryIterable);
        return x;
    }
}
