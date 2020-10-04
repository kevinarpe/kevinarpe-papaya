package com.googlecode.kevinarpe.papaya.container;

/*-
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// package-private
@FullyTested
final class FullEnumMapBuilderImpl<TEnumKey extends Enum<TEnumKey>, TValue>
implements FullEnumMap.Builder<TEnumKey, TValue> {

    private final Class<TEnumKey> enumClass;
    private final AreNullValuesAllowed areNullValuesAllowed;
    // EnumMap insertion order is guaranteed to be sorted by Enum.ordinal().
    private final EnumMap<TEnumKey, TValue> map;

    @Nullable
    private Map<TEnumKey, TValue> nullableReadOnlyMap;

    // package-private
    FullEnumMapBuilderImpl(Class<TEnumKey> enumClass,
                           AreNullValuesAllowed areNullValuesAllowed) {

        this.enumClass = ObjectArgs.checkNotNull(enumClass, "enumClass");
        this.areNullValuesAllowed = ObjectArgs.checkNotNull(areNullValuesAllowed, "areNullValuesAllowed");
        this.map = new EnumMap<>(enumClass);
    }

    @Override
    public Class<TEnumKey>
    getEnumClass() {
        return enumClass;
    }

    @Override
    public AreNullValuesAllowed
    areNullValuesAllowed() {
        return areNullValuesAllowed;
    }

    /** {@inheritDoc} */
    @Override
    public final Map<TEnumKey, TValue>
    getReadOnlyMap() {

        if (null == nullableReadOnlyMap) {
            nullableReadOnlyMap = Collections.unmodifiableMap(map);
        }
        return nullableReadOnlyMap;
    }

    /** {@inheritDoc} */
    @Override
    public final
    FullEnumMap.Builder<TEnumKey, TValue>
    put(TEnumKey key, @Nullable TValue value) {

        ObjectArgs.checkNotNull(key, "key");

        if (AreNullValuesAllowed.NO.equals(areNullValuesAllowed)) {
            ObjectArgs.checkNotNull(value, "value");
        }
        @Nullable
        final TValue oldValue = map.get(key);

        // Be careful: If oldValue is null, we cannot be sure if key is missing or key is mapped to null.
        if ((null != oldValue || map.containsKey(key))
            &&
            false == Objects.equals(oldValue, value)) {

            final String msg =
                "Key '" + key.name() + "' cannot be mapped to multiple values: [" + oldValue + "] and [" + value + "]";
            throw new IllegalArgumentException(msg);
        }
        map.put(key, value);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public final
    FullEnumMap.Builder<TEnumKey, TValue>
    putAll(Map<TEnumKey, ? extends TValue> map) {

        ObjectArgs.checkNotNull(map, "map");

        for (final Map.Entry<TEnumKey, ? extends TValue> entry : map.entrySet()) {

            final TEnumKey key = entry.getKey();
            @Nullable
            final TValue value = entry.getValue();

            if (AreNullValuesAllowed.NO.equals(areNullValuesAllowed) && null == value) {

                throw new NullPointerException("Key [" + key + "] is mapped to a null value");
            }
            put(key, value);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public final FullEnumMap<TEnumKey, TValue>
    build() {

        final FullEnumMap<TEnumKey, TValue> x = buildWhere(IsEmptyEnumAllowed.NO);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public final FullEnumMap<TEnumKey, TValue>
    buildWhere(IsEmptyEnumAllowed isEmptyEnumAllowed) {

        final FullEnumMap<TEnumKey, TValue> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, isEmptyEnumAllowed);
        return x;
    }
}
