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
    // ArrayArgs.checkLengthRange
    //

    @DataProvider
    private static final Object[][] _checkLengthRange_Pass_Data() {
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
    
    @Test(dataProvider = "_checkLengthRange_Pass_Data")
    public <T> void checkLengthRange_Pass(T ref[], int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, null);
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "");
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "   ");
    }

    @DataProvider
    private static final Object[][] _checkLengthRange_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "_checkLengthRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkLengthRange_FailWithInvalidMinOrMaxLen(T[] ref, int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRange_FailWithNullArray_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRange_FailWithNullArray_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkLengthRange_FailWithNullArray(T[] ref, int minLen, int maxLen) {
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
    // ArrayArgs.checkContains
    //
    
    @DataProvider
    private static final Object[][] _checkContains_Pass_Data() {
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
    
    @Test(dataProvider = "_checkContains_Pass_Data")
    public <THaystack, TNeedle extends THaystack>
    void checkContains_Pass(THaystack[] ref, TNeedle value) {
        ArrayArgs.checkContains(ref, value, "ref");
        // Demonstrate argName can be anything ridiculous.
        ArrayArgs.checkContains(ref, value, null);
        ArrayArgs.checkContains(ref, value, "");
        ArrayArgs.checkContains(ref, value, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkContains_Fail_Data() {
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
    
    @Test(dataProvider = "_checkContains_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <THaystack, TNeedle extends THaystack>
    void checkContains_Fail(THaystack[] ref, TNeedle value) {
        ArrayArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContains_FailWithEmptyArray() {
        ArrayArgs.checkContains(new Object[0], "abc", "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContains_FailWithNullArray() {
        ArrayArgs.checkContains((Object[]) null, "abc", "ref");
    }
}
