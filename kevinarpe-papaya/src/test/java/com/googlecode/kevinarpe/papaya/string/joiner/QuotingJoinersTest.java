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
public class QuotingJoinersTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiners.withSeparator(String)
    //

    @Test
    public void onString_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    private void _assertDefaults(QuotingJoiner x) {
        Assert.assertEquals(x.withLeftQuote(), QuotingJoiners.DEFAULT_LEFT_QUOTE);
        Assert.assertEquals(x.withRightQuote(), QuotingJoiners.DEFAULT_RIGHT_QUOTE);
        Assert.assertEquals(x.useForNull(), QuotingJoiners.DEFAULT_NULL_TEXT);
        Assert.assertEquals(x.useForNoElements(), QuotingJoiners.DEFAULT_NO_ELEMENTS_TEXT);
        Assert.assertEquals(x.skipNulls(), QuotingJoiners.DEFAULT_SKIP_NULLS_FLAG);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void onString_FaillWithNull() {
        QuotingJoiners.withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiners.withSeparator(char)
    //

    @Test
    public void onChar_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(',');
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiners.newQuotingJoinerFactory(String)
    //

    @Test
    public void newQuotingJoinerFactoryString_Pass() {
        QuotingJoinerFactory x = QuotingJoiners.newQuotingJoinerFactory(",");
        Assert.assertNotNull(x);
        QuotingJoiner y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiners.newQuotingJoinerFactory(char)
    //

    @Test
    public void newQuotingJoinerFactoryChar_Pass() {
        QuotingJoinerFactory x = QuotingJoiners.newQuotingJoinerFactory(',');
        Assert.assertNotNull(x);
        QuotingJoiner y = x.newInstance();
        Assert.assertEquals(y.withSeparator(), ",");
        _assertDefaults(y);
    }
}
