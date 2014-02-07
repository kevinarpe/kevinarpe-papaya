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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CastingMapEntryIterable<TInKey, TInValue, TOutKey, TOutValue>
implements Iterable<Map.Entry<TOutKey, TOutValue>> {

    static {
        JSONObject jsonObject = new JSONObject();
        // TODO: Make me work
//        CastingMapEntryIterable<?, ?, String, String> x =
//        CastingMapEntryIterable.from(jsonObject).keyAs(String.class).valueAs(String.class);
//        for (Map.Entry<String, String> entry :
//                CastingMapEntryIterable.from(jsonObject).keyAs(String.class).valueAs(String.class)) {
//
//        }
    }

    public static final class CastingMapEntryIterableBuilder<TInKey2, TInValue2> {

        private final Map<TInKey2, TInValue2> map;

        private CastingMapEntryIterableBuilder(Map<TInKey2, TInValue2> map) {
            this.map = ObjectArgs.checkNotNull(map, "map");
        }

        public <TOutKey2>
        CastingMapEntryIterableBuilder2<TInKey2, TInValue2, TOutKey2>
        keyAs(Class<TOutKey2> keyClass) {
            ObjectArgs.checkNotNull(keyClass, "keyClass");
            CastingMapEntryIterableBuilder2<TInKey2, TInValue2, TOutKey2> x =
                new CastingMapEntryIterableBuilder2<TInKey2, TInValue2, TOutKey2>(
                    map, keyClass);
            return x;
        }
    }

    public static final class CastingMapEntryIterableBuilder2<TInKey2, TInValue2, TOutKey2> {

        private final Map<TInKey2, TInValue2> map;
        private final Class<TOutKey2> keyClass;

        private CastingMapEntryIterableBuilder2(
                Map<TInKey2, TInValue2> map, Class<TOutKey2> keyClass) {
            this.map = ObjectArgs.checkNotNull(map, "map");
            this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        }

        public <TOutValue2>
        CastingMapEntryIterable<TInKey2, TInValue2, TOutKey2, TOutValue2>
        valueAs(Class<TOutValue2> valueClass) {
            ObjectArgs.checkNotNull(valueClass, "valueClass");
            CastingMapEntryIterable<TInKey2, TInValue2, TOutKey2, TOutValue2> x =
                new CastingMapEntryIterable<TInKey2, TInValue2, TOutKey2, TOutValue2>(
                    map, keyClass, valueClass);
            return x;
        }
    }

    public static <TInKey2, TInValue2>
    CastingMapEntryIterableBuilder<TInKey2, TInValue2>
    from(Map<TInKey2, TInValue2> map) {
        CastingMapEntryIterableBuilder<TInKey2, TInValue2> x =
            new CastingMapEntryIterableBuilder<TInKey2, TInValue2>(map);
        return x;
    }

    private final Map<TInKey, TInValue> map;
    private final Class<TOutKey> keyClass;
    private final Class<TOutValue> valueClass;

    private CastingMapEntryIterable(
            Map<TInKey, TInValue> map,
            Class<TOutKey> keyClass,
            Class<TOutValue> valueClass) {
        this.map = ObjectArgs.checkNotNull(map, "map");
        this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    @Override
    public Iterator<Map.Entry<TOutKey, TOutValue>> iterator() {
        CastingMapEntryIterator<TInKey, TInValue, TOutKey, TOutValue> x =
            CastingMapEntryIterator.from(map).keyAs(keyClass).valueAs(valueClass);
        return x;
    }
}
