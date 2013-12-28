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
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.*;

/**
 * This is a base class used by {@link FixedSizeForwardingList} and
 * {@link UnmodifiableForwardingList}.  There is no reason to use it directly; it is public only
 * to expose documentation via Javadoc.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see FixedSizeForwardingList
 * @see UnmodifiableForwardingList
 * @see ForwardingList
 */
public abstract class BaseFixedSizeForwardingList<TValue>
extends ForwardingList<TValue> {

    private final String featureDescription;

    /**
     * @param featureDescription
     *        Examples: {@code "fixed size"} or {@code "unmodifiable"}
     */
    protected BaseFixedSizeForwardingList(String featureDescription) {
        this.featureDescription =
            StringArgs.checkNotEmptyOrWhitespace(featureDescription, "featureDescription");
    }

    protected final void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException(String.format(
            "Class is %s: %s", featureDescription, this.getClass().getName()));
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final void add(int index, TValue element) {
        throwUnsupportedOperationException();
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean addAll(int index, Collection<? extends TValue> elements) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final TValue remove(int index) {
        throwUnsupportedOperationException();
        return null;
    }

    /**
     * Return value is an instance of {@link UnmodifiableIterator}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final Iterator<TValue> iterator() {
        return UnmodifiableIterator.of(super.iterator());
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean removeAll(Collection<?> collection) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean add(TValue element) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean remove(Object object) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean addAll(Collection<? extends TValue> collection) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final boolean retainAll(Collection<?> collection) {
        throwUnsupportedOperationException();
        return false;
    }

    /**
     * This method always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final void clear() {
        throwUnsupportedOperationException();
    }
}
