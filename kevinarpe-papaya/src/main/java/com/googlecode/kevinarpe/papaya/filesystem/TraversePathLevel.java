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
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import javax.annotation.concurrent.Immutable;
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

        DirectoryListing newDirectoryListingInstance(File dirPath)
        throws PathException;

        FileFilter newDescendDirFileFilterInstance(PathFilter pathFilter, int depth);

        FileFilter newIterateFileFilterInstance(PathFilter pathFilter, int depth);
    }

    @Immutable
    static final class FactoryImpl
    extends StatelessObject
    implements Factory {

        static final FactoryImpl INSTANCE = new FactoryImpl();

        @Override
        public DirectoryListingImpl newDirectoryListingInstance(File dirPath)
        throws PathException {
            return new DirectoryListingImpl(dirPath);
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
    // TODO: Need to rethink this package.  Can we write interfaces first?
    private final AbstractTraversePathIteratorImpl _parent;
    private final int _depth;
    private final DirectoryListing _origDirListing;
    private DirectoryListing _descendDirListing;
    private Iterator<File> _descendDirListingIter;
    private DirectoryListing _iterateDirListing;
    private Iterator<File> _iterateDirListingIter;

    TraversePathLevel(AbstractTraversePathIteratorImpl parent, File dirPath, int depth)
    throws PathException {
        this(parent, FactoryImpl.INSTANCE, dirPath, depth);
    }

    TraversePathLevel(
            AbstractTraversePathIteratorImpl parent, Factory factory, File dirPath, int depth)
    throws PathException {
        _parent = ObjectArgs.checkNotNull(parent, "parent");
        _factory = ObjectArgs.checkNotNull(factory, "factory");
        _origDirListing = _factory.newDirectoryListingInstance(dirPath);
        _depth = IntArgs.checkPositive(depth, "getDepth");
    }

    @Immutable
    static final class DescendDirFileFilter
    extends StatelessObject
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

    DirectoryListing getDescendDirListing() {
        if (null == _descendDirListing) {
            PathFilter descendDirPathFilter = _parent.withDescendDirPathFilter();
            FileFilter fileFilter =
                _factory.newDescendDirFileFilterInstance(descendDirPathFilter, _depth);
            DirectoryListing newDirListing = _origDirListing.filter(fileFilter);
            Comparator<File> pathComparator = _parent.withDescendDirPathComparator();
            _descendDirListing = newDirListing.sort(pathComparator);
            _descendDirListingIter = _descendDirListing.iterator();
        }
        return _descendDirListing;
    }

    Iterator<File> getDescendDirListingIter() {
        getDescendDirListing();
        return _descendDirListingIter;
    }

    boolean descendDirListingIter_hasNext() {
        getDescendDirListing();
        boolean x = _descendDirListingIter.hasNext();
        return x;
    }

    File descendDirListingIter_next() {
        getDescendDirListing();
        File path = _descendDirListingIter.next();
        return path;
    }

    @Immutable
    static final class IterateFileFilter
    extends StatelessObject
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

    DirectoryListing getIterateDirListing() {
        if (null == _iterateDirListing) {
            PathFilter pathFilter = _parent.withIteratePathFilter();
            FileFilter fileFilter = _factory.newIterateFileFilterInstance(pathFilter, _depth);
            DirectoryListing newDirListing = _origDirListing.filter(fileFilter);
            Comparator<File> pathComparator = _parent.withIteratePathComparator();
            _iterateDirListing = newDirListing.sort(pathComparator);
            _iterateDirListingIter = _iterateDirListing.iterator();
        }
        return _iterateDirListing;
    }

    Iterator<File> getIterateDirListingIter() {
        getIterateDirListing();
        return _iterateDirListingIter;
    }

    boolean iterateDirListingIter_hasNext() {
        getIterateDirListing();
        boolean x = _iterateDirListingIter.hasNext();
        return x;
    }

    File iterateDirListingIter_next() {
        getIterateDirListing();
        File path = _iterateDirListingIter.next();
        return path;
    }
}
