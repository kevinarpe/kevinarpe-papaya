package com.googlecode.kevinarpe.papaya.collect;

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
