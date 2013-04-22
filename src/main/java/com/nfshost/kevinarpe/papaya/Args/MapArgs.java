package com.nfshost.kevinarpe.papaya.Args;

import java.util.Map;

public final class MapArgs {

	/**
	 * Tests if a map reference is not null and each key and value is not null.
	 * 
	 * @param ref a map reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @see ObjectArgs#staticCheckNotNull(Object, String)
	 * @see #staticCheckKeysNotNull(Map, String)
	 * @see #staticCheckValuesNotNull(Map, String)
	 * @throws NullPointerException if {@code ref} (or any key or value) or {@code argName} is null
	 */
	public static <TKey, TValue> void staticCheckKeysAndValuesNotNull(
			Map<TKey, TValue> ref, String argName) {
		ObjectArgs.staticCheckNotNull(ref, argName);
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
	 * @see ObjectArgs#staticCheckNotNull(Object, String)
	 * @see #staticCheckKeysAndValuesNotNull(Map, String)
	 * @see #staticCheckValuesNotNull(Map, String)
	 * @throws NullPointerException if {@code ref} (or any key) or {@code argName} is null
	 */
	public static <TKey, TValue> void staticCheckKeysNotNull(
			Map<TKey, TValue> ref, String argName) {
		ObjectArgs.staticCheckNotNull(ref, argName);
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
	 * @see ObjectArgs#staticCheckNotNull(Object, String)
	 * @see #staticCheckKeysAndValuesNotNull(Map, String)
	 * @see #staticCheckKeysNotNull(Map, String)
	 * @throws NullPointerException if {@code ref} (or any value) or {@code argName} is null
	 */
	public static <TKey, TValue> void staticCheckValuesNotNull(
			Map<TKey, TValue> ref, String argName) {
		ObjectArgs.staticCheckNotNull(ref, argName);
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
