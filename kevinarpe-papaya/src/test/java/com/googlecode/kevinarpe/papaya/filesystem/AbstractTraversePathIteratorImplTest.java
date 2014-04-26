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
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest(TraversePathLevel.class)
public class AbstractTraversePathIteratorImplTest
extends PowerMockTestCase {

    private static final PathException DUMMY_PATH_EXCEPTION =
        new PathException(
            PathExceptionReason.PATH_IS_DIRECTORY, new File("dummy"), (File) null, "message");

    private AbstractTraversePathIteratorImpl.Factory mockFactory;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockFactory = PowerMockito.mock(AbstractTraversePathIteratorImpl.Factory.class);
    }

    private static class _TraversePathIterator
    extends AbstractTraversePathIteratorImpl {

        _TraversePathIterator(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIterateFileComparator) {
            super(dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator);
        }

        _TraversePathIterator(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIterateFileComparator,
                Factory factory) {
            super(dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator,
                factory);
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public File next() {
            return null;
        }
    }

    private TraversePathLevel _addLevel(_TraversePathIterator classUnderTest, File dirPath)
    throws PathException {
        final int origDepth = classUnderTest.getDepth();
        final int newDepth = 1 + origDepth;
        TraversePathLevel mockTraversePathLevel = PowerMockito.mock(TraversePathLevel.class);
        Mockito.when(mockFactory.newInstance(classUnderTest, dirPath, newDepth))
            .thenReturn(mockTraversePathLevel);

        Assert.assertSame(classUnderTest.tryAddLevel(dirPath), mockTraversePathLevel);
        Assert.assertEquals(classUnderTest.getDepth(), newDepth);
        Mockito.verify(mockFactory).newInstance(classUnderTest, dirPath, newDepth);
        return mockTraversePathLevel;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTraversePathIteratorImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        new BaseTraversePathIterTest.ctor_Pass_Helper() {

            @Override
            protected TraversePathIterSettingsImpl newInstance(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIteratePathComparator) {
                return new _TraversePathIterator(
                    dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                    optDescendDirPathComparator, optIteratePathFilter, optIteratePathComparator,
                    mockFactory);
            }
        };
    }

    @Test
    public void ctor_Pass2() {
        new BaseTraversePathIterTest.ctor_Pass_Helper() {

            @Override
            protected TraversePathIterSettingsImpl newInstance(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIteratePathComparator) {
                return new _TraversePathIterator(
                    dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                    optDescendDirPathComparator, optIteratePathFilter, optIteratePathComparator);
            }
        };
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTraversePathIteratorImpl.tryAddLevel()
    //

    @Test
    public void tryAddLevel_Pass()
    throws PathException {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, null, null, null, null, null, mockFactory);
        _addLevel(classUnderTest, new File("dummy"));
    }

    @Test//(expectedExceptions = PathRuntimeException.class)
    public void tryAddLevel_FailWithPathRuntimeException()
    throws PathException {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(
                null, null, TraversePathExceptionPolicy.THROW, null, null, null, null, mockFactory);

        final File dirPath = new File("dummy");
        Mockito.when(mockFactory.newInstance(classUnderTest, dirPath, 1)).thenThrow(DUMMY_PATH_EXCEPTION);

        try {
            classUnderTest.tryAddLevel(dirPath);
        }
        catch (Exception e) {
            Assert.assertSame(e.getClass(), PathRuntimeException.class);
        }
    }

    @Test
    public void tryAddLevel_PassWithIgnoredPathRuntimeException()
    throws PathException {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, TraversePathExceptionPolicy.IGNORE, null, null,
                                        null, null, mockFactory);

        Assert.assertEquals(classUnderTest.getDepth(), 0);

        final File dirPath = new File("dummy");
        Mockito.when(mockFactory.newInstance(classUnderTest, dirPath, 1)).thenThrow(DUMMY_PATH_EXCEPTION);

        Assert.assertNull(classUnderTest.tryAddLevel(dirPath));
        Assert.assertEquals(classUnderTest.getDepth(), 0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTraversePathIteratorImpl.tryRemoveAndGetNextLevel()
    //

    @Test
    public void tryRemoveAndGetNextLevel_PassWithNoNextLevel() {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, null, null, null, null, null, mockFactory);
        Assert.assertEquals(classUnderTest.getDepth(), 0);
        Assert.assertNull(classUnderTest.tryRemoveAndGetNextLevel());
    }

    @Test
    public void tryRemoveAndGetNextLevel_PassWithOneNextLevel()
    throws PathException {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, null, null, null, null, null, mockFactory);

        final File dirPath = new File("dummy");
        TraversePathLevel mockTraversePathLevel = _addLevel(classUnderTest, dirPath);

        Assert.assertEquals(classUnderTest.getDepth(), 1);
        Assert.assertNull(classUnderTest.tryRemoveAndGetNextLevel());
        Assert.assertEquals(classUnderTest.getDepth(), 0);
    }

    @Test
    public void tryRemoveAndGetNextLevel_PassWithTwoNextLevels()
    throws PathException {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, null, null, null, null, null, mockFactory);

        final File dirPath = new File("dummy");
        TraversePathLevel mockTraversePathLevel = _addLevel(classUnderTest, dirPath);

        final File dirPath2 = new File("dummy2");
        TraversePathLevel mockTraversePathLevel2 = _addLevel(classUnderTest, dirPath2);

        Assert.assertEquals(classUnderTest.getDepth(), 2);
        Assert.assertSame(classUnderTest.tryRemoveAndGetNextLevel(), mockTraversePathLevel);
        Assert.assertEquals(classUnderTest.getDepth(), 1);
        Assert.assertNull(classUnderTest.tryRemoveAndGetNextLevel());
        Assert.assertEquals(classUnderTest.getDepth(), 0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // AbstractTraversePathIteratorImpl.remove()
    //

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void remove_FailWithUnsupportedOperationException() {
        _TraversePathIterator classUnderTest =
            new _TraversePathIterator(null, null, null, null, null, null, null, mockFactory);
        classUnderTest.remove();
    }
}
