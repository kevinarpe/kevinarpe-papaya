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

import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;

import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Builder class for {@link AbstractTraversePathIteratorImpl}.
 *
 * This class divides directory traversal (which directories to descend) from path iteration (which
 * paths to return from {@link Iterator#next()}).  Independent control is provided for both groups.
 * <p>
 * Methods for directory traversal:
 * <ul>
 *     <li>{@link #withDescendDirPathFilter(PathFilter)}</li>
 *     <li>{@link #withDescendDirPathComparator(Comparator)}</li>
 * </ul>
 * <p>
 * Methods for path iteration:
 * <ul>
 *     <li>{@link #withIteratePathFilter(PathFilter)}</li>
 *     <li>{@link #withIteratePathComparator(Comparator)}</li>
 * </ul>
 * <p>
 * Example: Iterate all normal files (non-directories) in a directory tree.  This is equivalent to
 * the UNIX command: {@code find $dir -not -type d}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathDepthPolicy
 * @see AbstractTraversePathIterSettings
 */
@Immutable
@FullyTested
final class TraversePathIterableImpl
extends AbstractTraversePathIterSettings
implements TraversePathIterable {

    public TraversePathIterableImpl(File dirPath, TraversePathDepthPolicy depthPolicy) {
        this(
            dirPath,
            depthPolicy,
            TraversePathIterableUtils.DEFAULT_EXCEPTION_POLICY,
            TraversePathIterableUtils.DEFAULT_DESCEND_DIR_PATH_FILTER,
            TraversePathIterableUtils.DEFAULT_DESCEND_DIR_PATH_COMPARATOR,
            TraversePathIterableUtils.DEFAULT_ITERATE_PATH_FILTER,
            TraversePathIterableUtils.DEFAULT_ITERATE_PATH_COMPARATOR);
    }

    TraversePathIterableImpl(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter descendDirPathFilter,
            Comparator<File> descendDirPathComparator,
            PathFilter iteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            ObjectArgs.checkNotNull(exceptionPolicy, "exceptionPolicy"),
            ObjectArgs.checkNotNull(descendDirPathFilter, "descendDirPathFilter"),
            ObjectArgs.checkNotNull(descendDirPathComparator, "descendDirPathComparator"),
            ObjectArgs.checkNotNull(iteratePathFilter, "iteratePathFilter"),
            ObjectArgs.checkNotNull(optIteratePathComparator, "optIteratePathComparator"));
    }

    @Override
    public TraversePathIterable withRootDirPath(File rootDirPath) {
        return new TraversePathIterableImpl(
            ObjectArgs.checkNotNull(rootDirPath, "rootDirPath"),
            withDepthPolicy(),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterableImpl withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterable withExceptionPolicy(TraversePathExceptionPolicy exceptionPolicy) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            ObjectArgs.checkNotNull(exceptionPolicy, "exceptionPolicy"),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterableImpl withDescendDirPathFilter(PathFilter descendDirPathFilter) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            ObjectArgs.checkNotNull(descendDirPathFilter, "descendDirPathFilter"),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterable withDescendDirPathComparator(
            Comparator<File> descendDirPathComparator) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            ObjectArgs.checkNotNull(descendDirPathComparator, "descendDirPathComparator"),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterable withIteratePathFilter(PathFilter iteratePathFilter) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            ObjectArgs.checkNotNull(iteratePathFilter, "iteratePathFilter"),
            withIteratePathComparator());
    }

    @Override
    public TraversePathIterable withIteratePathComparator(
            Comparator<File> iteratePathComparator) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            ObjectArgs.checkNotNull(iteratePathComparator, "iteratePathComparator"));
    }

    @Override
    public TraversePathIterator iterator() {
        final TraversePathDepthPolicy depthPolicy = withDepthPolicy();
        return depthPolicy.createTraversePathIterator(
            withRootDirPath(),
            withExceptionPolicy(),
            withDescendDirPathFilter(),
            withDescendDirPathComparator(),
            withIteratePathFilter(),
            withIteratePathComparator());
    }

    @Override
    public final int hashCode() {
        int result =
            Objects.hashCode(
                withRootDirPath(),
                withDepthPolicy(),
                withExceptionPolicy(),
                withDescendDirPathFilter(),
                withDescendDirPathComparator(),
                withIteratePathFilter(),
                withIteratePathComparator());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof TraversePathIterableImpl) {
            final TraversePathIterableImpl other = (TraversePathIterableImpl) obj;
            result =
                Objects.equal(this.withRootDirPath(), other.withRootDirPath())
                    && Objects.equal(this.withDepthPolicy(), other.withDepthPolicy())
                    && Objects.equal(this.withExceptionPolicy(), other.withExceptionPolicy())
                    && Objects.equal(
                            this.withDescendDirPathFilter(),
                            other.withDescendDirPathFilter())
                    && Objects.equal(
                            this.withDescendDirPathComparator(),
                            other.withDescendDirPathComparator())
                    && Objects.equal(
                            this.withIteratePathFilter(),
                            other.withIteratePathFilter())
                    && Objects.equal(
                            this.withIteratePathComparator(),
                            other.withIteratePathComparator());
        }
        return result;
    }
}
