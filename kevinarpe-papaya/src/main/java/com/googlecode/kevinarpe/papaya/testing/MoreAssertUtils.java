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

    @Override
    public <T> void assertListEquals(
            @Nullable List<T> actualList, @Nullable List<? extends T> expectedList) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertListEquals(actualList, expectedList, optionalMessagePrefixFormat);
    }

    @Override
    public <T> void assertListEquals(
            @Nullable List<T> actualList,
            @Nullable List<? extends T> expectedList,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertListEquals(actualList, expectedList, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <T> void assertSetEquals(
            @Nullable Set<T> actualSet, @Nullable Set<? extends T> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    @Override
    public <T> void assertSetEquals(
            @Nullable Set<T> actualSet,
            @Nullable Set<? extends T> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertSetEquals(actualSet, expectedSet, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <T> void assertLinkedSetEquals(
            @Nullable Set<T> actualSet, @Nullable Set<? extends T> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertLinkedSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    @Override
    public <T> void assertLinkedSetEquals(
            @Nullable Set<T> actualSet,
            @Nullable Set<? extends T> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertLinkedSetEquals(actualSet, expectedSet, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TKey, TValue> void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;

        _coreAssertMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    @Override
    public <TKey, TValue> void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertMapEquals(actualMap, expectedMap, messagePrefixFormat, formatArgArr);
    }

    @Override
    public <TKey, TValue> void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;

        _coreAssertLinkedMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    @Override
    public <TKey, TValue> void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertLinkedMapEquals(actualMap, expectedMap, messagePrefixFormat, formatArgArr);
    }

    private <T> void _coreAssertListEquals(
            @Nullable List<T> actualList,
            @Nullable List<? extends T> expectedList,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualList == expectedList) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            List.class, actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);

        final int size =
            moreAssertUtilsHelper.assertSameContainerSize(
                List.class,
                actualList.size(),
                expectedList.size(),
                optionalMessagePrefixFormat,
                formatArgArr);

        for (int i = 0; i < size; ++i) {
            T actualValue = actualList.get(i);
            T expectedValue = expectedList.get(i);
            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "Lists not equal: Index %d: Actual != Expected:"
                        + "%n\tActual  : '%s'"
                        + "%n\tExpected: '%s'",
                    i, actualValue, expectedValue);
            }
        }

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            List.class, actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);
    }

    private <T> void _coreAssertSetEquals(
            @Nullable Set<T> actualSet,
            @Nullable Set<? extends T> expectedSet,
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

    private <T> void _coreAssertLinkedSetEquals(
            @Nullable Set<T> actualSet,
            @Nullable Set<? extends T> expectedSet,
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

        Iterator<T> actualIter = actualSet.iterator();
        Iterator<? extends T> expectedIter = expectedSet.iterator();
        int count = 0;
        while (expectedIter.hasNext() && actualIter.hasNext()) {
            ++count;
            T actualValue = actualIter.next();
            T expectedValue = expectedIter.next();

            if (!Objects.equal(actualValue, expectedValue)) {
                moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "LinkedSets not equal: Entry %d of %d: Actual and expected values are not equal"
                        + "%n\tActual  : '%s'"
                        + "%n\tExpected: '%s'",
                    count, size, actualValue, expectedValue);
            }
        }

        if (expectedIter.hasNext() != actualIter.hasNext()) {
            moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "LinkedSets not equal: Size is equal, but number of elements by iteration is not equal");
        }

        moreAssertUtilsHelper.assertSetContains(
            "LinkedSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Set.class, actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey, TValue> void _coreAssertMapEquals(
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

    private <TKey, TValue> void _coreAssertLinkedMapEquals(
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

        if (expectedEntryIter.hasNext() != actualEntryIter.hasNext()) {
            moreAssertUtilsHelper.throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "LinkedMaps not equal: Size is equal, but number of elements by iteration is not equal");
        }

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "LinkedMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            Map.class, actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
    }
}
