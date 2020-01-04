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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ShortArrayArgsTest {
    
    public static Short[] toShortObjectArray(short[] arr) {
        Short[] arr2 = new Short[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            arr2[i] = arr[i];
        }
        return arr2;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkPositive(short[], String)
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 1 } },
                { new short[] { (short) 1, (short) 2, (short) 3 } },
                { new short[] { (short) 99 } },
                { new short[] { (short) 99, (short) 101, (short) 103 } },
                { new short[] { (short) 1, Byte.MAX_VALUE } },
                { new short[] { (short)(1.0f + PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new short[] { (short)(1.0f - PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), } },
                { new short[] { (short)(1.0d + PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
                { new short[] { (short)(1.0d - PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), } },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(short[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkPositive(arr, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkPositive(arr, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkPositive(arr2, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkPositive(arr2, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkPositive(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { new short[] { (short) 0, 4, 5, 6 } },
                { new short[] { 4, (short) 0, 5, 6 } },
                { new short[] { 4, (short) 0, 5, 6 } },
                { new short[] { 4, 5, (short) 0, 6 } },
                { new short[] { 4, 5, 6, (short) 0 } },
                { new short[] {(short) -1, (short) 99, (short) 101, (short) 103 } },
                { new short[] { (short) 99,(short) -1, (short) 101, (short) 103 } },
                { new short[] {(short) -1, (short) 99, (short) 101,(short) -1, (short) 103 } },
                { new short[] {(short) -1, (short) 99, (short) 101, (short) 103,(short) -1 } },
                { new short[] { Byte.MIN_VALUE, (short) 1, (short) 2, (short) 3 } },
                { new short[] { (short) 1, Byte.MIN_VALUE, (short) 2, (short) 3 } },
                { new short[] { (short) 1, (short) 2, Byte.MIN_VALUE, (short) 3 } },
                { new short[] { (short) 1, (short) 2, (short) 3, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(short[] arr) {
        ShortArrayArgs.checkPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull() {
        ShortArrayArgs.checkPositive((short[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNull2() {
        ShortArrayArgs.checkPositive((Short[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkPositive_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 0 } },
                { new short[] { (short) 0,(short) -1,(short) -2,(short) -3 } },
                { new short[] { -99 } },
                { new short[] { -99,(short) -101,(short) -103 } },
                { new short[] { Byte.MIN_VALUE, (short) 0,(short) -1,(short) -2 } },
                { new short[] { (short) 0, Byte.MIN_VALUE,(short) -1,(short) -2 } },
                { new short[] { (short) 0,(short) -1, Byte.MIN_VALUE,(short) -2 } },
                { new short[] { (short) 0,(short) -1,(short) -2, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(short[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkNotPositive(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkNotPositive(arr, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotPositive(arr, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotPositive(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotPositive(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotPositive(arr2, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotPositive(arr2, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotPositive(arr2, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { new short[] { (short) 1 } },
                { new short[] { (short) 1, (short) 2, (short) 3 } },
                { new short[] { (short) 99 } },
                { new short[] { (short) 99, (short) 101, (short) 103 } },
                { new short[] { Byte.MAX_VALUE } },
                { new short[] { (short) 1, Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(short[] arr) {
        ShortArrayArgs.checkNotPositive(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkNotPositive(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull() {
        ShortArrayArgs.checkNotPositive((short[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNull2() {
        ShortArrayArgs.checkNotPositive((Short[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotPositive_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotPositive_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkNotPositive(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { new short[] {(short) -1 } },
                { new short[] {(short) -1,(short) -2,(short) -3 } },
                { new short[] { -99 } },
                { new short[] { -99,(short) -101,(short) -103 } },
                { new short[] { Byte.MIN_VALUE,(short) -1 } },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(short[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkNegative(arr, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkNegative(arr, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNegative(arr2, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNegative(arr2, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { new short[] { (short) 0, (short) 1, (short) 2, (short) 3 } },
                { new short[] { (short) 1, (short) 0, (short) 2, (short) 3 } },
                { new short[] { (short) 1, (short) 2, (short) 0, (short) 3 } },
                { new short[] { (short) 1, (short) 2, (short) 3, (short) 0 } },
                { new short[] { (short) 1 } },
                { new short[] { Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(short[] arr) {
        ShortArrayArgs.checkNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull() {
        ShortArrayArgs.checkNegative((short[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNull2() {
        ShortArrayArgs.checkNegative((Short[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNegative_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 0 } },
                { new short[] { (short) 0, (short) 1, (short) 2 } },
                { new short[] { (short) 99 } },
                { new short[] { (short) 99, (short) 101, (short) 103 } },
                { new short[] { (short) 1, Byte.MAX_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(short[] arr) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkNotNegative(arr, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkNotNegative(arr, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotNegative(arr, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotNegative(arr, "   "));
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotNegative(arr2, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotNegative(arr2, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotNegative(arr2, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotNegative(arr2, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { new short[] {(short) -1, 4, 5, 6 } },
                { new short[] { 4,(short) -1, 5, 6 } },
                { new short[] { 4, 5,(short) -1, 6 } },
                { new short[] { 4, 5, 6,(short) -1 } },
                { new short[] { Byte.MIN_VALUE, (short) 0, (short) 1, (short) 2 } },
                { new short[] { (short) 0, Byte.MIN_VALUE, (short) 1, (short) 2 } },
                { new short[] { (short) 0, (short) 1, Byte.MIN_VALUE, (short) 2 } },
                { new short[] { (short) 0, (short) 1, (short) 2, Byte.MIN_VALUE } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(short[] arr) {
        ShortArrayArgs.checkNotNegative(arr, "arr");
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput2(short[] arr) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkNotNegative(arr2, "arr2");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull() {
        ShortArrayArgs.checkNotNegative((short[]) null, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNull2() {
        ShortArrayArgs.checkNotNegative((Short[]) null, "arr");
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotNegative_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkNotNegative(arr, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 1 },(short) -1, (short) 2 },
                { new short[] { (short) 1 },(short) -1, (short) 1 },
                { new short[] { (short) 1 }, (short) 0, (short) 1 },
                { new short[] { (short) 1 }, (short) 0, (short) 2 },
                { new short[] { (short) 1 }, (short) 1, (short) 1 },
                { new short[] { (short) 1 }, (short) 1, (short) 2 },
                
                { new short[] { (short) 1, (short) 0 },(short) -1, (short) 2 },
                { new short[] { (short) 1, (short) 0 },(short) -1, (short) 1 },
                { new short[] { (short) 1, (short) 0 }, (short) 0, (short) 1 },
                { new short[] { (short) 1, (short) 0 }, (short) 0, (short) 2 },
                { new short[] { (short) 1 }, (short) 1, (short) 1 },
                { new short[] { (short) 1, (short) 2 }, (short) 1, (short) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(short[] arr, short minValue, short maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueInsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueInsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass2(short[] arr, short minValue, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new short[] { (short) 1 },(short) -1, (short) 0 },
                { new short[] { (short) 1 }, (short) 0, (short) 0 },
                { new short[] { (short) 1 }, (short) 2, (short) 2 },
                { new short[] { (short) 1 }, (short) 2, (short) 1 },
                { new short[] { (short) 1 },(short) -1,(short) -2 },
                { new short[] { (short) 1 },(short) -2,(short) -1 },
                
                { new short[] {(short) -1 }, (short) 1, (short) 0 },
                { new short[] {(short) -1 }, (short) 0, (short) 0 },
                { new short[] {(short) -1 },(short) -2,(short) -2 },
                { new short[] {(short) -1 },(short) -1,(short) -2 },
                { new short[] {(short) -1 }, (short) 1, (short) 2 },
                { new short[] {(short) -1 }, (short) 2, (short) 1 },
                
                { new short[] { (short) 1, (short) 1 },(short) -1, (short) 0 },
                { new short[] { (short) 1, (short) 1 }, (short) 0, (short) 0 },
                { new short[] { (short) 1, (short) 1 }, (short) 2, (short) 2 },
                { new short[] { (short) 1, (short) 1 }, (short) 2, (short) 1 },
                { new short[] { (short) 1, (short) 1 },(short) -1,(short) -2 },
                { new short[] { (short) 1, (short) 1 },(short) -2,(short) -1 },
                
                { new short[] {(short) -1,(short) -1 }, (short) 1, (short) 0 },
                { new short[] {(short) -1,(short) -1 }, (short) 0, (short) 0 },
                { new short[] {(short) -1,(short) -1 },(short) -2,(short) -2 },
                { new short[] {(short) -1,(short) -1 },(short) -1,(short) -2 },
                { new short[] {(short) -1,(short) -1 }, (short) 1, (short) 2 },
                { new short[] {(short) -1,(short) -1 }, (short) 2, (short) 1 },
                
                { new short[] { (short) 1, 5 },(short) -1, (short) 0 },
                { new short[] { (short) 1, 5 }, (short) 0, (short) 0 },
                { new short[] { (short) 1, 5 }, (short) 2, (short) 2 },
                { new short[] { (short) 1, 5 }, (short) 2, (short) 1 },
                { new short[] { (short) 1, 5 },(short) -1,(short) -2 },
                { new short[] { (short) 1, 5 },(short) -2,(short) -1 },
                
                { new short[] {(short) -1, -5 }, (short) 1, (short) 0 },
                { new short[] {(short) -1, -5 }, (short) 0, (short) 0 },
                { new short[] {(short) -1, -5 },(short) -2,(short) -2 },
                { new short[] {(short) -1, -5 },(short) -1,(short) -2 },
                { new short[] {(short) -1, -5 }, (short) 1, (short) 2 },
                { new short[] {(short) -1, -5 }, (short) 2, (short) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            short[] arr, short minValue, short maxValue) {
        ShortArrayArgs.checkValueInsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput2(
            short[] arr, short minValue, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkValueInsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull() {
        ShortArrayArgs.checkValueInsideRange((short[]) null, (short) 1, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNull2() {
        ShortArrayArgs.checkValueInsideRange((Short[]) null, (short) 1, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueInsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkValueInsideRange(arr, (short) 1, (short) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 3 },(short) -1, (short) 2 },
                { new short[] { (short) 3 },(short) -1, (short) 1 },
                { new short[] { (short) 3 }, (short) 0, (short) 1 },
                { new short[] { (short) 3 }, (short) 0, (short) 2 },
                { new short[] { (short) 3 }, (short) 1, (short) 1 },
                { new short[] { (short) 3 }, (short) 1, (short) 2 },
                
                { new short[] { (short) 3, (short) 99 },(short) -1, (short) 2 },
                { new short[] { (short) 3, (short) 99 },(short) -1, (short) 1 },
                { new short[] { (short) 3, (short) 99 }, (short) 0, (short) 1 },
                { new short[] { (short) 3, (short) 99 }, (short) 0, (short) 2 },
                { new short[] { (short) 3 }, (short) 1, (short) 1 },
                { new short[] { (short) 3, (short) 99 }, (short) 1, (short) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(short[] arr, short minValue, short maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, null));
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, ""));
        Assert.assertTrue(
            arr == ShortArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass2(short[] arr, short minValue, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, null));
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, ""));
        Assert.assertTrue(
            arr2 == ShortArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { new short[] { (short) 0 },(short) -1, (short) 0 },
                { new short[] { (short) 0 }, (short) 0, (short) 0 },
                { new short[] { (short) 2 }, (short) 2, (short) 2 },
                { new short[] { (short) 2 }, (short) 2, (short) 1 },
                { new short[] {(short) -2 },(short) -1,(short) -2 },
                { new short[] {(short) -2 },(short) -2,(short) -1 },
                
                { new short[] { (short) 1 }, (short) 1, (short) 0 },
                { new short[] { (short) 0 }, (short) 0, (short) 0 },
                { new short[] {(short) -2 },(short) -2,(short) -2 },
                { new short[] {(short) -1 },(short) -1,(short) -2 },
                { new short[] { (short) 1 }, (short) 1, (short) 2 },
                { new short[] { (short) 1 }, (short) 2, (short) 1 },
                
                { new short[] { (short) 0, (short) 0 },(short) -1, (short) 0 },
                { new short[] { (short) 0, (short) 0 }, (short) 0, (short) 0 },
                { new short[] { (short) 2, (short) 2 }, (short) 2, (short) 2 },
                { new short[] { (short) 2, (short) 2 }, (short) 2, (short) 1 },
                { new short[] {(short) -2,(short) -2 },(short) -1,(short) -2 },
                { new short[] {(short) -2,(short) -2 },(short) -2,(short) -1 },
                
                { new short[] { (short) 1, (short) 1 }, (short) 1, (short) 0 },
                { new short[] { (short) 0, (short) 0 }, (short) 0, (short) 0 },
                { new short[] {(short) -2,(short) -2 },(short) -2,(short) -2 },
                { new short[] {(short) -1,(short) -1 },(short) -1,(short) -2 },
                { new short[] { (short) 1, (short) 1 }, (short) 1, (short) 2 },
                { new short[] { (short) 1, (short) 1 }, (short) 2, (short) 1 },
                
                { new short[] { (short) 0,(short) -1 },(short) -1, (short) 0 },
                { new short[] { (short) 0, (short) 0 }, (short) 0, (short) 0 },
                { new short[] { (short) 2, (short) 2 }, (short) 2, (short) 2 },
                { new short[] { (short) 2, (short) 1 }, (short) 2, (short) 1 },
                { new short[] {(short) -1,(short) -2 },(short) -1,(short) -2 },
                { new short[] {(short) -1,(short) -2 },(short) -2,(short) -1 },
                
                { new short[] { (short) 0, (short) 1 }, (short) 1, (short) 0 },
                { new short[] { (short) 0, (short) 0 }, (short) 0, (short) 0 },
                { new short[] {(short) -2,(short) -2 },(short) -2,(short) -2 },
                { new short[] {(short) -1,(short) -2 },(short) -1,(short) -2 },
                { new short[] { (short) 2, (short) 1 }, (short) 1, (short) 2 },
                { new short[] { (short) 1, (short) 2 }, (short) 2, (short) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            short[] arr, short minValue, short maxValue) {
        ShortArrayArgs.checkValueOutsideRange(arr, minValue, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput2(
            short[] arr, short minValue, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkValueOutsideRange(arr2, minValue, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull() {
        ShortArrayArgs.checkValueOutsideRange((short[]) null, (short) 1, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNull2() {
        ShortArrayArgs.checkValueOutsideRange((Short[]) null, (short) 1, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkValueOutsideRange_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkValueOutsideRange(arr, (short) 1, (short) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 1, (short) 2, (short) 3 },(short) -1 },
                { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 0 },
                { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 1 },
                
                { new short[] {(short) -1,(short) -2,(short) -3 },(short) -3 },
                { new short[] {(short) -1,(short) -2,(short) -3 }, (short) -4 },
                { new short[] {(short) -1,(short) -2,(short) -3 }, (short) -5 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(short[] arr, short minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkMinValue(arr, minValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkMinValue(arr, minValue, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkMinValue(arr, minValue, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkMinValue(arr, minValue, "   "));
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass2(short[] arr, short minValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMinValue(arr2, minValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMinValue(arr2, minValue, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMinValue(arr2, minValue, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMinValue(arr2, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 2 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 3 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 4 },
            
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) -2 },
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) -1 },
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) 0 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(short[] arr, short minValue) {
        ShortArrayArgs.checkMinValue(arr, minValue, "arr");
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput2(short[] arr, short minValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkMinValue(arr2, minValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull() {
        ShortArrayArgs.checkMinValue((short[]) null, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNull2() {
        ShortArrayArgs.checkMinValue((Short[]) null, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkMinValue(arr, (short) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 3 },
                { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 4 },
                { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 5 },
                
                { new short[] {(short) -1,(short) -2,(short) -3 },(short) -1 },
                { new short[] {(short) -1,(short) -2,(short) -3 }, (short) 0 },
                { new short[] {(short) -1,(short) -2,(short) -3 }, (short) 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(short[] arr, short maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkMaxValue(arr, maxValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkMaxValue(arr, maxValue, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkMaxValue(arr, maxValue, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkMaxValue(arr, maxValue, "   "));
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass2(short[] arr, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMaxValue(arr2, maxValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMaxValue(arr2, maxValue, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMaxValue(arr2, maxValue, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkMaxValue(arr2, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) -1 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 0 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 1 },
            
            { new short[] { (short) -1,(short) -2, (short) -3 }, (short) -2 },
            { new short[] { (short) -1,(short) -2, (short) -3 }, (short) -3 },
            { new short[] { (short) -1,(short) -2, (short) -3 }, (short) -4 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(short[] arr, short maxValue) {
        ShortArrayArgs.checkMaxValue(arr, maxValue, "arr");
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput2(short[] arr, short maxValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkMaxValue(arr2, maxValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull() {
        ShortArrayArgs.checkMaxValue((short[]) null, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNull2() {
        ShortArrayArgs.checkMaxValue((Short[]) null, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkMaxValue(arr, (short) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
            { new short[] { (short) 1 }, (short) 1 },
            { new short[] { (short) 1, (short) 1 }, (short) 1 },
            { new short[] { (short) 1, (short) 1, (short) 1 }, (short) 1 },
            
            { new short[] {(short) -1 },(short) -1 },
            { new short[] {(short) -1,(short) -1 },(short) -1 },
            { new short[] {(short) -1,(short) -1,(short) -1 },(short) -1 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(short[] arr, short exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass2(short[] arr, short exactValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) -1 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 0 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 1 },
            
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) -2 },
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) -3 },
            { new short[] { (short) -1, (short) -2, (short) -3 }, (short) -4 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(short[] arr, short exactValue) {
        ShortArrayArgs.checkExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput2(short[] arr, short exactValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull() {
        ShortArrayArgs.checkExactValue((short[]) null, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNull2() {
        ShortArrayArgs.checkExactValue((Short[]) null, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkExactValue(arr, (short) 1, "arr");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { new short[] { (short) 1 }, (short) 2 },
            { new short[] { (short) 1, (short) 1 }, (short) 2 },
            { new short[] { (short) 1, (short) 1, (short) 1 },(short) -2 },
            
            { new short[] {(short) -1 },(short) -2 },
            { new short[] {(short) -1,(short) -1 },(short) -2 },
            { new short[] {(short) -1,(short) -1,(short) -1 }, (short) 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(short[] arr, short exactValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr == ShortArrayArgs.checkNotExactValue(arr, exactValue, "arr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr == ShortArrayArgs.checkNotExactValue(arr, exactValue, null));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotExactValue(arr, exactValue, ""));
        Assert.assertTrue(arr == ShortArrayArgs.checkNotExactValue(arr, exactValue, "   "));
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass2(short[] arr, short exactValue) {
        Short[] arr2 = toShortObjectArray(arr);
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotExactValue(arr2, exactValue, "arr2"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotExactValue(arr2, exactValue, null));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotExactValue(arr2, exactValue, ""));
        Assert.assertTrue(arr2 == ShortArrayArgs.checkNotExactValue(arr2, exactValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 1 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 2 },
            { new short[] { (short) 1, (short) 2, (short) 3 }, (short) 3 },
            
            { new short[] {(short) -1,(short) -2,(short) -3 },(short) -1 },
            { new short[] {(short) -1,(short) -2,(short) -3 },(short) -2 },
            { new short[] {(short) -1,(short) -2,(short) -3 },(short) -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(short[] arr, short exactValue) {
        ShortArrayArgs.checkNotExactValue(arr, exactValue, "arr");
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput2(short[] arr, short exactValue) {
        Short[] arr2 = toShortObjectArray(arr);
        ShortArrayArgs.checkNotExactValue(arr2, exactValue, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull() {
        ShortArrayArgs.checkNotExactValue((short[]) null, (short) 1, "arr");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNull2() {
        ShortArrayArgs.checkNotExactValue((Short[]) null, (short) 1, "arr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithNullElement_Data() {
        return new Object[][] {
                { new Short[] { null, (short) 1 } },
                { new Short[] { (short) 1, null } },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithNullElement_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNullElement(Short[] arr) {
        ShortArrayArgs.checkNotExactValue(arr, (short) 1, "arr");
    }
}
