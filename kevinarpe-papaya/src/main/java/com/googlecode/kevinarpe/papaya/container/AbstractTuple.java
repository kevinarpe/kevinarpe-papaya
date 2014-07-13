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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.string.joiner.Joiner2;
import com.googlecode.kevinarpe.papaya.string.joiner.Joiner2Utils;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.AutoFormatter2;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.StringFormatter2;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract super class for tuples.  This class can be used to easily create multi-element keys and
 * values for collections.  This class also extends {@link UnmodifiableForwardingList} so it can
 * conveniently be used as a {@link List} when necessary.
 * <p>
 * For example, if a map needs to be keyed by two elements:
 * <ul>
 *     <li>You can avoid a map or maps, e.g., {@code Map&lt;?, Map&lt;?, ?>>}.</li>
 *     <li>You can use a two-element keys, e.g., {@code Map&lt;? extends AbstractTuple&lt;?>, ?>}.</li>
 * </ul>
 * <p>
 * Methods {@link #hashCode()} and {@link #equals(Object)} have correct overrides in this class,
 * making it safe for use in standard Java collections.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        tuple element type; may be {@link Object} for tuples with mixed element types
 *
 * @see UnmodifiableForwardingList
 * @see Tuple
 */
@FullyTested
public abstract class AbstractTuple<TValue>
extends UnmodifiableForwardingList<TValue>
implements Tuple<TValue> {

    private static final AbstractTuple EMPTY = new AbstractTuple<Object>() {};

    static final Joiner2 JOINER =
        Joiner2Utils.INSTANCE.withSeparator(", ")
            .withElementFormatter(AutoFormatter2.INSTANCE)
            .withFinalFormatter(new StringFormatter2("(%s)"));

    /**
     * Returns a shared reference to the global empty tuple.
     *
     * @param <TValue>
     *        may be {@link Object}
     *
     * @return empty tuple
     */
    public static <TValue> AbstractTuple<TValue> empty() {
        @SuppressWarnings("unchecked")
        AbstractTuple<TValue> x = (AbstractTuple<TValue>) EMPTY;
        return x;
    }

    private final TValue[] valueArr;

    private AbstractTuple() {
        this.valueArr = _createEmptyArray();
    }

    private static <TValue> TValue[] _createEmptyArray() {
        @SuppressWarnings("unchecked")
        TValue[] arr = (TValue[]) new Object[0];
        return arr;
    }

    /**
     * Constructs a non-empty tuple.  Advice: Do not provide mutable values.
     * <p>
     * For empty tuples, see {@link #empty()}.
     *
     * @param valueArr
     * <ul>
     *     <li>may contain {@code null} values</li>
     *     <li>must not contain arrays</li>
     *     <li>must not be empty</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code valueArr} is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code valueArr} is zero
     *
     * @see #get(int)
     * @see #empty()
     * @see #AbstractTuple(Tuple)
     */
    protected AbstractTuple(TValue... valueArr) {
        ArrayArgs.checkNotEmpty(valueArr, "valueArr");

        _checkNoArrayValues(valueArr);
        this.valueArr = valueArr.clone();
    }

    private static <TValue> void _checkNoArrayValues(TValue[] valueArr) {
        for (int i = 0; i < valueArr.length; ++i) {
            TValue value = valueArr[i];
            if (null != value && value.getClass().isArray()) {
                throw new IllegalArgumentException(String.format(
                    "Value[%d] is an array type: %s", i, value.getClass().getSimpleName()));
            }
        }
    }

    /**
     * Copies any tuple: empty or not.
     *
     * @param tuple
     *        must not be {@code null}
     *
     * @throws NullPointerException
     *         if {@code tuple} is {@code null}
     *
     * @see #AbstractTuple(Object[])
     */
    protected AbstractTuple(Tuple<TValue> tuple) {
        ObjectArgs.checkNotNull(tuple, "tuple");

        if (0 == tuple.size()) {
            this.valueArr = _createEmptyArray();
        }
        else {
            this.valueArr = tuple.toArray();
            _checkNoArrayValues(valueArr);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final TValue get(int index) {
        ArrayArgs.checkAccessIndex(valueArr, index, "valueArr", "index");

        TValue x = valueArr[index];
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public final int size() {
        int x = valueArr.length;
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public final TValue[] toArray() {
        return valueArr.clone();
    }

    private transient List<TValue> valueList;

    @Override
    protected List<TValue> delegate() {
        if (null == valueList) {
            @SuppressWarnings("unchecked")
            List<TValue> x = Arrays.asList(valueArr);
            valueList = x;
        }
        return valueList;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        String x = JOINER.join(valueArr);
        return x;
    }
}
