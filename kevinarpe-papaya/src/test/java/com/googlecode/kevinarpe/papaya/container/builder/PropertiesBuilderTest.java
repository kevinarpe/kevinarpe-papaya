package com.googlecode.kevinarpe.papaya.container.builder;

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

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.testing.MoreAssertUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Properties;

import static org.testng.Assert.assertNotNull;

public class PropertiesBuilderTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.create()
    //

    @Test
    public void create_Pass() {
        PropertiesBuilder x = PropertiesBuilder.create();
        assertNotNull(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // PropertiesBuilder.build()
    //

    @DataProvider
    private static Object[][] _build_Pass_Data() {
        return new Object[][] {
            { _copyOf(ImmutableMap.<String, String>of()) },
            { _copyOf(ImmutableMap.of("abc", "def")) },
            { _copyOf(ImmutableMap.of("abc", "ABC", "def", "DEF")) },
        };
    }

    private static Properties _copyOf(Map<String, String> map) {
        Properties prop = new Properties();
        prop.putAll(map);
        return prop;
    }

    @Test(dataProvider = "_build_Pass_Data")
    public void build_Pass(Properties inputProp) {
        PropertiesBuilder classUnderTest = PropertiesBuilder.create();
        classUnderTest.putAll(inputProp);
        Properties prop = classUnderTest.build();
        MoreAssertUtils.INSTANCE.assertMapEquals(prop, inputProp);
    }
}
