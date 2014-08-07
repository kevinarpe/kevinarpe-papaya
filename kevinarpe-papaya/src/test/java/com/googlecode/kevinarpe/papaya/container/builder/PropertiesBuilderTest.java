package com.googlecode.kevinarpe.papaya.container.builder;

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
import com.google.common.collect.Maps;
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import com.googlecode.kevinarpe.papaya.testing.testng.TestNGPermutationBuilderUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class PropertiesBuilderTest {

    private PropertiesBuilder classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = PropertiesBuilder.create();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        Properties map = classUnderTest.build();
        assertTrue(map.isEmpty());
        Properties map2 = classUnderTest.build();
        assertTrue(map2.isEmpty());
        assertNotSame(map, map2);
    }

    @DataProvider
    private static Object[][] _build_Pass_Data() {
        return new Object[][] {
            { ImmutableMap.of() },
            { ImmutableMap.of("abc", "ABC") },
            { ImmutableMap.of("abc", "ABC", "def", "DEF") },
        };
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Map<String, String> inputMap) {
        PropertiesBuilder classUnderTest = PropertiesBuilder.create();
        classUnderTest.putAll(inputMap);
        Properties map = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertMapEquals(map, inputMap);
        Properties map2 = classUnderTest.build();
        assertNotSame(map, map2);
        MoreAssertUtils.INSTANCE.assertMapEquals(map2, inputMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.put()
    //

    @DataProvider
    private static Object[][] _put_FailWithClassCastException_Data() {
        return new Object[][] {
            { 123, "abc" },
            { "abc", 123 },
            { 123, 456 },
        };
    }

    @Test(expectedExceptions = ClassCastException.class,
            dataProvider = "_put_FailWithClassCastException_Data")
    public void put_FailWithClassCastException(Object key, Object value) {
        classUnderTest.put(key, value);
    }

    @DataProvider
    private static Object[][] _put_Pass_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withParam(null, "", "   ", "abc")
                .addParam(null, "", "   ", "def")
                .build();
        return x;
    }

    @Test(dataProvider = "_put_Pass_Data")
    public void put_Pass(Object key, Object value) {
        classUnderTest.put(key, value);
        assertTrue(classUnderTest.containsKey(key));
        assertEquals(classUnderTest.get(key), value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.putAll()
    //

    @DataProvider
    private static Object[][] _putAll_FailWithClassCastException_Data() {
        return new Object[][] {
            { ImmutableMap.of(123, "abc") },
            { ImmutableMap.of("abc", 123) },
            { ImmutableMap.of(123, 456) },
        };
    }

    @Test(expectedExceptions = ClassCastException.class,
            dataProvider = "_putAll_FailWithClassCastException_Data")
    public void putAll_FailWithClassCastException(Map<?, ?> map) {
        classUnderTest.putAll(map);
    }

    @Test(dataProvider = "_put_Pass_Data")
    public void putAll_Pass(String key, String value) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put(key, value);
        classUnderTest.putAll(map);
        assertTrue(classUnderTest.containsKey(key));
        assertEquals(classUnderTest.get(key), value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.getProperty(String)
    //

    @Test(dataProvider = "_put_Pass_Data")
    public void getProperty_String_Pass(String key, String value) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put(key, value);
        classUnderTest.putAll(map);
        assertEquals(classUnderTest.getProperty(key), value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.getProperty(String, String)
    //

    @Test(dataProvider = "_put_Pass_Data")
    public void getProperty_StringString_Pass(String key, String value) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put(key, value);
        classUnderTest.putAll(map);
        String defaultValue = UUID.randomUUID().toString();
        String expectedValue = (null == value) ? defaultValue : value;
        assertEquals(classUnderTest.getProperty(key, defaultValue), expectedValue);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.setProperty(String, String)
    //

    @Test(dataProvider = "_put_Pass_Data")
    public void setProperty_Pass(String key, String value) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put(key, value);
        {
            Object oldValue = classUnderTest.setProperty(key, value);
            assertNull(oldValue);
            assertTrue(classUnderTest.containsKey(key));
            assertEquals(classUnderTest.get(key), value);
        }
        {
            Object oldValue = classUnderTest.setProperty(key, value);
            assertSame(oldValue, value);
            assertTrue(classUnderTest.containsKey(key));
            assertEquals(classUnderTest.get(key), value);
        }
    }
}
