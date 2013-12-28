package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingList;

import java.util.List;
import java.util.ListIterator;

/**
 * This is a specialized form of {@link ForwardingList} that does not allow the underlying list
 * to change.
 * <p>
 * This class is closely related to {@link FixedSizeForwardingList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see FixedSizeForwardingList
 * @see BaseFixedSizeForwardingList
 * @see ForwardingList
 * @see UnmodifiableIterator
 * @see UnmodifiableListIterator
 */
public abstract class UnmodifiableForwardingList<TValue>
extends BaseFixedSizeForwardingList<TValue> {

    protected UnmodifiableForwardingList() {
        super("unmodifiable");
    }

    /**
     * Return value is an instance of {@link UnmodifiableListIterator}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator() {
        return UnmodifiableListIterator.of(super.listIterator());
    }

    /**
     * Return value is an instance of {@link UnmodifiableListIterator}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator(int index) {
        return UnmodifiableListIterator.of(super.listIterator(index));
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     */
    @Override
    public final TValue set(int index, TValue element) {
        throwUnsupportedOperationException();
        return null;
    }

    /**
     * Return value is an instance of {@link UnmodifiableForwardingList}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final List<TValue> subList(int fromIndex, int toIndex) {
        final List<TValue> sublist = super.subList(fromIndex, toIndex);
        return new UnmodifiableForwardingList<TValue>() {
            @Override
            protected List<TValue> delegate() {
                return sublist;
            }
        };
    }
}
