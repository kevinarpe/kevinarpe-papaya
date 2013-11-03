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
    private static Object[][] checkLengthRangeObject_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeObject_Pass_Data")
    public <T> void checkLengthRangeObject_Pass(T ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeObject_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeObject_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkLengthRangeObject_FailWithInvalidMinOrMaxLen(
            T[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkLengthRangeObject_FailWithNullArray(T[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(byte[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeByte_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeByte_Pass_Data")
    public void checkLengthRangeByte_Pass(byte ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeByte_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeByte_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeByte_FailWithInvalidMinOrMaxLen(
            byte[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeByte_FailWithNullArray(byte[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(short[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeShort_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeShort_Pass_Data")
    public void checkLengthRangeShort_Pass(short ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeShort_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeShort_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeShort_FailWithInvalidMinOrMaxLen(
            short[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeShort_FailWithNullArray(short[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(int[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeInteger_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeInteger_Pass_Data")
    public void checkLengthRangeInteger_Pass(int ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeInteger_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeInteger_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeInteger_FailWithInvalidMinOrMaxLen(
            int[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeInteger_FailWithNullArray(int[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(long[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeLong_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeLong_Pass_Data")
    public void checkLengthRangeLong_Pass(long ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeLong_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeLong_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeLong_FailWithInvalidMinOrMaxLen(
            long[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeLong_FailWithNullArray(long[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(float[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeFloat_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeFloat_Pass_Data")
    public void checkLengthRangeFloat_Pass(float ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeFloat_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeFloat_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeFloat_FailWithInvalidMinOrMaxLen(
            float[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeFloat_FailWithNullArray(float[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(double[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeDouble_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeDouble_Pass_Data")
    public void checkLengthRangeDouble_Pass(double ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeDouble_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeDouble_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeDouble_FailWithInvalidMinOrMaxLen(
            double[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeDouble_FailWithNullArray(double[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(char[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeCharacter_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeCharacter_Pass_Data")
    public void checkLengthRangeCharacter_Pass(char ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeCharacter_FailWithInvalidMinOrMaxLen(
            char[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeCharacter_FailWithNullArray(char[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkLengthRange(boolean[], int, int)
    //

    @DataProvider
    private static Object[][] checkLengthRangeBoolean_Pass_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeBoolean_Pass_Data")
    public void checkLengthRangeBoolean_Pass(boolean ref[], int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }

    @DataProvider
    private static Object[][] checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRangeBoolean_FailWithInvalidMinOrMaxLen(
            boolean[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkLengthRangeBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRangeBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRangeBoolean_FailWithNullArray(boolean[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty(Object[], String)
    //

    @DataProvider
    private static Object[][] checkNotEmptyObject_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyObject_Pass_Data")
    public <T> void checkNotEmptyObject_Pass(T[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmpty(ref, "   "));
    }

    @DataProvider
    private static Object[][] checkNotEmptyObject_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { } },
                { new Object[] { } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyObject_FailWithEmptyArray_Data",
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
    private static Object[][] checkNotEmptyByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 } },
                { new byte[] { 99, 101 } },
                { new byte[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyByte_Pass_Data")
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
    private static Object[][] checkNotEmptyShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 } },
                { new short[] { 99, 101 } },
                { new short[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyShort_Pass_Data")
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
    private static Object[][] checkNotEmptyInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 } },
                { new int[] { 99, 101 } },
                { new int[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyInteger_Pass_Data")
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
    private static Object[][] checkNotEmptyLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 } },
                { new long[] { 99, 101 } },
                { new long[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyLong_Pass_Data")
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
    private static Object[][] checkNotEmptyFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 } },
                { new float[] { 99, 101 } },
                { new float[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyFloat_Pass_Data")
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
    private static Object[][] checkNotEmptyDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 } },
                { new double[] { 99, 101 } },
                { new double[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyDouble_Pass_Data")
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
    private static Object[][] checkNotEmptyCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 } },
                { new char[] { 99, 101 } },
                { new char[] { 99, 101, 103 } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyCharacter_Pass_Data")
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
    private static Object[][] checkNotEmptyBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true } },
                { new boolean[] { true, false } },
                { new boolean[] { true, false, true } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyBoolean_Pass_Data")
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
    private static Object[][] checkMinLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthObject_Pass_Data")
    public <T> void checkMinLengthObject_Pass(T[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthObject_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthObject_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMinLengthObject_FailWithInvalidMinLen(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMinLengthObject_FailWithNullArray(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(byte[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthByte_Pass_Data")
    public void checkMinLengthByte_Pass(byte[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthByte_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { }, 2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthByte_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthByte_FailWithInvalidMinLen(byte[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthByte_FailWithNullArray(byte[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(short[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthShort_Pass_Data")
    public void checkMinLengthShort_Pass(short[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthShort_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { }, 2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthShort_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthShort_FailWithInvalidMinLen(short[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthShort_FailWithNullArray(short[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(int[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthInteger_Pass_Data")
    public void checkMinLengthInteger_Pass(int[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthInteger_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { }, 2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthInteger_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthInteger_FailWithInvalidMinLen(int[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthInteger_FailWithNullArray(int[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(long[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthLong_Pass_Data")
    public void checkMinLengthLong_Pass(long[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthLong_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { }, 2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthLong_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthLong_FailWithInvalidMinLen(long[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthLong_FailWithNullArray(long[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(float[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthFloat_Pass_Data")
    public void checkMinLengthFloat_Pass(float[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthFloat_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { }, 2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthFloat_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthFloat_FailWithInvalidMinLen(float[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthFloat_FailWithNullArray(float[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(double[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthDouble_Pass_Data")
    public void checkMinLengthDouble_Pass(double[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthDouble_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { }, 2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthDouble_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthDouble_FailWithInvalidMinLen(double[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthDouble_FailWithNullArray(double[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(char[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthCharacter_Pass_Data")
    public void checkMinLengthCharacter_Pass(char[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthCharacter_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { }, 2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthCharacter_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthCharacter_FailWithInvalidMinLen(char[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthCharacter_FailWithNullArray(char[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength(boolean[], int, String)
    //

    @DataProvider
    private static Object[][] checkMinLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthBoolean_Pass_Data")
    public void checkMinLengthBoolean_Pass(boolean[] ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMinLengthBoolean_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { }, 2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 3 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthBoolean_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLengthBoolean_FailWithInvalidMinLen(boolean[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMinLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLengthBoolean_FailWithNullArray(boolean[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(Object[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { }, 99 },
                { new String[] { "a" }, 1 },
                { new String[] { "a" }, 99 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthObject_Pass_Data")
    public <T> void checkMaxLengthObject_Pass(T[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthObject_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthObject_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMaxLengthObject_FailWithInvalidMaxLen(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMaxLengthObject_FailWithNullArray(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(byte[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { }, 99 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99 }, 99 },
                { new byte[] { 99, 101 }, 2 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthByte_Pass_Data")
    public void checkMaxLengthByte_Pass(byte[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthByte_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthByte_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthByte_FailWithInvalidMaxLen(byte[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthByte_FailWithNullArray(byte[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(short[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { }, 99 },
                { new short[] { 99 }, 1 },
                { new short[] { 99 }, 99 },
                { new short[] { 99, 101 }, 2 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthShort_Pass_Data")
    public void checkMaxLengthShort_Pass(short[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthShort_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthShort_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthShort_FailWithInvalidMaxLen(short[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthShort_FailWithNullArray(short[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(int[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { }, 99 },
                { new int[] { 99 }, 1 },
                { new int[] { 99 }, 99 },
                { new int[] { 99, 101 }, 2 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthInteger_Pass_Data")
    public void checkMaxLengthInteger_Pass(int[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthInteger_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthInteger_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthInteger_FailWithInvalidMaxLen(int[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthInteger_FailWithNullArray(int[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(long[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { }, 99 },
                { new long[] { 99 }, 1 },
                { new long[] { 99 }, 99 },
                { new long[] { 99, 101 }, 2 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthLong_Pass_Data")
    public void checkMaxLengthLong_Pass(long[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthLong_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthLong_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthLong_FailWithInvalidMaxLen(long[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthLong_FailWithNullArray(long[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(float[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { }, 99 },
                { new float[] { 99 }, 1 },
                { new float[] { 99 }, 99 },
                { new float[] { 99, 101 }, 2 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthFloat_Pass_Data")
    public void checkMaxLengthFloat_Pass(float[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthFloat_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthFloat_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthFloat_FailWithInvalidMaxLen(float[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthFloat_FailWithNullArray(float[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(double[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { }, 99 },
                { new double[] { 99 }, 1 },
                { new double[] { 99 }, 99 },
                { new double[] { 99, 101 }, 2 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthDouble_Pass_Data")
    public void checkMaxLengthDouble_Pass(double[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthDouble_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthDouble_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthDouble_FailWithInvalidMaxLen(double[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthDouble_FailWithNullArray(double[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(char[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { }, 99 },
                { new char[] { 99 }, 1 },
                { new char[] { 99 }, 99 },
                { new char[] { 99, 101 }, 2 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthCharacter_Pass_Data")
    public void checkMaxLengthCharacter_Pass(char[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthCharacter_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthCharacter_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthCharacter_FailWithInvalidMaxLen(char[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthCharacter_FailWithNullArray(char[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength(boolean[], int, String)
    //

    @DataProvider
    private static Object[][] checkMaxLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { }, 99 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true }, 99 },
                { new boolean[] { true, false }, 2 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthBoolean_Pass_Data")
    public void checkMaxLengthBoolean_Pass(boolean[] ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthBoolean_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthBoolean_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLengthBoolean_FailWithInvalidMaxLen(boolean[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkMaxLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLengthBoolean_FailWithNullArray(boolean[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(Object[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthObject_Pass_Data")
    public <T> void checkExactLengthObject_Pass(T[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthObject_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthObject_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkExactLengthObject_FailWithInvalidExactLen(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkExactLengthObject_FailWithNullArray(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(byte[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthByte_Pass_Data")
    public void checkExactLengthByte_Pass(byte[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthByte_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new byte[] { }, -2 },
                { new byte[] { }, 2 },
                { new byte[] { 99 }, -3 },
                { new byte[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthByte_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthByte_FailWithInvalidExactLen(byte[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthByte_FailWithNullArray(byte[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(short[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthShort_Pass_Data")
    public void checkExactLengthShort_Pass(short[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthShort_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new short[] { }, -2 },
                { new short[] { }, 2 },
                { new short[] { 99 }, -3 },
                { new short[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthShort_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthShort_FailWithInvalidExactLen(short[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthShort_FailWithNullArray(short[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(int[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthInteger_Pass_Data")
    public void checkExactLengthInteger_Pass(int[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthInteger_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new int[] { }, -2 },
                { new int[] { }, 2 },
                { new int[] { 99 }, -3 },
                { new int[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthInteger_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthInteger_FailWithInvalidExactLen(int[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthInteger_FailWithNullArray(int[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(long[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthLong_Pass_Data")
    public void checkExactLengthLong_Pass(long[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthLong_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new long[] { }, -2 },
                { new long[] { }, 2 },
                { new long[] { 99 }, -3 },
                { new long[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthLong_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthLong_FailWithInvalidExactLen(long[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthLong_FailWithNullArray(long[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(float[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthFloat_Pass_Data")
    public void checkExactLengthFloat_Pass(float[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthFloat_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new float[] { }, -2 },
                { new float[] { }, 2 },
                { new float[] { 99 }, -3 },
                { new float[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthFloat_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthFloat_FailWithInvalidExactLen(float[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthFloat_FailWithNullArray(float[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(double[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthDouble_Pass_Data")
    public void checkExactLengthDouble_Pass(double[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthDouble_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new double[] { }, -2 },
                { new double[] { }, 2 },
                { new double[] { 99 }, -3 },
                { new double[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthDouble_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthDouble_FailWithInvalidExactLen(double[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthDouble_FailWithNullArray(double[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(char[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthCharacter_Pass_Data")
    public void checkExactLengthCharacter_Pass(char[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthCharacter_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new char[] { }, -2 },
                { new char[] { }, 2 },
                { new char[] { 99 }, -3 },
                { new char[] { 99 }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthCharacter_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthCharacter_FailWithInvalidExactLen(char[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthCharacter_FailWithNullArray(char[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength(boolean[], int, String)
    //

    @DataProvider
    private static Object[][] checkExactLengthBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthBoolean_Pass_Data")
    public void checkExactLengthBoolean_Pass(boolean[] ref, int exactLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, null));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, ""));
        Assert.assertTrue(ref == ArrayArgs.checkExactLength(ref, exactLen, "   "));
    }
    
    @DataProvider
    private static Object[][] checkExactLengthBoolean_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new boolean[] { }, -2 },
                { new boolean[] { }, 2 },
                { new boolean[] { true }, -3 },
                { new boolean[] { true }, 3 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthBoolean_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLengthBoolean_FailWithInvalidExactLen(boolean[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static Object[][] checkExactLengthBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLengthBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLengthBoolean_FailWithNullArray(boolean[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkElementsNotNull
    //

    @DataProvider
    private static Object[][] checkElementsNotNull_Pass_Data() {
        return new Object[][] {
                { new String[] { } },
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
        };
    }
    
    @Test(dataProvider = "checkElementsNotNull_Pass_Data")
    public <T> void checkElementsNotNull_Pass(T[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkElementsNotNull(ref, "   "));
    }

    @DataProvider
    private static Object[][] checkElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "checkElementsNotNull_FailWithNullElements_Data",
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
    private static Object[][] checkNotEmptyAndElementsNotNull_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndElementsNotNull_Pass_Data")
    public <T> void checkNotEmptyAndElementsNotNull_Pass(T[] ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, null));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, ""));
        Assert.assertTrue(ref == ArrayArgs.checkNotEmptyAndElementsNotNull(ref, "   "));
    }

    @DataProvider
    private static Object[][] checkNotEmptyAndElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndElementsNotNull_FailWithNullElements_Data",
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
    private static Object[][] checkAccessIndexObject_Pass_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexObject_Pass_Data")
    public <T> void checkAccessIndexObject_Pass(T[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexObject_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { }, -1 },
                { new String[] { }, 0 },
                { new String[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexObject_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkAccessIndexObject_FailWithEmptyArray(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1 },
                { new String[] { "a", "b" }, -1 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndexObject_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkAccessIndexObject_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(byte[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexByte_Pass_Data")
    public void checkAccessIndexByte_Pass(byte[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexByte_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new byte[] { }, -1 },
                { new byte[] { }, 0 },
                { new byte[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexByte_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexByte_FailWithEmptyArray(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1 },
                { new byte[] { 99, 101 }, -1 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 2 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexByte_FailWithInvalidIndex(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexByte_FailWithNullArray(byte[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(short[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexShort_Pass_Data")
    public void checkAccessIndexShort_Pass(short[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexShort_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new short[] { }, -1 },
                { new short[] { }, 0 },
                { new short[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexShort_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexShort_FailWithEmptyArray(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1 },
                { new short[] { 99, 101 }, -1 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 2 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexShort_FailWithInvalidIndex(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexShort_FailWithNullArray(short[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(int[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexInteger_Pass_Data")
    public void checkAccessIndexInteger_Pass(int[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexInteger_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new int[] { }, -1 },
                { new int[] { }, 0 },
                { new int[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexInteger_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexInteger_FailWithEmptyArray(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1 },
                { new int[] { 99, 101 }, -1 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 2 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexInteger_FailWithInvalidIndex(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexInteger_FailWithNullArray(int[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(long[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexLong_Pass_Data")
    public void checkAccessIndexLong_Pass(long[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexLong_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new long[] { }, -1 },
                { new long[] { }, 0 },
                { new long[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexLong_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexLong_FailWithEmptyArray(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1 },
                { new long[] { 99, 101 }, -1 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 2 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexLong_FailWithInvalidIndex(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexLong_FailWithNullArray(long[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(float[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexFloat_Pass_Data")
    public void checkAccessIndexFloat_Pass(float[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexFloat_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new float[] { }, -1 },
                { new float[] { }, 0 },
                { new float[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexFloat_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexFloat_FailWithEmptyArray(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1 },
                { new float[] { 99, 101 }, -1 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 2 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexFloat_FailWithInvalidIndex(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexFloat_FailWithNullArray(float[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(double[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexDouble_Pass_Data")
    public void checkAccessIndexDouble_Pass(double[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexDouble_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new double[] { }, -1 },
                { new double[] { }, 0 },
                { new double[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexDouble_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexDouble_FailWithEmptyArray(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1 },
                { new double[] { 99, 101 }, -1 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 2 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexDouble_FailWithInvalidIndex(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexDouble_FailWithNullArray(double[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(char[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexCharacter_Pass_Data")
    public void checkAccessIndexCharacter_Pass(char[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexCharacter_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new char[] { }, -1 },
                { new char[] { }, 0 },
                { new char[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexCharacter_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexCharacter_FailWithEmptyArray(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1 },
                { new char[] { 99, 101 }, -1 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 2 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexCharacter_FailWithInvalidIndex(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexCharacter_FailWithNullArray(char[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex(boolean[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkAccessIndexBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexBoolean_Pass_Data")
    public void checkAccessIndexBoolean_Pass(boolean[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkAccessIndexBoolean_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new boolean[] { }, -1 },
                { new boolean[] { }, 0 },
                { new boolean[] { }, 1 },
        };
    }

    @Test(dataProvider = "checkAccessIndexBoolean_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkAccessIndexBoolean_FailWithEmptyArray(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static Object[][] checkAccessIndexBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1 },
                { new boolean[] { true, false }, -1 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 2 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkAccessIndexBoolean_FailWithInvalidIndex(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkAccessIndexBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndexBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkAccessIndexBoolean_FailWithNullArray(boolean[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(Object[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexObject_Pass_Data() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexObject_Pass_Data")
    public <T> void checkInsertIndexObject_Pass(T[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { }, -1 },
                { new String[] { "a" }, -1 },
                { new String[] { "a", "b" }, -1 },
                { new String[] { }, 1 },
                { new String[] { "a" }, 2 },
                { new String[] { "a", "b" }, 3 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndexObject_FailWithInvalidIndex(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexObject_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexObject_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkInsertIndexObject_FailWithNullArray(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(byte[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { }, 0 },
                { new byte[] { 99 }, 0 },
                { new byte[] { 99 }, 1 },
                { new byte[] { 99, 101 }, 0 },
                { new byte[] { 99, 101 }, 1 },
                { new byte[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexByte_Pass_Data")
    public void checkInsertIndexByte_Pass(byte[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { }, -1 },
                { new byte[] { 99 }, -1 },
                { new byte[] { 99, 101 }, -1 },
                { new byte[] { }, 1 },
                { new byte[] { 99 }, 2 },
                { new byte[] { 99, 101 }, 3 },
                { new byte[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexByte_FailWithInvalidIndex(byte[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexByte_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexByte_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexByte_FailWithNullArray(byte[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(short[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexShort_Pass_Data() {
        return new Object[][] {
                { new short[] { }, 0 },
                { new short[] { 99 }, 0 },
                { new short[] { 99 }, 1 },
                { new short[] { 99, 101 }, 0 },
                { new short[] { 99, 101 }, 1 },
                { new short[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexShort_Pass_Data")
    public void checkInsertIndexShort_Pass(short[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { }, -1 },
                { new short[] { 99 }, -1 },
                { new short[] { 99, 101 }, -1 },
                { new short[] { }, 1 },
                { new short[] { 99 }, 2 },
                { new short[] { 99, 101 }, 3 },
                { new short[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexShort_FailWithInvalidIndex(short[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexShort_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexShort_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexShort_FailWithNullArray(short[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(int[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { }, 0 },
                { new int[] { 99 }, 0 },
                { new int[] { 99 }, 1 },
                { new int[] { 99, 101 }, 0 },
                { new int[] { 99, 101 }, 1 },
                { new int[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexInteger_Pass_Data")
    public void checkInsertIndexInteger_Pass(int[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { }, -1 },
                { new int[] { 99 }, -1 },
                { new int[] { 99, 101 }, -1 },
                { new int[] { }, 1 },
                { new int[] { 99 }, 2 },
                { new int[] { 99, 101 }, 3 },
                { new int[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexInteger_FailWithInvalidIndex(int[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexInteger_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexInteger_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexInteger_FailWithNullArray(int[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(long[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexLong_Pass_Data() {
        return new Object[][] {
                { new long[] { }, 0 },
                { new long[] { 99 }, 0 },
                { new long[] { 99 }, 1 },
                { new long[] { 99, 101 }, 0 },
                { new long[] { 99, 101 }, 1 },
                { new long[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexLong_Pass_Data")
    public void checkInsertIndexLong_Pass(long[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { }, -1 },
                { new long[] { 99 }, -1 },
                { new long[] { 99, 101 }, -1 },
                { new long[] { }, 1 },
                { new long[] { 99 }, 2 },
                { new long[] { 99, 101 }, 3 },
                { new long[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexLong_FailWithInvalidIndex(long[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexLong_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexLong_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexLong_FailWithNullArray(long[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(float[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { }, 0 },
                { new float[] { 99 }, 0 },
                { new float[] { 99 }, 1 },
                { new float[] { 99, 101 }, 0 },
                { new float[] { 99, 101 }, 1 },
                { new float[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexFloat_Pass_Data")
    public void checkInsertIndexFloat_Pass(float[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { }, -1 },
                { new float[] { 99 }, -1 },
                { new float[] { 99, 101 }, -1 },
                { new float[] { }, 1 },
                { new float[] { 99 }, 2 },
                { new float[] { 99, 101 }, 3 },
                { new float[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexFloat_FailWithInvalidIndex(float[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexFloat_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexFloat_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexFloat_FailWithNullArray(float[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(double[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { }, 0 },
                { new double[] { 99 }, 0 },
                { new double[] { 99 }, 1 },
                { new double[] { 99, 101 }, 0 },
                { new double[] { 99, 101 }, 1 },
                { new double[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexDouble_Pass_Data")
    public void checkInsertIndexDouble_Pass(double[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { }, -1 },
                { new double[] { 99 }, -1 },
                { new double[] { 99, 101 }, -1 },
                { new double[] { }, 1 },
                { new double[] { 99 }, 2 },
                { new double[] { 99, 101 }, 3 },
                { new double[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexDouble_FailWithInvalidIndex(double[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexDouble_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexDouble_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexDouble_FailWithNullArray(double[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(char[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { }, 0 },
                { new char[] { 99 }, 0 },
                { new char[] { 99 }, 1 },
                { new char[] { 99, 101 }, 0 },
                { new char[] { 99, 101 }, 1 },
                { new char[] { 99, 101 }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexCharacter_Pass_Data")
    public void checkInsertIndexCharacter_Pass(char[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { }, -1 },
                { new char[] { 99 }, -1 },
                { new char[] { 99, 101 }, -1 },
                { new char[] { }, 1 },
                { new char[] { 99 }, 2 },
                { new char[] { 99, 101 }, 3 },
                { new char[] { 99, 101 }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexCharacter_FailWithInvalidIndex(char[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexCharacter_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexCharacter_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexCharacter_FailWithNullArray(char[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex(boolean[], int, String, String)
    //

    @DataProvider
    private static Object[][] checkInsertIndexBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { }, 0 },
                { new boolean[] { true }, 0 },
                { new boolean[] { true }, 1 },
                { new boolean[] { true, false }, 0 },
                { new boolean[] { true, false }, 1 },
                { new boolean[] { true, false }, 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexBoolean_Pass_Data")
    public void checkInsertIndexBoolean_Pass(boolean[] ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == ArrayArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    private static Object[][] checkInsertIndexBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { }, -1 },
                { new boolean[] { true }, -1 },
                { new boolean[] { true, false }, -1 },
                { new boolean[] { }, 1 },
                { new boolean[] { true }, 2 },
                { new boolean[] { true, false }, 3 },
                { new boolean[] { true, false }, 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndexBoolean_FailWithInvalidIndex(boolean[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static Object[][] checkInsertIndexBoolean_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndexBoolean_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndexBoolean_FailWithNullArray(boolean[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount(Object[], int, int, String, String, String)
    //

    @DataProvider
    private static Object[][] checkIndexAndCountObject_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountObject_Pass_Data")
    public <T> void checkIndexAndCountObject_Pass(T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountObject_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { }, -1, -1 },
                { new String[] { }, 0, 0 },
                { new String[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountObject_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCountObject_FailWithEmptyArray(T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountObject_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a", "b" }, -1, 0 },
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountObject_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCountObject_FailWithInvalidIndex(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountObject_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a", "b" }, 0, -1 },
                { new String[] { "a", "b" }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountObject_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCountObject_FailWithNegativeCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountObject_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2 },
                { new String[] { "a" }, 0, 99 },
                { new String[] { "a", "b" }, 0, 3 },
                { new String[] { "a", "b" }, 0, 99 },
                { new String[] { "a", "b" }, 1, 3 },
                { new String[] { "a", "b" }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountObject_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountByte_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountByte_Pass_Data")
    public void checkIndexAndCountByte_Pass(byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountByte_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new byte[] { }, -1, -1 },
                { new byte[] { }, 0, 0 },
                { new byte[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountByte_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountByte_FailWithEmptyArray(byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountByte_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1, 0 },
                { new byte[] { 99, 101 }, -1, 0 },
                { new byte[] { 99 }, 1, 1 },
                { new byte[] { 99, 101 }, 3, 1 },
                { new byte[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountByte_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountByte_FailWithInvalidIndex(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountByte_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, -1 },
                { new byte[] { 99, 101 }, 0, -1 },
                { new byte[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountByte_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountByte_FailWithNegativeCount(
            byte[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountByte_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new byte[] { 99 }, 0, 2 },
                { new byte[] { 99 }, 0, 99 },
                { new byte[] { 99, 101 }, 0, 3 },
                { new byte[] { 99, 101 }, 0, 99 },
                { new byte[] { 99, 101 }, 1, 3 },
                { new byte[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountByte_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountShort_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountShort_Pass_Data")
    public void checkIndexAndCountShort_Pass(short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountShort_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new short[] { }, -1, -1 },
                { new short[] { }, 0, 0 },
                { new short[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountShort_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountShort_FailWithEmptyArray(short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountShort_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1, 0 },
                { new short[] { 99, 101 }, -1, 0 },
                { new short[] { 99 }, 1, 1 },
                { new short[] { 99, 101 }, 3, 1 },
                { new short[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountShort_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountShort_FailWithInvalidIndex(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountShort_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, -1 },
                { new short[] { 99, 101 }, 0, -1 },
                { new short[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountShort_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountShort_FailWithNegativeCount(
            short[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountShort_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new short[] { 99 }, 0, 2 },
                { new short[] { 99 }, 0, 99 },
                { new short[] { 99, 101 }, 0, 3 },
                { new short[] { 99, 101 }, 0, 99 },
                { new short[] { 99, 101 }, 1, 3 },
                { new short[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountShort_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountInteger_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountInteger_Pass_Data")
    public void checkIndexAndCountInteger_Pass(int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountInteger_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new int[] { }, -1, -1 },
                { new int[] { }, 0, 0 },
                { new int[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountInteger_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountInteger_FailWithEmptyArray(int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountInteger_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1, 0 },
                { new int[] { 99, 101 }, -1, 0 },
                { new int[] { 99 }, 1, 1 },
                { new int[] { 99, 101 }, 3, 1 },
                { new int[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountInteger_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountInteger_FailWithInvalidIndex(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountInteger_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, -1 },
                { new int[] { 99, 101 }, 0, -1 },
                { new int[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountInteger_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountInteger_FailWithNegativeCount(
            int[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountInteger_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new int[] { 99 }, 0, 2 },
                { new int[] { 99 }, 0, 99 },
                { new int[] { 99, 101 }, 0, 3 },
                { new int[] { 99, 101 }, 0, 99 },
                { new int[] { 99, 101 }, 1, 3 },
                { new int[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountInteger_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountLong_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountLong_Pass_Data")
    public void checkIndexAndCountLong_Pass(long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountLong_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new long[] { }, -1, -1 },
                { new long[] { }, 0, 0 },
                { new long[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountLong_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountLong_FailWithEmptyArray(long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountLong_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1, 0 },
                { new long[] { 99, 101 }, -1, 0 },
                { new long[] { 99 }, 1, 1 },
                { new long[] { 99, 101 }, 3, 1 },
                { new long[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountLong_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountLong_FailWithInvalidIndex(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountLong_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, -1 },
                { new long[] { 99, 101 }, 0, -1 },
                { new long[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountLong_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountLong_FailWithNegativeCount(
            long[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountLong_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new long[] { 99 }, 0, 2 },
                { new long[] { 99 }, 0, 99 },
                { new long[] { 99, 101 }, 0, 3 },
                { new long[] { 99, 101 }, 0, 99 },
                { new long[] { 99, 101 }, 1, 3 },
                { new long[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountLong_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountFloat_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountFloat_Pass_Data")
    public void checkIndexAndCountFloat_Pass(float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountFloat_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new float[] { }, -1, -1 },
                { new float[] { }, 0, 0 },
                { new float[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountFloat_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountFloat_FailWithEmptyArray(float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountFloat_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1, 0 },
                { new float[] { 99, 101 }, -1, 0 },
                { new float[] { 99 }, 1, 1 },
                { new float[] { 99, 101 }, 3, 1 },
                { new float[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountFloat_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountFloat_FailWithInvalidIndex(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountFloat_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, -1 },
                { new float[] { 99, 101 }, 0, -1 },
                { new float[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountFloat_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountFloat_FailWithNegativeCount(
            float[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountFloat_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new float[] { 99 }, 0, 2 },
                { new float[] { 99 }, 0, 99 },
                { new float[] { 99, 101 }, 0, 3 },
                { new float[] { 99, 101 }, 0, 99 },
                { new float[] { 99, 101 }, 1, 3 },
                { new float[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountFloat_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountDouble_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountDouble_Pass_Data")
    public void checkIndexAndCountDouble_Pass(double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountDouble_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new double[] { }, -1, -1 },
                { new double[] { }, 0, 0 },
                { new double[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountDouble_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountDouble_FailWithEmptyArray(double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountDouble_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1, 0 },
                { new double[] { 99, 101 }, -1, 0 },
                { new double[] { 99 }, 1, 1 },
                { new double[] { 99, 101 }, 3, 1 },
                { new double[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountDouble_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountDouble_FailWithInvalidIndex(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountDouble_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, -1 },
                { new double[] { 99, 101 }, 0, -1 },
                { new double[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountDouble_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountDouble_FailWithNegativeCount(
            double[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountDouble_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new double[] { 99 }, 0, 2 },
                { new double[] { 99 }, 0, 99 },
                { new double[] { 99, 101 }, 0, 3 },
                { new double[] { 99, 101 }, 0, 99 },
                { new double[] { 99, 101 }, 1, 3 },
                { new double[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountDouble_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountCharacter_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountCharacter_Pass_Data")
    public void checkIndexAndCountCharacter_Pass(char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountCharacter_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new char[] { }, -1, -1 },
                { new char[] { }, 0, 0 },
                { new char[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountCharacter_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountCharacter_FailWithEmptyArray(char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountCharacter_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1, 0 },
                { new char[] { 99, 101 }, -1, 0 },
                { new char[] { 99 }, 1, 1 },
                { new char[] { 99, 101 }, 3, 1 },
                { new char[] { 99, 101 }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountCharacter_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountCharacter_FailWithInvalidIndex(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountCharacter_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, -1 },
                { new char[] { 99, 101 }, 0, -1 },
                { new char[] { 99, 101 }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountCharacter_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountCharacter_FailWithNegativeCount(
            char[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountCharacter_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new char[] { 99 }, 0, 2 },
                { new char[] { 99 }, 0, 99 },
                { new char[] { 99, 101 }, 0, 3 },
                { new char[] { 99, 101 }, 0, 99 },
                { new char[] { 99, 101 }, 1, 3 },
                { new char[] { 99, 101 }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountCharacter_FailWithInvalidCount_Data",
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
    private static Object[][] checkIndexAndCountBoolean_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCountBoolean_Pass_Data")
    public void checkIndexAndCountBoolean_Pass(boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkIndexAndCount(ref, index, count, null, null, null);
        ArrayArgs.checkIndexAndCount(ref, index, count, "", "", "");
        ArrayArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountBoolean_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new boolean[] { }, -1, -1 },
                { new boolean[] { }, 0, 0 },
                { new boolean[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkIndexAndCountBoolean_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountBoolean_FailWithEmptyArray(boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountBoolean_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1, 0 },
                { new boolean[] { true, false }, -1, 0 },
                { new boolean[] { true }, 1, 1 },
                { new boolean[] { true, false }, 3, 1 },
                { new boolean[] { true, false }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountBoolean_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCountBoolean_FailWithInvalidIndex(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountBoolean_FailWithNegativeCount_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, -1 },
                { new boolean[] { true, false }, 0, -1 },
                { new boolean[] { true, false }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountBoolean_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCountBoolean_FailWithNegativeCount(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkIndexAndCountBoolean_FailWithInvalidCount_Data() {
        return new Object[][] {
                { new boolean[] { true }, 0, 2 },
                { new boolean[] { true }, 0, 99 },
                { new boolean[] { true, false }, 0, 3 },
                { new boolean[] { true, false }, 0, 99 },
                { new boolean[] { true, false }, 1, 3 },
                { new boolean[] { true, false }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCountBoolean_FailWithInvalidCount_Data",
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
    private static Object[][] checkFromAndToIndicesObject_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesObject_Pass_Data")
    public <T> void checkFromAndToIndicesObject_Pass(T[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesObject_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new String[] { }, -1, -1 },
                { new String[] { }, 0, 0 },
                { new String[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesObject_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkFromAndToIndicesObject_FailWithEmptyArray(T[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesObject_FailWithInvalidIndices_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesObject_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesByte_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesByte_Pass_Data")
    public void checkFromAndToIndicesByte_Pass(byte[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesByte_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new byte[] { }, -1, -1 },
                { new byte[] { }, 0, 0 },
                { new byte[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesByte_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesByte_FailWithEmptyArray(
            byte[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesByte_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new byte[] { 99 }, -1, 0 },
                { new byte[] { 99 }, 0, -1 },
                { new byte[] { 99 }, -1, -1 },
                { new byte[] { 99 }, 1, 1 },
                { new byte[] { 99 }, 1, 0 },
                { new byte[] { 99, 101 }, 3, 1 },
                { new byte[] { 99, 101 }, 99, 2 },
                { new byte[] { 99, 101, 103, 105 }, 2, 1 },
                { new byte[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesByte_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesShort_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesShort_Pass_Data")
    public void checkFromAndToIndicesShort_Pass(short[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesShort_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new short[] { }, -1, -1 },
                { new short[] { }, 0, 0 },
                { new short[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesShort_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesShort_FailWithEmptyArray(
            short[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesShort_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new short[] { 99 }, -1, 0 },
                { new short[] { 99 }, 0, -1 },
                { new short[] { 99 }, -1, -1 },
                { new short[] { 99 }, 1, 1 },
                { new short[] { 99 }, 1, 0 },
                { new short[] { 99, 101 }, 3, 1 },
                { new short[] { 99, 101 }, 99, 2 },
                { new short[] { 99, 101, 103, 105 }, 2, 1 },
                { new short[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesShort_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesInteger_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesInteger_Pass_Data")
    public void checkFromAndToIndicesInteger_Pass(int[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesInteger_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new int[] { }, -1, -1 },
                { new int[] { }, 0, 0 },
                { new int[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesInteger_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesInteger_FailWithEmptyArray(
            int[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesInteger_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new int[] { 99 }, -1, 0 },
                { new int[] { 99 }, 0, -1 },
                { new int[] { 99 }, -1, -1 },
                { new int[] { 99 }, 1, 1 },
                { new int[] { 99 }, 1, 0 },
                { new int[] { 99, 101 }, 3, 1 },
                { new int[] { 99, 101 }, 99, 2 },
                { new int[] { 99, 101, 103, 105 }, 2, 1 },
                { new int[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesInteger_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesLong_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesLong_Pass_Data")
    public void checkFromAndToIndicesLong_Pass(long[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesLong_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new long[] { }, -1, -1 },
                { new long[] { }, 0, 0 },
                { new long[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesLong_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesLong_FailWithEmptyArray(
            long[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesLong_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new long[] { 99 }, -1, 0 },
                { new long[] { 99 }, 0, -1 },
                { new long[] { 99 }, -1, -1 },
                { new long[] { 99 }, 1, 1 },
                { new long[] { 99 }, 1, 0 },
                { new long[] { 99, 101 }, 3, 1 },
                { new long[] { 99, 101 }, 99, 2 },
                { new long[] { 99, 101, 103, 105 }, 2, 1 },
                { new long[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesLong_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesFloat_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesFloat_Pass_Data")
    public void checkFromAndToIndicesFloat_Pass(float[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesFloat_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new float[] { }, -1, -1 },
                { new float[] { }, 0, 0 },
                { new float[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesFloat_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesFloat_FailWithEmptyArray(
            float[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesFloat_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new float[] { 99 }, -1, 0 },
                { new float[] { 99 }, 0, -1 },
                { new float[] { 99 }, -1, -1 },
                { new float[] { 99 }, 1, 1 },
                { new float[] { 99 }, 1, 0 },
                { new float[] { 99, 101 }, 3, 1 },
                { new float[] { 99, 101 }, 99, 2 },
                { new float[] { 99, 101, 103, 105 }, 2, 1 },
                { new float[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesFloat_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesDouble_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesDouble_Pass_Data")
    public void checkFromAndToIndicesDouble_Pass(double[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesDouble_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new double[] { }, -1, -1 },
                { new double[] { }, 0, 0 },
                { new double[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesDouble_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesDouble_FailWithEmptyArray(
            double[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesDouble_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new double[] { 99 }, -1, 0 },
                { new double[] { 99 }, 0, -1 },
                { new double[] { 99 }, -1, -1 },
                { new double[] { 99 }, 1, 1 },
                { new double[] { 99 }, 1, 0 },
                { new double[] { 99, 101 }, 3, 1 },
                { new double[] { 99, 101 }, 99, 2 },
                { new double[] { 99, 101, 103, 105 }, 2, 1 },
                { new double[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesDouble_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesCharacter_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesCharacter_Pass_Data")
    public void checkFromAndToIndicesCharacter_Pass(char[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesCharacter_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new char[] { }, -1, -1 },
                { new char[] { }, 0, 0 },
                { new char[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesCharacter_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesCharacter_FailWithEmptyArray(
            char[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesCharacter_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new char[] { 99 }, -1, 0 },
                { new char[] { 99 }, 0, -1 },
                { new char[] { 99 }, -1, -1 },
                { new char[] { 99 }, 1, 1 },
                { new char[] { 99 }, 1, 0 },
                { new char[] { 99, 101 }, 3, 1 },
                { new char[] { 99, 101 }, 99, 2 },
                { new char[] { 99, 101, 103, 105 }, 2, 1 },
                { new char[] { 99, 101, 103, 105 }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesCharacter_FailWithInvalidIndices_Data",
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
    private static Object[][] checkFromAndToIndicesBoolean_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndicesBoolean_Pass_Data")
    public void checkFromAndToIndicesBoolean_Pass(boolean[] ref, int fromIndex, int toIndex) {
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        ArrayArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesBoolean_FailWithEmptyArray_Data() {
        return new Object[][] {
                { new boolean[] { }, -1, -1 },
                { new boolean[] { }, 0, 0 },
                { new boolean[] { }, 1, 1 },
        };
    }

    @Test(dataProvider = "checkFromAndToIndicesBoolean_FailWithEmptyArray_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkFromAndToIndicesBoolean_FailWithEmptyArray(
            boolean[] ref, int index, int count) {
        ArrayArgs.checkFromAndToIndices(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static Object[][] checkFromAndToIndicesBoolean_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { new boolean[] { true }, -1, 0 },
                { new boolean[] { true }, 0, -1 },
                { new boolean[] { true }, -1, -1 },
                { new boolean[] { true }, 1, 1 },
                { new boolean[] { true }, 1, 0 },
                { new boolean[] { true, false }, 3, 1 },
                { new boolean[] { true, false }, 99, 2 },
                { new boolean[] { true, false, true, false }, 2, 1 },
                { new boolean[] { true, false, true, false }, 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndicesBoolean_FailWithInvalidIndices_Data",
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
    private static Object[][] checkContainsObject_Pass_Data() {
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
    
    @Test(dataProvider = "checkContainsObject_Pass_Data")
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
    private static Object[][] checkContainsObject_Fail_Data() {
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
    
    @Test(dataProvider = "checkContainsObject_Fail_Data",
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
    private static Object[][] checkContainsByte_Pass_Data() {
        return new Object[][] {
                { new byte[] { 1 }, (byte) 1 },
                { new byte[] { 1, 2 }, (byte) 2 },
                { new byte[] { 1, 2, 3 }, (byte) 2 },
                { new byte[] { 1, 2, 3, 2 }, (byte) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsByte_Pass_Data")
    public void checkContainsByte_Pass(byte[] ref, byte value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsByte_Fail_Data() {
        return new Object[][] {
                { new byte[] { 1 }, (byte) 99 },
                { new byte[] { 1, 2 }, (byte) 99 },
                { new byte[] { 1, 2, 3 }, (byte) 99 },
                { new byte[] { 1, 2, 3, 2 }, (byte) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsByte_Fail_Data",
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
    private static Object[][] checkContainsShort_Pass_Data() {
        return new Object[][] {
                { new short[] { 1 }, (short) 1 },
                { new short[] { 1, 2 }, (short) 2 },
                { new short[] { 1, 2, 3 }, (short) 2 },
                { new short[] { 1, 2, 3, 2 }, (short) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsShort_Pass_Data")
    public void checkContainsShort_Pass(short[] ref, short value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsShort_Fail_Data() {
        return new Object[][] {
                { new short[] { 1 }, (short) 99 },
                { new short[] { 1, 2 }, (short) 99 },
                { new short[] { 1, 2, 3 }, (short) 99 },
                { new short[] { 1, 2, 3, 2 }, (short) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsShort_Fail_Data",
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
    private static Object[][] checkContainsInteger_Pass_Data() {
        return new Object[][] {
                { new int[] { 1 }, (int) 1 },
                { new int[] { 1, 2 }, (int) 2 },
                { new int[] { 1, 2, 3 }, (int) 2 },
                { new int[] { 1, 2, 3, 2 }, (int) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsInteger_Pass_Data")
    public void checkContainsInteger_Pass(int[] ref, int value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsInteger_Fail_Data() {
        return new Object[][] {
                { new int[] { 1 }, (int) 99 },
                { new int[] { 1, 2 }, (int) 99 },
                { new int[] { 1, 2, 3 }, (int) 99 },
                { new int[] { 1, 2, 3, 2 }, (int) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsInteger_Fail_Data",
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
    private static Object[][] checkContainsLong_Pass_Data() {
        return new Object[][] {
                { new long[] { 1 }, (long) 1 },
                { new long[] { 1, 2 }, (long) 2 },
                { new long[] { 1, 2, 3 }, (long) 2 },
                { new long[] { 1, 2, 3, 2 }, (long) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsLong_Pass_Data")
    public void checkContainsLong_Pass(long[] ref, long value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsLong_Fail_Data() {
        return new Object[][] {
                { new long[] { 1 }, (long) 99 },
                { new long[] { 1, 2 }, (long) 99 },
                { new long[] { 1, 2, 3 }, (long) 99 },
                { new long[] { 1, 2, 3, 2 }, (long) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsLong_Fail_Data",
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
    private static Object[][] checkContainsFloat_Pass_Data() {
        return new Object[][] {
                { new float[] { 1 }, (float) 1 },
                { new float[] { 1, 2 }, (float) 2 },
                { new float[] { 1, 2, 3 }, (float) 2 },
                { new float[] { 1, 2, 3, 2 }, (float) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsFloat_Pass_Data")
    public void checkContainsFloat_Pass(float[] ref, float value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsFloat_Fail_Data() {
        return new Object[][] {
                { new float[] { 1 }, (float) 99 },
                { new float[] { 1, 2 }, (float) 99 },
                { new float[] { 1, 2, 3 }, (float) 99 },
                { new float[] { 1, 2, 3, 2 }, (float) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsFloat_Fail_Data",
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
    private static Object[][] checkContainsDouble_Pass_Data() {
        return new Object[][] {
                { new double[] { 1 }, (double) 1 },
                { new double[] { 1, 2 }, (double) 2 },
                { new double[] { 1, 2, 3 }, (double) 2 },
                { new double[] { 1, 2, 3, 2 }, (double) 2 },
        };
    }
    
    @Test(dataProvider = "checkContainsDouble_Pass_Data")
    public void checkContainsDouble_Pass(double[] ref, double value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsDouble_Fail_Data() {
        return new Object[][] {
                { new double[] { 1 }, (double) 99 },
                { new double[] { 1, 2 }, (double) 99 },
                { new double[] { 1, 2, 3 }, (double) 99 },
                { new double[] { 1, 2, 3, 2 }, (double) 99 },
        };
    }
    
    @Test(dataProvider = "checkContainsDouble_Fail_Data",
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
    private static Object[][] checkContainsCharacter_Pass_Data() {
        return new Object[][] {
                { new char[] { 'a' }, 'a' },
                { new char[] { 'a', 'b' }, 'b' },
                { new char[] { 'a', 'b', 'c' }, 'b' },
                { new char[] { 'a', 'b', 'c', 'b' }, 'b' },
        };
    }
    
    @Test(dataProvider = "checkContainsCharacter_Pass_Data")
    public void checkContainsCharacter_Pass(char[] ref, char value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsCharacter_Fail_Data() {
        return new Object[][] {
                { new char[] { 'a' }, 'x' },
                { new char[] { 'a', 'b' }, 'x' },
                { new char[] { 'a', 'b', 'c' }, 'x' },
                { new char[] { 'a', 'b', 'c', 'b' }, 'x' },
        };
    }
    
    @Test(dataProvider = "checkContainsObject_Fail_Data",
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
    private static Object[][] checkContainsBoolean_Pass_Data() {
        return new Object[][] {
                { new boolean[] { true }, true },
                { new boolean[] { true, false }, false },
                { new boolean[] { true, false, true }, false },
                { new boolean[] { true, false, true, false }, false },
        };
    }
    
    @Test(dataProvider = "checkContainsBoolean_Pass_Data")
    public void checkContainsBoolean_Pass(boolean[] ref, boolean value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == ArrayArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    private static Object[][] checkContainsBoolean_Fail_Data() {
        return new Object[][] {
                { new boolean[] { true }, false },
                { new boolean[] { false }, true },
        };
    }
    
    @Test(dataProvider = "checkContainsObject_Fail_Data",
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
