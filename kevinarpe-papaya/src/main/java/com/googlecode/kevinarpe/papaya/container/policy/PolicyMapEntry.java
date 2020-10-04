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

import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PolicyIterator
 * @see PolicyListIterator
 * @see PolicyCollection
 * @see PolicyList
 * @see PolicySet
 * @see PolicyMap
 */
@FullyTested
public class PolicyMapEntry<TKey, TValue>
extends ForwardingMapEntry<TKey, TValue> {

    private final Map.Entry<TKey, TValue> entry;
    private final ImmutableSet<DoNotAllow> doNotAllowSet;

    public PolicyMapEntry(Map.Entry<TKey, TValue> entry,
                          @EmptyContainerAllowed
                          Set<DoNotAllow> doNotAllowSet) {

        this.entry = ObjectArgs.checkNotNull(entry, "entry");
        CollectionArgs.checkElementsNotNull(doNotAllowSet, "doNotAllowSet");
        this.doNotAllowSet = Sets.immutableEnumSet(doNotAllowSet);
    }

    @Override
    protected Map.Entry<TKey, TValue>
    delegate() {
        return entry;
    }

    public ImmutableSet<DoNotAllow>
    doNotAllowSet() {
        return doNotAllowSet;
    }
//
//    @Override
//    public TKey getKey() {
//        abc // Intentional syntax error
//    }
//
//    @Override
//    public TValue getValue() {
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
    public TValue
    setValue(@Nullable TValue value) {

        if (null == value) {
            DoNotAllow.throwIfNotAllowed(doNotAllowSet(), DoNotAllow.NullValue);
        }
        @Nullable final TValue x = super.setValue(value);
        return x;
    }
}
