package com.googlecode.kevinarpe.papaya.testing;

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

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
interface MoreAssertUtilsHelper {

    <TBase, TActual extends TBase, TExpected extends TBase>
    void assertEqualsAndHashCodeCorrect(
            Class<TBase> clazz,
            TActual actual,
            TExpected expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);

    <TBase, TActual extends TBase, TExpected extends TBase>
    void assertNeitherNull(
            Class<TBase> clazz,
            TActual actual,
            TExpected expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);

    int assertSameContainerSize(
            Class<?> containerClass,
            int actualContainerSize,
            int expectedContainerSize,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);

    void throwAssertionError(
            @Nullable String optionalMessagePrefixFormat,
            Object[] messagePrefixFormatArgArr,
            String format,
            Object... formatArgArr);

    <TValue>
    void assertSetContains(
            String setDescription,
            Set<TValue> actualSet,
            Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);

    <TKey, TValue>
    void assertMapEntrySetEquals(
            String mapDescription,
            Map<TKey, TValue> actualMap,
            Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);

    void assertBothIteratorsDoNotHaveNext(
            String containerDescription,
            Iterator<?> actualIter,
            Iterator<?> expectedIter,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr);
}
