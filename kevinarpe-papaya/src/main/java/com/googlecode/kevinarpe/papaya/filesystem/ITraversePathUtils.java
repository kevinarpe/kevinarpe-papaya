package com.googlecode.kevinarpe.papaya.filesystem;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import java.io.File;
import java.util.Comparator;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link TraversePathUtils} or {@link TraversePathUtils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TraversePathUtils
 */
public interface ITraversePathUtils {

    /**
     * Constructs an iterable with its required attributes and none of its optional attributes:
     * <ul>
     *     <li>descend directory path filter</li>
     *     <li>descend directory comparator</li>
     *     <li>iterate path filter</li>
     *     <li>iterate comparator</li>
     * </ul>
     * <p>
     * The exception policy is set to the default:
     * {@link TraversePathUtils#DEFAULT_EXCEPTION_POLICY}.  It can be changed via
     * {@link TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)}.
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
     * @see TraversePathIterable#withRootDirPath(File)
     * @see TraversePathIterable#withDepthPolicy(TraversePathDepthPolicy)
     * @see TraversePathIterable#withExceptionPolicy(TraversePathExceptionPolicy)
     * @see TraversePathUtils#DEFAULT_EXCEPTION_POLICY
     * @see TraversePathIterable#withOptionalDescendDirPathFilter(PathFilter)
     * @see TraversePathIterable#withOptionalDescendDirPathComparator(Comparator)
     * @see TraversePathIterable#withOptionalIteratePathFilter(PathFilter)
     * @see TraversePathIterable#withOptionalIteratePathComparator(Comparator)
     * @see #getTraversePathIterableFactory()
     */
    TraversePathIterable newTraversePathIterable(File dirPath, TraversePathDepthPolicy depthPolicy);

    /**
     * Retrieves the global instance that implements interface {@link TraversePathIterableFactory}.
     *
     * @return global instance
     *
     * @see #newTraversePathIterable(File, TraversePathDepthPolicy)
     */
    TraversePathIterableFactory getTraversePathIterableFactory();
}
