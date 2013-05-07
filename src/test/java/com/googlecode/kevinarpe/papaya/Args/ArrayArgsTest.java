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
    private static final Object[][] _dataForShouldCheckLengthRangeAsValid() {
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
    
    @Test(dataProvider = "_dataForShouldCheckLengthRangeAsValid")
    public <T> void shouldCheckLengthRangeAsValid(T ref[], int minLen, int maxLen) {
        ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeAsValidWithInvalidMinOrMaxLen() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeAsValidWithInvalidMinOrMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckLengthRangeAsValidWithInvalidMinOrMaxLen(
            T[] ref, int minLen, int maxLen) {
    	ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeAsValidWithNullArray() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckLengthRangeAsValidWithNullArray(
            T[] ref, int minLen, int maxLen) {
    	ArrayArgs.checkLengthRange(ref, minLen, maxLen, "ref");
    }

    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckLengthRangeAsValidWithNullArgName(
            T[] ref, int minLen, int maxLen) {
    	ArrayArgs.checkLengthRange(ref, minLen, maxLen, null);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckLengthRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, 3, "" },
                { null, 4, 3, "   " },  // ASCII spaces
                { null, 6, 7, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckLengthRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckLengthRangeAsValidWithInvalidArgName(
            T[] ref, int minLen, int maxLen, String argName) {
    	ArrayArgs.checkLengthRange(ref, minLen, maxLen, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotEmpty() {
        return new Object[][] {
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
                { new String[] { "a", "b", "c" } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotEmpty")
    public <T> void shouldCheckAsNotEmpty(T[] ref) {
        ArrayArgs.checkNotEmpty(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithEmptyArray() {
        return new Object[][] {
                { new String[] { } },
                { new Object[] { } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithEmptyArray",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckAsNotEmptyWithEmptyArray(T[] ref) {
        ArrayArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckAsNotEmptyWithNullArray() {
        T[] ref = null;
        ArrayArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithEmptyArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckAsNotEmptyWithNullArgName(T[] ref) {
        String argName = null;
        ArrayArgs.checkNotEmpty(ref, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithInvalidArgName() {
        return new Object[][] {
                { new String[] { }, "" },
                { new String[] { }, "   " },  // ASCII spaces
                { new String[] { }, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckAsNotEmptyWithInvalidArgName(T[] ref, String argName) {
        ArrayArgs.checkNotEmpty(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMinLength
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinLengthAsValid() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinLengthAsValid")
    public <T> void shouldCheckMinLengthAsValid(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthAsValidWithInvalidMinLen() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthAsValidWithInvalidMinLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckMinLengthAsValidWithInvalidMinLen(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthAsValidWithNullArray() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMinLengthAsValidWithNullArray(T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, "ref");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMinLengthAsValidWithNullArgName(
            T[] ref, int minLen) {
        ArrayArgs.checkMinLength(ref, minLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinLengthAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinLengthAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckMinLengthAsValidWithInvalidArgName(
            T[] ref, int minLen, String argName) {
        ArrayArgs.checkMinLength(ref, minLen, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkMaxLength
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxLengthAsValid() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { }, 99 },
                { new String[] { "a" }, 1 },
                { new String[] { "a" }, 99 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxLengthAsValid")
    public <T> void shouldCheckMaxLengthAsValid(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthAsValidWithInvalidMaxLen() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthAsValidWithInvalidMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckMaxLengthAsValidWithInvalidMaxLen(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthAsValidWithNullArray() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMaxLengthAsValidWithNullArray(T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, "ref");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMaxLengthAsValidWithNullArgName(
            T[] ref, int maxLen) {
        ArrayArgs.checkMaxLength(ref, maxLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxLengthAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxLengthAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckMaxLengthAsValidWithInvalidArgName(
            T[] ref, int maxLen, String argName) {
        ArrayArgs.checkMaxLength(ref, maxLen, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkExactLength
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactLengthAsValid() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactLengthAsValid")
    public <T> void shouldCheckExactLengthAsValid(T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthAsValidWithInvalidExactLen() {
        return new Object[][] {
                { new String[] { }, -2 },
                { new String[] { }, 2 },
                { new String[] { "a" }, -3 },
                { new String[] { "a" }, 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthAsValidWithInvalidExactLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckExactLengthAsValidWithInvalidExactLen(
            T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthAsValidWithNullArray() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckExactLengthAsValidWithNullArray(
            T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, "ref");
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckExactLengthAsValidWithNullArgName(
            T[] ref, int exactLen) {
        ArrayArgs.checkExactLength(ref, exactLen, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactLengthAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactLengthAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckExactLengthAsValidWithInvalidArgName(
            T[] ref, int exactLen, String argName) {
        ArrayArgs.checkExactLength(ref, exactLen, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkElementsNotNull
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckElementsAsNotNull() {
        return new Object[][] {
                { new String[] { } },
                { new String[] { "a" } },
                { new String[] { "a", "b" } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckElementsAsNotNull")
    public <T> void shouldCheckElementsAsNotNull(T[] ref) {
        ArrayArgs.checkElementsNotNull(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithNullElements() {
        return new Object[][] {
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithNullElements",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithNullElements(T[] ref) {
        ArrayArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckElementsAsNotNullWithNullArray() {
        ArrayArgs.checkElementsNotNull(null, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithNullArgName() {
        return new Object[][] {
                { null },
                { new String[] { null } },
                { new String[] { "a", null } },
                { new String[] { null, "a" } },
                { new String[] { null, "a", "b" } },
                { new String[] { "a", null, "b" } },
                { new String[] { "a", "b", null } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithNullArgName(T[] ref) {
        ArrayArgs.checkElementsNotNull(ref, null);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { new String[] { null }, "" },
                { new String[] { "a", null }, "" },
                
                { null, "   " },
                { new String[] { null }, "   " },
                { new String[] { "a", null }, "   " },
                
                { null, "　　　" },
                { new String[] { null }, "　　　" },
                { new String[] { "a", null }, "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithInvalidArgName(
            T[] ref, String argName) {
        ArrayArgs.checkElementsNotNull(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkAccessIndex
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAccessIndexAsValid() {
        return new Object[][] {
                { new String[] { "a" }, 0 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAccessIndexAsValid")
    public <T> void shouldCheckAccessIndexAsValid(T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithInvalidIndex() {
        return new Object[][] {
                { new String[] { "a" }, -1 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, -1 },
                { new String[] { "a", "b" }, 2 },
                { new String[] { "a", "b" }, 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckAccessIndexAsValidWithInvalidIndex(
            T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithNullArray() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckAccessIndexAsValidWithNullArray(
            T[] ref, int index) {
        ArrayArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithNullArgNames() {
    	String[] L = new String[] { "a" };
        return new Object[][] {
                { null, 4, null, "index" },
                { null, -4, null, "index" },
                { L, 4, "ref", null },
                { L, -4, "ref", null },
                { null, 6, null, null },
                { null, -6, null, null },
                { L, 6, null, null },
                { L, -6, null, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckAccessIndexAsValidWithNullArgNames(
            T[] ref, int index, String collectionArgName, String indexArgName) {
        ArrayArgs.checkAccessIndex(ref, index, collectionArgName, indexArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithInvalidArgNames() {
    	String[] L = new String[] { "a" };
        return new Object[][] {
                { null, 4, "", "index" },
                { null, -4, "", "index" },
                { L, 4, "ref", "" },
                { L, -4, "ref", "" },
                { null, 6, "", "" },
                { null, -6, "", "" },
                { L, 6, "", "" },
                { L, -6, "", "" },
                
                // ASCII spaces
                { null, 4, "   ", "index" },
                { null, -4, "   ", "index" },
                { L, 4, "ref", "   " },
                { L, -4, "ref", "   " },
                { null, 6, "   ", "   " },
                { null, -6, "   ", "   " },
                { L, 6, "   ", "   " },
                { L, -6, "   ", "   " },
                
                // wide Japanese spaces
                { null, 4, "　　　", "index" },
                { null, -4, "　　　", "index" },
                { L, 4, "ref", "　　　" },
                { L, -4, "ref", "　　　" },
                { null, 6, "　　　", "　　　" },
                { null, -6, "　　　", "　　　" },
                { L, 6, "　　　", "　　　" },
                { L, -6, "　　　", "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckAccessIndexAsValidWithInvalidArgNames(
            T[] ref, int index, String collectionArgName, String indexArgName) {
        ArrayArgs.checkAccessIndex(ref, index, collectionArgName, indexArgName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkInsertIndex
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckInsertIndexAsValid() {
        return new Object[][] {
                { new String[] { }, 0 },
                { new String[] { "a" }, 0 },
                { new String[] { "a" }, 1 },
                { new String[] { "a", "b" }, 0 },
                { new String[] { "a", "b" }, 1 },
                { new String[] { "a", "b" }, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckInsertIndexAsValid")
    public <T> void shouldCheckInsertIndexAsValid(T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithInvalidIndex() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckInsertIndexAsValidWithInvalidIndex(
            T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithNullArray() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithNullArray",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckInsertIndexAsValidWithNullArray(
            T[] ref, int index) {
        ArrayArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithNullArgNames() {
        String[] L = new String[] { "a" };
        return new Object[][] {
                { null, 4, null, "index" },
                { null, -4, null, "index" },
                { L, 4, "ref", null },
                { L, -4, "ref", null },
                { null, 6, null, null },
                { null, -6, null, null },
                { L, 6, null, null },
                { L, -6, null, null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckInsertIndexAsValidWithNullArgNames(
            T[] ref, int index, String collectionArgName, String indexArgName) {
        ArrayArgs.checkInsertIndex(ref, index, collectionArgName, indexArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithInvalidArgNames() {
        String[] L = new String[] { "a" };
        return new Object[][] {
                { null, 4, "", "index" },
                { null, -4, "", "index" },
                { L, 4, "ref", "" },
                { L, -4, "ref", "" },
                { null, 6, "", "" },
                { null, -6, "", "" },
                { L, 6, "", "" },
                { L, -6, "", "" },
                
                // ASCII spaces
                { null, 4, "   ", "index" },
                { null, -4, "   ", "index" },
                { L, 4, "ref", "   " },
                { L, -4, "ref", "   " },
                { null, 6, "   ", "   " },
                { null, -6, "   ", "   " },
                { L, 6, "   ", "   " },
                { L, -6, "   ", "   " },
                
                // wide Japanese spaces
                { null, 4, "　　　", "index" },
                { null, -4, "　　　", "index" },
                { L, 4, "ref", "　　　" },
                { L, -4, "ref", "　　　" },
                { null, 6, "　　　", "　　　" },
                { null, -6, "　　　", "　　　" },
                { L, 6, "　　　", "　　　" },
                { L, -6, "　　　", "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckInsertIndexAsValidWithInvalidArgNames(
            T[] ref, int index, String collectionArgName, String indexArgName) {
        ArrayArgs.checkInsertIndex(ref, index, collectionArgName, indexArgName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ArrayArgs.checkIndexAndCount
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckIndexAndCountAsValid() {
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
    
    @Test(dataProvider = "_dataForShouldCheckIndexAndCountAsValid")
    public <T> void shouldCheckIndexAndCountAsValid(T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidIndex() {
        return new Object[][] {
                { new String[] { "a" }, -1, 0 },
                { new String[] { "a" }, 1, 1 },
                { new String[] { "a", "b" }, -1, 0 },
                { new String[] { "a", "b" }, 3, 1 },
                { new String[] { "a", "b" }, 99, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidIndex(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithNegativeCount() {
        return new Object[][] {
                { new String[] { "a" }, 0, -1 },
                { new String[] { "a", "b" }, 0, -1 },
                { new String[] { "a", "b" }, 1, -1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithNegativeCount",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithNegativeCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidCount() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2 },
                { new String[] { "a" }, 0, 99 },
                { new String[] { "a", "b" }, 0, 3 },
                { new String[] { "a", "b" }, 0, 99 },
                { new String[] { "a", "b" }, 1, 3 },
                { new String[] { "a", "b" }, 1, 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidCount",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidCount(
            T[] ref, int index, int count) {
        ArrayArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckIndexAndCountAsValidWithNullArray() {
        ArrayArgs.checkIndexAndCount(null, 0, 0, "ref", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithNullArgNames() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2, null, "index", "count" },
                { new String[] { "a" }, 0, 99, "ref", null, "count" },
                { new String[] { "a", "b" }, 0, 3, "ref", "index", null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithNullArgNames(
            T[] ref,
            int index,
            int count,
            String collectionArgName,
            String indexArgName,
            String countArgName) {
        ArrayArgs.checkIndexAndCount(
            ref, index, count, collectionArgName, indexArgName, countArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidArgNames() {
        return new Object[][] {
                { new String[] { "a" }, 0, 2, "", "index", "count" },
                { new String[] { "a" }, 0, 99, "ref", "", "count" },
                { new String[] { "a", "b" }, 0, 3, "ref", "index", "" },
                
                { new String[] { "a" }, 0, 2, "   ", "index", "count" },
                { new String[] { "a" }, 0, 99, "ref", "   ", "count" },
                { new String[] { "a", "b" }, 0, 3, "ref", "index", "   " },
                
                { new String[] { "a" }, 0, 2, "　　　", "index", "count" },
                { new String[] { "a" }, 0, 99, "ref", "　　　", "count" },
                { new String[] { "a", "b" }, 0, 3, "ref", "index", "　　　" },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidArgNames(
            T[] ref,
            int index,
            int count,
            String collectionArgName,
            String indexArgName,
            String countArgName) {
        ArrayArgs.checkIndexAndCount(
            ref, index, count, collectionArgName, indexArgName, countArgName);
    }
}
