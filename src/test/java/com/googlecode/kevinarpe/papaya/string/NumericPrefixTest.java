package com.googlecode.kevinarpe.papaya.string;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumericPrefixTest {

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.ctor
    //

    @DataProvider
    private static Object[][] Pass_Data() {
        return new Object[][] {
            { String.valueOf(Long.MAX_VALUE), Long.MAX_VALUE },
            { String.valueOf(Long.MIN_VALUE), Long.MIN_VALUE },
            { String.valueOf(Long.MAX_VALUE) + "abc", Long.MAX_VALUE },
            { String.valueOf(Long.MIN_VALUE) + "abc", Long.MIN_VALUE },
            { "123", 123L },
            { "+123", 123L },
            { "-123", -123L },
            { "123abc", 123L },
            { "+123abc", 123L },
            { "-123abc", -123L },
            { "123.abc", 123L },
            { "+123.abc", 123L },
            { "-123.abc", -123L },
            { "123.456abc", 123L },
            { "+123.456abc", 123L },
            { "-123.456abc", -123L },
            { "123,456abc", 123L },
            { "+123,456abc", 123L },
            { "-123,456abc", -123L },
            { "", null },
            { "   ", null },
            { "abc", null },
        };
    }

    @Test(dataProvider = "Pass_Data")
    public void ctor_Pass(String str, Long expectedValue) {
        new NumericPrefix<String>(str);
    }

    private <TString extends CharSequence> void core_ctor_Pass(TString str, Long expectedValue) {
        NumericPrefix<TString> x = new NumericPrefix<TString>(str);
        Assert.assertEquals(null != expectedValue, x.hasNumericValue());
        if (null != expectedValue) {
            Assert.assertEquals(expectedValue.longValue(), x.getNumericValue());
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new NumericPrefix<String>(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull2() {
        new NumericPrefix<CharSequence>(null);
    }
    @DataProvider
    private static Object[][] ctor_FailWithOutOfRangeNumber_Data() {
        return new Object[][] {
            { String.valueOf(Long.MAX_VALUE) + "123" },
            { String.valueOf(Long.MAX_VALUE) + "123abc" },
            { String.valueOf(Long.MIN_VALUE) + "123" },
            { String.valueOf(Long.MIN_VALUE) + "123abc" },
        };
    }

    @Test(expectedExceptions = NumberFormatException.class,
            dataProvider = "ctor_FailWithOutOfRangeNumber_Data")
    public void ctor_FailWithOutOfRangeNumber(String str) {
        new NumericPrefix<String>(str);
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.getInput()
    //

    @Test(dataProvider = "Pass_Data")
    public void getInput_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        Assert.assertEquals(str, x.getInput());
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.hasNumericValue()
    //

    @Test(dataProvider = "Pass_Data")
    public void hasNumericValue_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        Assert.assertEquals(null != expectedValue, x.hasNumericValue());
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.getNumericValue()
    //

    @Test(dataProvider = "Pass_Data")
    public void getNumericValue_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        if (null == expectedValue) {
            try {
                x.getNumericValue();
            }
            catch (IllegalStateException e) {
                // Ignore
            }
        }
        else {
            Assert.assertEquals(expectedValue.longValue(), x.getNumericValue());
        }
    }
}
