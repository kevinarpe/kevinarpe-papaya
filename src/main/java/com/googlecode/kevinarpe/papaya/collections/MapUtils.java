package com.googlecode.kevinarpe.papaya.collections;

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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;

class MapUtils {
    
    static class CoreMapInserter
            <
                TKey,
                TValue,
                // TODO: Style this alternative.
                //TMap extends Map<? extends TKey, ? extends TValue>,
                TMap extends Map<TKey, TValue>,
                TClass extends CoreMapInserter<TKey, TValue, TMap, TClass>
            > {
        
        @SuppressWarnings("serial")
        public static class DuplicateKeyException
        extends RuntimeException {
            
            public DuplicateKeyException(String message) {
                super(StringArgs.checkNotEmptyOrWhitespace(message, "message"));
            }
        }
        
        private boolean _allowNullKey = false;
        private boolean _allowNullValues = false;
        private boolean _allowDuplicateKeys = false;
        private List<Map.Entry<TKey, TValue>> _newEntryList;
       
        public CoreMapInserter() {
            _newEntryList = new LinkedList<Map.Entry<TKey, TValue>>();
        }
        
        public TClass allowNullKey(boolean b) {
            _allowNullKey = b;
            return (TClass) this;
        }
        
        public TClass allowNullValues(boolean b) {
            _allowNullValues = b;
            return (TClass) this;
        }
        
        public TClass allowDuplicateKeys(boolean b) {
            _allowDuplicateKeys = b;
            return (TClass) this;
        }
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass put(TKey2 key, TValue2 value) {
            _newEntryList.add(new AbstractMap.SimpleImmutableEntry<TKey, TValue>(key, value));
            return (TClass) this;
        }
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass put(Iterable<TKey2> keyIterable, Iterable<TValue2> valueIterable) {
            ObjectArgs.checkNotNull(keyIterable, "keyIterable");
            ObjectArgs.checkNotNull(valueIterable, "valueIterable");
            
            List<TKey2> keyList = Lists.newLinkedList(keyIterable);
            List<TValue2> valueList = Lists.newLinkedList(valueIterable);
            put(keyList, valueList);
            return (TClass) this;
        }
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass put(TKey2[] keyArr, TValue2[] valueArr) {
            ObjectArgs.checkNotNull(keyArr, "keyArr");
            ObjectArgs.checkNotNull(valueArr, "valueArr");
            
            List<TKey2> keyList = Arrays.asList(keyArr);
            List<TValue2> valueList = Arrays.asList(valueArr);
            put(keyList, valueList);
            return (TClass) this;
        }
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass put(List<TKey2> keyList, List<TValue2> valueList) {
            ObjectArgs.checkNotNull(keyList, "keyList");
            ObjectArgs.checkNotNull(valueList, "valueList");
            int keyListSize = keyList.size();
            int valueListSize = valueList.size();
            if (keyListSize != valueListSize) {
                throw new IllegalArgumentException(String.format(
                    "Key list size (%d) != Value list size (%d)", keyListSize, valueListSize));
            }
            
            for (int i = 0; i < keyListSize; ++i) {
                TKey2 key = keyList.get(i);
                TValue2 value = valueList.get(i);
                _newEntryList.add(new AbstractMap.SimpleImmutableEntry<TKey, TValue>(key, value));
            }
            return (TClass) this;
        }
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass putAll(Map<TKey2, TValue2> map) {
            ObjectArgs.checkNotNull(map, "map");
            
            putAll(map.entrySet());
            return (TClass) this;
        }
        
        
        public <TKey2 extends TKey, TValue2 extends TValue>
        TClass putAll(Iterable<Map.Entry<TKey2, TValue2>> entryIterable) {
            for (Map.Entry<TKey2, TValue2> entry: entryIterable) {
                TKey2 key = entry.getKey();
                TValue2 value = entry.getValue();
                put(key, value);
            }
            return (TClass) this;
        }
        
        public TMap insert(TMap map) {
            ObjectArgs.checkNotNull(map, "map");
            
            Set<TKey> newKeySet =
                (_allowDuplicateKeys ? null : new HashSet<TKey>(_newEntryList.size()));
            for (Map.Entry<TKey, TValue> entry: _newEntryList) {
                TKey key = entry.getKey();
                TValue value = entry.getValue();
                if (!_allowNullKey && null == key) {
                    throw new NullPointerException(String.format(
                        "Key is null, but null key is not allowed: Value = '%s'", value));
                }
                if (!_allowNullValues && null == value) {
                    throw new NullPointerException(String.format(
                        "Value is null, but null values are not allowed: Key = '%s'", key));
                }
                if (!_allowDuplicateKeys) {
                    if (newKeySet.contains(key)) {
                        throw new DuplicateKeyException(String.format(
                            "Key is duplicate in new entries for insert: '%s'", key));
                    }
                    if (map.containsKey(key)) {
                        throw new DuplicateKeyException(String.format(
                            "Key is duplicate in map for insert: '%s'", key));
                    }
                    newKeySet.add(key);
                }
                map.put(key, value);
            }
            return map;
        }
    }
}
