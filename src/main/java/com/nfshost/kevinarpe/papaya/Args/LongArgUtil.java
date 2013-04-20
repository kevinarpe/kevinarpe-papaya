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

public final class LongArgUtil {

	/**
	 * Tests if {@code value > 0}
	 * 
	 * @param value a value to test
	 * @param argName argument name for {@code value}, e.g., "strListSize" or "searchRegexLength"
	 * @return the validated value
	 * @throws NullPointerException if {@code argName} is null
	 * @throws IllegalArgumentException if value of {@code value <= 0}
	 * @see #staticCheckNotPositive(long, String)
	 * @see #staticCheckNegative(long, String)
	 * @see #staticCheckNotNegative(long, String)
	 */
	public static long staticCheckPositive(long value, String argName) {
		ObjectArgUtil.staticCheckNotNull(argName, "argName");
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
	 * @see #staticCheckPositive(long, String)
	 * @see #staticCheckNegative(long, String)
	 * @see #staticCheckNotNegative(long, String)
	 */
	public static long staticCheckNotPositive(long value, String argName) {
		ObjectArgUtil.staticCheckNotNull(argName, "argName");
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
	 * @see #staticCheckPositive(long, String)
	 * @see #staticCheckNotPositive(long, String)
	 * @see #staticCheckNotNegative(long, String)
	 */
	public static long staticCheckNegative(long value, String argName) {
		ObjectArgUtil.staticCheckNotNull(argName, "argName");
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
	 * @see #staticCheckPositive(long, String)
	 * @see #staticCheckNotPositive(long, String)
	 * @see #staticCheckNegative(long, String)
	 */
	public static long staticCheckNotNegative(long value, String argName) {
		ObjectArgUtil.staticCheckNotNull(argName, "argName");
		if (value < 0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is negative: %d",
					argName, value));
		}
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckMinValue(long, long, String)
	 * @see #staticCheckMaxValue(long, long, String)
	 * @see #staticCheckExactValue(long, long, String)
	 */
	public static long staticCheckLongRange(
			long value, long minValue, long maxValue, String argName) {
		ComparableArgUtil.staticCheckRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckMaxValue(long, long, String)
	 * @see #staticCheckExactValue(long, long, String)
	 */
	public static long staticCheckMinValue(long value, long minValue, String argName) {
		ComparableArgUtil.staticCheckMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckMinValue(long, long, String)
	 * @see #staticCheckExactValue(long, long, String)
	 */
	public static long staticCheckMaxValue(long value, long maxValue, String argName) {
		ComparableArgUtil.staticCheckMaxValue(value, maxValue, argName);
		return value;
	}

	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckMinValue(long, long, String)
	 * @see #staticCheckMaxValue(long, long, String)
	 */
	public static long staticCheckExactValue(long value, long exactValue, String argName) {
		ComparableArgUtil.staticCheckExactValue(value, exactValue, argName);
		return value;
	}
}
