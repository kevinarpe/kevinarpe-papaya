package com.googlecode.kevinarpe.papaya.properties;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.container.builder.MapBuilder;
import com.googlecode.kevinarpe.papaya.container.builder.MapBuilderFactory;
import com.googlecode.kevinarpe.papaya.input.InputSource2;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Loads groups of Java properties, checks for correctness, and carefully logs any overrides.
 * <p>
 * To construct new instances, see {@link PropertiesLoaderUtils#newInstance()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PropertiesLoaderUtils
 */
public interface PropertiesLoader {

    /**
     * Retrieves the current optional policy to check each group of properties.
     *
     * @return current policy which can be {@code null}
     *
     * @see #withOptionalPolicy(PropertiesLoaderPolicy)
     */
    @Nullable PropertiesLoaderPolicy withOptionalPolicy();

    /**
     * Constructs a new instance with a new optional policy to check each group of properties.
     *
     * @param optionalPolicy
     *        policy for each group of properties.  Can be {@code null}
     *
     * @return new instance
     *
     * @see #withOptionalPolicy()
     */
    PropertiesLoaderImpl withOptionalPolicy(@Nullable PropertiesLoaderPolicy optionalPolicy);

    /**
     * Loads Java properties sequentially from a list of input sources.  Properties from later input
     * sources (higher indices) override earlier input sources (lower indices).
     * <p>
     * About logging:
     * <ul>
     *     <li>Each properties source is logged at level INFO.</li>
     *     <li>The number of properties found is logged at level DEBUG.</li>
     *     <li>Each property (key and value) is logged at level TRACE.</li>
     *     <li>Overriding properties are logged at level TRACE.</li>
     * </ul>
     * <p>
     * To load properties to a different collection, see {@link #load(List, MapBuilderFactory)}.
     *
     * @param inputSourceList
     *        one or more input sources to load properties
     *
     * @return new {@link Properties} instance
     *
     * @throws NullPointerException
     *         if {@code inputSourceList} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code inputSourceList} is zero
     * @throws PropertiesLoaderException
     * <ul>
     *     <li>if input source cannot be read</li>
     *     <li>if properties from an input source fail policy (duplicates, etc.)</li>
     * </ul>
     *
     * @see #load(List, MapBuilderFactory)
     */
    Properties load(List<? extends InputSource2> inputSourceList)
    throws PropertiesLoaderException;

    /**
     * Identical to {@link #load(List)}, except resulting collection can be any type of {@code Map}.
     *
     * @param inputSourceList
     *        one or more input sources to load properties
     * @param mapBuilderFactory
     *        factory to build a {@code Map}
     * @param <TMap>
     *        {@code extends Map<String, String>}
     * @param <TMapBuilder>
     *        {@code extends MapBuilder<TMap, String, String>}
     *
     * @return new {@code TMap} instance
     *
     * @throws NullPointerException
     *         if {@code inputSourceList} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code inputSourceList} is zero
     * @throws PropertiesLoaderException
     * <ul>
     *     <li>if input source cannot be read</li>
     *     <li>if properties from an input source fail policy (duplicates, etc.)</li>
     * </ul>
     *
     * @see #load(List)
     * @see MapBuilderFactory
     */
    <
        TMap extends Map<String, String>,
        TMapBuilder extends MapBuilder<TMap, String, String>
    >
    TMap load(
            List<? extends InputSource2> inputSourceList,
            MapBuilderFactory<TMapBuilder> mapBuilderFactory)
    throws PropertiesLoaderException;
}
