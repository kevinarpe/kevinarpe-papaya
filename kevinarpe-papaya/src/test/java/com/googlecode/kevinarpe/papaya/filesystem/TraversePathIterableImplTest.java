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
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;

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
            new TraversePathIterableImpl(expected.withRootDirPath(), expected.withDepthPolicy());
        classUnderTestWithoutDefaults =
            classUnderTestWithDefaults
                .withDescendDirPathFilter(pathFilter)
                .withDescendDirPathComparator(fileComparator)
                .withIteratePathFilter(pathFilter2)
                .withIteratePathComparator(fileComparator2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        BaseTraversePathIterTest.assertAttrSame(
            classUnderTestWithDefaults,
            expected.withRootDirPath(),
            expected.withDepthPolicy(),
            TraversePathIterableUtils.DEFAULT_EXCEPTION_POLICY,
            TraversePathIterableUtils.DEFAULT_DESCEND_DIR_PATH_FILTER,
            TraversePathIterableUtils.DEFAULT_DESCEND_DIR_PATH_COMPARATOR,
            TraversePathIterableUtils.DEFAULT_ITERATE_PATH_FILTER,
            TraversePathIterableUtils.DEFAULT_ITERATE_PATH_COMPARATOR);
    }

    @Test
    public void ctor_Pass2() {
        new BaseTraversePathIterTest.ctor_Pass_Helper() {

            @Override
            protected AbstractTraversePathIterSettings newInstance(
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
        assertEquals(next.withRootDirPath(), newPath);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            newPath,
            classUnderTestWithDefaults.withDepthPolicy(),
            classUnderTestWithDefaults.withExceptionPolicy(),
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            classUnderTestWithDefaults.withDescendDirPathComparator(),
            classUnderTestWithDefaults.withIteratePathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator());
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
            (classUnderTestWithDefaults.withDepthPolicy() == TraversePathDepthPolicy.DEPTH_FIRST
                ? TraversePathDepthPolicy.DEPTH_LAST : TraversePathDepthPolicy.DEPTH_FIRST);
        TraversePathIterable next = classUnderTestWithDefaults.withDepthPolicy(depthPolicy);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withDepthPolicy(), depthPolicy);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            depthPolicy,
            classUnderTestWithDefaults.withExceptionPolicy(),
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            classUnderTestWithDefaults.withDescendDirPathComparator(),
            classUnderTestWithDefaults.withIteratePathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator());
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
            (classUnderTestWithDefaults.withExceptionPolicy() == TraversePathExceptionPolicy.THROW
                ? TraversePathExceptionPolicy.IGNORE : TraversePathExceptionPolicy.THROW);
        TraversePathIterable next =
            classUnderTestWithDefaults.withExceptionPolicy(exceptionPolicy);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withExceptionPolicy(), exceptionPolicy);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            classUnderTestWithDefaults.withDepthPolicy(),
            exceptionPolicy,
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            classUnderTestWithDefaults.withDescendDirPathComparator(),
            classUnderTestWithDefaults.withIteratePathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withExceptionPolicy_FailWithNull() {
        classUnderTestWithDefaults.withExceptionPolicy((TraversePathExceptionPolicy) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withDescendDirPathFilter()
    //

    @Test
    public void withDescendDirPathFilter_Pass() {
        TraversePathIterable next = classUnderTestWithDefaults.withDescendDirPathFilter(pathFilter);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withDescendDirPathFilter(), pathFilter);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            classUnderTestWithDefaults.withDepthPolicy(),
            classUnderTestWithDefaults.withExceptionPolicy(),
            pathFilter,
            classUnderTestWithDefaults.withDescendDirPathComparator(),
            classUnderTestWithDefaults.withIteratePathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDescendDirPathFilter_FailWithNull() {
        classUnderTestWithDefaults.withDescendDirPathFilter((PathFilter) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withDescendDirPathComparator()
    //

    @Test
    public void withDescendDirPathComparator_Pass() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withDescendDirPathComparator(fileComparator);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withDescendDirPathComparator(), fileComparator);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            classUnderTestWithDefaults.withDepthPolicy(),
            classUnderTestWithDefaults.withExceptionPolicy(),
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            fileComparator,
            classUnderTestWithDefaults.withIteratePathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withDescendDirPathComparator_FailWithNull() {
        classUnderTestWithDefaults.withDescendDirPathComparator((Comparator<File>) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withIteratePathFilter()
    //

    @Test
    public void withIteratePathFilter_Pass() {
        TraversePathIterable next = classUnderTestWithDefaults.withIteratePathFilter(pathFilter);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withIteratePathFilter(), pathFilter);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            classUnderTestWithDefaults.withDepthPolicy(),
            classUnderTestWithDefaults.withExceptionPolicy(),
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            classUnderTestWithDefaults.withIteratePathComparator(),
            pathFilter,
            classUnderTestWithDefaults.withIteratePathComparator());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withIteratePathFilter_FailWithNull() {
        classUnderTestWithDefaults.withIteratePathFilter((PathFilter) null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterableImpl.withIteratePathComparator()
    //

    @Test
    public void withIteratePathComparator_Pass() {
        TraversePathIterable next =
            classUnderTestWithDefaults.withIteratePathComparator(fileComparator);
        assertNotNull(next);
        assertNotSame(next, classUnderTestWithDefaults);
        assertEquals(next.withIteratePathComparator(), fileComparator);
        BaseTraversePathIterTest.assertAttrSame(
            next,
            classUnderTestWithDefaults.withRootDirPath(),
            classUnderTestWithDefaults.withDepthPolicy(),
            classUnderTestWithDefaults.withExceptionPolicy(),
            classUnderTestWithDefaults.withDescendDirPathFilter(),
            classUnderTestWithDefaults.withDescendDirPathComparator(),
            classUnderTestWithDefaults.withIteratePathFilter(),
            fileComparator);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withIteratePathComparator_FailWithNull() {
        classUnderTestWithDefaults.withIteratePathComparator((Comparator<File>) null);
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
                classUnderTestWithoutDefaults.withRootDirPath(classUnderTestWithoutDefaults.withRootDirPath()))
            .addEqualityGroup(
                classUnderTestWithoutDefaults.withRootDirPath(newDirPath),
                classUnderTestWithoutDefaults.withRootDirPath(newDirPath))
            .testEquals();
    }
}
