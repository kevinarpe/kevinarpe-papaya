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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TraversePathIterableTest {

    private BaseTraversePathIter expected;
    private TraversePathIterable classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        expected = BaseTraversePathIterTest.newInstance();
        classUnderTest =
            new TraversePathIterable(expected.getDirPath(), expected.getDepthPolicy());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterable.ctor()
    //

    @Test
    public void ctor_Pass() {
        BaseTraversePathIterTest.assertAttrSame(
            classUnderTest,
            expected.getDirPath(),
            expected.getDepthPolicy(),
            TraversePathIterable.DEFAULT_EXCEPTION_POLICY,
            (PathFilter) null,
            (Comparator<File>) null,
            (PathFilter) null,
            (Comparator<File>) null);
    }

    @Test
    public void ctor_Pass2() {
        new BaseTraversePathIterTest.ctor_Pass_Helper() {

            @Override
            protected BaseTraversePathIter newInstance(
                    File dirPath,
                    TraversePathDepthPolicy depthPolicy,
                    TraversePathExceptionPolicy exceptionPolicy,
                    PathFilter optDescendDirPathFilter,
                    Comparator<File> optDescendDirPathComparator,
                    PathFilter optIteratePathFilter,
                    Comparator<File> optIteratePathComparator) {
                return new TraversePathIterable(
                    dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                    optDescendDirPathComparator, optIteratePathFilter, optIteratePathComparator);
            }
        };
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullDirPath() {
        new TraversePathIterable((File) null, TraversePathDepthPolicy.DEPTH_LAST);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullDepthPolicy() {
        new TraversePathIterable(new File("dummy"), (TraversePathDepthPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterable.withDirPath()
    //

    @Test
    public void withDirPath_Pass() {
        final File newPath = new File("newPath");
        TraversePathIterable next = classUnderTest.withDirPath(newPath);
        assertNotSame(next, classUnderTest);
        assertEquals(next.getDirPath(), newPath);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDirPath_FailWithNull() {
        classUnderTest.withDirPath((File) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterable.withDepthPolicy()
    //

    @Test
    public void withDepthPolicy_Pass() {
        TraversePathDepthPolicy depthPolicy =
            (classUnderTest.getDepthPolicy() == TraversePathDepthPolicy.DEPTH_FIRST
                ? TraversePathDepthPolicy.DEPTH_LAST : TraversePathDepthPolicy.DEPTH_FIRST);
        TraversePathIterable next = classUnderTest.withDepthPolicy(depthPolicy);
        assertNotSame(next, classUnderTest);
        assertEquals(next.getDepthPolicy(), depthPolicy);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDepthPolicy_FailWithNull() {
        classUnderTest.withDepthPolicy((TraversePathDepthPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterable.withExceptionPolicy()
    //

    @Test
    public void withExceptionPolicy_Pass() {
        TraversePathExceptionPolicy exceptionPolicy =
            (classUnderTest.getExceptionPolicy() == TraversePathExceptionPolicy.THROW
                ? TraversePathExceptionPolicy.IGNORE : TraversePathExceptionPolicy.THROW);
        TraversePathIterable next = classUnderTest.withExceptionPolicy(exceptionPolicy);
        assertNotSame(next, classUnderTest);
        assertEquals(next.getExceptionPolicy(), exceptionPolicy);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withExceptionPolicy_FailWithNull() {
        classUnderTest.withExceptionPolicy((TraversePathExceptionPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterable.withOptionalDescendDirPathFilter()
    //

    @Test
    public void withOptionalDescendDirPathFilter_Pass() {
        PathFilter pathFilter = new PathFilter() {

            @Override
            public boolean accept(File path, int depth) {
                return false;
            }
        };
        TraversePathIterable next = classUnderTest.withOptionalDescendDirPathFilter(pathFilter);
        assertNotSame(next, classUnderTest);
        assertEquals(next.getOptionalDescendDirPathFilter(), pathFilter);
    }

    @Test
    public void withOptionalDescendDirPathFilter_PassWithNull() {
        TraversePathIterable next =
            classUnderTest.withOptionalDescendDirPathFilter((PathFilter) null);
        assertNotSame(next, classUnderTest);
        assertNull(next.getOptionalDescendDirPathFilter());
    }

    // TODO: Last
}
