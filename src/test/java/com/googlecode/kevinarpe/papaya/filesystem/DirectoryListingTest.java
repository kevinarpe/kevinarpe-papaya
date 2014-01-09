package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileLastModifiedOldestToNewestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import org.joda.time.DateTime;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

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
            { new File[] { new File("abc/123"), new File("def/456"), new File("ghi/789") } },
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
            assertEquals(e.getReason(), PathExceptionReason.PATH_DOES_NOT_EXIST);
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
            assertEquals(e.getReason(), PathExceptionReason.PATH_IS_NORMAL_FILE);
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
                e.getReason(), PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY);
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
                e.getReason(), PathExceptionReason.UNKNOWN);
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

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.filter(FileFilter)/filter(List<FileFilter>)
    //

    @Test(dataProvider = "ctor_Pass_Data")
    public void filter_Pass(File[] pathArr)
    throws PathException {
        _filter_Pass_Helper(pathArr, new _DirectoryListingFactory() {
            @Override
            public DirectoryListing create(File[] pathArr)
            throws PathException {
                return createDirectoryListing(pathArr);
            }
        });
        _filter_Pass_Helper(pathArr, new _DirectoryListingFactory() {
            @Override
            public DirectoryListing create(File[] pathArr)
            throws PathException {
                return createDirectoryListing(pathArr, LinkedList.class);
            }
        });
        _filter_Pass_Helper(pathArr, new _DirectoryListingFactory() {
            @Override
            public DirectoryListing create(File[] pathArr)
            throws PathException {
                return createDirectoryListing(pathArr, Vector.class);
            }
        });
    }

    private static interface _DirectoryListingFactory {
        DirectoryListing create(File[] pathArr) throws PathException;
    }

    private void _filter_Pass_Helper(File[] pathArr, _DirectoryListingFactory factory)
    throws PathException {
        FileFilter all = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        FileFilter none = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return false;
            }
        };

        _filterHelper(factory.create(pathArr), all, Arrays.asList(pathArr));
        _filterHelper(factory.create(pathArr), Arrays.asList(all), Arrays.asList(pathArr));
        _filterHelper(factory.create(pathArr), Arrays.asList(all, all), Arrays.asList(pathArr));

        _filterHelper(factory.create(pathArr), none, Collections.<File>emptyList());
        _filterHelper(factory.create(pathArr), Arrays.asList(none), Collections.<File>emptyList());
        _filterHelper(factory.create(pathArr), Arrays.asList(none, none), Collections.<File>emptyList());

        _filterHelper(factory.create(pathArr), Arrays.asList(all, none), Collections.<File>emptyList());
        _filterHelper(factory.create(pathArr), Arrays.asList(none, all), Collections.<File>emptyList());

        ArrayList<File> expectedPathList = Lists.newArrayList(pathArr);
        DirectoryListing directoryListing = factory.create(pathArr);
        while (!expectedPathList.isEmpty()) {
            FileFilter rejectFirst = new FileFilter() {
                boolean isFirst = true;

                @Override
                public boolean accept(File pathname) {
                    if (isFirst) {
                        isFirst = false;
                        return false;
                    }
                    return true;
                }
            };
            expectedPathList.remove(0);
            _filterHelper(directoryListing, rejectFirst, expectedPathList);
        }
    }

    private void _filterHelper(
            DirectoryListing directoryListing, FileFilter fileFilter, List<File> expectedPathList)
    throws PathException {
        assertSame(directoryListing.filter(fileFilter), directoryListing);
        assertEquals(directoryListing.getChildPathList(), expectedPathList);
    }

    private void _filterHelper(
            DirectoryListing directoryListing,
            List<FileFilter> fileFilterList,
            List<File> expectedPathList)
    throws PathException {
        assertSame(directoryListing.filter(fileFilterList), directoryListing);
        assertEquals(directoryListing.getChildPathList(), expectedPathList);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void filter_FailWithNull()
    throws PathException {
        createDirectoryListing(new File[0]).filter((FileFilter) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void filter2_FailWithNull()
    throws PathException {
        createDirectoryListing(new File[0]).filter((List<FileFilter>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void filter2_FailWithNullElement()
    throws PathException {
        createDirectoryListing(new File[0]).filter(Lists.newArrayList(new FileFilter[] { null }));
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.sort(Comparator<File>)/sort(List<Comparator<File>>)
    //

    @Test
    public void sort_Pass()
    throws PathException {
        DateTime now = DateTime.now();
        DateTime beforeNow = now.plusHours(-1);
        DateTime afterNow = now.plusHours(+1);

        DirectoryListing directoryListing =
            createDirectoryListing(new File[] {
                createMockFilePath("def", afterNow),
                createMockFilePath("abc", now),
                createMockFilePath("ghi", beforeNow),
            });

        Comparator<File> mockComparator =
            createMockComparator(new FileNameLexicographicalComparator());
        assertSame(
            directoryListing.sort(mockComparator),
            directoryListing);
        List<File> childPathList = directoryListing.getChildPathList();
        assertEquals(childPathList.get(0).getName(), "abc");
        assertEquals(childPathList.get(1).getName(), "def");
        assertEquals(childPathList.get(2).getName(), "ghi");
        verify(mockComparator, atLeast(1)).compare(any(File.class), any(File.class));
    }

    private Comparator<File> createMockComparator(final Comparator<File> comparator) {
        // We cannot spy on a final class with Mockito (or Powermock), so we fake a spy here.
        // Create a mock, but delgate compare(T, T) calls to a real Comparator<T> instance.
        @SuppressWarnings("unchecked")
        Comparator<File> mockComparator = (Comparator<File>) mock(Comparator.class);
        when(mockComparator.compare(any(File.class), any(File.class))).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                File left = (File) invocation.getArguments()[0];
                File right = (File) invocation.getArguments()[1];
                return comparator.compare(left, right);
            }
        });
        return mockComparator;
    }

    @Test
    public void sort2_Pass()
    throws PathException {
        DateTime now = DateTime.now();
        DateTime beforeNow = now.plusHours(-1);
        DateTime afterNow = now.plusHours(+1);

        DirectoryListing directoryListing =
            createDirectoryListing(new File[] {
                createMockFilePath("def", afterNow),
                createMockFilePath("abc", now),
                createMockFilePath("abc", beforeNow),
            });
        Comparator<File> mockComparator =
            createMockComparator(new FileNameLexicographicalComparator());
        Comparator<File> mockComparator2 =
            createMockComparator(new FileLastModifiedOldestToNewestComparator());
        @SuppressWarnings("unchecked")
        List<Comparator<File>> comparatorList = Arrays.asList(mockComparator, mockComparator2);
        assertSame(directoryListing.sort(comparatorList), directoryListing);
        List<File> childPathList = directoryListing.getChildPathList();
        assertEquals(childPathList.get(0).getName(), "abc");
        assertEquals(childPathList.get(1).getName(), "abc");
        assertEquals(childPathList.get(2).getName(), "def");

        assertEquals(childPathList.get(0).lastModified(), beforeNow.getMillis());
        assertEquals(childPathList.get(1).lastModified(), now.getMillis());
        assertEquals(childPathList.get(2).lastModified(), afterNow.getMillis());

        InOrder order = inOrder(mockComparator, mockComparator2);
        order.verify(mockComparator, atLeast(1)).compare(any(File.class), any(File.class));
        order.verify(mockComparator2, atLeast(1)).compare(any(File.class), any(File.class));
    }

    private File createMockFilePath(String name, DateTime lastModified) {
        File mockFilePath = mock(File.class);
        when(mockFilePath.exists()).thenReturn(true);
        when(mockFilePath.isDirectory()).thenReturn(false);
        when(mockFilePath.isFile()).thenReturn(true);
        when(mockFilePath.getName()).thenReturn(name);
        when(mockFilePath.lastModified()).thenReturn(lastModified.getMillis());
        return mockFilePath;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void sort_FailWithNull()
    throws PathException {
        createDirectoryListing(new File[0]).sort((Comparator<File>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void sort2_FailWithNull()
    throws PathException {
        createDirectoryListing(new File[0]).sort((List<Comparator<File>>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void sort2_FailWithNullElement()
    throws PathException {
        List<Comparator<File>> list = Lists.newArrayList();
        list.add((Comparator<File>) null);
        createDirectoryListing(new File[] { null }).sort(list);
    }
}
