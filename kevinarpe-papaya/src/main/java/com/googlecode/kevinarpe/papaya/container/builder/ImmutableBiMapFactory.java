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

import com.google.common.collect.ImmutableBiMap;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Map;

/**
 * Creates {@code HashMapBuilder}s.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 *
 * @see MapFactory
 * @see HashMapBuilder
 * @see LinkedHashMapFactory
 * @see ImmutableMapFactory
 * @see PropertiesFactory
 */
public final class ImmutableBiMapFactory<TKey, TValue>
extends ForwardingMapFactoryHelper<TKey, TValue, ImmutableBiMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    ImmutableBiMap<TKey, TValue>,
                    ImmutableBiMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> ImmutableBiMapFactory<TKey, TValue> create() {
        ImmutableBiMapFactory<TKey, TValue> x = new ImmutableBiMapFactory<TKey, TValue>();
        return x;
    }

    private final MapFactoryHelper<TKey, TValue, ImmutableBiMap<TKey, TValue>> _mapFactoryHelper;

    private ImmutableBiMapFactory() {
        this(new MapBuilderUtils<TKey, TValue, ImmutableBiMap<TKey, TValue>>());
    }

    ImmutableBiMapFactory(IMapBuilderUtils<TKey, TValue, ImmutableBiMap<TKey, TValue>> mapBuilderUtils) {
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, ImmutableBiMap<TKey, TValue>>(
                new _MinimalistMapBuilderFactory<TKey, TValue>(),
                mapBuilderUtils);
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableBiMapBuilder<TKey, TValue> builder() {
        ImmutableBiMapBuilder<TKey, TValue> x = ImmutableBiMapBuilder.create();
        return x;
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, ImmutableBiMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableBiMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        ImmutableBiMap<TKey, TValue> x = ImmutableBiMap.copyOf(map);
        return x;
    }

    private static final class _MinimalistMapBuilderFactory<TKey, TValue>
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        ImmutableBiMap<TKey, TValue>,
                        _MinimalistMapBuilder<TKey, TValue>
                    > {

        @Override
        public _MinimalistMapBuilder<TKey, TValue> newInstance() {
            _MinimalistMapBuilder<TKey, TValue> x = new _MinimalistMapBuilder<TKey, TValue>();
            return x;
        }
    }

    private static final class _MinimalistMapBuilder<TKey, TValue>
    implements MinimalistMapBuilder<TKey, TValue, ImmutableBiMap<TKey, TValue>> {

        private final ImmutableBiMap.Builder<TKey, TValue> builder;

        private _MinimalistMapBuilder() {
            builder = ImmutableBiMap.builder();
        }

        @Override
        public void put(TKey key, TValue value) {
            builder.put(key, value);
        }

        @Override
        public ImmutableBiMap<TKey, TValue> getMap() {
            ImmutableBiMap<TKey, TValue> x = builder.build();
            return x;
        }
    }
}
