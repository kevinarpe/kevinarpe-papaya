package com.googlecode.kevinarpe.papaya.argument;

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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ByteArrayArgsTest {
    
    public static Byte[] toByteObjectArray(byte[] arr) {
        Byte[] arr2 = new Byte[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkPositive(byte[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1 } },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 99 } },
                { new byte[] { (byte) 99, (byte) 101, (byte) 103 } },
                { new byte[] { (byte) 1, Byte.MAX_VALUE } },
                { new byte[] { (byte)(1.0f + PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new byte[] { (byte)(1.0f - PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new byte[] { (byte)(1.0d + PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
                { new byte[] { (byte)(1.0d - PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(byte[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) 0, 4, 5, 6 } },
                { new byte[] { 4, (byte) 0, 5, 6 } },
                { new byte[] { 4, (byte) 0, 5, 6 } },
                { new byte[] { 4, 5, (byte) 0, 6 } },
                { new byte[] { 4, 5, 6, (byte) 0 } },
                { new byte[] { (byte) -1, (byte) 99, (byte) 101, (byte) 103 } },
                { new byte[] { (byte) 99, (byte) -1, (byte) 101, (byte) 103 } },
                { new byte[] { (byte) -1, (byte) 99, (byte) 101, (byte) -1, (byte) 103 } },
                { new byte[] { (byte) -1, (byte) 99, (byte) 101, (byte) 103, (byte) -1 } },
                { new byte[] { Byte.MIN_VALUE, (byte) 1, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 1, Byte.MIN_VALUE, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 1, (byte) 2, Byte.MIN_VALUE, (byte) 3 } },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(byte[] arr) {
        ByteArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        ByteArrayArgs.checkPositive((byte[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        ByteArrayArgs.checkPositive((Byte[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 0 } },
                { new byte[] { (byte) 0, (byte) -1, (byte) -2, (byte) -3 } },
                { new byte[] { -99 } },
                { new byte[] { -99, (byte) -101, (byte) -103 } },
                { new byte[] { Byte.MIN_VALUE, (byte) 0, (byte) -1, (byte) -2 } },
                { new byte[] { (byte) 0, Byte.MIN_VALUE, (byte) -1, (byte) -2 } },
                { new byte[] { (byte) 0, (byte) -1, Byte.MIN_VALUE, (byte) -2 } },
                { new byte[] { (byte) 0, (byte) -1, (byte) -2, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(byte[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1 } },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 99 } },
                { new byte[] { (byte) 99, (byte) 101, (byte) 103 } },
                { new byte[] { Byte.MAX_VALUE } },
                { new byte[] { (byte) 1, Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(byte[] arr) {
        ByteArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        ByteArrayArgs.checkNotPositive((byte[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        ByteArrayArgs.checkNotPositive((Byte[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) -1 } },
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 } },
                { new byte[] { -99 } },
                { new byte[] { -99, (byte) -101, (byte) -103 } },
                { new byte[] { Byte.MIN_VALUE, (byte) -1 } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(byte[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) 0, (byte) 1, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 1, (byte) 0, (byte) 2, (byte) 3 } },
                { new byte[] { (byte) 1, (byte) 2, (byte) 0, (byte) 3 } },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 0 } },
                { new byte[] { (byte) 1 } },
                { new byte[] { Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(byte[] arr) {
        ByteArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        ByteArrayArgs.checkNegative((byte[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        ByteArrayArgs.checkNegative((Byte[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 0 } },
                { new byte[] { (byte) 0, (byte) 1, (byte) 2 } },
                { new byte[] { (byte) 99 } },
                { new byte[] { (byte) 99, (byte) 101, (byte) 103 } },
                { new byte[] { (byte) 1, Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(byte[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) -1, 4, 5, 6 } },
                { new byte[] { 4, (byte) -1, 5, 6 } },
                { new byte[] { 4, 5, (byte) -1, 6 } },
                { new byte[] { 4, 5, 6, (byte) -1 } },
                { new byte[] { Byte.MIN_VALUE, (byte) 0, (byte) 1, (byte) 2 } },
                { new byte[] { (byte) 0, Byte.MIN_VALUE, (byte) 1, (byte) 2 } },
                { new byte[] { (byte) 0, (byte) 1, Byte.MIN_VALUE, (byte) 2 } },
                { new byte[] { (byte) 0, (byte) 1, (byte) 2, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(byte[] arr) {
        ByteArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(byte[] arr) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        ByteArrayArgs.checkNotNegative((byte[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        ByteArrayArgs.checkNotNegative((Byte[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1 }, (byte) -1, (byte) 2 },
                { new byte[] { (byte) 1 }, (byte) -1, (byte) 1 },
                { new byte[] { (byte) 1 }, (byte) 0, (byte) 1 },
                { new byte[] { (byte) 1 }, (byte) 0, (byte) 2 },
                { new byte[] { (byte) 1 }, (byte) 1, (byte) 1 },
                { new byte[] { (byte) 1 }, (byte) 1, (byte) 2 },
                
                { new byte[] { (byte) 1, (byte) 0 }, (byte) -1, (byte) 2 },
                { new byte[] { (byte) 1, (byte) 0 }, (byte) -1, (byte) 1 },
                { new byte[] { (byte) 1, (byte) 0 }, (byte) 0, (byte) 1 },
                { new byte[] { (byte) 1, (byte) 0 }, (byte) 0, (byte) 2 },
                { new byte[] { (byte) 1 }, (byte) 1, (byte) 1 },
                { new byte[] { (byte) 1, (byte) 2 }, (byte) 1, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(byte[] arr, byte minValue, byte maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 1 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 1 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 1 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) 1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 1 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) -1 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) -1 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -1 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -1 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) -1 }, (byte) 2, (byte) 1 },
                
                { new byte[] { (byte) 1, (byte) 1 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) -1, (byte) -1 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) 2, (byte) 1 },
                
                { new byte[] { (byte) 1, 5 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 1, 5 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 1, 5 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 1, 5 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) 1, 5 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 1, 5 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) -1, -5 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) -1, -5 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -1, -5 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1, -5 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -1, -5 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) -1, -5 }, (byte) 2, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            byte[] arr, byte minValue, byte maxValue) {
        ByteArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        ByteArrayArgs.checkValueInsideRange((byte[]) null, (byte) 1, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        ByteArrayArgs.checkValueInsideRange((Byte[]) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkValueInsideRange(arr, (byte) 1, (byte) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 3 }, (byte) -1, (byte) 2 },
                { new byte[] { (byte) 3 }, (byte) -1, (byte) 1 },
                { new byte[] { (byte) 3 }, (byte) 0, (byte) 1 },
                { new byte[] { (byte) 3 }, (byte) 0, (byte) 2 },
                { new byte[] { (byte) 3 }, (byte) 1, (byte) 1 },
                { new byte[] { (byte) 3 }, (byte) 1, (byte) 2 },
                
                { new byte[] { (byte) 3, (byte) 99 }, (byte) -1, (byte) 2 },
                { new byte[] { (byte) 3, (byte) 99 }, (byte) -1, (byte) 1 },
                { new byte[] { (byte) 3, (byte) 99 }, (byte) 0, (byte) 1 },
                { new byte[] { (byte) 3, (byte) 99 }, (byte) 0, (byte) 2 },
                { new byte[] { (byte) 3 }, (byte) 1, (byte) 1 },
                { new byte[] { (byte) 3, (byte) 99 }, (byte) 1, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(byte[] arr, byte minValue, byte maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == ByteArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == ByteArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new byte[] { (byte) 0 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 2 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 2 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) -2 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -2 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) 1 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -2 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 1 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) 1 }, (byte) 2, (byte) 1 },
                
                { new byte[] { (byte) 0, (byte) 0 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 0, (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 2, (byte) 2 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 2, (byte) 2 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) -2, (byte) -2 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -2, (byte) -2 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) 0, (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -2, (byte) -2 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1, (byte) -1 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) 1, (byte) 1 }, (byte) 2, (byte) 1 },
                
                { new byte[] { (byte) 0, (byte) -1 }, (byte) -1, (byte) 0 },
                { new byte[] { (byte) 0, (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) 2, (byte) 2 }, (byte) 2, (byte) 2 },
                { new byte[] { (byte) 2, (byte) 1 }, (byte) 2, (byte) 1 },
                { new byte[] { (byte) -1, (byte) -2 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) -1, (byte) -2 }, (byte) -2, (byte) -1 },
                
                { new byte[] { (byte) 0, (byte) 1 }, (byte) 1, (byte) 0 },
                { new byte[] { (byte) 0, (byte) 0 }, (byte) 0, (byte) 0 },
                { new byte[] { (byte) -2, (byte) -2 }, (byte) -2, (byte) -2 },
                { new byte[] { (byte) -1, (byte) -2 }, (byte) -1, (byte) -2 },
                { new byte[] { (byte) 2, (byte) 1 }, (byte) 1, (byte) 2 },
                { new byte[] { (byte) 1, (byte) 2 }, (byte) 2, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            byte[] arr, byte minValue, byte maxValue) {
        ByteArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        ByteArrayArgs.checkValueOutsideRange((byte[]) null, (byte) 1, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        ByteArrayArgs.checkValueOutsideRange((Byte[]) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkValueOutsideRange(arr, (byte) 1, (byte) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) -1 },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 0 },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 1 },
                
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -3 },
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -4 },
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -5 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(byte[] arr, byte minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(byte[] arr, byte minValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 2 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 3 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 4 },
            
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -1 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) 0 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(byte[] arr, byte minValue) {
        ByteArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(byte[] arr, byte minValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        ByteArrayArgs.checkMinValue((byte[]) null, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        ByteArrayArgs.checkMinValue((Byte[]) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkMinValue(arr, (byte) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 3 },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 4 },
                { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 5 },
                
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -1 },
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) 0 },
                { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(byte[] arr, byte maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(byte[] arr, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) -1 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 0 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 1 },
            
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -3 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -4 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(byte[] arr, byte maxValue) {
        ByteArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(byte[] arr, byte maxValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        ByteArrayArgs.checkMaxValue((byte[]) null, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        ByteArrayArgs.checkMaxValue((Byte[]) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkMaxValue(arr, (byte) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1 }, (byte) 1 },
            { new byte[] { (byte) 1, (byte) 1 }, (byte) 1 },
            { new byte[] { (byte) 1, (byte) 1, (byte) 1 }, (byte) 1 },
            
            { new byte[] { (byte) -1 }, (byte) -1 },
            { new byte[] { (byte) -1, (byte) -1 }, (byte) -1 },
            { new byte[] { (byte) -1, (byte) -1, (byte) -1 }, (byte) -1 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(byte[] arr, byte exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(byte[] arr, byte exactValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) -1 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 0 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 1 },
            
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -3 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -4 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(byte[] arr, byte exactValue) {
        ByteArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(byte[] arr, byte exactValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        ByteArrayArgs.checkExactValue((byte[]) null, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        ByteArrayArgs.checkExactValue((Byte[]) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkExactValue(arr, (byte) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1 }, (byte) 2 },
            { new byte[] { (byte) 1, (byte) 1 }, (byte) 2 },
            { new byte[] { (byte) 1, (byte) 1, (byte) 1 }, (byte) -2 },
            
            { new byte[] { (byte) -1 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -1 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -1, (byte) -1 }, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(byte[] arr, byte exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ByteArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ByteArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == ByteArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(byte[] arr, byte exactValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == ByteArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 1 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 2 },
            { new byte[] { (byte) 1, (byte) 2, (byte) 3 }, (byte) 3 },
            
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -1 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -2 },
            { new byte[] { (byte) -1, (byte) -2, (byte) -3 }, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(byte[] arr, byte exactValue) {
        ByteArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(byte[] arr, byte exactValue) {
        Byte[] arr2 = toByteObjectArray(arr);
        ByteArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        ByteArrayArgs.checkNotExactValue((byte[]) null, (byte) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        ByteArrayArgs.checkNotExactValue((Byte[]) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Byte[] { null, (byte) 1 } },
                { new Byte[] { (byte) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Byte[] arr) {
        ByteArrayArgs.checkNotExactValue(arr, (byte) 1, "arr");
    }
}
