package com.googlecode.kevinarpe.papaya.Args;

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

import com.googlecode.kevinarpe.papaya.args.StringArgs;

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
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotEmptyWithEmptyString() {
        StringArgs.checkNotEmpty("", "s");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotEmptyWithNullString() {
        StringArgs.checkNotEmpty(null, "argName");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithNullArgName() {
        return new Object[][] {
                { null },
                { "" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckAsNotEmptyWithNullArgName(String s) {
        StringArgs.checkNotEmpty(s, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
                
                { "", "" },
                { "", "   " },  // ASCII spaces
                { "", "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckAsNotEmptyWithInvalidArgName(String s, String argName) {
        StringArgs.checkNotEmpty(s, argName);
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
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckNotEmptyOrWhitespaceWithInvalidString() {
        return new Object[][] {
                { "" },
                { "\t" },
                { "   " },  // ASCII space
                { "　　　" },  // wide japanese space
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckNotEmptyOrWhitespaceWithInvalidString",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckNotEmptyOrWhitespaceWithInvalidString(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, "argName");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckNotEmptyOrWhitespaceWithInvalidString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckNotEmptyOrWhitespaceWithNullArgName(String s) {
        StringArgs.checkNotEmptyOrWhitespace(s, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckNotEmptyOrWhitespaceWithInvalidArgName() {
        return new Object[][] {
        		{ null, "" },
                { "", "" },
                { "\t", "" },
                { "   ", "" },  // ASCII space
                { "　　　", "" },  // wide japanese space
                
        		{ null, "\t" },
                { "", "\t" },
                { "\t", "\t" },
                { "   ", "\t" },  // ASCII space
                { "　　　", "\t" },  // wide japanese space
                
                // ASCII space (arg #2)
        		{ null, "   " },
                { "", "   " },
                { "\t", "   " },
                { "   ", "   " },  // ASCII space
                { "　　　", "   " },  // wide japanese space
                
                // wide japanese space (arg #2)
        		{ null, "　　　" },
                { "", "　　　" },
                { "\t", "　　　" },
                { "   ", "　　　" },  // ASCII space
                { "　　　", "　　　" },  // wide japanese space
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckNotEmptyOrWhitespaceWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckNotEmptyOrWhitespaceWithInvalidArgName(String s, String argName) {
        StringArgs.checkNotEmptyOrWhitespace(s, argName);
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
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckLengthRangeWithNullArgName(
            String s, int minLen, int maxLen) {
        StringArgs.checkLengthRange(s, minLen, maxLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeWithInvalidArgName() {
        return new Object[][] {
                { null, 4, 3, "" },
                { null, 6, 7, "   " },  // ASCII spaces
                { null, 6, 7, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckLengthRangeWithInvalidArgName(
            String s, int minLen, int maxLen, String argName) {
        StringArgs.checkLengthRange(s, minLen, maxLen, argName);
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
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMinLengthWithNullArgName(String s, int minLen) {
        StringArgs.checkMinLength(s, minLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 6, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMinLengthWithInvalidArgName(
            String s, int minLen, String argName) {
        StringArgs.checkMinLength(s, minLen, argName);
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
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckMaxLengthWithNullArgName(String s, int maxLen) {
        StringArgs.checkMaxLength(s, maxLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 6, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckMaxLengthWithInvalidArgName(
            String s, int maxLen, String argName) {
        StringArgs.checkMaxLength(s, maxLen, argName);
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
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthWithNullString",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckExactLengthWithNullArgName(String s, int len) {
        StringArgs.checkExactLength(s, len, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 6, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckExactLengthWithInvalidArgName(
            String s, int exactLen, String argName) {
        StringArgs.checkExactLength(s, exactLen, argName);
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
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexWithNullArgNames() {
        return new Object[][] {
                { null, 4, "ref", null },
                { null, 4, null, "index" },
                { null, 6, null, null },
                { "abc", -4, "ref", null },
                { "abc", -4, null, "index" },
                { "abc", -6, null, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckInsertIndexWithNullArgNames(
    		String s, int index, String refArgName, String indexArgName) {
        StringArgs.checkInsertIndex(s, index, refArgName, indexArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexWithInvalidArgNames() {
        return new Object[][] {
                { null, 4, "", "index" },
                { null, 6, "", "" },
                { "abc", -4, "ref", "" },
                { "abc", -6, "", "" },
                
                // ASCII spaces
                { null, 4, "   ", "index" },
                { null, 6, "   ", "   " },
                { "abc", -4, "ref", "   " },
                { "abc", -6, "   ", "   " },
                
                // wide Japanese spaces
                { null, 4, "　　　", "index" },
                { null, 6, "　　　", "　　　" },
                { "abc", -4, "ref", "　　　" },
                { "abc", -6, "　　　", "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckInsertIndexWithInvalidArgNames(
    		String s, int index, String refArgName, String indexArgName) {
        StringArgs.checkInsertIndex(s, index, refArgName, indexArgName);
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
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithNullArgNames() {
        return new Object[][] {
                { null, 4, 2, "ref", null, null },
                { null, 6, 2, null, "index", null },
                { null, 0, 2, null, null, "count" },
                { null, 0, 2, null, null, null },
                
                { null, -4, 2, "ref", null, null },
                { null, -6, 2, null, "index", null },
                { null, -0, 2, null, null, "count" },
                { null, -0, 2, null, null, null },
                
                { null, 4, -2, "ref", null, null },
                { null, 6, -2, null, "index", null },
                { null, 0, -2, null, null, "count" },
                { null, 0, -2, null, null, null },
                
                { null, -4, -2, "ref", null, null },
                { null, -6, -2, null, "index", null },
                { null, -0, -2, null, null, "count" },
                { null, -0, -2, null, null, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckIndexAndCountWithNullArgNames(
    		String s,
    		int index,
    		int count,
    		String refArgName,
    		String indexArgName,
    		String countArgName) {
        StringArgs.checkIndexAndCount(s, index, count, refArgName, indexArgName, countArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountWithInvalidArgNames() {
        return new Object[][] {
                { null, 6, 2, "", "index", "" },
                { null, 0, 2, "", "", "count" },
                { null, 0, 2, "", "", "" },
                
                { null, -6, 2, "", "index", "" },
                { null, -0, 2, "", "", "count" },
                { null, -0, 2, "", "", "" },
                
                { null, 6, -2, "", "index", "" },
                { null, 0, -2, "", "", "count" },
                { null, 0, -2, "", "", "" },
                
                { null, -6, -2, "", "index", "" },
                { null, -0, -2, "", "", "count" },
                { null, -0, -2, "", "", "" },
                
                // ASCII spaces
                { null, 6, 2, "   ", "index", "   " },
                { null, 0, 2, "   ", "   ", "count" },
                { null, 0, 2, "   ", "   ", "   " },
                
                { null, -6, 2, "   ", "index", "   " },
                { null, -0, 2, "   ", "   ", "count" },
                { null, -0, 2, "   ", "   ", "   " },
                
                { null, 6, -2, "   ", "index", "   " },
                { null, 0, -2, "   ", "   ", "count" },
                { null, 0, -2, "   ", "   ", "   " },
                
                { null, -6, -2, "   ", "index", "   " },
                { null, -0, -2, "   ", "   ", "count" },
                { null, -0, -2, "   ", "   ", "   " },
                
                // wide Japanese spaces
                { null, 6, 2, "　　　", "index", "　　　" },
                { null, 0, 2, "　　　", "　　　", "count" },
                { null, 0, 2, "　　　", "　　　", "　　　" },
                
                { null, -6, 2, "　　　", "index", "　　　" },
                { null, -0, 2, "　　　", "　　　", "count" },
                { null, -0, 2, "　　　", "　　　", "　　　" },
                
                { null, 6, -2, "　　　", "index", "　　　" },
                { null, 0, -2, "　　　", "　　　", "count" },
                { null, 0, -2, "　　　", "　　　", "　　　" },
                
                { null, -6, -2, "　　　", "index", "　　　" },
                { null, -0, -2, "　　　", "　　　", "count" },
                { null, -0, -2, "　　　", "　　　", "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCheckIndexAndCountWithInvalidArgNames(
    		String s,
    		int index,
    		int count,
    		String refArgName,
    		String indexArgName,
    		String countArgName) {
        StringArgs.checkIndexAndCount(s, index, count, refArgName, indexArgName, countArgName);
    }
}
