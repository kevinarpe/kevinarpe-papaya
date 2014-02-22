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

import com.googlecode.kevinarpe.papaya.exception.ClassNotFoundRuntimeException;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds {@link Class} references by recursively scanning a directory for source files.  This class
 * can be used to build dynamic test suites.  To construct a new instance, see
 * {@link TestClassFinders#newInstance()}.
 * <p>
 * An interface is used instead of a concrete class to make mocking and testing easier.
 * <p>
 * Example:
 * <pre>{@code
 * List<Class<?>> classList =
 *     TestClassFinders.newInstance()
 *         .withRootDir(new File("src/test"))
 *         .withIncludePatterns(Pattern.compile("Test\\.java"))
 *         .findAsList();
 * }</pre>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface TestClassFinder {

    /**
     * Constructs a new instance with new root directory to use when finding test classes.  For
     * projects that use Maven, path {@code new File("./src/test")} is a good choice.
     *
     * @param rootDirPath
     *        Path to root directory to search for test classes.  Must not be {@code null}.
     *
     * @return new instance with new root directory
     *
     * @throws NullPointerException
     *         if {@code rootDirPath} is {@code null}
     *
     * @see TestClassFinders#DEFAULT_ROOT_DIR_PATH
     * @see #withRootDirPath()
     */
    TestClassFinder withRootDirPath(File rootDirPath);

    /**
     * Retrieves root directory to use when finding test classes.  Default is the user working
     * directory from system property {@code "user.dir"}.
     *
     * @return root directory
     *
     * @see TestClassFinders#DEFAULT_ROOT_DIR_PATH
     * @see #withRootDirPath(File)
     */
    File withRootDirPath();

    /**
     * This is a convenience method to call {@link #withIncludePatterns(List)}.
     *
     * @throws NullPointerException
     *         if {@code filePathPattern} or {@code moreFilePathPatternsArr} (or any element) is
     *         {@code null}
     */
    TestClassFinder withIncludePatterns(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr);

    /**
     * Constructs a new instance with a new list of patterns used to include source files.
     * To exclude files by pattern, see {@link #withExcludePatterns(List)}.
     * <p>
     * Matching is performed with {@link Matcher#find()}, which does not require the entire input
     * string to match, like {@link Matcher#matches()}.  If anchoring is required, the patterns must
     * explicitly include {@code "^"} (start anchor) or {@code "$"} (end anchor).
     *
     * @param filePathPatternList
     * <ul>
     *     <li>List of patterns to match absolute file paths</li>
     *     <li>Example: {@code "ExternalTest\\.java$"}</li>
     *     <li>To match all files use: {@code "."}</li>
     *     <li>Must not be {@code null}, but may be empty</li>
     * </ul>
     *
     * @return new instance with new list of include patterns
     *
     * @throws NullPointerException
     *         if {@code filePathPatternList} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if {@code filePathPatternList} is empty
     *
     * @see TestClassFinders#DEFAULT_INCLUDE_PATTERN_LIST
     * @see #withIncludePatterns(Pattern, Pattern...)
     * @see #withIncludePatterns()
     * @see #withExcludePatterns(List)
     */
    TestClassFinder withIncludePatterns(List<Pattern> filePathPatternList);

    /**
     * Retrieves the list of patterns used to include source files.  Default is a single pattern to
     * match all files: {@code "."}
     *
     * @return list of include patterns.  Never {@code null} or empty.
     *
     * @see TestClassFinders#DEFAULT_INCLUDE_PATTERN_LIST
     * @see #withIncludePatterns(List)
     * @see #withExcludePatterns()
     */
    List<Pattern> withIncludePatterns();

    /**
     * This is a convenience method to call {@link #withExcludePatterns(List)}.
     *
     * @throws NullPointerException
     *         if {@code filePathPattern} or {@code moreFilePathPatternsArr} (or any element) is
     *         {@code null}
     */
    TestClassFinder withExcludePatterns(
            Pattern filePathPattern, Pattern... moreFilePathPatternsArr);

    /**
     * Constructs a new instance with a new list of patterns used to exclude source files.
     * To include files by pattern, see {@link #withIncludePatterns(List)}.
     * <p>
     * Matching is performed with {@link Matcher#find()}, which does not require the entire input
     * string to match, like {@link Matcher#matches()}.  If anchoring is required, the patterns must
     * explicitly include {@code "^"} (start anchor) or {@code "$"} (end anchor).
     *
     * @param filePathPatternList
     * <ul>
     *     <li>List of patterns to match absolute file paths</li>
     *     <li>Example: {@code "ExternalTest\\.java$"}</li>
     *     <li>Must not be {@code null}, but may be empty</li>
     * </ul>
     *
     * @return new instance with new list of exclude patterns
     *
     * @throws NullPointerException
     *         if {@code filePathPatternList} (or any element) is {@code null}
     *
     * @see TestClassFinders#DEFAULT_EXCLUDE_PATTERN_LIST
     * @see #withIncludePatterns(Pattern, Pattern...)
     * @see #withIncludePatterns()
     * @see #withIncludePatterns(List)
     */
    TestClassFinder withExcludePatterns(List<Pattern> filePathPatternList);

    /**
     * Retrieves the list of patterns used to include source files.  Default is empty list (no
     * patterns to exclude source files).
     *
     * @return list of include patterns.  Never {@code null} or empty.
     *
     * @see TestClassFinders#DEFAULT_EXCLUDE_PATTERN_LIST
     * @see #withExcludePatterns(List)
     * @see #withIncludePatterns()
     */
    List<Pattern> withExcludePatterns();

    /**
     * Constructs a new instance with a new logger at a specific level.
     *
     * @param logLevel
     *        level to log.  Use {@link SLF4JLogLevel#OFF} to disable logging.
     *
     * @return new instance with new logger
     *
     * @see TestClassFinders#DEFAULT_LOG_LEVEL
     * @see #withLogLevel()
     */
    TestClassFinder withLogLevel(SLF4JLogLevel logLevel);

    /**
     * Retrieves the current logger level.  All logging during a search is done to this level.
     * Default is no logging (off).
     *
     * @return current logger level
     *
     * @see TestClassFinders#DEFAULT_LOG_LEVEL
     * @see #withLogLevel(SLF4JLogLevel)
     */
    SLF4JLogLevel withLogLevel();

    /**
     * Searches recursively from the root directory to find Java sources files and returns the
     * corresponding {@link Class} for each matching file.  Three class types are automatically
     * excluded: nested, inner, and abstract classes.
     * <p>
     * If a source file matches at least one include pattern and matches zero exclude patterns, it
     * will be included in the result.
     * <p>
     * If logging is enabled, exactly one line is logged for each normal file.
     *
     * @return list of top-level classes.  May be empty, but never {@code null}.
     *
     * @throws ClassNotFoundRuntimeException
     *         if a {@link Class} cannot be found for a source file
     *
     * @see #findAsArray()
     * @see #withLogLevel(SLF4JLogLevel)
     */
    List<Class<?>> findAsList();

    /**
     * This is a convenience method to call {@link #findAsList()}.
     */
    Class<?>[] findAsArray();
}
