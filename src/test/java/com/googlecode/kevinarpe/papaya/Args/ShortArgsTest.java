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

import com.googlecode.kevinarpe.papaya.args.ShortArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ShortArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { (short) 1 },
                { (short) 99 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(short i) {
        ShortArgs.checkPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
        return new Object[][] {
                { (short) 0 },
                { (short) -1 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithNonPositiveInput(short i) {
        ShortArgs.checkPositive(i, "i");
    }

    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(short i) {
        ShortArgs.checkPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithInvalidArgName() {
        return new Object[][] {
                { (short) 0, "" },
                { (short) -1, "   " },  // ASCII spaces
                { Short.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithInvalidArgName(short i, String argName) {
        ShortArgs.checkPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotPositive() {
        return new Object[][] {
                { (short) 0 },
                { (short) -99 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotPositive")
    public void shouldCheckAsNotPositive(short i) {
        ShortArgs.checkNotPositive(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
        return new Object[][] {
                { (short) 1 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithPositiveInput(short i) {
        ShortArgs.checkNotPositive(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(short i) {
        ShortArgs.checkNotPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, "" },
                { 99, "   " },  // ASCII spaces
                { Short.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithInvalidArgName(short i, String argName) {
        ShortArgs.checkNotPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { (short) -1 },
                { (short) -99 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(short i) {
        ShortArgs.checkNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
        return new Object[][] {
                { (short) 0 },
                { (short) 1 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithNonNegativeInput(short i) {
        ShortArgs.checkNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(short i) {
        ShortArgs.checkNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, "" },
                { 99, "   " },  // ASCII spaces
                { Short.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithInvalidArgName(short i, String argName) {
        ShortArgs.checkNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNegative() {
        return new Object[][] {
                { (short) 0 },
                { (short) 99 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNegative")
    public void shouldCheckAsNotNegative(short i) {
        ShortArgs.checkNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { (short) -1 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(short i) {
        ShortArgs.checkNotNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(short i) {
        ShortArgs.checkNotNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithInvalidArgName() {
        return new Object[][] {
                { (short) -1, "" },
                { -99, "   " },  // ASCII spaces
                { Short.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithInvalidArgName(short i, String argName) {
        ShortArgs.checkNotNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { (short) 1, (short) -1, (short) 2 },
                { (short) 1, (short) -1, (short) 1 },
                { (short) 1, (short) 0, (short) 1 },
                { (short) 1, (short) 0, (short) 2 },
                { (short) 1, (short) 1, (short) 1 },
                { (short) 1, (short) 1, (short) 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(short i, short minValue, short maxValue) {
        ShortArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
        return new Object[][] {
                { (short) 1, (short) -1, (short) 0 },
                { (short) 1, (short) 0, (short) 0 },
                { (short) 1, (short) 2, (short) 2 },
                { (short) 1, (short) 2, (short) 1 },
                { (short) 1, (short) -1, (short) -2 },
                { (short) 1, (short) -2, (short) -1 },
                
                { (short) -1, (short) 1, (short) 0 },
                { (short) -1, (short) 0, (short) 0 },
                { (short) -1, (short) -2, (short) -2 },
                { (short) -1, (short) -1, (short) -2 },
                { (short) -1, (short) 1, (short) 2 },
                { (short) -1, (short) 2, (short) 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            short i, short minValue, short maxValue) {
        ShortArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            short i, short minValue, short maxValue) {
        ShortArgs.checkValueRange(i, minValue, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, (short) -1, (short) 0, "" },
                { (short) 1, (short) 0, (short) 0, "   " },  // ASCII spaces
                { (short) 1, (short) 2, (short) 2, "　　　" },  // wide Japanese spaces
                { (short) 1, (short) 2, (short) 1, "" },
                { (short) 1, (short) -1, (short) -2, "   " },  // ASCII spaces
                { (short) 1, (short) -2, (short) -1, "　　　" },  // wide Japanese spaces
                
                { (short) -1, (short) 1, (short) 0, "" },
                { (short) -1, (short) 0, (short) 0, "   " },  // ASCII spaces
                { (short) -1, (short) -2, (short) -2, "　　　" },  // wide Japanese spaces
                { (short) -1, (short) -1, (short) -2, "" },
                { (short) -1, (short) 1, (short) 2, "   " },  // ASCII spaces
                { (short) -1, (short) 2, (short) 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidArgName(
            short i, short minValue, short maxValue, String argName) {
        ShortArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { (short) 1, (short) -1 },
                { (short) 1, (short) 0 },
                { (short) 1, (short) 1 },
                
                { (short) -1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(short i, short minValue) {
        ShortArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (short) 1, (short) 2 },
                { (short) 1, (short) 3 },
                { (short) -1, (short) 0 },
                { (short) -1, (short) 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(short i, short minValue) {
        ShortArgs.checkMinValue(i, minValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(short i, short minValue) {
        ShortArgs.checkMinValue(i, minValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, (short) 2, "" },
                { (short) 1, (short) 3, "   " },  // ASCII spaces
                { (short) -1, (short) 0, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidArgName(
            short i, short minValue, String argName) {
        ShortArgs.checkMinValue(i, minValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { (short) 1, (short) 1 },
                { (short) 1, (short) 2 },
                { (short) 1, (short) 3 },
                
                { (short) -1, (short) -1 },
                { (short) -1, (short) 0 },
                { (short) -1, (short) 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(short i, short maxValue) {
        ShortArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (short) 1, (short) 0 },
                { (short) 1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(short i, short maxValue) {
        ShortArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(short i, short maxValue) {
        ShortArgs.checkMaxValue(i, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, (short) 0, "" },
                { (short) 1, (short) -1, "   " },  // ASCII spaces
                { (short) -1, (short) -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidArgName(
            short i, short maxValue, String argName) {
        ShortArgs.checkMaxValue(i, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { (short) 1, (short) 1 },
                { (short) 0, (short) 0 },
                { (short) -1, (short) -1 },
                { Short.MAX_VALUE, Short.MAX_VALUE },
                { Short.MIN_VALUE, Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(short i, short value) {
        ShortArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (short) 1, (short) 0 },
                { (short) 1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(short i, short value) {
        ShortArgs.checkMaxValue(i, value, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(short i, short value) {
        ShortArgs.checkExactValue(i, value, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (short) 1, (short) 0, "" },
                { (short) 1, (short) -1, "   " },  // ASCII spaces
                { (short) -1, (short) -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidArgName(
            short i, short value, String argName) {
        ShortArgs.checkExactValue(i, value, argName);
    }
}
