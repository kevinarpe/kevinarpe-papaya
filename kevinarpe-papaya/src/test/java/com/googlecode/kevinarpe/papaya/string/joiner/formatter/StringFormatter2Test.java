package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class StringFormatter2Test {

    private static final String FORMAT = "[%s]";

    private StringFormatterHelper mockStringFormatterHelper;
    private StringFormatter2 classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockStringFormatterHelper = mock(StringFormatterHelper.class);
        classUnderTest = new StringFormatter2(FORMAT, mockStringFormatterHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.ctor()
    //

    @Test
    public void ctor_Pass() {
        new StringFormatter2(FORMAT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.format(Object)
    //

    @Test
    public void format_Pass() {
        String value = "value";
        when(mockStringFormatterHelper.format(anyString(), eq(FORMAT), eq(value)))
            .thenReturn("result");
        assertEquals(classUnderTest.format(value), "result");
    }
}
