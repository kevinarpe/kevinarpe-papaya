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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ByteArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkPositive
    //

    @DataProvider
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { (byte) 1 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(byte i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkPositive(i, null));
        Assert.assertTrue(i == ByteArgs.checkPositive(i, ""));
        Assert.assertTrue(i == ByteArgs.checkPositive(i, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(byte i) {
        ByteArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(byte i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkNotPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkNotPositive(i, null));
        Assert.assertTrue(i == ByteArgs.checkNotPositive(i, ""));
        Assert.assertTrue(i == ByteArgs.checkNotPositive(i, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(byte i) {
        ByteArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { (byte) -1 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(byte i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkNegative(i, null));
        Assert.assertTrue(i == ByteArgs.checkNegative(i, ""));
        Assert.assertTrue(i == ByteArgs.checkNegative(i, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(byte i) {
        ByteArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(byte i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkNotNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkNotNegative(i, null));
        Assert.assertTrue(i == ByteArgs.checkNotNegative(i, ""));
        Assert.assertTrue(i == ByteArgs.checkNotNegative(i, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(byte i) {
        ByteArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkValueRange
    //

    @DataProvider
    public static Object[][] checkValueRange_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) -1, (byte) 2 },
                { (byte) 1, (byte) -1, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 2 },
                { (byte) 1, (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 1, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueRange_Pass_Data")
    public void checkValueRange_Pass(byte i, byte minValue, byte maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkValueRange(i, minValue, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkValueRange(i, minValue, maxValue, null));
        Assert.assertTrue(i == ByteArgs.checkValueRange(i, minValue, maxValue, ""));
        Assert.assertTrue(i == ByteArgs.checkValueRange(i, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) -1, (byte) 0 },
                { (byte) 1, (byte) 0, (byte) 0 },
                { (byte) 1, (byte) 2, (byte) 2 },
                { (byte) 1, (byte) 2, (byte) 1 },
                { (byte) 1, (byte) -1, (byte) -2 },
                { (byte) 1, (byte) -2, (byte) -1 },
                
                { (byte) -1, (byte) 1, (byte) 0 },
                { (byte) -1, (byte) 0, (byte) 0 },
                { (byte) -1, (byte) -2, (byte) -2 },
                { (byte) -1, (byte) -1, (byte) -2 },
                { (byte) -1, (byte) 1, (byte) 2 },
                { (byte) -1, (byte) 2, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) -1 },
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) 1 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(byte i, byte minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkMinValue(i, minValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkMinValue(i, minValue, null));
        Assert.assertTrue(i == ByteArgs.checkMinValue(i, minValue, ""));
        Assert.assertTrue(i == ByteArgs.checkMinValue(i, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(byte i, byte maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkMaxValue(i, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkMaxValue(i, maxValue, null));
        Assert.assertTrue(i == ByteArgs.checkMaxValue(i, maxValue, ""));
        Assert.assertTrue(i == ByteArgs.checkMaxValue(i, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 0, (byte) 0 },
                { (byte) -1, (byte) -1 },
                { Byte.MAX_VALUE, Byte.MAX_VALUE },
                { Byte.MIN_VALUE, Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(byte i, byte value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkExactValue(i, value, null));
        Assert.assertTrue(i == ByteArgs.checkExactValue(i, value, ""));
        Assert.assertTrue(i == ByteArgs.checkExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(byte i, byte value) {
        ByteArgs.checkExactValue(i, value, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 2 },
                { (byte) 0, (byte) 1 },
                { (byte) -1, (byte) 0 },
                { Byte.MAX_VALUE, Byte.MIN_VALUE },
                { Byte.MIN_VALUE, Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(byte i, byte value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ByteArgs.checkNotExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ByteArgs.checkNotExactValue(i, value, null));
        Assert.assertTrue(i == ByteArgs.checkNotExactValue(i, value, ""));
        Assert.assertTrue(i == ByteArgs.checkNotExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 0, (byte) 0 },
                { (byte) -1, (byte) -1 },
                { (byte) -2, (byte) -2 },
                { (byte) -3, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(byte i, byte value) {
        ByteArgs.checkNotExactValue(i, value, "i");
    }
}
