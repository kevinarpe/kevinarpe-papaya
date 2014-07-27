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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class ForwardingMapFactoryHelperTest {

    private static final class _ForwardingMapFactoryHelper
    extends ForwardingMapFactoryHelper<String, Integer, HashMap<String, Integer>> {

        private final MapFactoryHelper<String, Integer, HashMap<String, Integer>> _mapFactoryHelper;

        private _ForwardingMapFactoryHelper(
                MapFactoryHelper<String, Integer, HashMap<String, Integer>> mapFactoryHelper) {
            _mapFactoryHelper = mapFactoryHelper;
        }

        @Override
        protected MapFactoryHelper<String, Integer, HashMap<String, Integer>> delegate() {
            return _mapFactoryHelper;
        }
    }

    private MapFactoryHelper<String, Integer, HashMap<String, Integer>>
        mockMapFactoryHelper;
    private HashMap<String, Integer> hashMap;

    private _ForwardingMapFactoryHelper classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMapFactoryHelper =
            MockitoUtils.INSTANCE.mockGenericInterface(MapFactoryHelper.class);
        hashMap = Maps.newHashMap();

        classUnderTest = new _ForwardingMapFactoryHelper(mockMapFactoryHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ForwardingMapFactoryHelper.copyOf(Class, Class, Object...)
    //

    @Test
    public void copyOf1_Pass() {
        Object[] keysAndValuesArr = { "abc", 123 };
        when(mockMapFactoryHelper.copyOf(String.class, Integer.class, keysAndValuesArr))
            .thenReturn(hashMap);
        HashMap<String, Integer> map =
            classUnderTest.copyOf(String.class, Integer.class, keysAndValuesArr);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelper).copyOf(String.class, Integer.class, keysAndValuesArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ForwardingMapFactoryHelper.copyOf(Iterable, Iterable)
    //

    @Test
    public void copyOf2_Pass() {
        List<String> keyList = Lists.newArrayList("abc");
        List<Integer> valueList = Lists.newArrayList(123);
        when(mockMapFactoryHelper.copyOf(keyList, valueList)).thenReturn(hashMap);
        HashMap<String, Integer> map = classUnderTest.copyOf(keyList, valueList);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelper).copyOf(keyList, valueList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ForwardingMapFactoryHelper.copyOf(Map.Entry...)
    //

    @SuppressWarnings("unchecked")
    @Test
    public void copyOf3_Pass() {
        Map.Entry<String, Integer>[] mapEntryArr = new Map.Entry[] {
            new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123),
        };
        when(mockMapFactoryHelper.copyOf(mapEntryArr)).thenReturn(hashMap);
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryArr);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelper).copyOf(mapEntryArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ForwardingMapFactoryHelper.copyOf(Iterable)
    //

    @Test
    public void copyOf4_Pass() {
        @SuppressWarnings("unchecked")
        List<? extends Map.Entry<String, Integer>> mapEntryList =
            Lists.newArrayList(new AbstractMap.SimpleImmutableEntry<String, Integer>("abc", 123));
        when(mockMapFactoryHelper.copyOf(mapEntryList)).thenReturn(hashMap);
        HashMap<String, Integer> map = classUnderTest.copyOf(mapEntryList);
        assertSame(map, hashMap);
        verify(mockMapFactoryHelper).copyOf(mapEntryList);
    }
}
