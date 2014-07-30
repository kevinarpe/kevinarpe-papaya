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

import com.google.common.collect.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class MoreAssertUtilsTest {

    private static final String FORMAT = "%s:%d";
    private static final Object[] FORMAT_ARG_ARR = new Object[]{"abc", 123};
    private static final String PREFIX;

    static {
        PREFIX = String.format(FORMAT, FORMAT_ARG_ARR);
    }

    private MoreAssertUtilsHelper mockMoreAssertUtilsHelper;

    private MoreAssertUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMoreAssertUtilsHelper = mock(MoreAssertUtilsHelper.class);

        classUnderTest = new MoreAssertUtils(mockMoreAssertUtilsHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertListEquals(List, List)
    // MoreAssertUtils.assertListEquals(List, List, String, Object...)
    //

    @Test
    public void assertListEquals_PassWhenSame() {
        List<String> list = Lists.newArrayList("abc");
        _coreAssertListEquals(list, list);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertNeitherNull_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc");
        AssertionError anAssertionError = new AssertionError();
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertNeitherNull(List.class, actualList, expectedList, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertNeitherNull(List.class, actualList, expectedList, FORMAT, FORMAT_ARG_ARR);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc");
        AssertionError anAssertionError = new AssertionError();
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), FORMAT, FORMAT_ARG_ARR);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhenListsDifferent() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("def");
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), null))
            .thenReturn(actualList.size());
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), FORMAT, FORMAT_ARG_ARR))
            .thenReturn(actualList.size());
        AssertionError anAssertionError = new AssertionError();
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .throwAssertionError(eq((String) null), eq(new Object[0]), anyString(), anyVararg());
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .throwAssertionError(eq(FORMAT), eq(FORMAT_ARG_ARR), anyString(), anyVararg());
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList();
        List<String> expectedList = Lists.newArrayList();
        AssertionError anAssertionError = new AssertionError();
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertEqualsAndHashCodeCorrect(
                List.class, actualList, expectedList, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertEqualsAndHashCodeCorrect(
                List.class, actualList, expectedList, FORMAT, FORMAT_ARG_ARR);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test
    public void assertListEquals_PassWhenEquals() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc");
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), null))
            .thenReturn(actualList.size());
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                List.class, actualList.size(), expectedList.size(), FORMAT, FORMAT_ARG_ARR))
            .thenReturn(actualList.size());
        _coreAssertListEquals(actualList, expectedList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertSetEquals(Set, Set)
    // MoreAssertUtils.assertSetEquals(Set, Set, String, Object...)
    //

    // TODO: LAST

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _coreAssertListEquals(
            @Nullable final List<String> actualList, @Nullable final List<String> expectedList) {
        _coreAssertListEquals(actualList, expectedList, (AssertionError) null);
    }

    private void _coreAssertListEquals(
            @Nullable final List<String> actualList,
            @Nullable final List<String> expectedList,
            @Nullable AssertionError anAssertionError) {
        try {
            classUnderTest.assertListEquals(actualList, expectedList);
            if (null != anAssertionError) {
                throw new RuntimeException("failed to throw AssertionError");
            }
        }
        catch (AssertionError e) {
            assertSame(e, anAssertionError);
        }
        try {
            classUnderTest.assertListEquals(actualList, expectedList, FORMAT, FORMAT_ARG_ARR);
            if (null != anAssertionError) {
                throw new RuntimeException("failed to throw AssertionError");
            }
        }
        catch (AssertionError e) {
            assertSame(e, anAssertionError);
            throw e;
        }
    }
}
