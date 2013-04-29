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

import java.util.Arrays;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MapUtilTest {

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
        		{ "abc", 123 },
        		{ "abc", 123, "def", 456 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCreateHashMap")
    public void shouldCreateHashMap(Object... keyAndValueArr) {
    	HashMap<?, ?> map = MapUtil.asHashMap(Arrays.asList(keyAndValueArr));
    	Assert.assertEquals(keyAndValueArr.length / 2, map.size());
    	for (int i = 0; i < keyAndValueArr.length; i += 2) {
    		Object key = keyAndValueArr[i];
    		Object value = keyAndValueArr[1 + i];
    		Assert.assertTrue(map.containsKey(key));
    		Object valueForKey = map.get(key);
    		Assert.assertEquals(value, valueForKey);
    	}
    }
}
