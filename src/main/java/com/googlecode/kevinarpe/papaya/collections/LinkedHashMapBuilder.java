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

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapBuilder<TKey, TValue>
extends MapUtils.CoreMapInserter<TKey, TValue, Map<TKey,TValue>, LinkedHashMapBuilder<TKey, TValue>> {
    
    public static <TKey, TValue> LinkedHashMapBuilder<TKey, TValue> create() {
        LinkedHashMapBuilder<TKey, TValue> x = new LinkedHashMapBuilder<TKey, TValue>();
        return x;
    }
    
    protected LinkedHashMapBuilder() {
        super();
    }
    
    public LinkedHashMap<TKey, TValue> build() {
        LinkedHashMap<TKey, TValue> map = new LinkedHashMap<TKey, TValue>();
        insert(map);
        return map;
    }
}
