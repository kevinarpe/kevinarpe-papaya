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

import java.util.Collection;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.Args.CollectionArgs;
import com.googlecode.kevinarpe.papaya.Args.StringArgs;

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
                { ImmutableList.of(), 0, 10 },
                { ImmutableList.of(), 0, 0 },
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
                { ImmutableList.of(), 3, 4 },
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
}
