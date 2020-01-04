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

public class FloatArrayArgsTest {
    
    public static Float[] toFloatObjectArray(float[] arr) {
        Float[] arr2 = new Float[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = new Float(arr[i]);
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkPositive(float[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new float[] { 1 } },
                { new float[] { 1, 2, 3 } },
                { new float[] { 99 } },
                { new float[] { 99, 101, 103 } },
                { new float[] { 1, Float.MAX_VALUE } },
                { new float[] { PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(float[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new float[] { 0, 4, 5, 6 } },
                { new float[] { 4, 0, 5, 6 } },
                { new float[] { 4, 0, 5, 6 } },
                { new float[] { 4.0f, 5.0f, 0.0f, 6.0f } },
                { new float[] { 4.0f, 5.0f, 6.0f, 0.0f } },
                { new float[] { -1.0f, 99.0f, 101.0f, 103.0f } },
                { new float[] { 99.0f, -1.0f, 101.0f, 103.0f } },
                { new float[] { -1.0f, 99.0f, 101.0f, -1.0f, 103.0f } },
                { new float[] { -1.0f, 99.0f, 101.0f, 103.0f, -1.0f } },
                { new float[] { -Float.MIN_VALUE, 1.0f, 2.0f, 3.0f } },
                { new float[] { 1.0f, -Float.MIN_VALUE, 2.0f, 3.0f } },
                { new float[] { 1.0f, 2.0f, -Float.MIN_VALUE, 3.0f } },
                { new float[] { 1.0f, 2.0f, 3.0f, -Float.MIN_VALUE } },
                { new float[] { -Float.MAX_VALUE, 1.0f, 2.0f, 3.0f } },
                { new float[] { 1.0f, -Float.MAX_VALUE, 2.0f, 3.0f } },
                { new float[] { 1.0f, 2.0f, -Float.MAX_VALUE, 3.0f } },
                { new float[] { 1.0f, 2.0f, 3.0f, -Float.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(float[] arr) {
        FloatArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        FloatArrayArgs.checkPositive((float[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        FloatArrayArgs.checkPositive((Float[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new float[] { 0.0f } },
                { new float[] { 0.0f, -1.0f, -2.0f, -3.0f } },
                { new float[] { -99.0f } },
                { new float[] { -99.0f, -101.0f, -103.0f } },
                { new float[] { -Float.MIN_VALUE, 0.0f, -1.0f, -2.0f } },
                { new float[] { 0.0f, -Float.MIN_VALUE, -1.0f, -2.0f } },
                { new float[] { 0.0f, -1.0f, -Float.MIN_VALUE, -2.0f } },
                { new float[] { 0.0f, -1.0f, -2.0f, -Float.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(float[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new float[] { 1.0f } },
                { new float[] { 1.0f, 2.0f, 3.0f } },
                { new float[] { 99.0f } },
                { new float[] { 99.0f, 101.0f, 103.0f } },
                { new float[] { Float.MIN_VALUE } },
                { new float[] { 1.0f, Float.MIN_VALUE } },
                { new float[] { Float.MAX_VALUE } },
                { new float[] { 1.0f, Float.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(float[] arr) {
        FloatArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        FloatArrayArgs.checkNotPositive((float[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        FloatArrayArgs.checkNotPositive((Float[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new float[] { -1.0f } },
                { new float[] { -1.0f, -2.0f, -3.0f } },
                { new float[] { -99.0f } },
                { new float[] { -99.0f, -101.0f, -103.0f } },
                { new float[] { -Float.MIN_VALUE, -1.0f } },
                { new float[] { -Float.MAX_VALUE, -1.0f } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(float[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new float[] { 0.0f, 1.0f, 2.0f, 3.0f } },
                { new float[] { 1.0f, 0.0f, 2.0f, 3.0f } },
                { new float[] { 1.0f, 2.0f, 0.0f, 3.0f } },
                { new float[] { 1.0f, 2.0f, 3.0f, 0.0f } },
                { new float[] { 1.0f } },
                { new float[] { Float.MIN_VALUE } },
                { new float[] { Float.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(float[] arr) {
        FloatArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        FloatArrayArgs.checkNegative((float[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        FloatArrayArgs.checkNegative((Float[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new float[] { 0.0f } },
                { new float[] { 0.0f, 1.0f, 2.0f } },
                { new float[] { 99.0f } },
                { new float[] { 99.0f, 101.0f, 103.0f } },
                { new float[] { 1.0f, Float.MIN_VALUE } },
                { new float[] { 1.0f, Float.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(float[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new float[] { -1.0f, 4.0f, 5.0f, 6.0f } },
                { new float[] { 4.0f, -1.0f, 5.0f, 6.0f } },
                { new float[] { 4.0f, 5.0f, -1.0f, 6.0f } },
                { new float[] { 4.0f, 5.0f, 6.0f, -1.0f } },
                { new float[] { -Float.MIN_VALUE, 0.0f, 1.0f, 2.0f } },
                { new float[] { 0.0f, -Float.MIN_VALUE, 1.0f, 2.0f } },
                { new float[] { 0.0f, 1.0f, -Float.MIN_VALUE, 2.0f } },
                { new float[] { 0.0f, 1.0f, 2.0f, -Float.MIN_VALUE } },
                { new float[] { -Float.MAX_VALUE, 0.0f, 1.0f, 2.0f } },
                { new float[] { 0.0f, -Float.MAX_VALUE, 1.0f, 2.0f } },
                { new float[] { 0.0f, 1.0f, -Float.MAX_VALUE, 2.0f } },
                { new float[] { 0.0f, 1.0f, 2.0f, -Float.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(float[] arr) {
        FloatArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(float[] arr) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        FloatArrayArgs.checkNotNegative((float[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        FloatArrayArgs.checkNotNegative((Float[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new float[] { 1.0f }, -1.0f, 2.0f },
                { new float[] { 1.0f }, -1.0f, 1.0f },
                { new float[] { 1.0f }, 0.0f, 1.0f },
                { new float[] { 1.0f }, 0.0f, 2.0f },
                { new float[] { 1.0f }, 1.0f, 1.0f },
                { new float[] { 1.0f }, 1.0f, 2.0f },
                
                { new float[] { 1.0f, 0.0f }, -1.0f, 2.0f },
                { new float[] { 1.0f, 0.0f }, -1.0f, 1.0f },
                { new float[] { 1.0f, 0.0f }, 0.0f, 1.0f },
                { new float[] { 1.0f, 0.0f }, 0.0f, 2.0f },
                { new float[] { 1.0f }, 1.0f, 1.0f },
                { new float[] { 1.0f, 2.0f }, 1.0f, 2.0f },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(float[] arr, float minValue, float maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(float[] arr, float minValue, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new float[] { 1.0f }, -1.0f, 0.0f },
                { new float[] { 1.0f }, 0.0f, 0.0f },
                { new float[] { 1.0f }, 2.0f, 2.0f },
                { new float[] { 1.0f }, 2.0f, 1.0f },
                { new float[] { 1.0f }, -1.0f, -2.0f },
                { new float[] { 1.0f }, -2.0f, -1.0f },
                
                { new float[] { -1.0f }, 1.0f, 0.0f },
                { new float[] { -1.0f }, 0.0f, 0.0f },
                { new float[] { -1.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f }, -1.0f, -2.0f },
                { new float[] { -1.0f }, 1.0f, 2.0f },
                { new float[] { -1.0f }, 2.0f, 1.0f },
                
                { new float[] { 1.0f, 1.0f }, -1.0f, 0.0f },
                { new float[] { 1.0f, 1.0f }, 0.0f, 0.0f },
                { new float[] { 1.0f, 1.0f }, 2.0f, 2.0f },
                { new float[] { 1.0f, 1.0f }, 2.0f, 1.0f },
                { new float[] { 1.0f, 1.0f }, -1.0f, -2.0f },
                { new float[] { 1.0f, 1.0f }, -2.0f, -1.0f },
                
                { new float[] { -1.0f, -1.0f }, 1.0f, 0.0f },
                { new float[] { -1.0f, -1.0f }, 0.0f, 0.0f },
                { new float[] { -1.0f, -1.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f, -1.0f }, -1.0f, -2.0f },
                { new float[] { -1.0f, -1.0f }, 1.0f, 2.0f },
                { new float[] { -1.0f, -1.0f }, 2.0f, 1.0f },
                
                { new float[] { 1.0f, 5.0f }, -1.0f, 0.0f },
                { new float[] { 1.0f, 5.0f }, 0.0f, 0.0f },
                { new float[] { 1.0f, 5.0f }, 2.0f, 2.0f },
                { new float[] { 1.0f, 5.0f }, 2.0f, 1.0f },
                { new float[] { 1.0f, 5.0f }, -1.0f, -2.0f },
                { new float[] { 1.0f, 5.0f }, -2.0f, -1.0f },
                
                { new float[] { -1.0f, -5.0f }, 1.0f, 0.0f },
                { new float[] { -1.0f, -5.0f }, 0.0f, 0.0f },
                { new float[] { -1.0f, -5.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f, -5.0f }, -1.0f, -2.0f },
                { new float[] { -1.0f, -5.0f }, 1.0f, 2.0f },
                { new float[] { -1.0f, -5.0f }, 2.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            float[] arr, float minValue, float maxValue) {
        FloatArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            float[] arr, float minValue, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        FloatArrayArgs.checkValueInsideRange((float[]) null, 1.0f, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        FloatArrayArgs.checkValueInsideRange((Float[]) null, 1.0f, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkValueInsideRange(arr, 1.0f, 1.0f, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new float[] { 3.0f }, -1.0f, 2.0f },
                { new float[] { 3.0f }, -1.0f, 1.0f },
                { new float[] { 3.0f }, 0.0f, 1.0f },
                { new float[] { 3.0f }, 0.0f, 2.0f },
                { new float[] { 3.0f }, 1.0f, 1.0f },
                { new float[] { 3.0f }, 1.0f, 2.0f },
                
                { new float[] { 3.0f, 99.0f }, -1.0f, 2.0f },
                { new float[] { 3.0f, 99.0f }, -1.0f, 1.0f },
                { new float[] { 3.0f, 99.0f }, 0.0f, 1.0f },
                { new float[] { 3.0f, 99.0f }, 0.0f, 2.0f },
                { new float[] { 3.0f }, 1.0f, 1.0f },
                { new float[] { 3.0f, 99.0f }, 1.0f, 2.0f },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(float[] arr, float minValue, float maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == FloatArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(float[] arr, float minValue, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == FloatArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new float[] { 0.0f }, -1.0f, 0.0f },
                { new float[] { 0.0f }, 0.0f, 0.0f },
                { new float[] { 2.0f }, 2.0f, 2.0f },
                { new float[] { 2.0f }, 2.0f, 1.0f },
                { new float[] { -2.0f }, -1.0f, -2.0f },
                { new float[] { -2.0f }, -2.0f, -1.0f },
                
                { new float[] { 1.0f }, 1.0f, 0.0f },
                { new float[] { 0.0f }, 0.0f, 0.0f },
                { new float[] { -2.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f }, -1.0f, -2.0f },
                { new float[] { 1.0f }, 1.0f, 2.0f },
                { new float[] { 1.0f }, 2.0f, 1.0f },
                
                { new float[] { 0.0f, 0.0f }, -1.0f, 0.0f },
                { new float[] { 0.0f, 0.0f }, 0.0f, 0.0f },
                { new float[] { 2.0f, 2.0f }, 2.0f, 2.0f },
                { new float[] { 2.0f, 2.0f }, 2.0f, 1.0f },
                { new float[] { -2.0f, -2.0f }, -1.0f, -2.0f },
                { new float[] { -2.0f, -2.0f }, -2.0f, -1.0f },
                
                { new float[] { 1.0f, 1.0f }, 1.0f, 0.0f },
                { new float[] { 0.0f, 0.0f }, 0.0f, 0.0f },
                { new float[] { -2.0f, -2.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f, -1.0f }, -1.0f, -2.0f },
                { new float[] { 1.0f, 1.0f }, 1.0f, 2.0f },
                { new float[] { 1.0f, 1.0f }, 2.0f, 1.0f },
                
                { new float[] { 0.0f, -1.0f }, -1.0f, 0.0f },
                { new float[] { 0.0f, 0.0f }, 0.0f, 0.0f },
                { new float[] { 2.0f, 2.0f }, 2.0f, 2.0f },
                { new float[] { 2.0f, 1.0f }, 2.0f, 1.0f },
                { new float[] { -1.0f, -2.0f }, -1.0f, -2.0f },
                { new float[] { -1.0f, -2.0f }, -2.0f, -1.0f },
                
                { new float[] { 0.0f, 1.0f }, 1.0f, 0.0f },
                { new float[] { 0.0f, 0.0f }, 0.0f, 0.0f },
                { new float[] { -2.0f, -2.0f }, -2.0f, -2.0f },
                { new float[] { -1.0f, -2.0f }, -1.0f, -2.0f },
                { new float[] { 2.0f, 1.0f }, 1.0f, 2.0f },
                { new float[] { 1.0f, 2.0f }, 2.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            float[] arr, float minValue, float maxValue) {
        FloatArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            float[] arr, float minValue, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        FloatArrayArgs.checkValueOutsideRange((float[]) null, 1.0f, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        FloatArrayArgs.checkValueOutsideRange((Float[]) null, 1.0f, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkValueOutsideRange(arr, 1.0f, 1.0f, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new float[] { 1.0f, 2.0f, 3.0f }, -1.0f },
                { new float[] { 1.0f, 2.0f, 3.0f }, 0.0f },
                { new float[] { 1.0f, 2.0f, 3.0f }, 1.0f },
                
                { new float[] { -1.0f, -2.0f, -3.0f }, -3.0f },
                { new float[] { -1.0f, -2.0f, -3.0f }, -4.0f },
                { new float[] { -1.0f, -2.0f, -3.0f }, -5.0f },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(float[] arr, float minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(float[] arr, float minValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new float[] { 1.0f, 2.0f, 3.0f }, 2.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 3.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 4.0f },
            
            { new float[] { -1.0f, -2.0f, -3.0f }, -2.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -1.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, 0.0f },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(float[] arr, float minValue) {
        FloatArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(float[] arr, float minValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        FloatArrayArgs.checkMinValue((float[]) null, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        FloatArrayArgs.checkMinValue((Float[]) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkMinValue(arr, 1.0f, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new float[] { 1.0f, 2.0f, 3.0f }, 3.0f },
                { new float[] { 1.0f, 2.0f, 3.0f }, 4.0f },
                { new float[] { 1.0f, 2.0f, 3.0f }, 5.0f },
                
                { new float[] { -1.0f, -2.0f, -3.0f }, -1.0f },
                { new float[] { -1.0f, -2.0f, -3.0f }, 0.0f },
                { new float[] { -1.0f, -2.0f, -3.0f }, 1.0f },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(float[] arr, float maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(float[] arr, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new float[] { 1.0f, 2.0f, 3.0f }, -1.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 0.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 1.0f },
            
            { new float[] { -1.0f, -2.0f, -3.0f }, -2.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -3.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -4.0f },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(float[] arr, float maxValue) {
        FloatArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(float[] arr, float maxValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        FloatArrayArgs.checkMaxValue((float[]) null, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        FloatArrayArgs.checkMaxValue((Float[]) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkMaxValue(arr, 1.0f, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new float[] { 1.0f }, 1.0f },
            { new float[] { 1.0f, 1.0f }, 1.0f },
            { new float[] { 1.0f, 1.0f, 1.0f }, 1.0f },
            
            { new float[] { -1.0f }, -1.0f },
            { new float[] { -1.0f, -1.0f }, -1.0f },
            { new float[] { -1.0f, -1.0f, -1.0f }, -1.0f },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(float[] arr, float exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(float[] arr, float exactValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new float[] { 1.0f, 2.0f, 3.0f }, -1.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 0.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 1.0f },
            
            { new float[] { -1.0f, -2.0f, -3.0f }, -2.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -3.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -4.0f },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(float[] arr, float exactValue) {
        FloatArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(float[] arr, float exactValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        FloatArrayArgs.checkExactValue((float[]) null, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        FloatArrayArgs.checkExactValue((Float[]) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkExactValue(arr, 1.0f, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new float[] { 1.0f }, 2.0f },
            { new float[] { 1.0f, 1.0f }, 2.0f },
            { new float[] { 1.0f, 1.0f, 1.0f }, -2.0f },
            
            { new float[] { -1.0f }, -2.0f },
            { new float[] { -1.0f, -1.0f }, -2.0f },
            { new float[] { -1.0f, -1.0f, -1.0f }, 2.0f },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(float[] arr, float exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == FloatArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == FloatArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == FloatArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(float[] arr, float exactValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == FloatArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new float[] { 1.0f, 2.0f, 3.0f }, 1.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 2.0f },
            { new float[] { 1.0f, 2.0f, 3.0f }, 3.0f },
            
            { new float[] { -1.0f, -2.0f, -3.0f }, -1.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -2.0f },
            { new float[] { -1.0f, -2.0f, -3.0f }, -3.0f },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(float[] arr, float exactValue) {
        FloatArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(float[] arr, float exactValue) {
        Float[] arr2 = toFloatObjectArray(arr);
        FloatArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        FloatArrayArgs.checkNotExactValue((float[]) null, 1.0f, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        FloatArrayArgs.checkNotExactValue((Float[]) null, 1.0f, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Float[] { null, 1.0f } },
                { new Float[] { 1.0f, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Float[] arr) {
        FloatArrayArgs.checkNotExactValue(arr, 1.0f, "arr");
    }
}
