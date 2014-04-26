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

import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;

/**
 * Extends interface {@link SharedJoiner2Settings} to add settings specific to
 * {@link MapJoiner2}.
 * <p>
 * See {@link SharedJoiner2Settings} for an inheritance diagram.
 *
 * @param <TSelf>
 *        type that extends this interface (for method chaining)
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see SharedJoiner2Settings
 * @see MapJoiner2
 */
public interface MapJoiner2Settings<TSelf extends MapJoiner2Settings<TSelf>>
extends SharedJoiner2Settings<TSelf, TSelf> {

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
     * Constructs a new instance with new key formatter.  Formatters can be used to add a prefix
     * or suffix to each key.
     *
     * @param keyFormatter
     *        key formatter to add a prefix and/or suffix to each key.
     *        Must not be {@code null}.
     *
     * @return new instance with formatter for each key
     *
     * @throws NullPointerException
     *         if {@code keyFormatter} is {@code null}
     *
     * @see #withKeyFormatter()
     * @see #withValueFormatter(Formatter2)
     * @see Joiner2Utils#DEFAULT_KEY_FORMATTER
     */
    TSelf withKeyFormatter(Formatter2 keyFormatter);

    /**
     * Retrieves the current key formatter.  The default is no prefix or suffix.
     *
     * @return current key formatter
     *
     * @see #withKeyFormatter(Formatter2)
     * @see Joiner2Utils#DEFAULT_KEY_FORMATTER
     */
    Formatter2 withKeyFormatter();

    /**
     * Constructs a new instance with new value formatter.  Formatters can be used to add a prefix
     * or suffix to each value.
     *
     * @param valueFormatter
     *        value formatter to add a prefix and/or suffix to each value.
     *        Must not be {@code null}.
     *
     * @return new instance with formatter for each value
     *
     * @throws NullPointerException
     *         if {@code keyFormatter} is {@code null}
     *
     * @see #withValueFormatter()
     * @see #withKeyFormatter(Formatter2)
     * @see Joiner2Utils#DEFAULT_VALUE_FORMATTER
     */
    TSelf withValueFormatter(Formatter2 valueFormatter);

    /**
     * Retrieves the current value formatter.  The default is no prefix or suffix.
     *
     * @return current value formatter
     *
     * @see #withValueFormatter(Formatter2)
     * @see Joiner2Utils#DEFAULT_VALUE_FORMATTER
     */
    Formatter2 withValueFormatter();

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
     * @see Joiner2Utils#DEFAULT_KEY_NULL_TEXT
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
     * @see Joiner2Utils#DEFAULT_KEY_NULL_TEXT
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
     * @see Joiner2Utils#DEFAULT_VALUE_NULL_TEXT
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
     * @see Joiner2Utils#DEFAULT_VALUE_NULL_TEXT
     */
    String useForNullValue();
}
