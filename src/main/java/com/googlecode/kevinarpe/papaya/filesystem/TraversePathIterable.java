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
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Builder class for {@link TraversePathIterator}.
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
 * @see BaseTraversePathIter
 */
@NotFullyTested
public final class TraversePathIterable
extends BaseTraversePathIter
implements Iterable<File> {

    /**
     * Used by the constructor to set the default exception policy for path iterators.  The value
     * is {@link TraversePathExceptionPolicy#THROW}.
     *
     * @see #TraversePathIterable(File, TraversePathDepthPolicy)
     * @see #getExceptionPolicy()
     * @see #withExceptionPolicy(TraversePathExceptionPolicy)
     */
    public static final TraversePathExceptionPolicy DEFAULT_EXCEPTION_POLICY =
        TraversePathExceptionPolicy.THROW;

    /**
     * Constructs an iterable with its required attributes and none of its optional attributes:
     * <ul>
     *     <li>descend directory path filter</li>
     *     <li>descend directory comparator</li>
     *     <li>iterate path filter</li>
     *     <li>iterate comparator</li>
     * </ul>
     * <p>
     * The exception policy is set to the default: {@link #DEFAULT_EXCEPTION_POLICY}.  It can be
     * changed via {@link #withExceptionPolicy(TraversePathExceptionPolicy)}.
     *
     * @param dirPath
     *        root directory to traverse.  Must not be {@code null}, but need not exist.  Directory
     *        trees may be highly ephemeral, so the existance of this directory at the time of
     *        construction is not required.
     *
     * @param depthPolicy
     *        when to descend directories: first or last.  Must not be {@code null}.
     *
     * @throws NullPointerException
     *         if {@code dirPath} or {@code depthPolicy} is {@code null}
     *
     * @see #withDirPath(File)
     * @see #withDepthPolicy(TraversePathDepthPolicy)
     * @see #withExceptionPolicy(TraversePathExceptionPolicy)
     * @see #DEFAULT_EXCEPTION_POLICY
     * @see #withOptionalDescendDirPathFilter(PathFilter)
     * @see #withOptionalDescendDirPathComparator(Comparator)
     * @see #withOptionalIteratePathFilter(PathFilter)
     * @see #withOptionalIteratePathComparator(Comparator)
     */
    public TraversePathIterable(File dirPath, TraversePathDepthPolicy depthPolicy) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            DEFAULT_EXCEPTION_POLICY,
            (PathFilter) null,
            (Comparator<File>) null,
            (PathFilter) null,
            (Comparator<File>) null);
    }

    TraversePathIterable(
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

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the root directory to traverse.
     * If the depth policy is {@link TraversePathDepthPolicy#DEPTH_FIRST}, this directory will be
     * the <i>last</i> path considered for iteration (depending upon the filter).  The vice versa
     * also holds true for {@link TraversePathDepthPolicy#DEPTH_LAST}: this directory will be the
     * <i>first</i> path considered for iteration (depending upon the filter).
     *
     * @param dirPath
     *        root directory to traverse.  Must not be {@code null}, but need not exist.  Directory
     *        trees may be highly ephemeral, so the existance of this directory at the time of
     *        construction is not required.
     *
     * @return <b>new</b> iterable
     *
     * @throws NullPointerException
     *         if {@code dirPath} is {@code null}
     *
     * @see #getDirPath()
     */
    public TraversePathIterable withDirPath(File dirPath) {
        return new TraversePathIterable(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            getDepthPolicy(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the depth policy.
     *
     * @param depthPolicy
     *        when to descend directories: first or last.  Must not be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @throws NullPointerException
     *         if {@code depthPolicy} is {@code null}
     *
     * @see #getDepthPolicy()
     */
    public TraversePathIterable withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterable(
            getDirPath(),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the exception policy.  The
     * default value is {@link #DEFAULT_EXCEPTION_POLICY}.
     *
     * @param exceptionPolicy
     *        how to handle exceptions thrown during directory listings.  Must not be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @throws NullPointerException
     *         if {@code exceptionPolicy} is {@code null}
     *
     * @see #DEFAULT_EXCEPTION_POLICY
     * @see #getExceptionPolicy()
     */
    public TraversePathIterable withExceptionPolicy(TraversePathExceptionPolicy exceptionPolicy) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            ObjectArgs.checkNotNull(exceptionPolicy, "exceptionPolicy"),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional descend directory
     * path filter.  This attribute filters directories before traversal.  If {@code null},
     * <i>all</i> directories are traversed.
     * <p>
     * To control which paths are iterated, see {@link #withOptionalIteratePathFilter(PathFilter)}.
     *
     * @param optDescendDirPathFilter
     *        path filter for descend directories.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #getOptionalDescendDirPathFilter()
     */
    public TraversePathIterable withOptionalDescendDirPathFilter(
            PathFilter optDescendDirPathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getExceptionPolicy(),
            optDescendDirPathFilter,
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional descend directory
     * comparator.  This attribute sorts directories before traversal.  If {@code null}, directories
     * are <i>not</i> sorted before traversal.
     * <p>
     * To control the order paths are iterated, see
     * {@link #withOptionalIteratePathComparator(Comparator)}.
     *
     * @param optDescendDirPathComparator
     *        comparator to sort descend directories.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #getOptionalDescendDirPathComparator()
     */
    public TraversePathIterable withOptionalDescendDirPathComparator(
            Comparator<File> optDescendDirPathComparator) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            optDescendDirPathComparator,
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional iterated paths
     * filter.  This attribute filters paths before iteration.  If {@code null}, <i>all</i> paths
     * are iterated, including directories.
     * <p>
     * To control which directories are traversed, see
     * {@link #withOptionalIteratePathFilter(PathFilter)}.
     * To control how directories are paths are traversed, see
     * {@link #withOptionalDescendDirPathFilter(PathFilter)}.
     *
     * @param optIteratePathFilter
     *        path filter for iterated paths.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #getOptionalIteratePathFilter()
     */
    public TraversePathIterable withOptionalIteratePathFilter(PathFilter optIteratePathFilter) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            optIteratePathFilter,
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional iterated paths
     * comparator.  This attribute sorts paths before iteration.  If {@code null}, paths are
     * <i>not</i> sorted before iteration.
     * <p>
     * To control the order directories are traversed, see
     * {@link #withOptionalDescendDirPathComparator(Comparator)}.
     *
     * @param optIteratePathComparator
     *        comparator to sort iterated paths.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #getOptionalIteratePathComparator()
     */
    public TraversePathIterable withOptionalIteratePathComparator(
            Comparator<File> optIteratePathComparator) {
        return new TraversePathIterable(
            getDirPath(),
            getDepthPolicy(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            optIteratePathComparator);
    }

    /**
     * Depending on the depth policy, returns a new instance of
     * {@link TraversePathDepthFirstIterator} or {@link TraversePathDepthLastIterator}.
     *
     * @return new path iterator
     *
     * @see #getDepthPolicy()
     */
    @Override
    public TraversePathIterator iterator() {
        final TraversePathDepthPolicy depthPolicy = getDepthPolicy();
        return depthPolicy.createTraversePathIterator(
            getDirPath(),
            getExceptionPolicy(),
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
     *     <li>{@link #getExceptionPolicy()}</li>
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
                getExceptionPolicy(),
                getOptionalDescendDirPathFilter(),
                getOptionalDescendDirPathComparator(),
                getOptionalIteratePathFilter(),
                getOptionalIteratePathComparator());
        return result;
    }

    /**
     * Equates by:
     * <ul>
     *     <li>{@link #getDirPath()}</li>
     *     <li>{@link #getDepthPolicy()}</li>
     *     <li>{@link #getExceptionPolicy()}</li>
     *     <li>{@link #getOptionalDescendDirPathFilter()}</li>
     *     <li>{@link #getOptionalDescendDirPathComparator()}</li>
     *     <li>{@link #getOptionalIteratePathFilter()}</li>
     *     <li>{@link #getOptionalIteratePathComparator()}</li>
     * </ul>
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof TraversePathIterable) {
            final TraversePathIterable other = (TraversePathIterable) obj;
            result =
                Objects.equal(this.getDirPath(), other.getDirPath())
                    && Objects.equal(this.getDepthPolicy(), other.getDepthPolicy())
                    && Objects.equal(this.getExceptionPolicy(), other.getExceptionPolicy())
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
