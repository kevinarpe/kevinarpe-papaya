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
 * Static methods to check {@code Iterable<Character>} arguments.
 * <p>
 * See {@link com.googlecode.kevinarpe.papaya.argument.ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CharIterableArgs {

    // Disable default constructor
    private CharIterableArgs() {
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
     * @see #checkValueOutsideRange(Iterable, char, char, String)
     * @see CharArrayArgs#checkValueInsideRange(char[], char, char, String)
     * @see CharArrayArgs#checkValueInsideRange(Character[], char, char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkValueInsideRange(TIterable ref, char minValue, char maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
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
     * @see #checkValueInsideRange(Iterable, char, char, String)
     * @see CharArrayArgs#checkValueOutsideRange(char[], char, char, String)
     * @see CharArrayArgs#checkValueOutsideRange(Character[], char, char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkValueOutsideRange(TIterable ref, char minValue, char maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
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
     * @see #checkMaxValue(Iterable, char, String)
     * @see CharArrayArgs#checkMinValue(char[], char, String)
     * @see CharArrayArgs#checkMinValue(Character[], char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkMinValue(TIterable ref, char minValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
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
     * @see #checkMinValue(Iterable, char, String)
     * @see CharArrayArgs#checkMaxValue(char[], char, String)
     * @see CharArrayArgs#checkMaxValue(Character[], char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkMaxValue(TIterable ref, char maxValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
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
     * @see #checkNotExactValue(Iterable, char, String)
     * @see CharArrayArgs#checkExactValue(char[], char, String)
     * @see CharArrayArgs#checkExactValue(Character[], char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkExactValue(TIterable ref, char exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
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
     * @see #checkExactValue(Iterable, char, String)
     * @see CharArrayArgs#checkNotExactValue(char[], char, String)
     * @see CharArrayArgs#checkNotExactValue(Character[], char, String)
     */
    @FullyTested
    public static <TIterable extends Iterable<Character>>
    TIterable checkNotExactValue(TIterable ref, char exactValue, String argName) {
        IterableArgs.checkElementsNotNull(ref, argName);

        ValueAsTypeIterator._IValueAsLongIterator iter =
            new ValueAsTypeIterator._UncheckedCharObjectIterableAsLongIterator(ref);
        ValueAsTypeIterator._checkNotExactValue(iter, exactValue, argName);
        return ref;
    }
}
