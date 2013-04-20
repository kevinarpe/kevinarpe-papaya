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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nfshost.kevinarpe.papaya.Args.IntArgUtil;
import com.nfshost.kevinarpe.papaya.Args.ObjectArgUtil;
import com.nfshost.kevinarpe.papaya.Args.StringArgUtil;

public final class StringUtil {

	public static final String NEW_LINE;
	public static final String UNIX_NEW_LINE;
	public static final String WINDOWS_NEW_LINE;
	
	private static final Pattern _NEW_LINE_REGEX;
	
	static {
		NEW_LINE = System.lineSeparator();
		UNIX_NEW_LINE = "\n";
		WINDOWS_NEW_LINE = "\r\n";
		_NEW_LINE_REGEX = Pattern.compile("\r?\n");
	}
	
	public static String staticWhitespaceTrimLeft(String s) {
		ObjectArgUtil.staticCheckNotNull(s, "s");
		final int len = s.length();
		int i = 0;
		for (; i < len; ++i) {
			char ch = s.charAt(i);
			if (!Character.isWhitespace(ch)) {
				break;
			}
		}
		if (0 == i) {
			return s;
		}
		if (len == i) {
			return "";
		}
		String s2 = s.substring(i);
		return s2;
	}
	
	public static String staticWhitespaceTrimRight(String s) {
		ObjectArgUtil.staticCheckNotNull(s, "s");
		final int len = s.length();
		int i = len - 1;
		for (; i >= 0; --i) {
			char ch = s.charAt(i);
			if (!Character.isWhitespace(ch)) {
				break;
			}
		}
		if (len - 1 == i) {
			return s;
		}
		if (-1 == i) {
			return "";
		}
		String s2 = s.substring(0, i + 1);
		return s2;
	}
	
	public static String staticSubstringLeft(String s, int count) {
		ObjectArgUtil.staticCheckNotNull(s, "s");
		IntArgUtil.staticCheckNotNegative(count, "count");
		int len = s.length();
		count = (count > len ? len : count);
		String s2 = s.substring(0, count);
		return s2;
	}
	
	public static String staticSubstringRight(String s, int count) {
		ObjectArgUtil.staticCheckNotNull(s, "s");
		IntArgUtil.staticCheckNotNegative(count, "count");
		int len = s.length();
		count = (count > len ? len : count);
		String s2 = s.substring(len - count, len);
		return s2;
	}
	
	public static String staticReplaceAll(String haystack, Pattern regex, String replacement) {
		ObjectArgUtil.staticCheckNotNull(haystack, "haystack");
		ObjectArgUtil.staticCheckNotNull(regex, "regex");
		ObjectArgUtil.staticCheckNotNull(replacement, "replacement");
		Matcher m = regex.matcher(haystack);
		String s2 = m.replaceAll(replacement);
		return s2;
	}
	
	// Ref: http://stackoverflow.com/a/6417487/257299
	public static int staticFindLastPatternMatch(String haystack, Pattern regex) {
		ObjectArgUtil.staticCheckNotNull(haystack, "haystack");
		ObjectArgUtil.staticCheckNotNull(regex, "regex");
		Matcher m = regex.matcher(haystack);
		int haystackLen = haystack.length();
		int index = _staticFindLastPatternMatchCore(0, haystackLen, m);
		return index;
	}
	
	private static int _staticFindLastPatternMatchCore(int start, int end, Matcher m) {
		if (start > end) {
			return -1;
		}
		final int pivot = ((end - start) / 2) + start;
		if (m.find(pivot)) {
			int index = m.start();
			if (index + 1 > end) {
				return index;
			}
			int result = _staticFindLastPatternMatchCore(1 + index, end, m);
			return (-1 == result ? index : result);
		}
		else if (m.find(start)) {
			int index = m.start();
			if (index + 1 > pivot) {
				return index;
			}
			int result = _staticFindLastPatternMatchCore(1 + index, pivot, m);
			return (-1 == result ? index : result);
		}
		return -1;
	}
	
	// TODO: Split by new line
	// TODO: Convert new lines
	// TODO: Add line prefix / suffix
	// TODO: String insert/remove/replace (like ArrayUtil)
	
	/**
	 * This is a convenience method for {@link #staticRemove(String, int, int)}
	 * where {@code count = 1}.
	 */
	public static String staticRemove(String s, int index) {
		final int count = 1;
		String s2 = staticRemove(s, index, count);
		return s2;
	}
	
	/**
	 * Removes characters from a string by creating a new string and copying remaining characters
	 * from the source string.  To be precise, using the term "remove" is a misnomer, as Java
	 * strings cannot change size.
	 * <p>
	 * If {@code count == 0}, the input string reference is returned.  No copy is made.
	 * 
	 * @param str a string reference
	 * @param index offset to begin removing characters.  Range: 0 to {@code str.length() - 1}
	 * @param count number of characters to remove.  Must be non-negative.
	 * @return reference to a string with characters removed
	 * @throws NullPointerException if {@code str} is null
	 * @throws IllegalArgumentException if {@code index} and {@code count} are invalid
	 */
	public static String staticRemove(String str, int index, int count) {
		StringArgUtil.staticCheckIndexAndCount(str, index, count, "s", "index", "count");
		
		if (0 == count) {
			return str;
		}
		
		final int len = str.length();
		String newStr = "";
		
		final int countBefore = index;
		final int countAfter = len - (index + count);
		
		if (0 != len && 0 != countBefore) {
			newStr = str.substring(0, countBefore);
		}
		if (0 != len && 0 != countAfter) {
			String after = str.substring(index + count);
			newStr += after;
		}
		return newStr;
	}
	
	/**
	 * Insert characters into a string by creating a new string and copying characters from the
	 * source strings.  To be precise, using the term "insert" is a misnomer, as Java strings
	 * cannot change size.
	 * <p>
	 * If {@code count == 0}, the input string reference is returned.  No copy is made.
	 * 
	 * @param str a string reference
	 * @param index offset to begin inserting characters.  Range: 0 to {@code str.length()}
	 * @param newText characters to insert
	 * @return reference to a string with characters inserted
	 * @throws NullPointerException if {@code str} or {@code newText} is null
	 * @throws IllegalArgumentException if {@code index} is invalid
	 */
	public static String staticInsert(String str, int index, String newText) {
		StringArgUtil.staticCheckInsertIndex(str, index, "str", "index");
		ObjectArgUtil.staticCheckNotNull(newText, "newText");
		
		if (0 == newText.length()) {
			return str;
		}
		
		String newStr = "";
		final int len = str.length();
		final int countBefore = index;
		final int countAfter = len - index;
		
		if (0 != len && 0 != countBefore) {
			newStr = str.substring(0, countBefore);
		}
		newStr += newText;
		if (0 != len && 0 != countAfter) {
			String after = str.substring(index);
			newStr += after;
		}
		return newStr;
	}
	
	/**
	 * This is a convenience method to combine {@link #staticRemove(String, int, int)}
	 * and {@link #staticInsert(String, int, String)} where {@code count = 1}.
	 */
	public static String staticReplace(String str, int index, String newText) {
		final int count = 1;
		String newStr = staticReplace(str, index, count, newText);
		return newStr;
	}
	
	/**
	 * This is a convenience method to combine {@link #staticRemove(String, int, int)}
	 * and {@link #staticInsert(String, int, String)}.
	 */
	public static String staticReplace(String str, int index, int count, String newText) {
		String newStr = staticRemove(str, index, count);
		String newStr2 = staticInsert(newStr, index, newText);
		return newStr2;
	}
}
