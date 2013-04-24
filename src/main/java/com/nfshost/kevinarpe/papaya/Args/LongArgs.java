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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class LongArgs {

    /**
     * Tests if {@code value > 0}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value <= 0}
     * @see #checkNotPositive(long, String)
     * @see #checkNegative(long, String)
     * @see #checkNotNegative(long, String)
     */
    public static long checkPositive(long value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value <= 0) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is not positive: %d",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value <= 0}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value > 0}
     * @see #checkPositive(long, String)
     * @see #checkNegative(long, String)
     * @see #checkNotNegative(long, String)
     */
    public static long checkNotPositive(long value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value > 0) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is positive: %d",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value < 0}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value => 0}
     * @see #checkPositive(long, String)
     * @see #checkNotPositive(long, String)
     * @see #checkNotNegative(long, String)
     */
    public static long checkNegative(long value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value >= 0) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is not negative: %d",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value >= 0}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value < 0}
     * @see #checkPositive(long, String)
     * @see #checkNotPositive(long, String)
     * @see #checkNegative(long, String)
     */
    public static long checkNotNegative(long value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value < 0) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is negative: %d",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(long, long, String)
     * @see #checkMaxValue(long, long, String)
     * @see #checkExactValue(long, long, String)
     */
    public static long checkValueRange(
            long value, long minValue, long maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(long, long, long, String)
     * @see #checkMaxValue(long, long, String)
     * @see #checkExactValue(long, long, String)
     */
    public static long checkMinValue(long value, long minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(long, long, long, String)
     * @see #checkMinValue(long, long, String)
     * @see #checkExactValue(long, long, String)
     */
    public static long checkMaxValue(long value, long maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(long, long, long, String)
     * @see #checkMinValue(long, long, String)
     * @see #checkMaxValue(long, long, String)
     */
    public static long checkExactValue(long value, long exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
}
