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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.PathArgs;
import com.googlecode.kevinarpe.papaya.container.Collections3;
import com.googlecode.kevinarpe.papaya.container.ICollections3;
import com.googlecode.kevinarpe.papaya.container.builder.ImmutableListFactory;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;

import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@Immutable
@FullyTested
final class DirectoryListingImpl
implements DirectoryListing {

    private static final ImmutableListFactory<File> IMMUTABLE_LIST_BUILDER_FACTORY =
        ImmutableListFactory.create();
    private final File _dirPath;
    private final ImmutableList<File> _childPathList;
    private final ICollections3 _collections3;

    public DirectoryListingImpl(File dirPath)
    throws PathException {
        this(
            PathArgs.checkDirectoryExists(dirPath, "dirPath"),
            _createChildPathList(dirPath),
            Collections3.INSTANCE);
    }

    private static ImmutableList<File> _createChildPathList(File dirPath)
    throws PathException {
        File[] childPathArr = dirPath.listFiles();
        if (null != childPathArr) {
            ImmutableList<File> x = ImmutableList.copyOf(childPathArr);
            return x;
        }
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

    DirectoryListingImpl(File dirPath, ImmutableList<File> childPathList, ICollections3 collections3) {
        _dirPath = dirPath;
        _childPathList = childPathList;
        _collections3 = collections3;
    }

    /** {@inheritDoc} */
    @Override
    public File getDirPath() {
        return _dirPath;
    }

    /** {@inheritDoc} */
    @Override
    public ImmutableList<File> getChildPathList() {
        return _childPathList;
    }

    /** {@inheritDoc} */
    @Override
    public DirectoryListing sort(Comparator<File> fileComparator) {
        ObjectArgs.checkNotNull(fileComparator, "fileComparator");

        ArrayList<File> childPathList = Lists.newArrayList(_childPathList);
        ImmutableList<File> sortedChildPathList =
            _collections3.sort(
                childPathList, fileComparator, IMMUTABLE_LIST_BUILDER_FACTORY);
        ImmutableList<File> childPathImmutableList = ImmutableList.copyOf(childPathList);
        DirectoryListingImpl x =
            new DirectoryListingImpl(_dirPath, childPathImmutableList, _collections3);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public DirectoryListing filter(FileFilter fileFilter) {
        ObjectArgs.checkNotNull(fileFilter, "fileFilter");

        ArrayList<File> childPathList = Lists.newArrayList(_childPathList);
        for (Iterator<File> iter = childPathList.iterator(); iter.hasNext(); ) {
            File path = iter.next();
            if (!fileFilter.accept(path)) {
                iter.remove();
            }
        }
        ImmutableList<File> childPathImmutableList = ImmutableList.copyOf(childPathList);
        DirectoryListingImpl x =
            new DirectoryListingImpl(_dirPath, childPathImmutableList, _collections3);
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<File> iterator() {
        Iterator<File> x = _childPathList.iterator();
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = Objects.hashCode(_dirPath, _childPathList);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        // Ref: http://stackoverflow.com/a/5039178/257299
        boolean result = (this == obj);
        if (!result && obj instanceof DirectoryListing) {
            final DirectoryListingImpl other = (DirectoryListingImpl) obj;
            result = Objects.equal(this._dirPath, other._dirPath)
                && Objects.equal(this._childPathList, other._childPathList);
        }
        return result;
    }

    // TODO: Add toString for this class and all of filesystem package
}
