package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

/**
 * This is a collection of methods to manipulate {@link String} references.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public final class StringUtils {

	// Disable default constructor
	private StringUtils() {
	}

	/**
	 * This is the current newline: {@code System.getProperty("line.separator")}.  This will differ
	 * between operating systems, such as UNIX variants and Microsoft Windows.
	 * <p>
	 * It is also available when calling {@link String#format(String, Object...)} and using
	 * {@code %n} in the format string.
	 * 
	 * @see #NEW_LINE
	 * @see #UNIX_NEW_LINE
	 * @see #WINDOWS_NEW_LINE
	 */
    public static final String NEW_LINE;
    
    /**
     * This is the newline used by UNIX variants: "\n".  This value will never differ between
     * operating systems.
     */
    public static final String UNIX_NEW_LINE;
    
    /**
     * This is the newline used by Microsoft Windows: "\r\n".  This value will never differ between
     * operating systems.
     */
    public static final String WINDOWS_NEW_LINE;
    
    private static final Pattern _NEW_LINE_REGEX;
    
    static {
        NEW_LINE = System.getProperty("line.separator");
        UNIX_NEW_LINE = "\n";
        WINDOWS_NEW_LINE = "\r\n";
        _NEW_LINE_REGEX = Pattern.compile("\r?\n");
    }
    
    /**
     * Strictly converts a string to a boolean value.  Ignoring case, only {@code "true"} and
     * {@code "false"} are accepted as valid values.  The built-in Java method,
     * {@link Boolean#parseBoolean(String)}, is more lenient, treating {@code "true"} (or case
     * variants) as {@code true} and all other values, including {@code null}, as {@code false}.
     * 
     * @param str input value
     * @return boolean value
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code str} is not "true" or "false", ignoring case
     * @see FuncUtils#PARSE_BOOLEAN_FROM_STRING
     */
    public static boolean parseBoolean(String str) {
        ObjectArgs.checkNotNull(str, "str");
        if (str.equalsIgnoreCase("true")) {
            return true;
        }
        if (str.equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException(String.format(
            "Failed to convert string to boolean: '%s'", str));
    }
    
    /**
     * Creates a copy of the input string, but removes all leading whitespace chars.
     * To be precise, "leading" is always defined as starting from index zero.
     * This terminology may be backwards for right-to-left languages such as Hebrew and Arabic.
     * <p>
     * Whitespace is defined as {@link Character#isWhitespace(char)}, which will include all
     * Unicode whitespace, such as the Japanese full-width space.
     * <p>
     * If zero leading whitespace chars are found, the input string reference is returned.
     * <p>
     * If all characters in input string are whitespace, an empty string is returned.
     * 
     * @param str a string reference
     * @return string reference without leading whitespace chars
     * @throws NullPointerException if {@code str} is {@code null}
     * @see #trimWhitespaceSuffix(String)
     */
    public static String trimWhitespacePrefix(String str) {
        ObjectArgs.checkNotNull(str, "str");
        final int len = str.length();
        int i = 0;
        for (; i < len; ++i) {
            char ch = str.charAt(i);
            if (!Character.isWhitespace(ch)) {
                break;
            }
        }
        if (0 == i) {
            return str;
        }
        if (len == i) {
            return "";
        }
        String s2 = str.substring(i);
        return s2;
    }
    
    // TODO: trimPrefixByCallback
    // TODO: trimSuffixByCallback
    
    /**
     * Creates a copy of the input string, but removes all trailing whitespace chars.
     * To be precise, "trailing" is always defined as starting from the last index.
     * This terminology may be backwards for right-to-left languages such as Hebrew and Arabic.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * <p>
     * If zero leading whitespace chars are found, the input string reference is returned.
     * <p>
     * If all characters in input string are whitespace, an empty string is returned.
     * 
     * @param str a string reference
     * @return string reference without leading whitespace chars
     * @throws NullPointerException if {@code str} is {@code null}
     * @see #trimWhitespacePrefix(String)
     */
    public static String trimWhitespaceSuffix(String str) {
        ObjectArgs.checkNotNull(str, "s");
        final int len = str.length();
        int i = len - 1;
        for (; i >= 0; --i) {
            char ch = str.charAt(i);
            if (!Character.isWhitespace(ch)) {
                break;
            }
        }
        if (len - 1 == i) {
            return str;
        }
        if (-1 == i) {
            return "";
        }
        String s2 = str.substring(0, i + 1);
        return s2;
    }
    
    /**
     * Tests if an input string is one or more whitespace chars.  Empty strings are not
     * considered whitespace.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * 
     * @param str input string
     * @return true if only whitespace 
     * @throws NullPointerException if {@code str} is {@code null}
     */
    public static <T extends CharSequence> boolean isOnlyWhitespace(String str) {
    	ObjectArgs.checkNotNull(str, "str");
        int len = str.length();
        if (0 == len) {
        	return false;
        }
        boolean b = isEmptyOrWhitespace(str);
        return b;
    }
    
    /**
     * Tests if an input string is empty or one or more whitespace chars.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * 
     * @param str input string
     * @return true if empty or all whitespace 
     * @throws NullPointerException if {@code str} is {@code null}
     */
    public static <T extends CharSequence> boolean isEmptyOrWhitespace(String str) {
        ObjectArgs.checkNotNull(str, "str");
        int len = str.length();
        for (int i = 0; i < len; ++i) {
            char ch = str.charAt(i);
            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method is modeled after Left() from Visual Basic.
     * Copies a specified number of leading chars from a string.
     * To be precise, "leading" is always defined as starting from index zero.
     * This terminology may be backwards for right-to-left languages such as Hebrew and Arabic.
     * <p>
     * If {@code count == 0}, the empty string is retured.
     * <p>
     * If {@code count >= str.length()}, the input string reference is returned.
     * 
     * @param str input string reference to process
     * @param count number of chars to copy starting from first index.
     *        Must be >= 0, and may be greater than input length
     * @return string reference created from leading chars
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public static String substringPrefix(String str, int count) {
        ObjectArgs.checkNotNull(str, "str");
        IntArgs.checkNotNegative(count, "count");
        int len = str.length();
        if (count >= len) {
            return str;
        }
        String str2 = str.substring(0, count);
        return str2;
    }
    
    /**
     * This method is modeled after Right() from Visual Basic.
     * Copies a specified number of trailing chars from a string.
     * To be precise, "trailing" is always defined as starting from the last index.
     * This terminology may be backwards for right-to-left languages such as Hebrew and Arabic.
     * <p>
     * If {@code count == 0}, the empty string is retured.
     * <p>
     * If {@code count >= str.length()}, the input string reference is returned.
     * 
     * @param str input string reference to process
     * @param count number of chars to copy starting from last index.
     *        Must be >= 0, and may be greater than input length
     * @return string reference created from trailing chars
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public static String substringSuffix(String str, int count) {
        ObjectArgs.checkNotNull(str, "str");
        IntArgs.checkNotNegative(count, "count");
        int len = str.length();
        if (count >= len) {
            return str;
        }
        String str2 = str.substring(len - count, len);
        return str2;
    }
    
    public static String replaceAll(String haystack, Pattern regex, String replacement) {
        ObjectArgs.checkNotNull(haystack, "haystack");
        ObjectArgs.checkNotNull(regex, "regex");
        ObjectArgs.checkNotNull(replacement, "replacement");
        Matcher m = regex.matcher(haystack);
        String s2 = m.replaceAll(replacement);
        return s2;
    }
    
    // TODO: replaceFirstN
    // TODO: replaceLast
    // TODO: replaceLastN
    
    // Ref: http://stackoverflow.com/a/6417487/257299
    public static int findLastPatternMatch(String haystack, Pattern regex) {
        ObjectArgs.checkNotNull(haystack, "haystack");
        ObjectArgs.checkNotNull(regex, "regex");
        Matcher m = regex.matcher(haystack);
        int haystackLen = haystack.length();
        int index = _findLastPatternMatchCore(0, haystackLen, m);
        return index;
    }
    
    private static int _findLastPatternMatchCore(int start, int end, Matcher m) {
        if (start > end) {
            return -1;
        }
        final int pivot = ((end - start) / 2) + start;
        if (m.find(pivot)) {
            int index = m.start();
            if (index + 1 > end) {
                return index;
            }
            int result = _findLastPatternMatchCore(1 + index, end, m);
            return (-1 == result ? index : result);
        }
        else if (m.find(start)) {
            int index = m.start();
            if (index + 1 > pivot) {
                return index;
            }
            int result = _findLastPatternMatchCore(1 + index, pivot, m);
            return (-1 == result ? index : result);
        }
        return -1;
    }
    
    // TODO: Split by new line
    // TODO: Convert new lines
    // TODO: Add line prefix / suffix
    
    /**
     * This is a convenience method for {@link #remove(String, int, int)}
     * where {@code count = 1}.
     */
    public static String remove(String s, int index) {
        final int count = 1;
        String s2 = remove(s, index, count);
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
     * @throws NullPointerException if {@code str} is {@code null}
     * @throws IllegalArgumentException if {@code index} and {@code count} are invalid
     */
    public static String remove(String str, int index, int count) {
        StringArgs.checkIndexAndCount(str, index, count, "s", "index", "count");
        
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
     * @throws NullPointerException if {@code str} or {@code newText} is {@code null}
     * @throws IllegalArgumentException if {@code index} is invalid
     */
    public static String insert(String str, int index, String newText) {
        StringArgs.checkInsertIndex(str, index, "str", "index");
        ObjectArgs.checkNotNull(newText, "newText");
        
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
     * This is a convenience method to combine {@link #remove(String, int, int)}
     * and {@link #insert(String, int, String)} where {@code count = 1}.
     */
    public static String replace(String str, int index, String newText) {
        final int count = 1;
        String newStr = replace(str, index, count, newText);
        return newStr;
    }
    
    /**
     * This is a convenience method to combine {@link #remove(String, int, int)}
     * and {@link #insert(String, int, String)}.
     */
    public static String replace(String str, int index, int count, String newText) {
        String newStr = remove(str, index, count);
        String newStr2 = insert(newStr, index, newText);
        return newStr2;
    }
}