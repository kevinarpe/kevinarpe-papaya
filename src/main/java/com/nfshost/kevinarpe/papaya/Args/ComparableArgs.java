/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

public final class ComparableArgs {

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
     *         or if value of {@code ref} is outside allowed range
     * @see ObjectArgs#staticCheckNotNull(Object, String)
     * @see #staticCheckMinValue(Comparable, Comparable, String)
     * @see #staticCheckMaxValue(Comparable, Comparable, String)
     * @see #staticCheckExactValue(Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T staticCheckValueRange(
            T ref, T minValue, T maxValue, String argName) {
        ObjectArgs.staticCheckNotNull(minValue, "minValue");
        ObjectArgs.staticCheckNotNull(maxValue, "maxValue");
        _staticCheckRangeCore(ref, minValue, maxValue, argName);        
        return ref;
    }
    
    private static <T extends Comparable<T>> void _staticCheckRangeCore(
            T ref, T optMinValue, T optMaxValue, String argName) {
        ObjectArgs.staticCheckNotNull(ref, argName);
        if (null != optMinValue &&
                null != optMaxValue &&
                optMinValue.compareTo(optMaxValue) > 0) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': 'minValue' > 'maxValue': %s > %s",
                argName, optMinValue, optMaxValue));
        }
        if (null != optMinValue && ref.compareTo(optMinValue) < 0) {
            throw new IllegalArgumentException(String.format(
                "Argument '%s': value < 'minValue': %s < %s",
                argName, ref, optMinValue));
        }
        if (null != optMaxValue && ref.compareTo(optMaxValue) > 0) {
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
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
     * @see #staticCheckValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T staticCheckMinValue(
            T ref, T minValue, String argName) {
        ObjectArgs.staticCheckNotNull(minValue, "minValue");
        T maxValue = null;
        _staticCheckRangeCore(ref, minValue, maxValue, argName);        
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
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
     * @see #staticCheckValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T staticCheckMaxValue(
            T ref, T maxValue, String argName) {
        ObjectArgs.staticCheckNotNull(maxValue, "maxValue");
        T minValue = null;
        _staticCheckRangeCore(ref, minValue, maxValue, argName);        
        return ref;
    }
    
    /**
     * Tests if a Comparable reference is not null and has an exact value.
     * 
     * @param ref an object reference
     * @param maxValue maximum value (inclusive)
     * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @return the validated object reference
     * @throws NullPointerException if {@code ref}, {@code maxValue},
     *         or {@code argName} is null
     * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
     * @see #staticCheckValueRange(Comparable, Comparable, Comparable, String)
     */
    public static <T extends Comparable<T>> T staticCheckExactValue(
            T ref, T exactValue, String argName) {
        ObjectArgs.staticCheckNotNull(exactValue, "exactValue");
        _staticCheckRangeCore(ref, exactValue, exactValue, argName);        
        return ref;
    }
}
