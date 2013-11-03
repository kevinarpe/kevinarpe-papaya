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

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.argument.ShortArgs;

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
    public static Object[][] checkPositive_Pass_Data() {
        return new Object[][] {
                { (short) 1 },
                { (short) 99 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_Pass_Data")
    public void checkPositive_Pass(short i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkPositive(i, null));
        Assert.assertTrue(i == ShortArgs.checkPositive(i, ""));
        Assert.assertTrue(i == ShortArgs.checkPositive(i, "   "));
    }

    @DataProvider
    public static Object[][] checkPositive_FailWithNonPositiveInput_Data() {
        return new Object[][] {
                { (short) 0 },
                { (short) -1 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkPositive_FailWithNonPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkPositive_FailWithNonPositiveInput(short i) {
        ShortArgs.checkPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNotPositive
    //

    @DataProvider
    public static Object[][] checkNotPositive_Pass_Data() {
        return new Object[][] {
                { (short) 0 },
                { (short) -99 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_Pass_Data")
    public void checkNotPositive_Pass(short i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkNotPositive(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkNotPositive(i, null));
        Assert.assertTrue(i == ShortArgs.checkNotPositive(i, ""));
        Assert.assertTrue(i == ShortArgs.checkNotPositive(i, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotPositive_FailWithPositiveInput_Data() {
        return new Object[][] {
                { (short) 1 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotPositive_FailWithPositiveInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotPositive_FailWithPositiveInput(short i) {
        ShortArgs.checkNotPositive(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNegative
    //

    @DataProvider
    public static Object[][] checkNegative_Pass_Data() {
        return new Object[][] {
                { (short) -1 },
                { (short) -99 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_Pass_Data")
    public void checkNegative_Pass(short i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkNegative(i, null));
        Assert.assertTrue(i == ShortArgs.checkNegative(i, ""));
        Assert.assertTrue(i == ShortArgs.checkNegative(i, "   "));
    }

    @DataProvider
    public static Object[][] checkNegative_FailWithNonNegativeInput_Data() {
        return new Object[][] {
                { (short) 0 },
                { (short) 1 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNegative_FailWithNonNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNegative_FailWithNonNegativeInput(short i) {
        ShortArgs.checkNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNotNegative
    //

    @DataProvider
    public static Object[][] checkNotNegative_Pass_Data() {
        return new Object[][] {
                { (short) 0 },
                { (short) 99 },
                { Short.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_Pass_Data")
    public void checkNotNegative_Pass(short i) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkNotNegative(i, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkNotNegative(i, null));
        Assert.assertTrue(i == ShortArgs.checkNotNegative(i, ""));
        Assert.assertTrue(i == ShortArgs.checkNotNegative(i, "   "));
    }

    @DataProvider
    public static Object[][] checkNotNegative_FailWithNegativeInput_Data() {
        return new Object[][] {
                { (short) -1 },
                { Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotNegative_FailWithNegativeInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotNegative_FailWithNegativeInput(short i) {
        ShortArgs.checkNotNegative(i, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkValueRange
    //

    @DataProvider
    public static Object[][] checkValueRange_Pass_Data() {
        return new Object[][] {
                { (short) 1, (short) -1, (short) 2 },
                { (short) 1, (short) -1, (short) 1 },
                { (short) 1, (short) 0, (short) 1 },
                { (short) 1, (short) 0, (short) 2 },
                { (short) 1, (short) 1, (short) 1 },
                { (short) 1, (short) 1, (short) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueRange_Pass_Data")
    public void checkValueRange_Pass(short i, short minValue, short maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkValueRange(i, minValue, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkValueRange(i, minValue, maxValue, null));
        Assert.assertTrue(i == ShortArgs.checkValueRange(i, minValue, maxValue, ""));
        Assert.assertTrue(i == ShortArgs.checkValueRange(i, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            short i, short minValue, short maxValue) {
        ShortArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { (short) 1, (short) -1 },
                { (short) 1, (short) 0 },
                { (short) 1, (short) 1 },
                
                { (short) -1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(short i, short minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkMinValue(i, minValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkMinValue(i, minValue, null));
        Assert.assertTrue(i == ShortArgs.checkMinValue(i, minValue, ""));
        Assert.assertTrue(i == ShortArgs.checkMinValue(i, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (short) 1, (short) 2 },
                { (short) 1, (short) 3 },
                { (short) -1, (short) 0 },
                { (short) -1, (short) 1 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(short i, short minValue) {
        ShortArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { (short) 1, (short) 1 },
                { (short) 1, (short) 2 },
                { (short) 1, (short) 3 },
                
                { (short) -1, (short) -1 },
                { (short) -1, (short) 0 },
                { (short) -1, (short) 1 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(short i, short maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkMaxValue(i, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkMaxValue(i, maxValue, null));
        Assert.assertTrue(i == ShortArgs.checkMaxValue(i, maxValue, ""));
        Assert.assertTrue(i == ShortArgs.checkMaxValue(i, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (short) 1, (short) 0 },
                { (short) 1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(short i, short maxValue) {
        ShortArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
                { (short) 1, (short) 1 },
                { (short) 0, (short) 0 },
                { (short) -1, (short) -1 },
                { Short.MAX_VALUE, Short.MAX_VALUE },
                { Short.MIN_VALUE, Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(short i, short value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkExactValue(i, value, null));
        Assert.assertTrue(i == ShortArgs.checkExactValue(i, value, ""));
        Assert.assertTrue(i == ShortArgs.checkExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (short) 1, (short) 0 },
                { (short) 1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(short i, short value) {
        ShortArgs.checkExactValue(i, value, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ShortArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
                { (short) 1, (short) 0 },
                { (short) 1, (short) -1 },
                { (short) -1, (short) -2 },
                { (short) -1, (short) -3 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(short i, short value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == ShortArgs.checkNotExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == ShortArgs.checkNotExactValue(i, value, null));
        Assert.assertTrue(i == ShortArgs.checkNotExactValue(i, value, ""));
        Assert.assertTrue(i == ShortArgs.checkNotExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (short) 1, (short) 1 },
                { (short) 0, (short) 0 },
                { (short) -1, (short) -1 },
                { Short.MAX_VALUE, Short.MAX_VALUE },
                { Short.MIN_VALUE, Short.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(short i, short value) {
        ShortArgs.checkNotExactValue(i, value, "i");
    }
}
