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

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.MapUtil;
import com.googlecode.kevinarpe.papaya.Args.MapArgs;

public class MapArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // MapArgs.checkKeysNotNull
    //
    
    @SuppressWarnings("unchecked")
	@DataProvider
    private static final Object[][] _dataForShouldCheckKeysAsNotNull() {
        return new Object[][] {
                { MapUtil.asHashMap(Collections.emptyList()) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123)) },
                { MapUtil.asHashMap(Arrays.asList("abc", null)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, "def", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, "def", null)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithNullKey() {
        return new Object[][] {
                { MapUtil.asHashMap(Arrays.asList(null, 123)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)) },
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
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtil.asHashMap(Arrays.asList(null, 123)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAsNotNullWithNullArgName(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, null);
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "" },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "" },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "   " },
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
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldCheckValuesAsNotNull() {
        return new Object[][] {
                { MapUtil.asHashMap(Collections.emptyList()) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123)) },
                { MapUtil.asHashMap(Arrays.asList(null, 123)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, "def", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValuesAsNotNull")
    public <TKey, TValue> void shouldCheckValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithNullValue() {
        return new Object[][] {
                { MapUtil.asHashMap(Arrays.asList(123, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")) },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)) },
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
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtil.asHashMap(Arrays.asList(123, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")) },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValuesAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckValuesAsNotNullWithNullArgName(
    		Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, null);
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtil.asHashMap(Arrays.asList(123, null)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "" },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")), "" },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(123, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")), "   " },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(Arrays.asList(123, null)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(123, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(123, null, 456, "abc")), "   " },
                { MapUtil.asHashMap(Arrays.asList(123, "abc", 456, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, "abc", 456, null)), "   " },
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
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldCheckKeysAndValuesAsNotNull() {
        return new Object[][] {
                { MapUtil.asHashMap(Collections.emptyList()) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, "def", 456)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAndValuesAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAndValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithNullKeyOrValue() {
        return new Object[][] {
                { MapUtil.asHashMap(Arrays.asList(null, 123)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)) },
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
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { MapUtil.asHashMap(Arrays.asList(null, 123)) },
                { MapUtil.asHashMap(Arrays.asList(null, null)) },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)) },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotNullWithNullArgName(
    		Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, null);
    }
    
    @SuppressWarnings("unchecked")
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "" },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "" },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "" },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "　　　" },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, null)), "   " },
                { MapUtil.asHashMap(Arrays.asList(null, 123, "abc", 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", 123, null, 456)), "   " },
                { MapUtil.asHashMap(Arrays.asList("abc", null, null, 456)), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotInvalidWithNullArgName(
            Map<TKey, TValue> ref, String argName) {
        MapArgs.checkKeysAndValuesNotNull(ref, argName);
    }
}
