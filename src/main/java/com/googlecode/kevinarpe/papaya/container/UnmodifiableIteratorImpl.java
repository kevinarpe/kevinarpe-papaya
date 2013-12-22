package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingIterator;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Iterator;

// Ref: http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection
public class UnmodifiableIteratorImpl<TValue>
extends ForwardingIterator<TValue> {

    public static <TValue> UnmodifiableIteratorImpl<TValue> of(Iterator<TValue> iter) {
        ObjectArgs.checkNotNull(iter, "iter");

        if (iter instanceof UnmodifiableIteratorImpl) {
            return (UnmodifiableIteratorImpl<TValue>) iter;
        }
        return new UnmodifiableIteratorImpl<TValue>(iter);
    }

    private final Iterator<TValue> _iter;

    private UnmodifiableIteratorImpl(Iterator<TValue> iter) {
        _iter = ObjectArgs.checkNotNull(iter, "iter");
    }

    @Override
    protected Iterator<TValue> delegate() {
        return _iter;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(String.format(
            "Class %s is unmodifiable", this.getClass().getName()));
    }
}
