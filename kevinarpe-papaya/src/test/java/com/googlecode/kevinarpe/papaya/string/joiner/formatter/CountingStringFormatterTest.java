package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class CountingStringFormatterTest {

    private static final String FORMAT = "%d: [%s]";

    private Formatter2 mockFormatter;
    private StringFormatterHelper mockStringFormatterHelper;
    private CountingStringFormatter classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockFormatter = mock(Formatter2.class);
        mockStringFormatterHelper = mock(StringFormatterHelper.class);
        classUnderTest =
            new CountingStringFormatter(FORMAT, mockFormatter, mockStringFormatterHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CountingFormatter.ctor()
    //

    @Test
    public void ctor_Pass() {
        CountingStringFormatter x = new CountingStringFormatter(FORMAT, mockFormatter);
        assertEquals(x.getFirstCount(), CountingStringFormatter.DEFAULT_FIRST_COUNT);
        assertEquals(x.getNextCount(), CountingStringFormatter.DEFAULT_FIRST_COUNT);
    }

    @DataProvider
    private static Object[][] _ctor_Fail_Data() {
        Formatter2 mockFormatter2 = mock(Formatter2.class);
        return new Object[][] {
            { (String) null, mockFormatter2, NullPointerException.class },
            { "abc", (Formatter2) null, NullPointerException.class },
            { "", mockFormatter2, IllegalArgumentException.class },
            { "   ", mockFormatter2, IllegalArgumentException.class },
        };
    }

    @Test(dataProvider = "_ctor_Fail_Data",
            expectedExceptions = Exception.class)
    public void ctor_Fail(
            String format, Formatter2 formatter, Class<? extends Exception> expectedExceptionClass)
    throws Exception {
        try {
            new CountingStringFormatter(format, formatter);
        }
        catch (Exception e) {
            assertSame(e.getClass(), expectedExceptionClass);
            throw e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CountingFormatter.format(Object)/.withFirstCount(int)
    //

    @Test
    public void format_PassWithDefaultFirstCount() {
        _format_Pass(classUnderTest, CountingStringFormatter.DEFAULT_FIRST_COUNT);
    }

    @Test
    public void format_PassWithNonDefaultFirstCount() {
        classUnderTest = classUnderTest.withFirstCount(42);
        _format_Pass(classUnderTest, 42);
    }

    private void _format_Pass(CountingStringFormatter classUnderTest2, int firstCount) {
        final String value = "value";
        final String value2 = "value2";
        when(mockFormatter.format(value)).thenReturn(value2);
        when(
            mockStringFormatterHelper.format(
                anyString(),
                eq(FORMAT),
                eq(firstCount),
                eq(value2)))
            .thenReturn("result");
        when(
            mockStringFormatterHelper.format(
                anyString(),
                eq(FORMAT),
                eq(1 + firstCount),
                eq(value2)))
            .thenReturn("result2");

        assertEquals(classUnderTest2.getFirstCount(), firstCount);
        assertEquals(classUnderTest2.getNextCount(), firstCount);

        assertEquals(classUnderTest2.format(value), "result");

        assertEquals(classUnderTest2.getFirstCount(), firstCount);
        assertEquals(classUnderTest2.getNextCount(), 1 + firstCount);

        assertEquals(classUnderTest2.format(value), "result2");

        assertEquals(classUnderTest2.getFirstCount(), firstCount);
        assertEquals(classUnderTest2.getNextCount(), 2 + firstCount);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void format_FailWhenFormatThrowsException() {
        RuntimeException aRuntimeException = new RuntimeException();
        when(mockStringFormatterHelper.format(anyString(), anyString(), anyInt(), anyString()))
            .thenThrow(aRuntimeException);
        assertEquals(classUnderTest.getNextCount(), CountingStringFormatter.DEFAULT_FIRST_COUNT);
        try {
            classUnderTest.format("value");
        }
        catch (RuntimeException e) {
            assertSame(e, aRuntimeException);
            // Demonstrate the counter does not increment when exception thrown
            assertEquals(
                classUnderTest.getNextCount(), CountingStringFormatter.DEFAULT_FIRST_COUNT);
            throw e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // CountingFormatter.toString()
    //

    @Test
    public void toString_Pass() {
        assertNotNull(classUnderTest.toString());
    }
}
