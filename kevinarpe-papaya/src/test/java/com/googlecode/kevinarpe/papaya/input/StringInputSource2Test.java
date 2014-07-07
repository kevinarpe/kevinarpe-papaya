package com.googlecode.kevinarpe.papaya.input;

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

import com.google.common.io.CharStreams;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class StringInputSource2Test {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringInputSource2.ctor()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new StringInputSource2((String) null);
    }

    @DataProvider
    private static Object[][] _ctor_Pass_Data() {
        return new Object[][] {
            { "abc" },
            { "" },
            { "   " },
            { String.format("abc%ndef%nghi") },
        };
    }

    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(String text)
    throws IOException {
        StringInputSource2 classUnderTest = new StringInputSource2(text);
        assertNull(classUnderTest.getByteStream());
        assertNotNull(classUnderTest.getCharacterStream());
        String output = CharStreams.toString(classUnderTest.getCharacterStream());
        assertEquals(output, text);
        assertNotNull(classUnderTest.toString());
    }
}
