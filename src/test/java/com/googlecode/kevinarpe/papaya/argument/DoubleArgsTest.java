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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
    private static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { 1.0d },
                { 99.0d },
                { Double.MAX_VALUE },
                { (double)PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT, },
                { PrimitiveTypeUtils.EPSILON_POSITIVE_DOUBLE, },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(double i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkPositive(i, null));
        Assert.assertTrue(i == DoubleArgs.checkPositive(i, ""));
        Assert.assertTrue(i == DoubleArgs.checkPositive(i, "   "));
    }

    @DataProvider
    private static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { 0.0d },
                { -1.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(double i) {
        DoubleArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNotPositive
    //

    @DataProvider
    private static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { 0.0d },
                { -99.0d },
                { -Double.MIN_VALUE },
                { -Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(double i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkNotPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkNotPositive(i, null));
        Assert.assertTrue(i == DoubleArgs.checkNotPositive(i, ""));
        Assert.assertTrue(i == DoubleArgs.checkNotPositive(i, "   "));
    }
    
    @DataProvider
    private static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { 1.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(double i) {
        DoubleArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNegative
    //

    @DataProvider
    private static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { -1.0d },
                { -99.0d },
                { -Double.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(double i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkNegative(i, null));
        Assert.assertTrue(i == DoubleArgs.checkNegative(i, ""));
        Assert.assertTrue(i == DoubleArgs.checkNegative(i, "   "));
    }

    @DataProvider
    private static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { 0.0d },
                { 1.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(double i) {
        DoubleArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNotNegative
    //

    @DataProvider
    private static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { 0.0d },
                { 99.0d },
                { Double.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(double i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkNotNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkNotNegative(i, null));
        Assert.assertTrue(i == DoubleArgs.checkNotNegative(i, ""));
        Assert.assertTrue(i == DoubleArgs.checkNotNegative(i, "   "));
    }

    @DataProvider
    private static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { -1.0d },
                { -Double.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(double i) {
        DoubleArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkValueRange
    //

    @DataProvider
    private static Object[][] checkValueRange_Pass_Data() {
        return new Object[][] {
                { 1.0d, -1.0d, 2.0d },
                { 1.0d, -1.0d, 1.0d },
                { 1.0d, 0.0d, 1.0d },
                { 1.0d, 0.0d, 2.0d },
                { 1.0d, 1.0d, 1.0d },
                { 1.0d, 1.0d, 2.0d },
        };
    }
    
    @Test(dataProvider = "checkValueRange_Pass_Data")
    public void checkValueRange_Pass(double i, double minValue, double maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkValueRange(i, minValue, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkValueRange(i, minValue, maxValue, null));
        Assert.assertTrue(i == DoubleArgs.checkValueRange(i, minValue, maxValue, ""));
        Assert.assertTrue(i == DoubleArgs.checkValueRange(i, minValue, maxValue, "   "));
    }
    
    @DataProvider
    private static Object[][] checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            double i, double minValue, double maxValue) {
        DoubleArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkMinValue
    //

    @DataProvider
    private static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { 1.0d, -1.0d },
                { 1.0d, 0.0d },
                { 1.0d, 1.0d },
                
                { -1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(double i, double minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkMinValue(i, minValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkMinValue(i, minValue, null));
        Assert.assertTrue(i == DoubleArgs.checkMinValue(i, minValue, ""));
        Assert.assertTrue(i == DoubleArgs.checkMinValue(i, minValue, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0d, 2.0d },
                { 1.0d, 3.0d },
                { -1.0d, 0.0d },
                { -1.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(double i, double minValue) {
        DoubleArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkMaxValue
    //

    @DataProvider
    private static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { 1.0d, 1.0d },
                { 1.0d, 2.0d },
                { 1.0d, 3.0d },
                
                { -1.0d, -1.0d },
                { -1.0d, 0.0d },
                { -1.0d, 1.0d },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(double i, double maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkMaxValue(i, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkMaxValue(i, maxValue, null));
        Assert.assertTrue(i == DoubleArgs.checkMaxValue(i, maxValue, ""));
        Assert.assertTrue(i == DoubleArgs.checkMaxValue(i, maxValue, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0d, 0.0d },
                { 1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(double i, double maxValue) {
        DoubleArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkExactValue
    //

    @DataProvider
    private static Object[][] checkExactValue_Pass_Data() {
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
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(double i, double value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkExactValue(i, value, null));
        Assert.assertTrue(i == DoubleArgs.checkExactValue(i, value, ""));
        Assert.assertTrue(i == DoubleArgs.checkExactValue(i, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0d, 0.0d },
                { 1.0d, -1.0d },
                { -1.0d, -2.0d },
                { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(double i, double value) {
        DoubleArgs.checkExactValue(i, value, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DoubleArgs.checkNotExactValue
    //

    @DataProvider
    private static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { 1.0d, 0.0d },
            { 1.0d, -1.0d },
            { -1.0d, -2.0d },
            { -1.0d, -3.0d },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(double i, double value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == DoubleArgs.checkNotExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == DoubleArgs.checkNotExactValue(i, value, null));
        Assert.assertTrue(i == DoubleArgs.checkNotExactValue(i, value, ""));
        Assert.assertTrue(i == DoubleArgs.checkNotExactValue(i, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(double i, double value) {
        DoubleArgs.checkNotExactValue(i, value, "i");
    }
}
