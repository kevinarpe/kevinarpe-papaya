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
import com.google.common.collect.HashBiMap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.HashMap;

/**
 * Builds {@code HashMap} collections.
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
 * @see HashMap
 * @see HashMapFactory
 * @see LinkedHashMapBuilder
 * @see ImmutableMapBuilder
 * @see PropertiesBuilder
 */
@FullyTested
public final class HashBiMapBuilder<TKey, TValue>
extends AbstractBiMapBuilder
            <
                TKey,
                TValue,
                HashBiMap<TKey, TValue>,
                HashBiMapBuilder<TKey, TValue>
            > {

    /**
     * Constructs a new builder.
     */
    public static <TKey, TValue> HashBiMapBuilder<TKey, TValue> create() {
        HashBiMapBuilder<TKey, TValue> x = new HashBiMapBuilder<TKey, TValue>();
        return x;
    }

    private HashBiMapBuilder() {
        this(HashBiMap.<TKey, TValue>create());
    }

    HashBiMapBuilder(BiMap<TKey, TValue> map) {
        super(map);
    }

    /**
     * Builds a new {@code HashMap} from values stored in the builder.
     */
    @Override
    public HashBiMap<TKey, TValue> build() {
        HashBiMap<TKey, TValue> x = HashBiMap.create(delegate());
        return x;
    }

    @Override
    protected HashBiMapBuilder<TKey, TValue> self() {
        return this;
    }
}
