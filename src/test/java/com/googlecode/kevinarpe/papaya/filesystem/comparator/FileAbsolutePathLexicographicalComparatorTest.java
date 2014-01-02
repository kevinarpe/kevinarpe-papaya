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

import com.googlecode.kevinarpe.papaya.comparator.CaseSensitive;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.when;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileAbsolutePathLexicographicalComparatorTest
extends AbstractFileLexicographicalComparatorTestBase<FileAbsolutePathLexicographicalComparator> {

    @DataProvider
    private static Object[][] compare_Pass_Data() {
        return new Object[][] {
            { "abc/123", "def/456" },
            { "def/456", "abc/123" },
            { "abc/123", "abc/123" },
            { "abc/123", "ABC/123" },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
    public void compare_Pass(String pathname1, String pathname2) {
        for (CaseSensitive cs : CaseSensitive.values()) {
            compare_Pass_core(newComparator(cs), pathname1, pathname2);
        }
    }

    @Override
    protected FileAbsolutePathLexicographicalComparator newComparator() {
        return new FileAbsolutePathLexicographicalComparator();
    }

    @Override
    protected FileAbsolutePathLexicographicalComparator newComparator(CaseSensitive caseSensitive) {
        return new FileAbsolutePathLexicographicalComparator(caseSensitive);
    }

    @Override
    protected void setupMocksForComparePass(File mockPath, String mockReturnValue) {
        when(mockPath.getAbsolutePath()).thenReturn(mockReturnValue);
    }

    @Override
    protected int compareValues(
            FileAbsolutePathLexicographicalComparator comparator, String value1, String value2) {
        return compareStrings(comparator, value1, value2);
    }
}
