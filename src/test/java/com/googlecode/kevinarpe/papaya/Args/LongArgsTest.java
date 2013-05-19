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
    private static final Object[][] _checkPositive_Pass_Data() {
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
    
    @Test(dataProvider = "_checkPositive_Pass_Data")
    public void checkPositive_Pass(long i) {
        LongArgs.checkPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkPositive(i, null);
        LongArgs.checkPositive(i, "");
        LongArgs.checkPositive(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { 0 },
                { -1 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(long i) {
        LongArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _checkNotPositive_Pass_Data() {
        return new Object[][] {
                { 0 },
                { -99 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(long i) {
        LongArgs.checkNotPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkNotPositive(i, null);
        LongArgs.checkNotPositive(i, "");
        LongArgs.checkNotPositive(i, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { 1 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(long i) {
        LongArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _checkNegative_Pass_Data() {
        return new Object[][] {
                { -1 },
                { -99 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_Pass_Data")
    public void checkNegative_Pass(long i) {
        LongArgs.checkNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkNegative(i, null);
        LongArgs.checkNegative(i, "");
        LongArgs.checkNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { 0 },
                { 1 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(long i) {
        LongArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _checkNotNegative_Pass_Data() {
        return new Object[][] {
                { 0 },
                { 99 },
                { Long.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(long i) {
        LongArgs.checkNotNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkNotNegative(i, null);
        LongArgs.checkNotNegative(i, "");
        LongArgs.checkNotNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { -1 },
                { Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(long i) {
        LongArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _checkValueRange_Pass_Data() {
        return new Object[][] {
                { 1, -1, 2 },
                { 1, -1, 1 },
                { 1, 0, 1 },
                { 1, 0, 2 },
                { 1, 1, 1 },
                { 1, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkValueRange_Pass_Data")
    public void checkValueRange_Pass(long i, long minValue, long maxValue) {
        LongArgs.checkValueRange(i, minValue, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkValueRange(i, minValue, maxValue, null);
        LongArgs.checkValueRange(i, minValue, maxValue, "");
        LongArgs.checkValueRange(i, minValue, maxValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "_checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            long i, long minValue, long maxValue) {
        LongArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _checkMinValue_Pass_Data() {
        return new Object[][] {
                { 1, -1 },
                { 1, 0 },
                { 1, 1 },
                
                { -1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_checkMinValue_Pass_Data")
    public void checkMinValue_Pass(long i, long minValue) {
        LongArgs.checkMinValue(i, minValue, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkMinValue(i, minValue, null);
        LongArgs.checkMinValue(i, minValue, "");
        LongArgs.checkMinValue(i, minValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1, 2 },
                { 1, 3 },
                { -1, 0 },
                { -1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(long i, long minValue) {
        LongArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _checkMaxValue_Pass_Data() {
        return new Object[][] {
                { 1, 1 },
                { 1, 2 },
                { 1, 3 },
                
                { -1, -1 },
                { -1, 0 },
                { -1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(long i, long maxValue) {
        LongArgs.checkMaxValue(i, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkMaxValue(i, maxValue, null);
        LongArgs.checkMaxValue(i, maxValue, "");
        LongArgs.checkMaxValue(i, maxValue, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1, 0 },
                { 1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(long i, long maxValue) {
        LongArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // LongArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _checkExactValue_Pass_Data() {
        return new Object[][] {
                { 1, 1 },
                { 0, 0 },
                { -1, -1 },
                { Long.MAX_VALUE, Long.MAX_VALUE },
                { Long.MIN_VALUE, Long.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_Pass_Data")
    public void checkExactValue_Pass(long i, long value) {
        LongArgs.checkExactValue(i, value, "i");
        // Demonstrate argName can be anything ridiculous.
        LongArgs.checkExactValue(i, value, null);
        LongArgs.checkExactValue(i, value, "");
        LongArgs.checkExactValue(i, value, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { 1, 0 },
                { 1, -1 },
                { -1, -2 },
                { -1, -3 },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(long i, long value) {
        LongArgs.checkExactValue(i, value, "i");
    }
}
