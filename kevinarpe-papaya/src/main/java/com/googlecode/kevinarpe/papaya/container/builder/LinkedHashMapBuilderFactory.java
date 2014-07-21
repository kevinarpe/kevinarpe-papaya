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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creates {@code LinkedHashMapBuilder}s.
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
 * @see LinkedHashMapBuilder
 * @see HashMapBuilderFactory
 * @see ImmutableMapBuilderFactory
 * @see PropertiesBuilderFactory
 */
@FullyTested
public final class LinkedHashMapBuilderFactory<TKey, TValue>
extends AbstractMapBuilderFactory
                <
                    TKey,
                    TValue,
                    LinkedHashMap<TKey, TValue>,
                    LinkedHashMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> LinkedHashMapBuilderFactory<TKey, TValue> create() {
        LinkedHashMapBuilderFactory<TKey, TValue> x =
            new LinkedHashMapBuilderFactory<TKey, TValue>();
        return x;
    }

    private LinkedHashMapBuilderFactory() {
        // Empty
    }

    /** {@inheritDoc} */
    @Override
    public LinkedHashMapBuilder<TKey, TValue> builder() {
        LinkedHashMapBuilder<TKey, TValue> x = LinkedHashMapBuilder.create();
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public LinkedHashMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        LinkedHashMap<TKey, TValue> x = new LinkedHashMap<TKey, TValue>(map);
        return x;
    }

    private static final class _MapBuilderFactoryHelper<TKey, TValue>
    implements MapBuilderFactoryHelper<TKey, TValue, LinkedHashMap<TKey, TValue>> {

        private final LinkedHashMap<TKey, TValue> map;

        private _MapBuilderFactoryHelper(int initialCapacity) {
            map = new LinkedHashMap<TKey, TValue>(initialCapacity);
        }

        @Override
        public void put(TKey key, TValue value) {
            map.put(key, value);
        }

        @Override
        public LinkedHashMap<TKey, TValue> getMap() {
            return map;
        }
    }

    @Override
    protected MapBuilderFactoryHelper<TKey, TValue, LinkedHashMap<TKey, TValue>>
    createMapBuilderHelper(int initialCapacity) {
        _MapBuilderFactoryHelper<TKey, TValue> x =
            new _MapBuilderFactoryHelper<TKey, TValue>(initialCapacity);
        return x;
    }
}
