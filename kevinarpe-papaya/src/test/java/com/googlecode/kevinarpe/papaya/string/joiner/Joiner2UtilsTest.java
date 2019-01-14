package com.googlecode.kevinarpe.papaya.string.joiner;

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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Joiner2UtilsTest {

    private Joiner2Utils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new Joiner2Utils();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        Joiner2 x = classUnderTest.withSeparator(",");
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    private void _assertDefaults(Joiner2 x) {
        Assert.assertEquals(x.withElementFormatter(), Joiner2Utils.DEFAULT_ELEMENT_FORMATTER);
        Assert.assertEquals(x.useForNull(), Joiner2Utils.DEFAULT_NULL_TEXT);
        Assert.assertEquals(x.useForNoElements(), Joiner2Utils.DEFAULT_NO_ELEMENTS_TEXT);
        Assert.assertEquals(x.skipNulls(), Joiner2Utils.DEFAULT_SKIP_NULLS_FLAG);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparatorString_FaillWithNull() {
        classUnderTest.withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        Joiner2 x = classUnderTest.withSeparator(',');
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.newJoiner2Factory(String)
    //

    @Test
    public void newQuotingJoinerFactoryString_Pass() {
        Joiner2Factory x = classUnderTest.newJoiner2Factory(",");
        Assert.assertNotNull(x);
        Joiner2 y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void newQuotingJoinerFactoryString_FailWithNull() {
        classUnderTest.newJoiner2Factory((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.newJoiner2Factory(char)
    //

    @Test
    public void newQuotingJoinerFactoryChar_Pass() {
        Joiner2Factory x = classUnderTest.newJoiner2Factory(',');
        Assert.assertNotNull(x);
        Joiner2 y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }
}
