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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IQuotingJoiner
    <
        TSelf extends IQuotingJoiner<TSelf, TQuotingMapJoiner>,
        TQuotingMapJoiner extends IQuotingMapJoiner<TQuotingMapJoiner>
    >
extends QuotingJoinerSettings<TSelf, TQuotingMapJoiner> {

//    <TAppendable extends Appendable>
//    TAppendable appendTo(
//        TAppendable appendable,
//        Object value1,
//        Object value2,
//        Object... valueArr)
//    throws IOException;
//
//    <TAppendable extends Appendable>
//    TAppendable appendTo(TAppendable appendable, Object[] partArr)
//    throws IOException;
//
//    <TAppendable extends Appendable>
//    TAppendable appendTo(TAppendable appendable, Iterable<?> partIterable)
//    throws IOException;
//
//    <TAppendable extends Appendable>
//    TAppendable appendTo(TAppendable appendable, Iterator<?> partIter)
//    throws IOException;

    StringBuilder appendTo(
        StringBuilder builder,
        Object value1,
        Object value2,
        Object... valueArr);

    StringBuilder appendTo(StringBuilder builder, Object[] partArr);

    StringBuilder appendTo(StringBuilder builder, Iterable<?> partIterable);

    StringBuilder appendTo(StringBuilder builder, Iterator<?> partIter);

    String join(Object value1, Object value2, Object... valueArr);

    String join(Object[] partArr);

    String join(Iterable<?> partIterable);

    String join(Iterator<?> partIter);
}
