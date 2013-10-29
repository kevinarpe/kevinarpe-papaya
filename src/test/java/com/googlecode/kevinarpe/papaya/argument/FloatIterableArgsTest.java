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

public class FloatIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkPositive
    //

    @DataProvider
    public static Object[][] _checkPositive_Pass_Data() {
        return FloatArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "_checkPositive_Pass_Data")
    public void checkPositive_Pass(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == FloatIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return FloatArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        FloatIterableArgs.checkPositive((List<Float>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] _checkNotPositive_Pass_Data() {
        return FloatArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == FloatIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return FloatArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        FloatIterableArgs.checkNotPositive((List<Float>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkNegative
    //

    @DataProvider
    public static Object[][] _checkNegative_Pass_Data() {
        return FloatArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNegative_Pass_Data")
    public void checkNegative_Pass(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == FloatIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return FloatArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        FloatIterableArgs.checkNegative((List<Float>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] _checkNotNegative_Pass_Data() {
        return FloatArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == FloatIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return FloatArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(float[] arr) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        FloatIterableArgs.checkNotNegative((List<Float>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return FloatArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(float[] arr, float minValue, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            float[] arr, float minValue, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        FloatIterableArgs.checkValueInsideRange((List<Float>) null, 1.0f, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkValueInsideRange(list, 1.0f, 1.0f, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return FloatArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(float[] arr, float minValue, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == FloatIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            float[] arr, float minValue, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        FloatIterableArgs.checkValueOutsideRange((List<Float>) null, 1.0f, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkValueOutsideRange(list, 1.0f, 1.0f, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return FloatArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(float[] arr, float minValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == FloatIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(float[] arr, float minValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        FloatIterableArgs.checkMinValue((List<Float>) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkMinValue(list, 1.0f, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return FloatArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(float[] arr, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == FloatIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(float[] arr, float maxValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        FloatIterableArgs.checkMaxValue((List<Float>) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkMaxValue(list, 1.0f, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return FloatArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(float[] arr, float exactValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == FloatIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(float[] arr, float exactValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        FloatIterableArgs.checkExactValue((List<Float>) null, 1.0f, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkExactValue(list, 1.0f, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return FloatArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(float[] arr, float exactValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == FloatIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == FloatIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == FloatIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == FloatIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return FloatArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(float[] arr, float exactValue) {
        Float[] arr2 = FloatArrayArgsTest.toFloatObjectArray(arr);
        List<Float> list = Arrays.asList(arr2);
        FloatIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        FloatIterableArgs.checkNotExactValue((List<Float>) null, 1.0f, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return FloatArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Float[] arr) {
        List<Float> list = Arrays.asList(arr);
        FloatIterableArgs.checkNotExactValue(list, 1.0f, "list");
    }
}
