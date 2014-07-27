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

import java.util.Collection;

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
public interface ContainerFactory
    <
        TValue,
        TContainer,
        TContainerBuilder extends Builder<TContainer>
    >
extends BuilderFactory<TContainerBuilder> {

    /**
     * Creates a new collection initialised with an array of elements.
     *
     * @param elementArr
     *        may be empty, but must not be {@code null}
     *
     * @return new collection with elements from {@code elementArr}
     *
     * @throws NullPointerException
     *         if {@code elementArr} is {@code null}
     */
    TContainer copyOf(TValue... elementArr);

    /**
     * Copies a collection.
     * <p>
     * Why are the inputs {@link Iterable} instead of {@link Collection}?
     * <br/>Ref: <a href="http://stackoverflow.com/a/1159871/257299"
     * >http://stackoverflow.com/a/1159871/257299</a>
     *
     * @param iterable
     *        may be empty, but must not be {@code null}
     *
     * @return copy of {@code collection}
     *
     * @throws NullPointerException
     *         if {@code collection} is {@code null}
     */
    TContainer copyOf(Iterable<? extends TValue> iterable);
}
