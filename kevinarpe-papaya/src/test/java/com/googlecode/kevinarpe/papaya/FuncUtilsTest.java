package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FuncUtilsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // FuncUtil.PARSE_BOOLEAN_FROM_STRING
    //

    @DataProvider
    public static Object[][] _dataForShouldParseBooleanFromString() {
        return new Object[][] {
                { "true", true },
                { "True", true },
                { "tRue", true },
                { "trUe", true },
                { "truE", true },
                { "TRUE", true },
                
                { "false", false },
                { "False", false },
                { "fAlse", false },
                { "faLse", false },
                { "falSe", false },
                { "falsE", false },
                { "FALSE", false },
        };
    }
    
    @Test(dataProvider = "_dataForShouldParseBooleanFromString")
    public void shouldParseBooleanFromString(String input, boolean expectedOutput) {
        boolean output = FuncUtils.PARSE_BOOLEAN_FROM_STRING.call(input);
        Assert.assertEquals(output, expectedOutput);
    }
}
