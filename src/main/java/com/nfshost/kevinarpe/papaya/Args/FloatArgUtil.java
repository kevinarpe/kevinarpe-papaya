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

public class FloatArgUtil {

	/**
	 * This is a convenience method for {@link DoubleArgUtil#staticCheckPositive(double, String)}.
	 * 
	 * @see #staticCheckNotPositive(double, String)
	 * @see #staticCheckNegative(double, String)
	 * @see #staticCheckNotNegative(double, String)
	 */
	public static float staticCheckPositive(float value, String argName) {
		DoubleArgUtil.staticCheckPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link DoubleArgUtil#staticCheckNotPositive(double, String)}.
	 * 
	 * @see #staticCheckPositive(double, String)
	 * @see #staticCheckNegative(double, String)
	 * @see #staticCheckNotNegative(double, String)
	 */
	public static float staticCheckNotPositive(float value, String argName) {
		DoubleArgUtil.staticCheckNotPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link DoubleArgUtil#staticCheckNegative(double, String)}.
	 * 
	 * @see #staticCheckPositive(double, String)
	 * @see #staticCheckNotPositive(double, String)
	 * @see #staticCheckNotNegative(double, String)
	 */
	public static float staticCheckNegative(float value, String argName) {
		DoubleArgUtil.staticCheckNegative(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link DoubleArgUtil#staticCheckNotNegative(double, String)}.
	 * 
	 * @see #staticCheckPositive(double, String)
	 * @see #staticCheckNotPositive(double, String)
	 * @see #staticCheckNegative(double, String)
	 */
	public static float staticCheckNotNegative(float value, String argName) {
		DoubleArgUtil.staticCheckNotNegative(value, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckMinValue(float, float, String)
	 * @see #staticCheckMaxValue(float, float, String)
	 * @see #staticCheckExactValue(float, float, String)
	 */
	public static float staticCheckRange(
			float value, float minValue, float maxValue, String argName) {
		ComparableArgUtil.staticCheckRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(float, float, float, String)
	 * @see #staticCheckMaxValue(float, float, String)
	 * @see #staticCheckExactValue(float, float, String)
	 */
	public static float staticCheckMinValue(float value, float minValue, String argName) {
		ComparableArgUtil.staticCheckMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(float, float, float, String)
	 * @see #staticCheckMinValue(float, float, String)
	 * @see #staticCheckExactValue(float, float, String)
	 */
	public static float staticCheckMaxValue(float value, float maxValue, String argName) {
		ComparableArgUtil.staticCheckMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(float, float, float, String)
	 * @see #staticCheckMinValue(float, float, String)
	 * @see #staticCheckMaxValue(float, float, String)
	 */
	public static float staticCheckExactValue(float value, float exactValue, String argName) {
		ComparableArgUtil.staticCheckRange(value, exactValue, exactValue, argName);
		return value;
	}
}
