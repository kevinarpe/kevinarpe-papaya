package com.googlecode.kevinarpe.papaya.junit;

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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathDepthPolicy;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterable;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactory;
import com.googlecode.kevinarpe.papaya.filesystem.factory.TraversePathIterableFactoryImpl;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ClassFinder {

    public static final File DEFAULT_ROOT_DIR_PATH = new File(System.getProperty("user.dir"));
    public static final List<Pattern> DEFAULT_INCLUDE_BY_FILE_PATH_PATTERN_LIST =
        ImmutableList.of();
    public static final List<Pattern> DEFAULT_EXCLUDE_BY_FILE_PATH_PATTERN_LIST =
        ImmutableList.of();
    public static final boolean DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG = true;

    private final File _rootDirPath;
    private final List<Pattern> _includeByAbsolutePathPatternList;
    private final List<Pattern> _excludeByAbsolutePathPatternList;
    private final boolean _excludeAbstractClassesFlag;
    private final TraversePathIterableFactory _traversePathIterableFactory;

    public ClassFinder() {
        this(
            DEFAULT_ROOT_DIR_PATH,
            DEFAULT_INCLUDE_BY_FILE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_BY_FILE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            TraversePathIterableFactoryImpl.INSTANCE);
    }

    ClassFinder(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathIterableFactory traversePathIterableFactory) {
        this(
            DEFAULT_ROOT_DIR_PATH,
            DEFAULT_INCLUDE_BY_FILE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_BY_FILE_PATH_PATTERN_LIST,
            DEFAULT_EXCLUDE_ABSTRACT_CLASSES_FLAG,
            ObjectArgs.checkNotNull(traversePathIterableFactory, "traversePathIterableFactory"));
    }

    private ClassFinder(
            File rootDirPath,
            List<Pattern> includeByFilePathPatternList,
            List<Pattern> excludeByFilePathPatternList,
            boolean excludeAbstractClassesFlag,
            TraversePathIterableFactory traversePathIterableFactory) {
        _rootDirPath = rootDirPath;
        _includeByAbsolutePathPatternList = includeByFilePathPatternList;
        _excludeByAbsolutePathPatternList = excludeByFilePathPatternList;
        _excludeAbstractClassesFlag = excludeAbstractClassesFlag;
        _traversePathIterableFactory = traversePathIterableFactory;
    }

    public ClassFinder withRootDirPath(File rootDirPath)
    throws PathException {
        PathArgs.checkDirectoryExists(rootDirPath, "rootDirPath");

        ClassFinder x = new ClassFinder(
            rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            _traversePathIterableFactory);
        return x;
    }

    public ClassFinder includeByAbsolutePathPattern(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toList(filePathPattern, moreFilePathPatternsArr);
        ClassFinder x = new ClassFinder(
            _rootDirPath,
            list,
            _excludeByAbsolutePathPatternList,
            _excludeAbstractClassesFlag,
            _traversePathIterableFactory);
        return x;
    }

    public ClassFinder excludeByAbsolutePathPattern(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr) {
        List<Pattern> list = _toList(filePathPattern, moreFilePathPatternsArr);
        ClassFinder x = new ClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            list,
            _excludeAbstractClassesFlag,
            _traversePathIterableFactory);
        return x;
    }

    public ClassFinder excludeAbstractClasses(boolean flag) {
        ClassFinder x = new ClassFinder(
            _rootDirPath,
            _includeByAbsolutePathPatternList,
            _excludeByAbsolutePathPatternList,
            flag,
            _traversePathIterableFactory);
        return x;
    }

    private List<Pattern> _toList(Pattern filePathPattern, Pattern[] moreFilePathPatternsArr) {
        ObjectArgs.checkNotNull(filePathPattern, "filePathPattern");
        ArrayArgs.checkElementsNotNull(moreFilePathPatternsArr, "moreFilePathPatternsArr");

        List<Pattern> list = new ArrayList<Pattern>(1 + moreFilePathPatternsArr.length);
        list.add(filePathPattern);
        list.addAll(Arrays.asList(moreFilePathPatternsArr));
        return list;
    }

    public ClassFinderResult find() {
        _assertCanFind();
        TraversePathIterable pathIter = _traversePathIterableFactory.newInstance(
            _rootDirPath, TraversePathDepthPolicy.DEPTH_LAST);
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (File path : pathIter) {
            String pathname = path.getAbsolutePath();
            if (_isMatch(pathname, _includeByAbsolutePathPatternList)
                    && !_isMatch(pathname, _excludeByAbsolutePathPatternList)) {
                Class<?> klass = _getClass(path);
                if (!_excludeAbstractClassesFlag || !Modifier.isAbstract(klass.getModifiers())) {
                    classList.add(klass);
                }
            }
        }
        ClassFinderResult result = new ClassFinderResult(classList);
        return result;
    }

    private void _assertCanFind() {
        if (_includeByAbsolutePathPatternList.isEmpty()) {
            throw new IllegalStateException("Must call method includeByFilePathPatterns() first");
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

    private Class<?> _getClass(final File path) {
        File pathForIter = path;
        String className = pathForIter.getName();
        while (true) {
            try {
                // TODO: Use a factory to abstract away?  Try Powermock first.
                Class<?> klass = Class.forName(className);
                return klass;
            }
            catch (Throwable ignored) {
            }
            pathForIter = pathForIter.getParentFile();
            if (null == pathForIter) {
                throw new IllegalArgumentException(String.format(
                    "Failed to find class for path: '%s'", path.getAbsolutePath()));
            }
            className = pathForIter.getName() + "." + className;
        }
    }
}
