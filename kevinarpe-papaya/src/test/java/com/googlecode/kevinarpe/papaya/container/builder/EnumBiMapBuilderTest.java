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

import com.google.common.collect.EnumBiMap;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import com.googlecode.kevinarpe.papaya.testing.testng.TestNGPermutationBuilderUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;

public class EnumBiMapBuilderTest {

    private static enum Enum1 { A, B, C }
    private static enum Enum2 { D, E, F }

    private EnumBiMapBuilder<Enum1, Enum2> classUnderTest;
    private EnumBiMapBuilder<Enum1, Enum1> classUnderTest2;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = EnumBiMapBuilder.create(Enum1.class, Enum2.class);
        classUnderTest2 = EnumBiMapBuilder.create(Enum1.class, Enum1.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // EnumBiMapBuilder.create()
    //

    @DataProvider
    private static Object[][] _create_FailWithNull_Data() {
        Object[][] x =
            TestNGPermutationBuilderUtils.INSTANCE
                .withNullableParam(Enum1.class)
                .addNullableParam(Enum2.class).build();
        return x;
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_create_FailWithNull_Data")
    public void create_FailWithNull(Class<Enum1> keyClass, Class<Enum2> valueClass) {
        EnumBiMapBuilder.create(keyClass, valueClass);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // EnumBiMapBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        EnumBiMap<Enum1, Enum2> map = classUnderTest.build();
        assertTrue(map.isEmpty());
        EnumBiMap<Enum1, Enum2> map2 = classUnderTest.build();
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
            { ImmutableMap.of(Enum1.A, Enum2.D) },
            { ImmutableMap.of(Enum1.A, Enum2.D, Enum1.B, Enum2.E) },
        };
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Map<Enum1, Enum2> inputMap) {
        EnumBiMap<Enum1, Enum2> inputBiMap = EnumBiMap.create(Enum1.class, Enum2.class);
        inputBiMap.putAll(inputMap);
        classUnderTest.putAll(inputBiMap);
        EnumBiMap<Enum1, Enum2> map = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertEnumBiMapEquals(Enum1.class, Enum2.class, map, inputBiMap);
        EnumBiMap<Enum1, Enum2> map2 = classUnderTest.build();
        assertNotSame(map, map2);
        MoreAssertUtils.INSTANCE.assertEnumBiMapEquals(Enum1.class, Enum2.class, map2, inputBiMap);
    }
}
