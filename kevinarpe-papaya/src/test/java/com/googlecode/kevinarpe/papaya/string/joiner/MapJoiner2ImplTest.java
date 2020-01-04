package com.googlecode.kevinarpe.papaya.string.joiner;

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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.Formatter2;
import com.googlecode.kevinarpe.papaya.string.joiner.formatter.StringFormatter2;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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

    private Formatter2 mockFormatter;

    private Joiner2Utils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockFormatter = Mockito.mock(Formatter2.class);

        classUnderTest = new Joiner2Utils();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyValueSeparator(String)
    //

    @Test
    public void withKeyValueSeparatorString_Pass() {
        MapJoiner2 j =
            classUnderTest.withSeparator(",").withKeyValueSeparator("x").withKeyValueSeparator("y");
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyValueSeparatorString_FailWithNull() {
        classUnderTest.withSeparator("x").withKeyValueSeparator("y").withKeyValueSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyValueSeparator(char)
    //

    @Test
    public void withKeyValueSeparatorChar_Pass() {
        MapJoiner2 j =
            classUnderTest.withSeparator(",").withKeyValueSeparator("x").withKeyValueSeparator('y');
        Assert.assertEquals(j.withKeyValueSeparator(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withKeyFormatter(Formatter2)
    //

    @Test
    public void withKeyFormatterFormatter2_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.withKeyFormatter(mockFormatter);
        Assert.assertSame(x.withKeyFormatter(), mockFormatter);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withKeyFormatterFormatter2_FaillWithNull() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x.withKeyFormatter((Formatter2) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withValueFormatter(Formatter2)
    //

    @Test
    public void withValueFormatterFormatter2_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.withValueFormatter(mockFormatter);
        Assert.assertSame(x.withValueFormatter(), mockFormatter);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withValueFormatterFormatter2_FaillWithNull() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x.withValueFormatter((Formatter2) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullKey(String)
    //

    @Test
    public void useForNullKeyString_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("x").useForNullKey("y");
        Assert.assertEquals(x.useForNullKey(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullKeyString_FailWithNull() {
        classUnderTest.withSeparator("x").withKeyValueSeparator("y").useForNullKey((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullKey(char)
    //

    @Test
    public void useForNullKeyChar_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("x").useForNullKey('y');
        Assert.assertEquals(x.useForNullKey(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullValue(String)
    //

    @Test
    public void useForNullValueString_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("x").useForNullValue("y");
        Assert.assertEquals(x.useForNullValue(), "y");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNullValueString_FailWithNull() {
        classUnderTest.withSeparator("x").withKeyValueSeparator("y").useForNullValue((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNullValue(char)
    //

    @Test
    public void useForNullValueChar_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("x").useForNullValue('y');
        Assert.assertEquals(x.useForNullValue(), "y");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.appendTo(Appendable, *)
    // MapJoiner2Impl.appendTo(StringBuilder, *)/join(*) [pass only]
    //

    private static final class _NegatingIntegerFormatter2
        implements Formatter2 {

        @Override
        public String format(Object value) {
            Integer x = (Integer) value;
            String y = String.valueOf(-1 * x);
            return y;
        }
    }

    @DataProvider
    private static Object[][] _appendTo_Pass_Data() {
        return new Object[][] {
            { ",", "=", null, null, null, null, null, null, null, ImmutableMap.of(), "" },
            { ",", "=", null, null, null, null, null, null, null, ImmutableMap.of("a", "1"), "a=1" },
            { ",", "=", null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "a=1,b=2" },
            { ",", "=", null, null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "a=1,b=2,c=3" },

            { ",", "=", new StringFormatter2("[%s]"), null, null, null, null, null, null, ImmutableMap.of(), "" },
            { ",", "=", new StringFormatter2("[%s]"), null, null, null, null, null, null, ImmutableMap.of("a", "1"), "[a=1]" },
            { ",", "=", new StringFormatter2("[%s]"), null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[a=1],[b=2]" },
            { ",", "=", new StringFormatter2("[%s]"), null, null, null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[a=1],[b=2],[c=3]" },

            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", null, null, null, null, ImmutableMap.of(), "<empty>" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", null, null, null, null, ImmutableMap.of("a", "1"), "[a=1]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", null, null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[a=1],[b=2]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", null, null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[a=1],[b=2],[c=3]" },

            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), null, null, null, ImmutableMap.of(), "<empty>" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), null, null, null, ImmutableMap.of("a", "1"), "[(a)=1]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), null, null, null, ImmutableMap.of("a", "1", "b", "2"), "[(a)=1],[(b)=2]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), null, null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)=1],[(b)=2],[(c)=3]" },

            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, ImmutableMap.of(), "<empty>" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", null, "2"), "[(a)={1}],[null={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[null={2}],[(b)=null]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[null={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[null={3}],[(c)=null]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[null={4}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), null, null, _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[null={4}],[(d)=null]" },

            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, ImmutableMap.of(), "<empty>" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", null, "2"), "[(a)={1}],[nullKey={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[nullKey={2}],[(b)=null]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[nullKey={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[nullKey={3}],[(c)=null]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", null, _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}],[(d)=null]" },

            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(), "<empty>" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1"), "[(a)={1}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", null, "2"), "[(a)={1}],[nullKey={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", null, "2", "b", null), "[(a)={1}],[nullKey={2}],[(b)=nullValue]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2"), "[(a)={1}],[(b)={2}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3"), "[(a)={1}],[(b)={2}],[nullKey={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3", "c", null), "[(a)={1}],[(b)={2}],[nullKey={3}],[(c)=nullValue]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2", "c", "3"), "[(a)={1}],[(b)={2}],[(c)={3}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}]" },
            { ",", "=", new StringFormatter2("[%s]"), null, "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}],[(d)=nullValue]" },

            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(), "<<empty>>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1"), "<[(a)={1}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", null, "2"), "<[(a)={1}],[nullKey={2}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", null, "2", "b", null), "<[(a)={1}],[nullKey={2}],[(b)=nullValue]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2"), "<[(a)={1}],[(b)={2}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3"), "<[(a)={1}],[(b)={2}],[nullKey={3}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", null, "3", "c", null), "<[(a)={1}],[(b)={2}],[nullKey={3}],[(c)=nullValue]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of("a", "1", "b", "2", "c", "3"), "<[(a)={1}],[(b)={2}],[(c)={3}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4"), "<[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new StringFormatter2("(%s)"), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf("a", "1", "b", "2", "c", "3", null, "4", "d", null), "<[(a)={1}],[(b)={2}],[(c)={3}],[nullKey={4}],[(d)=nullValue]>" },

            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(), "<<empty>>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(123, "1"), "<[-123={1}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", null, "2"), "<[-123={1}],[nullKey={2}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", null, "2", 456, null), "<[-123={1}],[nullKey={2}],[-456=nullValue]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(123, "1", 456, "2"), "<[-123={1}],[-456={2}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", 456, "2", null, "3"), "<[-123={1}],[-456={2}],[nullKey={3}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", 456, "2", null, "3", 789, null), "<[-123={1}],[-456={2}],[nullKey={3}],[-789=nullValue]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", ImmutableMap.of(123, "1", 456, "2", 789, "3"), "<[-123={1}],[-456={2}],[-789={3}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", 456, "2", 789, "3", null, "4"), "<[-123={1}],[-456={2}],[-789={3}],[nullKey={4}]>" },
            { ",", "=", new StringFormatter2("[%s]"), new StringFormatter2("<%s>"), "<empty>", new _NegatingIntegerFormatter2(), new StringFormatter2("{%s}"), "nullKey", "nullValue", _mapOf(123, "1", 456, "2", 789, "3", null, "4", 234, null), "<[-123={1}],[-456={2}],[-789={3}],[nullKey={4}],[-234=nullValue]>" },
        };
    }

    private static Map<Object, Object> _mapOf(Object... arr) {
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
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
            Formatter2 optElementFormatter,
            Formatter2 optFinalFormatter,
            String optNoElementsText,
            Formatter2 optKeyFormatter,
            Formatter2 optValueFormatter,
            String optKeyNullText,
            String optValueNullText,
            Map<String, String> map,
            String expectedResult)
    throws IOException {
        Joiner2 joiner = classUnderTest.withSeparator(separator);
        MapJoiner2 mapJoiner = joiner.withKeyValueSeparator(keyValueSeparator);
        if (null != optElementFormatter) {
            mapJoiner = mapJoiner.withElementFormatter(optElementFormatter);
        }
        if (null != optFinalFormatter) {
            mapJoiner = mapJoiner.withFinalFormatter(optFinalFormatter);
        }
        if (null != optNoElementsText) {
            mapJoiner = mapJoiner.useForNoElements(optNoElementsText);
        }
        if (null != optKeyFormatter) {
            mapJoiner = mapJoiner.withKeyFormatter(optKeyFormatter);
        }
        if (null != optValueFormatter) {
            mapJoiner = mapJoiner.withValueFormatter(optValueFormatter);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=")
            .appendTo(mockAppendable, ImmutableMap.of("a", "1"));
    }

    @Test(expectedExceptions = IOException.class)
    public void appendIterableToAppendable_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        classUnderTest.withSeparator(",").withKeyValueSeparator("=")
            .appendTo(mockAppendable, ImmutableMap.of("a", "1").entrySet());
    }

    @Test(expectedExceptions = IOException.class)
    public void appendIteratorToAppendable_FailWhenAppendableThrowsIOException()
    throws IOException {
        Appendable mockAppendable = _newMockAppendableThrowsIOException();
        classUnderTest.withSeparator(",").withKeyValueSeparator("=")
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, map);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, partIter);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(appendable, partIter);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void appendIteratorToAppendable_FailWithNullMapEntry()
    throws IOException {
        Appendable mockAppendable = Mockito.mock(Appendable.class);
        Iterator<? extends Map.Entry<?, ?>> partIter =
            Iterators.forArray(new Map.Entry<?, ?>[] { null });
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(mockAppendable, partIter);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, map);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, partIter);
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
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").appendTo(sb, partIter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.join(*) [fail only]
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void joinMap_FailWithNull() {
        classUnderTest.withSeparator(",").withKeyValueSeparator("=").join((Map<?, ?>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void joinIterable_FailWithNull() {
        classUnderTest.withSeparator(",").withKeyValueSeparator("=")
            .join((Iterable<? extends Map.Entry<?, ?>>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void joinIterator_FailWithNull() {
        classUnderTest.withSeparator(",").withKeyValueSeparator("=")
            .join((Iterator<? extends Map.Entry<?, ?>>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withSeparator(String)
    //

    @Test
    public void withSeparatorString_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.withSeparator("x");
        Assert.assertEquals(x.withSeparator(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withSeparator_FailWithNull() {
        classUnderTest.withSeparator("x").withKeyValueSeparator("=").withSeparator((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withSeparator(char)
    //

    @Test
    public void withSeparatorChar_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.withSeparator('x');
        Assert.assertEquals(x.withSeparator(), "x");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.withElementFormatter(Formatter2)
    //

    @Test
    public void withFormatterFormatter2_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.withElementFormatter(mockFormatter);
        Assert.assertSame(x.withElementFormatter(), mockFormatter);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withFormatterFormatter2_FaillWithNull() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x.withElementFormatter((Formatter2) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNoElements(String)
    //

    @Test
    public void useForNoElementsString_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.useForNoElements("x");
        Assert.assertEquals(x.useForNoElements(), "x");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void useForNoElements_FailWithNull() {
        classUnderTest.withSeparator("x").withKeyValueSeparator("=").useForNoElements((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // MapJoiner2Impl.useForNoElements(char)
    //

    @Test
    public void useForNoElementsChar_Pass() {
        MapJoiner2 x = classUnderTest.withSeparator(",").withKeyValueSeparator("=");
        x = x.useForNoElements('x');
        Assert.assertEquals(x.useForNoElements(), "x");
    }
}
