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

public final class IntArgs {

	/**
	 * This is a convenience method for {@link LongArgs#staticCheckPositive(long, String)}.
	 * 
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static int staticCheckPositive(int value, String argName) {
		LongArgs.staticCheckPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link LongArgs#staticCheckNotPositive(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static int staticCheckNotPositive(int value, String argName) {
		LongArgs.staticCheckNotPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link LongArgs#staticCheckNegative(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static int staticCheckNegative(int value, String argName) {
		LongArgs.staticCheckNegative(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link LongArgs#staticCheckNotNegative(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 */
	public static int staticCheckNotNegative(int value, String argName) {
		LongArgs.staticCheckNotNegative(value, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgs#staticCheckValueRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckMinValue(int, int, String)
	 * @see #staticCheckMaxValue(int, int, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static int staticCheckValueRange(
			int value, int minValue, int maxValue, String argName) {
		ComparableArgs.staticCheckValueRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgs#staticCheckMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckValueRange(int, int, int, String)
	 * @see #staticCheckMaxValue(int, int, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static int staticCheckMinValue(int value, int minValue, String argName) {
		ComparableArgs.staticCheckMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgs#staticCheckMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckValueRange(int, int, int, String)
	 * @see #staticCheckMinValue(int, int, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static int staticCheckMaxValue(int value, int maxValue, String argName) {
		ComparableArgs.staticCheckMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgs#staticCheckExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckValueRange(int, int, int, String)
	 * @see #staticCheckMinValue(int, int, String)
	 * @see #staticCheckMaxValue(int, int, String)
	 */
	public static int staticCheckExactValue(int value, int exactValue, String argName) {
		ComparableArgs.staticCheckExactValue(value, exactValue, argName);
		return value;
	}
}
