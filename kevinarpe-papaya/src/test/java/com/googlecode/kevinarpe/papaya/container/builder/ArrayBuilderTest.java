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

import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

public class ArrayBuilderTest {

    private ArrayBuilder<String> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = ArrayBuilder.create(String.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.create()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void create_FailWithNull() {
        ArrayBuilder.create((Class<String>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.getComponentType()
    //

    @Test
    public void getComponentType_Pass() {
        assertSame(classUnderTest.getComponentType(), String.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.addMany(Object, Object...)
    //

    @Test
    public void addMany_Object_ObjectArr_Pass() {
        assertSame(classUnderTest.addMany("abc", "def"), classUnderTest);
        assertEquals(classUnderTest.build(), new String[] { "abc", "def" });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.addMany(Object[])
    //

    @Test
    public void addMany_ObjectArr_Pass() {
        String[] arr = {"abc", "def"};
        assertSame(classUnderTest.addMany(arr), classUnderTest);
        assertEquals(classUnderTest.build(), arr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.addMany(Iterable)
    //

    @Test
    public void addMany_Iterable_Pass() {
        ImmutableList<String> list = ImmutableList.of("abc", "def");
        assertSame(classUnderTest.addMany(list), classUnderTest);
        assertEquals(classUnderTest.build(), list.toArray());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayBuilder.addMany(Iterator)
    //

    @Test
    public void addMany_Iterator_Pass() {
        ImmutableList<String> list = ImmutableList.of("abc", "def");
        assertSame(classUnderTest.addMany(list.iterator()), classUnderTest);
        assertEquals(classUnderTest.build(), list.toArray());
    }
}
