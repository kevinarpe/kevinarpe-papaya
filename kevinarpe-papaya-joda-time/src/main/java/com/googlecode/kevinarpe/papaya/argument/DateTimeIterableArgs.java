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
import org.joda.time.DateTime;

/**
 * Static methods to check {@code Iterable<DateTime>} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DateTimeIterableArgs {

    // Disable default constructor
    private DateTimeIterableArgs() {
    }

    /**
     * Tests if all values in an {@link Iterable} are within specified range.
     * <p>
     * Example: All values must be {@code >= 3 and <= 7}:
     * <br>{@code checkValueInsideRange(numbersList, 3, 7, "numbersList");}
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code iterable} is outside allowed range</li>
     * </ul>
     *
     * @see #checkValueOutsideRange(Iterable, DateTime, DateTime, String)
     * @see DateTimeArrayArgs#checkValueInsideRange(DateTime[], DateTime, DateTime, String)
     * @see DateTimeArrayArgs#checkValueInsideRange(DateTime[], DateTime, DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkValueInsideRange(
            TIterable iterable, DateTime minValue, DateTime maxValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkValueInsideRange(iter, minValue, maxValue, iterableArgName);
        return iterable;
    }

    /**
     * Tests if all values in an {@link Iterable} are <b>not</b> within specified range.
     * <p>
     * Example: All values must be {@code <= 3 and >= 7}:
     * <br>{@code checkValueInsideRange(numbersList, 4, 6, "numbersList");}
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive) for inclusive range of each value in array to test
     * @param maxValue
     *        maximum value (inclusive) for inclusive range of each value in array to test
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minValue > maxValue}</li>
     *   <li>if any value of {@code iterable} is inside not-allowed range</li>
     * </ul>
     *
     * @see #checkValueInsideRange(Iterable, DateTime, DateTime, String)
     * @see DateTimeArrayArgs#checkValueOutsideRange(DateTime[], DateTime, DateTime, String)
     * @see DateTimeArrayArgs#checkValueOutsideRange(DateTime[], DateTime, DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkValueOutsideRange(
            TIterable iterable, DateTime minValue, DateTime maxValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkValueOutsideRange(iter, minValue, maxValue, iterableArgName);
        return iterable;
    }

    /**
     * Tests if all values in an {@link Iterable} have a minimum value.
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param minValue
     *        minimum value (inclusive)
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code iterable} is less than {@code minValue}
     *
     * @see #checkMaxValue(Iterable, DateTime, String)
     * @see DateTimeArrayArgs#checkMinValue(DateTime[], DateTime, String)
     * @see DateTimeArrayArgs#checkMinValue(DateTime[], DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkMinValue(TIterable iterable, DateTime minValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkMinValue(iter, minValue, iterableArgName);
        return iterable;
    }

    /**
     * Tests if all values in an {@link Iterable} have a maximum value.
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param maxValue
     *        maximum value (inclusive)
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code iterable} is greater than {@code maxValue}
     *
     * @see #checkMinValue(Iterable, DateTime, String)
     * @see DateTimeArrayArgs#checkMaxValue(DateTime[], DateTime, String)
     * @see DateTimeArrayArgs#checkMaxValue(DateTime[], DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkMaxValue(TIterable iterable, DateTime maxValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkMaxValue(iter, maxValue, iterableArgName);
        return iterable;
    }

    /**
     * Tests if all values in an {@link Iterable} have an exact value.
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code iterable} is {@code exactValue}
     *
     * @see #checkNotExactValue(Iterable, DateTime, String)
     * @see DateTimeArrayArgs#checkExactValue(DateTime[], DateTime, String)
     * @see DateTimeArrayArgs#checkExactValue(DateTime[], DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkExactValue(TIterable iterable, DateTime exactValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkExactValue(iter, exactValue, iterableArgName);
        return iterable;
    }

    /**
     * Tests if all values in an {@link Iterable} do not have an exact value.
     *
     * @param iterable
     *        iterable of values to test.  May be empty
     * @param exactValue
     *        expected value
     * @param iterableArgName
     *        argument name for {@code iterable}, e.g., "valueList"
     *
     * @return the validated iterable
     *
     * @throws NullPointerException
     *         if {@code iterable} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if any value of {@code iterable} is {@code exactValue}
     *
     * @see #checkExactValue(Iterable, DateTime, String)
     * @see DateTimeArrayArgs#checkNotExactValue(DateTime[], DateTime, String)
     * @see DateTimeArrayArgs#checkNotExactValue(DateTime[], DateTime, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<DateTime>>
    TIterable checkNotExactValue(TIterable iterable, DateTime exactValue, String iterableArgName) {
        IterableArgs.checkElementsNotNull(iterable, iterableArgName);

        ValueAsTypeIterator._IValueAsComparableIterator<DateTime> iter =
            new ValueAsTypeIterator._UncheckedComparableIterableAsComparableIterator<DateTime>(iterable);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, iterableArgName);
        return iterable;
    }
}
