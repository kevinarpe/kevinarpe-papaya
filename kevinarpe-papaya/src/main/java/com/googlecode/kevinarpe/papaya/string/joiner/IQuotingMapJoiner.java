package com.googlecode.kevinarpe.papaya.string.joiner;

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

import java.util.Iterator;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IQuotingMapJoiner<TSelf extends IQuotingMapJoiner<TSelf>>
extends QuotingMapJoinerSettings<TSelf> {

    StringBuilder appendTo(StringBuilder builder, Map<?, ?> map);

    // TODO: Add (1) two or more, (2) array
    StringBuilder appendTo(
        StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> partIterable);

    StringBuilder appendTo(
        StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> partIter);

    String join(Map<?, ?> map);

    String join(Iterable<? extends Map.Entry<?, ?>> partIterable);

    String join(Iterator<? extends Map.Entry<?, ?>> partIter);
}
