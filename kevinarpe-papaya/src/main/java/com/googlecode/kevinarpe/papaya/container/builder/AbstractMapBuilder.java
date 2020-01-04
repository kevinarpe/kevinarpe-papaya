package com.googlecode.kevinarpe.papaya.container.builder;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Base class for all implementations of {@code MapBuilder}.  The underlying map is an instance of
 * {@link LinkedHashMap}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TMap>
 *        type of map to build: {@code extends Map<TKey, TValue>}
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
        TMap extends Map<TKey, TValue>,
        TKey,
        TValue
    >
extends ForwardingMap<TKey, TValue>
implements MapBuilder<TMap, TKey, TValue> {

    private final LinkedHashMap<TKey, TValue> _map;

    protected AbstractMapBuilder() {
        _map = Maps.newLinkedHashMap();
    }

    @Override
    protected final LinkedHashMap<TKey, TValue> delegate() {
        return _map;
    }

    @Override
    public abstract TMap build();
}
