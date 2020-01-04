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

public class LongArrayArgsTest {
    
    public static Long[] toLongObjectArray(long[] arr) {
        Long[] arr2 = new Long[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = new Long(arr[i]);
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkPositive(long[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new long[] { 1 } },
                { new long[] { 1, 2, 3 } },
                { new long[] { 99 } },
                { new long[] { 99, 101, 103 } },
                { new long[] { 1, Long.MAX_VALUE } },
                { new long[] { (long)(1.0f + PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new long[] { (long)(1.0f - PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new long[] { (long)(1.0d + PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
                { new long[] { (long)(1.0d - PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(long[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == LongArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new long[] { 0, 4, 5, 6 } },
                { new long[] { 4, 0, 5, 6 } },
                { new long[] { 4, 0, 5, 6 } },
                { new long[] { 4, 5, 0, 6 } },
                { new long[] { 4, 5, 6, 0 } },
                { new long[] { -1, 99, 101, 103 } },
                { new long[] { 99, -1, 101, 103 } },
                { new long[] { -1, 99, 101, -1, 103 } },
                { new long[] { -1, 99, 101, 103, -1 } },
                { new long[] { Long.MIN_VALUE, 1, 2, 3 } },
                { new long[] { 1, Long.MIN_VALUE, 2, 3 } },
                { new long[] { 1, 2, Long.MIN_VALUE, 3 } },
                { new long[] { 1, 2, 3, Long.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(long[] arr) {
        LongArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        LongArrayArgs.checkPositive((long[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        LongArrayArgs.checkPositive((Long[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new long[] { 0 } },
                { new long[] { 0, -1, -2, -3 } },
                { new long[] { -99 } },
                { new long[] { -99, -101, -103 } },
                { new long[] { Long.MIN_VALUE, 0, -1, -2 } },
                { new long[] { 0, Long.MIN_VALUE, -1, -2 } },
                { new long[] { 0, -1, Long.MIN_VALUE, -2 } },
                { new long[] { 0, -1, -2, Long.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(long[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == LongArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new long[] { 1 } },
                { new long[] { 1, 2, 3 } },
                { new long[] { 99 } },
                { new long[] { 99, 101, 103 } },
                { new long[] { Long.MAX_VALUE } },
                { new long[] { 1, Long.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(long[] arr) {
        LongArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        LongArrayArgs.checkNotPositive((long[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        LongArrayArgs.checkNotPositive((Long[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new long[] { -1 } },
                { new long[] { -1, -2, -3 } },
                { new long[] { -99 } },
                { new long[] { -99, -101, -103 } },
                { new long[] { Long.MIN_VALUE, -1 } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(long[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == LongArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new long[] { 0, 1, 2, 3 } },
                { new long[] { 1, 0, 2, 3 } },
                { new long[] { 1, 2, 0, 3 } },
                { new long[] { 1, 2, 3, 0 } },
                { new long[] { 1 } },
                { new long[] { Long.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(long[] arr) {
        LongArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        LongArrayArgs.checkNegative((long[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        LongArrayArgs.checkNegative((Long[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new long[] { 0 } },
                { new long[] { 0, 1, 2 } },
                { new long[] { 99 } },
                { new long[] { 99, 101, 103 } },
                { new long[] { 1, Long.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(long[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == LongArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new long[] { -1, 4, 5, 6 } },
                { new long[] { 4, -1, 5, 6 } },
                { new long[] { 4, 5, -1, 6 } },
                { new long[] { 4, 5, 6, -1 } },
                { new long[] { Long.MIN_VALUE, 0, 1, 2 } },
                { new long[] { 0, Long.MIN_VALUE, 1, 2 } },
                { new long[] { 0, 1, Long.MIN_VALUE, 2 } },
                { new long[] { 0, 1, 2, Long.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(long[] arr) {
        LongArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(long[] arr) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        LongArrayArgs.checkNotNegative((long[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        LongArrayArgs.checkNotNegative((Long[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new long[] { 1 }, -1, 2 },
                { new long[] { 1 }, -1, 1 },
                { new long[] { 1 }, 0, 1 },
                { new long[] { 1 }, 0, 2 },
                { new long[] { 1 }, 1, 1 },
                { new long[] { 1 }, 1, 2 },
                
                { new long[] { 1, 0 }, -1, 2 },
                { new long[] { 1, 0 }, -1, 1 },
                { new long[] { 1, 0 }, 0, 1 },
                { new long[] { 1, 0 }, 0, 2 },
                { new long[] { 1 }, 1, 1 },
                { new long[] { 1, 2 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(long[] arr, long minValue, long maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(long[] arr, long minValue, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new long[] { 1 }, -1, 0 },
                { new long[] { 1 }, 0, 0 },
                { new long[] { 1 }, 2, 2 },
                { new long[] { 1 }, 2, 1 },
                { new long[] { 1 }, -1, -2 },
                { new long[] { 1 }, -2, -1 },
                
                { new long[] { -1 }, 1, 0 },
                { new long[] { -1 }, 0, 0 },
                { new long[] { -1 }, -2, -2 },
                { new long[] { -1 }, -1, -2 },
                { new long[] { -1 }, 1, 2 },
                { new long[] { -1 }, 2, 1 },
                
                { new long[] { 1, 1 }, -1, 0 },
                { new long[] { 1, 1 }, 0, 0 },
                { new long[] { 1, 1 }, 2, 2 },
                { new long[] { 1, 1 }, 2, 1 },
                { new long[] { 1, 1 }, -1, -2 },
                { new long[] { 1, 1 }, -2, -1 },
                
                { new long[] { -1, -1 }, 1, 0 },
                { new long[] { -1, -1 }, 0, 0 },
                { new long[] { -1, -1 }, -2, -2 },
                { new long[] { -1, -1 }, -1, -2 },
                { new long[] { -1, -1 }, 1, 2 },
                { new long[] { -1, -1 }, 2, 1 },
                
                { new long[] { 1, 5 }, -1, 0 },
                { new long[] { 1, 5 }, 0, 0 },
                { new long[] { 1, 5 }, 2, 2 },
                { new long[] { 1, 5 }, 2, 1 },
                { new long[] { 1, 5 }, -1, -2 },
                { new long[] { 1, 5 }, -2, -1 },
                
                { new long[] { -1, -5 }, 1, 0 },
                { new long[] { -1, -5 }, 0, 0 },
                { new long[] { -1, -5 }, -2, -2 },
                { new long[] { -1, -5 }, -1, -2 },
                { new long[] { -1, -5 }, 1, 2 },
                { new long[] { -1, -5 }, 2, 1 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            long[] arr, long minValue, long maxValue) {
        LongArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            long[] arr, long minValue, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        LongArrayArgs.checkValueInsideRange((long[]) null, 1L, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        LongArrayArgs.checkValueInsideRange((Long[]) null, 1L, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkValueInsideRange(arr, 1L, 1L, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new long[] { 3 }, -1, 2 },
                { new long[] { 3 }, -1, 1 },
                { new long[] { 3 }, 0, 1 },
                { new long[] { 3 }, 0, 2 },
                { new long[] { 3 }, 1, 1 },
                { new long[] { 3 }, 1, 2 },
                
                { new long[] { 3, 99 }, -1, 2 },
                { new long[] { 3, 99 }, -1, 1 },
                { new long[] { 3, 99 }, 0, 1 },
                { new long[] { 3, 99 }, 0, 2 },
                { new long[] { 3 }, 1, 1 },
                { new long[] { 3, 99 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(long[] arr, long minValue, long maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == LongArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(long[] arr, long minValue, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == LongArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new long[] { 0 }, -1, 0 },
                { new long[] { 0 }, 0, 0 },
                { new long[] { 2 }, 2, 2 },
                { new long[] { 2 }, 2, 1 },
                { new long[] { -2 }, -1, -2 },
                { new long[] { -2 }, -2, -1 },
                
                { new long[] { 1 }, 1, 0 },
                { new long[] { 0 }, 0, 0 },
                { new long[] { -2 }, -2, -2 },
                { new long[] { -1 }, -1, -2 },
                { new long[] { 1 }, 1, 2 },
                { new long[] { 1 }, 2, 1 },
                
                { new long[] { 0, 0 }, -1, 0 },
                { new long[] { 0, 0 }, 0, 0 },
                { new long[] { 2, 2 }, 2, 2 },
                { new long[] { 2, 2 }, 2, 1 },
                { new long[] { -2, -2 }, -1, -2 },
                { new long[] { -2, -2 }, -2, -1 },
                
                { new long[] { 1, 1 }, 1, 0 },
                { new long[] { 0, 0 }, 0, 0 },
                { new long[] { -2, -2 }, -2, -2 },
                { new long[] { -1, -1 }, -1, -2 },
                { new long[] { 1, 1 }, 1, 2 },
                { new long[] { 1, 1 }, 2, 1 },
                
                { new long[] { 0, -1 }, -1, 0 },
                { new long[] { 0, 0 }, 0, 0 },
                { new long[] { 2, 2 }, 2, 2 },
                { new long[] { 2, 1 }, 2, 1 },
                { new long[] { -1, -2 }, -1, -2 },
                { new long[] { -1, -2 }, -2, -1 },
                
                { new long[] { 0, 1 }, 1, 0 },
                { new long[] { 0, 0 }, 0, 0 },
                { new long[] { -2, -2 }, -2, -2 },
                { new long[] { -1, -2 }, -1, -2 },
                { new long[] { 2, 1 }, 1, 2 },
                { new long[] { 1, 2 }, 2, 1 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            long[] arr, long minValue, long maxValue) {
        LongArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            long[] arr, long minValue, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        LongArrayArgs.checkValueOutsideRange((long[]) null, 1L, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        LongArrayArgs.checkValueOutsideRange((Long[]) null, 1L, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkValueOutsideRange(arr, 1L, 1L, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new long[] { 1, 2, 3 }, -1 },
                { new long[] { 1, 2, 3 }, 0 },
                { new long[] { 1, 2, 3 }, 1 },
                
                { new long[] { -1, -2, -3 }, -3 },
                { new long[] { -1, -2, -3 }, -4 },
                { new long[] { -1, -2, -3 }, -5 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(long[] arr, long minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == LongArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(long[] arr, long minValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new long[] { 1, 2, 3 }, 2 },
            { new long[] { 1, 2, 3 }, 3 },
            { new long[] { 1, 2, 3 }, 4 },
            
            { new long[] { -1, -2, -3 }, -2 },
            { new long[] { -1, -2, -3 }, -1 },
            { new long[] { -1, -2, -3 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(long[] arr, long minValue) {
        LongArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(long[] arr, long minValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        LongArrayArgs.checkMinValue((long[]) null, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        LongArrayArgs.checkMinValue((Long[]) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkMinValue(arr, 1L, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new long[] { 1, 2, 3 }, 3 },
                { new long[] { 1, 2, 3 }, 4 },
                { new long[] { 1, 2, 3 }, 5 },
                
                { new long[] { -1, -2, -3 }, -1 },
                { new long[] { -1, -2, -3 }, 0 },
                { new long[] { -1, -2, -3 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(long[] arr, long maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == LongArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(long[] arr, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new long[] { 1, 2, 3 }, -1 },
            { new long[] { 1, 2, 3 }, 0 },
            { new long[] { 1, 2, 3 }, 1 },
            
            { new long[] { -1, -2, -3 }, -2 },
            { new long[] { -1, -2, -3 }, -3 },
            { new long[] { -1, -2, -3 }, -4 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(long[] arr, long maxValue) {
        LongArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(long[] arr, long maxValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        LongArrayArgs.checkMaxValue((long[]) null, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        LongArrayArgs.checkMaxValue((Long[]) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkMaxValue(arr, 1L, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new long[] { 1 }, 1 },
            { new long[] { 1, 1 }, 1 },
            { new long[] { 1, 1, 1 }, 1 },
            
            { new long[] { -1 }, -1 },
            { new long[] { -1, -1 }, -1 },
            { new long[] { -1, -1, -1 }, -1 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(long[] arr, long exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == LongArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(long[] arr, long exactValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new long[] { 1, 2, 3 }, -1 },
            { new long[] { 1, 2, 3 }, 0 },
            { new long[] { 1, 2, 3 }, 1 },
            
            { new long[] { -1, -2, -3 }, -2 },
            { new long[] { -1, -2, -3 }, -3 },
            { new long[] { -1, -2, -3 }, -4 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(long[] arr, long exactValue) {
        LongArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(long[] arr, long exactValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        LongArrayArgs.checkExactValue((long[]) null, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        LongArrayArgs.checkExactValue((Long[]) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkExactValue(arr, 1L, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new long[] { 1 }, 2 },
            { new long[] { 1, 1 }, 2 },
            { new long[] { 1, 1, 1 }, -2 },
            
            { new long[] { -1 }, -2 },
            { new long[] { -1, -1 }, -2 },
            { new long[] { -1, -1, -1 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(long[] arr, long exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == LongArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == LongArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == LongArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == LongArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(long[] arr, long exactValue) {
        Long[] arr2 = toLongObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == LongArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new long[] { 1, 2, 3 }, 1 },
            { new long[] { 1, 2, 3 }, 2 },
            { new long[] { 1, 2, 3 }, 3 },
            
            { new long[] { -1, -2, -3 }, -1 },
            { new long[] { -1, -2, -3 }, -2 },
            { new long[] { -1, -2, -3 }, -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(long[] arr, long exactValue) {
        LongArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(long[] arr, long exactValue) {
        Long[] arr2 = toLongObjectArray(arr);
        LongArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        LongArrayArgs.checkNotExactValue((long[]) null, 1L, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        LongArrayArgs.checkNotExactValue((Long[]) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Long[] { null, 1L } },
                { new Long[] { 1L, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Long[] arr) {
        LongArrayArgs.checkNotExactValue(arr, 1L, "arr");
    }
}
