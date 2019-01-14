package com.googlecode.kevinarpe.papaya.testing;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.collect.Collections2;
import com.googlecode.kevinarpe.papaya.exception.ClassNotFoundRuntimeException;
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterator;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEventAttribute;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLogger;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerFactory;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JMockLoggerUtils;
import com.googlecode.kevinarpe.papaya.testing.logging.AnyLoggingEventAttributeValuePredicate;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TestClassFinderImplTest {

    private TraversePathIterableFactory mockTraversePathIterableFactory;
    private TraversePathIterable mockTraversePathIterable;
    private TraversePathIterator mockTraversePathIterator;
    private TestClassFinderImpl.IteratePathFilterFactory mockIteratePathFilterFactory;
    private PathFilter mockPathFilter;
    private SourceFileToClassHelper mockSourceFileToClassHelper;
    private SLF4JMockLoggerFactory mockLoggerFactory;
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
        mockLoggerFactory = SLF4JMockLoggerUtils.INSTANCE.newFactoryInstance();
        classUnderTestWithoutMocks = new TestClassFinderImpl();
        classUnderTestWithMocks =
            new TestClassFinderImpl(
                mockTraversePathIterableFactory,
                mockIteratePathFilterFactory,
                mockSourceFileToClassHelper,
                mockLoggerFactory);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    public static void assertAttrEquals(
            TestClassFinder testClassFinder,
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList) {
        assertEquals(
            testClassFinder.withRootDirPath(),
            rootDirPath);
        assertEquals(
            testClassFinder.withIncludePatterns(),
            includeByFilePathPatternList);
        assertEquals(
            testClassFinder.withExcludePatterns(),
            excludeByFilePathPatternList);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinderImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);
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
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(oldPath);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            oldPath,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);
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
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(newPattern, newPattern2);
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);
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
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern),
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withIncludePatterns(
                Arrays.asList(newPattern, newPattern2));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST);
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
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            Arrays.asList(newPattern));

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.withExcludePatterns(
                Arrays.asList(newPattern, newPattern2));
        assertAttrEquals(
            classUnderTestWithoutMocks,
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            Arrays.asList(newPattern, newPattern2));
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
        _assertLogger(SLF4JLogLevel.DEBUG, 1);
    }

    private void _assertLogger(SLF4JLogLevel logLevel, int count) {
        SLF4JMockLogger logger =
            mockLoggerFactory.getLogger(TestClassFinderImpl.class.getName());
        assertEquals(
            Collections2.filter(
                logger.getLoggingEventList(),
                new AnyLoggingEventAttributeValuePredicate<SLF4JLoggingEvent>(
                    SLF4JLoggingEventAttribute.LEVEL, logLevel)).size(),
            count
        );
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
        _assertLogger(SLF4JLogLevel.DEBUG, 1);
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
        classUnderTestWithMocks =
            classUnderTestWithMocks.withIncludePatterns(includePatternList);
        if (!excludePatternList.isEmpty()) {
            classUnderTestWithMocks =
                classUnderTestWithMocks.withExcludePatterns(excludePatternList);
        }
        TestClassFinderImpl.IteratePathFilter pathFilter =
            classUnderTestWithMocks.new IteratePathFilter();
        boolean actualResult = pathFilter.accept(mockFile, 1);
        assertEquals(actualResult, expectedResult);
        _assertLogger(SLF4JLogLevel.DEBUG, 1);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void TestClassFinderPathFilter_accept_FailWithNull() {
        classUnderTestWithoutMocks.new IteratePathFilter().accept((File) null, 1);
    }
}
