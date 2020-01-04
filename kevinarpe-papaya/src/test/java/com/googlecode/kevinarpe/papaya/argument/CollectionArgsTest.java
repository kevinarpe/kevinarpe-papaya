package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

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
    public static Object[][] checkSizeRange_Pass_Data() {
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
    
    @Test(dataProvider = "checkSizeRange_Pass_Data")
    public <T> void checkSizeRange_Pass(Collection<T> ref, int minSize, int maxSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkSizeRange(ref, minSize, maxSize, null));
        Assert.assertTrue(ref == CollectionArgs.checkSizeRange(ref, minSize, maxSize, ""));
        Assert.assertTrue(ref == CollectionArgs.checkSizeRange(ref, minSize, maxSize, "   "));
    }

    @DataProvider
    public static Object[][] checkSizeRange_FailWithInvalidMinOrMaxLen_Data() {
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
    
    @Test(dataProvider = "checkSizeRange_FailWithInvalidMinOrMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkSizeRange_FailWithInvalidMinOrMaxLen(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    public static Object[][] checkSizeRange_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "checkSizeRange_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkSizeRange_FailWithNullCollection(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkNotEmpty
    //

    @DataProvider
    public static Object[][] checkNotEmpty_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
                { ImmutableList.of("a", "b", "c") },
        };
    }
    
    @Test(dataProvider = "checkNotEmpty_Pass_Data")
    public <T> void checkNotEmpty_Pass(Collection<T> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkNotEmpty(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkNotEmpty(ref, null));
        Assert.assertTrue(ref == CollectionArgs.checkNotEmpty(ref, ""));
        Assert.assertTrue(ref == CollectionArgs.checkNotEmpty(ref, "   "));
    }

    @DataProvider
    public static Object[][] checkNotEmpty_FailWithEmptyCollection_Data() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableSet.of() },
                { new ArrayList<String>() },
                { new HashSet<String>() },
        };
    }
    
    @Test(dataProvider = "checkNotEmpty_FailWithEmptyCollection_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmpty_FailWithEmptyCollection(Collection<T> ref) {
        CollectionArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <T> void checkNotEmpty_FailWithNullCollection() {
        CollectionArgs.checkNotEmpty((Collection<Object>) null, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMinSize
    //

    @DataProvider
    public static Object[][] checkMinSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_Pass_Data")
    public <T> void checkMinSize_Pass(Collection<T> ref, int minSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkMinSize(ref, minSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkMinSize(ref, minSize, null));
        Assert.assertTrue(ref == CollectionArgs.checkMinSize(ref, minSize, ""));
        Assert.assertTrue(ref == CollectionArgs.checkMinSize(ref, minSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMinSize_FailWithInvalidMinLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_FailWithInvalidMinLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMinSize_FailWithInvalidMinLen(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkMinSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMinSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMinSize_FailWithNullCollection(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMaxSize
    //

    @DataProvider
    public static Object[][] checkMaxSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { new ArrayList<String>(), 99 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a"), 99 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_Pass_Data")
    public <T> void checkMaxSize_Pass(Collection<T> ref, int maxSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkMaxSize(ref, maxSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkMaxSize(ref, maxSize, null));
        Assert.assertTrue(ref == CollectionArgs.checkMaxSize(ref, maxSize, ""));
        Assert.assertTrue(ref == CollectionArgs.checkMaxSize(ref, maxSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkMaxSize_FailWithInvalidMaxLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_FailWithInvalidMaxLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkMaxSize_FailWithInvalidMaxLen(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkMaxSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkMaxSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkMaxSize_FailWithNullCollection(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkExactSize
    //

    @DataProvider
    public static Object[][] checkExactSize_Pass_Data() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_Pass_Data")
    public <T> void checkExactSize_Pass(Collection<T> ref, int exactSize) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkExactSize(ref, exactSize, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkExactSize(ref, exactSize, null));
        Assert.assertTrue(ref == CollectionArgs.checkExactSize(ref, exactSize, ""));
        Assert.assertTrue(ref == CollectionArgs.checkExactSize(ref, exactSize, "   "));
    }
    
    @DataProvider
    public static Object[][] checkExactSize_FailWithInvalidExactLen_Data() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_FailWithInvalidExactLen_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkExactSize_FailWithInvalidExactLen(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    public static Object[][] checkExactSize_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkExactSize_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkExactSize_FailWithNullCollection(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkElementsNotNull
    //

    @DataProvider
    public static Object[][] checkElementsNotNull_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
        };
    }
    
    @Test(dataProvider = "checkElementsNotNull_Pass_Data")
    public <T> void checkElementsNotNull_Pass(Collection<T> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkElementsNotNull(ref, null));
        Assert.assertTrue(ref == CollectionArgs.checkElementsNotNull(ref, ""));
        Assert.assertTrue(ref == CollectionArgs.checkElementsNotNull(ref, "   "));
    }

    @DataProvider
    public static Object[][] checkElementsNotNull_FailWithNullElements_Data() {
        return new Object[][] {
                { Arrays.asList(new Object[] { null }) },
                { Arrays.asList("a", null) },
                { Arrays.asList(null, "a") },
                { Arrays.asList(null, "a", "b") },
                { Arrays.asList("a", null, "b") },
                { Arrays.asList("a", "b", null) },
        };
    }
    
    @Test(dataProvider = "checkElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkElementsNotNull_FailWithNullElements(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkElementsNotNull_FailWithNullCollection() {
        CollectionArgs.checkElementsNotNull((Collection<Object>) null, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkElementsUnique
    //

    @DataProvider
    public static Object[][] checkElementsUnique_Pass_Data() {
        return new Object[][] {
            { Arrays.<Object>asList() },
            { Arrays.asList(new Object[] { null }) },
            { Arrays.asList("a") },
            { Arrays.asList("a", null) },
            { Arrays.asList("a", "b") },
            { Arrays.asList("a", null, "b") },
        };
    }

    @Test(dataProvider = "checkElementsUnique_Pass_Data")
    public <T> void checkElementsUnique_Pass(Collection<T> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkElementsUnique(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkElementsUnique(ref, null));
        Assert.assertTrue(ref == CollectionArgs.checkElementsUnique(ref, ""));
        Assert.assertTrue(ref == CollectionArgs.checkElementsUnique(ref, "   "));
    }

    @DataProvider
    public static Object[][] checkElementsUnique_FailWithDuplicateElements_Data() {
        return new Object[][] {
            { Arrays.<Object>asList(null, null) },
            { Arrays.asList(null, null, "a") },
            { Arrays.asList("a", "a") },
            { Arrays.asList("a", "a", null) },
            { Arrays.asList("a", "b", "a") },
        };
    }

    @Test(dataProvider = "checkElementsUnique_FailWithDuplicateElements_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkElementsUnique_FailWithNullElements(Collection<T> ref) {
        CollectionArgs.checkElementsUnique(ref, "ref");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void checkElementsUnique_FailWithNullCollection() {
        CollectionArgs.checkElementsUnique((Collection<Object>) null, "ref");
    }

    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkNotEmptyAndElementsNotNull
    //

    @DataProvider
    public static Object[][] checkNotEmptyAndElementsNotNull_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
        };
    }
    
    @Test(dataProvider = "checkNotEmptyAndElementsNotNull_Pass_Data")
    public <T> void checkNotEmptyAndElementsNotNull_Pass(Collection<T> ref) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(ref == CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(ref == CollectionArgs.checkNotEmptyAndElementsNotNull(ref, null));
        Assert.assertTrue(ref == CollectionArgs.checkNotEmptyAndElementsNotNull(ref, ""));
        Assert.assertTrue(ref == CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "   "));
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithEmptyCollection() {
        CollectionArgs.checkNotEmptyAndElementsNotNull(new ArrayList<String>(), "ref");
    }

    @DataProvider
    public static Object[][] checkNotEmptyAndElementsNotNull_FailWithNullElements_Data() {
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
    
    @Test(dataProvider = "checkNotEmptyAndElementsNotNull_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkNotEmptyAndElementsNotNull_FailWithNullElements(
            Collection<T> ref) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(ref, "ref");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkAccessIndex
    //

    @DataProvider
    public static Object[][] checkAccessIndex_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndex_Pass_Data")
    public <T> void checkAccessIndex_Pass(Collection<T> ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == CollectionArgs.checkAccessIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == CollectionArgs.checkAccessIndex(ref, index, null, null));
        Assert.assertTrue(index == CollectionArgs.checkAccessIndex(ref, index, "", ""));
        Assert.assertTrue(index == CollectionArgs.checkAccessIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    public static Object[][] checkAccessIndex_FailWithEmptyCollection_Data() {
        return new Object[][] {
                { ImmutableList.of(), -1 },
                { ImmutableList.of(), -1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndex_FailWithEmptyCollection_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkAccessIndex_FailWithEmptyCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    public static Object[][] checkAccessIndex_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1 },
                { ImmutableList.of("a", "b"), -1 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndex_FailWithNegativeIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndex_FailWithNegativeIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    public static Object[][] checkAccessIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkAccessIndex_FailWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    public static Object[][] checkAccessIndex_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkAccessIndex_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkAccessIndex_FailWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkInsertIndex
    //

    @DataProvider
    public static Object[][] checkInsertIndex_Pass_Data() {
        return new Object[][] {
                { ImmutableList.of(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_Pass_Data")
    public <T> void checkInsertIndex_Pass(Collection<T> ref, int index) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(index == CollectionArgs.checkInsertIndex(ref, index, "ref", "index"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(index == CollectionArgs.checkInsertIndex(ref, index, null, null));
        Assert.assertTrue(index == CollectionArgs.checkInsertIndex(ref, index, "", ""));
        Assert.assertTrue(index == CollectionArgs.checkInsertIndex(ref, index, "   ", "   "));
    }

    @DataProvider
    public static Object[][] checkInsertIndex_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { ImmutableList.of(), -1 },
                { ImmutableList.of("a"), -1 },
                { ImmutableList.of("a", "b"), -1 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithNegativeIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndex_FailWithNegativeIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    public static Object[][] checkInsertIndex_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of(), 1 },
                { ImmutableList.of("a"), 2 },
                { ImmutableList.of("a", "b"), 3 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkInsertIndex_FailWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    public static Object[][] checkInsertIndex_FailWithNullCollection_Data() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "checkInsertIndex_FailWithNullCollection_Data",
            expectedExceptions = NullPointerException.class)
    public <T> void checkInsertIndex_FailWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkIndexAndCount
    //

    @DataProvider
    public static Object[][] checkIndexAndCount_Pass_Data() {
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
    
    @Test(dataProvider = "checkIndexAndCount_Pass_Data")
    public <T> void checkIndexAndCount_Pass(Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkIndexAndCount(ref, index, count, null, null, null);
        CollectionArgs.checkIndexAndCount(ref, index, count, "", "", "");
        CollectionArgs.checkIndexAndCount(ref, index, count, "   ", "   ", "   ");
    }

    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithEmptyCollection_Data() {
        return new Object[][] {
                { ImmutableList.of(), -1, -1 },
                { ImmutableList.of(), 0, 0 },
                { ImmutableList.of(), 1, 1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithEmptyCollection_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCount_FailWithEmptyCollection(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithNegativeIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1, 0 },
                { ImmutableList.of("a"), -99, 0 },
                { ImmutableList.of("a"), -99, 1 },
                { ImmutableList.of("a", "b"), -1, 0 },
                { ImmutableList.of("a", "b"), -99, 0 },
                { ImmutableList.of("a", "b"), -99, 1 },
                { ImmutableList.of("a", "b"), -99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithNegativeIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithNegativeIndex(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithInvalidIndex_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a", "b"), 3, 1 },
                { ImmutableList.of("a", "b"), 99, 2 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithInvalidIndex_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkIndexAndCount_FailWithInvalidIndex(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithNegativeCount_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, -1 },
                { ImmutableList.of("a", "b"), 0, -1 },
                { ImmutableList.of("a", "b"), 1, -1 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithNegativeCount_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkIndexAndCount_FailWithNegativeCount(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    public static Object[][] checkIndexAndCount_FailWithInvalidCount_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 2 },
                { ImmutableList.of("a"), 0, 99 },
                { ImmutableList.of("a", "b"), 0, 3 },
                { ImmutableList.of("a", "b"), 0, 99 },
                { ImmutableList.of("a", "b"), 1, 3 },
                { ImmutableList.of("a", "b"), 1, 99 },
        };
    }
    
    @Test(dataProvider = "checkIndexAndCount_FailWithInvalidCount_Data",
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
    public static Object[][] checkFromAndToIndices_Pass_Data() {
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
    
    @Test(dataProvider = "checkFromAndToIndices_Pass_Data")
    public <T> void checkFromAndToIndices_Pass(Collection<T> ref, int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
        // Demonstrate argName can be anything ridiculous.
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, null, null, null);
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "", "", "");
        CollectionArgs.checkFromAndToIndices(ref, fromIndex, toIndex, "   ", "   ", "   ");
    }
    
    // TODO: Check all *FailWithNegativeIndices* tests if they need:
    // (a) tests for empty arrays/collections
    // (b) docs for throws IllegalArgumentException for empty arrays/collections

    @DataProvider
    public static Object[][] checkFromAndToIndices_FailWithEmptyCollection_Data() {
        return new Object[][] {
                { ImmutableList.of(), -1, 0 },
                { ImmutableList.of(), 0, -1 },
                { ImmutableList.of(), -1, -1 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndices_FailWithEmptyCollection_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void checkFromAndToIndices_FailWithEmptyCollection(
            Collection<T> ref, int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    public static Object[][] checkFromAndToIndices_FailWithNegativeIndices_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), -1, 0 },
                { ImmutableList.of("a"), 0, -1 },
                { ImmutableList.of("a"), -1, -1 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndices_FailWithNegativeIndices_Data",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void checkFromAndToIndices_FailWithNegativeIndices(
            Collection<T> ref, int fromIndex, int toIndex) {
        CollectionArgs.checkFromAndToIndices(
            ref, fromIndex, toIndex, "ref", "fromIndex", "toIndex");
    }

    @DataProvider
    public static Object[][] checkFromAndToIndices_FailWithInvalidIndices_Data() {
        return new Object[][] {
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a"), 1, 0 },
                { ImmutableList.of("a", "b"), 3, 1 },
                { ImmutableList.of("a", "b"), 99, 2 },
                { ImmutableList.of("a", "b", "c", "d"), 2, 1 },
                { ImmutableList.of("a", "b", "c", "d"), 2, 99 },
        };
    }
    
    @Test(dataProvider = "checkFromAndToIndices_FailWithInvalidIndices_Data",
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
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkContains
    //
    
    @DataProvider
    public static Object[][] checkContains_Pass_Data() {
        return new Object[][] {
                { Arrays.asList("a"), "a" },
                { Arrays.asList("a", "b"), "b" },
                { Arrays.asList("a", "b", "c"), "b" },
                { Arrays.asList("a", "b", "c", "b"), "b" },
                { Arrays.asList("a", "b", "xyz"), "xyz" },
                { Arrays.asList("東京", "大阪", "札幌"), "大阪" },
                { Arrays.asList("a", "b", null, "c"), null },
        };
    }
    
    @Test(dataProvider = "checkContains_Pass_Data")
    public <THaystack, TNeedle extends THaystack>
    void checkContains_Pass(Collection<THaystack> ref, TNeedle value) {
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(value == CollectionArgs.checkContains(ref, value, "ref"));
        // Demonstrate argName can be anything ridiculous.
        Assert.assertTrue(value == CollectionArgs.checkContains(ref, value, null));
        Assert.assertTrue(value == CollectionArgs.checkContains(ref, value, ""));
        Assert.assertTrue(value == CollectionArgs.checkContains(ref, value, "   "));
    }
    
    @DataProvider
    public static Object[][] checkContains_Fail_Data() {
        return new Object[][] {
                { Arrays.<Object>asList(), "x" },
                { Arrays.asList("a"), "x" },
                { Arrays.asList("a"), null },
                { Arrays.asList("a", "b"), "x" },
                { Arrays.asList("a", "b", "c"), "x" },
                { Arrays.asList("a", "b", "c", "b"), "x" },
                { Arrays.asList("a", "b", "xyz"), "x" },
                { Arrays.asList("東京", "大阪", "札幌"), "x" },
                { Arrays.asList("a", "b", null, "c"), "x" },
        };
    }
    
    @Test(dataProvider = "checkContains_Fail_Data",
            expectedExceptions = IllegalArgumentException.class)
    public <THaystack, TNeedle extends THaystack>
    void checkContains_Fail(Collection<THaystack> ref, TNeedle value) {
        CollectionArgs.checkContains(ref, value, "ref");
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkContains_FailWithEmptyCollection() {
        CollectionArgs.checkContains(Arrays.<Object>asList(), "abc", "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void checkContains_FailWithNullCollection() {
        CollectionArgs.checkContains((List<Object>) null, "abc", "ref");
    }
}
