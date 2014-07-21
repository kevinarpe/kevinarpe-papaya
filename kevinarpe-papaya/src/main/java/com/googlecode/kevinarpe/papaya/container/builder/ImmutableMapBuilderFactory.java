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

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Map;

/**
 * Creates {@code ImmutableMapBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 *
 * @see #create()
 * @see MapBuilderFactory
 * @see ImmutableMapBuilder
 * @see HashMapBuilderFactory
 * @see LinkedHashMapBuilderFactory
 * @see PropertiesBuilderFactory
 */
@FullyTested
public final class ImmutableMapBuilderFactory<TKey, TValue>
extends AbstractMapBuilderFactory
                <
                    TKey,
                    TValue,
                    ImmutableMap<TKey, TValue>,
                    ImmutableMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> ImmutableMapBuilderFactory<TKey, TValue> create() {
        ImmutableMapBuilderFactory<TKey, TValue> x = new ImmutableMapBuilderFactory<TKey, TValue>();
        return x;
    }

    private ImmutableMapBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableMapBuilder<TKey, TValue> builder() {
        ImmutableMapBuilder<TKey, TValue> x = ImmutableMapBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        ImmutableMap<TKey, TValue> x = ImmutableMap.copyOf(map);
        return x;
    }

    private static final class _MapBuilderFactoryHelper<TKey, TValue>
    implements MapBuilderFactoryHelper<TKey, TValue, ImmutableMap<TKey, TValue>> {

        private final ImmutableMap.Builder<TKey, TValue> builder;

        private _MapBuilderFactoryHelper() {
            builder = ImmutableMap.builder();
        }

        @Override
        public void put(TKey key, TValue value) {
            builder.put(key, value);
        }

        @Override
        public ImmutableMap<TKey, TValue> getMap() {
            ImmutableMap<TKey, TValue> x = builder.build();
            return x;
        }
    }

    @Override
    protected MapBuilderFactoryHelper<TKey, TValue, ImmutableMap<TKey, TValue>>
    createMapBuilderHelper(int initialCapacity) {
        _MapBuilderFactoryHelper<TKey, TValue> x =
            new _MapBuilderFactoryHelper<TKey, TValue>();
        return x;
    }
}
