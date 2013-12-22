package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingListIterator;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.ListIterator;

// Ref: http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection
public class UnmodifiableListIteratorImpl<TValue>
extends ForwardingListIterator<TValue> {

    public static <TValue> UnmodifiableListIteratorImpl<TValue> of(ListIterator<TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (iter instanceof UnmodifiableListIteratorImpl) {
            return (UnmodifiableListIteratorImpl<TValue>) iter;
        }
        return new UnmodifiableListIteratorImpl<TValue>(iter);
    }

    private final ListIterator<TValue> _iter;

    private UnmodifiableListIteratorImpl(ListIterator<TValue> iter) {
        _iter = ObjectArgs.checkNotNull(iter, "iter");
    }

    @Override
    protected ListIterator<TValue> delegate() {
        return _iter;
    }

    private void _throwUnsupportedOperationException() {
        throw new UnsupportedOperationException(String.format(
            "Class %s is unmodifiable", this.getClass().getName()));
    }

    @Override
    public void add(TValue element) {
        _throwUnsupportedOperationException();
    }

    @Override
    public void set(TValue element) {
        _throwUnsupportedOperationException();
    }

    @Override
    public void remove() {
        _throwUnsupportedOperationException();
    }
}
