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
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link MoreAssertUtils} or {@link MoreAssertUtils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see MoreAssertUtils
 */
public interface IMoreAssertUtils {

    /**
     * This is a convenience method to call {@link #assertListEquals(List, List, String, Object...)}
     * without an assert message prefix.
     */
    <TValue>
    void assertListEquals(@Nullable List<TValue> actual, @Nullable List<? extends TValue> expected);

    /**
     * Compares two lists for equality.  Nulls are handled correctly.  Methods
     * {@link List#equals(Object)} and {@link List#hashCode()} are also tested.
     *
     * @param actualList
     *        may be {@code null} or empty
     * @param expectedList
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     *
     * @throws NullPointerException
     *         if {@code messagePrefixFormat} or {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if lists are not equal
     */
    <TValue>
    void assertListEquals(
            @Nullable List<TValue> actualList,
            @Nullable List<? extends TValue> expectedList,
            String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call {@link #assertSetEquals(Set, Set, String, Object...)}
     * without an assert message prefix.
     */
    <TValue>
    void assertSetEquals(@Nullable Set<TValue> actual, @Nullable Set<? extends TValue> expected);

    /**
     * Compares two sets for equality.  Nulls are handled correctly.  Methods
     * {@link Set#equals(Object)} and {@link Set#hashCode()} are also tested.
     * <p>
     * No assumptions are made about order of iteration.  For linked sets, see
     * {@link #assertLinkedSetEquals(Set, Set, String, Object...)}.
     *
     * @param actualSet
     *        may be {@code null} or empty
     * @param expectedSet
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     *
     * @throws NullPointerException
     *         if {@code messagePrefixFormat} or {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if sets are not equal
     */
    <TValue>
    void assertSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertLinkedSetEquals(Set, Set, String, Object...)} without an assert message prefix.
     */
    <TValue>
    void assertLinkedSetEquals(
            @Nullable Set<TValue> actual, @Nullable Set<? extends TValue> expected);

    /**
     * Compares two sets for equality.  Nulls are handled correctly.  Methods
     * {@link Set#equals(Object)} and {@link Set#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test.  For non-linked sets, see
     * {@link #assertSetEquals(Set, Set, String, Object...)}.
     *
     * @param actualSet
     *        may be {@code null} or empty
     * @param expectedSet
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     *
     * @throws NullPointerException
     *         if {@code messagePrefixFormat} or {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if sets are not equal
     */
    <TValue>
    void assertLinkedSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call {@link #assertMapEquals(Map, Map, String, Object...)}
     * without an assert message prefix.
     */
    <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap);

    /**
     * Compares two maps for equality.  Nulls are handled correctly.  Methods
     * {@link Map#equals(Object)} and {@link Map#hashCode()} are also tested.
     * <p>
     * No assumptions are made about order of iteration.  For linked maps, see
     * {@link #assertLinkedMapEquals(Map, Map, String, Object...)}.
     *
     * @param actualMap
     *        may be {@code null} or empty
     * @param expectedMap
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     */
    <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertLinkedMapEquals(Map, Map, String, Object...)} without an assert message prefix.
     */
    <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap);

    /**
     * Compares two maps for equality.  Nulls are handled correctly.  Methods
     * {@link Map#equals(Object)} and {@link Map#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test.  For non-linked sets, see
     * {@link #assertMapEquals(Map, Map, String, Object...)}.
     *
     * @param actualMap
     *        may be {@code null} or empty
     * @param expectedMap
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     */
    <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);
}
