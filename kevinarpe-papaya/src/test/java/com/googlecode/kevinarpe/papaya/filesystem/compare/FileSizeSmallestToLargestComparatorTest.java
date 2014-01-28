package com.googlecode.kevinarpe.papaya.filesystem.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileSizeSmallestToLargestComparatorTest
extends AbstractFileComparatorTestBase<FileSizeSmallestToLargestComparator, Long> {

    @DataProvider
    private static Object[][] _compare_Pass_Data() {
        return new Object[][] {
            { 99, 17 },
            { 17, 99 },
            { 99, 99 },
        };
    }

    @Test(dataProvider = "_compare_Pass_Data")
    public void compare_Pass(long fileSizeByteCount1, long fileSizeByteCount2) {
        compare_Pass_core(fileSizeByteCount1, fileSizeByteCount2);
    }

    @Override
    protected FileSizeSmallestToLargestComparator newComparator() {
        return new FileSizeSmallestToLargestComparator();
    }

    @Override
    protected void setupMocksForComparePass(File mockPath, Long mockReturnValue) {
        when(mockPath.length()).thenReturn(mockReturnValue);
    }

    @Override
    protected int compareValues(
            FileSizeSmallestToLargestComparator comparator, Long value1, Long value2) {
        return compareLongs(comparator, value1, value2);
    }
    
    @DataProvider
    private static Object[][] _compare_PassWhenAtLeastOnePathIsDir_Data() {
        File mockFilePath = mock(File.class);
        File mockDirPath1 = mock(File.class);
        File mockDirPath2 = mock(File.class);
        when(mockFilePath.isDirectory()).thenReturn(false);
        when(mockFilePath.length()).thenReturn(1234L);
        when(mockDirPath1.isDirectory()).thenReturn(true);
        when(mockDirPath2.isDirectory()).thenReturn(true);
        return new Object[][] {
            { mockFilePath, mockDirPath1, +1 },
            { mockDirPath1, mockFilePath, -1 },
            { mockDirPath1, mockDirPath2, 0 },
            { mockDirPath2, mockDirPath1, 0 },
            { mockDirPath1, mockDirPath1, 0 },
            { mockDirPath2, mockDirPath2, 0 },
        };
    }
    
    @Test(dataProvider = "_compare_PassWhenAtLeastOnePathIsDir_Data")
    public void compare_PassWhenAtLeastOnePathIsDir(File path1, File path2, int expectedResult) {
        int actualResult = new FileSizeSmallestToLargestComparator().compare(path1, path2);
        assertEquals(actualResult, expectedResult);
    }
}
