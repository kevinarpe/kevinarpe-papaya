package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.argument.ValueAsTypeIterator._IValueAsDoubleIterator;

/**
 * Static methods to check {@code float[]} and {@code Float[]} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class FloatArrayArgs {

    // Disable default constructor
    private FloatArrayArgs() {
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
     * @see #checkPositive(Float[], String)
     * @see FloatIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static float[] checkPositive(float[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkPositive(float[], String)
     * @see LongIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static Float[] checkPositive(Float[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotPositive(Float[], String)
     * @see LongIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static float[] checkNotPositive(float[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkNotPositive(float[], String)
     * @see LongIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static Float[] checkNotPositive(Float[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNegative(Float[], String)
     * @see LongIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static float[] checkNegative(float[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkNegative(float[], String)
     * @see LongIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static Float[] checkNegative(Float[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotNegative(Float[], String)
     * @see LongIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static float[] checkNotNegative(float[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkNotNegative(float[], String)
     * @see LongIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static Float[] checkNotNegative(Float[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkValueInsideRange(Float[], float, float, String)
     * @see LongIterableArgs#checkValueInsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static float[] checkValueInsideRange(
            float[] ref, float minRangeValue, float maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkValueInsideRange(float[], float, float, String)
     * @see LongIterableArgs#checkValueInsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static Float[] checkValueInsideRange(
            Float[] ref, float minRangeValue, float maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkValueOutsideRange(Float[], float, float, String)
     * @see LongIterableArgs#checkValueOutsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static float[] checkValueOutsideRange(
            float[] ref, float minRangeValue, float maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkValueOutsideRange(float[], float, float, String)
     * @see LongIterableArgs#checkValueOutsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static Float[] checkValueOutsideRange(
            Float[] ref, float minRangeValue, float maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkMinValue(Float[], float, String)
     * @see LongIterableArgs#checkMinValue(Iterable, long, String)
     */
    @FullyTested
    public static float[] checkMinValue(float[] ref, float minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkMinValue(float[], float, String)
     * @see LongIterableArgs#checkMinValue(Iterable, long, String)
     */
    @FullyTested
    public static Float[] checkMinValue(Float[] ref, float minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkMaxValue(Float[], float, String)
     * @see LongIterableArgs#checkMaxValue(Iterable, long, String)
     */
    @FullyTested
    public static float[] checkMaxValue(float[] ref, float maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkMaxValue(float[], float, String)
     * @see LongIterableArgs#checkMaxValue(Iterable, long, String)
     */
    @FullyTested
    public static Float[] checkMaxValue(Float[] ref, float maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkExactValue(Float[], float, String)
     * @see LongIterableArgs#checkExactValue(Iterable, long, String)
     */
    @FullyTested
    public static float[] checkExactValue(float[] ref, float exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkExactValue(float[], float, String)
     * @see LongIterableArgs#checkExactValue(Iterable, long, String)
     */
    @FullyTested
    public static Float[] checkExactValue(Float[] ref, float exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotExactValue(Float[], float, String)
     * @see LongIterableArgs#checkNotExactValue(Iterable, long, String)
     */
    @FullyTested
    public static float[] checkNotExactValue(float[] ref, float exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveFloatArrayAsDoubleIterator(ref);
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
     * @see #checkNotExactValue(float[], float, String)
     * @see LongIterableArgs#checkNotExactValue(Iterable, long, String)
     */
    @FullyTested
    public static Float[] checkNotExactValue(Float[] ref, float exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedFloatObjectArrayAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
