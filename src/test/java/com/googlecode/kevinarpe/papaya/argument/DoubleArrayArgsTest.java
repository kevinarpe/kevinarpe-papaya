package com.googlecode.kevinarpe.papaya.argument;

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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;

public class DoubleArrayArgsTest {
    
    public static Double[] toDoubleObjectArray(double[] arr) {
        Double[] arr2 = new Double[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = new Double(arr[i]);
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkPositive(double[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new double[] { 1 } },
                { new double[] { 1, 2, 3 } },
                { new double[] { 99 } },
                { new double[] { 99, 101, 103 } },
                { new double[] { 1, Double.MAX_VALUE } },
                { new double[] { (double)PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT } },
                { new double[] { PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(double[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new double[] { 0, 4, 5, 6 } },
                { new double[] { 4, 0, 5, 6 } },
                { new double[] { 4, 0, 5, 6 } },
                { new double[] { 4.0d, 5.0d, 0.0d, 6.0d } },
                { new double[] { 4.0d, 5.0d, 6.0d, 0.0d } },
                { new double[] { -1.0d, 99.0d, 101.0d, 103.0d } },
                { new double[] { 99.0d, -1.0d, 101.0d, 103.0d } },
                { new double[] { -1.0d, 99.0d, 101.0d, -1.0d, 103.0d } },
                { new double[] { -1.0d, 99.0d, 101.0d, 103.0d, -1.0d } },
                { new double[] { -Double.MIN_VALUE, 1.0d, 2.0d, 3.0d } },
                { new double[] { 1.0d, -Double.MIN_VALUE, 2.0d, 3.0d } },
                { new double[] { 1.0d, 2.0d, -Double.MIN_VALUE, 3.0d } },
                { new double[] { 1.0d, 2.0d, 3.0d, -Double.MIN_VALUE } },
                { new double[] { -Double.MAX_VALUE, 1.0d, 2.0d, 3.0d } },
                { new double[] { 1.0d, -Double.MAX_VALUE, 2.0d, 3.0d } },
                { new double[] { 1.0d, 2.0d, -Double.MAX_VALUE, 3.0d } },
                { new double[] { 1.0d, 2.0d, 3.0d, -Double.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(double[] arr) {
        DoubleArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        DoubleArrayArgs.checkPositive((double[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        DoubleArrayArgs.checkPositive((Double[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new double[] { 0.0d } },
                { new double[] { 0.0d, -1.0d, -2.0d, -3.0d } },
                { new double[] { -99.0d } },
                { new double[] { -99.0d, -101.0d, -103.0d } },
                { new double[] { -Double.MIN_VALUE, 0.0d, -1.0d, -2.0d } },
                { new double[] { 0.0d, -Double.MIN_VALUE, -1.0d, -2.0d } },
                { new double[] { 0.0d, -1.0d, -Double.MIN_VALUE, -2.0d } },
                { new double[] { 0.0d, -1.0d, -2.0d, -Double.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(double[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new double[] { 1.0d } },
                { new double[] { 1.0d, 2.0d, 3.0d } },
                { new double[] { 99.0d } },
                { new double[] { 99.0d, 101.0d, 103.0d } },
                { new double[] { Double.MIN_VALUE } },
                { new double[] { 1.0d, Double.MIN_VALUE } },
                { new double[] { Double.MAX_VALUE } },
                { new double[] { 1.0d, Double.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(double[] arr) {
        DoubleArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        DoubleArrayArgs.checkNotPositive((double[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        DoubleArrayArgs.checkNotPositive((Double[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new double[] { -1.0d } },
                { new double[] { -1.0d, -2.0d, -3.0d } },
                { new double[] { -99.0d } },
                { new double[] { -99.0d, -101.0d, -103.0d } },
                { new double[] { -Double.MIN_VALUE, -1.0d } },
                { new double[] { -Double.MAX_VALUE, -1.0d } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(double[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new double[] { 0.0d, 1.0d, 2.0d, 3.0d } },
                { new double[] { 1.0d, 0.0d, 2.0d, 3.0d } },
                { new double[] { 1.0d, 2.0d, 0.0d, 3.0d } },
                { new double[] { 1.0d, 2.0d, 3.0d, 0.0d } },
                { new double[] { 1.0d } },
                { new double[] { Double.MIN_VALUE } },
                { new double[] { Double.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(double[] arr) {
        DoubleArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        DoubleArrayArgs.checkNegative((double[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        DoubleArrayArgs.checkNegative((Double[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new double[] { 0.0d } },
                { new double[] { 0.0d, 1.0d, 2.0d } },
                { new double[] { 99.0d } },
                { new double[] { 99.0d, 101.0d, 103.0d } },
                { new double[] { 1.0d, Double.MIN_VALUE } },
                { new double[] { 1.0d, Double.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(double[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new double[] { -1.0d, 4.0d, 5.0d, 6.0d } },
                { new double[] { 4.0d, -1.0d, 5.0d, 6.0d } },
                { new double[] { 4.0d, 5.0d, -1.0d, 6.0d } },
                { new double[] { 4.0d, 5.0d, 6.0d, -1.0d } },
                { new double[] { -Double.MIN_VALUE, 0.0d, 1.0d, 2.0d } },
                { new double[] { 0.0d, -Double.MIN_VALUE, 1.0d, 2.0d } },
                { new double[] { 0.0d, 1.0d, -Double.MIN_VALUE, 2.0d } },
                { new double[] { 0.0d, 1.0d, 2.0d, -Double.MIN_VALUE } },
                { new double[] { -Double.MAX_VALUE, 0.0d, 1.0d, 2.0d } },
                { new double[] { 0.0d, -Double.MAX_VALUE, 1.0d, 2.0d } },
                { new double[] { 0.0d, 1.0d, -Double.MAX_VALUE, 2.0d } },
                { new double[] { 0.0d, 1.0d, 2.0d, -Double.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(double[] arr) {
        DoubleArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(double[] arr) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        DoubleArrayArgs.checkNotNegative((double[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        DoubleArrayArgs.checkNotNegative((Double[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new double[] { 1.0d }, -1.0d, 2.0d },
                { new double[] { 1.0d }, -1.0d, 1.0d },
                { new double[] { 1.0d }, 0.0d, 1.0d },
                { new double[] { 1.0d }, 0.0d, 2.0d },
                { new double[] { 1.0d }, 1.0d, 1.0d },
                { new double[] { 1.0d }, 1.0d, 2.0d },
                
                { new double[] { 1.0d, 0.0d }, -1.0d, 2.0d },
                { new double[] { 1.0d, 0.0d }, -1.0d, 1.0d },
                { new double[] { 1.0d, 0.0d }, 0.0d, 1.0d },
                { new double[] { 1.0d, 0.0d }, 0.0d, 2.0d },
                { new double[] { 1.0d }, 1.0d, 1.0d },
                { new double[] { 1.0d, 2.0d }, 1.0d, 2.0d },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(double[] arr, double minValue, double maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(double[] arr, double minValue, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new double[] { 1.0d }, -1.0d, 0.0d },
                { new double[] { 1.0d }, 0.0d, 0.0d },
                { new double[] { 1.0d }, 2.0d, 2.0d },
                { new double[] { 1.0d }, 2.0d, 1.0d },
                { new double[] { 1.0d }, -1.0d, -2.0d },
                { new double[] { 1.0d }, -2.0d, -1.0d },
                
                { new double[] { -1.0d }, 1.0d, 0.0d },
                { new double[] { -1.0d }, 0.0d, 0.0d },
                { new double[] { -1.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d }, -1.0d, -2.0d },
                { new double[] { -1.0d }, 1.0d, 2.0d },
                { new double[] { -1.0d }, 2.0d, 1.0d },
                
                { new double[] { 1.0d, 1.0d }, -1.0d, 0.0d },
                { new double[] { 1.0d, 1.0d }, 0.0d, 0.0d },
                { new double[] { 1.0d, 1.0d }, 2.0d, 2.0d },
                { new double[] { 1.0d, 1.0d }, 2.0d, 1.0d },
                { new double[] { 1.0d, 1.0d }, -1.0d, -2.0d },
                { new double[] { 1.0d, 1.0d }, -2.0d, -1.0d },
                
                { new double[] { -1.0d, -1.0d }, 1.0d, 0.0d },
                { new double[] { -1.0d, -1.0d }, 0.0d, 0.0d },
                { new double[] { -1.0d, -1.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d, -1.0d }, -1.0d, -2.0d },
                { new double[] { -1.0d, -1.0d }, 1.0d, 2.0d },
                { new double[] { -1.0d, -1.0d }, 2.0d, 1.0d },
                
                { new double[] { 1.0d, 5.0d }, -1.0d, 0.0d },
                { new double[] { 1.0d, 5.0d }, 0.0d, 0.0d },
                { new double[] { 1.0d, 5.0d }, 2.0d, 2.0d },
                { new double[] { 1.0d, 5.0d }, 2.0d, 1.0d },
                { new double[] { 1.0d, 5.0d }, -1.0d, -2.0d },
                { new double[] { 1.0d, 5.0d }, -2.0d, -1.0d },
                
                { new double[] { -1.0d, -5.0d }, 1.0d, 0.0d },
                { new double[] { -1.0d, -5.0d }, 0.0d, 0.0d },
                { new double[] { -1.0d, -5.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d, -5.0d }, -1.0d, -2.0d },
                { new double[] { -1.0d, -5.0d }, 1.0d, 2.0d },
                { new double[] { -1.0d, -5.0d }, 2.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            double[] arr, double minValue, double maxValue) {
        DoubleArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            double[] arr, double minValue, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        DoubleArrayArgs.checkValueInsideRange((double[]) null, 1.0d, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        DoubleArrayArgs.checkValueInsideRange((Double[]) null, 1.0d, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkValueInsideRange(arr, 1.0d, 1.0d, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new double[] { 3.0d }, -1.0d, 2.0d },
                { new double[] { 3.0d }, -1.0d, 1.0d },
                { new double[] { 3.0d }, 0.0d, 1.0d },
                { new double[] { 3.0d }, 0.0d, 2.0d },
                { new double[] { 3.0d }, 1.0d, 1.0d },
                { new double[] { 3.0d }, 1.0d, 2.0d },
                
                { new double[] { 3.0d, 99.0d }, -1.0d, 2.0d },
                { new double[] { 3.0d, 99.0d }, -1.0d, 1.0d },
                { new double[] { 3.0d, 99.0d }, 0.0d, 1.0d },
                { new double[] { 3.0d, 99.0d }, 0.0d, 2.0d },
                { new double[] { 3.0d }, 1.0d, 1.0d },
                { new double[] { 3.0d, 99.0d }, 1.0d, 2.0d },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(double[] arr, double minValue, double maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == DoubleArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(double[] arr, double minValue, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == DoubleArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new double[] { 0.0d }, -1.0d, 0.0d },
                { new double[] { 0.0d }, 0.0d, 0.0d },
                { new double[] { 2.0d }, 2.0d, 2.0d },
                { new double[] { 2.0d }, 2.0d, 1.0d },
                { new double[] { -2.0d }, -1.0d, -2.0d },
                { new double[] { -2.0d }, -2.0d, -1.0d },
                
                { new double[] { 1.0d }, 1.0d, 0.0d },
                { new double[] { 0.0d }, 0.0d, 0.0d },
                { new double[] { -2.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d }, -1.0d, -2.0d },
                { new double[] { 1.0d }, 1.0d, 2.0d },
                { new double[] { 1.0d }, 2.0d, 1.0d },
                
                { new double[] { 0.0d, 0.0d }, -1.0d, 0.0d },
                { new double[] { 0.0d, 0.0d }, 0.0d, 0.0d },
                { new double[] { 2.0d, 2.0d }, 2.0d, 2.0d },
                { new double[] { 2.0d, 2.0d }, 2.0d, 1.0d },
                { new double[] { -2.0d, -2.0d }, -1.0d, -2.0d },
                { new double[] { -2.0d, -2.0d }, -2.0d, -1.0d },
                
                { new double[] { 1.0d, 1.0d }, 1.0d, 0.0d },
                { new double[] { 0.0d, 0.0d }, 0.0d, 0.0d },
                { new double[] { -2.0d, -2.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d, -1.0d }, -1.0d, -2.0d },
                { new double[] { 1.0d, 1.0d }, 1.0d, 2.0d },
                { new double[] { 1.0d, 1.0d }, 2.0d, 1.0d },
                
                { new double[] { 0.0d, -1.0d }, -1.0d, 0.0d },
                { new double[] { 0.0d, 0.0d }, 0.0d, 0.0d },
                { new double[] { 2.0d, 2.0d }, 2.0d, 2.0d },
                { new double[] { 2.0d, 1.0d }, 2.0d, 1.0d },
                { new double[] { -1.0d, -2.0d }, -1.0d, -2.0d },
                { new double[] { -1.0d, -2.0d }, -2.0d, -1.0d },
                
                { new double[] { 0.0d, 1.0d }, 1.0d, 0.0d },
                { new double[] { 0.0d, 0.0d }, 0.0d, 0.0d },
                { new double[] { -2.0d, -2.0d }, -2.0d, -2.0d },
                { new double[] { -1.0d, -2.0d }, -1.0d, -2.0d },
                { new double[] { 2.0d, 1.0d }, 1.0d, 2.0d },
                { new double[] { 1.0d, 2.0d }, 2.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            double[] arr, double minValue, double maxValue) {
        DoubleArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            double[] arr, double minValue, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        DoubleArrayArgs.checkValueOutsideRange((double[]) null, 1.0d, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        DoubleArrayArgs.checkValueOutsideRange((Double[]) null, 1.0d, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkValueOutsideRange(arr, 1.0d, 1.0d, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new double[] { 1.0d, 2.0d, 3.0d }, -1.0d },
                { new double[] { 1.0d, 2.0d, 3.0d }, 0.0d },
                { new double[] { 1.0d, 2.0d, 3.0d }, 1.0d },
                
                { new double[] { -1.0d, -2.0d, -3.0d }, -3.0d },
                { new double[] { -1.0d, -2.0d, -3.0d }, -4.0d },
                { new double[] { -1.0d, -2.0d, -3.0d }, -5.0d },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(double[] arr, double minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(double[] arr, double minValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new double[] { 1.0d, 2.0d, 3.0d }, 2.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 3.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 4.0d },
            
            { new double[] { -1.0d, -2.0d, -3.0d }, -2.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -1.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, 0.0d },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(double[] arr, double minValue) {
        DoubleArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(double[] arr, double minValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        DoubleArrayArgs.checkMinValue((double[]) null, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        DoubleArrayArgs.checkMinValue((Double[]) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkMinValue(arr, 1.0d, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new double[] { 1.0d, 2.0d, 3.0d }, 3.0d },
                { new double[] { 1.0d, 2.0d, 3.0d }, 4.0d },
                { new double[] { 1.0d, 2.0d, 3.0d }, 5.0d },
                
                { new double[] { -1.0d, -2.0d, -3.0d }, -1.0d },
                { new double[] { -1.0d, -2.0d, -3.0d }, 0.0d },
                { new double[] { -1.0d, -2.0d, -3.0d }, 1.0d },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(double[] arr, double maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(double[] arr, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new double[] { 1.0d, 2.0d, 3.0d }, -1.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 0.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 1.0d },
            
            { new double[] { -1.0d, -2.0d, -3.0d }, -2.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -3.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -4.0d },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(double[] arr, double maxValue) {
        DoubleArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(double[] arr, double maxValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        DoubleArrayArgs.checkMaxValue((double[]) null, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        DoubleArrayArgs.checkMaxValue((Double[]) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkMaxValue(arr, 1.0d, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new double[] { 1.0d }, 1.0d },
            { new double[] { 1.0d, 1.0d }, 1.0d },
            { new double[] { 1.0d, 1.0d, 1.0d }, 1.0d },
            
            { new double[] { -1.0d }, -1.0d },
            { new double[] { -1.0d, -1.0d }, -1.0d },
            { new double[] { -1.0d, -1.0d, -1.0d }, -1.0d },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(double[] arr, double exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(double[] arr, double exactValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new double[] { 1.0d, 2.0d, 3.0d }, -1.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 0.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 1.0d },
            
            { new double[] { -1.0d, -2.0d, -3.0d }, -2.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -3.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -4.0d },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(double[] arr, double exactValue) {
        DoubleArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(double[] arr, double exactValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        DoubleArrayArgs.checkExactValue((double[]) null, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        DoubleArrayArgs.checkExactValue((Double[]) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkExactValue(arr, 1.0d, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new double[] { 1.0d }, 2.0d },
            { new double[] { 1.0d, 1.0d }, 2.0d },
            { new double[] { 1.0d, 1.0d, 1.0d }, -2.0d },
            
            { new double[] { -1.0d }, -2.0d },
            { new double[] { -1.0d, -1.0d }, -2.0d },
            { new double[] { -1.0d, -1.0d, -1.0d }, 2.0d },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(double[] arr, double exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == DoubleArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(double[] arr, double exactValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == DoubleArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new double[] { 1.0d, 2.0d, 3.0d }, 1.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 2.0d },
            { new double[] { 1.0d, 2.0d, 3.0d }, 3.0d },
            
            { new double[] { -1.0d, -2.0d, -3.0d }, -1.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -2.0d },
            { new double[] { -1.0d, -2.0d, -3.0d }, -3.0d },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(double[] arr, double exactValue) {
        DoubleArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(double[] arr, double exactValue) {
        Double[] arr2 = toDoubleObjectArray(arr);
        DoubleArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        DoubleArrayArgs.checkNotExactValue((double[]) null, 1.0d, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        DoubleArrayArgs.checkNotExactValue((Double[]) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Double[] { null, 1.0d } },
                { new Double[] { 1.0d, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Double[] arr) {
        DoubleArrayArgs.checkNotExactValue(arr, 1.0d, "arr");
    }
}
