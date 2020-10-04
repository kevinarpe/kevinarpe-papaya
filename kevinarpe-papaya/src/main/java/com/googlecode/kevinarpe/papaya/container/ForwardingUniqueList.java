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

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ForwardingListIterator;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;

/**
 * Most (non-abstract) implementations of {@link UniqueList} should use this base class.
 * <p>
 * Implementation note: By design, this is <b>not</b> abstract, as most forwarding contains from Google Guava library.
 * Why?  We need to make a set copy of the list from {@link #delegate()} immediately from the ctor.  It is not always
 * safe to call an abstract method from ctor.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * See: ArrayUniqueListTest
 *
 * @see ForwardingList
 * @see UniqueList
 * @see ArrayUniqueList
 * @see ImmutableUniqueList
 */
@FullyTested
public class ForwardingUniqueList<T>
extends ForwardingList<T>
implements UniqueList<T> {

    @EmptyContainerAllowed
    private final List<T> list;
    @EmptyContainerAllowed
    private final Set<T> set;

    @FunctionalInterface
    public interface SetCopyCtor<T2> {

        @EmptyContainerAllowed
        Set<T2>
        copyOf(@EmptyContainerAllowed List<T2> list);
    }

    private static final SetCopyCtor<Object> HASH_SET_COPY_CTOR = HashSet::new;

    /**
     * This is a convenience ctor to call {@link #ForwardingUniqueList(List, SetCopyCtor)} where {@code setCopyCtor}
     * is {@link HashSet#HashSet(Collection)}.
     *
     * <p>
     * Intentional: This ctor is protected.  It will upgraded to public when there is a very good reason!
     */
    @SuppressWarnings("unchecked")
    protected ForwardingUniqueList(@EmptyContainerAllowed List<T> list) {

        this(list,
            // @SuppressWarnings("unchecked")
            (SetCopyCtor<T>) HASH_SET_COPY_CTOR);
    }

    /**
     * Intentional: This ctor is protected.  It will upgraded to public when there is a very good reason!
     */
    protected ForwardingUniqueList(@EmptyContainerAllowed List<T> list,
                                   SetCopyCtor<T> setCopyCtor) {

        this.list = CollectionArgs.checkElementsUnique(list, "list");
        this.set = setCopyCtor.copyOf(list);
    }

    @Override
    protected List<T>
    delegate() {
        return list;
    }

    protected List<T>
    list() {
        return delegate();
    }

    protected Set<T>
    set() {
        return set;
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

    /** {@inheritDoc} */
    @Override
    public boolean
    contains(@Nullable Object value) {

        final boolean x = set().contains(value);
        return x;
    }

    @Override
    public Iterator<T>
    iterator() {
        // See: ImmutableList
        final ListIterator<T> x = listIterator();
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

    /** {@inheritDoc} */
    @Override
    public boolean
    add(@Nullable T value) {

        if (set().add(value)) {

            list().add(value);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean
    remove(@Nullable Object value) {

        set().remove(value);
        final boolean x = list().remove(value);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    containsAll(@EmptyContainerAllowed Collection<?> c) {

        for (@Nullable final Object value : c) {

            if (false == contains(value)) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    addAll(@EmptyContainerAllowed Collection<? extends T> c) {

        boolean hasChanged = false;

        for (@Nullable final T value : c) {

            hasChanged |= add(value);
        }
        return hasChanged;
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    addAll(final int index,
           @EmptyContainerAllowed Collection<? extends T> c) {

        boolean hasChanged = false;
        int index2 = index;

        for (@Nullable final T value : c) {

            add(index2, value);
            hasChanged = true;
            ++index2;
        }
        return hasChanged;
    }

    @Override
    public boolean
    removeAll(@EmptyContainerAllowed Collection<?> c) {

        boolean hasChanged = false;

        for (@Nullable final Object value : c) {

            hasChanged |= remove(value);
        }
        return hasChanged;
    }

    @Override
    public boolean
    retainAll(@EmptyContainerAllowed Collection<?> c) {

        boolean hasChanged = false;
        final Iterator<T> iter = iterator();
        while (iter.hasNext()) {

            @Nullable
            final Object value = iter.next();

            if (false == c.contains(value)) {

                iter.remove();
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    @Override
    public void
    clear() {

        list().clear();
        set().clear();
    }
//
//    @Override
//    public T get(int index) {
//        abc // Intentional syntax error
//    }

    // See: _ListIterator.set(Object)
    /** {@inheritDoc} */
    @Nullable
    @Override
    public T
    set(final int index, @Nullable T value) {

        if (Objects.equals(list().get(index), value)) {
            return value;
        }
        else if (set().add(value)) {

            @Nullable
            final T oldValue = list().set(index, value);
            if (false == set().remove(oldValue)) {
                throw new IllegalStateException("Unreachable code: Failed to remove old value [" + oldValue + "]");
            }
            return oldValue;
        }
        else {
            if (null == value) {
                throw new IllegalArgumentException("Argument 'value' is not unique: null");
            }
            else {
                throw new IllegalArgumentException(String.format(
                    "Argument 'value' is not unique: (%s)[%s]", value.getClass().getSimpleName(), value));
            }
        }
    }

    // See: _ListIterator.add(Object)
    /** {@inheritDoc} */
    @Override
    public void
    add(final int index, @Nullable T value) {

        if (set().add(value)) {

            list().add(index, value);
        }
        else {
            if (null == value) {
                throw new IllegalArgumentException("Argument 'value' is not unique: null");
            }
            else {
                throw new IllegalArgumentException(String.format(
                    "Argument 'value' is not unique: (%s)[%s]", value.getClass().getSimpleName(), value));
            }
        }
    }

    @Nullable
    @Override
    public T
    remove(final int index) {

        @Nullable
        final T value = list().remove(index);
        set().remove(value);
        return value;
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

    @Override
    public ListIterator<T>
    listIterator() {

        final ListIterator<T> x = listIterator(0);
        return x;
    }

    @Override
    public ListIterator<T>
    listIterator(final int index) {

        final ListIterator<T> iter = list().listIterator(index);
        final _ListIterator x = new _ListIterator(iter);
        return x;
    }

    // Dear Reader: Yes, list iterators are much complex than I knew!  I anticipate there will be hidden bugs uncovered
    // in the years to come...
    private final class _ListIterator
    extends ForwardingListIterator<T> {

        private final ListIterator<T> iter;
        /** See: ArrayList.Itr */
        private int lastIndex;

        private _ListIterator(ListIterator<T> iter) {

            this.iter = ObjectArgs.checkNotNull(iter, "iter");
            this.lastIndex = -1;
        }

        @Override
        protected ListIterator<T>
        delegate() {
            return iter;
        }

        @Nullable
        @Override
        public T
        next() {

            final int nextIndex = iter.nextIndex();
            @Nullable
            final T value = iter.next();
            lastIndex = nextIndex;
            return value;
        }

        @Nullable
        @Override
        public T
        previous() {

            final int previousIndex = iter.previousIndex();
            @Nullable
            final T value = iter.previous();
            lastIndex = previousIndex;
            return value;
        }

        @Override
        public void
        remove() {

            if (-1 == lastIndex) {
                throw new IllegalStateException("Must call next() or previous() before remove()");
            }
            @Nullable
            final T value = ForwardingUniqueList.this.list().get(lastIndex);
            iter.remove();
            if (false == ForwardingUniqueList.this.set().remove(value)) {
                throw new IllegalStateException("Unreachable code: Failed to remove value: [" + value + "]");
            }
            lastIndex = -1;
        }

        // See: ForwardingUniqueList.set(int, Object)
        @Override
        public void
        set(@Nullable T value) {

            if (-1 == lastIndex) {
                throw new IllegalStateException("Must call next() or previous() before set()");
            }
            @Nullable
            final T oldValue = ForwardingUniqueList.this.list().get(lastIndex);
            if (Objects.equals(oldValue, value)) {
                return;
            }
            else if (ForwardingUniqueList.this.set().add(value)) {

                iter.set(value);
                if (false == ForwardingUniqueList.this.set().remove(oldValue)) {
                    throw new IllegalStateException("Unreachable code: Failed to remove old value [" + oldValue + "]");
                }
            }
            else {
                if (null == value) {
                    throw new IllegalArgumentException("Argument 'value' is not unique: null");
                }
                else {
                    throw new IllegalArgumentException(String.format(
                        "Argument 'value' is not unique: (%s)[%s]", value.getClass().getSimpleName(), value));
                }
            }
        }

        // See: ForwardingUniqueList.add(int, Object)
        @Override
        public void
        add(@Nullable T value) {

            if (ForwardingUniqueList.this.set().add(value)) {

                iter.add(value);
            }
            else {
                if (null == value) {
                    throw new IllegalArgumentException("Argument 'value' is not unique: null");
                }
                else {
                    throw new IllegalArgumentException(String.format(
                        "Argument 'value' is not unique: (%s)[%s]", value.getClass().getSimpleName(), value));
                }
            }
            lastIndex = -1;
        }
    }

    /** {@inheritDoc} */
    @Override
    public UniqueList<T>
    subList(final int fromIndex,
            final int toIndex) {

        final List<T> subList = list().subList(fromIndex, toIndex);
        return new ForwardingUniqueList<>(subList);
    }
//
//    // default
//    @Override
//    public void replaceAll(UnaryOperator<T> operator) {
//        abc // Intentional syntax error
//    }

    // default
    @Override
    public void
    sort(Comparator<? super T> c) {

        list().sort(c);
    }
//
//    // default
//    @Override
//    public boolean removeIf(Predicate<? super T> filter) {
//        abc // Intentional syntax error
//    }
}
