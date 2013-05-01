package com.googlecode.kevinarpe.papaya.Args;

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
import java.util.HashSet;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.googlecode.kevinarpe.papaya.MapUtils;
import com.googlecode.kevinarpe.papaya.Args.MapArgs;

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
    private static final Object[][] _dataForShouldCheckSizeRangeAsValid() {
        return new Object[][] {
                { new HashMap<Object, Object>(), 0, 10 },
                { new HashMap<Object, Object>(), 0, 0 },
                { MapUtils.asHashMap("a", 1), 0, 10 },
                { MapUtils.asHashMap("a", 1), 1, 10 },
                { MapUtils.asHashMap("a", 1), 0, 1 },
                { MapUtils.asHashMap("a", 1), 1, 1 },
                { MapUtils.asHashMap("a", 1, "b", 2), 0, 10 },
                { MapUtils.asHashMap("a", 1, "b", 2), 2, 10 },
                { MapUtils.asHashMap("a", 1, "b", 2), 0, 2 },
                { MapUtils.asHashMap("a", 1, "b", 2), 2, 2 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 0, 10 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 3, 10 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 0, 3 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 3, 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckSizeRangeAsValid")
    public <TKey, TValue> void shouldCheckSizeRangeAsValid(
    		Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithInvalidMinOrMaxLen() {
        return new Object[][] {
                { new HashMap<Object, Object>(), 3, 4 },
                { MapUtils.asHashMap("a", 1), -3, 3 },
                { MapUtils.asHashMap("a", 1, "b", 2), 1, -3 },
                { MapUtils.asHashMap("a", 1), -3, -4 },
                { MapUtils.asHashMap("a", 1), 4, 3 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 6, 7 },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3), 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithInvalidMinOrMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldCheckSizeRangeAsValidWithInvalidMinOrMaxLen(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithNullMap() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithNullMap",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckSizeRangeAsValidWithNullMap(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckSizeRangeAsValidWithNullArgName(
            Map<TKey, TValue> ref, int minSize, int maxSize) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, null);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, 3, "" },
                { null, 4, 3, "   " },  // ASCII spaces
                { null, 6, 7, "　　　" },  // wide Japanese spaces
                { null, 0, 1, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldCheckSizeRangeAsValidWithInvalidArgName(
            Map<TKey, TValue> ref, int minSize, int maxSize, String argName) {
        MapArgs.checkSizeRange(ref, minSize, maxSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotEmpty() {
        return new Object[][] {
                { MapUtils.asHashMap("a", 1) },
                { MapUtils.asHashMap("a", 1, "b", 2) },
                { MapUtils.asHashMap("a", 1, "b", 2, "c", 3) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotEmpty")
    public <TKey, TValue> void shouldCheckAsNotEmpty(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmpty(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithEmptyMap() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableSet.of() },
                { new HashMap<String, String>() },
                { new HashSet<String>() },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithEmptyMap",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckAsNotEmptyWithEmptyMap(Map<TKey, TValue> ref) {
        MapArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckAsNotEmptyWithNullMap() {
        Map<TKey, TValue> ref = null;
        MapArgs.checkNotEmpty(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithNullArgName() {
        return new Object[][] {
                { ImmutableMap.of() },
                { new HashMap<String, String>() },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckAsNotEmptyWithNullArgName(Map<TKey, TValue> ref) {
        String argName = null;
        MapArgs.checkNotEmpty(ref, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithInvalidArgName() {
        return new Object[][] {
                { new HashMap<String, String>(), "" },
                { new HashMap<String, String>(), "   " },  // ASCII spaces
                { new HashMap<String, String>(), "　　　" },  // wide Japanese spaces
                { new HashMap<String, String>(), "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckAsNotEmptyWithInvalidArgName(Map<TKey, TValue> ref, String argName) {
        MapArgs.checkNotEmpty(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkMinSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinSizeAsValid() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { MapUtils.asHashMap("a", 1), 0 },
                { MapUtils.asHashMap("a", 1), 1 },
                { MapUtils.asHashMap("a", 1, "b", 2), 0 },
                { MapUtils.asHashMap("a", 1, "b", 2), 1 },
                { MapUtils.asHashMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinSizeAsValid")
    public <TKey, TValue> void shouldCheckMinSizeAsValid(Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithInvalidMinLen() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { MapUtils.asHashMap("a", 1), -3 },
                { MapUtils.asHashMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithInvalidMinLen",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckMinSizeAsValidWithInvalidMinLen(Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithNullMap() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithNullMap",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckMinSizeAsValidWithNullMap(Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckMinSizeAsValidWithNullArgName(
            Map<TKey, TValue> ref, int minSize) {
        MapArgs.checkMinSize(ref, minSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldCheckMinSizeAsValidWithInvalidArgName(
            Map<TKey, TValue> ref, int minSize, String argName) {
        MapArgs.checkMinSize(ref, minSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkMaxSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxSizeAsValid() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { new HashMap<String, String>(), 99 },
                { MapUtils.asHashMap("a", 1), 1 },
                { MapUtils.asHashMap("a", 1), 99 },
                { MapUtils.asHashMap("a", 1, "b", 2), 2 },
                { MapUtils.asHashMap("a", 1, "b", 2), 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxSizeAsValid")
    public <TKey, TValue> void shouldCheckMaxSizeAsValid(Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithInvalidMaxLen() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { MapUtils.asHashMap("a", 1), -3 },
                { MapUtils.asHashMap("a", 1), 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithInvalidMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckMaxSizeAsValidWithInvalidMaxLen(Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithNullMap() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithNullMap",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckMaxSizeAsValidWithNullMap(Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckMaxSizeAsValidWithNullArgName(
            Map<TKey, TValue> ref, int maxSize) {
        MapArgs.checkMaxSize(ref, maxSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldCheckMaxSizeAsValidWithInvalidArgName(
            Map<TKey, TValue> ref, int maxSize, String argName) {
        MapArgs.checkMaxSize(ref, maxSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkExactSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactSizeAsValid() {
        return new Object[][] {
                { new HashMap<String, String>(), 0 },
                { MapUtils.asHashMap("a", 1), 1 },
                { MapUtils.asHashMap("a", 1, "b", 2), 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactSizeAsValid")
    public <TKey, TValue> void shouldCheckExactSizeAsValid(Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithInvalidExactLen() {
        return new Object[][] {
                { new HashMap<String, String>(), -2 },
                { new HashMap<String, String>(), 2 },
                { MapUtils.asHashMap("a", 1), -3 },
                { MapUtils.asHashMap("a", 1), 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithInvalidExactLen",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckExactSizeAsValidWithInvalidExactLen(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithNullMap() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithNullMap",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckExactSizeAsValidWithNullMap(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldCheckExactSizeAsValidWithNullArgName(
            Map<TKey, TValue> ref, int exactSize) {
        MapArgs.checkExactSize(ref, exactSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldCheckExactSizeAsValidWithInvalidArgName(
            Map<TKey, TValue> ref, int exactSize, String argName) {
        MapArgs.checkExactSize(ref, exactSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysNotNull
    //
    
	@DataProvider
    private static final Object[][] _dataForShouldCheckKeysAsNotNull() {
        return new Object[][] {
        		{ new HashMap<Object, Object>() },
                { MapUtils.asHashMap("abc", 123) },
                { MapUtils.asHashMap("abc", null) },
                { MapUtils.asHashMap("abc", 123, "def", 456) },
                { MapUtils.asHashMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithNullKey() {
        return new Object[][] {
                { MapUtils.asHashMap(null, 123) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(null, 123, "abc", 456) },
                { MapUtils.asHashMap("abc", 123, null, 456) },
                { MapUtils.asHashMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAsNotNullWithNullKey",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAsNotNullWithNullKey(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAsNotNullWithNullMap() {
        MapArgs.checkKeysNotNull(null, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtils.asHashMap(null, 123) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(null, 123, "abc", 456) },
                { MapUtils.asHashMap("abc", 123, null, 456) },
                { MapUtils.asHashMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAsNotNullWithNullArgName(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtils.asHashMap(null, 123), "" },
                { MapUtils.asHashMap(null, null), "" },
                { MapUtils.asHashMap(null, 123, "abc", 456), "" },
                { MapUtils.asHashMap("abc", 123, null, 456), "" },
                { MapUtils.asHashMap("abc", null, null, 456), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtils.asHashMap(null, 123), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtils.asHashMap("abc", 123, null, 456), "   " },
                { MapUtils.asHashMap("abc", null, null, 456), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtils.asHashMap(null, 123), "　　　" },
                { MapUtils.asHashMap(null, null), "　　　" },
                { MapUtils.asHashMap(null, 123, "abc", 456), "　　　" },
                { MapUtils.asHashMap("abc", 123, null, 456), "　　　" },
                { MapUtils.asHashMap("abc", null, null, 456), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtils.asHashMap(null, 123), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtils.asHashMap("abc", 123, null, 456), "   " },
                { MapUtils.asHashMap("abc", null, null, 456), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckKeysAsNotInvalidWithNullArgName(
            Map<TKey, TValue> ref, String argName) {
        MapArgs.checkKeysNotNull(ref, argName);
    }

    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckValuesAsNotNull() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { MapUtils.asHashMap("abc", 123) },
                { MapUtils.asHashMap(null, 123) },
                { MapUtils.asHashMap("abc", 123, "def", 456) },
                { MapUtils.asHashMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValuesAsNotNull")
    public <TKey, TValue> void shouldCheckValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithNullValue() {
        return new Object[][] {
                { MapUtils.asHashMap(123, null) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(123, null, 456, "abc") },
                { MapUtils.asHashMap(123, "abc", 456, null) },
                { MapUtils.asHashMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValuesAsNotNullWithNullValue",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckValuesAsNotNullWithNullValue(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckValuesAsNotNullWithNullMap() {
        MapArgs.checkValuesNotNull(null, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtils.asHashMap(123, null) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(123, null, 456, "abc") },
                { MapUtils.asHashMap(123, "abc", 456, null) },
                { MapUtils.asHashMap(null, "abc", 456, null) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValuesAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckValuesAsNotNullWithNullArgName(
    		Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtils.asHashMap(123, null), "" },
                { MapUtils.asHashMap(null, null), "" },
                { MapUtils.asHashMap(123, null, 456, "abc"), "" },
                { MapUtils.asHashMap(123, "abc", 456, null), "" },
                { MapUtils.asHashMap(null, "abc", 456, null), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtils.asHashMap(123, null), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(123, null, 456, "abc"), "   " },
                { MapUtils.asHashMap(123, "abc", 456, null), "   " },
                { MapUtils.asHashMap(null, "abc", 456, null), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtils.asHashMap(123, null), "　　　" },
                { MapUtils.asHashMap(null, null), "　　　" },
                { MapUtils.asHashMap(123, null, 456, "abc"), "　　　" },
                { MapUtils.asHashMap(123, "abc", 456, null), "　　　" },
                { MapUtils.asHashMap(null, "abc", 456, null), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtils.asHashMap(123, null), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(123, null, 456, "abc"), "   " },
                { MapUtils.asHashMap(123, "abc", 456, null), "   " },
                { MapUtils.asHashMap(null, "abc", 456, null), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValuesAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckValuesAsNotInvalidWithNullArgName(
            Map<TKey, TValue> ref, String argName) {
        MapArgs.checkValuesNotNull(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysAndValuesNotNull
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckKeysAndValuesAsNotNull() {
        return new Object[][] {
                { new HashMap<Object, Object>() },
                { MapUtils.asHashMap("abc", 123) },
                { MapUtils.asHashMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAndValuesAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAndValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithNullKeyOrValue() {
        return new Object[][] {
                { MapUtils.asHashMap(null, 123) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(null, 123, "abc", 456) },
                { MapUtils.asHashMap("abc", 123, null, 456) },
                { MapUtils.asHashMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithNullKeyOrValue",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotNullWithNullKeyOrValue(
    		Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotNullWithNullMap() {
        MapArgs.checkKeysAndValuesNotNull(null, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtils.asHashMap(null, 123) },
                { MapUtils.asHashMap(null, null) },
                { MapUtils.asHashMap(null, 123, "abc", 456) },
                { MapUtils.asHashMap("abc", 123, null, 456) },
                { MapUtils.asHashMap("abc", null, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotNullWithNullArgName(
    		Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtils.asHashMap(null, 123), "" },
                { MapUtils.asHashMap(null, null), "" },
                { MapUtils.asHashMap(null, 123, "abc", 456), "" },
                { MapUtils.asHashMap("abc", 123, null, 456), "" },
                { MapUtils.asHashMap("abc", null, null, 456), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtils.asHashMap(null, 123), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtils.asHashMap("abc", 123, null, 456), "   " },
                { MapUtils.asHashMap("abc", null, null, 456), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtils.asHashMap(null, 123), "　　　" },
                { MapUtils.asHashMap(null, null), "　　　" },
                { MapUtils.asHashMap(null, 123, "abc", 456), "　　　" },
                { MapUtils.asHashMap("abc", 123, null, 456), "　　　" },
                { MapUtils.asHashMap("abc", null, null, 456), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtils.asHashMap(null, 123), "   " },
                { MapUtils.asHashMap(null, null), "   " },
                { MapUtils.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtils.asHashMap("abc", 123, null, 456), "   " },
                { MapUtils.asHashMap("abc", null, null, 456), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotInvalidWithNullArgName(
            Map<TKey, TValue> ref, String argName) {
        MapArgs.checkKeysAndValuesNotNull(ref, argName);
    }
}
