package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Base interface for all collection builder factories.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for collection
 * @param <TContainer>
 *        type of collection to build: extends Collection&lt;TValue>
 * @param <TContainerBuilder>
 *        type of builder to create: extends Builder&lt;TCollection>
 *
 * @see ListFactory
 * @see SetFactory
 */
public interface ContainerFactory2
    <
        TValue,
        TContainer,
        TContainerBuilder extends Builder<TContainer>
    >
extends BuilderFactory<TContainerBuilder> {

    /**
     * Creates a new empty collection.  For immutable collections ({@link ImmutableList}, etc.), a
     * shared copy may be returned, but for mutable collections ({@link ArrayList}, etc.), a new
     * instance always created and returned.
     */
    TContainer of();
    TContainer of(TValue v);
    TContainer of(TValue v, TValue v2);
    TContainer of(TValue v, TValue v2, TValue v3);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10, TValue v11);
    TContainer of(TValue v, TValue v2, TValue v3, TValue v4, TValue v5, TValue v6, TValue v7, TValue v8, TValue v9, TValue v10, TValue v11, TValue v12, TValue... moreValueArr);

    /**
     * Creates a new collection with one or more elements.
     *
     * @param value
     *        may be {@code null} for many containers (except {@link ImmutableList}, etc.)
     * @param moreValueArr
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return new collection
     *
     * @throws NullPointerException
     *         if {@code moreValueArr} is {@code null}
     */
//    TContainer copyOf(TValue value, TValue... moreValueArr);

    /**
     * Creates a new collection with zero or more elements.
     *
     * @param valueArr
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return new collection
     *
     * @throws NullPointerException
     *         if {@code valueArr} is {@code null}
     */
    TContainer copyOf(TValue[] valueArr);

    /**
     * Copies a collection.
     * <p>
     * Why are the inputs {@link Iterable} instead of {@link Collection}?
     * <br/>Ref: <a href="http://stackoverflow.com/a/1159871/257299"
     * >http://stackoverflow.com/a/1159871/257299</a>
     *
     * @param iterable
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return copy of {@code collection}
     *
     * @throws NullPointerException
     *         if {@code collection} is {@code null}
     */
    TContainer copyOf(Iterable<? extends TValue> iterable);

    /**
     * Creates a new collection with zero or more elements.  The iterator is exhausted upon return.
     *
     * @param iter
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return new collection
     *
     * @throws NullPointerException
     *         if {@code iter} is {@code null}
     */
    TContainer copyOf(Iterator<? extends TValue> iter);
}
