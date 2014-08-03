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

import com.googlecode.kevinarpe.papaya.input.IInputSource2Utils;
import com.googlecode.kevinarpe.papaya.input.InputSource2;
import com.googlecode.kevinarpe.papaya.jdk.properties.JavaPropertiesLoader;
import com.googlecode.kevinarpe.papaya.jdk.properties.JavaProperty;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class JavaPropertiesLoaderHelperImplTest {

    private JavaPropertiesLoader mockJavaPropertiesLoader;
    private IInputSource2Utils mockIInputSource2Utils;
    private JavaPropertiesLoaderHelperImpl classUnderTest;
    private InputSource2 mockInputSource2;
    private InputStream mockInputStream;
    private Reader mockReader;
    private List<JavaProperty> mockJavaPropertyList;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockJavaPropertiesLoader = mock(JavaPropertiesLoader.class);
        mockIInputSource2Utils = mock(IInputSource2Utils.class);
        classUnderTest =
            new JavaPropertiesLoaderHelperImpl(mockJavaPropertiesLoader, mockIInputSource2Utils);
        mockInputSource2 = mock(InputSource2.class);
        mockInputStream = mock(InputStream.class);
        mockReader = mock(Reader.class);
        {
            @SuppressWarnings("unchecked")
            List<JavaProperty> x = mock(List.class);
            mockJavaPropertyList = x;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkPropertiesLoaderHelperImpl.loadPropertyList(InputSource2)
    //

    @Test(expectedExceptions = PropertiesLoaderException.class)
    public void loadPropertyList_FailWhenJdkPropertiesLoader_loadInputStream_ThrowsException()
    throws IOException, PropertiesLoaderException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        IOException exception = new IOException();
        when(mockJavaPropertiesLoader.load(mockInputStream)).thenThrow(exception);
        try {
            classUnderTest.loadPropertyList(mockInputSource2);
        }
        catch (PropertiesLoaderException e) {
            assertSame(e.getCause(), exception);
            verify(mockIInputSource2Utils).closeQuietly(mockInputSource2);
            throw e;
        }
    }

    @Test(expectedExceptions = PropertiesLoaderException.class)
    public void loadPropertyList_FailWhenJdkPropertiesLoader_loadReader_ThrowsException()
    throws IOException, PropertiesLoaderException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        IOException exception = new IOException();
        when(mockJavaPropertiesLoader.load(mockReader)).thenThrow(exception);
        try {
            classUnderTest.loadPropertyList(mockInputSource2);
        }
        catch (PropertiesLoaderException e) {
            assertSame(e.getCause(), exception);
            verify(mockIInputSource2Utils).closeQuietly(mockInputSource2);
            throw e;
        }
    }

    @Test
    public void loadPropertyList_PassWhenInputSourceIsByteStream()
    throws IOException, PropertiesLoaderException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        when(mockJavaPropertiesLoader.load(mockInputStream)).thenReturn(mockJavaPropertyList);
        List<JavaProperty> javaPropertyList = classUnderTest.loadPropertyList(mockInputSource2);
        assertSame(javaPropertyList, mockJavaPropertyList);
        verify(mockIInputSource2Utils).closeQuietly(mockInputSource2);
    }

    @Test
    public void loadPropertyList_PassWhenInputSourceIsCharacterStream()
    throws IOException, PropertiesLoaderException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        when(mockJavaPropertiesLoader.load(mockReader)).thenReturn(mockJavaPropertyList);
        List<JavaProperty> javaPropertyList = classUnderTest.loadPropertyList(mockInputSource2);
        assertSame(javaPropertyList, mockJavaPropertyList);
        verify(mockIInputSource2Utils).closeQuietly(mockInputSource2);
    }
}
