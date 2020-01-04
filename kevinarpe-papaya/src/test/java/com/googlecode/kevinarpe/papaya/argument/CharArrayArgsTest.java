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

public class CharArrayArgsTest {
    
    public static Character[] toCharObjectArray(char[] arr) {
        Character[] arr2 = new Character[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = arr[i];
        }
        return arr2;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new char[] { (char) 1 }, (char) 0, (char) 1 },
                { new char[] { (char) 1 }, (char) 0, (char) 2 },
                { new char[] { (char) 1 }, (char) 1, (char) 1 },
                { new char[] { (char) 1 }, (char) 1, (char) 2 },
                
                { new char[] { (char) 1, (char) 0 }, (char) 0, (char) 1 },
                { new char[] { (char) 1, (char) 0 }, (char) 0, (char) 2 },
                { new char[] { (char) 1 }, (char) 1, (char) 1 },
                { new char[] { (char) 1, (char) 2 }, (char) 1, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(char[] arr, char minValue, char maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(char[] arr, char minValue, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new char[] { (char) 1 }, (char) -1, (char) 0 },
                { new char[] { (char) 1 }, (char) 0, (char) 0 },
                { new char[] { (char) 1 }, (char) 2, (char) 2 },
                { new char[] { (char) 1 }, (char) 2, (char) 1 },
                { new char[] { (char) 1 }, (char) -1, (char) -2 },
                { new char[] { (char) 1 }, (char) -2, (char) -1 },
                
                { new char[] { (char) -1 }, (char) 1, (char) 0 },
                { new char[] { (char) -1 }, (char) 0, (char) 0 },
                { new char[] { (char) -1 }, (char) -2, (char) -2 },
                { new char[] { (char) -1 }, (char) -1, (char) -2 },
                { new char[] { (char) -1 }, (char) 1, (char) 2 },
                { new char[] { (char) -1 }, (char) 2, (char) 1 },
                
                { new char[] { (char) 1, (char) 1 }, (char) -1, (char) 0 },
                { new char[] { (char) 1, (char) 1 }, (char) 0, (char) 0 },
                { new char[] { (char) 1, (char) 1 }, (char) 2, (char) 2 },
                { new char[] { (char) 1, (char) 1 }, (char) 2, (char) 1 },
                { new char[] { (char) 1, (char) 1 }, (char) -1, (char) -2 },
                { new char[] { (char) 1, (char) 1 }, (char) -2, (char) -1 },
                
                { new char[] { (char) -1, (char) -1 }, (char) 1, (char) 0 },
                { new char[] { (char) -1, (char) -1 }, (char) 0, (char) 0 },
                { new char[] { (char) -1, (char) -1 }, (char) -2, (char) -2 },
                { new char[] { (char) -1, (char) -1 }, (char) -1, (char) -2 },
                { new char[] { (char) -1, (char) -1 }, (char) 1, (char) 2 },
                { new char[] { (char) -1, (char) -1 }, (char) 2, (char) 1 },
                
                { new char[] { (char) 1, (char) 5 }, (char) -1, (char) 0 },
                { new char[] { (char) 1, (char) 5 }, (char) 0, (char) 0 },
                { new char[] { (char) 1, (char) 5 }, (char) 2, (char) 2 },
                { new char[] { (char) 1, (char) 5 }, (char) 2, (char) 1 },
                { new char[] { (char) 1, (char) 5 }, (char) -1, (char) -2 },
                { new char[] { (char) 1, (char) 5 }, (char) -2, (char) -1 },
                
                { new char[] { (char) -1, (char) -5 }, (char) 1, (char) 0 },
                { new char[] { (char) -1, (char) -5 }, (char) 0, (char) 0 },
                { new char[] { (char) -1, (char) -5 }, (char) -2, (char) -2 },
                { new char[] { (char) -1, (char) -5 }, (char) -1, (char) -2 },
                { new char[] { (char) -1, (char) -5 }, (char) 1, (char) 2 },
                { new char[] { (char) -1, (char) -5 }, (char) 2, (char) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            char[] arr, char minValue, char maxValue) {
        CharArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            char[] arr, char minValue, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        CharArrayArgs.checkValueInsideRange((char[]) null, (char) 1, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        CharArrayArgs.checkValueInsideRange((Character[]) null, (char) 1, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkValueInsideRange(arr, (char) 1, (char) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new char[] { (char) 3 }, (char) 0, (char) 1 },
                { new char[] { (char) 3 }, (char) 0, (char) 2 },
                { new char[] { (char) 3 }, (char) 1, (char) 1 },
                { new char[] { (char) 3 }, (char) 1, (char) 2 },
                
                { new char[] { (char) 3, (char) 99 }, (char) 0, (char) 1 },
                { new char[] { (char) 3, (char) 99 }, (char) 0, (char) 2 },
                { new char[] { (char) 3 }, (char) 1, (char) 1 },
                { new char[] { (char) 3, (char) 99 }, (char) 1, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(char[] arr, char minValue, char maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == CharArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(char[] arr, char minValue, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == CharArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new char[] { (char) 0 }, (char) -1, (char) 0 },
                { new char[] { (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) 2 }, (char) 2, (char) 2 },
                { new char[] { (char) 2 }, (char) 2, (char) 1 },
                { new char[] { (char) -2 }, (char) -1, (char) -2 },
                { new char[] { (char) -2 }, (char) -2, (char) -1 },
                
                { new char[] { (char) 1 }, (char) 1, (char) 0 },
                { new char[] { (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) -2 }, (char) -2, (char) -2 },
                { new char[] { (char) -1 }, (char) -1, (char) -2 },
                { new char[] { (char) 1 }, (char) 1, (char) 2 },
                { new char[] { (char) 1 }, (char) 2, (char) 1 },
                
                { new char[] { (char) 0, (char) 0 }, (char) -1, (char) 0 },
                { new char[] { (char) 0, (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) 2, (char) 2 }, (char) 2, (char) 2 },
                { new char[] { (char) 2, (char) 2 }, (char) 2, (char) 1 },
                { new char[] { (char) -2, (char) -2 }, (char) -1, (char) -2 },
                { new char[] { (char) -2, (char) -2 }, (char) -2, (char) -1 },
                
                { new char[] { (char) 1, (char) 1 }, (char) 1, (char) 0 },
                { new char[] { (char) 0, (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) -2, (char) -2 }, (char) -2, (char) -2 },
                { new char[] { (char) -1, (char) -1 }, (char) -1, (char) -2 },
                { new char[] { (char) 1, (char) 1 }, (char) 1, (char) 2 },
                { new char[] { (char) 1, (char) 1 }, (char) 2, (char) 1 },
                
                { new char[] { (char) 0, (char) -1 }, (char) -1, (char) 0 },
                { new char[] { (char) 0, (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) 2, (char) 2 }, (char) 2, (char) 2 },
                { new char[] { (char) 2, (char) 1 }, (char) 2, (char) 1 },
                { new char[] { (char) -1, (char) -2 }, (char) -1, (char) -2 },
                { new char[] { (char) -1, (char) -2 }, (char) -2, (char) -1 },
                
                { new char[] { (char) 0, (char) 1 }, (char) 1, (char) 0 },
                { new char[] { (char) 0, (char) 0 }, (char) 0, (char) 0 },
                { new char[] { (char) -2, (char) -2 }, (char) -2, (char) -2 },
                { new char[] { (char) -1, (char) -2 }, (char) -1, (char) -2 },
                { new char[] { (char) 2, (char) 1 }, (char) 1, (char) 2 },
                { new char[] { (char) 1, (char) 2 }, (char) 2, (char) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            char[] arr, char minValue, char maxValue) {
        CharArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            char[] arr, char minValue, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        CharArrayArgs.checkValueOutsideRange((char[]) null, (char) 1, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        CharArrayArgs.checkValueOutsideRange((Character[]) null, (char) 1, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkValueOutsideRange(arr, (char) 1, (char) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 1 },
                
                { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -3 },
                { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -4 },
                { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -5 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(char[] arr, char minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == CharArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == CharArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == CharArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == CharArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(char[] arr, char minValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == CharArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == CharArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == CharArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == CharArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 2 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 3 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 4 },
            
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -2 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -1 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(char[] arr, char minValue) {
        CharArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(char[] arr, char minValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        CharArrayArgs.checkMinValue((char[]) null, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        CharArrayArgs.checkMinValue((Character[]) null, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkMinValue(arr, (char) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 3 },
                { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 4 },
                { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 5 },
                
                { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(char[] arr, char maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == CharArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == CharArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == CharArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == CharArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(char[] arr, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == CharArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == CharArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == CharArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == CharArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 0 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 1 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 2 },

            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -2 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -3 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -4 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(char[] arr, char maxValue) {
        CharArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(char[] arr, char maxValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        CharArrayArgs.checkMaxValue((char[]) null, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        CharArrayArgs.checkMaxValue((Character[]) null, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkMaxValue(arr, (char) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new char[] { (char) 1 }, (char) 1 },
            { new char[] { (char) 1, (char) 1 }, (char) 1 },
            { new char[] { (char) 1, (char) 1, (char) 1 }, (char) 1 },
            
            { new char[] { (char) -1 }, (char) -1 },
            { new char[] { (char) -1, (char) -1 }, (char) -1 },
            { new char[] { (char) -1, (char) -1, (char) -1 }, (char) -1 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(char[] arr, char exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == CharArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == CharArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == CharArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == CharArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(char[] arr, char exactValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == CharArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == CharArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == CharArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == CharArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) -1 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 0 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 1 },
            
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -2 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -3 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -4 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(char[] arr, char exactValue) {
        CharArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(char[] arr, char exactValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        CharArrayArgs.checkExactValue((char[]) null, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        CharArrayArgs.checkExactValue((Character[]) null, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkExactValue(arr, (char) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new char[] { (char) 1 }, (char) 2 },
            { new char[] { (char) 1, (char) 1 }, (char) 2 },
            { new char[] { (char) 1, (char) 1, (char) 1 }, (char) -2 },
            
            { new char[] { (char) -1 }, (char) -2 },
            { new char[] { (char) -1, (char) -1 }, (char) -2 },
            { new char[] { (char) -1, (char) -1, (char) -1 }, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(char[] arr, char exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == CharArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == CharArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == CharArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == CharArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(char[] arr, char exactValue) {
        Character[] arr2 = toCharObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == CharArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == CharArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == CharArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == CharArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 1 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 2 },
            { new char[] { (char) 1, (char) 2, (char) 3 }, (char) 3 },
            
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -1 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -2 },
            { new char[] { (char) -1, (char) -2, (char) -3 }, (char) -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(char[] arr, char exactValue) {
        CharArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(char[] arr, char exactValue) {
        Character[] arr2 = toCharObjectArray(arr);
        CharArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        CharArrayArgs.checkNotExactValue((char[]) null, (char) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        CharArrayArgs.checkNotExactValue((Character[]) null, (char) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Character[] { null, (char) 1 } },
                { new Character[] { (char) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Character[] arr) {
        CharArrayArgs.checkNotExactValue(arr, (char) 1, "arr");
    }
}
