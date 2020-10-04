package com.googlecode.kevinarpe.papaya.container;

/*-
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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * This new collection blends the requirements of {@link List} and {@link Set}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface UniqueList<T>
extends List<T>, Set<T> {

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method is O(1) [constant], not O(n) [linear] because the underlying
     * {@link Set} is used.
     *
     * @see Set#contains(Object)
     */
    @Override
    boolean contains(Object o);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method will return {@code false} when argument {@code value} exists in
     * this collection.
     *
     * @see Set#add(Object)
     */
    @Override
    boolean add(@Nullable T value);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method does not require O(n) [linear] search because an underlying
     * {@link Set} is used.
     *
     * @see #contains(Object)
     */
    @Override
    boolean containsAll(@EmptyContainerAllowed Collection<?> c);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method will return {@code false} when all values in argument {@code c}
     * exists in this collection.
     *
     * @see Set#add(Object)
     */
    @Override
    boolean addAll(@EmptyContainerAllowed Collection<? extends T> c);

    /**
     * @see #addAll(Collection)
     */
    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: See {@link #addAll(Collection)}
     */
    @Override
    boolean addAll(int index, Collection<? extends T> c);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method will <b>throw</b> {@link IllegalArgumentException}
     * when {@code value} exists in this collection.
     */
    @Nullable
    @Override
    T set(int index, @Nullable T value);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Unlike {@link List}, this method will <b>throw</b> {@link IllegalArgumentException}
     * when {@code value} exists in this collection.
     */
    @Override
    void add(int index, @Nullable T element);

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link UniqueList}
     */
    @Override
    UniqueList<T>
    subList(final int fromIndex,
            final int toIndex);

    // Note: Must override as List and Set have conflicting impls.
    @Override
    default Spliterator<T> spliterator() {

        final Spliterator<T> x =
            Spliterators.spliterator(this,
                // From ImmutableList
                Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED
                // From ImmutableSet
                    | Spliterator.DISTINCT);
        return x;
    }
}
