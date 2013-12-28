package com.googlecode.kevinarpe.papaya.string;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.filesystem.comparator.FileNameNumericSmallestToLargestComparator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tiny class to find the numeric prefix of a {@link String}, e.g., {@code "123abc" -> 123}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TString>
 *        data type to search; usually {@link String}
 *
 * @see FileNameNumericSmallestToLargestComparator
 */
@FullyTested
public final class NumericPrefix<TString extends CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("^([+-]?\\d+)");
    private static final long NOT_FOUND = -1;

    private final TString _input;
    private final long _value;

    /**
     * Scans char data to find a numeric prefix.
     *
     * @param str
     *        to search for a numeric prefix.
     *        Must not be {@code null}, but empty string is allowed.
     *
     * @throws NullPointerException
     *         if {@code str} is {@code null}
     * @throws NumberFormatException
     *         if numeric prefix cannot be parsed as a {@code long} value
     *
     * @see #hasNumericValue()
     * @see #getNumericValue()
     * @see Long#parseLong(String)
     */
    public NumericPrefix(TString str) {
        _input = ObjectArgs.checkNotNull(str, "str");

        Matcher matcher = PATTERN.matcher(str);
        long value = NOT_FOUND;
        if (matcher.find()) {
            String valueStr = matcher.group();
            value = Long.parseLong(valueStr);
        }
        _value = value;
    }

    /**
     * @return original input value to constructor
     *
     * @see #hasNumericValue()
     * @see #getNumericValue()
     */
    public TString getInput() {
        return _input;
    }

    /**
     * @return {@code true} if numeric prefix was found, else {@code false}
     *
     * @see #getNumericValue()
     * @see #getInput()
     */
    public boolean hasNumericValue() {
        return _value != NOT_FOUND;
    }

    /**
     * @return numeric prefix as a {@code long} value
     *
     * @throws IllegalStateException
     *         if {@link #hasNumericValue()} is {@code false}
     */
    public long getNumericValue() {
        if (!hasNumericValue()) {
            throw new IllegalStateException(String.format(
                "String does not have a numeric prefix: '%s'", _input));
        }
        return _value;
    }
}
