package com.googlecode.kevinarpe.papaya.container;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ArrayAsFixedSizeListTest {

    ///////////////////////////////////////////////////////////////////////////
    // ArrayAsFixedSizeList.copyOf()
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
    public <TValue> void copyOf_PassWithEmptyArray(TValue[] arr) {
        ArrayAsFixedSizeList<TValue> list = ArrayAsFixedSizeList.copyOf(arr);
        Assert.assertNotSame(arr, list.getArrayRef());
        Assert.assertEquals(arr, list.getArrayRef());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void copyOf_FailWithNull() {
        ArrayAsFixedSizeList.copyOf(null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayAsFixedSizeList.referenceTo()
    //

    @Test(dataProvider = "Pass_Data")
    public <TValue> void referenceTo_PassWithEmptyArray(TValue[] arr) {
        ArrayAsFixedSizeList<TValue> list = ArrayAsFixedSizeList.referenceTo(arr);
        Assert.assertSame(arr, list.getArrayRef());
        Assert.assertEquals(arr, list.getArrayRef());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void referenceTo_FailWithNull() {
        ArrayAsFixedSizeList.referenceTo(null);
    }
}
