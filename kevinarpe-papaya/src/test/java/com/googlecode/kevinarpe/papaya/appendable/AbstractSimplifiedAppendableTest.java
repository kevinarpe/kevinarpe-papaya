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
import org.testng.annotations.Test;

import java.io.IOException;

public class AbstractSimplifiedAppendableTest {

    private Appendable mockAppendable;

    private AbstractSimplifiedAppendable classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockAppendable = Mockito.mock(Appendable.class);
        final Appendable mockAppendable2 = mockAppendable;
        classUnderTest = new AbstractSimplifiedAppendable() {
            @Override
            public Appendable append(CharSequence csq)
            throws IOException {
                Appendable appendable = mockAppendable2.append(csq);
                return appendable;
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSimplifiedAppendable.append(CharSequence, int, int)
    //

    @Test
    public void appendCharSequenceIntInt_Pass()
    throws IOException {
        Mockito.when(mockAppendable.append("def")).thenReturn(mockAppendable);
        Assert.assertSame(classUnderTest.append("abcdefghi", 3, 6), mockAppendable);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void appendCharSequenceIntInt_FailWithNull()
    throws IOException {
        Assert.assertSame(classUnderTest.append((CharSequence) null, -1, 6), mockAppendable);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void appendCharSequenceIntInt_FailWithIndexOutOfBoundsException()
    throws IOException {
        Assert.assertSame(classUnderTest.append("abcdefghi", -1, 6), mockAppendable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractSimplifiedAppendable.append(char)
    //

    @Test
    public void appendChar_Pass()
    throws IOException {
        Mockito.when(mockAppendable.append("c")).thenReturn(mockAppendable);
        Assert.assertSame(classUnderTest.append('c'), mockAppendable);
    }
}
