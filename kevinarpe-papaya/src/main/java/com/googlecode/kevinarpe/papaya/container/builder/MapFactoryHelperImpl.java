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

import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class MapFactoryHelperImpl
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>
    >
implements MapFactoryHelper<TKey, TValue, TMap> {

    private final IMapBuilderUtils<TKey, TValue, TMap> _mapBuilderUtils;

    private final MinimalistMapBuilderFactory
                    <
                        TKey,
                        TValue,
                        TMap,
                        ? extends MinimalistMapBuilder<TKey, TValue, TMap>
                    > _minimalistmapBuilderFactory;

    public MapFactoryHelperImpl(
            MinimalistMapBuilderFactory
                <
                    TKey,
                    TValue,
                    TMap, ? extends MinimalistMapBuilder<TKey, TValue, TMap>
                > minimalistMapBuilderFactory,
            IMapBuilderUtils<TKey, TValue, TMap> mapBuilderUtils) {
        _mapBuilderUtils = ObjectArgs.checkNotNull(mapBuilderUtils, "mapBuilderUtils");
        _minimalistmapBuilderFactory =
            ObjectArgs.checkNotNull(minimalistMapBuilderFactory, "mapFactoryHelperFactory");
    }

    @Override
    public TMap copyOf(Class<TKey> keyClass, Class<TValue> valueClass, Object... keysAndValuesArr) {
        MinimalistMapBuilder<TKey, TValue, TMap> mapBuilderHelper =
            _minimalistmapBuilderFactory.newInstance();
        _mapBuilderUtils.putMany(mapBuilderHelper, keyClass, valueClass, keysAndValuesArr);
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(
            Iterable<? extends TKey> keyIterable,
            Iterable<? extends TValue> valueIterable) {
        MinimalistMapBuilder<TKey, TValue, TMap> mapBuilderHelper =
            _minimalistmapBuilderFactory.newInstance();
        _mapBuilderUtils.putMany(mapBuilderHelper, keyIterable, valueIterable);
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(Map.Entry<? extends TKey, ? extends TValue>... entryArr) {
        MinimalistMapBuilder<TKey, TValue, TMap> mapBuilderHelper =
            _minimalistmapBuilderFactory.newInstance();
        _mapBuilderUtils.putMany(mapBuilderHelper, entryArr);
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(
            Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable) {
        MinimalistMapBuilder<TKey, TValue, TMap> mapBuilderHelper =
            _minimalistmapBuilderFactory.newInstance();
        _mapBuilderUtils.putMany(mapBuilderHelper, entryIterable);
        TMap map = mapBuilderHelper.getMap();
        return map;
    }
}
