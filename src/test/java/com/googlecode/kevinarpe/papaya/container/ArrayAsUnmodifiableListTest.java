package com.googlecode.kevinarpe.papaya.container;

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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ArrayAsUnmodifiableListTest {

    ///////////////////////////////////////////////////////////////////////////
    // ArrayAsUnmodifiableList.copyOf()
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
    public void referenceTo_Pass(String[] arr) {
        ArrayAsUnmodifiableList<String> list = ArrayAsUnmodifiableList.referenceTo(arr);
        Assert.assertEquals(Arrays.asList(arr), list);
        if (0 != arr.length) {
            arr[0] = "new:" + arr[0];
            Assert.assertEquals(arr[0], list.get(0));
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void referenceTo_FailWithNull() {
        ArrayAsUnmodifiableList.referenceTo(null);
    }
}
