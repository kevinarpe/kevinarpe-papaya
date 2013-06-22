package com.googlecode.kevinarpe.papaya.args;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.Map;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;

/**
 * Static methods to check {@link Map} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class MapArgs {

    // Disable default constructor
    private MapArgs() {
    }

    /**
     * Tests if a {@link Map} reference is not null and its size within specified range.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref
     *        a map reference
     * @param minSize
     *        minimum number of key-value pairs (inclusive).  Must be non-negative.
     * @param maxSize
     *        maximum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minSize < 0}</li>
     *   <li>if {@code maxSize < 0}</li>
     *   <li>if {@code minSize > maxSize}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinSize(Map, int, String)
     * @see #checkMaxSize(Map, int, String)
     * @see #checkExactSize(Map, int, String)
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkSizeRange(TMap ref, int minSize, int maxSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Map", size, minSize, maxSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Map} reference is not null and has a minimum size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref
     *        a collection reference
     * @param minSize
     *        minimum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkSizeRange(Map, int, int, String)
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkMinSize(TMap ref, int minSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkMinSize(ref, "Map", size, minSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Map} reference is not null and has a maximum size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref
     *        a collection reference
     * @param maxSize
     *        maximum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code maxSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * <ul>
     *
     * @see #checkSizeRange(Map, int, int, String)
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkMaxSize(TMap ref, int maxSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkMaxSize(ref, "Map", size, maxSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Map} reference is not null and has an exact size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref
     *        a collection reference
     * @param exactSize
     *        exact number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code exactSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * <ul>
     *
     * @see #checkSizeRange(Map, int, int, String)
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkExactSize(TMap ref, int exactSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkExactSize(ref, "Map", size, exactSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Map} reference is not null and not empty (size >= 1).
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref
     *        a map reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if number of key-value pairs in {@code ref} is zero
     *
     * @see #checkSizeRange(Map, int, int, String)
     * @see #checkMinSize(Map, int, String)
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkNotEmpty(TMap ref, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkNotEmpty(ref, "Map", size, argName);
        return ref;
    }
    
    /**
     * Tests if a map reference is not null and each key is not null.  An empty map will pass
     * this test.
     * 
     * @param ref
     *        a map reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkValuesNotNull(Map, String)
     *
     * @throws NullPointerException
     *         if {@code ref} (or any key) is {@code null}
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkKeysNotNull(TMap ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        if (ref.containsKey(null)) {
            TValue value = ref.get(null);
            String valueStr = (null == value ? "null" : String.format("'%s'", value));
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new NullPointerException(String.format(
                "Map argument '%s': Key is null where value is %s%s", argName, valueStr, w));
        }
        return ref;
    }

    /**
     * Tests if a map reference is not null and each value is not null.  An empty map will pass
     * this test.
     * 
     * @param ref
     *        a map reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated map reference
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkKeysNotNull(Map, String)
     *
     * @throws NullPointerException
     *         if {@code ref} (or any value) is {@code null}
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkValuesNotNull(TMap ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (Map.Entry<TKey, TValue> entry: ref.entrySet()) {
            TValue value = entry.getValue();
            if (null == value) {
                TKey key = entry.getKey();
                String keyStr = (null == key ? "null" : String.format("'%s'", key));
                String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new NullPointerException(String.format(
                    "Map argument '%s': Value is null where key is %s%s", argName, keyStr, w));
            }
        }
        return ref;
    }

    /**
     * This is a convenience method for {@link #checkKeysNotNull(Map, String)} and
     * {@link #checkValuesNotNull(Map, String)}.
     * 
     * @return the validated map reference
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkKeysAndValuesNotNull(TMap ref, String argName) {
        checkKeysNotNull(ref, argName);
        checkValuesNotNull(ref, argName);
        return ref;
    }

    /**
     * This is a convenience method for {@link #checkNotEmpty(Map, String)} and
     * {@link #checkKeysNotNull(Map, String)}.
     * 
     * @return the validated map reference
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkNotEmptyAndKeysNotNull(TMap ref, String argName) {
        checkNotEmpty(ref, argName);
        checkKeysNotNull(ref, argName);
        return ref;
    }

    /**
     * This is a convenience method for {@link #checkNotEmpty(Map, String)} and
     * {@link #checkValuesNotNull(Map, String)}.
     * 
     * @return the validated map reference
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkNotEmptyAndValuesNotNull(TMap ref, String argName) {
        checkNotEmpty(ref, argName);
        checkValuesNotNull(ref, argName);
        return ref;
    }

    /**
     * This is a convenience method for {@link #checkNotEmpty(Map, String)},
     * {@link #checkKeysNotNull(Map, String)}, and {@link #checkValuesNotNull(Map, String)}.
     * 
     * @return the validated map reference
     */
    @FullyTested
    public static <TKey, TValue, TMap extends Map<TKey, TValue>>
    TMap checkNotEmptyAndKeysAndValuesNotNull(TMap ref, String argName) {
        checkNotEmpty(ref, argName);
        checkKeysNotNull(ref, argName);
        checkValuesNotNull(ref, argName);
        return ref;
    }
}
