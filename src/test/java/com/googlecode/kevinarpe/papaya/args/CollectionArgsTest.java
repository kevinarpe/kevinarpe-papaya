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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.googlecode.kevinarpe.papaya.args.CollectionArgs;

public class CollectionArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkSizeRange
    //

    @DataProvider
    private static final Object[][] _checkSizeRange_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0, 10 },
                { new ArrayList<String>(), 0, 0 },
                { ImmutableList.of("a"), 0, 10 },
                { ImmutableList.of("a"), 1, 10 },
                { ImmutableList.of("a"), 0, 1 },
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a", "b"), 0, 10 },
                { ImmutableList.of("a", "b"), 2, 10 },
                { ImmutableList.of("a", "b"), 0, 2 },
                { ImmutableList.of("a", "b"), 2, 2 },
                { ImmutableList.of("a", "b", "c"), 0, 10 },
                { ImmutableList.of("a", "b", "c"), 3, 10 },
                { ImmutableList.of("a", "b", "c"), 0, 3 },
                { ImmutableList.of("a", "b", "c"), 3, 3 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_Pass_Data")
    public <T> void checkSizeRange_Pass(Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, null);
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "");
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "   ");
    }

    @DataProvider
    private static final Object[][] _checkSizeRange_FailWithInvalidMinOrMaxLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 3, 4 },
                { ImmutableList.of("a"), -3, 3 },
                { ImmutableList.of("a", "b"), 1, -3 },
                { ImmutableList.of("a"), -3, -4 },
                { ImmutableList.of("a"), 4, 3 },
                { ImmutableList.of("a", "b", "c"), 6, 7 },
                { ImmutableList.of("a", "b", "c"), 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkSizeRange_FailWithInvalidMinOrMaxLen(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _checkSizeRange_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_checkSizeRange_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkSizeRange_FailWithNullCollection(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
                { ImmutableList.of("a", "b", "c") },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_Pass_Data")
    public <T> void checkNotEmpty_Pass(Collection<T> ref) {
        CollectionArgs.checkNotEmpty(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkNotEmpty(ref, null);
        CollectionArgs.checkNotEmpty(ref, "");
        CollectionArgs.checkNotEmpty(ref, "   ");
    }

    @DataProvider
    private static final Object[][] _checkNotEmpty_FailWithEmptyCollection_Data() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableSet.of() },
                { new ArrayList<String>() },
                { new HashSet<String>() },
        };
    }
    
    @Test(dataProvider = "_checkNotEmpty_FailWithEmptyCollection_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmpty_FailWithEmptyCollection(Collection<T> ref) {
        CollectionArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <T> void checkNotEmpty_FailWithNullCollection() {
        CollectionArgs.checkNotEmpty(null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMinSize
    //

    @DataProvider
    private static final Object[][] _checkMinSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_Pass_Data")
    public <T> void checkMinSize_Pass(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkMinSize(ref, minSize, null);
        CollectionArgs.checkMinSize(ref, minSize, "");
        CollectionArgs.checkMinSize(ref, minSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMinSize_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMinSize_FailWithInvalidMinLen(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMinSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMinSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMinSize_FailWithNullCollection(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMaxSize
    //

    @DataProvider
    private static final Object[][] _checkMaxSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { new ArrayList<String>(), 99 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a"), 99 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_Pass_Data")
    public <T> void checkMaxSize_Pass(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkMaxSize(ref, maxSize, null);
        CollectionArgs.checkMaxSize(ref, maxSize, "");
        CollectionArgs.checkMaxSize(ref, maxSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxSize_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMaxSize_FailWithInvalidMaxLen(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkMaxSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkMaxSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMaxSize_FailWithNullCollection(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkExactSize
    //

    @DataProvider
    private static final Object[][] _checkExactSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_Pass_Data")
    public <T> void checkExactSize_Pass(Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkExactSize(ref, exactSize, null);
        CollectionArgs.checkExactSize(ref, exactSize, "");
        CollectionArgs.checkExactSize(ref, exactSize, "   ");
    }
    
    @DataProvider
    private static final Object[][] _checkExactSize_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkExactSize_FailWithInvalidExactLen(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _checkExactSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkExactSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkExactSize_FailWithNullCollection(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkElementsNotNull
    //

    @DataProvider
    private static final Object[][] _checkElementsNotNull_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
        };
    }
    
    @Test(dataProvider = "_checkElementsNotNull_Pass_Data")
    public <T> void checkElementsNotNull_Pass(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkElementsNotNull(ref, null);
        CollectionArgs.checkElementsNotNull(ref, "");
        CollectionArgs.checkElementsNotNull(ref, "   ");
    }

    @DataProvider
    private static final Object[][] _checkElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { Arrays.asList(new Object[] { null }) },
                { Arrays.asList("a", null) },
                { Arrays.asList(null, "a") },
                { Arrays.asList(null, "a", "b") },
                { Arrays.asList("a", null, "b") },
                { Arrays.asList("a", "b", null) },
        };
    }
    
    @Test(dataProvider = "_checkElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkElementsNotNull_FailWithNullElements(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkElementsNotNull_FailWithNullCollection() {
        CollectionArgs.checkElementsNotNull((Collection<Object>) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkNotEmptyAndElementsNotNull
    //

    @DataProvider
    private static final Object[][] _checkNotEmptyAndElementsNotNull_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndElementsNotNull_Pass_Data")
    public <T> void checkNotEmptyAndElementsNotNull_Pass(Collection<T> ref) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "ref");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, null);
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "");
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "   ");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithEmptyCollection() {
        CollectionArgs.checkNotEmptyAndElementsNotNull(new ArrayList<String>(), "ref");
    }

    @DataProvider
    private static final Object[][] _checkNotEmptyAndElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { null },
                { Arrays.asList(new Object[] { null }) },
                { Arrays.asList("a", null) },
                { Arrays.asList(null, "a") },
                { Arrays.asList(null, "a", "b") },
                { Arrays.asList("a", null, "b") },
                { Arrays.asList("a", "b", null) },
        };
    }
    
    @Test(dataProvider = "_checkNotEmptyAndElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithNullElements(
            Collection<T> ref) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkAccessIndex
    //

    @DataProvider
    private static final Object[][] _checkAccessIndex_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_Pass_Data")
    public <T> void checkAccessIndex_Pass(Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkAccessIndex(ref, index, null, null);
        CollectionArgs.checkAccessIndex(ref, index, "", "");
        CollectionArgs.checkAccessIndex(ref, index, "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkAccessIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), -1 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndex_FailWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkAccessIndex_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkAccessIndex_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkAccessIndex_FailWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkInsertIndex
    //

    @DataProvider
    private static final Object[][] _checkInsertIndex_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_Pass_Data")
    public <T> void checkInsertIndex_Pass(Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkInsertIndex(ref, index, null, null);
        CollectionArgs.checkInsertIndex(ref, index, "", "");
        CollectionArgs.checkInsertIndex(ref, index, "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of(), -1 },
                { ImmutableList.of(), 1 },
                { ImmutableList.of("a"), -1 },
                { ImmutableList.of("a"), 2 },
                { ImmutableList.of("a", "b"), -1 },
                { ImmutableList.of("a", "b"), 3 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndex_FailWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _checkInsertIndex_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_checkInsertIndex_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkInsertIndex_FailWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkIndexAndCount
    //

    @DataProvider
    private static final Object[][] _checkIndexAndCount_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 0 },
                { ImmutableList.of("a"), 0, 1 },
                { ImmutableList.of("a", "b"), 0, 0 },
                { ImmutableList.of("a", "b"), 0, 1 },
                { ImmutableList.of("a", "b"), 0, 2 },
                { ImmutableList.of("a", "b"), 1, 0 },
                { ImmutableList.of("a", "b"), 1, 1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_Pass_Data")
    public <T> void checkIndexAndCount_Pass(Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkIndexAndCount(ref, index, count, null, null, null);
        CollectionArgs.checkIndexAndCount(ref, index, count, "", "", "");
        CollectionArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1, 0 },
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a", "b"), -1, 0 },
                { ImmutableList.of("a", "b"), 3, 1 },
                { ImmutableList.of("a", "b"), 99, 2 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithInvalidIndex(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithNegativeCount_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, -1 },
                { ImmutableList.of("a", "b"), 0, -1 },
                { ImmutableList.of("a", "b"), 1, -1 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithNegativeCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithNegativeCount(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _checkIndexAndCount_FailWithInvalidCount_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 2 },
                { ImmutableList.of("a"), 0, 99 },
                { ImmutableList.of("a", "b"), 0, 3 },
                { ImmutableList.of("a", "b"), 0, 99 },
                { ImmutableList.of("a", "b"), 1, 3 },
                { ImmutableList.of("a", "b"), 1, 99 },
        };
    }
    
    @Test(dataProvider = "_checkIndexAndCount_FailWithInvalidCount_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithInvalidCount(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkIndexAndCount_FailWithNullCollection() {
        CollectionArgs.checkIndexAndCount(null, 0, 0, "ref", "index", "count");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkFromAndToIndices
    //

    @DataProvider
    private static final Object[][] _checkFromAndToIndices_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 0 },
                { ImmutableList.of("a"), 0, 1 },
                { ImmutableList.of("a", "b"), 0, 0 },
                { ImmutableList.of("a", "b"), 0, 1 },
                { ImmutableList.of("a", "b"), 0, 2 },
                { ImmutableList.of("a", "b"), 1, 1 },
                { ImmutableList.of("a", "b"), 1, 2 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndices_Pass_Data")
    public <T> void checkFromAndToIndices_Pass(Collection<T> ref, int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }

    @DataProvider
    private static final Object[][] _checkFromAndToIndices_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1, 0 },
                { ImmutableList.of("a"), 0, -1 },
                { ImmutableList.of("a"), -1, -1 },
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a"), 1, 0 },
                { ImmutableList.of("a", "b"), 3, 1 },
                { ImmutableList.of("a", "b"), 99, 2 },
                { ImmutableList.of("a", "b", "c", "d"), 2, 1 },
                { ImmutableList.of("a", "b", "c", "d"), 2, 99 },
        };
    }
    
    @Test(dataProvider = "_checkFromAndToIndices_FailWithInvalidIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkFromAndToIndices_FailWithInvalidIndices(
            Collection<T> ref, int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkFromAndToIndices_FailWithNullCollection() {
        CollectionArgs.checkFromAndToIndices(null, 0, 0, "ref", "fromIndex", "toIndex");
    }
}
