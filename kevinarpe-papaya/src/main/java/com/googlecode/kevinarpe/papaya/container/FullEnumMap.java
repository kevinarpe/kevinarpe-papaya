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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.policy.DoNotAllow;
import com.googlecode.kevinarpe.papaya.container.policy.PolicyMap;
import com.googlecode.kevinarpe.papaya.function.ThrowingFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class a blend of {@link ImmutableMap} and {@link EnumMap}, but requires all enum keys to be present.
 * <p>
 * These maps are CPU and memory intensive to construct, but very fast to access data after construction.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class FullEnumMap<TEnumKey extends Enum<TEnumKey>, TValue>
extends PolicyMap<TEnumKey, TValue>
implements EnumMap2<TEnumKey, TValue> {

    public interface Builder<TEnumKey2 extends Enum<TEnumKey2>, TValue2> {

        Class<TEnumKey2>
        getEnumClass();

        AreNullValuesAllowed
        areNullValuesAllowed();

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
         *        may be {@code null} when {@link AreNullValuesAllowed#YES}
         *
         * @throws NullPointerException
         *         if {@code key} is {@code null}
         *         <br>if {@code value} is {@code null} when {@link AreNullValuesAllowed#NO}
         *
         * @throws IllegalArgumentException
         *         if {@code key} is already mapped to a value not equal to {@code value}
         */
        Builder<TEnumKey2, TValue2>
        put(TEnumKey2 key,
            @Nullable TValue2 value);

        /**
         * Convenience method to iterate all pairs and calls {@link #put(Enum, Object)}.
         *
         * @throws NullPointerException
         *         if {@code map} or any key is {@code null}
         *         <br>if any value is {@code null} when {@link AreNullValuesAllowed#NO}
         */
        Builder<TEnumKey2, TValue2>
        putAll(Map<TEnumKey2, ? extends TValue2> map);

        /**
         * Convenience method to call {@link #buildWhere(IsEmptyEnumAllowed)}
         * with {@link IsEmptyEnumAllowed#NO}.
         */
        FullEnumMap<TEnumKey2, TValue2>
        build();

        /**
         * Java allows enums to have no values (constants), but almost always this is an accident.  By "default"
         * ({@link #build()}), do not all empty maps to be built.
         *
         * @throws IllegalArgumentException
         *         if {@code isEmptyEnumAllowed} is {@link IsEmptyEnumAllowed#NO}
         *         and enum ({@code TEnumKey2}) has zero values
         */
        FullEnumMap<TEnumKey2, TValue2>
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
    builder(Class<TEnumKey2> enumClass,
            AreNullValuesAllowed areNullValuesAllowed) {

        final FullEnumMapBuilderImpl<TEnumKey2, TValue2> x =
            new FullEnumMapBuilderImpl<>(enumClass, areNullValuesAllowed);
        return x;
    }

    /**
     * If you need to throw from {@code keyToValueFunc},
     * see: {@link #ofKeys2(Class, AreNullValuesAllowed, ThrowingFunction)}.
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
     * @see #ofKeys2(Class, AreNullValuesAllowed, ThrowingFunction)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    FullEnumMap<TEnumKey2, TValue2>
    ofKeys(Class<TEnumKey2> enumClass,
           AreNullValuesAllowed areNullValuesAllowed,
           Function<TEnumKey2, TValue2> keyToValueFunc) {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(keyToValueFunc, "keyToValueFunc");

        final EnumMap<TEnumKey2, TValue2> map = new EnumMap<>(enumClass);
        final TEnumKey2[] keyArr = enumClass.getEnumConstants();
        for (final TEnumKey2 key : keyArr) {

            final TValue2 value = keyToValueFunc.apply(key);
            map.put(key, value);
        }
        // Intentional: Never allow empty enums for this static factory method.
        final FullEnumMap<TEnumKey2, TValue2> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * If you do <b>not</b> need to throw from {@code keyToValueFunc},
     * see: {@link #ofKeys(Class, AreNullValuesAllowed, Function)}.
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
     * @see #ofKeys(Class, AreNullValuesAllowed, Function)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    FullEnumMap<TEnumKey2, TValue2>
    ofKeys2(Class<TEnumKey2> enumClass,
            AreNullValuesAllowed areNullValuesAllowed,
            ThrowingFunction<TEnumKey2, TValue2> keyToValueFunc)
    throws Exception{

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(keyToValueFunc, "keyToValueFunc");

        final EnumMap<TEnumKey2, TValue2> map = new EnumMap<>(enumClass);
        final TEnumKey2[] keyArr = enumClass.getEnumConstants();
        for (final TEnumKey2 key : keyArr) {

            final TValue2 value = keyToValueFunc.apply(key);
            map.put(key, value);
        }
        // Intentional: Never allow empty enums for this static factory method.
        final FullEnumMap<TEnumKey2, TValue2> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * If you need to throw from {@code valueToKeyFunc},
     * see: {@link #ofValues2(Class, AreNullValuesAllowed, Collection, ThrowingFunction)}.
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
     * @see #ofValues2(Class, AreNullValuesAllowed, Collection, ThrowingFunction)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    FullEnumMap<TEnumKey2, TValue2>
    ofValues(Class<TEnumKey2> enumClass,
             AreNullValuesAllowed areNullValuesAllowed,
             Collection<? extends TValue2> c,
             Function<TValue2, TEnumKey2> valueToKeyFunc) {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        CollectionArgs.checkNotEmptyAndElementsNotNull(c, "c");
        ObjectArgs.checkNotNull(valueToKeyFunc, "valueToKeyFunc");

        final EnumMap<TEnumKey2, TValue2> map = new EnumMap<>(enumClass);
        for (final TValue2 value : c) {

            final TEnumKey2 key = valueToKeyFunc.apply(value);
            map.put(key, value);
        }
        // Intentional: Never allow empty enums for this static factory method.
        final FullEnumMap<TEnumKey2, TValue2> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, IsEmptyEnumAllowed.NO);
        return x;
    }

    /**
     * If you do <b>not</b> need to throw from {@code valueToKeyFunc},
     * see: {@link #ofValues(Class, AreNullValuesAllowed, Collection, Function)}.
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
     * @see #ofValues(Class, AreNullValuesAllowed, Collection, Function)
     */
    public static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    FullEnumMap<TEnumKey2, TValue2>
    ofValues2(Class<TEnumKey2> enumClass,
              AreNullValuesAllowed areNullValuesAllowed,
              Collection<? extends TValue2> c,
              ThrowingFunction<TValue2, TEnumKey2> valueToKeyFunc)
    throws Exception {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        CollectionArgs.checkNotEmptyAndElementsNotNull(c, "c");
        ObjectArgs.checkNotNull(valueToKeyFunc, "valueToKeyFunc");

        final EnumMap<TEnumKey2, TValue2> map = new EnumMap<>(enumClass);
        for (final TValue2 value : c) {

            final TEnumKey2 key = valueToKeyFunc.apply(value);
            map.put(key, value);
        }
        // Intentional: Never allow empty enums for this static factory method.
        final FullEnumMap<TEnumKey2, TValue2> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, IsEmptyEnumAllowed.NO);
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
    FullEnumMap<TEnumKey2, TValue2>
    copyOf(Class<TEnumKey2> enumClass,
           AreNullValuesAllowed areNullValuesAllowed,
           Map<TEnumKey2, ? extends TValue2> map) {

        // Intentional: Never allow empty enums for this static factory method.
        final FullEnumMap<TEnumKey2, TValue2> x =
            new FullEnumMap<>(enumClass, areNullValuesAllowed, map, IsEmptyEnumAllowed.NO);
        return x;
    }

    private final Class<TEnumKey> enumClass;

    /**
     * Iteration order follows enum iterator order like {@link EnumMap}.
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
    public FullEnumMap(Class<TEnumKey> enumClass,
                       AreNullValuesAllowed areNullValuesAllowed,
                       @EmptyContainerAllowed
                       Map<TEnumKey, ? extends TValue> map,
                       IsEmptyEnumAllowed isEmptyEnumAllowed) {
        super(
            _createEnumMap(enumClass, map),
            _createDoNotAllowSet(areNullValuesAllowed));

        this.enumClass = ObjectArgs.checkNotNull(enumClass, "enumClass");
        if (AreNullValuesAllowed.YES.equals(areNullValuesAllowed)) {

            MapArgs.checkKeysNotNull(map, "map");
        }
        else {
            MapArgs.checkKeysAndValuesNotNull(map, "map");
        }
        ObjectArgs.checkNotNull(isEmptyEnumAllowed, "isEmptyEnumAllowed");
        _FullEnumMaps._assertAllKeysFound(enumClass, map, isEmptyEnumAllowed);
    }

    private static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    EnumMap<TEnumKey2, TValue2>
    _createEnumMap(Class<TEnumKey2> enumClass,
                   @EmptyContainerAllowed
                   Map<TEnumKey2, ? extends TValue2> map) {

        ObjectArgs.checkNotNull(enumClass, "enumClass");
        ObjectArgs.checkNotNull(map, "map");

        // Intentional: USE TWO STEP CONSTRUCTION!!!  This map might be empty.  Be careful.
        final EnumMap<TEnumKey2, TValue2> enumMap = new EnumMap<>(enumClass);
        enumMap.putAll(map);
        return enumMap;
    }

    private static ImmutableSet<DoNotAllow>
    _createDoNotAllowSet(AreNullValuesAllowed areNullValuesAllowed) {

        ObjectArgs.checkNotNull(areNullValuesAllowed, "areNullValuesAllowed");

        final ImmutableSet.Builder<DoNotAllow> b =
            ImmutableSet.<DoNotAllow>builder()
                .add(DoNotAllow.NullKey)
                .add(DoNotAllow.Remove);

        if (AreNullValuesAllowed.NO.equals(areNullValuesAllowed)) {

            b.add(DoNotAllow.NullValue);
        }
        final ImmutableSet<DoNotAllow> x = b.build();
        return x;
    }

    @Override
    public Class<TEnumKey>
    getEnumClass() {
        return enumClass;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public TValue
    getByEnum(TEnumKey key) {

        ObjectArgs.checkNotNull(key, "key");

        @Nullable
        final TValue x = get(key);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Return value is never {@code null}.
     */
    @Nonnull
    @Override
    public TValue
    getByEnumOrThrow(TEnumKey key) {

        final TValue x = EnumMap2.super.getByEnumOrThrow(key);
        return x;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public TValue
    getOrThrow(Object key) {

        ObjectArgs.checkNotNull(key, "key");

        @Nullable
        final TValue value = get(key);
        // Be careful: If value is null, we cannot be sure if key is missing or key is mapped to null.
        if (null != value || containsKey(key)) {
            return value;
        }
        else {
            throw new IllegalArgumentException("Key [" + key + "] does not exist");
        }
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
    @Override
    public TValue
    computeIfAbsent(@Nullable TEnumKey key,
                    Function<? super TEnumKey, ? extends TValue> mappingFunction) {

        throw new UnsupportedOperationException();
    }
}
