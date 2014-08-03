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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class MapFactoryHelperImplTest {

    private static MinimalistMapBuilderFactory
                        <
                            String,
                            Integer,
                            HashMap<String, Integer>,
                            MinimalistMapBuilder<String, Integer, HashMap<String, Integer>>
                        > mockMinimalistMapBuilderFactory;

    private static IMapBuilderUtils<String, Integer, HashMap<String, Integer>> mockIMapBuilderUtils;

    private MinimalistMapBuilder<String, Integer, HashMap<String, Integer>>
        mockMinimalistMapBuilder;

    private HashMap<String, Integer> hashMap;
    private MapFactoryHelperImpl<String, Integer, HashMap<String, Integer>> classUnderTest;

    @BeforeClass
    public static void beforeAllTests() {
        mockMinimalistMapBuilderFactory =
            MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMapBuilderFactory.class);
        mockIMapBuilderUtils = MockitoUtils.INSTANCE.mockGenericInterface(IMapBuilderUtils.class);
    }

    @BeforeMethod
    public void beforeEachTestMethod() {
        beforeAllTests();
        mockMinimalistMapBuilder =
            MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMapBuilder.class);
        when(mockMinimalistMapBuilderFactory.newInstance())
            .thenReturn(mockMinimalistMapBuilder);
        hashMap = Maps.newHashMap();
        when(mockMinimalistMapBuilder.getMap()).thenReturn(hashMap);

        classUnderTest =
            new MapFactoryHelperImpl<String, Integer, HashMap<String, Integer>>(
                mockMinimalistMapBuilderFactory, mockIMapBuilderUtils);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(mockMinimalistMapBuilderFactory)
                .addNullableParam(mockIMapBuilderUtils)
                .build();
        return x;
    }

    @SuppressWarnings("unchecked")
    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_ctor_FailWithNull_Data")
    public void ctor_FailWithNull(
            MinimalistMapBuilderFactory
                <
                    String,
                    Integer,
                    HashMap<String, Integer>,
                    MinimalistMapBuilder<String, Integer, HashMap<String, Integer>>
                > minimalistMapBuilderFactory,
            IMapBuilderUtils<String, Integer, HashMap<String, Integer>> mapBuilderUtils) {
        new MapFactoryHelperImpl(minimalistMapBuilderFactory, mapBuilderUtils);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Class, Class, Object...)
    //

    @Test
    public void copyOf_ObjectArr_Pass() {
        Object[] keysAndValuesArr = new Object[0];
        HashMap<String, Integer> map =
            classUnderTest.copyOf(String.class, Integer.class, keysAndValuesArr);
        assertSame(map, hashMap);
        verify(mockIMapBuilderUtils)
            .putMany(mockMinimalistMapBuilder, String.class, Integer.class, keysAndValuesArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Iterable, Iterable)
    //

    @Test
    public void copyOf_Iterable_Pass() {
        Collection<String> keyCollection = Lists.newArrayList();
        Collection<Integer> valueCollection = Lists.newArrayList();
        HashMap<String, Integer> map = classUnderTest.copyOf(keyCollection, valueCollection);
        assertSame(map, hashMap);
        verify(mockIMapBuilderUtils)
            .putMany(mockMinimalistMapBuilder, keyCollection, valueCollection);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Map.Entry...)
    //

    @SuppressWarnings("unchecked")
    @Test
    public void copyOf_MapEntryArr_Pass() {
        Map.Entry<String, Integer>[] mapEntryArr = new Map.Entry[0];
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryArr);
        assertSame(map, hashMap);
        verify(mockIMapBuilderUtils).putMany(mockMinimalistMapBuilder, mapEntryArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapFactoryHelperImpl.copyOf(Iterable)
    //

    @Test
    public void copyOf_MapEntryIterable_Pass() {
        Collection<Map.Entry<String, Integer>> mapEntryCollection = Lists.newArrayList();
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryCollection);
        assertSame(map, hashMap);
        verify(mockIMapBuilderUtils).putMany(mockMinimalistMapBuilder, mapEntryCollection);
    }
}
