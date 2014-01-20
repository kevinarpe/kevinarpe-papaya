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

import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class BaseTraversePathIterTest {

//    public static interface Factory<TTraversePathIter extends BaseTraversePathIter> {
//
//        TTraversePathIter newInstance(
//            File dirPath,
//            TraversePathDepthPolicy depthPolicy,
//            TraversePathExceptionPolicy exceptionPolicy,
//            PathFilter optDescendDirPathFilter,
//            Comparator<File> optDescendDirPathComparator,
//            PathFilter optIteratePathFilter,
//            Comparator<File> optIteratePathComparator);
//    }

    public static BaseTraversePathIter newInstance() {
        File dirPath = new File("dummy");
        TraversePathDepthPolicy depthPolicy = TraversePathDepthPolicy.DEPTH_FIRST;
        TraversePathExceptionPolicy exceptionPolicy = TraversePathExceptionPolicy.IGNORE;
        PathFilter optDescendDirPathFilter = new PathFilter() {
            @Override
            public boolean accept(File path, int depth) {
                return false;
            }
        };
        Comparator<File> optDescendDirPathComparator = new FileNameLexicographicalComparator();
        PathFilter optIteratePathFilter = new PathFilter() {
            @Override
            public boolean accept(File path, int depth) {
                return false;
            }
        };
        Comparator<File> optIterateFileComparator = new FileNameLexicographicalComparator();

        BaseTraversePathIter x = new BaseTraversePathIter(
            dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
            optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator);
        return x;
    }

    public static void assertAttrSame(BaseTraversePathIter actual, BaseTraversePathIter expected) {
        assertSame(actual.getDirPath(), expected.getDirPath());
        assertSame(actual.getDepthPolicy(), expected.getDepthPolicy());
        assertSame(actual.getExceptionPolicy(), expected.getExceptionPolicy());
        assertSame(
            actual.getOptionalDescendDirPathFilter(), expected.getOptionalDescendDirPathFilter());
        assertSame(
            actual.getOptionalDescendDirPathComparator(),
            expected.getOptionalDescendDirPathComparator());
        assertSame(actual.getOptionalIteratePathFilter(), expected.getOptionalIteratePathFilter());
        assertSame(
            actual.getOptionalIteratePathComparator(), expected.getOptionalIteratePathComparator());
    }

    public static void assertAttrSame(
            BaseTraversePathIter actual,
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        assertSame(actual.getDirPath(), dirPath);
        assertSame(actual.getDepthPolicy(), depthPolicy);
        assertSame(actual.getExceptionPolicy(), exceptionPolicy);
        assertSame(actual.getOptionalDescendDirPathFilter(), optDescendDirPathFilter);
        assertSame(actual.getOptionalDescendDirPathComparator(), optDescendDirPathComparator);
        assertSame(actual.getOptionalIteratePathFilter(), optIteratePathFilter);
        assertSame(actual.getOptionalIteratePathComparator(), optIteratePathComparator);
    }

//    public static <TTraversePathIter extends BaseTraversePathIter>
//    void ctor_Pass_Helper(Factory<TTraversePathIter> factory, BaseTraversePathIter expected) {
//        TTraversePathIter actual =
//            factory.newInstance(
//                expected.getDirPath(),
//                expected.getDepthPolicy(),
//                expected.getExceptionPolicy(),
//                expected.getOptionalDescendDirPathFilter(),
//                expected.getOptionalDescendDirPathComparator(),
//                expected.getOptionalIteratePathFilter(),
//                expected.getOptionalIteratePathComparator());
//        assertAttrSame(actual, expected);
//    }

    public static abstract class ctor_Pass_Helper {

        public ctor_Pass_Helper() {
            BaseTraversePathIter expected = BaseTraversePathIterTest.newInstance();

            BaseTraversePathIter actual =
                newInstance(
                    expected.getDirPath(),
                    expected.getDepthPolicy(),
                    expected.getExceptionPolicy(),
                    expected.getOptionalDescendDirPathFilter(),
                    expected.getOptionalDescendDirPathComparator(),
                    expected.getOptionalIteratePathFilter(),
                    expected.getOptionalIteratePathComparator());

            assertSame(actual.getDirPath(), expected.getDirPath());
            assertSame(actual.getDepthPolicy(), expected.getDepthPolicy());
            assertSame(actual.getExceptionPolicy(), expected.getExceptionPolicy());
            assertSame(
                actual.getOptionalDescendDirPathFilter(),
                expected.getOptionalDescendDirPathFilter());
            assertSame(
                actual.getOptionalDescendDirPathComparator(),
                expected.getOptionalDescendDirPathComparator());
            assertSame(
                actual.getOptionalIteratePathFilter(),
                expected.getOptionalIteratePathFilter());
            assertSame(
                actual.getOptionalIteratePathComparator(),
                expected.getOptionalIteratePathComparator());
        }

        protected abstract BaseTraversePathIter newInstance(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIteratePathComparator);
    }

    private BaseTraversePathIter classUnderTest;
    private final File dirPath = new File("dummy");
    private final TraversePathDepthPolicy depthPolicy = TraversePathDepthPolicy.DEPTH_FIRST;
    private final TraversePathExceptionPolicy exceptionPolicy = TraversePathExceptionPolicy.IGNORE;
    private final PathFilter optDescendDirPathFilter = new PathFilter() {
        @Override
        public boolean accept(File path, int depth) {
            return false;
        }
    };
    private final Comparator<File> optDescendDirPathComparator = new FileNameLexicographicalComparator();
    private final PathFilter optIteratePathFilter = new PathFilter() {
        @Override
        public boolean accept(File path, int depth) {
            return false;
        }
    };
    private final Comparator<File> optIterateFileComparator = new FileNameLexicographicalComparator();

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new BaseTraversePathIter(
            dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
            optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // BaseTraversePathIter.ctor()
    //

    @Test
    public void ctor_Pass() {
        assertSame(classUnderTest.getDirPath(), dirPath);
        assertSame(classUnderTest.getDepthPolicy(), depthPolicy);
        assertSame(classUnderTest.getExceptionPolicy(), exceptionPolicy);
        assertSame(classUnderTest.getOptionalDescendDirPathFilter(), optDescendDirPathFilter);
        assertSame(
            classUnderTest.getOptionalDescendDirPathComparator(), optDescendDirPathComparator);
        assertSame(classUnderTest.getOptionalIteratePathFilter(), optIteratePathFilter);
        assertSame(classUnderTest.getOptionalIteratePathComparator(), optIterateFileComparator);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // BaseTraversePathIter.toString()
    //

    @Test
    public void toString_Pass() {
        String x = classUnderTest.toString();
        assertNotNull(x);
        assertTrue(!StringUtils.isEmptyOrWhitespace(x));
    }
}
