package com.googlecode.kevinarpe.papaya.comparator;

import com.google.common.collect.ImmutableList;
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
     */
    public static <TValue> Comparator<TValue> of(Comparator<TValue> comparator) {
        ObjectArgs.checkNotNull(comparator, "comparator");

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
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public int compare(TValue value1, TValue value2) {
        return -1 * comparator.compare(value1, value2);
    }

    /**
     * Correctly compares the inner {@link Comparator} references for equality.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return (this == obj ||
            (obj instanceof ReverseComparator &&
                this.comparator.equals(((ReverseComparator) obj).comparator)));
    }

    @Override
    public String toString() {
        String x = String.format("%s {%n\t%s: %s (%s)%n}",
            this.getClass().getName(),
            "comparator", comparator.getClass().getName(), comparator);
        return x;
    }
}
