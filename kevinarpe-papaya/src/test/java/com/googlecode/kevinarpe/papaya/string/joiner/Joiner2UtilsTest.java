package com.googlecode.kevinarpe.papaya.string.joiner;

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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Joiner2UtilsTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        Joiner2 x = Joiner2Utils.withSeparator(",");
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    private void _assertDefaults(Joiner2 x) {
        Assert.assertEquals(x.withLeftQuote(), Joiner2Utils.DEFAULT_LEFT_QUOTE);
        Assert.assertEquals(x.withRightQuote(), Joiner2Utils.DEFAULT_RIGHT_QUOTE);
        Assert.assertEquals(x.useForNull(), Joiner2Utils.DEFAULT_NULL_TEXT);
        Assert.assertEquals(x.useForNoElements(), Joiner2Utils.DEFAULT_NO_ELEMENTS_TEXT);
        Assert.assertEquals(x.skipNulls(), Joiner2Utils.DEFAULT_SKIP_NULLS_FLAG);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparatorString_FaillWithNull() {
        Joiner2Utils.withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        Joiner2 x = Joiner2Utils.withSeparator(',');
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.newQuotingJoinerFactory(String)
    //

    @Test
    public void newQuotingJoinerFactoryString_Pass() {
        Joiner2Factory x = Joiner2Utils.newQuotingJoinerFactory(",");
        Assert.assertNotNull(x);
        Joiner2 y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void newQuotingJoinerFactoryString_FailWithNull() {
        Joiner2Utils.newQuotingJoinerFactory((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Joiner2Utils.newQuotingJoinerFactory(char)
    //

    @Test
    public void newQuotingJoinerFactoryChar_Pass() {
        Joiner2Factory x = Joiner2Utils.newQuotingJoinerFactory(',');
        Assert.assertNotNull(x);
        Joiner2 y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }
}