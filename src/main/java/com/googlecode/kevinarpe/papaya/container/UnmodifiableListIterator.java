package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingListIterator;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ListIterator;

/**
 * This class is a simple extension of {@link ForwardingListIterator}, where methods
 * {@link #add(Object)}, {@link #remove()}, and {@link #set(Object)} are disabled -- always throw
 * {@link UnsupportedOperationException}.
 * <p>
 * The class constructor is private.  Instead, use static method {@link #of(ListIterator)}.
 * <p>
 * This class is {@code final} to disable modify-by-inheritance.
 * <p>
 * Why is this class not called {@code ImmutableListIterator}?  Read more here:
 * <a href="http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection"
 * >http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection</a>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ForwardingListIterator
 * @see UnmodifiableIterator
 * @see #of(ListIterator)
 * @see UnmodifiableForwardingList#listIterator()
 * @see UnmodifiableForwardingList#listIterator(int)
 */
@FullyTested
public final class UnmodifiableListIterator<TValue>
extends ForwardingListIterator<TValue> {

    /**
     * Returns an unmodifiable view of an underlying {@link ListIterator}.  If the input is an
     * instance of {@link UnmodifiableListIterator}, this method avoids wrapping the iterator again
     * -- the input value is returned untouched.
     *
     * @param iter
     *        list iterator to create an unmodifable view
     *
     * @return an unmodifiable view of underlying list iterator
     *
     * @throws NullPointerException
     *         if {@code iter} is {@code null}
     */
    public static <TValue> UnmodifiableListIterator<TValue> of(ListIterator<TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (iter instanceof UnmodifiableListIterator) {
            return (UnmodifiableListIterator<TValue>) iter;
        }
        return new UnmodifiableListIterator<TValue>(iter);
    }

    private final ListIterator<TValue> iter;

    private UnmodifiableListIterator(ListIterator<TValue> iter) {
        this.iter = ObjectArgs.checkNotNull(iter, "iter");
    }

    /**
     * This method is final to disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    protected final ListIterator<TValue> delegate() {
        return iter;
    }

    private final void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException(String.format(
            "Class is unmodifiable: %s", this.getClass().getName()));
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final void add(TValue element) {
        throwUnsupportedOperationException();
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final void set(TValue element) {
        throwUnsupportedOperationException();
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final void remove() {
        throwUnsupportedOperationException();
    }
}
