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

import java.util.Set;

/**
 * Builds sets.  Implementations are always required implement the {@link Set} interface.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @param <TSet>
 *        type of set to build: extends Set&lt;TValue>
 * @param <TValue>
 *        type of value for set elements
 *
 * @see Set
 * @see Builder
 * @see HashSetBuilder
 * @see LinkedHashSetBuilder
 * @see ImmutableSetBuilder
 */
public interface SetBuilder
    <
        TValue,
        TSet extends Set<TValue>,
        TSelf extends SetBuilder<TValue, TSet, TSelf>
    >
extends Set<TValue>, CollectionBuilder<TValue, TSet, TSelf> {
}
