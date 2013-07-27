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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.argument.StringArgs;

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
    private static final Object[][] _checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { " ", "dummy" },  // ASCII space
                { "\t", "dummy" },
                { "　", "dummy" },  // wide Japanese space
                { "abc", "dummy" },
                { "僕は前に東京に住んでいました。", "dummy" },
                { "我会写中文。", "dummy" },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_Pass_Data")
    public void checkNotEmpty_Pass(String ref, String argName) {
        StringArgs.checkNotEmpty(ref, argName);
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkNotEmpty(ref, null);
        StringArgs.checkNotEmpty(ref, "");
        StringArgs.checkNotEmpty(ref, "   ");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmpty_FailWithEmptyString() {
        StringArgs.checkNotEmpty("", "dummy");
    }
    
    @DataProvider
    private static final Object[][] _checkNotEmpty_FailWithNullString_Data() {
        return new Object[][] {
                { null, "dummy" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "_checkNotEmpty_FailWithNullString_Data")
    public void checkNotEmpty_FailWithNullString(String ref, String argName) {
        StringArgs.checkNotEmpty(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkNotEmptyOrWhitespace
    //
    
    @DataProvider
    private static final Object[][] _checkNotEmptyOrWhitespace_Pass_Data() {
        return new Object[][] {
                { " abc" },  // ASCII space
                { "\tabc" },
                { "　abc" },  // wide japanese space
                { "abc" },
                { "僕は東京に住んでいました。" },
                { "我会写中文。" },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyOrWhitespace_Pass_Data")
    public void checkNotEmptyOrWhitespace_Pass(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "s");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkNotEmptyOrWhitespace(s, null);
        StringArgs.checkNotEmptyOrWhitespace(s, "");
        StringArgs.checkNotEmptyOrWhitespace(s, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkNotEmptyOrWhitespace_FailWithInvalidString_Data() {
        return new Object[][] {
                { "" },
                { "\t" },
                { "   " },  // ASCII space
                { "　　　" },  // wide japanese space
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyOrWhitespace_FailWithInvalidString_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkNotEmptyOrWhitespace_FailWithInvalidString(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "argName");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkLengthRange
    //
    
    @DataProvider
    private static final Object[][] _checkLengthRange_Pass_Data() {
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
    
    @Test(dataProvider = "_checkLengthRange_Pass_Data")
    public void checkLengthRange_Pass(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkLengthRange(s, minLen, maxLen, null);
        StringArgs.checkLengthRange(s, minLen, maxLen, "");
        StringArgs.checkLengthRange(s, minLen, maxLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRange_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "_checkLengthRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkLengthRange_FailWithInvalidMinOrMaxLen(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _checkLengthRange_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkLengthRange_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkLengthRange_FailWithNullString(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMinLength
    //
    
    @DataProvider
    private static final Object[][] _checkMinLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 1 },
                { "abc", 0 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_Pass_Data")
    public void checkMinLength_Pass(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkMinLength(s, minLen, null);
        StringArgs.checkMinLength(s, minLen, "");
        StringArgs.checkMinLength(s, minLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLength_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { "東京", 3 },
                { "abc", -3 },
                { "abc", 4 },
                { "abc", 6 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMinLength_FailWithInvalidMinLen(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _checkMinLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMinLength_FailWithNullString(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMaxLength
    //
    
    @DataProvider
    private static final Object[][] _checkMaxLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 4 },
                { "abc", 5 },
                { "", 0 },
                { "", 2 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_Pass_Data")
    public void checkMaxLength_Pass(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkMaxLength(s, maxLen, null);
        StringArgs.checkMaxLength(s, maxLen, "");
        StringArgs.checkMaxLength(s, maxLen, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLength_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { "東京", 0 },
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkMaxLength_FailWithInvalidMaxLen(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkMaxLength_FailWithNullString(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkExactLength
    //
    
    @DataProvider
    private static final Object[][] _checkExactLength_Pass_Data() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_Pass_Data")
    public void checkExactLength_Pass(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkExactLength(s, len, null);
        StringArgs.checkExactLength(s, len, "");
        StringArgs.checkExactLength(s, len, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLength_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkExactLength_FailWithInvalidExactLen(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    @DataProvider
    private static final Object[][] _checkExactLength_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactLength_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkExactLength_FailWithNullString(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkInsertIndex
    //
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_Pass_Data() {
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
    
    @Test(dataProvider = "_checkInsertIndex_Pass_Data")
    public void checkInsertIndex_Pass(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkInsertIndex(s, index, null, null);
        StringArgs.checkInsertIndex(s, index, "", "");
        StringArgs.checkInsertIndex(s, index, "   ", "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { "東京", -1 },
                { "abc", -3 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkInsertIndex_FailWithNegativeIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { "東京", 3 },
                { "abc", 4 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkInsertIndex_FailWithInvalidIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkInsertIndex_FailWithNullString(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkIndexAndCount
    //
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_Pass_Data() {
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
    
    @Test(dataProvider = "_checkIndexAndCount_Pass_Data")
    public void checkIndexAndCount_Pass(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        StringArgs.checkIndexAndCount(s, index, count, null, null, null);
        StringArgs.checkIndexAndCount(s, index, count, "", "", "");
        StringArgs.checkIndexAndCount(s, index, count, "   ", "   ", "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { "東京", -1, 1 },
                { "abc", -3, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithNegativeIndex_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCount_FailWithNegativeIndex(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { "東京", 2, 2 },
                { "東京", 3, 2 },
                { "abc", 4, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCount_FailWithInvalidIndex(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithNegativeCount_Data() {
        return new Object[][] {
                { "東京", 0, -1 },
                { "abc", 0, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void checkIndexAndCount_FailWithNegativeCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidCount_Data() {
        return new Object[][] {
                { "東京", 1, 2 },
                { "abc", 2, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void checkIndexAndCount_FailWithInvalidCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithNullString_Data() {
        return new Object[][] {
                { null, 4, 2 },
                { null, 6, 2 },
                { null, 0, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithNullString_Data",
            expectedExceptions = NullPointerException.class)
    public void checkIndexAndCount_FailWithNullString(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
}
