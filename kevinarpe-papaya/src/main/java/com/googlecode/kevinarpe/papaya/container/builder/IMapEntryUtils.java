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

import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IMapEntryUtils {

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4,
        TKey key5, TValue value5);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4,
        TKey key5, TValue value5,
        TKey key6, TValue value6);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4,
        TKey key5, TValue value5,
        TKey key6, TValue value6,
        TKey key7, TValue value7);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4,
        TKey key5, TValue value5,
        TKey key6, TValue value6,
        TKey key7, TValue value7,
        TKey key8, TValue value8);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
    createList(
        TKey key, TValue value,
        TKey key2, TValue value2,
        TKey key3, TValue value3,
        TKey key4, TValue value4,
        TKey key5, TValue value5,
        TKey key6, TValue value6,
        TKey key7, TValue value7,
        TKey key8, TValue value8,
        TKey key9, TValue value9);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
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
        TKey key10, TValue value10);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
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
        TKey key11, TValue value11);

    <TKey, TValue> ImmutableList<Map.Entry<TKey, TValue>>
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
        TKey key12, TValue value12);
}
