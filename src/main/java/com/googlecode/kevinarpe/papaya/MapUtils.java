package com.googlecode.kevinarpe.papaya;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.googlecode.kevinarpe.papaya.Args.ObjectArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class MapUtils {

	// Disable default constructor
	private MapUtils() {
	}
	
	/**
     * Convenience method to call {@link #asHashMap2(List)}.
     * 
     * @see #asHashMap3(List, List)
     * @see #putKeysAndValues(Map, Object...)
	 */
    public static <TKey, TValue> HashMap<TKey, TValue> asHashMap(
    		Object... keyAndValueArr) {
    	List<Object> x = Arrays.asList(keyAndValueArr);
    	HashMap<TKey, TValue> map = asHashMap2(x);
    	return map;
    }
	
	/**
	 * Creates a new {@link HashMap} from a list of keys and values.  Duplicate keys are
	 * not allowed.
	 * <p>
	 * If you want to convert many arguments to a {@link List}, see
	 * {@link Arrays#asList(Object...)}.
	 * <p>
	 * The name of this method is set to prevent accidental method signature conflicts with
	 * {@link #asHashMap(Object...)} and {@link #asHashMap3(List, List)}.
	 * 
	 * @param keyAndValueList list of references: key, then value (repeat).
	 *        Keys or values may be {@code null}.  List may be empty.
	 * @return new HashMap reference with key-value pairs inserted
	 * @throws NullPointerException if {@code keyAndValueList} is null
	 * @throws IllegalArgumentException if {@code keyAndValueList.size()} is not even,
	 *         or a key cannot be cast to type {@code TKey},
	 *         or a value cannot be cast to type {@code TValue},
	 *         or a duplicate key exists
	 * @see #asHashMap(Object...)
	 * @see #asHashMap3(List, List)
	 * @see #putKeysAndValues2(Map, List)
	 */
    public static <TKey, TValue> HashMap<TKey, TValue> asHashMap2(List<?> keyAndValueList) {
		_checkKeyAndValueList(keyAndValueList);
        final int capacity = keyAndValueList.size() / 2;
        HashMap<TKey, TValue> map = new HashMap<TKey, TValue>(capacity);
    	_putKeysAndValues2(map, keyAndValueList);
    	return map;
    }
    
	/**
     * Convenience method to call {@link #putKeysAndValues2(Map, List)}.
     * 
     * @see #putKeysAndValues3(Map, List, List)
     * @see #asHashMap(Object...)
	 */
	public static <TKey, TValue> Map<TKey, TValue> putKeysAndValues(
			Map<TKey, TValue> map, Object... keyAndValueArr) {
    	List<Object> x = Arrays.asList(keyAndValueArr);
    	putKeysAndValues2(map, x);
    	return map;
	}
	
	/**
	 * Adds key-value pairs to an existing {@link Map} reference.  Duplicate keys are not allowed.
	 * <p>
	 * If you want to convert many arguments to a {@link List}, see
	 * {@link Arrays#asList(Object...)}.
	 * <p>
	 * The name of this method is set to prevent accidental method signature conflicts with
	 * {@link #putKeysAndValues(Map, Object...)} and {@link #putKeysAndValues3(Map, List, List)}.
	 *
	 * @param map reference to add key-value pairs
	 * @param keyAndValueList list of references: key, then value (repeat).
	 *        Keys or values may be {@code null}.  List may be empty.
	 * @return input reference
	 * @throws NullPointerException if {@code map} or {@code keyAndValueList} is null
	 * @throws IllegalArgumentException if {@code keyAndValueList.size()} is not even,
	 *         or a key cannot be cast to type {@code TKey},
	 *         or a value cannot be cast to type {@code TValue},
	 *         or a duplicate key exists
	 * @see #putKeysAndValues(Map, Object...)
	 * @see #putKeysAndValues3(Map, List, List)
	 * @see #asHashMap2(List)
	 */
	public static <TKey, TValue> Map<TKey, TValue> putKeysAndValues2(
			Map<TKey, TValue> map, List<?> keyAndValueList) {
		ObjectArgs.checkNotNull(map, "map");
		_checkKeyAndValueList(keyAndValueList);
    	_putKeysAndValues2(map, keyAndValueList);
    	return map;
	}
	
	private static void _checkKeyAndValueList(List<?> keyAndValueList) {
		ObjectArgs.checkNotNull(keyAndValueList, "keyAndValueList");
        if (1 == (keyAndValueList.size() % 2)) {
        	String msg = String.format(
                "Key-Value list size is not even: %d", keyAndValueList.size());
            throw new IllegalArgumentException(msg);
        }
	}
	
	@SuppressWarnings("unchecked")
	private static <TKey, TValue> Map<TKey, TValue> _putKeysAndValues2(
			Map<TKey, TValue> map, List<?> keyAndValueList) {
		int keyCount = keyAndValueList.size() / 2;
		int mapSize = map.size();
		HashSet<TKey> keySet = (0 == mapSize ? null : new HashSet<TKey>(keyCount));
		int keyAndValueListSize = keyAndValueList.size();
        for (int i = 0; i < keyAndValueListSize; i += 2) {
            Object key = keyAndValueList.get(i);
            Object value = keyAndValueList.get(1 + i);
            TKey castKey = null;
            try {
                castKey = (TKey) key;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast key #%d (one-based) at index %d: [%s]",
                    1 + (i / 2), i, key);
                throw new IllegalArgumentException(msg, e);
            }
            TValue castValue = null;
            try {
                castValue = (TValue) value;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast value #%d (one-based) at index %d: [%s]",
                    1 + (i / 2), 1 + i, value);
                throw new IllegalArgumentException(msg, e);
            }
            if ((null == keySet && map.containsKey(castKey))
            		|| (null != keySet && keySet.contains(castKey))) {
            	String msg = String.format("Duplicate key #%d (one-based) at index %d: [%s]",
        			1 + (i / 2), i, key);
            	throw new IllegalArgumentException(msg);
            }
            if (null != keySet) {
            	keySet.add(castKey);
            }
            map.put(castKey, castValue);
        }
        return map;
	}
	
	/**
	 * Creates a new {@link HashMap} from two lists: keys and values.  Duplicate keys are
	 * not allowed.
	 * <p>
	 * If you want to convert many arguments to a {@link List}, see
	 * {@link Arrays#asList(Object...)}.
	 * <p>
	 * The name of this method is set to prevent accidental method signature conflicts with
	 * {@link #asHashMap(Object...)} and {@link #asHashMap2(List)}.
	 * 
	 * @param keyList list of references for keys.  Keys may be {@code null}.
	 *        List may be empty.
	 * @param valueList list of references for values.  Keys may be {@code null}.
	 *        List may be empty.
	 * @return new HashMap reference with key-value pairs inserted
	 * @throws NullPointerException if {@code keyList} or {@code valueList} is null
	 * @throws IllegalArgumentException if {@code keyList.size() != valueList.size()},
	 *         or a key cannot be cast to type {@code TKey},
	 *         or a value cannot be cast to type {@code TValue},
	 *         or a duplicate key exists
	 * @see #asHashMap(Object...)
	 * @see #asHashMap2(List)
	 * @see #putKeysAndValues3(Map, List, List)
	 */
    public static <TKey, TValue> HashMap<TKey, TValue> asHashMap3(
    		List<?> keyList, List<?> valueList) {
    	_checkKeyAndValueLists(keyList, valueList);
        final int capacity = keyList.size();
        HashMap<TKey, TValue> map = new HashMap<TKey, TValue>(capacity);
    	_putKeysAndValues3(map, keyList, valueList);
    	return map;
    }
	
    /**
	 * Adds key-value pairs to an existing {@link Map} reference.  Duplicate keys are not allowed.
	 * <p>
	 * If you want to convert many arguments to a {@link List}, see
	 * {@link Arrays#asList(Object...)}.
	 * <p>
	 * The name of this method is set to prevent accidental method signature conflicts with
	 * {@link #putKeysAndValues(Map, Object...)} and {@link #putKeysAndValues2(Map, List)}.
     * 
	 * @param map reference to add key-value pairs
	 * @param keyList list of references for keys.  Keys may be {@code null}.
	 *        List may be empty.
	 * @param valueList list of references for values.  Keys may be {@code null}.
	 *        List may be empty.
	 * @return input reference
	 * @throws NullPointerException if {@code map}, {@code keyList}, or {@code valueList} is null
	 * @throws IllegalArgumentException if {@code keyList.size() != valueList.size()},
	 *         or a key cannot be cast to type {@code TKey},
	 *         or a value cannot be cast to type {@code TValue},
	 *         or a duplicate key exists
	 * @see #putKeysAndValues(Map, Object...)
	 * @see #putKeysAndValues2(Map, List)
	 * @see #asHashMap3(List, List)
     */
	public static <TKey, TValue> Map<TKey, TValue> putKeysAndValues3(
			Map<TKey, TValue> map, List<?> keyList, List<?> valueList) {
    	_checkKeyAndValueLists(keyList, valueList);
    	_putKeysAndValues3(map, keyList, valueList);
    	return map;
	}
	
	private static void _checkKeyAndValueLists(List<?> keyList, List<?> valueList) {
		ObjectArgs.checkNotNull(keyList, "keyList");
		ObjectArgs.checkNotNull(valueList, "valueList");
		int keyListSize = keyList.size();
		int valueListSize = valueList.size();
		if (keyListSize != valueListSize) {
			throw new IllegalArgumentException(String.format(
				"Key list size [%d] != Value list size [%d]",
				keyListSize, valueListSize));
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <TKey, TValue> Map<TKey, TValue> _putKeysAndValues3(
			Map<TKey, TValue> map,
			List<?> keyList,
			List<?> valueList) {
		int keyListSize = keyList.size();
		int mapSize = map.size();
		HashSet<TKey> keySet = (0 == mapSize ? null : new HashSet<TKey>(keyListSize));
        for (int i = 0; i < keyListSize; ++i) {
            Object key = keyList.get(i);
            Object value = valueList.get(i);
            TKey castKey = null;
            try {
                castKey = (TKey) key;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast key at index %d: [%s]",
                    i, key);
                throw new IllegalArgumentException(msg, e);
            }
            TValue castValue = null;
            try {
                castValue = (TValue) value;
            }
            catch (Exception e) {
                String msg = String.format("Failed to cast value at index %d: [%s]",
                    i, value);
                throw new IllegalArgumentException(msg, e);
            }
            if ((null == keySet && map.containsKey(castKey))
            		|| (null != keySet && keySet.contains(castKey))) {
            	String msg = String.format("Duplicate key at index %d: [%s]", i, key);
            	throw new IllegalArgumentException(msg);
            }
            if (null != keySet) {
            	keySet.add(castKey);
            }
            map.put(castKey, castValue);
        }
        return map;
	}
}
