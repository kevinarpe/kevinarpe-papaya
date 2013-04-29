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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtil;
import com.googlecode.kevinarpe.papaya.Args.DoubleArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DoubleArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsPositive() {
        return new Object[][] {
                { 1.0d },
                { 99.0d },
                { Double.MAX_VALUE },
                { (double)PrimitiveTypeUtil.EPSILON_POSITIVE_FLOAT, },
                { PrimitiveTypeUtil.EPSILON_POSITIVE_DOUBLE, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsPositive")
    public void shouldCheckAsPositive(double i) {
        DoubleArgs.checkPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNullArgName() {
        return new Object[][] {
                { 1.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsPositiveWithNullArgName(double i, String argName) {
        DoubleArgs.checkPositive(i, argName);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsPositiveWithNonPositiveInput() {
        return new Object[][] {
                { 0.0d },
                { -1.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsPositiveWithNonPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsPositiveWithNonPositiveInput(double i) {
        DoubleArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotPositive() {
        return new Object[][] {
                { 0.0d },
                { -99.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotPositive")
    public void shouldCheckAsNotPositive(double i) {
        DoubleArgs.checkNotPositive(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithNullArgName() {
        return new Object[][] {
                { 1.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotPositiveWithNullArgName(double i, String argName) {
        DoubleArgs.checkNotPositive(i, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotPositiveWithPositiveInput() {
        return new Object[][] {
                { 1.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotPositiveWithPositiveInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotPositiveWithPositiveInput(double i) {
        DoubleArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNegative() {
        return new Object[][] {
                { -1.0d },
                { -99.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
                { (double)PrimitiveTypeUtil.EPSILON_NEGATIVE_FLOAT, },
                { PrimitiveTypeUtil.EPSILON_NEGATIVE_DOUBLE, },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNegative")
    public void shouldCheckAsNegative(double i) {
        DoubleArgs.checkNegative(i, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNullArgName() {
        return new Object[][] {
                { 1.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNegativeWithNullArgName(double i, String argName) {
        DoubleArgs.checkNegative(i, argName);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNegativeWithNonNegativeInput() {
        return new Object[][] {
                { 0.0d },
                { 1.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNegativeWithNonNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNegativeWithNonNegativeInput(double i) {
        DoubleArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotNegative() {
        return new Object[][] {
                { 0.0d },
                { 99.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotNegative")
    public void shouldCheckAsNotNegative(double i) {
        DoubleArgs.checkNotNegative(i, "i");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNullArgName() {
        return new Object[][] {
                { 1.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotNegativeWithNullArgName(double i, String argName) {
        DoubleArgs.checkNotNegative(i, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotNegativeWithNegativeInput() {
        return new Object[][] {
                { -1.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotNegativeWithNegativeInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotNegativeWithNegativeInput(double i) {
        DoubleArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { 1.0d, -1.0d, 2.0d },
                { 1.0d, -1.0d, 1.0d },
                { 1.0d, 0.0d, 1.0d },
                { 1.0d, 0.0d, 2.0d },
                { 1.0d, 1.0d, 1.0d },
                { 1.0d, 1.0d, 2.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(double i, double minValue, double maxValue) {
        DoubleArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0d, 2.0d, 1.0d, null },
                { 1.0d, 2.0d, 3.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            double i, double minValue, double maxValue, String argName) {
        DoubleArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0d, -1.0d, 0.0d },
                { 1.0d, 0.0d, 0.0d },
                { 1.0d, 2.0d, 2.0d },
                { 1.0d, 2.0d, 1.0d },
                { 1.0d, -1.0d, -2.0d },
                { 1.0d, -2.0d, -1.0d },
                
                { -1.0d, 1.0d, 0.0d },
                { -1.0d, 0.0d, 0.0d },
                { -1.0d, -2.0d, -2.0d },
                { -1.0d, -1.0d, -2.0d },
                { -1.0d, 1.0d, 2.0d },
                { -1.0d, 2.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            double i, double minValue, double maxValue) {
        DoubleArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { 1.0d, -1.0d },
                { 1.0d, 0.0d },
                { 1.0d, 1.0d },
                
                { -1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(double i, double minValue) {
        DoubleArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0d, 2.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(
            double i, double minValue, String argName) {
        DoubleArgs.checkMinValue(i, minValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0d, 2.0d },
                { 1.0d, 3.0d },
                { -1.0d, 0.0d },
                { -1.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(double i, double minValue) {
        DoubleArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { 1.0d, 1.0d },
                { 1.0d, 2.0d },
                { 1.0d, 3.0d },
                
                { -1.0d, -1.0d },
                { -1.0d, 0.0d },
                { -1.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(double i, double maxValue) {
        DoubleArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0d, 0.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(
            double i, double maxValue, String argName) {
        DoubleArgs.checkMaxValue(i, maxValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0d, 0.0d },
                { 1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(double i, double maxValue) {
        DoubleArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { 1.0d, 1.0d },
                { 0.0d, 0.0d },
                { -1.0d, -1.0d },
                { Double.MAX_VALUE, Double.MAX_VALUE },
                { -Double.MAX_VALUE, -Double.MAX_VALUE },
                { Double.MIN_VALUE, Double.MIN_VALUE },
                { -Double.MIN_VALUE, -Double.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(double i, double value) {
        DoubleArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithNullArgName() {
        return new Object[][] {
                { 1.0d, 2.0d, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(
            double i, double value, String argName) {
        DoubleArgs.checkExactValue(i, value, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { 1.0d, 0.0d },
                { 1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(double i, double value) {
        DoubleArgs.checkMaxValue(i, value, "i");
    }
}
