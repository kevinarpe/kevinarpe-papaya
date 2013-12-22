package com.googlecode.kevinarpe.papaya.container;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.List;

// Ref: http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection
public final class ArrayAsUnmodifiableList<TValue>
extends UnmodifiableForwardingList<TValue> {

    public static <TValue> ArrayAsUnmodifiableList referenceTo(TValue[] arr) {
        return new ArrayAsUnmodifiableList(arr);
    }

    private final TValue[] _arr;
    private final List<TValue> _arrAsList;

    private ArrayAsUnmodifiableList(TValue[] arr) {
        ObjectArgs.checkNotNull(arr, "arr");

        _arr = arr;
        _arrAsList = Arrays.asList(arr);
    }

    @Override
    protected List<TValue> delegate() {
        return _arrAsList;
    }
}
