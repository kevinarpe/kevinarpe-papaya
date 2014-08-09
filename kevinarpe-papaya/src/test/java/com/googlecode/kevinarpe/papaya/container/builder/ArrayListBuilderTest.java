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
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertTrue;

public class ArrayListBuilderTest {

    private ArrayListBuilder<String> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = ArrayListBuilder.create();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ArrayListBuilder.build()
    //

    @Test
    public void build_PassWithEmpty() {
        ArrayList<String> list = classUnderTest.build();
        assertTrue(list.isEmpty());
        ArrayList<String> list2 = classUnderTest.build();
        assertNotSame(list2, list);
        MoreAssertUtils.INSTANCE.assertListEquals(list2, list);
    }

    @Test
    public void build_Pass() {
        ImmutableList<String> inputList = ImmutableList.of("abc", "def", "ghi", "jkl");
        classUnderTest.addAll(inputList);
        ArrayList<String> list = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertListEquals(list, inputList);
        ArrayList<String> list2 = classUnderTest.build();
        assertNotSame(list2, list);
        MoreAssertUtils.INSTANCE.assertListEquals(list2, inputList);
    }
}
