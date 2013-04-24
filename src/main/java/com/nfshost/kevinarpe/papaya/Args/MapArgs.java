package com.nfshost.kevinarpe.papaya.Args;

import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class MapArgs {

    /**
     * Tests if a map reference is not null and each key and value is not null.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysNotNull(Map, String)
     * @see #checkValuesNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any key or value) or {@code argName} is null
     */
    public static <TKey, TValue> void checkKeysAndValuesNotNull(
            Map<TKey, TValue> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (Map.Entry<TKey, TValue> entry: ref.entrySet()) {
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            if (null == key && null != value) {
                throw new NullPointerException(String.format(
                    "Map argument '%s': Key is null where value is '%s'", argName, value));
            }
            else if (null != key && null == value) {
                throw new NullPointerException(String.format(
                    "Map argument '%s': Value is null where key is '%s'", argName, key));
            }
            else if (null == key && null == value) {
                throw new NullPointerException(String.format(
                    "Map argument '%s': Both key and value are null", argName));
            }
        }
    }

    /**
     * Tests if a map reference is not null and each key is not null.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkValuesNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any key) or {@code argName} is null
     */
    public static <TKey, TValue> void checkKeysNotNull(
            Map<TKey, TValue> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (Map.Entry<TKey, TValue> entry: ref.entrySet()) {
            TKey key = entry.getKey();
            if (null == key) {
                TValue value = entry.getValue();
                throw new NullPointerException(String.format(
                    "Map argument '%s': Key is null where value is '%s'", argName, value));
            }
        }
    }

    /**
     * Tests if a map reference is not null and each value is not null.
     * 
     * @param ref a map reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkKeysAndValuesNotNull(Map, String)
     * @see #checkKeysNotNull(Map, String)
     * @throws NullPointerException if {@code ref} (or any value) or {@code argName} is null
     */
    public static <TKey, TValue> void checkValuesNotNull(
            Map<TKey, TValue> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (Map.Entry<TKey, TValue> entry: ref.entrySet()) {
            TValue value = entry.getValue();
            if (null == value) {
                TKey key = entry.getKey();
                throw new NullPointerException(String.format(
                    "Map argument '%s': Value is null where key is '%s'", argName, key));
            }
        }
    }
}
