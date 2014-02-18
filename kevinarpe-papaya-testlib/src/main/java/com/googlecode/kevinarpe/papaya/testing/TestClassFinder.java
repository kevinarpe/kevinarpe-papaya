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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.ClassNotFoundRuntimeException;
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.ITraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactoryImpl;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLevelLogger;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLevelLoggers;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLogLevel;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Implement interface only for easy of mocking by users later?  A bit like JavaEE?  What about other classes, like the PathIter family?
public final class TestClassFinder {

    public static final File DEFAULT_ROOT_DIR_PATH = new File(System.getProperty("user.dir"));
    public static final List<Pattern> DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST =
        ImmutableList.of();
    public static final List<Pattern> DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST =
        ImmutableList.of();
    public static final boolean DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG = true;
    public static final SLF4JLogLevel DEFAULT_LOG_LEVEL = SLF4JLogLevel.OFF;

    private final File _rootDirPath;
    private final List<Pattern> _includeByAbsolutePathPatternList;
    private final List<Pattern> _excludeByAbsolutePathPatternList;
    // TODO: Necessary?
    private final boolean _excludeAbstractClassesFlag;
    private final SLF4JLevelLogger _levelLogger;

    private final TraversePathIterableFactory _traversePathIterableFactory;
    private final IteratePathFilterFactory _iteratePathFilterFactory;
    private final SourceFileToClassHelper _sourceFileToClassHelper;

    // Replace with static method, e.g, withRootDirPath(File)
    public TestClassFinder() {
        this(
            TraversePathIterableFactoryImpl.INSTANCE,
            IteratePathFilterFactoryImpl.INSTANCE,
            SourceFileToClassHelperImpl.INSTANCE);
    }

    // Package-private for testing
    TestClassFinder(
            TraversePathIterableFactory traversePathIterableFactory,
            IteratePathFilterFactory iteratePathFilterFactory,
            SourceFileToClassHelper sourceFileToClassHelper) {
        this(
            DEFAULT_ROOT_DIR_PATH,
            DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            _newSLF4JLevelLogger(DEFAULT_LOG_LEVEL),
            ObjectArgs.checkNotNull(traversePathIterableFactory, "traversePathIterableFactory"),
            ObjectArgs.checkNotNull(iteratePathFilterFactory, "iteratePathFilterFactory"),
            ObjectArgs.checkNotNull(sourceFileToClassHelper, "sourceFileToClassHelper"));
    }

    private static SLF4JLevelLogger _newSLF4JLevelLogger(SLF4JLogLevel logLevel) {
        SLF4JLevelLogger x= SLF4JLevelLoggers.newInstance(logLevel, TestClassFinder.class);
        return x;
    }

    private TestClassFinder(
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            boolean excludeAbstractClassesFlag,
            SLF4JLevelLogger levelLogger,
            TraversePathIterableFactory traversePathIterableFactory,
            IteratePathFilterFactory iteratePathFilterFactory,
            SourceFileToClassHelper sourceFileToClassHelper) {
        _rootDirPath = rootDirPath;
        _includeByAbsolutePathPatternList = includeByFilePathPatternList;
        _excludeByAbsolutePathPatternList = excludeByFilePathPatternList;
        _excludeAbstractClassesFlag = excludeAbstractClassesFlag;
        _levelLogger = levelLogger;
        _traversePathIterableFactory = traversePathIterableFactory;
        _iteratePathFilterFactory = iteratePathFilterFactory;
        _sourceFileToClassHelper = sourceFileToClassHelper;
    }

    public File withRootDirPath() {
        return _rootDirPath;
    }

