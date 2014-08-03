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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class MoreAssertUtilsHelperImpl
extends StatelessObject
implements MoreAssertUtilsHelper {

    public static final MoreAssertUtilsHelperImpl INSTANCE = new MoreAssertUtilsHelperImpl();

    @Override
    public <TBase, TActual extends TBase, TExpected extends TBase>
    void assertEqualsAndHashCodeCorrect(
            Class<TBase> clazz,
            TActual actual,
            TExpected expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {

        if (!actual.equals(expected)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: actual%1$s.equals(expectedList)"
                    + "%n\tProbably equals() has incorrect override definition",
                clazz.getSimpleName());
        }
        if (!expected.equals(actual)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: expected%1$s.equals(actualList)"
                    + "%n\tProbably equals() has incorrect override definition",
                clazz.getSimpleName());
        }
        if (actual.equals(null)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: actual%1$s.equals(null)"
                    + "%n\tProbably equals() has incorrect override definition",
                clazz.getSimpleName());
        }
        if (expected.equals(null)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: Size and all elements are equal, but fails test: expected%1$s.equals(null)"
                    + "%n\tProbably equals() has incorrect override definition",
                clazz.getSimpleName());
        }
        final int actualHashCode = actual.hashCode();
        final int expectedHashCode = expected.hashCode();
        if (actualHashCode != expectedHashCode) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%1$ss not equal: %1$ss pass equals() test, but fail test: actual%1$s.hashCode() != expected%1$s.hashCode()"
                    + "%n\tActual  : %2$d"
                    + "%n\tExpected: %3$d"
                    + "%n\tProbably hashCode() has incorrect override definition",
                clazz.getSimpleName(), actualHashCode, expectedHashCode);
        }
    }

    @Override
    public <TBase, TActual extends TBase, TExpected extends TBase>
    void assertNeitherNull(
            Class<TBase> clazz,
            TActual actual,
            TExpected expected,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {

        if (null == actual && null != expected) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual is null, but expected is not null",
                clazz.getSimpleName());
        }
        if (null != actual && null == expected) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual is not null, but expected is null",
                clazz.getSimpleName());
        }
    }

    @Override
    public int assertSameContainerSize(
            Class<?> containerClass,
            int actualContainerSize,
            int expectedContainerSize,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {

        if (actualContainerSize != expectedContainerSize) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual has size %d, but expected has size %d",
                containerClass.getSimpleName(), actualContainerSize, expectedContainerSize);
        }
        return actualContainerSize;
    }

    @Override
    public void throwAssertionError(
            @Nullable String optionalMessagePrefixFormat,
            Object[] messagePrefixFormatArgArr,
            String format,
            Object... formatArgArr) {

        String msg = String.format(format, formatArgArr);
        if (null != optionalMessagePrefixFormat) {
            String prefix = String.format(optionalMessagePrefixFormat, messagePrefixFormatArgArr);
            msg = String.format("%s:%n%s", prefix, msg);
        }
        throw new AssertionError(msg);
    }

    @Override
    public <TValue>
    void assertSetContains(
            String setDescription,
            Set<TValue> actualSet,
            Set<? extends TValue> expectedSet,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {

        for (TValue expectedValue : expectedSet) {
            if (!actualSet.contains(expectedValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Actual does not contain expected value: '%s'",
                    setDescription, expectedValue);
            }
        }

        for (TValue actualValue : actualSet) {
            if (!expectedSet.contains(actualValue)) {
                throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                    "%ss not equal: Expected does not contain actual value: '%s'",
                    setDescription, actualValue);
            }
        }

        if (!actualSet.containsAll(expectedSet)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Expected set contains each actual value, but fails test: actualSet.containsAll(expectedSet)",
                setDescription);
        }

        if (!expectedSet.containsAll(actualSet)) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual set contains each expected value, but fails test: expectedSet.containsAll(actualSet)",
                setDescription);
        }
    }

    @Override
    public <TKey, TValue>
    void assertMapEntrySetEquals(
            String mapDescription,
            Map<TKey, TValue> actualMap,
            Map<? extends TKey, ? extends TValue> expectedMap,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {

        if (!actualMap.entrySet().equals(expectedMap.entrySet())) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Expected map contains each actual key-value pair, but fails test: actualMap.entrySet().equals(expectedMap.entrySet())",
                mapDescription);
        }

        if (!expectedMap.entrySet().equals(actualMap.entrySet())) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                "%ss not equal: Actual map contains each expected key-value pair, but fails test: expectedMap.entrySet().equals(actualMap.entrySet())",
                mapDescription);
        }
    }

    // TODO: LAST: Test me
    @Override
    public void assertBothIteratorsDoNotHaveNext(
            String containerDescription,
            Iterator<?> actualIter,
            Iterator<?> expectedIter,
            @Nullable String optionalMessagePrefixFormat,
            Object... formatArgArr) {
        final String prefix =
            "%ss not equal: Size is equal, but number of elements by iteration is not equal";
        if (actualIter.hasNext() && !expectedIter.hasNext()) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                prefix + "%n\tActual has more elements than expected",
                containerDescription);
        }
        if (!actualIter.hasNext() && expectedIter.hasNext()) {
            throwAssertionError(optionalMessagePrefixFormat, formatArgArr,
                prefix + "%n\tExpected has more elements than actual",
                containerDescription);
        }
    }
}
