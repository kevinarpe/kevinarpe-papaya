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
 *     <li>{@link #withOptionalDescendDirPathFilter(PathFilter)}</li>
 *     <li>{@link #withOptionalDescendDirPathComparator(Comparator)}</li>
 * </ul>
 * <p>
 * Methods for path iteration:
 * <ul>
 *     <li>{@link #withOptionalIteratePathFilter(PathFilter)}</li>
 *     <li>{@link #withOptionalIteratePathComparator(Comparator)}</li>
 * </ul>
 * <p>
 * Example: Iterate all normal files (non-directories) in a directory tree.  This is equivalent to
 * the UNIX command: {@code find $dir -not -type d}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathDepthPolicy
 * @see TraversePathIterSettingsImpl
 */
@FullyTested
final class TraversePathIterableImpl
extends TraversePathIterSettingsImpl
implements TraversePathIterable {

    public TraversePathIterableImpl(File dirPath, TraversePathDepthPolicy depthPolicy) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            TraversePathUtils.DEFAULT_EXCEPTION_POLICY,
            (PathFilter) null,
            (Comparator<File>) null,
            (PathFilter) null,
            (Comparator<File>) null);
    }

    TraversePathIterableImpl(
        File dirPath,
        TraversePathDepthPolicy depthPolicy,
        TraversePathExceptionPolicy exceptionPolicy,
        PathFilter optDescendDirPathFilter,
        Comparator<File> optDescendDirPathComparator,
        PathFilter optIteratePathFilter,
        Comparator<File> optIteratePathComparator) {
        super(
            dirPath,
            depthPolicy,
            exceptionPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optIteratePathFilter,
            optIteratePathComparator);
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable withRootDirPath(File rootDirPath) {
        return new TraversePathIterableImpl(
            ObjectArgs.checkNotNull(rootDirPath, "rootDirPath"),
            withDepthPolicy(),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterableImpl withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable withExceptionPolicy(TraversePathExceptionPolicy exceptionPolicy) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            ObjectArgs.checkNotNull(exceptionPolicy, "exceptionPolicy"),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterableImpl withOptionalDescendDirPathFilter(
        PathFilter optDescendDirPathFilter) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            optDescendDirPathFilter,
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable withOptionalDescendDirPathComparator(
        Comparator<File> optDescendDirPathComparator) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            optDescendDirPathComparator,
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable withOptionalIteratePathFilter(PathFilter optIteratePathFilter) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            optIteratePathFilter,
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterable withOptionalIteratePathComparator(
        Comparator<File> optIteratePathComparator) {
        return new TraversePathIterableImpl(
            withRootDirPath(),
            withDepthPolicy(),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            optIteratePathComparator);
    }

    /** {@inheritDoc} */
    @Override
    public TraversePathIterator iterator() {
        final TraversePathDepthPolicy depthPolicy = withDepthPolicy();
        return depthPolicy.createTraversePathIterator(
            withRootDirPath(),
            withExceptionPolicy(),
            withOptionalDescendDirPathFilter(),
            withOptionalDescendDirPathComparator(),
            withOptionalIteratePathFilter(),
            withOptionalIteratePathComparator());
    }

    /** {@inheritDoc} */
    @Override
    public final int hashCode() {
        int result =
            Objects.hashCode(
                withRootDirPath(),
                withDepthPolicy(),
                withExceptionPolicy(),
                withOptionalDescendDirPathFilter(),
                withOptionalDescendDirPathComparator(),
                withOptionalIteratePathFilter(),
                withOptionalIteratePathComparator());
        return result;
    }

    /** {@inheritDoc} */
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
                            this.withOptionalDescendDirPathFilter(),
                            other.withOptionalDescendDirPathFilter())
                    && Objects.equal(
                            this.withOptionalDescendDirPathComparator(),
                            other.withOptionalDescendDirPathComparator())
                    && Objects.equal(
                            this.withOptionalIteratePathFilter(),
                            other.withOptionalIteratePathFilter())
                    && Objects.equal(
                            this.withOptionalIteratePathComparator(),
                            other.withOptionalIteratePathComparator());
        }
        return result;
    }
}
