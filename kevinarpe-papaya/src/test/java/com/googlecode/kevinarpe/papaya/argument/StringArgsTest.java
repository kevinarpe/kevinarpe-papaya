package com.googlecode.kevinarpe.papaya.argument;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class StringArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkNotEmpty
    //
    
    @DataProvider
    public static Object[][] checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { " ", "dummy" },  // ASCII space
                { "\t", "dummy" },
                { "　", "dummy" },  // wide Japanese space
                { "abc", "dummy" },
                { "僕は前に東京に住んでいました。", "dummy" },
                { "我会写中文。", "dummy" },
        };
    }
    
    @Test(dataProvider = "checkNotEmpty_Pass_Data")
    public void checkNotEmpty_Pass(String ref, String argName) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkNotEmpty(ref, argName));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == StringArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == StringArgs.checkNotEmpty(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmpty_FailWithEmptyString() {
        StringArgs.checkNotEmpty("", "dummy");
    }
    
    @DataProvider
    public static Object[][] checkNotEmpty_FailWithNullString_Data() {
        return new Object[][] {
                { null, "dummy" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "checkNotEmpty_FailWithNullString_Data")
    public void checkNotEmpty_FailWithNullString(String ref, String argName) {
        StringArgs.checkNotEmpty(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkNotEmptyOrWhitespace
    //
    
    @DataProvider
    public static Object[][] checkNotEmptyOrWhitespace_Pass_Data() {
        return new Object[][] {
                { " abc" },  // ASCII space
                { "\tabc" },
                { "　abc" },  // wide japanese space
                { "abc" },
                { "僕は東京に住んでいました。" },
                { "我会写中文。" },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyOrWhitespace_Pass_Data")
    public void checkNotEmptyOrWhitespace_Pass(String ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkNotEmptyOrWhitespace(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkNotEmptyOrWhitespace(ref, null));
        Assert.assertTrue(ref == StringArgs.checkNotEmptyOrWhitespace(ref, ""));
        Assert.assertTrue(ref == StringArgs.checkNotEmptyOrWhitespace(ref, "   "));
    }
    
    @DataProvider
    public static Object[][] checkNotEmptyOrWhitespace_FailWithInvalidString_Data() {
        return new Object[][] {
                { "" },
                { "\t" },
                { "   " },  // ASCII space
                { "　　　" },  // wide japanese space
        };
    }
    
    @Test(dataProvider = "checkNotEmptyOrWhitespace_FailWithInvalidString_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyOrWhitespace_FailWithInvalidString(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "argName");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkLengthRange
    //
    
    @DataProvider
    public static Object[][] checkLengthRange_Pass_Data() {
        return new Object[][] {
                { "東京", 1, 2 },
                { "abc", 3, 3 },
                { "abc", 1, 3 },
                { "abc", 3, 4 },
                { "abc", 1, 4 },
                { "abc", 0, 4 },
                { "", 0, 4 },
                { "", 0, 0 },
        };
    }
    
    @Test(dataProvider = "checkLengthRange_Pass_Data")
    public void checkLengthRange_Pass(String ref, int minLen, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkLengthRange(ref, minLen, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkLengthRange(ref, minLen, maxLen, null));
        Assert.assertTrue(ref == StringArgs.checkLengthRange(ref, minLen, maxLen, ""));
        Assert.assertTrue(ref == StringArgs.checkLengthRange(ref, minLen, maxLen, "   "));
    }
    
    @DataProvider
    public static Object[][] checkLengthRange_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { "東京", 3, 4 },
                { "abc", -3, 3 },
                { "abc", 1, -3 },
                { "abc", -3, -4 },
                { "abc", 4, 3 },
                { "abc", 6, 7 },
                { "abc", 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRange_FailWithInvalidMinOrMaxLen(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    @DataProvider
    public static Object[][] checkLengthRange_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkLengthRange_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRange_FailWithNullString(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMinLength
    //
    
    @DataProvider
    public static Object[][] checkMinLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 1 },
                { "abc", 0 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLength_Pass_Data")
    public void checkMinLength_Pass(String ref, int minLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkMinLength(ref, minLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkMinLength(ref, minLen, null));
        Assert.assertTrue(ref == StringArgs.checkMinLength(ref, minLen, ""));
        Assert.assertTrue(ref == StringArgs.checkMinLength(ref, minLen, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinLength_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { "東京", 3 },
                { "abc", -3 },
                { "abc", 4 },
                { "abc", 6 },
        };
    }
    
    @Test(dataProvider = "checkMinLength_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLength_FailWithInvalidMinLen(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    @DataProvider
    public static Object[][] checkMinLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLength_FailWithNullString(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMaxLength
    //
    
    @DataProvider
    public static Object[][] checkMaxLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 4 },
                { "abc", 5 },
                { "", 0 },
                { "", 2 },
        };
    }
    
    @Test(dataProvider = "checkMaxLength_Pass_Data")
    public void checkMaxLength_Pass(String ref, int maxLen) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkMaxLength(ref, maxLen, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkMaxLength(ref, maxLen, null));
        Assert.assertTrue(ref == StringArgs.checkMaxLength(ref, maxLen, ""));
        Assert.assertTrue(ref == StringArgs.checkMaxLength(ref, maxLen, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxLength_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { "東京", 0 },
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "checkMaxLength_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLength_FailWithInvalidMaxLen(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    @DataProvider
    public static Object[][] checkMaxLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLength_FailWithNullString(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkExactLength
    //
    
    @DataProvider
    public static Object[][] checkExactLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLength_Pass_Data")
    public void checkExactLength_Pass(String ref, int len) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == StringArgs.checkExactLength(ref, len, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == StringArgs.checkExactLength(ref, len, null));
        Assert.assertTrue(ref == StringArgs.checkExactLength(ref, len, ""));
        Assert.assertTrue(ref == StringArgs.checkExactLength(ref, len, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactLength_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "checkExactLength_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLength_FailWithInvalidExactLen(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    @DataProvider
    public static Object[][] checkExactLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLength_FailWithNullString(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkInsertIndex
    //
    
    @DataProvider
    public static Object[][] checkInsertIndex_Pass_Data() {
        return new Object[][] {
                { "東京", 0 },
                { "東京", 1 },
                { "東京", 2 },
                { "abc", 0 },
                { "abc", 1 },
                { "abc", 2 },
                { "abc", 3 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_Pass_Data")
    public void checkInsertIndex_Pass(String ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == StringArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == StringArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == StringArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == StringArgs.checkInsertIndex(ref, index, "   ", "   "));
    }
    
    @DataProvider
    public static Object[][] checkInsertIndex_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { "東京", -1 },
                { "abc", -3 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithNegativeIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndex_FailWithNegativeIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    public static Object[][] checkInsertIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { "東京", 3 },
                { "abc", 4 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndex_FailWithInvalidIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    public static Object[][] checkInsertIndex_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndex_FailWithNullString(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkIndexAndCount
    //
    
    @DataProvider
    public static Object[][] checkIndexAndCount_Pass_Data() {
        return new Object[][] {
                { "東京", 0, 0 },
                { "東京", 0, 1 },
                { "東京", 0, 2 },
                { "東京", 1, 0 },
                { "東京", 1, 1 },
                { "abc", 0, 0 },
                { "abc", 0, 1 },
                { "abc", 0, 2 },
                { "abc", 0, 3 },
                { "abc", 1, 0 },
                { "abc", 1, 1 },
                { "abc", 1, 2 },
                { "abc", 2, 0 },
                { "abc", 2, 1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_Pass_Data")
    public void checkIndexAndCount_Pass(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkIndexAndCount(s, index, count, null, null, null);
        StringArgs.checkIndexAndCount(s, index, count, "", "", "");
        StringArgs.checkIndexAndCount(s, index, count, "   ", "   ", "   ");
    }
    
    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { "東京", -1, 1 },
                { "abc", -3, 1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithNegativeIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCount_FailWithNegativeIndex(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { "東京", 2, 2 },
                { "東京", 3, 2 },
                { "abc", 4, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCount_FailWithInvalidIndex(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithNegativeCount_Data() {
        return new Object[][] {
                { "東京", 0, -1 },
                { "abc", 0, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCount_FailWithNegativeCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithInvalidCount_Data() {
        return new Object[][] {
                { "東京", 1, 2 },
                { "abc", 2, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCount_FailWithInvalidCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4, 2 },
                { null, 6, 2 },
                { null, 0, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkIndexAndCount_FailWithNullString(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
}
