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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

import java.io.File;
import java.util.Comparator;

/**
 * Based class for {@link TraversePathIterable}, {@link TraversePathDepthFirstIterator}, and
 * {@link TraversePathDepthLastIterator} to provide read-only access to its attributes.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public class BaseTraversePathIter {

    private final File _dirPath;
    private final TraversePathDepthPolicy _depthPolicy;
    private final PathFilter _optDescendDirPathFilter;
    private final Comparator<File> _optDescendDirPathComparator;
    private final PathFilter _optIteratePathFilter;
    private final Comparator<File> _optIteratePathComparator;

    /**
     * Parameters to this method are completely unchecked.  Subclasses must implement checks.
     */
    protected BaseTraversePathIter(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        _dirPath = dirPath;
        _depthPolicy = depthPolicy;
        _optDescendDirPathFilter = optDescendDirPathFilter;
        _optDescendDirPathComparator = optDescendDirPathComparator;
        _optIteratePathFilter = optIteratePathFilter;
        _optIteratePathComparator = optIteratePathComparator;
    }

    /**
     * @return root directory to traverse.  There is no guarantee this directory exists, but this
     *         value is never {@code null}.
     *
     * @see TraversePathIterable#withDirPath(File)
     */
    public final File getDirPath() {
        return _dirPath;
    }

    /**
     * @return when to descend directories: first or last.  Never {@code null}.
     *
     * @see TraversePathIterable#withDepthPolicy(TraversePathDepthPolicy)
     */
    public final TraversePathDepthPolicy getDepthPolicy() {
        return _depthPolicy;
    }

    /**
     * @return filter used before traversing directories.  May be {@code null}.
     *
     * @see TraversePathIterable#withOptionalDescendDirPathFilter(PathFilter)
     */
    public final PathFilter getOptionalDescendDirPathFilter() {
        return _optDescendDirPathFilter;
    }

    /**
     * @return comparator to sort directories before traversal.  May be {@code null}.
     *
     * @see TraversePathIterable#withOptionalDescendDirPathComparator(Comparator)
     */
    public final Comparator<File> getOptionalDescendDirPathComparator() {
        return _optDescendDirPathComparator;
    }

    /**
     * @return filter used before iterating paths.  May be {@code null}.
     *
     * @see TraversePathIterable#withOptionalIteratePathFilter(PathFilter)
     */
    public final PathFilter getOptionalIteratePathFilter() {
        return _optIteratePathFilter;
    }

    /**
     * @return comparator to sort paths before iteration.  May be {@code null}.
     *
     * @see TraversePathIterable#withOptionalIteratePathComparator(Comparator)
     */
    public final Comparator<File> getOptionalIteratePathComparator() {
        return _optIteratePathComparator;
    }

    // Do not override hashCode() and equals() here.  It doesn't make sense to support this
    // operation for iterators.
}
