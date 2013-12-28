package com.googlecode.kevinarpe.papaya.container;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ArrayAsImmutableListTest {

    ///////////////////////////////////////////////////////////////////////////
    // ArrayAsImmutableList.copyOf()
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
    public void copyOf_Pass(String[] arr) {
        ArrayAsImmutableList<String> list = ArrayAsImmutableList.copyOf(arr);
        Assert.assertEquals(Arrays.asList(arr), list);
        if (0 != arr.length) {
            final String oldValue = arr[0];
            arr[0] = "new:" + arr[0];
            Assert.assertEquals(oldValue, list.get(0));
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_FailWithNull() {
        ArrayAsImmutableList.copyOf(null);
    }
}
