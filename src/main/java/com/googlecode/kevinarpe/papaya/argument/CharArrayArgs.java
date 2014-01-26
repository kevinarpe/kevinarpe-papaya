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

/**
 * Static methods to check {@code char[]} and {@code Character[]} arguments.
 * <p>
 * See {@link com.googlecode.kevinarpe.papaya.argument.ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CharArrayArgs {

    // Disable default constructor
    private CharArrayArgs() {
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
     * @see #checkValueInsideRange(Character[], char, char, String)
     * @see CharIterableArgs#checkValueInsideRange(Iterable, char, char, String)
     */
    @FullyTested
    public static char[] checkValueInsideRange(
            char[] ref, char minRangeValue, char maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkValueInsideRange(char[], char, char, String)
     * @see CharIterableArgs#checkValueInsideRange(Iterable, char, char, String)
     */
    @FullyTested
    public static Character[] checkValueInsideRange(
            Character[] ref, char minRangeValue, char maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(Character[], char, char, String)
     * @see CharIterableArgs#checkValueOutsideRange(Iterable, char, char, String)
     */
    @FullyTested
    public static char[] checkValueOutsideRange(
            char[] ref, char minRangeValue, char maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(char[], char, char, String)
     * @see CharIterableArgs#checkValueOutsideRange(Iterable, char, char, String)
     */
    @FullyTested
    public static Character[] checkValueOutsideRange(
            Character[] ref, char minRangeValue, char maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
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
     * @see #checkMinValue(Character[], char, String)
     * @see CharIterableArgs#checkMinValue(Iterable, char, String)
     */
    @FullyTested
    public static char[] checkMinValue(char[] ref, char minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkMinValue(char[], char, String)
     * @see CharIterableArgs#checkMinValue(Iterable, char, String)
     */
    @FullyTested
    public static Character[] checkMinValue(Character[] ref, char minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(Character[], char, String)
     * @see CharIterableArgs#checkMaxValue(Iterable, char, String)
     */
    @FullyTested
    public static char[] checkMaxValue(char[] ref, char maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(char[], char, String)
     * @see CharIterableArgs#checkMaxValue(Iterable, char, String)
     */
    @FullyTested
    public static Character[] checkMaxValue(Character[] ref, char maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
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
     * @see #checkExactValue(Character[], char, String)
     * @see CharIterableArgs#checkExactValue(Iterable, char, String)
     */
    @FullyTested
    public static char[] checkExactValue(char[] ref, char exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkExactValue(char[], char, String)
     * @see CharIterableArgs#checkExactValue(Iterable, char, String)
     */
    @FullyTested
    public static Character[] checkExactValue(Character[] ref, char exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(Character[], char, String)
     * @see CharIterableArgs#checkNotExactValue(Iterable, char, String)
     */
    @FullyTested
    public static char[] checkNotExactValue(char[] ref, char exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveCharArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(char[], char, String)
     * @see CharIterableArgs#checkNotExactValue(Iterable, char, String)
     */
    @FullyTested
    public static Character[] checkNotExactValue(Character[] ref, char exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
