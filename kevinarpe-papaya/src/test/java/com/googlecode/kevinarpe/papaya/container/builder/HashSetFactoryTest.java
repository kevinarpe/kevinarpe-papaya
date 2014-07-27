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
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class HashSetFactoryTest {

    private HashSetFactory<String> classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = HashSetFactory.create();
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
    // HashSetBuilderFactory.copyOf(Object...)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf1_FailWithNull() {
        classUnderTest.copyOf((String[]) null);
    }

    @DataProvider
    private static Object[][] _copyOf_Pass_Data() {
        return new Object[][] {
            { Sets.<String>newHashSet() },
            { Sets.newHashSet(Lists.newArrayList("abc")) },
            { Sets.newHashSet(Lists.newArrayList((String) null)) },
            { Sets.newHashSet(Lists.newArrayList("abc", "def")) },
            { Sets.newHashSet(Lists.newArrayList("abc", (String) null)) },
        };
    }

    @Test(dataProvider = "_copyOf_Pass_Data")
    public void copyOf1_Pass(Set<String> srcSet) {
        HashSet<String> set = classUnderTest.copyOf(srcSet.toArray(new String[srcSet.size()]));
        MoreAssertUtils.INSTANCE.assertSetEquals(set, srcSet);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // HashSetBuilderFactory.copyOf(Iterable)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf2_FailWithNull() {
        classUnderTest.copyOf((Iterable<String>) null);
    }

    @Test(dataProvider = "_copyOf_Pass_Data")
    public void copyOf2_Pass(Set<String> srcSet) {
        HashSet<String> set = classUnderTest.copyOf(srcSet);
        MoreAssertUtils.INSTANCE.assertSetEquals(set, srcSet);
    }
}
