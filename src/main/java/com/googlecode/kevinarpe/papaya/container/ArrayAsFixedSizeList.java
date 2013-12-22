package com.googlecode.kevinarpe.papaya.container;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Arrays;
import java.util.List;

// Ref: http://stackoverflow.com/questions/8892350/immutable-vs-unmodifiable-collection
public final class ArrayAsFixedSizeList<TValue>
extends FixedSizeForwardingList<TValue> {

    public static <TValue> ArrayAsFixedSizeList copyOf(TValue[] arr) {
        final boolean shouldCloneArr = true;
        return new ArrayAsFixedSizeList(arr, shouldCloneArr);
    }

    public static <TValue> ArrayAsFixedSizeList referenceTo(TValue[] arr) {
        final boolean shouldCloneArr = false;
        return new ArrayAsFixedSizeList(arr, shouldCloneArr);
    }

    private final TValue[] _arr;
    private final List<TValue> _arrAsList;

    private ArrayAsFixedSizeList(TValue[] arr, boolean shouldCloneArr) {
        ObjectArgs.checkNotNull(arr, "arr");

        _arr = (shouldCloneArr ? arr.clone() : arr);
        _arrAsList = Arrays.asList(_arr);
    }

    @Override
    protected List<TValue> delegate() {
        return _arrAsList;
    }

    public TValue[] getArrayRef() {
        return _arr;
    }

//    @Override
//    public Object[] toArray() {
//        return _arr.clone();
//    }
//
//    @Override
//    public <T> T[] toArray(T[] array) {
//        // This logic is directly copied from ArrayList.toArray(T[])
//        if (array.length < _arr.length) {
//            // Make a new array of a's runtime type, but my contents:
//            return (T[]) Arrays.copyOf(_arr, _arr.length, array.getClass());
//        }
//        System.arraycopy(_arr, 0, array, 0, _arr.length);
//        if (array.length > _arr.length) {
//            array[_arr.length] = null;
//        }
//        return array;
//    }
}
