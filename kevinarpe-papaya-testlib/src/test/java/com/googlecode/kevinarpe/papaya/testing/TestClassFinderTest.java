package com.googlecode.kevinarpe.papaya.testing;

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

import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterator;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLogLevel;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TestClassFinderTest {

    private TraversePathIterableFactory mockTraversePathIterableFactory;
    private TraversePathIterable mockTraversePathIterable;
    private TraversePathIterator mockTraversePathIterator;
    private SourceFileToClassHelper mockSourceFileToClassHelper;
    private TestClassFinder classUnderTestWithoutMocks;
    private TestClassFinder classUnderTestWithMocks;

    @BeforeMethod
    public void beforeEachTest() {
        mockTraversePathIterableFactory = mock(TraversePathIterableFactory.class);
        mockTraversePathIterable = mock(TraversePathIterable.class);
        mockTraversePathIterator = mock(TraversePathIterator.class);
        mockSourceFileToClassHelper = mock(SourceFileToClassHelper.class);
        classUnderTestWithoutMocks = new TestClassFinder();
        classUnderTestWithMocks =
            new TestClassFinder(mockTraversePathIterableFactory, mockSourceFileToClassHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private TestClassFinder _withKnownIncludePattern(TestClassFinder testClassFinder) {
        testClassFinder =
            testClassFinder.includeByAbsolutePathPattern(
                Pattern.compile("^.*/kevinarpe-papaya-testlib/src/test/.*Test\\.java$"));
        return testClassFinder;
    }

    private void _assertEquals(
            TestClassFinder testClassFinder,
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            boolean excludeAbstractClassesFlag,
            SLF4JLogLevel logLevel) {
        Assert.assertEquals(
            testClassFinder.withRootDirPath(),
            rootDirPath);
        Assert.assertEquals(
            testClassFinder.includeByAbsolutePathPattern(),
            includeByFilePathPatternList);
        Assert.assertEquals(
            testClassFinder.excludeByAbsolutePathPattern(),
            excludeByFilePathPatternList);
        Assert.assertEquals(
            testClassFinder.excludeAbstractClasses(),
            excludeAbstractClassesFlag);
        Assert.assertEquals(
            testClassFinder.withLogLevel(),
            logLevel);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.ctor()
    //

    @Test
    public void ctor_Pass() {
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.withRootDirPath()
    //

    @Test
    public void withRootDirPath_Pass() {
        File oldPath = classUnderTestWithoutMocks.withRootDirPath();
        File newPath = new File(UUID.randomUUID().toString());

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(newPath);
        _assertEquals(
            classUnderTestWithoutMocks,
            newPath,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(oldPath);
        _assertEquals(
            classUnderTestWithoutMocks,
            oldPath,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withRootDirPath_FailWithNull() {
        classUnderTestWithoutMocks.withRootDirPath((File) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.includeByAbsolutePathPattern(Pattern, Pattern...)
    //

    @Test
    public void includeByAbsolutePathPattern_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includeByAbsolutePathPattern_FailWithNull() {
        classUnderTestWithoutMocks.includeByAbsolutePathPattern((Pattern) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includeByAbsolutePathPattern_FailWithNull2() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern, (Pattern) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.includeByAbsolutePathPattern(List<Pattern>)
    //

    @Test
    public void includeByAbsolutePathPattern2_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includeByAbsolutePathPattern2_FailWithNull() {
        classUnderTestWithoutMocks.includeByAbsolutePathPattern((List<Pattern>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.excludeByAbsolutePathPattern(Pattern, Pattern...)
    //

    @Test
    public void excludeByAbsolutePathPattern_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern);
        Assert.assertEquals(
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(),
            Arrays.asList(newPattern));

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern, newPattern2);
        Assert.assertEquals(
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(),
            Arrays.asList(newPattern, newPattern2));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludeByAbsolutePathPattern_FailWithNull() {
        classUnderTestWithoutMocks.excludeByAbsolutePathPattern((Pattern) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludeByAbsolutePathPattern_FailWithNull2() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern, (Pattern) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.excludeByAbsolutePathPattern(List<Pattern>)
    //

    @Test
    public void excludeByAbsolutePathPattern2_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            Arrays.asList(newPattern),
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludeByAbsolutePathPattern2_FailWithNull() {
        classUnderTestWithoutMocks.excludeByAbsolutePathPattern((List<Pattern>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.excludeAbstractClasses(boolean)
    //

    @Test
    public void excludeAbstractClasses_Pass() {
        boolean b = classUnderTestWithoutMocks.excludeAbstractClasses();
        boolean b2 = !b;

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.excludeAbstractClasses(b2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            b2,
            TestClassFinder.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.excludeAbstractClasses(b);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            b,
            TestClassFinder.DEFAULT_LOG_LEVEL);
    }

    // TODO: Test the logging methods (withLogLevel)
    // TODO: Write a class to capture SLF4J Logger events for assertion -- similar to Log4JTestBase
    // TODO: Ref: http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.findAsList()
    //

    @Test(expectedExceptions = IllegalStateException.class)
    public void findAsList_FailWithNoIncludePatterns() {
        classUnderTestWithoutMocks.findAsList();
    }

    private static class Class1 { }
    private static abstract class AbstractClass2 { }
    private static class Class3 { }

    @Test
    public void findAsList_Pass2()
    throws ClassNotFoundException {
        when(
            mockTraversePathIterableFactory.newInstance(
                any(File.class), any(TraversePathDepthPolicy.class)))
            .thenReturn(mockTraversePathIterable);
        when(mockTraversePathIterable.iterator()).thenReturn(mockTraversePathIterator);
        when(mockTraversePathIterator.hasNext()).thenReturn(true, true, true, false);
        when(mockTraversePathIterator.next()).thenReturn(new File("dummy"));
        when(mockSourceFileToClassHelper.getClass(any(File.class)))
            .thenReturn((Class) Class1.class, (Class) AbstractClass2.class, (Class) Class3.class);
        // TODO: LAST
        // Need to replace 'new TestClassFinderPathFilter()' with injectable PathFilter
        // ... otherwise, we are re-testing TestClassFinderPathFilter here.  Hard to mock!
        List<Class<?>> classList = classUnderTestWithMocks.includeByAbsolutePathPattern(Pattern) .findAsList();
    }

    @Test
    public void findAsList_Pass() {
        classUnderTestWithoutMocks = _withKnownIncludePattern(classUnderTestWithoutMocks);
        List<Class<?>> classList = classUnderTestWithoutMocks.findAsList();
        Assert.assertTrue(!classList.isEmpty());

        Class<?>[] classArray = classUnderTestWithoutMocks.findAsArray();
        Assert.assertEquals(Arrays.asList(classArray), classList);
    }

    @Test(expectedExceptions = PathRuntimeException.class)
    public void findAsList_FailWithPathRuntimeException() {
        classUnderTestWithoutMocks = _withKnownIncludePattern(classUnderTestWithoutMocks);
        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withRootDirPath(new File(UUID.randomUUID().toString()));
        classUnderTestWithoutMocks.findAsList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.findAsArray()
    //

    @Test
    public void findAsArray_Pass() {
        classUnderTestWithoutMocks = _withKnownIncludePattern(classUnderTestWithoutMocks);
        Class<?>[] classArray = classUnderTestWithoutMocks.findAsArray();
        Assert.assertTrue(0 != classArray.length);

        List<Class<?>> classList = classUnderTestWithoutMocks.findAsList();
        Assert.assertEquals(Arrays.asList(classArray), classList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.TestClassFinderPathFilter.accept(File, int)
    //

    @Test
    public void TestClassFinderPathFilter_accept_FailWithDirPath() {
        TestClassFinder.TestClassFinderPathFilter pathFilter =
            classUnderTestWithoutMocks.new TestClassFinderPathFilter();
        File mockFile = Mockito.mock(File.class);
        when(mockFile.isFile()).thenReturn(false);
        Assert.assertFalse(pathFilter.accept(mockFile, 1));
    }

    @DataProvider
    private static Object[][] _TestClassFinderPathFilter_accept_Pass_Data() {
        Pattern matchingPattern = Pattern.compile("^a$");
        Pattern notMatchingPattern = Pattern.compile("^b$");
        List<Pattern> emptyList = Arrays.asList();
        return new Object[][] {
            {
                "a",
                Arrays.asList(matchingPattern),
                Arrays.asList(),
                true,
            },
            {
                "a",
                Arrays.asList(matchingPattern, matchingPattern),
                Arrays.asList(),
                true,
            },
            {
                "a",
                Arrays.asList(notMatchingPattern, matchingPattern),
                Arrays.asList(),
                true,
            },
            {
                "a",
                Arrays.asList(matchingPattern),
                Arrays.asList(notMatchingPattern),
                true,
            },
            {
                "a",
                Arrays.asList(matchingPattern, matchingPattern),
                Arrays.asList(notMatchingPattern),
                true,
            },
            {
                "a",
                Arrays.asList(notMatchingPattern, matchingPattern),
                Arrays.asList(notMatchingPattern),
                true,
            },
            {
                "a",
                Arrays.asList(matchingPattern),
                Arrays.asList(matchingPattern),
                false,
            },
            {
                "a",
                Arrays.asList(matchingPattern, matchingPattern),
                Arrays.asList(matchingPattern),
                false,
            },
            {
                "a",
                Arrays.asList(notMatchingPattern, matchingPattern),
                Arrays.asList(matchingPattern),
                false,
            },
        };
    }

    @Test(dataProvider = "_TestClassFinderPathFilter_accept_Pass_Data")
    public void TestClassFinderPathFilter_accept_Pass(
            String absPathname,
            List<Pattern> includePatternList,
            List<Pattern> excludePatternList,
            boolean expectedResult) {
        File mockFile = Mockito.mock(File.class);
        when(mockFile.isFile()).thenReturn(true);
        when(mockFile.getAbsolutePath()).thenReturn(absPathname);
        TestClassFinder.TestClassFinderPathFilter pathFilter =
            classUnderTestWithoutMocks.new TestClassFinderPathFilter();
        boolean actualResult = pathFilter.accept(mockFile, 1);
    }
}
