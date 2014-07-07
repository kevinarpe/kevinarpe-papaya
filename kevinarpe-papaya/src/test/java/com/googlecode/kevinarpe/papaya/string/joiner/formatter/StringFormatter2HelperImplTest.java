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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

public class StringFormatter2HelperImplTest {

    private StringFormatterHelperImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new StringFormatterHelperImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatterHelperImpl.format()
    //

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        return new Object[][] {
            { "description", "[%s]", new Object[] { "abc" }, "[abc]" },
            { "description", "[%s, %d]", new Object[] { "abc", 1234 }, "[abc, 1234]" },
            { "description", "[%s, %.2f]", new Object[] { "abc", 1234.1234 }, "[abc, 1234.12]" },
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(
            String description, String format, Object[] argArr, String expectedResult) {
        String actualResult = classUnderTest.format(description, format, argArr);
        assertEquals(actualResult, expectedResult);
    }

    @DataProvider
    private static Object[][] _format_FailWhenFormatThrowsException_Data() {
        return new Object[][] {
            { "xyz", "%s", new Object[] { }, "Failed to format xyz: String.format([%s])" },
            { "xyz", "%d", new Object[] { "abc" }, "Failed to format xyz: String.format([%d], String: [abc])" },
            { "xyz", "%d", new Object[] { "abc", 9L }, "Failed to format xyz: String.format([%d], String: [abc], Long: 9)" },
        };
    }

    @Test(dataProvider = "_format_FailWhenFormatThrowsException_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void format_FailWhenFormatThrowsException(
            String description, String format, Object[] argArr, String expectedExceptionMessage) {
        try {
            classUnderTest.format(description, format, argArr);
        }
        catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), expectedExceptionMessage);
            throw e;
        }
    }

    @DataProvider
    private static Object[][] _format_FailWithInvalidArgs_Data() {
        return new Object[][] {
            { (String) null, "%s", new Object[] { "abc" }, NullPointerException.class },
            { "description", (String) null, new Object[] { "abc" }, NullPointerException.class },
            { "description", "%s", (Object[]) null, NullPointerException.class },
            { "", "%s", new Object[] { "abc" }, IllegalArgumentException.class },
            { "   ", "%s", new Object[] { "abc" }, IllegalArgumentException.class },
            { "description", "", new Object[] { "abc" }, IllegalArgumentException.class },
            { "description", "   ", new Object[] { "abc" }, IllegalArgumentException.class },
        };
    }

    @Test(dataProvider = "_format_FailWithInvalidArgs_Data",
            expectedExceptions = Exception.class)
    public void format_FailWithInvalidArgs(
            String description,
            String format,
            Object[] argArr,
            Class<? extends Exception> expectedExceptionClass)
    throws Exception {
        try {
            classUnderTest.format(description, format, argArr);
        }
        catch (Exception e) {
            assertSame(e.getClass(), expectedExceptionClass);
            throw e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatterHelperImpl.valueToString()
    //

    @DataProvider
    private static Object[][] _valueToString_Pass_Data() {
        return new Object[][] {
            { null, "null" },
            { 'a', "Character: 'a'" },
            { "a", "String: [a]" },
            { 9, "Integer: 9" },
            { true, "Boolean: true" },
        };
    }

    @Test(dataProvider = "_valueToString_Pass_Data")
    public void valueToString_Pass(Object value, String expectedResult) {
        String actualResult = classUnderTest.valueToString(value);
        assertEquals(actualResult, expectedResult);
    }
}
