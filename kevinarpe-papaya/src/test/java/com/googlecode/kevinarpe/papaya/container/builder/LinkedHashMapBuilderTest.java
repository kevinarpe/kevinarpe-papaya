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
import org.testng.annotations.Test;

import java.util.LinkedHashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class LinkedHashMapBuilderTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LinkedHashMapBuilder.create()
    //

    @Test
    public void create_Pass() {
        LinkedHashMapBuilder<String, String> x = LinkedHashMapBuilder.create();
        assertNotNull(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // LinkedHashMapBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        LinkedHashMapBuilder<String, String> classUnderTest = LinkedHashMapBuilder.create();
        LinkedHashMap<String, String> map = classUnderTest.build();
        assertTrue(map.isEmpty());
    }

    @Test
    public void build_Pass() {
        ImmutableMap<String, String> inputMap = ImmutableMap.of("abc", "def", "ghi", "jkl");
        LinkedHashMapBuilder<String, String> classUnderTest = LinkedHashMapBuilder.create();
        classUnderTest.putAll(inputMap);
        LinkedHashMap<String, String> map = classUnderTest.build();
        assertEquals(map, inputMap);
    }
}
