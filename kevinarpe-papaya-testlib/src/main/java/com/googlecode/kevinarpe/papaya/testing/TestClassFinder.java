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
import com.googlecode.kevinarpe.papaya.filesystem.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactoryImpl;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLevelLogger;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLevelLoggerImpl;
import com.googlecode.kevinarpe.papaya.logging.SLF4JLogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final boolean _excludeAbstractClassesFlag;
    private final SLF4JLogLevel _logLevel;
    private final TraversePathIterableFactory _traversePathIterableFactory;
    private final SourceFileToClassHelper _sourceFileToClassHelper;
    private final SLF4JLevelLogger _levelLogger;

    public TestClassFinder() {
        this(
            TraversePathIterableFactoryImpl.INSTANCE,
            SourceFileToClassHelperImpl.INSTANCE);
    }

    // Package-private for testing
    TestClassFinder(
            TraversePathIterableFactory traversePathIterableFactory,
            SourceFileToClassHelper sourceFileToClassHelper) {
        this(
            DEFAULT_ROOT_DIR_PATH,
            DEFAULT_INCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_BY_ABSOLUTE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            DEFAULT_LOG_LEVEL,
            ObjectArgs.checkNotNull(traversePathIterableFactory, "traversePathIterableFactory"),
            ObjectArgs.checkNotNull(sourceFileToClassHelper, "sourceFileToClassHelper"),
            _newSLF4JLevelLogger(DEFAULT_LOG_LEVEL));
    }

    private static SLF4JLevelLogger _newSLF4JLevelLogger(SLF4JLogLevel logLevel) {
        Logger logger = _getLogger(logLevel);
        // TODO: Tough to test.  Use a hidden factory?
        SLF4JLevelLogger levelLogger = new SLF4JLevelLoggerImpl(logger, logLevel);
        return levelLogger;
    }

    private static Logger _getLogger(SLF4JLogLevel logLevel) {
        ObjectArgs.checkNotNull(logLevel, "logLevel");

        if (logLevel == SLF4JLogLevel.OFF) {
            return null;
        }
        // TODO: Tough to test.  Use a hidden factory?
        Logger logger = LoggerFactory.getLogger(TestClassFinder.class);
        return logger;
    }

    private TestClassFinder(
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            boolean excludeAbstractClassesFlag,
            SLF4JLogLevel logLevel,
            TraversePathIterableFactory traversePathIterableFactory,
            SourceFileToClassHelper sourceFileToClassHelper,
            SLF4JLevelLogger levelLogger) {
        _rootDirPath = rootDirPath;
        _includeByAbsolutePathPatternList = includeByFilePathPatternList;
        _excludeByAbsolutePathPatternList = excludeByFilePathPatternList;
        _excludeAbstractClassesFlag = excludeAbstractClassesFlag;
        _logLevel = logLevel;
        _traversePathIterableFactory = traversePathIterableFactory;
        _sourceFileToClassHelper = sourceFileToClassHelper;
        this._levelLogger = levelLogger;
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
            _logLevel,
            _traversePathIterableFactory,
            _sourceFileToClassHelper,
            _levelLogger);
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
            _logLevel,
            _traversePathIterableFactory,
            _sourceFileToClassHelper,
            _levelLogger);
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
            _logLevel,
            _traversePathIterableFactory,
            _sourceFileToClassHelper,
            _levelLogger);
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
            _logLevel,
            _traversePathIterableFactory,
            _sourceFileToClassHelper,
            _levelLogger);
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
        TestClassFinder x = new TestClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            ObjectArgs.checkNotNull(logLevel, "logLevel"),
            _traversePathIterableFactory,
            _sourceFileToClassHelper,
            _newSLF4JLevelLogger(logLevel));
        return x;
    }

    public SLF4JLogLevel withLogLevel() {
        return _logLevel;
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
        TraversePathIterable pathIter = _newTraversePathIterable();
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

    private TraversePathIterable _newTraversePathIterable() {
        TraversePathIterable pathIter = _traversePathIterableFactory.newInstance(
            _rootDirPath, TraversePathDepthPolicy.DEPTH_LAST);
        // TODO: Replace with hidden factory
        pathIter = pathIter.withOptionalIteratePathFilter(new PathFilter() {
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
        });
        return pathIter;
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

    private boolean _isMatch(String pathname, List<Pattern> patternList) {
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
