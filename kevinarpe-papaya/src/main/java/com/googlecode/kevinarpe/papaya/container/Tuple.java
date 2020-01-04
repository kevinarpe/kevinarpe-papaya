package com.googlecode.kevinarpe.papaya.container;

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

/**
 * N-element tuple, which may be empty.  This interface (and abstract helper class) was created
 * satisfy the need to easily create multi-element keys and values for maps.
 * <p>
 * Ref: <a href="http://code.google.com/p/guava-libraries/wiki/IdeaGraveyard"
 * >http://code.google.com/p/guava-libraries/wiki/IdeaGraveyard</a>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        tuple element type; may be {@link Object} for tuples with mixed element types
 *
 * @see AbstractTuple
 */
// TODO: Create ForwardingTuple?
public interface Tuple<TValue> {

    /**
     * Retrieves a tuple element by its zero-based index.
     *
     * @param index
     *        zero-based index to access a tuple element
     *
     * @return value at {@code index}
     *
     * @throws IllegalArgumentException
     *         if this tuple is empty
     * @throws IndexOutOfBoundsException
     * <ul>
     *   <li>if {@code index < 0}</li>
     *   <li>{@code index >= tuple.length}</li>
     * </ul>
     *
     * @see #size()
     * @see #toArray()
     */
    TValue get(int index);

    /**
     * Retrieves the number of elements in this tuple.
     *
     * @see #get(int)
     */
    int size();

    /**
     * Creates a new array with all elements in this tuple.
     *
     * @see #get(int)
     */
    TValue[] toArray();

    /**
     * Joins all elements in this tuple to a string.
     * <p>
     * Example: {@code "("abc", 123, 'Q', true, 456.789, ...}"}
     * <hr>
     * {@inheritDoc}
     */
    @Override
    String toString();

    /**
     * Must be correctly implemented to include all tuple elements.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    int hashCode();

    /**
     * Must be correctly implemented to include all tuple elements.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);
}
