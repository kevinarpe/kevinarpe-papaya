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

import com.google.common.collect.ForwardingMap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base class for all implementations of {@code MapBuilder}.  The underlying map is an instance of
 * {@link LinkedHashMap}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TMap>
 *        type of map to build: extends Map&lt;TKey, TValue>
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 *
 * @see ForwardingMap
 * @see MapBuilder
 * @see HashMapBuilder
 * @see LinkedHashMapBuilder
 * @see ImmutableMapBuilder
 * @see PropertiesBuilder
 */
@FullyTested
public abstract class AbstractMapBuilder
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>,
        TSelf extends MapBuilder<TKey, TValue, TMap, TSelf>
    >
extends ForwardingMap<TKey, TValue>
implements MapBuilder<TKey, TValue, TMap, TSelf> {

    private final Map<TKey, TValue> _map;
    private final IMapBuilderUtils<TKey, TValue, TMap> _mapBuilderUtils;
    private final MinimalistMap<TKey, TValue> _minimalistMap;

    protected AbstractMapBuilder(Map<TKey, TValue> map) {
        this(
            map,
            new MapBuilderUtils<TKey, TValue, TMap>(),
            new MinimalistMapImpl<TKey, TValue>(map));
    }

    AbstractMapBuilder(
            Map<TKey, TValue> map,
            IMapBuilderUtils<TKey, TValue, TMap> mapBuilderUtils,
            MinimalistMap<TKey, TValue> minimalistMap) {
        super();
        _map = ObjectArgs.checkNotNull(map, "map");
        _mapBuilderUtils = ObjectArgs.checkNotNull(mapBuilderUtils, "mapBuilderUtils");
        _minimalistMap = ObjectArgs.checkNotNull(minimalistMap, "minimalistMap");
    }

    @Override
    protected final Map<TKey, TValue> delegate() {
        return _map;
    }

    @Override
    public abstract TMap build();

    protected abstract TSelf self();

    @Override
    public final TSelf putOne(TKey key, TValue value) {
        _map.put(key, value);
        TSelf x = self();
        return x;
    }

    @Override
    public TSelf putMany(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            TKey key,
            TValue value,
            Object... moreKeysAndValuesArr) {
        _mapBuilderUtils.putMany(
            _minimalistMap, keyClass, valueClass, key, value, moreKeysAndValuesArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Class<TKey> keyClass, Class<TValue> valueClass, Object[] keysAndValuesArr) {
        _mapBuilderUtils.putMany(_minimalistMap, keyClass, valueClass, keysAndValuesArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Iterable<? extends TKey> keyIterable, Iterable<? extends TValue> valueIterable) {
        _mapBuilderUtils.putMany(_minimalistMap, keyIterable, valueIterable);
        TSelf x = self();
        return x;
    }

    @Override
    public TSelf putMany(Iterator<? extends TKey> keyIter, Iterator<? extends TValue> valueIter) {
        _mapBuilderUtils.putMany(_minimalistMap, keyIter, valueIter);
        TSelf x = self();
        return x;
    }

    @Override
    public TSelf putMany(
            Entry<? extends TKey, ? extends TValue> entry,
            Entry<? extends TKey, ? extends TValue>... moreEntryArr) {
        _mapBuilderUtils.putMany(_minimalistMap, entry, moreEntryArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(Map.Entry<? extends TKey, ? extends TValue>[] entryArr) {
        _mapBuilderUtils.putMany(_minimalistMap, entryArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable) {
        _mapBuilderUtils.putMany(_minimalistMap, entryIterable);
        TSelf x = self();
        return x;
    }

    @Override
    public TSelf putMany(Iterator<? extends Entry<? extends TKey, ? extends TValue>> entryIter) {
        _mapBuilderUtils.putMany(_minimalistMap, entryIter);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        _map.putAll(map);
        TSelf x = self();
        return x;
    }
}
