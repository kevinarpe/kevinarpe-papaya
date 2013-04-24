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
                { PrimitiveTypeUtil.EPSILON_POSITIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(float i) {
        FloatArgs.checkPositive(i, "i");
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
        FloatArgs.checkPositive(i, argName);
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
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(float i, String argName) {
        FloatArgs.checkNotPositive(i, argName);
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
                { PrimitiveTypeUtil.EPSILON_NEGATIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(float i) {
        FloatArgs.checkNegative(i, "i");
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
        FloatArgs.checkNegative(i, argName);
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
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNullArgName() {
        return new Object[][] {
                { 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(float i, String argName) {
        FloatArgs.checkNotNegative(i, argName);
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
        FloatArgs.checkNotNegative(i, "i");
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
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            float i, float minValue, float maxValue, String argName) {
        FloatArgs.checkValueRange(i, minValue, maxValue, argName);
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
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(
            float i, float minValue, String argName) {
        FloatArgs.checkMinValue(i, minValue, argName);
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
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(
            float i, float maxValue, String argName) {
        FloatArgs.checkMaxValue(i, maxValue, argName);
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
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0f, 1.0f, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(
            float i, float value, String argName) {
        FloatArgs.checkExactValue(i, value, argName);
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
}
