package com.googlecode.kevinarpe.papaya.filesystem;

import com.googlecode.kevinarpe.papaya.exception.PathException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DirectoryListingTest {

    private static File createMockDirPath(File[] pathArr) {
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.listFiles()).thenReturn(pathArr);
        return mockDirPath;
    }

    private static DirectoryListing createDirectoryListing(File[] pathArr)
        throws PathException {
        File mockDirPath = createMockDirPath(pathArr);
        DirectoryListing x = new DirectoryListing(mockDirPath);
        return x;
    }

    private static DirectoryListing createDirectoryListing(File[] pathArr, Class<? extends List> listClass)
        throws PathException {
        File mockDirPath = createMockDirPath(pathArr);
        DirectoryListing x = new DirectoryListing(mockDirPath, listClass);
        return x;
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.ctor(File)/ctor(File, Class)
    //

    @DataProvider
    private static Object[][] ctor_Pass_Data() {
        return new Object[][] {
            { new File[] { } },
            { new File[] { new File("abc/123") } },
            { new File[] { new File("abc/123"), new File("def/456") } },
        };
    }

    @Test(dataProvider = "ctor_Pass_Data")
    public void ctor_Pass(File[] pathArr)
    throws PathException {
        List<File> pathList = Arrays.asList(pathArr);
        core_ctor_Pass(
            createDirectoryListing(pathArr),
            pathList,
            DirectoryListing.DEFAULT_LIST_CLASS);
        core_ctor_Pass(
            createDirectoryListing(pathArr, DirectoryListing.DEFAULT_LIST_CLASS),
            pathList,
            DirectoryListing.DEFAULT_LIST_CLASS);
        core_ctor_Pass(createDirectoryListing(pathArr, LinkedList.class), pathList, LinkedList.class);
        core_ctor_Pass(createDirectoryListing(pathArr, Vector.class), pathList, Vector.class);
    }

    private void core_ctor_Pass(
            DirectoryListing listDir, List<File> pathList, Class<? extends List> listClass) {
        List<File> pathList2 = listDir.getChildPathList();
        assertEquals(pathList2, pathList);
        assertEquals(pathList2.getClass(), listClass);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull()
    throws PathException {
        new DirectoryListing((File) null);
    }

    @DataProvider
    private static Object[][] ctor_FailWithNull2_Data() {
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);

        return new Object[][] {
            { mockDirPath, null },
            { null, DirectoryListing.DEFAULT_LIST_CLASS },
            { null, LinkedList.class },
            { null, Vector.class },
            { null, null },
        };
    }

    @Test(dataProvider = "ctor_FailWithNull2_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull2(File dirPath, Class<? extends List> listClass)
    throws PathException {
        new DirectoryListing(dirPath, listClass);
    }

    @Test(expectedExceptions = PathException.class)
    public void ctor_FailWithDirNotExists()
    throws PathException {
        File mockDirPath = mock(File.class);
        when(mockDirPath.listFiles()).thenReturn(null);
        when(mockDirPath.exists()).thenReturn(true).thenReturn(false);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);

        try {
            new DirectoryListing(mockDirPath, ArrayList.class);
        }
        catch (PathException e) {
            assertEquals(e.getReason(), PathException.PathExceptionReason.PATH_DOES_NOT_EXIST);
            verify(mockDirPath).listFiles();
            throw e;
        }
    }

    @Test(expectedExceptions = PathException.class)
    public void ctor_FailWithRegularFilePath()
    throws PathException {
        File mockDirPath = mock(File.class);
        when(mockDirPath.listFiles()).thenReturn(null);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(false);
        when(mockDirPath.isFile()).thenReturn(true);

        try {
            new DirectoryListing(mockDirPath, ArrayList.class);
        }
        catch (PathException e) {
            assertEquals(e.getReason(), PathException.PathExceptionReason.PATH_IS_FILE);
            throw e;
        }
    }

    @Test(expectedExceptions = PathException.class)
    public void ctor_FailWithDirNotExecutable()
    throws PathException {
        File mockDirPath = mock(File.class);
        when(mockDirPath.listFiles()).thenReturn(null);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.canExecute()).thenReturn(false);

        try {
            new DirectoryListing(mockDirPath, ArrayList.class);
        }
        catch (PathException e) {
            assertEquals(
                e.getReason(), PathException.PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY);
            throw e;
        }
    }

    @Test(expectedExceptions = PathException.class)
    public void ctor_FailWithUnknownError()
    throws PathException {
        File mockDirPath = mock(File.class);
        when(mockDirPath.listFiles()).thenReturn(null);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.canExecute()).thenReturn(true);

        try {
            new DirectoryListing(mockDirPath, ArrayList.class);
        }
        catch (PathException e) {
            assertEquals(
                e.getReason(), PathException.PathExceptionReason.UNKNOWN);
            throw e;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.ctor(DirectoryListing)
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void copyCtor_FailWithNull() {
        new DirectoryListing((DirectoryListing) null);
    }

    @Test(dataProvider = "ctor_Pass_Data")
    public void copyCtor_Pass(File[] pathArr)
    throws PathException {
        List<File> pathList = Arrays.asList(pathArr);
        DirectoryListing directoryListing = createDirectoryListing(pathArr);
        DirectoryListing directoryListingCopy = new DirectoryListing(directoryListing);

        assertEquals(directoryListingCopy, directoryListing);
        assertEquals(directoryListing.getChildPathList(), pathList);
        assertEquals(directoryListingCopy.getChildPathList(), pathList);
        assertEquals(
            directoryListingCopy.getChildPathList().getClass(),
            DirectoryListing.DEFAULT_LIST_CLASS);
    }

    private void copyAndAssertEquals(
        DirectoryListing listDir, List<File> pathList, Class<? extends List> listClass) {
        DirectoryListing listDirCopy = new DirectoryListing(listDir, listClass);
        assertEquals(listDirCopy, listDir);
        List<File> pathList2 = listDir.getChildPathList();
        assertEquals(pathList2, pathList);
//        assertEquals(path)
//        assertEquals(listDirCopy.getDelegateClass(), listClass);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.ctor(DirectoryListing, Class)
    //

    @DataProvider
    private static Object[][] copyCtor2_FailWithNull_Data()
    throws PathException {
        DirectoryListing x = createDirectoryListing(new File[] { new File("abc") });
        return new Object[][] {
            { (DirectoryListing) null, LinkedList.class },
            { x, (Class<?>) null },
            { (DirectoryListing) null, LinkedList.class },
        };
    }

    @Test(dataProvider = "copyCtor2_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void copyCtor2_FailWithNull(DirectoryListing other, Class<? extends List> listClass) {
        new DirectoryListing(other, listClass);
    }

    @Test(dataProvider = "ctor_Pass_Data")
    public void copyCtor2_Pass(File[] pathArr)
    throws PathException {
        List<File> pathList = Arrays.asList(pathArr);
        DirectoryListing directoryListing = createDirectoryListing(pathArr, LinkedList.class);
        DirectoryListing directoryListingCopy = new DirectoryListing(directoryListing);

        assertEquals(directoryListingCopy, directoryListing);
        assertEquals(directoryListing.getChildPathList(), pathList);
        assertEquals(directoryListingCopy.getChildPathList(), pathList);
        assertEquals(
            directoryListingCopy.getChildPathList().getClass(),
            DirectoryListing.DEFAULT_LIST_CLASS);
    }
}
