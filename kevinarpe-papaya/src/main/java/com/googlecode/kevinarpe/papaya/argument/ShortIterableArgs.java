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

/**
 * Static methods to check {@code Iterable<Short>} arguments.
 * <p>
 * See {@link com.googlecode.kevinarpe.papaya.argument.ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ShortIterableArgs {

    // Disable default constructor
    private ShortIterableArgs() {
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
     * @see ShortArrayArgs#checkPositive(short[], String)
     * @see ShortArrayArgs#checkPositive(Short[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see ShortArrayArgs#checkNotPositive(short[], String)
     * @see ShortArrayArgs#checkNotPositive(Short[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkNotPositive(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see ShortArrayArgs#checkNegative(short[], String)
     * @see ShortArrayArgs#checkNegative(Short[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see ShortArrayArgs#checkNotNegative(short[], String)
     * @see ShortArrayArgs#checkNotNegative(Short[], String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkNotNegative(TIterable ref, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotNegative(iter, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are within specified range.
     * <p>
     * Example: All values must be {@code >= 3 and <= 7}:
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
     * @see #checkValueOutsideRange(Iterable, short, short, String)
     * @see ShortArrayArgs#checkValueInsideRange(short[], short, short, String)
     * @see ShortArrayArgs#checkValueInsideRange(Short[], short, short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkValueInsideRange(TIterable ref, short minValue, short maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkValueInsideRange(iter, minValue, maxValue, argName);
        return ref;
    }

    /**
     * Tests if all values in an {@link Iterable} are <b>not</b> within specified range.
     * <p>
     * Example: All values must be {@code <= 3 and >= 7}:
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
     * @see #checkValueInsideRange(Iterable, short, short, String)
     * @see ShortArrayArgs#checkValueOutsideRange(short[], short, short, String)
     * @see ShortArrayArgs#checkValueOutsideRange(Short[], short, short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkValueOutsideRange(TIterable ref, short minValue, short maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see #checkMaxValue(Iterable, short, String)
     * @see ShortArrayArgs#checkMinValue(short[], short, String)
     * @see ShortArrayArgs#checkMinValue(Short[], short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkMinValue(TIterable ref, short minValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see #checkMinValue(Iterable, short, String)
     * @see ShortArrayArgs#checkMaxValue(short[], short, String)
     * @see ShortArrayArgs#checkMaxValue(Short[], short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkMaxValue(TIterable ref, short maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see #checkNotExactValue(Iterable, short, String)
     * @see ShortArrayArgs#checkExactValue(short[], short, String)
     * @see ShortArrayArgs#checkExactValue(Short[], short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkExactValue(TIterable ref, short exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
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
     * @see #checkExactValue(Iterable, short, String)
     * @see ShortArrayArgs#checkNotExactValue(short[], short, String)
     * @see ShortArrayArgs#checkNotExactValue(Short[], short, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Short>>
    TIterable checkNotExactValue(TIterable ref, short exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedShortObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
