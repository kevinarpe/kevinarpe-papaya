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

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * This class a blend of {@link ImmutableMap} and {@link java.util.EnumMap}, but requires all enum keys to be present.
 * <p>
 * These maps are CPU and memory intensive to construct, but very fast to access data after construction.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ImmutableFullEnumMap<TEnumKey extends Enum<TEnumKey>, TValue>
extends ForwardingMap<TEnumKey, TValue>
implements EnumMap2<TEnumKey, TValue> {

    // Intentional: This Builder not strictly need to be an iface/impl,
    // but it reduces clutter in this class, and improved readability.
    public interface Builder<TEnumKey2 extends Enum<TEnumKey2>, TValue2> {

        /**
         * @param key
         *        must not be {@code null}
         * @param value
         *        must not be {@code null}
         *
         * @throws NullPointerException
         *         if any arg is {@code null}
         * @throws IllegalArgumentException
         *         if {@code key} is already mapped to a value not equal to {@code value}
         */
        Builder<TEnumKey2, TValue2>
        put(TEnumKey2 key, TValue2 value);

        /**
         * Convenience method to iterate all pairs and call {@link #put(Enum, Object)}.
         *
         * @throws NullPointerException
         *         if any arg is {@code null}
         */
        Builder<TEnumKey2, TValue2>
        putAll(Map<TEnumKey2, ? extends TValue2> map);

        /**
         * Convenience method to call {@link #buildWhere(IsEmptyEnumAllowed)} with {@link IsEmptyEnumAllowed#NO}.
         */
        ImmutableFullEnumMap<TEnumKey2, TValue2>
        build();

        /**
         * Java allows enums to have no values (constants), but almost always this is an accident.  By "default"
         * ({@link #build()}), do not all empty maps to be built.
         *
         * @throws IllegalArgumentException
         *         if {@code isEmptyEnumAllowed} is {@link IsEmptyEnumAllowed#NO}
         *         and enum ({@code TEnumKey2}) has zero values
         */
        ImmutableFullEnumMap<TEnumKey2, TValue2>
        buildWhere(IsEmptyEnumAllowed isEmptyEnumAllowed);
    }

    /**
     * @param enumClass
     *        must not be {@code null}
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    Builder<TEnumKey2, TValue2>
    builder(Class<TEnumKey2> enumClass) {

        final ImmutableFullEnumMapBuilderImpl<TEnumKey2, TValue2> x = new ImmutableFullEnumMapBuilderImpl<>(enumClass);
        return x;
    }

    /**
     * If you need to throw from {@code keyToValueFunc}, see: {@link #ofKeys2(Class, ThrowingFunction)}.
     *
     * @param enumClass
     *        must contain one or more enum constants
     * @param keyToValueFunc
     *        maps key to value
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code enumClass} has zero enum constants
     *
     * @see #ofKeys2(Class, ThrowingFunction)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    ImmutableFullEnumMap<TEnumKey2, TValue2>
    ofKeys(Class<TEnumKey2> enumClass,
           Function<TEnumKey2, TValue2> keyToValueFunc) {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(keyToValueFunc, "keyToValueFunc");

        final ImmutableMap.Builder<TEnumKey2, TValue2> b = ImmutableMap.builder();
        final TEnumKey2[] keyArr = enumClass.getEnumConstants();
        for (final TEnumKey2 key : keyArr) {

            final TValue2 value = keyToValueFunc.apply(key);
            b.put(key, value);
        }
        final ImmutableMap<TEnumKey2, TValue2> z = b.build();
        final ImmutableFullEnumMap<TEnumKey2, TValue2> x =
            new ImmutableFullEnumMap<>(enumClass, z, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * @param enumClass
     *        must contain one or more enum constants
     * @param keyToValueFunc
     *        maps key to value (and may throw a checked exception)
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code enumClass} has zero enum constants
     *
     * @see #ofKeys(Class, Function)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    ImmutableFullEnumMap<TEnumKey2, TValue2>
    ofKeys2(Class<TEnumKey2> enumClass,
            ThrowingFunction<TEnumKey2, TValue2> keyToValueFunc)
    throws Exception{

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(keyToValueFunc, "keyToValueFunc");

        final ImmutableMap.Builder<TEnumKey2, TValue2> b = ImmutableMap.builder();
        final TEnumKey2[] keyArr = enumClass.getEnumConstants();
        for (final TEnumKey2 key : keyArr) {

            final TValue2 value = keyToValueFunc.apply(key);
            b.put(key, value);
        }
        final ImmutableMap<TEnumKey2, TValue2> z = b.build();
        final ImmutableFullEnumMap<TEnumKey2, TValue2> x =
            new ImmutableFullEnumMap<>(enumClass, z, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * If you need to throw from {@code valueToKeyFunc}, see: {@link #ofValues2(Class, Collection, ThrowingFunction)}.
     *
     * @param enumClass
     *        must contain one or more enum constants
     * @param c
     *        non-empty collection of values where number of elements matches {@code enumClass.getEnumConstants()}
     * @param valueToKeyFunc
     *        maps value to key
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code c} is empty or contains {@code null} values</li>
     *     <li>if multiple values map to the same key</li>
     * </ul>
     *
     * @see Class#getEnumConstants()
     * @see #ofValues2(Class, Collection, ThrowingFunction)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    ImmutableFullEnumMap<TEnumKey2, TValue2>
    ofValues(Class<TEnumKey2> enumClass,
             Collection<? extends TValue2> c,
             Function<TValue2, TEnumKey2> valueToKeyFunc) {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        CollectionArgs.checkNotEmptyAndElementsNotNull(c, "c");
        ObjectArgs.checkNotNull(valueToKeyFunc, "valueToKeyFunc");

        final ImmutableMap.Builder<TEnumKey2, TValue2> b = ImmutableMap.builder();
        for (final TValue2 value : c) {

            final TEnumKey2 key = valueToKeyFunc.apply(value);
            b.put(key, value);
        }
        final ImmutableMap<TEnumKey2, TValue2> z = b.build();
        final ImmutableFullEnumMap<TEnumKey2, TValue2> x =
            new ImmutableFullEnumMap<>(enumClass, z, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * @param enumClass
     *        must contain one or more enum constants
     * @param c
     *        non-empty collection of values where number of elements matches {@code enumClass.getEnumConstants()}
     * @param valueToKeyFunc
     *        maps value to key (and may throw a checked exception)
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code c} is empty or contains {@code null} values</li>
     *     <li>if multiple values map to the same key</li>
     * </ul>
     *
     * @see Class#getEnumConstants()
     * @see #ofValues(Class, Collection, Function)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    ImmutableFullEnumMap<TEnumKey2, TValue2>
    ofValues2(Class<TEnumKey2> enumClass,
              Collection<? extends TValue2> c,
              ThrowingFunction<TValue2, TEnumKey2> valueToKeyFunc)
    throws Exception{

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        CollectionArgs.checkNotEmptyAndElementsNotNull(c, "c");
        ObjectArgs.checkNotNull(valueToKeyFunc, "valueToKeyFunc");

        final ImmutableMap.Builder<TEnumKey2, TValue2> b = ImmutableMap.builder();
        for (final TValue2 value : c) {

            final TEnumKey2 key = valueToKeyFunc.apply(value);
            b.put(key, value);
        }
        final ImmutableMap<TEnumKey2, TValue2> z = b.build();
        final ImmutableFullEnumMap<TEnumKey2, TValue2> x =
            new ImmutableFullEnumMap<>(enumClass, z, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * @param enumClass
     *        must not be {@code null} nor be empty
     * @param map
     *        keys must be all enums and must not be {@code null} nor be empty
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code map} is empty
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    ImmutableFullEnumMap<TEnumKey2, TValue2>
    copyOf(Class<TEnumKey2> enumClass,
           Map<TEnumKey2, ? extends TValue2> map) {

        final ImmutableFullEnumMap<TEnumKey2, TValue2> x =
            // Intentional: Never allow empty enums for this static factory method.
            new ImmutableFullEnumMap<>(enumClass, map, IsEmptyEnumAllowed.NO);
        return x;
    }

    private final Class<TEnumKey> enumClass;
    private final ImmutableMap<TEnumKey, TValue> map;

    /**
     * Iteration order follows enum iterator order like {@link java.util.EnumMap}.
     *
     * @param enumClass
     *        must not be {@code null} nor be empty
     * @param map
     *        keys must be all enums and must not be {@code null} nor be empty
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code isEmptyEnumAllowed} is {@link IsEmptyEnumAllowed#NO}
     *         and enum ({@code TEnumKey2}) has zero values
     *
     * @see Maps#immutableEnumMap(Map)
     */
    public ImmutableFullEnumMap(Class<TEnumKey> enumClass,
                                @EmptyContainerAllowed Map<TEnumKey, ? extends TValue> map,
                                IsEmptyEnumAllowed isEmptyEnumAllowed) {

        this.enumClass = ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(map, "map");
        ObjectArgs.checkNotNull(isEmptyEnumAllowed, "isEmptyEnumAllowed");
        @ReadOnlyContainer
        @Nullable
        final TEnumKey[] enumValueArr = _getEnumArrOrThrow(enumClass, isEmptyEnumAllowed);
        _assertAllKeysFound(enumClass, map, enumValueArr);
        this.map = Maps.immutableEnumMap(map);
    }

    private static <TEnumKey2 extends Enum<TEnumKey2>>
    TEnumKey2[]
    _getEnumArrOrThrow(Class<TEnumKey2> enumClass,
                       IsEmptyEnumAllowed isEmptyEnumAllowed) {
        @ReadOnlyContainer
        @Nullable
        final TEnumKey2[] enumValueArr = enumClass.getEnumConstants();
        if (null == enumValueArr) {
            throw new IllegalStateException("Unreachable code");
        }
        if (IsEmptyEnumAllowed.NO.equals(isEmptyEnumAllowed) && 0 == enumValueArr.length) {

            final String msg = "Enum class " + enumClass.getSimpleName() + " has zero values";
            throw new IllegalArgumentException(msg);
        }
        return enumValueArr;
    }

    private static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    void _assertAllKeysFound(Class<TEnumKey2> enumClass,
                             @EmptyContainerAllowed Map<TEnumKey2, ? extends TValue2> map,
                             @ReadOnlyContainer TEnumKey2[] enumValueArr) {

        if (enumValueArr.length == map.size()) {
            return;
        }
        final StringBuilder sb =
            new StringBuilder("Missing values from enum class ").append(enumClass.getSimpleName()).append(": ");

        String delim = "";
        for (final TEnumKey2 enumValue : enumValueArr) {

            if (map.containsKey(enumValue)) {
                continue;
            }
            sb.append(delim).append(enumValue.name());
            delim = ", ";
        }
        final String sbs = sb.toString();
        throw new IllegalArgumentException(sbs);
    }

    @Override
    protected Map<TEnumKey, TValue>
    delegate() {
        return map;
    }

    @Override
    public Class<TEnumKey>
    getEnumClass() {
        return enumClass;
    }
}
