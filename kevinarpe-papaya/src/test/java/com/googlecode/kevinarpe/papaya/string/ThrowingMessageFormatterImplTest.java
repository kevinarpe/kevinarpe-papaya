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

import javax.annotation.Nullable;

public class ThrowingMessageFormatterImplTest {

    private ThrowingMessageFormatterImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = ThrowingMessageFormatterImpl.INSTANCE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ThrowingMessageFormatterImpl.format()
    //

    @DataProvider
    private static Object[][]
    _format_Pass_Data() {
        return new Object[][] {
            { "abc", new Object[0],
                "abc"},
            { "abc", new Object[] { 123, "def" },
                "abc"},
            { "abc[%d][%s]", new Object[] { 123, "def" },
                "abc[123][def]"},
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void
    format_Pass(String format,
                Object[] argArr,
                @Nullable String expectedResult) {
        try {
            final String x = classUnderTest.format(format, argArr);
            Assert.assertEquals(x, expectedResult);
        }
        catch (IllegalArgumentException e) {
            if (null != expectedResult) {
                throw new IllegalStateException("Unreachable code");
            }
        }
    }

    @DataProvider
    private static Object[][]
    _format_FailNullPointerException_Data() {
        return MessageFormatterImplTest._format_FailNullPointerException_Data();
    }

    @Test(dataProvider = "_format_FailNullPointerException_Data", expectedExceptions = NullPointerException.class)
    public void
    format_FailNullPointerException(String format, Object[] argArr) {

        final String x = classUnderTest.format(format, argArr);
        Assert.assertTrue(false, "Unreachable code");
    }

    @DataProvider
    private static Object[][]
    _format_FailIllegalArgumentException_Data() {
        return new Object[][] {
            { ">%f<", new Object[] {"abc"} },
            { ">%f<", new Object[] {"abc", 123} },
            { ">%f<", new Object[] {"abc", null} },
        };
    }

    @Test(dataProvider = "_format_FailIllegalArgumentException_Data", expectedExceptions = IllegalArgumentException.class)
    public void
    format_FailIllegalStateException(String format, Object[] argArr) {

        final String x = classUnderTest.format(format, argArr);
    }
}
