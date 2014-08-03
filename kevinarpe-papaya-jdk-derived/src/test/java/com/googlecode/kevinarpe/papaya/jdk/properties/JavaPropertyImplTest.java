package com.googlecode.kevinarpe.papaya.jdk.properties;

/*
 * #%L
 * This file is part of Papaya (JDK Derived Classes).
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya (JDK Derived Classes) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2 only as
 * published by the Free Software Foundation.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in the LICENSE
 * file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Special notice for this module of Papaya:
 * Classes in this module may contain significant portions that are originally
 * part of the JDK source base.  In such cases, prominent notices appear before
 * these blocks of source code.
 * #L%
 */

import com.google.common.testing.EqualsTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JavaPropertyImplTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _assertKeyAndValue(JavaPropertyImpl classUnderTest, String key, String value) {
        assertEquals(classUnderTest.getKey(), key);
        assertEquals(classUnderTest.getValue(), value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_Pass_Data() {
        return new Object[][] {
            { "", "def" },
            { "abc", "" },
            { "", "" },
        };
    }

    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(String key, String value) {
        _assertKeyAndValue(new JavaPropertyImpl(key, value), key, value);
    }

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        return new Object[][] {
            { null, "def" },
            { "abc", null },
            { null, null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_ctor_FailWithNull_Data")
    public void ctor_FailWithNull(String key, String value) {
        new JavaPropertyImpl(key, value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("abc", "def"), new JavaPropertyImpl("abc", "def"));
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("abc", "ghi"), new JavaPropertyImpl("abc", "ghi"));
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("ghi", "def"), new JavaPropertyImpl("ghi", "def"));
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("", "def"), new JavaPropertyImpl("", "def"));
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("abc", ""), new JavaPropertyImpl("abc", ""));
        equalsTester.addEqualityGroup(
            new JavaPropertyImpl("", ""), new JavaPropertyImpl("", ""));
        equalsTester.testEquals();
    }
}
