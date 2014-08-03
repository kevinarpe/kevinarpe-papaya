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
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertNotNull;

public class HashMapBuilderTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashMapBuilder.create()
    //

    @Test
    public void create_Pass() {
        HashMapBuilder<String, String> x = HashMapBuilder.create();
        assertNotNull(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashMapBuilder.build()
    //

    @DataProvider
    private static Object[][] _build_Pass_Data() {
        return new Object[][] {
            { ImmutableMap.of()},
            { ImmutableMap.of("abc", 123)},
            { ImmutableMap.of("abc", 123, "def", 456)},
        };
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Map<String, Integer> inputMap) {
        HashMapBuilder<String, Integer> classUnderTest = HashMapBuilder.create();
        classUnderTest.putAll(inputMap);
        HashMap<String, Integer> map = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertMapEquals(map, inputMap);
    }
}
