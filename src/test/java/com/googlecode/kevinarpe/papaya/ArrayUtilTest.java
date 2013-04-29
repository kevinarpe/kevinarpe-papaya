package com.googlecode.kevinarpe.papaya;

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

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.ArrayUtil;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ArrayUtilTest {

    @BeforeClass
    public void oneTimeSetup() {
    }
    
    @AfterClass
    public void oneTimeTearDown() {
    }
    
    @DataProvider
    private static final Object[][] staticRemove1_data1() {
        return new Object[][] {
                { new Object[] { "abc", "def", "ghi" } },
                { new Object[] { 7, 8 , 9, 10, 11 } },
                { new Object[] { 1.23, 4.56, 8.912, 371.32 } },
                { new Object[] { true, false } },
        };
    }
    
    @Test(dataProvider = "staticRemove1_data1")
    public void staticRemove1_test1(Object[] arr) {
        for (int index = 0; index < arr.length; ++index) {
            for (int count = 1; count <= arr.length - index; ++count) {
                Object[] manualArr = new Object[arr.length - count];
                for (int j = 0; j < index; ++j) {
                    manualArr[j] = arr[j];
                }
                for (int j = index; j < manualArr.length; ++j) {
                    manualArr[j] = arr[count + j];
                }
                if (1 == count) {
                    Object[] arr2 = ArrayUtil.remove(arr, index);
                    Assert.assertEquals(Arrays.equals(manualArr, arr2), true);
                }
                Object[] arr3 = ArrayUtil.remove(arr, index, count);
                Assert.assertEquals(Arrays.equals(manualArr, arr3), true);
            }
        }
    }
}