    public TestClassFinder withRootDirPath(File rootDirPath) {
        ObjectArgs.checkNotNull(rootDirPath, "rootDirPath");

        TestClassFinder x = new TestClassFinder(
            rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            _levelLogger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    public List<Pattern> includeByAbsolutePathPattern() {
        return _includeByAbsolutePathPatternList;
    }

    public TestClassFinder includeByAbsolutePathPattern(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toArrayList(filePathPattern, moreFilePathPatternsArr);
        TestClassFinder x = includeByAbsolutePathPattern(list);
        return x;
    }

    public TestClassFinder includeByAbsolutePathPattern(List<Pattern> filePathPatternList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(filePathPatternList, "filePathPatternList");
        TestClassFinder x = new TestClassFinder(
            _rootDirPath,
            ImmutableList.copyOf(filePathPatternList),
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            _levelLogger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    public List<Pattern> excludeByAbsolutePathPattern() {
        return _excludeByAbsolutePathPatternList;
    }

    public TestClassFinder excludeByAbsolutePathPattern(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toArrayList(filePathPattern, moreFilePathPatternsArr);
        TestClassFinder x = excludeByAbsolutePathPattern(list);
        return x;
    }

    public TestClassFinder excludeByAbsolutePathPattern(List<Pattern> filePathPatternList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(filePathPatternList, "filePathPatternList");
        TestClassFinder x = new TestClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            ImmutableList.copyOf(filePathPatternList),
            _excludeAbstractClassesFlag,
            _levelLogger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    public boolean excludeAbstractClasses() {
        return _excludeAbstractClassesFlag;
    }

    public TestClassFinder excludeAbstractClasses(boolean flag) {
        TestClassFinder x = new TestClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            flag,
            _levelLogger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    private List<Pattern> _toArrayList(Pattern filePathPattern, Pattern[] moreFilePathPatternsArr) {
        ObjectArgs.checkNotNull(filePathPattern, "filePathPattern");
        ArrayArgs.checkElementsNotNull(moreFilePathPatternsArr, "moreFilePathPatternsArr");

        List<Pattern> list = new ArrayList<Pattern>(1 + moreFilePathPatternsArr.length);
        list.add(filePathPattern);
        if (moreFilePathPatternsArr.length > 0) {
            list.addAll(Arrays.asList(moreFilePathPatternsArr));
        }
        return list;
    }

    public TestClassFinder withLogLevel(SLF4JLogLevel logLevel) {
        ObjectArgs.checkNotNull(logLevel, "logLevel");

        SLF4JLevelLogger levelLogger = _newSLF4JLevelLogger(logLevel);
        TestClassFinder x = new TestClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            levelLogger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    public SLF4JLogLevel withLogLevel() {
        SLF4JLogLevel x = _levelLogger.getLogLevel();
        return x;
    }

    /**
     *
     * @return
     *
     * @throws PathRuntimeException
     * @throws ClassNotFoundRuntimeException
     */
    public List<Class<?>> findAsList() {
        _assertCanFind();
        ITraversePathIterable pathIter = _newTraversePathIterable();
        List<Class<?>> classList = new ArrayList<Class<?>>();
        _logRootDirPath();
        for (File path : pathIter) {
            Class<?> clazz = _getClass(path);
            if (!_excludeAbstractClassesFlag || !Modifier.isAbstract(clazz.getModifiers())) {
                classList.add(clazz);
            }
        }
        return classList;
    }

    public Class<?>[] findAsArray() {
        List<Class<?>> classList = findAsList();
        Class<?>[] classArr = new Class<?>[classList.size()];
        classArr = classList.toArray(classArr);
        return classArr;
    }

    private void _assertCanFind() {
        if (_includeByAbsolutePathPatternList.isEmpty()) {
            throw new IllegalStateException("Must call method includeByFilePathPatterns() first");
        }
    }

    private ITraversePathIterable _newTraversePathIterable() {
        PathFilter iteratePathFilter = _iteratePathFilterFactory.newInstance(this);
        ITraversePathIterable pathIterable = _traversePathIterableFactory.newInstance(
            _rootDirPath, TraversePathDepthPolicy.DEPTH_LAST);
        pathIterable = pathIterable.withOptionalIteratePathFilter(iteratePathFilter);
        return pathIterable;
    }

    private void _logRootDirPath() {
        if (_rootDirPath.isAbsolute()) {
            _levelLogger.log("Root dir path: '{}'", _rootDirPath.getPath());
        }
        else {
            _levelLogger.log("Root dir path: '{}' -> '{}'",
                _rootDirPath.getPath(), _rootDirPath.getAbsolutePath());
        }
    }

    interface IteratePathFilterFactory {

        PathFilter newInstance(TestClassFinder parent);
    }

    final static class IteratePathFilterFactoryImpl
    implements IteratePathFilterFactory {

        public static final IteratePathFilterFactoryImpl INSTANCE =
            new IteratePathFilterFactoryImpl();

        @Override
        public PathFilter newInstance(TestClassFinder parent) {
            IteratePathFilter x = parent.new IteratePathFilter();
            return x;
        }
    }

    final class IteratePathFilter
    implements PathFilter {

        @Override
        public boolean accept(File path, int depth) {
            if (!path.isFile()) {
                return false;
            }
            String absPathname = path.getAbsolutePath();
            boolean include = _isMatch(absPathname, _includeByAbsolutePathPatternList);
            boolean exclude = _isMatch(absPathname, _excludeByAbsolutePathPatternList);
            _logIsMatch(_levelLogger, absPathname, include, exclude);
            boolean result = include && !exclude;
            return result;
        }
    }

    private static void _logIsMatch(
            SLF4JLevelLogger levelLogger, String absPathname, boolean include, boolean exclude) {
        if (include && exclude) {
            levelLogger.log("'{}': include && exclude", absPathname);
        }
        if (!include && exclude) {
            levelLogger.log("'{}': !include && exclude", absPathname);
        }
        if (include && !exclude) {
            levelLogger.log("'{}': include && !exclude", absPathname);
        }
        if (!include && !exclude) {
            levelLogger.log("'{}': !include && !exclude", absPathname);
        }
    }

    private static boolean _isMatch(String pathname, List<Pattern> patternList) {
        for (Pattern pattern : patternList) {
            if (pattern.matcher(pathname).find()) {
                return true;
            }
        }
        return false;
    }

    private Class<?> _getClass(File path) {
        try {
            Class<?> clazz = _sourceFileToClassHelper.getClass(path);
            return clazz;
        }
        catch (ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }
}
