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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ArrayArgs {

    /**
     * Tests if an array reference is not null and its length within specified range.
     * Length is defined as the number of elements.
     * 
     * @param ref an array reference
     * @param minLen minimum number of elements (inclusive).  Must be non-negative.
     * @param maxLen maximum number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code minLen < 0},
     *         or if {@code maxLen < 0},
     *         or if {@code minLen > maxLen}, 
     *         or if number of elements in {@code ref} is outside allowed range
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinLength(Object[], int, String)
     * @see #checkMaxLength(Object[], int, String)
     * @see #checkExactLength(Object[], int, String)
     */
    public static <T> T[] checkLengthRange(
            T[] ref, int minLen, int maxLen, String argName) {
        IntArgs.checkNotNegative(minLen, "minLen");
        IntArgs.checkNotNegative(maxLen, "maxLen");
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and not empty (size >= 1).
     * Length is defined as the number of elements.
     * 
     * @param ref an array reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if number of elements in {@code ref} is zero
     * @see #checkLengthRange(Object[], int, int, String)
     * @see #checkMinLength(Object[], int, String)
     */
    public static <T> T[] checkNotEmpty(T[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        int minLen = 1;
        int maxLen = -1;
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has a minimum length.
     * Length is defined as the number of elements.
     * 
     * @param ref an array reference
     * @param minLen minimum number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code minLen < 0},
     *         or if number of elements in {@code ref} is outside allowed range
     * @see #checkLengthRange(Object[], int, int, String)
     */
    public static <T> T[] checkMinLength(T[] ref, int minLen, String argName) {
        IntArgs.checkNotNegative(minLen, "minLen");
        int len = (null == ref ? -1 : ref.length);
        int maxLen = -1;
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has a maximum length.
     * Length is defined as the number of elements.
     * 
     * @param ref an array reference
     * @param maxLen maximum number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code maxLen < 0},
     *         or if number of elements in {@code ref} is outside allowed range
     * @see #checkLengthRange(Object[], int, int, String)
     */
    public static <T> T[] checkMaxLength(T[] ref, int maxLen, String argName) {
        IntArgs.checkNotNegative(maxLen, "maxLen");
        int len = (null == ref ? -1 : ref.length);
        int minLen = -1;
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has an exact length.
     * Length is defined as the number of elements.
     * 
     * @param ref an array reference
     * @param exactLen exact number of elements (inclusive).  Must be non-negative.
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @throws NullPointerException if {@code ref} or {@code argName} is null
     * @throws IllegalArgumentException if {@code exactLen < 0},
     *         or if number of elements in {@code ref} is outside allowed range
     * @see #checkLengthRange(Object[], int, int, String)
     */
    public static <T> T[] checkExactLength(T[] ref, int exactLen, String argName) {
        IntArgs.checkNotNegative(exactLen, "exactLen");
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, exactLen, exactLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code arrArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    public static <T> int checkAccessIndex(
            T[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to insert an element.
     * 
     * @param ref an array reference
     * @param index index of element to insert.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code arrArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static <T> int checkInsertIndex(
            T[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            byte[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            short[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            int[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            long[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            float[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            double[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            char[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @return the validated index
     * @throws NullPointerException if {@code ref}, {@code listArgName},
     *         or {@code indexArgName} is null
     * @throws IndexOutOfBoundsException if {@code index < 0},
     *         or {@code index >= ref.length}
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    public static int checkAccessIndex(
            boolean[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(
            ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and each element is not null.
     * 
     * @param ref an array reference
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see CollectionArgs#checkElementsNotNull(java.util.Collection, String)
     * @throws NullPointerException if {@code ref} (or any element) or {@code argName} is null
     */
    public static <T> void checkElementsNotNull(T[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (int i = 0; i < ref.length; ++i) {
            T item = ref[i];
            if (null == item) {
                throw new NullPointerException(String.format(
                    "Array argument '%s': Item at index %d is null", argName, i));
            }
        }
    }
    
    /**
     * Tests if an array reference is not null, and an index and count is valid to access elements.
     * 
     * @param ref an array reference
     * @param index index of element to access.  Must be non-negative.
     * @param count number of elements to access, starting from {@code index}.
     *              Must be non-negative.
     * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName argument name for {@code index}, e.g., "strListIndex"
     * @param countArgName argument name for {@code count}, e.g., "strListCount"
     * @throws NullPointerException if {@code ref}, {@code arrArgName}, {@code indexArgName},
     *         or {@code countArgName} is null
     * @throws IllegalArgumentException if {@code index < 0},
     *         or if {@code count < 0}
     * @throws IndexOutOfBoundsException if {@code index >= ref.length},
     *         or if {@code index + count > ref.length}
     */
    public static <T> void checkIndexAndCount(
            T[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
}
