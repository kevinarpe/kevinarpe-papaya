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
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
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

    public interface Builder<TEnumKey2 extends Enum<TEnumKey2>, TValue2> {

        Class<TEnumKey2>
        getEnumClass();

        /**
         * @return read-only reference to underlying map used by builder
         *
         * @see Collections#unmodifiableMap(Map)
         */
        Map<TEnumKey2, TValue2>
        getReadOnlyMap();

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
        put(TEnumKey2 key,
            TValue2 value);

        /**
         * Convenience method to iterate all pairs and calls {@link #put(Enum, Object)}.
         *
         * @throws NullPointerException
         *         if any arg is {@code null}
         */
        Builder<TEnumKey2, TValue2>
        putAll(Map<TEnumKey2, ? extends TValue2> map);

        /**
         * Convenience method to call {@link #buildWhere(IsEmptyEnumAllowed)}
         * with {@link IsEmptyEnumAllowed#NO}.
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
     * If you do <b>not</b> need to throw from {@code keyToValueFunc}, see: {@link #ofKeys(Class, Function)}.
     *
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
     * If you do <b>not</b> need to throw from {@code valueToKeyFunc}, see: {@link #ofValues(Class, Collection, Function)}.
     *
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
    throws Exception {

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
     * Note: To construct an empty instance, please call the constructor,
     * {@link #ImmutableFullEnumMap(Class, Map, IsEmptyEnumAllowed)}, directly with {@link IsEmptyEnumAllowed#YES}.
     *
     * @param enumClass
     *        must not be {@code null} nor be empty
     * @param map
     *        keys must be all enums and must not be {@code null} nor be empty
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code map} is empty
     *
     * @see #ImmutableFullEnumMap(Class, Map, IsEmptyEnumAllowed)
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
     *        keys must be all enums and must not be {@code null}
     *        <br>This argument may be empty if {@code isEmptyEnumAllowed} is {@link IsEmptyEnumAllowed#YES}.
     *
     * @throws NullPointerException
     *         if any args is {@code null}
     * @throws IllegalArgumentException
     *         if {@code isEmptyEnumAllowed} is {@link IsEmptyEnumAllowed#NO}
     *         and enum ({@code TEnumKey2}) has zero values
     *
     * @see #copyOf(Class, Map)
     * @see Maps#immutableEnumMap(Map)
     */
    public ImmutableFullEnumMap(Class<TEnumKey> enumClass,
                                @EmptyContainerAllowed Map<TEnumKey, ? extends TValue> map,
                                IsEmptyEnumAllowed isEmptyEnumAllowed) {

        this.enumClass = ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(map, "map");
        ObjectArgs.checkNotNull(isEmptyEnumAllowed, "isEmptyEnumAllowed");
        _FullEnumMaps._assertAllKeysFound(enumClass, map, isEmptyEnumAllowed);
        this.map = Maps.immutableEnumMap(map);
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

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Result is never {@code null}.
     */
    @Nonnull
    @Override
    public TValue
    getByEnum(TEnumKey key) {

        ObjectArgs.checkNotNull(key, "key");

        // Is null possible here?  Not sure, but be very careful.
        @Nullable
        final TValue value = get(key);
        if (null == value) {

            if (containsKey(key)) {

                throw new NullPointerException(
                    "Internal error: Key [" + key + "] is mapped to a null value: How is this possible?");
            }
            else {
                throw new NullPointerException("Failed to find mapping for key: [" + key + "]");
            }
        }
        return value;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Result is never {@code null}.
     */
    @Nonnull
    @Override
    public TValue
    getOrThrow(Object key) {

        ObjectArgs.checkNotNull(key, "key");

        @Nullable
        final TValue value = get(key);
        // This is impossible, but check to be very careful.
        if (null == value) {
            if (containsKey(key)) {
                throw new IllegalArgumentException("Key [" + key + "] is mapped to null");
            }
            else {
                throw new IllegalArgumentException("Failed to find mapping for key: [" + key + "]");
            }
        }
        return value;
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    put(@Nullable TEnumKey key,
        @Nullable TValue value) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    remove(@Nullable Object key) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public void
    putAll(@EmptyContainerAllowed Map<? extends TEnumKey, ? extends TValue> map) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public void
    clear() {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public void
    replaceAll(BiFunction<? super TEnumKey, ? super TValue, ? extends TValue> function) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    putIfAbsent(@Nullable TEnumKey key,
                @Nullable TValue value) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    remove(@Nullable Object key,
           @Nullable Object value) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public boolean
    replace(@Nullable TEnumKey key,
            @Nullable TValue oldValue,
            @Nullable TValue newValue) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    replace(@Nullable TEnumKey key,
            @Nullable TValue value) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    computeIfAbsent(@Nullable TEnumKey key,
                    Function<? super TEnumKey, ? extends TValue> mappingFunction) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    computeIfPresent(@Nullable TEnumKey key,
                     BiFunction<? super TEnumKey, ? super TValue, ? extends TValue> remappingFunction) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    compute(@Nullable TEnumKey key,
            BiFunction<? super TEnumKey, ? super TValue, ? extends TValue> remappingFunction) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave the map unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Nullable
    @Override
    public TValue
    merge(@Nullable TEnumKey key,
          @Nullable TValue value,
          BiFunction<? super TValue, ? super TValue, ? extends TValue> remappingFunction) {

        throw new UnsupportedOperationException();
    }
}
