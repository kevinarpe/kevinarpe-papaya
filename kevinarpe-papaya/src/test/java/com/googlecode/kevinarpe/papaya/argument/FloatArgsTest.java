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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import com.googlecode.kevinarpe.papaya.argument.FloatArgs;

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
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { 1.0f },
                { 99.0f },
                { Float.MAX_VALUE },
                { PrimitiveTypeUtils.EPSILON_POSITIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(float i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkPositive(i, null));
        Assert.assertTrue(i == FloatArgs.checkPositive(i, ""));
        Assert.assertTrue(i == FloatArgs.checkPositive(i, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { 0.0f },
                { -1.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(float i) {
        FloatArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { 0.0f },
                { -99.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(float i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkNotPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkNotPositive(i, null));
        Assert.assertTrue(i == FloatArgs.checkNotPositive(i, ""));
        Assert.assertTrue(i == FloatArgs.checkNotPositive(i, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { 1.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(float i) {
        FloatArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { -1.0f },
                { -99.0f },
                { -Float.MIN_VALUE },
                { -Float.MAX_VALUE },
                { PrimitiveTypeUtils.EPSILON_NEGATIVE_FLOAT, },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(float i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkNegative(i, null));
        Assert.assertTrue(i == FloatArgs.checkNegative(i, ""));
        Assert.assertTrue(i == FloatArgs.checkNegative(i, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { 0.0f },
                { 1.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(float i) {
        FloatArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { 0.0f },
                { 99.0f },
                { Float.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(float i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkNotNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkNotNegative(i, null));
        Assert.assertTrue(i == FloatArgs.checkNotNegative(i, ""));
        Assert.assertTrue(i == FloatArgs.checkNotNegative(i, "  "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { -1.0f },
                { -Float.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(float i) {
        FloatArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkValueRange
    //

    @DataProvider
    public static Object[][] checkValueRange_Pass_Data() {
        return new Object[][] {
                { 1.0f, -1.0f, 2.0f },
                { 1.0f, -1.0f, 1.0f },
                { 1.0f, 0.0f, 1.0f },
                { 1.0f, 0.0f, 2.0f },
                { 1.0f, 1.0f, 1.0f },
                { 1.0f, 1.0f, 2.0f },
        };
    }
    
    @Test(dataProvider = "checkValueRange_Pass_Data")
    public void checkValueRange_Pass(float i, float minValue, float maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkValueRange(i, minValue, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkValueRange(i, minValue, maxValue, null));
        Assert.assertTrue(i == FloatArgs.checkValueRange(i, minValue, maxValue, ""));
        Assert.assertTrue(i == FloatArgs.checkValueRange(i, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            float i, float minValue, float maxValue) {
        FloatArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { 1.0f, -1.0f },
                { 1.0f, 0.0f },
                { 1.0f, 1.0f },
                
                { -1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(float i, float minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkMinValue(i, minValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkMinValue(i, minValue, null));
        Assert.assertTrue(i == FloatArgs.checkMinValue(i, minValue, ""));
        Assert.assertTrue(i == FloatArgs.checkMinValue(i, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0f, 2.0f },
                { 1.0f, 3.0f },
                { -1.0f, 0.0f },
                { -1.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(float i, float minValue) {
        FloatArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { 1.0f, 1.0f },
                { 1.0f, 2.0f },
                { 1.0f, 3.0f },
                
                { -1.0f, -1.0f },
                { -1.0f, 0.0f },
                { -1.0f, 1.0f },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(float i, float maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkMaxValue(i, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkMaxValue(i, maxValue, null));
        Assert.assertTrue(i == FloatArgs.checkMaxValue(i, maxValue, ""));
        Assert.assertTrue(i == FloatArgs.checkMaxValue(i, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0f, 0.0f },
                { 1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(float i, float maxValue) {
        FloatArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
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
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(float i, float value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkExactValue(i, value, null));
        Assert.assertTrue(i == FloatArgs.checkExactValue(i, value, ""));
        Assert.assertTrue(i == FloatArgs.checkExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1.0f, 0.0f },
                { 1.0f, -1.0f },
                { -1.0f, -2.0f },
                { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(float i, float value) {
        FloatArgs.checkExactValue(i, value, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FloatArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
            { 1.0f, 0.0f },
            { 1.0f, -1.0f },
            { -1.0f, -2.0f },
            { -1.0f, -3.0f },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(float i, float value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == FloatArgs.checkNotExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == FloatArgs.checkNotExactValue(i, value, null));
        Assert.assertTrue(i == FloatArgs.checkNotExactValue(i, value, ""));
        Assert.assertTrue(i == FloatArgs.checkNotExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(float i, float value) {
        FloatArgs.checkNotExactValue(i, value, "i");
    }
}
