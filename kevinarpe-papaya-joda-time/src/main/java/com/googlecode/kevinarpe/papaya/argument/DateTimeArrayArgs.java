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
import org.joda.time.DateTime;

/**
 * Static methods to check {@code DateTime[]} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DateTimeArrayArgs {

    // Disable default constructor
    private DateTimeArrayArgs() {
    }

    /**
     * Tests if all values in an array are within specified range.
     * <p>
     * Example: All values must be {@code >= 3 and <= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 3, 7, "numbersArray");}
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxRangeValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code arr} is outside allowed range</li>
     * </ul>
     *
     * @see #checkValueInsideRange(DateTime[], DateTime, DateTime, String)
     * @see DateTimeIterableArgs#checkValueInsideRange(Iterable, DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkValueInsideRange(
            DateTime[] arr, DateTime minRangeValue, DateTime maxRangeValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkValueInsideRange(iter, minRangeValue, maxRangeValue, arrArgName);
        return arr;
    }

    /**
     * Tests if all values in an array are <b>not</b> within specified range.
     * <p>
     * Example: All values must be {@code <= 3 and >= 7}:
     * <br>{@code checkValueInsideRange(numbersArray, 4, 6, "numbersArray");}
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param minRangeValue
     *        minimum value (inclusive) for exclusive range of each value in array to test
     * @param maxRangeValue
     *        maximum value (inclusive) for exclusive range of each value in array to test
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code arr} is inside not-allowed range</li>
     * </ul>
     *
     * @see #checkValueOutsideRange(DateTime[], DateTime, DateTime, String)
     * @see DateTimeIterableArgs#checkValueOutsideRange(Iterable, DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkValueOutsideRange(
            DateTime[] arr, DateTime minRangeValue, DateTime maxRangeValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minRangeValue, maxRangeValue, arrArgName);
        return arr;
    }

    /**
     * Tests if all values in an array have a minimum value.
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code arr} is less than {@code minValue}
     *
     * @see #checkMinValue(DateTime[], DateTime, String)
     * @see DateTimeIterableArgs#checkMinValue(Iterable, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkMinValue(DateTime[] arr, DateTime minValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkMinValue(iter, minValue, arrArgName);
        return arr;
    }

    /**
     * Tests if all values in an array have a maximum value.
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code arr} is greater than {@code maxValue}
     *
     * @see #checkMaxValue(DateTime[], DateTime, String)
     * @see DateTimeIterableArgs#checkMaxValue(Iterable, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkMaxValue(DateTime[] arr, DateTime maxValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, arrArgName);
        return arr;
    }

    /**
     * Tests if all values in an array have an exact value.
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code arr} is not {@code exactValue}
     *
     * @see #checkExactValue(DateTime[], DateTime, String)
     * @see DateTimeIterableArgs#checkExactValue(Iterable, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkExactValue(
            DateTime[] arr, DateTime exactValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, arrArgName);
        return arr;
    }

    // TOOD: Rename 'ref' for *ArrayArgs classes to 'arr'
    // TODO: Rename 'argName' for *ArrayArgs classes to 'arrArgName'

    /**
     * Tests if all values in an array do not have an exact value.
     *
     * @param arr
     *        array of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param arrArgName
     *        argument name for {@code arr}, e.g., "valueList"
     *
     * @return the validated array
     *
     * @throws NullPointerException
     *         if {@code arr} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code arr} is {@code exactValue}
     *
     * @see #checkNotExactValue(DateTime[], DateTime, String)
     * @see DateTimeIterableArgs#checkNotExactValue(Iterable, DateTime, String)
     */
    @FullyTested
    public static DateTime[] checkNotExactValue(
            DateTime[] arr, DateTime exactValue, String arrArgName) {
        ArrayArgs.checkElementsNotNull(arr, arrArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableArrayAsComparableIterator<DateTime>(arr);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, arrArgName);
        return arr;
    }
}
