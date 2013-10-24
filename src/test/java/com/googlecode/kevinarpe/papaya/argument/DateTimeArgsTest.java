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

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.argument.DateTimeArgs;

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
    private static final Object[][] _checkValueRange_Pass_Data() {
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
    
    @Test(dataProvider = "_checkValueRange_Pass_Data")
    public void checkValueRange_Pass(DateTime dt, DateTime minValue, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkValueRange(dt, minValue, maxValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkValueRange(dt, minValue, maxValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkValueRange(dt, minValue, maxValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkValueRange(dt, minValue, maxValue, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkValueRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "_checkValueRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueRange_FailWithInvalidInput(
            DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueRange(dt, minValue, maxValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMinValue
    //
    
    @DataProvider
    private static final Object[][] _checkMinValue_Pass_Data() {
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
    
    @Test(dataProvider = "_checkMinValue_Pass_Data")
    public void checkMinValue_Pass(DateTime dt, DateTime minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1) },
                { dt, dt.plusDays(2) },
                { dt.minusDays(2), dt.minusDays(1) },
                { dt.minusDays(2), dt },
        };
    }
    
    @Test(dataProvider = "_checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(DateTime dt, DateTime minValue) {
        DateTimeArgs.checkMinValue(dt, minValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMaxValue
    //
    
    @DataProvider
    private static final Object[][] _checkMaxValue_Pass_Data() {
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
    
    @Test(dataProvider = "_checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(DateTime dt, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "_checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(DateTime dt, DateTime maxValue) {
        DateTimeArgs.checkMaxValue(dt, maxValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkExactValue
    //

    @DataProvider
    private static final Object[][] _checkExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(2) },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_Pass_Data")
    public void checkExactValue_Pass(DateTime dt, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, null));
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "_checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(DateTime dt, DateTime value) {
        DateTimeArgs.checkExactValue(dt, value, "dt");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkNotExactValue
    //

    @DataProvider
    private static final Object[][] _checkNotExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "_checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(DateTime dt, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, null));
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, "   "));
    }

    @DataProvider
    private static final Object[][] _checkNotExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "_checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(DateTime dt, DateTime value) {
        DateTimeArgs.checkNotExactValue(dt, value, "dt");
    }
}
