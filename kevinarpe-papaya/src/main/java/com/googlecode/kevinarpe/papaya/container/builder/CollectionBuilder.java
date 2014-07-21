package com.googlecode.kevinarpe.papaya.container.builder;

import java.util.Collection;
import java.util.Iterator;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
public interface CollectionBuilder
    <
        TValue,
        TCollection extends Collection<TValue>,
        TSelf extends CollectionBuilder<TValue, TCollection, TSelf>
    >
extends Collection<TValue>, Builder<TCollection> {

    /**
     * Adds many values to the collection builder.
     *
     * @param valueArr
     *        zero or more value to add; must not be {@code null}
     *
     * @return reference to self
     */
    TSelf addMany(TValue... valueArr);

    TSelf addMany(Iterable<? extends TValue> valueIterable);

    TSelf addMany(Iterator<? extends TValue> valueIter);
}
