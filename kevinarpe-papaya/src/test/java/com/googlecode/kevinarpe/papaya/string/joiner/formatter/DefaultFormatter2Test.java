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

public class DefaultFormatter2Test {

    private DefaultFormatter2 classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new DefaultFormatter2();
    }

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        return new Object[][] {
            { "abc", "abc" },
            { 123, "123" },
            { null, "null" }
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(Object input, String expected) {
        String actual = classUnderTest.format(input);
        assertEquals(actual, expected);
    }
}
