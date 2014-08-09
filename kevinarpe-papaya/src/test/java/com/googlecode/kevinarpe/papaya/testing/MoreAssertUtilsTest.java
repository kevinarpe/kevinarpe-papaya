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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private MoreAssertUtilsHelper mockMoreAssertUtilsHelper;
    private Map<String, Integer> mockActualMap;
    private Map<String, Integer> mockExpectedMap;
    private AssertionError anAssertionError;
    private MoreAssertUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMoreAssertUtilsHelper = mock(MoreAssertUtilsHelper.class);
        mockActualMap = MockitoUtils.INSTANCE.mockGenericInterface(Map.class, "mockActualMap");
        mockExpectedMap = MockitoUtils.INSTANCE.mockGenericInterface(Map.class, "mockExpectedMap");
        anAssertionError = new AssertionError();

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
        _when_assertNeitherNull_Throws_AssertionException(
            "List", actualList, expectedList, anAssertionError);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc");
        _when_assertSameContainerSize_Throws_AssertionException(
            "List", actualList.size(), expectedList.size(), anAssertionError);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhenListsElementsAreNotEqual() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("def");
        _when_assertSameContainerSize("List", actualList, expectedList);
        _when_throwAssertionError(anAssertionError);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertBothIteratorsDoNotHaveNext_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc", "def");
        _when_assertBothIteratorsDoNotHaveNext_Throws_AssertionException(
            "List", anAssertionError);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertListEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        List<String> actualList = Lists.newArrayList();
        List<String> expectedList = Lists.newArrayList();
        _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            "List", actualList, expectedList, anAssertionError);
        _coreAssertListEquals(actualList, expectedList, anAssertionError);
    }

    @Test
    public void assertListEquals_PassWhenEquals() {
        List<String> actualList = Lists.newArrayList("abc");
        List<String> expectedList = Lists.newArrayList("abc");
        _when_assertSameContainerSize("List", actualList, expectedList);
        _coreAssertListEquals(actualList, expectedList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertSetEquals(Set, Set, String, Object...)
    // MoreAssertUtils.assertSetEquals(Set, Set)
    //

    @Test
    public void assertSetEquals_PassWhenSame() {
        Set<String> set = Sets.newHashSet("abc");
        _coreAssertSetEquals(set, set);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetEquals_FailWhen_assertNeitherNull_Throws_AssertionException() {
        Set<String> actualSet = Sets.newHashSet("abc");
        Set<String> expectedSet = Sets.newHashSet("abc");
        _when_assertNeitherNull_Throws_AssertionException(
            "Set", actualSet, expectedSet, anAssertionError);
        _coreAssertSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        Set<String> actualSet = Sets.newHashSet("abc");
        Set<String> expectedSet = Sets.newHashSet("abc");
        _when_assertSameContainerSize_Throws_AssertionException(
            "Set", actualSet.size(), expectedSet.size(), anAssertionError);
        _coreAssertSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetEquals_FailWhen_assertSetContains_Throws_AssertionException() {
        Set<String> actualSet = Sets.newHashSet("abc");
        Set<String> expectedSet = Sets.newHashSet("abc");
        _when_assertSetContains_Throws_AssertionException(
            "Set", actualSet, expectedSet, anAssertionError);
        _coreAssertSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertSetEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        Set<String> actualSet = Sets.newHashSet();
        Set<String> expectedSet = Sets.newHashSet();
        _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            "Set", actualSet, expectedSet, anAssertionError);
        _coreAssertSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test
    public void assertSetEquals_PassWhenEquals() {
        Set<String> actualSet = Sets.newHashSet("abc");
        Set<String> expectedSet = Sets.newHashSet("abc");
        _when_assertSameContainerSize("Set", actualSet, expectedSet);
        _coreAssertSetEquals(actualSet, expectedSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertLinkedSetEquals(Set, Set)
    // MoreAssertUtils.assertLinkedSetEquals(Set, Set, String, Object...)
    //

    @Test
    public void assertLinkedSetEquals_PassWhenSame() {
        Set<String> set = Sets.newLinkedHashSet(Arrays.asList("abc"));
        _coreAssertLinkedSetEquals(set, set);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhen_assertNeitherNull_Throws_AssertionException() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        _when_assertNeitherNull_Throws_AssertionException(
            "LinkedSet", actualSet, expectedSet, anAssertionError);
        _coreAssertLinkedSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        _when_assertSameContainerSize_Throws_AssertionException(
            "LinkedSet", actualSet.size(), expectedSet.size(), anAssertionError);
        _coreAssertLinkedSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhenLinkedSetsElementsAreNotEqual() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("def"));
        _coreAssertLinkedSetEquals_Fail(actualSet, expectedSet);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhen_assertBothIteratorsDoNotHaveNext_Throws_AssertionException() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc", "def"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        _when_assertBothIteratorsDoNotHaveNext_Throws_AssertionException(
            "LinkedSet", anAssertionError);
        _coreAssertLinkedSetEquals_Fail(actualSet, expectedSet);
    }

    private void _coreAssertLinkedSetEquals_Fail(Set<String> actualSet, Set<String> expectedSet) {
        _when_assertSameContainerSize("LinkedSet", actualSet, expectedSet);
        _when_throwAssertionError(anAssertionError);
        _coreAssertLinkedSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhen_assertSetContains_Throws_AssertionException() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("abc"));
        _when_assertSetContains_Throws_AssertionException(
            "LinkedSet", actualSet, expectedSet, anAssertionError);
        _coreAssertLinkedSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedSetEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        Set<String> actualSet = Sets.newLinkedHashSet();
        Set<String> expectedSet = Sets.newLinkedHashSet();
        _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            "LinkedSet", actualSet, expectedSet, anAssertionError);
        _coreAssertLinkedSetEquals(actualSet, expectedSet, anAssertionError);
    }

    @Test
    public void assertLinkedSetEquals_PassWhenEquals() {
        Set<String> actualSet = Sets.newLinkedHashSet(Arrays.asList("abc", "def", "ghi", "abc"));
        Set<String> expectedSet = Sets.newLinkedHashSet(Arrays.asList("abc", "def", "ghi", "abc"));
        _when_assertSameContainerSize("LinkedSet", actualSet, expectedSet);
        _coreAssertLinkedSetEquals(actualSet, expectedSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertMapEquals(Map, Map, String, Object...)
    // MoreAssertUtils.assertMapEquals(Map, Map)
    //

    @Test
    public void assertMapEquals_PassWhenSame() {
        Map<String, Integer> set = ImmutableMap.of("abc", 123);
        _coreAssertMapEquals(set, set);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhen_assertNeitherNull_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertNeitherNull_Throws_AssertionException(
            "Map", actualMap, expectedMap, anAssertionError);
        _coreAssertMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertSameContainerSize_Throws_AssertionException(
            "Map", actualMap.size(), expectedMap.size(), anAssertionError);
        _coreAssertMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhenExpectedKeyNotFoundInActualMap() {
        when(mockExpectedMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockActualMap.containsKey("abc")).thenReturn(false);

        // These lines makes this test more resilient if the implementation changes, e.g.,
        // actual or expected map is checked first or second.
        when(mockActualMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockExpectedMap.containsKey("abc")).thenReturn(true);
        when(mockExpectedMap.get("abc")).thenReturn(123);

        _when_throwAssertionError(anAssertionError);
        _coreAssertMapEquals(mockActualMap, mockExpectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhenExpectedValueNotEqualInActualMap() {
        when(mockExpectedMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockActualMap.containsKey("abc")).thenReturn(true);
        when(mockActualMap.get("abc")).thenReturn(456);

        // These lines makes this test more resilient if the implementation changes, e.g.,
        // actual or expected map is checked first or second.
        when(mockActualMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockExpectedMap.containsKey("abc")).thenReturn(true);
        when(mockExpectedMap.get("abc")).thenReturn(123);

        _when_throwAssertionError(anAssertionError);
        _coreAssertMapEquals(mockActualMap, mockExpectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhenActualKeyNotFoundInExpectedMap() {
        when(mockActualMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockExpectedMap.containsKey("abc")).thenReturn(false);

        // These lines makes this test more resilient if the implementation changes, e.g.,
        // actual or expected map is checked first or second.
        when(mockExpectedMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockActualMap.containsKey("abc")).thenReturn(true);
        when(mockActualMap.get("abc")).thenReturn(123);

        _when_throwAssertionError(anAssertionError);
        _coreAssertMapEquals(mockActualMap, mockExpectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhenActualValueNotEqualInExpectedMap() {
        when(mockActualMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockExpectedMap.containsKey("abc")).thenReturn(true);
        when(mockExpectedMap.get("abc")).thenReturn(456);

        // These lines makes this test more resilient if the implementation changes, e.g.,
        // actual or expected map is checked first or second.
        when(mockExpectedMap.entrySet()).thenReturn(ImmutableMap.of("abc", 123).entrySet());
        when(mockActualMap.containsKey("abc")).thenReturn(true);
        when(mockActualMap.get("abc")).thenReturn(123);

        _when_throwAssertionError(anAssertionError);
        _coreAssertMapEquals(mockActualMap, mockExpectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhen_assertMapEntrySetEquals_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertMapEntrySetEquals_Throws_AssertionException(
            "Map", actualMap, expectedMap, anAssertionError);
        _coreAssertMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertMapEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            "Map", actualMap, expectedMap, anAssertionError);
        _coreAssertMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test
    public void assertMapEquals_PassWhenEquals() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _coreAssertMapEquals(actualMap, expectedMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MoreAssertUtils.assertLinkedMapEquals(Map, Map, String, Object...)
    // MoreAssertUtils.assertLinkedMapEquals(Map, Map)
    //

    @Test
    public void assertLinkedMapEquals_PassWhenSame() {
        Map<String, Integer> set = ImmutableMap.of("abc", 123);
        _coreAssertLinkedMapEquals(set, set);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhen_assertNeitherNull_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertNeitherNull_Throws_AssertionException(
            "LinkedMap", actualMap, expectedMap, anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhen_assertSameContainerSize_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertSameContainerSize_Throws_AssertionException(
            "LinkedMap", actualMap.size(), expectedMap.size(), anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhenKeysAreNotEqual() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("def", 123);
        _when_throwAssertionError(anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhenValuesAreNotEqual() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 456);
        _when_throwAssertionError(anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhen_assertBothIteratorsDoNotHaveNext_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123, "def", 456);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertBothIteratorsDoNotHaveNext_Throws_AssertionException(
            "LinkedMap", anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhen_assertMapEntrySetEquals_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertMapEntrySetEquals_Throws_AssertionException(
            "LinkedMap", actualMap, expectedMap, anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void assertLinkedMapEquals_FailWhen_assertEqualsAndHashCodeCorrect_Throws_AssertionException() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123);
        _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            "Map", actualMap, expectedMap, anAssertionError);
        _coreAssertLinkedMapEquals(actualMap, expectedMap, anAssertionError);
    }

    @Test
    public void assertLinkedMapEquals_PassWhenEquals() {
        Map<String, Integer> actualMap = ImmutableMap.of("abc", 123, "def", 456, "ghi", 789);
        Map<String, Integer> expectedMap = ImmutableMap.of("abc", 123, "def", 456, "ghi", 789);
        _coreAssertLinkedMapEquals(actualMap, expectedMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _when_assertNeitherNull_Throws_AssertionException(
            String classSimpleName,
            Object actual,
            Object expected,
            AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertNeitherNull(classSimpleName, actual, expected, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertNeitherNull(classSimpleName, actual, expected, FORMAT, FORMAT_ARG_ARR);
    }

    private void _when_assertSameContainerSize_Throws_AssertionException(
            String classSimpleName,
            int actualContainerSize,
            int expectedContainerSize,
            AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSameContainerSize(
                classSimpleName, actualContainerSize, expectedContainerSize, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSameContainerSize(classSimpleName, actualContainerSize, expectedContainerSize,
                FORMAT, FORMAT_ARG_ARR);
    }

    private void _when_assertSameContainerSize(
            String classSimpleName,
            Collection<?> actualCollection,
            Collection<?> expectedCollection) {
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                classSimpleName, actualCollection.size(), expectedCollection.size(), null))
            .thenReturn(actualCollection.size());
        when(
            mockMoreAssertUtilsHelper.assertSameContainerSize(
                classSimpleName,
                actualCollection.size(),
                expectedCollection.size(),
                FORMAT,
                FORMAT_ARG_ARR))
            .thenReturn(actualCollection.size());
    }

    private void _when_throwAssertionError(AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .throwAssertionError(eq((String) null), eq(new Object[0]), anyString(), anyVararg());
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .throwAssertionError(eq(FORMAT), eq(FORMAT_ARG_ARR), anyString(), anyVararg());
    }

    private void _when_assertBothIteratorsDoNotHaveNext_Throws_AssertionException(
            String containerDescription, AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertBothIteratorsDoNotHaveNext(
                eq(containerDescription),
                Mockito.<Iterator<?>>any(),
                Mockito.<Iterator<?>>any(),
                eq((String) null),
                anyVararg());
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertBothIteratorsDoNotHaveNext(
                eq(containerDescription),
                Mockito.<Iterator<?>>any(),
                Mockito.<Iterator<?>>any(),
                eq(FORMAT),
                Mockito.anyVararg());
    }

    private
    void _when_assertEqualsAndHashCodeCorrect_Throws_AssertionException(
            String classSimpleName,
            Object actual,
            Object expected,
            AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertEqualsAndHashCodeCorrect(classSimpleName, actual, expected, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertEqualsAndHashCodeCorrect(classSimpleName, actual, expected, FORMAT, FORMAT_ARG_ARR);
    }

    private void _when_assertSetContains_Throws_AssertionException(
            String classSimpleName,
            Set<String> actualSet,
            Set<String> expectedSet,
            AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSetContains(classSimpleName, actualSet, expectedSet, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertSetContains(classSimpleName, actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
    }

    private <TKey, TValue>
    void _when_assertMapEntrySetEquals_Throws_AssertionException(
        String classSimpleName,
        Map<TKey, TValue> actualMap,
        Map<? extends TKey, ? extends TValue> expectedMap,
        AssertionError anAssertionError) {
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertMapEntrySetEquals(classSimpleName, actualMap, expectedMap, null);
        doThrow(anAssertionError)
            .when(mockMoreAssertUtilsHelper)
            .assertMapEntrySetEquals(
                classSimpleName, actualMap, expectedMap, FORMAT, FORMAT_ARG_ARR);
    }

    private <TValue>
    void _coreAssertListEquals(
            @Nullable final List<TValue> actualList, @Nullable final List<TValue> expectedList) {
        _coreAssertListEquals(actualList, expectedList, (AssertionError) null);
    }

    private <TValue>
    void _coreAssertListEquals(
            @Nullable final List<TValue> actualList,
            @Nullable final List<TValue> expectedList,
            @Nullable AssertionError anAssertionError) {
        _coreAssertEquals(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertListEquals(actualList, expectedList);
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertListEquals(
                        actualList, expectedList, FORMAT, FORMAT_ARG_ARR);
                }
            },
            anAssertionError);
    }

    private <TValue>
    void _coreAssertSetEquals(
            @Nullable final Set<TValue> actualSet, @Nullable final Set<TValue> expectedSet) {
        _coreAssertSetEquals(actualSet, expectedSet, (AssertionError) null);
    }

    private <TValue>
    void _coreAssertSetEquals(
            @Nullable final Set<TValue> actualSet,
            @Nullable final Set<TValue> expectedSet,
            @Nullable AssertionError anAssertionError) {
        _coreAssertEquals(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetEquals(actualSet, expectedSet);
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertSetEquals(actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            anAssertionError);
    }

    private <TValue>
    void _coreAssertLinkedSetEquals(
            @Nullable final Set<TValue> actualSet, @Nullable final Set<TValue> expectedSet) {
        _coreAssertLinkedSetEquals(actualSet, expectedSet, (AssertionError) null);
    }

    private <TValue>
    void _coreAssertLinkedSetEquals(
            @Nullable final Set<TValue> actualSet,
            @Nullable final Set<TValue> expectedSet,
            @Nullable AssertionError anAssertionError) {
        _coreAssertEquals(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertLinkedSetEquals(actualSet, expectedSet);
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertLinkedSetEquals(
                        actualSet, expectedSet, FORMAT, FORMAT_ARG_ARR);
                }
            },
            anAssertionError);
    }

    private <TKey, TValue>
    void _coreAssertMapEquals(
            @Nullable final Map<TKey, TValue> actualMap,
            @Nullable final Map<TKey, TValue> expectedMap) {
        _coreAssertMapEquals(actualMap, expectedMap, (AssertionError) null);
    }

    private <TKey, TValue>
    void _coreAssertMapEquals(
            @Nullable final Map<TKey, TValue> actualMap,
            @Nullable final Map<TKey, TValue> expectedMap,
            @Nullable AssertionError anAssertionError) {
        _coreAssertEquals(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertMapEquals(actualMap, expectedMap);
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertMapEquals(actualMap, expectedMap, FORMAT, FORMAT_ARG_ARR);
                }
            },
            anAssertionError);
    }

    private <TKey, TValue>
    void _coreAssertLinkedMapEquals(
            @Nullable final Map<TKey, TValue> actualMap,
            @Nullable final Map<TKey, TValue> expectedMap) {
        _coreAssertLinkedMapEquals(actualMap, expectedMap, (AssertionError) null);
    }

    private <TKey, TValue>
    void _coreAssertLinkedMapEquals(
            @Nullable final Map<TKey, TValue> actualMap,
            @Nullable final Map<TKey, TValue> expectedMap,
            @Nullable AssertionError anAssertionError) {
        _coreAssertEquals(
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertLinkedMapEquals(actualMap, expectedMap);
                }
            },
            new Runnable() {
                @Override
                public void run() {
                    classUnderTest.assertLinkedMapEquals(
                        actualMap, expectedMap, FORMAT, FORMAT_ARG_ARR);
                }
            },
            anAssertionError);
    }

    private void _coreAssertEquals(
            Runnable withoutMessageRunnable,
            Runnable withMessageRunnable,
            @Nullable AssertionError anAssertionError) {
        try {
            withoutMessageRunnable.run();
            if (null != anAssertionError) {
                throw new RuntimeException("Failed to throw AssertionError");
            }
        }
        catch (AssertionError e) {
            assertSame(e, anAssertionError);
        }
        try {
            withMessageRunnable.run();
            if (null != anAssertionError) {
                throw new RuntimeException("Failed to throw AssertionError");
            }
        }
        catch (AssertionError e) {
            assertSame(e, anAssertionError);
            throw e;
        }
    }
}
