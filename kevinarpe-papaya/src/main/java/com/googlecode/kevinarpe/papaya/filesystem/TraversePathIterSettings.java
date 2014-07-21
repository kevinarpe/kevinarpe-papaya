package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface TraversePathIterSettings {

    /**
     * @return root directory to traverse.  There is no guarantee this directory exists, but this
     *         value is never {@code null}.
     *
     * @see TraversePathIterable#withRootDirPath(File)
     */
    File withRootDirPath();

    /**
     * @return when to descend directories: first or last.  Never {@code null}.
     *
     * @see TraversePathIterable#withDepthPolicy(TraversePathDepthPolicy)
     */
    TraversePathDepthPolicy withDepthPolicy();

    /**
     * @return how to handle exceptions thrown during directory listings
     *
     * @see TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)
     */
    TraversePathExceptionPolicy withExceptionPolicy();

    /**
     * @return filter used before traversing directories.  May be {@code null}.
     *
     * @see TraversePathIterable#withDescendDirPathFilter(PathFilter)
     */
    PathFilter withDescendDirPathFilter();

    /**
     * @return comparator to sort directories before traversal.  May be {@code null}.
     *
     * @see TraversePathIterable#withDescendDirPathComparator(Comparator)
     */
    Comparator<File> withDescendDirPathComparator();

    /**
     * @return filter used before iterating paths.  May be {@code null}.
     *
     * @see TraversePathIterable#withIteratePathFilter(PathFilter)
     */
    PathFilter withIteratePathFilter();

    /**
     * @return comparator to sort paths before iteration.  May be {@code null}.
     *
     * @see TraversePathIterable#withIteratePathComparator(Comparator)
     */
    Comparator<File> withIteratePathComparator();
}
