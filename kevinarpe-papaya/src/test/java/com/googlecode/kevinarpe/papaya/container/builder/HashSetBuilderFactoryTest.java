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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class HashSetBuilderFactoryTest {

    private HashSetBuilderFactory<String> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = HashSetBuilderFactory.create();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilderFactory.create()
    //

    @Test
    public void create_Pass() {
        assertNotNull(classUnderTest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilderFactory.builder()
    //

    @Test
    public void builder_Pass() {
        HashSetBuilder<String> x = classUnderTest.builder();
        assertTrue(x.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilderFactory.copyOf()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_FailWithNull() {
        classUnderTest.copyOf((Collection<String>) null);
    }

    @DataProvider
    private static Object[][] _copyOf_Pass_Data() {
        return new Object[][] {
            {
                Lists.<String>newArrayList(),
                Lists.<String>newArrayList()
            },
            {
                Lists.<String>newArrayList("abc", "def", "ghi", "abc"),
                Sets.<String>newHashSet("abc", "def", "ghi")
            },
            {
                Lists.<String>newLinkedList(),
                Lists.<String>newArrayList()
            },
            {
                Lists.<String>newLinkedList(Arrays.asList("abc", "def", "ghi", "abc")),
                Lists.<String>newArrayList("abc", "def", "ghi")
            },
            {
                Sets.<String>newHashSet(),
                Lists.<String>newArrayList()
            },
            {
                Sets.<String>newHashSet("def", "ghi", "abc", "abc"),
                Lists.<String>newArrayList(Sets.<String>newHashSet("def", "ghi", "abc", "abc"))
            },
            {
                Sets.<String>newLinkedHashSet(),
                Lists.<String>newArrayList()
            },
            {
                Sets.<String>newLinkedHashSet(Arrays.asList("def", "ghi", "abc", "abc")),
                Lists.<String>newArrayList("def", "ghi", "abc")
            }
        };
    }

    @Test(dataProvider = "_copyOf_Pass_Data")
    public void copyOf_Pass(Collection<String> source, HashSet<String> expected) {
        HashSet<String> copy = classUnderTest.copyOf(source);
        assertEquals(copy, expected);
    }
}
