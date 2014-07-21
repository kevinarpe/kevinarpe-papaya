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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.HashMap;
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
 * @see MapBuilderFactory
 * @see HashMapBuilder
 * @see LinkedHashMapBuilderFactory
 * @see ImmutableMapBuilderFactory
 * @see PropertiesBuilderFactory
 */
@FullyTested
public final class HashMapBuilderFactory<TKey, TValue>
extends AbstractMapBuilderFactory
                <
                    TKey,
                    TValue,
                    HashMap<TKey, TValue>,
                    HashMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> HashMapBuilderFactory<TKey, TValue> create() {
        HashMapBuilderFactory<TKey, TValue> x = new HashMapBuilderFactory<TKey, TValue>();
        return x;
    }

    private HashMapBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public HashMapBuilder<TKey, TValue> builder() {
        HashMapBuilder<TKey, TValue> x = HashMapBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public HashMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        HashMap<TKey, TValue> x = new HashMap<TKey, TValue>(map);
        return x;
    }

    private static final class _MapBuilderFactoryHelper<TKey, TValue>
    implements MapBuilderFactoryHelper<TKey, TValue, HashMap<TKey, TValue>> {

        private final HashMap<TKey, TValue> map;

        private _MapBuilderFactoryHelper(int initialCapacity) {
            map = new HashMap<TKey, TValue>(initialCapacity);
        }

        @Override
        public void put(TKey key, TValue value) {
            map.put(key, value);
        }

        @Override
        public HashMap<TKey, TValue> getMap() {
            return map;
        }
    }

    @Override
    protected MapBuilderFactoryHelper<TKey, TValue, HashMap<TKey, TValue>>
    createMapBuilderHelper(int initialCapacity) {
        _MapBuilderFactoryHelper<TKey, TValue> x =
            new _MapBuilderFactoryHelper<TKey, TValue>(initialCapacity);
        return x;
    }
}
