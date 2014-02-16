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

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class QuotingJoinerImplTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        x = x.withSeparator("x");
        Assert.assertEquals(x.withSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparator_FailWithNull() {
        QuotingJoiners.withSeparator("x").withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(',');
        x = x.withSeparator('x');
        Assert.assertEquals(x.withSeparator(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.withQuotes(String, String)/withQuotes(String, char)/withQuotes(char, String)/withQuotes(char, char)
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
        QuotingJoiner x = QuotingJoiners.withSeparator(",").withQuotes(leftQuote, rightQuote);
        Assert.assertEquals(x.withLeftQuote(), leftQuote);
        Assert.assertEquals(x.withRightQuote(), rightQuote);

        // String, char
        if (!rightQuote.isEmpty()) {
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiners.withSeparator(",").withQuotes(leftQuote, rightQuoteChar);
            Assert.assertEquals(x.withLeftQuote(), leftQuote);
            Assert.assertEquals(x.withRightQuote(), rightQuoteCharString);
        }

        // char, String
        if (!leftQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            x = QuotingJoiners.withSeparator(",").withQuotes(leftQuoteChar, rightQuote);
            Assert.assertEquals(x.withLeftQuote(), leftQuoteCharString);
            Assert.assertEquals(x.withRightQuote(), rightQuote);
        }

        // char, char
        if (!leftQuote.isEmpty() && !rightQuote.isEmpty()) {
            char leftQuoteChar = leftQuote.charAt(0);
            String leftQuoteCharString = String.valueOf(leftQuoteChar);
            char rightQuoteChar = rightQuote.charAt(0);
            String rightQuoteCharString = String.valueOf(rightQuoteChar);
            x = QuotingJoiners.withSeparator(",").withQuotes(leftQuoteChar, rightQuoteChar);
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
        QuotingJoiners.withSeparator(",").withQuotes(leftQuote, rightQuote);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.useForNoElements(String)
    //

    @Test
    public void useForNoElementsString_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        x = x.useForNoElements("x");
        Assert.assertEquals(x.useForNoElements(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNoElements_FailWithNull() {
        QuotingJoiners.withSeparator("x").useForNoElements((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.useForNoElements(char)
    //

    @Test
    public void useForNoElementsChar_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(',');
        x = x.useForNoElements('x');
        Assert.assertEquals(x.useForNoElements(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.useForNull(String)
    //

    @Test
    public void useForNullString_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        x = x.useForNull("x");
        Assert.assertEquals(x.useForNull(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNull_FailWithNull() {
        QuotingJoiners.withSeparator("x").useForNull((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.useForNull(char)
    //

    @Test
    public void useForNullChar_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(',');
        x = x.useForNull('x');
        Assert.assertEquals(x.useForNull(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.skipNulls(boolean)
    //

    @Test
    public void skipNulls_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(',');
        x = x.skipNulls(true);
        Assert.assertEquals(x.skipNulls(), true);
        x = x.skipNulls(false);
        Assert.assertEquals(x.skipNulls(), false);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.appendTo(Appendable, *)
    // QuotingJoinerImpl.appendTo(StringBuilder, *)/join(*) [pass only]
    //

    @SuppressWarnings("unchecked")
    @DataProvider
    private static Object[][] _appendTo_Pass_Data() {
        return new Object[][] {
            { ",", null, null, null, null, false, Arrays.asList(), "" },
            { ",", null, null, null, null, false, Arrays.asList("a"), "a" },
            { ",", null, null, null, null, false, Arrays.asList("a", "b"), "a,b" },
            { ",", null, null, null, null, false, Arrays.asList("a", "b", "c"), "a,b,c" },

            { ",", null, null, null, null, true, Arrays.asList(), "" },
            { ",", null, null, null, null, true, Arrays.asList("a"), "a" },
            { ",", null, null, null, null, true, Arrays.asList("a", "b"), "a,b" },
            { ",", null, null, null, null, true, Arrays.asList("a", "b", "c"), "a,b,c" },

            { ",", "[", "]", null, null, false, Arrays.asList(), "" },
            { ",", "[", "]", null, null, false, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", null, null, false, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", null, null, false, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },

            { ",", "[", "]", null, null, true, Arrays.asList(), "" },
            { ",", "[", "]", null, null, true, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", null, null, true, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", null, null, true, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },

            { ",", "[", "]", "(empty)", null, false, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },

            { ",", "[", "]", "(empty)", null, true, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },

            { ",", "[", "]", "(empty)", null, false, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", null), "[a],[null]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", null, "b", null), "[a],[null],[b],[null]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },
            { ",", "[", "]", "(empty)", null, false, Arrays.asList("a", null, "b", null, "c", null), "[a],[null],[b],[null],[c],[null]" },

            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a", null), "[a],[nullValue]" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a", null, "b", null), "[a],[nullValue],[b],[nullValue]" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },
            { ",", "[", "]", "(empty)", "nullValue", false, Arrays.asList("a", null, "b", null, "c", null), "[a],[nullValue],[b],[nullValue],[c],[nullValue]" },

            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a", null), "[a]" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a", null, "b", null), "[a],[b]" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },
            { ",", "[", "]", "(empty)", "nullValue", true, Arrays.asList("a", null, "b", null, "c", null), "[a],[b],[c]" },

            { ",", "[", "]", "(empty)", null, true, Arrays.asList(), "(empty)" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a"), "[a]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", null), "[a]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", "b"), "[a],[b]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", null, "b", null), "[a],[b]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", "b", "c"), "[a],[b],[c]" },
            { ",", "[", "]", "(empty)", null, true, Arrays.asList("a", null, "b", null, "c", null), "[a],[b],[c]" },
        };
    }

    @Test(dataProvider = "_appendTo_Pass_Data")
    public void appendTo_Pass(
            String separator,
            String optLeftQuote,
            String optRightQuote,
            String optNoElementsText,
            String optNullText,
            boolean skipNullsFlag,
            List<String> partList,
            String expectedResult)
    throws IOException {
        Assert.assertTrue(
            (null == optLeftQuote && null == optRightQuote)
            || (null != optLeftQuote && null != optRightQuote));
        QuotingJoiner joiner = QuotingJoiners.withSeparator(separator);
        if (null != optLeftQuote && null != optRightQuote) {
            joiner = joiner.withQuotes(optLeftQuote, optRightQuote);
        }
        if (null != optNoElementsText) {
            joiner = joiner.useForNoElements(optNoElementsText);
        }
        if (null != optNullText) {
            joiner = joiner.useForNull(optNullText);
        }
        joiner = joiner.skipNulls(skipNullsFlag);
        _core_appendTo1_Pass(joiner, partList, expectedResult);
        _core_appendTo2_Pass(joiner, partList, expectedResult);
        _core_appendTo3_Pass(joiner, partList, expectedResult);
        _core_appendTo4_Pass(joiner, partList, expectedResult);
    }

    private void _core_appendTo1_Pass(
            QuotingJoiner joiner, List<String> partList, String expectedResult)
    throws IOException {
        if (partList.size() < 2) {
            return;
        }
        Object value1 = partList.get(0);
        Object value2 = partList.get(1);
        Object[] otherValueArr =
            (partList.size() == 2
                ? new Object[0]
                : partList.subList(2, partList.size()).toArray());

        StringBuilder sb = joiner.appendTo(new StringBuilder(), value1, value2, otherValueArr);
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            joiner.appendTo((Appendable) new StringBuilder(), value1, value2, otherValueArr);
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = joiner.join(value1, value2, otherValueArr);
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendTo2_Pass(
        QuotingJoiner joiner, List<String> partList, String expectedResult)
    throws IOException {
        StringBuilder sb = joiner.appendTo(new StringBuilder(), partList.toArray());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            joiner.appendTo((Appendable) new StringBuilder(), partList.toArray());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = joiner.join(partList.toArray());
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendTo3_Pass(
            QuotingJoiner joiner, List<String> partList, String expectedResult)
    throws IOException {
        StringBuilder sb = joiner.appendTo(new StringBuilder(), partList);
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            joiner.appendTo((Appendable) new StringBuilder(), partList);
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = joiner.join(partList);
        Assert.assertEquals(actualResult, expectedResult);
    }

    private void _core_appendTo4_Pass(
            QuotingJoiner joiner, List<String> partList, String expectedResult)
    throws IOException {
        StringBuilder sb = joiner.appendTo(new StringBuilder(), partList.iterator());
        Assert.assertEquals(sb.toString(), expectedResult);

        Appendable appendable =
            joiner.appendTo((Appendable) new StringBuilder(), partList.iterator());
        Assert.assertEquals(appendable.toString(), expectedResult);

        String actualResult = joiner.join(partList.iterator());
        Assert.assertEquals(actualResult, expectedResult);
    }

    private static Appendable _newMockAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Mockito.when(mockAppendable.append(Mockito.anyString())).thenThrow(new IOException());
        return mockAppendable;
    }

    @Test(expectedExceptions = IOException.class)
    public void appendToAppendable1_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        QuotingJoiners.withSeparator(",").appendTo(mockAppendable, "abc", "def");
    }

    @Test(expectedExceptions = IOException.class)
    public void appendToAppendable2_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        QuotingJoiners.withSeparator(",").appendTo(mockAppendable, Arrays.asList("abc").toArray());
    }

    @Test(expectedExceptions = IOException.class)
    public void appendToAppendable3_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        QuotingJoiners.withSeparator(",").appendTo(mockAppendable, Arrays.asList("abc"));
    }

    @Test(expectedExceptions = IOException.class)
    public void appendToAppendable4_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        QuotingJoiners.withSeparator(",").appendTo(mockAppendable, Arrays.asList("abc").iterator());
    }

    @DataProvider
    private static Object[][] _appendToAppendable12_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        return new Object[][] {
            { mockAppendable, (Object[]) null },
            { (Appendable) null, new Object[0] },
            { (Appendable) null, (Object[]) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendToAppendable12_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendToAppendable1_FailWithNull(TAppendable appendable, Object[] partArr)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(appendable, "abc", "def", partArr);
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendToAppendable12_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendToAppendable2_FailWithNull(TAppendable appendable, Object[] partArr)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(appendable, partArr);
    }

    @DataProvider
    private static Object[][] _appendToAppendable3_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterable mockIterable = Mockito.mock(Iterable.class);
        return new Object[][] {
            { mockAppendable, (Iterable) null },
            { (Appendable) null, mockIterable },
            { (Appendable) null, (Iterable) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendToAppendable3_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendToAppendable3_FailWithNull(TAppendable appendable, Iterable<?> partIter)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(appendable, partIter);
    }

    @DataProvider
    private static Object[][] _appendToAppendable4_FailWithNull_Data() {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterator mockIterator = Mockito.mock(Iterator.class);
        return new Object[][] {
            { mockAppendable, (Iterator) null },
            { (Appendable) null, mockIterator },
            { (Appendable) null, (Iterator) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendToAppendable4_FailWithNull_Data")
    public <TAppendable extends Appendable>
    void appendToAppendable4_FailWithNull(TAppendable appendable, Iterator<?> partIter)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(appendable, partIter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.appendTo(StringBuilder, *) [fail only]
    //

    @DataProvider
    private static Object[][] _appendArray12ToStringBuilder_FailWithNull_Data() {
        return new Object[][] {
            { new StringBuilder(), (Object[]) null },
            { (Appendable) null, new Object[0] },
            { (Appendable) null, (Object[]) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendArray12ToStringBuilder_FailWithNull_Data")
    public void appendArray1ToStringBuilder_FailWithNull(StringBuilder sb, Object[] partArr)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(sb, "abc", "def", partArr);
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_appendArray12ToStringBuilder_FailWithNull_Data")
    public void appendArray2ToStringBuilder_FailWithNull(StringBuilder sb, Object[] partArr)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(sb, partArr);
    }

    @DataProvider
    private static Object[][] _appendIterableToStringBuilder_FailWithNull_Data() {
        Iterable mockIterable = Mockito.mock(Iterable.class);
        return new Object[][] {
            { new StringBuilder(), (Iterable) null },
            { (Appendable) null, mockIterable },
            { (Appendable) null, (Iterable) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_appendIterableToStringBuilder_FailWithNull_Data")
    public void appendIterableToStringBuilder_FailWithNull(StringBuilder sb, Iterable<?> partIter)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(sb, partIter);
    }

    @DataProvider
    private static Object[][] _appendIteratorToStringBuilder_FailWithNull_Data() {
        Iterator mockIterator = Mockito.mock(Iterator.class);
        return new Object[][] {
            { new StringBuilder(), (Iterator) null },
            { (Appendable) null, mockIterator },
            { (Appendable) null, (Iterator) null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
        dataProvider = "_appendIteratorToStringBuilder_FailWithNull_Data")
    public void appendIteratorToStringBuilder_FailWithNull(StringBuilder sb, Iterator<?> partIter)
    throws IOException {
        QuotingJoiners.withSeparator(",").appendTo(sb, partIter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.join(*) [fail only]
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void join1_FailWithNull()
    throws IOException {
        QuotingJoiners.withSeparator(",").join("abc", "def", (Object[]) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void join2_FailWithNull()
    throws IOException {
        QuotingJoiners.withSeparator(",").join((Object[]) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void join3_FailWithNull()
    throws IOException {
        QuotingJoiners.withSeparator(",").join((Iterable<?>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void join4_FailWithNull()
    throws IOException {
        QuotingJoiners.withSeparator(",").join((Iterator<?>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        QuotingMapJoiner y = x.withKeyValueSeparator("x");
        Assert.assertEquals(y.withKeyValueSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparator_FailWithNull() {
        QuotingJoiners.withSeparator("x").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // QuotingJoinerImpl.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        QuotingJoiner x = QuotingJoiners.withSeparator(",");
        QuotingMapJoiner y = x.withKeyValueSeparator('x');
        Assert.assertEquals(y.withKeyValueSeparator(), "x");
        Assert.assertEquals(y.withKeyLeftQuote(), QuotingJoiners.DEFAULT_KEY_LEFT_QUOTE);
        Assert.assertEquals(y.withKeyRightQuote(), QuotingJoiners.DEFAULT_KEY_RIGHT_QUOTE);
        Assert.assertEquals(y.withValueLeftQuote(), QuotingJoiners.DEFAULT_VALUE_LEFT_QUOTE);
        Assert.assertEquals(y.withValueRightQuote(), QuotingJoiners.DEFAULT_VALUE_RIGHT_QUOTE);
        Assert.assertEquals(y.useForNullKey(), QuotingJoiners.DEFAULT_KEY_NULL_TEXT);
        Assert.assertEquals(y.useForNullValue(), QuotingJoiners.DEFAULT_VALUE_NULL_TEXT);
    }
}
