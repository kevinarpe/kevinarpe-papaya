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

import java.util.List;

/**
 * Builds lists.  Implementations are always required implement the {@link List} interface.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TValue>
 *        type of value for list elements
 * @param <TList>
 *        type of list to build: extends List&lt;TValue>
 *
 * @see List
 * @see Builder
 * @see ArrayListBuilder
 * @see LinkedListBuilder
 * @see ImmutableListBuilder
 */
public interface ListBuilder
    <
        TValue,
        TList extends List<TValue>,
        TSelf extends ListBuilder<TValue, TList, TSelf>
    >
extends List<TValue>, CollectionBuilder<TValue, TList, TSelf> {
}