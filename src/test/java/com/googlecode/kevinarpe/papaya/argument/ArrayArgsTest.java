package com.googlecode.kevinarpe.papaya.argument;

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

import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;

public class ArrayArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(Object[], int, int)
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
    // ArrayArgs.checkLengthRange(byte[], int, int)
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
    // ArrayArgs.checkLengthRange(short[], int, int)
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
    // ArrayArgs.checkLengthRange(int[], int, int)
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
    // ArrayArgs.checkLengthRange(long[], int, int)
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
    // ArrayArgs.checkLengthRange(float[], int, int)
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
    // ArrayArgs.checkLengthRange(double[], int, int)
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
    // ArrayArgs.checkLengthRange(char[], int, int)
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
    // ArrayArgs.checkLengthRange(boolean[], int, int)
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
    // ArrayArgs.checkNotEmpty(Object[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyObject_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyObject_Pass_Data")
    public <T> void checkNotEmptyObject_Pass(T[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyObject_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { } },
                { new Object[] { } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyObject_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmptyObject_FailWithEmptyArray(T[] ref) {
        ArrayArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyObject_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((Object[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(byte[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 } },
                { new byte[] { 99, 101 } },
                { new byte[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyByte_Pass_Data")
    public void checkNotEmptyByte_Pass(byte[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyByte_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new byte[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyByte_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((byte[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(short[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 } },
                { new short[] { 99, 101 } },
                { new short[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyShort_Pass_Data")
    public void checkNotEmptyShort_Pass(short[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyShort_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new short[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyShort_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((short[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(int[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 } },
                { new int[] { 99, 101 } },
                { new int[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyInteger_Pass_Data")
    public void checkNotEmptyInteger_Pass(int[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyInteger_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new int[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyInteger_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((int[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(long[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 } },
                { new long[] { 99, 101 } },
                { new long[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyLong_Pass_Data")
    public void checkNotEmptyLong_Pass(long[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyLong_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new long[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyLong_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((long[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(float[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 } },
                { new float[] { 99, 101 } },
                { new float[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyFloat_Pass_Data")
    public void checkNotEmptyFloat_Pass(float[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyFloat_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new float[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyFloat_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((float[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(double[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 } },
                { new double[] { 99, 101 } },
                { new double[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyDouble_Pass_Data")
    public void checkNotEmptyDouble_Pass(double[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyDouble_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new double[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyDouble_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((double[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(char[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 } },
                { new char[] { 99, 101 } },
                { new char[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyCharacter_Pass_Data")
    public void checkNotEmptyCharacter_Pass(char[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyCharacter_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new char[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyCharacter_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((char[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(boolean[], String)
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true } },
                { new boolean[] { true, false } },
                { new boolean[] { true, false, true } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyBoolean_Pass_Data")
    public void checkNotEmptyBoolean_Pass(boolean[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyBoolean_FailWithEmptyArray() {
        ArrayArgs.checkNotEmpty(new boolean[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyBoolean_FailWithNullArray() {
        ArrayArgs.checkNotEmpty((boolean[]) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(Object[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthObject_Pass_Data")
    public <T> void checkMinLengthObject_Pass(T[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthObject_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthObject_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMinLengthObject_FailWithInvalidMinLen(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMinLengthObject_FailWithNullArray(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(byte[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthByte_Pass_Data")
    public void checkMinLengthByte_Pass(byte[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthByte_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { }, 2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthByte_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthByte_FailWithInvalidMinLen(byte[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthByte_FailWithNullArray(byte[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(short[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthShort_Pass_Data")
    public void checkMinLengthShort_Pass(short[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthShort_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { }, 2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthShort_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthShort_FailWithInvalidMinLen(short[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthShort_FailWithNullArray(short[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(int[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthInteger_Pass_Data")
    public void checkMinLengthInteger_Pass(int[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthInteger_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { }, 2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthInteger_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthInteger_FailWithInvalidMinLen(int[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthInteger_FailWithNullArray(int[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(long[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthLong_Pass_Data")
    public void checkMinLengthLong_Pass(long[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthLong_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { }, 2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthLong_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthLong_FailWithInvalidMinLen(long[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthLong_FailWithNullArray(long[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(float[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthFloat_Pass_Data")
    public void checkMinLengthFloat_Pass(float[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthFloat_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { }, 2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthFloat_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthFloat_FailWithInvalidMinLen(float[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthFloat_FailWithNullArray(float[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(double[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthDouble_Pass_Data")
    public void checkMinLengthDouble_Pass(double[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthDouble_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { }, 2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthDouble_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthDouble_FailWithInvalidMinLen(double[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthDouble_FailWithNullArray(double[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(char[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthCharacter_Pass_Data")
    public void checkMinLengthCharacter_Pass(char[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthCharacter_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { }, 2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthCharacter_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthCharacter_FailWithInvalidMinLen(char[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthCharacter_FailWithNullArray(char[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(boolean[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMinLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthBoolean_Pass_Data")
    public void checkMinLengthBoolean_Pass(boolean[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthBoolean_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { }, 2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthBoolean_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthBoolean_FailWithInvalidMinLen(boolean[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthBoolean_FailWithNullArray(boolean[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(Object[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { }, 99 },
                { new String[] { "a" }, 1 },
                { new String[] { "a" }, 99 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthObject_Pass_Data")
    public <T> void checkMaxLengthObject_Pass(T[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthObject_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthObject_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMaxLengthObject_FailWithInvalidMaxLen(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMaxLengthObject_FailWithNullArray(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(byte[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { }, 99 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99 }, 99 },
                { new byte[] { 99, 101 }, 2 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthByte_Pass_Data")
    public void checkMaxLengthByte_Pass(byte[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthByte_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthByte_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthByte_FailWithInvalidMaxLen(byte[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthByte_FailWithNullArray(byte[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(short[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { }, 99 },
                { new short[] { 99 }, 1 },
                { new short[] { 99 }, 99 },
                { new short[] { 99, 101 }, 2 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthShort_Pass_Data")
    public void checkMaxLengthShort_Pass(short[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthShort_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthShort_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthShort_FailWithInvalidMaxLen(short[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthShort_FailWithNullArray(short[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(int[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { }, 99 },
                { new int[] { 99 }, 1 },
                { new int[] { 99 }, 99 },
                { new int[] { 99, 101 }, 2 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthInteger_Pass_Data")
    public void checkMaxLengthInteger_Pass(int[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthInteger_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthInteger_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthInteger_FailWithInvalidMaxLen(int[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthInteger_FailWithNullArray(int[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(long[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { }, 99 },
                { new long[] { 99 }, 1 },
                { new long[] { 99 }, 99 },
                { new long[] { 99, 101 }, 2 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthLong_Pass_Data")
    public void checkMaxLengthLong_Pass(long[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthLong_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthLong_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthLong_FailWithInvalidMaxLen(long[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthLong_FailWithNullArray(long[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(float[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { }, 99 },
                { new float[] { 99 }, 1 },
                { new float[] { 99 }, 99 },
                { new float[] { 99, 101 }, 2 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthFloat_Pass_Data")
    public void checkMaxLengthFloat_Pass(float[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthFloat_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthFloat_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthFloat_FailWithInvalidMaxLen(float[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthFloat_FailWithNullArray(float[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(double[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { }, 99 },
                { new double[] { 99 }, 1 },
                { new double[] { 99 }, 99 },
                { new double[] { 99, 101 }, 2 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthDouble_Pass_Data")
    public void checkMaxLengthDouble_Pass(double[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthDouble_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthDouble_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthDouble_FailWithInvalidMaxLen(double[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthDouble_FailWithNullArray(double[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(char[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { }, 99 },
                { new char[] { 99 }, 1 },
                { new char[] { 99 }, 99 },
                { new char[] { 99, 101 }, 2 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthCharacter_Pass_Data")
    public void checkMaxLengthCharacter_Pass(char[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthCharacter_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthCharacter_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthCharacter_FailWithInvalidMaxLen(char[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthCharacter_FailWithNullArray(char[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(boolean[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkMaxLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { }, 99 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true }, 99 },
                { new boolean[] { true, false }, 2 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthBoolean_Pass_Data")
    public void checkMaxLengthBoolean_Pass(boolean[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthBoolean_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthBoolean_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthBoolean_FailWithInvalidMaxLen(boolean[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthBoolean_FailWithNullArray(boolean[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(Object[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthObject_Pass_Data")
    public <T> void checkExactLengthObject_Pass(T[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthObject_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthObject_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkExactLengthObject_FailWithInvalidExactLen(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkExactLengthObject_FailWithNullArray(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(byte[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthByte_Pass_Data")
    public void checkExactLengthByte_Pass(byte[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthByte_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { }, 2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthByte_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthByte_FailWithInvalidExactLen(byte[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthByte_FailWithNullArray(byte[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(short[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthShort_Pass_Data")
    public void checkExactLengthShort_Pass(short[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthShort_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { }, 2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthShort_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthShort_FailWithInvalidExactLen(short[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthShort_FailWithNullArray(short[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(int[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthInteger_Pass_Data")
    public void checkExactLengthInteger_Pass(int[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthInteger_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { }, 2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthInteger_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthInteger_FailWithInvalidExactLen(int[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthInteger_FailWithNullArray(int[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(long[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthLong_Pass_Data")
    public void checkExactLengthLong_Pass(long[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthLong_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { }, 2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthLong_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthLong_FailWithInvalidExactLen(long[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthLong_FailWithNullArray(long[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(float[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthFloat_Pass_Data")
    public void checkExactLengthFloat_Pass(float[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthFloat_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { }, 2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthFloat_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthFloat_FailWithInvalidExactLen(float[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthFloat_FailWithNullArray(float[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(double[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthDouble_Pass_Data")
    public void checkExactLengthDouble_Pass(double[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthDouble_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { }, 2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthDouble_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthDouble_FailWithInvalidExactLen(double[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthDouble_FailWithNullArray(double[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(char[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthCharacter_Pass_Data")
    public void checkExactLengthCharacter_Pass(char[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthCharacter_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { }, 2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthCharacter_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthCharacter_FailWithInvalidExactLen(char[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthCharacter_FailWithNullArray(char[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(boolean[], int, String)
    //

    @DataProvider
    private static final Object[][] _checkExactLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthBoolean_Pass_Data")
    public void checkExactLengthBoolean_Pass(boolean[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthBoolean_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { }, 2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthBoolean_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthBoolean_FailWithInvalidExactLen(boolean[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthBoolean_FailWithNullArray(boolean[] ref, int exactLen) {
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
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, "   "));
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
    // ArrayArgs.checkNotEmptyAndElementsNotNull
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyAndElementsNotNull_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndElementsNotNull_Pass_Data")
    public <T> void checkNotEmptyAndElementsNotNull_Pass(T[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, "   "));
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyAndElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithNullElements(T[] ref) {
        ArrayArgs.checkNotEmptyAndElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithEmptyArray() {
        ArrayArgs.checkNotEmptyAndElementsNotNull(new Object[0], "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkNotEmptyAndElementsNotNull_FailWithNullArray() {
        ArrayArgs.checkNotEmptyAndElementsNotNull(null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(Object[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexObject_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexObject_Pass_Data")
    public <T> void checkAccessIndexObject_Pass(T[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexObject_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1 },
                { new String[] { "a", "b" }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexObject_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkAccessIndexObject_FailWithNegativeIndex(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndexObject_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkAccessIndexObject_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(byte[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexByte_Pass_Data")
    public void checkAccessIndexByte_Pass(byte[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexByte_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1 },
                { new byte[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexByte_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexByte_FailWithNegativeIndex(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 2 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexByte_FailWithInvalidIndex(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexByte_FailWithNullArray(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(short[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexShort_Pass_Data")
    public void checkAccessIndexShort_Pass(short[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexShort_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1 },
                { new short[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexShort_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexShort_FailWithNegativeIndex(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 2 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexShort_FailWithInvalidIndex(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexShort_FailWithNullArray(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(int[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexInteger_Pass_Data")
    public void checkAccessIndexInteger_Pass(int[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexInteger_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1 },
                { new int[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexInteger_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexInteger_FailWithNegativeIndex(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 2 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexInteger_FailWithInvalidIndex(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexInteger_FailWithNullArray(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(long[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexLong_Pass_Data")
    public void checkAccessIndexLong_Pass(long[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexLong_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1 },
                { new long[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexLong_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexLong_FailWithNegativeIndex(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 2 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexLong_FailWithInvalidIndex(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexLong_FailWithNullArray(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(float[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexFloat_Pass_Data")
    public void checkAccessIndexFloat_Pass(float[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexFloat_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1 },
                { new float[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexFloat_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexFloat_FailWithNegativeIndex(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 2 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexFloat_FailWithInvalidIndex(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexFloat_FailWithNullArray(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(double[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexDouble_Pass_Data")
    public void checkAccessIndexDouble_Pass(double[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexDouble_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1 },
                { new double[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexDouble_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexDouble_FailWithNegativeIndex(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 2 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexDouble_FailWithInvalidIndex(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexDouble_FailWithNullArray(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(char[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexCharacter_Pass_Data")
    public void checkAccessIndexCharacter_Pass(char[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexCharacter_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1 },
                { new char[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexCharacter_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexCharacter_FailWithNegativeIndex(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 2 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexCharacter_FailWithInvalidIndex(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexCharacter_FailWithNullArray(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(boolean[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkAccessIndexBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexBoolean_Pass_Data")
    public void checkAccessIndexBoolean_Pass(boolean[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexBoolean_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1 },
                { new boolean[] { true, false }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexBoolean_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexBoolean_FailWithNegativeIndex(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndexBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 2 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexBoolean_FailWithInvalidIndex(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndexBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndexBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexBoolean_FailWithNullArray(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(Object[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexObject_Pass_Data")
    public <T> void checkInsertIndexObject_Pass(T[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexObject_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new String[] { }, -1 },
                { new String[] { "a" }, -1 },
                { new String[] { "a", "b" }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexObject_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkInsertIndexObject_FailWithNegativeIndex(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { }, 1 },
                { new String[] { "a" }, 2 },
                { new String[] { "a", "b" }, 3 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndexObject_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkInsertIndexObject_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(byte[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexByte_Pass_Data")
    public void checkInsertIndexByte_Pass(byte[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexByte_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new byte[] { }, -1 },
                { new byte[] { 99 }, -1 },
                { new byte[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexByte_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexByte_FailWithNegativeIndex(byte[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { }, 1 },
                { new byte[] { 99 }, 2 },
                { new byte[] { 99, 101 }, 3 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexByte_FailWithInvalidIndex(byte[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexByte_FailWithNullArray(byte[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(short[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexShort_Pass_Data")
    public void checkInsertIndexShort_Pass(short[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexShort_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new short[] { }, -1 },
                { new short[] { 99 }, -1 },
                { new short[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexShort_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexShort_FailWithNegativeIndex(short[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { }, 1 },
                { new short[] { 99 }, 2 },
                { new short[] { 99, 101 }, 3 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexShort_FailWithInvalidIndex(short[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexShort_FailWithNullArray(short[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(int[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexInteger_Pass_Data")
    public void checkInsertIndexInteger_Pass(int[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexInteger_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new int[] { }, -1 },
                { new int[] { 99 }, -1 },
                { new int[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexInteger_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexInteger_FailWithNegativeIndex(int[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { }, 1 },
                { new int[] { 99 }, 2 },
                { new int[] { 99, 101 }, 3 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexInteger_FailWithInvalidIndex(int[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexInteger_FailWithNullArray(int[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(long[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexLong_Pass_Data")
    public void checkInsertIndexLong_Pass(long[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexLong_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new long[] { }, -1 },
                { new long[] { 99 }, -1 },
                { new long[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexLong_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexLong_FailWithNegativeIndex(long[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { }, 1 },
                { new long[] { 99 }, 2 },
                { new long[] { 99, 101 }, 3 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexLong_FailWithInvalidIndex(long[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexLong_FailWithNullArray(long[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(float[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexFloat_Pass_Data")
    public void checkInsertIndexFloat_Pass(float[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexFloat_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new float[] { }, -1 },
                { new float[] { 99 }, -1 },
                { new float[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexFloat_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexFloat_FailWithNegativeIndex(float[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { }, 1 },
                { new float[] { 99 }, 2 },
                { new float[] { 99, 101 }, 3 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexFloat_FailWithInvalidIndex(float[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexFloat_FailWithNullArray(float[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(double[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexDouble_Pass_Data")
    public void checkInsertIndexDouble_Pass(double[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexDouble_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new double[] { }, -1 },
                { new double[] { 99 }, -1 },
                { new double[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexDouble_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexDouble_FailWithNegativeIndex(double[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { }, 1 },
                { new double[] { 99 }, 2 },
                { new double[] { 99, 101 }, 3 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexDouble_FailWithInvalidIndex(double[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexDouble_FailWithNullArray(double[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(char[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexCharacter_Pass_Data")
    public void checkInsertIndexCharacter_Pass(char[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexCharacter_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new char[] { }, -1 },
                { new char[] { 99 }, -1 },
                { new char[] { 99, 101 }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexCharacter_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexCharacter_FailWithNegativeIndex(char[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { }, 1 },
                { new char[] { 99 }, 2 },
                { new char[] { 99, 101 }, 3 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexCharacter_FailWithInvalidIndex(char[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexCharacter_FailWithNullArray(char[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(boolean[], int, String, String)
    //

    @DataProvider
    private static final Object[][] _checkInsertIndexBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexBoolean_Pass_Data")
    public void checkInsertIndexBoolean_Pass(boolean[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexBoolean_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new boolean[] { }, -1 },
                { new boolean[] { true }, -1 },
                { new boolean[] { true, false }, -1 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexBoolean_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndexBoolean_FailWithNegativeIndex(boolean[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndexBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { }, 1 },
                { new boolean[] { true }, 2 },
                { new boolean[] { true, false }, 3 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexBoolean_FailWithInvalidIndex(boolean[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndexBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndexBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexBoolean_FailWithNullArray(boolean[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(Object[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountObject_Pass_Data() {
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
    
    @Test(dataProvider = "_checkIndexAndCountObject_Pass_Data")
    public <T> void checkIndexAndCountObject_Pass(T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountObject_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a", "b" }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountObject_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCountObject_FailWithNegativeIndex(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCountObject_FailWithInvalidIndex(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountObject_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a", "b" }, 0, -1 },
                { new String[] { "a", "b" }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountObject_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCountObject_FailWithNegativeCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountObject_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2 },
                { new String[] { "a" }, 0, 99 },
                { new String[] { "a", "b" }, 0, 3 },
                { new String[] { "a", "b" }, 0, 99 },
                { new String[] { "a", "b" }, 1, 3 },
                { new String[] { "a", "b" }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountObject_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCountObject_FailWithInvalidCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountObject_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((Object[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(byte[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, 0 },
                { new byte[] { 99 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 0 },
                { new byte[] { 99, 101 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 2 },
                { new byte[] { 99, 101 }, 1, 0 },
                { new byte[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountByte_Pass_Data")
    public void checkIndexAndCountByte_Pass(byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountByte_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1, 0 },
                { new byte[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountByte_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountByte_FailWithNegativeIndex(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 1, 1 },
                { new byte[] { 99, 101 }, 3, 1 },
                { new byte[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountByte_FailWithInvalidIndex(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountByte_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, -1 },
                { new byte[] { 99, 101 }, 0, -1 },
                { new byte[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountByte_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountByte_FailWithNegativeCount(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountByte_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, 2 },
                { new byte[] { 99 }, 0, 99 },
                { new byte[] { 99, 101 }, 0, 3 },
                { new byte[] { 99, 101 }, 0, 99 },
                { new byte[] { 99, 101 }, 1, 3 },
                { new byte[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountByte_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountByte_FailWithInvalidCount(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountByte_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((byte[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(short[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, 0 },
                { new short[] { 99 }, 0, 1 },
                { new short[] { 99, 101 }, 0, 0 },
                { new short[] { 99, 101 }, 0, 1 },
                { new short[] { 99, 101 }, 0, 2 },
                { new short[] { 99, 101 }, 1, 0 },
                { new short[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountShort_Pass_Data")
    public void checkIndexAndCountShort_Pass(short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountShort_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1, 0 },
                { new short[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountShort_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountShort_FailWithNegativeIndex(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, 1, 1 },
                { new short[] { 99, 101 }, 3, 1 },
                { new short[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountShort_FailWithInvalidIndex(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountShort_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, -1 },
                { new short[] { 99, 101 }, 0, -1 },
                { new short[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountShort_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountShort_FailWithNegativeCount(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountShort_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, 2 },
                { new short[] { 99 }, 0, 99 },
                { new short[] { 99, 101 }, 0, 3 },
                { new short[] { 99, 101 }, 0, 99 },
                { new short[] { 99, 101 }, 1, 3 },
                { new short[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountShort_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountShort_FailWithInvalidCount(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountShort_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((short[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(int[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, 0 },
                { new int[] { 99 }, 0, 1 },
                { new int[] { 99, 101 }, 0, 0 },
                { new int[] { 99, 101 }, 0, 1 },
                { new int[] { 99, 101 }, 0, 2 },
                { new int[] { 99, 101 }, 1, 0 },
                { new int[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountInteger_Pass_Data")
    public void checkIndexAndCountInteger_Pass(int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountInteger_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1, 0 },
                { new int[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountInteger_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountInteger_FailWithNegativeIndex(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, 1, 1 },
                { new int[] { 99, 101 }, 3, 1 },
                { new int[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountInteger_FailWithInvalidIndex(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountInteger_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, -1 },
                { new int[] { 99, 101 }, 0, -1 },
                { new int[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountInteger_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountInteger_FailWithNegativeCount(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountInteger_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, 2 },
                { new int[] { 99 }, 0, 99 },
                { new int[] { 99, 101 }, 0, 3 },
                { new int[] { 99, 101 }, 0, 99 },
                { new int[] { 99, 101 }, 1, 3 },
                { new int[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountInteger_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountInteger_FailWithInvalidCount(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountInteger_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((int[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(long[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, 0 },
                { new long[] { 99 }, 0, 1 },
                { new long[] { 99, 101 }, 0, 0 },
                { new long[] { 99, 101 }, 0, 1 },
                { new long[] { 99, 101 }, 0, 2 },
                { new long[] { 99, 101 }, 1, 0 },
                { new long[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountLong_Pass_Data")
    public void checkIndexAndCountLong_Pass(long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountLong_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1, 0 },
                { new long[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountLong_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountLong_FailWithNegativeIndex(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, 1, 1 },
                { new long[] { 99, 101 }, 3, 1 },
                { new long[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountLong_FailWithInvalidIndex(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountLong_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, -1 },
                { new long[] { 99, 101 }, 0, -1 },
                { new long[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountLong_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountLong_FailWithNegativeCount(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountLong_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, 2 },
                { new long[] { 99 }, 0, 99 },
                { new long[] { 99, 101 }, 0, 3 },
                { new long[] { 99, 101 }, 0, 99 },
                { new long[] { 99, 101 }, 1, 3 },
                { new long[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountLong_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountLong_FailWithInvalidCount(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountLong_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((long[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(float[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, 0 },
                { new float[] { 99 }, 0, 1 },
                { new float[] { 99, 101 }, 0, 0 },
                { new float[] { 99, 101 }, 0, 1 },
                { new float[] { 99, 101 }, 0, 2 },
                { new float[] { 99, 101 }, 1, 0 },
                { new float[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountFloat_Pass_Data")
    public void checkIndexAndCountFloat_Pass(float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountFloat_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1, 0 },
                { new float[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountFloat_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountFloat_FailWithNegativeIndex(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, 1, 1 },
                { new float[] { 99, 101 }, 3, 1 },
                { new float[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountFloat_FailWithInvalidIndex(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountFloat_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, -1 },
                { new float[] { 99, 101 }, 0, -1 },
                { new float[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountFloat_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountFloat_FailWithNegativeCount(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountFloat_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, 2 },
                { new float[] { 99 }, 0, 99 },
                { new float[] { 99, 101 }, 0, 3 },
                { new float[] { 99, 101 }, 0, 99 },
                { new float[] { 99, 101 }, 1, 3 },
                { new float[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountFloat_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountFloat_FailWithInvalidCount(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountFloat_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((float[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(double[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, 0 },
                { new double[] { 99 }, 0, 1 },
                { new double[] { 99, 101 }, 0, 0 },
                { new double[] { 99, 101 }, 0, 1 },
                { new double[] { 99, 101 }, 0, 2 },
                { new double[] { 99, 101 }, 1, 0 },
                { new double[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountDouble_Pass_Data")
    public void checkIndexAndCountDouble_Pass(double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountDouble_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1, 0 },
                { new double[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountDouble_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountDouble_FailWithNegativeIndex(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, 1, 1 },
                { new double[] { 99, 101 }, 3, 1 },
                { new double[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountDouble_FailWithInvalidIndex(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountDouble_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, -1 },
                { new double[] { 99, 101 }, 0, -1 },
                { new double[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountDouble_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountDouble_FailWithNegativeCount(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountDouble_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, 2 },
                { new double[] { 99 }, 0, 99 },
                { new double[] { 99, 101 }, 0, 3 },
                { new double[] { 99, 101 }, 0, 99 },
                { new double[] { 99, 101 }, 1, 3 },
                { new double[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountDouble_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountDouble_FailWithInvalidCount(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountDouble_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((double[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(char[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, 0 },
                { new char[] { 99 }, 0, 1 },
                { new char[] { 99, 101 }, 0, 0 },
                { new char[] { 99, 101 }, 0, 1 },
                { new char[] { 99, 101 }, 0, 2 },
                { new char[] { 99, 101 }, 1, 0 },
                { new char[] { 99, 101 }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountCharacter_Pass_Data")
    public void checkIndexAndCountCharacter_Pass(char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountCharacter_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1, 0 },
                { new char[] { 99, 101 }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountCharacter_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountCharacter_FailWithNegativeIndex(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, 1, 1 },
                { new char[] { 99, 101 }, 3, 1 },
                { new char[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountCharacter_FailWithInvalidIndex(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountCharacter_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, -1 },
                { new char[] { 99, 101 }, 0, -1 },
                { new char[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountCharacter_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountCharacter_FailWithNegativeCount(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountCharacter_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, 2 },
                { new char[] { 99 }, 0, 99 },
                { new char[] { 99, 101 }, 0, 3 },
                { new char[] { 99, 101 }, 0, 99 },
                { new char[] { 99, 101 }, 1, 3 },
                { new char[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountCharacter_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountCharacter_FailWithInvalidCount(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountCharacter_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((char[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(boolean[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCountBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, 0 },
                { new boolean[] { true }, 0, 1 },
                { new boolean[] { true, false }, 0, 0 },
                { new boolean[] { true, false }, 0, 1 },
                { new boolean[] { true, false }, 0, 2 },
                { new boolean[] { true, false }, 1, 0 },
                { new boolean[] { true, false }, 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountBoolean_Pass_Data")
    public void checkIndexAndCountBoolean_Pass(boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountBoolean_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1, 0 },
                { new boolean[] { true, false }, -1, 0 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountBoolean_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountBoolean_FailWithNegativeIndex(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, 1, 1 },
                { new boolean[] { true, false }, 3, 1 },
                { new boolean[] { true, false }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountBoolean_FailWithInvalidIndex(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountBoolean_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, -1 },
                { new boolean[] { true, false }, 0, -1 },
                { new boolean[] { true, false }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountBoolean_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountBoolean_FailWithNegativeCount(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCountBoolean_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, 2 },
                { new boolean[] { true }, 0, 99 },
                { new boolean[] { true, false }, 0, 3 },
                { new boolean[] { true, false }, 0, 99 },
                { new boolean[] { true, false }, 1, 3 },
                { new boolean[] { true, false }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCountBoolean_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountBoolean_FailWithInvalidCount(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCountBoolean_FailWithNullArray() {
        ArrayArgs.checkIndexAndCount((boolean[]) null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(Object[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesObject_Pass_Data() {
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
    
    @Test(dataProvider = "_checkFromAndToIndicesObject_Pass_Data")
    public <T> void checkFromAndToIndicesObject_Pass(T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesObject_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a" }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesObject_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkFromAndToIndicesObject_FailWithNegativeIndices(
            T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesObject_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a" }, 1, 0 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
                { new String[] { "a", "b", "c", "d" }, 2, 1 },
                { new String[] { "a", "b", "c", "d" }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesObject_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkFromAndToIndicesObject_FailWithInvalidIndices(
            T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesObject_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((Object[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(byte[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, 0 },
                { new byte[] { 99 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 0 },
                { new byte[] { 99, 101 }, 0, 1 },
                { new byte[] { 99, 101 }, 0, 2 },
                { new byte[] { 99, 101 }, 1, 1 },
                { new byte[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesByte_Pass_Data")
    public void checkFromAndToIndicesByte_Pass(byte[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesByte_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1, 0 },
                { new byte[] { 99 }, 0, -1 },
                { new byte[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesByte_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesByte_FailWithNegativeIndices(
            byte[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesByte_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 1, 1 },
                { new byte[] { 99 }, 1, 0 },
                { new byte[] { 99, 101 }, 3, 1 },
                { new byte[] { 99, 101 }, 99, 2 },
                { new byte[] { 99, 101, 103, 105 }, 2, 1 },
                { new byte[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesByte_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesByte_FailWithInvalidIndices(
            byte[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesByte_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((byte[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(short[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, 0 },
                { new short[] { 99 }, 0, 1 },
                { new short[] { 99, 101 }, 0, 0 },
                { new short[] { 99, 101 }, 0, 1 },
                { new short[] { 99, 101 }, 0, 2 },
                { new short[] { 99, 101 }, 1, 1 },
                { new short[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesShort_Pass_Data")
    public void checkFromAndToIndicesShort_Pass(short[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesShort_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1, 0 },
                { new short[] { 99 }, 0, -1 },
                { new short[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesShort_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesShort_FailWithNegativeIndices(
            short[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesShort_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new short[] { 99 }, 1, 1 },
                { new short[] { 99 }, 1, 0 },
                { new short[] { 99, 101 }, 3, 1 },
                { new short[] { 99, 101 }, 99, 2 },
                { new short[] { 99, 101, 103, 105 }, 2, 1 },
                { new short[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesShort_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesShort_FailWithInvalidIndices(
            short[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesShort_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((short[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(int[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, 0 },
                { new int[] { 99 }, 0, 1 },
                { new int[] { 99, 101 }, 0, 0 },
                { new int[] { 99, 101 }, 0, 1 },
                { new int[] { 99, 101 }, 0, 2 },
                { new int[] { 99, 101 }, 1, 1 },
                { new int[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesInteger_Pass_Data")
    public void checkFromAndToIndicesInteger_Pass(int[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesInteger_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1, 0 },
                { new int[] { 99 }, 0, -1 },
                { new int[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesInteger_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesInteger_FailWithNegativeIndices(
            int[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesInteger_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new int[] { 99 }, 1, 1 },
                { new int[] { 99 }, 1, 0 },
                { new int[] { 99, 101 }, 3, 1 },
                { new int[] { 99, 101 }, 99, 2 },
                { new int[] { 99, 101, 103, 105 }, 2, 1 },
                { new int[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesInteger_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesInteger_FailWithInvalidIndices(
            int[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesInteger_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((int[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(long[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, 0 },
                { new long[] { 99 }, 0, 1 },
                { new long[] { 99, 101 }, 0, 0 },
                { new long[] { 99, 101 }, 0, 1 },
                { new long[] { 99, 101 }, 0, 2 },
                { new long[] { 99, 101 }, 1, 1 },
                { new long[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesLong_Pass_Data")
    public void checkFromAndToIndicesLong_Pass(long[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesLong_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1, 0 },
                { new long[] { 99 }, 0, -1 },
                { new long[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesLong_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesLong_FailWithNegativeIndices(
            long[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesLong_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new long[] { 99 }, 1, 1 },
                { new long[] { 99 }, 1, 0 },
                { new long[] { 99, 101 }, 3, 1 },
                { new long[] { 99, 101 }, 99, 2 },
                { new long[] { 99, 101, 103, 105 }, 2, 1 },
                { new long[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesLong_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesLong_FailWithInvalidIndices(
            long[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesLong_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((long[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(float[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, 0 },
                { new float[] { 99 }, 0, 1 },
                { new float[] { 99, 101 }, 0, 0 },
                { new float[] { 99, 101 }, 0, 1 },
                { new float[] { 99, 101 }, 0, 2 },
                { new float[] { 99, 101 }, 1, 1 },
                { new float[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesFloat_Pass_Data")
    public void checkFromAndToIndicesFloat_Pass(float[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesFloat_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1, 0 },
                { new float[] { 99 }, 0, -1 },
                { new float[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesFloat_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesFloat_FailWithNegativeIndices(
            float[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesFloat_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new float[] { 99 }, 1, 1 },
                { new float[] { 99 }, 1, 0 },
                { new float[] { 99, 101 }, 3, 1 },
                { new float[] { 99, 101 }, 99, 2 },
                { new float[] { 99, 101, 103, 105 }, 2, 1 },
                { new float[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesFloat_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesFloat_FailWithInvalidIndices(
            float[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesFloat_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((float[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(double[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, 0 },
                { new double[] { 99 }, 0, 1 },
                { new double[] { 99, 101 }, 0, 0 },
                { new double[] { 99, 101 }, 0, 1 },
                { new double[] { 99, 101 }, 0, 2 },
                { new double[] { 99, 101 }, 1, 1 },
                { new double[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesDouble_Pass_Data")
    public void checkFromAndToIndicesDouble_Pass(double[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesDouble_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1, 0 },
                { new double[] { 99 }, 0, -1 },
                { new double[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesDouble_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesDouble_FailWithNegativeIndices(
            double[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesDouble_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new double[] { 99 }, 1, 1 },
                { new double[] { 99 }, 1, 0 },
                { new double[] { 99, 101 }, 3, 1 },
                { new double[] { 99, 101 }, 99, 2 },
                { new double[] { 99, 101, 103, 105 }, 2, 1 },
                { new double[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesDouble_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesDouble_FailWithInvalidIndices(
            double[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesDouble_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((double[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(char[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, 0 },
                { new char[] { 99 }, 0, 1 },
                { new char[] { 99, 101 }, 0, 0 },
                { new char[] { 99, 101 }, 0, 1 },
                { new char[] { 99, 101 }, 0, 2 },
                { new char[] { 99, 101 }, 1, 1 },
                { new char[] { 99, 101 }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesCharacter_Pass_Data")
    public void checkFromAndToIndicesCharacter_Pass(char[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesCharacter_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1, 0 },
                { new char[] { 99 }, 0, -1 },
                { new char[] { 99 }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesCharacter_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesCharacter_FailWithNegativeIndices(
            char[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesCharacter_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new char[] { 99 }, 1, 1 },
                { new char[] { 99 }, 1, 0 },
                { new char[] { 99, 101 }, 3, 1 },
                { new char[] { 99, 101 }, 99, 2 },
                { new char[] { 99, 101, 103, 105 }, 2, 1 },
                { new char[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesCharacter_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesCharacter_FailWithInvalidIndices(
            char[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesCharacter_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((char[]) null, 0, 0, "ref", "fromIndex", "toIndex");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkFromAndToIndices(boolean[], int, int, String, String, String)
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, 0 },
                { new boolean[] { true }, 0, 1 },
                { new boolean[] { true, false }, 0, 0 },
                { new boolean[] { true, false }, 0, 1 },
                { new boolean[] { true, false }, 0, 2 },
                { new boolean[] { true, false }, 1, 1 },
                { new boolean[] { true, false }, 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesBoolean_Pass_Data")
    public void checkFromAndToIndicesBoolean_Pass(boolean[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesBoolean_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1, 0 },
                { new boolean[] { true }, 0, -1 },
                { new boolean[] { true }, -1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesBoolean_FailWithNegativeIndices_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesBoolean_FailWithNegativeIndices(
            boolean[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndicesBoolean_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new boolean[] { true }, 1, 1 },
                { new boolean[] { true }, 1, 0 },
                { new boolean[] { true, false }, 3, 1 },
                { new boolean[] { true, false }, 99, 2 },
                { new boolean[] { true, false, true, false }, 2, 1 },
                { new boolean[] { true, false, true, false }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndicesBoolean_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkFromAndToIndicesBoolean_FailWithInvalidIndices(
            boolean[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndicesBoolean_FailWithNullArray() {
        ArrayArgs.checkFromAndToIndices((boolean[]) null, 0, 0, "ref", "fromIndex", "toIndex");
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
