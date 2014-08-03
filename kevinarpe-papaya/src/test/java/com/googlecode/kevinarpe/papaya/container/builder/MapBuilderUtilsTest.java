package com.googlecode.kevinarpe.papaya.container.builder;

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
import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import com.googlecode.kevinarpe.papaya.testing.testng.TestNGPermutationBuilderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MapBuilderUtilsTest {

    private static MinimalistMap<String, Integer> mockMinimalistMap;
    private MinimalistMap<List, Integer> mockMinimalistMap2;
    private MinimalistMap<String, List> mockMinimalistMap3;

    private MapBuilderUtils<String, Integer, HashMap<String, Integer>> classUnderTest;
    private MapBuilderUtils<List, Integer, HashMap<List, Integer>> classUnderTest2;
    private MapBuilderUtils<String, List, HashMap<String, List>> classUnderTest3;

    @BeforeClass
    public static void beforeAllTests() {
        mockMinimalistMap = MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMap.class);
    }

    @BeforeMethod
    public void beforeEachTestMethod() {
        beforeAllTests();
        mockMinimalistMap2 = MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMap.class);
        mockMinimalistMap3 = MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMap.class);

        classUnderTest = new MapBuilderUtils<String, Integer, HashMap<String, Integer>>();
        classUnderTest2 = new MapBuilderUtils<List, Integer, HashMap<List, Integer>>();
        classUnderTest3 = new MapBuilderUtils<String, List, HashMap<String, List>>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapBuilderUtils.putMany(MinimalistMap, Class, Class, Object...)
    //

    @DataProvider
    private static Object[][] _putMany_ObjectArr_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(mockMinimalistMap)
                .addNullableParam(String.class)
                .addNullableParam(Integer.class)
                .addNullableParam(new Object[0])
                .build();
        return x;
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_putMany_ObjectArr_FailWithNull_Data")
    public void putMany_ObjectArr_FailWithNull(
            MinimalistMap<String, Integer> map,
            Class<String> keyClass,
            Class<Integer> valueClass,
            Object[] keysAndValuesArr) {
        classUnderTest.putMany(map, keyClass, valueClass, keysAndValuesArr);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putMany_ObjectArr_FailWithGenericType() {
        classUnderTest2.putMany(mockMinimalistMap2, List.class, Integer.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putMany_ObjectArr_FailWithGenericType2() {
        classUnderTest3.putMany(mockMinimalistMap3, String.class, List.class);
    }

    @DataProvider
    private static Object[][] _putMany_ObjectArr_FailWithOddLengthKeysAndValuesArr_Data() {
        return new Object[][][] {
            { { "abc" } },
            { { "abc", 123, "def" } },
            { { "abc", 123, "def", 456, "ghi" } },
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
        dataProvider = "_putMany_ObjectArr_FailWithOddLengthKeysAndValuesArr_Data")
    public void putMany_ObjectArr_FailWithOddLengthKeysAndValuesArr(Object[] keysAndValuesArr) {
        classUnderTest.putMany(mockMinimalistMap, String.class, Integer.class, keysAndValuesArr);
    }

    @DataProvider
    private static Object[][] _putMany_ObjectArr_FailWithClassCastException_Data() {
        return new Object[][][] {
            { { "abc", "def" } },
            { { 123, 456 } },
            { { 123, "abc", } },
            { { "abc", 123, 456, 789 } },
            { { "abc", 123, "def", "ghi" } },
            { { "abc", 123, 456, "def" } },
        };
    }

    @Test(expectedExceptions = ClassCastException.class,
        dataProvider = "_putMany_ObjectArr_FailWithClassCastException_Data")
    public void putMany_ObjectArr_FailWithClassCastException(Object[] keysAndValuesArr) {
        classUnderTest.putMany(mockMinimalistMap, String.class, Integer.class, keysAndValuesArr);
    }

    @DataProvider
    private static Object[][] _putMany_ObjectArr_Pass_Data() {
        return new Object[][][] {
            { { } },
            { { "abc", 123 } },
            { { "abc", 123, "def", 456 } },
        };
    }

    @Test(dataProvider = "_putMany_ObjectArr_Pass_Data")
    public void putMany_ObjectArr_Pass(Object[] keysAndValuesArr) {
        classUnderTest.putMany(mockMinimalistMap, String.class, Integer.class, keysAndValuesArr);
        verify(mockMinimalistMap, times(keysAndValuesArr.length / 2))
            .put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapBuilderUtils.putMany(MinimalistMap, Iterable, Iterable)
    //

    @DataProvider
    private static Object[][] _putMany_Iterable_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(mockMinimalistMap)
                .addNullableParam(new ArrayList<String>())
                .addNullableParam(new ArrayList<Integer>())
                .build();
        return x;
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_putMany_Iterable_FailWithNull_Data")
    public void putMany_Iterable_FailWithNull(
            MinimalistMap<String, Integer> map,
            Iterable<String> keyIterable,
            Iterable<Integer> valueIterable) {
        classUnderTest.putMany(map, keyIterable, valueIterable);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void putMany_Iterable_FailWithDifferentListSizes() {
        classUnderTest.putMany(
            mockMinimalistMap, Lists.newArrayList("abc", "def"), Lists.newArrayList(123));
    }

    @DataProvider
    private static Object[][] _putMany_Iterable_Pass_Data() {
        return new Object[][] {
            { new ArrayList<String>(), new ArrayList<Integer>() },
            { Lists.newArrayList("abc"), Lists.newArrayList(123) },
            { Lists.newArrayList("abc", "def"), Lists.newArrayList(123, 456) },
        };
    }

    @Test(dataProvider = "_putMany_Iterable_Pass_Data")
    public void putMany_Iterable_Pass(
            Collection<String> keyCollection, Collection<Integer> valueCollection) {
        classUnderTest.putMany(mockMinimalistMap, keyCollection, valueCollection);
        verify(mockMinimalistMap, times(keyCollection.size())).put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapBuilderUtils.putMany(MinimalistMap, Map.Entry...)
    //

    @DataProvider
    private static Object[][] _putMany_MapEntryArr_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(mockMinimalistMap)
                .addNullableParam(new Map.Entry[0])
                .build();
        return x;
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_putMany_MapEntryArr_FailWithNull_Data")
    public void putMany_MapEntryArr_FailWithNull(
            MinimalistMap<String, Integer> map,
            Map.Entry<String, Integer>[] entryArr) {
        classUnderTest.putMany(map, entryArr);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void putMany_MapEntryArr_FailWithNullElement() {
        classUnderTest.putMany(
            mockMinimalistMap,
            new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
            (Map.Entry<String, Integer>) null);
    }

    @SuppressWarnings("unchecked")
    @DataProvider
    private static Object[][] _putMany_MapEntryArr_Pass_Data() {
        return new Object[][][] {
            { new Map.Entry[0] },
            {
                new Map.Entry[]
                    {
                        new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
                    }
            },
            {
                new Map.Entry[]
                    {
                        new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
                        new AbstractMap.SimpleImmutableEntry<String, Integer>("def", 456),
                    }
            },
        };
    }

    @Test(dataProvider = "_putMany_MapEntryArr_Pass_Data")
    public void putMany_MapEntryArr_Pass(
            Map.Entry<? extends String, ? extends Integer>[] mapEntryArr) {
        classUnderTest.putMany(mockMinimalistMap, mapEntryArr);
        verify(mockMinimalistMap, times(mapEntryArr.length)).put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapBuilderUtils.putMany(MinimalistMap, Iterable)
    //

    @DataProvider
    private static Object[][] _putMany_MapEntryIterable_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(mockMinimalistMap)
                .addNullableParam(new ArrayList<Map.Entry<String, Integer>>())
                .build();
        return x;
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_putMany_MapEntryIterable_FailWithNull_Data")
    public void putMany_MapEntryIterable_FailWithNull(
            MinimalistMap<String, Integer> map,
            Iterable<Map.Entry<String, Integer>> entryIterable) {
        classUnderTest.putMany(map, entryIterable);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void putMany_MapEntryIterable_FailWithNullElement() {
        classUnderTest.putMany(
            mockMinimalistMap,
            Lists.newArrayList(
                new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
                (Map.Entry<String, Integer>) null));
    }

    @SuppressWarnings("unchecked")
    @DataProvider
    private static Object[][] _putMany_MapEntryIterable_Pass_Data() {
        return new Object[][] {
            { new ArrayList<Map.Entry<String, Integer>>() },
            {
                Lists.newArrayList(
                    new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123)),
            },
            {
                Lists.newArrayList(
                    new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
                    new AbstractMap.SimpleImmutableEntry<String, Integer>("def", 456)),
            },
        };
    }

    @Test(dataProvider = "_putMany_MapEntryIterable_Pass_Data")
    public void putMany_MapEntryIterable_Pass(
            Collection<? extends Map.Entry<String, Integer>> mapEntryCollection) {
        classUnderTest.putMany(mockMinimalistMap, mapEntryCollection);
        verify(mockMinimalistMap, times(mapEntryCollection.size())).put(anyString(), anyInt());
    }
}
