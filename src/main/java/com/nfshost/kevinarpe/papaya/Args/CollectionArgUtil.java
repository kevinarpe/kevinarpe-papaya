/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

import java.util.Collection;

public final class CollectionArgUtil {

	/**
	 * Tests if a collection reference is not null and its size within specified range.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param minSize minimum number of elements (inclusive).  Must be non-negative.
	 * @param maxSize maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minSize < 0},
	 *         or if {@code maxSize < 0},
	 *         or if {@code minSize > maxSize}, 
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckMinSize(Collection, int, String)
	 * @see #staticCheckMaxSize(Collection, int, String)
	 * @see #staticCheckExactSize(Collection, int, String)
	 */
	public static <T> void staticCheckSizeRange(
			Collection<T> ref, int minSize, int maxSize, String argName) {
		IntArgUtil.staticCheckNotNegative(minSize, "minSize");
		IntArgUtil.staticCheckNotNegative(maxSize, "maxSize");
		int size = (null == ref ? -1 : ref.size());
		ContainerArgUtil._staticCheckSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and not empty (size >= 1).
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if number of elements in {@code ref} is zero
	 * @see #staticCheckSizeRange(Collection, int, int, String)
	 * @see #staticCheckMinSize(Collection, int, String)
	 */
	public static <T> void staticCheckNotEmpty(Collection<T> ref, String argName) {
		int size = (null == ref ? -1 : ref.size());
		int minSize = 1;
		int maxSize = -1;
		ContainerArgUtil._staticCheckSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has a minimum size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param minSize minimum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckMinSize(
			Collection<T> ref, int minSize, String argName) {
		IntArgUtil.staticCheckNotNegative(minSize, "minSize");
		int size = (null == ref ? -1 : ref.size());
		int maxSize = -1;
		ContainerArgUtil._staticCheckSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has a maximum size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param maxSize maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code maxSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckMaxSize(
			Collection<T> ref, int maxSize, String argName) {
		IntArgUtil.staticCheckNotNegative(maxSize, "maxSize");
		int size = (null == ref ? -1 : ref.size());
		int minSize = -1;
		ContainerArgUtil._staticCheckSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has an exact size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param exactSize exact number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code exactSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckExactSize(
			Collection<T> ref, int exactSize, String argName) {
		IntArgUtil.staticCheckNotNegative(exactSize, "exactSize");
		int size = (null == ref ? -1 : ref.size());
		ContainerArgUtil._staticCheckSizeRange(ref, "Collection", size, exactSize, exactSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and an index is valid to access an element.
	 * 
	 * @param ref a collection reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.size()}
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckInsertIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckAccessIndex(
			Collection<T> ref, int index, String collectionArgName, String indexArgName) {
		int size = (null == ref ? -1 : ref.size());
		ContainerArgUtil._staticCheckAccessIndex(
			ref, "Collection", size, index, collectionArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if a collection reference is not null and an index is valid to insert an element.
	 * 
	 * @param ref a collection reference
	 * @param index index of element to insert.  Must be non-negative.
	 * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index > ref.size()}
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckAccessIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckInsertIndex(
			Collection<T> ref, int index, String collectionArgName, String indexArgName) {
		int size = (null == ref ? -1 : ref.size());
		ContainerArgUtil._staticCheckInsertIndex(
			ref, "Collection", size, index, collectionArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if a collection reference is not null and each element is not null.
	 * 
	 * @param ref a collection reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see ArrayArgUtil#staticCheckElementsNotNull(Object[], String)
	 * @throws NullPointerException if {@code ref} (or any element) or {@code argName} is null
	 */
	public static <T> void staticCheckElementsNotNull(
			Collection<T> ref, String argName) {
		ObjectArgUtil.staticCheckNotNull(ref, argName);
		int count = 0;
		for (T item: ref) {
			if (null == item) {
				throw new NullPointerException(String.format(
					"Collection argument '%s': Item at index %d is null", argName, count));
			}
			++count;
		}
	}
	
	/**
	 * Tests if a collection reference is not null, and an index and count is valid to
	 * access elements.
	 * 
	 * @param ref a collection reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param count number of elements to access, starting from {@code index}.
	 *              Must be non-negative.
	 * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
	 * @param countArgName argument name for {@code count}, e.g., "strListCount"
	 */
	public static <T> void staticCheckIndexAndCount(
			Collection<T> ref,
			int index,
			int count,
			String collectionArgName,
			String indexArgName,
			String countArgName) {
		int size = (null == ref ? -1 : ref.size());
		ContainerArgUtil._staticCheckIndexAndCount(
			ref, "Collection", size, index, count, collectionArgName, indexArgName, countArgName);
	}
}
