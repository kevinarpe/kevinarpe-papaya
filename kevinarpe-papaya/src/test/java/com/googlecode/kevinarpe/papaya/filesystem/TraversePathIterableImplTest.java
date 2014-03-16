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

import com.google.common.testing.EqualsTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TraversePathIterableImplTest {

    private final File newDirPath = new File("newDirPath");

    private final PathFilter pathFilter = new PathFilter() {
        @Override
        public boolean accept(File path, int depth) {
            return false;
        }
    };

    private final PathFilter pathFilter2 = new PathFilter() {
        @Override
        public boolean accept(File path, int depth) {
            return false;
        }
    };

    private final Comparator<File> fileComparator = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            return 0;
        }
    };

    private final Comparator<File> fileComparator2 = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            return 0;
        }
    };

    private TraversePathIterSettings expected;
    private TraversePathIterable classUnderTestWithDefaults;
    private TraversePathIterable classUnderTestWithoutDefaults;

    @BeforeMethod
    public void beforeEachTestMethod() {
        expected = BaseTraversePathIterTest.newInstance();
        classUnderTestWithDefaults =
            new TraversePathIterableImpl(expected.getRootDirPath(), expected.getDepthPolicy());
        classUnderTestWithoutDefaults =
            classUnderTestWithDefaults
                .withOptionalDescendDirPathFilter(pathFilter)
                .withOptionalDescendDirPathComparator(fileComparator)
                .withOptionalIteratePathFilter(pathFilter2)
                .withOptionalIteratePathComparator(fileComparator2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        BaseTraversePathIterTest.assertAttrSame(
            classUnderTestWithDefaults,
            expected.getRootDirPath(),
            expected.getDepthPolicy(),
            TraversePathIterableImpl.DEFAULT_EXCEPTION_POLICY,
            (PathFilter) null,
            (Comparator<File>) null,
            (PathFilter) null,
            (Comparator<File>) null);
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
                return new TraversePathIterableImpl(
                    dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
                    optDescendDirPathComparator, optIteratePathFilter, optIteratePathComparator);
            }
        };
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullDirPath() {
        new TraversePathIterableImpl((File) null, TraversePathDepthPolicy.DEPTH_LAST);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNullDepthPolicy() {
        new TraversePathIterableImpl(new File("dummy"), (TraversePathDepthPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withRootDirPath()
    //

    @Test
    public void withDirPath_Pass() {
        final File newPath = new File("newPath");
        TraversePathIterable next = classUnderTestWithDefaults.withRootDirPath(newPath);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getRootDirPath(), newPath);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            newPath,
            classUnderTestWithDefaults.getDepthPolicy(),
            classUnderTestWithDefaults.getExceptionPolicy(),
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            classUnderTestWithDefaults.getOptionalDescendDirPathComparator(),
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDirPath_FailWithNull() {
        classUnderTestWithDefaults.withRootDirPath((File) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withDepthPolicy()
    //

    @Test
    public void withDepthPolicy_Pass() {
        TraversePathDepthPolicy depthPolicy =
            (classUnderTestWithDefaults.getDepthPolicy() == TraversePathDepthPolicy.DEPTH_FIRST
                ? TraversePathDepthPolicy.DEPTH_LAST : TraversePathDepthPolicy.DEPTH_FIRST);
        TraversePathIterable next = classUnderTestWithDefaults.withDepthPolicy(depthPolicy);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getDepthPolicy(), depthPolicy);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            depthPolicy,
            classUnderTestWithDefaults.getExceptionPolicy(),
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            classUnderTestWithDefaults.getOptionalDescendDirPathComparator(),
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDepthPolicy_FailWithNull() {
        classUnderTestWithDefaults.withDepthPolicy((TraversePathDepthPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withExceptionPolicy()
    //

    @Test
    public void withExceptionPolicy_Pass() {
        TraversePathExceptionPolicy exceptionPolicy =
            (classUnderTestWithDefaults.getExceptionPolicy() == TraversePathExceptionPolicy.THROW
                ? TraversePathExceptionPolicy.IGNORE : TraversePathExceptionPolicy.THROW);
        TraversePathIterable next =
            classUnderTestWithDefaults.withExceptionPolicy(exceptionPolicy);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getExceptionPolicy(), exceptionPolicy);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            classUnderTestWithDefaults.getDepthPolicy(),
            exceptionPolicy,
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            classUnderTestWithDefaults.getOptionalDescendDirPathComparator(),
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withExceptionPolicy_FailWithNull() {
        classUnderTestWithDefaults.withExceptionPolicy((TraversePathExceptionPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withOptionalDescendDirPathFilter()
    //

    @Test
    public void withOptionalDescendDirPathFilter_Pass() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalDescendDirPathFilter(pathFilter);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getOptionalDescendDirPathFilter(), pathFilter);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            classUnderTestWithDefaults.getDepthPolicy(),
            classUnderTestWithDefaults.getExceptionPolicy(),
            pathFilter,
            classUnderTestWithDefaults.getOptionalDescendDirPathComparator(),
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test
    public void withOptionalDescendDirPathFilter_PassWithNull() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalDescendDirPathFilter((PathFilter) null);
        assertNotSame(next, classUnderTestWithDefaults);
        assertNull(next.getOptionalDescendDirPathFilter());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withOptionalDescendDirPathComparator()
    //

    @Test
    public void withOptionalDescendDirPathComparator_Pass() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalDescendDirPathComparator(fileComparator);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getOptionalDescendDirPathComparator(), fileComparator);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            classUnderTestWithDefaults.getDepthPolicy(),
            classUnderTestWithDefaults.getExceptionPolicy(),
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            fileComparator,
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test
    public void withOptionalDescendDirPathComparator_PassWithNull() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalDescendDirPathComparator((Comparator<File>) null);
        assertNotSame(next, classUnderTestWithDefaults);
        assertNull(next.getOptionalDescendDirPathComparator());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withOptionalIteratePathFilter()
    //

    @Test
    public void withOptionalIteratePathFilter_Pass() {
        TraversePathIterable next = classUnderTestWithDefaults.withOptionalIteratePathFilter(pathFilter);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getOptionalIteratePathFilter(), pathFilter);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            classUnderTestWithDefaults.getDepthPolicy(),
            classUnderTestWithDefaults.getExceptionPolicy(),
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            classUnderTestWithDefaults.getOptionalIteratePathComparator(),
            pathFilter,
            classUnderTestWithDefaults.getOptionalIteratePathComparator());
    }

    @Test
    public void withOptionalIteratePathFilter_PassWithNull() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalIteratePathFilter((PathFilter) null);
        assertNotSame(next, classUnderTestWithDefaults);
        assertNull(next.getOptionalIteratePathFilter());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withOptionalIteratePathComparator()
    //

    @Test
    public void withOptionalIteratePathComparator_Pass() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalIteratePathComparator(fileComparator);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.getOptionalIteratePathComparator(), fileComparator);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.getRootDirPath(),
            classUnderTestWithDefaults.getDepthPolicy(),
            classUnderTestWithDefaults.getExceptionPolicy(),
            classUnderTestWithDefaults.getOptionalDescendDirPathFilter(),
            classUnderTestWithDefaults.getOptionalDescendDirPathComparator(),
            classUnderTestWithDefaults.getOptionalIteratePathFilter(),
            fileComparator);
    }

    @Test
    public void withOptionalIteratePathComparator_PassWithNull() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withOptionalIteratePathComparator((Comparator<File>) null);
        assertNotSame(next, classUnderTestWithDefaults);
        assertNull(next.getOptionalIteratePathComparator());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.iterator()
    //

    @Test
    public void iterator_Pass() {
        for (TraversePathDepthPolicy depthPolicy : TraversePathDepthPolicy.values()) {
            TraversePathIterable next = classUnderTestWithoutDefaults.withDepthPolicy(depthPolicy);
            TraversePathIterator iter = next.iterator();
            assertNotNull(iter);
            BaseTraversePathIterTest.assertAttrSame(iter, next);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.hashCode()/equals()
    //

    // TODO: Need more tests to cover all branches
    @Test
    public void hashCodeAndEquals_Pass() {
        new EqualsTester()
            .addEqualityGroup(1, 1)  // Something that is not this class (TraversePathIterableImpl)
            .addEqualityGroup(
                classUnderTestWithoutDefaults,
                classUnderTestWithoutDefaults.withRootDirPath(classUnderTestWithoutDefaults.getRootDirPath()))
            .addEqualityGroup(
                classUnderTestWithoutDefaults.withRootDirPath(newDirPath),
                classUnderTestWithoutDefaults.withRootDirPath(newDirPath))
            .testEquals();
    }
}