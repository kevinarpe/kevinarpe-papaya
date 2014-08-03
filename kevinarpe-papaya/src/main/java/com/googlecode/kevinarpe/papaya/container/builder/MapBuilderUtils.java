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

import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ClassArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
final class MapBuilderUtils
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>
    >
extends StatelessObject
implements IMapBuilderUtils<TKey, TValue, TMap> {

    public MapBuilderUtils() {
        super();
    }

    @Override
    public void putMany(
            MinimalistMap<TKey, TValue> map,
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            Object... keysAndValuesArr) {
        ObjectArgs.checkNotNull(map, "map");
        ClassArgs.checkNotGenericType(keyClass, "keyClass");
        ClassArgs.checkNotGenericType(valueClass, "valueClass");
        ObjectArgs.checkNotNull(keysAndValuesArr, "keysAndValuesArr");
        if (0 != keysAndValuesArr.length % 2) {
            throw new IllegalArgumentException(String.format(
                "Argument 'keysAndValuesArr' does not have an even length: %d",
                keysAndValuesArr.length));
        }

        for (int i = 0; i < keysAndValuesArr.length; i += 2) {
            Object key = keysAndValuesArr[i];
            Object value = keysAndValuesArr[1 + i];
            TKey castKey =
                ObjectArgs.checkCast(
                    key, keyClass, "keysAndValuesArr[" + i + "]", "keyClass");
            TValue castValue =
                ObjectArgs.checkCast(
                    value, valueClass, "keysAndValuesArr[" + (1 + i) + "]", "valueClass");
            map.put(castKey, castValue);
        }
    }

    @Override
    public void putMany(
            MinimalistMap<TKey, TValue> map,
            Iterable<? extends TKey> keyIterable,
            Iterable<? extends TValue> valueIterable) {
        ObjectArgs.checkNotNull(map, "map");
        ObjectArgs.checkNotNull(keyIterable, "keyIterable");
        ObjectArgs.checkNotNull(valueIterable, "valueIterable");

        Iterator<? extends TKey> keyIter = keyIterable.iterator();
        Iterator<? extends TValue> valueIter = valueIterable.iterator();
        int count = 0;
        while (keyIter.hasNext() && valueIter.hasNext()) {
            ++count;
            TKey key = keyIter.next();
            TValue value = valueIter.next();
            map.put(key, value);
        }
        _checkRemaining(keyIter, valueIter, count);
    }

    private void _checkRemaining(
        Iterator<? extends TKey> keyIter, Iterator<? extends TValue> valueIter, int count) {
        final int remainingKeyCount = Iterators.size(keyIter);
        final int remainingValueCount = Iterators.size(valueIter);
        if (0 != remainingKeyCount || 0 != remainingValueCount) {
            throw new IllegalArgumentException(String.format(
                "Argument 'keyIterable' has different size than argument 'valueIterable': %d != %d",
                count + remainingKeyCount, count + remainingValueCount));
        }
    }

    @Override
    public void putMany(
            MinimalistMap<TKey, TValue> map,
            Map.Entry<? extends TKey, ? extends TValue>... entryArr) {
        ObjectArgs.checkNotNull(map, "map");
        ArrayArgs.checkElementsNotNull(entryArr, "entryArr");

        for (Map.Entry<? extends TKey, ? extends TValue> entry : entryArr) {
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            map.put(key, value);
        }
    }

    @Override
    public void putMany(
            MinimalistMap<TKey, TValue> map,
            Iterable<? extends Map.Entry<? extends TKey, ? extends TValue>> entryIterable) {
        ObjectArgs.checkNotNull(map, "map");
        ObjectArgs.checkNotNull(entryIterable, "entryIterable");

        int index = 0;
        for (Map.Entry<? extends TKey, ? extends TValue> entry : entryIterable) {
            ObjectArgs.checkNotNull(entry, "entryIterable[" + index + "]");
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            map.put(key, value);
            ++index;
        }
    }
}
