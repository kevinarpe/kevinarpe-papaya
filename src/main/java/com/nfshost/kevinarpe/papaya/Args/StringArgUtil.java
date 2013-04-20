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

public final class StringArgUtil {

	/**
	 * Tests if a string reference is neither null nor empty.
	 * 
	 * @param ref a string reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code ref} is empty
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckNotEmptyOrWhitespace(CharSequence, String)
	 */
	public static <T extends CharSequence> T staticCheckNotEmpty(T ref, String argName) {
		ObjectArgUtil.staticCheckNotNull(ref, argName);
		int len = ref.length();
		if (0 == len) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s' is an empty string", argName));
		}
		return ref;
	}
	
	/**
	 * Tests if a string reference is neither (a) null, (b) empty, nor (c) only whitespace.
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
	 * whitespace chars used in East Asian languages.
	 * 
	 * @param ref a string reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code ref} is empty or only whitespace
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckNotEmpty(CharSequence, String)
	 */
	public static <T extends CharSequence> T staticCheckNotEmptyOrWhitespace(
			T ref, String argName) {
		staticCheckNotEmpty(ref, argName);
		int len = ref.length();
		for (int i = 0; i < len; ++i) {
			char ch = ref.charAt(i);
			if (!Character.isWhitespace(ch)) {
				return ref;
			}
		}
		throw new IllegalArgumentException(String.format(
			"Argument '%s' is all whitespace: '%s'", argName, ref));
	}
	
	/**
	 * Tests if a string reference is not null and its length within specified range.
	 * Length is defined as the number of characters in a CharSequence reference.
	 * Kindly remember that characters in East Asian languages are frequently two bytes.
	 * 
	 * @param ref a string reference
	 * @param minLen minimum number of chars (inclusive).  Must be non-negative. 
	 * @param maxLen maximum number of chars (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minLen < 0},
	 *         or if {@code maxLen < 0},
	 *         or if {@code minLen > maxLen}, 
	 *         or if number of chars in {@code ref} is outside allowed range
	 * @see ObjectArgUtil#staticCheckNotNull(Object, String)
	 * @see #staticCheckNotEmpty(CharSequence, String)
	 * @see #staticCheckNotEmptyOrWhitespace(CharSequence, String)
	 * @see #staticCheckMinLength(CharSequence, int, String)
	 * @see #staticCheckMaxLength(CharSequence, int, String)
	 * @see #staticCheckExactLength(CharSequence, int, String)
	 */
	public static <T extends CharSequence> T staticCheckLengthRange(
			T ref, int minLen, int maxLen, String argName) {
		IntArgUtil.staticCheckNotNegative(minLen, "minLen");
		IntArgUtil.staticCheckNotNegative(maxLen, "maxLen");
		_staticCheckLengthRangeCore(ref, minLen, maxLen, argName);
		return ref;
	}
	
	private static <T extends CharSequence> void _staticCheckLengthRangeCore(
			T ref, int minLen, int maxLen, String argName) {
		ObjectArgUtil.staticCheckNotNull(ref, argName);
		if (-1 != minLen && -1 != maxLen && minLen > maxLen) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': 'minLen' > 'maxLen': %d > %d", argName, minLen, maxLen));
		}
		int len = ref.length();
		if (-1 != minLen && len < minLen) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': length() < 'minLen': %d < %d%n\tValue: [%s]",
				argName, len, minLen, ref));
		}
		if (-1 != maxLen && len > maxLen) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': length() > 'maxLen': %d > %d%n\tValue: [%s]",
				argName, len, maxLen, ref));
		}
	}
	
	/**
	 * Tests if a string reference is not null and has a minimum length.
	 * Length is defined as the number of characters in a CharSequence reference.
	 * Kindly remember that characters in East Asian languages are frequently two bytes.
	 * 
	 * @param ref a string reference
	 * @param minLen minimum number of chars (inclusive).  Must be non-negative. 
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minLen < 0},
	 *         or if number of chars in {@code ref} is outside allowed range
	 * @see #staticCheckLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckMinLength(
			T ref, int minLen, String argName) {
		IntArgUtil.staticCheckNotNegative(minLen, "minLen");
		int maxLen = -1;
		_staticCheckLengthRangeCore(ref, minLen, maxLen, argName);
		return ref;
	}
	
	/**
	 * Tests if a string reference is not null and has a maximum length.
	 * Length is defined as the number of characters in a CharSequence reference.
	 * Kindly remember that characters in East Asian languages are frequently two bytes.
	 * 
	 * @param ref a string reference
	 * @param maxLen maximum number of chars (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code maxLen < 0},
	 *         or if number of chars in {@code ref} is outside allowed range
	 * @see #staticCheckLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckMaxLength(
			T ref, int maxLen, String argName) {
		IntArgUtil.staticCheckNotNegative(maxLen, "maxLen");
		int minLen = -1;
		_staticCheckLengthRangeCore(ref, minLen, maxLen, argName);
		return ref;
	}
	
	/**
	 * Tests if a string reference is not null and has an exact length.
	 * Length is defined as the number of characters in a CharSequence reference.
	 * Kindly remember that characters in East Asian languages are frequently two bytes.
	 * 
	 * @param ref a string reference
	 * @param exactLen exact number of chars (inclusive).  Must be non-negative. 
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code exactLen < 0},
	 *         or if number of chars in {@code ref} is outside allowed range
	 * @see #staticCheckLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckExactLength(
			T ref, int exactLen, String argName) {
		IntArgUtil.staticCheckNotNegative(exactLen, "exactLen");
		_staticCheckLengthRangeCore(ref, exactLen, exactLen, argName);
		return ref;
	}
}
