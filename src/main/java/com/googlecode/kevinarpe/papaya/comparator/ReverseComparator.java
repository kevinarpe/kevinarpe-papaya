package com.googlecode.kevinarpe.papaya.comparator;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Comparator;

public class ReverseComparator<TValue, TComparator extends Comparator<TValue>>
implements Comparator<TValue> {

    private final Comparator<TValue> _comparator;

    public ReverseComparator(Comparator<TValue> comparator) {
        _comparator = ObjectArgs.checkNotNull(comparator, "comparator");
    }

    @Override
    public int compare(TValue value1, TValue value2) {
        return -1 * _comparator.compare(value1, value2);
    }
}
