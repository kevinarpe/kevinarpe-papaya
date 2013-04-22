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

public class DoubleArgs {

    /**
     * Tests if {@code value > 0.0d}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value <= 0.0d}
     * @see #staticCheckNotPositive(double, String)
     * @see #staticCheckNegative(double, String)
     * @see #staticCheckNotNegative(double, String)
     */
    public static double staticCheckPositive(double value, String argName) {
        ObjectArgs.staticCheckNotNull(argName, "argName");
        if (value <= 0.0d) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is not positive: %f",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value <= 0.0d}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value > 0.0d}
     * @see #staticCheckPositive(double, String)
     * @see #staticCheckNegative(double, String)
     * @see #staticCheckNotNegative(double, String)
     */
    public static double staticCheckNotPositive(double value, String argName) {
        ObjectArgs.staticCheckNotNull(argName, "argName");
        if (value > 0.0d) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is positive: %f",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value < 0.0d}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value => 0.0d}
     * @see #staticCheckPositive(double, String)
     * @see #staticCheckNotPositive(double, String)
     * @see #staticCheckNotNegative(double, String)
     */
    public static double staticCheckNegative(double value, String argName) {
        ObjectArgs.staticCheckNotNull(argName, "argName");
        if (value >= 0.0d) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is not negative: %f",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Tests if {@code value >= 0.0d}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value < 0.0d}
     * @see #staticCheckPositive(double, String)
     * @see #staticCheckNotPositive(double, String)
     * @see #staticCheckNegative(double, String)
     */
    public static double staticCheckNotNegative(double value, String argName) {
        ObjectArgs.staticCheckNotNull(argName, "argName");
        if (value < 0.0d) {
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is negative: %f",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #staticCheckMinValue(double, double, String)
     * @see #staticCheckMaxValue(double, double, String)
     * @see #staticCheckExactValue(double, double, String)
     */
    public static double staticCheckValueRange(
            double value, double minValue, double maxValue, String argName) {
        ComparableArgs.staticCheckValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckMinValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(double, double, double, String)
     * @see #staticCheckMaxValue(double, double, String)
     * @see #staticCheckExactValue(double, double, String)
     */
    public static double staticCheckMinValue(double value, double minValue, String argName) {
        ComparableArgs.staticCheckMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(double, double, double, String)
     * @see #staticCheckMinValue(double, double, String)
     * @see #staticCheckExactValue(double, double, String)
     */
    public static double staticCheckMaxValue(double value, double maxValue, String argName) {
        ComparableArgs.staticCheckMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckExactValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(double, double, double, String)
     * @see #staticCheckMinValue(double, double, String)
     * @see #staticCheckMaxValue(double, double, String)
     */
    public static double staticCheckExactValue(
            double value, double exactValue, String argName) {
        ComparableArgs.staticCheckExactValue(value, exactValue, argName);
        return value;
    }
}
