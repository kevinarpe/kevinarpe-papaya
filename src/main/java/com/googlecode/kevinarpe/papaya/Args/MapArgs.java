package com.googlecode.kevinarpe.papaya.Args;

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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class MapArgs {

	// Disable default constructor
	private MapArgs() {
	}

    /**
     * Tests if a {@link Map} reference is not null and its size within specified range.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref a map reference
     * @param minSize minimum number of key-value pairs (inclusive).  Must be non-negative.
     * @param maxSize maximum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code minSize < 0},
     *         or if {@code maxSize < 0},
     *         or if {@code minSize > maxSize}, 
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinSize(Map, int, String)
     * @see #checkMaxSize(Map, int, String)
     * @see #checkExactSize(Map, int, String)
     */
    public static <TKey, TValue> void checkSizeRange(
            Map<TKey, TValue> ref, int minSize, int maxSize, String argName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        IntArgs.checkNotNegative(maxSize, "maxSize");
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Map", size, minSize, maxSize, argName);
    }
	
    /**
     * Tests if a {@link Map} reference is not null and has a minimum size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref a collection reference
     * @param minSize minimum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code minSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Map, int, int, String)
     */
    public static <TKey, TValue> void checkMinSize(
            Map<TKey, TValue> ref, int minSize, String argName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        int size = (null == ref ? -1 : ref.size());
        int maxSize = -1;
        ContainerArgs._checkSizeRange(ref, "Map", size, minSize, maxSize, argName);
    }
    
    /**
     * Tests if a {@link Map} reference is not null and has a maximum size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref a collection reference
     * @param maxSize maximum number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code maxSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Map, int, int, String)
     */
    public static <TKey, TValue> void checkMaxSize(
            Map<TKey, TValue> ref, int maxSize, String argName) {
        IntArgs.checkNotNegative(maxSize, "maxSize");
        int size = (null == ref ? -1 : ref.size());
        int minSize = -1;
        ContainerArgs._checkSizeRange(ref, "Map", size, minSize, maxSize, argName);
    }
    
    /**
     * Tests if a {@link Map} reference is not null and has an exact size.
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref a collection reference
     * @param exactSize exact number of key-value pairs (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code exactSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Map, int, int, String)
     */
    public static <TKey, TValue> void checkExactSize(
            Map<TKey, TValue> ref, int exactSize, String argName) {
        IntArgs.checkNotNegative(exactSize, "exactSize");
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Map", size, exactSize, exactSize, argName);
    }
    
    /**
     * Tests if a {@link Map} reference is not null and not empty (size >= 1).
     * Size is defined as the number of key-value pairs.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if number of key-value pairs in {@code ref} is zero,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Map, int, int, String)
     * @see #checkMinSize(Map, int, String)
     */
    public static <TKey, TValue> void checkNotEmpty(Map<TKey, TValue> ref, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkNotEmpty(ref, "Map", size, argName);
    }
    
    /**
     * Tests if a map reference is not null and each key is not null.  An empty map will pass
     * this test.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkValuesNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any key) or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     */
    public static <TKey, TValue> void checkKeysNotNull(
            Map<TKey, TValue> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        if (ref.containsKey(null)) {
            TValue value = ref.get(null);
            String valueStr = (null == value ? "null" : String.format("'%s'", value));
            StringArgs._checkArgNameValid(argName, "argName");
            throw new NullPointerException(String.format(
                "Map argument '%s': Key is null where value is %s", argName, valueStr));
        }
    }

    /**
     * Tests if a map reference is not null and each value is not null.  An empty map will pass
     * this test.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkKeysNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any value) or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     */
    public static <TKey, TValue> void checkValuesNotNull(
            Map<TKey, TValue> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (Map.Entry<TKey, TValue> entry: ref.entrySet()) {
            TValue value = entry.getValue();
            if (null == value) {
                TKey key = entry.getKey();
                String keyStr = (null == key ? "null" : String.format("'%s'", key));
                StringArgs._checkArgNameValid(argName, "argName");
                throw new NullPointerException(String.format(
                    "Map argument '%s': Value is null where key is %s", argName, keyStr));
            }
        }
    }

    /**
     * Tests if a map reference is not null and each key and value is not null.  An empty map will
     * pass this test.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysNotNull(Map, String)
     * @see #checkValuesNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any key or value) or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     */
    public static <TKey, TValue> void checkKeysAndValuesNotNull(
            Map<TKey, TValue> ref, String argName) {
        checkKeysNotNull(ref, argName);
        checkValuesNotNull(ref, argName);
        ObjectArgs.checkNotNull(ref, argName);
    }
}
