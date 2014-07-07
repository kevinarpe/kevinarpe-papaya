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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class AbstractStringFormatter2Test {

    private static class _AbstractStringFormatter2
    extends AbstractStringFormatter2 {

        protected _AbstractStringFormatter2(String format) {
            super(format);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractStringFormatter.ctor(String)
    //

    @Test
    public void ctor_Pass() {
        assertEquals("abc", new _AbstractStringFormatter2("abc").getFormat());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new _AbstractStringFormatter2((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithEmpty() {
        new _AbstractStringFormatter2("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithOnlyWhitespace() {
        new _AbstractStringFormatter2("   ");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractStringFormatter.toString()
    //

    @Test
    public void toString_Pass() {
        assertNotNull(new _AbstractStringFormatter2("abc").toString());
    }
}
