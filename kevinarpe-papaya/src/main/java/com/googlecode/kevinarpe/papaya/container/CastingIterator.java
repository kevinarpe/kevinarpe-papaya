package com.googlecode.kevinarpe.papaya.container;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.google.common.base.Function;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.ObjectUtils;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class CastingIterator<TInValue, TOutValue>
extends ForwardingIterator<TOutValue> {

    public static final class CastingIteratorBuilder<TInValue2> {

        private final Iterator<TInValue2> iterator;

        private CastingIteratorBuilder(Iterator<TInValue2> iterator) {
            this.iterator = ObjectArgs.checkNotNull(iterator, "iterator");
        }

        public <TOutValue2> CastingIterator<TInValue2, TOutValue2> as(
                Class<TOutValue2> valueClass) {
            ObjectArgs.checkNotNull(valueClass, "valueClass");
            CastingIterator<TInValue2, TOutValue2> x =
                new CastingIterator<TInValue2, TOutValue2>(iterator, valueClass);
            return x;
        }
    }

    public static <TInValue2> CastingIteratorBuilder<TInValue2> from(
            Iterator<TInValue2> iterator) {
        CastingIteratorBuilder<TInValue2> x = new CastingIteratorBuilder<TInValue2>(iterator);
        return x;
    }

    private final Iterator<TInValue> iterator;
    private final Class<TOutValue> valueClass;

    private CastingIterator(Iterator<TInValue> iterator, Class<TOutValue> valueClass) {
        this.iterator = ObjectArgs.checkNotNull(iterator, "iterator");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    @Override
    protected Iterator<TOutValue> delegate() {
        Iterator<TOutValue> x =
            Iterators.transform(iterator, new Function<TInValue, TOutValue>() {
                @Nullable
                @Override
                public TOutValue apply(TInValue input) {
                    TOutValue output = ObjectUtils.cast(input, valueClass);
                    return output;
                }
            });
        return x;
    }
}
