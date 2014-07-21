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
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileAbsolutePathLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileLastModifiedOldestToNewestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameNumericPrefixSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileSizeSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileTypeComparator;
import com.googlecode.kevinarpe.papaya.filesystem.filter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Creates, sorts, and filters child paths from a parent directory.  This class was built as a
 * replacement for {@link File#listFiles()}.  To instantiate this interface, see
 * {@link DirectoryListingUtils#newInstance(File)}.
 * <p>
 * Numerous {@link Comparator}s for class {@link File} are included in this library, including:
 * <ul>
 *     <li>{@link FileAbsolutePathLexicographicalComparator}</li>
 *     <li>{@link FileLastModifiedOldestToNewestComparator}</li>
 *     <li>{@link FileNameLexicographicalComparator}</li>
 *     <li>{@link FileNameNumericPrefixSmallestToLargestComparator}</li>
 *     <li>{@link FileSizeSmallestToLargestComparator}</li>
 *     <li>{@link FileTypeComparator}</li>
 * </ul>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Iterable
 * @see File
 * @see DirectoryListingFactory
 * @see DirectoryListingUtils
 */
public interface DirectoryListing
extends Iterable<File> {

    /**
     * @return parent directory for child paths
     *
     * @see #getChildPathList()
     */
    File getDirPath();

    /**
     * @return list of child paths
     */
    List<File> getChildPathList();

    /**
     * Constructs a new instance with a sorted list of child paths.
     * <p>
     * To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
     *
     * @param pathComparator
     *        how to sort paths.  Must not be {@code null}.  See {@link DirectoryListing class docs}
     *        for a information about file comparators included in this library.
     *
     * @return new instance with sorted child paths
     *
     * @throws NullPointerException
     *         if {@code pathComparator} is {@code null}
     *
     * @see DirectoryListing#filter(FileFilter)
     */
    DirectoryListing sort(Comparator<File> pathComparator);

    /**
     * Constructs a new instance with a filtered list of child paths.
     * <p>
     * To combine more than one file filter, consider using
     * {@link FileFilterUtils#anyOf(Collection)} or {@link FileFilterUtils#allOf(Collection)}.
     *
     * @param fileFilter
     *        how to filter paths.  Must not be {@code null}.
     *
     * @return reference to {@code this}
     *
     * @throws NullPointerException
     *         if {@code fileFilter} is {@code null}
     *
     * @see DirectoryListing#sort(Comparator)
     */
    DirectoryListing filter(FileFilter fileFilter);

    /**
     * Convenience method to return iterator for child path list.  Implemented to allow instances
     * of this class to be used in foreach loops.
     *
     * <hr/>
     * {@inheritDoc}
     *
     * @see #getChildPathList()
     */
    @Override
    Iterator<File> iterator();

    /**
     * Returns hash code of parent dir path and child path list.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    int hashCode();

    /**
     * Equates by parent dir path and child path list.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);
}
