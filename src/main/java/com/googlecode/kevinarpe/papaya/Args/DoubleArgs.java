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
public class DoubleArgs {

    /**
     * Tests if {@code value > 0.0d}
     * 
     * @param value a value to test
     * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
     * @return the validated value
     * @throws NullPointerException if {@code argName} is null
     * @throws IllegalArgumentException if value of {@code value <= 0.0d},
     *         or if {@code argName} is empty or whitespace
     * @see #checkNotPositive(double, String)
     * @see #checkNegative(double, String)
     * @see #checkNotNegative(double, String)
     */
    public static double checkPositive(double value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value <= 0.0d) {
            StringArgs._checkArgNameValid(argName, "argName");
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
     * @throws IllegalArgumentException if value of {@code value > 0.0d},
     *         or if {@code argName} is empty or whitespace
     * @see #checkPositive(double, String)
     * @see #checkNegative(double, String)
     * @see #checkNotNegative(double, String)
     */
    public static double checkNotPositive(double value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value > 0.0d) {
            StringArgs._checkArgNameValid(argName, "argName");
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
     * @throws IllegalArgumentException if value of {@code value => 0.0d},
     *         or if {@code argName} is empty or whitespace
     * @see #checkPositive(double, String)
     * @see #checkNotPositive(double, String)
     * @see #checkNotNegative(double, String)
     */
    public static double checkNegative(double value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value >= 0.0d) {
            StringArgs._checkArgNameValid(argName, "argName");
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
     * @throws IllegalArgumentException if value of {@code value < 0.0d},
     *         or if {@code argName} is empty or whitespace
     * @see #checkPositive(double, String)
     * @see #checkNotPositive(double, String)
     * @see #checkNegative(double, String)
     */
    public static double checkNotNegative(double value, String argName) {
        ObjectArgs.checkNotNull(argName, "argName");
        if (value < 0.0d) {
            StringArgs._checkArgNameValid(argName, "argName");
            throw new IllegalArgumentException(String.format(
                    "Argument '%s': value is negative: %f",
                    argName, value));
        }
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(double, double, String)
     * @see #checkMaxValue(double, double, String)
     * @see #checkExactValue(double, double, String)
     */
    public static double checkValueRange(
            double value, double minValue, double maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(double, double, double, String)
     * @see #checkMaxValue(double, double, String)
     * @see #checkExactValue(double, double, String)
     */
    public static double checkMinValue(double value, double minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(double, double, double, String)
     * @see #checkMinValue(double, double, String)
     * @see #checkExactValue(double, double, String)
     */
    public static double checkMaxValue(double value, double maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(double, double, double, String)
     * @see #checkMinValue(double, double, String)
     * @see #checkMaxValue(double, double, String)
     */
    public static double checkExactValue(
            double value, double exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
}
