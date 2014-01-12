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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.util.Comparator;

public final class TraversePathIterable
extends BaseTraversePathIter
implements Iterable<File> {

    public TraversePathIterable(File dirPath, TraversePathDepthPolicy depthPolicy) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            (PathFilter) null,
            (Comparator<File>) null,
            (PathFilter) null,
            (Comparator<File>) null);
    }

    private TraversePathIterable(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optPathFilter,
            Comparator<File> optFileComparator) {
        super(
            dirPath,
            depthPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optPathFilter,
            optFileComparator);
    }

    public TraversePathIterable withDirPath(File dirPath) {
        return new TraversePathIterable(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    public TraversePathIterable withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterable(
            getDirPath(),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    public TraversePathIterable withOptionalDescendDirPathFilter(
            PathFilter optDescendDirPathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            optDescendDirPathFilter,
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    public TraversePathIterable withOptionalDescendDirPathComparator(
            Comparator<File> optDescendDirPathComparator) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            optDescendDirPathComparator,
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    public TraversePathIterable withOptionalPathFilter(PathFilter optPathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            optPathFilter,
            getOptionalIteratePathComparator());
    }

    public TraversePathIterable withOptionalPathComparator(Comparator<File> optPathComparator) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            optPathComparator);
    }

    @Override
    public TraversePathIterator iterator() {
        final TraversePathDepthPolicy depthPolicy = getDepthPolicy();
        return depthPolicy.createTraversePathIterator(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Returns hash code of:
     * <ul>
     *     <li>{@link #getDirPath()}</li>
     *     <li>{@link #getDepthPolicy()}</li>
     *     <li>{@link #getOptionalDescendDirPathFilter()}</li>
     *     <li>{@link #getOptionalDescendDirPathComparator()}</li>
     *     <li>{@link #getOptionalIteratePathFilter()}</li>
     *     <li>{@link #getOptionalIteratePathComparator()}</li>
     * </ul>
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        int result =
            Objects.hashCode(
                getDirPath(),
                getDepthPolicy(),
                getOptionalDescendDirPathFilter(),
                getOptionalDescendDirPathComparator(),
                getOptionalIteratePathFilter(),
                getOptionalIteratePathComparator());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof TraversePathIterable) {
            final TraversePathIterable other = (TraversePathIterable) obj;
            result =
                Objects.equal(this.getDirPath(), other.getDirPath())
                    && Objects.equal(this.getDepthPolicy(), other.getDepthPolicy())
                    && Objects.equal(
                            this.getOptionalDescendDirPathFilter(),
                            other.getOptionalDescendDirPathFilter())
                    && Objects.equal(
                            this.getOptionalDescendDirPathComparator(),
                            other.getOptionalDescendDirPathComparator())
                    && Objects.equal(
                            this.getOptionalIteratePathFilter(),
                            other.getOptionalIteratePathFilter())
                    && Objects.equal(
                            this.getOptionalIteratePathComparator(),
                            other.getOptionalIteratePathComparator());
        }
        return result;
    }
}
