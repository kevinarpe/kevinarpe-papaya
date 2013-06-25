package com.googlecode.kevinarpe.papaya.collect;

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

import java.util.HashMap;
import java.util.Map;

public class HashMapBuilder<TKey, TValue>
extends MapUtils.CoreMapInserter<TKey, TValue, Map<TKey,TValue>, HashMapBuilder<TKey, TValue>> {
    
    public static <TKey, TValue> HashMapBuilder<TKey, TValue> create() {
        HashMapBuilder<TKey, TValue> x = new HashMapBuilder<TKey, TValue>();
        return x;
    }
    
    protected HashMapBuilder() {
        super();
    }
    
    public HashMap<TKey, TValue> build() {
        HashMap<TKey, TValue> map = new HashMap<TKey, TValue>();
        insert(map);
        return map;
    }
}
