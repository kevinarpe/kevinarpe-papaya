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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest(DirectoryListing.class)
public class TraversePathLevelTest
extends PowerMockTestCase {

    private TraversePathIterator mockTraversePathIterator;
    private TraversePathLevel.DirectoryListingFactory mockDirectoryListingFactory;
    private DirectoryListing mockOrigDirectoryListing;
    private DirectoryListing mockDescendDirDirectoryListing;
    private DirectoryListing mockIterateDirectoryListing;
    private PathFilter mockPathFilter;

    @BeforeMethod(alwaysRun = true)
    public void beforeEachTest() {
        mockTraversePathIterator = mock(TraversePathIterator.class);
        mockDirectoryListingFactory = mock(TraversePathLevel.DirectoryListingFactory.class);
        mockOrigDirectoryListing = mock(DirectoryListing.class);
        mockDescendDirDirectoryListing = mock(DirectoryListing.class);
        mockIterateDirectoryListing = mock(DirectoryListing.class);
        mockPathFilter = mock(PathFilter.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.ctor()
    //

    @Test
    public void ctor_Pass()
    throws PathException {
        final File dirPath = new File("topDir");
        final int depth = 4;
        new TraversePathLevel(
            mockTraversePathIterator, dirPath, depth, mockDirectoryListingFactory);
        verify(mockDirectoryListingFactory)
            .newInstance(dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS);
    }

    // TODO: ctor_FailWithNull

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirDirectoryListing()
    //

    @Test
    public void getDescendDirDirectoryListing_Pass() {

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
