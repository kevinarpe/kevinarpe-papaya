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

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DoubleIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkPositive
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return DoubleArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return DoubleArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        DoubleIterableArgs.checkPositive((List<Double>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return DoubleArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return DoubleArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        DoubleIterableArgs.checkNotPositive((List<Double>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return DoubleArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return DoubleArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        DoubleIterableArgs.checkNegative((List<Double>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return DoubleArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return DoubleArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(double[] arr) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        DoubleIterableArgs.checkNotNegative((List<Double>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return DoubleArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(double[] arr, double minValue, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            double[] arr, double minValue, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        DoubleIterableArgs.checkValueInsideRange((List<Double>) null, 1.0d, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkValueInsideRange(list, 1.0d, 1.0d, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return DoubleArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(double[] arr, double minValue, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == DoubleIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            double[] arr, double minValue, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        DoubleIterableArgs.checkValueOutsideRange((List<Double>) null, 1.0d, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkValueOutsideRange(list, 1.0d, 1.0d, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return DoubleArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(double[] arr, double minValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(double[] arr, double minValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        DoubleIterableArgs.checkMinValue((List<Double>) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkMinValue(list, 1.0d, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return DoubleArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(double[] arr, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(double[] arr, double maxValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        DoubleIterableArgs.checkMaxValue((List<Double>) null, 1.0d, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkMaxValue(list, 1.0d, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return DoubleArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(double[] arr, double exactValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(double[] arr, double exactValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        DoubleIterableArgs.checkExactValue((List<Double>) null, 1.0d, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkExactValue(list, 1.0d, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return DoubleArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(double[] arr, double exactValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DoubleIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DoubleIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == DoubleIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return DoubleArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(double[] arr, double exactValue) {
        Double[] arr2 = DoubleArrayArgsTest.toDoubleObjectArray(arr);
        List<Double> list = Arrays.asList(arr2);
        DoubleIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        DoubleIterableArgs.checkNotExactValue((List<Double>) null, 1.0d, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return DoubleArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Double[] arr) {
        List<Double> list = Arrays.asList(arr);
        DoubleIterableArgs.checkNotExactValue(list, 1.0d, "list");
    }
}
