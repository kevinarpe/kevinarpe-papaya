package com.googlecode.kevinarpe.papaya.argument;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ValueAsTypeIterator._IValueAsLongIterator;

public final class LongArrayArgs {

    // Disable default constructor
    private LongArrayArgs() {
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
     * @see #checkPositive(Long[], String)
     */
    @FullyTested
    public static long[] checkPositive(long[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     * @see #checkPositive(long[], String)
     */
    @FullyTested
    public static Long[] checkPositive(Long[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkNotPositive(long[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkNotPositive(Long[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkNegative(long[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkNegative(Long[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkNotNegative(long[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkNotNegative(Long[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are within specified range.
     * <p>
     * Example: All values must be >= 3 and <= 7:
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
     */
    @FullyTested
    public static long[] checkValueInsideRange(
            long[] ref, long minRangeValue, long maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an array are within specified range.
     * <p>
     * Example: All values must be >= 3 and <= 7:
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
     */
    @FullyTested
    public static Long[] checkValueInsideRange(
            Long[] ref, long minRangeValue, long maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are <b>not</b> within specified range.
     * <p>
     * Example: All values must be <= 3 and >= 7:
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
     */
    @FullyTested
    public static long[] checkValueOutsideRange(
            long[] ref, long minRangeValue, long maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minRangeValue, maxRangeValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an array are <b>not</b> within specified range.
     * <p>
     * Example: All values must be <= 3 and >= 7:
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
     */
    @FullyTested
    public static Long[] checkValueOutsideRange(
            Long[] ref, long minRangeValue, long maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkMinValue(long[] ref, long minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkMinValue(Long[] ref, long minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkMaxValue(long[] ref, long maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkMaxValue(Long[] ref, long maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkExactValue(long[] ref, long exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkExactValue(Long[] ref, long exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static long[] checkNotExactValue(long[] ref, long exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveLongArrayAsLongIterator(ref);
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
     */
    @FullyTested
    public static Long[] checkNotExactValue(Long[] ref, long exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedLongObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
