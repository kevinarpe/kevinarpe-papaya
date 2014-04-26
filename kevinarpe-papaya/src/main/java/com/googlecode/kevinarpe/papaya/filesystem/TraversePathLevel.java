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
import com.googlecode.kevinarpe.papaya.argument.IntArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class TraversePathLevel {

    static interface Factory {

        DirectoryListing newDirectoryListingInstance(File dirPath, Class<? extends List> listClass)
        throws PathException;

        DirectoryListing newDirectoryListingInstance(DirectoryListing other);

        DescendDirFileFilter newDescendDirFileFilterInstance(PathFilter pathFilter, int depth);

        IterateFileFilter newIterateFileFilterInstance(PathFilter pathFilter, int depth);
    }

    static final class FactoryImpl
    implements Factory {

        static final FactoryImpl INSTANCE = new FactoryImpl();

        @Override
        public DirectoryListing newDirectoryListingInstance(
                File dirPath, Class<? extends List> listClass)
        throws PathException {
            return new DirectoryListing(dirPath, listClass);
        }

        @Override
        public DirectoryListing newDirectoryListingInstance(DirectoryListing other) {
            return new DirectoryListing(other);
        }

        @Override
        public DescendDirFileFilter newDescendDirFileFilterInstance(
                PathFilter pathFilter, int depth) {
            return new DescendDirFileFilter(pathFilter, depth);
        }

        @Override
        public IterateFileFilter newIterateFileFilterInstance(PathFilter pathFilter, int depth) {
            return new IterateFileFilter(pathFilter, depth);
        }
    }

    static final Class<? extends List> DEFAULT_DIRECTORY_LISTING_LIST_CLASS = LinkedList.class;

    private final Factory _factory;
    private final AbstractTraversePathIteratorImpl _parent;
    private final int _depth;
    private final DirectoryListing _origDirectoryListing;
    private DirectoryListing _descendDirDirectoryListing;
    private Iterator<File> _descendDirDirectoryListingIter;
    private DirectoryListing _iterateDirectoryListing;
    private Iterator<File> _iterateDirectoryListingIter;

    TraversePathLevel(AbstractTraversePathIteratorImpl parent, File dirPath, int depth)
    throws PathException {
        this(parent, FactoryImpl.INSTANCE, dirPath, depth);
    }

    TraversePathLevel(
            AbstractTraversePathIteratorImpl parent,
            Factory factory,
            File dirPath,
            int depth)
    throws PathException {
        _parent = ObjectArgs.checkNotNull(parent, "parent");
        _factory = ObjectArgs.checkNotNull(factory, "factory");
        _origDirectoryListing =
            _factory.newDirectoryListingInstance(dirPath, DEFAULT_DIRECTORY_LISTING_LIST_CLASS);
        _depth = IntArgs.checkPositive(depth, "getDepth");
    }

    static final class DescendDirFileFilter
    implements FileFilter {

        private final PathFilter _pathFilter;
        private final int _depth;

        DescendDirFileFilter(PathFilter pathFilter, int depth) {
            _pathFilter = pathFilter;
            _depth = depth;
        }

        @Override
        public boolean accept(File path) {
            if (!path.isDirectory()) {
                return false;
            }
            if (null == _pathFilter) {
                return true;
            }
            boolean result = _pathFilter.accept(path, _depth);
            return result;
        }
    }

    public DirectoryListing getDescendDirDirectoryListing() {
        if (null == _descendDirDirectoryListing) {
            DirectoryListing newDirListing =
                _factory.newDirectoryListingInstance(_origDirectoryListing);
            PathFilter descendDirPathFilter = _parent.getOptionalDescendDirPathFilter();
            DescendDirFileFilter fileFilter =
                _factory.newDescendDirFileFilterInstance(descendDirPathFilter, _depth);
            newDirListing.filter(fileFilter);
            sortDirListing(newDirListing, _parent.getOptionalDescendDirPathComparator());
            _descendDirDirectoryListing = newDirListing;
        }
        return _descendDirDirectoryListing;
    }

    private void sortDirListing(DirectoryListing dirListing, Comparator<File> optPathComparator) {
        if (null != optPathComparator) {
            dirListing.sort(optPathComparator);
        }
    }

    public Iterator<File> getDescendDirDirectoryListingIter() {
        if (null == _descendDirDirectoryListingIter) {
            DirectoryListing descendDirDirectoryListing = getDescendDirDirectoryListing();
            List<File> childPathList = descendDirDirectoryListing.getChildPathList();
            _descendDirDirectoryListingIter = childPathList.iterator();
        }
        return _descendDirDirectoryListingIter;
    }

    static final class IterateFileFilter
    implements FileFilter {

        private final PathFilter _pathFilter;
        private final int _depth;

        IterateFileFilter(PathFilter pathFilter, int depth) {
            _pathFilter = pathFilter;
            _depth = depth;
        }

        @Override
        public boolean accept(File path) {
            boolean result = true;
            if (null != _pathFilter) {
                result = _pathFilter.accept(path, _depth);
            }
            return result;
        }
    }

    public DirectoryListing getIterateDirectoryListing() {
        if (null == _iterateDirectoryListing) {
            PathFilter pathFilter = _parent.getOptionalIteratePathFilter();
            DirectoryListing newDirListing =
                _factory.newDirectoryListingInstance(_origDirectoryListing);
            if (null != pathFilter) {
                IterateFileFilter fileFilter =
                    _factory.newIterateFileFilterInstance(pathFilter, _depth);
                newDirListing.filter(fileFilter);
            }
            sortDirListing(newDirListing, _parent.getOptionalIteratePathComparator());
            _iterateDirectoryListing = newDirListing;
        }
        return _iterateDirectoryListing;
    }

    public Iterator<File> getIterateDirectoryListingIter() {
        if (null == _iterateDirectoryListingIter) {
            DirectoryListing iterateDirectoryListing = getIterateDirectoryListing();
            List<File> childPathList = iterateDirectoryListing.getChildPathList();
            _iterateDirectoryListingIter = childPathList.iterator();
        }
        return _iterateDirectoryListingIter;
    }
}
