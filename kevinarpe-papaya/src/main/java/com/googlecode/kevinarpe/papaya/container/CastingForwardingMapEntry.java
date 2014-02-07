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

import com.googlecode.kevinarpe.papaya.ObjectUtils;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class CastingForwardingMapEntry<TInKey, TInValue, TOutKey, TOutValue>
implements Map.Entry<TOutKey, TOutValue> {

//    static {
//        JSONObject jsonObject = new JSONObject();
//        Map.Entry entry = new AbstractMap.SimpleEntry(null, null);
//        CastingForwardingMapEntry.from(entry);
//    }

    public static final class CastingForwardingMapEntryBuilder<TInKey2, TInValue2> {

        private final Map.Entry<TInKey2, TInValue2> entry;

        private CastingForwardingMapEntryBuilder(Map.Entry<TInKey2, TInValue2> entry) {
            this.entry = ObjectArgs.checkNotNull(entry, "entry");
        }

        public <TOutKey2>
        CastingForwardingMapEntryBuilder2<TInKey2, TInValue2, TOutKey2> keyAs(
                Class<TOutKey2> keyClass) {
            ObjectArgs.checkNotNull(keyClass, "keyClass");
            CastingForwardingMapEntryBuilder2<TInKey2, TInValue2, TOutKey2> x =
                new CastingForwardingMapEntryBuilder2<TInKey2, TInValue2, TOutKey2>(
                    entry, keyClass);
            return x;
        }
    }

    public static final class CastingForwardingMapEntryBuilder2
            <TInKey2, TInValue2, TOutKey2> {

        private final Map.Entry<TInKey2, TInValue2> entry;
        private final Class<TOutKey2> keyClass;

        private CastingForwardingMapEntryBuilder2(
                Map.Entry<TInKey2, TInValue2> entry, Class<TOutKey2> keyClass) {
            this.entry = ObjectArgs.checkNotNull(entry, "entry");
            this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        }

        public <TOutValue2>
        CastingForwardingMapEntry<TInKey2, TInValue2, TOutKey2, TOutValue2> valueAs(
                Class<TOutValue2> valueClass) {
            ObjectArgs.checkNotNull(valueClass, "valueClass");
            CastingForwardingMapEntry<TInKey2, TInValue2, TOutKey2, TOutValue2> x =
                new CastingForwardingMapEntry<TInKey2, TInValue2, TOutKey2, TOutValue2>(
                    entry, keyClass, valueClass);
            return x;
        }
    }

    public static <TInKey2, TInValue2>
    CastingForwardingMapEntryBuilder<TInKey2, TInValue2> from(
            Map.Entry<TInKey2, TInValue2> entry) {
//        ArrayList a = new ArrayList();
//        ArrayList b = new ArrayList<Object>();
//        ArrayList<?> c = new ArrayList<Object>();
//        ArrayList<?> d = new ArrayList();
        CastingForwardingMapEntryBuilder<TInKey2, TInValue2> x =
            new CastingForwardingMapEntryBuilder<TInKey2, TInValue2>(entry);
        return x;
    }

    // TODO: Replace TInValue and TOutValue with ? and ?
    // This matches raw types better.  Cast to <Object, Object> as necessary later.
    public static
    CastingForwardingMapEntryBuilder<?, ?> fromRaw(Map.Entry rawEntry) {
        Map.Entry<?, ?> entry = rawEntry;
        CastingForwardingMapEntryBuilder<?, ?> x =
            new CastingForwardingMapEntryBuilder(entry);
        return x;
    }

    private final Map.Entry<TInKey, TInValue> entry;
    private final Class<TOutKey> keyClass;
    private final Class<TOutValue> valueClass;

    private CastingForwardingMapEntry(
            Map.Entry<TInKey, TInValue> entry,
            Class<TOutKey> keyClass,
            Class<TOutValue> valueClass) {
        this.entry = ObjectArgs.checkNotNull(entry, "entry");
        this.keyClass = ObjectArgs.checkNotNull(keyClass, "keyClass");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    @Override
    public TOutKey getKey() {
        Object key = entry.getKey();
        TOutKey castKey = ObjectUtils.cast("key", key, keyClass);
        return castKey;
    }

    @Override
    public TOutValue getValue() {
        Object value = entry.getValue();
        TOutValue castValue = ObjectUtils.cast("value", value, valueClass);
        return castValue;
    }

    @Override
    public TOutValue setValue(TOutValue value) {
        TypeVariable[] typeVarArr = entry.getClass().getTypeParameters();
        TypeVariable typeVar = typeVarArr[1];  // 0 == TKey, 1 == TValue
        Class<?> otherValueClass = typeVar.getClass();
        Object castValue = ObjectUtils.cast("value", value, otherValueClass);
        @SuppressWarnings("unchecked")
        Map.Entry<Object, Object> castEntry = (Map.Entry<Object, Object>) entry;
        Object oldValue = castEntry.setValue(castValue);
        TOutValue castOldValue = ObjectUtils.cast("previous value", oldValue, valueClass);
        return castOldValue;
    }
}
