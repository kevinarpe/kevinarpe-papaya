package com.googlecode.kevinarpe.papaya.junit;

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ClassFinderResult {

    private final List<Class<?>> _classList;

    public ClassFinderResult(List<Class<?>> classList) {
        _classList = CollectionArgs.checkElementsNotNull(classList, "classList");
    }

    public List<Class<?>> asList() {
        return ImmutableList.copyOf(_classList);
    }

    public Class<?>[] asArray() {
        Class<?>[] classArr = new Class<?>[_classList.size()];
        classArr = _classList.toArray(classArr);
        return classArr;
    }
}
