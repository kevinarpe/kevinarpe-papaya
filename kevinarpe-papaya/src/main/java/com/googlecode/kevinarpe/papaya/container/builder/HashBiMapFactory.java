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

import com.google.common.collect.HashBiMap;
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
public final class HashBiMapFactory<TKey, TValue>
extends ForwardingMapFactoryHelper<TKey, TValue, HashBiMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    HashBiMap<TKey, TValue>,
                    HashBiMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> HashBiMapFactory<TKey, TValue> create() {
        HashBiMapFactory<TKey, TValue> x = new HashBiMapFactory<TKey, TValue>();
        return x;
    }

    private final MapFactoryHelper<TKey, TValue, HashBiMap<TKey, TValue>> _mapFactoryHelper;

    private HashBiMapFactory() {
        this(new MapBuilderUtils<TKey, TValue, HashBiMap<TKey, TValue>>());
    }

    HashBiMapFactory(IMapBuilderUtils<TKey, TValue, HashBiMap<TKey, TValue>> mapBuilderUtils) {
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, HashBiMap<TKey, TValue>>(
                new _MinimalistMapBuilderFactory<TKey, TValue>(),
                mapBuilderUtils);
    }

    /** {@inheritDoc} */
    @Override
    public HashBiMapBuilder<TKey, TValue> builder() {
        HashBiMapBuilder<TKey, TValue> x = HashBiMapBuilder.create();
        return x;
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, HashBiMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
    }

    /** {@inheritDoc} */
    @Override
    public HashBiMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        HashBiMap<TKey, TValue> x = HashBiMap.create(map);
        return x;
    }

    private static final class _MinimalistMapBuilderFactory<TKey, TValue>
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        HashBiMap<TKey, TValue>,
                        _MinimalistMapBuilder<TKey, TValue>
                    > {

        @Override
        public _MinimalistMapBuilder<TKey, TValue> newInstance() {
            _MinimalistMapBuilder<TKey, TValue> x = new _MinimalistMapBuilder<TKey, TValue>();
            return x;
        }
    }

    private static final class _MinimalistMapBuilder<TKey, TValue>
    implements MinimalistMapBuilder<TKey, TValue, HashBiMap<TKey, TValue>> {

        private final HashBiMap<TKey, TValue> _map;

        private _MinimalistMapBuilder() {
            _map = HashBiMap.create();
        }

        @Override
        public void put(TKey key, TValue value) {
            _map.put(key, value);
        }

        @Override
        public HashBiMap<TKey, TValue> getMap() {
            return _map;
        }
    }
}
