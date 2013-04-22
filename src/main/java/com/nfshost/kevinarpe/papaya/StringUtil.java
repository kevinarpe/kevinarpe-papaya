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

import com.nfshost.kevinarpe.papaya.Args.IntArgs;
import com.nfshost.kevinarpe.papaya.Args.ObjectArgs;
import com.nfshost.kevinarpe.papaya.Args.StringArgs;

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
    
    /**
     * Creates a copy of the input string, but removes all leading whitespace chars.
     * To be precise, "leading" is always defined as starting from index zero.
     * This may be backwards for right-to-left languages such as Hebrew and Arabic.
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
     * @throws NullPointerException if {@code str} is null
     * @see #staticWhitespaceTrimSuffix(String)
     */
    public static String staticWhitespaceTrimPrefix(String str) {
        ObjectArgs.staticCheckNotNull(str, "str");
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
    
    /**
     * Creates a copy of the input string, but removes all trailing whitespace chars.
     * To be precise, "trailing" is always defined as starting from the last index.
     * This may be backwards for right-to-left languages such as Hebrew and Arabic.
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
     * @throws NullPointerException if {@code str} is null
     * @see #staticWhitespaceTrimPrefix(String)
     */
    public static String staticWhitespaceTrimSuffix(String s) {
        ObjectArgs.staticCheckNotNull(s, "s");
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
    
    public static String staticSubstringPrefix(String s, int count) {
        ObjectArgs.staticCheckNotNull(s, "s");
        IntArgs.staticCheckNotNegative(count, "count");
        int len = s.length();
        count = (count > len ? len : count);
        String s2 = s.substring(0, count);
        return s2;
    }
    
    public static String staticSubstringSuffix(String s, int count) {
        ObjectArgs.staticCheckNotNull(s, "s");
        IntArgs.staticCheckNotNegative(count, "count");
        int len = s.length();
        count = (count > len ? len : count);
        String s2 = s.substring(len - count, len);
        return s2;
    }
    
    public static String staticReplaceAll(String haystack, Pattern regex, String replacement) {
        ObjectArgs.staticCheckNotNull(haystack, "haystack");
        ObjectArgs.staticCheckNotNull(regex, "regex");
        ObjectArgs.staticCheckNotNull(replacement, "replacement");
        Matcher m = regex.matcher(haystack);
        String s2 = m.replaceAll(replacement);
        return s2;
    }
    
    // Ref: http://stackoverflow.com/a/6417487/257299
    public static int staticFindLastPatternMatch(String haystack, Pattern regex) {
        ObjectArgs.staticCheckNotNull(haystack, "haystack");
        ObjectArgs.staticCheckNotNull(regex, "regex");
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
        StringArgs.staticCheckIndexAndCount(str, index, count, "s", "index", "count");
        
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
        StringArgs.staticCheckInsertIndex(str, index, "str", "index");
        ObjectArgs.staticCheckNotNull(newText, "newText");
        
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
