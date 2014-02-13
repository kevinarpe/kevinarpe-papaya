package com.googlecode.kevinarpe.papaya;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class EnumUtilsTest {

    private enum TestEnum {
        A, B, C;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // EnumUtils.valueOf(Class<? extends Enum<?>>, String)
    //

    @Test
    public void valueOf_Pass() {
        for (TestEnum e : TestEnum.values()) {
            TestEnum e2 = EnumUtils.valueOf(TestEnum.class, e.name());
            Assert.assertSame(e2, e);
        }
    }

    @DataProvider
    private static Object[][] _valueOf_FailWithNull_Data() {
        return new Object[][] {
            { (Class<?>) null, "blah" },
            { List.class, (String) null },
            { (Class<?>) null, (String) null },
        };
    }

    @Test(dataProvider = "_valueOf_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void valueOf_FailWithNull(Class<TestEnum> enumType, String name) {
        EnumUtils.valueOf(enumType, name);
    }

    @DataProvider
    private static Object[][] _valueOf_FailWithInvalidName_Data() {
        return new Object[][] {
            { "" },
            { "   " },
        };
    }

    @Test(dataProvider = "_valueOf_FailWithInvalidName_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void valueOf_FailWithInvalidName(String name) {
        EnumUtils.valueOf(TestEnum.class, name);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // EnumUtils.tryValueOf(Class<? extends Enum<?>>, String)
    //

    @Test
    public void tryValueOf_Pass() {
        for (TestEnum e : TestEnum.values()) {
            TestEnum e2 = EnumUtils.tryValueOf(TestEnum.class, e.name());
            Assert.assertSame(e2, e);
        }
    }

    @Test(dataProvider = "_valueOf_FailWithNull_Data")
    public void tryValueOf_FailWithNull(Class<TestEnum> enumType, String name) {
        TestEnum e = EnumUtils.tryValueOf(enumType, name);
        Assert.assertNull(e);
    }

    @Test(dataProvider = "_valueOf_FailWithInvalidName_Data")
    public void tryValueOf_FailWithInvalidName(String name) {
        TestEnum e = EnumUtils.tryValueOf(TestEnum.class, name);
        Assert.assertNull(e);
    }
}
