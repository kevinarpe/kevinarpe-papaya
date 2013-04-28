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
