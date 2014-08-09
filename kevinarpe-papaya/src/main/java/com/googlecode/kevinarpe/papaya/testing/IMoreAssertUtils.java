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

import com.google.common.collect.BiMap;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
     * Compares two lists for equality.  Null lists are handled correctly.  Methods
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
     * Compares two sets for equality.  Null sets are handled correctly.  Methods
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
     * Compares two sets for equality.  Null sets are handled correctly.  Methods
     * {@link Set#equals(Object)} and {@link Set#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test.  For non-linked sets, see
     * {@link #assertSetEquals(Set, Set, String, Object...)}.  If unfamiliar with linked sets, see
     * {@link LinkedHashSet}.
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
     * This is a convenience method to call
     * {@link #assertEnumSetEquals(Class, Set, Set, String, Object...)} without an assert
     * message prefix.
     */
    <TValue extends Enum<TValue>>
    void assertEnumSetEquals(
            Class<TValue> valueClass,
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet);

    /**
     * Compares two sets for equality.  Null sets are handled correctly.  Methods
     * {@link Set#equals(Object)} and {@link Set#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test; values must follow order of method
     * {@link Enum#ordinal()}.  If unfamiliar with enum sets, see {@link EnumSet}.
     *
     * @param valueClass
     *        enum class for values
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
     *         if {@code valueClass}, {@code messagePrefixFormat}, or {@code formatArgArr} is
     *         {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if sets are not equal
     */
    <TValue extends Enum<TValue>>
    void assertEnumSetEquals(
            Class<TValue> valueClass,
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
     * Compares two maps for equality.  Null maps are handled correctly.  Methods
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
     *
     * @throws NullPointerException
     *         if {@code messagePrefixFormat} or {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if maps are not equal
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
     * Compares two maps for equality.  Null maps are handled correctly.  Methods
     * {@link Map#equals(Object)} and {@link Map#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test.  For non-linked maps, see
     * {@link #assertMapEquals(Map, Map, String, Object...)}.  If unfamiliar with linked maps, see
     * {@link LinkedHashMap}.
     *
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
     *
     * @throws NullPointerException
     *         if {@code messagePrefixFormat} or {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if maps are not equal
     */
    <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertEnumMapEquals(Class, Map, Map, String, Object...)} without an assert
     * message prefix.
     */
    <TKey extends Enum<TKey>, TValue>
    void assertEnumMapEquals(
            Class<TKey> keyClass,
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap);

    /**
     * Compares two maps for equality.  Null maps are handled correctly.  Methods
     * {@link Map#equals(Object)} and {@link Map#hashCode()} are also tested.
     * <p>
     * Order of iteration is important in this test; keys must follow order of method
     * {@link Enum#ordinal()}.  If unfamiliar with enum maps, see {@link EnumMap}.
     *
     * @param keyClass
     *        enum class for keys
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
     *
     * @throws NullPointerException
     *         if {@code keyClass}, {@code messagePrefixFormat}, or {@code formatArgArr} is
     *         {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if maps are not equal
     */
    <TKey extends Enum<TKey>, TValue>
    void assertEnumMapEquals(
            Class<TKey> keyClass,
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertBiMapEquals(BiMap, BiMap, String, Object...)} without an assert message prefix.
     */
    <TKey, TValue>
    void assertBiMapEquals(
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap);

    /**
     * Compares two bi-directional maps for equality.  Null maps are handled correctly.  Methods
     * {@link BiMap#equals(Object)} and {@link BiMap#hashCode()} are also tested.
     * <p>
     * No assumptions are made about order of iteration.
     *
     * @param actualBiMap
     *        may be {@code null} or empty
     * @param expectedBiMap
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
     *         if maps are not equal
     */
    <TKey, TValue>
    void assertBiMapEquals(
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertEnumBiMapEquals(Class, Class, BiMap, BiMap, String, Object...)} without an
     * assert message prefix.
     */
    <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    void assertEnumBiMapEquals(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap);

    /**
     * Compares two bi-directional maps for equality where keys and values are enums.  Null maps are
     * handled correctly.  Methods {@link BiMap#equals(Object)} and {@link BiMap#hashCode()} are
     * also tested.
     * <p>
     * Order of iteration is important in this test; both keys and values must follow order of
     * respective {@link Enum#ordinal()} methods.  If unfamiliar with enum maps, see
     * {@link EnumMap}.
     *
     * @param keyClass
     *        enum class for keys
     * @param valueClass
     *        enum class for values
     * @param actualBiMap
     *        may be {@code null} or empty
     * @param expectedBiMap
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     *
     * @throws NullPointerException
     *         if {@code keyClass}, {@code valueClass}, {@code messagePrefixFormat}, or
     *         {@code formatArgArr} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if maps are not equal
     */
    <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    void assertEnumBiMapEquals(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);

    /**
     * This is a convenience method to call
     * {@link #assertEnumHashBiMapEquals(Class, BiMap, BiMap, String, Object...)} without an assert
     * message prefix.
     */
    <TKey extends Enum<TKey>, TValue>
    void assertEnumHashBiMapEquals(
            Class<TKey> keyClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap);

    /**
     * Compares two bi-directional maps for equality where keys are enums.  Null maps are handled
     * correctly.  Methods {@link BiMap#equals(Object)} and {@link BiMap#hashCode()} are
     * also tested.
     * <p>
     * Order of iteration is important in this test; keys must follow order of method
     * {@link Enum#ordinal()}.  If unfamiliar with enum maps, see {@link EnumMap}.
     *
     * @param keyClass
     *        enum class for keys
     * @param actualBiMap
     *        may be {@code null} or empty
     * @param expectedBiMap
     *        may be {@code null} or empty
     * @param messagePrefixFormat
     *        assert message prefix: used as format for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, empty or only whitespace.
     * @param formatArgArr
     *        assert message prefix: used as arg array for {@link String#format(String, Object...)}.
     *        Must not be {@code null}, but may be empty or contain {@code null} elements.
     *
     * @throws NullPointerException
     *         if {@code keyClass}, {@code messagePrefixFormat}, or {@code formatArgArr} is
     *         {@code null}
     * @throws IllegalArgumentException
     *         if {@code messagePrefixFormat} is empty or only whitespace
     * @throws AssertionError
     *         if maps are not equal
     */
    <TKey extends Enum<TKey>, TValue>
    void assertEnumHashBiMapEquals(
            Class<TKey> keyClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr);
}
