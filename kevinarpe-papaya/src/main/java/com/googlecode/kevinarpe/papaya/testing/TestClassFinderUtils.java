package com.googlecode.kevinarpe.papaya.testing;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Constants and static utilities for {@code TestClassFinder}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #newInstance()
 * @see #newFactory()
 * @see TestClassFinder
 * @see StatelessObject
 * @see ITestClassFinderUtils
 */
@FullyTested
public final class TestClassFinderUtils
extends StatelessObject
implements ITestClassFinderUtils {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final TestClassFinderUtils INSTANCE = new TestClassFinderUtils();

    /**
     * Default value for {@link TestClassFinder#withRootDirPath()}: System property
     * {@code "user.dir"}.  This is the equivalent of: {@code new File(".").getAbsoluteFile()}
     */
    public static final File DEFAULT_ROOT_DIR_PATH = new File(System.getProperty("user.dir"));

    /**
     * Default value for {@link TestClassFinder#withIncludePatterns()}: A single pattern to match
     * all Java source files.
     */
    public static final List<Pattern> DEFAULT_INCLUDE_PATTERN_LIST =
        ImmutableList.of(Pattern.compile("\\.java$"));

    /**
     * Default value for {@link TestClassFinder#withExcludePatterns()}: An empty list (no patterns).
     */
    public static final List<Pattern> DEFAULT_EXCLUDE_PATTERN_LIST = ImmutableList.of();

    /**
     * For projects that require total, static-free mocking capabilities, use this constructor.
     * Else, the static constant {@link #INSTANCE} will suffice.
     */
    public TestClassFinderUtils() {
        // Empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestClassFinder newInstance() {
        TestClassFinderFactory factory = newFactory();
        TestClassFinder x = factory.newInstance();
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestClassFinderFactory newFactory() {
        return TestClassFinderFactoryImpl.INSTANCE;
    }
}
