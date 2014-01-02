package com.googlecode.kevinarpe.papaya.container;

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

import com.google.common.collect.ForwardingListIterator;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ListIterator;

/**
 * This class is a simple extension of {@link ForwardingListIterator}, where methods
 * {@link #add(Object)} and {@link #remove()} are disabled -- always throw
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
 * @see UnmodifiableListIterator
 * @see #of(ListIterator)
 * @see FixedSizeForwardingList
 * @see FixedSizeForwardingList#listIterator()
 * @see FixedSizeForwardingList#listIterator(int)
 */
@FullyTested
public final class FixedSizeListIterator<TValue>
extends ForwardingListIterator<TValue> {

    /**
     * Returns an unmodifiable view of an underlying {@link ListIterator}.  If the input is an
     * instance of {@link FixedSizeListIterator}, this method avoids wrapping the iterator again
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
    public static <TValue> FixedSizeListIterator<TValue> of(ListIterator<TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (iter instanceof FixedSizeListIterator) {
            return (FixedSizeListIterator<TValue>) iter;
        }
        return new FixedSizeListIterator<TValue>(iter);
    }

    private final ListIterator<TValue> iter;

    private FixedSizeListIterator(ListIterator<TValue> iter) {
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
            "Class is fixed size: %s", this.getClass().getName()));
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
    public final void remove() {
        throwUnsupportedOperationException();
    }
}
