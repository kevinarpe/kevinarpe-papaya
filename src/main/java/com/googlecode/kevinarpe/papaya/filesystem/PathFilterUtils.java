package com.googlecode.kevinarpe.papaya.filesystem;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;

import java.io.File;
import java.util.Collection;

/**
 * Utilities for interface {@link PathFilter}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class PathFilterUtils {

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
     *         if {@code comparatorList} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code comparatorList} is zero
     *
     * @see #allOf(Collection)
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
     *         if {@code comparatorList} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code comparatorList} is zero
     *
     * @see #anyOf(Collection)
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
