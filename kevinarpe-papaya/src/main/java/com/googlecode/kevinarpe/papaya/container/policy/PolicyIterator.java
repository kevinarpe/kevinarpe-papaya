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

import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PolicyListIterator
 * @see PolicyCollection
 * @see PolicyList
 * @see PolicySet
 * @see PolicyMap
 * @see PolicyMapEntry
 */
@FullyTested
public class PolicyIterator<T>
extends ForwardingIterator<T> {

    private final Iterator<T> iter;
    private final ImmutableSet<DoNotAllow> doNotAllowSet;

    public PolicyIterator(Iterator<T> iter,
                          @EmptyContainerAllowed
                          Set<DoNotAllow> doNotAllowSet) {

        this.iter = ObjectArgs.checkNotNull(iter, "iter");
        CollectionArgs.checkElementsNotNull(doNotAllowSet, "doNotAllowSet");
        this.doNotAllowSet = Sets.immutableEnumSet(doNotAllowSet);
    }

    @Override
    protected Iterator<T>
    delegate() {
        return iter;
    }

    public ImmutableSet<DoNotAllow>
    doNotAllowSet() {
        return doNotAllowSet;
    }
//
//    @Override
//    public boolean hasNext() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public T next() {
//        abc // Intentional syntax error
//    }

    /**
     * {@inheritDoc}
     * <hr>
     * Override notes: Throws {@link UnsupportedOperationException} when {@link DoNotAllow#Remove}
     */
    // default -- Yeah, I was surprised too.
    @Override
    public void
    remove() {

        DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.Remove);
        super.remove();
    }
}
