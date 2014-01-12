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

import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class TraversePathIterator
extends BaseTraversePathIter
implements Iterator<File> {

    final class _Level {

        private final int _depth;
        private final DirectoryListing _origDirectoryListing;
        private DirectoryListing _descendDirDirectoryListing;
        private Iterator<File> _descendDirDirectoryListingIter;
        private DirectoryListing _iterateDirectoryListing;
        private Iterator<File> _iterateDirectoryListingIter;

        // TODO: How to handle missing dirs?
        // Many initial listFiles() shows a dir... but later, does not exist.  Handle well.  Policy?
        // Add option to ignore.
        private _Level(File dirPath, int depth)
        throws PathException {
            _origDirectoryListing = new DirectoryListing(dirPath, LinkedList.class);
            _depth = depth;
        }

        public DirectoryListing getDescendDirDirectoryListing() {
            if (null == _descendDirDirectoryListing) {
                DirectoryListing newDirListing = new DirectoryListing(_origDirectoryListing);
                final PathFilter descendDirPathFilter =
                    TraversePathIterator.this.getOptionalDescendDirPathFilter();
                newDirListing.filter(
                    new FileFilter() {
                        @Override
                        public boolean accept(File path) {
                            if (!path.isDirectory()) {
                                return false;
                            }
                            if (null == descendDirPathFilter) {
                                return true;
                            }
                            boolean result = descendDirPathFilter.accept(path, _depth);
                            return result;
                        }
                    });
                newDirListing.sort(
                    TraversePathIterator.this.getDescendDirPathComparatorList());
                _descendDirDirectoryListing = newDirListing;
            }
            return _descendDirDirectoryListing;
        }

        public Iterator<File> getDescendDirDirectoryListingIter() {
            if (null == _descendDirDirectoryListingIter) {
                DirectoryListing descendDirDirectoryListing = getDescendDirDirectoryListing();
                List<File> childPathList = descendDirDirectoryListing.getChildPathList();
                _descendDirDirectoryListingIter = childPathList.iterator();
            }
            return _descendDirDirectoryListingIter;
        }

        public DirectoryListing getIterateDirectoryListing() {
            if (null == _iterateDirectoryListing) {
                final PathFilter pathFilter =
                    TraversePathIterator.this.getOptionalIteratePathFilter();
                DirectoryListing newDirListing = new DirectoryListing(_origDirectoryListing);
                if (null != pathFilter) {
                    newDirListing.filter(
                        new FileFilter() {
                            @Override
                            public boolean accept(File path) {
                                boolean result = true;
                                if (null != pathFilter) {
                                    result = pathFilter.accept(path, _depth);
                                }
                                return result;
                            }
                        });
                }
                newDirListing.sort(
                    TraversePathIterator.this.getIteratePathComparatorList());
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

    private final LinkedList<_Level> _levelList;

    TraversePathIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            List<Comparator<File>> descendDirPathComparatorList,
            PathFilter optIteratePathFilter,
            List<Comparator<File>> iterateFileComparatorList) {
        super(
            dirPath,
            depthPolicy,
            optDescendDirPathFilter,
            descendDirPathComparatorList,
            optIteratePathFilter,
            iterateFileComparatorList);
        _levelList = Lists.newLinkedList();
    }

    protected final _Level addLevel(File dirPath) {
        final int depth = 1 + _levelList.size();
        _Level level = null;
        try {
            level = new _Level(dirPath, depth);
        }
        catch (PathException e) {
            // TODO: Fixme
            throw new RuntimeException(e);
        }
        _levelList.add(level);
        return level;
    }

    protected final _Level removeLevel() {
        _levelList.removeLast();
        _Level level = (_levelList.isEmpty() ? null :_levelList.getLast());
        return level;
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException(String.format(
            "Class is unmodifiable: %s", this.getClass().getName()));
    }

    public final int depth() {
        return _levelList.size() - 1;
    }
}
