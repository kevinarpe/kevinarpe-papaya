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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a {@link UniqueList} backed by an {@link ArrayList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ArrayUniqueList<T>
extends ForwardingUniqueList<T> {

    /**
     * @see ArrayList#ArrayList(int)
     */
    public static <T2>
    ArrayUniqueList<T2>
    withInitialCapacity(final int initialCapacity) {

        final ArrayList<T2> list = new ArrayList<>(initialCapacity);
        final ArrayUniqueList<T2> x = new ArrayUniqueList<>(list);
        return x;
    }

    /**
     * @see ArrayList#ArrayList(Collection)
     */
    public static <T2>
    ArrayUniqueList<T2>
    copyOf(@EmptyContainerAllowed Collection<? extends T2> c) {

        final ArrayUniqueList<T2> x = new ArrayUniqueList<>(c);
        return x;
    }

    /**
     * @see ArrayList#ArrayList()
     */
    public ArrayUniqueList() {
        super(new ArrayList<>());
    }

    private ArrayUniqueList(@EmptyContainerAllowed Collection<? extends T> c) {
        super(new ArrayList<>(c));
    }

    protected ArrayList<T>
    arrayList() {

        final List<T> list = delegate();
        final ArrayList<T> x = (ArrayList<T>) list;
        return x;
    }

    /**
     * @see ArrayList#trimToSize()
     */
    public void
    trimToSize() {

        final ArrayList<T> list = arrayList();
        list.trimToSize();
    }

    /**
     * @see ArrayList#ensureCapacity(int)
     */
    public void
    ensureCapacity(final int minCapacity) {

        final ArrayList<T> list = arrayList();
        list.ensureCapacity(minCapacity);
    }
}
