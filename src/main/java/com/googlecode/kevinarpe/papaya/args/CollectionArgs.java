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
import java.util.List;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;

/**
 * Static methods to check {@link Collection} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class CollectionArgs {

    // Disable default constructor
    private CollectionArgs() {
    }

    /**
     * Tests if a {@link Collection} reference is not null and its size within specified range.
     * Size is defined as the number of elements.
     * 
     * @param ref
     *        a collection reference
     * @param minSize
     *        minimum number of elements (inclusive).  Must be non-negative.
     * @param maxSize
     *        maximum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
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
     * @see #checkMinSize(Collection, int, String)
     * @see #checkMaxSize(Collection, int, String)
     * @see #checkExactSize(Collection, int, String)
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkSizeRange(TCollection ref, int minSize, int maxSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkSizeRange(ref, "Collection", size, minSize, maxSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and not empty (size >= 1).
     * Size is defined as the number of elements.
     * 
     * @param ref
     *        a collection reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code ref} is zero
     *
     * @see #checkSizeRange(Collection, int, int, String)
     * @see #checkMinSize(Collection, int, String)
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkNotEmpty(TCollection ref, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkNotEmpty(ref, "Collection", size, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has a minimum size.
     * Size is defined as the number of elements.
     * 
     * @param ref
     *        a collection reference
     * @param minSize
     *        minimum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkSizeRange(Collection, int, int, String)
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkMinSize(TCollection ref, int minSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkMinSize(ref, "Collection", size, minSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has a maximum size.
     * Size is defined as the number of elements.
     * 
     * @param ref
     *        a collection reference
     * @param maxSize
     *        maximum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code maxSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkSizeRange(Collection, int, int, String)
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkMaxSize(TCollection ref, int maxSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkMaxSize(ref, "Collection", size, maxSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and has an exact size.
     * Size is defined as the number of elements.
     * 
     * @param ref
     *        a collection reference
     * @param exactSize
     *        exact number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code exactSize < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkSizeRange(Collection, int, int, String)
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkExactSize(TCollection ref, int exactSize, String argName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkExactSize(ref, "Collection", size, exactSize, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and each element is not null.  An empty
     * collection will pass this test.
     * 
     * @param ref
     *        a collection reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated collection reference
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see ArrayArgs#checkElementsNotNull(Object[], String)
     *
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkElementsNotNull(TCollection ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        int count = 0;
        for (TValue item: ref) {
            if (null == item) {
                String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new NullPointerException(String.format(
                    "Collection argument '%s': Item at index %d is null%s", argName, count, w));
            }
            ++count;
        }
        return ref;
    }
    
    /**
     * This is a convenience method for {@link #checkNotEmpty(Collection, String)}
     * and {@link #checkElementsNotNull(Collection, String)}.
     */
    @FullyTested
    public static <TValue, TCollection extends Collection<TValue>>
    TCollection checkNotEmptyAndElementsNotNull(TCollection ref, String argName) {
        checkNotEmpty(ref, argName);
        checkElementsNotNull(ref, argName);
        return ref;
    }
    
    /**
     * Tests if a {@link Collection} reference is not null and an index is valid to access
     * an element.
     * 
     * @param ref
     *        a collection reference
     * @param index
     *        index of element to access.  Must be non-negative.
     * @param collectionArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName
     *        argument name for {@code index}, e.g., "strListIndex"
     *
     * @return the validated index
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code index >= ref.size()}</li>
     * </ul>
     *
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
     * @param ref
     *        a collection reference
     * @param index
     *        index of element to insert.  Must be non-negative.
     * @param collectionArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName
     *        argument name for {@code index}, e.g., "strListIndex"
     *
     * @return the validated index
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code index > ref.size()}</li>
     * </ul>
     *
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
     * Tests if a {@link Collection} reference is not null, and an index and count are valid to
     * access elements.
     * 
     * @param ref
     *        a collection reference
     * @param index
     *        index of element to access.  Must be non-negative.
     * @param count
     *        number of elements to access, starting from {@code index}.
     *              Must be non-negative.
     * @param collectionArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName
     *        argument name for {@code index}, e.g., "strListIndex"
     * @param countArgName
     *        argument name for {@code count}, e.g., "strListCount"
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code count < 0}</li>
     * </ul>
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index >= ref.size()}</li>
     *   <li>if {@code index + count > ref.size()}</li>
     * </ul>
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
    
    /**
     * Tests if a {@link Collection} reference is not null, and 'from' and 'to' indices are valid
     * to access elements.
     * <p>
     * This test method was written for index pairs used by {@link List#subList(int, int)}.
     * 
     * @param ref
     *        a collection reference
     * @param fromIndex
     *        first index of element to access.  Must be non-negative.
     * @param toIndex
     *        one greater than last index of element to access.  Must be non-negative.
     * @param collectionArgName
     *        argument name for {@code ref}, e.g., "strList"
     * @param fromIndexArgName
     *        argument name for {@code fromIndex}, e.g., "strListFromIndex"
     * @param toIndexArgName
     *        argument name for {@code toIndex}, e.g., "strListToIndex"
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code fromIndex < 0}</li>
     *   <li>if {@code toIndex < 0}</li>
     *   <li>if {@code fromIndex >= ref.size()}</li>
     *   <li>if {@code toIndex > ref.size()}</li>
     * </ul>
     */
    @FullyTested
    public static <T> void checkFromAndToIndices(
            Collection<T> ref,
            int fromIndex,
            int toIndex,
            String collectionArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int size = (null == ref ? -1 : ref.size());
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Collection",
            size,
            fromIndex,
            toIndex,
            collectionArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * Tests if a value is found in a collection of valid values.
     * 
     * @param ref
     *        a collection reference of valid values
     * @param value
     *        reference to test if found in collection of valid values
     * @param collectionArgName
     *        argument name for {@code ref}, e.g., "strList"
     *
     * @return validated value
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code ref} is empty</li>
     *   <li>if {@code ref} does not contain {@code value}</li>
     * </ul>
     * 
     * @see ArrayArgs#checkContains(Object[], Object, String)
     */
    @FullyTested
    public static <THaystack, TNeedle extends THaystack>
    TNeedle checkContains(Collection<THaystack> ref, TNeedle value, String collectionArgName) {
        ContainerArgs._checkContains(ref, "Collection", value, collectionArgName);
        return value;
    }
}
