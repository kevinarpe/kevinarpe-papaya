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
 * @param <TCollection>
 *        type of collection to build: extends Collection&lt;TValue>
 * @param <TCollectionBuilder>
 *        type of builder to create: extends Builder&lt;TCollection>
 *
 * @see ListFactory
 * @see SetFactory
 */
public interface CollectionFactory
    <
        TValue,
        TCollection extends Collection<TValue>,
        TCollectionBuilder extends Builder<TCollection>
    >
extends ContainerFactory<TValue, TCollection, TCollectionBuilder> {
}