package com.googlecode.kevinarpe.papaya.input;

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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class InputSource2UtilsTest {

    private InputSource2Utils classUnderTest;
    private InputSource2 mockInputSource2;
    private InputStream mockInputStream;
    private Reader mockReader;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new InputSource2Utils();
        mockInputSource2 = mock(InputSource2.class);
        mockInputStream = mock(InputStream.class);
        mockReader = mock(Reader.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // InputSource2Utils.checkValid(InputSource2, String)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void checkValidInputSource2_FailWhenNull() {
        classUnderTest.checkValid((InputSource2) null, "dummy");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidInputSource2_FailWhenBothStreamsNull() {
        classUnderTest.checkValid(mockInputSource2, "dummy");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidInputSource2_FailWhenBothStreamsNonNull() {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.checkValid(mockInputSource2, "dummy");
    }

    @Test
    public void checkValidInputSource2_PassWhenByteStream() {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        classUnderTest.checkValid(mockInputSource2, "dummy");
    }

    @Test
    public void checkValidInputSource2_PassWhenCharacterStream() {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.checkValid(mockInputSource2, "dummy");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // InputSource2Utils.checkValid(Collection<InputSource2>, String)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void checkValidCollectionInputSource2_FailWhenNull() {
        classUnderTest.checkValid((Collection<InputSource2>) null, "dummy");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidCollectionInputSource2_FailWhenEmpty() {
        classUnderTest.checkValid(new ArrayList<InputSource2>(), "dummy");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidCollectionInputSource2_FailWhenBothStreamsNull() {
        classUnderTest.checkValid(Arrays.asList(mockInputSource2), "dummy");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkValidCollectionInputSource2_FailWhenBothStreamsNonNull() {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.checkValid(Arrays.asList(mockInputSource2), "dummy");
    }

    @Test
    public void checkValidCollectionInputSource2_PassWhenByteStream() {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        classUnderTest.checkValid(Arrays.asList(mockInputSource2), "dummy");
    }

    @Test
    public void checkValidCollectionInputSource2_PassWhenCharacterStream() {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.checkValid(Arrays.asList(mockInputSource2), "dummy");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // InputSource2Utils.close(InputSource2)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void close_FailWhenNull()
    throws IOException {
        classUnderTest.close((InputSource2) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void close_FailWhenBothStreamsNull()
    throws IOException {
        classUnderTest.close(mockInputSource2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void close_FailWhenBothStreamsNonNull()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.close(mockInputSource2);
    }

    @Test(expectedExceptions = IOException.class)
    public void close_FailWhenByteStreamThrowsException()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        IOException exception = new IOException();
        doThrow(exception).when(mockInputStream).close();
        try {
            classUnderTest.close(mockInputSource2);
        }
        catch (IOException e) {
            assertSame(e.getCause(), exception);
            throw e;
        }
    }

    @Test(expectedExceptions = IOException.class)
    public void close_FailWhenCharacterStreamThrowsException()
    throws IOException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        IOException exception = new IOException();
        doThrow(exception).when(mockReader).close();
        try {
            classUnderTest.close(mockInputSource2);
        }
        catch (IOException e) {
            assertSame(e.getCause(), exception);
            throw e;
        }
    }

    @Test
    public void close_PassWhenByteStream()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        classUnderTest.close(mockInputSource2);
        verify(mockInputStream).close();
    }

    @Test
    public void close_PassWhenCharacterStream()
    throws IOException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.close(mockInputSource2);
        verify(mockReader).close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // InputSource2Utils.closeQuietly(InputSource2)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void closeQuietly_FailWhenNull()
    throws IOException {
        classUnderTest.closeQuietly((InputSource2) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void closeQuietly_FailWhenBothStreamsNull()
    throws IOException {
        classUnderTest.closeQuietly(mockInputSource2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void closeQuietly_FailWhenBothStreamsNonNull()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.closeQuietly(mockInputSource2);
    }

    @Test
    public void closeQuietly_PassWhenByteStreamThrowsException()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        IOException exception = new IOException();
        doThrow(exception).when(mockInputStream).close();
        classUnderTest.closeQuietly(mockInputSource2);
    }

    @Test
    public void closeQuietly_PassWhenCharacterStreamThrowsException()
    throws IOException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        IOException exception = new IOException();
        doThrow(exception).when(mockReader).close();
        classUnderTest.closeQuietly(mockInputSource2);
    }

    @Test
    public void closeQuietly_PassWhenByteStream()
    throws IOException {
        when(mockInputSource2.getByteStream()).thenReturn(mockInputStream);
        classUnderTest.closeQuietly(mockInputSource2);
        verify(mockInputStream).close();
    }

    @Test
    public void closeQuietly_PassWhenCharacterStream()
    throws IOException {
        when(mockInputSource2.getCharacterStream()).thenReturn(mockReader);
        classUnderTest.closeQuietly(mockInputSource2);
        verify(mockReader).close();
    }
}
