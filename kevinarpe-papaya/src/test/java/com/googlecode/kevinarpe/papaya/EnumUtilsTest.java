package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EnumUtilsTest {

    private EnumUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new EnumUtils();
    }

    private enum TestEnum {
        A, B, C;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // EnumUtils.valueOf(Class<? extends Enum<?>>, String)
    //

    @Test
    public void valueOf_Pass() {
        for (TestEnum e : TestEnum.values()) {
            TestEnum e2 = classUnderTest.valueOf(TestEnum.class, e.name());
            Assert.assertSame(e2, e);
        }
    }

    @DataProvider
    private static Object[][] _valueOf_FailWithNull_Data() {
        return new Object[][] {
            { (Class<?>) null, "blah" },
            { List.class, (String) null },
            { (Class<?>) null, (String) null },
        };
    }

    @Test(dataProvider = "_valueOf_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void valueOf_FailWithNull(Class<TestEnum> enumType, String name) {
        classUnderTest.valueOf(enumType, name);
    }

    @DataProvider
    private static Object[][] _valueOf_FailWithInvalidName_Data() {
        return new Object[][] {
            { "" },
            { "   " },
        };
    }

    @Test(dataProvider = "_valueOf_FailWithInvalidName_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void valueOf_FailWithInvalidName(String name) {
        classUnderTest.valueOf(TestEnum.class, name);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // EnumUtils.tryValueOf(Class<? extends Enum<?>>, String)
    //

    @Test
    public void tryValueOf_Pass() {
        for (TestEnum e : TestEnum.values()) {
            TestEnum e2 = classUnderTest.tryValueOf(TestEnum.class, e.name());
            Assert.assertSame(e2, e);
        }
    }

    @Test(dataProvider = "_valueOf_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void tryValueOf_FailWithNull(Class<TestEnum> enumType, String name) {
        TestEnum e = classUnderTest.tryValueOf(enumType, name);
        Assert.assertNull(e);
    }

    @Test(dataProvider = "_valueOf_FailWithInvalidName_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void tryValueOf_FailWithInvalidName(String name) {
        TestEnum e = classUnderTest.tryValueOf(TestEnum.class, name);
        Assert.assertNull(e);
    }
}
