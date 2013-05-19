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

import com.googlecode.kevinarpe.papaya.args.IntArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class IntArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkPositive
    //

    @DataProvider
    private static final Object[][] _checkPositive_Pass_Data() {
        return new Object[][] {
                { 1 },
                { 99 },
                { Integer.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkPositive_Pass_Data")
    public void checkPositive_Pass(int i) {
        IntArgs.checkPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkPositive(i, null);
        IntArgs.checkPositive(i, "");
        IntArgs.checkPositive(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { 0 },
                { -1 },
                { Integer.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(int i) {
        IntArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkNotPositive
    //

    @DataProvider
    private static final Object[][] _checkNotPositive_Pass_Data() {
        return new Object[][] {
                { 0 },
                { -99 },
                { Integer.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(int i) {
        IntArgs.checkNotPositive(i, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkNotPositive(i, null);
        IntArgs.checkNotPositive(i, "");
        IntArgs.checkNotPositive(i, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { 1 },
                { Integer.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(int i) {
        IntArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkNegative
    //

    @DataProvider
    private static final Object[][] _checkNegative_Pass_Data() {
        return new Object[][] {
                { -1 },
                { -99 },
                { Integer.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_Pass_Data")
    public void checkNegative_Pass(int i) {
        IntArgs.checkNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkNegative(i, null);
        IntArgs.checkNegative(i, "");
        IntArgs.checkNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { 0 },
                { 1 },
                { Integer.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(int i) {
        IntArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkNotNegative
    //

    @DataProvider
    private static final Object[][] _checkNotNegative_Pass_Data() {
        return new Object[][] {
                { 0 },
                { 99 },
                { Integer.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(int i) {
        IntArgs.checkNotNegative(i, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkNotNegative(i, null);
        IntArgs.checkNotNegative(i, "");
        IntArgs.checkNotNegative(i, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { -1 },
                { Integer.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(int i) {
        IntArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkValueRange
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
    public void checkValueRange_Pass(int i, int minValue, int maxValue) {
        IntArgs.checkValueRange(i, minValue, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkValueRange(i, minValue, maxValue, null);
        IntArgs.checkValueRange(i, minValue, maxValue, "");
        IntArgs.checkValueRange(i, minValue, maxValue, "   ");
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
            int i, int minValue, int maxValue) {
        IntArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkMinValue
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
    public void checkMinValue_Pass(int i, int minValue) {
        IntArgs.checkMinValue(i, minValue, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkMinValue(i, minValue, null);
        IntArgs.checkMinValue(i, minValue, "");
        IntArgs.checkMinValue(i, minValue, "   ");
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
    public void checkMinValue_FailWithInvalidInput(int i, int minValue) {
        IntArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkMaxValue
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
    public void checkMaxValue_Pass(int i, int maxValue) {
        IntArgs.checkMaxValue(i, maxValue, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkMaxValue(i, maxValue, null);
        IntArgs.checkMaxValue(i, maxValue, "");
        IntArgs.checkMaxValue(i, maxValue, "   ");
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
    public void checkMaxValue_FailWithInvalidInput(int i, int maxValue) {
        IntArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // IntArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _checkExactValue_Pass_Data() {
        return new Object[][] {
                { 1, 1 },
                { 0, 0 },
                { -1, -1 },
                { Integer.MAX_VALUE, Integer.MAX_VALUE },
                { Integer.MIN_VALUE, Integer.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_Pass_Data")
    public void checkExactValue_Pass(int i, int value) {
        IntArgs.checkExactValue(i, value, "i");
        // Demonstrate argName can be anything ridiculous.
        IntArgs.checkExactValue(i, value, null);
        IntArgs.checkExactValue(i, value, "");
        IntArgs.checkExactValue(i, value, "   ");
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
    public void checkExactValue_FailWithInvalidInput(int i, int value) {
        IntArgs.checkExactValue(i, value, "i");
    }
}
