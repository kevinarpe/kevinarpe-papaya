package com.googlecode.kevinarpe.papaya.Args;

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

import com.googlecode.kevinarpe.papaya.args.ByteArgs;

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
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { (byte) 1 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(byte i) {
        ByteArgs.checkPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithNonPositiveInput(byte i) {
        ByteArgs.checkPositive(i, "i");
    }

    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(byte i) {
        ByteArgs.checkPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithInvalidArgName() {
        return new Object[][] {
                { (byte) 0, "" },
                { (byte) -1, "   " },  // ASCII spaces
                { Byte.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithInvalidArgName(byte i, String argName) {
        ByteArgs.checkPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotPositive() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotPositive")
    public void shouldCheckAsNotPositive(byte i) {
        ByteArgs.checkNotPositive(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
        return new Object[][] {
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithPositiveInput(byte i) {
        ByteArgs.checkNotPositive(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(byte i) {
        ByteArgs.checkNotPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, "" },
                { 99, "   " },  // ASCII spaces
                { Byte.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithInvalidArgName(byte i, String argName) {
        ByteArgs.checkNotPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { (byte) -1 },
                { (byte) -99 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(byte i) {
        ByteArgs.checkNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 1 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithNonNegativeInput(byte i) {
        ByteArgs.checkNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(byte i) {
        ByteArgs.checkNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, "" },
                { 99, "   " },  // ASCII spaces
                { Byte.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithInvalidArgName(byte i, String argName) {
        ByteArgs.checkNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNegative() {
        return new Object[][] {
                { (byte) 0 },
                { (byte) 99 },
                { Byte.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNegative")
    public void shouldCheckAsNotNegative(byte i) {
        ByteArgs.checkNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { (byte) -1 },
                { Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(byte i) {
        ByteArgs.checkNotNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(byte i) {
        ByteArgs.checkNotNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithInvalidArgName() {
        return new Object[][] {
                { (byte) -1, "" },
                { -99, "   " },  // ASCII spaces
                { Byte.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithInvalidArgName(byte i, String argName) {
        ByteArgs.checkNotNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { (byte) 1, (byte) -1, (byte) 2 },
                { (byte) 1, (byte) -1, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 1 },
                { (byte) 1, (byte) 0, (byte) 2 },
                { (byte) 1, (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 1, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            byte i, byte minValue, byte maxValue) {
        ByteArgs.checkValueRange(i, minValue, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, (byte) -1, (byte) 0, "" },
                { (byte) 1, (byte) 0, (byte) 0, "   " },  // ASCII spaces
                { (byte) 1, (byte) 2, (byte) 2, "　　　" },  // wide Japanese spaces
                { (byte) 1, (byte) 2, (byte) 1, "" },
                { (byte) 1, (byte) -1, (byte) -2, "   " },  // ASCII spaces
                { (byte) 1, (byte) -2, (byte) -1, "　　　" },  // wide Japanese spaces
                
                { (byte) -1, (byte) 1, (byte) 0, "" },
                { (byte) -1, (byte) 0, (byte) 0, "   " },  // ASCII spaces
                { (byte) -1, (byte) -2, (byte) -2, "　　　" },  // wide Japanese spaces
                { (byte) -1, (byte) -1, (byte) -2, "" },
                { (byte) -1, (byte) 1, (byte) 2, "   " },  // ASCII spaces
                { (byte) -1, (byte) 2, (byte) 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidArgName(
            byte i, byte minValue, byte maxValue, String argName) {
        ByteArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { (byte) 1, (byte) -1 },
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) 1 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(byte i, byte minValue) {
        ByteArgs.checkMinValue(i, minValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, (byte) 2, "" },
                { (byte) 1, (byte) 3, "   " },  // ASCII spaces
                { (byte) -1, (byte) 0, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidArgName(
            byte i, byte minValue, String argName) {
        ByteArgs.checkMinValue(i, minValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 1, (byte) 2 },
                { (byte) 1, (byte) 3 },
                
                { (byte) -1, (byte) -1 },
                { (byte) -1, (byte) 0 },
                { (byte) -1, (byte) 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(byte i, byte maxValue) {
        ByteArgs.checkMaxValue(i, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, (byte) 0, "" },
                { (byte) 1, (byte) -1, "   " },  // ASCII spaces
                { (byte) -1, (byte) -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidArgName(
            byte i, byte maxValue, String argName) {
        ByteArgs.checkMaxValue(i, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { (byte) 1, (byte) 1 },
                { (byte) 0, (byte) 0 },
                { (byte) -1, (byte) -1 },
                { Byte.MAX_VALUE, Byte.MAX_VALUE },
                { Byte.MIN_VALUE, Byte.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(byte i, byte value) {
        ByteArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (byte) 1, (byte) 0 },
                { (byte) 1, (byte) -1 },
                { (byte) -1, (byte) -2 },
                { (byte) -1, (byte) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(byte i, byte value) {
        ByteArgs.checkMaxValue(i, value, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(byte i, byte value) {
        ByteArgs.checkExactValue(i, value, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (byte) 1, (byte) 0, "" },
                { (byte) 1, (byte) -1, "   " },  // ASCII spaces
                { (byte) -1, (byte) -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidArgName(
            byte i, byte value, String argName) {
        ByteArgs.checkExactValue(i, value, argName);
    }
}
