package com.googlecode.kevinarpe.papaya.properties;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLogger;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerUtils;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertiesMergerImplTest {

    private PropertiesMergerImpl classUnderTest;
    private SLF4JMockLogger mockLogger;
    private Map<? super String, ? super String> mockMap;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger =
            SLF4JMockLoggerUtils.INSTANCE.newFactoryInstance().getLogger(
                PropertiesMergerImplTest.class.getName());
        classUnderTest = new PropertiesMergerImpl(mockLogger);
        {
            @SuppressWarnings("unchecked")
            Map<? super String, ? super String> mockMap2 = mock(Map.class);
            mockMap = mockMap2;
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new PropertiesMergerImpl((Logger) null);
    }

    @Test
    public void merge_PassWithEmptyJdkPropertyList() {
        classUnderTest.merge(mockMap, ImmutableList.<JdkProperty>of());
        verify(mockMap, never()).containsKey(anyString());
        verify(mockMap, never()).get(anyString());
        verify(mockMap, never()).put(anyString(), anyString());
    }

    @Test
    public void merge_PassWithNewJdkProperties() {
        classUnderTest.merge(
            mockMap,
            ImmutableList.of(new JdkProperty("key1", "value1"), new JdkProperty("key2", "value2")));
        verify(mockMap).containsKey("key1");
        verify(mockMap).containsKey("key2");
        verify(mockMap, never()).get(anyString());
        verify(mockMap).put("key1", "value1");
        verify(mockMap).put("key2", "value2");
    }

    @Test
    public void merge_PassWithOneDupeJdkProperty() {
        when(mockMap.containsKey("key1")).thenReturn(true);
        when(mockMap.get("key1")).thenReturn("value1");
        classUnderTest.merge(
            mockMap,
            ImmutableList.of(new JdkProperty("key1", "value1"), new JdkProperty("key2", "value2")));
        verify(mockMap).containsKey("key1");
        verify(mockMap).containsKey("key2");
        verify(mockMap).get("key1");
        verify(mockMap).put("key2", "value2");
    }

    @Test
    public void merge_PassWithAllDupeJdkProperties() {
        when(mockMap.containsKey("key1")).thenReturn(true);
        when(mockMap.get("key1")).thenReturn("value1");
        when(mockMap.containsKey("key2")).thenReturn(true);
        when(mockMap.get("key2")).thenReturn("value2");
        classUnderTest.merge(
            mockMap,
            ImmutableList.of(new JdkProperty("key1", "value1"), new JdkProperty("key2", "value2")));
        verify(mockMap).containsKey("key1");
        verify(mockMap).containsKey("key2");
        verify(mockMap).get("key1");
        verify(mockMap).get("key2");
        verify(mockMap, never()).put(anyString(), anyString());
    }
}
