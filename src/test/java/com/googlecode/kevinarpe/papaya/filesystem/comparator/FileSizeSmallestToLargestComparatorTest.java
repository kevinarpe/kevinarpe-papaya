package com.googlecode.kevinarpe.papaya.filesystem.comparator;

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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.when;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileSizeSmallestToLargestComparatorTest
extends AbstractFileComparatorTestBase<FileSizeSmallestToLargestComparator, Long> {

    @DataProvider
    private static Object[][] compare_Pass_Data() {
        return new Object[][] {
            { 99, 17 },
            { 17, 99 },
            { 99, 99 },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
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
}
