package com.googlecode.kevinarpe.papaya.string.joiner;

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
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;

import java.io.IOException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;

/**
 * Extension of Google Guava's {@link com.google.common.base.Joiner.MapJoiner}.  To construct a new
 * instance, see {@link Joiner2Utils#withSeparator(String)} and
 * {@link Joiner2Utils#withSeparator(char)}.
 * <p>
 * Differences to {@code Joiner.MapJoiner}:
 * <ul>
 *     <li>{@link #withKeyFormatter(Formatter2)}: Format each joined key precisely.  Easily add a
 *     prefix and/or suffix, e.g., {@code new StringFormatter("[%s]")}</li>
 *     <li>{@link #withValueFormatter(Formatter2)}: Format each joined value precisely.</li>
 *     <li>{@link #withElementFormatter(Formatter2)}: Format each joined key-value pair precisely.</li>
 *     <li>{@link #withFinalFormatter(Formatter2)}: Format all joined elements.</li>
 *     <li>Default text for {@code null} keys and values is {@code "null"}.  This matches the
 *     behavior of {@link Formatter}. By default, class {@link Joiner} will throw a
 *     {@link NullPointerException} when joining a {@code null} key or value, unless
 *     {@link Joiner#useForNull(String)} is called before joining.  This is a source of many
 *     surprising/annoying/accidental runtime exceptions.</li>
 *     <li>{@link #useForNoElements(String)}: During a join, if no map entries are found, this text
 *     is used.  When joining map entries for an exception or log message, {@code "<empty>"} is a
 *     good value to indicate to (exception and log) readers that no map entries were found.</li>
 *     <li>Uses interface {@link MapJoiner2}, instead of a concrete class.  This follows
 *     JavaEE design principles, and is helpful for mocking and testing.  Due to Java interface
 *     limitations with generic methods, the methods {@code appendTo(Appendable, *)} are slightly
 *     different from {@code Joiner.MapJoiner}.  The {@code Appendable} reference is not a generic
 *     type (for input and output).</li>
 *     <li>All settings may always be changed, e.g., key-value separator, null key text, etc.  Class
 *     {@code Joiner.MapJoiner} does now allow these attributes to be set more than once.</li>
 *     <li>Settings accessors, e.g., {@link #withKeyValueSeparator(String)}.</li>
 *     <li>Default settings are available as {@code public static final} members, e.g.,
 *     {@link Joiner2Utils#DEFAULT_KEY_NULL_TEXT}.</li>
 * </ul>
 * <p>
 * See {@link SharedJoiner2Settings} for an inheritance diagram.
 * <p>
 * Examples:
 * <pre>{@code
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withElementFormatter(new StringFormatter("[%s]"))
 *     .withKeyValueSeparator("=")
 *     .withKeyFormatter(new StringFormatter("(%s)"))
 *     .withValueFormatter(new StringFormatter("{%s}"))
 *     .join(map) -> "[(a)={1}], [(b)={2}], [(c)={3}], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withElementFormatter(new StringFormatter("[%s]"))
 *     .withKeyValueSeparator("=")
 *     .withKeyFormatter(new StringFormatter("(%s)"))
 *     .withValueFormatter(new StringFormatter("{%s}"))
 *     .join(mapWithNulls) -> "[(a)={1}], [(b)={2}], [(null)={3}], [(c)={4}], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withElementFormatter(new StringFormatter("[%s]"))
 *     .withKeyValueSeparator("=")
 *     .withKeyFormatter(new StringFormatter("(%s)"))
 *     .withValueFormatter(new StringFormatter("{%s}"))
 *     .skipNulls(true)
 *     .join(mapWithNulls) -> "[(a)={1}], [(b)={2}], [(c)={3}], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withElementFormatter(new StringFormatter("[%s]"))
 *     .withKeyValueSeparator("=")
 *     .withKeyFormatter(new StringFormatter("(%s)"))
 *     .withValueFormatter(new StringFormatter("{%s}"))
 *     .useForNoElements("<empty>")
 *     .join(emptyMap) -> "<empty>"
 * }</pre>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Joiner2Utils
 * @see MapJoiner2Settings
 * @see Joiner2
 */
public interface MapJoiner2
extends MapJoiner2Settings<MapJoiner2> {

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
