package com.googlecode.kevinarpe.papaya.container;

/*-
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// Intentional: Package-private
@FullyTested
final class ImmutableFullEnumMapBuilderImpl<TEnumKey extends Enum<TEnumKey>, TValue>
implements ImmutableFullEnumMap.Builder<TEnumKey, TValue> {

    private final Class<TEnumKey> enumClass;
    // ImmutableMap has guaranteed insertion order.  However, EnumMap is guaranteed to be sorted by Enum.ordinal().
    private final EnumMap<TEnumKey, TValue> map;

    @FullyTested
    public ImmutableFullEnumMapBuilderImpl(Class<TEnumKey> enumClass) {

        this.enumClass = ObjectArgs.checkNotNull(enumClass, "enumClass");
        this.map = new EnumMap<>(enumClass);
    }

    @Override
    public ImmutableFullEnumMap.Builder<TEnumKey, TValue>
    put(TEnumKey key, TValue value) {

        ObjectArgs.checkNotNull(key, "key");
        ObjectArgs.checkNotNull(value, "value");
        @Nullable
        final TValue prevValue = map.get(key);
        if (null != prevValue && false == prevValue.equals(value)) {
            final String msg =
                "Key '" + key.name() + "' cannot be mapped to multiple values: [" + prevValue + "] and [" + value + "]";
            throw new IllegalArgumentException(msg);
        }
        map.put(key, value);
        return this;
    }

    @Override
    public ImmutableFullEnumMap.Builder<TEnumKey, TValue>
    putAll(Map<TEnumKey, ? extends TValue> map) {

        ObjectArgs.checkNotNull(map, "map");
        for (final Map.Entry<TEnumKey, ? extends TValue> entry : map.entrySet()) {

            final TEnumKey key = entry.getKey();
            final TValue value = entry.getValue();
            put(key, value);
        }
        return this;
    }

    @Override
    public ImmutableFullEnumMap<TEnumKey, TValue>
    build() {
        final ImmutableFullEnumMap<TEnumKey, TValue> x = buildWhere(ImmutableFullEnumMap.IsEmptyEnumAllowed.NO);
        return x;
    }

    @Override
    public ImmutableFullEnumMap<TEnumKey, TValue>
    buildWhere(ImmutableFullEnumMap.IsEmptyEnumAllowed isEmptyEnumAllowed) {

        final ImmutableFullEnumMap<TEnumKey, TValue> x = new ImmutableFullEnumMap<>(enumClass, map, isEmptyEnumAllowed);
        return x;
    }
}
