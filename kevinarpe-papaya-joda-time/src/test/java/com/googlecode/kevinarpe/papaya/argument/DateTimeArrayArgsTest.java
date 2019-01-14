package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

public class DateTimeArrayArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(2), dt.plusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(2), dt },
            { new DateTime[] { dt }, dt.minusDays(1), dt },
            { new DateTime[] { dt }, dt.minusDays(1), dt.plusDays(1) },
            { new DateTime[] { dt }, dt, dt },
            { new DateTime[] { dt }, dt, dt.plusDays(1) },
        };
    }

    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, null));
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, ""));
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValueRange_FailWithNulls_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { null, dt, dt },
            { new DateTime[] { null }, dt, dt },
            { new DateTime[] { null, dt }, dt, dt },
            { new DateTime[] { dt, null }, dt, dt },
            { new DateTime[] { dt }, null, dt },
            { new DateTime[] { dt }, dt, null },

            { null, null, dt },
            { new DateTime[] { null }, null, dt },
            { new DateTime[] { null, dt }, null, dt },
            { new DateTime[] { dt, null }, null, dt },

            { null, dt, null },
            { new DateTime[] { null }, dt, null },
            { new DateTime[] { null, dt }, dt, null },
            { new DateTime[] { dt, null }, dt, null },

            { new DateTime[] { dt }, null, null },

            { null, null, null },
            { new DateTime[] { null }, null, null },
            { new DateTime[] { null, dt }, null, null },
            { new DateTime[] { dt, null }, null, null },
        };
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNulls(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, "dtArr");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(2), dt.minusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(1), dt.minusDays(1) },
            { new DateTime[] { dt }, dt.plusDays(1), dt.plusDays(1) },
            { new DateTime[] { dt }, dt.plusDays(1), dt },
            { new DateTime[] { dt }, dt.minusDays(2), dt.minusDays(3) },
            { new DateTime[] { dt }, dt.minusDays(3), dt.minusDays(2) },

            { new DateTime[] { dt.minusDays(2) }, dt, dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(1), dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(3), dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(2), dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt, dt.plusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt.plusDays(1), dt },
        };
    }

    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        DateTimeArrayArgs.checkValueInsideRange(dtArr, minValue, maxValue, "dtArr");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(2), dt.minusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(1), dt.minusDays(1) },
            { new DateTime[] { dt }, dt.plusDays(1), dt.plusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(3), dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueOutsideRange(dtArr, minValue, maxValue, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueOutsideRange(dtArr, minValue, maxValue, null));
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueOutsideRange(dtArr, minValue, maxValue, ""));
        Assert.assertTrue(
            dtArr == DateTimeArrayArgs.checkValueOutsideRange(dtArr, minValue, maxValue, "   "));
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNulls(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        DateTimeArrayArgs.checkValueOutsideRange(dtArr, minValue, maxValue, "dtArr");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(2) },
            { new DateTime[] { dt }, dt.minusDays(1) },
            { new DateTime[] { dt }, dt },

            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(DateTime[] dtArr, DateTime minValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMinValue(dtArr, minValue, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMinValue(dtArr, minValue, null));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMinValue(dtArr, minValue, ""));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMinValue(dtArr, minValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValue_FailWithNulls_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { null, dt },
            { new DateTime[] { null }, dt },
            { new DateTime[] { null, dt }, dt },
            { new DateTime[] { dt, null }, dt },

            { new DateTime[] { dt }, null },

            { null, null },
            { new DateTime[] { null }, null },
            { new DateTime[] { null, dt }, null },
            { new DateTime[] { dt, null }, null },
        };
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNulls(DateTime[] dtArr, DateTime minValue) {
        DateTimeArrayArgs.checkMinValue(dtArr, minValue, "dtArr");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.plusDays(1) },
            { new DateTime[] { dt }, dt.plusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt },
        };
    }

    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(DateTime[] dtArr, DateTime minValue) {
        DateTimeArrayArgs.checkMinValue(dtArr, minValue, "dtArr");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt },
            { new DateTime[] { dt }, dt.plusDays(1) },
            { new DateTime[] { dt }, dt.plusDays(2) },

            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt },
        };
    }

    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(DateTime[] dtArr, DateTime maxValue) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, null));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, ""));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNulls(DateTime[] dtArr, DateTime maxValue) {
        DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, "dtArr");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(DateTime[] dtArr, DateTime maxValue) {
        DateTimeArrayArgs.checkMaxValue(dtArr, maxValue, "dtArr");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt },
            { new DateTime[] { dt.minusDays(1) }, dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(DateTime[] dtArr, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkExactValue(dtArr, value, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkExactValue(dtArr, value, null));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkExactValue(dtArr, value, ""));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkExactValue(dtArr, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNulls(DateTime[] dtArr, DateTime value) {
        DateTimeArrayArgs.checkExactValue(dtArr, value, "dtArr");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(DateTime[] dtArr, DateTime value) {
        DateTimeArrayArgs.checkExactValue(dtArr, value, "dtArr");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeArrayArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt.minusDays(1) },
            { new DateTime[] { dt }, dt.minusDays(2) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(3) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(4) },
        };
    }

    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(DateTime[] dtArr, DateTime value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkNotExactValue(dtArr, value, "dtArr"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkNotExactValue(dtArr, value, null));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkNotExactValue(dtArr, value, ""));
        Assert.assertTrue(dtArr == DateTimeArrayArgs.checkNotExactValue(dtArr, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
        expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNulls(DateTime[] dtArr, DateTime value) {
        DateTimeArrayArgs.checkNotExactValue(dtArr, value, "dtArr");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        DateTime dt = new DateTime();
        return new Object[][] {
            { new DateTime[] { dt }, dt },
            { new DateTime[] { dt.minusDays(1) }, dt.minusDays(1) },
            { new DateTime[] { dt.minusDays(2) }, dt.minusDays(2) },
        };
    }

    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
        expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(DateTime[] dtArr, DateTime value) {
        DateTimeArrayArgs.checkNotExactValue(dtArr, value, "dtArr");
    }
}
