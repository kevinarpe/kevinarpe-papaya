package com.googlecode.kevinarpe.papaya.filesystem;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.container.ArrayAsFixedSizeList;
import com.googlecode.kevinarpe.papaya.container.UnmodifiableForwardingList;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PathList
extends UnmodifiableForwardingList<File> {

    public static PathList from(File dirPath)
    throws PathException {
        File[] childPathArr = dirPath.listFiles();
        if (null == childPathArr) {
            if (!dirPath.exists()) {
                String msg = String.format(
                    "Failed to list files for path (does not exist): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathException.PathExceptionReason.PATH_DOES_NOT_EXIST, dirPath, null, msg);
            }
            if (dirPath.isFile()) {
                String msg = String.format(
                    "Failed to list files for path (exists as file, not directory): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(PathException.PathExceptionReason.PATH_IS_FILE, dirPath, null, msg);
            }
            // Exists + Directory...
            if (!dirPath.canExecute()) {
                String msg = String.format(
                    "Failed to list files for path (execute permission not set): '%s'",
                    dirPath.getAbsolutePath());
                throw new PathException(
                    PathException.PathExceptionReason.PATH_IS_NON_EXECUTABLE_DIRECTORY, dirPath, null, msg);
            }
            String msg = String.format(
                "Failed to list files for path (unknown error): '%s'",
                dirPath.getAbsolutePath());
            throw new PathException(PathException.PathExceptionReason.UNKNOWN, dirPath, null, msg);
        }
        return new PathList(dirPath, childPathArr);
    }

    private final File _dirPath;
    private ArrayAsFixedSizeList<File> _childPathArrAsFixedSizeList;

    protected PathList(File dirPath, File[] childPathArr) {
        _dirPath = ObjectArgs.checkNotNull(dirPath, "dirPath");
        ArrayArgs.checkElementsNotNull(childPathArr, "childPathArr");

        _childPathArrAsFixedSizeList = ArrayAsFixedSizeList.referenceTo(childPathArr);
    }

    public PathList(PathList other) {
        ObjectArgs.checkNotNull(other, "other");

        _dirPath = other._dirPath;
        _childPathArrAsFixedSizeList =
            ArrayAsFixedSizeList.copyOf(other._childPathArrAsFixedSizeList.getArrayRef());
    }

    @Override
    protected List<File> delegate() {
        return _childPathArrAsFixedSizeList;
    }

    public File getDirPath() {
        return _dirPath;
    }

    public PathList sortInPlace(List<Comparator<File>> fileComparatorList) {
        CollectionArgs.checkElementsNotNull(fileComparatorList, "fileComparatorList");

        File[] childPathArr = _childPathArrAsFixedSizeList.getArrayRef();
        for (Comparator<File> comp : fileComparatorList) {
            Arrays.sort(childPathArr, comp);
        }
        return this;
    }

    public PathList sortIntoNewList(List<Comparator<File>> fileComparatorList) {
        PathList newList = new PathList(this);
        return newList.sortInPlace(fileComparatorList);
    }

    public PathList filterInPlace(FileFilter fileFilter) {
        ObjectArgs.checkNotNull(fileFilter, "fileFilter");

        File[] newChildPathArr = new File[_childPathArrAsFixedSizeList.size()];
        File[] childPathArr = _childPathArrAsFixedSizeList.getArrayRef();
        final int size = childPathArr.length;
        int newChildPathArrIter = 0;
        for (int childPathArrIter = 0; childPathArrIter < size; ++childPathArrIter) {
            File file = childPathArr[childPathArrIter];
            if (fileFilter.accept(file)) {
                newChildPathArr[newChildPathArrIter] = file;
                ++newChildPathArrIter;
            }
        }
        if (newChildPathArrIter < size) {
            File[] newChildPathArr2 = new File[newChildPathArrIter];
            System.arraycopy(newChildPathArr, 0, newChildPathArr2, 0, newChildPathArr2.length);
            _childPathArrAsFixedSizeList = ArrayAsFixedSizeList.referenceTo(newChildPathArr2);
        }
        // else: we 'accept' 100% of paths via 'fileFilter': array is unchanged
        return this;
    }

    public PathList filterInfoNewList(FileFilter fileFilter) {
        PathList newList = new PathList(this);
        return newList.filterInPlace(fileFilter);
    }
}
