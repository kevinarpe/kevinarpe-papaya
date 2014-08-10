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

import java.util.Iterator;

/**
 * Base interface for all container builders.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of container element
 * @param <TContainer>
 *        type of container (to hold zero or more elements of type {@code TValue})
 * @param <TSelf>
 *        see <a href="http://en.wikipedia.org/wiki/Curiously_recurring_template_pattern"
 *        >curiously recurring template pattern (CRTP)</a>
 *
 * @see Builder
 */
public interface ContainerBuilder
    <
        TValue,
        TContainer,
        TSelf extends ContainerBuilder<TValue, TContainer, TSelf>
    >
extends Builder<TContainer> {

    /**
     * Adds many values to the builder.
     *
     * @param value
     *        may be {@code null} for many containers (except {@link ImmutableList}, etc.)
     * @param moreValueArr
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return reference to self
     *
     * @throws NullPointerException
     *         if {@code moreValueArr} is {@code null}
     */
    TSelf addMany(TValue value, TValue... moreValueArr);

    /**
     * Adds many values to the builder.
     *
     * @param valueArr
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return reference to self
     *
     * @throws NullPointerException
     *         if {@code valueArr} is {@code null}
     */
    TSelf addMany(TValue[] valueArr);

    /**
     * Adds many values to the builder.
     *
     * @param valueIterable
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return reference to self
     *
     * @throws NullPointerException
     *         if {@code valueIterable} is {@code null}
     */
    TSelf addMany(Iterable<? extends TValue> valueIterable);

    /**
     * Adds many values to the builder.  The iterator is exhausted upon return.
     *
     * @param valueIter
     *        must not be {@code null}, but may be empty or contain {@code null} for many containers
     *        (except {@link ImmutableList}, etc.)
     *
     * @return reference to self
     *
     * @throws NullPointerException
     *         if {@code valueIterable} is {@code null}
     */
    TSelf addMany(Iterator<? extends TValue> valueIter);
}
