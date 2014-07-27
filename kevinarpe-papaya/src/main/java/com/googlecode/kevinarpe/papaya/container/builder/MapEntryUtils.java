package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.google.common.collect.ImmutableList;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Test me
public final class MapEntryUtils
implements IMapEntryUtils {

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(TKey key, TValue value) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(TKey key, TValue value, TKey key2, TValue value2) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value, TKey key2, TValue value2, TKey key3, TValue value3) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7,
            TKey key8, TValue value8) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7,
                key8, value8);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7,
            TKey key8, TValue value8,
            TKey key9, TValue value9) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7,
                key8, value8,
                key9, value9);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7,
            TKey key8, TValue value8,
            TKey key9, TValue value9,
            TKey key10, TValue value10) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7,
                key8, value8,
                key9, value9,
                key10, value10);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7,
            TKey key8, TValue value8,
            TKey key9, TValue value9,
            TKey key10, TValue value10,
            TKey key11, TValue value11) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7,
                key8, value8,
                key9, value9,
                key10, value10,
                key11, value11);
        return list;
    }

    @Override
    public <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
            TKey key, TValue value,
            TKey key2, TValue value2,
            TKey key3, TValue value3,
            TKey key4, TValue value4,
            TKey key5, TValue value5,
            TKey key6, TValue value6,
            TKey key7, TValue value7,
            TKey key8, TValue value8,
            TKey key9, TValue value9,
            TKey key10, TValue value10,
            TKey key11, TValue value11,
            TKey key12, TValue value12) {
        ImmutableList<Map.Entry<TKey, TValue>> list =
            _create(
                key, value,
                key2, value2,
                key3, value3,
                key4, value4,
                key5, value5,
                key6, value6,
                key7, value7,
                key8, value8,
                key9, value9,
                key10, value10,
                key11, value11,
                key12, value12);
        return list;
    }

    private <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>> _create(Object... arr) {
        ImmutableList.Builder<Map.Entry<TKey, TValue>> builder = ImmutableList.builder();
        for (int i = 0; i < arr.length; ++i) {
            @SuppressWarnings("unchecked")
            TKey key = (TKey) arr[i];
            @SuppressWarnings("unchecked")
            TValue value = (TValue) arr[i];
            builder.add(_entryOf(key, value));
        }
        ImmutableList<Map.Entry<TKey, TValue>> list = builder.build();
        return list;
    }

    private <TKey, TValue> Map.Entry<TKey, TValue> _entryOf(TKey key, TValue value) {
        Map.Entry<TKey, TValue> x = new AbstractMap.SimpleImmutableEntry<TKey, TValue>(key, value);
        return x;
    }
}
