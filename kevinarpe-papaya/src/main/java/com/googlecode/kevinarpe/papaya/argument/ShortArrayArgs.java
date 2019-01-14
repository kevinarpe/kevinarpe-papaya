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

/**
 * Static methods to check {@code short[]} and {@code Short[]} arguments.
 * <p>
 * See {@link com.googlecode.kevinarpe.papaya.argument.ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ShortArrayArgs {

    // Disable default constructor
    private ShortArrayArgs() {
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
     * @see #checkPositive(Short[], String)
     * @see ShortIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static short[] checkPositive(short[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkPositive(short[], String)
     * @see ShortIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static Short[] checkPositive(Short[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkNotPositive(Short[], String)
     * @see ShortIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static short[] checkNotPositive(short[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkNotPositive(short[], String)
     * @see ShortIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static Short[] checkNotPositive(Short[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkNegative(Short[], String)
     * @see ShortIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static short[] checkNegative(short[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkNegative(short[], String)
     * @see ShortIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static Short[] checkNegative(Short[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkNotNegative(Short[], String)
     * @see ShortIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static short[] checkNotNegative(short[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkNotNegative(short[], String)
     * @see ShortIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static Short[] checkNotNegative(Short[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkValueInsideRange(Short[], short, short, String)
     * @see ShortIterableArgs#checkValueInsideRange(Iterable, short, short, String)
     */
    @FullyTested
    public static short[] checkValueInsideRange(
            short[] ref, short minRangeValue, short maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkValueInsideRange(short[], short, short, String)
     * @see ShortIterableArgs#checkValueInsideRange(Iterable, short, short, String)
     */
    @FullyTested
    public static Short[] checkValueInsideRange(
            Short[] ref, short minRangeValue, short maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(Short[], short, short, String)
     * @see ShortIterableArgs#checkValueOutsideRange(Iterable, short, short, String)
     */
    @FullyTested
    public static short[] checkValueOutsideRange(
            short[] ref, short minRangeValue, short maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(short[], short, short, String)
     * @see ShortIterableArgs#checkValueOutsideRange(Iterable, short, short, String)
     */
    @FullyTested
    public static Short[] checkValueOutsideRange(
            Short[] ref, short minRangeValue, short maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkMinValue(Short[], short, String)
     * @see ShortIterableArgs#checkMinValue(Iterable, short, String)
     */
    @FullyTested
    public static short[] checkMinValue(short[] ref, short minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkMinValue(short[], short, String)
     * @see ShortIterableArgs#checkMinValue(Iterable, short, String)
     */
    @FullyTested
    public static Short[] checkMinValue(Short[] ref, short minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(Short[], short, String)
     * @see ShortIterableArgs#checkMaxValue(Iterable, short, String)
     */
    @FullyTested
    public static short[] checkMaxValue(short[] ref, short maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(short[], short, String)
     * @see ShortIterableArgs#checkMaxValue(Iterable, short, String)
     */
    @FullyTested
    public static Short[] checkMaxValue(Short[] ref, short maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkExactValue(Short[], short, String)
     * @see ShortIterableArgs#checkExactValue(Iterable, short, String)
     */
    @FullyTested
    public static short[] checkExactValue(short[] ref, short exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkExactValue(short[], short, String)
     * @see ShortIterableArgs#checkExactValue(Iterable, short, String)
     */
    @FullyTested
    public static Short[] checkExactValue(Short[] ref, short exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(Short[], short, String)
     * @see ShortIterableArgs#checkNotExactValue(Iterable, short, String)
     */
    @FullyTested
    public static short[] checkNotExactValue(short[] ref, short exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveShortArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(short[], short, String)
     * @see ShortIterableArgs#checkNotExactValue(Iterable, short, String)
     */
    @FullyTested
    public static Short[] checkNotExactValue(Short[] ref, short exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
