package com.googlecode.kevinarpe.papaya.container.builder;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class HashSetBuilderTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilder.create()
    //

    @Test
    public void create_Pass() {
        HashSetBuilder<String> x = HashSetBuilder.create();
        assertNotNull(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        HashSetBuilder<String> classUnderTest = HashSetBuilder.create();
        HashSet<String> set = classUnderTest.build();
        assertTrue(set.isEmpty());
    }

    @Test
    public void build_Pass() {
        ImmutableSet<String> inputSet = ImmutableSet.of("abc", "def", "ghi", "jkl");
        HashSetBuilder<String> classUnderTest = HashSetBuilder.create();
        classUnderTest.addAll(inputSet);
        HashSet<String> set = classUnderTest.build();
        assertEquals(set, inputSet);
    }
}
