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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class BaseTraversePathIterTest {

    public static TraversePathIterSettings newInstance() {
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

        TraversePathIterSettingsImpl x = new TraversePathIterSettingsImpl(
            dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
            optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator);
        return x;
    }

    public static void assertAttrSame(TraversePathIterSettings actual, TraversePathIterSettings expected) {
        Assert.assertSame(actual.getRootDirPath(), expected.getRootDirPath());
        Assert.assertSame(actual.getDepthPolicy(), expected.getDepthPolicy());
        Assert.assertSame(actual.getExceptionPolicy(), expected.getExceptionPolicy());
        Assert.assertSame(
            actual.getOptionalDescendDirPathFilter(), expected.getOptionalDescendDirPathFilter());
        Assert.assertSame(
            actual.getOptionalDescendDirPathComparator(),
            expected.getOptionalDescendDirPathComparator());
        Assert.assertSame(actual.getOptionalIteratePathFilter(), expected.getOptionalIteratePathFilter());
        Assert.assertSame(
            actual.getOptionalIteratePathComparator(), expected.getOptionalIteratePathComparator());
    }

    public static void assertAttrSame(
            TraversePathIterSettings actual,
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        Assert.assertSame(actual.getRootDirPath(), dirPath);
        Assert.assertSame(actual.getDepthPolicy(), depthPolicy);
        Assert.assertSame(actual.getExceptionPolicy(), exceptionPolicy);
        Assert.assertSame(actual.getOptionalDescendDirPathFilter(), optDescendDirPathFilter);
        Assert.assertSame(actual.getOptionalDescendDirPathComparator(), optDescendDirPathComparator);
        Assert.assertSame(actual.getOptionalIteratePathFilter(), optIteratePathFilter);
        Assert.assertSame(actual.getOptionalIteratePathComparator(), optIteratePathComparator);
    }

    public static abstract class ctor_Pass_Helper {

        public ctor_Pass_Helper() {
            TraversePathIterSettings expected = BaseTraversePathIterTest.newInstance();

            TraversePathIterSettings actual =
                newInstance(
                    expected.getRootDirPath(),
                    expected.getDepthPolicy(),
                    expected.getExceptionPolicy(),
                    expected.getOptionalDescendDirPathFilter(),
                    expected.getOptionalDescendDirPathComparator(),
                    expected.getOptionalIteratePathFilter(),
                    expected.getOptionalIteratePathComparator());

            Assert.assertSame(actual.getRootDirPath(), expected.getRootDirPath());
            Assert.assertSame(actual.getDepthPolicy(), expected.getDepthPolicy());
            Assert.assertSame(actual.getExceptionPolicy(), expected.getExceptionPolicy());
            Assert.assertSame(
                actual.getOptionalDescendDirPathFilter(),
                expected.getOptionalDescendDirPathFilter());
            Assert.assertSame(
                actual.getOptionalDescendDirPathComparator(),
                expected.getOptionalDescendDirPathComparator());
            Assert.assertSame(
                actual.getOptionalIteratePathFilter(),
                expected.getOptionalIteratePathFilter());
            Assert.assertSame(
                actual.getOptionalIteratePathComparator(),
                expected.getOptionalIteratePathComparator());
        }

        protected abstract TraversePathIterSettings newInstance(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> optDescendDirPathComparator,
                PathFilter optIteratePathFilter,
                Comparator<File> optIteratePathComparator);
    }

    private TraversePathIterSettingsImpl classUnderTest;
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
        classUnderTest = new TraversePathIterSettingsImpl(
            dirPath, depthPolicy, exceptionPolicy, optDescendDirPathFilter,
            optDescendDirPathComparator, optIteratePathFilter, optIterateFileComparator);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterSettingsImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        Assert.assertSame(classUnderTest.getRootDirPath(), dirPath);
        Assert.assertSame(classUnderTest.getDepthPolicy(), depthPolicy);
        Assert.assertSame(classUnderTest.getExceptionPolicy(), exceptionPolicy);
        Assert.assertSame(classUnderTest.getOptionalDescendDirPathFilter(), optDescendDirPathFilter);
        Assert.assertSame(
            classUnderTest.getOptionalDescendDirPathComparator(), optDescendDirPathComparator);
        Assert.assertSame(classUnderTest.getOptionalIteratePathFilter(), optIteratePathFilter);
        Assert.assertSame(classUnderTest.getOptionalIteratePathComparator(), optIterateFileComparator);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TraversePathIterSettingsImpl.toString()
    //

    @Test
    public void toString_Pass() {
        String x = classUnderTest.toString();
        Assert.assertNotNull(x);
        Assert.assertTrue(!StringUtils.isEmptyOrWhitespace(x));
    }
}
