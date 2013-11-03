package com.googlecode.kevinarpe.papaya.argument;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DateTimeIterableArgsTest {

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkValueInsideRange
    //

    @DataProvider
    public static Object[][] checkValueInsideRange_Pass_Data() {
        return DateTimeArrayArgsTest.checkValueInsideRange_Pass_Data();
    }

    @Test(dataProvider = "checkValueInsideRange_Pass_Data")
    public void checkValueInsideRange_Pass(DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValueRange_FailWithNulls_Data() {
        return DateTimeArrayArgsTest.checkValueRange_FailWithNulls_Data();
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkValueInsideRange_FailWithNulls(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }

    @DataProvider
    public static Object[][] checkValueInsideRange_FailWithInvalidInput_Data() {
        return DateTimeArrayArgsTest.checkValueInsideRange_FailWithInvalidInput_Data();
    }

    @Test(dataProvider = "checkValueInsideRange_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkValueInsideRange_FailWithInvalidInput(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkValueInsideRange(list, minValue, maxValue, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkValueOutsideRange
    //

    @DataProvider
    public static Object[][] checkValueOutsideRange_Pass_Data() {
        return DateTimeArrayArgsTest.checkValueOutsideRange_Pass_Data();
    }

    @Test(dataProvider = "checkValueOutsideRange_Pass_Data")
    public void checkValueOutsideRange_Pass(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueOutsideRange(list, minValue, maxValue, null));
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueOutsideRange(list, minValue, maxValue, ""));
        Assert.assertTrue(
            list == DateTimeIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "   "));
    }

    @Test(dataProvider = "checkValueRange_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkValueOutsideRange_FailWithNulls(
            DateTime[] dtArr, DateTime minValue, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkValueOutsideRange(list, minValue, maxValue, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkMinValue
    //

    @DataProvider
    public static Object[][] checkMinValue_Pass_Data() {
        return DateTimeArrayArgsTest.checkMinValue_Pass_Data();
    }

    @Test(dataProvider = "checkMinValue_Pass_Data")
    public void checkMinValue_Pass(DateTime[] dtArr, DateTime minValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DateTimeIterableArgs.checkMinValue(list, minValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DateTimeIterableArgs.checkMinValue(list, minValue, null));
        Assert.assertTrue(list == DateTimeIterableArgs.checkMinValue(list, minValue, ""));
        Assert.assertTrue(list == DateTimeIterableArgs.checkMinValue(list, minValue, "   "));
    }

    @DataProvider
    public static Object[][] checkValue_FailWithNulls_Data() {
        return DateTimeArrayArgsTest.checkValue_FailWithNulls_Data();
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinValue_FailWithNulls(DateTime[] dtArr, DateTime minValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkMinValue(list, minValue, "list");
    }

    @DataProvider
    public static Object[][] checkMinValue_FailWithInvalidInput_Data() {
        return DateTimeArrayArgsTest.checkMinValue_FailWithInvalidInput_Data();
    }

    @Test(dataProvider = "checkMinValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinValue_FailWithInvalidInput(DateTime[] dtArr, DateTime minValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkMinValue(list, minValue, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkMaxValue
    //

    @DataProvider
    public static Object[][] checkMaxValue_Pass_Data() {
        return DateTimeArrayArgsTest.checkMaxValue_Pass_Data();
    }

    @Test(dataProvider = "checkMaxValue_Pass_Data")
    public void checkMaxValue_Pass(DateTime[] dtArr, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DateTimeIterableArgs.checkMaxValue(list, maxValue, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DateTimeIterableArgs.checkMaxValue(list, maxValue, null));
        Assert.assertTrue(list == DateTimeIterableArgs.checkMaxValue(list, maxValue, ""));
        Assert.assertTrue(list == DateTimeIterableArgs.checkMaxValue(list, maxValue, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxValue_FailWithNulls(DateTime[] dtArr, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkMaxValue(list, maxValue, "list");
    }

    @DataProvider
    public static Object[][] checkMaxValue_FailWithInvalidInput_Data() {
        return DateTimeArrayArgsTest.checkMaxValue_FailWithInvalidInput_Data();
    }

    @Test(dataProvider = "checkMaxValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxValue_FailWithInvalidInput(DateTime[] dtArr, DateTime maxValue) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkMaxValue(list, maxValue, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkExactValue
    //

    @DataProvider
    public static Object[][] checkExactValue_Pass_Data() {
        return DateTimeArrayArgsTest.checkExactValue_Pass_Data();
    }

    @Test(dataProvider = "checkExactValue_Pass_Data")
    public void checkExactValue_Pass(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DateTimeIterableArgs.checkExactValue(list, value, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DateTimeIterableArgs.checkExactValue(list, value, null));
        Assert.assertTrue(list == DateTimeIterableArgs.checkExactValue(list, value, ""));
        Assert.assertTrue(list == DateTimeIterableArgs.checkExactValue(list, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactValue_FailWithNulls(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkExactValue(list, value, "list");
    }

    @DataProvider
    public static Object[][] checkExactValue_FailWithInvalidInput_Data() {
        return DateTimeArrayArgsTest.checkExactValue_FailWithInvalidInput_Data();
    }

    @Test(dataProvider = "checkExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactValue_FailWithInvalidInput(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkExactValue(list, value, "list");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DateTimeIterableArgs.checkNotExactValue
    //

    @DataProvider
    public static Object[][] checkNotExactValue_Pass_Data() {
        return DateTimeArrayArgsTest.checkNotExactValue_Pass_Data();
    }

    @Test(dataProvider = "checkNotExactValue_Pass_Data")
    public void checkNotExactValue_Pass(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(list == DateTimeIterableArgs.checkNotExactValue(list, value, "list"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(list == DateTimeIterableArgs.checkNotExactValue(list, value, null));
        Assert.assertTrue(list == DateTimeIterableArgs.checkNotExactValue(list, value, ""));
        Assert.assertTrue(list == DateTimeIterableArgs.checkNotExactValue(list, value, "   "));
    }

    @Test(dataProvider = "checkValue_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotExactValue_FailWithNulls(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkNotExactValue(list, value, "list");
    }

    @DataProvider
    public static Object[][] checkNotExactValue_FailWithInvalidInput_Data() {
        return DateTimeArrayArgsTest.checkNotExactValue_FailWithInvalidInput_Data();
    }

    @Test(dataProvider = "checkNotExactValue_FailWithInvalidInput_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotExactValue_FailWithInvalidInput(DateTime[] dtArr, DateTime value) {
        List<DateTime> list = (dtArr == null ? null : Arrays.asList(dtArr));
        DateTimeIterableArgs.checkNotExactValue(list, value, "list");
    }
}
