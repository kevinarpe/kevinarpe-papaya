package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Collection of static methods to manipulate {@link String} references.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
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
     * @see #NEW_LINE_REGEX
     */
    public static final String NEW_LINE;
    
    /**
     * This is the current newline as a compiled regular expression:
     * {@code Pattern.compile(Pattern.quote(NEW_LINE))}
     * 
     * @see #NEW_LINE
     */
    public static final Pattern NEW_LINE_REGEX;

    /**
     * A regular expression pattern to match either UNIX or Windows newlines:
     * {@code Pattern.compile("\r?\n")}
     * <p>
     * This is useful when splitting text to its lines from a file of unknown source.
     *
     * @see #NEW_LINE_REGEX
     */
    public static final Pattern UNIX_OR_WINDOWS_NEW_LINE_REGEX;

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
    
    //private static final Pattern _NEW_LINE_REGEX;
    
    static {
        NEW_LINE = System.getProperty("line.separator");
        UNIX_NEW_LINE = "\n";
        WINDOWS_NEW_LINE = "\r\n";
        NEW_LINE_REGEX = Pattern.compile(NEW_LINE);
        UNIX_OR_WINDOWS_NEW_LINE_REGEX = Pattern.compile("\r?\n");
    }
    
    /**
     * Strictly converts a string to a boolean value.  Ignoring case, only {@code "true"} and
     * {@code "false"} are accepted as valid values.  The built-in Java method,
     * {@link Boolean#parseBoolean(String)}, is more lenient, treating {@code "true"} (or case
     * variants) as {@code true} and all other values, including {@code null}, as {@code false}.
     * 
     * @param str
     *        input value
     *
     * @return boolean value
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code str} is not "true" or "false", ignoring case
     *
     * @see FuncUtils#PARSE_BOOLEAN_FROM_STRING
     */
    @FullyTested
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
     * Removes all leading whitespace chars.  To be precise, "leading" is always defined as
     * starting from index zero.  This terminology may be backwards for right-to-left languages
     * such as Hebrew, Arabic, and Farsi.
     * <p>
     * Whitespace is defined as {@link Character#isWhitespace(char)}, which will include all
     * Unicode whitespace, such as the Japanese full-width space.
     * <p>
     * If zero leading whitespace chars are found, the input string reference is returned.  If all
     * characters in input string are whitespace, an empty string is returned.
     * 
     * @param str
     *        a string reference
     *
     * @return string reference without leading whitespace chars
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     *
     * @see #trimWhitespaceSuffix(String)
     */
    @FullyTested
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
     * Removes all trailing whitespace chars.  To be precise, "trailing" is always defined as
     * starting from the last index.  This terminology may be backwards for right-to-left languages
     * such as Hebrew, Arabic, and Farsi.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * <p>
     * If zero leading whitespace chars are found, the input string reference is returned.  If all
     * characters in input string are whitespace, an empty string is returned.
     * 
     * @param str
     *        a string reference
     *
     * @return string reference without leading whitespace chars
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     *
     * @see #trimWhitespacePrefix(String)
     */
    // TODO: This is replaced by Guava's CharMatchers.
    @FullyTested
    public static String trimWhitespaceSuffix(String str) {
        ObjectArgs.checkNotNull(str, "str");
        
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
    
//    /**
//     * Tests if an input string is one or more whitespace chars.  Empty strings are not
//     * considered whitespace.
//     * <p>
//     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
//     * whitespace chars used in East Asian languages.
//     * 
//     * @param str input string
//     * @return true if only whitespace 
//     * @throws NullPointerException if {@code str} is {@code null}
//     */
//    public static <T extends CharSequence> boolean isOnlyWhitespace(String str) {
//        ObjectArgs.checkNotNull(str, "str");
//        int len = str.length();
//        if (0 == len) {
//            return false;
//        }
//        boolean b = isEmptyOrWhitespace(str);
//        return b;
//    }
    
    /**
     * Tests if an input string is empty or only whitespace chars.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * 
     * @param str
     *        input string
     *
     * @return true if empty or all whitespace 
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     */
    @FullyTested
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
     * This method is modeled after Left() from Visual Basic.  Copies a specified number of leading
     * chars from a string.  To be precise, "leading" is always defined as starting from index
     * zero.  This terminology may be backwards for right-to-left languages such as Hebrew, Arabic,
     * and Farsi.
     * <p>
     * If {@code count == 0}, the empty string is retured.  If {@code count >= str.length()}, the
     * input string reference is returned.
     * 
     * @param str
     *        input string reference to process
     * @param count
     *        number of chars to copy starting from first index.
     *        Must not be negative and may be greater than input length
     *
     * @return string reference created from leading chars
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code count} is negative
     */
    @FullyTested
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
     * This method is modeled after Right() from Visual Basic.  Copies a specified number of
     * trailing chars from a string.  To be precise, "trailing" is always defined as starting from
     * the last index.  This terminology may be backwards for right-to-left languages such as
     * Hebrew, Arabic, and Farsi.
     * <p>
     * If {@code count == 0}, the empty string is retured.  If {@code count >= str.length()}, the
     * input string reference is returned.
     * 
     * @param str
     *        input string reference to process
     * @param count
     *        number of chars to copy starting from last index.
     *        Must not be negative and may be greater than input length
     *
     * @return string reference created from trailing chars
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code count} is negative
     */
    @FullyTested
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
    
