package com.googlecode.kevinarpe.papaya.container.policy;

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

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PolicyIterator
 * @see PolicyListIterator
 * @see PolicyCollection
 * @see PolicySet
 * @see PolicyMap
 * @see PolicyMapEntry
 */
@FullyTested
public class PolicyList<T>
extends ForwardingList<T> {

    private final List<T> list;
    private final ImmutableSet<DoNotAllow> doNotAllowSet;

    public PolicyList(@EmptyContainerAllowed
                          List<T> list,
                      @EmptyContainerAllowed
                          Set<DoNotAllow> doNotAllowSet) {

        this.list = ObjectArgs.checkNotNull(list, "list");
        CollectionArgs.checkElementsNotNull(doNotAllowSet, "doNotAllowSet");
        this.doNotAllowSet = Sets.immutableEnumSet(doNotAllowSet);
    }

    @Override
    protected List<T>
    delegate() {
        return list;
    }

    public ImmutableSet<DoNotAllow>
    doNotAllowSet() {
        return doNotAllowSet;
    }
//
//    @Override
//    public int size() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public boolean isEmpty() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicyIterator} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicyIterator<T>
    iterator() {

        final Iterator<T> iter = super.iterator();
        final PolicyIterator<T> x = new PolicyIterator<>(iter, doNotAllowSet());
        return x;
    }
//
//    @Override
//    public Object[] toArray() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public <T1> T1[] toArray(T1[] a) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code value} is {@code null}
     * when {@link DoNotAllow#NullValue}
     */
    @Override
    public boolean
    add(@Nullable T value) {

        if (null == value) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullValue);
        }
        final boolean x = super.add(value);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Override
    public boolean
    remove(@Nullable Object value) {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        final boolean x = super.remove(value);
        return x;
    }
//
//    @Override
//    public boolean containsAll(Collection<?> c) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code c} has {@code null} values when
     * {@link DoNotAllow#NullValue}
     */
    @Override
    public boolean
    addAll(@EmptyContainerAllowed Collection<? extends T> c) {

        PolicyCollection._checkCollectionToAdd(doNotAllowSet(), c);
        final boolean x = super.addAll(c);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code c} has {@code null} values when
     * {@link DoNotAllow#NullValue}
     */
    @Override
    public boolean
    addAll(final int index,
           @EmptyContainerAllowed Collection<? extends T> c) {

        PolicyCollection._checkCollectionToAdd(doNotAllowSet(), c);
        final boolean x = super.addAll(index, c);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Override
    public boolean
    removeAll(@EmptyContainerAllowed Collection<?> c) {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        final boolean x = super.removeAll(c);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Override
    public boolean
    retainAll(@EmptyContainerAllowed Collection<?> c) {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        final boolean x = super.retainAll(c);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Override
    public void
    clear() {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        super.clear();
    }
//
//    @Override
//    public T get(int index) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code value} is {@code null}
     * when {@link DoNotAllow#NullValue}
     */
    @Nullable
    @Override
    public T
    set(final int index,
        @Nullable T value) {

        if (null == value) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullValue);
        }
        @Nullable final T x = super.set(index, value);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} if {@code value} is {@code null}
     * when {@link DoNotAllow#NullValue}
     */
    @Override
    public void
    add(final int index,
        @Nullable T value) {

        if (null == value) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullValue);
        }
        super.add(index, value);
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    @Nullable
    @Override
    public T
    remove(final int index) {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        @Nullable final T x = super.remove(index);
        return x;
    }
//
//    @Override
//    public int indexOf(Object o) {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public int lastIndexOf(Object o) {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicyListIterator} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicyListIterator<T>
    listIterator() {

        final PolicyListIterator<T> x = listIterator(0);
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicyListIterator} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicyListIterator<T>
    listIterator(final int index) {

        final ListIterator<T> iter = super.listIterator(index);
        final PolicyListIterator<T> x = new PolicyListIterator<>(iter, doNotAllowSet());
        return x;
    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Returns {@link PolicyList} with matching {@link #doNotAllowSet()}
     */
    @Override
    public PolicyList<T>
    subList(final int fromIndex,
            final int toIndex) {

        final List<T> subList = super.subList(fromIndex, toIndex);
        return new PolicyList<>(subList, doNotAllowSet());
    }
//
//    // The OpenJDK impl calls listIterator(), ListIterator.hasNext(), ListIterator.next(), and ListIterator.set().
//    // default
//    @Override
//    public void replaceAll(UnaryOperator<T> operator) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls toArray(), listIterator(), ListIterator.next(), and ListIterator.set().
//    // default
//    @Override
//    public void sort(Comparator<? super T> c) {
//        abc // Intentional syntax error
//    }

    // default
    @Override
    public void
    sort(Comparator<? super T> c) {

        delegate().sort(c);
    }
//
//    // The OpenJDK impl calls iterator(), Iterator.hasNext(), Iterator.next(), and Iterator.remove().
//    // default
//    @Override
//    public boolean removeIf(Predicate<? super T> filter) {
//        abc // Intentional syntax error
//    }
//
//    // The OpenJDK impl calls iterator(), Iterator.hasNext(), and Iterator.next().
//    // default
//    @Override
//    public void forEach(Consumer<? super T> action) {
//        abc // Intentional syntax error
//    }
}
