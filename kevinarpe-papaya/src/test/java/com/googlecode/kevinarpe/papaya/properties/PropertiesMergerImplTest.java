package com.googlecode.kevinarpe.papaya.properties;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

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
