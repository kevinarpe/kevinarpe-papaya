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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;

import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.util.Comparator;

/**
 * Based class for {@link TraversePathIterableImpl} and {@link AbstractTraversePathIteratorImpl} to provide
 * read-only access to its attributes.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@Immutable
@FullyTested
abstract class AbstractTraversePathIterSettings
implements TraversePathIterSettings {

    private final File _dirPath;
    private final TraversePathDepthPolicy _depthPolicy;
    private final TraversePathExceptionPolicy _exceptionPolicy;
    private final PathFilter _descendDirPathFilter;
    private final Comparator<File> _descendDirPathComparator;
    private final PathFilter _iteratePathFilter;
    private final Comparator<File> _iteratePathComparator;

    /**
     * Parameters to this method are completely unchecked.  Subclasses must implement checks.
     */
    protected AbstractTraversePathIterSettings(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter descendDirPathFilter,
            Comparator<File> descendDirPathComparator,
            PathFilter iteratePathFilter,
            Comparator<File> iteratePathComparator) {
        _dirPath = dirPath;
        _depthPolicy = depthPolicy;
        _exceptionPolicy = exceptionPolicy;
        _descendDirPathFilter = descendDirPathFilter;
        _descendDirPathComparator = descendDirPathComparator;
        _iteratePathFilter = iteratePathFilter;
        _iteratePathComparator = iteratePathComparator;
    }

    @Override
    public final File withRootDirPath() {
        return _dirPath;
    }

    @Override
    public final TraversePathDepthPolicy withDepthPolicy() {
        return _depthPolicy;
    }

    @Override
    public TraversePathExceptionPolicy withExceptionPolicy() {
        return _exceptionPolicy;
    }

    @Override
    public final PathFilter withDescendDirPathFilter() {
        return _descendDirPathFilter;
    }

    @Override
    public final Comparator<File> withDescendDirPathComparator() {
        return _descendDirPathComparator;
    }

    @Override
    public final PathFilter withIteratePathFilter() {
        return _iteratePathFilter;
    }

    @Override
    public final Comparator<File> withIteratePathComparator() {
        return _iteratePathComparator;
    }

    // Do not override hashCode() and equals() here.  It doesn't make sense to support this
    // operation for iterators.

    @Override
    public final String toString() {
        // TODO: Add object ID, e.g., @123456
        String x = String.format(
            "%s {%n\t%s = %s%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n\t%s = '%s'%n}",
            AbstractTraversePathIterSettings.class.getName(),
            "dirPath", _formatPath(_dirPath),
            "depthPolicy", _depthPolicy,
            "exceptionPolicy", _exceptionPolicy,
            "descendDirPathFilter", _descendDirPathFilter,
            "descendDirPathComparator", _descendDirPathComparator,
            "iteratePathFilter", _iteratePathFilter,
            "iteratePathComparator", _iteratePathComparator);
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
