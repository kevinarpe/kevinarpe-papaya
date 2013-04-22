/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.PrimitiveTypeUtil;

public class FloatArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { 1.0f },
                { 99.0f },
                { Float.MAX_VALUE },
                { PrimitiveTypeUtil.EPSILON_POSITIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(float i) {
        FloatArgs.staticCheckPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(float i, String argName) {
        FloatArgs.staticCheckPositive(i, argName);
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
        FloatArgs.staticCheckPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckNotPositive
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
        FloatArgs.staticCheckNotPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(float i, String argName) {
        FloatArgs.staticCheckNotPositive(i, argName);
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
        FloatArgs.staticCheckNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { -1.0f },
                { -99.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
                { PrimitiveTypeUtil.EPSILON_NEGATIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(float i) {
        FloatArgs.staticCheckNegative(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(float i, String argName) {
        FloatArgs.staticCheckNegative(i, argName);
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
        FloatArgs.staticCheckNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckNotNegative
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
        FloatArgs.staticCheckNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(float i, String argName) {
        FloatArgs.staticCheckNotNegative(i, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { -1.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(float i) {
        FloatArgs.staticCheckNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckValueRange
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
        FloatArgs.staticCheckValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            float i, float minValue, float maxValue, String argName) {
        FloatArgs.staticCheckValueRange(i, minValue, maxValue, argName);
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
        FloatArgs.staticCheckValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckMinValue
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
        FloatArgs.staticCheckMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(
            float i, float minValue, String argName) {
        FloatArgs.staticCheckMinValue(i, minValue, argName);
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
        FloatArgs.staticCheckMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckMaxValue
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
        FloatArgs.staticCheckMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(
            float i, float maxValue, String argName) {
        FloatArgs.staticCheckMaxValue(i, maxValue, argName);
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
        FloatArgs.staticCheckMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.staticCheckExactValue
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
        FloatArgs.staticCheckExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(
            float i, float value, String argName) {
        FloatArgs.staticCheckExactValue(i, value, argName);
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
        FloatArgs.staticCheckMaxValue(i, value, "i");
    }
}
