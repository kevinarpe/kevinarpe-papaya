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

public final class ShortArgUtil {

	/**
	 * This is a convenience method for {@link LongArgUtil#staticCheckPositive(long, String)}.
	 * 
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static short staticCheckPositive(short value, String argName) {
		LongArgUtil.staticCheckPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link LongArgUtil#staticCheckNotPositive(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static short staticCheckNotPositive(short value, String argName) {
		LongArgUtil.staticCheckNotPositive(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link LongArgUtil#staticCheckNegative(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNotNegative(byte, String)
	 */
	public static short staticCheckNegative(short value, String argName) {
		LongArgUtil.staticCheckNegative(value, argName);
		return value;
	}
	
	/**
	 * This is a convenience method for {@link #staticCheckNotNegative(long, String)}.
	 * 
	 * @see #staticCheckPositive(byte, String)
	 * @see #staticCheckNotPositive(byte, String)
	 * @see #staticCheckNegative(byte, String)
	 */
	public static short staticCheckNotNegative(short value, String argName) {
		LongArgUtil.staticCheckNotNegative(value, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckMinValue(short, short, String)
	 * @see #staticCheckMaxValue(short, short, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static short staticCheckRange(
			short value, short minValue, short maxValue, String argName) {
		ComparableArgUtil.staticCheckRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(short, short, short, String)
	 * @see #staticCheckMaxValue(short, short, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static short staticCheckMinValue(short value, short minValue, String argName) {
		ComparableArgUtil.staticCheckMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(short, short, short, String)
	 * @see #staticCheckMinValue(short, short, String)
	 * @see #staticCheckExactValue(short, short, String)
	 */
	public static short staticCheckMaxValue(short value, short maxValue, String argName) {
		ComparableArgUtil.staticCheckMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckExactValue(Comparable, Comparable, String)}
	 * where {@code minValue = null} and {@code maxValue = null}.
	 * 
	 * @see #staticCheckRange(short, short, short, String)
	 * @see #staticCheckMinValue(short, short, String)
	 * @see #staticCheckMaxValue(short, short, String)
	 */
	public static short staticCheckExactValue(short value, short exactValue, String argName) {
		ComparableArgUtil.staticCheckExactValue(value, exactValue, argName);
		return value;
	}
}
