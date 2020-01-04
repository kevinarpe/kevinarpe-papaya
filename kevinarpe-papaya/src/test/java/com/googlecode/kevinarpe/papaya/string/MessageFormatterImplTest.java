package com.googlecode.kevinarpe.papaya.string;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MessageFormatterImplTest {

    private MessageFormatterImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = MessageFormatterImpl.INSTANCE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MessageFormatterImpl.format()
    //

    @DataProvider
    private static Object[][]
    _format_Pass_Data() {
        return new Object[][] {
            { "", new Object[0],
                ""},
            { " ", new Object[0],
                " "},
            { "\n", new Object[0],
                "\n"},
            { "\t", new Object[0],
                "\t"},
            { "abc", new Object[0],
                "abc"},
            { "abc", new Object[] { 123, "def" },
                "abc"},
            { "abc[%d][%s]", new Object[] { 123, "def" },
                "abc[123][def]"},
            { ">%f<", new Object[] {"abc", 123},
                String.format("Failed to format message: [>%%f<]%n\tArg[0]: (String) [abc]%n\tArg[1]: (Integer) [123]")},
            { ">%f<", new Object[] {"abc", null},
                String.format("Failed to format message: [>%%f<]%n\tArg[0]: (String) [abc]%n\tArg[1]: null")},
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void
    format_Pass(String format, Object[] argArr, String expectedResult) {

        final String x = classUnderTest.format(format, argArr);
        Assert.assertEquals(x, expectedResult);
    }

    @DataProvider
    public static Object[][]
    _format_FailNullPointerException_Data() {
        return new Object[][] {
            { (String) null, new Object[0] },
            { (String) null, (Object[]) null },
            { (String) null, new Object[] {"abc"} },
            { (String) null, new Object[] {"abc", 123} },
            { "abc", (Object[]) null },
            { "abc%s", (Object[]) null },
        };
    }

    @Test(dataProvider = "_format_FailNullPointerException_Data", expectedExceptions = NullPointerException.class)
    public void
    format_FailNullPointerException(String format, Object[] argArr) {

        final String x = classUnderTest.format(format, argArr);
        Assert.assertTrue(false, "Unreachable code");
    }
}
