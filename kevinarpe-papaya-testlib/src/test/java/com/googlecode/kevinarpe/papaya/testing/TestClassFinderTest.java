package com.googlecode.kevinarpe.papaya.testing;

import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.mockito.Mockito.mock;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class TestClassFinderTest {

    private TraversePathIterableFactory mockTraversePathIterableFactory;
    private SourceFileToClassHelper mockSourceFileToClassHelper;
    private TestClassFinder classUnderTestWithoutMocks;
    private TestClassFinder classUnderTestWithMocks;

    @BeforeMethod
    public void beforeEachTest() {
        mockTraversePathIterableFactory = mock(TraversePathIterableFactory.class);
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
            boolean excludeAbstractClassesFlag) {
        Assert.assertEquals(
            classUnderTestWithoutMocks.withRootDirPath(),
            rootDirPath);
        Assert.assertEquals(
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(),
            includeByFilePathPatternList);
        Assert.assertEquals(
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(),
            excludeByFilePathPatternList);
        Assert.assertEquals(
            classUnderTestWithoutMocks.excludeAbstractClasses(),
            excludeAbstractClassesFlag);
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
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);
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
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.withRootDirPath(oldPath);
        _assertEquals(
            classUnderTestWithoutMocks,
            oldPath,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);
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
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);
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
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.includeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);
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
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);

        classUnderTestWithoutMocks =
            classUnderTestWithoutMocks.excludeByAbsolutePathPattern(newPattern, newPattern2);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            Arrays.asList(newPattern, newPattern2),
            TestClassFinder.DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG);
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
            b2);

        classUnderTestWithoutMocks = classUnderTestWithoutMocks.excludeAbstractClasses(b);
        _assertEquals(
            classUnderTestWithoutMocks,
            TestClassFinder.DEFAULT_ROOT_DIR_PATH,
            TestClassFinder.DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            TestClassFinder.DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            b);
    }

    // TODO: Test the logging methods
    // TODO: Write a class to capture SLF4J Logger events for assertion -- similar to Log4JTestBase

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // TestClassFinder.findAsList()
    //

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
    // TestClassFinder.findAsList()
    //

    @Test
    public void findAsArray_Pass() {
        classUnderTestWithoutMocks = _withKnownIncludePattern(classUnderTestWithoutMocks);
        Class<?>[] classArray = classUnderTestWithoutMocks.findAsArray();
        Assert.assertTrue(0 != classArray.length);

        List<Class<?>> classList = classUnderTestWithoutMocks.findAsList();
        Assert.assertEquals(Arrays.asList(classArray), classList);
    }
}
