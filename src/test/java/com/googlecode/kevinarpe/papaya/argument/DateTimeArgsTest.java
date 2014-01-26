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

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DateTimeArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
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
    
    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(DateTime dt, DateTime minValue, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValueRange_FailWithNulls_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { null, dt, dt },
            { dt, null, dt },
            { dt, dt, null },

            { null, null, dt },
            { null, dt, null },
            { dt, null, null },

            { null, null, null },
        };
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNulls(
        DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, "dt");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
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
    
    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueInsideRange(dt, minValue, maxValue, "dt");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { dt, dt.minusDays(2), dt.minusDays(1) },
            { dt, dt.minusDays(1), dt.minusDays(1) },
            { dt, dt.plusDays(1), dt.plusDays(1) },
            { dt, dt.minusDays(3), dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(DateTime dt, DateTime minValue, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, "   "));
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNulls(
            DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, "dt");
    }

    @DataProvider
    public static Object[][] checkValueOutsideRange_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { dt, dt.minusDays(2), dt.plusDays(1) },
            { dt, dt.minusDays(2), dt },
            { dt, dt.minusDays(1), dt },
            { dt, dt.minusDays(1), dt.plusDays(1) },
            { dt, dt, dt },
            { dt, dt, dt.plusDays(1) },

            { dt.minusDays(2), dt, dt.minusDays(1) },
            { dt.minusDays(2), dt.minusDays(2), dt.minusDays(3) },
            { dt.minusDays(2), dt.plusDays(1), dt },
        };
    }

    @Test(dataProvider = "checkValueOutsideRange_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkValueOutsideRange_FailWithInvalidInput(
            DateTime dt, DateTime minValue, DateTime maxValue) {
        DateTimeArgs.checkValueOutsideRange(dt, minValue, maxValue, "dt");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMinValue
    //
    
    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
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
    
    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(DateTime dt, DateTime minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkMinValue(dt, minValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValue_FailWithNulls_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { null, dt },
            { dt, null },
            { null, null },
        };
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNulls(DateTime dt, DateTime minValue) {
        DateTimeArgs.checkMinValue(dt, minValue, "dt");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.plusDays(1) },
                { dt, dt.plusDays(2) },
                { dt.minusDays(2), dt.minusDays(1) },
                { dt.minusDays(2), dt },
        };
    }
    
    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(DateTime dt, DateTime minValue) {
        DateTimeArgs.checkMinValue(dt, minValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkMaxValue
    //
    
    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
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
    
    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(DateTime dt, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, null));
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkMaxValue(dt, maxValue, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNulls(DateTime dt, DateTime maxValue) {
        DateTimeArgs.checkMaxValue(dt, maxValue, "dt");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(DateTime dt, DateTime maxValue) {
        DateTimeArgs.checkMaxValue(dt, maxValue, "dt");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(2) },
        };
    }
    
    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(DateTime dt, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, null));
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkExactValue(dt, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNulls(DateTime dt, DateTime value) {
        DateTimeArgs.checkExactValue(dt, value, "dt");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }
    
    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(DateTime dt, DateTime value) {
        DateTimeArgs.checkExactValue(dt, value, "dt");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt.minusDays(1) },
                { dt, dt.minusDays(2) },
                { dt.minusDays(2), dt.minusDays(3) },
                { dt.minusDays(2), dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(DateTime dt, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, "dt"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, null));
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, ""));
        Assert.assertTrue(dt == DateTimeArgs.checkNotExactValue(dt, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNulls(DateTime dt, DateTime value) {
        DateTimeArgs.checkNotExactValue(dt, value, "dt");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
                { dt, dt },
                { dt.minusDays(1), dt.minusDays(1) },
                { dt.minusDays(2), dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(DateTime dt, DateTime value) {
        DateTimeArgs.checkNotExactValue(dt, value, "dt");
    }
}
