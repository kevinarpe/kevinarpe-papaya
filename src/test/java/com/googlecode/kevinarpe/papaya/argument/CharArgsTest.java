package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.argument.CharArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class CharArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkValueRange
    //

    @DataProvider
    public static Object[][] checkValueRange_Pass_Data() {
        return new Object[][] {
                { (char) 2, (char) 0, (char) 3 },
                { (char) 2, (char) 0, (char) 2 },
                { (char) 1, (char) 0, (char) 1 },
                { (char) 1, (char) 0, (char) 2 },
                { (char) 1, (char) 1, (char) 1 },
                { (char) 1, (char) 1, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkValueRange_Pass_Data")
    public void checkValueRange_Pass(char i, char minValue, char maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == CharArgs.checkValueRange(i, minValue, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == CharArgs.checkValueRange(i, minValue, maxValue, null));
        Assert.assertTrue(i == CharArgs.checkValueRange(i, minValue, maxValue, ""));
        Assert.assertTrue(i == CharArgs.checkValueRange(i, minValue, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkValueRange_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (char) 2, (char) 0, (char) 1 },
                { (char) 1, (char) 0, (char) 0 },
                { (char) 1, (char) 2, (char) 2 },
                { (char) 1, (char) 2, (char) 1 },
                { (char) 3, (char) 1, (char) 0 },
                { (char) 3, (char) 0, (char) 1 },
                
                { (char) 1, (char) 3, (char) 2 },
                { (char) 1, (char) 2, (char) 2 },
                { (char) 1, (char) 0, (char) 0 },
                { (char) 1, (char) 1, (char) 0 },
                { (char) 1, (char) 3, (char) 4 },
                { (char) 1, (char) 4, (char) 3 },
        };
    }
    
    @Test(dataProvider = "checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            char i, char minValue, char maxValue) {
        CharArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return new Object[][] {
                { (char) 2, (char) 0 },
                { (char) 1, (char) 0 },
                { (char) 1, (char) 1 },
                
                { (char) 2, (char) 2 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(char i, char minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == CharArgs.checkMinValue(i, minValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == CharArgs.checkMinValue(i, minValue, null));
        Assert.assertTrue(i == CharArgs.checkMinValue(i, minValue, ""));
        Assert.assertTrue(i == CharArgs.checkMinValue(i, minValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (char) 1, (char) 2 },
                { (char) 1, (char) 3 },
                { (char) 0, (char) 1 },
                { (char) 0, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(char i, char minValue) {
        CharArgs.checkMinValue(i, minValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return new Object[][] {
                { (char) 1, (char) 1 },
                { (char) 1, (char) 2 },
                { (char) 1, (char) 3 },
                
                { (char) 0, (char) 0 },
                { (char) 0, (char) 1 },
                { (char) 0, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(char i, char maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == CharArgs.checkMaxValue(i, maxValue, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == CharArgs.checkMaxValue(i, maxValue, null));
        Assert.assertTrue(i == CharArgs.checkMaxValue(i, maxValue, ""));
        Assert.assertTrue(i == CharArgs.checkMaxValue(i, maxValue, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(char i, char maxValue) {
        CharArgs.checkMaxValue(i, maxValue, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return new Object[][] {
                { (char) 1, (char) 1 },
                { (char) 0, (char) 0 },
                { (char) 2, (char) 2 },
                { Character.MAX_VALUE, Character.MAX_VALUE },
                { Character.MIN_VALUE, Character.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(char i, char value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == CharArgs.checkExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == CharArgs.checkExactValue(i, value, null));
        Assert.assertTrue(i == CharArgs.checkExactValue(i, value, ""));
        Assert.assertTrue(i == CharArgs.checkExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(char i, char value) {
        CharArgs.checkExactValue(i, value, "i");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return new Object[][] {
                { (char) -1, (char) 0 },
                { (char) 0, (char) 1 },
                { (char) 2, (char) 3 },
                { Character.MAX_VALUE, Character.MIN_VALUE },
                { Character.MIN_VALUE, Character.MAX_VALUE },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(char i, char value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(i == CharArgs.checkNotExactValue(i, value, "i"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(i == CharArgs.checkNotExactValue(i, value, null));
        Assert.assertTrue(i == CharArgs.checkNotExactValue(i, value, ""));
        Assert.assertTrue(i == CharArgs.checkNotExactValue(i, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return new Object[][] {
                { (char) -1, (char) -1 },
                { (char) 0, (char) 0 },
                { (char) 1, (char) 1 },
                { (char) 2, (char) 2 },
        };
    }
    
    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(char i, char value) {
        CharArgs.checkNotExactValue(i, value, "i");
    }
}
