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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.argument.ByteArgs;

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
    private static final Object[][] _checkPositive_Pass_Data() {
        return new Object[][] {
                { (byte) 1 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkPositive_Pass_Data")
    public void checkPositive_Pass(byte i) {
        ByteArgs.checkPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkPositive(i, null);
        ByteArgs.checkPositive(i, "");
        ByteArgs.checkPositive(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(byte i) {
        ByteArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _checkNotPositive_Pass_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(byte i) {
        ByteArgs.checkNotPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkNotPositive(i, null);
        ByteArgs.checkNotPositive(i, "");
        ByteArgs.checkNotPositive(i, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(byte i) {
        ByteArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _checkNegative_Pass_Data() {
        return new Object[][] {
                { (byte) -1 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_Pass_Data")
    public void checkNegative_Pass(byte i) {
        ByteArgs.checkNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkNegative(i, null);
        ByteArgs.checkNegative(i, "");
        ByteArgs.checkNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(byte i) {
        ByteArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _checkNotNegative_Pass_Data() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(byte i) {
        ByteArgs.checkNotNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkNotNegative(i, null);
        ByteArgs.checkNotNegative(i, "");
        ByteArgs.checkNotNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(byte i) {
        ByteArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _checkValueRange_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) -1, (byte) 2 },
                { (byte) 1, (byte) -1, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 2 },
                { (byte) 1, (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 1, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "_checkValueRange_Pass_Data")
    public void checkValueRange_Pass(byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkValueRange(i, minValue, maxValue, null);
        ByteArgs.checkValueRange(i, minValue, maxValue, "");
        ByteArgs.checkValueRange(i, minValue, maxValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "_checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _checkMinValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) -1 },
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) 1 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_checkMinValue_Pass_Data")
    public void checkMinValue_Pass(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkMinValue(i, minValue, null);
        ByteArgs.checkMinValue(i, minValue, "");
        ByteArgs.checkMinValue(i, minValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "_checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _checkMaxValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "_checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkMaxValue(i, maxValue, null);
        ByteArgs.checkMaxValue(i, maxValue, "");
        ByteArgs.checkMaxValue(i, maxValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _checkExactValue_Pass_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 0, (byte) 0 },
                { (byte) -1, (byte) -1 },
                { Byte.MAX_VALUE, Byte.MAX_VALUE },
                { Byte.MIN_VALUE, Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_Pass_Data")
    public void checkExactValue_Pass(byte i, byte value) {
        ByteArgs.checkExactValue(i, value, "i");
        // Demonstrate argName can be anything ridiculous.
        ByteArgs.checkExactValue(i, value, null);
        ByteArgs.checkExactValue(i, value, "");
        ByteArgs.checkExactValue(i, value, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(byte i, byte value) {
        ByteArgs.checkExactValue(i, value, "i");
    }
}
