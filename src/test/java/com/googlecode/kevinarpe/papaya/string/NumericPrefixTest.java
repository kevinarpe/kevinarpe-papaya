package com.googlecode.kevinarpe.papaya.string;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NumericPrefixTest {

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.ctor
    //

    @DataProvider
    private static Object[][] Pass_Data() {
        return new Object[][] {
            { String.valueOf(Long.MAX_VALUE), Long.MAX_VALUE },
            { String.valueOf(Long.MIN_VALUE), Long.MIN_VALUE },
            { String.valueOf(Long.MAX_VALUE) + "abc", Long.MAX_VALUE },
            { String.valueOf(Long.MIN_VALUE) + "abc", Long.MIN_VALUE },
            { "123", 123L },
            { "+123", 123L },
            { "-123", -123L },
            { "123abc", 123L },
            { "+123abc", 123L },
            { "-123abc", -123L },
            { "123.abc", 123L },
            { "+123.abc", 123L },
            { "-123.abc", -123L },
            { "123.456abc", 123L },
            { "+123.456abc", 123L },
            { "-123.456abc", -123L },
            { "123,456abc", 123L },
            { "+123,456abc", 123L },
            { "-123,456abc", -123L },
            { "", null },
            { "   ", null },
            { "abc", null },
        };
    }

    @Test(dataProvider = "Pass_Data")
    public void ctor_Pass(String str, Long expectedValue) {
        new NumericPrefix<String>(str);
    }

    private <TString extends CharSequence> void core_ctor_Pass(TString str, Long expectedValue) {
        NumericPrefix<TString> x = new NumericPrefix<TString>(str);
        Assert.assertEquals(null != expectedValue, x.hasNumericValue());
        if (null != expectedValue) {
            Assert.assertEquals(expectedValue.longValue(), x.getNumericValue());
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new NumericPrefix<String>(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull2() {
        new NumericPrefix<CharSequence>(null);
    }
    @DataProvider
    private static Object[][] ctor_FailWithOutOfRangeNumber_Data() {
        return new Object[][] {
            { String.valueOf(Long.MAX_VALUE) + "123" },
            { String.valueOf(Long.MAX_VALUE) + "123abc" },
            { String.valueOf(Long.MIN_VALUE) + "123" },
            { String.valueOf(Long.MIN_VALUE) + "123abc" },
        };
    }

    @Test(expectedExceptions = NumberFormatException.class,
            dataProvider = "ctor_FailWithOutOfRangeNumber_Data")
    public void ctor_FailWithOutOfRangeNumber(String str) {
        new NumericPrefix<String>(str);
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.getInput()
    //

    @Test(dataProvider = "Pass_Data")
    public void getInput_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        Assert.assertEquals(str, x.getInput());
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.hasNumericValue()
    //

    @Test(dataProvider = "Pass_Data")
    public void hasNumericValue_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        Assert.assertEquals(null != expectedValue, x.hasNumericValue());
    }

    ///////////////////////////////////////////////////////////////////////////
    // NumericPrefix.getNumericValue()
    //

    @Test(dataProvider = "Pass_Data")
    public void getNumericValue_Pass(String str, Long expectedValue) {
        NumericPrefix<String> x = new NumericPrefix<String>(str);
        if (null == expectedValue) {
            try {
                x.getNumericValue();
            }
            catch (IllegalStateException e) {
                // Ignore
            }
        }
        else {
            Assert.assertEquals(expectedValue.longValue(), x.getNumericValue());
        }
    }
}
