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

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface EnumMap2<TEnumKey extends Enum<TEnumKey>, TValue>
extends Map<TEnumKey, TValue> {

    /**
     * Oddly, this is missing from {@link EnumMap}.
     *
     * @return enum class associated with this map
     */
    Class<TEnumKey>
    getEnumClass();

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: If possible, prefer {@link #getByEnum(Enum)} or {@link #getOrThrow(Object)}.
     */
    @Override
    TValue get(Object key);

    /**
     * If possible, please use {@link #getByEnumOrThrow(Enum)}.
     * <p>
     * This is a safer version of {@link #get(Object)} that throws when {@code key} is {@code null} or missing (unmapped).
     *
     * @param key
     *        must be an instance of {@code TEnumKey}
     *
     * @throws NullPointerException
     *         if {@code key} is {@code null}
     * @throws IllegalArgumentException
     *         if no mapping exists for {@code key}
     *
     * @see #getByEnum(Enum)
     */
    @Nullable
    TValue
    getOrThrow(Object key);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: If possible, prefer {@link #getByEnumOrDefault(Enum, Object)}.
     */
    @Nullable
    @Override
    default TValue
    getOrDefault(@Nullable Object key,
                 @Nullable TValue defaultValue) {

        return Map.super.getOrDefault(key, defaultValue);
    }

    /**
     * If possible, please use {@link #getByEnumOrThrow(Enum)}.
     * <p>
     * This is a safer version of {@link #get(Object)}.
     *
     * @return value mapped to {@code key}
     *         <br><b>Important:</b> Return value of {@code null} is ambiguous.  This may be the value mapped to
     *         {@code key}, or an indicator that {@code key} does not exist.
     *
     * @throws NullPointerException
     *         if {@code key} is {@code null}
     *
     * @see #getByEnumOrThrow(Enum)
     * @see #getOrThrow(Object)
     * @see #getByEnumOrDefault(Enum, Object)
     */
    @Nullable
    TValue
    getByEnum(TEnumKey key);

    /**
     * This is a safer version of {@link #get(Object)} that throws when {@code key} is {@code null} or missing (unmapped).
     *
     * @return value mapped to {@code key}, which may be {@code null}
     *
     * @throws NullPointerException
     *         if {@code key} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code key} is missing (unmapped)
     *
     * @see #getByEnum(Enum)
     * @see #getOrThrow(Object)
     * @see #getByEnumOrDefault(Enum, Object)
     */
    @Nullable
    default TValue
    getByEnumOrThrow(TEnumKey key) {

        @Nullable
        final TValue value = getByEnum(key);
        // Be careful: If value is null, we cannot be sure if key is missing or key is mapped to null.
        if (null != value || containsKey(key)) {
            return value;
        }
        else {
            throw new IllegalArgumentException("Key [" + key + "] does not exist");
        }
    }

    /**
     * This is a safer version of {@link #getOrDefault(Object, Object)}.
     *
     * @param defaultValue
     *        value can be {@code null}
     *
     * @throws NullPointerException
     *         if {@code key} is {@code null}
     *
     * @see #getByEnum(Enum)
     * @see #getByEnumOrThrow(Enum)
     * @see #getOrThrow(Object)
     * @see #getOrDefault(Object, Object)
     */
    @Nullable
    default TValue
    getByEnumOrDefault(TEnumKey key,
                       @Nullable TValue defaultValue) {
        @Nullable
        final TValue value = getByEnum(key);
        // Be careful: If value is null, we cannot be sure if key is missing or key is mapped to null.
        if (null != value || containsKey(key)) {
            return value;
        }
        else {
            return defaultValue;
        }
    }
}
