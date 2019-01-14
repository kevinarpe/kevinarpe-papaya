package com.googlecode.kevinarpe.papaya.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Collection;
import java.util.Comparator;

/**
 * Most comparators are written to order items as least-to-greatest, e.g., smallest to largest
 * numbers in a collection.  This class is a simple wrapper to safely reverse the ordering.
 * <p>
 * Do not call the constructor directly; instead use the static helper method
 * {@link #of(Comparator)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of object to be compared
 * @see Comparator
 * @see #of(Comparator)
 */
@FullyTested
public final class ReverseComparator<TValue>
implements Comparator<TValue> {

    /**
     * Returns a {@link Comparator} that inverts the result of {@link #compare(Object, Object)}.
     * If the input is an instance of {@link ReverseComparator}, the inner {@link Comparator} is
     * returned.  This is inspired by {@link ImmutableList#copyOf(Collection)}.
     *
     * @param comparator
     *        reference to an existing {@link Comparator}; must not be {@code null}
     * @param <TValue>
     *        type of object to be compared
     *
     * @return {@link Comparator} that inverts the result of {@link #compare(Object, Object)}
     *
     * @throws NullPointerException
     *         if {@code compare} is {@code null}
     */
    public static <TValue> Comparator<TValue> of(Comparator<TValue> comparator) {
        ObjectArgs.checkNotNull(comparator, "compare");

        if (comparator instanceof ReverseComparator) {
            return ((ReverseComparator<TValue>) comparator).comparator;
        }
        return new ReverseComparator<TValue>(comparator);
    }

    private final Comparator<TValue> comparator;

    private ReverseComparator(Comparator<TValue> comparator) {
        this.comparator = comparator;
    }

    /**
     * Inverts the result from the inner {@link Comparator} by multiplying by {@code -1}.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public int compare(TValue value1, TValue value2) {
        return -1 * comparator.compare(value1, value2);
    }

    /**
     * Correctly compares the inner {@link Comparator} references for equality.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (this == obj ||
            (obj instanceof ReverseComparator &&
                this.comparator.equals(((ReverseComparator) obj).comparator)));
    }

    /**
     * @return hash code for inner compare
     */
    @Override
    public int hashCode() {
        return this.comparator.hashCode();
    }

    /**
     * Returns a string suitable for debugging.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String x = String.format("%s {%n\t%s: %s (%s)%n}",
            this.getClass().getName(),
            "compare", comparator.getClass().getName(), comparator);
        return x;
    }
}