//    public static String replaceAll(String haystack, Pattern regex, String replacement) {
//        ObjectArgs.checkNotNull(haystack, "haystack");
//        ObjectArgs.checkNotNull(regex, "regex");
//        ObjectArgs.checkNotNull(replacement, "replacement");
//        Matcher m = regex.matcher(haystack);
//        String s2 = m.replaceAll(replacement);
//        return s2;
//    }
    
    // TODO: replaceFirstN
    // TODO: replaceLast
    // TODO: replaceLastN
    
//    // Ref: http://stackoverflow.com/a/6417487/257299
//    public static int findLastPatternMatch(String haystack, Pattern regex) {
//        ObjectArgs.checkNotNull(haystack, "haystack");
//        ObjectArgs.checkNotNull(regex, "regex");
//        Matcher m = regex.matcher(haystack);
//        int haystackLen = haystack.length();
//        int index = _findLastPatternMatchCore(0, haystackLen, m);
//        return index;
//    }
//    
//    private static int _findLastPatternMatchCore(int start, int end, Matcher m) {
//        if (start > end) {
//            return -1;
//        }
//        final int pivot = ((end - start) / 2) + start;
//        if (m.find(pivot)) {
//            int index = m.start();
//            if (index + 1 > end) {
//                return index;
//            }
//            int result = _findLastPatternMatchCore(1 + index, end, m);
//            return (-1 == result ? index : result);
//        }
//        else if (m.find(start)) {
//            int index = m.start();
//            if (index + 1 > pivot) {
//                return index;
//            }
//            int result = _findLastPatternMatchCore(1 + index, pivot, m);
//            return (-1 == result ? index : result);
//        }
//        return -1;
//    }
//    
//    // TODO: Split by new line
//    // TODO: Convert new lines
//    // TODO: Add line prefix / suffix
//    
    /**
     * This is a convenience method for {@link #removeByIndexAndCount(String, int, int)}
     * where {@code count = 1}.
     */
    @FullyTested
    public static String removeCharAt(String str, int index) {
        final int count = 1;
        String x = removeByIndexAndCount(str, index, count);
        return x;
    }
    
    /**
     * Removes characters from a string by creating a new string and copying remaining characters
     * from the source string.  To be precise, using the term "remove" is a misnomer, as Java
     * strings cannot change size; they are immutable.
     * <p>
     * If {@code count == 0}, the input string reference is returned.  No copy is made.
     * 
     * @param str
     *        a string reference.  May be empty, but not {@code null}
     * @param index
     *        offset to begin removing characters.  Range: 0 to {@code str.length() - 1}
     * @param count
     *        number of characters to remove.  Must be non-negative.
     * 
     * @return reference to a string with characters removed
     * 
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code str} is empty</li>
     *   <li>if {@code count < 0}</li>
     * </ul>
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code index >= str.length()}</li>
     *   <li>if {@code index + count > str.length()}</li>
     * </ul>
     * 
     * @see StringArgs#checkIndexAndCount(CharSequence, int, int, String, String, String)
     */
    @NotFullyTested
    public static String removeByIndexAndCount(String str, int index, int count) {
        StringArgs.checkIndexAndCount(str, index, count, "str", "index", "count");
        
        if (0 == count) {
            return str;
        }
        
        // Never zero because StringArgs.checkIndexAndCount(...) rejects empty strings.
        final int len = str.length();
        String newStr = "";
        
        final int countBefore = index;
        final int countAfter = len - (index + count);
        
        if (0 != countBefore) {
            newStr = str.substring(0, countBefore);
        }
        if (0 != countAfter) {
            String after = str.substring(index + count);
            newStr += after;
        }
        return newStr;
    }

