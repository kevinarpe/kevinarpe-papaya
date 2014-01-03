package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.google.common.collect.ImmutableList;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.filesystem.FileType;
import org.testng.annotations.*;

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

    // TODO: Last!  Figure out this group shit.
    @AfterGroups(groups = {"compare_Pass"})
    public void afterEachCallTo_compare_Pass() {
        assertTrue(compare_Pass_filePath1.delete());
        assertTrue(compare_Pass_filePath2.delete());
        assertTrue(compare_Pass_dirPath1.delete());
        assertTrue(compare_Pass_dirPath2.delete());
    }

    @BeforeTest(dependsOnMethods = "compare_Pass")
    @BeforeMethod()
    public void beforeEachCallTo_compare_Pass()
    throws IOException {
        compare_Pass_filePath1 = new File(UUID.randomUUID().toString());
        compare_Pass_filePath1.createNewFile();
        compare_Pass_filePath2 = new File(UUID.randomUUID().toString());
        compare_Pass_filePath2.createNewFile();

        compare_Pass_dirPath1 = new File(UUID.randomUUID().toString());
        compare_Pass_dirPath1.mkdir();
        compare_Pass_dirPath2 = new File(UUID.randomUUID().toString());
        compare_Pass_dirPath2.mkdir();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.ctor()
    //

    @DataProvider
    private static Object[][] ctor_Pass_Data() {
        return new Object[][] {
            { Arrays.asList(FileType.REGULAR_FILE) },
            { Arrays.asList(FileType.DIRECTORY) },
            { Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY) },
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
            { Arrays.asList(FileType.REGULAR_FILE, null) },
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
            { Arrays.asList(FileType.REGULAR_FILE, FileType.REGULAR_FILE) },
            { Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY, FileType.REGULAR_FILE) },
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

//    private static File compare_Pass_filePath1;
//    private static File compare_Pass_filePath2;
//    private static File compare_Pass_dirPath1;
//    private static File compare_Pass_dirPath2;
//
//    @AfterTest( = "compare_Pass")
//    public void afterEachCallTo_compare_Pass() {
//        assertTrue(compare_Pass_filePath1.delete());
//        assertTrue(compare_Pass_filePath2.delete());
//        assertTrue(compare_Pass_dirPath1.delete());
//        assertTrue(compare_Pass_dirPath2.delete());
//    }
//
//    @BeforeTest(dependsOnMethods = "compare_Pass")
//    @BeforeMethod()
//    public void beforeEachCallTo_compare_Pass()
//    throws IOException {
//        compare_Pass_filePath1 = new File(UUID.randomUUID().toString());
//        compare_Pass_filePath1.createNewFile();
//        compare_Pass_filePath2 = new File(UUID.randomUUID().toString());
//        compare_Pass_filePath2.createNewFile();
//
//        compare_Pass_dirPath1 = new File(UUID.randomUUID().toString());
//        compare_Pass_dirPath1.mkdir();
//        compare_Pass_dirPath2 = new File(UUID.randomUUID().toString());
//        compare_Pass_dirPath2.mkdir();
//    }

//    @DataProvider
//    private static Object[][] compare_Pass_Data() {
//
//        return new Object[][] {
//            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.REGULAR_FILE), 0 },
//            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY), 0 },
//            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE), 0 },
//
//            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.REGULAR_FILE), 0 },
//            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY), 0 },
//            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE), 0 },
//        };
//    }

//    @Test(dataProvider = "compare_Pass_Data")
    @Test
    public void compare_Pass()
    throws IOException {
        compare_Pass_filePath1 = new File(UUID.randomUUID().toString());
        compare_Pass_filePath1.createNewFile();
        compare_Pass_filePath2 = new File(UUID.randomUUID().toString());
        compare_Pass_filePath2.createNewFile();

        compare_Pass_dirPath1 = new File(UUID.randomUUID().toString());
        compare_Pass_dirPath1.mkdir();
        compare_Pass_dirPath2 = new File(UUID.randomUUID().toString());
        compare_Pass_dirPath2.mkdir();

        Object[][] argArrArr = new Object[][] {
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.REGULAR_FILE), 0 },
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY), 0 },
            { compare_Pass_filePath1, compare_Pass_filePath2, Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE), 0 },

            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.DIRECTORY), 0 },
            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY), 0 },
            { compare_Pass_dirPath1, compare_Pass_dirPath2, Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE), 0 },
        };

        try {
            for (Object[] argArr : argArrArr) {
                File path1 = (File) argArr[0];
                File path2 = (File) argArr[1];
                List<FileType> fileTypeList = (List<FileType>) argArr[2];
                int expectedCompareResult = (Integer) argArr[3];

                assertEquals(
                    new FileTypeComparator(fileTypeList).compare(path1, path2),
                    expectedCompareResult);
            }
        }
        finally {
            assertTrue(compare_Pass_filePath1.delete());
            assertTrue(compare_Pass_filePath2.delete());
            assertTrue(compare_Pass_dirPath1.delete());
            assertTrue(compare_Pass_dirPath2.delete());
        }
    }

    @Test
    public void compare_Pass2() throws IOException {
        File filePath1 = new File(UUID.randomUUID().toString());
        filePath1.createNewFile();
        File filePath2 = new File(UUID.randomUUID().toString());
        filePath2.createNewFile();

        try {
            assertEquals(
                new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE))
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
            new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE))
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
        new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE)).compare(path1, path2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // FileTypeComparator.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE)),
                new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY)),
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY)),
                new FileTypeComparator(Arrays.asList(FileType.REGULAR_FILE, FileType.DIRECTORY)))
            .addEqualityGroup(
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE)),
                new FileTypeComparator(Arrays.asList(FileType.DIRECTORY, FileType.REGULAR_FILE)))
            .testEquals();
    }
}
