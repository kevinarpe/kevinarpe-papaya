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

import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;

public class EnumHashBiMapBuilderTest {

    private static enum Enum1 { A, B, C }

    private EnumHashBiMapBuilder<Enum1, String> classUnderTest;
    private EnumHashBiMapBuilder<Enum1, Enum1> classUnderTest2;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = EnumHashBiMapBuilder.create(Enum1.class);
        classUnderTest2 = EnumHashBiMapBuilder.create(Enum1.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // EnumHashBiMapBuilder.create()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void create_FailWithNull() {
        EnumHashBiMapBuilder.create((Class<Enum1>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // EnumHashBiMapBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        EnumHashBiMap<Enum1, String> map = classUnderTest.build();
        assertTrue(map.isEmpty());
        EnumHashBiMap<Enum1, String> map2 = classUnderTest.build();
        assertTrue(map2.isEmpty());
        assertNotSame(map, map2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWithDupeKey() {
        classUnderTest2.put(Enum1.A, Enum1.A);
        classUnderTest2.put(Enum1.B, Enum1.A);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void build_FailWithDupeValue() {
        classUnderTest2.put(Enum1.A, Enum1.A);
        classUnderTest2.inverse().put(Enum1.B, Enum1.A);
    }

    @DataProvider
    private static Object[][] _build_Pass_Data() {
        return new Object[][] {
            { ImmutableMap.of() },
            { ImmutableMap.of(Enum1.A, "abc") },
            { ImmutableMap.of(Enum1.A, "abc", Enum1.B, "def") },
            { HashMapBuilder.create().putOne(Enum1.A, (String) null).build() },
        };
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Map<Enum1, String> inputMap) {
        EnumHashBiMap<Enum1, String> inputBiMap = EnumHashBiMap.create(Enum1.class);
        inputBiMap.putAll(inputMap);
        classUnderTest.putAll(inputBiMap);
        EnumHashBiMap<Enum1, String> map = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertEnumHashBiMapEquals(Enum1.class, map, inputBiMap);
        EnumHashBiMap<Enum1, String> map2 = classUnderTest.build();
        assertNotSame(map, map2);
        MoreAssertUtils.INSTANCE.assertEnumHashBiMapEquals(Enum1.class, map2, inputBiMap);
    }
}
