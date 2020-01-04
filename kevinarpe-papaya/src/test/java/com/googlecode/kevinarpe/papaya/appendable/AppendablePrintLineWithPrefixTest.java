package com.googlecode.kevinarpe.papaya.appendable;

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

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintStream;

public class AppendablePrintLineWithPrefixTest {

    private static final String PREFIX = "prefix";

    private PrintStream mockPrintStream;

    private AppendablePrintLineWithPrefix classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockPrintStream = Mockito.mock(PrintStream.class);
        classUnderTest = new AppendablePrintLineWithPrefix(mockPrintStream, PREFIX);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AppendablePrintLineWithPrefix.ctor(PrintStream, String)
    //

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        PrintStream mockPrintStream2 = Mockito.mock(PrintStream.class);

        return new Object[][] {
            { (PrintStream) null, (String) null },
            { mockPrintStream2, (String) null },
            { (PrintStream) null, "abc" },
        };
    }

    @Test(dataProvider = "_ctor_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull(PrintStream stream, String prefix) {
        new AppendablePrintLineWithPrefix(stream, prefix);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AppendablePrintLineWithPrefix.
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void appendCharSequence_FailWithNull()
    throws IOException {
        classUnderTest.append((CharSequence) null);
    }

    @Test
    public void appendCharSequence_Pass()
    throws IOException {
        Assert.assertSame(classUnderTest.append("abc"), classUnderTest);
        Mockito.verify(mockPrintStream).println((CharSequence) (PREFIX + "abc"));
    }
}
