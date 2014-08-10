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

import java.util.Iterator;
import java.util.Map;

/**
 * Builds maps.  Implementations are always required implement the {@link Map} interface.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 * @param <TMap>
 *        type of map to build: extends Map&lt;TKey, TValue>
 *
 * @see Map
 * @see Builder
 * @see HashMapBuilder
 * @see LinkedHashMapBuilder
 * @see ImmutableMapBuilder
 * @see PropertiesBuilder
 */
public interface MapBuilder
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>,
        TSelf extends MapBuilder<TKey, TValue, TMap, TSelf>
    >
extends Map<TKey, TValue>, Builder<TMap> {

    /**
     * Puts one entry into the builder.
     *
     * @param key
     *        may be {@code null} depending on underlying map implementation
     * @param value
     *        may be {@code null} depending on underlying map implementation
     *
     * @return reference to self
     */
    TSelf putOne(TKey key, TValue value);

    /**
     *
     * @param keyClass
     *        type of keys
     * @param valueClass
     * @param key
     * @param value
     * @param moreKeysAndValuesArr
     * @return
     */
    TSelf putMany(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            TKey key,
            TValue value,
            Object... moreKeysAndValuesArr);

    TSelf putMany(Class<TKey> keyClass, Class<TValue> valueClass, Object[] keysAndValuesArr);

    TSelf putMany(Iterable<? extends TKey> keyIterable, Iterable<? extends TValue> valueIterable);

    TSelf putMany(Iterator<? extends TKey> keyIter, Iterator<? extends TValue> valueIter);

    TSelf putMany(
            Map.Entry<? extends TKey, ? extends TValue> entry,
            Map.Entry<? extends TKey, ? extends TValue>... moreEntryArr);

    TSelf putMany(Map.Entry<? extends TKey, ? extends TValue>[] entryArr);

    TSelf putMany(Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable);

    TSelf putMany(Iterator<? extends Entry<? extends TKey, ? extends TValue>> entryIterator);

    TSelf putMany(Map<? extends TKey, ? extends TValue> map);
}
