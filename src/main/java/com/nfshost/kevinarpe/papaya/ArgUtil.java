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

package com.nfshost.kevinarpe.papaya;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a collection of static methods to check arguments received in methods.
 * The goal is provide a wide range of checks that produce readable, detailed errors.
 * 
 * <p>Unless noted, all methods throw unchecked exceptions -- {@link RuntimeException}
 * and its subclasses.  Most frequently, {@link IllegalArgumentException} is thrown.
 * 
 * <pre>
 *     public void myMethod(List<String> strList) {
 *         ArgUtil.staticCheckNotNull(strList, "strList");
 *         // Do work here.
 *     }</pre>
 * 
 * I have written similiar libraries in Perl, Python, VBA, C, C++, Java, and C# over the years.
 * It's time to put this into open source.
 * 
 * @author kevinarpe@gmail.com
 */
public final class ArgUtil {
	
	private static final Pattern _ONLY_WHITESPACE_REGEX;
	
	static {
		_ONLY_WHITESPACE_REGEX = Pattern.compile("^\\p{javaWhitespace}+$");
	}
	
	/**
	 * Tests if an object reference passed as an argument is not null.
	 * 
	 * @param ref an object reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated object reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 */
	public static <T> T staticCheckNotNull(T ref, String argName) {
		if (null == argName) {
			throw new NullPointerException(String.format("Argument name (argName) is null"));
		}
		if (null == ref) {
			throw new NullPointerException(String.format("Argument '%s' is null", argName));
		}
		return ref;
	}
	
