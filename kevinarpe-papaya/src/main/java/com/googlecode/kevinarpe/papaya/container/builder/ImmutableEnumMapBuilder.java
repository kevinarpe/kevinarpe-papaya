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
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.EnumMap;

/**
 * Builds {@code ImmutableMap} collections.
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
 * @see ImmutableMap
 * @see ImmutableMapFactory
 * @see HashMapBuilder
 * @see LinkedHashMapBuilder
 * @see PropertiesBuilder
 */
@FullyTested
public final class ImmutableEnumMapBuilder<TKey extends Enum<TKey>, TValue>
extends AbstractMapBuilder
            <
                TKey,
                TValue,
                ImmutableMap<TKey, TValue>,
                ImmutableEnumMapBuilder<TKey, TValue>
            > {

    /**
     * Constructs a new builder.
     */
    public static <TKey extends Enum<TKey>, TValue>
    ImmutableEnumMapBuilder<TKey, TValue> create(Class<TKey> keyClass) {
        ImmutableEnumMapBuilder<TKey, TValue> x =
            new ImmutableEnumMapBuilder<TKey, TValue>(keyClass);
        return x;
    }

    private ImmutableEnumMapBuilder(Class<TKey> keyClass) {
        super(new EnumMap<TKey, TValue>(keyClass));

    }

    /**
     * Builds a new {@code ImmutableMap} from values stored in the builder.
     */
    @Override
    public ImmutableMap<TKey, TValue> build() {
        ImmutableMap<TKey, TValue> x = ImmutableMap.copyOf(delegate());
        return x;
    }

    @Override
    protected ImmutableEnumMapBuilder<TKey, TValue> self() {
        return this;
    }
}
