package com.googlecode.kevinarpe.papaya.argument;

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

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ObjectArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkNotNull
    //
    
    @DataProvider
    public static Object[][] checkNotNull_Pass_Data() {
        return new Object[][] {
                { (byte)7 },
                { (short)7 },
                { (int)7 },
                { (long)7 },
                { (float)7 },
                { (double)7 },
                { (char)7 },
                { "" },
                { "abc" },
                { new Object() },
                { new ArrayList<Integer>() },
                { (boolean)true },
                { (boolean)false },
        };
    }
    
    @Test(dataProvider = "checkNotNull_Pass_Data")
    public void checkNotNull_Pass(Object x) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(x == ObjectArgs.checkNotNull(x, "x"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(x == ObjectArgs.checkNotNull(x, null));
        Assert.assertTrue(x == ObjectArgs.checkNotNull(x, ""));
        Assert.assertTrue(x == ObjectArgs.checkNotNull(x, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotNull_FailWithNull_Data() {
        return new Object[][] {
                { null, null },
                { null, "value" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "checkNotNull_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotNull_FailWithNull(Object x, String argName) {
        ObjectArgs.checkNotNull(x, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkInstanceOfType
    //
    
    @DataProvider
    public static Object[][] checkInstanceOfType_Pass_Data() {
        String s = "";
        Integer i = 0;
        return new Object[][] {
            { s, String.class },
            { s, CharSequence.class },
            { s, Comparable.class },
            { i, Integer.class },
            { i, Number.class },
            { i, Comparable.class },
        };
    }
    
    final List<String> ARG_NAME_LIST = Arrays.asList("blah", null, "", "   ");
    
    @Test(dataProvider = "checkInstanceOfType_Pass_Data")
    public void checkInstanceOfType_Pass(Object ref, Class<?> destClass) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            ref ==
                ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName"));
        // Demonstrate argName can be anything ridiculous.
        for (String refArgName: ARG_NAME_LIST) {
            for (String destClassArgName: ARG_NAME_LIST) {
                Assert.assertTrue(
                    ref == ObjectArgs.checkInstanceOfType(
                                ref, destClass, refArgName, destClassArgName));
            }
        }
    }
    
    @Test(dataProvider = "checkInstanceOfType_Pass_Data")
    public void checkInstanceOfType_Pass2(Object ref, Class<?> destClass) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            ref ==
                ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName"));
        // Demonstrate argName can be anything ridiculous.
        for (String refArgName: ARG_NAME_LIST) {
            Assert.assertTrue(ref == ObjectArgs.checkInstanceOfType(ref, destClass, refArgName));
        }
    }
    
    @DataProvider
    public static Object[][] checkInstanceOfType_FailWithInvalidDestClass_Data() {
        return new Object[][] {
                { new String(), Integer.class },
                { new StringBuilder(), String.class },
        };
    }
    
    @Test(dataProvider = "checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkInstanceOfType_FailWithInvalidDestClass(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkInstanceOfType_FailWithInvalidDestClass2(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName");
    }
    
    @DataProvider
    public static Object[][] checkInstanceOfType_FailWithNulls_Data() {
        return new Object[][] {
                { null, null },
                { null, String.class },
                { new String(), null },
        };
    }
    
    @Test(dataProvider = "checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInstanceOfType_FailWithNulls(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInstanceOfType_FailWithNulls2(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkCast
    //

    @Test(dataProvider = "checkInstanceOfType_Pass_Data")
    public void checkCast_Pass(Object ref, Class<?> destClass) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            ref ==
                ObjectArgs.checkCast(ref, destClass, "refArgName", "destClassArgName"));
        // Demonstrate argName can be anything ridiculous.
        for (String refArgName: ARG_NAME_LIST) {
            for (String destClassArgName: ARG_NAME_LIST) {
                Assert.assertTrue(
                    ref == ObjectArgs.checkCast(
                        ref, destClass, refArgName, destClassArgName));
            }
        }
    }

    @Test(dataProvider = "checkInstanceOfType_Pass_Data")
    public void checkCast_Pass2(Object ref, Class<?> destClass) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            ref ==
                ObjectArgs.checkCast(ref, destClass, "refArgName"));
        // Demonstrate argName can be anything ridiculous.
        for (String refArgName: ARG_NAME_LIST) {
            Assert.assertTrue(ref == ObjectArgs.checkCast(ref, destClass, refArgName));
        }
    }

    @Test(dataProvider = "checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkCast_FailWithInvalidDestClass(Object ref, Class<?> destClass) {
        ObjectArgs.checkCast(ref, destClass, "refArgName", "destClassArgName");
    }

    @Test(dataProvider = "checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkCast_FailWithInvalidDestClass2(Object ref, Class<?> destClass) {
        ObjectArgs.checkCast(ref, destClass, "refArgName");
    }

    @Test(dataProvider = "checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkCast_FailWithNulls(Object ref, Class<?> destClass) {
        ObjectArgs.checkCast(ref, destClass, "refArgName", "destClassArgName");
    }

    @Test(dataProvider = "checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkCast_FailWithNulls2(Object ref, Class<?> destClass) {
        ObjectArgs.checkCast(ref, destClass, "refArgName");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkAssignableToType
    //
    
    @DataProvider
    public static Object[][] checkAssignableToType_Pass_Data() {
        return new Object[][] {
                { String.class, String.class },
                { String.class, CharSequence.class },
                { String.class, Comparable.class },
                { Integer.class, Integer.class },
                { Integer.class, Number.class },
                { Integer.class, Comparable.class },
        };
    }
    
    @Test(dataProvider = "checkAssignableToType_Pass_Data")
    public void checkAssignableToType_Pass(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
        
        // Demonstrate argName can be anything ridiculous.
        for (String srcClassArgName: ARG_NAME_LIST) {
            for (String destClassArgName: ARG_NAME_LIST) {
                ObjectArgs.checkAssignableToType(
                    srcClass, destClass, srcClassArgName, destClassArgName);
            }
        }
    }
    
    @Test(dataProvider = "checkAssignableToType_Pass_Data")
    public void checkAssignableToType_Pass2(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName");
        
        // Demonstrate argName can be anything ridiculous.
        for (String srcClassArgName: ARG_NAME_LIST) {
            ObjectArgs.checkAssignableToType(srcClass, destClass, srcClassArgName);
        }
    }
    
    @DataProvider
    public static Object[][] checkAssignableToType_FailWithInvalidDestClass_Data() {
        return new Object[][] {
                { String.class, Integer.class },
                { StringBuilder.class, String.class },
        };
    }
    
    @Test(dataProvider = "checkAssignableToType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkAssignableToType_FailWithInvalidDestClass(
            Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "checkAssignableToType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkAssignableToType_FailWithInvalidDestClass2(
            Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(srcClass, destClass, "srcClassArgName");
    }
    
    @DataProvider
    public static Object[][] checkAssignableToType_FailWithNulls_Data() {
        return new Object[][] {
                { null, null },
                { null, String.class },
                { String.class, null },
        };
    }
    
    @Test(dataProvider = "checkAssignableToType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAssignableToType_FailWithNulls(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "checkAssignableToType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAssignableToType_FailWithNulls2(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(srcClass, destClass, "srcClassArgName");
    }
}
