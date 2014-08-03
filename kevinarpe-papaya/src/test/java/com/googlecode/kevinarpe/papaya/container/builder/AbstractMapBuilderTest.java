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
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

public class AbstractMapBuilderTest {

    private static class _AbstractMapBuilder
    extends AbstractMapBuilder<String, Integer, Map<String, Integer>, _AbstractMapBuilder> {

        private _AbstractMapBuilder(
                Map<String, Integer> map,
                IMapBuilderUtils<String, Integer, Map<String, Integer>> mapBuilderUtils,
                MinimalistMap<String, Integer> minimalistMap) {
            super(map, mapBuilderUtils, minimalistMap);
        }

        @Override
        public Map<String, Integer> build() {
            return delegate();
        }

        @Override
        protected _AbstractMapBuilder self() {
            return this;
        }
    }

    private Map<String, Integer> mockMap;
    private IMapBuilderUtils<String, Integer, Map<String, Integer>> mockMapBuilderUtils;
    private static MinimalistMap<String, Integer> mockMinimalistMap;

    private _AbstractMapBuilder classUnderTest;

    @BeforeClass
    public void beforeAllTests() {
        mockMinimalistMap = MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMap.class);
    }

    @BeforeMethod
    public void beforeEachTest() {
        beforeAllTests();
        mockMap = MockitoUtils.INSTANCE.mockGenericInterface(Map.class);
        mockMapBuilderUtils = MockitoUtils.INSTANCE.mockGenericInterface(IMapBuilderUtils.class);

        classUnderTest = new _AbstractMapBuilder(mockMap, mockMapBuilderUtils, mockMinimalistMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        return TestNGPermutationBuilderUtils.INSTANCE
            .withNullableParam(new HashMap<String, Integer>())
            .addNullableParam(new MapBuilderUtils<String, Integer, Map<String, Integer>>())
            .addNullableParam(mockMinimalistMap)
            .build();
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_ctor_FailWithNull_Data")
    public void ctor_FailWithNull(
            Map<String, Integer> map,
            IMapBuilderUtils<String, Integer, Map<String, Integer>> mapBuilderUtils,
            MinimalistMap<String, Integer> minimalistMap) {
        new _AbstractMapBuilder(map, mapBuilderUtils, minimalistMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putOne()
    //

    @Test
    public void putOne_Pass() {
        _AbstractMapBuilder x = classUnderTest.putOne("abc", 123);
        assertSame(x, classUnderTest);
        verify(mockMap).put("abc", 123);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putMany(Class, Class, Object...)
    //

    @Test
    public void putMany_ObjectArr_Pass() {
        Object[] arr = new Object[] { "abc", 123 };
        _AbstractMapBuilder x = classUnderTest.putMany(String.class, Integer.class, arr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, String.class, Integer.class, arr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putMany(Iterable, Iterable)
    //

    @Test
    public void putMany_IterableIterable_Pass() {
        Iterable<String> keyIterable = Lists.newArrayList("abc");
        Iterable<Integer> valueIterable = Lists.newArrayList(123);
        _AbstractMapBuilder x = classUnderTest.putMany(keyIterable, valueIterable);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, keyIterable, valueIterable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putMany(Map.Entry...)
    //

    @Test
    public void putMany_MapEntryArr_Pass() {
        @SuppressWarnings("unchecked")
        Map.Entry<String, Integer>[] entryArr = new Map.Entry[0];
        _AbstractMapBuilder x = classUnderTest.putMany(entryArr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, entryArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putMany(Iterable)
    //

    @Test
    public void putMany_Iterable_Pass() {
        @SuppressWarnings("unchecked")
        Iterable<Map.Entry<String, Integer>> entryIterable =
            Lists.<Map.Entry<String, Integer>>newArrayList(
                new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123));
        _AbstractMapBuilder x = classUnderTest.putMany(entryIterable);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, entryIterable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractMapBuilder.putMany(Map)
    //

    @Test
    public void putMany_Map_Pass() {
        _AbstractMapBuilder x = classUnderTest.putMany(mockMap);
        assertSame(x, classUnderTest);
        verify(mockMap).putAll(mockMap);
    }
}
