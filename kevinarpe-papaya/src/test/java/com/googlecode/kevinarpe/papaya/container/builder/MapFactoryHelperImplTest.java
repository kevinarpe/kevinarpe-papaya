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
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import com.googlecode.kevinarpe.papaya.testing.testng.TestNGPermutationBuilderUtils;
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
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class MapFactoryHelperImplTest {

    private MapFactoryHelperHelperFactory
                <
                    String,
                    Integer,
                    HashMap<String, Integer>,
                    MapFactoryHelperHelper<String, Integer, HashMap<String, Integer>>
                > mockMapFactoryHelperHelperFactory;

    private MapFactoryHelperHelper<String, Integer, HashMap<String, Integer>>
        mockMapFactoryHelperHelper;

    private HashMap<String, Integer> hashMap;

    private MapFactoryHelperHelperFactory
                <
                    List,
                    Integer,
                    HashMap<List, Integer>,
                    MapFactoryHelperHelper<List, Integer, HashMap<List, Integer>>
                > mockMapFactoryHelperHelperFactory2;

    private MapFactoryHelperHelperFactory
                <
                    String,
                    List,
                    HashMap<String, List>,
                    MapFactoryHelperHelper<String, List, HashMap<String, List>>
                > mockMapFactoryHelperHelperFactory3;

    private MapFactoryHelperImpl<String, Integer, HashMap<String, Integer>> classUnderTest;
    private MapFactoryHelperImpl<List, Integer, HashMap<List, Integer>> classUnderTest2;
    private MapFactoryHelperImpl<String, List, HashMap<String, List>> classUnderTest3;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMapFactoryHelperHelperFactory =
            MockitoUtils.INSTANCE.mockGenericInterface(MapFactoryHelperHelperFactory.class);
        mockMapFactoryHelperHelper =
            MockitoUtils.INSTANCE.mockGenericInterface(MapFactoryHelperHelper.class);
        when(mockMapFactoryHelperHelperFactory.newInstance())
            .thenReturn(mockMapFactoryHelperHelper);
        hashMap = Maps.newHashMap();
        when(mockMapFactoryHelperHelper.getMap()).thenReturn(hashMap);

        mockMapFactoryHelperHelperFactory2 =
            MockitoUtils.INSTANCE.mockGenericInterface(MapFactoryHelperHelperFactory.class);

        mockMapFactoryHelperHelperFactory3 =
            MockitoUtils.INSTANCE.mockGenericInterface(MapFactoryHelperHelperFactory.class);

        classUnderTest =
            new MapFactoryHelperImpl<String, Integer, HashMap<String, Integer>>(
                mockMapFactoryHelperHelperFactory);
        classUnderTest2 =
            new MapFactoryHelperImpl<List, Integer, HashMap<List, Integer>>(
                mockMapFactoryHelperHelperFactory2);
        classUnderTest3 =
            new MapFactoryHelperImpl<String, List, HashMap<String, List>>(
                mockMapFactoryHelperHelperFactory3);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Class, Class, Object...)
    //

    @DataProvider
    private static Object[][] _copyOf_ObjectArr_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE.withNullableParam(String.class)
                .addNullableParam(Integer.class)
                .addNullableParam(new Object[0])
                .build();
        return x;
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_copyOf_ObjectArr_FailWithNull_Data")
    public void copyOf_ObjectArr_FailWithNull(
            Class<String> keyClass, Class<Integer> valueClass, Object[] keysAndValuesArr) {
        classUnderTest.copyOf(keyClass, valueClass, keysAndValuesArr);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_ObjectArr_FailWithGenericType() {
        classUnderTest2.copyOf(List.class, Integer.class);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_ObjectArr_FailWithGenericType2() {
        classUnderTest3.copyOf(String.class, List.class);
    }

    @DataProvider
    private static Object[][] _copyOf_ObjectArr_FailWithOddLengthKeysAndValuesArr_Data() {
        return new Object[][][] {
            { { "abc" } },
            { { "abc", 123, "def" } },
            { { "abc", 123, "def", 456, "ghi" } },
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            dataProvider = "_copyOf_ObjectArr_FailWithOddLengthKeysAndValuesArr_Data")
    public void copyOf_ObjectArr_FailWithOddLengthKeysAndValuesArr(Object[] keysAndValuesArr) {
        classUnderTest.copyOf(String.class, Integer.class, keysAndValuesArr);
    }

    @DataProvider
    private static Object[][] _copyOf_ObjectArr_FailWithClassCastException_Data() {
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
            dataProvider = "_copyOf_ObjectArr_FailWithClassCastException_Data")
    public void copyOf_ObjectArr_FailWithClassCastException(Object[] keysAndValuesArr) {
        classUnderTest.copyOf(String.class, Integer.class, keysAndValuesArr);
    }

    @DataProvider
    private static Object[][] _copyOf_ObjectArr_Pass_Data() {
        return new Object[][][] {
            { { } },
            { { "abc", 123 } },
            { { "abc", 123, "def", 456 } },
        };
    }

    @Test(dataProvider = "_copyOf_ObjectArr_Pass_Data")
    public void copyOf_ObjectArr_Pass(Object[] keysAndValuesArr) {
        HashMap<String, Integer> map =
            classUnderTest.copyOf(String.class, Integer.class, keysAndValuesArr);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelperHelper, times(keysAndValuesArr.length / 2))
            .put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Iterable, Iterable)
    //

    @DataProvider
    private static Object[][] _copyOf_Iterable_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE.withNullableParam(new ArrayList<String>())
                .addNullableParam(new ArrayList<Integer>())
                .build();
        return x;
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_copyOf_Iterable_FailWithNull_Data")
    public void copyOf_Iterable_FailWithNull(
            Iterable<String> keyIterable, Iterable<Integer> valueIterable) {
        classUnderTest.copyOf(keyIterable, valueIterable);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void copyOf_Iterable_FailWithDifferentListSizes() {
        classUnderTest.copyOf(Lists.newArrayList("abc", "def"), Lists.newArrayList(123));
    }

    @DataProvider
    private static Object[][] _copyOf_Iterable_Pass_Data() {
        return new Object[][] {
            { new ArrayList<String>(), new ArrayList<Integer>() },
            { Lists.newArrayList("abc"), Lists.newArrayList(123) },
            { Lists.newArrayList("abc", "def"), Lists.newArrayList(123, 456) },
        };
    }

    @Test(dataProvider = "_copyOf_Iterable_Pass_Data")
    public void copyOf_Iterable_Pass(
            Collection<String> keyCollection, Collection<Integer> valueCollection) {
        HashMap<String, Integer> map = classUnderTest.copyOf(keyCollection, valueCollection);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelperHelper, times(keyCollection.size())).put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Map.Entry...)
    //

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_MapEntryArr_FailWithNull() {
        classUnderTest.copyOf((Map.Entry<String, Integer>[]) null);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_MapEntryArr_FailWithNullElement() {
        classUnderTest.copyOf(
            new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
            (Map.Entry<String, Integer>) null);
    }

    @SuppressWarnings("unchecked")
    @DataProvider
    private static Object[][] _copyOf_MapEntryArr_Pass_Data() {
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

    @Test(dataProvider = "_copyOf_MapEntryArr_Pass_Data")
    public void copyOf_MapEntryArr_Pass(
            Map.Entry<? extends String, ? extends Integer>[] mapEntryArr) {
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryArr);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelperHelper, times(mapEntryArr.length)).put(anyString(), anyInt());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Iterable)
    //

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_MapEntryIterable_FailWithNull() {
        classUnderTest.copyOf((Iterable<Map.Entry<String, Integer>>) null);
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_MapEntryIterable_FailWithNullElement() {
        classUnderTest.copyOf(
            Lists.newArrayList(
                new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
                (Map.Entry<String, Integer>) null));
    }

    @SuppressWarnings("unchecked")
    @DataProvider
    private static Object[][] _copyOf_MapEntryIterable_Pass_Data() {
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

    @Test(dataProvider = "_copyOf_MapEntryIterable_Pass_Data")
    public void copyOf_MapEntryIterable_Pass(
            Collection<? extends Map.Entry<String, Integer>> mapEntryCollection) {
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryCollection);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelperHelper, times(mapEntryCollection.size()))
            .put(anyString(), anyInt());
    }
}
