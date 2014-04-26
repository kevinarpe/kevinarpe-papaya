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
import com.googlecode.kevinarpe.papaya.compare.ComparatorUtils;

import java.io.File;
import java.util.Collection;
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

    /**
     * Used by the constructor to set the default exception policy for path iterators.  The value
     * is {@link TraversePathExceptionPolicy#THROW}.
     *
     * @see #TraversePathIterableImpl(File, TraversePathDepthPolicy)
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
     * @see #withRootDirPath(File)
     * @see #withDepthPolicy(TraversePathDepthPolicy)
     * @see #withExceptionPolicy(TraversePathExceptionPolicy)
     * @see #DEFAULT_EXCEPTION_POLICY
     * @see #withOptionalDescendDirPathFilter(PathFilter)
     * @see #withOptionalDescendDirPathComparator(Comparator)
     * @see #withOptionalIteratePathFilter(PathFilter)
     * @see #withOptionalIteratePathComparator(Comparator)
     */
    public TraversePathIterableImpl(File dirPath, TraversePathDepthPolicy depthPolicy) {
        super(
            ObjectArgs.checkNotNull(dirPath, "dirPath"),
            ObjectArgs.checkNotNull(depthPolicy, "depthPolicy"),
            DEFAULT_EXCEPTION_POLICY,
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

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the root directory to traverse.
     * If the depth policy is {@link TraversePathDepthPolicy#DEPTH_FIRST}, this directory will be
     * the <i>last</i> path considered for iteration (depending upon the filter).  The vice versa
     * also holds true for {@link TraversePathDepthPolicy#DEPTH_LAST}: this directory will be the
     * <i>first</i> path considered for iteration (depending upon the filter).
     *
     * @param rootDirPath
     *        root directory to traverse.  Must not be {@code null}, but need not exist.  Directory
     *        trees may be highly ephemeral, so the existance of this directory at the time of
     *        construction is not required.
     *
     * @return <b>new</b> iterable
     *
     * @throws NullPointerException
     *         if {@code rootDirPath} is {@code null}
     *
     * @see #getRootDirPath()
     */
    @Override
    public TraversePathIterable withRootDirPath(File rootDirPath) {
        return new TraversePathIterableImpl(
            ObjectArgs.checkNotNull(rootDirPath, "rootDirPath"),
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
    @Override
    public TraversePathIterableImpl withDepthPolicy(TraversePathDepthPolicy depthPolicy) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
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
    @Override
    public TraversePathIterable withExceptionPolicy(TraversePathExceptionPolicy exceptionPolicy) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
            getDepthPolicy(),
            ObjectArgs.checkNotNull(exceptionPolicy, "exceptionPolicy"),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional descend directory
     * path filter.  This attribute filters directories before traversal, <i>including</i> the root
     * directory: {@link #getRootDirPath()}.  Use the second parameter, {@code depth}, in method
     * {@link PathFilter#accept(File, int)} to easily control this case.
     * <p>
     * Example:
     * <pre><code>
     * new PathFilter() {
     *     &#64;Override
     *     public boolean accept(File path, int depth) {
     *         if (0 == depth) {
     *             return true;  // always accept the root directory
     *         }
     *         // else, apply different logic
     *     }
     * }
     * </code></pre>
     * <p>
     * If the path filter is {@code null}, then <i>all</i> directories are traversed.  To combine
     * more than one path filter, consider using {@link PathFilterUtils#anyOf(Collection)} or
     * {@link PathFilterUtils#allOf(Collection)}.  To control which paths are iterated, see
     * {@link #withOptionalIteratePathFilter(PathFilter)}.
     *
     * @param optDescendDirPathFilter
     *        path filter for descend directories.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #getOptionalDescendDirPathFilter()
     */
    @Override
    public TraversePathIterableImpl withOptionalDescendDirPathFilter(
        PathFilter optDescendDirPathFilter) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
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
     * are <i>not</i> sorted before traversal.  To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
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
    @Override
    public TraversePathIterable withOptionalDescendDirPathComparator(
        Comparator<File> optDescendDirPathComparator) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
            getDepthPolicy(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            optDescendDirPathComparator,
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional iterated paths
     * filter.  This attribute filters paths before iteration, <i>including</i> the root directory:
     * {@link #getRootDirPath()}.  Use the second parameter, {@code depth}, in method
     * {@link PathFilter#accept(File, int)} to easily control this case.
     * <p>
     * Example:
     * <pre><code>
     * new PathFilter() {
     *     &#64;Override
     *     public boolean accept(File path, int depth) {
     *         if (0 == depth) {
     *             return true;  // always accept the root directory
     *         }
     *         // else, apply different logic
     *     }
     * }
     * </code></pre>
     * <p>
     * If the path filter is {@code null}, then <i>all</i> paths are iterated, including
     * directories.  To combine more than one path filter, consider using
     * {@link PathFilterUtils#anyOf(Collection)} or {@link PathFilterUtils#allOf(Collection)}.
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
    @Override
    public TraversePathIterable withOptionalIteratePathFilter(PathFilter optIteratePathFilter) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
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
     * <i>not</i> sorted before iteration.  To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
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
    @Override
    public TraversePathIterable withOptionalIteratePathComparator(
        Comparator<File> optIteratePathComparator) {
        return new TraversePathIterableImpl(
            getRootDirPath(),
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
            getRootDirPath(),
            getExceptionPolicy(),
            getOptionalDescendDirPathFilter(),
            getOptionalDescendDirPathComparator(),
            getOptionalIteratePathFilter(),
            getOptionalIteratePathComparator());
    }

    /**
     * Returns hash code of all attributes.  These are:
     * <ul>
     *     <li>{@link #getRootDirPath()}</li>
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
                getRootDirPath(),
                getDepthPolicy(),
                getExceptionPolicy(),
                getOptionalDescendDirPathFilter(),
                getOptionalDescendDirPathComparator(),
                getOptionalIteratePathFilter(),
                getOptionalIteratePathComparator());
        return result;
    }

    /**
     * Equates by all attributes.  These are:
     * <ul>
     *     <li>{@link #getRootDirPath()}</li>
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
        if (!result && obj instanceof TraversePathIterableImpl) {
            final TraversePathIterableImpl other = (TraversePathIterableImpl) obj;
            result =
                Objects.equal(this.getRootDirPath(), other.getRootDirPath())
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
