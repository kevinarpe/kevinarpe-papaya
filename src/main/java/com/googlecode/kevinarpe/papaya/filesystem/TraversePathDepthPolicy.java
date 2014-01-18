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
 * Controls how a directory tree is traversed: getDepth-first or getDepth-last.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathIterable
 */
@NotFullyTested
public enum TraversePathDepthPolicy {

    /**
     * When traversing a directory tree, descend to the deepest level before iterating.
     * <p>
     * An example use case for this mode: Recursively deleting a directory tree.  In most modern
     * file systems, it is required to remove all files in a directory before removing the parent
     * directory.  This necessitates getDepth-first traversal/iteration.
     *
     * @see #DEPTH_LAST
     */
    DEPTH_FIRST {
        @Override
        TraversePathDepthFirstIterator createTraversePathIterator(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> descendDirPathComparatorList,
                PathFilter optIteratePathFilter,
                Comparator<File> iterateFileComparatorList) {
            return new TraversePathDepthFirstIterator(
                dirPath,
                depthPolicy,
                exceptionPolicy,
                optDescendDirPathFilter,
                descendDirPathComparatorList,
                optIteratePathFilter,
                iterateFileComparatorList);
        }
    },

    /**
     * When traversing a directory tree, do <i>not</i> descend before iterating.  This is the normal
     * behavior of the UNIX command line tool {@code find}.
     *
     * @see #DEPTH_FIRST
     */
    DEPTH_LAST {
        @Override
        TraversePathDepthLastIterator createTraversePathIterator(
                File dirPath,
                TraversePathDepthPolicy depthPolicy,
                TraversePathExceptionPolicy exceptionPolicy,
                PathFilter optDescendDirPathFilter,
                Comparator<File> descendDirPathComparatorList,
                PathFilter optIteratePathFilter,
                Comparator<File> iterateFileComparatorList) {
            return new TraversePathDepthLastIterator(
                dirPath,
                depthPolicy,
                exceptionPolicy,
                optDescendDirPathFilter,
                descendDirPathComparatorList,
                optIteratePathFilter,
                iterateFileComparatorList);
        }
    };

    abstract TraversePathIterator createTraversePathIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> descendDirPathComparatorList,
            PathFilter optIteratePathFilter,
            Comparator<File> iterateFileComparatorList);
}
