package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingList;

import java.util.List;
import java.util.ListIterator;

/**
 * This is a specialized form of {@link ForwardingList} that does not allow the underlying list size
 * to change.  However, elements may be modified via {@link #set(int, Object)}.
 * <p>
 * This class is closely related to {@link UnmodifiableForwardingList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see UnmodifiableForwardingList
 * @see BaseFixedSizeForwardingList
 * @see ForwardingList
 * @see FixedSizeListIterator
 */
public abstract class FixedSizeForwardingList<TValue>
extends BaseFixedSizeForwardingList<TValue> {

    protected FixedSizeForwardingList() {
        super("fixed size");
    }

    /**
     * Return value is an instance of {@link FixedSizeListIterator}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator() {
        return FixedSizeListIterator.of(super.listIterator());
    }

    /**
     * Return value is an instance of {@link FixedSizeListIterator}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator(int index) {
        return FixedSizeListIterator.of(super.listIterator(index));
    }

    /**
     * Return value is an instance of {@link FixedSizeForwardingList}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final List<TValue> subList(int fromIndex, int toIndex) {
        final List<TValue> sublist = super.subList(fromIndex, toIndex);
        return new FixedSizeForwardingList<TValue>() {
            @Override
            protected List<TValue> delegate() {
                return sublist;
            }
        };
    }
}
