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

import com.google.common.collect.BiMap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.ForwardingBiMap;

import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public abstract class AbstractBiMapBuilder
    <
        TKey,
        TValue,
        TMap extends BiMap<TKey, TValue>,
        TSelf extends BiMapBuilder<TKey, TValue, TMap, TSelf>
    >
extends ForwardingBiMap<TKey, TValue>
implements BiMapBuilder<TKey, TValue, TMap, TSelf> {

    private final BiMap<TKey, TValue> _biMap;
    private final IMapBuilderUtils<TKey, TValue, TMap> _biMapBuilderUtils;
    private final MinimalistMap<TKey, TValue> _minimalistMap;

    protected AbstractBiMapBuilder(BiMap<TKey, TValue> biMap) {
        this(
            biMap,
            new MapBuilderUtils<TKey, TValue, TMap>(),
            new MinimalistMapImpl<TKey, TValue>(biMap));
    }

    AbstractBiMapBuilder(
            BiMap<TKey, TValue> biMap,
            IMapBuilderUtils<TKey, TValue, TMap> mapBuilderUtils,
            MinimalistMap<TKey, TValue> minimalistMap) {
        super();
        _biMap = ObjectArgs.checkNotNull(biMap, "biMap");
        _biMapBuilderUtils = ObjectArgs.checkNotNull(mapBuilderUtils, "mapBuilderUtils");
        _minimalistMap = ObjectArgs.checkNotNull(minimalistMap, "minimalistMap");
    }

    @Override
    protected final BiMap<TKey, TValue> delegate() {
        return _biMap;
    }

    @Override
    public abstract TMap build();

    protected abstract TSelf self();

    @Override
    public final TSelf putOne(TKey key, TValue value) {
        _biMap.put(key, value);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Class<TKey> keyClass, Class<TValue> valueClass, Object... keysAndValuesArr) {
        _biMapBuilderUtils.putMany(_minimalistMap, keyClass, valueClass, keysAndValuesArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Iterable<? extends TKey> keyIterable, Iterable<? extends TValue> valueIterable) {
        _biMapBuilderUtils.putMany(_minimalistMap, keyIterable, valueIterable);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(Map.Entry<? extends TKey, ? extends TValue>... entryArr) {
        _biMapBuilderUtils.putMany(_minimalistMap, entryArr);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(
            Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable) {
        _biMapBuilderUtils.putMany(_minimalistMap, entryIterable);
        TSelf x = self();
        return x;
    }

    @Override
    public final TSelf putMany(Map<? extends TKey, ? extends TValue> map) {
        ObjectArgs.checkNotNull(map, "map");

        _biMap.putAll(map);
        TSelf x = self();
        return x;
    }
}
