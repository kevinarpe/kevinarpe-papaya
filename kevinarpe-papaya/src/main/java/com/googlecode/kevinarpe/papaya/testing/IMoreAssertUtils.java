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
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface IMoreAssertUtils {

    <T>
    void assertListEquals(@Nullable List<T> actual, @Nullable List<? extends T> expected);

    <T>
    void assertListEquals(
            @Nullable List<T> actual,
            @Nullable List<? extends T> expected,
            String messagePrefixFormat,
            Object... formatArgArr);

    <T>
    void assertSetEquals(@Nullable Set<T> actual, @Nullable Set<? extends T> expected);

    <T>
    void assertSetEquals(
            @Nullable Set<T> actual,
            @Nullable Set<? extends T> expected,
            String messagePrefixFormat,
            Object... formatArgArr);

    <T>
    void assertLinkedSetEquals(
            @Nullable Set<T> actual, @Nullable Set<? extends T> expected);

    <T>
    void assertLinkedSetEquals(
            @Nullable Set<T> actual,
            @Nullable Set<? extends T> expected,
            String messagePrefixFormat,
            Object... formatArgArr);

    <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap);

    <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap);

    <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);
}
