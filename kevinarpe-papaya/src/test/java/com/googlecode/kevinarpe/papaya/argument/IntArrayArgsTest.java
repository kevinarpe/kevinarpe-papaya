package com.googlecode.kevinarpe.papaya.argument;

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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;

public class IntArrayArgsTest {
    
    public static Integer[] toIntObjectArray(int[] arr) {
        Integer[] arr2 = new Integer[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkPositive(int[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new int[] { 1 } },
                { new int[] { 1, 2, 3 } },
                { new int[] { 99 } },
                { new int[] { 99, 101, 103 } },
                { new int[] { 1, Integer.MAX_VALUE } },
                { new int[] { (int)(1.0f + PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new int[] { (int)(1.0f - PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new int[] { (int)(1.0d + PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
                { new int[] { (int)(1.0d - PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(int[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == IntArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new int[] { 0, 4, 5, 6 } },
                { new int[] { 4, 0, 5, 6 } },
                { new int[] { 4, 0, 5, 6 } },
                { new int[] { 4, 5, 0, 6 } },
                { new int[] { 4, 5, 6, 0 } },
                { new int[] { -1, 99, 101, 103 } },
                { new int[] { 99, -1, 101, 103 } },
                { new int[] { -1, 99, 101, -1, 103 } },
                { new int[] { -1, 99, 101, 103, -1 } },
                { new int[] { Integer.MIN_VALUE, 1, 2, 3 } },
                { new int[] { 1, Integer.MIN_VALUE, 2, 3 } },
                { new int[] { 1, 2, Integer.MIN_VALUE, 3 } },
                { new int[] { 1, 2, 3, Integer.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(int[] arr) {
        IntArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        IntArrayArgs.checkPositive((int[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        IntArrayArgs.checkPositive((Integer[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new int[] { 0 } },
                { new int[] { 0, -1, -2, -3 } },
                { new int[] { -99 } },
                { new int[] { -99, -101, -103 } },
                { new int[] { Integer.MIN_VALUE, 0, -1, -2 } },
                { new int[] { 0, Integer.MIN_VALUE, -1, -2 } },
                { new int[] { 0, -1, Integer.MIN_VALUE, -2 } },
                { new int[] { 0, -1, -2, Integer.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(int[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == IntArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new int[] { 1 } },
                { new int[] { 1, 2, 3 } },
                { new int[] { 99 } },
                { new int[] { 99, 101, 103 } },
                { new int[] { Integer.MAX_VALUE } },
                { new int[] { 1, Integer.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(int[] arr) {
        IntArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        IntArrayArgs.checkNotPositive((int[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        IntArrayArgs.checkNotPositive((Integer[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new int[] { -1 } },
                { new int[] { -1, -2, -3 } },
                { new int[] { -99 } },
                { new int[] { -99, -101, -103 } },
                { new int[] { Integer.MIN_VALUE, -1 } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(int[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == IntArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new int[] { 0, 1, 2, 3 } },
                { new int[] { 1, 0, 2, 3 } },
                { new int[] { 1, 2, 0, 3 } },
                { new int[] { 1, 2, 3, 0 } },
                { new int[] { 1 } },
                { new int[] { Integer.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(int[] arr) {
        IntArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        IntArrayArgs.checkNegative((int[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        IntArrayArgs.checkNegative((Integer[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new int[] { 0 } },
                { new int[] { 0, 1, 2 } },
                { new int[] { 99 } },
                { new int[] { 99, 101, 103 } },
                { new int[] { 1, Integer.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(int[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == IntArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new int[] { -1, 4, 5, 6 } },
                { new int[] { 4, -1, 5, 6 } },
                { new int[] { 4, 5, -1, 6 } },
                { new int[] { 4, 5, 6, -1 } },
                { new int[] { Integer.MIN_VALUE, 0, 1, 2 } },
                { new int[] { 0, Integer.MIN_VALUE, 1, 2 } },
                { new int[] { 0, 1, Integer.MIN_VALUE, 2 } },
                { new int[] { 0, 1, 2, Integer.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(int[] arr) {
        IntArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(int[] arr) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        IntArrayArgs.checkNotNegative((int[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        IntArrayArgs.checkNotNegative((Integer[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new int[] { 1 }, -1, 2 },
                { new int[] { 1 }, -1, 1 },
                { new int[] { 1 }, 0, 1 },
                { new int[] { 1 }, 0, 2 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 1 }, 1, 2 },
                
                { new int[] { 1, 0 }, -1, 2 },
                { new int[] { 1, 0 }, -1, 1 },
                { new int[] { 1, 0 }, 0, 1 },
                { new int[] { 1, 0 }, 0, 2 },
                { new int[] { 1 }, 1, 1 },
                { new int[] { 1, 2 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(int[] arr, int minValue, int maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new int[] { 1 }, -1, 0 },
                { new int[] { 1 }, 0, 0 },
                { new int[] { 1 }, 2, 2 },
                { new int[] { 1 }, 2, 1 },
                { new int[] { 1 }, -1, -2 },
                { new int[] { 1 }, -2, -1 },
                
                { new int[] { -1 }, 1, 0 },
                { new int[] { -1 }, 0, 0 },
                { new int[] { -1 }, -2, -2 },
                { new int[] { -1 }, -1, -2 },
                { new int[] { -1 }, 1, 2 },
                { new int[] { -1 }, 2, 1 },
                
                { new int[] { 1, 1 }, -1, 0 },
                { new int[] { 1, 1 }, 0, 0 },
                { new int[] { 1, 1 }, 2, 2 },
                { new int[] { 1, 1 }, 2, 1 },
                { new int[] { 1, 1 }, -1, -2 },
                { new int[] { 1, 1 }, -2, -1 },
                
                { new int[] { -1, -1 }, 1, 0 },
                { new int[] { -1, -1 }, 0, 0 },
                { new int[] { -1, -1 }, -2, -2 },
                { new int[] { -1, -1 }, -1, -2 },
                { new int[] { -1, -1 }, 1, 2 },
                { new int[] { -1, -1 }, 2, 1 },
                
                { new int[] { 1, 5 }, -1, 0 },
                { new int[] { 1, 5 }, 0, 0 },
                { new int[] { 1, 5 }, 2, 2 },
                { new int[] { 1, 5 }, 2, 1 },
                { new int[] { 1, 5 }, -1, -2 },
                { new int[] { 1, 5 }, -2, -1 },
                
                { new int[] { -1, -5 }, 1, 0 },
                { new int[] { -1, -5 }, 0, 0 },
                { new int[] { -1, -5 }, -2, -2 },
                { new int[] { -1, -5 }, -1, -2 },
                { new int[] { -1, -5 }, 1, 2 },
                { new int[] { -1, -5 }, 2, 1 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            int[] arr, int minValue, int maxValue) {
        IntArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        IntArrayArgs.checkValueInsideRange((int[]) null, 1, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        IntArrayArgs.checkValueInsideRange((Integer[]) null, 1, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkValueInsideRange(arr, 1, 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new int[] { 3 }, -1, 2 },
                { new int[] { 3 }, -1, 1 },
                { new int[] { 3 }, 0, 1 },
                { new int[] { 3 }, 0, 2 },
                { new int[] { 3 }, 1, 1 },
                { new int[] { 3 }, 1, 2 },
                
                { new int[] { 3, 99 }, -1, 2 },
                { new int[] { 3, 99 }, -1, 1 },
                { new int[] { 3, 99 }, 0, 1 },
                { new int[] { 3, 99 }, 0, 2 },
                { new int[] { 3 }, 1, 1 },
                { new int[] { 3, 99 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(int[] arr, int minValue, int maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == IntArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == IntArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new int[] { 0 }, -1, 0 },
                { new int[] { 0 }, 0, 0 },
                { new int[] { 2 }, 2, 2 },
                { new int[] { 2 }, 2, 1 },
                { new int[] { -2 }, -1, -2 },
                { new int[] { -2 }, -2, -1 },
                
                { new int[] { 1 }, 1, 0 },
                { new int[] { 0 }, 0, 0 },
                { new int[] { -2 }, -2, -2 },
                { new int[] { -1 }, -1, -2 },
                { new int[] { 1 }, 1, 2 },
                { new int[] { 1 }, 2, 1 },
                
                { new int[] { 0, 0 }, -1, 0 },
                { new int[] { 0, 0 }, 0, 0 },
                { new int[] { 2, 2 }, 2, 2 },
                { new int[] { 2, 2 }, 2, 1 },
                { new int[] { -2, -2 }, -1, -2 },
                { new int[] { -2, -2 }, -2, -1 },
                
                { new int[] { 1, 1 }, 1, 0 },
                { new int[] { 0, 0 }, 0, 0 },
                { new int[] { -2, -2 }, -2, -2 },
                { new int[] { -1, -1 }, -1, -2 },
                { new int[] { 1, 1 }, 1, 2 },
                { new int[] { 1, 1 }, 2, 1 },
                
                { new int[] { 0, -1 }, -1, 0 },
                { new int[] { 0, 0 }, 0, 0 },
                { new int[] { 2, 2 }, 2, 2 },
                { new int[] { 2, 1 }, 2, 1 },
                { new int[] { -1, -2 }, -1, -2 },
                { new int[] { -1, -2 }, -2, -1 },
                
                { new int[] { 0, 1 }, 1, 0 },
                { new int[] { 0, 0 }, 0, 0 },
                { new int[] { -2, -2 }, -2, -2 },
                { new int[] { -1, -2 }, -1, -2 },
                { new int[] { 2, 1 }, 1, 2 },
                { new int[] { 1, 2 }, 2, 1 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            int[] arr, int minValue, int maxValue) {
        IntArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        IntArrayArgs.checkValueOutsideRange((int[]) null, 1, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        IntArrayArgs.checkValueOutsideRange((Integer[]) null, 1, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkValueOutsideRange(arr, 1, 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new int[] { 1, 2, 3 }, -1 },
                { new int[] { 1, 2, 3 }, 0 },
                { new int[] { 1, 2, 3 }, 1 },
                
                { new int[] { -1, -2, -3 }, -3 },
                { new int[] { -1, -2, -3 }, -4 },
                { new int[] { -1, -2, -3 }, -5 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(int[] arr, int minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == IntArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(int[] arr, int minValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new int[] { 1, 2, 3 }, 2 },
            { new int[] { 1, 2, 3 }, 3 },
            { new int[] { 1, 2, 3 }, 4 },
            
            { new int[] { -1, -2, -3 }, -2 },
            { new int[] { -1, -2, -3 }, -1 },
            { new int[] { -1, -2, -3 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(int[] arr, int minValue) {
        IntArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(int[] arr, int minValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        IntArrayArgs.checkMinValue((int[]) null, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        IntArrayArgs.checkMinValue((Integer[]) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkMinValue(arr, 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new int[] { 1, 2, 3 }, 3 },
                { new int[] { 1, 2, 3 }, 4 },
                { new int[] { 1, 2, 3 }, 5 },
                
                { new int[] { -1, -2, -3 }, -1 },
                { new int[] { -1, -2, -3 }, 0 },
                { new int[] { -1, -2, -3 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(int[] arr, int maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == IntArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(int[] arr, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new int[] { 1, 2, 3 }, -1 },
            { new int[] { 1, 2, 3 }, 0 },
            { new int[] { 1, 2, 3 }, 1 },
            
            { new int[] { -1, -2, -3 }, -2 },
            { new int[] { -1, -2, -3 }, -3 },
            { new int[] { -1, -2, -3 }, -4 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(int[] arr, int maxValue) {
        IntArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(int[] arr, int maxValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        IntArrayArgs.checkMaxValue((int[]) null, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        IntArrayArgs.checkMaxValue((Integer[]) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkMaxValue(arr, 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new int[] { 1 }, 1 },
            { new int[] { 1, 1 }, 1 },
            { new int[] { 1, 1, 1 }, 1 },
            
            { new int[] { -1 }, -1 },
            { new int[] { -1, -1 }, -1 },
            { new int[] { -1, -1, -1 }, -1 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(int[] arr, int exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == IntArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(int[] arr, int exactValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new int[] { 1, 2, 3 }, -1 },
            { new int[] { 1, 2, 3 }, 0 },
            { new int[] { 1, 2, 3 }, 1 },
            
            { new int[] { -1, -2, -3 }, -2 },
            { new int[] { -1, -2, -3 }, -3 },
            { new int[] { -1, -2, -3 }, -4 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(int[] arr, int exactValue) {
        IntArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(int[] arr, int exactValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        IntArrayArgs.checkExactValue((int[]) null, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        IntArrayArgs.checkExactValue((Integer[]) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkExactValue(arr, 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new int[] { 1 }, 2 },
            { new int[] { 1, 1 }, 2 },
            { new int[] { 1, 1, 1 }, -2 },
            
            { new int[] { -1 }, -2 },
            { new int[] { -1, -1 }, -2 },
            { new int[] { -1, -1, -1 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(int[] arr, int exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == IntArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == IntArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == IntArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == IntArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(int[] arr, int exactValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == IntArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new int[] { 1, 2, 3 }, 1 },
            { new int[] { 1, 2, 3 }, 2 },
            { new int[] { 1, 2, 3 }, 3 },
            
            { new int[] { -1, -2, -3 }, -1 },
            { new int[] { -1, -2, -3 }, -2 },
            { new int[] { -1, -2, -3 }, -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(int[] arr, int exactValue) {
        IntArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(int[] arr, int exactValue) {
        Integer[] arr2 = toIntObjectArray(arr);
        IntArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        IntArrayArgs.checkNotExactValue((int[]) null, 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        IntArrayArgs.checkNotExactValue((Integer[]) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Integer[] { null, 1 } },
                { new Integer[] { 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Integer[] arr) {
        IntArrayArgs.checkNotExactValue(arr, 1, "arr");
    }
}
