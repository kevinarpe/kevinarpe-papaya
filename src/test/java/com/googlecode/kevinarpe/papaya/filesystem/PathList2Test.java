package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PathList2Test {

    ///////////////////////////////////////////////////////////////////////////
    // PathList2.ctor(File)/ctor(File, Class)
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
        List<File> pathList = Lists.newArrayList(pathArr);
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.listFiles()).thenReturn(pathArr);

        PathList2 pathList2 = new PathList2(mockDirPath);
        assertEquals(pathList2, pathList);
        assertEquals(pathList2.getDelegateClass(), PathList2.DEFAULT_LIST_CLASS);

        PathList2 pathList3 = new PathList2(mockDirPath, LinkedList.class);
        assertEquals(pathList3, pathList);
        assertEquals(pathList3.getDelegateClass(), LinkedList.class);

        PathList2 pathList4 = new PathList2(mockDirPath, Vector.class);
        assertEquals(pathList4, pathList);
        assertEquals(pathList4.getDelegateClass(), Vector.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull()
    throws PathException {
        new PathList2((File) null);
    }

    @DataProvider
    private static Object[][] ctor_FailWithNull2_Data() {
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);

        return new Object[][] {
            { mockDirPath, null },
            { null, PathList2.DEFAULT_LIST_CLASS },
            { null, LinkedList.class },
            { null, Vector.class },
            { null, null },
        };
    }

    @Test(dataProvider = "ctor_FailWithNull2_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull2(File dirPath, Class<? extends List> clazz)
    throws PathException {
        new PathList2(dirPath, clazz);
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
            new PathList2(mockDirPath, ArrayList.class);
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
            new PathList2(mockDirPath, ArrayList.class);
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
            new PathList2(mockDirPath, ArrayList.class);
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
            new PathList2(mockDirPath, ArrayList.class);
        }
        catch (PathException e) {
            assertEquals(
                e.getReason(), PathException.PathExceptionReason.UNKNOWN);
            throw e;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PathList2.ctor(PathList2)
    //

//    @DataProvider
//    private static Object[][] copyCtor_Pass_Data() {
//        return new Object[][] {
//            { new File[] { } },
//            { new File[] { new File("abc/123") } },
//            { new File[] { new File("abc/123"), new File("def/456") } },
//        };
//    }

    @Test(dataProvider = "ctor_Pass_Data")
    public void copyCtor_Pass(File[] pathArr)
    throws PathException {
        List<File> pathList = Lists.newArrayList(pathArr);
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.listFiles()).thenReturn(pathArr);

        PathList2 pathList2 = new PathList2(mockDirPath);

        PathList2 pathList3 = new PathList2(pathList2);
        assertEquals(pathList3, pathList2);
        assertEquals(pathList3, pathList);
        assertEquals(pathList3.getDelegateClass(), PathList2.DEFAULT_LIST_CLASS);

        PathList2 pathList4 = new PathList2(pathList2, LinkedList.class);
        // TODO: Why does this pass?  We are comparing ArrayList to LinkedList!
        assertEquals(pathList4, pathList2);
        assertEquals(pathList4, pathList);
        assertEquals(pathList4.getDelegateClass(), LinkedList.class);

        PathList2 pathList5 = new PathList2(pathList2, Vector.class);
        assertEquals(pathList5, pathList2);
        assertEquals(pathList5, pathList);
        assertEquals(pathList5.getDelegateClass(), Vector.class);
    }
}
