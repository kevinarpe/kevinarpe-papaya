package com.googlecode.kevinarpe.papaya.container;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.BiMap;
import com.google.common.collect.ForwardingMap;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class ForwardingBiMap<TKey, TValue>
extends ForwardingMap<TKey, TValue>
implements BiMap<TKey, TValue> {

    protected ForwardingBiMap() {}

    abstract protected BiMap<TKey, TValue> delegate();

    @Override
    public TValue forcePut(@Nullable TKey key, @Nullable TValue value) {
        TValue x = delegate().forcePut(key, value);
        return x;
    }

    @Override
    public BiMap<TValue, TKey> inverse() {
        BiMap<TValue, TKey> x = delegate().inverse();
        return x;
    }

    @Override
    public Set<TValue> values() {
        return (Set<TValue>) super.values();
    }
}
