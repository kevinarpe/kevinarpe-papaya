package com.googlecode.kevinarpe.papaya.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.jdk.properties.JdkProperty;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLogger;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerUtils;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PropertiesMergerImplTest {

    private PropertiesMergerImpl classUnderTest;
    private SLF4JMockLogger mockLogger;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger =
            SLF4JMockLoggerUtils.INSTANCE.newFactoryInstance().getLogger(
                PropertiesMergerImplTest.class.getName());
        classUnderTest = new PropertiesMergerImpl(mockLogger);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new PropertiesMergerImpl((Logger) null);
    }

    @DataProvider
    private static Object[][] _merge_PassWithEmpty_Data() {
        return new Object[][] {
            { ImmutableMap.<String, String>of(), ImmutableList.<JdkProperty>of() },
        };
    }

    @Test(dataProvider = "_merge_PassWithEmpty_Data")
    public void merge_PassWithEmpty(Map<String, String> map, List<JdkProperty> propertyList) {
        final int mapSize = map.size();
        classUnderTest.merge(map, propertyList);
        assertEquals(map.size(), mapSize + propertyList.size());
    }
}
