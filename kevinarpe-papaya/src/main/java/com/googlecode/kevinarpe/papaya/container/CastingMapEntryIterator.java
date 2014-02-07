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

import com.google.common.base.Function;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CastingMapEntryIterator<TInKey, TInValue, TOutKey, TOutValue>
extends ForwardingIterator<Map.Entry<TOutKey, TOutValue>> {

    public static final class CastingMapEntryIteratorBuilder<TInKey2, TInValue2> {

        private final Map<TInKey2, TInValue2> map;

        private CastingMapEntryIteratorBuilder(Map<TInKey2, TInValue2> map) {
            this.map = ObjectArgs.checkNotNull(map, "map");
        }

        public <TOutKey2>
        CastingMapEntryIteratorBuilder2<TInKey2, TInValue2, TOutKey2>
        keyAs(Class<TOutKey2> keyClass) {
            ObjectArgs.checkNotNull(keyClass, "keyClass");
            CastingMapEntryIteratorBuilder2<TInKey2, TInValue2, TOutKey2> x =
                new CastingMapEntryIteratorBuilder2<TInKey2, TInValue2, TOutKey2>(
                    map, keyClass);
            return x;
        }
    }

    public static final class CastingMapEntryIteratorBuilder2<TInKey2, TInValue2, TOutKey2> {

        private final Map<TInKey2, TInValue2> map;
        private final Class<TOutKey2> keyClass;

        private CastingMapEntryIteratorBuilder2(
                Map<TInKey2, TInValue2> map, Class<TOutKey2> keyClass) {
            this.map = ObjectArgs.checkNotNull(map, "map");
            this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        }

        public <TOutValue2>
        CastingMapEntryIterator<TInKey2, TInValue2, TOutKey2, TOutValue2>
        valueAs(Class<TOutValue2> valueClass) {
            ObjectArgs.checkNotNull(valueClass, "valueClass");
            CastingMapEntryIterator<TInKey2, TInValue2, TOutKey2, TOutValue2> x =
                new CastingMapEntryIterator<TInKey2, TInValue2, TOutKey2, TOutValue2>(
                    map, keyClass, valueClass);
            return x;
        }
    }

    public static <TInKey2, TInValue2>
    CastingMapEntryIteratorBuilder<TInKey2, TInValue2>
    from(Map<TInKey2, TInValue2> map) {
        CastingMapEntryIteratorBuilder<TInKey2, TInValue2> x =
            new CastingMapEntryIteratorBuilder<TInKey2, TInValue2>(map);
        return x;
    }

    private final Map<TInKey, TInValue> map;
    private final Class<TOutKey> keyClass;
    private final Class<TOutValue> valueClass;
    private final Iterator<Map.Entry<TInKey, TInValue>> iterator;

    private CastingMapEntryIterator(
            Map<TInKey, TInValue> map,
            Class<TOutKey> keyClass,
            Class<TOutValue> valueClass) {
        this.map = ObjectArgs.checkNotNull(map, "map");
        this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
        final Set<Map.Entry<TInKey, TInValue>> entrySet = map.entrySet();
        iterator = entrySet.iterator();
    }

    @Override
    protected Iterator<Map.Entry<TOutKey, TOutValue>> delegate() {
        Iterator<Map.Entry<TOutKey, TOutValue>> x =
            Iterators.transform(
                    iterator,
                    new Function<Map.Entry<TInKey, TInValue>, Map.Entry<TOutKey, TOutValue>>() {
                @Nullable
                @Override
                public Map.Entry<TOutKey, TOutValue> apply(Map.Entry<TInKey, TInValue> input) {
                    CastingForwardingMapEntry<TInKey, TInValue, TOutKey, TOutValue> output =
                        CastingForwardingMapEntry.from(input).keyAs(keyClass).valueAs(valueClass);
                    return output;
                }
            });
        return x;
    }
}
