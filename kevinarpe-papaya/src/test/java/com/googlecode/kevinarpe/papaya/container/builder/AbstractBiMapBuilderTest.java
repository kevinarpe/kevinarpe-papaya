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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.testing.mockito.MockitoUtils;
import com.googlecode.kevinarpe.papaya.testing.testng.TestNGPermutationBuilderUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertSame;

public class AbstractBiMapBuilderTest {

    private static class _AbstractBiMapBuilder
    extends AbstractBiMapBuilder<String, Integer, BiMap<String, Integer>, _AbstractBiMapBuilder> {

        private _AbstractBiMapBuilder(
                BiMap<String, Integer> map,
                IMapBuilderUtils<String, Integer, BiMap<String, Integer>> mapBuilderUtils,
                MinimalistMap<String, Integer> minimalistMap) {
            super(map, mapBuilderUtils, minimalistMap);
        }

        @Override
        public BiMap<String, Integer> build() {
            return delegate();
        }

        @Override
        protected _AbstractBiMapBuilder self() {
            return this;
        }
    }

    private BiMap<String, Integer> mockBiMap;
    private IMapBuilderUtils<String, Integer, BiMap<String, Integer>> mockMapBuilderUtils;
    private static MinimalistMap<String, Integer> mockMinimalistMap;

    private _AbstractBiMapBuilder classUnderTest;

    @BeforeClass
    public void beforeAllTests() {
        mockMinimalistMap = MockitoUtils.INSTANCE.mockGenericInterface(MinimalistMap.class);
    }

    @BeforeMethod
    public void beforeEachTest() {
        beforeAllTests();
        mockBiMap = MockitoUtils.INSTANCE.mockGenericInterface(BiMap.class);
        mockMapBuilderUtils = MockitoUtils.INSTANCE.mockGenericInterface(IMapBuilderUtils.class);

        classUnderTest =
            new _AbstractBiMapBuilder(mockBiMap, mockMapBuilderUtils, mockMinimalistMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        return TestNGPermutationBuilderUtils.INSTANCE
            .withNullableParam(HashBiMap.<String, Integer>create())
            .addNullableParam(new MapBuilderUtils<String, Integer, BiMap<String, Integer>>())
            .addNullableParam(mockMinimalistMap)
            .build();
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_ctor_FailWithNull_Data")
    public void ctor_FailWithNull(
        BiMap<String, Integer> map,
        IMapBuilderUtils<String, Integer, BiMap<String, Integer>> mapBuilderUtils,
        MinimalistMap<String, Integer> minimalistMap) {
        new _AbstractBiMapBuilder(map, mapBuilderUtils, minimalistMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putOne()
    //

    @Test
    public void putOne_Pass() {
        _AbstractBiMapBuilder x = classUnderTest.putOne("abc", 123);
        assertSame(x, classUnderTest);
        verify(mockBiMap).put("abc", 123);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Class, Class, Object, Object, Object...)
    //

    @Test
    public void putMany_Object_Object_ObjectArr_Pass() {
        Object[] arr = new Object[] { "abc", 123 };
        _AbstractBiMapBuilder x =
            classUnderTest.putMany(String.class, Integer.class, "def", 456, arr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils)
            .putMany(mockMinimalistMap, String.class, Integer.class, "def", 456, arr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Class, Class, Object[])
    //

    @Test
    public void putMany_ObjectArr_Pass() {
        Object[] arr = new Object[] { "abc", 123 };
        _AbstractBiMapBuilder x = classUnderTest.putMany(String.class, Integer.class, arr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, String.class, Integer.class, arr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Iterable, Iterable)
    //

    @Test
    public void putMany_Iterable_Iterable_Pass() {
        Iterable<String> keyIterable = Lists.newArrayList("abc");
        Iterable<Integer> valueIterable = Lists.newArrayList(123);
        _AbstractBiMapBuilder x = classUnderTest.putMany(keyIterable, valueIterable);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, keyIterable, valueIterable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Iterator, Iterator)
    //

    @Test
    public void putMany_Iterator_Iterator_Pass() {
        Iterator<String> keyIterator = Lists.newArrayList("abc").iterator();
        Iterator<Integer> valueIterator = Lists.newArrayList(123).iterator();
        _AbstractBiMapBuilder x = classUnderTest.putMany(keyIterator, valueIterator);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, keyIterator, valueIterator);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Map.Entry, Map.Entry...)
    //

    @SuppressWarnings("unchecked")
    @Test
    public void putMany_MapEntry_MapEntryArr_Pass() {
        Map.Entry<String, Integer> entry =
            new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123);
        Map.Entry<String, Integer>[] entryArr = new Map.Entry[0];
        _AbstractBiMapBuilder x = classUnderTest.putMany(entry, entryArr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, entry, entryArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Map.Entry[])
    //

    @SuppressWarnings("unchecked")
    @Test
    public void putMany_MapEntryArr_Pass() {
        Map.Entry<String, Integer>[] entryArr = new Map.Entry[0];
        _AbstractBiMapBuilder x = classUnderTest.putMany(entryArr);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, entryArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Iterable)
    //

    @Test
    public void putMany_Iterable_Pass() {
        @SuppressWarnings("unchecked")
        ArrayList<? extends Map.Entry<String, Integer>> list =
            Lists.newArrayList(
                new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123));
        _AbstractBiMapBuilder x = classUnderTest.putMany(list);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, list);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Iterator)
    //

    @Test
    public void putMany_Iterator_Pass() {
        @SuppressWarnings("unchecked")
        ArrayList<? extends Map.Entry<String, Integer>> list =
            Lists.newArrayList(
                new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123));
        Iterator<? extends Map.Entry<String, Integer>> iter = list.iterator();
        _AbstractBiMapBuilder x = classUnderTest.putMany(iter);
        assertSame(x, classUnderTest);
        verify(mockMapBuilderUtils).putMany(mockMinimalistMap, iter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractBiMapBuilder.putMany(Map)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void putMany_Map_FailWithNull() {
        classUnderTest.putMany((Map<? extends String, ? extends Integer>) null);
    }

    @Test
    public void putMany_Map_Pass() {
        _AbstractBiMapBuilder x = classUnderTest.putMany(mockBiMap);
        assertSame(x, classUnderTest);
        verify(mockBiMap).putAll(mockBiMap);
    }
}
