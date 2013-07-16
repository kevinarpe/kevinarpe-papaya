package com.googlecode.kevinarpe.papaya.args;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.args.ArrayArgs;

public class ArrayArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(Object ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0, 0 },
                { new String[] { "a" }, 0, 10 },
                { new String[] { "a" }, 1, 10 },
                { new String[] { }, 0, 10 },
                { new String[] { "a" }, 0, 1 },
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a", "b" }, 0, 10 },
                { new String[] { "a", "b" }, 2, 10 },
                { new String[] { "a", "b" }, 0, 2 },
                { new String[] { "a", "b" }, 2, 2 },
                { new String[] { "a", "b", "c" }, 0, 10 },
                { new String[] { "a", "b", "c" }, 3, 10 },
                { new String[] { "a", "b", "c" }, 0, 3 },
                { new String[] { "a", "b", "c" }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeObject_Pass_Data")
    public <T> void checkLengthRangeObject_Pass(T ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeObject_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new String[] { }, 3, 4 },
                { new String[] { "a" }, -3, 3 },
                { new String[] { "a", "b" }, 1, -3 },
                { new String[] { "a" }, -3, -4 },
                { new String[] { "a" }, 4, 3 },
                { new String[] { "a", "b", "c" }, 6, 7 },
                { new String[] { "a", "b", "c" }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeObject_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkLengthRangeObject_FailWithInvalidMinOrMaxLen(
            T[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkLengthRangeObject_FailWithNullArray(T[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(byte ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0, 0 },
                { new byte[] { 99 }, 0, 10 },
                { new byte[] { 99 }, 1, 10 },
                { new byte[] { }, 0, 10 },
                { new byte[] { 99 }, 0, 1 },
                { new byte[] { 99 }, 1, 1 },
                { new byte[] { 99, 101 }, 0, 10 },
                { new byte[] { 99, 101 }, 2, 10 },
                { new byte[] { 99, 101 }, 0, 2 },
                { new byte[] { 99, 101 }, 2, 2 },
                { new byte[] { 99, 101, 103 }, 0, 10 },
                { new byte[] { 99, 101, 103 }, 3, 10 },
                { new byte[] { 99, 101, 103 }, 0, 3 },
                { new byte[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeByte_Pass_Data")
    public void checkLengthRangeByte_Pass(byte ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeByte_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new byte[] { }, 3, 4 },
                { new byte[] { 99 }, -3, 3 },
                { new byte[] { 99, 101 }, 1, -3 },
                { new byte[] { 99 }, -3, -4 },
                { new byte[] { 99 }, 4, 3 },
                { new byte[] { 99, 101, 103 }, 6, 7 },
                { new byte[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeByte_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeByte_FailWithInvalidMinOrMaxLen(
            byte[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeByte_FailWithNullArray(byte[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(short ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0, 0 },
                { new short[] { 99 }, 0, 10 },
                { new short[] { 99 }, 1, 10 },
                { new short[] { }, 0, 10 },
                { new short[] { 99 }, 0, 1 },
                { new short[] { 99 }, 1, 1 },
                { new short[] { 99, 101 }, 0, 10 },
                { new short[] { 99, 101 }, 2, 10 },
                { new short[] { 99, 101 }, 0, 2 },
                { new short[] { 99, 101 }, 2, 2 },
                { new short[] { 99, 101, 103 }, 0, 10 },
                { new short[] { 99, 101, 103 }, 3, 10 },
                { new short[] { 99, 101, 103 }, 0, 3 },
                { new short[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeShort_Pass_Data")
    public void checkLengthRangeShort_Pass(short ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeShort_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new short[] { }, 3, 4 },
                { new short[] { 99 }, -3, 3 },
                { new short[] { 99, 101 }, 1, -3 },
                { new short[] { 99 }, -3, -4 },
                { new short[] { 99 }, 4, 3 },
                { new short[] { 99, 101, 103 }, 6, 7 },
                { new short[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeShort_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeShort_FailWithInvalidMinOrMaxLen(
            short[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeShort_FailWithNullArray(short[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(int ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0, 0 },
                { new int[] { 99 }, 0, 10 },
                { new int[] { 99 }, 1, 10 },
                { new int[] { }, 0, 10 },
                { new int[] { 99 }, 0, 1 },
                { new int[] { 99 }, 1, 1 },
                { new int[] { 99, 101 }, 0, 10 },
                { new int[] { 99, 101 }, 2, 10 },
                { new int[] { 99, 101 }, 0, 2 },
                { new int[] { 99, 101 }, 2, 2 },
                { new int[] { 99, 101, 103 }, 0, 10 },
                { new int[] { 99, 101, 103 }, 3, 10 },
                { new int[] { 99, 101, 103 }, 0, 3 },
                { new int[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeInteger_Pass_Data")
    public void checkLengthRangeInteger_Pass(int ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeInteger_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new int[] { }, 3, 4 },
                { new int[] { 99 }, -3, 3 },
                { new int[] { 99, 101 }, 1, -3 },
                { new int[] { 99 }, -3, -4 },
                { new int[] { 99 }, 4, 3 },
                { new int[] { 99, 101, 103 }, 6, 7 },
                { new int[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeInteger_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeInteger_FailWithInvalidMinOrMaxLen(
            int[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeInteger_FailWithNullArray(int[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(long ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0, 0 },
                { new long[] { 99 }, 0, 10 },
                { new long[] { 99 }, 1, 10 },
                { new long[] { }, 0, 10 },
                { new long[] { 99 }, 0, 1 },
                { new long[] { 99 }, 1, 1 },
                { new long[] { 99, 101 }, 0, 10 },
                { new long[] { 99, 101 }, 2, 10 },
                { new long[] { 99, 101 }, 0, 2 },
                { new long[] { 99, 101 }, 2, 2 },
                { new long[] { 99, 101, 103 }, 0, 10 },
                { new long[] { 99, 101, 103 }, 3, 10 },
                { new long[] { 99, 101, 103 }, 0, 3 },
                { new long[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeLong_Pass_Data")
    public void checkLengthRangeLong_Pass(long ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeLong_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new long[] { }, 3, 4 },
                { new long[] { 99 }, -3, 3 },
                { new long[] { 99, 101 }, 1, -3 },
                { new long[] { 99 }, -3, -4 },
                { new long[] { 99 }, 4, 3 },
                { new long[] { 99, 101, 103 }, 6, 7 },
                { new long[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeLong_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeLong_FailWithInvalidMinOrMaxLen(
            long[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeLong_FailWithNullArray(long[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(float ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0, 0 },
                { new float[] { 99 }, 0, 10 },
                { new float[] { 99 }, 1, 10 },
                { new float[] { }, 0, 10 },
                { new float[] { 99 }, 0, 1 },
                { new float[] { 99 }, 1, 1 },
                { new float[] { 99, 101 }, 0, 10 },
                { new float[] { 99, 101 }, 2, 10 },
                { new float[] { 99, 101 }, 0, 2 },
                { new float[] { 99, 101 }, 2, 2 },
                { new float[] { 99, 101, 103 }, 0, 10 },
                { new float[] { 99, 101, 103 }, 3, 10 },
                { new float[] { 99, 101, 103 }, 0, 3 },
                { new float[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeFloat_Pass_Data")
    public void checkLengthRangeFloat_Pass(float ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeFloat_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new float[] { }, 3, 4 },
                { new float[] { 99 }, -3, 3 },
                { new float[] { 99, 101 }, 1, -3 },
                { new float[] { 99 }, -3, -4 },
                { new float[] { 99 }, 4, 3 },
                { new float[] { 99, 101, 103 }, 6, 7 },
                { new float[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeFloat_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeFloat_FailWithInvalidMinOrMaxLen(
            float[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeFloat_FailWithNullArray(float[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(double ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0, 0 },
                { new double[] { 99 }, 0, 10 },
                { new double[] { 99 }, 1, 10 },
                { new double[] { }, 0, 10 },
                { new double[] { 99 }, 0, 1 },
                { new double[] { 99 }, 1, 1 },
                { new double[] { 99, 101 }, 0, 10 },
                { new double[] { 99, 101 }, 2, 10 },
                { new double[] { 99, 101 }, 0, 2 },
                { new double[] { 99, 101 }, 2, 2 },
                { new double[] { 99, 101, 103 }, 0, 10 },
                { new double[] { 99, 101, 103 }, 3, 10 },
                { new double[] { 99, 101, 103 }, 0, 3 },
                { new double[] { 99, 101, 103 }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeDouble_Pass_Data")
    public void checkLengthRangeDouble_Pass(double ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeDouble_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new double[] { }, 3, 4 },
                { new double[] { 99 }, -3, 3 },
                { new double[] { 99, 101 }, 1, -3 },
                { new double[] { 99 }, -3, -4 },
                { new double[] { 99 }, 4, 3 },
                { new double[] { 99, 101, 103 }, 6, 7 },
                { new double[] { 99, 101, 103 }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeDouble_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeDouble_FailWithInvalidMinOrMaxLen(
            double[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeDouble_FailWithNullArray(double[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(char ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0, 0 },
                { new char[] { 'a' }, 0, 10 },
                { new char[] { 'a' }, 1, 10 },
                { new char[] { }, 0, 10 },
                { new char[] { 'a' }, 0, 1 },
                { new char[] { 'a' }, 1, 1 },
                { new char[] { 'a', 'b' }, 0, 10 },
                { new char[] { 'a', 'b' }, 2, 10 },
                { new char[] { 'a', 'b' }, 0, 2 },
                { new char[] { 'a', 'b' }, 2, 2 },
                { new char[] { 'a', 'b', 'c' }, 0, 10 },
                { new char[] { 'a', 'b', 'c' }, 3, 10 },
                { new char[] { 'a', 'b', 'c' }, 0, 3 },
                { new char[] { 'a', 'b', 'c' }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeCharacter_Pass_Data")
    public void checkLengthRangeCharacter_Pass(char ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new char[] { }, 3, 4 },
                { new char[] { 'a' }, -3, 3 },
                { new char[] { 'a', 'b' }, 1, -3 },
                { new char[] { 'a' }, -3, -4 },
                { new char[] { 'a' }, 4, 3 },
                { new char[] { 'a', 'b', 'c' }, 6, 7 },
                { new char[] { 'a', 'b', 'c' }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen(
            char[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeCharacter_FailWithNullArray(char[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(boolean ref[], int, int)
    //

    @DataProvider
    private static final Object[][] _checkLengthRangeBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0, 0 },
                { new boolean[] { true }, 0, 10 },
                { new boolean[] { true }, 1, 10 },
                { new boolean[] { }, 0, 10 },
                { new boolean[] { true }, 0, 1 },
                { new boolean[] { true }, 1, 1 },
                { new boolean[] { true, false }, 0, 10 },
                { new boolean[] { true, false }, 2, 10 },
                { new boolean[] { true, false }, 0, 2 },
                { new boolean[] { true, false }, 2, 2 },
                { new boolean[] { true, false, true }, 0, 10 },
                { new boolean[] { true, false, true }, 3, 10 },
                { new boolean[] { true, false, true }, 0, 3 },
                { new boolean[] { true, false, true }, 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeBoolean_Pass_Data")
    public void checkLengthRangeBoolean_Pass(boolean ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static final Object[][] _checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new boolean[] { }, 3, 4 },
                { new boolean[] { true }, -3, 3 },
                { new boolean[] { true, false }, 1, -3 },
                { new boolean[] { true }, -3, -4 },
                { new boolean[] { true }, 4, 3 },
                { new boolean[] { true, false, true }, 6, 7 },
                { new boolean[] { true, false, true }, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen(
            boolean[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRangeBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRangeBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeBoolean_FailWithNullArray(boolean[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_Pass_Data")
    public <T> void checkNotEmpty_Pass(T[] ref) {
        ArrayArgs.checkNotEmpty(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkNotEmpty(ref, null);
        ArrayArgs.checkNotEmpty(ref, "");
        ArrayArgs.checkNotEmpty(ref, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotEmpty_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { } },
                { new Object[] { } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmpty_FailWithEmptyArray(T[] ref) {
        ArrayArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmpty_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((Object[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength
    //

    @DataProvider
    private static final Object[][] _checkMinLength_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_Pass_Data")
    public <T> void checkMinLength_Pass(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkMinLength(ref, minLen, null);
        ArrayArgs.checkMinLength(ref, minLen, "");
        ArrayArgs.checkMinLength(ref, minLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLength_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMinLength_FailWithInvalidMinLen(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLength_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMinLength_FailWithNullArray(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength
    //

    @DataProvider
    private static final Object[][] _checkMaxLength_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { }, 99 },
                { new String[] { "a" }, 1 },
                { new String[] { "a" }, 99 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_Pass_Data")
    public <T> void checkMaxLength_Pass(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkMaxLength(ref, maxLen, null);
        ArrayArgs.checkMaxLength(ref, maxLen, "");
        ArrayArgs.checkMaxLength(ref, maxLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLength_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMaxLength_FailWithInvalidMaxLen(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLength_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMaxLength_FailWithNullArray(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength
    //

    @DataProvider
    private static final Object[][] _checkExactLength_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_Pass_Data")
    public <T> void checkExactLength_Pass(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkExactLength(ref, exactLen, null);
        ArrayArgs.checkExactLength(ref, exactLen, "");
        ArrayArgs.checkExactLength(ref, exactLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLength_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkExactLength_FailWithInvalidExactLen(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLength_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkExactLength_FailWithNullArray(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkElementsNotNull
    //

    @DataProvider
    private static final Object[][] _checkElementsNotNull_Pass_Data() {
        return new Object[][] {
                { new String[] { } },
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
        };
    }
    
    @Test(dataProvider = "_checkElementsNotNull_Pass_Data")
    public <T> void checkElementsNotNull_Pass(T[] ref) {
        ArrayArgs.checkElementsNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkElementsNotNull(ref, null);
        ArrayArgs.checkElementsNotNull(ref, "");
        ArrayArgs.checkElementsNotNull(ref, "   ");
    }

    @DataProvider
    private static final Object[][] _checkElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "_checkElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkElementsNotNull_FailWithNullElements(T[] ref) {
        ArrayArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkElementsNotNull_FailWithNullArray() {
        ArrayArgs.checkElementsNotNull(null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex
    //

    @DataProvider
    private static final Object[][] _checkAccessIndex_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_Pass_Data")
    public <T> void checkAccessIndex_Pass(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkAccessIndex(ref, index, null, null);
        ArrayArgs.checkAccessIndex(ref, index, "", "");
        ArrayArgs.checkAccessIndex(ref, index, "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, -1 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndex_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndex_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkAccessIndex_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex
    //

    @DataProvider
    private static final Object[][] _checkInsertIndex_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_Pass_Data")
    public <T> void checkInsertIndex_Pass(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkInsertIndex(ref, index, null, null);
        ArrayArgs.checkInsertIndex(ref, index, "", "");
        ArrayArgs.checkInsertIndex(ref, index, "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { }, -1 },
                { new String[] { }, 1 },
                { new String[] { "a" }, -1 },
                { new String[] { "a" }, 2 },
                { new String[] { "a", "b" }, -1 },
                { new String[] { "a", "b" }, 3 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndex_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkInsertIndex_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCount_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, 0 },
                { new String[] { "a" }, 0, 1 },
                { new String[] { "a", "b" }, 0, 0 },
                { new String[] { "a", "b" }, 0, 1 },
                { new String[] { "a", "b" }, 0, 2 },
                { new String[] { "a", "b" }, 1, 0 },
                { new String[] { "a", "b" }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_Pass_Data")
    public <T> void checkIndexAndCount_Pass(T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a", "b" }, -1, 0 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithInvalidIndex(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a", "b" }, 0, -1 },
                { new String[] { "a", "b" }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithNegativeCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithNegativeCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2 },
                { new String[] { "a" }, 0, 99 },
                { new String[] { "a", "b" }, 0, 3 },
                { new String[] { "a", "b" }, 0, 99 },
                { new String[] { "a", "b" }, 1, 3 },
                { new String[] { "a", "b" }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithInvalidCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCount_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((Object[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndices_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, 0 },
                { new String[] { "a" }, 0, 1 },
                { new String[] { "a", "b" }, 0, 0 },
                { new String[] { "a", "b" }, 0, 1 },
                { new String[] { "a", "b" }, 0, 2 },
                { new String[] { "a", "b" }, 1, 1 },
                { new String[] { "a", "b" }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndices_Pass_Data")
    public <T> void checkFromAndToIndices_Pass(T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndices_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a" }, -1, -1 },
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a" }, 1, 0 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
                { new String[] { "a", "b", "c", "d" }, 2, 1 },
                { new String[] { "a", "b", "c", "d" }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndices_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkFromAndToIndices_FailWithInvalidIndices(
            T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndices_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((Object[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(Object[] ref, Object value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsObject_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, "a" },
                { new String[] { "a", "b" }, "b" },
                { new String[] { "a", "b", "c" }, "b" },
                { new String[] { "a", "b", "c", "b" }, "b" },
                { new String[] { "a", "b", "xyz" }, "xyz" },
                { new String[] { "東京", "大阪", "札幌" }, "大阪" },
                { new String[] { "a", "b", null, "c" }, null },
        };
    }
    
    @Test(dataProvider = "_checkContainsObject_Pass_Data")
    public <THaystack, TNeedle extends THaystack>
    void checkContainsObject_Pass(THaystack[] ref, TNeedle value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsObject_Fail_Data() {
        return new Object[][] {
                { new String[] { "a" }, "x" },
                { new String[] { "a" }, null },
                { new String[] { "a", "b" }, "x" },
                { new String[] { "a", "b", "c" }, "x" },
                { new String[] { "a", "b", "c", "b" }, "x" },
                { new String[] { "a", "b", "xyz" }, "x" },
                { new String[] { "東京", "大阪", "札幌" }, "x" },
                { new String[] { "a", "b", null, "c" }, "x" },
        };
    }
    
    @Test(dataProvider = "_checkContainsObject_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <THaystack, TNeedle extends THaystack>
    void checkContainsObject_Fail(THaystack[] ref, TNeedle value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsObject_FailWithEmptyArray() {
        ArrayArgs.checkContains(new Object[0], "abc", "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsObject_FailWithNullArray() {
        ArrayArgs.checkContains((Object[]) null, "abc", "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(byte[] ref, byte value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 1 }, (byte) 1 },
                { new byte[] { 1, 2 }, (byte) 2 },
                { new byte[] { 1, 2, 3 }, (byte) 2 },
                { new byte[] { 1, 2, 3, 2 }, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsByte_Pass_Data")
    public void checkContainsByte_Pass(byte[] ref, byte value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsByte_Fail_Data() {
        return new Object[][] {
                { new byte[] { 1 }, (byte) 99 },
                { new byte[] { 1, 2 }, (byte) 99 },
                { new byte[] { 1, 2, 3 }, (byte) 99 },
                { new byte[] { 1, 2, 3, 2 }, (byte) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsByte_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsByte_Fail(byte[] ref, byte value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsByte_FailWithEmptyArray() {
        ArrayArgs.checkContains(new byte[0], (byte) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsByte_FailWithNullArray() {
        ArrayArgs.checkContains((byte[]) null, (byte) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(short[] ref, short value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 1 }, (short) 1 },
                { new short[] { 1, 2 }, (short) 2 },
                { new short[] { 1, 2, 3 }, (short) 2 },
                { new short[] { 1, 2, 3, 2 }, (short) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsShort_Pass_Data")
    public void checkContainsShort_Pass(short[] ref, short value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsShort_Fail_Data() {
        return new Object[][] {
                { new short[] { 1 }, (short) 99 },
                { new short[] { 1, 2 }, (short) 99 },
                { new short[] { 1, 2, 3 }, (short) 99 },
                { new short[] { 1, 2, 3, 2 }, (short) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsShort_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsShort_Fail(short[] ref, short value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsShort_FailWithEmptyArray() {
        ArrayArgs.checkContains(new short[0], (short) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsShort_FailWithNullArray() {
        ArrayArgs.checkContains((short[]) null, (short) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(int[] ref, int value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 1 }, (int) 1 },
                { new int[] { 1, 2 }, (int) 2 },
                { new int[] { 1, 2, 3 }, (int) 2 },
                { new int[] { 1, 2, 3, 2 }, (int) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsInteger_Pass_Data")
    public void checkContainsInteger_Pass(int[] ref, int value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsInteger_Fail_Data() {
        return new Object[][] {
                { new int[] { 1 }, (int) 99 },
                { new int[] { 1, 2 }, (int) 99 },
                { new int[] { 1, 2, 3 }, (int) 99 },
                { new int[] { 1, 2, 3, 2 }, (int) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsInteger_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsInteger_Fail(int[] ref, int value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsInteger_FailWithEmptyArray() {
        ArrayArgs.checkContains(new int[0], (int) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsInteger_FailWithNullArray() {
        ArrayArgs.checkContains((int[]) null, (int) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(long[] ref, long value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 1 }, (long) 1 },
                { new long[] { 1, 2 }, (long) 2 },
                { new long[] { 1, 2, 3 }, (long) 2 },
                { new long[] { 1, 2, 3, 2 }, (long) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsLong_Pass_Data")
    public void checkContainsLong_Pass(long[] ref, long value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsLong_Fail_Data() {
        return new Object[][] {
                { new long[] { 1 }, (long) 99 },
                { new long[] { 1, 2 }, (long) 99 },
                { new long[] { 1, 2, 3 }, (long) 99 },
                { new long[] { 1, 2, 3, 2 }, (long) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsLong_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsLong_Fail(long[] ref, long value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsLong_FailWithEmptyArray() {
        ArrayArgs.checkContains(new long[0], (long) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsLong_FailWithNullArray() {
        ArrayArgs.checkContains((long[]) null, (long) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(float[] ref, float value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 1 }, (float) 1 },
                { new float[] { 1, 2 }, (float) 2 },
                { new float[] { 1, 2, 3 }, (float) 2 },
                { new float[] { 1, 2, 3, 2 }, (float) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsFloat_Pass_Data")
    public void checkContainsFloat_Pass(float[] ref, float value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsFloat_Fail_Data() {
        return new Object[][] {
                { new float[] { 1 }, (float) 99 },
                { new float[] { 1, 2 }, (float) 99 },
                { new float[] { 1, 2, 3 }, (float) 99 },
                { new float[] { 1, 2, 3, 2 }, (float) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsFloat_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsFloat_Fail(float[] ref, float value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsFloat_FailWithEmptyArray() {
        ArrayArgs.checkContains(new float[0], (float) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsFloat_FailWithNullArray() {
        ArrayArgs.checkContains((float[]) null, (float) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(double[] ref, double value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 1 }, (double) 1 },
                { new double[] { 1, 2 }, (double) 2 },
                { new double[] { 1, 2, 3 }, (double) 2 },
                { new double[] { 1, 2, 3, 2 }, (double) 2 },
        };
    }
    
    @Test(dataProvider = "_checkContainsDouble_Pass_Data")
    public void checkContainsDouble_Pass(double[] ref, double value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsDouble_Fail_Data() {
        return new Object[][] {
                { new double[] { 1 }, (double) 99 },
                { new double[] { 1, 2 }, (double) 99 },
                { new double[] { 1, 2, 3 }, (double) 99 },
                { new double[] { 1, 2, 3, 2 }, (double) 99 },
        };
    }
    
    @Test(dataProvider = "_checkContainsDouble_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsDouble_Fail(double[] ref, double value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsDouble_FailWithEmptyArray() {
        ArrayArgs.checkContains(new double[0], (double) 99, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsDouble_FailWithNullArray() {
        ArrayArgs.checkContains((double[]) null, (double) 99, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(char[] ref, char value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 'a' }, 'a' },
                { new char[] { 'a', 'b' }, 'b' },
                { new char[] { 'a', 'b', 'c' }, 'b' },
                { new char[] { 'a', 'b', 'c', 'b' }, 'b' },
        };
    }
    
    @Test(dataProvider = "_checkContainsCharacter_Pass_Data")
    public void checkContainsCharacter_Pass(char[] ref, char value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsCharacter_Fail_Data() {
        return new Object[][] {
                { new char[] { 'a' }, 'x' },
                { new char[] { 'a', 'b' }, 'x' },
                { new char[] { 'a', 'b', 'c' }, 'x' },
                { new char[] { 'a', 'b', 'c', 'b' }, 'x' },
        };
    }
    
    @Test(dataProvider = "_checkContainsObject_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsCharacter_Fail(char[] ref, char value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsCharacter_FailWithEmptyArray() {
        ArrayArgs.checkContains(new char[0], 'a', "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsCharacter_FailWithNullArray() {
        ArrayArgs.checkContains((char[]) null, 'a', "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkContains(boolean[] ref, boolean value, String arrayArgName)
    //
    
    @DataProvider
    private static final Object[][] _checkContainsBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, true },
                { new boolean[] { true, false }, false },
                { new boolean[] { true, false, true }, false },
                { new boolean[] { true, false, true, false }, false },
        };
    }
    
    @Test(dataProvider = "_checkContainsBoolean_Pass_Data")
    public void checkContainsBoolean_Pass(boolean[] ref, boolean value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkContainsBoolean_Fail_Data() {
        return new Object[][] {
                { new boolean[] { true }, false },
                { new boolean[] { false }, true },
        };
    }
    
    @Test(dataProvider = "_checkContainsObject_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkContainsBoolean_Fail(boolean[] ref, boolean value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContainsBoolean_FailWithEmptyArray() {
        ArrayArgs.checkContains(new boolean[0], true, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContainsBoolean_FailWithNullArray() {
        ArrayArgs.checkContains((boolean[]) null, true, "ref");
    }
}
