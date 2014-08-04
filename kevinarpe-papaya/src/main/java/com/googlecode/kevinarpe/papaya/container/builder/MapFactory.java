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

import java.util.Map;

/**
 * Base interface for all map builder factories.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 * @param <TMap>
 *        type of map to build: extends Map&lt;TKey, TValue>
 * @param <TMapBuilder>
 *        type of map builder to create: extends MapBuilder
 *
 * @see HashMapFactory
 * @see LinkedHashMapFactory
 * @see ImmutableMapFactory
 * @see PropertiesFactory
 */
public interface MapFactory
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>,
        TMapBuilder extends MapBuilder<TKey, TValue, TMap, TMapBuilder>
    >
extends BuilderFactory<TMapBuilder>, MapFactoryHelper<TKey, TValue, TMap> {

    /**
     * Copies a map.
     *
     * @param map
     *        must not be {@code null}
     *
     * @return copy of {@code map}
     *
     * @throws NullPointerException
     *         if {@code map} is {@code null}
     */
    TMap copyOf(Map<? extends TKey, ? extends TValue> map);
}