package com.googlecode.kevinarpe.papaya.Args;

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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ComparableArgs {

	// Disable default constructor
	private ComparableArgs() {
	}

    /**
     * Tests if a Comparable reference is not null and its value within specified range.
     * 
     * @param ref an object reference
     * @param minValue minimum value (inclusive)
     * @param maxValue maximum value (inclusive)
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref}, {@code minValue}, {@code maxValue},
     *         or {@code argName} is null
     * @throws IllegalArgumentException if {@code minValue > maxValue},
     *         or if value of {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkMinValue(Comparable, Comparable, String)
     * @see #checkMaxValue(Comparable, Comparable, String)
     * @see #checkExactValue(Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T checkValueRange(
            T ref, T minValue, T maxValue, String argName) {
        ObjectArgs.checkNotNull(minValue, "minValue");
        ObjectArgs.checkNotNull(maxValue, "maxValue");
        _checkRangeCore(ref, minValue, maxValue, argName);        
        return ref;
    }
    
    private static <T extends Comparable<T>> void _checkRangeCore(
            T ref, T optMinValue, T optMaxValue, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        if (null != optMinValue &&
                null != optMaxValue &&
                optMinValue.compareTo(optMaxValue) > 0) {
            StringArgs._checkArgNameValid(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': 'minValue' > 'maxValue': %s > %s",
                argName, optMinValue, optMaxValue));
        }
        if (null != optMinValue && ref.compareTo(optMinValue) < 0) {
            StringArgs._checkArgNameValid(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': value < 'minValue': %s < %s",
                argName, ref, optMinValue));
        }
        if (null != optMaxValue && ref.compareTo(optMaxValue) > 0) {
            StringArgs._checkArgNameValid(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': value > 'maxValue': %s > %s",
                argName, ref, optMaxValue));
        }
    }
    
    /**
     * Tests if a Comparable reference is not null and has a minimum value.
     * 
     * @param ref an object reference
     * @param minValue minimum value (inclusive)
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref}, {@code minValue},
     *         or {@code argName} is null
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T checkMinValue(
            T ref, T minValue, String argName) {
        ObjectArgs.checkNotNull(minValue, "minValue");
        T maxValue = null;
        _checkRangeCore(ref, minValue, maxValue, argName);        
        return ref;
    }
    
    /**
     * Tests if a Comparable reference is not null and has a maximum value.
     * 
     * @param ref an object reference
     * @param maxValue maximum value (inclusive)
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref}, {@code maxValue},
     *         or {@code argName} is null
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T checkMaxValue(
            T ref, T maxValue, String argName) {
        ObjectArgs.checkNotNull(maxValue, "maxValue");
        T minValue = null;
        _checkRangeCore(ref, minValue, maxValue, argName);        
        return ref;
    }
    
    /**
     * Tests if a Comparable reference is not null and has an exact value.
     * 
     * @param ref an object reference
     * @param exactValue expected value
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref}, {@code maxValue},
     *         or {@code argName} is null
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range,
     *         or if {@code argName} is empty or whitespace
     * @see #checkValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T checkExactValue(
            T ref, T exactValue, String argName) {
        ObjectArgs.checkNotNull(exactValue, "exactValue");
        _checkRangeCore(ref, exactValue, exactValue, argName);        
        return ref;
    }
}
