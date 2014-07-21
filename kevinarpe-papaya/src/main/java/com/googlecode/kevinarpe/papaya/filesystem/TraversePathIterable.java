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

import com.googlecode.kevinarpe.papaya.compare.ComparatorUtils;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilterUtils;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface TraversePathIterable
extends TraversePathIterSettings, Iterable<File> {

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
     * @see #withRootDirPath()
     */
    TraversePathIterable withRootDirPath(File rootDirPath);

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
     * @see #withDepthPolicy()
     */
    TraversePathIterable withDepthPolicy(TraversePathDepthPolicy depthPolicy);

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the exception policy.  The
     * default value is {@link TraversePathIterableUtils#DEFAULT_EXCEPTION_POLICY}.
     *
     * @param exceptionPolicy
     *        how to handle exceptions thrown during directory listings.  Must not be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @throws NullPointerException
     *         if {@code exceptionPolicy} is {@code null}
     *
     * @see TraversePathIterableUtils#DEFAULT_EXCEPTION_POLICY
     * @see #withExceptionPolicy()
     */
    TraversePathIterable withExceptionPolicy(TraversePathExceptionPolicy exceptionPolicy);

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional descend directory
     * path filter.  This attribute filters directories before traversal, <i>including</i> the root
     * directory: {@link #withRootDirPath()}.  Use the second parameter, {@code depth}, in method
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
     * {@link #withIteratePathFilter(PathFilter)}.
     *
     * @param optDescendDirPathFilter
     *        path filter for descend directories.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #withDescendDirPathFilter()
     */
    TraversePathIterable withDescendDirPathFilter(PathFilter optDescendDirPathFilter);

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional descend directory
     * comparator.  This attribute sorts directories before traversal.  If {@code null}, directories
     * are <i>not</i> sorted before traversal.  To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
     * <p>
     * To control the order paths are iterated, see
     * {@link #withIteratePathComparator(Comparator)}.
     *
     * @param optDescendDirPathComparator
     *        comparator to sort descend directories.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #withDescendDirPathComparator()
     */
    TraversePathIterable withDescendDirPathComparator(
        Comparator<File> optDescendDirPathComparator);

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional iterated paths
     * filter.  This attribute filters paths before iteration, <i>including</i> the root directory:
     * {@link #withRootDirPath()}.  Use the second parameter, {@code depth}, in method
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
     * {@link #withIteratePathFilter(PathFilter)}.
     * To control how directories are paths are traversed, see
     * {@link #withDescendDirPathFilter(PathFilter)}.
     *
     * @param optIteratePathFilter
     *        path filter for iterated paths.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #withIteratePathFilter()
     */
    TraversePathIterable withIteratePathFilter(PathFilter optIteratePathFilter);

    /**
     * Constructs a <b>new</b> iterable from the current, replacing the optional iterated paths
     * comparator.  This attribute sorts paths before iteration.  If {@code null}, paths are
     * <i>not</i> sorted before iteration.  To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
     * <p>
     * To control the order directories are traversed, see
     * {@link #withDescendDirPathComparator(Comparator)}.
     *
     * @param optIteratePathComparator
     *        comparator to sort iterated paths.  May be {@code null}.
     *
     * @return <b>new</b> iterable
     *
     * @see #withIteratePathComparator()
     */
    TraversePathIterable withIteratePathComparator(
        Comparator<File> optIteratePathComparator);

    /**
     * Depending on the depth policy, returns a new instance of
     * {@link TraversePathDepthFirstIterator} or {@link TraversePathDepthLastIterator}.
     *
     * @return new path iterator
     *
     * @see #withDepthPolicy()
     */
    TraversePathIterator iterator();

    /**
     * Returns hash code of all attributes.  These are:
     * <ul>
     *     <li>{@link #withRootDirPath()}</li>
     *     <li>{@link #withDepthPolicy()}</li>
     *     <li>{@link #withExceptionPolicy()}</li>
     *     <li>{@link #withDescendDirPathFilter()}</li>
     *     <li>{@link #withDescendDirPathComparator()}</li>
     *     <li>{@link #withIteratePathFilter()}</li>
     *     <li>{@link #withIteratePathComparator()}</li>
     * </ul>
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    int hashCode();

    /**
     * Equates by all attributes.  These are:
     * <ul>
     *     <li>{@link #withRootDirPath()}</li>
     *     <li>{@link #withDepthPolicy()}</li>
     *     <li>{@link #withExceptionPolicy()}</li>
     *     <li>{@link #withDescendDirPathFilter()}</li>
     *     <li>{@link #withDescendDirPathComparator()}</li>
     *     <li>{@link #withIteratePathFilter()}</li>
     *     <li>{@link #withIteratePathComparator()}</li>
     * </ul>
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);
}
