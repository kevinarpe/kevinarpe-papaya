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
import com.googlecode.kevinarpe.papaya.argument.ValueAsTypeIterator._IValueAsDoubleIterator;

public class DoubleIterableArgs {

    // Disable default constructor
    private DoubleIterableArgs() {
    }

    /**
     * Tests if all values in an {@link Iterable} are positive: greater than zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not positive
     * 
     * @see DoubleArrayArgs#checkPositive(double[], String)
     * @see DoubleArrayArgs#checkPositive(Double[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkPositive(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are not positive: less than or equal to zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is positive
     * 
     * @see DoubleArrayArgs#checkNotPositive(double[], String)
     * @see DoubleArrayArgs#checkNotPositive(Double[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkNotPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNotPositive(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are negative: less than zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is not negative
     * 
     * @see DoubleArrayArgs#checkNegative(double[], String)
     * @see DoubleArrayArgs#checkNegative(Double[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are not negative: greater than or equal to zero.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value is negative
     * 
     * @see DoubleArrayArgs#checkNotNegative(double[], String)
     * @see DoubleArrayArgs#checkNotNegative(Double[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkNotNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} are within specified range.
     * <p>
     * Example: All values must be >= 3 and <= 7:
     * <br>{@code checkValueInsideRange(numbersList, 3, 7, "numbersList");}
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is outside allowed range</li>
     * </ul>
     * 
     * @see #checkValueOutsideRange(Iterable, double, double, String)
     * @see DoubleArrayArgs#checkValueInsideRange(double[], double, double, String)
     * @see DoubleArrayArgs#checkValueInsideRange(Double[], double, double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkValueInsideRange(
            TIterable ref, double minValue, double maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minValue, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} are <b>not</b> within specified range.
     * <p>
     * Example: All values must be <= 3 and >= 7:
     * <br>{@code checkValueInsideRange(numbersList, 4, 6, "numbersList");}
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code ref} is inside not-allowed range</li>
     * </ul>
     * 
     * @see #checkValueInsideRange(Iterable, double, double, String)
     * @see DoubleArrayArgs#checkValueOutsideRange(double[], double, double, String)
     * @see DoubleArrayArgs#checkValueOutsideRange(Double[], double, double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkValueOutsideRange(
            TIterable ref, double minValue, double maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        _IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minValue, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have a minimum value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is less than {@code minValue}
     * 
     * @see #checkMaxValue(Iterable, double, String)
     * @see DoubleArrayArgs#checkMinValue(double[], double, String)
     * @see DoubleArrayArgs#checkMinValue(Double[], double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkMinValue(TIterable ref, double minValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkMinValue(iter, minValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have a maximum value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is greater than {@code maxValue}
     * 
     * @see #checkMinValue(Iterable, double, String)
     * @see DoubleArrayArgs#checkMaxValue(double[], double, String)
     * @see DoubleArrayArgs#checkMaxValue(Double[], double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkMaxValue(TIterable ref, double maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} have an exact value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     * 
     * @see #checkNotExactValue(Iterable, double, String)
     * @see DoubleArrayArgs#checkExactValue(double[], double, String)
     * @see DoubleArrayArgs#checkExactValue(Double[], double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkExactValue(TIterable ref, double exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, argName);
        return ref;
    }
    
    /**
     * Tests if all values in an {@link Iterable} do not have an exact value.
     * 
     * @param ref
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param argName
     *        argument name for {@code ref}, e.g., "valueList"
     * 
     * @return the validated iterable
     * 
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code ref} is {@code exactValue}
     * 
     * @see #checkExactValue(Iterable, double, String)
     * @see DoubleArrayArgs#checkNotExactValue(double[], double, String)
     * @see DoubleArrayArgs#checkNotExactValue(Double[], double, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Double>>
    TIterable checkNotExactValue(TIterable ref, double exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);
        
        ValueAsTypeIterator._IValueAsDoubleIterator iter =
            new ValueAsTypeIterator._UncheckedDoubleObjectIterableAsDoubleIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}