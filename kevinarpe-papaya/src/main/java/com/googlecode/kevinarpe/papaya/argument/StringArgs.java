package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * Static methods to check {@link String} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class StringArgs {

    // Disable default constructor
    private StringArgs() {
    }

    private static final String _WARNING_PREFIX = String.format("%nWARNING: ");
    
    static String _getArgNameWarning(String ref, String argName) {
        String x = _getIdentifierWarning("Argument name", ref, argName);
        return x;
    }
    
    static String _getIdentifierWarning(String idType, String id, String argName) {
        if (null == idType) {
            return String.format("%sArgument 'idType' is null", _WARNING_PREFIX);
        }
        if (null == argName) {
            return String.format("%sArgument 'argName' is null", _WARNING_PREFIX);
        }
        if (null == id) {
            return String.format("%s%s '%s' is null",
                _WARNING_PREFIX, idType, argName);
        }
        if (id.isEmpty()) {
            return String.format("%s%s '%s' is empty",
                _WARNING_PREFIX, idType, argName);
        }
        if (_isOnlyWhitespace(id)) {
            return String.format("%s%s '%s' is only whitespace: '%s'",
                _WARNING_PREFIX, idType, argName, id);
        }
        return "";
    }
    
    static <T extends CharSequence> boolean _isOnlyWhitespace(T ref) {
        int len = ref.length();
        for (int i = 0; i < len; ++i) {
            char ch = ref.charAt(i);
            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Tests if a string reference is neither null nor empty, where empty is defined as
     * {@code 0 == ref.length()}.
     * <p>
     * Examples that fail:
     * <ul>
     *   <li>{@code null}</li>
     *   <li>{@code ""} (empty)</li>
     * </ul>
     * <p>
     * Examples that pass:
     * <ul>
     *   <li>{@code "  "} (whitespace)</li>
     *   <li>{@code "abc"}</li>
     *   <li>{@code "大阪"}</li>
     * </ul>
     * 
     * @param ref
     *        a string reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code ref} is empty
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkNotEmptyOrWhitespace(CharSequence, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkNotEmpty(T ref, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        int len = ref.length();
        if (0 == len) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s' is an empty string%s", argName, w));
        }
        return ref;
    }
    
    /**
     * Tests if a string reference is neither (a) null, (b) empty, nor (c) one or more
     * whitespace chars.
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}, which includes all special
     * whitespace chars used in East Asian languages.
     * <p>
     * Examples that fail:
     * <ul>
     *   <li>{@code null}</li>
     *   <li>{@code ""} (empty)</li>
     *   <li>{@code "  "} (whitespace)</li>
     * </ul>
     * <p>
     * Examples that pass:
     * <ul>
     *   <li>{@code "abc"}</li>
     *   <li>{@code "大阪"}</li>
     * </ul>
     * 
     * @param ref
     *        a string reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code ref} is empty or only whitespace
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkNotEmpty(CharSequence, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkNotEmptyOrWhitespace(T ref, String argName) {
        checkNotEmpty(ref, argName);
        if (!_isOnlyWhitespace(ref)) {
            return ref;
        }
        String w = StringArgs._getArgNameWarning(argName, "argName");
        throw new IllegalArgumentException(String.format(
            "Argument '%s' is all whitespace: '%s'%s", argName, ref, w));
    }
    
    /**
     * Tests if a string reference is not null and its length within specified range.
     * Length is defined as the number of characters in a CharSequence reference.
     * Kindly remember that characters in East Asian languages are frequently two bytes.
     * 
     * @param ref
     *        a string reference
     * @param minLen
     *        minimum number of chars (inclusive).  Must be non-negative. 
     * @param maxLen
     *        maximum number of chars (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minLen < 0}</li>
     *   <li>if {@code maxLen < 0}</li>
     *   <li>if {@code minLen > maxLen}</li>
     *   <li>if number of chars in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see #checkNotEmpty(CharSequence, String)
     * @see #checkNotEmptyOrWhitespace(CharSequence, String)
     * @see #checkMinLength(CharSequence, int, String)
     * @see #checkMaxLength(CharSequence, int, String)
     * @see #checkExactLength(CharSequence, int, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkLengthRange(
            T ref, int minLen, int maxLen, String argName) {
        IntArgs.checkNotNegative(minLen, "minLen");
        IntArgs.checkNotNegative(maxLen, "maxLen");
        _checkLengthRangeCore(ref, minLen, maxLen, argName);
        return ref;
    }
    
    private static <T extends CharSequence> void _checkLengthRangeCore(
            T ref, int minLen, int maxLen, String argName) {
        ObjectArgs.checkNotNull(ref, argName);
        if (-1 != minLen && -1 != maxLen && minLen > maxLen) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': 'minLen' > 'maxLen': %d > %d%s",
                argName, minLen, maxLen, w));
        }
        int len = ref.length();
        if (-1 != minLen && len < minLen) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': length() < 'minLen': %d < %d%n\tValue: [%s]%s",
                argName, len, minLen, ref, w));
        }
        if (-1 != maxLen && len > maxLen) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            throw new IllegalArgumentException(String.format(
                "Argument '%s': length() > 'maxLen': %d > %d%n\tValue: [%s]%s",
                argName, len, maxLen, ref, w));
        }
    }
    
    /**
     * Tests if a string reference is not null and has a minimum length.
     * Length is defined as the number of characters in a CharSequence reference.
     * Kindly remember that characters in East Asian languages are frequently two bytes.
     * 
     * @param ref
     *        a string reference
     * @param minLen
     *        minimum number of chars (inclusive).  Must be non-negative. 
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code minLen < 0}</li>
     *   <li>if number of chars in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(CharSequence, int, int, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkMinLength(
            T ref, int minLen, String argName) {
        IntArgs.checkNotNegative(minLen, "minLen");
        int maxLen = -1;
        _checkLengthRangeCore(ref, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if a string reference is not null and has a maximum length.
     * Length is defined as the number of characters in a CharSequence reference.
     * Kindly remember that characters in East Asian languages are frequently two bytes.
     * 
     * @param ref
     *        a string reference
     * @param maxLen
     *        maximum number of chars (inclusive).  Must be non-negative.
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code maxLen < 0}</li>
     *   <li>if number of chars in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(CharSequence, int, int, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkMaxLength(
            T ref, int maxLen, String argName) {
        IntArgs.checkNotNegative(maxLen, "maxLen");
        int minLen = -1;
        _checkLengthRangeCore(ref, minLen, maxLen, argName);
        return ref;
    }
    
    /**
     * Tests if a string reference is not null and has an exact length.
     * Length is defined as the number of characters in a CharSequence reference.
     * Kindly remember that characters in East Asian languages are frequently two bytes.
     * 
     * @param ref
     *        a string reference
     * @param exactLen
     *        exact number of chars (inclusive).  Must be non-negative. 
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated string reference
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code exactLen < 0}</li>
     *   <li>if number of chars in {@code ref} is outside allowed range</li>
     * </ul>
     *
     * @see #checkLengthRange(CharSequence, int, int, String)
     */
    @FullyTested
    public static <T extends CharSequence> T checkExactLength(
            T ref, int exactLen, String argName) {
        IntArgs.checkNotNegative(exactLen, "exactLen");
        _checkLengthRangeCore(ref, exactLen, exactLen, argName);
        return ref;
    }
    
    /**
     * Tests if a string reference is not null and an index is valid to insert an character.
     * 
     * @param ref
     *        an string reference
     * @param index
     *        index of character to insert.  Must be non-negative.
     * @param refArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName
     *        argument name for {@code index}, e.g., "strListIndex"
     *
     * @return the validated index
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code index >= ref.length()}</li>
     * </ul>
     */
    @FullyTested
    public static <T extends CharSequence> int checkInsertIndex(
            T ref, int index, String refArgName, String indexArgName) {
        int len = (null == ref ? -1 : ref.length());
        ContainerArgs._checkInsertIndex(
            ref, "String", len, index, refArgName, indexArgName);
        return index;
    }
    
    /**
     * Tests if an string reference is not null, and an index and count is valid to
     * access characters.
     * 
     * @param ref
     *        a string reference
     * @param index
     *        index of character to access.  Must be non-negative.
     * @param count
     *        number of characters to access, starting from {@code index}.
     *              Must be non-negative.
     * @param refArgName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     * @param indexArgName
     *        argument name for {@code index}, e.g., "strListIndex"
     * @param countArgName
     *        argument name for {@code count}, e.g., "strListCount"
     *
     * @throws NullPointerException
     *         if {@code ref} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *   <li>if {@code ref} is empty</li>
     *   <li>if {@code count < 0}</li>
     * </ul>
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>if {@code index >= ref.length()}</li>
     *   <li>if {@code index + count > ref.length()}</li>
     * </ul>
     */
    @FullyTested
    public static <T extends CharSequence> void checkIndexAndCount(
            T ref,
            int index,
            int count,
            String refArgName,
            String indexArgName,
            String countArgName) {
        int len = (null == ref ? -1 : ref.length());
        ContainerArgs._checkIndexAndCount(
            ref, "String", len, index, count, refArgName, indexArgName, countArgName);
    }
}
