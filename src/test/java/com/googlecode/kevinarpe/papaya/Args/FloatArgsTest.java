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
import com.googlecode.kevinarpe.papaya.args.FloatArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FloatArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { 1.0f },
                { 99.0f },
                { Float.MAX_VALUE },
                { PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(float i) {
        FloatArgs.checkPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
        return new Object[][] {
                { 0.0f },
                { -1.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithNonPositiveInput(float i) {
        FloatArgs.checkPositive(i, "i");
    }

    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(float i) {
        FloatArgs.checkPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithInvalidArgName() {
        return new Object[][] {
                { 0.0f, "" },
                { -1.0f, "   " },  // ASCII spaces
                { -Float.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithInvalidArgName(float i, String argName) {
        FloatArgs.checkPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotPositive() {
        return new Object[][] {
                { 0.0f },
                { -99.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotPositive")
    public void shouldCheckAsNotPositive(float i) {
        FloatArgs.checkNotPositive(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
        return new Object[][] {
                { 1.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithPositiveInput(float i) {
        FloatArgs.checkNotPositive(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(float i) {
        FloatArgs.checkNotPositive(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, "" },
                { 99.0f, "   " },  // ASCII spaces
                { Float.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithInvalidArgName(float i, String argName) {
        FloatArgs.checkNotPositive(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { -1.0f },
                { -99.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
                { PrimitiveTypeUtils.EPSILON_NEGATIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(float i) {
        FloatArgs.checkNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
        return new Object[][] {
                { 0.0f },
                { 1.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithNonNegativeInput(float i) {
        FloatArgs.checkNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(float i) {
        FloatArgs.checkNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, "" },
                { 99.0f, "   " },  // ASCII spaces
                { Float.MAX_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithInvalidArgName(float i, String argName) {
        FloatArgs.checkNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNegative() {
        return new Object[][] {
                { 0.0f },
                { 99.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNegative")
    public void shouldCheckAsNotNegative(float i) {
        FloatArgs.checkNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { -1.0f },
                { -Float.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(float i) {
        FloatArgs.checkNotNegative(i, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(float i) {
        FloatArgs.checkNotNegative(i, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithInvalidArgName() {
        return new Object[][] {
                { -1.0f, "" },
                { -99.0f, "   " },  // ASCII spaces
                { -Float.MIN_VALUE, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithInvalidArgName(float i, String argName) {
        FloatArgs.checkNotNegative(i, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { 1.0f, -1.0f, 2.0f },
                { 1.0f, -1.0f, 1.0f },
                { 1.0f, 0.0f, 1.0f },
                { 1.0f, 0.0f, 2.0f },
                { 1.0f, 1.0f, 1.0f },
                { 1.0f, 1.0f, 2.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(float i, float minValue, float maxValue) {
        FloatArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0f, -1.0f, 0.0f },
                { 1.0f, 0.0f, 0.0f },
                { 1.0f, 2.0f, 2.0f },
                { 1.0f, 2.0f, 1.0f },
                { 1.0f, -1.0f, -2.0f },
                { 1.0f, -2.0f, -1.0f },
                
                { -1.0f, 1.0f, 0.0f },
                { -1.0f, 0.0f, 0.0f },
                { -1.0f, -2.0f, -2.0f },
                { -1.0f, -1.0f, -2.0f },
                { -1.0f, 1.0f, 2.0f },
                { -1.0f, 2.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            float i, float minValue, float maxValue) {
        FloatArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            float i, float minValue, float maxValue) {
        FloatArgs.checkValueRange(i, minValue, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, -1.0f, 0.0f, "" },
                { 1.0f, 0.0f, 0.0f, "   " },  // ASCII spaces
                { 1.0f, 2.0f, 2.0f, "　　　" },  // wide Japanese spaces
                { 1.0f, 2.0f, 1.0f, "" },
                { 1.0f, -1.0f, -2.0f, "   " },  // ASCII spaces
                { 1.0f, -2.0f, -1.0f, "　　　" },  // wide Japanese spaces
                
                { -1.0f, 1.0f, 0.0f, "" },
                { -1.0f, 0.0f, 0.0f, "   " },  // ASCII spaces
                { -1.0f, -2.0f, -2.0f, "　　　" },  // wide Japanese spaces
                { -1.0f, -1.0f, -2.0f, "" },
                { -1.0f, 1.0f, 2.0f, "   " },  // ASCII spaces
                { -1.0f, 2.0f, 1.0f, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidArgName(
            float i, float minValue, float maxValue, String argName) {
        FloatArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { 1.0f, -1.0f },
                { 1.0f, 0.0f },
                { 1.0f, 1.0f },
                
                { -1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(float i, float minValue) {
        FloatArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0f, 2.0f },
                { 1.0f, 3.0f },
                { -1.0f, 0.0f },
                { -1.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(float i, float minValue) {
        FloatArgs.checkMinValue(i, minValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(float i, float minValue) {
        FloatArgs.checkMinValue(i, minValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, 2.0f, "" },
                { 1.0f, 3.0f, "   " },  // ASCII spaces
                { -1.0f, 0.0f, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidArgName(
            float i, float minValue, String argName) {
        FloatArgs.checkMinValue(i, minValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { 1.0f, 1.0f },
                { 1.0f, 2.0f },
                { 1.0f, 3.0f },
                
                { -1.0f, -1.0f },
                { -1.0f, 0.0f },
                { -1.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(float i, float maxValue) {
        FloatArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0f, 0.0f },
                { 1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(float i, float maxValue) {
        FloatArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(float i, float maxValue) {
        FloatArgs.checkMaxValue(i, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, 0.0f, "" },
                { 1.0f, -1.0f, "   " },  // ASCII spaces
                { -1.0f, -2.0f, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidArgName(
            float i, float maxValue, String argName) {
        FloatArgs.checkMaxValue(i, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { 1.0f, 1.0f },
                { 0.0f, 0.0f },
                { -1.0f, -1.0f },
                { Float.MAX_VALUE, Float.MAX_VALUE },
                { -Float.MAX_VALUE, -Float.MAX_VALUE },
                { Float.MIN_VALUE, Float.MIN_VALUE },
                { -Float.MIN_VALUE, -Float.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(float i, float value) {
        FloatArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0f, 0.0f },
                { 1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(float i, float value) {
        FloatArgs.checkMaxValue(i, value, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(float i, float value) {
        FloatArgs.checkExactValue(i, value, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { 1.0f, 0.0f, "" },
                { 1.0f, -1.0f, "   " },  // ASCII spaces
                { -1.0f, -2.0f, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidArgName(
            float i, float value, String argName) {
        FloatArgs.checkExactValue(i, value, argName);
    }
}
