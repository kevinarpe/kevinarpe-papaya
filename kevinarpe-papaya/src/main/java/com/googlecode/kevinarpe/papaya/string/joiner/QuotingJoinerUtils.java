package com.googlecode.kevinarpe.papaya.string.joiner;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/**
 * Constants and static utilities for {@link QuotingJoiner} and {@link QuotingMapJoiner}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #withSeparator(String)
 * @see #withSeparator(char)
 * @see #newQuotingJoinerFactory(String)
 * @see #newQuotingJoinerFactory(char)
 */
// TODO: Add feature for counting?
@FullyTested
public final class QuotingJoinerUtils {
    
    private QuotingJoinerUtils() {
        // disabled
    }

    /**
     * Default value for {@link SharedQuotingJoinerSettings#withLeftQuote()}: {@code ""}
     * (empty string).
     *
     * @see #DEFAULT_RIGHT_QUOTE
     * @see SharedQuotingJoinerSettings#withQuotes(String, String)
     */
    public static final String DEFAULT_LEFT_QUOTE = "";
    /**
     * Default value for {@link SharedQuotingJoinerSettings#withRightQuote()}: {@code ""}
     * (empty string).
     *
     * @see #DEFAULT_LEFT_QUOTE
     * @see SharedQuotingJoinerSettings#withQuotes(String, String)
     */
    public static final String DEFAULT_RIGHT_QUOTE = "";
    /**
     * Default value for {@link SharedQuotingJoinerSettings#useForNoElements()}: {@code ""}
     * (empty string).
     *
     * @see SharedQuotingJoinerSettings#useForNoElements(String)
     */
    public static final String DEFAULT_NO_ELEMENTS_TEXT = "";
    /**
     * Default value for {@link QuotingJoinerSettings#useForNull()}: {@code "null"}.
     * <p>
     * This default differs from class {@link Joiner}, which uses {@code null}, and throws a
     * {@link NullPointerException} when joining a {@code null} value, unless
     * {@link Joiner#useForNull(String)} is called before joining.
     *
     * @see QuotingJoinerSettings#useForNull(String)
     */
    public static final String DEFAULT_NULL_TEXT = "null";
    /**
     * Default value for {@link QuotingJoinerSettings#skipNulls()}: {@code false}.
     *
     * @see QuotingJoinerSettings#skipNulls(boolean)
     */
    public static final boolean DEFAULT_SKIP_NULLS_FLAG = false;

    /**
     * Default value for {@link QuotingMapJoinerSettings#withKeyLeftQuote()}: {@code ""}
     * (empty string).
     *
     * @see QuotingMapJoinerSettings#withKeyQuotes(String, String)
     */
    public static final String DEFAULT_KEY_LEFT_QUOTE = "";

    /**
     * Default value for {@link QuotingMapJoinerSettings#withKeyRightQuote()}: {@code ""}
     * (empty string).
     *
     * @see QuotingMapJoinerSettings#withKeyQuotes(String, String)
     */
    public static final String DEFAULT_KEY_RIGHT_QUOTE = "";

    /**
     * Default value for {@link QuotingMapJoinerSettings#withValueLeftQuote()}: {@code ""}
     * (empty string).
     *
     * @see QuotingMapJoinerSettings#withValueQuotes(String, String)
     */
    public static final String DEFAULT_VALUE_LEFT_QUOTE = "";

    /**
     * Default value for {@link QuotingMapJoinerSettings#withValueRightQuote()}: {@code ""}
     * (empty string).
     *
     * @see QuotingMapJoinerSettings#withValueQuotes(String, String)
     */
    public static final String DEFAULT_VALUE_RIGHT_QUOTE = "";

    /**
     * Default value for {@link QuotingMapJoinerSettings#useForNullKey()}: {@code "null"}.
     *
     * @see QuotingMapJoinerSettings#useForNullKey(String)
     */
    public static final String DEFAULT_KEY_NULL_TEXT = "null";

    /**
     * Default value for {@link QuotingMapJoinerSettings#useForNullValue()}: {@code "null"}.
     *
     * @see QuotingMapJoinerSettings#useForNullValue(String)
     */
    public static final String DEFAULT_VALUE_NULL_TEXT = "null";

    /**
     * Constructs a new instance of {@link QuotingJoiner}.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     *
     * @see #withSeparator(char)
     * @see QuotingJoiner#withSeparator(String)
     * @see QuotingJoiner#withSeparator(char)
     */
    public static QuotingJoiner withSeparator(String separator) {
        QuotingJoinerFactoryImpl factory = new QuotingJoinerFactoryImpl(separator);
        QuotingJoiner x = factory.newInstance();
        return x;
    }

    /**
     * This is a convenience method to call {@link #withSeparator(String)}.
     */
    public static QuotingJoiner withSeparator(char separator) {
        String separatorString = String.valueOf(separator);
        QuotingJoiner x = withSeparator(separatorString);
        return x;
    }

    /**
     * Constructs a new instance of {@link QuotingJoinerFactory}.  Unless an additional layer of
     * indirection is required for mocking or testing, it is usually sufficient to call
     * {@link #withSeparator(String)}.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     */
    public static QuotingJoinerFactory newQuotingJoinerFactory(String separator) {
        QuotingJoinerFactoryImpl x = new QuotingJoinerFactoryImpl(separator);
        return x;
    }

    /**
     * This is a convenience method to call {@link #newQuotingJoinerFactory(String)}.
     */
    public static QuotingJoinerFactory newQuotingJoinerFactory(char separator) {
        String separatorString = String.valueOf(separator);
        QuotingJoinerFactory x = newQuotingJoinerFactory(separatorString);
        return x;
    }
}
