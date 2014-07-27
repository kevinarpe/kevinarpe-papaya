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

import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Helper to define all map copyOf operations except map-to-map.
 * <p>
 * This interface was somewhat artificially carved out of {@link MapFactory} as the algorithms for
 * the methods below were nearly identical across all {@code MapFactory} implementations, e.g.,
 * {@link HashMapFactory}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type for map keys
 * @param <TValue>
 *        type for map values
 * @param <TMap>
 *        type for maps: extends Map&lt;TKey, TValue>
 *
 * @see ForwardingMapFactoryHelper
 */
// TODO: Make this package-private and move docs up to MapFactory with explicit copy-with-@Override?
public interface MapFactoryHelper
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>
    > {

    /**
     * Casts keys and values from a variadic array and inserts into a new map.
     * <p>
     * This method cannot be used if {@code TKey} or {@code TValue} is a generic type, e.g.,
     * {@link List}, because generic keys and values cannot be safely cast.
     *
     * @param keyClass
     *        class for key type
     * @param valueClass
     *        class for value type
     * @param keysAndValuesArr
     * <ul>
     *     <li>variadic array of keys and values (can be empty)</li>
     *     <li>each <i>even</i> index has a key, e.g., [0], [2], [4]...</li>
     *     <li>each <i>odd</i> index has a value, e.g., [1], [3], [5]...</li>
     *     <li>{@code null}s may be allowed, depending on the map type,
     *     e.g., {@link HashMap} is OK, but {@link ImmutableMap} is not</li>
     * </ul>
     *
     * @return new map
     *
     * @throws NullPointerException
     *         if {@code keyClass}, {@code valueClass}, or {@code keysAndValuesArr} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code keyClass} or {@code valueClass} has generic type parameters</li>
     *     <li>if length of {@code keysAndValuesArr} is not even</li>
     * </ul>
     * @throws ClassCastException
     * <ul>
     *     <li>if a key cannot be safely cast to type {@code keyClass}</li>
     *     <li>if a value cannot be safely cast to type {@code valueClass}</li>
     * </ul>
     */
    TMap copyOf(Class<TKey> keyClass, Class<TValue> valueClass, Object... keysAndValuesArr);

    /**
     * Iterates keys and values and inserts into a new map.
     * <p>
     * Why are the inputs {@link Iterable} instead of {@link Collection}?
     * <br/>Ref: <a href="http://stackoverflow.com/a/1159871/257299"
     * >http://stackoverflow.com/a/1159871/257299</a>
     *
     * @param keyIterable
     * <ul>
     *     <li>collection of keys (can be empty)</li>
     *     <li>must have same size as {@code valueIterable}</li>
     *     <li>be careful not to use a collection where iteration order is not defined,
     *     e.g., {@link HashSet}</li>
     * </ul>
     * @param valueIterable
     * <ul>
     *     <li>collection of values (can be empty)</li>
     *     <li>must have same size as {@code keyIterable}</li>
     *     <li>be careful not to use a collection where iteration order is not defined,
     *     e.g., {@link HashSet}</li>
     * </ul>
     *
     * @return new map
     *
     * @throws NullPointerException
     *         if {@code keyIterable} or {@code valueIterable} is {@code null}
     * @throws IllegalArgumentException
     *         if length of {@code keyIterable} or {@code valueIterable} is not equal
     */
    TMap copyOf(Iterable<? extends TKey> keyIterable, Iterable<? extends TValue> valueIterable);

    /**
     * Inserts entries into a new map.
     *
     * @param entryArr
     * <ul>
     *     <li>variadic array of map entries (can be empty)</li>
     *     <li>entries may not be {@code null}, but {@code null} keys or values may be allowed,
     *     depending on the map type, e.g., {@link HashMap} is OK, but {@link ImmutableMap}
     *     is not</li>
     * </ul>
     *
     * @return new map
     *
     * @throws NullPointerException
     *         if {@code entryArr} (or any element) is {@code null}
     */
    TMap copyOf(Map.Entry<? extends TKey, ? extends TValue>... entryArr);

    /**
     * Iterates entries and inserts into a new map.
     * <p>
     * Why are the inputs {@link Iterable} instead of {@link Collection}?
     * <br/>Ref: <a href="http://stackoverflow.com/a/1159871/257299"
     * >http://stackoverflow.com/a/1159871/257299</a>
     *
     * @param entryIterable
     * <ul>
     *     <li>collection of map entries (can be empty)</li>
     *     <li>entries may not be {@code null}, but {@code null} keys or values may be allowed,
     *     depending on the map type, e.g., {@link HashMap} is OK, but {@link ImmutableMap}
     *     is not</li>
     * </ul>
     *
     * @return new map
     *
     * @throws NullPointerException
     *         if {@code entryIterable} (or any element) is {@code null}
     */
    TMap copyOf(Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable);
}
