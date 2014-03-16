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
 * Settings shared between {@link Joiner2} and {@link MapJoiner2}.
 * <p>
 * Inheritance diagram:
 * <pre>{@code
 *     interface
 *     SharedJoiner2Settings
 *        /                   \
 * interface                 interface
 * Joiner2Settings     MapJoiner2Settings
 *    |                         |
 * interface                 interface
 * Joiner2             MapJoiner2
 *    |                         |
 * class                     class
 * Joiner2Impl         MapJoiner2Impl
 * }</pre>
 *
 * @param <TSelf>
 *        type that extends this interface (for method chaining)
 * @param <TQuotingMapJoinerSettings>
 *        type that extends {@link MapJoiner2Settings}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Joiner2Settings
 * @see MapJoiner2Settings
 */
public interface SharedJoiner2Settings
    <
        TSelf extends SharedJoiner2Settings<TSelf, TQuotingMapJoinerSettings>,
        TQuotingMapJoinerSettings extends MapJoiner2Settings<TQuotingMapJoinerSettings>
    >
{

    /**
     * Constructs a new instance with new element separator.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance with new element separator
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     *
     * @see #withSeparator(char)
     * @see #withSeparator()
     */
    TSelf withSeparator(String separator);

    /**
     * This is a convenience method to call {@link #withSeparator(String)}.
     */
    TSelf withSeparator(char separator);

    /**
     * Retrieves the current element separator.
     *
     * @return element separator
     *
     * @see #withSeparator(String)
     * @see #withSeparator(char)
     */
    String withSeparator();

    /**
     * Constructs a new instance with new element prefix and suffix.  These values are prepended or
     * appended to each element after its conversion to {@code String} during a join.
     *
     * @param leftQuote
     *        String to prefix to elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "("} (left paren/bracket)
     * @param rightQuote
     *        String to suffix to elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ")"} (right paren/bracket)
     *
     * @return new instance with new prefix and suffix for each element
     *
     * @throws NullPointerException
     *         if {@code leftQuote} or {@code rightQuote} is {@code null}
     *
     * @see #withQuotes(String, char)
     * @see #withQuotes(char, String)
     * @see #withQuotes(char, char)
     * @see #withLeftQuote()
     * @see #withRightQuote()
     * @see Joiner2Utils#DEFAULT_LEFT_QUOTE
     * @see Joiner2Utils#DEFAULT_RIGHT_QUOTE
     */
    TSelf withQuotes(String leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withQuotes(String, String)}.
     */
    TSelf withQuotes(String leftQuote, char rightQuote);

    /**
     * This is a convenience method to call {@link #withQuotes(String, String)}.
     */
    TSelf withQuotes(char leftQuote, String rightQuote);

    /**
     * This is a convenience method to call {@link #withQuotes(String, String)}.
     */
    TSelf withQuotes(char leftQuote, char rightQuote);

    /**
     * Retrieves the current element prefix.  The default is {@code ""} (empty string).
     *
     * @return current element prefix
     *
     * @see #withQuotes(String, String)
     * @see #withRightQuote()
     * @see Joiner2Utils#DEFAULT_LEFT_QUOTE
     */
    String withLeftQuote();

    /**
     * Retrieves the current element suffix.  The default is {@code ""} (empty string).
     *
     * @return current element suffix
     *
     * @see #withQuotes(String, String)
     * @see #withLeftQuote()
     * @see Joiner2Utils#DEFAULT_RIGHT_QUOTE
     */
    String withRightQuote();

    /**
     * Constructs a new instance with new text to use when join finds no elements.
     *
     * @param noElementsText
     *        String to be used when join finds no elements.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "(empty)"}
     *
     * @return new instance with new no elements text
     *
     * @throws NullPointerException
     *         if {@code noElementsText} is {@code null}
     *
     * @see #useForNoElements(char)
     * @see #useForNoElements()
     * @see Joiner2Utils#DEFAULT_NO_ELEMENTS_TEXT
     */
    TSelf useForNoElements(String noElementsText);

    /**
     * This is a convenience method to call {@link #useForNoElements(String)}.
     */
    TSelf useForNoElements(char noElementsText);

    /**
     * Retrieves text to use when join finds no elements.  Default is {@code ""} (empty string).
     *
     * @return no elements text
     *
     * @see #useForNoElements(String)
     * @see Joiner2Utils#DEFAULT_NO_ELEMENTS_TEXT
     */
    String useForNoElements();

    /**
     * Constructs a new instance of {@link MapJoiner2} to join map entries.
     *
     * @param keyValueSeparator
     *        String to insert between keys and values during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "="} (equals)
     *
     * @return new instance of {@link MapJoiner2Impl}
     *
     * @throws NullPointerException
     *         if {@code keyValueSeparator} is {@code null}
     *
     * @see #withKeyValueSeparator(char)
     */
    TQuotingMapJoinerSettings withKeyValueSeparator(String keyValueSeparator);

    /**
     * This is a convenience method to call {@link #withKeyValueSeparator(String)}.
     */
    TQuotingMapJoinerSettings withKeyValueSeparator(char keyValueSeparator);
}