package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.Maps;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DirectoryListingTest {

    private final static Map<File[], File> pathArrToMockDirPathMap = Maps.newHashMap();

    private static File createMockDirPath(File[] pathArr) {
        File mockDirPath = pathArrToMockDirPathMap.get(pathArr);
        if (null == mockDirPath) {
            mockDirPath = mock(File.class);
            when(mockDirPath.exists()).thenReturn(true);
            when(mockDirPath.isDirectory()).thenReturn(true);
            when(mockDirPath.isFile()).thenReturn(false);
            when(mockDirPath.listFiles()).thenReturn(pathArr);
            pathArrToMockDirPathMap.put(pathArr, mockDirPath);
        }
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
        ObjectArgs.checkNotNull(listClass, "listClass");

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
            LinkedList.class);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.getDirPath()
    //

    @Test(dataProvider = "ctor_Pass_Data")
    public void getDirPath_Pass(File[] pathArr)
    throws PathException {
        {
            DirectoryListing directoryListing = createDirectoryListing(pathArr);
            assertEquals(directoryListing.getDirPath(), pathArrToMockDirPathMap.get(pathArr));
        }
        {
            DirectoryListing directoryListing = createDirectoryListing(pathArr, LinkedList.class);
            assertEquals(directoryListing.getDirPath(), pathArrToMockDirPathMap.get(pathArr));
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.getChildPathList()
    //

    @Test(dataProvider = "ctor_Pass_Data")
    public void getChildPathList_Pass(File[] pathArr)
    throws PathException {
        List<File> pathList = Arrays.asList(pathArr);
        {
            DirectoryListing directoryListing = createDirectoryListing(pathArr);
            assertEquals(directoryListing.getChildPathList(), pathList);
            assertEquals(
                directoryListing.getChildPathList().getClass(),
                DirectoryListing.DEFAULT_LIST_CLASS);
        }
        {
            DirectoryListing directoryListing = createDirectoryListing(pathArr, LinkedList.class);
            assertEquals(directoryListing.getChildPathList(), pathList);
            assertEquals(
                directoryListing.getChildPathList().getClass(),
                LinkedList.class);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.hashCode()/equals()
    //

    @Test(dataProvider = "ctor_Pass_Data")
    public void hashCodeAndEquals_Pass(File[] pathArr)
    throws PathException {
        new EqualsTester()
            .addEqualityGroup(
                createDirectoryListing(pathArr),
                createDirectoryListing(pathArr))
            .addEqualityGroup(
                createDirectoryListing(pathArr, LinkedList.class),
                createDirectoryListing(pathArr, LinkedList.class))
            .addEqualityGroup(
                createDirectoryListing(pathArr, Vector.class),
                createDirectoryListing(pathArr, Vector.class))
            .testEquals();
    }
}
