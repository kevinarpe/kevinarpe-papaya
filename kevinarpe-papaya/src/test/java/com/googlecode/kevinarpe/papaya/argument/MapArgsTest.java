package com.googlecode.kevinarpe.papaya.argument;

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

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.argument.MapArgs;

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
    public static Object[][] checkSizeRange_Pass_Data() {
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
    
    @Test(dataProvider = "checkSizeRange_Pass_Data")
    public <TKey, TValue> void checkSizeRange_Pass(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkSizeRange(ref, minSize, maxSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkSizeRange(ref, minSize, maxSize, null));
        Assert.assertTrue(ref == MapArgs.checkSizeRange(ref, minSize, maxSize, ""));
        Assert.assertTrue(ref == MapArgs.checkSizeRange(ref, minSize, maxSize, "   "));
    }

    @DataProvider
    public static Object[][] checkSizeRange_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkSizeRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkSizeRange_FailWithInvalidMinOrMaxLen(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    public static Object[][] checkSizeRange_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkSizeRange_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkSizeRange_FailWithNullMap(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmpty
    //

    @DataProvider
    public static Object[][] checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { _asMap("a", 1) },
                { _asMap("a", 1, "b", 2) },
                { _asMap("a", 1, "b", 2, "c", 3) },
        };
    }
    
    @Test(dataProvider = "checkNotEmpty_Pass_Data")
    public <TKey, TValue> void checkNotEmpty_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == MapArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkNotEmpty(ref, "   "));
    }

    @DataProvider
    public static Object[][] checkNotEmpty_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "checkNotEmpty_FailWithEmptyMap_Data",
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
    public static Object[][] checkMinSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { _asMap("a", 1), 0 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1, "b", 2), 0 },
                { _asMap("a", 1, "b", 2), 1 },
                { _asMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_Pass_Data")
    public <TKey, TValue> void checkMinSize_Pass(Map<TKey, TValue> ref, int minSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkMinSize(ref, minSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkMinSize(ref, minSize, null));
        Assert.assertTrue(ref == MapArgs.checkMinSize(ref, minSize, ""));
        Assert.assertTrue(ref == MapArgs.checkMinSize(ref, minSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinSize_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkMinSize_FailWithInvalidMinLen(
            Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkMinSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkMinSize_FailWithNullMap(
            Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkMaxSize
    //

    @DataProvider
    public static Object[][] checkMaxSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { new HashMap<String, String>(), 99 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1), 99 },
                { _asMap("a", 1, "b", 2), 2 },
                { _asMap("a", 1, "b", 2), 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_Pass_Data")
    public <TKey, TValue> void checkMaxSize_Pass(Map<TKey, TValue> ref, int maxSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkMaxSize(ref, maxSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkMaxSize(ref, maxSize, null));
        Assert.assertTrue(ref == MapArgs.checkMaxSize(ref, maxSize, ""));
        Assert.assertTrue(ref == MapArgs.checkMaxSize(ref, maxSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxSize_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkMaxSize_FailWithInvalidMaxLen(
            Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkMaxSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkMaxSize_FailWithNullMap(
            Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkExactSize
    //

    @DataProvider
    public static Object[][] checkExactSize_Pass_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { _asMap("a", 1), 1 },
                { _asMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_Pass_Data")
    public <TKey, TValue> void checkExactSize_Pass(Map<TKey, TValue> ref, int exactSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkExactSize(ref, exactSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkExactSize(ref, exactSize, null));
        Assert.assertTrue(ref == MapArgs.checkExactSize(ref, exactSize, ""));
        Assert.assertTrue(ref == MapArgs.checkExactSize(ref, exactSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactSize_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { _asMap("a", 1), -3 },
                { _asMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkExactSize_FailWithInvalidExactLen(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkExactSize_FailWithNullMap_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_FailWithNullMap_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkExactSize_FailWithNullMap(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysNotNull
    //
    
    @DataProvider
    public static Object[][] checkKeysNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap("abc", null) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "checkKeysNotNull_Pass_Data")
    public <TKey, TValue> void checkKeysNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkKeysNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkKeysNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkKeysNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkKeysNotNull(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkKeysNotNull_FailWithNullKey_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "checkKeysNotNull_FailWithNullKey_Data",
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
    public static Object[][] checkValuesNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap(null, 123) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "checkValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkValuesNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkValuesNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkValuesNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkValuesNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkValuesNotNull(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValuesNotNull_FailWithNullValue_Data() {
        return new Object[][] {
                { _asMap(123, null) },
                { _asMap(null, null) },
                { _asMap(123, null, 456, "abc") },
                { _asMap(123, "abc", 456, null) },
                { _asMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "checkValuesNotNull_FailWithNullValue_Data",
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
    public static Object[][] checkKeysAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { _asMap("abc", 123) },
                { _asMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "checkKeysAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkKeysAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkKeysAndValuesNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkKeysAndValuesNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkKeysAndValuesNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkKeysAndValuesNotNull(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkKeysAndValuesNotNull_FailWithNullKeyOrValue_Data() {
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
    
    @Test(dataProvider = "checkKeysAndValuesNotNull_FailWithNullKeyOrValue_Data",
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
    public static Object[][] checkNotEmptyAndKeysNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap("abc", null) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndKeysNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysNotNull(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotEmptyAndKeysNotNull_FailWithNullKey_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysNotNull_FailWithNullKey_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysNotNull_FailWithNullKey(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysNotNull(ref, "ref");
    }

    @DataProvider
    public static Object[][] checkNotEmptyAndKeysNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysNotNull_FailWithEmptyMap_Data",
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
    public static Object[][] checkNotEmptyAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap(null, 123) },
                { _asMap("abc", 123, "def", 456) },
                { _asMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndValuesNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndValuesNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndValuesNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndValuesNotNull(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotEmptyAndValuesNotNull_FailWithNullValue_Data() {
        return new Object[][] {
                { _asMap(123, null) },
                { _asMap(null, null) },
                { _asMap(123, null, 456, "abc") },
                { _asMap(123, "abc", 456, null) },
                { _asMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndValuesNotNull_FailWithNullValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndValuesNotNull_FailWithNullValue(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndValuesNotNull(ref, "ref");
    }

    @DataProvider
    public static Object[][] checkNotEmptyAndValuesNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndValuesNotNull_FailWithEmptyMap_Data",
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
    public static Object[][] checkNotEmptyAndKeysAndValuesNotNull_Pass_Data() {
        return new Object[][] {
                { _asMap("abc", 123) },
                { _asMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysAndValuesNotNull_Pass_Data")
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_Pass(Map<TKey, TValue> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, null));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, ""));
        Assert.assertTrue(ref == MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap() {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(new HashMap<Object, Object>(), "ref");
    }
    
    @DataProvider
    public static Object[][] checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue_Data() {
        return new Object[][] {
                { _asMap(null, 123) },
                { _asMap(null, null) },
                { _asMap(null, 123, "abc", 456) },
                { _asMap("abc", 123, null, 456) },
                { _asMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue_Data",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void checkNotEmptyAndKeysAndValuesNotNull_FailWithNullKeyOrValue(
            Map<TKey, TValue> ref) {
        MapArgs.checkNotEmptyAndKeysAndValuesNotNull(ref, "ref");
    }

    @DataProvider
    public static Object[][] checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap_Data() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndKeysAndValuesNotNull_FailWithEmptyMap_Data",
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
