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

import com.google.common.collect.ImmutableMap;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class QuotingMapJoinerTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        QuotingMapJoiner j =
            QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyValueSeparator("y");
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparatorString_FailWithNull() {
        QuotingJoiner.on("x").withKeyValueSeparator("y").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        QuotingMapJoiner j =
            QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyValueSeparator('y');
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
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

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyQuotes2_FailWithNull() {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyQuotes((String) null, 'a');
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyQuotes3_FailWithNull() {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withKeyQuotes('a', (String) null);
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

    @Test(expectedExceptions = NullPointerException.class)
    public void withValueQuotes2_FailWithNull() {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withValueQuotes((String) null, 'a');
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withValueQuotes3_FailWithNull() {
        QuotingJoiner.on(",").withKeyValueSeparator("x").withValueQuotes('a', (String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.useForNullKey(String)
    //

    @Test
    public void useForNullKeyString_Pass() {
        QuotingMapJoiner j = QuotingJoiner.on(",").withKeyValueSeparator("x").useForNullKey("y");
        Assert.assertEquals(j.useForNullKey(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullKeyString_FailWithNull() {
        QuotingJoiner.on("x").withKeyValueSeparator("y").useForNullKey((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.useForNullKey(char)
    //

    @Test
    public void useForNullKeyChar_Pass() {
        QuotingMapJoiner j = QuotingJoiner.on(",").withKeyValueSeparator("x").useForNullKey('y');
        Assert.assertEquals(j.useForNullKey(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.useForNullValue(String)
    //

    @Test
    public void useForNullValueString_Pass() {
        QuotingMapJoiner j = QuotingJoiner.on(",").withKeyValueSeparator("x").useForNullValue("y");
        Assert.assertEquals(j.useForNullValue(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullValueString_FailWithNull() {
        QuotingJoiner.on("x").withKeyValueSeparator("y").useForNullValue((String) null);
    }

    // TODO: Test all the items from QJSettings interface

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.useForNullValue(char)
    //

    @Test
    public void useForNullValueChar_Pass() {
        QuotingMapJoiner j = QuotingJoiner.on(",").withKeyValueSeparator("x").useForNullValue('y');
        Assert.assertEquals(j.useForNullValue(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingMapJoiner.appendTo(Appendable, *)
    // QuotingMapJoiner.appendTo(StringBuilder, *)/join(*) [pass only]
    //

    @DataProvider
    private static Object[][] _appendTo_Pass_Data() {
        return new Object[][] {
            { ",", "=", null, null, null, null, null, null, null, null, null, ImmutableMap.of(), "" },
            { ",", "=", null, null, null, null, null, null, null, null, null, ImmutableMap.of("a", "1"), "a=1" },
            { ",", "=", null, null, null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "a=1,b=2" },
            { ",", "=", null, null, null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "a=1,b=2,c=3" },

            { ",", "=", "[", "]", null, null, null, null, null, null, null, ImmutableMap.of(), "" },
            { ",", "=", "[", "]", null, null, null, null, null, null, null, ImmutableMap.of("a", "1"), "[a=1]" },
            { ",", "=", "[", "]", null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[a=1],[b=2]" },
            { ",", "=", "[", "]", null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[a=1],[b=2],[c=3]" },

            { ",", "=", "[", "]", "(empty)", null, null, null, null, null, null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", null, null, null, null, null, null, ImmutableMap.of("a", "1"), "[a=1]" },
            { ",", "=", "[", "]", "(empty)", null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[a=1],[b=2]" },
            { ",", "=", "[", "]", "(empty)", null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[a=1],[b=2],[c=3]" },

            { ",", "=", "[", "]", "(empty)", "(", ")", null, null, null, null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", null, null, null, null, ImmutableMap.of("a", "1"), "[(a)=1]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[(a)=1],[(b)=2]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)=1],[(b)=2],[(c)=3]" },

            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },

            // TODO: Create builders like ImmutableMap.of(*), but do not restrict null key/value

            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", null, "2"), "[(a)={1}],[(nullKey)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[(nullKey)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[(nullKey)={4}]" },

            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", null, "2"), "[(a)={1}],[(nullKey)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[(nullKey)={2}],[(b)={nullValue}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[(nullKey)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[(nullKey)={3}],[(c)={nullValue}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[(nullKey)={4}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[(nullKey)={4}],[(d)={nullValue}]" },
        };
    }

    private static Map<String, String> _mapOf(String... arr) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < arr.length; i += 2) {
            if (map.containsKey(arr[i])) {
                throw new IllegalArgumentException(String.format(
                    "Duplicate key '%s': %s", arr[i], Arrays.toString(arr)));
            }
            map.put(arr[i], arr[1 + i]);
        }
        return map;
    }

    // TODO: LAST
    @Test(dataProvider = "_appendTo_Pass_Data")
    public void appendTo_Pass(
            String separator,
            String keyValueSeparator,
            String optLeftQuote,
            String optRightQuote,
            String optNoElementsText,
            String optKeyLeftQuote,
            String optKeyRightQuote,
            String optValueLeftQuote,
            String optValueRightQuote,
            String optKeyNullText,
            String optValueNullText,
            Map<String, String> map,
            String expectedResult)
    throws IOException {
        Assert.assertTrue(
            (null == optLeftQuote && null == optRightQuote)
            || (null != optLeftQuote && null != optRightQuote));
        Assert.assertTrue(
            (null == optKeyLeftQuote && null == optKeyRightQuote)
            || (null != optKeyLeftQuote && null != optKeyRightQuote));
        Assert.assertTrue(
            (null == optValueLeftQuote && null == optValueRightQuote)
            || (null != optValueLeftQuote && null != optValueRightQuote));
        QuotingJoiner joiner = QuotingJoiner.on(separator);
        QuotingMapJoiner mapJoiner = joiner.withKeyValueSeparator(keyValueSeparator);
        if (null != optLeftQuote && null != optRightQuote) {
            mapJoiner = mapJoiner.withQuotes(optLeftQuote, optRightQuote);
        }
        if (null != optNoElementsText) {
            mapJoiner = mapJoiner.useForNoElements(optNoElementsText);
        }
        if (null != optKeyLeftQuote && null != optKeyRightQuote) {
            mapJoiner = mapJoiner.withKeyQuotes(optKeyLeftQuote, optKeyRightQuote);
        }
        if (null != optValueLeftQuote && null != optValueRightQuote) {
            mapJoiner = mapJoiner.withValueQuotes(optValueLeftQuote, optValueRightQuote);
        }
        if (null != optKeyNullText) {
            mapJoiner = mapJoiner.useForNullKey(optKeyNullText);
        }
        if (null != optValueNullText) {
            mapJoiner = mapJoiner.useForNullValue(optValueNullText);
        }
        _core_appendTo1_Pass(mapJoiner, map, expectedResult);
        _core_appendTo2_Pass(mapJoiner, map, expectedResult);
        _core_appendTo3_Pass(mapJoiner, map, expectedResult);
    }

    private void _core_appendTo1_Pass(
            QuotingMapJoiner mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map);
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable = mapJoiner.appendTo((Appendable) new StringBuilder(), map);
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map);
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendTo2_Pass(
            QuotingMapJoiner mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map.entrySet());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable = mapJoiner.appendTo((Appendable) new StringBuilder(), map.entrySet());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map.entrySet());
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendTo3_Pass(
            QuotingMapJoiner mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map.entrySet().iterator());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            mapJoiner.appendTo((Appendable) new StringBuilder(), map.entrySet().iterator());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map.entrySet().iterator());
        Assert.assertEquals(actualResult, expectedResult);
    }
}
