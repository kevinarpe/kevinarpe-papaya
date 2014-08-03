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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

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
 * @see MapFactory
 * @see ImmutableMapBuilder
 * @see HashMapFactory
 * @see LinkedHashMapFactory
 * @see PropertiesFactory
 */
public final class ImmutableMapFactory<TKey, TValue>
extends ForwardingMapFactoryHelper<TKey, TValue, ImmutableMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    ImmutableMap<TKey, TValue>,
                    ImmutableMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> ImmutableMapFactory<TKey, TValue> create() {
        ImmutableMapFactory<TKey, TValue> x = new ImmutableMapFactory<TKey, TValue>();
        return x;
    }

    private final MapFactoryHelper<TKey, TValue, ImmutableMap<TKey, TValue>> _mapFactoryHelper;

    private ImmutableMapFactory() {
        this(new MapBuilderUtils<TKey, TValue, ImmutableMap<TKey, TValue>>());
    }

    ImmutableMapFactory(IMapBuilderUtils<TKey, TValue, ImmutableMap<TKey, TValue>> mapBuilderUtils) {
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, ImmutableMap<TKey, TValue>>(
                new _MinimalistMapBuilderFactory<TKey, TValue>(),
                mapBuilderUtils);
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, ImmutableMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
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

    private static final class _MinimalistMapBuilderFactory<TKey, TValue>
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        ImmutableMap<TKey, TValue>,
                        _MinimalistMapBuilder<TKey, TValue>
                    > {

        @Override
        public _MinimalistMapBuilder<TKey, TValue> newInstance() {
            _MinimalistMapBuilder<TKey, TValue> x = new _MinimalistMapBuilder<TKey, TValue>();
            return x;
        }
    }

    private static final class _MinimalistMapBuilder<TKey, TValue>
    implements MinimalistMapBuilder<TKey, TValue, ImmutableMap<TKey, TValue>> {

        private final ImmutableMap.Builder<TKey, TValue> builder;

        private _MinimalistMapBuilder() {
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
}
