package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CountingStringFormatterTest {

    private CountingStringFormatter classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = CountingStringFormatter.withDefaultFirstCount("%d: [%s]");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.withDefaultFirstCount(String)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void withDefaultFirstCount_FailWithNull() {
        CountingStringFormatter.withDefaultFirstCount((String) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void withDefaultFirstCount_FailWithEmpty() {
        CountingStringFormatter.withDefaultFirstCount("");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void withDefaultFirstCount_FailWithOnlyWhitespace() {
        CountingStringFormatter.withDefaultFirstCount("   ");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.withFirstCount(String)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void withFirstCount_FailWithNull() {
        CountingStringFormatter.withFirstCount((String) null, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void withFirstCount_FailWithEmpty() {
        CountingStringFormatter.withFirstCount("", 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void withFirstCount_FailWithOnlyWhitespace() {
        CountingStringFormatter.withFirstCount("   ", 1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // StringFormatter.format(Object)
    //

    @DataProvider
    private static Object[][] _format_Pass_Data() {
        return new Object[][] {
            {
                null,
                String.format("%d: [null]", CountingStringFormatter.DEFAULT_FIRST_COUNT),
                String.format("%d: [null]", 1 + CountingStringFormatter.DEFAULT_FIRST_COUNT),
            },
            {
                "",
                String.format("%d: []", CountingStringFormatter.DEFAULT_FIRST_COUNT),
                String.format("%d: []", 1 + CountingStringFormatter.DEFAULT_FIRST_COUNT),
            },
            {
                "abc",
                String.format("%d: [abc]", CountingStringFormatter.DEFAULT_FIRST_COUNT),
                String.format("%d: [abc]", 1 + CountingStringFormatter.DEFAULT_FIRST_COUNT),
            },
        };
    }

    @Test(dataProvider = "_format_Pass_Data")
    public void format_Pass(Object value, String expected1, String expected2) {
        Assert.assertEquals(classUnderTest.format(value), expected1);
        Assert.assertEquals(classUnderTest.format(value), expected2);
    }
}
