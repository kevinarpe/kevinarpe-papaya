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

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IntIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkPositive
    //

    @DataProvider
    public static Object[][] _checkPositive_Pass_Data() {
        return IntArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "_checkPositive_Pass_Data")
    public void checkPositive_Pass(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == IntIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == IntIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return IntArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        IntIterableArgs.checkPositive((List<Integer>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] _checkNotPositive_Pass_Data() {
        return IntArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == IntIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == IntIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return IntArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        IntIterableArgs.checkNotPositive((List<Integer>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkNegative
    //

    @DataProvider
    public static Object[][] _checkNegative_Pass_Data() {
        return IntArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNegative_Pass_Data")
    public void checkNegative_Pass(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == IntIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == IntIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return IntArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        IntIterableArgs.checkNegative((List<Integer>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] _checkNotNegative_Pass_Data() {
        return IntArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "_checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == IntIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == IntIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return IntArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(int[] arr) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        IntIterableArgs.checkNotNegative((List<Integer>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return IntArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == IntIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == IntIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == IntIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == IntIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        IntIterableArgs.checkValueInsideRange((List<Integer>) null, 1, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkValueInsideRange(list, 1, 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return IntArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == IntIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == IntIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == IntIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == IntIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            int[] arr, int minValue, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        IntIterableArgs.checkValueOutsideRange((List<Integer>) null, 1, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkValueOutsideRange(list, 1, 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return IntArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(int[] arr, int minValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == IntIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == IntIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(int[] arr, int minValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        IntIterableArgs.checkMinValue((List<Integer>) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkMinValue(list, 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return IntArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(int[] arr, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == IntIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == IntIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(int[] arr, int maxValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        IntIterableArgs.checkMaxValue((List<Integer>) null, 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkMaxValue(list, 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return IntArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(int[] arr, int exactValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == IntIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == IntIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(int[] arr, int exactValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        IntIterableArgs.checkExactValue((List<Integer>) null, 1, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkExactValue(list, 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return IntArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(int[] arr, int exactValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == IntIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == IntIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == IntIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == IntIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return IntArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(int[] arr, int exactValue) {
        Integer[] arr2 = IntArrayArgsTest.toIntObjectArray(arr);
        List<Integer> list = Arrays.asList(arr2);
        IntIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        IntIterableArgs.checkNotExactValue((List<Integer>) null, 1, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return IntArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Integer[] arr) {
        List<Integer> list = Arrays.asList(arr);
        IntIterableArgs.checkNotExactValue(list, 1, "list");
    }
}
