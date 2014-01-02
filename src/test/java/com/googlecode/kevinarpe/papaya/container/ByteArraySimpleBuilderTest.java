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

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class ByteArraySimpleBuilderTest {
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArraySimpleBuilder.ctor()
    //
    
    @DataProvider
    public static Object[][] _ctor_Pass_Data() {
        return new Object[][] {
                { 1 },
                { 99 },
                { 10000 },
        };
    }

    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(int initialCapacity) {
        new ByteArraySimpleBuilder(initialCapacity);
    }
    
    @DataProvider
    public static Object[][] _ctor_Fail_Data() {
        return new Object[][] {
                { 0 },
                { -99 },
                { -10000 },
        };
    }

    @Test(dataProvider = "_ctor_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void ctor_Fail(int initialCapacity) {
        new ByteArraySimpleBuilder(initialCapacity);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArraySimpleBuilder.append(byte)
    //
    
    @Test
    public void appendByte_Pass() {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        Assert.assertEquals(x.length(), 0);
        Assert.assertEquals(x.toArray(), new byte[0]);
        int len = 0;
        byte[] byteArr = new byte[] { 99, 101, 103 };
        for (byte b: byteArr) {
            x.append(b);
            ++len;
            Assert.assertEquals(x.length(), len);
        }
        Assert.assertEquals(x.toArray(), byteArr);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArraySimpleBuilder.append(byte[])
    //
    
    @DataProvider
    public static Object[][] _appendByteArr_Pass_Data() {
        return new Object[][] {
                { new byte[] { } },
                { new byte[] { 99 } },
                { new byte[] { 99, 101 } },
                { new byte[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_appendByteArr_Pass_Data")
    public void appendByteArr_Pass(byte[] byteArr) {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        Assert.assertEquals(x.length(), 0);
        Assert.assertEquals(x.toArray(), new byte[0]);
        x.append(byteArr);
        Assert.assertEquals(x.length(), byteArr.length);
        Assert.assertEquals(x.toArray(), byteArr);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void appendByteArr_FailWithNullArray() {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        x.append((byte[]) null);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ByteArraySimpleBuilder.append(byte[], int, int)
    //
    
    @DataProvider
    public static Object[][] _appendByteArr2_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, 0 },
                { new byte[] { 99 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 0 },
                { new byte[] { 99, 101 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 2 },
                { new byte[] { 99, 101 }, 1, 0 },
                { new byte[] { 99, 101 }, 1, 1 },
                { new byte[] { 99, 101, 103 }, 0, 0 },
                { new byte[] { 99, 101, 103 }, 0, 1 },
                { new byte[] { 99, 101, 103 }, 0, 2 },
                { new byte[] { 99, 101, 103 }, 0, 3 },
                { new byte[] { 99, 101, 103 }, 1, 0 },
                { new byte[] { 99, 101, 103 }, 1, 1 },
                { new byte[] { 99, 101, 103 }, 1, 2 },
                { new byte[] { 99, 101, 103 }, 2, 0 },
                { new byte[] { 99, 101, 103 }, 2, 1 },
        };
    }
    
    @Test(dataProvider = "_appendByteArr2_Pass_Data")
    public void appendByteArr2_Pass(byte[] byteArr, int offset, int length) {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        Assert.assertEquals(x.length(), 0);
        Assert.assertEquals(x.toArray(), new byte[0]);
        x.append(byteArr, offset, length);
        Assert.assertEquals(x.length(), length);
        Assert.assertEquals(x.toArray(), Arrays.copyOfRange(byteArr, offset, offset + length));
    }
    
    @DataProvider
    public static Object[][] _appendByteArr2_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 0, 0 },
                { null, 1, 0 },
                { null, 0, 1 },
                { null, 1, 1 },
                { null, -1, 0 },
                { null, 0, -1 },
                { null, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_appendByteArr2_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void appendByteArr2_FailWithNullArray(byte[] byteArr, int offset, int length) {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        x.append(byteArr, offset, length);
    }
    
    @DataProvider
    public static Object[][] _appendByteArr2_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new byte[] { }, 0, 0 },
                { new byte[] { }, 1, 0 },
                { new byte[] { }, 0, 1 },
                { new byte[] { }, 1, 1 },
                { new byte[] { }, -1, 0 },
                { new byte[] { }, 0, -1 },
                { new byte[] { }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_appendByteArr2_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void appendByteArr2_FailWithEmptyArray(byte[] byteArr, int offset, int length) {
        ByteArraySimpleBuilder x = new ByteArraySimpleBuilder(1);
        x.append(byteArr, offset, length);
    }
}
