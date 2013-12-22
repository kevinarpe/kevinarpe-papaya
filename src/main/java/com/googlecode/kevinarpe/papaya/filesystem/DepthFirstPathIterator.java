package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DepthFirstPathIterator
extends UnmodifiableIterator {

//    private static final FileFilter ALL_FILE_FILTER = new FileFilter() {
//
//        @Override
//        public boolean accept(File pathname) {
//            return true;
//        }
//    };

    private static final PathFilter DIR_ONLY_PATH_FILTER = new PathFilter() {

        @Override
        public boolean accept(File path, int depth) {
            return path.isDirectory();
        }
    };

    // TODO: Create builder with options: List<Comparator<File>> fileComparatorList

    // TODO: Need two policy enums
    // (1) depth-first vs depth-last
    // (2) file-first vs dir-first

    // TODO: What about ArrayAsIterable, ArrayIterator, ArrayUnmodifiableIterator

//    private final File _dirPath;
    private final PathFilter _descendDirFilter;
    private final List<Comparator<File>> _descendFileComparatorList;
    private final PathFilter _optAscendFilter;
    private final List<Comparator<File>> _ascendFileComparatorList;
    private final List<_PathListData> _pathListDataList;
    private _PathListData _currentPathListData;

    private static class _PathListData {

        public final PathList descendDirPathList;
        public final PathList ascendPathList;
        public final Iterator<File> descendDirPathListIter;
        public final Iterator<File> ascendPathListIter;

        private _PathListData(PathList descendDirPathList, PathList ascendPathList) {
            this.descendDirPathList =
                ObjectArgs.checkNotNull(descendDirPathList, "descendDirPathList");
            this.ascendPathList =
                ObjectArgs.checkNotNull(ascendPathList, "ascendPathList");
            this.descendDirPathListIter = descendDirPathList.iterator();
            this.ascendPathListIter = ascendPathList.iterator();
            // TODO: Find a way to delay creating the ascendPathListIter.
            // Also, can we delay creating the ascendPathList (including filter + sort)?
        }
    }

    public DepthFirstPathIterator(
            File dirPath,
            final PathFilter optDescendDirFilter,
            List<Comparator<File>> descendFileComparatorList,
            PathFilter optAscendFilter,
            List<Comparator<File>> ascendFileComparatorList)
    throws IOException {
//        _dirPath = PathArgs.checkDirectoryExists(dirPath, "dirPath");

        if (null == optDescendDirFilter) {
            _descendDirFilter = DIR_ONLY_PATH_FILTER;
        }
        else {
            _descendDirFilter = new PathFilter() {

                @Override
                public boolean accept(File path, int depth) {
                    if (!path.isDirectory()) {
                        return false;
                    }
                    return optDescendDirFilter.accept(path, depth);
                }
            };
        }
        _descendFileComparatorList =
            CollectionArgs.checkElementsNotNull(
                descendFileComparatorList, "descendFileComparatorList");

        _optAscendFilter = optAscendFilter;

        _ascendFileComparatorList =
            CollectionArgs.checkElementsNotNull(
                ascendFileComparatorList, "ascendFileComparatorList");

        _pathListDataList = Lists.newLinkedList();

        PathList childPathList = PathList.from(dirPath);

        final int depth = 0;  // _pathListDataList.size();
        PathList descendDirPathList = childPathList.filterInfoNewList(new FileFilter() {
            @Override
            public boolean accept(File path) {
                return _descendDirFilter.accept(path, depth);
            }
        });
        descendDirPathList.sortInPlace(_descendFileComparatorList);

        PathList ascendDirPathList = childPathList;
        if (null != _optAscendFilter) {
            childPathList.filterInPlace(new FileFilter() {
                @Override
                public boolean accept(File path) {
                    return _optAscendFilter.accept(path, depth);
                }
            });
        }
        ascendDirPathList.sortInPlace(_ascendFileComparatorList);

        _currentPathListData = new _PathListData(descendDirPathList, ascendDirPathList);
        _pathListDataList.add(_currentPathListData);
    }

    @Override
    public boolean hasNext() {
        boolean b = _currentPathListData.ascendPathListIter.hasNext();
        if (b) {
            return true;
        }
//        _currentPathListData.descendDirPathListIter.hasNext()
        // LAST
        return false;
    }

    @Override
    public File next() {
        File x = _currentPathListData.ascendPathListIter.next();
        return x;
    }

    //TODO: @Override
    // Maybe add to a new interface... TraversePathIterator?
    public int depth() {
        return _pathListDataList.size() - 1;
    }
}
