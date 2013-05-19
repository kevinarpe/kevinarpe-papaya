package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;

import com.googlecode.kevinarpe.papaya.args.CollectionArgs;
import com.googlecode.kevinarpe.papaya.args.IntArgs;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

public class UniqueArrayList<E>
extends ArrayList<E> {

    private static final long serialVersionUID = 6245816156068280199L;
    
    private Map<E, Integer> _valueToIndexMap;
    
    public UniqueArrayList() {
        this(10);
    }

    public UniqueArrayList(int initialCapacity) {
        super(IntArgs.checkNotNegative(initialCapacity, "initialCapacity"));
        _valueToIndexMap = new HashMap<E, Integer>(initialCapacity);
    }
    
    public UniqueArrayList(Collection<? extends E> c) {
        this(_safeGetSize(c));
        for (E e: c) {
            add(e);
        }
    }
    
    protected static <E2> int _safeGetSize(Collection<E2> c) {
        ObjectArgs.checkNotNull(c, "c");
        int x = c.size();
        return x;
    }

    @Override
    public boolean contains(Object o) {
        boolean x = _valueToIndexMap.containsKey(o);
        return x;
    }

    @Override
    public int indexOf(Object o) {
        Integer index = _valueToIndexMap.get(o);
        if (null == index) {
            return -1;
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int x = indexOf(o);
        return x;
    }

    @Override
    public Object clone() {
        UniqueArrayList<E> x = new UniqueArrayList<E>(this);
        return x;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        ObjectArgs.checkNotNull(a, "a");
        T[] x = super.toArray(a);
        return x;
    }

    @Override
    public E get(int index) {
        CollectionArgs.checkAccessIndex(this, index, "this", "index");
        E e = super.get(index);
        return e;
    }

    @Override
    public E set(int index, E element) {
        CollectionArgs.checkAccessIndex(this, index, "this", "index");
        E last = super.set(index, element);
        _valueToIndexMap.remove(last);
        _valueToIndexMap.put(element, index);
        return last;
    }

    @Override
    public boolean add(E e) {
        _checkNotDuplicateValue(e);
        boolean b = super.add(e);
        int index = size() - 1;
        _valueToIndexMap.put(e, index);
        return b;
    }
    
    protected void _checkNotDuplicateValue(E e) {
        if (_valueToIndexMap.containsKey(e)) {
            String msg = String.format("Duplicate item: '%s'", e);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void add(int index, E element) {
        CollectionArgs.checkInsertIndex(this, index, "this", "index");
        int size = size();
        if (index == size) {
            add(element);
        }
        else {
            _checkNotDuplicateValue(element);
            super.add(index, element);
            _rebuildValueToIndexMap();
        }
    }
    
    // TODO: This can be smarter.
    // Consider actions: insert, update, delete.
    // Each has a specific algorithm to reduce overhead.
    protected void _rebuildValueToIndexMap() {
        _valueToIndexMap.clear();
        int size = size();
        for (int i = 0; i < size; ++i) {
            E e = get(i);
            _valueToIndexMap.put(e, i);
        }
    }

    @Override
    public E remove(int index) {
        CollectionArgs.checkAccessIndex(this, index, "this", "index");
        int oldSize = size();
        E e = super.remove(index);
        if (index == oldSize) {
            _valueToIndexMap.remove(e);
        }
        else {
            _rebuildValueToIndexMap();
        }
        return e;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (-1 == index) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        _valueToIndexMap.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        ObjectArgs.checkNotNull(c, "c");
        if (c.isEmpty()) {
            return false;
        }
        for (E e: c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        CollectionArgs.checkInsertIndex(this, index, "this", "index");
        int size = size();
        if (index == size) {
            boolean b = addAll(c);
            return b;
        }
        ObjectArgs.checkNotNull(c, "c");
        if (c.isEmpty()) {
            return false;
        }
        int insertIndex = index;
        for (E e: c) {
            _checkNotDuplicateValue(e);
            super.add(insertIndex, e);
            ++insertIndex;
        }
        _rebuildValueToIndexMap();
        return true;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            this, fromIndex, toIndex, "this", "fromIndex", "toIndex");
        if (fromIndex == toIndex) {
            return;
        }
        int oldSize = size();
        boolean rebuild = (toIndex < oldSize);
        for (int index = toIndex; index >= fromIndex; --index) {
            E e = super.remove(index);
            if (!rebuild) {
                _valueToIndexMap.remove(e);
            }
        }
        if (rebuild) {
            _rebuildValueToIndexMap();
        }
    }

    @Override
    public Iterator<E> iterator() {
        Iter x = new Iter();
        return x;
    }

    @Override
    public ListIterator<E> listIterator() {
        ListIter x = new ListIter();
        return x;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        ListIter x = new ListIter(index);
        return x;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            this, fromIndex, toIndex, "this", "fromIndex", "toIndex");
        int size = size();
        if (0 == fromIndex && toIndex == size) {
            return this;
        }
        SubList subList = new SubList(fromIndex, toIndex);
        return subList;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        ObjectArgs.checkNotNull(c, "c");
        boolean b = super.containsAll(c);
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ObjectArgs.checkNotNull(c, "c");
        if (c.isEmpty()) {
            return false;
        }
        boolean result = false;
        for (Object e: c) {
            int index = indexOf(e);
            if (-1 != index) {
                super.remove(index);
                result = true;
            }
        }
        if (result) {
            _rebuildValueToIndexMap();
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ObjectArgs.checkNotNull(c, "c");
        if (c.isEmpty()) {
            boolean b = !isEmpty();
            clear();
            return b;
        }
        boolean result = false;
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            E e = iter.next();
            if (!c.contains(e)) {
                // TODO: Terrible performance!
                // Need a flag to disable calls to _rebuildValueToIndexMap()!
                iter.remove();
                result = true;
            }
        }
        if (result) {
            _rebuildValueToIndexMap();
        }
        return result;
    }

    public class Iter
    implements Iterator<E> {
        
        private final ListIterator<E> _listIter;
        private E _lastElement;
        
        public Iter() {
            this(0);
        }
        
        protected Iter(int index) {
            CollectionArgs.checkInsertIndex(UniqueArrayList.this, index, "this", "index");
            _listIter = UniqueArrayList.super.listIterator(index);
            _lastElement = null;
        }
        
        protected ListIterator<E> getListIter() {
            return _listIter;
        }
        
        protected E getLastElement() {
            return _lastElement;
        }
        
        protected void setLastElement(E e) {
            _lastElement = e;
        }
        
        @Override
        public boolean hasNext() {
            boolean b = _listIter.hasNext();
            return b;
        }

        @Override
        public E next() {
            _lastElement = _listIter.next();
            return _lastElement;
        }

        @Override
        public void remove() {
            _listIter.remove();
            int index = UniqueArrayList.this._valueToIndexMap.remove(_lastElement);
            int size = UniqueArrayList.this.size();
            // if (index == size) then the last element was removed.
            if (index < size) {
                UniqueArrayList.this._rebuildValueToIndexMap();
            }
        }
    }
    
    public class ListIter
    extends Iter
    implements ListIterator<E> {
        
        public ListIter() {
            this(0);
        }
        
        public ListIter(int index) {
            super(index);
        }

        @Override
        public boolean hasPrevious() {
            ListIterator<E> listIter = getListIter();
            boolean b = listIter.hasPrevious();
            return b;
        }

        @Override
        public E previous() {
            ListIterator<E> listIter = getListIter();
            E e = listIter.previous();
            setLastElement(e);
            return e;
        }

        @Override
        public int nextIndex() {
            ListIterator<E> listIter = getListIter();
            int x = listIter.nextIndex();
            return x;
        }

        @Override
        public int previousIndex() {
            ListIterator<E> listIter = getListIter();
            int x = listIter.previousIndex();
            return x;
        }

        @Override
        public void set(E e) {
            ListIterator<E> listIter = getListIter();
            listIter.set(e);
            E lastElement = getLastElement();
            Integer index = UniqueArrayList.this._valueToIndexMap.remove(lastElement);
            UniqueArrayList.this._valueToIndexMap.put(e, index);
        }

        @Override
        public void add(E e) {
            ListIterator<E> listIter = getListIter();
            int index = listIter.nextIndex();
            listIter.add(e);
            int size = UniqueArrayList.this.size();
            if (index == size - 1) {
                UniqueArrayList.this._valueToIndexMap.put(e, index);
            }
            else {
                UniqueArrayList.this._rebuildValueToIndexMap();
            }
        }
    }
    
    protected class SubList
    implements List<E>, RandomAccess {
        
        private final List<E> _subList;
        private final int _indexOffset;
        
        public SubList(int fromIndex, int toIndex) {
            _subList = UniqueArrayList.super.subList(fromIndex, toIndex);
            _indexOffset = fromIndex;
        }

        @Override
        public int size() {
            int x = _subList.size();
            return x;
        }

        @Override
        public boolean isEmpty() {
            boolean b = _subList.isEmpty();
            return b;
        }

        @Override
        public boolean contains(Object o) {
            int index = indexOf(o);
            boolean b = (-1 != index);
            return b;
        }

        @Override
        public int indexOf(Object o) {
            Integer index = UniqueArrayList.this._valueToIndexMap.get(o);
            if (null == index || !_isValidIndex(index)) {
                return -1;
            }
            return index;
        }

        protected boolean _isValidIndex(int index) {
            if (index < _indexOffset) {
                return false;
            }
            int subListSize = _subList.size();
            if (index >= _indexOffset + subListSize) {
                return false;
            }
            return true;
        }

        @Override
        public int lastIndexOf(Object o) {
            int x = indexOf(o);
            return x;
        }

        @Override
        public Object[] toArray() {
            Object[] x = _subList.toArray();
            return x;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            ObjectArgs.checkNotNull(a, "a");
            T[] x = _subList.toArray(a);
            return x;
        }

        @Override
        public E get(int index) {
            CollectionArgs.checkAccessIndex(this, index, "this", "index");
            E e = _subList.get(index);
            return e;
        }

        @Override
        public E set(int index, E element) {
            CollectionArgs.checkAccessIndex(this, index, "this", "index");
            E last = _subList.set(index, element);
            UniqueArrayList.this._valueToIndexMap.remove(last);
            UniqueArrayList.this._valueToIndexMap.put(element, index);
            return last;
        }

        @Override
        public boolean add(E e) {
            int subListSize = _subList.size();
            add(subListSize, e);
            return true;
        }

        @Override
        public void add(int index, E element) {
            CollectionArgs.checkInsertIndex(this, index, "this", "index");
            _checkNotDuplicateValue(element);
            _subList.add(element);
            UniqueArrayList.this._rebuildValueToIndexMap();
        }

        @Override
        public E remove(int index) {
            CollectionArgs.checkAccessIndex(this, index, "this", "index");
            E e = _subList.remove(index);
            UniqueArrayList.this._rebuildValueToIndexMap();
            return e;
        }
        
        @Override
        public boolean remove(Object o) {
            int index = indexOf(o);
            if (-1 == index) {
                return false;
            }
            _subList.remove(index);
            UniqueArrayList.this._rebuildValueToIndexMap();
            return true;
        }

        @Override
        public void clear() {
            if (_subList.isEmpty()) {
                return;
            }
            _subList.clear();
            UniqueArrayList.this._rebuildValueToIndexMap();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            int subListSize = _subList.size();
            boolean b = addAll(subListSize, c);
            return b;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            CollectionArgs.checkInsertIndex(this, index, "this", "index");
            ObjectArgs.checkNotNull(c, "c");
            if (c.isEmpty()) {
                return false;
            }
            for (E e: c) {
                _checkNotDuplicateValue(e);
                _subList.add(e);
            }
            UniqueArrayList.this._rebuildValueToIndexMap();
            return true;
        }
        
        @Override
        public Iterator<E> iterator() {
            ListIterator<E> x = listIterator(0);
            return x;
        }

        @Override
        public ListIterator<E> listIterator() {
            ListIterator<E> x = listIterator(0);
            return x;
        }

        @Override
        public ListIterator<E> listIterator(final int index) {
            CollectionArgs.checkInsertIndex(this, index, "this", "index");
            
            ListIterator<E> x = new ListIterator<E>() {

                private final ListIterator<E> _listIter =
                    SubList.this._subList.listIterator(index + SubList.this._indexOffset);
                private E _lastElement;
                
                @Override
                public boolean hasNext() {
                    boolean b = _listIter.hasNext();
                    return b;
                }

                @Override
                public E next() {
                    _lastElement = _listIter.next();
                    return _lastElement;
                }

                @Override
                public boolean hasPrevious() {
                    boolean b = _listIter.hasPrevious();
                    return b;
                }

                @Override
                public E previous() {
                    _lastElement = _listIter.previous();
                    return _lastElement;
                }

                @Override
                public int nextIndex() {
                    int x = _listIter.nextIndex();
                    return x;
                }

                @Override
                public int previousIndex() {
                    int x = _listIter.previousIndex();
                    return x;
                }

                @Override
                public void remove() {
                    _listIter.remove();
                    UniqueArrayList.this._rebuildValueToIndexMap();
                }

                @Override
                public void set(E e) {
                    _listIter.set(e);
                    Integer index = UniqueArrayList.this._valueToIndexMap.remove(_lastElement);
                    UniqueArrayList.this._valueToIndexMap.put(e, index);
                }

                @Override
                public void add(E e) {
                    _listIter.add(e);
                    UniqueArrayList.this._rebuildValueToIndexMap();
                }
            };
            return x;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            CollectionArgs.checkFromAndToIndices(
                this, fromIndex, toIndex, "this", "fromIndex", "toIndex");
            int subListSize = _subList.size();
            if (0 == fromIndex && toIndex == subListSize) {
                return this;
            }
            List<E> x =
                UniqueArrayList.this.subList(fromIndex + _indexOffset, toIndex + _indexOffset);
            return x;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            boolean b = _subList.equals(obj);
            return b;
        }

        @Override
        public int hashCode() {
            int x = _subList.hashCode();
            return x;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            ObjectArgs.checkNotNull(c, "c");
            boolean b = _subList.containsAll(c);
            return b;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            ObjectArgs.checkNotNull(c, "c");
            if (c.isEmpty()) {
                return false;
            }
            boolean result = false;
            for (Object e: c) {
                int index = indexOf(e);
                if (-1 != index) {
                    _subList.remove(index);
                    result = true;
                }
            }
            if (result) {
                _rebuildValueToIndexMap();
            }
            return result;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            ObjectArgs.checkNotNull(c, "c");
            if (c.isEmpty()) {
                boolean b = !isEmpty();
                clear();
                return b;
            }
            boolean result = false;
            Iterator<E> iter = iterator();
            while (iter.hasNext()) {
                E e = iter.next();
                if (!c.contains(e)) {
                    // TODO: Terrible performance!
                    // Need a flag to disable calls to _rebuildValueToIndexMap()!
                    iter.remove();
                    result = true;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            String x = _subList.toString();
            return x;
        }
    }
}
