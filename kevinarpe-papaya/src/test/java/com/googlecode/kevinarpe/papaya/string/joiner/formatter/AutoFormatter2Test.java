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

public class AutoFormatter2Test {

    private AutoFormatter2 classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new AutoFormatter2();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AutoFormatter2.format()
    //

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        Object x = new Object();
        return new Object[][] {
            { null, StringFormatterHelperImpl.NULL_VALUE_AS_STRING },
            { "abc", "\"abc\"" },
            { 'Q', "'Q'"},
            { Boolean.TRUE, "true"},
            { 123, "123" },
            { 123.456, "123.456" },
            { x, "{" + x + "}" }
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(Object value, String expected) {
        String actual = classUnderTest.format(value);
        assertEquals(actual, expected);
    }
}
