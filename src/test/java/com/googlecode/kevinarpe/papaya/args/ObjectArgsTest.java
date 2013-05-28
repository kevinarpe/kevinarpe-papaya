package com.googlecode.kevinarpe.papaya.args;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

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
    private static final Object[][] _checkNotNull_Pass_Data() {
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
    
    @Test(dataProvider = "_checkNotNull_Pass_Data")
    public void checkNotNull_Pass(Object x) {
        ObjectArgs.checkNotNull(x, "x");
    }
    
    @DataProvider
    private static final Object[][] _checkNotNull_FailWithNull_Data() {
        return new Object[][] {
                { null, null },
                { null, "value" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_checkNotNull_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotNull_FailWithNull(Object x, String argName) {
        ObjectArgs.checkNotNull(x, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkInstanceOfType
    //
    
    @DataProvider
    private static final Object[][] _checkInstanceOfType_Pass_Data() {
        return new Object[][] {
                { new String(), String.class },
                { new String(), CharSequence.class },
                { new String(), Comparable.class },
                { new Integer(0), Integer.class },
                { new Integer(0), Number.class },
                { new Integer(0), Comparable.class },
        };
    }
    
    @Test(dataProvider = "_checkInstanceOfType_Pass_Data")
    public void checkInstanceOfType_Pass(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName");
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName");
    }
    
    @DataProvider
    private static final Object[][] _checkInstanceOfType_FailWithInvalidDestClass_Data() {
        return new Object[][] {
                { new String(), Integer.class },
                { new StringBuilder(), String.class },
        };
    }
    
    @Test(dataProvider = "_checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkInstanceOfType_FailWithInvalidDestClass(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "_checkInstanceOfType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkInstanceOfType_FailWithInvalidDestClass2(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName");
    }
    
    @DataProvider
    private static final Object[][] _checkInstanceOfType_FailWithNulls_Data() {
        return new Object[][] {
                { null, null },
                { null, String.class },
                { new String(), null },
        };
    }
    
    @Test(dataProvider = "_checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInstanceOfType_FailWithNulls(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "_checkInstanceOfType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInstanceOfType_FailWithNulls2(Object ref, Class<?> destClass) {
        ObjectArgs.checkInstanceOfType(ref, destClass, "refArgName");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ObjectArgs.checkAssignableToType
    //
    
    @DataProvider
    private static final Object[][] _checkAssignableToType_Pass_Data() {
        return new Object[][] {
                { String.class, String.class },
                { String.class, CharSequence.class },
                { String.class, Comparable.class },
                { Integer.class, Integer.class },
                { Integer.class, Number.class },
                { Integer.class, Comparable.class },
        };
    }
    
    @Test(dataProvider = "_checkAssignableToType_Pass_Data")
    public void checkAssignableToType_Pass(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
        ObjectArgs.checkAssignableToType(srcClass, destClass, "srcClassArgName");
    }
    
    @DataProvider
    private static final Object[][] _checkAssignableToType_FailWithInvalidDestClass_Data() {
        return new Object[][] {
                { String.class, Integer.class },
                { StringBuilder.class, String.class },
        };
    }
    
    @Test(dataProvider = "_checkAssignableToType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkAssignableToType_FailWithInvalidDestClass(
            Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "_checkAssignableToType_FailWithInvalidDestClass_Data",
            expectedExceptions = ClassCastException.class)
    public void checkAssignableToType_FailWithInvalidDestClass2(
            Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(srcClass, destClass, "srcClassArgName");
    }
    
    @DataProvider
    private static final Object[][] _checkAssignableToType_FailWithNulls_Data() {
        return new Object[][] {
                { null, null },
                { null, String.class },
                { String.class, null },
        };
    }
    
    @Test(dataProvider = "_checkAssignableToType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAssignableToType_FailWithNulls(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(
            srcClass, destClass, "srcClassArgName", "destClassArgName");
    }
    
    @Test(dataProvider = "_checkAssignableToType_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAssignableToType_FailWithNulls2(Class<?> srcClass, Class<?> destClass) {
        ObjectArgs.checkAssignableToType(srcClass, destClass, "srcClassArgName");
    }
}
