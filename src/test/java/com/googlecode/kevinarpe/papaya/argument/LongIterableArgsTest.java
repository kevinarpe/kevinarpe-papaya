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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class LongIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkPositive
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return LongArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == LongIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == LongIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return LongArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        LongIterableArgs.checkPositive((List<Long>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return LongArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == LongIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == LongIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return LongArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        LongIterableArgs.checkNotPositive((List<Long>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return LongArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == LongIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == LongIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return LongArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        LongIterableArgs.checkNegative((List<Long>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return LongArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == LongIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == LongIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return LongArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(long[] arr) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        LongIterableArgs.checkNotNegative((List<Long>) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return LongArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(long[] arr, long minValue, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == LongIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == LongIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == LongIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == LongIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            long[] arr, long minValue, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        LongIterableArgs.checkValueInsideRange((List<Long>) null, 1L, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkValueInsideRange(list, 1L, 1L, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return LongArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(long[] arr, long minValue, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == LongIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == LongIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == LongIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == LongIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            long[] arr, long minValue, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        LongIterableArgs.checkValueOutsideRange((List<Long>) null, 1L, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkValueOutsideRange(list, 1L, 1L, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return LongArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(long[] arr, long minValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == LongIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == LongIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(long[] arr, long minValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        LongIterableArgs.checkMinValue((List<Long>) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkMinValue(list, 1L, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return LongArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(long[] arr, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == LongIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == LongIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(long[] arr, long maxValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        LongIterableArgs.checkMaxValue((List<Long>) null, 1L, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkMaxValue(list, 1L, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return LongArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(long[] arr, long exactValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == LongIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == LongIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(long[] arr, long exactValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        LongIterableArgs.checkExactValue((List<Long>) null, 1L, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkExactValue(list, 1L, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return LongArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(long[] arr, long exactValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == LongIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == LongIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == LongIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == LongIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return LongArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(long[] arr, long exactValue) {
        Long[] arr2 = LongArrayArgsTest.toLongObjectArray(arr);
        List<Long> list = Arrays.asList(arr2);
        LongIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        LongIterableArgs.checkNotExactValue((List<Long>) null, 1L, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return LongArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Long[] arr) {
        List<Long> list = Arrays.asList(arr);
        LongIterableArgs.checkNotExactValue(list, 1L, "list");
    }
}
