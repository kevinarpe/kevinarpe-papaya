package com.googlecode.kevinarpe.papaya.container.builder;

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public abstract class AbstractMapBuilderFactory
    <
        TKey,
        TValue,
        TMap extends Map<TKey, TValue>,
        TMapBuilder extends MapBuilder<TKey, TValue, TMap>
    >
extends StatelessObject
implements MapBuilderFactory
        <
            TKey,
            TValue,
            TMap,
            TMapBuilder
        > {

    protected abstract MapBuilderFactoryHelper<TKey, TValue, TMap>
    createMapBuilderHelper(int initialCapacity);

    @Override
    public final TMap copyOf(
        Class<TKey> keyClass, Class<TValue> valueClass, Object... keysAndValuesArr) {
        ObjectArgs.checkNotGenericType(keyClass, "keyClass");
        ObjectArgs.checkNotGenericType(valueClass, "valueClass");
        ObjectArgs.checkNotNull(keysAndValuesArr, "keysAndValuesArr");
        if (0 != keysAndValuesArr.length % 2) {
            throw new IllegalArgumentException(String.format(
                "Argument 'keysAndValuesArr' does not have an even length: %d",
                keysAndValuesArr.length));
        }

        MapBuilderFactoryHelper<TKey, TValue, TMap> mapBuilderHelper =
            createMapBuilderHelper(keysAndValuesArr.length / 2);
        for (int i = 0; i < keysAndValuesArr.length; i += 2) {
            Object key = keysAndValuesArr[i];
            Object value = keysAndValuesArr[1 + i];
            TKey castKey =
                ObjectArgs.checkCast(
                    key, keyClass, "keysAndValuesArr[" + i + "]", "keyClass");
            TValue castValue =
                ObjectArgs.checkCast(
                    value, valueClass, "keysAndValuesArr[" + (1 + i) + "]", "valueClass");
            mapBuilderHelper.put(castKey, castValue);
        }
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(Map.Entry<? extends TKey, ? extends TValue>... entryArr) {
        MapBuilderFactoryHelper<TKey, TValue, TMap> mapBuilderHelper =
            createMapBuilderHelper(entryArr.length);
        for (Map.Entry<? extends TKey, ? extends TValue> entry : entryArr) {
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            mapBuilderHelper.put(key, value);
        }
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(
            Collection<? extends TKey> keyCollection,
            Collection<? extends TValue> valueCollection) {
        ObjectArgs.checkNotNull(keyCollection, "keyCollection");
        ObjectArgs.checkNotNull(valueCollection, "valueCollection");
        if (keyCollection.size() != valueCollection.size()) {
            throw new IllegalArgumentException(String.format(
                "Argument 'keyCollection' has different size than argument 'valueCollection': %d != %d",
                keyCollection.size(), valueCollection.size()));
        }

        MapBuilderFactoryHelper<TKey, TValue, TMap> mapBuilderHelper =
            createMapBuilderHelper(keyCollection.size());
        Iterator<? extends TKey> keyIter = keyCollection.iterator();
        Iterator<? extends TValue> valueIter = valueCollection.iterator();
        while (keyIter.hasNext()) {
            TKey key = keyIter.next();
            TValue value = valueIter.next();
            mapBuilderHelper.put(key, value);
        }
        TMap map = mapBuilderHelper.getMap();
        return map;
    }

    @Override
    public final TMap copyOf(
            Collection<? extends Map.Entry<? extends TKey, ? extends TValue>> entryCollection) {
        ObjectArgs.checkNotNull(entryCollection, "entryCollection");

        MapBuilderFactoryHelper<TKey, TValue, TMap> mapBuilderHelper =
            createMapBuilderHelper(entryCollection.size());
        for (Map.Entry<? extends TKey, ? extends TValue> entry : entryCollection) {
            TKey key = entry.getKey();
            TValue value = entry.getValue();
            mapBuilderHelper.put(key, value);
        }
        TMap map = mapBuilderHelper.getMap();
        return map;
    }
}
