package com.googlecode.kevinarpe.papaya.properties;

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

import com.googlecode.kevinarpe.papaya.container.builder.MapBuilder;
import com.googlecode.kevinarpe.papaya.container.builder.MapFactory;
import com.googlecode.kevinarpe.papaya.input.IInputSource2Utils;
import com.googlecode.kevinarpe.papaya.input.InputSource2;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.jdk.properties.RandomAccessList;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLogger;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerFactory;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerUtils;
import org.mockito.InOrder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class PropertiesLoaderImplTest {

    private PropertiesLoaderPolicy mockPropertiesLoaderPolicy;
    private PropertiesLoaderPolicy mockPropertiesLoaderPolicy2;
    private JdkPropertiesLoaderHelper mockJdkPropertiesLoaderHelper;
    private IInputSource2Utils mockIInputSource2Utils;
    private PropertiesMerger mockPropertiesMerger;
    private SLF4JMockLoggerFactory mockSLF4JMockLoggerFactory;
    private SLF4JMockLogger logger;
    private PropertiesLoaderImpl classUnderTest;
    private InputSource2 mockInputSource2A;
    private InputSource2 mockInputSource2B;
    private List<InputSource2> inputSourceList;
    private MapFactory<
                        String,
                        String,
                        Map<String, String>,
                        MapBuilder<String, String, Map<String, String>>
                    >
        mockMapFactory;
    private MapBuilder<String, String, Map<String, String>> mockMapBuilder;
    private Map<String, String> mockMap;
    private RandomAccessList<JdkProperty> mockJdkPropertyListA;
    private RandomAccessList<JdkProperty> mockJdkPropertyListB;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockPropertiesLoaderPolicy = mock(PropertiesLoaderPolicy.class);
        mockPropertiesLoaderPolicy2 = mock(PropertiesLoaderPolicy.class);
        mockJdkPropertiesLoaderHelper = mock(JdkPropertiesLoaderHelper.class);
        mockIInputSource2Utils = mock(IInputSource2Utils.class);
        mockPropertiesMerger = mock(PropertiesMerger.class);
        mockSLF4JMockLoggerFactory = mock(SLF4JMockLoggerFactory.class);
        logger =
            SLF4JMockLoggerUtils.INSTANCE.newFactoryInstance()
                .getLogger(PropertiesLoaderImplTest.class.getName());
        classUnderTest =
            new PropertiesLoaderImpl(
                mockPropertiesLoaderPolicy,
                mockJdkPropertiesLoaderHelper,
                mockIInputSource2Utils,
                mockPropertiesMerger,
                logger);
        mockInputSource2A = mock(InputSource2.class);
        mockInputSource2B = mock(InputSource2.class);
        inputSourceList = Arrays.asList(mockInputSource2A, mockInputSource2B);
        {
            @SuppressWarnings("unchecked")
            MapFactory<
                                    String,
                                    String,
                                    Map<String, String>,
                                    MapBuilder<String, String, Map<String, String>>
                                >
                x = mock(MapFactory.class);
            mockMapFactory = x;
        }
        {
            @SuppressWarnings("unchecked")
            MapBuilder<String, String, Map<String, String>> x = mock(MapBuilder.class);
            mockMapBuilder = x;
        }
        {
            @SuppressWarnings("unchecked")
            Map<String, String> x = mock(Map.class);
            mockMap = x;
        }
        {
            @SuppressWarnings("unchecked")
            RandomAccessList<JdkProperty> x = mock(RandomAccessList.class);
            mockJdkPropertyListA = x;
        }
        {
            @SuppressWarnings("unchecked")
            RandomAccessList<JdkProperty> x = mock(RandomAccessList.class);
            mockJdkPropertyListB = x;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesLoaderImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        new PropertiesLoaderImpl(
            mockJdkPropertiesLoaderHelper,
            mockIInputSource2Utils,
            mockPropertiesMerger,
            mockSLF4JMockLoggerFactory);
        verify(mockSLF4JMockLoggerFactory).getLogger(anyString());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesLoaderImpl.withPolicy()
    //

    @Test
    public void withPolicy_Pass() {
        assertSame(classUnderTest.withPolicy(), mockPropertiesLoaderPolicy);
        classUnderTest = classUnderTest.withPolicy(mockPropertiesLoaderPolicy2);
        assertSame(classUnderTest.withPolicy(), mockPropertiesLoaderPolicy2);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withPolicy_FailWithNull() {
        classUnderTest.withPolicy((PropertiesLoaderPolicy) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesLoaderImpl.load(List<InputSource2>, MapBuilderFactory<TMapBuilder>)
    //

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void load_FailWhenInputSourceInvalid()
    throws Exception {
        IllegalArgumentException exception = new IllegalArgumentException();
        doThrow(exception)
            .when(mockIInputSource2Utils).checkValid(inputSourceList, "inputSourceList");
        try {
            classUnderTest.load(inputSourceList, mockMapFactory);
        }
        catch (Exception e) {
            assertSame(e, exception);
            throw e;
        }
    }

    @Test(expectedExceptions = PropertiesLoaderException.class)
    public void load_FailWhen_JdkPropertiesLoaderHelper_loadPropertyList_throwsException()
    throws Exception {
        PropertiesLoaderException exception = new PropertiesLoaderException();
        when(mockJdkPropertiesLoaderHelper.loadPropertyList(mockInputSource2A))
            .thenThrow(exception);
        try {
            classUnderTest.load(inputSourceList, mockMapFactory);
        }
        catch (Exception e) {
            assertSame(e, exception);
            throw e;
        }
    }

    @Test(expectedExceptions = PropertiesLoaderException.class)
    public void load_FailWhen_PropertiesLoaderPolicy_apply_throwsException()
    throws Exception {
        PropertiesLoaderException exception = new PropertiesLoaderException();
        when(mockJdkPropertiesLoaderHelper.loadPropertyList(mockInputSource2A))
            .thenReturn(mockJdkPropertyListA);
        doThrow(exception).
            when(mockPropertiesLoaderPolicy).apply(mockJdkPropertyListA);
        try {
            classUnderTest.load(inputSourceList, mockMapFactory);
        }
        catch (Exception e) {
            assertSame(e, exception);
            throw e;
        }
    }

    @Test
    public void load_Pass()
    throws PropertiesLoaderException {
        when(mockMapFactory.builder()).thenReturn(mockMapBuilder);
        when(mockMapBuilder.build()).thenReturn(mockMap);
        when(mockJdkPropertiesLoaderHelper.loadPropertyList(mockInputSource2A))
            .thenReturn(mockJdkPropertyListA);
        when(mockJdkPropertiesLoaderHelper.loadPropertyList(mockInputSource2B))
            .thenReturn(mockJdkPropertyListB);
        Map<String, String> map = classUnderTest.load(inputSourceList, mockMapFactory);
        assertSame(map, mockMap);
        InOrder inOrder =
            inOrder(mockIInputSource2Utils, mockJdkPropertiesLoaderHelper, mockPropertiesMerger);
        inOrder.verify(mockIInputSource2Utils).checkValid(eq(inputSourceList), anyString());
        inOrder.verify(mockJdkPropertiesLoaderHelper).loadPropertyList(mockInputSource2A);
        inOrder.verify(mockPropertiesMerger).merge(mockMapBuilder, mockJdkPropertyListA);
        inOrder.verify(mockJdkPropertiesLoaderHelper).loadPropertyList(mockInputSource2B);
        inOrder.verify(mockPropertiesMerger).merge(mockMapBuilder, mockJdkPropertyListB);
    }
}
