package com.googlecode.kevinarpe.papaya.comparator;

import java.util.Comparator;

public abstract class AbstractStatelessComparator<TValue>
implements Comparator<TValue> {

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (null != obj && this.getClass().equals(obj.getClass())));
    }
}
