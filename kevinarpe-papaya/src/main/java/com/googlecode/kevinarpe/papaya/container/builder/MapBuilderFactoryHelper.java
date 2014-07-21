package com.googlecode.kevinarpe.papaya.container.builder;

import java.util.Map;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public interface MapBuilderFactoryHelper
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>
    > {

    void put(TKey key, TValue value);

    TMap getMap();
}
