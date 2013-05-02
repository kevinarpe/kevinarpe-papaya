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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
    private static final Object[][] _dataForShouldCheckSizeRangeAsValid() {
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
    
    @Test(dataProvider = "_dataForShouldCheckSizeRangeAsValid")
    public <T> void shouldCheckSizeRangeAsValid(Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithInvalidMinOrMaxLen() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithInvalidMinOrMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckSizeRangeAsValidWithInvalidMinOrMaxLen(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckSizeRangeAsValidWithNullCollection(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4, 3 },
                { null, 6, 7 },
                { null, 0, 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckSizeRangeAsValidWithNullArgName(
            Collection<T> ref, int minSize, int maxSize) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, null);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckSizeRangeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, 3, "" },
                { null, 4, 3, "   " },  // ASCII spaces
                { null, 6, 7, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckSizeRangeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckSizeRangeAsValidWithInvalidArgName(
            Collection<T> ref, int minSize, int maxSize, String argName) {
        CollectionArgs.checkSizeRange(ref, minSize, maxSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkNotEmpty
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAsNotEmpty() {
        return new Object[][] {
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
                { ImmutableList.of("a", "b", "c") },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAsNotEmpty")
    public <T> void shouldCheckAsNotEmpty(Collection<T> ref) {
        CollectionArgs.checkNotEmpty(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithEmptyCollection() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableSet.of() },
                { new ArrayList<String>() },
                { new HashSet<String>() },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithEmptyCollection",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckAsNotEmptyWithEmptyCollection(Collection<T> ref) {
        CollectionArgs.checkNotEmpty(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckAsNotEmptyWithNullCollection() {
        Collection<T> ref = null;
        CollectionArgs.checkNotEmpty(ref, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithNullArgName() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableSet.of() },
                { new ArrayList<String>() },
                { new HashSet<String>() },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckAsNotEmptyWithNullArgName(Collection<T> ref) {
        String argName = null;
        CollectionArgs.checkNotEmpty(ref, argName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAsNotEmptyWithInvalidArgName() {
        return new Object[][] {
                { new ArrayList<String>(), "" },
                { new ArrayList<String>(), "   " },  // ASCII spaces
                { new ArrayList<String>(), "　　　" },  // wide Japanese spaces
                { new ArrayList<String>(), "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAsNotEmptyWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckAsNotEmptyWithInvalidArgName(Collection<T> ref, String argName) {
        CollectionArgs.checkNotEmpty(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMinSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMinSizeAsValid() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMinSizeAsValid")
    public <T> void shouldCheckMinSizeAsValid(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithInvalidMinLen() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithInvalidMinLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckMinSizeAsValidWithInvalidMinLen(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMinSizeAsValidWithNullCollection(Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMinSizeAsValidWithNullArgName(
            Collection<T> ref, int minSize) {
        CollectionArgs.checkMinSize(ref, minSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMinSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMinSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckMinSizeAsValidWithInvalidArgName(
            Collection<T> ref, int minSize, String argName) {
        CollectionArgs.checkMinSize(ref, minSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkMaxSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckMaxSizeAsValid() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { new ArrayList<String>(), 99 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a"), 99 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckMaxSizeAsValid")
    public <T> void shouldCheckMaxSizeAsValid(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithInvalidMaxLen() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithInvalidMaxLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckMaxSizeAsValidWithInvalidMaxLen(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMaxSizeAsValidWithNullCollection(Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckMaxSizeAsValidWithNullArgName(
            Collection<T> ref, int maxSize) {
        CollectionArgs.checkMaxSize(ref, maxSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckMaxSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckMaxSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckMaxSizeAsValidWithInvalidArgName(
            Collection<T> ref, int maxSize, String argName) {
        CollectionArgs.checkMaxSize(ref, maxSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkExactSize
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckExactSizeAsValid() {
        return new Object[][] {
                { new ArrayList<String>(), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckExactSizeAsValid")
    public <T> void shouldCheckExactSizeAsValid(Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithInvalidExactLen() {
        return new Object[][] {
                { new ArrayList<String>(), -2 },
                { new ArrayList<String>(), 2 },
                { ImmutableList.of("a"), -3 },
                { ImmutableList.of("a"), 3 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithInvalidExactLen",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckExactSizeAsValidWithInvalidExactLen(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckExactSizeAsValidWithNullCollection(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, "ref");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithNullArgName() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckExactSizeAsValidWithNullArgName(
            Collection<T> ref, int exactSize) {
        CollectionArgs.checkExactSize(ref, exactSize, null);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckExactSizeAsValidWithInvalidArgName() {
        return new Object[][] {
                { null, 4, "" },
                { null, 4, "   " },  // ASCII spaces
                { null, 6, "　　　" },  // wide Japanese spaces
                { null, 0, "   " },  // narrow Japanese spaces
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckExactSizeAsValidWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckExactSizeAsValidWithInvalidArgName(
            Collection<T> ref, int exactSize, String argName) {
        CollectionArgs.checkExactSize(ref, exactSize, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkElementsNotNull
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckElementsAsNotNull() {
        return new Object[][] {
                { ImmutableList.of() },
                { ImmutableList.of("a") },
                { ImmutableList.of("a", "b") },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckElementsAsNotNull")
    public <T> void shouldCheckElementsAsNotNull(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithNullElements() {
        return new Object[][] {
                { Arrays.asList(new Object[] { null }) },
                { Arrays.asList("a", null) },
                { Arrays.asList(null, "a") },
                { Arrays.asList(null, "a", "b") },
                { Arrays.asList("a", null, "b") },
                { Arrays.asList("a", "b", null) },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithNullElements",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithNullElements(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, "ref");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckElementsAsNotNullWithNullCollection() {
        CollectionArgs.checkElementsNotNull(null, "ref");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithNullArgName() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithNullArgName",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithNullArgName(Collection<T> ref) {
        CollectionArgs.checkElementsNotNull(ref, null);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckElementsAsNotNullWithInvalidArgName() {
        return new Object[][] {
                { null, "" },
                { Arrays.asList(new Object[] { null }), "" },
                { Arrays.asList("a", null), "" },
                
                { null, "   " },
                { Arrays.asList(new Object[] { null }), "   " },
                { Arrays.asList("a", null), "   " },
                
                { null, "　　　" },
                { Arrays.asList(new Object[] { null }), "　　　" },
                { Arrays.asList("a", null), "　　　" },
                
                { null, "   " },
                { Arrays.asList(new Object[] { null }), "   " },
                { Arrays.asList("a", null), "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckElementsAsNotNullWithInvalidArgName",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckElementsAsNotNullWithInvalidArgName(
            Collection<T> ref, String argName) {
        CollectionArgs.checkElementsNotNull(ref, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkAccessIndex
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckAccessIndexAsValid() {
        return new Object[][] {
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckAccessIndexAsValid")
    public <T> void shouldCheckAccessIndexAsValid(Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithInvalidIndex() {
        return new Object[][] {
                { ImmutableList.of("a"), -1 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), -1 },
                { ImmutableList.of("a", "b"), 2 },
                { ImmutableList.of("a", "b"), 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckAccessIndexAsValidWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckAccessIndexAsValidWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkAccessIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithNullArgNames() {
        List<String> L = ImmutableList.of("a");
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
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        CollectionArgs.checkAccessIndex(ref, index, collectionArgName, indexArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckAccessIndexAsValidWithInvalidArgNames() {
        List<String> L = ImmutableList.of("a");
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
                
                // narrow Japanese spaces
                { null, 4, "   ", "index" },
                { null, -4, "   ", "index" },
                { L, 4, "ref", "   " },
                { L, -4, "ref", "   " },
                { null, 6, "   ", "   " },
                { null, -6, "   ", "   " },
                { L, 6, "   ", "   " },
                { L, -6, "   ", "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckAccessIndexAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckAccessIndexAsValidWithInvalidArgNames(
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        CollectionArgs.checkAccessIndex(ref, index, collectionArgName, indexArgName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkInsertIndex
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckInsertIndexAsValid() {
        return new Object[][] {
                { ImmutableList.of(), 0 },
                { ImmutableList.of("a"), 0 },
                { ImmutableList.of("a"), 1 },
                { ImmutableList.of("a", "b"), 0 },
                { ImmutableList.of("a", "b"), 1 },
                { ImmutableList.of("a", "b"), 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCheckInsertIndexAsValid")
    public <T> void shouldCheckInsertIndexAsValid(Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithInvalidIndex() {
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
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckInsertIndexAsValidWithInvalidIndex(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithNullCollection() {
        return new Object[][] {
                { null, 4 },
                { null, 6 },
                { null, 0 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithNullCollection",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldCheckInsertIndexAsValidWithNullCollection(
            Collection<T> ref, int index) {
        CollectionArgs.checkInsertIndex(ref, index, "ref", "index");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithNullArgNames() {
        List<String> L = ImmutableList.of("a");
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
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        CollectionArgs.checkInsertIndex(ref, index, collectionArgName, indexArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckInsertIndexAsValidWithInvalidArgNames() {
        List<String> L = ImmutableList.of("a");
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
                
                // narrow Japanese spaces
                { null, 4, "   ", "index" },
                { null, -4, "   ", "index" },
                { L, 4, "ref", "   " },
                { L, -4, "ref", "   " },
                { null, 6, "   ", "   " },
                { null, -6, "   ", "   " },
                { L, 6, "   ", "   " },
                { L, -6, "   ", "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckInsertIndexAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldCheckInsertIndexAsValidWithInvalidArgNames(
            Collection<T> ref, int index, String collectionArgName, String indexArgName) {
        CollectionArgs.checkInsertIndex(ref, index, collectionArgName, indexArgName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // CollectionArgs.checkIndexAndCount
    //

    @DataProvider
    private static final Object[][] _dataForShouldCheckIndexAndCountAsValid() {
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
    
    @Test(dataProvider = "_dataForShouldCheckIndexAndCountAsValid")
    public <T> void shouldCheckIndexAndCountAsValid(Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidIndex() {
        return new Object[][] {
                { ImmutableList.of("a"), -1, 0 },
                { ImmutableList.of("a"), 1, 1 },
                { ImmutableList.of("a", "b"), -1, 0 },
                { ImmutableList.of("a", "b"), 3, 1 },
                { ImmutableList.of("a", "b"), 99, 2 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidIndex",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidIndex(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithNegativeCount() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, -1 },
                { ImmutableList.of("a", "b"), 0, -1 },
                { ImmutableList.of("a", "b"), 1, -1 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithNegativeCount",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithNegativeCount(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidCount() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 2 },
                { ImmutableList.of("a"), 0, 99 },
                { ImmutableList.of("a", "b"), 0, 3 },
                { ImmutableList.of("a", "b"), 0, 99 },
                { ImmutableList.of("a", "b"), 1, 3 },
                { ImmutableList.of("a", "b"), 1, 99 },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidCount",
            expectedExceptions = IndexOutOfBoundsException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidCount(
            Collection<T> ref, int index, int count) {
        CollectionArgs.checkIndexAndCount(ref, index, count, "ref", "index", "count");
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckIndexAndCountAsValidWithNullCollection() {
        CollectionArgs.checkIndexAndCount(null, 0, 0, "ref", "index", "count");
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithNullArgNames() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 2, null, "index", "count" },
                { ImmutableList.of("a"), 0, 99, "ref", null, "count" },
                { ImmutableList.of("a", "b"), 0, 3, "ref", "index", null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithNullArgNames",
            expectedExceptions = NullPointerException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithNullArgNames(
            Collection<T> ref,
            int index,
            int count,
            String collectionArgName,
            String indexArgName,
            String countArgName) {
        CollectionArgs.checkIndexAndCount(
            ref, index, count, collectionArgName, indexArgName, countArgName);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckIndexAndCountAsValidWithInvalidArgNames() {
        return new Object[][] {
                { ImmutableList.of("a"), 0, 2, "", "index", "count" },
                { ImmutableList.of("a"), 0, 99, "ref", "", "count" },
                { ImmutableList.of("a", "b"), 0, 3, "ref", "index", "" },
                
                { ImmutableList.of("a"), 0, 2, "   ", "index", "count" },
                { ImmutableList.of("a"), 0, 99, "ref", "   ", "count" },
                { ImmutableList.of("a", "b"), 0, 3, "ref", "index", "   " },
                
                { ImmutableList.of("a"), 0, 2, "　　　", "index", "count" },
                { ImmutableList.of("a"), 0, 99, "ref", "　　　", "count" },
                { ImmutableList.of("a", "b"), 0, 3, "ref", "index", "　　　" },
                
                { ImmutableList.of("a"), 0, 2, "   ", "index", "count" },
                { ImmutableList.of("a"), 0, 99, "ref", "   ", "count" },
                { ImmutableList.of("a", "b"), 0, 3, "ref", "index", "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckIndexAndCountAsValidWithInvalidArgNames",
            expectedExceptions = IllegalArgumentException.class)
    public <T> void shouldNotCheckIndexAndCountAsValidWithInvalidArgNames(
            Collection<T> ref,
            int index,
            int count,
            String collectionArgName,
            String indexArgName,
            String countArgName) {
        CollectionArgs.checkIndexAndCount(
            ref, index, count, collectionArgName, indexArgName, countArgName);
    }
}
