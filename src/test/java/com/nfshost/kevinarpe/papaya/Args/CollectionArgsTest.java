package com.nfshost.kevinarpe.papaya.Args;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.googlecode.kevinarpe.papaya.Args.CollectionArgs;

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
                { null, 0, 1, "   " },  // narrow Japanese spaces
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
}
