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

import java.util.Arrays;
import java.util.List;

public class ShortIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkPositive
    //

    @DataProvider
    private static Object[][] checkPositive_Pass_Data() {
        return ShortArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == ShortIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return ShortArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        ShortIterableArgs.checkPositive((List<Short>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkPositive_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkNotPositive
    //

    @DataProvider
    private static Object[][] checkNotPositive_Pass_Data() {
        return ShortArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == ShortIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return ShortArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        ShortIterableArgs.checkNotPositive((List<Short>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkNegative
    //

    @DataProvider
    private static Object[][] checkNegative_Pass_Data() {
        return ShortArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == ShortIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return ShortArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        ShortIterableArgs.checkNegative((List<Short>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNegative_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkNotNegative
    //

    @DataProvider
    private static Object[][] checkNotNegative_Pass_Data() {
        return ShortArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == ShortIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return ShortArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(short[] arr) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        ShortIterableArgs.checkNotNegative((List<Short>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return ShortArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(short[] arr, short minValue, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            short[] arr, short minValue, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        ShortIterableArgs.checkValueInsideRange((List<Short>) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkValueInsideRange(list, (byte) 1, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return ShortArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(short[] arr, short minValue, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == ShortIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            short[] arr, short minValue, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        ShortIterableArgs.checkValueOutsideRange((List<Short>) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkValueOutsideRange(list, (byte) 1, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return ShortArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(short[] arr, short minValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == ShortIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(short[] arr, short minValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        ShortIterableArgs.checkMinValue((List<Short>) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkMinValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return ShortArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(short[] arr, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == ShortIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(short[] arr, short maxValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        ShortIterableArgs.checkMaxValue((List<Short>) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkMaxValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return ShortArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(short[] arr, short exactValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == ShortIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(short[] arr, short exactValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        ShortIterableArgs.checkExactValue((List<Short>) null, (byte) 1, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkExactValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return ShortArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(short[] arr, short exactValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ShortIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ShortIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == ShortIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == ShortIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return ShortArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(short[] arr, short exactValue) {
        Short[] arr2 = ShortArrayArgsTest.toShortObjectArray(arr);
        List<Short> list = Arrays.asList(arr2);
        ShortIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        ShortIterableArgs.checkNotExactValue((List<Short>) null, (byte) 1, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return ShortArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Short[] arr) {
        List<Short> list = Arrays.asList(arr);
        ShortIterableArgs.checkNotExactValue(list, (byte) 1, "list");
    }
}
