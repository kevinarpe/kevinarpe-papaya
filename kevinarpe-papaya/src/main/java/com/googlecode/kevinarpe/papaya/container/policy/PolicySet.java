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

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PolicyIterator
 * @see PolicyListIterator
 * @see PolicyCollection
 * @see PolicyList
 * @see PolicyMap
 * @see PolicyMapEntry
 */
@FullyTested
public class PolicySet<T>
extends ForwardingSet<T> {

    private final Set<T> set;
    private final ImmutableSet<DoNotAllow> doNotAllowSet;

    public PolicySet(@EmptyContainerAllowed
                         Set<T> set,
                     @EmptyContainerAllowed
                         Set<DoNotAllow> doNotAllowSet) {

        this.set = ObjectArgs.checkNotNull(set, "set");
        CollectionArgs.checkElementsNotNull(doNotAllowSet, "doNotAllowSet");
        this.doNotAllowSet = Sets.immutableEnumSet(doNotAllowSet);
    }

    @Override
    protected Set<T>
    delegate() {
        return set;
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
    public void
    clear() {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        super.clear();
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
