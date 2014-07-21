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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.Lists2;
import com.googlecode.kevinarpe.papaya.exception.ClassNotFoundRuntimeException;
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterableUtils;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterableFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class TestClassFinderImpl
implements TestClassFinder {

    private final File _rootDirPath;
    private final List<Pattern> _includeByAbsolutePathPatternList;
    private final List<Pattern> _excludeByAbsolutePathPatternList;
    private final Logger _logger;

    private final TraversePathIterableFactory _traversePathIterableFactory;
    private final IteratePathFilterFactory _iteratePathFilterFactory;
    private final SourceFileToClassHelper _sourceFileToClassHelper;

    // Replace with static method, e.g, withRootDirPath(File)
    public TestClassFinderImpl() {
        this(
            TraversePathIterableUtils.INSTANCE.getFactory(),
            IteratePathFilterFactoryImpl.INSTANCE,
            SourceFileToClassHelperImpl.INSTANCE,
            LoggerFactory.getILoggerFactory());
    }

    public TestClassFinderImpl(
            TraversePathIterableFactory traversePathIterableFactory,
            IteratePathFilterFactory iteratePathFilterFactory,
            SourceFileToClassHelper sourceFileToClassHelper,
            ILoggerFactory loggerFactory) {
        this(
            TestClassFinderUtils.DEFAULT_ROOT_DIR_PATH,
            TestClassFinderUtils.DEFAULT_INCLUDE_PATTERN_LIST,
            TestClassFinderUtils.DEFAULT_EXCLUDE_PATTERN_LIST,
            _newLogger(loggerFactory),
            ObjectArgs.checkNotNull(traversePathIterableFactory, "traversePathIterableFactory"),
            ObjectArgs.checkNotNull(iteratePathFilterFactory, "iteratePathFilterFactory"),
            ObjectArgs.checkNotNull(sourceFileToClassHelper, "sourceFileToClassHelper"));
    }

    private static Logger _newLogger(ILoggerFactory loggerFactory) {
        ObjectArgs.checkNotNull(loggerFactory, "loggerFactory");

        Logger x = loggerFactory.getLogger(TestClassFinderImpl.class.getName());
        return x;
    }

    private TestClassFinderImpl(
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            Logger logger,
            TraversePathIterableFactory traversePathIterableFactory,
            IteratePathFilterFactory iteratePathFilterFactory,
            SourceFileToClassHelper sourceFileToClassHelper) {
        _rootDirPath = rootDirPath;
        _includeByAbsolutePathPatternList = includeByFilePathPatternList;
        _excludeByAbsolutePathPatternList = excludeByFilePathPatternList;
        _logger = logger;
        _traversePathIterableFactory = traversePathIterableFactory;
        _iteratePathFilterFactory = iteratePathFilterFactory;
        _sourceFileToClassHelper = sourceFileToClassHelper;
    }

    @Override
    public TestClassFinderImpl withRootDirPath(File rootDirPath) {
        ObjectArgs.checkNotNull(rootDirPath, "rootDirPath");

        TestClassFinderImpl x = new TestClassFinderImpl(
            rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            _logger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    @Override
    public File withRootDirPath() {
        return _rootDirPath;
    }

    @Override
    public TestClassFinderImpl withIncludePatterns(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toArrayList(filePathPattern, moreFilePathPatternsArr);
        TestClassFinderImpl x = withIncludePatterns(list);
        return x;
    }

    @Override
    public TestClassFinderImpl withIncludePatterns(List<Pattern> filePathPatternList) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(filePathPatternList, "filePathPatternList");
        TestClassFinderImpl x = new TestClassFinderImpl(
            _rootDirPath,
            ImmutableList.copyOf(filePathPatternList),
            _excludeByAbsolutePathPatternList,
            _logger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    @Override
    public List<Pattern> withIncludePatterns() {
        return _includeByAbsolutePathPatternList;
    }

    @Override
    public TestClassFinderImpl withExcludePatterns(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toArrayList(filePathPattern, moreFilePathPatternsArr);
        TestClassFinderImpl x = withExcludePatterns(list);
        return x;
    }

    @Override
    public TestClassFinderImpl withExcludePatterns(List<Pattern> filePathPatternList) {
        CollectionArgs.checkElementsNotNull(filePathPatternList, "filePathPatternList");

        TestClassFinderImpl x = new TestClassFinderImpl(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            ImmutableList.copyOf(filePathPatternList),
            _logger,
            _traversePathIterableFactory,
            _iteratePathFilterFactory,
            _sourceFileToClassHelper);
        return x;
    }

    @Override
    public List<Pattern> withExcludePatterns() {
        return _excludeByAbsolutePathPatternList;
    }

    private List<Pattern> _toArrayList(Pattern filePathPattern, Pattern[] moreFilePathPatternsArr) {
        ObjectArgs.checkNotNull(filePathPattern, "filePathPattern");
        ArrayArgs.checkElementsNotNull(moreFilePathPatternsArr, "moreFilePathPatternsArr");

        List<Pattern> list =
            Lists2.newUnmodifiableListFromOneOrMoreValues(filePathPattern, moreFilePathPatternsArr);
        return list;
    }

    /**
     *
     * @return
     *
     * @throws PathRuntimeException
     * @throws ClassNotFoundRuntimeException
     */
    @Override
    public List<Class<?>> findAsList() {
        TraversePathIterable pathIter = _newTraversePathIterable();
        List<Class<?>> classList = new ArrayList<Class<?>>();
        _logRootDirPath();
        for (File path : pathIter) {
            Class<?> clazz = _getClass(path);
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                classList.add(clazz);
            }
        }
        return classList;
    }

    @Override
    public Class<?>[] findAsArray() {
        List<Class<?>> classList = findAsList();
        Class<?>[] classArr = new Class<?>[classList.size()];
        classArr = classList.toArray(classArr);
        return classArr;
    }

    private TraversePathIterable _newTraversePathIterable() {
        PathFilter iteratePathFilter = _iteratePathFilterFactory.newInstance(this);
        TraversePathIterable pathIterable = _traversePathIterableFactory.newInstance(
            _rootDirPath, TraversePathDepthPolicy.DEPTH_LAST);
        pathIterable = pathIterable.withIteratePathFilter(iteratePathFilter);
        return pathIterable;
    }

    private void _logRootDirPath() {
        if (_rootDirPath.isAbsolute()) {
            _logger.debug("Root dir path: '{}'", _rootDirPath.getPath());
        }
        else {
            _logger.debug("Root dir path: '{}' -> '{}'",
                _rootDirPath.getPath(), _rootDirPath.getAbsolutePath());
        }
    }

    interface IteratePathFilterFactory {

        PathFilter newInstance(TestClassFinderImpl parent);
    }

    final static class IteratePathFilterFactoryImpl
    implements IteratePathFilterFactory {

        public static final IteratePathFilterFactoryImpl INSTANCE =
            new IteratePathFilterFactoryImpl();

        @Override
        public PathFilter newInstance(TestClassFinderImpl parent) {
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
            _logIsMatch(absPathname, include, exclude);
            boolean result = include && !exclude;
            return result;
        }

        private void _logIsMatch(String absPathname, boolean include, boolean exclude) {
            if (include && exclude) {
                _logger.debug(" include &&  exclude: '{}'", absPathname);
            }
            if (!include && exclude) {
                _logger.debug("!include &&  exclude: '{}'", absPathname);
            }
            if (include && !exclude) {
                _logger.debug(" include && !exclude: '{}'", absPathname);
            }
            if (!include && !exclude) {
                _logger.debug("!include && !exclude: '{}'", absPathname);
            }
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
