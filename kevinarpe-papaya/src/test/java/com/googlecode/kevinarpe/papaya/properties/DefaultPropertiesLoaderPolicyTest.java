package com.googlecode.kevinarpe.papaya.properties;

import com.google.common.base.Splitter;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class DefaultPropertiesLoaderPolicyTest {

    private DefaultPropertiesLoaderPolicy classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new DefaultPropertiesLoaderPolicy();
    }

    @DataProvider
    private static Object[][] _apply_Pass_Data() {
        return new Object[][] {
            {
                JdkPropertyTestUtils.asPropertyList(),
                0, 0
            },
            {
                JdkPropertyTestUtils.asPropertyList("abc", "def"),
                0, 0
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "ghi", "jkl",
                    "mno", "def",
                    "pqr", "jkl"),
                0, 0
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "def"),
                1, 0
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "def",
                    "abc", "def"),
                2, 0
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "ghi"),
                0, 1
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "ghi",
                    "abc", "jkl"),
                0, 2
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "def",
                    "abc", "ghi"),
                1, 1
            },
            {
                JdkPropertyTestUtils.asPropertyList(
                    "abc", "def",
                    "abc", "def",
                    "abc", "def",
                    "abc", "ghi",
                    "abc", "jkl"),
                2, 2
            },
        };
    }

    @Test(dataProvider = "_apply_Pass_Data")
    public void apply_Pass(
            List<JdkProperty> propertyList,
            int expectedDupKeyValuePairCount,
            int expectedDupKeyCount) {
        try {
            classUnderTest.apply(propertyList);
            if (0 != expectedDupKeyValuePairCount || 0 != expectedDupKeyCount) {
                throw new AssertionError("Expected exception");
            }
        }
        catch (PropertiesLoaderException e) {
            String msg = e.getMessage();
            List<String> lineList = Splitter.on(StringUtils.NEW_LINE).splitToList(msg);
            int dupKeyValuePairCount = 0;
            int dupKeyCount = 0;
            for (String line : lineList) {
                if (line.contains(DefaultPropertiesLoaderPolicy.DUP_KEY_VALUE_PAIR_MSG)) {
                    ++dupKeyValuePairCount;
                }
                else if (line.contains(DefaultPropertiesLoaderPolicy.DUP_KEY_MSG)) {
                    ++dupKeyCount;
                }
                else {
                    throw new AssertionError(String.format("Unexpected message line: '%s'", line));
                }
            }
            assertEquals(dupKeyValuePairCount, expectedDupKeyValuePairCount);
            assertEquals(dupKeyCount, expectedDupKeyCount);
        }
    }
}
