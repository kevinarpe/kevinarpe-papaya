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
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.compare.ComparatorUtils;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileAbsolutePathLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileLastModifiedOldestToNewestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameLexicographicalComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileNameNumericPrefixSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileSizeSmallestToLargestComparator;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileTypeComparator;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * Replaces {@link File#listFiles()}.  Provides ability to filter and sort the listing, similar
 * to the UNIX command line tool {@code /bin/ls}.
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
 */
@FullyTested
public final class DirectoryListing {

    /**
     * Default {@link List} class for constructor {@link #DirectoryListing(File)}:
     * {@link ArrayList}.
     *
     * @see #DirectoryListing(DirectoryListing, Class)
     */
    public static final Class<? extends List> DEFAULT_LIST_CLASS = ArrayList.class;

    private final File _dirPath;
    private List<File> _childPathList;

    /**
     * This is a convenience constructor for {@link #DirectoryListing(File, Class)}
     * where {@code listClass} is {@link #DEFAULT_LIST_CLASS}.
     */
    public DirectoryListing(File dirPath)
    throws PathException {
        this(dirPath, DEFAULT_LIST_CLASS);
    }

    /**
     * Builds a list of child paths for a directory.  Access the list of child paths via
     * {@link #getChildPathList()}.
     *
     * @param dirPath
     *        path to directory used to obtain a listing of child paths
     * @param listClass
     *        controls the {@link List} class used internally.  If filtering a large list of paths,
     *        it will be more efficient to use {@code LinkedList.class}.
     *
     * @throws NullPointerException
     *         if {@code path} or {@code listClass} is {@code null}
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *     if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_NORMAL_FILE}
     *     if {@code path} exists, but is not a directory</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_NON_EXECUTABLE_DIRECTORY}
     *     if {@code path} exists as a directory, but is not accessible for listing</li>
     *     <li>with reason {@link PathExceptionReason#UNKNOWN}
     *     if reason for error is unknown</li>
     * </ul>
     * @throws IllegalArgumentException
     *         if calling method {@link Class#newInstance()} fails for {@code listClass}
     *
     * @see #DirectoryListing(File)
     * @see #DEFAULT_LIST_CLASS
     * @see #getDirPath()
     * @see #getChildPathList()
     */
    public DirectoryListing(File dirPath, Class<? extends List> listClass)
    throws PathException {
        _dirPath = PathArgs.checkDirectoryExists(dirPath, "dirPath");
        ObjectArgs.checkNotNull(listClass, "listClass");

        File[] childPathArr = dirPath.listFiles();
        if (null == childPathArr) {
            if (!dirPath.exists()) {
                String msg = String.format(
                    "Failed to list files for path (does not exist): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PATH_DOES_NOT_EXIST, dirPath, null, msg);
            }
            if (dirPath.isFile()) {
                String msg = String.format(
                    "Failed to list files for path (exists as file, not directory): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PATH_IS_NORMAL_FILE, dirPath, null, msg);
            }
            // Exists + Directory...
            if (!dirPath.canExecute()) {
                String msg = String.format(
                    "Failed to list files for path (execute permission not set): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY,
                    dirPath, null, msg);
            }
            String msg = String.format(
                "Failed to list files for path (unknown error): '%s'",
                dirPath.getAbsolutePath());
            throw new PathException(PathExceptionReason.UNKNOWN, dirPath, null, msg);
        }

        _childPathList = _newInstance(listClass);
        if (0 != childPathArr.length) {
            _childPathList.addAll(Arrays.asList(childPathArr));
        }
    }

    /**
     * This is a convenience constructor for {@link #DirectoryListing(DirectoryListing, Class)}
     * where {@code listClass} is {@code getChildPathList().getClass()}.
     */
    public DirectoryListing(DirectoryListing other) {
        this(
            other,
            (null == other ? null : other._childPathList.getClass()),
            123);
    }

    /**
     * Copies another instance of this class including the {@link List} class used internally to
     * store child paths.
     *
     * @param other
     *        another instance of this class
     * @param listClass
     *        controls the {@link List} class used internally.  If filtering a large list of paths,
     *        it will be more efficient to use {@code LinkedList.class}.
     *
     * @throws NullPointerException
     *         if {@code other} or {@code listClass} is {@code null}
     * @throws IllegalArgumentException
     *         if calling method {@link Class#newInstance()} fails for {@code listClass}
     */
    public DirectoryListing(DirectoryListing other, Class<? extends List> listClass) {
        this(
            other,
            ObjectArgs.checkNotNull(listClass, "listClass"),
            123);
    }

    /**
     * Param {@code dummy} only exists here to distinguish this constructor from
     * {@link #DirectoryListing(DirectoryListing, Class)}.
     */
    private DirectoryListing(DirectoryListing other, Class<? extends List> listClass, int dummy) {
        ObjectArgs.checkNotNull(other, "other");

        _dirPath = other._dirPath;
        _childPathList = _newInstance(listClass);
        _childPathList.addAll(other._childPathList);
    }

    private static List<File> _newInstance(Class<? extends List> listClass) {
        try {
            @SuppressWarnings("unchecked")
            List<File> list = listClass.newInstance();
            return list;
        }
        catch (Exception e) {
            String msg = String.format(
                "Failed to create new instance of list class %s", listClass.getName());
            throw new IllegalArgumentException(msg, e);
        }
    }

    /**
     * @return parent directory for child paths
     *
     * @see #getChildPathList()
     */
    public File getDirPath() {
        return _dirPath;
    }

    /**
     * @return reference to the internal list of child paths.  This is not a copy, so changes made
     *         to the list will affect the internal state of this instance.
     */
    public List<File> getChildPathList() {
        return _childPathList;
    }

    /**
     * This is a convenience method for {@link #sort(List)}.
     *
     * @throws NullPointerException
     *         if {@code fileComparator} is {@code null}
     */
    /**
     * Performs an in-place sort on the list of child paths using a {@link Comparator}.  This method
     * operates directly on the internal list.  To preserve the current instance, use the copy
     * constructor first, then sort.
     * <p>
     * To combine more than one comparator, consider using
     * {@link ComparatorUtils#chain(Collection)}.
     *
     * @param fileComparator
     *        how to sort paths.  Must not be {@code null}.  See {@link DirectoryListing class docs}
     *        for a information about file comparators included in this library.
     *
     * @return reference to {@code this}
     *
     * @throws NullPointerException
     *         if {@code fileComparator} is {@code null}
     *
     * @see #DirectoryListing(DirectoryListing)
     * @see #DirectoryListing(DirectoryListing, Class)
     * @see #filter(FileFilter)
     */
    public DirectoryListing sort(Comparator<File> fileComparator) {
        ObjectArgs.checkNotNull(fileComparator, "fileComparator");

        Collections.sort(_childPathList, fileComparator);
        return this;
    }

    /**
     * Applies a filter to remove paths from the to the internal list of child paths.  This method
     * operates directly on the internal list.  To preserve the current instance, use a copy
     * constructor first, then sort.
     * <p>
     * To combine more than one file filter, consider using
     * {@link FileFilterUtils#anyOf(Collection)} or {@link FileFilterUtils#allOf(Collection)}.
     * <p>
     * As an optimisation, this method uses different strategies for list element removal
     * depending upon if the {@link List} class used for internal storage implements interface
     * {@link RandomAccess}.  For example, list class {@link ArrayList} implements interface
     * {@code RandomAccess}, but list class {@link LinkedList} does not.
     *
     * @param fileFilter
     *        how to filter paths.  Must not be {@code null}.
     *
     * @return reference to {@code this}
     *
     * @throws NullPointerException
     *         if {@code fileFilter} is {@code null}
     *
     * @see #DirectoryListing(DirectoryListing)
     * @see #DirectoryListing(DirectoryListing, Class)
     * @see #sort(Comparator)
     */
    public DirectoryListing filter(FileFilter fileFilter) {
        ObjectArgs.checkNotNull(fileFilter, "fileFilter");

        if (_childPathList instanceof RandomAccess) {
            _childPathList = _filterRandomAccessList(_childPathList, fileFilter);
        }
        else {  // instanceof SequentialAccess or LinkedList
            _filterSequentialAccessList(_childPathList, fileFilter);
        }

        return this;
    }

    private static List<File> _filterRandomAccessList(
            List<File> childPathList, FileFilter fileFilter) {
        List<File> newChildPathList = _newInstance(childPathList.getClass());
        for (File childPath : childPathList) {
            if (fileFilter.accept(childPath)) {
                newChildPathList.add(childPath);
            }
        }
        if (newChildPathList.size() == childPathList.size()) {
            return childPathList;
        }
        return newChildPathList;
    }

    private static void _filterSequentialAccessList(
            List<File> childPathList, FileFilter fileFilter) {
        ListIterator<File> iter = childPathList.listIterator();
        while (iter.hasNext()) {
            File childPath = iter.next();
            if (!fileFilter.accept(childPath)) {
                iter.remove();
            }
        }
    }

    /**
     * Returns hash code of {@link #getDirPath()}, {@link #getChildPathList()}, and the {@link List}
     * class used.  This will differentiate between two instances of this class using a different
     * internal storage {@code List} class.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hashCode(_dirPath, _childPathList);
        result = 31 * result + _childPathList.getClass().hashCode();
        return result;
    }

    /**
     * Equates by {@link #getDirPath()}, {@link #getChildPathList()}, and the {@link List}
     * class used.  This will differentiate between two instances of this class using a different
     * internal storage {@code List} class.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof DirectoryListing) {
            final DirectoryListing other = (DirectoryListing) obj;
            result = Objects.equal(this._dirPath, other._dirPath)
                && Objects.equal(
                    (null == this._childPathList ? null : this._childPathList.getClass()),
                    (null == other._childPathList ? null : other._childPathList.getClass()))
                && Objects.equal(this._childPathList, other._childPathList);
        }
        return result;
    }

    @Override
    public String toString() {
        return null;
    }
}
