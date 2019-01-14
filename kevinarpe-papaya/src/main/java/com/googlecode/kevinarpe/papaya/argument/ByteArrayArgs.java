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
 * Static methods to check {@code byte[]} and {@code Byte[]} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ByteArrayArgs {

    // Disable default constructor
    private ByteArrayArgs() {
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
     * @see #checkPositive(Byte[], String)
     * @see ByteIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static byte[] checkPositive(byte[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkPositive(byte[], String)
     * @see ByteIterableArgs#checkPositive(Iterable, String)
     */
    @FullyTested
    public static Byte[] checkPositive(Byte[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkNotPositive(Byte[], String)
     * @see ByteIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static byte[] checkNotPositive(byte[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkNotPositive(byte[], String)
     * @see ByteIterableArgs#checkNotPositive(Iterable, String)
     */
    @FullyTested
    public static Byte[] checkNotPositive(Byte[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkNegative(Byte[], String)
     * @see ByteIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static byte[] checkNegative(byte[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkNegative(byte[], String)
     * @see ByteIterableArgs#checkNegative(Iterable, String)
     */
    @FullyTested
    public static Byte[] checkNegative(Byte[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkNotNegative(Byte[], String)
     * @see ByteIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static byte[] checkNotNegative(byte[] ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkNotNegative(byte[], String)
     * @see ByteIterableArgs#checkNotNegative(Iterable, String)
     */
    @FullyTested
    public static Byte[] checkNotNegative(Byte[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkValueInsideRange(Byte[], byte, byte, String)
     * @see ByteIterableArgs#checkValueInsideRange(Iterable, byte, byte, String)
     */
    @FullyTested
    public static byte[] checkValueInsideRange(
            byte[] ref, byte minRangeValue, byte maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkValueInsideRange(byte[], byte, byte, String)
     * @see ByteIterableArgs#checkValueInsideRange(Iterable, byte, byte, String)
     */
    @FullyTested
    public static Byte[] checkValueInsideRange(
            Byte[] ref, byte minRangeValue, byte maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(Byte[], byte, byte, String)
     * @see ByteIterableArgs#checkValueOutsideRange(Iterable, byte, byte, String)
     */
    @FullyTested
    public static byte[] checkValueOutsideRange(
            byte[] ref, byte minRangeValue, byte maxRangeValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkValueOutsideRange(byte[], byte, byte, String)
     * @see ByteIterableArgs#checkValueOutsideRange(Iterable, byte, byte, String)
     */
    @FullyTested
    public static Byte[] checkValueOutsideRange(
            Byte[] ref, byte minRangeValue, byte maxRangeValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkMinValue(Byte[], byte, String)
     * @see ByteIterableArgs#checkMinValue(Iterable, byte, String)
     */
    @FullyTested
    public static byte[] checkMinValue(byte[] ref, byte minValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkMinValue(byte[], byte, String)
     * @see ByteIterableArgs#checkMinValue(Iterable, byte, String)
     */
    @FullyTested
    public static Byte[] checkMinValue(Byte[] ref, byte minValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(Byte[], byte, String)
     * @see ByteIterableArgs#checkMaxValue(Iterable, byte, String)
     */
    @FullyTested
    public static byte[] checkMaxValue(byte[] ref, byte maxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkMaxValue(byte[], byte, String)
     * @see ByteIterableArgs#checkMaxValue(Iterable, byte, String)
     */
    @FullyTested
    public static Byte[] checkMaxValue(Byte[] ref, byte maxValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkExactValue(Byte[], byte, String)
     * @see ByteIterableArgs#checkExactValue(Iterable, byte, String)
     */
    @FullyTested
    public static byte[] checkExactValue(byte[] ref, byte exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkExactValue(byte[], byte, String)
     * @see ByteIterableArgs#checkExactValue(Iterable, byte, String)
     */
    @FullyTested
    public static Byte[] checkExactValue(Byte[] ref, byte exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(Byte[], byte, String)
     * @see ByteIterableArgs#checkNotExactValue(Iterable, byte, String)
     */
    @FullyTested
    public static byte[] checkNotExactValue(byte[] ref, byte exactValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedPrimitiveByteArrayAsLongIterator(ref);
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
     * @see #checkNotExactValue(byte[], byte, String)
     * @see ByteIterableArgs#checkNotExactValue(Iterable, byte, String)
     */
    @FullyTested
    public static Byte[] checkNotExactValue(Byte[] ref, byte exactValue, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedByteObjectArrayAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
