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

import com.google.common.collect.EnumHashBiMap;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Map;
import java.util.Set;

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
public final class EnumHashBiMapFactory<TKey extends Enum<TKey>, TValue>
extends ForwardingMapFactoryHelper<TKey, TValue, EnumHashBiMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    EnumHashBiMap<TKey, TValue>,
                    EnumHashBiMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey extends Enum<TKey>, TValue>
    EnumHashBiMapFactory<TKey, TValue> create(Class<TKey> keyClass) {
        EnumHashBiMapFactory<TKey, TValue> x = new EnumHashBiMapFactory<TKey, TValue>(keyClass);
        return x;
    }

    private final Class<TKey> keyClass;
    private final MapFactoryHelper<TKey, TValue, EnumHashBiMap<TKey, TValue>> _mapFactoryHelper;

    private EnumHashBiMapFactory(Class<TKey> keyClass) {
        this(keyClass, new MapBuilderUtils<TKey, TValue, EnumHashBiMap<TKey, TValue>>());
    }

    EnumHashBiMapFactory(
            Class<TKey> keyClass,
            IMapBuilderUtils<TKey, TValue, EnumHashBiMap<TKey, TValue>> mapBuilderUtils) {
        this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");

        _MinimalistMapBuilderFactory<TKey, TValue> minimalistMapBuilderFactory =
            new _MinimalistMapBuilderFactory<TKey, TValue>(keyClass);
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, EnumHashBiMap<TKey, TValue>>(
                minimalistMapBuilderFactory, mapBuilderUtils);
    }

    /** {@inheritDoc} */
    @Override
    public EnumHashBiMapBuilder<TKey, TValue> builder() {
        EnumHashBiMapBuilder<TKey, TValue> x = EnumHashBiMapBuilder.create(keyClass);
        return x;
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, EnumHashBiMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
    }

    /** {@inheritDoc} */
    @Override
    public EnumHashBiMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        MapArgs.checkNotEmpty(map, "map");

        Set<? extends TKey> keySet = map.keySet();
        @SuppressWarnings("unchecked")
        Class<TKey> keyClass = (Class<TKey>) _getDeclaringClass(keySet, "map.keySet()");
        EnumHashBiMap<TKey, TValue> x = EnumHashBiMap.create(keyClass);
        x.putAll(map);
        return x;
    }

    // TODO: Move to helper?  See also: EnumBiMapFactory
    private static <T extends Enum<T>>
    Class<T> _getDeclaringClass(Iterable<? extends T> iterable, String argName) {
        for (T value : iterable) {
            if (null != value) {
                Class<T> clazz = value.getDeclaringClass();
                return clazz;
            }
        }
        throw new NullPointerException(String.format(
            "Argument '%s' has only null values: Unable to determine declaring class", argName));
    }

    private static final class _MinimalistMapBuilderFactory
        <
            TKey extends Enum<TKey>,
            TValue
        >
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        EnumHashBiMap<TKey, TValue>,
                        _MinimalistMapBuilder<TKey, TValue>
                    > {

        private final Class<TKey> keyClass;

        private _MinimalistMapBuilderFactory(Class<TKey> keyClass) {
            this.keyClass = keyClass;
        }

        @Override
        public _MinimalistMapBuilder<TKey, TValue> newInstance() {
            _MinimalistMapBuilder<TKey, TValue> x =
                new _MinimalistMapBuilder<TKey, TValue>(keyClass);
            return x;
        }
    }

    private static final class _MinimalistMapBuilder
        <
            TKey extends Enum<TKey>,
            TValue
        >
    implements MinimalistMapBuilder<TKey, TValue, EnumHashBiMap<TKey, TValue>> {

        private final EnumHashBiMap<TKey, TValue> _map;

        private _MinimalistMapBuilder(Class<TKey> keyClass) {
            _map = EnumHashBiMap.create(keyClass);
        }

        @Override
        public void put(TKey key, TValue value) {
            _map.put(key, value);
        }

        @Override
        public EnumHashBiMap<TKey, TValue> getMap() {
            return _map;
        }
    }
}
