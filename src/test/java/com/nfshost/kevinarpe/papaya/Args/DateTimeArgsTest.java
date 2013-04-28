package com.nfshost.kevinarpe.papaya.Args;

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

import org.joda.time.DateTime;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.Args.DateTimeArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DateTimeArgsTest {
    
    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkValueRange
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckValueRangeAsValid() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(2), dt.plusDays(1) },
                { dt, dt.minusDays(2), dt },
                { dt, dt.minusDays(1), dt },
                { dt, dt.minusDays(1), dt.plusDays(1) },
                { dt, dt, dt },
                { dt, dt, dt.plusDays(1) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckValueRangeAsValid")
    public void shouldCheckValueRangeAsValid(DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueRange(dt, minValue, maxValue, "dt");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithNullArgName() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1), dt, null },
                { dt, dt.plusDays(1), dt.plusDays(2), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckValueRangeAsValidWithNullArgName(
            DateTime dt, DateTime minValue, DateTime maxValue, String argName) {
        DateTimeArgs.checkValueRange(dt, minValue, maxValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckValueRangeAsValidWithInvalidInput() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(2), dt.minusDays(1) },
                { dt, dt.minusDays(1), dt.minusDays(1) },
                { dt, dt.plusDays(1), dt.plusDays(1) },
                { dt, dt.plusDays(1), dt },
                { dt, dt.minusDays(2), dt.minusDays(3) },
                { dt, dt.minusDays(3), dt.minusDays(2) },
                
                { dt.minusDays(2), dt, dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(3), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt, dt.plusDays(1) },
                { dt.minusDays(2), dt.plusDays(1), dt },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckValueRangeAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckValueRangeAsValidWithInvalidInput(
            DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueRange(dt, minValue, maxValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMinValue
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckMinValueAsValid() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(2) },
                { dt, dt.minusDays(1) },
                { dt, dt },
                
                { dt.minusDays(2), dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinValueAsValid")
    public void shouldCheckMinValueAsValid(DateTime dt, DateTime minValue) {
        DateTimeArgs.checkMinValue(dt, minValue, "dt");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithNullArgName() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinValueAsValidWithNullArgName(
            DateTime dt, DateTime minValue, String argName) {
        DateTimeArgs.checkMinValue(dt, minValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinValueAsValidWithInvalidInput() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1) },
                { dt, dt.plusDays(2) },
                { dt.minusDays(2), dt.minusDays(1) },
                { dt.minusDays(2), dt },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinValueAsValidWithInvalidInput(DateTime dt, DateTime minValue) {
        DateTimeArgs.checkMinValue(dt, minValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMaxValue
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxValueAsValid() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt, dt.plusDays(1) },
                { dt, dt.plusDays(2) },
                
                { dt.minusDays(2), dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(1) },
                { dt.minusDays(2), dt },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxValueAsValid")
    public void shouldCheckMaxValueAsValid(DateTime dt, DateTime maxValue) {
        DateTimeArgs.checkMaxValue(dt, maxValue, "dt");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithNullArgName() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxValueAsValidWithNullArgName(
            DateTime dt, DateTime maxValue, String argName) {
        DateTimeArgs.checkMaxValue(dt, maxValue, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxValueAsValidWithInvalidInput() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxValueAsValidWithInvalidInput(DateTime dt, DateTime maxValue) {
        DateTimeArgs.checkMaxValue(dt, maxValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactValueAsValid() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(2) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactValueAsValid")
    public void shouldCheckExactValueAsValid(DateTime dt, DateTime value) {
        DateTimeArgs.checkExactValue(dt, value, "dt");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithNullArgName() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactValueAsValidWithNullArgName(
            DateTime dt, DateTime value, String argName) {
        DateTimeArgs.checkExactValue(dt, value, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactValueAsValidWithInvalidInput() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactValueAsValidWithInvalidInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactValueAsValidWithInvalidInput(DateTime dt, DateTime value) {
        DateTimeArgs.checkMaxValue(dt, value, "dt");
    }
}
