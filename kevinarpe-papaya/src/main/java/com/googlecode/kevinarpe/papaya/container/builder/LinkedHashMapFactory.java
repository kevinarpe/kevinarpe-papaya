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
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

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
 * @see MapFactory
 * @see LinkedHashMapBuilder
 * @see HashMapFactory
 * @see ImmutableMapFactory
 * @see PropertiesFactory
 */
@FullyTested
public final class LinkedHashMapFactory<TKey, TValue>
extends ForwardingMapFactoryHelper<TKey, TValue, LinkedHashMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    LinkedHashMap<TKey, TValue>,
                    LinkedHashMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey, TValue> LinkedHashMapFactory<TKey, TValue> create() {
        LinkedHashMapFactory<TKey, TValue> x =
            new LinkedHashMapFactory<TKey, TValue>();
        return x;
    }

    private final MapFactoryHelper<TKey, TValue, LinkedHashMap<TKey, TValue>> _mapFactoryHelper;

    private LinkedHashMapFactory() {
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, LinkedHashMap<TKey, TValue>>(
                new _MapFactoryHelperHelperFactory<TKey, TValue>());
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, LinkedHashMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
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

    private static final class _MapFactoryHelperHelperFactory<TKey, TValue>
    extends StatelessObject
    implements MapFactoryHelperHelperFactory
                    <
                        TKey,
                        TValue,
                        LinkedHashMap<TKey, TValue>,
                        _MapFactoryHelperHelper<TKey, TValue>
                    > {

        @Override
        public _MapFactoryHelperHelper<TKey, TValue> newInstance() {
            _MapFactoryHelperHelper<TKey, TValue> x = new _MapFactoryHelperHelper<TKey, TValue>();
            return x;
        }
    }

    private static final class _MapFactoryHelperHelper<TKey, TValue>
    implements MapFactoryHelperHelper<TKey, TValue, LinkedHashMap<TKey, TValue>> {

        private final LinkedHashMap<TKey, TValue> _map;

        private _MapFactoryHelperHelper() {
            _map = new LinkedHashMap<TKey, TValue>();
        }

        @Override
        public void put(TKey key, TValue value) {
            _map.put(key, value);
        }

        @Override
        public LinkedHashMap<TKey, TValue> getMap() {
            return _map;
        }
    }
}
