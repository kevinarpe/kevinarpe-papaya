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
public class QuotingMapJoinerTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        QuotingMapJoiner y = x.withKeyValueSeparator("x").withKeyValueSeparator("y");
        Assert.assertEquals(y.withKeyValueSeparator(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparator_FailWithNull() {
        QuotingJoiner.on("x").withKeyValueSeparator("y").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        QuotingJoiner x = QuotingJoiner.on(",");
        QuotingMapJoiner y = x.withKeyValueSeparator("x").withKeyValueSeparator('y');
        Assert.assertEquals(y.withKeyValueSeparator(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withKeyQuotes(String, String)/withKeyQuotes(String, char)/withKeyQuotes(char, String)/withKeyQuotes(char, char)
    //

    @DataProvider
    private static Object[][] _withKeyQuotes_Pass_Data() {
        return new Object[][] {
            { "xyz", "abc" },
            { "", "abc" },
            { "xyz", "" },
            { "", "" },
            { "   ", "   " },
        };
    }

    @Test(dataProvider = "_withKeyQuotes_Pass_Data")
    public void withKeyQuotes_Pass(String leftQuote, String rightQuote) {
        // String, String
        QuotingMapJoiner x =
            QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withKeyLeftQuote(), leftQuote);
        Assert.assertEquals(x.withKeyRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withKeyLeftQuote(), leftQuote);
            Assert.assertEquals(x.withKeyRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withKeyQuotes(leftQuoteChar, rightQuote);
            Assert.assertEquals(x.withKeyLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withKeyRightQuote(), rightQuote);
        }

        // char, char
        if (!leftQuote.isEmpty() && !rightQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withKeyQuotes(leftQuoteChar, rightQuoteChar);
            Assert.assertEquals(x.withKeyLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withKeyRightQuote(), rightQuoteCharString);
        }
    }

    @DataProvider
    private static Object[][] _withKeyQuotes_FailWithNull_Data() {
        return new Object[][] {
            { (String) null, "xyz" },
            { "xyz", (String) null },
            { (String) null, (String) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_withKeyQuotes_FailWithNull_Data")
    public void withKeyQuotes_FailWithNull(String leftQuote, String rightQuote) {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuote);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withValueQuotes(String, String)/withValueQuotes(String, char)/withValueQuotes(char, String)/withValueQuotes(char, char)
    //

    @DataProvider
    private static Object[][] _withValueQuotes_Pass_Data() {
        return new Object[][] {
            { "xyz", "abc" },
            { "", "abc" },
            { "xyz", "" },
            { "", "" },
            { "   ", "   " },
        };
    }

    @Test(dataProvider = "_withValueQuotes_Pass_Data")
    public void withValueQuotes_Pass(String leftQuote, String rightQuote) {
        // String, String
        QuotingMapJoiner x =
            QuotingJoiner.on(",").withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withValueLeftQuote(), leftQuote);
        Assert.assertEquals(x.withValueRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withValueLeftQuote(), leftQuote);
            Assert.assertEquals(x.withValueRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withValueQuotes(leftQuoteChar, rightQuote);
            Assert.assertEquals(x.withValueLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withValueRightQuote(), rightQuote);
        }

        // char, char
        if (!leftQuote.isEmpty() && !rightQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiner.on(",")
                .withKeyValueSeparator("x").withValueQuotes(leftQuoteChar, rightQuoteChar);
            Assert.assertEquals(x.withValueLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withValueRightQuote(), rightQuoteCharString);
        }
    }

    @DataProvider
    private static Object[][] _withValueQuotes_FailWithNull_Data() {
        return new Object[][] {
            { (String) null, "xyz" },
            { "xyz", (String) null },
            { (String) null, (String) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_withValueQuotes_FailWithNull_Data")
    public void withValueQuotes_FailWithNull(String leftQuote, String rightQuote) {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuote);
    }

    // TODO: LAST
}
