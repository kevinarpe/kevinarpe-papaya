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

import com.google.common.collect.ImmutableList;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileTypeComparatorTest {

    private static File compare_Pass_filePath1;
    private static File compare_Pass_filePath2;
    private static File compare_Pass_dirPath1;
    private static File compare_Pass_dirPath2;

    @BeforeClass
    public void beforeClass()
    throws IOException {
        compare_Pass_filePath1 = new File("file." + UUID.randomUUID().toString());
        compare_Pass_filePath1.createNewFile();
        compare_Pass_filePath2 = new File("file." + UUID.randomUUID().toString());
        compare_Pass_filePath2.createNewFile();

        compare_Pass_dirPath1 = new File("dir." + UUID.randomUUID().toString());
        compare_Pass_dirPath1.mkdir();
        compare_Pass_dirPath2 = new File("dir." + UUID.randomUUID().toString());
        compare_Pass_dirPath2.mkdir();
    }

    @AfterClass
    public void afterClass() {
        assertTrue(compare_Pass_filePath1.delete());
        assertTrue(compare_Pass_filePath2.delete());
        assertTrue(compare_Pass_dirPath1.delete());
        assertTrue(compare_Pass_dirPath2.delete());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.ctor()
    //

    @DataProvider
    private static Object[][] ctor_Pass_Data() {
        return new Object[][] {
            { Arrays.asList(FileType.NORMAL_FILE) },
            { Arrays.asList(FileType.DIRECTORY) },
            { Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY) },
        };
    }

    @Test(dataProvider = "ctor_Pass_Data")
    public void ctor_Pass(List<FileType> fileTypeList) {
        new FileTypeComparator(fileTypeList);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new FileTypeComparator((List<FileType>) null);
    }

    @DataProvider
    private static Object[][] ctor_FailWithNullElements_Data() {
        return new Object[][] {
            { Arrays.asList(new Object[] { null }) },
            { Arrays.asList(FileType.NORMAL_FILE, null) },
        };
    }

    @Test(dataProvider = "ctor_FailWithNullElements_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullElements(List<FileType> fileTypeList) {
        new FileTypeComparator(fileTypeList);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithEmpty() {
        new FileTypeComparator(ImmutableList.<FileType>of());
    }

    @DataProvider
    private static Object[][] ctor_FailWithDuplicateElements_Data() {
        return new Object[][] {
            { Arrays.asList(FileType.NORMAL_FILE, FileType.NORMAL_FILE) },
            { Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY, FileType.NORMAL_FILE) },
        };
    }

    @Test(dataProvider = "ctor_FailWithDuplicateElements_Data",
            expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithDuplicateElements(List<FileType> fileTypeList) {
        new FileTypeComparator(fileTypeList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.compare()
    //

    @DataProvider
    private static Object[][] compare_Pass_Data() {

        return new Object[][] {
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.NORMAL_FILE), 0 },
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY), 0 },
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE), 0 },

            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.DIRECTORY), 0 },
            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY), 0 },
            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE), 0 },

            { compare_Pass_filePath1, compare_Pass_dirPath1, Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY), -1 },
            { compare_Pass_filePath1, compare_Pass_dirPath1, Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE), +1 },

            { compare_Pass_dirPath1, compare_Pass_filePath1, Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY), +1 },
            { compare_Pass_dirPath1, compare_Pass_filePath1, Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE), -1 },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
    public void compare_Pass(
            File path1, File path2, List<FileType> fileTypeList, int expectedCompareResult)
    throws IOException {
        assertEquals(
            new FileTypeComparator(fileTypeList).compare(path1, path2),
            expectedCompareResult);
    }

    @Test
    public void compare_Pass2() throws IOException {
        File filePath1 = new File(UUID.randomUUID().toString());
        filePath1.createNewFile();
        File filePath2 = new File(UUID.randomUUID().toString());
        filePath2.createNewFile();

        try {
            assertEquals(
                new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE))
                    .compare(filePath1, filePath2),
                0);
        }
        finally {
            assertTrue(filePath1.delete());
            assertTrue(filePath2.delete());
        }
    }

    @DataProvider
    private static Object[][] compare_FailWithUnknownFileType_Data() {
        File path = new File("abc");
        return new Object[][] {
            { path, null },
            { null, path },
            { null, null },
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void compare_FailWithUnknownFileType()
    throws IOException {
        File filePath1 = new File(UUID.randomUUID().toString());
        filePath1.createNewFile();
        File filePath2 = new File(UUID.randomUUID().toString());
        filePath2.createNewFile();

        try {
            new FileTypeComparator(Arrays.asList(FileType.DIRECTORY)).compare(filePath1, filePath2);
        }
        finally {
            assertTrue(filePath1.delete());
            assertTrue(filePath2.delete());
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void compare_FailWithUnknownFileType2()
    throws IOException {
        File dirPath1 = new File(UUID.randomUUID().toString());
        dirPath1.mkdir();
        File dirPath2 = new File(UUID.randomUUID().toString());
        dirPath2.mkdir();

        try {
            new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE))
                .compare(dirPath1, dirPath2);
        }
        finally {
            assertTrue(dirPath1.delete());
            assertTrue(dirPath2.delete());
        }
    }

    @DataProvider
    private static Object[][] compare_FailWithNull_Data() {
        File path = new File("abc");
        return new Object[][] {
            { path, null },
            { null, path },
            { null, null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "compare_FailWithNull_Data")
    public void compare_FailWithNullWithNull(File path1, File path2) {
        new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE)).compare(path1, path2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE)),
                new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY)),
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY)),
                new FileTypeComparator(Arrays.asList(FileType.NORMAL_FILE, FileType.DIRECTORY)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE)),
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY, FileType.NORMAL_FILE)))
            .testEquals();
    }
}
