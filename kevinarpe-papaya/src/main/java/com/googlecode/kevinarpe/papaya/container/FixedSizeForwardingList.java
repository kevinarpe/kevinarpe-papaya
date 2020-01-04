package com.googlecode.kevinarpe.papaya.container;

/*
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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.List;
import java.util.ListIterator;

/**
 * This is a specialized form of {@link ForwardingList} that does not allow the underlying list size
 * to change.  However, elements may be modified via {@link #set(int, Object)}.
 * <p>
 * This class is closely related to {@link UnmodifiableForwardingList}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see UnmodifiableForwardingList
 * @see BaseFixedSizeForwardingList
 * @see ForwardingList
 * @see FixedSizeListIterator
 */
@FullyTested
public abstract class FixedSizeForwardingList<TValue>
extends BaseFixedSizeForwardingList<TValue> {

    protected FixedSizeForwardingList() {
        super("fixed size");
    }

    /**
     * Return value is an instance of {@link FixedSizeListIterator}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator() {
        return FixedSizeListIterator.of(super.listIterator());
    }

    /**
     * Return value is an instance of {@link FixedSizeListIterator}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public final ListIterator<TValue> listIterator(int index) {
        return FixedSizeListIterator.of(super.listIterator(index));
    }

    /**
     * Return value is an instance of {@link FixedSizeForwardingList}.  Also, it is final to disable
     * modify-by-inheritance.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public final List<TValue> subList(int fromIndex, int toIndex) {
        final List<TValue> sublist = super.subList(fromIndex, toIndex);
        return new FixedSizeForwardingList<TValue>() {
            @Override
            protected List<TValue> delegate() {
                return sublist;
            }
        };
    }
}
