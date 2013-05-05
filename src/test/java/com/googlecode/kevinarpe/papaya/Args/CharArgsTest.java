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

import com.googlecode.kevinarpe.papaya.args.CharArgs;

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
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        return new Object[][] {
                { (char) 2, (char) 0, (char) 3 },
                { (char) 2, (char) 0, (char) 2 },
                { (char) 1, (char) 0, (char) 1 },
                { (char) 1, (char) 0, (char) 2 },
                { (char) 1, (char) 1, (char) 1 },
                { (char) 1, (char) 1, (char) 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(char i, char minValue, char maxValue) {
        CharArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            char i, char minValue, char maxValue) {
        CharArgs.checkValueRange(i, minValue, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            char i, char minValue, char maxValue) {
        CharArgs.checkValueRange(i, minValue, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { (char) 2, (char) 0, (char) 1, "" },
                { (char) 1, (char) 0, (char) 0, "   " },  // ASCII spaces
                { (char) 1, (char) 2, (char) 2, "　　　" },  // wide Japanese spaces
                { (char) 1, (char) 2, (char) 1, "" },
                { (char) 3, (char) 1, (char) 0, "   " },  // ASCII spaces
                { (char) 3, (char) 0, (char) 1, "　　　" },  // wide Japanese spaces
                
                { (char) 1, (char) 3, (char) 2, "" },
                { (char) 1, (char) 2, (char) 2, "   " },  // ASCII spaces
                { (char) 1, (char) 0, (char) 0, "　　　" },  // wide Japanese spaces
                { (char) 1, (char) 1, (char) 0, "" },
                { (char) 1, (char) 3, (char) 4, "   " },  // ASCII spaces
                { (char) 1, (char) 4, (char) 3, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidArgName(
            char i, char minValue, char maxValue, String argName) {
        CharArgs.checkValueRange(i, minValue, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkMinValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        return new Object[][] {
                { (char) 2, (char) 0 },
                { (char) 1, (char) 0 },
                { (char) 1, (char) 1 },
                
                { (char) 2, (char) 2 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(char i, char minValue) {
        CharArgs.checkMinValue(i, minValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (char) 1, (char) 2 },
                { (char) 1, (char) 3 },
                { (char) 0, (char) 1 },
                { (char) 0, (char) 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(char i, char minValue) {
        CharArgs.checkMinValue(i, minValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(char i, char minValue) {
        CharArgs.checkMinValue(i, minValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (char) 1, (char) 2, "" },
                { (char) 1, (char) 3, "   " },  // ASCII spaces
                { (char) 0, (char) 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidArgName(
    		char i, char minValue, String argName) {
        CharArgs.checkMinValue(i, minValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkMaxValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        return new Object[][] {
                { (char) 1, (char) 1 },
                { (char) 1, (char) 2 },
                { (char) 1, (char) 3 },
                
                { (char) 0, (char) 0 },
                { (char) 0, (char) 1 },
                { (char) 0, (char) 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(char i, char maxValue) {
        CharArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(char i, char maxValue) {
        CharArgs.checkMaxValue(i, maxValue, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(char i, char maxValue) {
        CharArgs.checkMaxValue(i, maxValue, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (char) 2, (char) 1, "" },
                { (char) 2, (char) 0, "   " },  // ASCII spaces
                { (char) 2, (char) 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidArgName(
    		char i, char maxValue, String argName) {
        CharArgs.checkMaxValue(i, maxValue, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CharArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        return new Object[][] {
                { (char) 1, (char) 1 },
                { (char) 0, (char) 0 },
                { (char) 2, (char) 2 },
                { Character.MAX_VALUE, Character.MAX_VALUE },
                { Character.MIN_VALUE, Character.MIN_VALUE },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(char i, char value) {
        CharArgs.checkExactValue(i, value, "i");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        return new Object[][] {
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
                { (char) 2, (char) 1 },
                { (char) 2, (char) 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(char i, char value) {
        CharArgs.checkExactValue(i, value, "i");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(char i, char value) {
        CharArgs.checkExactValue(i, value, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidArgName() {
        return new Object[][] {
                { (char) 2, (char) 1, "" },
                { (char) 2, (char) 0, "   " },  // ASCII spaces
                { (char) 2, (char) 1, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidArgName(
    		char i, char value, String argName) {
        CharArgs.checkExactValue(i, value, argName);
    }
}
