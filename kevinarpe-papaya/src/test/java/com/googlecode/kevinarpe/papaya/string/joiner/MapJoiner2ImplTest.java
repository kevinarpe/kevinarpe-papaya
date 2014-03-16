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
import com.google.common.collect.Iterators;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class MapJoiner2ImplTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        MapJoiner2 j =
            Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyValueSeparator("y");
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparatorString_FailWithNull() {
        Joiner2Utils.withSeparator("x").withKeyValueSeparator("y").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        MapJoiner2 j =
            Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyValueSeparator('y');
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyQuotes(String, String)/withKeyQuotes(String, char)/withKeyQuotes(char, String)/withKeyQuotes(char, char)
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
        MapJoiner2 x =
            Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withKeyLeftQuote(), leftQuote);
        Assert.assertEquals(x.withKeyRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = Joiner2Utils.withSeparator(",")
                .withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withKeyLeftQuote(), leftQuote);
            Assert.assertEquals(x.withKeyRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = Joiner2Utils.withSeparator(",")
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
            x = Joiner2Utils.withSeparator(",")
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
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyQuotes(leftQuote, rightQuote);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyQuotes2_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyQuotes((String) null, 'a');
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyQuotes3_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withKeyQuotes('a', (String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withValueQuotes(String, String)/withValueQuotes(String, char)/withValueQuotes(char, String)/withValueQuotes(char, char)
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
        MapJoiner2 x =
            Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withValueLeftQuote(), leftQuote);
        Assert.assertEquals(x.withValueRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = Joiner2Utils.withSeparator(",")
                .withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withValueLeftQuote(), leftQuote);
            Assert.assertEquals(x.withValueRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = Joiner2Utils.withSeparator(",")
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
            x = Joiner2Utils.withSeparator(",")
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
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withValueQuotes(leftQuote, rightQuote);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withValueQuotes2_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withValueQuotes((String) null, 'a');
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withValueQuotes3_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").withValueQuotes('a', (String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullKey(String)
    //

    @Test
    public void useForNullKeyString_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").useForNullKey("y");
        Assert.assertEquals(x.useForNullKey(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullKeyString_FailWithNull() {
        Joiner2Utils.withSeparator("x").withKeyValueSeparator("y").useForNullKey((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullKey(char)
    //

    @Test
    public void useForNullKeyChar_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").useForNullKey('y');
        Assert.assertEquals(x.useForNullKey(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullValue(String)
    //

    @Test
    public void useForNullValueString_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").useForNullValue("y");
        Assert.assertEquals(x.useForNullValue(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullValueString_FailWithNull() {
        Joiner2Utils.withSeparator("x").withKeyValueSeparator("y").useForNullValue((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullValue(char)
    //

    @Test
    public void useForNullValueChar_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("x").useForNullValue('y');
        Assert.assertEquals(x.useForNullValue(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.appendTo(Appendable, *)
    // MapJoiner2Impl.appendTo(StringBuilder, *)/join(*) [pass only]
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

            // TODO: Create builders like ImmutableMap.of(*), but do not restrict null key/value

            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", null, "2"), "[(a)={1}],[(null)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[(null)={2}],[(b)={null}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[(null)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[(null)={3}],[(c)={null}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[(null)={4}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", null, null, _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[(null)={4}],[(d)={null}]" },

            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of(), "(empty)" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", null, "2"), "[(a)={1}],[(nullKey)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[(nullKey)={2}],[(b)={null}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[(nullKey)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[(nullKey)={3}],[(c)={null}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[(nullKey)={4}]" },
            { ",", "=", "[", "]", "(empty)", "(", ")", "{", "}", "nullKey", null, _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[(nullKey)={4}],[(d)={null}]" },

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
        Joiner2 joiner = Joiner2Utils.withSeparator(separator);
        MapJoiner2 mapJoiner = joiner.withKeyValueSeparator(keyValueSeparator);
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
        _core_appendMapTo_Pass(mapJoiner, map, expectedResult);
        _core_appendIteratorTo_Pass(mapJoiner, map, expectedResult);
        _core_appendIterableTo_Pass(mapJoiner, map, expectedResult);
    }

    private void _core_appendMapTo_Pass(
            MapJoiner2 mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map);
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable = mapJoiner.appendTo((Appendable) new StringBuilder(), map);
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map);
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendIteratorTo_Pass(
            MapJoiner2 mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map.entrySet());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable = mapJoiner.appendTo((Appendable) new StringBuilder(), map.entrySet());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map.entrySet());
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendIterableTo_Pass(
            MapJoiner2 mapJoiner, Map<String, String> map, String expectedResult)
    throws IOException {
        StringBuilder sb = mapJoiner.appendTo(new StringBuilder(), map.entrySet().iterator());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            mapJoiner.appendTo((Appendable) new StringBuilder(), map.entrySet().iterator());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = mapJoiner.join(map.entrySet().iterator());
        Assert.assertEquals(actualResult, expectedResult);
    }

    private static Appendable _newMockAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Mockito.when(mockAppendable.append(Mockito.anyString())).thenThrow(new IOException());
        return mockAppendable;
    }

    @Test(expectedExceptions = IOException.class)
    public void appendMapToAppendable_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=")
            .appendTo(mockAppendable, ImmutableMap.of("a", "1"));
    }

    @Test(expectedExceptions = IOException.class)
    public void appendIterableToAppendable_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=")
            .appendTo(mockAppendable, ImmutableMap.of("a", "1").entrySet());
    }

    @Test(expectedExceptions = IOException.class)
    public void appendIteratorToAppendable_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=")
            .appendTo(mockAppendable, ImmutableMap.of("a", "1").entrySet().iterator());
    }

    @DataProvider
    private static Object[][] _appendMapToAppendable_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        return new Object[][] {
            { mockAppendable, (Map) null },
            { (Appendable) null, new HashMap<String, String>()},
            { (Appendable) null, (Map) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendMapToAppendable_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendMapToAppendable_FailWithNull(TAppendable appendable, Map<?, ?> map)
    throws IOException {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, map);
    }

    @DataProvider
    private static Object[][] _appendIterableToAppendable_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterable mockIterable = Mockito.mock(Iterable.class);
        return new Object[][] {
            { mockAppendable, (Iterable) null },
            { (Appendable) null, mockIterable },
            { (Appendable) null, (Iterable) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendIterableToAppendable_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendIterableToAppendable_FailWithNull(
            TAppendable appendable, Iterable<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, partIter);
    }

    @DataProvider
    private static Object[][] _appendIteratorToAppendable_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterator mockIterator = Mockito.mock(Iterator.class);
        return new Object[][] {
            { mockAppendable, (Iterator) null },
            { (Appendable) null, mockIterator },
            { (Appendable) null, (Iterator) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendIteratorToAppendable_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendIteratorToAppendable_FailWithNull(
            TAppendable appendable, Iterator<? extends Map.Entry<?, ?>> partIter)
    throws IOException {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, partIter);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void appendIteratorToAppendable_FailWithNullMapEntry()
    throws IOException {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterator<? extends Map.Entry<?, ?>> partIter =
            Iterators.forArray(new Map.Entry<?, ?>[] { null });
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(mockAppendable, partIter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.appendTo(StringBuilder, *) [fail only]
    //

    @DataProvider
    private static Object[][] _appendMapToStringBuilder_FailWithNull_Data() {
        return new Object[][] {
            { new StringBuilder(), (Map) null },
            { (StringBuilder) null, new HashMap<String, String>() },
            { (StringBuilder) null, (Map) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendMapToStringBuilder_FailWithNull_Data")
    public void appendMapToStringBuilder_FailWithNull(StringBuilder sb, Map<?, ?> map) {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, map);
    }

    @DataProvider
    private static Object[][] _appendIterableToStringBuilder_FailWithNull_Data() {
        Iterable mockIterable = Mockito.mock(Iterable.class);
        return new Object[][] {
            { new StringBuilder(), (Iterable) null },
            { (StringBuilder) null, mockIterable },
            { (StringBuilder) null, (Iterable) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_appendIterableToStringBuilder_FailWithNull_Data")
    public void appendIterableToStringBuilder_FailWithNull(
            StringBuilder sb, Iterable<? extends Map.Entry<?, ?>> partIter) {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, partIter);
    }

    @DataProvider
    private static Object[][] _appendIteratorToStringBuilder_FailWithNull_Data() {
        Iterator mockIterator = Mockito.mock(Iterator.class);
        return new Object[][] {
            { new StringBuilder(), (Iterator) null },
            { (StringBuilder) null, mockIterator },
            { (StringBuilder) null, (Iterator) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_appendIteratorToStringBuilder_FailWithNull_Data")
    public void appendIteratorToStringBuilder_FailWithNull(
            StringBuilder sb, Iterator<? extends Map.Entry<?, ?>> partIter) {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, partIter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.join(*) [fail only]
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void joinMap_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").join((Map<?, ?>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void joinIterable_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=")
            .join((Iterable<? extends Map.Entry<?, ?>>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void joinIterator_FailWithNull() {
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=")
            .join((Iterator<? extends Map.Entry<?, ?>>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=");
        x = x.withSeparator("x");
        Assert.assertEquals(x.withSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparator_FailWithNull() {
        Joiner2Utils.withSeparator("x").withKeyValueSeparator("=").withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=");
        x = x.withSeparator('x');
        Assert.assertEquals(x.withSeparator(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withQuotes(String, String)/withQuotes(String, char)/withQuotes(char, String)/withQuotes(char, char)
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
        MapJoiner2 x =
            Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").withQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withLeftQuote(), leftQuote);
        Assert.assertEquals(x.withRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").withQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withLeftQuote(), leftQuote);
            Assert.assertEquals(x.withRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").withQuotes(leftQuoteChar, rightQuote);
            Assert.assertEquals(x.withLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withRightQuote(), rightQuote);
        }

        // char, char
        if (!leftQuote.isEmpty() && !rightQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").withQuotes(leftQuoteChar, rightQuoteChar);
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
        Joiner2Utils.withSeparator(",").withKeyValueSeparator("=").withQuotes(leftQuote, rightQuote);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNoElements(String)
    //

    @Test
    public void useForNoElementsString_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=");
        x = x.useForNoElements("x");
        Assert.assertEquals(x.useForNoElements(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNoElements_FailWithNull() {
        Joiner2Utils.withSeparator("x").withKeyValueSeparator("=").useForNoElements((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNoElements(char)
    //

    @Test
    public void useForNoElementsChar_Pass() {
        MapJoiner2 x = Joiner2Utils.withSeparator(",").withKeyValueSeparator("=");
        x = x.useForNoElements('x');
        Assert.assertEquals(x.useForNoElements(), "x");
    }
}