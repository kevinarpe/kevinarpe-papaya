package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StringFormatterTest {

    private StringFormatter classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new StringFormatter("[%s]");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.ctor(String)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new StringFormatter((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithEmpty() {
        new StringFormatter("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithOnlyWhitespace() {
        new StringFormatter("   ");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.format(Object)
    //

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        return new Object[][] {
            { null, "[null]" },
            { "", "[]" },
            { "abc", "[abc]" },
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(Object value, String expected) {
        Assert.assertEquals(classUnderTest.format(value), expected);
    }
}
