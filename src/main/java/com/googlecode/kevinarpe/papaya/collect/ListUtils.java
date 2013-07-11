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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

public class ListUtils {
    
    static abstract class AbstractCoreListInserter
            <
                TValue,
                TList extends List<TValue>,
                TClass extends AbstractCoreListInserter<TValue, TList, TClass>
            > {
        
        private boolean _allowNulls = false;
        private List<TValue> _newValueList;
        
        public AbstractCoreListInserter() {
            _newValueList = new ArrayList<TValue>();
        }
        
        protected abstract TClass self();
        
        public TClass allowNulls(boolean b) {
            _allowNulls = b;
            TClass self = self();
            return self;
        }
        
        public <TValue2 extends TValue>
        TClass add(TValue value) {
            _newValueList.add(value);
            TClass self = self();
            return self;
        }
        
        public TList add(TList list) {
            ObjectArgs.checkNotNull(list, "list");
            
            if (!_allowNulls) {
                for (TValue value: _newValueList) {
                    if (null == value) {
                        throw new NullPointerException("Null values are not allowed");
                    }
                }
            }
            list.addAll(_newValueList);
            return list;
        }
    }
}
