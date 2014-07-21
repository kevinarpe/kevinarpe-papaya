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

import com.google.common.collect.Collections2;
import com.googlecode.kevinarpe.papaya.container.builder.ListBuilder;
import com.googlecode.kevinarpe.papaya.container.builder.ListBuilderFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helper methods for {@code Collection} instances.  Named {@code Collection3} to prevent name clash
 * with {@link Collections} and {@link Collections2}.
 * <p>
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link Collections3} or
 * {@link Collections3#INSTANCE} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Collections3
 */
public interface ICollections3 {

    <
        TValue extends Comparable<? super TValue>,
        TInputCollection extends Collection<? extends TValue>,
        TOutputList extends List<TValue>,
        TListBuilder extends ListBuilder<TValue, TOutputList, TListBuilder>,
        TListBuilderFactory extends ListBuilderFactory<TValue, TOutputList, TListBuilder>
    >
    TOutputList sort(TInputCollection collection, TListBuilderFactory listBuilderFactory);

    <
        TValue extends Comparable<? super TValue>,
        TInputCollection extends Collection<? extends TValue>,
        TOutputList extends List<TValue>,
        TListBuilder extends ListBuilder<TValue, TOutputList, TListBuilder>,
        TListBuilderFactory extends ListBuilderFactory<TValue, TOutputList, TListBuilder>
        >
    TOutputList sort(
            TInputCollection collection,
            Comparator<? super TValue> comparator,
            TListBuilderFactory listBuilderFactory);
}
