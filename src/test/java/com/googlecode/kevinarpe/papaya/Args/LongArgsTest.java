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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import com.googlecode.kevinarpe.papaya.args.LongArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class LongArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { 1 },
                { 99 },
                { Long.MAX_VALUE },
                { (long)(1.0f + PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), },
                { (long)(1.0f - PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT), },
                { (long)(1.0d + PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), },
                { (long)(1.0d - PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE), },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(long i) {
        LongArgs.checkPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
        return new Object[][] {
                { 0 },
                { -1 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithNonPositiveInput(long i) {
        LongArgs.checkPositive(i, "i");
    }

    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(long i) {
        LongArgs.checkPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithInvalidArgName() {
        return new Object[][] {
                { 0, "" },
                { -1, "   " },  // ASCII spaces
                { Long.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithInvalidArgName(long i, String argName) {
        LongArgs.checkPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotPositive() {
        return new Object[][] {
                { 0 },
                { -99 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotPositive")
    public void shouldCheckAsNotPositive(long i) {
        LongArgs.checkNotPositive(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
        return new Object[][] {
                { 1 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithPositiveInput(long i) {
        LongArgs.checkNotPositive(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(long i) {
        LongArgs.checkNotPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithInvalidArgName() {
        return new Object[][] {
                { 1, "" },
                { 99, "   " },  // ASCII spaces
                { Long.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithInvalidArgName(long i, String argName) {
        LongArgs.checkNotPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { -1 },
                { -99 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(long i) {
        LongArgs.checkNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
        return new Object[][] {
                { 0 },
                { 1 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithNonNegativeInput(long i) {
        LongArgs.checkNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(long i) {
        LongArgs.checkNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithInvalidArgName() {
        return new Object[][] {
                { 1, "" },
                { 99, "   " },  // ASCII spaces
                { Long.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithInvalidArgName(long i, String argName) {
        LongArgs.checkNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNegative() {
        return new Object[][] {
                { 0 },
                { 99 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNegative")
    public void shouldCheckAsNotNegative(long i) {
        LongArgs.checkNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { -1 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(long i) {
        LongArgs.checkNotNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(long i) {
        LongArgs.checkNotNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithInvalidArgName() {
        return new Object[][] {
                { -1, "" },
                { -99, "   " },  // ASCII spaces
                { Long.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithInvalidArgName(long i, String argName) {
        LongArgs.checkNotNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { 1, -1, 2 },
                { 1, -1, 1 },
                { 1, 0, 1 },
                { 1, 0, 2 },
                { 1, 1, 1 },
                { 1, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(long i, long minValue, long maxValue) {
        LongArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
        return new Object[][] {
                { 1, -1, 0 },
                { 1, 0, 0 },
                { 1, 2, 2 },
                { 1, 2, 1 },
                { 1, -1, -2 },
                { 1, -2, -1 },
                
                { -1, 1, 0 },
                { -1, 0, 0 },
                { -1, -2, -2 },
                { -1, -1, -2 },
                { -1, 1, 2 },
                { -1, 2, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            long i, long minValue, long maxValue) {
        LongArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            long i, long minValue, long maxValue) {
        LongArgs.checkValueRange(i, minValue, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1, -1, 0, "" },
                { 1, 0, 0, "   " },  // ASCII spaces
                { 1, 2, 2, "　　　" },  // wide Japanese spaces
                { 1, 2, 1, "" },
                { 1, -1, -2, "   " },  // ASCII spaces
                { 1, -2, -1, "　　　" },  // wide Japanese spaces
                
                { -1, 1, 0, "" },
                { -1, 0, 0, "   " },  // ASCII spaces
                { -1, -2, -2, "　　　" },  // wide Japanese spaces
                { -1, -1, -2, "" },
                { -1, 1, 2, "   " },  // ASCII spaces
                { -1, 2, 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidArgName(
            long i, long minValue, long maxValue, String argName) {
        LongArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { 1, -1 },
                { 1, 0 },
                { 1, 1 },
                
                { -1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(long i, long minValue) {
        LongArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1, 2 },
                { 1, 3 },
                { -1, 0 },
                { -1, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(long i, long minValue) {
        LongArgs.checkMinValue(i, minValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(long i, long minValue) {
        LongArgs.checkMinValue(i, minValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1, 2, "" },
                { 1, 3, "   " },  // ASCII spaces
                { -1, 0, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidArgName(
            long i, long minValue, String argName) {
        LongArgs.checkMinValue(i, minValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { 1, 1 },
                { 1, 2 },
                { 1, 3 },
                
                { -1, -1 },
                { -1, 0 },
                { -1, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(long i, long maxValue) {
        LongArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1, 0 },
                { 1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(long i, long maxValue) {
        LongArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(long i, long maxValue) {
        LongArgs.checkMaxValue(i, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1, 0, "" },
                { 1, -1, "   " },  // ASCII spaces
                { -1, -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidArgName(
            long i, long maxValue, String argName) {
        LongArgs.checkMaxValue(i, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { 1, 1 },
                { 0, 0 },
                { -1, -1 },
                { Long.MAX_VALUE, Long.MAX_VALUE },
                { Long.MIN_VALUE, Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(long i, long value) {
        LongArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1, 0 },
                { 1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(long i, long value) {
        LongArgs.checkMaxValue(i, value, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(long i, long value) {
        LongArgs.checkExactValue(i, value, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1, 0, "" },
                { 1, -1, "   " },  // ASCII spaces
                { -1, -2, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidArgName(
            long i, long value, String argName) {
        LongArgs.checkExactValue(i, value, argName);
    }
}
