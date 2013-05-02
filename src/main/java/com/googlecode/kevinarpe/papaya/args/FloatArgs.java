package com.googlecode.kevinarpe.papaya.args;

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
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FloatArgs {

	// Disable default constructor
	private FloatArgs() {
	}

    /**
     * This is a convenience method for {@link DoubleArgs#checkPositive(double, String)}.
     * 
     * @see #checkNotPositive(float, String)
     * @see #checkNegative(float, String)
     * @see #checkNotNegative(float, String)
     */
    public static float checkPositive(float value, String argName) {
        DoubleArgs.checkPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link DoubleArgs#checkNotPositive(double, String)}.
     * 
     * @see #checkPositive(float, String)
     * @see #checkNegative(float, String)
     * @see #checkNotNegative(float, String)
     */
    public static float checkNotPositive(float value, String argName) {
        DoubleArgs.checkNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link DoubleArgs#checkNegative(double, String)}.
     * 
     * @see #checkPositive(float, String)
     * @see #checkNotPositive(float, String)
     * @see #checkNotNegative(float, String)
     */
    public static float checkNegative(float value, String argName) {
        DoubleArgs.checkNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link DoubleArgs#checkNotNegative(double, String)}.
     * 
     * @see #checkPositive(float, String)
     * @see #checkNotPositive(float, String)
     * @see #checkNegative(float, String)
     */
    public static float checkNotNegative(float value, String argName) {
        DoubleArgs.checkNotNegative(value, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(float, float, String)
     * @see #checkMaxValue(float, float, String)
     * @see #checkExactValue(float, float, String)
     */
    public static float checkValueRange(
            float value, float minValue, float maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(float, float, float, String)
     * @see #checkMaxValue(float, float, String)
     * @see #checkExactValue(float, float, String)
     */
    public static float checkMinValue(float value, float minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(float, float, float, String)
     * @see #checkMinValue(float, float, String)
     * @see #checkExactValue(float, float, String)
     */
    public static float checkMaxValue(float value, float maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(float, float, float, String)
     * @see #checkMinValue(float, float, String)
     * @see #checkMaxValue(float, float, String)
     */
    public static float checkExactValue(float value, float exactValue, String argName) {
        ComparableArgs.checkValueRange(value, exactValue, exactValue, argName);
        return value;
    }
}
