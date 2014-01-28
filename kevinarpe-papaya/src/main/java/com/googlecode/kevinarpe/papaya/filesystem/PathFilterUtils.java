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
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import java.io.File;
import java.util.Collection;

/**
 * Utilities for interface {@link PathFilter}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see FileFilterUtils
 */
@FullyTested
public class PathFilterUtils {

    // Disable default constructor
    private PathFilterUtils() {
    }

    /**
     * Creates a new path filter which returns the logical OR of a collection of path filters.
     * A short-circuit algorithm is used, thus not all filters are guaranteed to run.  For example,
     * if the first result is true, none of the remaining filters are run.  This is important if the
     * filters are stateful.
     * <p>
     * As a special case, if the input list has exactly one element, it is returned unchanged.
     * <p>
     * {@code anyOf(Arrays.asList(filter1, filter2, filter3)).accept(path, depth)}
     * <br/>is equivalent to...
     * <br/>{@code filter1.accept(path, depth) || filter2.accept(path, depth) || filter3.accept(path, depth)}
     *
     * @param pathFilterCollection
     *        collection of path filters to combine.  Must not be {@code null}, empty, or contain
     *        {@code null} elements.
     *
     * @return new path filter which returns the logical OR of the input path filters
     *
     * @throws NullPointerException
     *         if {@code pathFilterCollection} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code pathFilterCollection} is zero
     *
     * @see #allOf(Collection)
     * @see FileFilterUtils#anyOf(Collection)
     * @see FileFilterUtils#allOf(Collection)
     */
    public static PathFilter anyOf(final Collection<PathFilter> pathFilterCollection) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            pathFilterCollection, "pathFilterCollection");

        if (1 == pathFilterCollection.size()) {
            for (PathFilter pathFilter : pathFilterCollection) {
                return pathFilter;
            }
        }

        final PathFilter pathFilter =
            new PathFilter() {
                @Override
                public boolean accept(File path, int depth) {
                    for (PathFilter pathFilter : pathFilterCollection) {
                        if (pathFilter.accept(path, depth)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
        return pathFilter;
    }

    /**
     * Creates a new path filter which returns the logical AND of a collection of path filters.
     * A short-circuit algorithm is used, thus not all filters are guaranteed to run.  For example,
     * if the first result is false, none of the remaining filters are run.  This is important if
     * the filters are stateful.
     * <p>
     * As a special case, if the input list has exactly one element, it is returned unchanged.
     * <p>
     * {@code allOf(Arrays.asList(filter1, filter2, filter3)).accept(path, depth)}
     * <br/>is equivalent to...
     * <br/>{@code filter1.accept(path, depth) && filter2.accept(path, depth) && filter3.accept(path, depth)}
     *
     * @param pathFilterCollection
     *        collection of path filters to combine.  Must not be {@code null}, empty, or contain
     *        {@code null} elements.
     *
     * @return new path filter which returns the logical AND of the input path filters
     *
     * @throws NullPointerException
     *         if {@code pathFilterCollection} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code pathFilterCollection} is zero
     *
     * @see #anyOf(Collection)
     * @see FileFilterUtils#anyOf(Collection)
     * @see FileFilterUtils#allOf(Collection)
     */
    public static PathFilter allOf(final Collection<PathFilter> pathFilterCollection) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            pathFilterCollection, "pathFilterCollection");

        if (1 == pathFilterCollection.size()) {
            for (PathFilter pathFilter : pathFilterCollection) {
                return pathFilter;
            }
        }

        final PathFilter pathFilter =
            new PathFilter() {
                @Override
                public boolean accept(File path, int depth) {
                    for (PathFilter pathFilter : pathFilterCollection) {
                        if (!pathFilter.accept(path, depth)) {
                            return false;
                        }
                    }
                    return true;
                }
            };
        return pathFilter;
    }
}
