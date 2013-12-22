package com.googlecode.kevinarpe.papaya.container;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.*;

// Ref: http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection
public final class ArrayAsImmutableList<TValue>
extends UnmodifiableForwardingList<TValue> {

    public static <TValue> ArrayAsImmutableList copyOf(TValue[] arr) {
        return new ArrayAsImmutableList(arr);
    }

    private final TValue[] _arr;
    private final List<TValue> _arrAsList;

    private ArrayAsImmutableList(TValue[] arr) {
        ObjectArgs.checkNotNull(arr, "arr");

        _arr = arr.clone();
        _arrAsList = Arrays.asList(arr);
    }

    @Override
    protected List<TValue> delegate() {
        return _arrAsList;
    }
}
