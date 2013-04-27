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

import java.lang.reflect.Array;

import com.googlecode.kevinarpe.papaya.Args.ArrayArgs;
import com.googlecode.kevinarpe.papaya.Args.IntArgs;
import com.googlecode.kevinarpe.papaya.Args.ObjectArgs;

public final class ArrayUtil {
    
    // TODO: Iterable over one or more arrays
    
    // @see Arrays#equals(Object[], Object[])
    /*
    public static <T> boolean equals(T[] arr1, T[] arr2) {
        if (null == arr1 && null == arr2) {
            return true;
        }
        if ((null != arr1 && null == arr2) ||
                (null == arr1 && null != arr2) ||
                arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; ++i) {
            T x1 = arr1[i];
            T x2 = arr2[i];
            if (!Objects.equal(x1, x2)) {
                return false;
            }
        }
        return true;
    }
    */
    
    /**
     * This is a convenience method for {@link #remove(Object[], int, int)}
     * where {@code count = 1}.
     */
    public static <T> T[] remove(T[] arr, int index) {
        int count = 1;
        T[] newArr = remove(arr, index, count);
        return newArr;
    }
    
    /**
     * Removes elements from an array by creating a new array and copying remaining elements from
     * the source array.  To be precise, using the term "remove" is a misnomer, as Java arrays
     * cannot change size.
     * <p>
     * If {@code count == 0}, the array is fully copied and returned.
     * 
     * @param arr an array reference
     * @param index index to begin removing elements.  Range: 0 to {@code arr.length - 1}
     * @param count number of elements to remove.  Must be non-negative.
     * @return reference to newly allocated array
     * @throws NullPointerException if {@code arr} is null
     * @throws IllegalArgumentException if {@code index} and {@code count} are invalid
     */
    public static <T> T[] remove(T[] arr, int index, int count) {
        ArrayArgs.checkIndexAndCount(arr, index, count, "arr", "index", "count");
        
        int newLen = arr.length - count;
        T[] newArr = _newGenericArrayFromExisting(arr, newLen);
        
        int countBefore = index;
        int countAfter = arr.length - (index + count);
        
        if (0 != arr.length && 0 != countBefore) {
            System.arraycopy(
                arr,           // src
                0,             // srcPos
                newArr,        // dest
                0,             // destPos
                countBefore);  // count
        }
        if (0 != arr.length && 0 != countAfter) {
            System.arraycopy(
                arr,            // src
                index + count,  // srcPos
                newArr,         // dest
                countBefore,    // destPos
                countAfter);    // count
        }
        return newArr;
    }
    
    /**
     * Dynamically creates a type-safe and type-correct Java array.  Normally, it is non-trivial
     * to allocate generic arrays.
     * 
     * @param componentClass element type in new array
     * @param length number of elements in new array.  Must be non-negative.
     * @return reference to newly allocated array
     * @throws NullPointerException if {@code componentClass} is null
     * @throws IllegalArgumentException if {@code length} is negative
     */
    public static <T> T[] newGenericArray(Class<T> componentClass, int length) {
        ObjectArgs.checkNotNull(componentClass, "componentClass");
        IntArgs.checkNotNegative(length, "length");
        
        @SuppressWarnings("unchecked")
        T[] newArr = (T[]) Array.newInstance(componentClass, length);
        return newArr;
    }
    
    private static <T> T[] _newGenericArrayFromExisting(T[] arr, int length) {
        @SuppressWarnings("unchecked")
        Class<T[]> arrClass = (Class<T[]>) arr.getClass();
        @SuppressWarnings("unchecked")
        Class<T> arrComponentClass = (Class<T>) arrClass.getComponentType();
        T[] newArr = newGenericArray(arrComponentClass, length);
        return newArr;
    }
    
    /**
     * This is a convenience method for {@link #insert(Object[], int, Object, Object...)}.
     */
    //Java 7: @SafeVarargs
    public static <T> T[] append(T[] arr, T newItem, T... newItemArr) {
        int index = (null == arr ? -1 : arr.length);
        T[] newArr = insert(arr, index, newItem, newItemArr);
        return newArr;
    }
    
    /**
     * Creates a new array, copies all elements from source array, then adds new items.
     * To be precise, using the term "insert" is a misnomer, as Java array's cannot change size.
     * 
     * @param arr an array reference
     * @param index index to insert new items.  Range: 0 to {@code arr.length}
     * @param newItem first new item to insert; null is OK
     * @param newItemArr optional list of additional items to insert; nulls are OK
     * @return reference to newly allocated array
     * @throws NullPointerException if {@code arr} is null
     * @throws IllegalArgumentException if {@code index} is invalid
     */
    //Java 7: @SafeVarargs
    public static <T> T[] insert(T[] arr, int index, T newItem, T... newItemArr) {
        ArrayArgs.checkInsertIndex(arr, index, "arr", "index");
        
        int newLen = arr.length + 1 + newItemArr.length;
        T[] newArr = _newGenericArrayFromExisting(arr, newLen);
        
        int countBeforeInsert = index;
        int countAfterInsert = arr.length - index;
        
        if (0 != arr.length && 0 != countBeforeInsert) {
            System.arraycopy(
                arr,                 // src
                0,                   // srcPos
                newArr,              // dest
                0,                   // destPos
                countBeforeInsert);  // count
        }
        newArr[countBeforeInsert] = newItem;
        if (0 != newItemArr.length) {
            System.arraycopy(
                newItemArr,             // src
                0,                      // srcPos
                newArr,                 // dest
                1 + countBeforeInsert,  // destPos
                newItemArr.length);     // count
        }
        if (0 != arr.length && 0 != countAfterInsert) {
            System.arraycopy(
                arr,                                        // src
                countBeforeInsert,                          // srcPos
                newArr,                                     // dest
                1 + countBeforeInsert + newItemArr.length,  // destPos
                countAfterInsert);                          // count
        }
        return newArr;
    }
}
