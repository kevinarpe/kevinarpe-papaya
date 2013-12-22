package com.googlecode.kevinarpe.papaya.container;

import com.google.common.collect.ForwardingList;

import java.util.*;

public abstract class FixedSizeForwardingList<TValue>
extends ForwardingList<TValue> {

    private void _throwUnsupportedOperationException() {
        throw new UnsupportedOperationException(String.format(
            "Class %s is unmodifiable", this.getClass().getName()));
    }

    @Override
    public final void add(int index, TValue element) {
        _throwUnsupportedOperationException();
    }

    @Override
    public final boolean addAll(int index, Collection<? extends TValue> elements) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final ListIterator<TValue> listIterator() {
        return UnmodifiableListIteratorImpl.of(super.listIterator());
    }

    @Override
    public final ListIterator<TValue> listIterator(int index) {
        return UnmodifiableListIteratorImpl.of(super.listIterator(index));
    }

    @Override
    public final TValue remove(int index) {
        _throwUnsupportedOperationException();
        return null;
    }

    @Override
    public final List<TValue> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(super.subList(fromIndex, toIndex));
    }

    @Override
    public final Iterator<TValue> iterator() {
        return UnmodifiableIteratorImpl.of(super.iterator());
    }

    @Override
    public final boolean removeAll(Collection<?> collection) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final boolean add(TValue element) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final boolean remove(Object object) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final boolean addAll(Collection<? extends TValue> collection) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final boolean retainAll(Collection<?> collection) {
        _throwUnsupportedOperationException();
        return false;
    }

    @Override
    public final void clear() {
        _throwUnsupportedOperationException();
    }
}
