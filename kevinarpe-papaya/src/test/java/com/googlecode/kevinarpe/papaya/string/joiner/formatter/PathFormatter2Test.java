package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

/*
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;

public class PathFormatter2Test {

    private PathFormatter2 classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new PathFormatter2();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PathFormatter.format()
    //

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        final File relPath = new File("abc/def");
        final File absPath = new File("/abc/def");
        return new Object[][] {
            { (File) null, "null" },
            { relPath, String.format("'%s' -> '%s'", relPath.getPath(), relPath.getAbsolutePath()) },
            { absPath, String.format("'%s'", absPath.getPath()) },
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(Object value, String expectedResult) {
        String actualResult = classUnderTest.format(value);
        assertEquals(actualResult, expectedResult);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void format_FailWithInvalidType() {
        classUnderTest.format("abc");
    }
}
