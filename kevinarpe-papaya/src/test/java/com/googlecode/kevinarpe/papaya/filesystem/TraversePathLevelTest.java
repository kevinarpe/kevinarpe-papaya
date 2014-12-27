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
import org.powermock.api.mockito.PowerMockito;
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
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest({
    DirectoryListing.class,
    AbstractTraversePathIteratorImpl.class,
    TraversePathLevel.DescendDirFileFilter.class,
    TraversePathLevel.IterateFileFilter.class,
    TraversePathIterSettingsImpl.class
})
public class TraversePathLevelTest
extends PowerMockTestCase {

    private AbstractTraversePathIteratorImpl mockAbstractTraversePathIteratorImpl;
    private TraversePathLevel.Factory mockFactory;
    private DirectoryListing mockOrigDirectoryListing;
    private DirectoryListing mockDescendDirDirectoryListing;
    private DirectoryListing mockIterateDirectoryListing;
    private TraversePathLevel.DescendDirFileFilter mockDescendDirFileFilter;
    private TraversePathLevel.IterateFileFilter mockIterateFileFilter;
    private PathFilter mockPathFilter;
    private Comparator<File> mockDescendDirPathComparator;
    private Comparator<File> mockIteratePathComparator;
    private List<File> mockPathList;
    private Iterator<File> mockDescendDirDirectoryListingIter;
    private Iterator<File> mockIterateDirectoryListingIter;
    private File mockPath;

    private final File dirPath = new File("topDir");
    private final int depth = 4;

    @SuppressWarnings("unchecked")
    @BeforeMethod(alwaysRun = true)
    public void beforeEachTest() {
        mockAbstractTraversePathIteratorImpl = mock(AbstractTraversePathIteratorImpl.class);
        mockFactory = mock(TraversePathLevel.Factory.class);
        mockOrigDirectoryListing = mock(DirectoryListing.class);
        mockDescendDirDirectoryListing = mock(DirectoryListing.class);
        mockIterateDirectoryListing = mock(DirectoryListing.class);
        mockDescendDirFileFilter = mock(TraversePathLevel.DescendDirFileFilter.class);
        mockIterateFileFilter = mock(TraversePathLevel.IterateFileFilter.class);
        mockPathFilter = mock(PathFilter.class);
        mockDescendDirPathComparator = (Comparator<File>) mock(Comparator.class);
        mockIteratePathComparator = (Comparator<File>) mock(Comparator.class);
        mockPathList = (List<File>) mock(List.class);
        mockDescendDirDirectoryListingIter = (Iterator<File>) mock(Iterator.class);
        mockIterateDirectoryListingIter = (Iterator<File>) mock(Iterator.class);
        mockPath = mock(File.class);
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
                dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS);
    }

    @Test
    public void ctor_FailWithNewDirectoryListingThrowsPathException()
    throws PathException {
        PathException expected = new PathException(
            PathExceptionReason.PATH_IS_DIRECTORY, new File("dummy"), null, "message");
        when(mockFactory.newDirectoryListingInstance(
            dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS))
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
        when(mockPath.isDirectory()).thenReturn(false);
        assertFalse(classUnderTest.accept(mockPath));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndNoPathFilter() {
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter((PathFilter) null, depth);
        when(mockPath.isDirectory()).thenReturn(true);
        assertTrue(classUnderTest.accept(mockPath));
    }

    @Test
    public void DescendDirFileFilter_accept_PassWithPathIsDirectoryAndHasPathFilter() {
        TraversePathLevel.DescendDirFileFilter classUnderTest =
            new TraversePathLevel.DescendDirFileFilter(mockPathFilter, depth);
        when(mockPath.isDirectory()).thenReturn(true);

        for (boolean accept : new boolean[] { true, false }) {
            when(mockPathFilter.accept(mockPath, depth)).thenReturn(accept);
            assertEquals(classUnderTest.accept(mockPath), accept);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getDescendDirDirectoryListing()
    //

    // Disabled due to random Power/Mockito failures:

    /**
     getDescendDirDirectoryListing_Pass(com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest)  Time elapsed: 0.001 sec  <<< FAILURE!
     org.mockito.exceptions.misusing.MissingMethodInvocationException:
     when() requires an argument which has to be 'a method call on a mock'.
     For example:
     when(mock.getArticles()).thenReturn(articles);

     Also, this error might show up because:
     1. you stub either of: final/private/equals()/hashCode() methods.
     Those methods *cannot* be stubbed/verified.
     2. inside when() you don't call method on mock but on some other object.
     3. the parent of the mocked class is not public.
     It is a limitation of the mock engine.

     at org.powermock.api.mockito.PowerMockito.when(PowerMockito.java:495)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.core_getDescendDirDirectoryListing_Pass(TraversePathLevelTest.java:207)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.getDescendDirDirectoryListing_Pass(TraversePathLevelTest.java:194)
     */
//    @Test
//    public void getDescendDirDirectoryListing_Pass()
//    throws PathException {
//        core_getDescendDirDirectoryListing_Pass();
//    }

    private TraversePathLevel core_getDescendDirDirectoryListing_Pass()
    throws PathException {
        when(
            mockFactory.newDirectoryListingInstance(
                dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS))
            .thenReturn(mockOrigDirectoryListing);
        when(mockFactory.newDirectoryListingInstance(mockOrigDirectoryListing))
            .thenReturn(mockDescendDirDirectoryListing);

        // This is related to craziness with JaCoCo (code coverage) + Mockito in Eclipse.
        PowerMockito.when(mockAbstractTraversePathIteratorImpl.withOptionalDescendDirPathFilter())
            .thenReturn(mockPathFilter);
//        PowerMockito.doReturn(mockPathFilter)
//            .when(mockAbstractTraversePathIteratorImpl).withOptionalDescendDirPathFilter();

        when(mockFactory.newDescendDirFileFilterInstance(mockPathFilter, depth))
            .thenReturn(mockDescendDirFileFilter);
        when(mockAbstractTraversePathIteratorImpl.withOptionalDescendDirPathComparator())
            .thenReturn(mockDescendDirPathComparator);

        TraversePathLevel tpl =
            new TraversePathLevel(mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);

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

    // Disabled due to random Power/Mockito failures:
    /**
     getDescendDirDirectoryListingIter_Pass(com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest)  Time elapsed: 0.006 sec  <<< FAILURE!
     org.mockito.exceptions.misusing.MissingMethodInvocationException:
     when() requires an argument which has to be 'a method call on a mock'.
     For example:
     when(mock.getArticles()).thenReturn(articles);

     Also, this error might show up because:
     1. you stub either of: final/private/equals()/hashCode() methods.
     Those methods *cannot* be stubbed/verified.
     2. inside when() you don't call method on mock but on some other object.
     3. the parent of the mocked class is not public.
     It is a limitation of the mock engine.

     at org.powermock.api.mockito.PowerMockito.when(PowerMockito.java:495)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.core_getDescendDirDirectoryListing_Pass(TraversePathLevelTest.java:207)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.getDescendDirDirectoryListingIter_Pass(TraversePathLevelTest.java:241)
     */
//    @Test
//    public void getDescendDirDirectoryListingIter_Pass()
//    throws PathException {
//        TraversePathLevel tpl = core_getDescendDirDirectoryListing_Pass();
//
//        when(mockDescendDirDirectoryListing.getChildPathList()).thenReturn(mockPathList);
//        when(mockPathList.iterator()).thenReturn(mockDescendDirDirectoryListingIter);
//
//        assertSame(mockDescendDirDirectoryListingIter, tpl.getDescendDirDirectoryListingIter());
//
//        verify(mockDescendDirDirectoryListing, times(1)).getChildPathList();
//        verify(mockPathList, times(1)).iterator();
//
//        // Run exactly the same test + verify again.  This confirms the result is cached internally.
//        assertSame(mockDescendDirDirectoryListingIter, tpl.getDescendDirDirectoryListingIter());
//
//        verify(mockDescendDirDirectoryListing, times(1)).getChildPathList();
//        verify(mockPathList, times(1)).iterator();
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.IterateFileFilter.accept()
    //

    @Test
    public void IterateFileFilter_accept_PassWithNonNullPathFilter() {
        TraversePathLevel.IterateFileFilter classUnderTest =
            new TraversePathLevel.IterateFileFilter(mockPathFilter, depth);

        for (boolean accept : new boolean[] { true, false }) {
            when(mockPathFilter.accept(mockPath, depth)).thenReturn(accept);
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
    // TraversePathLevel.getIterateDirectoryListing()
    //

    // Disabled due to random Power/Mockito failures:
    /**
     getIterateDirectoryListing_Pass(com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest)  Time elapsed: 0 sec  <<< FAILURE!
     org.mockito.exceptions.misusing.MissingMethodInvocationException:
     when() requires an argument which has to be 'a method call on a mock'.
     For example:
     when(mock.getArticles()).thenReturn(articles);

     Also, this error might show up because:
     1. you stub either of: final/private/equals()/hashCode() methods.
     Those methods *cannot* be stubbed/verified.
     2. inside when() you don't call method on mock but on some other object.
     3. the parent of the mocked class is not public.
     It is a limitation of the mock engine.

     at org.powermock.api.mockito.PowerMockito.when(PowerMockito.java:495)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.core_getIterateDirectoryListing_Pass(TraversePathLevelTest.java:298)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.getIterateDirectoryListing_Pass(TraversePathLevelTest.java:288)
     */
//    @Test
//    public void getIterateDirectoryListing_Pass()
//    throws PathException {
//        core_getIterateDirectoryListing_Pass();
//    }

    private TraversePathLevel core_getIterateDirectoryListing_Pass()
    throws PathException {
        when(mockFactory.newDirectoryListingInstance(
            dirPath, TraversePathLevel.DEFAULT_DIRECTORY_LISTING_LIST_CLASS))
            .thenReturn(mockOrigDirectoryListing);

        // This is related to craziness with JaCoCo (code coverage) + Mockito in Eclipse.
        when(mockAbstractTraversePathIteratorImpl.withOptionalIteratePathFilter())
            .thenReturn(mockPathFilter);
//        doReturn(mockPathFilter).when(mockAbstractTraversePathIteratorImpl).withOptionalIteratePathFilter();

        when(mockFactory.newDirectoryListingInstance(mockOrigDirectoryListing))
            .thenReturn(mockIterateDirectoryListing);
        when(mockFactory.newIterateFileFilterInstance(mockPathFilter, depth))
            .thenReturn(mockIterateFileFilter);
        when(mockAbstractTraversePathIteratorImpl.withOptionalIteratePathComparator())
            .thenReturn(mockIteratePathComparator);

        TraversePathLevel tpl =
            new TraversePathLevel(mockAbstractTraversePathIteratorImpl, mockFactory, dirPath, depth);

        assertSame(mockIterateDirectoryListing, tpl.getIterateDirectoryListing());

        verify(mockIterateDirectoryListing, times(1)).filter(mockIterateFileFilter);
        verify(mockIterateDirectoryListing, times(1)).sort(mockIteratePathComparator);

        // Run exactly the same test + verify again.  This confirms the result is cached internally.
        assertSame(mockIterateDirectoryListing, tpl.getIterateDirectoryListing());

        verify(mockIterateDirectoryListing, times(1)).filter(mockIterateFileFilter);
        verify(mockIterateDirectoryListing, times(1)).sort(mockIteratePathComparator);

        return tpl;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathLevel.getIterateDirectoryListingIter()
    //

    // Disabled due to random Power/Mockito failures:
    /**
     getIterateDirectoryListingIter_Pass(com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest)  Time elapsed: 0.001 sec  <<< FAILURE!
     org.mockito.exceptions.misusing.MissingMethodInvocationException:
     when() requires an argument which has to be 'a method call on a mock'.
     For example:
     when(mock.getArticles()).thenReturn(articles);

     Also, this error might show up because:
     1. you stub either of: final/private/equals()/hashCode() methods.
     Those methods *cannot* be stubbed/verified.
     2. inside when() you don't call method on mock but on some other object.
     3. the parent of the mocked class is not public.
     It is a limitation of the mock engine.

     at org.powermock.api.mockito.PowerMockito.when(PowerMockito.java:495)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.core_getIterateDirectoryListing_Pass(TraversePathLevelTest.java:298)
     at com.googlecode.kevinarpe.papaya.filesystem.TraversePathLevelTest.getIterateDirectoryListingIter_Pass(TraversePathLevelTest.java:333)
     */
//    @Test
//    public void getIterateDirectoryListingIter_Pass()
//        throws PathException {
//        TraversePathLevel tpl = core_getIterateDirectoryListing_Pass();
//
//        when(mockIterateDirectoryListing.getChildPathList()).thenReturn(mockPathList);
//        when(mockPathList.iterator()).thenReturn(mockIterateDirectoryListingIter);
//
//        assertSame(mockIterateDirectoryListingIter, tpl.getIterateDirectoryListingIter());
//
//        verify(mockIterateDirectoryListing, times(1)).getChildPathList();
//        verify(mockPathList, times(1)).iterator();
//
//        // Run exactly the same test + verify again.  This confirms the result is cached internally.
//        assertSame(mockIterateDirectoryListingIter, tpl.getIterateDirectoryListingIter());
//
//        verify(mockIterateDirectoryListing, times(1)).getChildPathList();
//        verify(mockPathList, times(1)).iterator();
//    }
}
