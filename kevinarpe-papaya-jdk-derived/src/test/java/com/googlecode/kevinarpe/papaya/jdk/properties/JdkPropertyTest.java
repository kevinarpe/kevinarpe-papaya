package com.googlecode.kevinarpe.papaya.jdk.properties;

import com.google.common.testing.EqualsTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JdkPropertyTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _assertKeyAndValue(JdkProperty classUnderTest, String key, String value) {
        assertEquals(classUnderTest.getKey(), key);
        assertEquals(classUnderTest.getValue(), value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.ctor()
    //

    @Test
    public void ctor_Pass() {
        _assertKeyAndValue(new JdkProperty("abc", "def"), "abc", "def");
        _assertKeyAndValue(new JdkProperty(null, null), null, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.setKey()
    //

    @DataProvider
    private static Object[][] _setKeyOrValue_Pass_Data() {
        return new Object[][] {
            { "abc", "def", "ghi" },
            { "abc", null, "ghi" },
            { null, "def", "ghi" },
            { "abc", "abc", "ghi" },

            { "abc", "def", null },
            { "abc", null, null },
            { null, "def", null },
            { "abc", "abc", null },
        };
    }

    @Test(dataProvider = "_setKeyOrValue_Pass_Data")
    public void setKey_Pass(String key1, String key2, String value) {
        JdkProperty classUnderTest = new JdkProperty(key1, value);
        _assertKeyAndValue(classUnderTest, key1, value);
        classUnderTest.setKey(key2);
        _assertKeyAndValue(classUnderTest, key2, value);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.setValue()
    //

    @Test(dataProvider = "_setKeyOrValue_Pass_Data")
    public void setValue_Pass(String key, String value1, String value2) {
        JdkProperty classUnderTest = new JdkProperty(key, value1);
        _assertKeyAndValue(classUnderTest, key, value1);
        classUnderTest.setValue(value2);
        _assertKeyAndValue(classUnderTest, key, value2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // JdkProperty.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(new JdkProperty("abc", "def"), new JdkProperty("abc", "def"));
        equalsTester.addEqualityGroup(new JdkProperty("abc", "ghi"), new JdkProperty("abc", "ghi"));
        equalsTester.addEqualityGroup(new JdkProperty("ghi", "def"), new JdkProperty("ghi", "def"));
        equalsTester.addEqualityGroup(new JdkProperty(null, null), new JdkProperty(null, null));
        equalsTester.testEquals();
    }
}
