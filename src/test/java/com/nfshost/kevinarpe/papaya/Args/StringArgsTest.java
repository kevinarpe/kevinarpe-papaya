/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.Args.StringArgs;

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
    private static final Object[][] _dataForShouldCheckAsNotEmpty() {
        return new Object[][] {
                { " " },  // ASCII space
                { "\t" },
                { "　" },  // wide Japanese space
                { "abc" },
                { "僕は前に東京に住んでいました。" },
                { "我会写中文。" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotEmpty")
    public void shouldCheckAsNotEmpty(String s) {
        StringArgs.checkNotEmpty(s, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyOrWhitespaceWithNullInputs() {
        return new Object[][] {
                { null, null },
                { "abc", null },
                { null, "value" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespaceWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotEmptyWithNullInputs(String s, String argName) {
        StringArgs.checkNotEmpty(s, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithEmptyInput() {
        return new Object[][] {
                { "" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithEmptyInput",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotEmptyWithEmptyInput(String s) {
        StringArgs.checkNotEmpty(s, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkNotEmptyOrWhitespace
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckNotEmptyOrWhitespace() {
        return new Object[][] {
                { " abc" },  // ASCII space
                { "\tabc" },
                { "　abc" },  // wide japanese space
                { "abc" },
                { "僕は東京に住んでいました。" },
                { "我会写中文。" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckNotEmptyOrWhitespace")
    public void shouldCheckNotEmptyOrWhitespace(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "s");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespaceWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckNotEmptyOrWhitespace(String s, String argName) {
        StringArgs.checkNotEmptyOrWhitespace(s, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyOrWhitespace2() {
        return new Object[][] {
                { "" },
                { " " },  // ASCII space
                { "\t" },
                { "　" },  // wide Japanese space
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyOrWhitespace2",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckNotEmptyOrWhitespace2(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkLengthRange
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckLengthRange() {
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
    
    @Test(dataProvider = "_dataForShouldCheckLengthRange")
    public void shouldCheckLengthRange(String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeWithInvalidMinOrMaxLen() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithInvalidMinOrMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckLengthRangeWithInvalidMinOrMaxLen(
            String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeWithNullString() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckLengthRangeWithNullString(
            String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMinLength
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckMinLength() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 1 },
                { "abc", 0 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinLength")
    public void shouldCheckMinLength(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthWithInvalidMinLen() {
        return new Object[][] {
                { "東京", 3 },
                { "abc", -3 },
                { "abc", 4 },
                { "abc", 6 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthWithInvalidMinLen",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinLengthWithInvalidMinLen(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthWithNullString() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinLengthWithNullString(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkMaxLength
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxLength() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "abc", 4 },
                { "abc", 5 },
                { "", 0 },
                { "", 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxLength")
    public void shouldCheckMaxLength(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthWithInvalidMaxLen() {
        return new Object[][] {
                { "東京", 0 },
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithInvalidMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxLengthWithInvalidMaxLen(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthWithNullString() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxLengthWithNullString(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkExactLength
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckExactLength() {
        return new Object[][] {
                { "東京", 2 },
                { "abc", 3 },
                { "", 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactLength")
    public void shouldCheckExactLength(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthWithInvalidExactLen() {
        return new Object[][] {
                { "東京", 1 },
                { "abc", -3 },
                { "abc", 1 },
                { "abc", 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthWithInvalidExactLen",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactLengthWithInvalidExactLen(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthWithNullString() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactLengthWithNullString(String s, int len) {
        StringArgs.checkExactLength(s, len, "s");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkInsertIndex
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckInsertIndex() {
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
    
    @Test(dataProvider = "_dataForShouldCheckInsertIndex")
    public void shouldCheckInsertIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexWithInvalidIndex() {
        return new Object[][] {
                { "東京", -1 },
                { "東京", 3 },
                { "abc", -3 },
                { "abc", 4 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void shouldNotCheckInsertIndexWithInvalidIndex(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexWithNullString() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckInsertIndexWithNullString(String s, int index) {
        StringArgs.checkInsertIndex(s, index, "s", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // StringArgs.checkIndexAndCount
    //
    
    @DataProvider
    private static final Object[][] _dataForShouldCheckIndexAndCount() {
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
    
    @Test(dataProvider = "_dataForShouldCheckIndexAndCount")
    public void shouldCheckIndexAndCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithInvalidIndex() {
        return new Object[][] {
                { "東京", -1, 1 },
                { "東京", 2, 2 },
                { "東京", 3, 2 },
                { "abc", -3, 1 },
                { "abc", 4, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void shouldNotCheckIndexAndCountWithInvalidIndex(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithNegativeCount() {
        return new Object[][] {
                { "東京", 0, -1 },
                { "abc", 0, -1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithNegativeCount",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckIndexAndCountWithNegativeCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithInvalidCount() {
        return new Object[][] {
                { "東京", 1, 2 },
                { "abc", 2, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithInvalidCount",
            expectedExceptions = IndexOutOfBoundsException.class)
    public void shouldNotCheckIndexAndCountWithInvalidCount(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
    
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithNullString() {
        return new Object[][] {
                { null, 4, 2 },
                { null, 6, 2 },
                { null, 0, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckIndexAndCountWithNullString(String s, int index, int count) {
        StringArgs.checkIndexAndCount(s, index, count, "s", "index", "count");
    }
}
