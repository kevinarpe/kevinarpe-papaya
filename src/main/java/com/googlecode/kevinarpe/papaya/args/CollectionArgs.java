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

import java.util.Collection;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;

/**
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class CollectionArgs {

	// Disable default constructor
	private CollectionArgs() {
	}

    /**
     * Tests if a {@link Collection} reference is not null and its size within specified range.
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
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinSize(Collection, int, String)
     * @see #checkMaxSize(Collection, int, String)
     * @see #checkExactSize(Collection, int, String)
     */
	@FullyTested
    public static <T> void checkSizeRange(
            Collection<T> ref, int minSize, int maxSize, String argName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        IntArgs.checkNotNegative(maxSize, "maxSize");
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Collection", size, minSize, maxSize, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and not empty (size >= 1).
     * Size is defined as the number of elements.
     * 
     * @param ref a collection reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if number of elements in {@code ref} is zero,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Collection, int, int, String)
     * @see #checkMinSize(Collection, int, String)
     */
	@FullyTested
    public static <T> void checkNotEmpty(Collection<T> ref, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkNotEmpty(ref, "Collection", size, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has a minimum size.
     * Size is defined as the number of elements.
     * 
     * @param ref a collection reference
     * @param minSize minimum number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code minSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Collection, int, int, String)
     */
	@FullyTested
    public static <T> void checkMinSize(
            Collection<T> ref, int minSize, String argName) {
        IntArgs.checkNotNegative(minSize, "minSize");
        int size = (null == ref ? -1 : ref.size());
        int maxSize = -1;
        ContainerArgs._checkSizeRange(ref, "Collection", size, minSize, maxSize, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has a maximum size.
     * Size is defined as the number of elements.
     * 
     * @param ref a collection reference
     * @param maxSize maximum number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code maxSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Collection, int, int, String)
     */
	@FullyTested
    public static <T> void checkMaxSize(
            Collection<T> ref, int maxSize, String argName) {
        IntArgs.checkNotNegative(maxSize, "maxSize");
        int size = (null == ref ? -1 : ref.size());
        int minSize = -1;
        ContainerArgs._checkSizeRange(ref, "Collection", size, minSize, maxSize, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has an exact size.
     * Size is defined as the number of elements.
     * 
     * @param ref a collection reference
     * @param exactSize exact number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code exactSize < 0},
     *         or if number of elements in {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkSizeRange(Collection, int, int, String)
     */
	@FullyTested
    public static <T> void checkExactSize(
            Collection<T> ref, int exactSize, String argName) {
        IntArgs.checkNotNegative(exactSize, "exactSize");
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Collection", size, exactSize, exactSize, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and each element is not null.  An empty
     * collection will pass this test.
     * 
     * @param ref a collection reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see ArrayArgs#checkElementsNotNull(Object[], String)
     * @throws NullPointerException if {@code ref} (or any element) or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     */
	@FullyTested
    public static <T> void checkElementsNotNull(
            Collection<T> ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        int count = 0;
        for (T item: ref) {
            if (null == item) {
                StringArgs._checkArgNameValid(argName, "argName");
                throw new NullPointerException(String.format(
                    "Collection argument '%s': Item at index %d is null", argName, count));
            }
            ++count;
        }
    }
    
    /**
     * This is a convenience method for {@link #checkNotEmpty(Collection, String)}
     * and {@link #checkElementsNotNull(Collection, String)}.
     */
	@FullyTested
    public static <T> void checkNotEmptyAndElementsNotNull(Collection<T> ref, String argName) {
    	checkNotEmpty(ref, argName);
    	checkElementsNotNull(ref, argName);
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and an index is valid to access
     * an element.
     * 
     * @param ref a collection reference
     * @param index index of element to access.  Must be non-negative.
     * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.size()}
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkInsertIndex(Collection, int, String, String)
     */
	@FullyTested
    public static <T> int checkAccessIndex(
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkAccessIndex(
            ref, "Collection", size, index, collectionArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and an index is valid to insert
     * an element.
     * 
     * @param ref a collection reference
     * @param index index of element to insert.  Must be non-negative.
     * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index > ref.size()}
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkAccessIndex(Collection, int, String, String)
     */
	@FullyTested
    public static <T> int checkInsertIndex(
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkInsertIndex(
            ref, "Collection", size, index, collectionArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null, and an index and count is valid to
     * access elements.
     * 
     * @param ref a collection reference
     * @param index index of element to access.  Must be non-negative.
     * @param count number of elements to access, starting from {@code index}.
     *              Must be non-negative.
     * @param collectionArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @param countArgName argument name for {@code count}, e.g., "strListCount"
     * @throws NullPointerException if {@code ref}, {@code collectionArgName},
     *         {@code indexArgName}, or {@code countArgName} is null
     * @throws IllegalArgumentException if {@code index < 0},
     *         or if {@code count < 0},
     *         or if {@code argName} is empty or whitespace
     * @throws IndexOutOfBoundsException if {@code index >= ref.size()},
     *         or if {@code index + count > ref.size()}
     */
	@FullyTested
    public static <T> void checkIndexAndCount(
            Collection<T> ref,
            int index,
            int count,
            String collectionArgName,
            String indexArgName,
            String countArgName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkIndexAndCount(
            ref, "Collection", size, index, count, collectionArgName, indexArgName, countArgName);
    }
}