//    /**
//     * Insert characters into a string by creating a new string and copying characters from the
//     * source strings.  To be precise, using the term "insert" is a misnomer, as Java strings
//     * cannot change size.
//     * <p>
//     * If {@code count == 0}, the input string reference is returned.  No copy is made.
//     * 
//     * @param str a string reference
//     * @param index offset to begin inserting characters.  Range: 0 to {@code str.length()}
//     * @param newText characters to insert
//     * @return reference to a string with characters inserted
//     * @throws NullPointerException if {@code str} or {@code newText} is {@code null}
//     * @throws IllegalArgumentException if {@code index} is invalid
//     */
//    public static String insert(String str, int index, String newText) {
//        StringArgs.checkInsertIndex(str, index, "str", "index");
//        ObjectArgs.checkNotNull(newText, "newText");
//        
//        if (0 == newText.length()) {
//            return str;
//        }
//        
//        String newStr = "";
//        final int len = str.length();
//        final int countBefore = index;
//        final int countAfter = len - index;
//        
//        if (0 != len && 0 != countBefore) {
//            newStr = str.substring(0, countBefore);
//        }
//        newStr += newText;
//        if (0 != len && 0 != countAfter) {
//            String after = str.substring(index);
//            newStr += after;
//        }
//        return newStr;
//    }
//    
//    /**
//     * This is a convenience method to combine {@link #remove(String, int, int)}
//     * and {@link #insert(String, int, String)} where {@code count = 1}.
//     */
//    public static String replace(String str, int index, String newText) {
//        final int count = 1;
//        String newStr = replace(str, index, count, newText);
//        return newStr;
//    }
//    
//    /**
//     * This is a convenience method to combine {@link #remove(String, int, int)}
//     * and {@link #insert(String, int, String)}.
//     */
//    public static String replace(String str, int index, int count, String newText) {
//        String newStr = remove(str, index, count);
//        String newStr2 = insert(newStr, index, newText);
//        return newStr2;
//    }
    
    /**
     * Options for methods that process text, such as
     * {@link StringUtils#addPrefixAndSuffixPerLine(String, String, String, TextProcessorOption...)}.
     * 
     * @author Kevin Connor ARPE (kevinarpe@gmail.com)
     * 
     * @see StringUtils#addPrefixPerLine(String, String, TextProcessorOption...)
     * @see StringUtils#addSuffixPerLine(String, String, TextProcessorOption...)
     * @see StringUtils#addPrefixAndSuffixPerLine(String, String, String, TextProcessorOption...)
     */
    @FullyTested
    public static enum TextProcessorOption {
        
        /**
         * Do not process the first line in a multiline block of text.  The first line will be
         * preserved, unchanged.
         */
        SKIP_FIRST_LINE;
        
        /**
         * Converts this reference to a debugger-friendly string.  {@link Enum#toString()} only
         * returns {@link #name()}.  This version embellishes the result with the full class name.
         * If only the name is required, call {@link #name()} directly.
         * <hr>
         * Docs from {@link Enum#toString()}:
         * <p>
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            String x = String.format(
                "enum %s: [name(): %s]",
                TextProcessorOption.class.getCanonicalName(),
                name());
            return x;
        }
    }
    
    /**
     * This is a convenience method to call
     * {@link #addPrefixAndSuffixPerLine(String, String, String, TextProcessorOption...)} with
     * {@code optLineSuffix} as {@code null}.
     */
    @FullyTested
    public static String addPrefixPerLine(
            String textBlock,
            String optLinePrefix,
            TextProcessorOption... optionArr) {
        String optLineSuffix = null;
        String x = addPrefixAndSuffixPerLine(textBlock, optLinePrefix, optLineSuffix, optionArr);
        return x;
    }
    
    /**
     * This is a convenience method to call
     * {@link #addPrefixAndSuffixPerLine(String, String, String, TextProcessorOption...)} with
     * {@code optLinePrefix} as {@code null}.
     */
    @FullyTested
    public static String addSuffixPerLine(
            String textBlock,
            String optLineSuffix,
            TextProcessorOption... optionArr) {
        String optLinePrefix = null;
        String x = addPrefixAndSuffixPerLine(textBlock, optLinePrefix, optLineSuffix, optionArr);
        return x;
    }
    
    /**
     * Creates a new string from a multiline block of text with additional prefix and/or suffix per
     * line.  This method can be used to programatically indent blocks of text.
     * <p>
     * Example:
     * <br>{@code String textBlock = String.format("This is a sample%nmultiline block%nof text.");}
     * <br>(The format code {@code "%n"} is replaced by current system newline.)
     * <pre>
     * {@code
     * This is a sample
     * multiline block
     * of text.
     * }
     * </pre>
     * Calling this method with {@code optLinePrefix} as {@code ">"} and {@code optLineSuffix} as
     * {@code "<"} will return:
     * <pre>
     * {@code
     * >This is a sample<
     * >multiline block<
     * >of text.<
     * }
     * </pre>
     * 
     * @param textBlock
     * <ul>
     *   <li>text to process, usually a multiline block of text</li>
     *   <li>text is split and joined using {@link #NEW_LINE}</li>
     *   <li>if text ends with {@link #NEW_LINE}:</li>
     *   <li>
     *     <ul>
     *       <li>final "phantom" empty line is ignored during processing</li>
     *       <li>result will also end with {@link #NEW_LINE}</li>
     *     </ul>
     *   </li>
     *   <li>must not be {@code null} or empty</li>
     * </ul>
     * @param optLinePrefix
     * <ul>
     *   <li>additional text to add to the beginning to each line in the multiline block of
     *   text</li>
     *   <li>may be {@code null} or empty, but only if parameter {@code optLineSuffix} is not
     *   <i>also</i> {@code null} or empty</li>
     * </ul>
     * @param optLineSuffix
     * <ul>
     *   <li>additional text to add to the end to each line in the multiline block of text</li>
     *   <li>may be {@code null} or empty, but only if parameter {@code optLinePrefix} is not
     *   <i>also</i> {@code null} or empty</li>
     * </ul>
     * @param optionArr
     * <ul>
     *   <li>zero or more values from enum {@link TextProcessorOption}</li>
     *   <li>values must not be {@code null}</li>
     * </ul>
     * 
     * @return new string with each line modified to have new prefix and/or suffix
     * 
     * @throws NullPointerException
     * <ul>
     *   <li>if {@code textBlock} is {@code null}</li>
     *   <li>if any element of {@code optionArr} is {@code null}</li>
     * </ul>
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code textBlock} has length zero (empty)</li>
     *   <li>if both {@code optLinePrefix} and {@code optLineSuffix} are {@code null} or empty</li>
     * </ul>
     * 
     * @see #addPrefixPerLine(String, String, TextProcessorOption...)
     * @see #addSuffixPerLine(String, String, TextProcessorOption...)
     */
    @FullyTested
    public static String addPrefixAndSuffixPerLine(
            String textBlock,
            String optLinePrefix,
            String optLineSuffix,
            TextProcessorOption... optionArr) {
        StringArgs.checkNotEmpty(textBlock, "textBlock");
        ArrayArgs.checkElementsNotNull(optionArr, "optionArr");
        
        boolean hasLinePrefix = (null != optLinePrefix && !optLinePrefix.isEmpty());
        boolean hasLineSuffix = (null != optLineSuffix && !optLineSuffix.isEmpty());
        if (!hasLinePrefix && !hasLineSuffix) {
            throw new IllegalArgumentException(
                "Both arguments 'optLinePrefix' and 'optLineSuffix' are null or empty");
        }
        
        Iterable<String> lineIter = Splitter.on(NEW_LINE).split(textBlock);
        ArrayList<String> lineList = new ArrayList<String>();
        Iterables.addAll(lineList, lineIter);
        
        List<TextProcessorOption> optionList = Arrays.asList(optionArr);
        
        int lineListSize = lineList.size();
        int i = (optionList.contains(TextProcessorOption.SKIP_FIRST_LINE) ? 1 : 0);
        for ( ; i < lineListSize; ++i) {
            String line = lineList.get(i);
            // Do not modify the last line if empty.
            if (i < lineListSize - 1 || !line.isEmpty()) {
                if (hasLinePrefix) {
                    line = optLinePrefix + line;
                }
                if (hasLineSuffix) {
                    line += optLineSuffix;
                }
                lineList.set(i, line);
            }
        }
        String x = Joiner.on(NEW_LINE).join(lineList);
        return x;
    }
}
