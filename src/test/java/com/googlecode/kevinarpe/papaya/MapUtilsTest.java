package com.googlecode.kevinarpe.papaya;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MapUtilsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // MapUtil.asHashMap
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCreateHashMap() {
        return new Object[][] {
        		{ },
        		{ null, null },
        		{ "abc", 123 },
        		{ "abc", 123, null, 456 },
        		{ "abc", 123, null, null },
        		{ "abc", 123, "def", null },
        		{ "abc", 123, "def", 456 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCreateHashMap")
    public void shouldCreateHashMap(Object... keyAndValueArr) {
    	List<Object> keyAndValueList = Arrays.asList(keyAndValueArr);
    	List<Object> keyList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	List<Object> valueList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	_createKeyAndValueLists(keyAndValueArr, keyList, valueList);
    	
    	HashMap<?, ?> map1 = MapUtils.asHashMap(keyAndValueArr);
    	_shouldCreateHashMap(map1, keyAndValueList);
    	HashMap<?, ?> map2 = MapUtils.asHashMap2(keyAndValueList);
    	_shouldCreateHashMap(map2, keyAndValueList);
    	HashMap<?, ?> map3 = MapUtils.asHashMap3(keyList, valueList);
    	_shouldCreateHashMap(map3, keyAndValueList);
    	
    	HashMap<Object, Object> map4 = new HashMap<Object, Object>();
    	
    	map4.clear();
    	MapUtils.putKeysAndValues(map4, keyAndValueArr);
    	_shouldCreateHashMap(map4, keyAndValueList);
    	MapUtils.putKeysAndValues(map4, keyAndValueArr);
    	_shouldCreateHashMap(map4, keyAndValueList);
    	
    	map4.clear();
    	MapUtils.putKeysAndValues2(map4, keyAndValueList);
    	_shouldCreateHashMap(map4, keyAndValueList);
    	MapUtils.putKeysAndValues2(map4, keyAndValueList);
    	_shouldCreateHashMap(map4, keyAndValueList);
    	
    	map4.clear();
    	MapUtils.putKeysAndValues3(map4, keyList, valueList);
    	_shouldCreateHashMap(map4, keyAndValueList);
    	MapUtils.putKeysAndValues3(map4, keyList, valueList);
    	_shouldCreateHashMap(map4, keyAndValueList);
    }
    
    private void _shouldCreateHashMap(HashMap<?, ?> map, List<?> keyAndValueList) {
    	int size = keyAndValueList.size();
    	Assert.assertEquals(size / 2, map.size());
    	for (int i = 0; i < size; i += 2) {
    		Object key = keyAndValueList.get(i);
    		Object value = keyAndValueList.get(i + 1);
    		Assert.assertTrue(map.containsKey(key));
    		Object valueForKey = map.get(key);
    		Assert.assertEquals(value, valueForKey);
    	}
    }
    
    private void _createKeyAndValueLists(
    		Object[] keyAndValueArr, List<Object> keyList, List<Object> valueList) {
    	for (int i = 0; i < keyAndValueArr.length; i += 2) {
    		Object key = keyAndValueArr[i];
    		Object value = keyAndValueArr[i + 1];
    		keyList.add(key);
    		valueList.add(value);
    	}
    }
    
    @DataProvider
    private static final Object[][] _dataWithDupicateKeys() {
        return new Object[][] {
        		{ null, 123, null, 456 },
        		{ "abc", 123, "abc", 456 },
        		{ "abc", 123, "def", 456, "def", 789 },
        };
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCreateHashMapWithDupicateKeys(Object... keyAndValueArr) {
    	MapUtils.asHashMap(keyAndValueArr);
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCreateHashMapWithDupicateKeys2(Object... keyAndValueArr) {
    	List<Object> keyAndValueList = Arrays.asList(keyAndValueArr);
    	MapUtils.asHashMap2(keyAndValueList);
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCreateHashMapWithDupicateKeys3(Object... keyAndValueArr) {
    	List<Object> keyList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	List<Object> valueList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	_createKeyAndValueLists(keyAndValueArr, keyList, valueList);
    	MapUtils.asHashMap3(keyList, valueList);
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotPutKeyAndValuesWithDupicateKeys(Object... keyAndValueArr) {
    	HashMap<Object, Object> map4 = new HashMap<Object, Object>();
    	MapUtils.putKeysAndValues(map4, keyAndValueArr);
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotPutKeyAndValuesWithDupicateKeys2(Object... keyAndValueArr) {
    	HashMap<Object, Object> map4 = new HashMap<Object, Object>();
    	List<Object> keyAndValueList = Arrays.asList(keyAndValueArr);
    	MapUtils.putKeysAndValues2(map4, keyAndValueList);
    }
    
    @Test(dataProvider = "_dataWithDupicateKeys",
    		expectedExceptions = IllegalArgumentException.class)
    public void shouldNotPutKeyAndValuesWithDupicateKeys3(Object... keyAndValueArr) {
    	HashMap<Object, Object> map4 = new HashMap<Object, Object>();
    	List<Object> keyList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	List<Object> valueList = new ArrayList<Object>(keyAndValueArr.length / 2);
    	_createKeyAndValueLists(keyAndValueArr, keyList, valueList);
    	MapUtils.putKeysAndValues3(map4, keyList, valueList);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCreateHashMapWithNullList() {
    	MapUtils.asHashMap2(null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCreateHashMapWithNullLists() {
        return new Object[][] {
        		{ null, null },
        		{ new ArrayList<Object>(), null },
        		{ null, new ArrayList<Object>() },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCreateHashMapWithNullLists",
    		expectedExceptions = NullPointerException.class)
    public <TKey, TValue> void shouldNotCreateHashMapWithNullLists(
    		List<TKey> keyList, List<TValue> valueList) {
    	MapUtils.asHashMap3(null, null);
    }
    
    
    @DataProvider
    private static final Object[][] _dataForShouldNotPutKeysAndValuesWithNullInputs() {
        return new Object[][] {
        		{ null, new Object[] { "abc", 123 } },
        		{ new HashMap<Object, Object>(), null },
        		{ null, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotPutKeysAndValuesWithNullInputs",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotPutKeysAndValuesWithNullInputs(
    		HashMap<Object, Object> map, Object[] keyAndValueArr) {
    	MapUtils.putKeysAndValues(map, keyAndValueArr);
    }
    
    @Test(dataProvider = "_dataForShouldNotPutKeysAndValuesWithNullInputs",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotPutKeysAndValuesWithNullInputs2(
    		HashMap<Object, Object> map, Object[] keyAndValueArr) {
    	List<Object> keyAndValueList =
			(null == keyAndValueArr ? null : Arrays.asList(keyAndValueArr));
    	MapUtils.putKeysAndValues2(map, keyAndValueList);
    }
    
    @Test(dataProvider = "_dataForShouldNotPutKeysAndValuesWithNullInputs",
    		expectedExceptions = NullPointerException.class)
    public void shouldNotPutKeysAndValuesWithNullInputs3(
    		HashMap<Object, Object> map, Object[] keyAndValueArr) {
    	List<Object> keyList = null;
    	List<Object> valueList = null;
		if (null != keyAndValueArr) {
	    	keyList = new ArrayList<Object>(keyAndValueArr.length / 2);
	    	valueList = new ArrayList<Object>(keyAndValueArr.length / 2);
	    	_createKeyAndValueLists(keyAndValueArr, keyList, valueList);
		}
    	MapUtils.putKeysAndValues3(map, keyList, valueList);
    }
    
    // TODO: Type cast exception
    // TODO: Non-even-sized key-value arrays/lists
}
