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
	
	public static String staticLeft(String s, int count) {
		ObjectArgUtil.staticCheckNotNull(s, "s");
		IntArgUtil.staticCheckNotNegative(count, "count");
		int len = s.length();
		count = (count > len ? len : count);
		String s2 = s.substring(0, count);
		return s2;
	}
	
	public static String staticRight(String s, int count) {
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
