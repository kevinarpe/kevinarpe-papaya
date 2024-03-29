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

import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.LinkedHashMap;

/**
 * Builds {@code LinkedHashMap} collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TKey>
 *        type of key for map
 * @param <TValue>
 *        type of value for map
 *
 * @see #create()
 * @see MapBuilder
 * @see LinkedHashMap
 * @see LinkedHashMapBuilderFactory
 * @see HashMapBuilder
 * @see ImmutableMapBuilder
 * @see PropertiesBuilder
 */
@FullyTested
public final class LinkedHashMapBuilder<TKey, TValue>
extends AbstractMapBuilder
            <
                LinkedHashMap<TKey, TValue>,
                TKey,
                TValue
            > {

    /**
     * Constructs a new builder.
     */
    public static <TKey, TValue> LinkedHashMapBuilder<TKey, TValue> create() {
        LinkedHashMapBuilder<TKey, TValue> x = new LinkedHashMapBuilder<TKey, TValue>();
        return x;
    }

    private LinkedHashMapBuilder() {
        // Empty
    }

    /**
     * Builds a new {@code LinkedHashMap} from values stored in the builder.
     */
    @Override
    public LinkedHashMap<TKey, TValue> build() {
        LinkedHashMap<TKey, TValue> x = Maps.newLinkedHashMap(delegate());
        return x;
    }
}
