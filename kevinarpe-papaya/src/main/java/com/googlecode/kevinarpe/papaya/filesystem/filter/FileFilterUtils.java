package com.googlecode.kevinarpe.papaya.filesystem.filter;

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
import com.googlecode.kevinarpe.papaya.filesystem.DirectoryListing;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

/**
 * Utilities for interface {@link FileFilter}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PathFilterUtils
 */
// TODO: Convert to I/*Utils format.
@FullyTested
public class FileFilterUtils {

    // Disable default constructor
    private FileFilterUtils() {
    }

    /**
     * Creates a new file filter which returns the logical OR of a collection of file filters.
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
     * @param fileFilterCollection
     *        collection of file filters to combine.  Must not be {@code null}, empty, or contain
     *        {@code null} elements.
     *
     * @return new file filter which returns the logical OR of the input file filters
     *
     * @throws NullPointerException
     *         if {@code fileFilterCollection} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code fileFilterCollection} is zero
     *
     * @see #allOf(Collection)
     * @see PathFilterUtils#anyOf(Collection)
     * @see PathFilterUtils#allOf(Collection)
     * @see DirectoryListing#filter(FileFilter)
     */
    public static FileFilter anyOf(final Collection<FileFilter> fileFilterCollection) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            fileFilterCollection, "fileFilterCollection");

        if (1 == fileFilterCollection.size()) {
            for (FileFilter fileFilter : fileFilterCollection) {
                return fileFilter;
            }
        }

        final FileFilter pathFilter =
            new FileFilter() {
                @Override
                public boolean accept(File path) {
                    for (FileFilter pathFilter : fileFilterCollection) {
                        if (pathFilter.accept(path)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
        return pathFilter;
    }

    /**
     * Creates a new file filter which returns the logical AND of a collection of file filters.
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
     * @param fileFilterCollection
     *        collection of file filters to combine.  Must not be {@code null}, empty, or contain
     *        {@code null} elements.
     *
     * @return new file filter which returns the logical AND of the input file filters
     *
     * @throws NullPointerException
     *         if {@code fileFilterCollection} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code fileFilterCollection} is zero
     *
     * @see #anyOf(Collection)
     * @see PathFilterUtils#anyOf(Collection)
     * @see PathFilterUtils#allOf(Collection)
     * @see DirectoryListing#filter(FileFilter)
     */
    public static FileFilter allOf(final Collection<FileFilter> fileFilterCollection) {
        CollectionArgs.checkNotEmptyAndElementsNotNull(
            fileFilterCollection, "fileFilterCollection");

        if (1 == fileFilterCollection.size()) {
            for (FileFilter fileFilter : fileFilterCollection) {
                return fileFilter;
            }
        }

        final FileFilter fileFilter =
            new FileFilter() {
                @Override
                public boolean accept(File file) {
                    for (FileFilter fileFilter : fileFilterCollection) {
                        if (!fileFilter.accept(file)) {
                            return false;
                        }
                    }
                    return true;
                }
            };
        return fileFilter;
    }
}