	/**
	 * Tests if a string reference is neither null nor empty.
	 * 
	 * @param ref a string reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code ref} is empty
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckStringNotEmptyOrWhitespace(CharSequence, String)
	 */
	public static <T extends CharSequence> T staticCheckStringNotEmpty(T ref, String argName) {
		staticCheckNotNull(ref, argName);
		int len = ref.length();
		if (0 == len) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s' is an empty string", argName));
		}
		return ref;
	}
	
	/**
	 * Tests if a string reference is neither (a) null, (b) empty, nor (c) only whitespace.
	 * 
	 * <p>Whitespace is defined by the regular expression, "^\p{javaWhitespace}+$", which will
	 * include all special whitespace chars used in East Asian langauges.  Unfortunately, Java
	 * defines \s incredibly narrow, so as to only include ASCII chars!
	 * 
	 * @param ref a string reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated string reference
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code ref} is empty or only whitespace
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckStringNotEmpty(CharSequence, String)
	 */
	public static <T extends CharSequence> T staticCheckStringNotEmptyOrWhitespace(
			T ref, String argName) {
		staticCheckStringNotEmpty(ref, argName);
		Matcher m = _ONLY_WHITESPACE_REGEX.matcher(ref);
		if (m.matches()) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s' is all whitespace: [%s]", ref));
		}
		return ref;
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
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckStringNotEmpty(CharSequence, String)
	 * @see #staticCheckStringNotEmptyOrWhitespace(CharSequence, String)
	 * @see #staticCheckStringMinLength(CharSequence, int, String)
	 * @see #staticCheckStringMaxLength(CharSequence, int, String)
	 * @see #staticCheckStringExactLength(CharSequence, int, String)
	 */
	public static <T extends CharSequence> T staticCheckStringLengthRange(
			T ref, int minLen, int maxLen, String argName) {
		staticCheckIntNotNegative(minLen, "minLen");
		staticCheckIntNotNegative(maxLen, "maxLen");
		_staticCheckStringLengthRangeCore(ref, minLen, maxLen, argName);
		return ref;
	}
	
	private static <T extends CharSequence> void _staticCheckStringLengthRangeCore(
			T ref, int minLen, int maxLen, String argName) {
		staticCheckNotNull(ref, argName);
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
	 * @see #staticCheckStringLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckStringMinLength(
			T ref, int minLen, String argName) {
		staticCheckIntNotNegative(minLen, "minLen");
		int maxLen = -1;
		_staticCheckStringLengthRangeCore(ref, minLen, maxLen, argName);
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
	 * @see #staticCheckStringLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckStringMaxLength(
			T ref, int maxLen, String argName) {
		staticCheckIntNotNegative(maxLen, "maxLen");
		int minLen = -1;
		_staticCheckStringLengthRangeCore(ref, minLen, maxLen, argName);
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
	 * @see #staticCheckStringLengthRange(CharSequence, int, int, String)
	 */
	public static <T extends CharSequence> T staticCheckStringExactLength(
			T ref, int exactLen, String argName) {
		staticCheckIntNotNegative(exactLen, "exactLen");
		_staticCheckStringLengthRangeCore(ref, exactLen, exactLen, argName);
		return ref;
	}
	
	/**
	 * Tests if a Comparable reference is not null and its value within specified range.
	 * 
	 * @param ref an object reference
	 * @param minValue minimum value (inclusive)
	 * @param maxValue maximum value (inclusive)
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated object reference
	 * @throws NullPointerException if {@code ref}, {@code minValue}, {@code maxValue},
	 *         or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minValue > maxValue},
	 *         or if value of {@code ref} is outside allowed range
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckComparableMinValue(Comparable, Comparable, String)
	 * @see #staticCheckComparableMaxValue(Comparable, Comparable, String)
	 * @see #staticCheckComparableExactValue(Comparable, Comparable, String)
	 */
	public static <T extends Comparable<T>> T staticCheckComparableRange(
			T ref, T minValue, T maxValue, String argName) {
		staticCheckNotNull(minValue, "minValue");
		staticCheckNotNull(maxValue, "maxValue");
		_staticCheckComparableRangeCore(ref, minValue, maxValue, argName);		
		return ref;
	}
	
	private static <T extends Comparable<T>> void _staticCheckComparableRangeCore(
			T ref, T optMinValue, T optMaxValue, String argName) {
		staticCheckNotNull(ref, argName);
		if (null != optMinValue &&
				null != optMaxValue &&
				optMinValue.compareTo(optMaxValue) > 0) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': 'minValue' > 'maxValue': %s > %s",
				argName, optMinValue, optMaxValue));
		}
		if (null != optMinValue && ref.compareTo(optMinValue) < 0) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': value < 'minValue': %s < %s",
				argName, ref, optMinValue));
		}
		if (null != optMaxValue && ref.compareTo(optMaxValue) > 0) {
			throw new IllegalArgumentException(String.format(
				"Argument '%s': value > 'maxValue': %s > %s",
				argName, ref, optMaxValue));
		}
	}
	
	/**
	 * Tests if a Comparable reference is not null and has a minimum value.
	 * 
	 * @param ref an object reference
	 * @param minValue minimum value (inclusive)
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated object reference
	 * @throws NullPointerException if {@code ref}, {@code minValue},
	 *         or {@code argName} is null
	 * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
	 * @see #staticCheckComparableRange(Comparable, Comparable, Comparable, String)
	 */
	public static <T extends Comparable<T>> T staticCheckComparableMinValue(
			T ref, T minValue, String argName) {
		staticCheckNotNull(minValue, "minValue");
		T maxValue = null;
		_staticCheckComparableRangeCore(ref, minValue, maxValue, argName);		
		return ref;
	}
	
	/**
	 * Tests if a Comparable reference is not null and has a maximum value.
	 * 
	 * @param ref an object reference
	 * @param maxValue maximum value (inclusive)
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated object reference
	 * @throws NullPointerException if {@code ref}, {@code maxValue},
	 *         or {@code argName} is null
	 * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
	 * @see #staticCheckComparableRange(Comparable, Comparable, Comparable, String)
	 */
	public static <T extends Comparable<T>> T staticCheckComparableMaxValue(
			T ref, T maxValue, String argName) {
		staticCheckNotNull(maxValue, "maxValue");
		T minValue = null;
		_staticCheckComparableRangeCore(ref, minValue, maxValue, argName);		
		return ref;
	}
	
	/**
	 * Tests if a Comparable reference is not null and has an exact value.
	 * 
	 * @param ref an object reference
	 * @param maxValue maximum value (inclusive)
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @return the validated object reference
	 * @throws NullPointerException if {@code ref}, {@code maxValue},
	 *         or {@code argName} is null
	 * @throws IllegalArgumentException if value of {@code ref} is outside allowed range
	 * @see #staticCheckComparableRange(Comparable, Comparable, Comparable, String)
	 */
	public static <T extends Comparable<T>> T staticCheckComparableExactValue(
			T ref, T exactValue, String argName) {
		staticCheckNotNull(exactValue, "exactValue");
		_staticCheckComparableRangeCore(ref, exactValue, exactValue, argName);		
		return ref;
	}
	
	public static byte staticCheckBytePositive(byte value, String argName) {
		staticCheckLongPositive(value, argName);
		return value;
	}
	
	public static byte staticCheckByteNotPositive(byte value, String argName) {
		staticCheckLongNotPositive(value, argName);
		return value;
	}
	
	public static byte staticCheckByteNegative(byte value, String argName) {
		staticCheckLongNegative(value, argName);
		return value;
	}
	
	public static byte staticCheckByteNotNegative(byte value, String argName) {
		staticCheckLongNotNegative(value, argName);
		return value;
	}
	
	public static short staticCheckShortPositive(short value, String argName) {
		staticCheckLongPositive(value, argName);
		return value;
	}
	
	public static short staticCheckShortNotPositive(short value, String argName) {
		staticCheckLongNotPositive(value, argName);
		return value;
	}
	
	public static short staticCheckShortNegative(short value, String argName) {
		staticCheckLongNegative(value, argName);
		return value;
	}
	
	public static short staticCheckShortNotNegative(short value, String argName) {
		staticCheckLongNotNegative(value, argName);
		return value;
	}
	
	public static int staticCheckIntPositive(int value, String argName) {
		staticCheckLongPositive(value, argName);
		return value;
	}
	
	public static int staticCheckIntNotPositive(int value, String argName) {
		staticCheckLongNotPositive(value, argName);
		return value;
	}
	
	public static int staticCheckIntNegative(int value, String argName) {
		staticCheckLongNegative(value, argName);
		return value;
	}
	
	public static int staticCheckIntNotNegative(int value, String argName) {
		staticCheckLongNotNegative(value, argName);
		return value;
	}
	
	public static long staticCheckLongPositive(long value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value <= 0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is not positive: %d",
					argName, value));
		}
		return value;
	}
	
	public static long staticCheckLongNotPositive(long value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value > 0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is positive: %d",
					argName, value));
		}
		return value;
	}
	
	public static long staticCheckLongNegative(long value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value >= 0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is not negative: %d",
					argName, value));
		}
		return value;
	}
	
	public static long staticCheckLongNotNegative(long value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value < 0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is negative: %d",
					argName, value));
		}
		return value;
	}
	
	public static float staticCheckFloatPositive(float value, String argName) {
		staticCheckDoublePositive(value, argName);
		return value;
	}
	
	public static float staticCheckFloatNotPositive(float value, String argName) {
		staticCheckDoubleNotPositive(value, argName);
		return value;
	}
	
	public static float staticCheckFloatNegative(float value, String argName) {
		staticCheckDoubleNegative(value, argName);
		return value;
	}
	
	public static float staticCheckFloatNotNegative(float value, String argName) {
		staticCheckDoubleNotNegative(value, argName);
		return value;
	}
	
	public static double staticCheckDoublePositive(double value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value <= 0.0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is not positive: %f",
					argName, value));
		}
		return value;
	}
	
	public static double staticCheckDoubleNotPositive(double value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value > 0.0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is positive: %f",
					argName, value));
		}
		return value;
	}
	
	public static double staticCheckDoubleNegative(double value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value >= 0.0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is not negative: %f",
					argName, value));
		}
		return value;
	}
	
	public static double staticCheckDoubleNotNegative(double value, String argName) {
		staticCheckNotNull(argName, "argName");
		if (value < 0.0) {
			throw new IllegalArgumentException(String.format(
					"Argument '%s': value is negative: %f",
					argName, value));
		}
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckByteMinValue(byte, byte, String)
	 * @see #staticCheckByteMaxValue(byte, byte, String)
	 * @see #staticCheckByteExactValue(byte, byte, String)
	 */
	public static byte staticCheckByteRange(
			byte value, byte minValue, byte maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckByteRange(byte, byte, byte, String)
	 * @see #staticCheckByteMaxValue(byte, byte, String)
	 * @see #staticCheckByteExactValue(byte, byte, String)
	 */
	public static byte staticCheckByteMinValue(byte value, byte minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckByteRange(byte, byte, byte, String)
	 * @see #staticCheckByteMinValue(byte, byte, String)
	 * @see #staticCheckByteExactValue(byte, byte, String)
	 */
	public static byte staticCheckByteMaxValue(byte value, byte maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckByteRange(byte, byte, byte, String)
	 * @see #staticCheckByteMinValue(byte, byte, String)
	 * @see #staticCheckByteMaxValue(byte, byte, String)
	 */
	public static byte staticCheckByteExactValue(byte value, byte exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckShortMinValue(short, short, String)
	 * @see #staticCheckShortMaxValue(short, short, String)
	 * @see #staticCheckShortExactValue(short, short, String)
	 */
	public static short staticCheckShortRange(
			short value, short minValue, short maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckShortRange(short, short, short, String)
	 * @see #staticCheckShortMaxValue(short, short, String)
	 * @see #staticCheckShortExactValue(short, short, String)
	 */
	public static short staticCheckShortMinValue(short value, short minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckShortRange(short, short, short, String)
	 * @see #staticCheckShortMinValue(short, short, String)
	 * @see #staticCheckShortExactValue(short, short, String)
	 */
	public static short staticCheckShortMaxValue(short value, short maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}
	 * where {@code minValue = null} and {@code maxValue = null}.
	 * 
	 * @see #staticCheckShortRange(short, short, short, String)
	 * @see #staticCheckShortMinValue(short, short, String)
	 * @see #staticCheckShortMaxValue(short, short, String)
	 */
	public static short staticCheckShortExactValue(short value, short exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckIntMinValue(int, int, String)
	 * @see #staticCheckIntMaxValue(int, int, String)
	 * @see #staticCheckIntExactValue(short, short, String)
	 */
	public static int staticCheckIntRange(
			int value, int minValue, int maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckIntRange(int, int, int, String)
	 * @see #staticCheckIntMaxValue(int, int, String)
	 * @see #staticCheckIntExactValue(short, short, String)
	 */
	public static int staticCheckIntMinValue(int value, int minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckIntRange(int, int, int, String)
	 * @see #staticCheckIntMinValue(int, int, String)
	 * @see #staticCheckIntExactValue(short, short, String)
	 */
	public static int staticCheckIntMaxValue(int value, int maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckIntRange(int, int, int, String)
	 * @see #staticCheckIntMinValue(int, int, String)
	 * @see #staticCheckIntMaxValue(int, int, String)
	 */
	public static int staticCheckIntExactValue(int value, int exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongMinValue(long, long, String)
	 * @see #staticCheckLongMaxValue(long, long, String)
	 * @see #staticCheckLongExactValue(long, long, String)
	 */
	public static long staticCheckLongRange(
			long value, long minValue, long maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckLongMaxValue(long, long, String)
	 * @see #staticCheckLongExactValue(long, long, String)
	 */
	public static long staticCheckLongMinValue(long value, long minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckLongMinValue(long, long, String)
	 * @see #staticCheckLongExactValue(long, long, String)
	 */
	public static long staticCheckLongMaxValue(long value, long maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}

	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckLongRange(long, long, long, String)
	 * @see #staticCheckLongMinValue(long, long, String)
	 * @see #staticCheckLongMaxValue(long, long, String)
	 */
	public static long staticCheckLongExactValue(long value, long exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}

	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckFloatMinValue(float, float, String)
	 * @see #staticCheckFloatMaxValue(float, float, String)
	 * @see #staticCheckFloatExactValue(float, float, String)
	 */
	public static float staticCheckFloatRange(
			float value, float minValue, float maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckFloatRange(float, float, float, String)
	 * @see #staticCheckFloatMaxValue(float, float, String)
	 * @see #staticCheckFloatExactValue(float, float, String)
	 */
	public static float staticCheckFloatMinValue(float value, float minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckFloatRange(float, float, float, String)
	 * @see #staticCheckFloatMinValue(float, float, String)
	 * @see #staticCheckFloatExactValue(float, float, String)
	 */
	public static float staticCheckFloatMaxValue(float value, float maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckFloatRange(float, float, float, String)
	 * @see #staticCheckFloatMinValue(float, float, String)
	 * @see #staticCheckFloatMaxValue(float, float, String)
	 */
	public static float staticCheckFloatExactValue(float value, float exactValue, String argName) {
		staticCheckComparableRange(value, exactValue, exactValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckDoubleMinValue(double, double, String)
	 * @see #staticCheckDoubleMaxValue(double, double, String)
	 * @see #staticCheckDoubleExactValue(double, double, String)
	 */
	public static double staticCheckDoubleRange(
			double value, double minValue, double maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckDoubleRange(double, double, double, String)
	 * @see #staticCheckDoubleMaxValue(double, double, String)
	 * @see #staticCheckDoubleExactValue(double, double, String)
	 */
	public static double staticCheckDoubleMinValue(double value, double minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckDoubleRange(double, double, double, String)
	 * @see #staticCheckDoubleMinValue(double, double, String)
	 * @see #staticCheckDoubleExactValue(double, double, String)
	 */
	public static double staticCheckDoubleMaxValue(double value, double maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckDoubleRange(double, double, double, String)
	 * @see #staticCheckDoubleMinValue(double, double, String)
	 * @see #staticCheckDoubleMaxValue(double, double, String)
	 */
	public static double staticCheckDoubleExactValue(
			double value, double exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableRange(Comparable, Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckCharMinValue(char, char, String)
	 * @see #staticCheckCharMaxValue(char, char, String)
	 * @see #staticCheckCharExactValue(char, char, String)
	 */
	public static char staticCheckCharRange(
			char value, char minValue, char maxValue, String argName) {
		staticCheckComparableRange(value, minValue, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMinValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckCharRange(char, char, char, String)
	 * @see #staticCheckCharMaxValue(char, char, String)
	 * @see #staticCheckCharExactValue(char, char, String)
	 */
	public static char staticCheckCharMinValue(char value, char minValue, String argName) {
		staticCheckComparableMinValue(value, minValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableMaxValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckCharRange(char, char, char, String)
	 * @see #staticCheckCharMinValue(char, char, String)
	 * @see #staticCheckCharExactValue(char, char, String)
	 */
	public static char staticCheckCharMaxValue(char value, char maxValue, String argName) {
		staticCheckComparableMaxValue(value, maxValue, argName);
		return value;
	}
	
	/**
	 * Convenience method to call
	 * {@link #staticCheckComparableExactValue(Comparable, Comparable, String)}.
	 * 
	 * @see #staticCheckCharRange(char, char, char, String)
	 * @see #staticCheckCharMinValue(char, char, String)
	 * @see #staticCheckCharMaxValue(char, char, String)
	 */
	public static char staticCheckCharExactValue(char value, char exactValue, String argName) {
		staticCheckComparableExactValue(value, exactValue, argName);
		return value;
	}
	
	/**
	 * Tests if a collection reference is not null and its size within specified range.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param minSize minimum number of elements (inclusive).  Must be non-negative.
	 * @param maxSize maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minSize < 0},
	 *         or if {@code maxSize < 0},
	 *         or if {@code minSize > maxSize}, 
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckCollectionMinSize(Collection, int, String)
	 * @see #staticCheckCollectionMaxSize(Collection, int, String)
	 * @see #staticCheckCollectionExactSize(Collection, int, String)
	 */
	public static <T> void staticCheckCollectionSizeRange(
			Collection<T> ref, int minSize, int maxSize, String argName) {
		staticCheckIntNotNegative(minSize, "minSize");
		staticCheckIntNotNegative(maxSize, "maxSize");
		int size = (null == ref ? -1 : ref.size());
		_staticCheckContainerSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has a minimum size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param minSize minimum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckCollectionSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckCollectionMinSize(
			Collection<T> ref, int minSize, String argName) {
		staticCheckIntNotNegative(minSize, "minSize");
		int size = (null == ref ? -1 : ref.size());
		int maxSize = -1;
		_staticCheckContainerSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has a maximum size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param maxSize maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code maxSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckCollectionSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckCollectionMaxSize(
			Collection<T> ref, int maxSize, String argName) {
		staticCheckIntNotNegative(maxSize, "maxSize");
		int size = (null == ref ? -1 : ref.size());
		int minSize = -1;
		_staticCheckContainerSizeRange(ref, "Collection", size, minSize, maxSize, argName);
	}
	
	/**
	 * Tests if a collection reference is not null and has an exact size.
	 * Size is defined as the number of elements.
	 * 
	 * @param ref a collection reference
	 * @param exactSize exact number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code exactSize < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckCollectionSizeRange(Collection, int, int, String)
	 */
	public static <T> void staticCheckCollectionExactSize(
			Collection<T> ref, int exactSize, String argName) {
		staticCheckIntNotNegative(exactSize, "exactSize");
		int size = (null == ref ? -1 : ref.size());
		_staticCheckContainerSizeRange(ref, "Collection", size, exactSize, exactSize, argName);
	}
	
	/**
	 * Tests if an array reference is not null and its length within specified range.
	 * Length is defined as the number of elements.
	 * 
	 * @param ref an array reference
	 * @param minLen minimum number of elements (inclusive).  Must be non-negative.
	 * @param maxLen maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minLen < 0},
	 *         or if {@code maxLen < 0},
	 *         or if {@code minLen > maxLen}, 
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckArrayLengthRange(Object[], int, int, String)
	 * @see #staticCheckArrayMinLength(Object[], int, String)
	 * @see #staticCheckArrayMaxLength(Object[], int, String)
	 * @see #staticCheckArrayExactLength(Object[], int, String)
	 */
	public static <T> T[] staticCheckArrayLengthRange(
			T[] ref, int minLen, int maxLen, String argName) {
		staticCheckIntNotNegative(minLen, "minLen");
		staticCheckIntNotNegative(maxLen, "maxLen");
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerSizeRange(ref, "Array", len, minLen, maxLen, argName);
		return ref;
	}
	
	/**
	 * Tests if an array reference is not null and has a minimum length.
	 * Length is defined as the number of elements.
	 * 
	 * @param ref an array reference
	 * @param minLen minimum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code minLen < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckArrayLengthRange(Object[], int, int, String)
	 */
	public static <T> T[] staticCheckArrayMinLength(T[] ref, int minLen, String argName) {
		staticCheckIntNotNegative(minLen, "minLen");
		int len = (null == ref ? -1 : ref.length);
		int maxLen = -1;
		_staticCheckContainerSizeRange(ref, "Array", len, minLen, maxLen, argName);
		return ref;
	}
	
	/**
	 * Tests if an array reference is not null and has a maximum length.
	 * Length is defined as the number of elements.
	 * 
	 * @param ref an array reference
	 * @param minLen minimum number of elements (inclusive).  Must be non-negative.
	 * @param maxLen maximum number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code maxLen < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckArrayLengthRange(Object[], int, int, String)
	 */
	public static <T> T[] staticCheckArrayMaxLength(T[] ref, int maxLen, String argName) {
		staticCheckIntNotNegative(maxLen, "maxLen");
		int len = (null == ref ? -1 : ref.length);
		int minLen = -1;
		_staticCheckContainerSizeRange(ref, "Array", len, minLen, maxLen, argName);
		return ref;
	}
	
	/**
	 * Tests if an array reference is not null and has an exact length.
	 * Length is defined as the number of elements.
	 * 
	 * @param ref an array reference
	 * @param exactLen exact number of elements (inclusive).  Must be non-negative.
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @throws NullPointerException if {@code ref} or {@code argName} is null
	 * @throws IllegalArgumentException if {@code exactLen < 0},
	 *         or if number of elements in {@code ref} is outside allowed range
	 * @see #staticCheckArrayLengthRange(Object[], int, int, String)
	 */
	public static <T> T[] staticCheckArrayExactLength(T[] ref, int exactLen, String argName) {
		staticCheckIntNotNegative(exactLen, "exactLen");
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerSizeRange(ref, "Array", len, exactLen, exactLen, argName);
		return ref;
	}
	
	private static void _staticCheckContainerSizeRange(
			Object container,
			String containerType,
			int size,
			int minSize,
			int maxSize,
			String containerArgName) {
		staticCheckNotNull(container, containerArgName);
		if (-1 != minSize && -1 != maxSize && minSize > maxSize) {
			throw new IllegalArgumentException(String.format(
				"%s argument '%s': 'minSize' > 'maxSize': %d > %d",
				containerType, containerArgName, minSize, maxSize));
		}
		if (-1 != minSize && size < minSize) {
			throw new IllegalArgumentException(String.format(
				"%s argument '%s': size < 'minSize': %d < %d",
				containerType, containerArgName, size, minSize));
		}
		if (-1 != maxSize && size > maxSize) {
			throw new IllegalArgumentException(String.format(
				"%s argument '%s': size > 'maxSize': %d > %d",
				containerType, containerArgName, size, maxSize));
		}
	}
	
	/**
	 * Tests if a collection reference is not null and an index is valid to access an element.
	 * 
	 * @param ref a collection reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param listArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.size()}
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckCollectionInsertIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckCollectionAccessIndex(
			Collection<T> ref, int index, String listArgName, String indexArgName) {
		int size = (null == ref ? -1 : ref.size());
		_staticCheckContainerAccessIndex(
			ref, "Collection", size, index, listArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if a collection reference is not null and an index is valid to insert an element.
	 * 
	 * @param ref a collection reference
	 * @param index index of element to insert.  Must be non-negative.
	 * @param listArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index > ref.size()}
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckCollectionInsertIndex(
			Collection<T> ref, int index, String listArgName, String indexArgName) {
		int size = (null == ref ? -1 : ref.size());
		_staticCheckContainerInsertIndex(
			ref, "Collection", size, index, listArgName, indexArgName);
		return index;
	}
	
	private static void _staticCheckContainerAccessIndex(
			Object container,
			String containerType,
			int containerSize,
			int index,
			String containerArgName,
			String indexArgName) {
		staticCheckNotNull(container, containerArgName);
		staticCheckNotNull(indexArgName, "indexArgName");
		_staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
		if (index >= containerSize) {
			throw new IndexOutOfBoundsException(String.format(
					"%s '%s': Index '%s' for access too large: %d >= %d",
					containerType,
					containerArgName,
					indexArgName,
					index,
					containerSize));
		}
	}
	
	private static void _staticCheckContainerInsertIndex(
			Object container,
			String containerType,
			int containerSize,
			int index,
			String containerArgName,
			String indexArgName) {
		staticCheckNotNull(container, containerArgName);
		staticCheckNotNull(indexArgName, "indexArgName");
		_staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
		if (index > containerSize) {
			throw new IndexOutOfBoundsException(String.format(
					"%s '%s': Index '%s' for insert too large: %d > %d",
					containerType,
					containerArgName,
					indexArgName,
					index,
					containerSize));
		}
	}

	private static void _staticCheckIndexNotNegative(
			String containerType, String containerArgName, String indexArgName, int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(String.format(
				"%s '%s': Index '%s' is negative: %d",
				containerType,
				containerArgName,
				indexArgName,
				index));
		}
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckArrayInsertIndex(Object[], int, String, String)
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckArrayAccessIndex(
			T[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to insert an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to insert.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckArrayAccessIndex(Object[], int, String, String)
	 * @see #staticCheckCollectionInsertIndex(Collection, int, String, String)
	 */
	public static <T> int staticCheckArrayInsertIndex(
			T[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerInsertIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			byte[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			short[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			int[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			long[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			float[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			double[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			char[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}
	
	/**
	 * Tests if an array reference is not null and an index is valid to access an element.
	 * 
	 * @param ref an array reference
	 * @param index index of element to access.  Must be non-negative.
	 * @param arrArgName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @param indexArgName argument name for {@code ref}, e.g., "strListIndex"
	 * @return the validated index
	 * @throws NullPointerException if {@code ref}, {@code listArgName},
	 *         or {@code indexArgName} is null
	 * @throws IndexOutOfBoundsException if {@code index < 0},
	 *         or {@code index >= ref.length}
	 * @see #staticCheckCollectionAccessIndex(Collection, int, String, String)
	 */
	public static int staticCheckArrayAccessIndex(
			boolean[] ref, int index, String arrArgName, String indexArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerAccessIndex(
			ref, "Array", len, index, arrArgName, indexArgName);
		return index;
	}

	/**
	 * Tests if a collection reference is not null and each element is not null.
	 * 
	 * @param ref a collection reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckArrayElementsNotNull(Object[], String)
	 */
	public static <T> void staticCheckCollectionElementsNotNull(
			Collection<T> ref, String argName) {
		staticCheckNotNull(ref, argName);
		int count = 0;
		for (T item: ref) {
			if (null == item) {
				throw new NullPointerException(String.format(
					"Collection argument '%s': Item #%d (zero-based) is null", argName, count));
			}
			++count;
		}
	}
	
	/**
	 * Tests if an array reference is not null and each element is not null.
	 * 
	 * @param ref an array reference
	 * @param argName argument name for {@code ref}, e.g., "strList" or "searchRegex"
	 * @see #staticCheckNotNull(Object, String)
	 * @see #staticCheckCollectionElementsNotNull(Object[], String)
	 */
	public static <T> void staticCheckArrayElementsNotNull(T[] ref, String argName) {
		staticCheckNotNull(ref, argName);
		for (int i = 0; i < ref.length; ++i) {
			T item = ref[i];
			if (null == item) {
				throw new NullPointerException(String.format(
					"Array argument '%s': Item #%d (zero-based) is null", argName, i));
			}
		}
	}
	
	public static <T> void staticCheckCollectionIndexAndCount(
			Collection<T> ref,
			int index,
			int count,
			String listArgName,
			String indexArgName,
			String countArgName) {
		int size = (null == ref ? -1 : ref.size());
		_staticCheckContainerIndexAndCount(
			ref, "Collection", size, index, count, listArgName, indexArgName, countArgName);
	}
	
	public static <T> void staticCheckArrayIndexAndCount(
			T[] ref,
			int index,
			int count,
			String arrArgName,
			String indexArgName,
			String countArgName) {
		int len = (null == ref ? -1 : ref.length);
		_staticCheckContainerIndexAndCount(
			ref, "Array", len, index, count, arrArgName, indexArgName, countArgName);
	}
	
	private static void _staticCheckContainerIndexAndCount(
			Object container,
			String containerType,
			int containerSize,
			int index,
			int count,
			String containerArgName,
			String indexArgName,
			String countArgName) {
		staticCheckNotNull(container, containerArgName);
		staticCheckNotNull(indexArgName, "indexArgName");
		_staticCheckIndexNotNegative(containerType, containerArgName, indexArgName, index);
		staticCheckIntNotNegative(count, countArgName);
		if (index >= containerSize) {
			throw new IndexOutOfBoundsException(String.format(
					"%s '%s': Index '%s' too large: %d >= %d",
					containerType,
					containerArgName,
					indexArgName,
					index,
					containerSize));
		}
		if (index + count > containerSize) {
			throw new IndexOutOfBoundsException(String.format(
					"%s '%s': Index '%s' and count '%s' too large: %d + %d > %d",
					containerType,
					containerArgName,
					indexArgName,
					countArgName,
					index,
					count,
					containerSize));
		}
	}
}
