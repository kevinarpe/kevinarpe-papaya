package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
 * Static methods to check {@code double[]} and {@code Double[]} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class DoubleArrayArgs {

    // Disable default constructor
    private DoubleArrayArgs() {
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
     * @see #checkPositive(Double[], String)
     * @see DoubleIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static double[] checkPositive(double[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkPositive(double[], String)
     * @see LongIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static Double[] checkPositive(Double[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotPositive(Double[], String)
     * @see LongIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static double[] checkNotPositive(double[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkNotPositive(double[], String)
     * @see LongIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static Double[] checkNotPositive(Double[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNegative(Double[], String)
     * @see LongIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static double[] checkNegative(double[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkNegative(double[], String)
     * @see LongIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static Double[] checkNegative(Double[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotNegative(Double[], String)
     * @see LongIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static double[] checkNotNegative(double[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkNotNegative(double[], String)
     * @see LongIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static Double[] checkNotNegative(Double[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * 
     * @see #checkValueInsideRange(Double[], double, double, String)
     * @see LongIterableArgs#checkValueInsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static double[] checkValueInsideRange(
            double[] ref, double minRangeValue, double maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * 
     * @see #checkValueInsideRange(double[], double, double, String)
     * @see LongIterableArgs#checkValueInsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static Double[] checkValueInsideRange(
            Double[] ref, double minRangeValue, double maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * 
     * @see #checkValueOutsideRange(Double[], double, double, String)
     * @see LongIterableArgs#checkValueOutsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static double[] checkValueOutsideRange(
            double[] ref, double minRangeValue, double maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * 
     * @see #checkValueOutsideRange(double[], double, double, String)
     * @see LongIterableArgs#checkValueOutsideRange(Iterable, long, long, String)
     */
    @FullyTested
    public static Double[] checkValueOutsideRange(
            Double[] ref, double minRangeValue, double maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkMinValue(Double[], double, String)
     * @see LongIterableArgs#checkMinValue(Iterable, long, String)
     */
    @FullyTested
    public static double[] checkMinValue(double[] ref, double minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkMinValue(double[], double, String)
     * @see LongIterableArgs#checkMinValue(Iterable, long, String)
     */
    @FullyTested
    public static Double[] checkMinValue(Double[] ref, double minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkMaxValue(Double[], double, String)
     * @see LongIterableArgs#checkMaxValue(Iterable, long, String)
     */
    @FullyTested
    public static double[] checkMaxValue(double[] ref, double maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkMaxValue(double[], double, String)
     * @see LongIterableArgs#checkMaxValue(Iterable, long, String)
     */
    @FullyTested
    public static Double[] checkMaxValue(Double[] ref, double maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkExactValue(Double[], double, String)
     * @see LongIterableArgs#checkExactValue(Iterable, long, String)
     */
    @FullyTested
    public static double[] checkExactValue(double[] ref, double exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkExactValue(double[], double, String)
     * @see LongIterableArgs#checkExactValue(Iterable, long, String)
     */
    @FullyTested
    public static Double[] checkExactValue(Double[] ref, double exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
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
     * @see #checkNotExactValue(Double[], double, String)
     * @see LongIterableArgs#checkNotExactValue(Iterable, long, String)
     */
    @FullyTested
    public static double[] checkNotExactValue(double[] ref, double exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveDoubleArrayAsDoubleIterator(ref);
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
     * @see #checkNotExactValue(double[], double, String)
     * @see LongIterableArgs#checkNotExactValue(Iterable, long, String)
     */
    @FullyTested
    public static Double[] checkNotExactValue(Double[] ref, double exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectArrayAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
