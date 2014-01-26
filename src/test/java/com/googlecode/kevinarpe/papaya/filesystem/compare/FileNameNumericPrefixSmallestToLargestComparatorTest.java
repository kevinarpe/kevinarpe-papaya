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

import com.googlecode.kevinarpe.papaya.string.NumericPrefix;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.when;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileNameNumericPrefixSmallestToLargestComparatorTest
extends AbstractFileComparatorTestBase<FileNameNumericPrefixSmallestToLargestComparator, String> {

    @DataProvider
    private static Object[][] compare_Pass_Data() {
        return new Object[][] {
            { "abc", "def" },
            { "def", "abc" },
            { "abc", "abc" },
            { "123abc", "123def" },
            { "123def", "123abc" },
            { "123abc", "123abc" },

            { "-123abc", "123def" },
            { "123def", "-123abc" },
            { "-123abc", "-123abc" },

            { "123", "123" },
            { "123", "123" },
            { "123", "123" },

            { "-123", "123" },
            { "123", "-123" },
            { "-123", "-123" },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
    public void compare_Pass(String fileName1, String fileName2) {
        compare_Pass_core(fileName1, fileName2);
    }

    @Override
    protected FileNameNumericPrefixSmallestToLargestComparator newComparator() {
        return new FileNameNumericPrefixSmallestToLargestComparator();
    }

    @Override
    protected void setupMocksForComparePass(File mockPath, String mockReturnValue) {
        when(mockPath.getName()).thenReturn(mockReturnValue);
    }

    @Override
    protected int compareValues(
            FileNameNumericPrefixSmallestToLargestComparator comparator,
            String value1,
            String value2) {
        NumericPrefix numericPrefix1 = new NumericPrefix(value1);
        NumericPrefix numericPrefix2 = new NumericPrefix(value2);

        if (!numericPrefix1.hasNumericValue() || !numericPrefix2.hasNumericValue()) {
            return compareStrings(comparator, value1, value2);
        }

        return compareLongs(
            comparator, numericPrefix1.getNumericValue(), numericPrefix2.getNumericValue());
    }
}
