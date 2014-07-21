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
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import org.joda.time.DateTime;
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
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DirectoryListingImplTest {

    private static File _createMockDirPath(File[] pathArr) {
        File mockDirPath = mock(File.class);
        when(mockDirPath.exists()).thenReturn(true);
        when(mockDirPath.isDirectory()).thenReturn(true);
        when(mockDirPath.isFile()).thenReturn(false);
        when(mockDirPath.listFiles()).thenReturn(pathArr);
        return mockDirPath;
    }

    private static DirectoryListingImpl _createDirectoryListing(File[] pathArr)
    throws PathException {
        File mockDirPath = _createMockDirPath(pathArr);
        DirectoryListingImpl x = new DirectoryListingImpl(mockDirPath);
        return x;
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.ctor(File)/ctor(File, Class)
    //

    @DataProvider
    private static Object[][] _ctor_Pass_Data() {
        return new Object[][] {
            { new File[] { } },
            { new File[] { new File("abc/123") } },
            { new File[] { new File("abc/123"), new File("def/456") } },
            { new File[] { new File("abc/123"), new File("def/456"), new File("ghi/789") } },
        };
    }

    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(File[] pathArr)
    throws PathException {
        File mockDirPath = _createMockDirPath(pathArr);
        DirectoryListingImpl classUnderTest = new DirectoryListingImpl(mockDirPath);
        assertSame(classUnderTest.getDirPath(), mockDirPath);
        assertEquals(classUnderTest.getChildPathList(), Arrays.asList(pathArr));

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull()
    throws PathException {
        new DirectoryListingImpl((File) null);
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
            new DirectoryListingImpl(mockDirPath);
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
        // A wee bit tricky!  The first 'false' is to pass the PathArgs.checkDirectoryExists(),
        // and the second 'true' is to trigger a specific exception after File.listFiles() fails.
        when(mockDirPath.isFile()).thenReturn(false).thenReturn(true);

        try {
            new DirectoryListingImpl(mockDirPath);
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
            new DirectoryListingImpl(mockDirPath);
        }
        catch (PathException e) {
            assertEquals(e.getReason(), PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY);
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
            new DirectoryListingImpl(mockDirPath);
        }
        catch (PathException e) {
            assertEquals(
                e.getReason(), PathExceptionReason.UNKNOWN);
            throw e;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.hashCode()/equals()
    //

    @Test(dataProvider = "_ctor_Pass_Data")
    public void hashCodeAndEquals_Pass(File[] pathArr)
    throws PathException {
        File[] pathArr2 = new File[pathArr.length + 1];
        System.arraycopy(pathArr, 0, pathArr2, 0, pathArr.length);
        pathArr2[pathArr2.length - 1] = new File("dummy");

        File mockDirPath = _createMockDirPath(pathArr);
        File mockDirPath2 = _createMockDirPath(pathArr2);

        new EqualsTester()
            .addEqualityGroup(
                new DirectoryListingImpl(mockDirPath),
                new DirectoryListingImpl(mockDirPath))
            .addEqualityGroup(
                new DirectoryListingImpl(mockDirPath2),
                new DirectoryListingImpl(mockDirPath2))
            .testEquals();
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.filter(FileFilter)
    //

    @Test(dataProvider = "_ctor_Pass_Data")
    public void filter_Pass(File[] pathArr)
    throws PathException {
        final FileFilter acceptAllFileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        final FileFilter acceptNoneFileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return false;
            }
        };

        _filterHelper(
            _createDirectoryListing(pathArr), acceptAllFileFilter, Arrays.asList(pathArr));
        _filterHelper(
            _createDirectoryListing(pathArr), acceptNoneFileFilter, Collections.<File>emptyList());

        ArrayList<File> expectedPathList = Lists.newArrayList(pathArr);
        DirectoryListing directoryListing = _createDirectoryListing(pathArr);
        while (!expectedPathList.isEmpty()) {
            final FileFilter rejectFirstFileFilter = new FileFilter() {
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
            directoryListing =
                _filterHelper(directoryListing, rejectFirstFileFilter, expectedPathList);
        }
    }

    private DirectoryListing _filterHelper(
            DirectoryListing directoryListing,
            FileFilter fileFilter,
            List<File> expectedPathList)
    throws PathException {
        DirectoryListing directoryListing2 = directoryListing.filter(fileFilter);
        assertNotSame(directoryListing2, directoryListing);
        assertEquals(directoryListing2.getChildPathList(), expectedPathList);
        return directoryListing2;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void filter_FailWithNull()
    throws PathException {
        _createDirectoryListing(new File[0]).filter((FileFilter) null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DirectoryListing.sort(Comparator<File>)
    //

    @Test
    public void sort_Pass()
    throws PathException {
        DateTime now = DateTime.now();
        DateTime beforeNow = now.plusHours(-1);
        DateTime afterNow = now.plusHours(+1);

        DirectoryListing directoryListing =
            _createDirectoryListing(new File[]{
                _createMockFilePath("def", afterNow),
                _createMockFilePath("abc", now),
                _createMockFilePath("ghi", beforeNow),
            });

        Comparator<File> mockComparator =
            _createMockComparator(new FileNameLexicographicalComparator());
        DirectoryListing directoryListing2 = directoryListing.sort(mockComparator);
        assertNotSame(directoryListing2, directoryListing);

        List<File> childPathList = directoryListing2.getChildPathList();
        assertEquals(childPathList.get(0).getName(), "abc");
        assertEquals(childPathList.get(1).getName(), "def");
        assertEquals(childPathList.get(2).getName(), "ghi");
        verify(mockComparator, atLeast(1)).compare(any(File.class), any(File.class));
    }

    private Comparator<File> _createMockComparator(final Comparator<File> comparator) {
        // We cannot spy on a final class with Mockito (or Powermock), so we fake a spy here.
        // Create a mock, but delegate compare(T, T) calls to a real Comparator<T> instance.
        @SuppressWarnings("unchecked")
        Comparator<File> mockComparator = (Comparator<File>) mock(Comparator.class);
        when(mockComparator.compare(any(File.class), any(File.class)))
            .thenAnswer(new Answer<Integer>() {
                @Override
                public Integer answer(InvocationOnMock invocation) throws Throwable {
                    File left = (File) invocation.getArguments()[0];
                    File right = (File) invocation.getArguments()[1];
                    return comparator.compare(left, right);
                }
            });
        return mockComparator;
    }

    private File _createMockFilePath(String name, DateTime lastModified) {
        File mockFilePath = mock(File.class);
        when(mockFilePath.exists()).thenReturn(true);
        when(mockFilePath.isDirectory()).thenReturn(false);
        when(mockFilePath.isFile()).thenReturn(true);
        when(mockFilePath.getName()).thenReturn(name);
        when(mockFilePath.lastModified()).thenReturn(lastModified.getMillis());
        return mockFilePath;
    }
}
