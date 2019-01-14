package com.googlecode.kevinarpe.papaya.container;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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
