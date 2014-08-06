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

import com.google.common.collect.EnumBiMap;
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
public final class EnumBiMapFactory<TKey extends Enum<TKey>, TValue extends Enum<TValue>>
extends ForwardingMapFactoryHelper<TKey, TValue, EnumBiMap<TKey, TValue>>
implements MapFactory
                <
                    TKey,
                    TValue,
                    EnumBiMap<TKey, TValue>,
                    EnumBiMapBuilder<TKey, TValue>
                > {

    /**
     * Constructs a new builder factory.
     */
    public static <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    EnumBiMapFactory<TKey, TValue> create(Class<TKey> keyClass, Class<TValue> valueClass) {
        EnumBiMapFactory<TKey, TValue> x = new EnumBiMapFactory<TKey, TValue>(keyClass, valueClass);
        return x;
    }

    private final Class<TKey> keyClass;
    private final Class<TValue> valueClass;
    private final MapFactoryHelper<TKey, TValue, EnumBiMap<TKey, TValue>> _mapFactoryHelper;

    private EnumBiMapFactory(Class<TKey> keyClass, Class<TValue> valueClass) {
        this(keyClass, valueClass, new MapBuilderUtils<TKey, TValue, EnumBiMap<TKey, TValue>>());
    }

    EnumBiMapFactory(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            IMapBuilderUtils<TKey, TValue, EnumBiMap<TKey, TValue>> mapBuilderUtils) {
        this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");

        _MinimalistMapBuilderFactory<TKey, TValue> minimalistMapBuilderFactory =
            new _MinimalistMapBuilderFactory<TKey, TValue>(keyClass, valueClass);
        _mapFactoryHelper =
            new MapFactoryHelperImpl<TKey, TValue, EnumBiMap<TKey, TValue>>(
                minimalistMapBuilderFactory, mapBuilderUtils);
    }

    /** {@inheritDoc} */
    @Override
    public EnumBiMapBuilder<TKey, TValue> builder() {
        EnumBiMapBuilder<TKey, TValue> x = EnumBiMapBuilder.create(keyClass, valueClass);
        return x;
    }

    @Override
    protected MapFactoryHelper<TKey, TValue, EnumBiMap<TKey, TValue>> delegate() {
        return _mapFactoryHelper;
    }

    /** {@inheritDoc} */
    @Override
    public EnumBiMap<TKey, TValue> copyOf(Map<? extends TKey, ? extends TValue> map) {
        MapArgs.checkNotEmpty(map, "map");

        Set<? extends TKey> keySet = map.keySet();
        @SuppressWarnings("unchecked")
        Class<TKey> keyClass = (Class<TKey>) _getDeclaringClass(keySet, "map.keySet()");
        @SuppressWarnings("unchecked")
        Class<TValue> valueClass = (Class<TValue>) _getDeclaringClass(map.values(), "map.values()");
        EnumBiMap<TKey, TValue> x = EnumBiMap.create(keyClass, valueClass);
        x.putAll(map);
        return x;
    }

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
            TValue extends Enum<TValue>
        >
    extends StatelessObject
    implements MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        EnumBiMap<TKey, TValue>,
                        _MinimalistMapBuilder<TKey, TValue>
                    > {

        private final Class<TKey> keyClass;
        private final Class<TValue> valueClass;

        private _MinimalistMapBuilderFactory(Class<TKey> keyClass, Class<TValue> valueClass) {
            this.keyClass = keyClass;
            this.valueClass = valueClass;
        }

        @Override
        public _MinimalistMapBuilder<TKey, TValue> newInstance() {
            _MinimalistMapBuilder<TKey, TValue> x =
                new _MinimalistMapBuilder<TKey, TValue>(keyClass, valueClass);
            return x;
        }
    }

    private static final class _MinimalistMapBuilder
        <
            TKey extends Enum<TKey>,
            TValue extends Enum<TValue>
        >
    implements MinimalistMapBuilder<TKey, TValue, EnumBiMap<TKey, TValue>> {

        private final EnumBiMap<TKey, TValue> _map;

        private _MinimalistMapBuilder(Class<TKey> keyClass, Class<TValue> valueClass) {
            _map = EnumBiMap.create(keyClass, valueClass);
        }

        @Override
        public void put(TKey key, TValue value) {
            _map.put(key, value);
        }

        @Override
        public EnumBiMap<TKey, TValue> getMap() {
            return _map;
        }
    }
}
