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
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;

import java.io.IOException;
import java.util.Formatter;
import java.util.Iterator;

/**
 * Extension of Google Guava's {@link Joiner}.  To construct a new instance, see
 * {@link Joiner2Utils#withSeparator(String)} and {@link Joiner2Utils#withSeparator(char)}.
 * <p>
 * Differences to {@link Joiner}:
 * <ul>
 *     <li>{@link #withFormatter(Formatter2)}: Format each joined element precisely.  Easily add a
 *     prefix and/or suffix, e.g., {@code new StringFormatter("[%s]")}</li>
 *     <li>Default text for {@code null} values is {@code "null"}.  This matches the behavior of
 *     {@link Formatter}. By default, class {@link Joiner} will throw a {@link NullPointerException}
 *     when joining a {@code null} value, unless {@link Joiner#useForNull(String)} is called before
 *     joining.  This is a source of many surprising/annoying/accidental runtime exceptions.</li>
 *     <li>{@link #useForNoElements(String)}: During a join, if no elements are found, this text
 *     is used.  When joining elements for an exception or log message, {@code "(empty)"} is a good
 *     value to indicate to (exception and log) readers that no elements were found.</li>
 *     <li>Uses interface {@link Joiner2}, instead of a concrete class.  This follows JavaEE
 *     design principles, and is helpful for mocking and testing.  Due to Java interface limitations
 *     with generic methods, the methods {@code appendTo(Appendable, *)} are slightly different from
 *     {@link Joiner}.  The {@code Appendable} reference is not a generic type (for input and
 *     output).</li>
 *     <li>All settings may always be changed, e.g., separator, null text, etc.  Class
 *     {@link Joiner} does now allow these attributes to be set more than once.</li>
 *     <li>Settings accessors, e.g., {@link #withSeparator()}.</li>
 *     <li>Default settings are available as {@code public static final} members, e.g.,
 *     {@link Joiner2Utils#DEFAULT_NULL_TEXT}.</li>
 * </ul>
 * <p>
 * Examples:
 * <pre>{@code
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withFormatter(new StringFormatter("[%s]"))
 *     .join(list) -> "[a], [b], [c], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withFormatter(new StringFormatter("[%s]"))
 *     .join(listWithNulls) -> "[a], [b], [null], [c], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withFormatter(new StringFormatter("[%s]"))
 *     .skipNulls(true)
 *     .join(listWithNulls) -> "[a], [b], [c], ..."
 * Joiner2Utils.INSTANCE.withSeparator(", ")
 *     .withFormatter(new StringFormatter("[%s]"))
 *     .useForNoElements("(empty)")
 *     .join(emptyList) -> "(empty)"
 * }</pre>
 * <p>
 * See {@link SharedJoiner2Settings} for an inheritance diagram.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Joiner2Utils
 * @see Joiner2Settings
 * @see MapJoiner2
 */
public interface Joiner2
extends Joiner2Settings<Joiner2, MapJoiner2> {

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partArr} is {@code null}
     */
    Appendable appendTo(
            Appendable appendable,
            Object part1,
            Object part2,
            Object... partArr)
    throws IOException;

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partArr} is {@code null}
     */
    Appendable appendTo(Appendable appendable, Object[] partArr)
    throws IOException;

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partIterable} is {@code null}
     */
    Appendable appendTo(Appendable appendable, Iterable<?> partIterable)
    throws IOException;

    /**
     * Appends all remaining elements from an {@link Iterator} to an instance of {@link Appendable}.
     * <p>
     * Due to Java interface limitations with generic methods, this method signature is slightly
     * different to {@link Joiner#appendTo(Appendable, Iterator)}.  The {@code Appendable} reference
     * is not a generic type (for input and output).
     *
     * @param appendable
     *        where to append text.  Must not be {@code null}.
     *
     * @param partIter
     *        stream of elements to join.  Must not be {@code null}.
     *        {@code null} elements receive special treatment that depends upon settings from
     *        {@link #skipNulls()} and {@link #useForNull()}.
     *
     * @return argument {@code appendable} (for method chaining)
     *
     * @throws NullPointerException
     *         if {@code appendable} or {@code partIter} is {@code null}
     * @throws IOException
     *         if {@code appendable} throws an {@link IOException}
     *
     * @see #appendTo(Appendable, Iterable)
     * @see #appendTo(Appendable, Object[])
     * @see #appendTo(Appendable, Object, Object, Object...)
     * @see #appendTo(StringBuilder, Iterator)
     * @see #join(Iterator)
     */
    Appendable appendTo(Appendable appendable, Iterator<?> partIter)
    throws IOException;

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code builder} or {@code partArr} is {@code null}
     */
    StringBuilder appendTo(
        StringBuilder builder,
        Object part1,
        Object part2,
        Object... partArr);

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code builder} or {@code partArr} is {@code null}
     */
    StringBuilder appendTo(StringBuilder builder, Object[] partArr);

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code builder} or {@code partIterable} is {@code null}
     */
    StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable);

    /**
     * This is a convenience method to call {@link #appendTo(Appendable, Iterator)}.
     *
     * @see #appendTo(StringBuilder, Iterable)
     * @see #appendTo(StringBuilder, Object[])
     * @see #appendTo(StringBuilder, Object, Object, Object...)
     */
    StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter);

    /**
     * This is a convenience method to call {@link #join(Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code partArr} is {@code null}
     */
    String join(Object part1, Object part2, Object... partArr);

    /**
     * This is a convenience method to call {@link #join(Iterable)}.
     *
     * @throws NullPointerException
     *         if {@code partArr} is {@code null}
     */
    String join(Object[] partArr);

    /**
     * This is a convenience method to call {@link #join(Iterator)}.
     *
     * @throws NullPointerException
     *         if {@code partIterable} is {@code null}
     */
    String join(Iterable<?> partIterable);

    /**
     * This is a convenience method to call {@link #appendTo(StringBuilder, Iterator)} with a new
     * instance of {@link StringBuilder}.
     */
    String join(Iterator<?> partIter);
}
