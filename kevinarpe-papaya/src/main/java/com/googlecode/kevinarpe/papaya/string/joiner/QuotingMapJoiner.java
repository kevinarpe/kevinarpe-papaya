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

import java.io.IOException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;

/**
 * Extension of Google Guava's {@link Joiner.MapJoiner}.  To construct a new instance, see
 * {@link QuotingJoiners#withSeparator(String)} and {@link QuotingJoiners#withSeparator(char)}.
 * <p>
 * Differences to {@link Joiner.MapJoiner}:
 * <ul>
 *     <li>{@link #withKeyQuotes(String, String)}: Prefix and suffix for each joined key</li>
 *     <li>{@link #withValueQuotes(String, String)}: Prefix and suffix for each joined value</li>
 *     <li>Default text for {@code null} keys and values is {@code "null"}.  This matches the
 *     behavior of {@link Formatter}. By default, class {@link Joiner} will throw a
 *     {@link NullPointerException} when joining a {@code null} key or value, unless
 *     {@link Joiner#useForNull(String)} is called before joining.  This is a source of many
 *     surprising/annoying/accidental runtime exceptions.</li>
 *     <li>{@link #useForNoElements(String)}: During a join, if no map entries are found, this text
 *     is used.  When joining map entries for an exception or log message, {@code "(empty)"} is a
 *     good value to indicate to (exception and log) readers that no map entries were found.</li>
 *     <li>Uses interface {@link QuotingMapJoiner}, instead of a concrete class.  This follows
 *     JavaEE design principles, and is helpful for mocking and testing.  Due to Java interface
 *     limitations with generic methods, the methods {@code appendTo(Appendable, *)} are slightly
 *     different from {@link Joiner.MapJoiner}.  The {@code Appendable} reference is not a generic
 *     type (for input and output).</li>
 *     <li>All settings may always be changed, e.g., key-value separator, null key text, etc.  Class
 *     {@link Joiner.MapJoiner} does now allow these attributes to be set more than once.</li>
 *     <li>Settings accessors, e.g., {@link #withKeyValueSeparator(String)}.</li>
 *     <li>Default settings are available as {@code public static final} members, e.g.,
 *     {@link QuotingJoiners#DEFAULT_KEY_NULL_TEXT}.</li>
 * </ul>
 * <p>
 * See {@link SharedQuotingJoinerSettings} for an inheritance diagram.
 * <p>
 * Examples:
 * <pre>{@code
 * QuotingJoiners.withSeparator(", ").withQuotes("[", "]").withKeyValueSeparator("=")
 *     .join(map) -> "[a=1], [b=2], [c=3], ..."
 * QuotingJoiners.withSeparator(", ").withQuotes("[", "]").withKeyValueSeparator("=")
 *     .join(mapWithNulls) -> "[a=1], [b=2], [null=3], [c=4], ..."
 * QuotingJoiners.withSeparator(", ").withQuotes("[", "]").withKeyValueSeparator("=").skipNulls(true)
 *     .join(mapWithNulls) -> "[a=1], [b=2], [c=3], ..."
 * QuotingJoiners.withSeparator(", ").withQuotes("[", "]").withKeyValueSeparator("=").useForNoElements("(empty)")
 *     .join(emptyMap) -> "(empty)"
 * }</pre>
 * <p>
 * See {@link SharedQuotingJoinerSettings} for an inheritance diagram.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see QuotingJoiners
 * @see QuotingMapJoinerSettings
 * @see QuotingJoiner
 */
public interface QuotingMapJoiner
extends QuotingMapJoinerSettings<QuotingMapJoiner> {

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code map} is {@code null}
     */
    Appendable appendTo(Appendable appendable, Map<?, ?> map)
    throws IOException;

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partIterable} is {@code null}
     */
    Appendable appendTo(Appendable appendable, Iterable<? extends Map.Entry<?, ?>> partIterable)
    throws IOException;

    /**
     * Appends all remaining map entries from an {@link Iterator} to an instance of
     * {@link Appendable}.
     * <p>
     * Due to Java interface limitations with generic methods, this method signature is slightly
     * different to {@link Joiner#appendTo(Appendable, Iterator)}.  The {@code Appendable} reference
     * is not a generic type (for input and output).
     *
     * @param appendable
     *        where to append text.  Must not be {@code null}.
     *
     * @param partIter
     *        stream of map entries to join.  Must not be {@code null}.
     *        Must not contain {@code null} map entries
     *
     * @return argument {@code appendable} (for method chaining)
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partIter} (or its elements) are {@code null}
     * @throws IOException
     *         if {@code appendable} throws an {@link IOException}
     *
     * @see #appendTo(Appendable, Iterable)
     * @see #appendTo(Appendable, Map)
     * @see #appendTo(StringBuilder, Iterator)
     * @see #join(Iterator)
     */
    Appendable appendTo(Appendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException;

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code builder} or {@code map} is {@code null}
     */
    StringBuilder appendTo(StringBuilder builder, Map<?, ?> map);

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code builder} or {@code partIterable} is {@code null}
     */
    StringBuilder appendTo(StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> partIterable);

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterator)}.
     *
     * @see #appendTo(StringBuilder, Iterable)
     * @see #appendTo(StringBuilder, Map)
     */
    StringBuilder appendTo(StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> partIter);

    /**
     * This is a convenience method to call {@link #join(Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code map} is {@code null}
     */
    String join(Map<?, ?> map);

    /**
     * This is a convenience method to call {@link #join(Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code partIterable} is {@code null}
     */
    String join(Iterable<? extends Map.Entry<?, ?>> partIterable);

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterator)} with a new
     * instance of {@link StringBuilder}.
     */
    String join(Iterator<? extends Map.Entry<?, ?>> partIter);
}
