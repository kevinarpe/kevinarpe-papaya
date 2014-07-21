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

import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
//@PrepareForTest({
//    DirectoryListing.class,
//    AbstractTraversePathIteratorImpl.class,
//    TraversePathLevel.DescendDirFileFilter.class,
//    TraversePathLevel.IterateFileFilter.class,
//    TraversePathIterSettingsImpl.class
//})
@PrepareForTest(AbstractTraversePathIteratorImpl.class)
public class TraversePathLevelTest
extends PowerMockTestCase {

    private AbstractTraversePathIteratorImpl mockAbstractTraversePathIteratorImpl;
    private TraversePathLevel.Factory mockFactory;
    private DirectoryListing mockOrigDirListing;
    private DirectoryListing mockDescendDirListing;
    private DirectoryListing mockDescendDirListing2;
    private DirectoryListing mockIterateDirListing;
    private DirectoryListing mockIterateDirListing2;
    private FileFilter mockDescendDirFileFilter;
    private FileFilter mockIterateFileFilter;
    private PathFilter mockDescendPathFilter;
    private PathFilter mockIteratePathFilter;
    private Comparator<File> mockDescendDirPathComparator;
    private Comparator<File> mockIteratePathComparator;
    private List<File> mockDescendDirPathList;
    private List<File> mockIterateDirPathList;
    private Iterator<File> descendDirListingIter;
    private Iterator<File> iterateDirListingIter;
    private File mockPath;

    private final File dirPath = new File("topDir");
    private final int depth = 4;

    @SuppressWarnings("unchecked")
    @BeforeMethod(alwaysRun = true)
    public void beforeEachTest() {
        mockAbstractTraversePathIteratorImpl =
            PowerMockito.mock(
                AbstractTraversePathIteratorImpl.class,
                Mockito.withSettings().name("mockAbstractTraversePathIteratorImpl"));
        mockFactory = Mockito.mock(TraversePathLevel.Factory.class, "mockFactory");
        mockOrigDirListing = Mockito.mock(DirectoryListing.class, "mockOrigDirListing");
        mockDescendDirListing = Mockito.mock(DirectoryListing.class, "mockDescendDirListing");
        mockDescendDirListing2 = Mockito.mock(DirectoryListing.class, "mockDescendDirListing2");
        mockIterateDirListing = Mockito.mock(DirectoryListing.class, "mockIterateDirListing");
        mockIterateDirListing2 = Mockito.mock(DirectoryListing.class, "mockIterateDirListing2");
        mockDescendDirFileFilter = Mockito.mock(FileFilter.class, "mockDescendDirFileFilter");
        mockIterateFileFilter = Mockito.mock(FileFilter.class, "mockIterateFileFilter");
        mockDescendPathFilter = Mockito.mock(PathFilter.class, "mockDescendPathFilter");
        mockIteratePathFilter = Mockito.mock(PathFilter.class, "mockIteratePathFilter");
        {
            @SuppressWarnings("unchecked")
            Comparator<File> x =
                (Comparator<File>) Mockito.mock(Comparator.class, "mockDescendDirPathComparator");
            mockDescendDirPathComparator = x;
        }
        {
            @SuppressWarnings("unchecked")
            Comparator<File> x =
                (Comparator<File>) Mockito.mock(Comparator.class, "mockIteratePathComparator");
            mockIteratePathComparator = x;
        }
        {
            @SuppressWarnings("unchecked")
            List<File> x = (List<File>) Mockito.mock(List.class, "mockDescendDirPathList ");
            mockDescendDirPathList = x;
        }
        {
            @SuppressWarnings("unchecked")
            List<File> x = (List<File>) Mockito.mock(List.class, "mockIterateDirPathList");
            mockIterateDirPathList = x;
        }
        {
            @SuppressWarnings("unchecked")
            Iterator<File> x =
                (Iterator<File>) Mockito.mock(Iterator.class, "descendDirListingIter");
            descendDirListingIter = x;
        }
        {
            @SuppressWarnings("unchecked")
            Iterator<File> x =
                (Iterator<File>) Mockito.mock(Iterator.class, "iterateDirListingIter");
            iterateDirListingIter = x;
        }
        mockPath = Mockito.mock(File.class, "mockPath");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.ctor()
    //

    @Test
    public void ctor_Pass()
    throws PathException {
        new TraversePathLevel(mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);
        verify(mockFactory)
            .newDirectoryListingInstance(
                dirPath);
    }

    @Test
    public void ctor_FailWithNewDirectoryListingThrowsPathException()
    throws PathException {
        PathException expected = new PathException(
            PathExceptionReason.PATH_IS_DIRECTORY, new File("dummy"), null, "message");
        Mockito.when(mockFactory.newDirectoryListingInstance(
                dirPath))
            .thenThrow(expected);
        try {
            new TraversePathLevel(mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);
        }
        catch (PathException actual) {
            assertSame(actual, expected);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull()
    throws PathException {
        new TraversePathLevel((AbstractTraversePathIteratorImpl) null, mockFactory, dirPath, depth);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull2()
    throws PathException {
        new TraversePathLevel(
            mockAbstractTraversePathIteratorImpl, (TraversePathLevel.Factory) null, dirPath, depth);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull3()
    throws PathException {
        new TraversePathLevel(
            mockAbstractTraversePathIteratorImpl, TraversePathLevel.FactoryImpl.INSTANCE, (File) null, depth);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ctor_FailWithInvalidDepth()
    throws PathException {
        new TraversePathLevel(mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, 0);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.DescendDirFileFilter.accept()
    //

    @Test
    public void DescendDirFileFilter_accept_PassWithPathNotDirectory() {
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter((PathFilter) null, depth);
        Mockito.when(mockPath.isDirectory()).thenReturn(false);
        assertFalse(classUnderTest.accept(mockPath));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndNoPathFilter() {
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter((PathFilter) null, depth);
        Mockito.when(mockPath.isDirectory()).thenReturn(true);
        assertTrue(classUnderTest.accept(mockPath));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndHasPathFilter() {
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter(mockDescendPathFilter, depth);
        Mockito.when(mockPath.isDirectory()).thenReturn(true);

        for (boolean accept : new boolean[] { true, false }) {
            Mockito.when(mockDescendPathFilter.accept(mockPath, depth)).thenReturn(accept);
            assertEquals(classUnderTest.accept(mockPath), accept);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirListing()
    //

    @Test
    public void getDescendDirDirectoryListing_Pass()
    throws PathException {
        core_getDescendDirDirectoryListing_Pass();
    }

    private TraversePathLevel core_getDescendDirDirectoryListing_Pass()
    throws PathException {
        Mockito.when(mockFactory.newDirectoryListingInstance(dirPath))
            .thenReturn(mockOrigDirListing);
        Mockito.when(mockAbstractTraversePathIteratorImpl.withDescendDirPathFilter())
            .thenReturn(mockDescendPathFilter);
        Mockito.when(mockFactory.newDescendDirFileFilterInstance(mockDescendPathFilter, depth))
            .thenReturn(mockDescendDirFileFilter);
        Mockito.when(mockOrigDirListing.filter(mockDescendDirFileFilter))
            .thenReturn(mockDescendDirListing);
        Mockito.when(mockAbstractTraversePathIteratorImpl.withDescendDirPathComparator())
            .thenReturn(mockDescendDirPathComparator);
        Mockito.when(mockDescendDirListing.sort(mockDescendDirPathComparator))
            .thenReturn(mockDescendDirListing2);
        Mockito.when(mockDescendDirListing2.iterator()).thenReturn(descendDirListingIter);

        TraversePathLevel level =
            new TraversePathLevel(
                mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);

        assertSame(mockDescendDirListing2, level.getDescendDirListing());

        verify(mockOrigDirListing).filter(mockDescendDirFileFilter);
        verify(mockDescendDirListing).sort(mockDescendDirPathComparator);

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(mockDescendDirListing2, level.getDescendDirListing());

        verify(mockOrigDirListing).filter(mockDescendDirFileFilter);
        verify(mockDescendDirListing).sort(mockDescendDirPathComparator);

        return level;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirDirectoryListingIter()
    //

    @Test
    public void getDescendDirDirectoryListingIter_Pass()
    throws PathException {
        TraversePathLevel tpl = core_getDescendDirDirectoryListing_Pass();

        assertSame(descendDirListingIter, tpl.getDescendDirListingIter());

        verify(mockDescendDirListing2).iterator();

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(descendDirListingIter, tpl.getDescendDirListingIter());

        // We have called 'getDescendDirListingIter()' a second time, but we do not increase the
        // verify call count here -- keep as one to confirm we are cached.
        verify(mockDescendDirListing2).iterator();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.IterateFileFilter.accept()
    //

    @Test
    public void IterateFileFilter_accept_PassWithNonNullPathFilter() {
        TraversePathLevel.IterateFileFilter classUnderTest =
            new TraversePathLevel.IterateFileFilter(mockIteratePathFilter, depth);

        for (boolean accept : new boolean[] { true, false }) {
            Mockito.when(mockIteratePathFilter.accept(mockPath, depth)).thenReturn(accept);
            assertEquals(classUnderTest.accept(mockPath), accept);
        }
    }

    @Test
    public void IterateFileFilter_accept_PassWithNullPathFilter() {
        TraversePathLevel.IterateFileFilter classUnderTest =
            new TraversePathLevel.IterateFileFilter((PathFilter) null, depth);

        assertTrue(classUnderTest.accept(mockPath));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getIterateDirListing()
    //

    @Test
    public void getIterateDirectoryListing_Pass()
    throws PathException {
        core_getIterateDirectoryListing_Pass();
    }

    private TraversePathLevel core_getIterateDirectoryListing_Pass()
    throws PathException {
        Mockito.when(mockFactory.newDirectoryListingInstance(dirPath))
            .thenReturn(mockOrigDirListing);
        Mockito.when(mockAbstractTraversePathIteratorImpl.withIteratePathFilter())
            .thenReturn(mockIteratePathFilter);
        Mockito.when(mockFactory.newIterateFileFilterInstance(mockIteratePathFilter, depth))
            .thenReturn(mockIterateFileFilter);
        Mockito.when(mockOrigDirListing.filter(mockIterateFileFilter))
            .thenReturn(mockIterateDirListing);
        Mockito.when(mockAbstractTraversePathIteratorImpl.withIteratePathComparator())
            .thenReturn(mockIteratePathComparator);
        Mockito.when(mockIterateDirListing.sort(mockIteratePathComparator))
            .thenReturn(mockIterateDirListing2);
        Mockito.when(mockIterateDirListing2.iterator()).thenReturn(iterateDirListingIter);

        TraversePathLevel tpl =
            new TraversePathLevel(
                mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);

        assertSame(mockIterateDirListing2, tpl.getIterateDirListing());

        verify(mockOrigDirListing).filter(mockIterateFileFilter);
        verify(mockIterateDirListing).sort(mockIteratePathComparator);

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(mockIterateDirListing2, tpl.getIterateDirListing());

        // We have called 'getIterateDirListing()' a second time, but we do not increase the verify
        // call count here -- keep as one to confirm we are cached.
        verify(mockOrigDirListing).filter(mockIterateFileFilter);
        verify(mockIterateDirListing).sort(mockIteratePathComparator);

        return tpl;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getIterateDirectoryListingIter()
    //

    @Test
    public void getIterateDirectoryListingIter_Pass()
        throws PathException {
        TraversePathLevel tpl = core_getIterateDirectoryListing_Pass();

        assertSame(iterateDirListingIter, tpl.getIterateDirListingIter());

        verify(mockIterateDirListing2).iterator();

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(iterateDirListingIter, tpl.getIterateDirListingIter());

        // We have called 'getIterateDirListingIter()' a second time, but we do not increase the
        // verify call count here -- keep as one to confirm we are cached.
        verify(mockIterateDirListing2).iterator();
    }
}
