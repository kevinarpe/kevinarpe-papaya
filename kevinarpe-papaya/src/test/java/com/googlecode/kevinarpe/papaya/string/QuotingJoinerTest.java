package com.googlecode.kevinarpe.papaya.string;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class QuotingJoinerTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.on(String)
    //

    @Test
    public void onString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    private void _assertDefaults(QuotingJoiner x) {
        Assert.assertEquals(x.withLeftQuote(), QuotingJoiner.DEFAULT_LEFT_QUOTE);
        Assert.assertEquals(x.withRightQuote(), QuotingJoiner.DEFAULT_RIGHT_QUOTE);
        Assert.assertEquals(x.useForNull(), QuotingJoiner.DEFAULT_NULL_TEXT);
        Assert.assertEquals(x.useForEmpty(), QuotingJoiner.DEFAULT_EMPTY_TEXT);
        Assert.assertEquals(x.skipNulls(), QuotingJoiner.DEFAULT_SKIP_NULLS);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void onString_FaillWithNull() {
        QuotingJoiner.on((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.on(char)
    //

    @Test
    public void onChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(',');
        Assert.assertNotNull(x);
        Assert.assertEquals(x.withSeparator(), ",");
        _assertDefaults(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        x = x.withSeparator("x");
        Assert.assertEquals(x.withSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparator_FailWithNull() {
        QuotingJoiner.on("x").withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(',');
        x = x.withSeparator('x');
        Assert.assertEquals(x.withSeparator(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.withQuotes(String, String)/withQuotes(String, char)/withQuotes(char, String)/withQuotes(char, char)
    //

    @DataProvider
    private static Object[][] _withQuotes_Pass_Data() {
        return new Object[][] {
            { "xyz", "abc" },
            { "", "abc" },
            { "xyz", "" },
            { "", "" },
            { "   ", "   " },
        };
    }

    @Test(dataProvider = "_withQuotes_Pass_Data")
    public void withQuotes_Pass(String leftQuote, String rightQuote) {
        // String, String
        QuotingJoiner x = QuotingJoiner.on(",").withQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withLeftQuote(), leftQuote);
        Assert.assertEquals(x.withRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",").withQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withLeftQuote(), leftQuote);
            Assert.assertEquals(x.withRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = QuotingJoiner.on(",").withQuotes(leftQuoteChar, rightQuote);
            Assert.assertEquals(x.withLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withRightQuote(), rightQuote);
        }

        // char, char
        if (!leftQuote.isEmpty() && !rightQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",").withQuotes(leftQuoteChar, rightQuoteChar);
            Assert.assertEquals(x.withLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withRightQuote(), rightQuoteCharString);
        }
    }

    @DataProvider
    private static Object[][] _withQuotes_FailWithNull_Data() {
        return new Object[][] {
            { (String) null, "xyz" },
            { "xyz", (String) null },
            { (String) null, (String) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_withQuotes_FailWithNull_Data")
    public void withQuotes_FailWithNull(String leftQuote, String rightQuote) {
        QuotingJoiner.on(",").withQuotes(leftQuote, rightQuote);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.useForEmpty(String)
    //

    @Test
    public void useForEmptyString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        x = x.useForEmpty("x");
        Assert.assertEquals(x.useForEmpty(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForEmpty_FailWithNull() {
        QuotingJoiner.on("x").useForEmpty((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.useForEmpty(char)
    //

    @Test
    public void useForEmptyChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(',');
        x = x.useForEmpty('x');
        Assert.assertEquals(x.useForEmpty(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.useForNull(String)
    //

    // TODO: Wrtie test when joining null value but useForNull() is unset

    @Test
    public void useForNullString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        x = x.useForNull("x");
        Assert.assertEquals(x.useForNull(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNull_FailWithNull() {
        QuotingJoiner.on("x").useForNull((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.useForNull(char)
    //

    @Test
    public void useForNullChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(',');
        x = x.useForNull('x');
        Assert.assertEquals(x.useForNull(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.skipNulls(boolean)
    //

    @Test
    public void skipNulls_Pass() {
        QuotingJoiner x = QuotingJoiner.on(',');
        x = x.skipNulls(true);
        Assert.assertEquals(x.skipNulls(), true);
        x = x.skipNulls(false);
        Assert.assertEquals(x.skipNulls(), false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        QuotingMapJoiner y = x.withKeyValueSeparator("x");
        Assert.assertEquals(y.withKeyValueSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparator_FailWithNull() {
        QuotingJoiner.on("x").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoiner.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        QuotingMapJoiner y = x.withKeyValueSeparator('x');
        Assert.assertEquals(y.withKeyValueSeparator(), "x");
        Assert.assertEquals(y.withKeyLeftQuote(), QuotingMapJoiner.DEFAULT_LEFT_KEY_QUOTE);
        Assert.assertEquals(y.withKeyRightQuote(), QuotingMapJoiner.DEFAULT_RIGHT_KEY_QUOTE);
        Assert.assertEquals(y.withValueLeftQuote(), QuotingMapJoiner.DEFAULT_LEFT_VALUE_QUOTE);
        Assert.assertEquals(y.withValueRightQuote(), QuotingMapJoiner.DEFAULT_RIGHT_VALUE_QUOTE);
    }
}
