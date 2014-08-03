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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Test me
public final class MoreAssertUtils
extends StatelessObject
implements IMoreAssertUtils {

    public static final MoreAssertUtils INSTANCE =
        new MoreAssertUtils();

    private final MoreAssertUtilsHelper moreAssertUtilsHelper;

    public MoreAssertUtils() {
        this(MoreAssertUtilsHelperImpl.INSTANCE);
    }

    MoreAssertUtils(MoreAssertUtilsHelper moreAssertUtilsHelper) {
        this.moreAssertUtilsHelper =
            ObjectArgs.checkNotNull(moreAssertUtilsHelper, "moreAssertUtilsHelper");
    }

    /**
     * This is a convenience method to call {@link #assertListEquals(List, List, String, Object...)}
     * without an assert message prefix.
     */
    @Override
    public <TValue>
    void assertListEquals(
            @Nullable List<TValue> actualList, @Nullable List<? extends TValue> expectedList) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertListEquals(actualList, expectedList, optionalMessagePrefixFormat);
    }

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
    @Override
    public <TValue>
    void assertListEquals(
            @Nullable List<TValue> actualList,
            @Nullable List<? extends TValue> expectedList,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertListEquals(actualList, expectedList, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TValue>
    void assertSetEquals(
            @Nullable Set<TValue> actualSet, @Nullable Set<? extends TValue> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    @Override
    public <TValue>
    void assertSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertSetEquals(actualSet, expectedSet, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TValue>
    void assertLinkedSetEquals(
            @Nullable Set<TValue> actualSet, @Nullable Set<? extends TValue> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertLinkedSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    @Override
    public <TValue>
    void assertLinkedSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertLinkedSetEquals(actualSet, expectedSet, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;

        _coreAssertMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    @Override
    public <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertMapEquals(actualMap, expectedMap, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;

        _coreAssertLinkedMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    @Override
    public <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertLinkedMapEquals(actualMap, expectedMap, messagePrefixFormat, formatArgArr);
    }

    private <TValue> void _coreAssertListEquals(
            @Nullable List<TValue> actualList,
            @Nullable List<? extends TValue> expectedList,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualList == expectedList) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            List.class, actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            List.class,
            actualList.size(),
            expectedList.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        Iterator<TValue> actualIter = actualList.iterator();
        Iterator<? extends TValue> expectedIter = expectedList.iterator();
        int index = 0;
        while (actualIter.hasNext() && expectedIter.hasNext()) {
            TValue actualValue = actualIter.next();
            TValue expectedValue = expectedIter.next();
            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Lists not equal: Index %d: Actual != Expected:"
                        + "%n\tActual  : '%s'"
                        + "%n\tExpected: '%s'",
                    index, actualValue, expectedValue);
            }
            ++index;
        }

        // TODO: LAST: Fix tests here
        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "List", actualIter, expectedIter, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            List.class, actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TValue>
    void _coreAssertSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualSet == expectedSet) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            Set.class, actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            Set.class,
            actualSet.size(),
            expectedSet.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertSetContains(
            "Set", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Set.class, actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TValue>
    void _coreAssertLinkedSetEquals(
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualSet == expectedSet) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            Set.class, actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        final int size =
            moreAssertUtilsHelper.assertSameContainerSize(
                Set.class,
                actualSet.size(),
                expectedSet.size(),
                optionalMessagePrefixFormat,
                formatArgArr);

        Iterator<TValue> actualIter = actualSet.iterator();
        Iterator<? extends TValue> expectedIter = expectedSet.iterator();
        int count = 0;
        while (expectedIter.hasNext() && actualIter.hasNext()) {
            ++count;
            TValue actualValue = actualIter.next();
            TValue expectedValue = expectedIter.next();

            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "LinkedSets not equal: Entry %d of %d: Actual and expected values are not equal"
                        + "%n\tActual  : '%s'"
                        + "%n\tExpected: '%s'",
                    count, size, actualValue, expectedValue);
            }
        }

        // TODO: LAST: Fix tests here
        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "LinkedSet", actualIter, expectedIter, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSetContains(
            "LinkedSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Set.class, actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey, TValue>
    void _coreAssertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualMap == expectedMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            Map.class, actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            Map.class,
            actualMap.size(),
            expectedMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        for (Map.Entry<? extends TKey, ? extends TValue> expectedEntry : expectedMap.entrySet()) {
            final TKey expectedKey = expectedEntry.getKey();
            if (!actualMap.containsKey(expectedKey)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Maps not equal: Actual does not contain expected key: '%s'",
                    expectedKey);
            }
            final TValue expectedValue = expectedEntry.getValue();
            final TValue actualValue = actualMap.get(expectedKey);
            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Maps not equal: Actual contains expected key, but value is not equal:"
                        + "%n\tKey           : '%s'"
                        + "%n\tActual   value: '%s'"
                        + "%n\tExpected value: '%s'",
                    expectedKey, actualValue, expectedValue);
            }
        }

        for (Map.Entry<TKey, TValue> actualEntry : actualMap.entrySet()) {
            final TKey actualKey = actualEntry.getKey();
            if (!expectedMap.containsKey(actualKey)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Maps not equal: Expected does not contain actual key: '%s'",
                    actualKey);
            }
            final TValue expectedValue = expectedMap.get(actualKey);
            final TValue actualValue = actualEntry.getValue();
            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Maps not equal: Expected contains actual key, but value is not equal:"
                        + "%n\tKey           : '%s'"
                        + "%n\tActual   value: '%s'"
                        + "%n\tExpected value: '%s'",
                    actualKey, actualValue, expectedValue);
            }
        }

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "Map", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Map.class, actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey, TValue>
    void _coreAssertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualMap == expectedMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            Map.class, actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        final int size =
            moreAssertUtilsHelper.assertSameContainerSize(
                Map.class,
                actualMap.size(),
                expectedMap.size(),
                optionalMessagePrefixFormat,
                formatArgArr);

        Iterator<? extends Map.Entry<? extends TKey, ? extends TValue>> expectedEntryIter =
            expectedMap.entrySet().iterator();
        Iterator<Map.Entry<TKey, TValue>> actualEntryIter = actualMap.entrySet().iterator();
        int count = 0;
        while (expectedEntryIter.hasNext() && actualEntryIter.hasNext()) {
            ++count;
            final Map.Entry<TKey, TValue> actualEntry = actualEntryIter.next();
            final TKey actualKey = actualEntry.getKey();
            final TValue actualValue = actualEntry.getValue();

            final Map.Entry<? extends TKey, ? extends TValue> expectedEntry =
                expectedEntryIter.next();
            final TKey expectedKey = expectedEntry.getKey();
            final TValue expectedValue = expectedEntry.getValue();

            if (!Objects.equal(actualKey, expectedKey)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "LinkedMaps not equal: Entry %d of %d: Actual and expected keys are not equal"
                        + "%n\tActual  : '%s'"
                        + "%n\tExpected: '%s'",
                    count, size, actualKey, expectedKey);
            }

            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "LinkedMaps not equal: Entry %d of %d: Actual and expected values are not equal"
                        + "%n\tKey           : '%s'"
                        + "%n\tActual   value: '%s'"
                        + "%n\tExpected value: '%s'",
                    count, size, actualKey, actualValue, expectedValue);
            }
        }

        // TODO: LAST: Fix tests here
        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "LinkedMap",
            actualEntryIter,
            expectedEntryIter,
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "LinkedMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Map.class, actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
    }
}
