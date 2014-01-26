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

public class ByteIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkPositive
    //

    @DataProvider
    private static Object[][] checkPositive_Pass_Data() {
        return ByteArrayArgsTest.checkPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkPositive(list, null));
        Assert.assertTrue(list == ByteIterableArgs.checkPositive(list, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return ByteArrayArgsTest.checkPositive_FailWithNonPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        ByteIterableArgs.checkPositive((List<Byte>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkPositive_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkNotPositive
    //

    @DataProvider
    private static Object[][] checkNotPositive_Pass_Data() {
        return ByteArrayArgsTest.checkNotPositive_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkNotPositive(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkNotPositive(list, null));
        Assert.assertTrue(list == ByteIterableArgs.checkNotPositive(list, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkNotPositive(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNonPositiveInput_Data() {
        return ByteArrayArgsTest.checkNotPositive_FailWithPositiveInput_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithNonPositiveInput(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkNotPositive(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        ByteIterableArgs.checkNotPositive((List<Byte>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkNotPositive_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkNotPositive(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkNegative
    //

    @DataProvider
    private static Object[][] checkNegative_Pass_Data() {
        return ByteArrayArgsTest.checkNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkNegative(list, null));
        Assert.assertTrue(list == ByteIterableArgs.checkNegative(list, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return ByteArrayArgsTest.checkNegative_FailWithNonNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        ByteIterableArgs.checkNegative((List<Byte>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNegative_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkNegative(list, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkNotNegative
    //

    @DataProvider
    private static Object[][] checkNotNegative_Pass_Data() {
        return ByteArrayArgsTest.checkNotNegative_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkNotNegative(list, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkNotNegative(list, null));
        Assert.assertTrue(list == ByteIterableArgs.checkNotNegative(list, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkNotNegative(list, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNonNegativeInput_Data() {
        return ByteArrayArgsTest.checkNotNegative_FailWithNegativeInput_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNonNegativeInput(byte[] arr) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkNotNegative(list, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        ByteIterableArgs.checkNotNegative((List<Byte>) null, "arr");
    }

    @DataProvider
    private static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkNotNegative_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkNotNegative(list, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return ByteArrayArgsTest.checkValueInsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        ByteIterableArgs.checkValueInsideRange((List<Byte>) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkValueInsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkValueInsideRange(list, (byte) 1, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return ByteArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == ByteIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkValueOutsideRange_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            byte[] arr, byte minValue, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        ByteIterableArgs.checkValueOutsideRange((List<Byte>) null, (byte) 1, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkValueOutsideRange_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkValueOutsideRange(list, (byte) 1, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return ByteArrayArgsTest.checkMinValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(byte[] arr, byte minValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == ByteIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkMinValue(list, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(byte[] arr, byte minValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkMinValue(list, minValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        ByteIterableArgs.checkMinValue((List<Byte>) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkMinValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkMinValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return ByteArrayArgsTest.checkMaxValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(byte[] arr, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == ByteIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkMaxValue(list, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(byte[] arr, byte maxValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkMaxValue(list, maxValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        ByteIterableArgs.checkMaxValue((List<Byte>) null, (byte) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkMaxValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkMaxValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return ByteArrayArgsTest.checkExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(byte[] arr, byte exactValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkExactValue(list, exactValue, null));
        Assert.assertTrue(list == ByteIterableArgs.checkExactValue(list, exactValue, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(byte[] arr, byte exactValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        ByteIterableArgs.checkExactValue((List<Byte>) null, (byte) 1, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkExactValue(list, (byte) 1, "list");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return ByteArrayArgsTest.checkNotExactValue_Pass_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(byte[] arr, byte exactValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == ByteIterableArgs.checkNotExactValue(list, exactValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == ByteIterableArgs.checkNotExactValue(list, exactValue, null));
        Assert.assertTrue(list == ByteIterableArgs.checkNotExactValue(list, exactValue, ""));
        Assert.assertTrue(list == ByteIterableArgs.checkNotExactValue(list, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return ByteArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(byte[] arr, byte exactValue) {
        Byte[] arr2 = ByteArrayArgsTest.toByteObjectArray(arr);
        List<Byte> list = Arrays.asList(arr2);
        ByteIterableArgs.checkNotExactValue(list, exactValue, "list");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        ByteIterableArgs.checkNotExactValue((List<Byte>) null, (byte) 1, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return ByteArrayArgsTest.checkNotExactValue_FailWithNullElement_Data();
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Byte[] arr) {
        List<Byte> list = Arrays.asList(arr);
        ByteIterableArgs.checkNotExactValue(list, (byte) 1, "list");
    }
}
