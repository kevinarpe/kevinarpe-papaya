package com.googlecode.kevinarpe.papaya.container;

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

import com.google.common.collect.ForwardingList;

import java.util.List;
import java.util.ListIterator;

/**
 * This is a specialized form of {@link ForwardingList} that does not allow the underlying list
 * to change.
 * <p>
 * This class is closely related to {@link FixedSizeForwardingList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see FixedSizeForwardingList
 * @see BaseFixedSizeForwardingList
 * @see ForwardingList
 * @see UnmodifiableIterator
 * @see UnmodifiableListIterator
 */
public abstract class UnmodifiableForwardingList<TValue>
extends BaseFixedSizeForwardingList<TValue> {

    protected UnmodifiableForwardingList() {
        super("unmodifiable");
    }

    /**
     * Return value is an instance of {@link UnmodifiableListIterator}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator() {
        return UnmodifiableListIterator.of(super.listIterator());
    }

    /**
     * Return value is an instance of {@link UnmodifiableListIterator}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator(int index) {
        return UnmodifiableListIterator.of(super.listIterator(index));
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.  Also, it is final to
     * disable modify-by-inheritance.
     */
    @Override
    public final TValue set(int index, TValue element) {
        throwUnsupportedOperationException();
        return null;
    }

    /**
     * Return value is an instance of {@link UnmodifiableForwardingList}.  Also, it is final to
     * disable modify-by-inheritance.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final List<TValue> subList(int fromIndex, int toIndex) {
        final List<TValue> sublist = super.subList(fromIndex, toIndex);
        return new UnmodifiableForwardingList<TValue>() {
            @Override
            protected List<TValue> delegate() {
                return sublist;
            }
        };
    }
}
