package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.io.File;
import java.util.Comparator;

/**
 * Based class for {@link TraversePathIterableImpl} and {@link AbstractTraversePathIteratorImpl} to provide
 * read-only access to its attributes.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
class TraversePathIterSettingsImpl
implements TraversePathIterSettings {

    private final File _dirPath;
    private final TraversePathDepthPolicy _depthPolicy;
    private final TraversePathExceptionPolicy _exceptionPolicy;
    private final PathFilter _optDescendDirPathFilter;
    private final Comparator<File> _optDescendDirPathComparator;
    private final PathFilter _optIteratePathFilter;
    private final Comparator<File> _optIteratePathComparator;

    /**
     * Parameters to this method are completely unchecked.  Subclasses must implement checks.
     */
    protected TraversePathIterSettingsImpl(
        File dirPath,
        TraversePathDepthPolicy depthPolicy,
        TraversePathExceptionPolicy exceptionPolicy,
        PathFilter optDescendDirPathFilter,
        Comparator<File> optDescendDirPathComparator,
        PathFilter optIteratePathFilter,
        Comparator<File> optIteratePathComparator) {
        _dirPath = dirPath;
        _depthPolicy = depthPolicy;
        _exceptionPolicy = exceptionPolicy;
        _optDescendDirPathFilter = optDescendDirPathFilter;
        _optDescendDirPathComparator = optDescendDirPathComparator;
        _optIteratePathFilter = optIteratePathFilter;
        _optIteratePathComparator = optIteratePathComparator;
    }

    /**
     * @return root directory to traverse.  There is no guarantee this directory exists, but this
     *         value is never {@code null}.
     *
     * @see TraversePathIterableImpl#withRootDirPath(File)
     */
    @Override
    public final File withRootDirPath() {
        return _dirPath;
    }

    /**
     * @return when to descend directories: first or last.  Never {@code null}.
     *
     * @see TraversePathIterableImpl#withDepthPolicy(TraversePathDepthPolicy)
     */
    @Override
    public final TraversePathDepthPolicy withDepthPolicy() {
        return _depthPolicy;
    }

    /**
     * @return how to handle exceptions thrown during directory listings
     *
     * @see TraversePathIterableImpl#withExceptionPolicy(TraversePathExceptionPolicy)
     */
    @Override
    public TraversePathExceptionPolicy withExceptionPolicy() {
        return _exceptionPolicy;
    }

    /**
     * @return filter used before traversing directories.  May be {@code null}.
     *
     * @see TraversePathIterableImpl#withOptionalDescendDirPathFilter(PathFilter)
     */
    @Override
    public final PathFilter withOptionalDescendDirPathFilter() {
        return _optDescendDirPathFilter;
    }

    /**
     * @return comparator to sort directories before traversal.  May be {@code null}.
     *
     * @see TraversePathIterableImpl#withOptionalDescendDirPathComparator(Comparator)
     */
    @Override
    public final Comparator<File> withOptionalDescendDirPathComparator() {
        return _optDescendDirPathComparator;
    }

    /**
     * @return filter used before iterating paths.  May be {@code null}.
     *
     * @see TraversePathIterableImpl#withOptionalIteratePathFilter(PathFilter)
     */
    @Override
    public final PathFilter withOptionalIteratePathFilter() {
        return _optIteratePathFilter;
    }

    /**
     * @return comparator to sort paths before iteration.  May be {@code null}.
     *
     * @see TraversePathIterableImpl#withOptionalIteratePathComparator(Comparator)
     */
    @Override
    public final Comparator<File> withOptionalIteratePathComparator() {
        return _optIteratePathComparator;
    }

    // Do not override hashCode() and equals() here.  It doesn't make sense to support this
    // operation for iterators.

    @Override
    public String toString() {
        // TODO: Add object ID, e.g., @123456
        String x = String.format(
            "%s {%n\t%s = %s%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n}",
            TraversePathIterSettingsImpl.class.getName(),
            "dirPath", _formatPath(_dirPath),
            "depthPolicy", _depthPolicy,
            "exceptionPolicy", _exceptionPolicy,
            "optDescendDirPathFilter", _optDescendDirPathFilter,
            "optDescendDirPathComparator", _optDescendDirPathComparator,
            "optIteratePathFilter", _optIteratePathFilter,
            "optIteratePathComparator", _optIteratePathComparator);
        return x;
    }

    private String _formatPath(File path) {
        if (path.isAbsolute()) {
            String x = String.format("'%s'", path);
            return x;
        }
        String x = String.format("'%s' -> '%s'", path, path.getAbsolutePath());
        return x;
    }
}
