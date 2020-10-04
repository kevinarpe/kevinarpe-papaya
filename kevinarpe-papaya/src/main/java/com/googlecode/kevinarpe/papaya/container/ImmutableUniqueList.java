package com.googlecode.kevinarpe.papaya.container;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ImmutableUniqueList<T>
extends ForwardingUniqueList<T> {

    /**
     * see RegularImmutableList#EMPTY
     * see ImmutableList#of()
     */
    private static final ImmutableUniqueList<Object> EMPTY = new ImmutableUniqueList<>(ImmutableList.of());

    public static <T2>
    ImmutableUniqueList<T2>
    empty() {

        @SuppressWarnings("unchecked")
        final ImmutableUniqueList<T2> x = (ImmutableUniqueList<T2>) EMPTY;
        return x;
    }

    public static <T2>
    ImmutableUniqueList<T2>
    of() {
        return empty();
    }

    public static <T2>
    ImmutableUniqueList<T2>
    of(@EmptyContainerAllowed ImmutableList<T2> list) {

        if (list.isEmpty()) {
            return empty();
        }
        final ImmutableUniqueList<T2> x = new ImmutableUniqueList<>(list);
        return x;
    }

    public static <T2>
    ImmutableUniqueList<T2>
    copyOf(@EmptyContainerAllowed Collection<? extends T2> c) {

        if (c.isEmpty()) {
            return empty();
        }
        final ImmutableUniqueList<T2> x = new ImmutableUniqueList<>(ImmutableList.copyOf(c));
        return x;
    }

    private ImmutableUniqueList(@EmptyContainerAllowed ImmutableList<T> list) {

        super(list, ImmutableSet::copyOf);
    }

    private ImmutableList<T>
    _immutableList() {

        final ImmutableList<T> x = (ImmutableList<T>) delegate();
        return x;
    }
//
//    @Override
//    protected List<T>
//    delegate() {
//        return list;
//    }
//
//        @Override
//        public int size() {
//            abc // Intentional syntax error
//        }
//
//        @Override
//        public boolean isEmpty() {
//            abc // Intentional syntax error
//        }
//
//    @Override
//    public boolean
//    contains(@Nullable Object value) {
//        return set.contains(value);
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link UnmodifiableIterator}
     */
    @Override
    public UnmodifiableIterator<T>
    iterator() {

        final UnmodifiableListIterator<T> x = listIterator();
        return x;
    }
//
//        @Override
//        public Object[] toArray() {
//            abc // Intentional syntax error
//        }
//
//        @Override
//        public <T1> T1[] toArray(T1[] a) {
//            abc // Intentional syntax error
//        }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    add(@Nullable T value) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    remove(@Nullable Object value) {

        throw new UnsupportedOperationException();
    }
//
//        @Override
//        public boolean containsAll(Collection<?> c) {
//            abc // Intentional syntax error
//        }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    addAll(@EmptyContainerAllowed Collection<? extends T> c) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    addAll(final int index,
           @EmptyContainerAllowed Collection<? extends T> c) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    removeAll(@EmptyContainerAllowed Collection<?> c) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public boolean
    retainAll(@EmptyContainerAllowed Collection<?> c) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public void
    clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public T
    set(final int index, @Nullable T element) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public void
    add(final int index, @Nullable T element) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    @Deprecated
    @Override
    public T
    remove(final int index) {

        throw new UnsupportedOperationException();
    }
//
//        @Override
//        public int indexOf(Object o) {
//            abc // Intentional syntax error
//        }
//
//        @Override
//        public int lastIndexOf(Object o) {
//            abc // Intentional syntax error
//        }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link UnmodifiableListIterator}
     */
    @Override
    public UnmodifiableListIterator<T>
    listIterator() {

        final ImmutableList<T> list = _immutableList();
        final UnmodifiableListIterator<T> x = list.listIterator();
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link UnmodifiableListIterator}
     */
    @Override
    public UnmodifiableListIterator<T>
    listIterator(final int index) {

        final ImmutableList<T> list = _immutableList();
        final UnmodifiableListIterator<T> x = list.listIterator(index);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link ImmutableUniqueList}
     */
    @Override
    public ImmutableUniqueList<T>
    subList(final int fromIndex,
            final int toIndex) {

        final ImmutableList<T> list = _immutableList();
        final ImmutableList<T> subList = list.subList(fromIndex, toIndex);
        return new ImmutableUniqueList<>(subList);
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    // default
    @Deprecated
    @Override
    public void
    replaceAll(UnaryOperator<T> operator) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    // default
    @Deprecated
    @Override
    public void
    sort(Comparator<? super T> c) {

        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave collection unmodified.
     *
     * @throws UnsupportedOperationException always
     */
    // default
    @Deprecated
    @Override
    public boolean
    removeIf(Predicate<? super T> filter) {

        throw new UnsupportedOperationException();
    }
}
