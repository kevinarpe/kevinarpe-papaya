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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Iterator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class CastingIterable<TInValue, TOutValue>
implements Iterable<TOutValue> {


    public static final class CastingIterableBuilder<TInValue2> {

        private final Iterable<TInValue2> iterable;

        private CastingIterableBuilder(Iterable<TInValue2> iterable) {
            this.iterable = ObjectArgs.checkNotNull(iterable, "iterable");
        }

        public <TOutValue2> CastingIterable<TInValue2, TOutValue2> as(
            Class<TOutValue2> valueClass) {
            ObjectArgs.checkNotNull(valueClass, "valueClass");
            CastingIterable<TInValue2, TOutValue2> x =
                new CastingIterable<TInValue2, TOutValue2>(iterable, valueClass);
            return x;
        }
    }

    public static <TInValue2> CastingIterableBuilder<TInValue2> from(
        Iterable<TInValue2> iterable) {
        CastingIterableBuilder<TInValue2> x = new CastingIterableBuilder<TInValue2>(iterable);
        return x;
    }

    private final Iterable<TInValue> iterable;
    private final Class<TOutValue> valueClass;

    private CastingIterable(Iterable<TInValue> iterable, Class<TOutValue> valueClass) {
        this.iterable = ObjectArgs.checkNotNull(iterable, "iterable");
        this.valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    @Override
    public CastingIterator<TInValue, TOutValue> iterator() {
        Iterator<TInValue> iterator = iterable.iterator();
        CastingIterator<TInValue, TOutValue> x =
            CastingIterator.from(iterator).as(valueClass);
        return x;
    }
}
