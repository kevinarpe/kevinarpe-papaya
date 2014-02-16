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
import java.util.Iterator;

/**
 * Extends interface {@link QuotingJoinerSettings} to add {@code appendTo(*)} and {@code join(*)}
 * methods.  Class {@link QuotingJoiner} fully implements this interface.
 * <p>
 * See {@link SharedQuotingJoinerSettings} for an inheritance diagram.
 *
 * @param <TSelf>
 *        type that extends this interface (for method chaining)
 * @param <TQuotingMapJoiner>
 *        type that extends {@link IQuotingMapJoiner}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see QuotingJoinerSettings
 * @see QuotingJoiner
 */
public interface IQuotingJoiner
    <
        TSelf extends IQuotingJoiner<TSelf, TQuotingMapJoiner>,
        TQuotingMapJoiner extends IQuotingMapJoiner<TQuotingMapJoiner>
    >
extends QuotingJoinerSettings<TSelf, TQuotingMapJoiner> {

    // TODO: Flaw in these interfaces?  Need to return IQuotingJoiner/IQuotingMapJoiner instead of concrete classes.

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
     * This method signature differs slightly from {@link Joiner} because the
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
