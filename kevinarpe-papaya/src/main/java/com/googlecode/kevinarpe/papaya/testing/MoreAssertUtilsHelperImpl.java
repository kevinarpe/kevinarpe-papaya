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
import com.google.common.collect.ObjectArrays;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.string.joiner.Joiner2;
import com.googlecode.kevinarpe.papaya.string.joiner.Joiner2Utils;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.EnumFormatter2;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
//@FullyTested
final class MoreAssertUtilsHelperImpl
extends StatelessObject
implements MoreAssertUtilsHelper {

    public static final MoreAssertUtilsHelperImpl INSTANCE = new MoreAssertUtilsHelperImpl();

    @Override
    public void assertEqualsAndHashCodeCorrect(
            String classSimpleName,
            Object actual,
            Object expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actual, "actual");
        ObjectArgs.checkNotNull(expected, "expected");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        if (!actual.equals(expected)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: actual%1$s.equals(expectedList)"
                    + "%n\tProbably equals() has incorrect override definition",
                classSimpleName);
        }
        if (!expected.equals(actual)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: expected%1$s.equals(actualList)"
                    + "%n\tProbably equals() has incorrect override definition",
                classSimpleName);
        }
        if (actual.equals(null)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: actual%1$s.equals(null)"
                    + "%n\tProbably equals() has incorrect override definition",
                classSimpleName);
        }
        if (expected.equals(null)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: expected%1$s.equals(null)"
                    + "%n\tProbably equals() has incorrect override definition",
                classSimpleName);
        }
        final int actualHashCode = actual.hashCode();
        final int expectedHashCode = expected.hashCode();
        if (actualHashCode != expectedHashCode) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: %1$ss pass equals() test, but fail test: actual%1$s.hashCode() != expected%1$s.hashCode()"
                    + "%n\tActual  : %2$d"
                    + "%n\tExpected: %3$d"
                    + "%n\tProbably hashCode() has incorrect override definition",
                classSimpleName, actualHashCode, expectedHashCode);
        }
    }

    @Override
    public void assertNeitherNull(
            String classSimpleName,
            Object actual,
            Object expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actual, "actual");
        ObjectArgs.checkNotNull(expected, "expected");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        if (null == actual && null != expected) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual is null, but expected is not null",
                classSimpleName);
        }
        if (null != actual && null == expected) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual is not null, but expected is null",
                classSimpleName);
        }
    }

    @Override
    public int assertSameContainerSize(
            String classSimpleName,
            int actualContainerSize,
            int expectedContainerSize,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        IntArgs.checkNotNegative(actualContainerSize, "actualContainerSize");
        IntArgs.checkNotNegative(expectedContainerSize, "expectedContainerSize");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        if (actualContainerSize != expectedContainerSize) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual has size %d, but expected has size %d",
                classSimpleName, actualContainerSize, expectedContainerSize);
        }
        return actualContainerSize;
    }

    @Override
    public void throwAssertionError(
            @Nullable String optionalMessagePrefixFormat,
            Object[] messagePrefixFormatArgArr,
            String format,
            Object... formatArgArr) {
        ObjectArgs.checkNotNull(messagePrefixFormatArgArr, "messagePrefixFormatArgArr");
        StringArgs.checkNotEmptyOrWhitespace(format, "format");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        String msg = String.format(format, formatArgArr);
        if (null != optionalMessagePrefixFormat) {
            String prefix = String.format(optionalMessagePrefixFormat, messagePrefixFormatArgArr);
            msg = String.format("%s:%n%s", prefix, msg);
        }
        throw new AssertionError(msg);
    }

    private static final Joiner2 JOINER =
        Joiner2Utils.INSTANCE
            .withSeparator(", ")
            .useForNoElements("<empty> (internal error)")
            .withElementFormatter(EnumFormatter2.INSTANCE);

    // TODO: Test me
    @Override
    public <TValue extends Enum<TValue>>
    void assertEnumSetOrder(
            Class<TValue> valueClass, Set<? extends TValue> enumSet, String argName) {
        ObjectArgs.checkNotNull(valueClass, "valueClass");
        CollectionArgs.checkElementsNotNull(enumSet, "enumSet");
        StringArgs.checkNotEmptyOrWhitespace(argName, "argName");

        TValue[] allEnumArr = valueClass.getEnumConstants();
        int allEnumArrIndex = 0;
        for (TValue anEnum : enumSet) {
            final int allEnumArrIndex0 = allEnumArrIndex;
            TValue thisEnum = null;
            do {
                // This block functions like Iterator.hasNext()
                if (allEnumArrIndex == allEnumArr.length) {
                    TValue[] triedEnumArr =
                        ObjectArrays.newArray(valueClass, allEnumArr.length - allEnumArrIndex0);
                    throw new IllegalArgumentException(String.format(
                        "EnumSet '%s' has incorrect order: Unexpected value %s; Expected one of: %s"
                        + "%n\tEnumSets are expected to have order matching Enum.ordinal()",
                        argName,
                        anEnum.name(),
                        JOINER.join(triedEnumArr)));
                }
                // These two lines function like Iterator.next()
                thisEnum = allEnumArr[allEnumArrIndex];
                ++allEnumArrIndex;
            }
            while (!thisEnum.equals(anEnum));
        }
    }

    @Override
    public <TValue>
    void assertSetContains(
            String classSimpleName,
            Set<TValue> actualSet,
            Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actualSet, "actualSet");
        ObjectArgs.checkNotNull(expectedSet, "expectedSet");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        for (TValue expectedValue : expectedSet) {
            if (!actualSet.contains(expectedValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Actual does not contain expected value: '%s'",
                    classSimpleName, expectedValue);
            }
        }

        for (TValue actualValue : actualSet) {
            if (!expectedSet.contains(actualValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Expected does not contain actual value: '%s'",
                    classSimpleName, actualValue);
            }
        }

        if (!actualSet.containsAll(expectedSet)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Expected set contains each actual value, but fails test: actual%1$s.containsAll(expected%1$s)",
                classSimpleName);
        }

        if (!expectedSet.containsAll(actualSet)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Actual set contains each expected value, but fails test: expected%1$s.containsAll(actual%1$s)",
                classSimpleName);
        }
    }

    @Override
    public <TKey, TValue>
    void assertMapContains(
            String classSimpleName,
            Map<TKey, TValue> actualMap,
            Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actualMap, "actualMap");
        ObjectArgs.checkNotNull(expectedMap, "expectedMap");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        for (Map.Entry<? extends TKey, ? extends TValue> expectedEntry : expectedMap.entrySet()) {
            final TKey expectedKey = expectedEntry.getKey();
            if (!actualMap.containsKey(expectedKey)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Actual does not contain expected key: '%s'",
                    classSimpleName, expectedKey);
            }
            final TValue expectedValue = expectedEntry.getValue();
            final TValue actualValue = actualMap.get(expectedKey);
            if (!Objects.equal(actualValue, expectedValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Actual contains expected key, but value is not equal:"
                        + "%n\tKey           : '%s'"
                        + "%n\tActual   value: '%s'"
                        + "%n\tExpected value: '%s'",
                    classSimpleName, expectedKey, actualValue, expectedValue);
            }
        }

        for (Map.Entry<TKey, TValue> actualEntry : actualMap.entrySet()) {
            final TKey actualKey = actualEntry.getKey();
            if (!expectedMap.containsKey(actualKey)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Expected does not contain actual key: '%s'",
                    classSimpleName, actualKey);
            }
            final TValue expectedValue = expectedMap.get(actualKey);
            final TValue actualValue = actualEntry.getValue();
            if (!Objects.equal(actualValue, expectedValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Expected contains actual key, but value is not equal:"
                        + "%n\tKey           : '%s'"
                        + "%n\tActual   value: '%s'"
                        + "%n\tExpected value: '%s'",
                    classSimpleName, actualKey, actualValue, expectedValue);
            }
        }
    }

    @Override
    public <TKey, TValue>
    void assertMapEntrySetEquals(
            String classSimpleName,
            Map<TKey, TValue> actualMap,
            Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actualMap, "actualMap");
        ObjectArgs.checkNotNull(expectedMap, "expectedMap");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        if (!actualMap.entrySet().equals(expectedMap.entrySet())) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Expected map contains each actual key-value pair, but fails test: actual%1$s.entrySet().equals(expected%1$s.entrySet())",
                classSimpleName);
        }

        if (!expectedMap.entrySet().equals(actualMap.entrySet())) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Actual map contains each expected key-value pair, but fails test: expected%1$s.entrySet().equals(actual%1$s.entrySet())",
                classSimpleName);
        }
    }

    @Override
    public void assertBothIteratorsDoNotHaveNext(
            String classSimpleName,
            Iterator<?> actualIter,
            Iterator<?> expectedIter,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        StringArgs.checkNotEmptyOrWhitespace(classSimpleName, "classSimpleName");
        ObjectArgs.checkNotNull(actualIter, "actualIter");
        ObjectArgs.checkNotNull(expectedIter, "expectedIter");
        ObjectArgs.checkNotNull(formatArgArr, "formatArgArr");

        final String prefix =
            "%ss not equal: Size is equal, but number of elements by iteration is not equal";
        if (actualIter.hasNext() && !expectedIter.hasNext()) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                prefix + "%n\tActual has more elements than expected",
                classSimpleName);
        }
        if (!actualIter.hasNext() && expectedIter.hasNext()) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                prefix + "%n\tExpected has more elements than actual",
                classSimpleName);
        }
    }
}
