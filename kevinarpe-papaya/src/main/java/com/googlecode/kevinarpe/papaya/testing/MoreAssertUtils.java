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
import com.google.common.collect.BiMap;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Assert utilities.  These methods were written to fill a gap left by JUnit and TestNG.  To use the
 * methods in this class create a new instance via {@link #MoreAssertUtils()} or use the public
 * static member {@link #INSTANCE}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see IMoreAssertUtils
 */
@FullyTested
public final class MoreAssertUtils
extends StatelessObject
implements IMoreAssertUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final MoreAssertUtils INSTANCE = new MoreAssertUtils();

    private final MoreAssertUtilsHelper moreAssertUtilsHelper;

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public MoreAssertUtils() {
        this(MoreAssertUtilsHelperImpl.INSTANCE);
    }

    MoreAssertUtils(MoreAssertUtilsHelper moreAssertUtilsHelper) {
        this.moreAssertUtilsHelper =
            ObjectArgs.checkNotNull(moreAssertUtilsHelper, "moreAssertUtilsHelper");
    }

    /** {@inheritDoc */
    @Override
    public <TValue>
    void assertListEquals(
            @Nullable List<TValue> actualList, @Nullable List<? extends TValue> expectedList) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertListEquals(actualList, expectedList, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
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

    /** {@inheritDoc */
    @Override
    public <TValue>
    void assertSetEquals(
            @Nullable Set<TValue> actualSet, @Nullable Set<? extends TValue> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
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

    /** {@inheritDoc */
    @Override
    public <TValue>
    void assertLinkedSetEquals(
            @Nullable Set<TValue> actualSet, @Nullable Set<? extends TValue> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertLinkedSetEquals(actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
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

    /** {@inheritDoc */
    @Override
    public <TValue extends Enum<TValue>>
    void assertEnumSetEquals(
            Class<TValue> valueClass,
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertEnumSetEquals(valueClass, actualSet, expectedSet, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
    @Override
    public <TValue extends Enum<TValue>>
    void assertEnumSetEquals(
            Class<TValue> valueClass,
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertEnumSetEquals(
            valueClass, actualSet, expectedSet, messagePrefixFormat, formatArgArr);
    }

    /** {@inheritDoc */
    @Override
    public <TKey, TValue>
    void assertMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
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

    /** {@inheritDoc */
    @Override
    public <TKey, TValue>
    void assertLinkedMapEquals(
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertLinkedMapEquals(actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
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

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue>
    void assertEnumMapEquals(
            Class<TKey> keyClass,
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertEnumMapEquals(keyClass, actualMap, expectedMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue>
    void assertEnumMapEquals(
            Class<TKey> keyClass,
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertEnumMapEquals(
            keyClass, actualMap, expectedMap, messagePrefixFormat, formatArgArr);
    }

    /** {@inheritDoc */
    @Override
    public <TKey, TValue>
    void assertBiMapEquals(
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertBiMapEquals(actualBiMap, expectedBiMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
    @Override
    public <TKey, TValue>
    void assertBiMapEquals(
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertBiMapEquals(actualBiMap, expectedBiMap, messagePrefixFormat, formatArgArr);
    }

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    void assertEnumBiMapEquals(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertEnumBiMapEquals(
            keyClass, valueClass, actualBiMap, expectedBiMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    void assertEnumBiMapEquals(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertEnumBiMapEquals(
            keyClass, valueClass, actualBiMap, expectedBiMap, messagePrefixFormat, formatArgArr);
    }

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue>
    void assertEnumHashBiMapEquals(
            Class<TKey> keyClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap) {
        final String optionalMessagePrefixFormat = null;
        _coreAssertEnumHashBiMapEquals(
            keyClass, actualBiMap, expectedBiMap, optionalMessagePrefixFormat);
    }

    /** {@inheritDoc */
    @Override
    public <TKey extends Enum<TKey>, TValue>
    void assertEnumHashBiMapEquals(
            Class<TKey> keyClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String messagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(messagePrefixFormat, "messagePrefixFormat");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        _coreAssertEnumHashBiMapEquals(
            keyClass, actualBiMap, expectedBiMap, messagePrefixFormat, formatArgArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private <TValue> void _coreAssertListEquals(
            @Nullable List<TValue> actualList,
            @Nullable List<? extends TValue> expectedList,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualList == expectedList) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "List", actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "List",
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

        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "List", actualIter, expectedIter, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "List", actualList, expectedList, optionalMessagePrefixFormat, formatArgArr);
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
            "Set", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "Set",
            actualSet.size(),
            expectedSet.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertSetContains(
            "Set", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "Set", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
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
            "LinkedSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        final int size =
            moreAssertUtilsHelper.assertSameContainerSize(
                "LinkedSet",
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

        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "LinkedSet", actualIter, expectedIter, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSetContains(
            "LinkedSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "LinkedSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TValue extends Enum<TValue>>
    void _coreAssertEnumSetEquals(
            Class<TValue> valueClass,
            @Nullable Set<TValue> actualSet,
            @Nullable Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualSet == expectedSet) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "EnumSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "EnumSet",
            actualSet.size(),
            expectedSet.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertEnumSetOrder(valueClass, actualSet, "actualSet");
        moreAssertUtilsHelper.assertEnumSetOrder(valueClass, expectedSet, "expectedSet");

        moreAssertUtilsHelper.assertSetContains(
            "EnumSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumSet", actualSet, expectedSet, optionalMessagePrefixFormat, formatArgArr);
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
            "Map", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "Map",
            actualMap.size(),
            expectedMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertMapContains(
            "Map", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "Map", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "Map", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
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
            "LinkedMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        final int size =
            moreAssertUtilsHelper.assertSameContainerSize(
                "LinkedMap",
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

        moreAssertUtilsHelper.assertBothIteratorsDoNotHaveNext(
            "LinkedMap",
            actualEntryIter,
            expectedEntryIter,
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "LinkedMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "LinkedMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey extends Enum<TKey>, TValue>
    void _coreAssertEnumMapEquals(
            Class<TKey> keyClass,
            @Nullable Map<TKey, TValue> actualMap,
            @Nullable Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualMap == expectedMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "EnumMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "EnumMap",
            actualMap.size(),
            expectedMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, actualMap.keySet(), "actualMap.keySet()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, expectedMap.keySet(), "expectedMap.keySet()");

        moreAssertUtilsHelper.assertMapContains(
            "EnumMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "EnumMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumMap", actualMap, expectedMap, optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey, TValue>
    void _coreAssertBiMapEquals(
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualBiMap == expectedBiMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "BiMap", actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "BiMap",
            actualBiMap.size(),
            expectedBiMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertMapContains(
            "BiMap", actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapContains(
            "BiMap", actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "BiMap", actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "BiMap", actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "BiMap", actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "BiMap", actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey extends Enum<TKey>, TValue extends Enum<TValue>>
    void _coreAssertEnumBiMapEquals(
            Class<TKey> keyClass,
            Class<TValue> valueClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualBiMap == expectedBiMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "EnumBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "EnumBiMap" ,
            actualBiMap.size(),
            expectedBiMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, actualBiMap.keySet(), "actualBiMap.keySet()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, expectedBiMap.keySet(), "expectedBiMap.keySet()");

        moreAssertUtilsHelper.assertEnumSetOrder(
            valueClass, actualBiMap.values(), "actualBiMap.values()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            valueClass, expectedBiMap.values(), "expectedBiMap.values()");

        moreAssertUtilsHelper.assertEnumSetOrder(
            valueClass, actualBiMap.inverse().keySet(), "actualBiMap.inverse().keySet()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            valueClass, expectedBiMap.inverse().keySet(), "expectedBiMap.inverse().keySet()");

        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, actualBiMap.inverse().values(), "actualBiMap.inverse().values()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, expectedBiMap.inverse().values(), "expectedBiMap.inverse().values()");

        moreAssertUtilsHelper.assertMapContains(
            "EnumBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapContains(
            "EnumBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "EnumBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "EnumBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);
    }

    private <TKey extends Enum<TKey>, TValue>
    void _coreAssertEnumHashBiMapEquals(
            Class<TKey> keyClass,
            @Nullable BiMap<TKey, TValue> actualBiMap,
            @Nullable BiMap<? extends TKey, ? extends TValue> expectedBiMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        if (actualBiMap == expectedBiMap) {  // also covers 'null == null'
            return;
        }
        moreAssertUtilsHelper.assertNeitherNull(
            "EnumHashBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertSameContainerSize(
            "EnumHashBiMap" ,
            actualBiMap.size(),
            expectedBiMap.size(),
            optionalMessagePrefixFormat,
            formatArgArr);

        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, actualBiMap.keySet(), "actualBiMap.keySet()");
        moreAssertUtilsHelper.assertEnumSetOrder(
            keyClass, expectedBiMap.keySet(), "expectedBiMap.keySet()");

        moreAssertUtilsHelper.assertMapContains(
            "EnumHashBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapContains(
            "EnumHashBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "EnumHashBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertMapEntrySetEquals(
            "EnumHashBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumHashBiMap" , actualBiMap, expectedBiMap, optionalMessagePrefixFormat, formatArgArr);

        moreAssertUtilsHelper.assertEqualsAndHashCodeCorrect(
            "EnumHashBiMap" , actualBiMap.inverse(), expectedBiMap.inverse(),
            optionalMessagePrefixFormat, formatArgArr);
    }
}
