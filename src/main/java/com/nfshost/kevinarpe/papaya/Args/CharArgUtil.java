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

public class CharArgUtil {

	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckMinValue(char, char, String)
	 * @see #staticCheckMaxValue(char, char, String)
	 * @see #staticCheckExactValue(char, char, String)
	 */
	public static char staticCheckRange(
			char value, char minValue, char maxValue, String argName) {
		ComparableArgUtil.staticCheckRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(char, char, char, String)
	 * @see #staticCheckMaxValue(char, char, String)
	 * @see #staticCheckExactValue(char, char, String)
	 */
	public static char staticCheckMinValue(char value, char minValue, String argName) {
		ComparableArgUtil.staticCheckMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(char, char, char, String)
	 * @see #staticCheckMinValue(char, char, String)
	 * @see #staticCheckExactValue(char, char, String)
	 */
	public static char staticCheckMaxValue(char value, char maxValue, String argName) {
		ComparableArgUtil.staticCheckMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link ComparableArgUtil#staticCheckExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckRange(char, char, char, String)
	 * @see #staticCheckMinValue(char, char, String)
	 * @see #staticCheckMaxValue(char, char, String)
	 */
	public static char staticCheckExactValue(char value, char exactValue, String argName) {
		ComparableArgUtil.staticCheckExactValue(value, exactValue, argName);
		return value;
	}
}
