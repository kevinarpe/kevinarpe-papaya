package com.googlecode.kevinarpe.papaya.container;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ArrayAsUnmodifiableListTest {

    ///////////////////////////////////////////////////////////////////////////
    // ArrayAsUnmodifiableList.copyOf()
    //

    @DataProvider
    private static Object[][] Pass_Data() {
        return new Object[][] {
            { new String[0] },
            { new String[] { "abc" } },
            { new String[] { "abc", "def" } },
        };
    }

    @Test(dataProvider = "Pass_Data")
    public void referenceTo_Pass(String[] arr) {
        ArrayAsUnmodifiableList<String> list = ArrayAsUnmodifiableList.referenceTo(arr);
        Assert.assertEquals(Arrays.asList(arr), list);
        if (0 != arr.length) {
            arr[0] = "new:" + arr[0];
            Assert.assertEquals(arr[0], list.get(0));
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void referenceTo_FailWithNull() {
        ArrayAsUnmodifiableList.referenceTo(null);
    }
}
