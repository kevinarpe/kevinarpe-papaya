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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ImmutableMapFactoryTest {

    private ImmutableMapFactory<String, Integer> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = ImmutableMapFactory.create();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.create()
    //

    @Test
    public void create_Pass() {
        ImmutableMapFactory<String, String> x = ImmutableMapFactory.create();
        assertNotNull(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.builder()
    //

    @Test
    public void newInstance_Pass() {
        ImmutableMapBuilder<String, Integer> x = classUnderTest.builder();
        assertNotNull(x);
        assertTrue(x.build().isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.copyOf(Class, Class, Object...)
    //

    @Test
    public void copyOf1_Pass() {
        ImmutableMap<String, Integer> srcMap = ImmutableMap.of("abc", 123, "def", 456);
        ImmutableMap<String, Integer> map =
            classUnderTest.copyOf(String.class, Integer.class, "abc", 123, "def", 456);
        MoreAssertUtils.INSTANCE.assertMapEquals(map, srcMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.copyOf(Iterable, Iterable)
    //

    @Test
    public void copyOf2_Pass() {
        ImmutableMap<String, Integer> srcMap = ImmutableMap.of("abc", 123, "def", 456);
        ImmutableMap<String, Integer> map =
            classUnderTest.copyOf(srcMap.keySet(), srcMap.values());
        MoreAssertUtils.INSTANCE.assertMapEquals(map, srcMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.copyOf(Map.Entry...)
    //

    @Test
    public void copyOf3_Pass() {
        ImmutableMap<String, Integer> srcMap = ImmutableMap.of("abc", 123, "def", 456);
        @SuppressWarnings("unchecked")
        ImmutableMap<String, Integer> map =
            classUnderTest.copyOf(srcMap.entrySet().toArray(new Map.Entry[srcMap.size()]));
        MoreAssertUtils.INSTANCE.assertMapEquals(map, srcMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.copyOf(Iterable, Iterable)
    //

    @Test
    public void copyOf4_Pass() {
        ImmutableMap<String, Integer> srcMap = ImmutableMap.of("abc", 123, "def", 456);
        ImmutableMap<String, Integer> map =
            classUnderTest.copyOf(srcMap.entrySet());
        MoreAssertUtils.INSTANCE.assertMapEquals(map, srcMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ImmutableMapFactory.copyOf(Map)
    //

    @Test
    public void copyOf5_Pass() {
        ImmutableMap<String, Integer> srcMap = ImmutableMap.of("abc", 123, "def", 456);
        ImmutableMap<String, Integer> map = classUnderTest.copyOf(srcMap);
        MoreAssertUtils.INSTANCE.assertMapEquals(map, srcMap);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf5_FailWithNull() {
        classUnderTest.copyOf((Map<String, Integer>) null);
    }
}
