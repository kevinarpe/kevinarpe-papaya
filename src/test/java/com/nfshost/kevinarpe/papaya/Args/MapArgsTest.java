package com.nfshost.kevinarpe.papaya.Args;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
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
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckKeysAsNotNull() {
        return new Object[][] {
                { MapUtil.asHashMap() },
                { MapUtil.asHashMap("abc", 123) },
                { MapUtil.asHashMap("abc", null) },
                { MapUtil.asHashMap("abc", 123, "def", 456) },
                { MapUtil.asHashMap("abc", 123, "def", null) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAsNotNullWithNullKey() {
        return new Object[][] {
                { MapUtil.asHashMap(null, 123) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(null, 123, "abc", 456) },
                { MapUtil.asHashMap("abc", 123, null, 456) },
                { MapUtil.asHashMap("abc", null, null, 456) },
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
                { MapUtil.asHashMap(null, 123) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(null, 123, "abc", 456) },
                { MapUtil.asHashMap("abc", 123, null, 456) },
                { MapUtil.asHashMap("abc", null, null, 456) },
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
                { MapUtil.asHashMap(null, 123), "" },
                { MapUtil.asHashMap(null, null), "" },
                { MapUtil.asHashMap(null, 123, "abc", 456), "" },
                { MapUtil.asHashMap("abc", 123, null, 456), "" },
                { MapUtil.asHashMap("abc", null, null, 456), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(null, 123), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtil.asHashMap("abc", 123, null, 456), "   " },
                { MapUtil.asHashMap("abc", null, null, 456), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(null, 123), "　　　" },
                { MapUtil.asHashMap(null, null), "　　　" },
                { MapUtil.asHashMap(null, 123, "abc", 456), "　　　" },
                { MapUtil.asHashMap("abc", 123, null, 456), "　　　" },
                { MapUtil.asHashMap("abc", null, null, 456), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(null, 123), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtil.asHashMap("abc", 123, null, 456), "   " },
                { MapUtil.asHashMap("abc", null, null, 456), "   " },
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
                { MapUtil.asHashMap() },
                { MapUtil.asHashMap("abc", 123) },
                { MapUtil.asHashMap(null, 123) },
                { MapUtil.asHashMap("abc", 123, "def", 456) },
                { MapUtil.asHashMap("abc", 123, null, 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValuesAsNotNull")
    public <TKey, TValue> void shouldCheckValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkValuesNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValuesAsNotNullWithNullValue() {
        return new Object[][] {
                { MapUtil.asHashMap(123, null) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(123, null, 456, "abc") },
                { MapUtil.asHashMap(123, "abc", 456, null) },
                { MapUtil.asHashMap(null, "abc", 456, null) },
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
                { MapUtil.asHashMap(123, null) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(123, null, 456, "abc") },
                { MapUtil.asHashMap(123, "abc", 456, null) },
                { MapUtil.asHashMap(null, "abc", 456, null) },
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
                { MapUtil.asHashMap(123, null), "" },
                { MapUtil.asHashMap(null, null), "" },
                { MapUtil.asHashMap(123, null, 456, "abc"), "" },
                { MapUtil.asHashMap(123, "abc", 456, null), "" },
                { MapUtil.asHashMap(null, "abc", 456, null), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(123, null), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(123, null, 456, "abc"), "   " },
                { MapUtil.asHashMap(123, "abc", 456, null), "   " },
                { MapUtil.asHashMap(null, "abc", 456, null), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(123, null), "　　　" },
                { MapUtil.asHashMap(null, null), "　　　" },
                { MapUtil.asHashMap(123, null, 456, "abc"), "　　　" },
                { MapUtil.asHashMap(123, "abc", 456, null), "　　　" },
                { MapUtil.asHashMap(null, "abc", 456, null), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(123, null), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(123, null, 456, "abc"), "   " },
                { MapUtil.asHashMap(123, "abc", 456, null), "   " },
                { MapUtil.asHashMap(null, "abc", 456, null), "   " },
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
                { MapUtil.asHashMap() },
                { MapUtil.asHashMap("abc", 123) },
                { MapUtil.asHashMap("abc", 123, "def", 456) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckKeysAndValuesAsNotNull")
    public <TKey, TValue> void shouldCheckKeysAndValuesAsNotNull(Map<TKey, TValue> ref) {
        MapArgs.checkKeysAndValuesNotNull(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckKeysAndValuesAsNotNullWithNullKeyOrValue() {
        return new Object[][] {
                { MapUtil.asHashMap(null, 123) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(null, 123, "abc", 456) },
                { MapUtil.asHashMap("abc", 123, null, 456) },
                { MapUtil.asHashMap("abc", null, null, 456) },
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
                { MapUtil.asHashMap(null, 123) },
                { MapUtil.asHashMap(null, null) },
                { MapUtil.asHashMap(null, 123, "abc", 456) },
                { MapUtil.asHashMap("abc", 123, null, 456) },
                { MapUtil.asHashMap("abc", null, null, 456) },
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
                { MapUtil.asHashMap(null, 123), "" },
                { MapUtil.asHashMap(null, null), "" },
                { MapUtil.asHashMap(null, 123, "abc", 456), "" },
                { MapUtil.asHashMap("abc", 123, null, 456), "" },
                { MapUtil.asHashMap("abc", null, null, 456), "" },
                
                // ASCII spaces
                { null, "   " },
                { MapUtil.asHashMap(null, 123), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtil.asHashMap("abc", 123, null, 456), "   " },
                { MapUtil.asHashMap("abc", null, null, 456), "   " },
                
                // wide Japanese spaces
                { null, "　　　" },
                { MapUtil.asHashMap(null, 123), "　　　" },
                { MapUtil.asHashMap(null, null), "　　　" },
                { MapUtil.asHashMap(null, 123, "abc", 456), "　　　" },
                { MapUtil.asHashMap("abc", 123, null, 456), "　　　" },
                { MapUtil.asHashMap("abc", null, null, 456), "　　　" },
                
                // narrow Japanese spaces
                { null, "   " },
                { MapUtil.asHashMap(null, 123), "   " },
                { MapUtil.asHashMap(null, null), "   " },
                { MapUtil.asHashMap(null, 123, "abc", 456), "   " },
                { MapUtil.asHashMap("abc", 123, null, 456), "   " },
                { MapUtil.asHashMap("abc", null, null, 456), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckKeysAndValuesAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <TKey, TValue> void shouldNotCheckKeysAndValuesAsNotInvalidWithNullArgName(
            Map<TKey, TValue> ref, String argName) {
        MapArgs.checkKeysAndValuesNotNull(ref, argName);
    }
}
