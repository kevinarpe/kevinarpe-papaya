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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest({
    DirectoryListing.class,
    TraversePathIterator.class,
    TraversePathLevel.DescendDirFileFilter.class,
    TraversePathLevel.IterateFileFilter.class
})
public class TraversePathLevelTest
extends PowerMockTestCase {

    private TraversePathIterator mockTraversePathIterator;
    private TraversePathLevel.Factory mockFactory;
    private DirectoryListing mockOrigDirectoryListing;
    private DirectoryListing mockDescendDirDirectoryListing;
    private DirectoryListing mockIterateDirectoryListing;
    private TraversePathLevel.DescendDirFileFilter mockDescendDirFileFilter;
    private TraversePathLevel.IterateFileFilter mockIterateFileFilter;
    private PathFilter mockPathFilter;
    private Comparator<File> mockDescendDirPathComparator;
    private List<File> mockPathList;
    private Iterator<File> mockDescendDirDirectoryListingIter;

    private final File dirPath = new File("topDir");
    private final int depth = 4;

    @BeforeMethod(alwaysRun = true)
    public void beforeEachTest() {
        mockTraversePathIterator = mock(TraversePathIterator.class);
        mockFactory = mock(TraversePathLevel.Factory.class);
        mockOrigDirectoryListing = mock(DirectoryListing.class);
        mockDescendDirDirectoryListing = mock(DirectoryListing.class);
        mockIterateDirectoryListing = mock(DirectoryListing.class);
        mockDescendDirFileFilter = mock(TraversePathLevel.DescendDirFileFilter.class);
        mockIterateFileFilter = mock(TraversePathLevel.IterateFileFilter.class);
        mockPathFilter = mock(PathFilter.class);
        mockDescendDirPathComparator = (Comparator<File>) mock(Comparator.class);
        mockPathList = (List<File>) mock(List.class);
        mockDescendDirDirectoryListingIter = (Iterator<File>) mock(Iterator.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.ctor()
    //

    @Test
    public void ctor_Pass()
    throws PathException {
        new TraversePathLevel(
            mockTraversePathIterator, dirPath, depth, mockFactory);
        verify(mockFactory)
            .newDirectoryListingInstance(
                dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS);
    }

    @Test//TODO:(expectedExceptions = PathException.class)
    public void ctor_FailWithNewDirectoryListingThrowsPathException()
    throws PathException {
        PathException expected = new PathException(
            PathExceptionReason.PATH_IS_DIRECTORY, new File("dummy"), null, "message");
        when(mockFactory.newDirectoryListingInstance(
                dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS))
            .thenThrow(expected);
        try {
            new TraversePathLevel(
                mockTraversePathIterator, dirPath, depth, mockFactory);
        }
        catch (PathException actual) {
            assertSame(actual, expected);
            //TODO:throw actual;
        }
    }

    // TODO: ctor_FailWithNull

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirDirectoryListing()
    //

    @Test
    public void getDescendDirDirectoryListing_Pass()
    throws PathException {
        core_getDescendDirDirectoryListing_Pass();
    }

    private TraversePathLevel core_getDescendDirDirectoryListing_Pass()
    throws PathException {
        when(mockFactory.newDirectoryListingInstance(
            dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS))
            .thenReturn(mockOrigDirectoryListing);
        when(mockFactory.newDirectoryListingInstance(mockOrigDirectoryListing))
            .thenReturn(mockDescendDirDirectoryListing);
        when(mockTraversePathIterator.getOptionalDescendDirPathFilter()).thenReturn(mockPathFilter);
        when(mockFactory.newDescendDirFileFilterInstance(mockPathFilter, depth))
            .thenReturn(mockDescendDirFileFilter);
        when(mockTraversePathIterator.getOptionalDescendDirPathComparator())
            .thenReturn(mockDescendDirPathComparator);

        TraversePathLevel tpl = new TraversePathLevel(
            mockTraversePathIterator, dirPath, depth, mockFactory);

        assertSame(mockDescendDirDirectoryListing, tpl.getDescendDirDirectoryListing());

        verify(mockDescendDirDirectoryListing, times(1)).filter(mockDescendDirFileFilter);
        verify(mockDescendDirDirectoryListing, times(1)).sort(mockDescendDirPathComparator);

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(mockDescendDirDirectoryListing, tpl.getDescendDirDirectoryListing());

        verify(mockDescendDirDirectoryListing, times(1)).filter(mockDescendDirFileFilter);
        verify(mockDescendDirDirectoryListing, times(1)).sort(mockDescendDirPathComparator);

        return tpl;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirDirectoryListingIter()
    //

    @Test
    public void getDescendDirDirectoryListingIter_Pass()
    throws PathException {
        TraversePathLevel tpl = core_getDescendDirDirectoryListing_Pass();

        when(mockDescendDirDirectoryListing.getChildPathList()).thenReturn(mockPathList);
        when(mockPathList.iterator()).thenReturn(mockDescendDirDirectoryListingIter);

        assertSame(mockDescendDirDirectoryListingIter, tpl.getDescendDirDirectoryListingIter());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.DescendDirFileFilter.accept()
    //

    @Test
    public void DescendDirFileFilter_accept_PassWithPathNotDirectory() {
        final int depth = 4;
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter((PathFilter) null, depth);
        File mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(false);
        assertFalse(classUnderTest.accept(mockFile));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndNoPathFilter() {
        final int depth = 4;
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter((PathFilter) null, depth);
        File mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(true);
        assertTrue(classUnderTest.accept(mockFile));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndHasPathFilter() {
        final int depth = 4;
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter(mockPathFilter, depth);
        File mockPath = mock(File.class);
        when(mockPath.isDirectory()).thenReturn(true);

        for (boolean accept : new boolean[] { true, false }) {
            when(mockPathFilter.accept(mockPath, depth)).thenReturn(accept);
            assertEquals(classUnderTest.accept(mockPath), accept);
        }
    }
}
