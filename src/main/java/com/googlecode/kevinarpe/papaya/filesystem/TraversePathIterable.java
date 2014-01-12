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
import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.util.Comparator;
import java.util.List;

public final class TraversePathIterable
extends BaseTraversePathIter
implements Iterable<File> {

    public TraversePathIterable(File dirPath, TraversePathDepthPolicy depthPolicy) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            (PathFilter) null,
            ImmutableList.<Comparator<File>>of(),
            (PathFilter) null,
            ImmutableList.<Comparator<File>>of());
    }

    private TraversePathIterable(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            List<Comparator<File>> descendDirPathComparatorList,
            PathFilter optPathFilter,
            List<Comparator<File>> fileComparatorList) {
        super(
            dirPath,
            depthPolicy,
            optDescendDirPathFilter,
            descendDirPathComparatorList,
            optPathFilter,
            fileComparatorList);
    }

    public TraversePathIterable withDirPath(File dirPath) {
        return new TraversePathIterable(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getDescendDirPathComparatorList(),
            getOptionalIteratePathFilter(),
            getIteratePathComparatorList());
    }

    public TraversePathIterable withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterable(
            getDirPath(),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            getOptionalDescendDirPathFilter(),
            getDescendDirPathComparatorList(),
            getOptionalIteratePathFilter(),
            getIteratePathComparatorList());
    }

    public TraversePathIterable withOptionalDescendDirPathFilter(
            PathFilter optDescendDirPathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            optDescendDirPathFilter,
            getDescendDirPathComparatorList(),
            getOptionalIteratePathFilter(),
            getIteratePathComparatorList());
    }

    public TraversePathIterable withDescendDirPathComparatorList(
            List<Comparator<File>> descendDirPathComparatorList) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            ImmutableList.copyOf(
                CollectionArgs.checkElementsNotNull(
                    descendDirPathComparatorList, "descendDirPathComparatorList")),
            getOptionalIteratePathFilter(),
            getIteratePathComparatorList());
    }

    public TraversePathIterable withOptionalPathFilter(PathFilter optPathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getDescendDirPathComparatorList(),
            optPathFilter,
            getIteratePathComparatorList());
    }

    public TraversePathIterable withPathComparatorList(List<Comparator<File>> pathComparatorList) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getDescendDirPathComparatorList(),
            getOptionalIteratePathFilter(),
            ImmutableList.copyOf(
                CollectionArgs.checkElementsNotNull(
                    pathComparatorList, "pathComparatorList")));
    }

    @Override
    public TraversePathIterator iterator() {
        final TraversePathDepthPolicy depthPolicy = getDepthPolicy();
        return depthPolicy.createTraversePathIterator(
            getDirPath(),
            getDepthPolicy(),
            getOptionalDescendDirPathFilter(),
            getDescendDirPathComparatorList(),
            getOptionalIteratePathFilter(),
            getIteratePathComparatorList());
    }

    @Override
    public final int hashCode() {
        int result =
            Objects.hashCode(
                getDirPath(),
                getDepthPolicy(),
                getOptionalDescendDirPathFilter(),
                getDescendDirPathComparatorList(),
                getOptionalIteratePathFilter(),
                getIteratePathComparatorList());
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
                            this.getDescendDirPathComparatorList(),
                            other.getDescendDirPathComparatorList())
                    && Objects.equal(
                            this.getOptionalIteratePathFilter(),
                            other.getOptionalIteratePathFilter())
                    && Objects.equal(
                            this.getIteratePathComparatorList(),
                            other.getIteratePathComparatorList());
        }
        return result;
    }
}
