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

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;

public class HashBiMapBuilderTest {

    private HashBiMapBuilder<String, Integer> classUnderTest;
    private HashBiMapBuilder<String, String> classUnderTest2;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = HashBiMapBuilder.create();
        classUnderTest2 = HashBiMapBuilder.create();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashBiMapBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        HashBiMap<String, Integer> map = classUnderTest.build();
        assertTrue(map.isEmpty());
        HashBiMap<String, Integer> map2 = classUnderTest.build();
        assertTrue(map2.isEmpty());
        assertNotSame(map, map2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWithDupeKey() {
        classUnderTest2.put("abc", "abc");
        classUnderTest2.put("def", "abc");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWithDupeValue() {
        classUnderTest2.put("abc", "abc");
        classUnderTest2.inverse().put("def", "abc");
    }

    // TODO: LAST: Expand to test other AbstractBiMap subclasses
    @DataProvider
    private static Object[][] _build_Pass_Data() {
        return new Object[][] {
            { ImmutableMap.of() },
            { ImmutableMap.of("abc", 123) },
            { ImmutableMap.of("abc", 123, "def", 456) },
            { HashMapBuilder.<String, Integer>create().putOne(null, 123).build() },
            { HashMapBuilder.<String, Integer>create().putOne("abc", null).build() },
            { HashMapBuilder.<String, Integer>create().putOne(null, null).build() },
        };
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Map<String, Integer> inputMap) {
        classUnderTest.putAll(inputMap);
        HashBiMap<String, Integer> map = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertMapEquals(map, inputMap);
        HashBiMap<String, Integer> map2 = classUnderTest.build();
        assertNotSame(map, map2);
        MoreAssertUtils.INSTANCE.assertMapEquals(map2, inputMap);
    }
}
