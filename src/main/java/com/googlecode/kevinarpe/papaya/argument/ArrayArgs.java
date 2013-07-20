package com.googlecode.kevinarpe.papaya.argument;

import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Chars;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

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

/**
 * Static methods to check array arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ArrayArgs {

    // Disable default constructor
    private ArrayArgs() {
    }

    /**
     * Tests if an array reference is not null and its length within specified range.
     * Length is defined as the number of elements.
     * 
     * @param ref
     *        an array reference
     * @param minLen
     *        minimum number of elements (inclusive).  Must be non-negative.
     * @param maxLen
     *        maximum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minLen < 0}</li>
     *   <li>if {@code maxLen < 0}</li>
     *   <li>if {@code minLen > maxLen}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinLength(Object[], int, String)
     * @see #checkMaxLength(Object[], int, String)
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static <T> T[] checkLengthRange(T[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static byte[] checkLengthRange(byte[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static short[] checkLengthRange(short[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static int[] checkLengthRange(int[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static long[] checkLengthRange(long[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static float[] checkLengthRange(float[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static double[] checkLengthRange(double[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static char[] checkLengthRange(char[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static boolean[] checkLengthRange(
            boolean[] ref, int minLen, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkSizeRange(ref, "Array", len, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and not empty (size >= 1).
     * Length is defined as the number of elements.
     * 
     * @param ref
     *        an array reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code ref} is zero
     *
     * @see #checkLengthRange(Object[], int, int, String)
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static <T> T[] checkNotEmpty(T[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static byte[] checkNotEmpty(byte[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static short[] checkNotEmpty(short[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static int[] checkNotEmpty(int[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static long[] checkNotEmpty(long[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static float[] checkNotEmpty(float[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static double[] checkNotEmpty(double[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static char[] checkNotEmpty(char[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * @see #checkNotEmpty(Object[], String)
     */
    @FullyTested
    public static boolean[] checkNotEmpty(boolean[] ref, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkNotEmpty(ref, "Array", len, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has a minimum length.
     * Length is defined as the number of elements.
     * 
     * @param ref
     *        an array reference
     * @param minLen
     *        minimum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minLen < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static <T> T[] checkMinLength(T[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static byte[] checkMinLength(byte[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static short[] checkMinLength(short[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static int[] checkMinLength(int[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static long[] checkMinLength(long[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static float[] checkMinLength(float[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static double[] checkMinLength(double[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static char[] checkMinLength(char[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMinLength(Object[], int, String)
     */
    @FullyTested
    public static boolean[] checkMinLength(boolean[] ref, int minLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMinSize(ref, "Array", len, minLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has a maximum length.
     * Length is defined as the number of elements.
     * 
     * @param ref
     *        an array reference
     * @param maxLen
     *        maximum number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code maxLen < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static <T> T[] checkMaxLength(T[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static byte[] checkMaxLength(byte[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static short[] checkMaxLength(short[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static int[] checkMaxLength(int[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static long[] checkMaxLength(long[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static float[] checkMaxLength(float[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static double[] checkMaxLength(double[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static char[] checkMaxLength(char[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * @see #checkMaxLength(Object[], int, String)
     */
    @FullyTested
    public static boolean[] checkMaxLength(boolean[] ref, int maxLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkMaxSize(ref, "Array", len, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and has an exact length.
     * Length is defined as the number of elements.
     * 
     * @param ref
     *        an array reference
     * @param exactLen
     *        exact number of elements (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code exactLen < 0}</li>
     *   <li>if number of elements in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(Object[], int, int, String)
     */
    @FullyTested
    public static <T> T[] checkExactLength(T[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static byte[] checkExactLength(byte[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static short[] checkExactLength(short[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static int[] checkExactLength(int[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static long[] checkExactLength(long[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static float[] checkExactLength(float[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static double[] checkExactLength(double[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static char[] checkExactLength(char[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * @see #checkExactLength(Object[], int, String)
     */
    @FullyTested
    public static boolean[] checkExactLength(boolean[] ref, int exactLen, String argName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkExactSize(ref, "Array", len, exactLen, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and each element is not null.
     * 
     * @param ref
     *        an array reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated array reference
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see CollectionArgs#checkElementsNotNull(java.util.Collection, String)
     *
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     */
    @FullyTested
    public static <T> T[] checkElementsNotNull(T[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        for (int i = 0; i < ref.length; ++i) {
            T item = ref[i];
            if (null == item) {
                String w = StringArgs._getArgNameWarning(argName, "argName");
                throw new NullPointerException(String.format(
                    "Array argument '%s': Item at index %d is null%s", argName, i, w));
            }
        }
        return ref;
    }
    
    /**
     * This is a convenience method for {@link #checkNotEmpty(Object[], String)}
     * and {@link #checkElementsNotNull(Object[], String)}.
     * <p>
     * This method is useful to check variable argument lists (var args).
     */
    @FullyTested
    public static <T> T[] checkNotEmptyAndElementsNotNull(T[] ref, String argName) {
        checkNotEmpty(ref, argName);
        checkElementsNotNull(ref, argName);
        return ref;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to access an element.
     * 
     * @param ref
     *        an array reference
     * @param index
     *        index of element to access.  Must be non-negative.
     * @param arrArgName
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
     *   <li>{@code index >= ref.length}</li>
     * </ul>
     *
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkAccessIndex(
            T[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            byte[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            short[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            int[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            long[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            float[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            double[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            char[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }

    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static int checkAccessIndex(
            boolean[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkAccessIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null and an index is valid to insert an element.
     * 
     * @param ref
     *        an array reference
     * @param index
     *        index of element to insert.  Must be non-negative.
     * @param arrArgName
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
     *   <li>if {@code index > ref.length}</li>
     * </ul>
     *
     * @see #checkAccessIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            T[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            byte[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            short[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            int[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            long[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            float[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            double[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            char[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * @see #checkInsertIndex(Object[], int, String, String)
     */
    @FullyTested
    public static <T> int checkInsertIndex(
            boolean[] ref, int index, String arrArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkInsertIndex(ref, "Array", len, index, arrArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an array reference is not null, and an index and count is valid to access elements.
     * 
     * @param ref
     *        an array reference
     * @param index
     *        index of element to access.  Must be non-negative.
     * @param count
     *        number of elements to access, starting from {@code index}.  Must be non-negative.
     * @param arrArgName
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
     *   <li>if {@code ref} is empty</li>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code count < 0}</li>
     * </ul>
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index >= ref.length}</li>
     *   <li>if {@code index + count > ref.length}</li>
     * </ul>
     */
    @FullyTested
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
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            byte[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            short[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            int[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            long[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            float[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            double[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            char[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * @see #checkIndexAndCount(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkIndexAndCount(
            boolean[] ref,
            int index,
            int count,
            String arrArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkIndexAndCount(
            ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
    }
    
    /**
     * Tests if an array reference is not null, and 'from' and 'to' indices are valid
     * to access elements.
     * <p>
     * This test method was written for index pairs used by {@link List#subList(int, int)}.
     * 
     * @param ref
     *        an array reference
     * @param fromIndex
     *        first index of element to access.  Must be non-negative.
     * @param toIndex
     *        one greater than last index of element to access.  Must be non-negative.
     * @param arrayArgName
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
     *   <li>if {@code fromIndex >= ref.length}</li>
     *   <li>if {@code toIndex > ref.length}</li>
     * </ul>
     */
    @FullyTested
    public static <T> void checkFromAndToIndices(
            T[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            byte[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            short[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            int[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            long[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            float[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            double[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            char[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * @see #checkFromAndToIndices(Object[], int, int, String, String, String)
     */
    @FullyTested
    public static void checkFromAndToIndices(
            boolean[] ref,
            int fromIndex,
            int toIndex,
            String arrayArgName,
            String fromIndexArgName,
            String toIndexArgName) {
        int len = (null == ref ? -1 : ref.length);
        ContainerArgs._checkFromAndToIndices(
            ref,
            "Array",
            len,
            fromIndex,
            toIndex,
            arrayArgName,
            fromIndexArgName,
            toIndexArgName);
    }
    
    /**
     * Tests if a value is found in a array of valid values.
     * 
     * @param ref
     * <ul>
     *   <li>An array reference of valid values</li>
     *   <li>Must not be empty.</li>
     *   <li>May contain {@code null} values or duplicate values</li>
     * </ul>
     * @param value
     *        reference to test if found in array of valid values.  May be {@code null}.
     * @param arrayArgName
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
     * @see CollectionArgs#checkContains(java.util.Collection, Object, String)
     */
    @FullyTested
    public static
    <THaystack, TNeedle extends THaystack>
    TNeedle checkContains(THaystack[] ref, TNeedle value, String arrayArgName) {
        List<THaystack> list = (null == ref ? null : Arrays.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static byte checkContains(byte[] ref, byte value, String arrayArgName) {
        List<Byte> list = (null == ref ? null : Bytes.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static short checkContains(short[] ref, short value, String arrayArgName) {
        List<Short> list = (null == ref ? null : Shorts.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static int checkContains(int[] ref, int value, String arrayArgName) {
        List<Integer> list = (null == ref ? null : Ints.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static long checkContains(long[] ref, long value, String arrayArgName) {
        List<Long> list = (null == ref ? null : Longs.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static float checkContains(float[] ref, float value, String arrayArgName) {
        List<Float> list = (null == ref ? null : Floats.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static double checkContains(double[] ref, double value, String arrayArgName) {
        List<Double> list = (null == ref ? null : Doubles.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static char checkContains(char[] ref, char value, String arrayArgName) {
        List<Character> list = (null == ref ? null : Chars.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
    
    /**
     * @see #checkContains(Object[], Object, String)
     */
    @FullyTested
    public static boolean checkContains(boolean[] ref, boolean value, String arrayArgName) {
        List<Boolean> list = (null == ref ? null : Booleans.asList(ref));
        ContainerArgs._checkContains(list, "Array", value, arrayArgName);
        return value;
    }
}
