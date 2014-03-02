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

/**
 * Extends interface {@link SharedQuotingJoinerSettings} to add settings specific to
 * {@link QuotingMapJoiner}.
 * <p>
 * See {@link SharedQuotingJoinerSettings} for an inheritance diagram.
 *
 * @param <TSelf>
 *        type that extends this interface (for method chaining)
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see SharedQuotingJoinerSettings
 * @see QuotingMapJoiner
 */
public interface QuotingMapJoinerSettings<TSelf extends QuotingMapJoinerSettings<TSelf>>
extends SharedQuotingJoinerSettings<TSelf, TSelf> {

    /**
     * Retrieves the current key-value separator.
     *
     * @return key-value separator
     *
     * @see #withKeyValueSeparator(String)
     * @see #withKeyValueSeparator(char)
     */
    String withKeyValueSeparator();

    /**
     * Constructs a new instance with new key prefix and suffix.  These values are prepended or
     * appended to each key after its conversion to {@code String} during a join.
     *
     * @param leftQuote
     *        String to prefix to keys during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "("} (left paren/bracket)
     * @param rightQuote
     *        String to suffix to keys during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ")"} (right paren/bracket)
     *
     * @return new instance with new prefix and suffix for each key
     *
     * @throws NullPointerException
     *         if {@code leftQuote} or {@code rightQuote} is {@code null}
     *
     * @see #withKeyQuotes(String, char)
     * @see #withKeyQuotes(char, String)
     * @see #withKeyQuotes(char, char)
     * @see #withKeyLeftQuote()
     * @see #withKeyRightQuote()
     * @see QuotingJoinerUtils#DEFAULT_KEY_LEFT_QUOTE
     * @see QuotingJoinerUtils#DEFAULT_KEY_RIGHT_QUOTE
     */
    TSelf withKeyQuotes(String leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withKeyQuotes(String, String)}.
     */
    TSelf withKeyQuotes(String leftQuote, char rightQuote);

    /**
     * This is a convenience method to call {@link #withKeyQuotes(String, String)}.
     */
    TSelf withKeyQuotes(char leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withKeyQuotes(String, String)}.
     */
    TSelf withKeyQuotes(char leftQuote, char rightQuote);

    /**
     * Retrieves the current key prefix.  The default is {@code ""} (empty string).
     *
     * @return current key prefix
     *
     * @see #withKeyQuotes(String, String)
     * @see #withKeyRightQuote()
     * @see QuotingJoinerUtils#DEFAULT_KEY_LEFT_QUOTE
     */
    String withKeyLeftQuote();

    /**
     * Retrieves the current key suffix.  The default is {@code ""} (empty string).
     *
     * @return current key suffix
     *
     * @see #withKeyQuotes(String, String)
     * @see #withKeyLeftQuote()
     * @see QuotingJoinerUtils#DEFAULT_KEY_RIGHT_QUOTE
     */
    String withKeyRightQuote();

    /**
     * Constructs a new instance with new value prefix and suffix.  These values are prepended or
     * appended to each value after its conversion to {@code String} during a join.
     *
     * @param leftQuote
     *        String to prefix to values during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "("} (left paren/bracket)
     * @param rightQuote
     *        String to suffix to values during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ")"} (right paren/bracket)
     *
     * @return new instance with new prefix and suffix for each value
     *
     * @throws NullPointerException
     *         if {@code leftQuote} or {@code rightQuote} is {@code null}
     *
     * @see #withValueQuotes(String, char)
     * @see #withValueQuotes(char, String)
     * @see #withValueQuotes(char, char)
     * @see #withValueLeftQuote()
     * @see #withValueRightQuote()
     * @see QuotingJoinerUtils#DEFAULT_VALUE_LEFT_QUOTE
     * @see QuotingJoinerUtils#DEFAULT_VALUE_RIGHT_QUOTE
     */
    TSelf withValueQuotes(String leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withValueQuotes(String, String)}.
     */
    TSelf withValueQuotes(String leftQuote, char rightQuote);

    /**
     * This is a convenience method to call {@link #withValueQuotes(String, String)}.
     */
    TSelf withValueQuotes(char leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withValueQuotes(String, String)}.
     */
    TSelf withValueQuotes(char leftQuote, char rightQuote);

    /**
     * Retrieves the current value prefix.  The default is {@code ""} (empty string).
     *
     * @return current value prefix
     *
     * @see #withValueQuotes(String, String)
     * @see #withValueRightQuote()
     * @see QuotingJoinerUtils#DEFAULT_VALUE_LEFT_QUOTE
     */
    String withValueLeftQuote();

    /**
     * Retrieves the current value suffix.  The default is {@code ""} (empty string).
     *
     * @return current value suffix
     *
     * @see #withValueQuotes(String, String)
     * @see #withValueLeftQuote()
     * @see QuotingJoinerUtils#DEFAULT_VALUE_RIGHT_QUOTE
     */
    String withValueRightQuote();

    /**
     * Constructs a new instance with new text to use when joining {@code null} keys.
     *
     * @param keyNullText
     *        String to be used for {@code null} keys.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "null"}
     *
     * @return new instance with new text for {@code null} keys
     *
     * @throws NullPointerException
     *         if {@code nullText} is {@code null}
     *
     * @see #useForNullKey(char)
     * @see #useForNullKey()
     * @see QuotingJoinerUtils#DEFAULT_KEY_NULL_TEXT
     */
    TSelf useForNullKey(String keyNullText);

    /**
     * This is a convenience method to call {@link #useForNullKey(String)}.
     */
    TSelf useForNullKey(char keyNullText);

    /**
     * Retrieves text to use when joining {@code null} keys.  Default is {@code "null"}.
     *
     * @return null key text
     *
     * @see #useForNullKey(String)
     * @see QuotingJoinerUtils#DEFAULT_KEY_NULL_TEXT
     */
    String useForNullKey();

    /**
     * Constructs a new instance with new text to use when joining {@code null} values.
     *
     * @param valueNullText
     *        String to be used for {@code null} values.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "null"}
     *
     * @return new instance with new text for {@code null} values
     *
     * @throws NullPointerException
     *         if {@code nullText} is {@code null}
     *
     * @see #useForNullValue(char)
     * @see #useForNullValue()
     * @see QuotingJoinerUtils#DEFAULT_VALUE_NULL_TEXT
     */
    TSelf useForNullValue(String valueNullText);

    /**
     * This is a convenience method to call {@link #useForNullValue(String)}.
     */
    TSelf useForNullValue(char valueNullText);

    /**
     * Retrieves text to use when joining {@code null} values.  Default is {@code "null"}.
     *
     * @return null value text
     *
     * @see #useForNullValue(String)
     * @see QuotingJoinerUtils#DEFAULT_VALUE_NULL_TEXT
     */
    String useForNullValue();
}
