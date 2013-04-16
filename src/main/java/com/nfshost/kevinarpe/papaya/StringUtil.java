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

public final class StringUtil {

	public static final String NEW_LINE;
	public static final String UNIX_NEW_LINE;
	public static final String WINDOWS_NEW_LINE;
	
	private static final Pattern _LEADING_WHITESPACE_REGEX;
	private static final Pattern _TRAILING_WHITESPACE_REGEX;
	private static final Pattern _NEW_LINE_REGEX;
	
	static {
		NEW_LINE = System.lineSeparator();
		UNIX_NEW_LINE = "\n";
		WINDOWS_NEW_LINE = "\r\n";
		
		_LEADING_WHITESPACE_REGEX = Pattern.compile("^\\s+");
		_TRAILING_WHITESPACE_REGEX = Pattern.compile("\\s+$");
		_NEW_LINE_REGEX = Pattern.compile("\r?\n");
	}
	
	public static String staticTrimLeft(String s) {
		ArgUtil.staticCheckNotNull(s, "s");
		Matcher m = _LEADING_WHITESPACE_REGEX.matcher(s);
		String s2 = m.replaceFirst("");
		return s2;
	}
	
	public static String staticTrimRight(String s) {
		ArgUtil.staticCheckNotNull(s, "s");
		Matcher m = _TRAILING_WHITESPACE_REGEX.matcher(s);
		String s2 = m.replaceFirst("");
		return s2;
	}
	
	public static String staticReplaceAll(String haystack, Pattern regex, String replacement) {
		ArgUtil.staticCheckNotNull(haystack, "haystack");
		ArgUtil.staticCheckNotNull(regex, "regex");
		ArgUtil.staticCheckNotNull(replacement, "replacement");
		Matcher m = regex.matcher(haystack);
		String s2 = m.replaceAll(replacement);
		return s2;
	}
	
	// Ref: http://stackoverflow.com/a/6417487/257299
	public static int staticFindLastPatternMatch(String haystack, Pattern regex) {
		ArgUtil.staticCheckNotNull(haystack, "haystack");
		ArgUtil.staticCheckNotNull(regex, "regex");
		Matcher m = regex.matcher(haystack);
		int haystackLen = haystack.length();
		int index = _staticFindLastPatternMatchCore(0, haystackLen, m);
		return index;
	}
	
	private static int _staticFindLastPatternMatchCore(int start, int end, Matcher m) {
		if (start > end) {
			return -1;
		}
		int pivot = ((end - start) / 2) + start;
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
	// TODO: String insert/replace (like ArrayUtil)
}
