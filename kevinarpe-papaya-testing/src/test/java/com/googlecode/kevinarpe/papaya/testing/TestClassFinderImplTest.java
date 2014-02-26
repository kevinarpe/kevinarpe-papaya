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

import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.ClassNotFoundRuntimeException;
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterator;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TestClassFinderImplTest {

    private static class MockILoggingFactory
    implements ILoggerFactory {

        @Override
        public Logger getLogger(String name) {
            return null;
        }
    }

    private static class MockLogger
    implements Logger {

        // TODO: All configuration of isEnabled

        private final String _name;

        public MockLogger(String name) {
            _name = StringArgs.checkNotEmptyOrWhitespace(name, "name");
        }

        @Override
        public String getName() {
            return _name;
        }

        @Override
        public boolean isTraceEnabled() {
            return false;
        }

        @Override
        public void trace(String msg) {

        }

        @Override
        public void trace(String format, Object arg) {

        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {

        }

        @Override
        public void trace(String format, Object... arguments) {

        }

        @Override
        public void trace(String msg, Throwable t) {

        }

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return false;
        }

        @Override
        public void trace(Marker marker, String msg) {

        }

        @Override
        public void trace(Marker marker, String format, Object arg) {

        }

        @Override
        public void trace(Marker marker, String format, Object arg1, Object arg2) {

        }

        @Override
        public void trace(Marker marker, String format, Object... argArray) {

        }

        @Override
        public void trace(Marker marker, String msg, Throwable t) {

        }

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public void debug(String msg) {

        }

        @Override
        public void debug(String format, Object arg) {

        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {

        }

        @Override
        public void debug(String format, Object... arguments) {

        }

        @Override
        public void debug(String msg, Throwable t) {

        }

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return false;
        }

        @Override
        public void debug(Marker marker, String msg) {

        }

        @Override
        public void debug(Marker marker, String format, Object arg) {

        }

        @Override
        public void debug(Marker marker, String format, Object arg1, Object arg2) {

        }

        @Override
        public void debug(Marker marker, String format, Object... arguments) {

        }

        @Override
        public void debug(Marker marker, String msg, Throwable t) {

        }

        @Override
        public boolean isInfoEnabled() {
            return false;
        }

        @Override
        public void info(String msg) {

        }

        @Override
        public void info(String format, Object arg) {

        }

        @Override
        public void info(String format, Object arg1, Object arg2) {

        }

        @Override
        public void info(String format, Object... arguments) {

        }

        @Override
        public void info(String msg, Throwable t) {

        }

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return false;
        }

        @Override
        public void info(Marker marker, String msg) {

        }

        @Override
        public void info(Marker marker, String format, Object arg) {

        }

        @Override
        public void info(Marker marker, String format, Object arg1, Object arg2) {

        }

        @Override
        public void info(Marker marker, String format, Object... arguments) {

        }

        @Override
        public void info(Marker marker, String msg, Throwable t) {

        }

        @Override
        public boolean isWarnEnabled() {
            return false;
        }

        @Override
        public void warn(String msg) {

        }

        @Override
        public void warn(String format, Object arg) {

        }

        @Override
        public void warn(String format, Object... arguments) {

        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {

        }

        @Override
        public void warn(String msg, Throwable t) {

        }

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return false;
        }

        @Override
        public void warn(Marker marker, String msg) {

        }

        @Override
        public void warn(Marker marker, String format, Object arg) {

        }

        @Override
        public void warn(Marker marker, String format, Object arg1, Object arg2) {

        }

        @Override
        public void warn(Marker marker, String format, Object... arguments) {

        }

        @Override
        public void warn(Marker marker, String msg, Throwable t) {

        }

        @Override
        public boolean isErrorEnabled() {
            return false;
        }

        @Override
        public void error(String msg) {

        }

        @Override
        public void error(String format, Object arg) {

        }

        @Override
        public void error(String format, Object arg1, Object arg2) {

        }

        @Override
        public void error(String format, Object... arguments) {

        }

        @Override
        public void error(String msg, Throwable t) {

        }

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return false;
        }

        @Override
        public void error(Marker marker, String msg) {

        }

        @Override
        public void error(Marker marker, String format, Object arg) {

        }

        @Override
        public void error(Marker marker, String format, Object arg1, Object arg2) {

        }

        @Override
        public void error(Marker marker, String format, Object... arguments) {

        }

        @Override
        public void error(Marker marker, String msg, Throwable t) {

        }
    }

    private TraversePathIterableFactory mockTraversePathIterableFactory;
    private TraversePathIterable mockTraversePathIterable;
    private TraversePathIterator mockTraversePathIterator;
    private TestClassFinderImpl.IteratePathFilterFactory mockIteratePathFilterFactory;
    private PathFilter mockPathFilter;
    private SourceFileToClassHelper mockSourceFileToClassHelper;
    private TestClassFinderImpl classUnderTestWithoutMocks;
    private TestClassFinderImpl classUnderTestWithMocks;

    @BeforeMethod
    public void beforeEachTest() {
        mockTraversePathIterableFactory = mock(TraversePathIterableFactory.class);
        mockTraversePathIterable = mock(TraversePathIterable.class);
        mockTraversePathIterator = mock(TraversePathIterator.class);
        mockIteratePathFilterFactory = mock(TestClassFinderImpl.IteratePathFilterFactory.class);
        mockPathFilter = mock(PathFilter.class);
        mockSourceFileToClassHelper = mock(SourceFileToClassHelper.class);
        classUnderTestWithoutMocks = new TestClassFinderImpl();
        classUnderTestWithMocks =
            new TestClassFinderImpl(
                mockTraversePathIterableFactory,
                mockIteratePathFilterFactory,
                mockSourceFileToClassHelper);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    public static void assertAttrEquals(
            TestClassFinder testClassFinder,
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            SLF4JLogLevel logLevel) {
        assertEquals(
            testClassFinder.withRootDirPath(),
            rootDirPath);
        assertEquals(
            testClassFinder.withIncludePatterns(),
            includeByFilePathPatternList);
        assertEquals(
            testClassFinder.withExcludePatterns(),
            excludeByFilePathPatternList);
        assertEquals(
            testClassFinder.withLogLevel(),
            logLevel);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withRootDirPath()
    //

    @Test
    public void withRootDirPath_Pass() {
        File oldPath = classUnderTestWithoutMocks.withRootDirPath();
        File newPath = new File(UUID.randomUUID().toString());

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(newPath);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            newPath,
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(oldPath);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            oldPath,
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withRootDirPath_FailWithNull() {
        classUnderTestWithoutMocks.withRootDirPath((File) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withIncludePatterns(Pattern, Pattern...)
    //

    @Test
    public void includePatterns_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(newPattern);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(newPattern, newPattern2);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includePatterns_FailWithNull() {
        classUnderTestWithoutMocks.withIncludePatterns((Pattern) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includePatterns_FailWithNull2() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.withIncludePatterns(
            newPattern, (Pattern) null, newPattern);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includePatterns_FailWithNull3() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.withIncludePatterns(newPattern, (Pattern[]) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withIncludePatterns(List<Pattern>)
    //

    @Test
    public void includePatterns2_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(Arrays.asList(newPattern));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(
                Arrays.asList(newPattern, newPattern2));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_LOG_LEVEL);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includePatterns2_FailWithNull() {
        classUnderTestWithoutMocks.withIncludePatterns((List<Pattern>) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void includePatterns2_FailWithNull2() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.withIncludePatterns(Arrays.asList(newPattern, null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void includePatterns2_FailWithEmptyList() {
        classUnderTestWithoutMocks.withIncludePatterns(Arrays.<Pattern>asList());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withExcludePatterns(Pattern, Pattern...)
    //

    @Test
    public void excludePatterns_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withExcludePatterns(newPattern);
        assertEquals(
            classUnderTestWithoutMocks.withExcludePatterns(),
            Arrays.asList(newPattern));

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withExcludePatterns(newPattern, newPattern2);
        assertEquals(
            classUnderTestWithoutMocks.withExcludePatterns(),
            Arrays.asList(newPattern, newPattern2));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludePatterns_FailWithNull() {
        classUnderTestWithoutMocks.withExcludePatterns((Pattern) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludePatterns_FailWithNull2() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        classUnderTestWithoutMocks.withExcludePatterns(newPattern, (Pattern) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withExcludePatterns(List<Pattern>)
    //

    @Test
    public void excludePatterns2_Pass() {
        Pattern newPattern = Pattern.compile(UUID.randomUUID().toString());
        Pattern newPattern2 = Pattern.compile(UUID.randomUUID().toString());

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withExcludePatterns(Arrays.asList(newPattern));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            Arrays.asList(newPattern),
            TestClassFinders.DEFAULT_LOG_LEVEL);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withExcludePatterns(
                Arrays.asList(newPattern, newPattern2));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinders.DEFAULT_ROOT_DIR_PATH,
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinders.DEFAULT_LOG_LEVEL);
    }

    @Test
    public void excludePatterns2_PassWithEmptyList() {
        classUnderTestWithoutMocks.withExcludePatterns(Arrays.<Pattern>asList());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void excludePatterns2_FailWithNull() {
        classUnderTestWithoutMocks.withExcludePatterns((List<Pattern>) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.withLogLevel(SLF4JLogLevel)
    //

    @Test
    public void withLogLevel_Pass() {
        SLF4JLogLevel oldLogLevel = classUnderTestWithoutMocks.withLogLevel();
        SLF4JLogLevel newLogLevel =
            (SLF4JLogLevel.OFF == oldLogLevel) ? SLF4JLogLevel.WARN : SLF4JLogLevel.OFF;

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withLogLevel(newLogLevel);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            classUnderTestWithoutMocks.withRootDirPath(),
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            newLogLevel);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withLogLevel(oldLogLevel);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            classUnderTestWithoutMocks.withRootDirPath(),
            TestClassFinders.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinders.DEFAULT_EXCLUDE_PATTERN_LIST,
            oldLogLevel);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void withLogLevel_FailWithNull() {
        classUnderTestWithoutMocks.withLogLevel((SLF4JLogLevel) null);
    }

    // TODO: Write a class to capture SLF4J Logger events for assertion -- similar to Log4JTestBase
    // TODO: Ref: http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.findAsList()
    //

    private static class Class1 { }
    private static abstract class AbstractClass2 { }
    private static class Class3 { }

    @SuppressWarnings("unchecked")
    public void _setFindAsMocks()
    throws ClassNotFoundException {
        when(
            mockTraversePathIterableFactory.newInstance(
                any(File.class), any(TraversePathDepthPolicy.class)))
            .thenReturn(mockTraversePathIterable);
        when(mockIteratePathFilterFactory.newInstance(any(TestClassFinderImpl.class)))
            .thenReturn(mockPathFilter);
        when(mockTraversePathIterable.withOptionalIteratePathFilter(mockPathFilter))
            .thenReturn(mockTraversePathIterable);
        when(mockTraversePathIterable.iterator()).thenReturn(mockTraversePathIterator);
        when(mockTraversePathIterator.hasNext()).thenReturn(true, true, true, false);
        when(mockTraversePathIterator.next()).thenReturn(new File("dummy"));
        when(mockSourceFileToClassHelper.getClass(any(File.class)))
            .thenReturn((Class) Class1.class, (Class) AbstractClass2.class, (Class) Class3.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void findAsList_Pass()
    throws ClassNotFoundException {
        _setFindAsMocks();
        classUnderTestWithMocks =
            classUnderTestWithMocks.withIncludePatterns(Pattern.compile("abc"));
        List<Class<?>> classList = classUnderTestWithMocks.findAsList();
        assertEquals(classList, Arrays.asList(Class1.class, Class3.class));
    }

    @Test(expectedExceptions = PathRuntimeException.class)
    public void findAsList_FailWithPathRuntimeException() {
        classUnderTestWithoutMocks
            .withRootDirPath(new File(UUID.randomUUID().toString()))
            .withIncludePatterns(Pattern.compile("a"))
            .findAsList();
    }

    @Test(expectedExceptions = ClassNotFoundRuntimeException.class)
    public void findAsList_FailWithClassNotFoundRuntimeException()
    throws ClassNotFoundException {
        _setFindAsMocks();
        classUnderTestWithMocks =
            classUnderTestWithMocks.withIncludePatterns(Pattern.compile("abc"));
        ClassNotFoundException cause = new ClassNotFoundException();
        when(mockSourceFileToClassHelper.getClass(any(File.class))).thenThrow(cause);
        try {
            classUnderTestWithMocks.findAsList();
        }
        catch (ClassNotFoundRuntimeException e) {
            assertSame(e.getCause(), cause);
            throw e;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.findAsArray()
    //

    @SuppressWarnings("unchecked")
    @Test
    public void findAsArray_Pass()
    throws ClassNotFoundException {
        _setFindAsMocks();
        classUnderTestWithMocks =
            classUnderTestWithMocks.withIncludePatterns(Pattern.compile("abc"));
        Class<?>[] classArr = classUnderTestWithMocks.findAsArray();
        assertEquals(Arrays.asList(classArr), Arrays.asList(Class1.class, Class3.class));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.IteratePathFilter.accept(File, int)
    //

    @Test
    public void TestClassFinderPathFilter_accept_FailWithDirPath() {
        TestClassFinderImpl.IteratePathFilter pathFilter =
            classUnderTestWithoutMocks.new IteratePathFilter();
        File mockFile = Mockito.mock(File.class);
        when(mockFile.isFile()).thenReturn(false);
        Assert.assertFalse(pathFilter.accept(mockFile, 1));
    }

    @DataProvider
    private static Object[][] _TestClassFinderPathFilter_accept_Pass_Data() {
        Pattern matchingPattern = Pattern.compile("^a$");
        Pattern notMatchingPattern = Pattern.compile("^b$");
        @SuppressWarnings("unchecked")
        List<Pattern> emptyList = Arrays.asList();
        return new Object[][] {
            {
                "a",
                Arrays.asList(matchingPattern),
                emptyList,
                true,
            },
            {
                "a",
                Arrays.asList(matchingPattern, matchingPattern),
                emptyList,
                true,
            },
            {
                "a",
                Arrays.asList(notMatchingPattern, matchingPattern),
                emptyList,
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
            {
                "a",
                Arrays.asList(notMatchingPattern),
                Arrays.asList(matchingPattern),
                false,
            },
            {
                "a",
                Arrays.asList(notMatchingPattern),
                Arrays.asList(notMatchingPattern),
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
        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(includePatternList);
        if (!excludePatternList.isEmpty()) {
            classUnderTestWithoutMocks =
                classUnderTestWithoutMocks.withExcludePatterns(excludePatternList);
        }
        TestClassFinderImpl.IteratePathFilter pathFilter =
            classUnderTestWithoutMocks.new IteratePathFilter();
        boolean actualResult = pathFilter.accept(mockFile, 1);
        assertEquals(actualResult, expectedResult);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void TestClassFinderPathFilter_accept_FailWithNull() {
        classUnderTestWithoutMocks.new IteratePathFilter().accept((File) null, 1);
    }
}
