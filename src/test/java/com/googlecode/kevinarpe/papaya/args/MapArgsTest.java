package com.googlecode.kevinarpe.papaya.args;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.args.MapArgs;

public class MapArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkSizeRange
    //

    @DataProvider
    private static final Object[][] _checkSizeRange_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>(), 0, 10 },
                { new HashMap<Object, Object>(), 0, 0 },
                { _asMap("a", 1), 0, 10 },
                { _asMap("a", 1), 1, 10 },
                { _asMap("a", 1), 0, 1 },
                { _asMap("a", 1), 1, 1 },
                { _asMap("a", 1, "b", 2), 0, 10 },
                { _asMap("a", 1, "b", 2), 2, 10 },
                { _asMap("a", 1, "b", 2), 0, 2 },
                { _asMap("a", 1, "b", 2), 2, 2 },
                { _asMap("a", 1, "b", 2, "c", 3), 0, 10 },
                { _asMap("a", 1, "b", 2, "c", 3), 3, 10 },
                { _asMap("a", 1, "b", 2, "c", 3), 0, 3 },
                { _asMap("a", 1, "b", 2, "c", 3), 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_Pass_Data")
    public <TKey, TValue> void checkSizeRange_Pass(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkSizeRange(ref, minSize, maxSize, null);
        MapArgs.checkSizeRange(ref, minSize, maxSize, "");
        MapArgs.checkSizeRange(ref, minSize, maxSize, "   ");
    }

    @DataProvider
    private static final Object[][] _checkSizeRange_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>(), 3, 4 },
                { _asMap("a", 1), -3, 3 },
                { _asMap("a", 1, "b", 2), 1, -3 },
                { _asMap("a", 1), -3, -4 },
                { _asMap("a", 1), 4, 3 },
                { _asMap("a", 1, "b", 2, "c", 3), 6, 7 },
                { _asMap("a", 1, "b", 2, "c", 3), 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkSizeRange_FailWithInvalidMinOrMaxLen(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _checkSizeRange_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkSizeRange_FailWithNullMap(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { _asMap("a", 1) },
                { _asMap("a", 1, "b", 2) },
                { _asMap("a", 1, "b", 2, "c", 3) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_Pass_Data")
    public <TKey, TValue> void checkNotEmpty_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmpty(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkNotEmpty(ref, null);
        MapArgs.checkNotEmpty(ref, "");
        MapArgs.checkNotEmpty(ref, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotEmpty_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_FailWithEmptyMap_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmpty_FailWithEmptyMap(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckAsNotEmptyWithNullMap() {
        Map<TKey, TValue> ref = null;
        MapArgs.checkNotEmpty(ref, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkMinSize
    //

    @DataProvider
    private static final Object[][] _checkMinSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { _asMap("a", 1), 0 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1, "b", 2), 0 },
                { _asMap("a", 1, "b", 2), 1 },
                { _asMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_Pass_Data")
    public <TKey, TValue> void checkMinSize_Pass(Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkMinSize(ref, minSize, null);
        MapArgs.checkMinSize(ref, minSize, "");
        MapArgs.checkMinSize(ref, minSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinSize_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkMinSize_FailWithInvalidMinLen(
            Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkMinSize_FailWithNullMap(
            Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkMaxSize
    //

    @DataProvider
    private static final Object[][] _checkMaxSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { new HashMap<String, String>(), 99 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1), 99 },
                { _asMap("a", 1, "b", 2), 2 },
                { _asMap("a", 1, "b", 2), 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_Pass_Data")
    public <TKey, TValue> void checkMaxSize_Pass(Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkMaxSize(ref, maxSize, null);
        MapArgs.checkMaxSize(ref, maxSize, "");
        MapArgs.checkMaxSize(ref, maxSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxSize_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkMaxSize_FailWithInvalidMaxLen(
            Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkMaxSize_FailWithNullMap(
            Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkExactSize
    //

    @DataProvider
    private static final Object[][] _checkExactSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_Pass_Data")
    public <TKey, TValue> void checkExactSize_Pass(Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkExactSize(ref, exactSize, null);
        MapArgs.checkExactSize(ref, exactSize, "");
        MapArgs.checkExactSize(ref, exactSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactSize_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkExactSize_FailWithInvalidExactLen(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkExactSize_FailWithNullMap(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkKeysNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap("abc", null) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "_checkKeysNotNull_Pass_Data")
    public <TKey, TValue> void checkKeysNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkKeysNotNull(ref, null);
        MapArgs.checkKeysNotNull(ref, "");
        MapArgs.checkKeysNotNull(ref, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkKeysNotNull_FailWithNullKey_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_checkKeysNotNull_FailWithNullKey_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkKeysNotNull_FailWithNullKey(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkKeysNotNull_FailWithNullMap() {
        MapArgs.checkKeysNotNull((Map<Object, Object>) null, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkValuesNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap(null, 123) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "_checkValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkValuesNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkValuesNotNull(ref, null);
        MapArgs.checkValuesNotNull(ref, "");
        MapArgs.checkValuesNotNull(ref, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkValuesNotNull_FailWithNullValue_Data() {
        return new Object[][] {
                { _asMap(123, null) },
                { _asMap(null, null) },
                { _asMap(123, null, 456, "abc") },
                { _asMap(123, "abc", 456, null) },
                { _asMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "_checkValuesNotNull_FailWithNullValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkValuesNotNull_FailWithNullValue(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValuesNotNull_FailWithNullMap() {
        MapArgs.checkValuesNotNull((Map<Object, Object>) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysAndValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkKeysAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "_checkKeysAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkKeysAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkKeysAndValuesNotNull(ref, null);
        MapArgs.checkKeysAndValuesNotNull(ref, "");
        MapArgs.checkKeysAndValuesNotNull(ref, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkKeysAndValuesNotNull_FailWithNullKeyOrValue_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    private static Map<Object, Object> _asMap(Object... argArr) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 0; i < argArr.length; i += 2) {
            map.put(argArr[i], argArr[1 + i]);
        }
        return map;
    }
    
    @Test(dataProvider = "_checkKeysAndValuesNotNull_FailWithNullKeyOrValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkKeysAndValuesNotNull_FailWithNullKeyOrValue(
            Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkKeysAndValuesNotNull_FailWithNullMap() {
        MapArgs.checkKeysAndValuesNotNull((Map<Object, Object>) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmptyAndKeysNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap("abc", null) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndKeysNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkNotEmptyAndKeysNotNull(ref, null);
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "");
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysNotNull_FailWithNullKey_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysNotNull_FailWithNullKey_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysNotNull_FailWithNullKey(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysNotNull_FailWithEmptyMap_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysNotNull_FailWithEmptyMap(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyAndKeysNotNull_FailWithNullMap() {
        MapArgs.checkNotEmptyAndKeysNotNull((Map<Object, Object>) null, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmptyAndValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap(null, 123) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkNotEmptyAndValuesNotNull(ref, null);
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "");
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndValuesNotNull_FailWithNullValue_Data() {
        return new Object[][] {
                { _asMap(123, null) },
                { _asMap(null, null) },
                { _asMap(123, null, 456, "abc") },
                { _asMap(123, "abc", 456, null) },
                { _asMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndValuesNotNull_FailWithNullValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndValuesNotNull_FailWithNullValue(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyAndValuesNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndValuesNotNull_FailWithEmptyMap_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmptyAndValuesNotNull_FailWithEmptyMap(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyAndValuesNotNull_FailWithNullMap() {
        MapArgs.checkNotEmptyAndValuesNotNull((Map<Object, Object>) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmptyAndKeysAndValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, null);
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "");
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "   ");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap() {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(new HashMap<Object, Object>(), "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithNullMap() {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull((Map<Object, Object>) null, "ref");
    }
}
