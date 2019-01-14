package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ValueAsTypeIterator._IValueAsLongIterator;

/**
 * Static methods to check {@code int[]} and {@code Integer[]} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class IntArrayArgs {

    // Disable default constructor
    private IntArrayArgs() {
    }

    /**
     * Tests if all values in an array are positive: greater than zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not positive
     * 
     * @see #checkPositive(Integer[], String)
     * @see IntIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static int[] checkPositive(int[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkPositive(iter, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an array are positive: greater than zero.
     * 
     * @param ref
     *        array of values to test.  May be empty.
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not positive
     * 
     * @see #checkPositive(int[], String)
     * @see IntIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static Integer[] checkPositive(Integer[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkPositive(iter, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an array are not positive: less than or equal to zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value is positive
     * 
     * @see #checkNotPositive(Integer[], String)
     * @see IntIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static int[] checkNotPositive(int[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotPositive(iter, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an array are not positive: less than or equal to zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is positive
     * 
     * @see #checkNotPositive(int[], String)
     * @see IntIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static Integer[] checkNotPositive(Integer[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotPositive(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are negative: less than zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not negative
     * 
     * @see #checkNegative(Integer[], String)
     * @see IntIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static int[] checkNegative(int[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are negative: less than zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not negative
     * 
     * @see #checkNegative(int[], String)
     * @see IntIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static Integer[] checkNegative(Integer[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are not negative: greater than or equal to zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value is negative
     * 
     * @see #checkNotNegative(Integer[], String)
     * @see IntIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static int[] checkNotNegative(int[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are not negative: greater than or equal to zero.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is negative
     * 
     * @see #checkNotNegative(int[], String)
     * @see IntIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static Integer[] checkNotNegative(Integer[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are within specified range.
     * <p>
     * Example: All values must be {@code >= 3 and <= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 3, 7, "numbersArray");}
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxRangeValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is outside allowed range</li>
     * </ul>
     * 
     * @see #checkValueInsideRange(Integer[], int, int, String)
     * @see IntIterableArgs#checkValueInsideRange(Iterable, int, int, String)
     */
    @FullyTested
    public static int[] checkValueInsideRange(
            int[] ref, int minRangeValue, int maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an array are within specified range.
     * <p>
     * Example: All values must be {@code >= 3 and <= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 3, 7, "numbersArray");}
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxRangeValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is outside allowed range</li>
     * </ul>
     * 
     * @see #checkValueInsideRange(int[], int, int, String)
     * @see IntIterableArgs#checkValueInsideRange(Iterable, int, int, String)
     */
    @FullyTested
    public static Integer[] checkValueInsideRange(
            Integer[] ref, int minRangeValue, int maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are <b>not</b> within specified range.
     * <p>
     * Example: All values must be {@code <= 3 and >= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 4, 6, "numbersArray");}
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for exclusive range of each value in array to test 
     * @param maxRangeValue
     *        maximum value (inclusive) for exclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is inside not-allowed range</li>
     * </ul>
     * 
     * @see #checkValueOutsideRange(Integer[], int, int, String)
     * @see IntIterableArgs#checkValueOutsideRange(Iterable, int, int, String)
     */
    @FullyTested
    public static int[] checkValueOutsideRange(
            int[] ref, int minRangeValue, int maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are <b>not</b> within specified range.
     * <p>
     * Example: All values must be {@code <= 3 and >= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 4, 6, "numbersArray");}
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for exclusive range of each value in array to test 
     * @param maxRangeValue
     *        maximum value (inclusive) for exclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is inside not-allowed range</li>
     * </ul>
     * 
     * @see #checkValueOutsideRange(int[], int, int, String)
     * @see IntIterableArgs#checkValueOutsideRange(Iterable, int, int, String)
     */
    @FullyTested
    public static Integer[] checkValueOutsideRange(
            Integer[] ref, int minRangeValue, int maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have a minimum value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is less than {@code minValue}
     * 
     * @see #checkMinValue(Integer[], int, String)
     * @see IntIterableArgs#checkMinValue(Iterable, int, String)
     */
    @FullyTested
    public static int[] checkMinValue(int[] ref, int minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkMinValue(iter, minValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have a minimum value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is less than {@code minValue}
     * 
     * @see #checkMinValue(int[], int, String)
     * @see IntIterableArgs#checkMinValue(Iterable, int, String)
     */
    @FullyTested
    public static Integer[] checkMinValue(Integer[] ref, int minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkMinValue(iter, minValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have a maximum value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is greater than {@code maxValue}
     * 
     * @see #checkMaxValue(Integer[], int, String)
     * @see IntIterableArgs#checkMaxValue(Iterable, int, String)
     */
    @FullyTested
    public static int[] checkMaxValue(int[] ref, int maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have a maximum value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is greater than {@code maxValue}
     * 
     * @see #checkMaxValue(int[], int, String)
     * @see IntIterableArgs#checkMaxValue(Iterable, int, String)
     */
    @FullyTested
    public static Integer[] checkMaxValue(Integer[] ref, int maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have an exact value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is not {@code exactValue}
     * 
     * @see #checkExactValue(Integer[], int, String)
     * @see IntIterableArgs#checkExactValue(Iterable, int, String)
     */
    @FullyTested
    public static int[] checkExactValue(int[] ref, int exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array have an exact value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is not {@code exactValue}
     * 
     * @see #checkExactValue(int[], int, String)
     * @see IntIterableArgs#checkExactValue(Iterable, int, String)
     */
    @FullyTested
    public static Integer[] checkExactValue(Integer[] ref, int exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array do not have an exact value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     * 
     * @see #checkNotExactValue(Integer[], int, String)
     * @see IntIterableArgs#checkNotExactValue(Iterable, int, String)
     */
    @FullyTested
    public static int[] checkNotExactValue(int[] ref, int exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveIntArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array do not have an exact value.
     * 
     * @param ref
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated array
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     * 
     * @see #checkNotExactValue(int[], int, String)
     * @see IntIterableArgs#checkNotExactValue(Iterable, int, String)
     */
    @FullyTested
    public static Integer[] checkNotExactValue(Integer[] ref, int exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedIntObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
