package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingIterator;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Iterator;

/**
 * This class is a simple extension of {@link ForwardingIterator}, where method {@link #remove()}
 * is disabled -- always throws {@link UnsupportedOperationException}.
 * <p>
 * The class constructor is private.  Instead, use static method {@link #of(Iterator)}.
 * <p>
 * This class is {@code final} to disable modify-by-inheritance.
 * <p>
 * Why is this class not called {@code ImmutableIterator}?  Read more here:
 * <a href="http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection"
 * >http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection</a>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ForwardingIterator
 * @see UnmodifiableListIterator
 * @see #of(Iterator)
 * @see FixedSizeForwardingList#iterator()
 * @see UnmodifiableForwardingList#iterator()
 */
@FullyTested
public final class UnmodifiableIterator<TValue>
extends ForwardingIterator<TValue> {

    /**
     * Returns an unmodifiable view of an underlying {@link Iterator}.  If the input is an instance
     * of {@link UnmodifiableIterator}, this method avoids wrapping the iterator again -- the input
     * value is returned untouched.
     *
     * @param iter
     *        iterator to create an unmodifable view
     *
     * @return an unmodifiable view of underlying iterator
     *
     * @throws NullPointerException
     *         if {@code iter} is {@code null}
     */
    public static <TValue> UnmodifiableIterator<TValue> of(Iterator<TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (iter instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator<TValue>) iter;
        }
        return new UnmodifiableIterator<TValue>(iter);
    }

    private final Iterator<TValue> iter;

    private UnmodifiableIterator(Iterator<TValue> iter) {
        this.iter = ObjectArgs.checkNotNull(iter, "iter");
    }

    /**
     * This method is final to disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    protected final Iterator<TValue> delegate() {
        return iter;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException(String.format(
            "Class is unmodifiable: %s", this.getClass().getName()));
    }
}
