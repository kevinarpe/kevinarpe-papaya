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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

// TODO: Builder vs Iterator.  How does this work?  Public vs package-private?
public class DepthFirstPathIterator
extends UnmodifiableIterator<File> {

    // TODO: Need two policy enums
    // (1) depth-first vs depth-last
    // (2) file-first vs dir-first

    private boolean _isInitDone;
    private final File _dirPath;
    private final PathFilter _optDescendDirFilter;
    private final List<Comparator<File>> _descendFileComparatorList;
    private final PathFilter _optAscendFilter;
    private final List<Comparator<File>> _ascendFileComparatorList;
    private final LinkedList<_Level> _levelList;
    private _Level _currentLevel;

    private _Level _addLevel(File dirPath) {
        final int depth = 1 + _levelList.size();
        try {
            _currentLevel = new _Level(dirPath, depth);
        }
        catch (PathException e) {
            throw new RuntimeException(e);
        }
        _levelList.add(_currentLevel);
        return _currentLevel;
    }

    private _Level _removeLevel() {
        _Level x = _levelList.removeLast();
        _currentLevel = (_levelList.isEmpty() ? null :_levelList.getLast());
        return x;
    }

    private class _Level {

        private final int _depth;
        private final DirectoryListing _origDirectoryListing;
        private DirectoryListing _descendDirDirectoryListing;
        private Iterator<File> _descendDirDirectoryListingIter;
        private DirectoryListing _ascendDirectoryListing;
        private Iterator<File> _ascendDirectoryListingIter;

        // TODO: How to handle missing dirs?
        // Many initial listFiles() shows a dir... but later, does not exist.  Handle well.  Policy?
        private _Level(File dirPath, int depth)
        throws PathException {
            _origDirectoryListing = new DirectoryListing(dirPath, LinkedList.class);
            _depth = depth;
        }

        public DirectoryListing getDescendDirDirectoryListing() {
            if (null == _descendDirDirectoryListing) {
                _descendDirDirectoryListing = new DirectoryListing(_origDirectoryListing).filter(
                    new FileFilter() {
                        @Override
                        public boolean accept(File path) {
                            if (!path.isDirectory()) {
                                return false;
                            }
                            if (null == DepthFirstPathIterator.this._optDescendDirFilter) {
                                return true;
                            }
                            return DepthFirstPathIterator.this._optDescendDirFilter.accept(
                                path, _depth);
                        }
                    });
                _descendDirDirectoryListing.sort(
                    DepthFirstPathIterator.this._descendFileComparatorList);
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

        public DirectoryListing getAscendDirectoryListing() {
            if (null == _ascendDirectoryListing) {
                if (null == DepthFirstPathIterator.this._optAscendFilter) {
                    _ascendDirectoryListing = new DirectoryListing(_origDirectoryListing);
                }
                else {
                    _ascendDirectoryListing = new DirectoryListing(_origDirectoryListing).filter(
                        new FileFilter() {
                            @Override
                            public boolean accept(File path) {
                                if (null == DepthFirstPathIterator.this._optAscendFilter) {
                                    return true;
                                }
                                return DepthFirstPathIterator.this._optAscendFilter.accept(
                                    path, _depth);
                            }
                        });
                }
                _ascendDirectoryListing.sort(DepthFirstPathIterator.this._ascendFileComparatorList);
            }
            return _ascendDirectoryListing;
        }

        public Iterator<File> getAscendDirectoryListingIter() {
            if (null == _ascendDirectoryListingIter) {
                DirectoryListing ascendDirectoryListing = getAscendDirectoryListing();
                List<File> childPathList = ascendDirectoryListing.getChildPathList();
                _ascendDirectoryListingIter = childPathList.iterator();
            }
            return _ascendDirectoryListingIter;
        }
    }

    DepthFirstPathIterator(
            File dirPath,
            PathFilter optDescendDirFilter,
            List<Comparator<File>> descendFileComparatorList,
            PathFilter optAscendFilter,
            List<Comparator<File>> ascendFileComparatorList) {
        _isInitDone = false;
        _dirPath = ObjectArgs.checkNotNull(dirPath, "dirPath");

        _optDescendDirFilter = optDescendDirFilter;

        _descendFileComparatorList =
            ImmutableList.copyOf(
                CollectionArgs.checkElementsNotNull(
                    descendFileComparatorList, "descendFileComparatorList"));

        _optAscendFilter = optAscendFilter;

        _ascendFileComparatorList =
            ImmutableList.copyOf(
                CollectionArgs.checkElementsNotNull(
                    ascendFileComparatorList, "ascendFileComparatorList"));

        _levelList = Lists.newLinkedList();
    }

    private void _descend() {
        if (null != _currentLevel) {
            while (_currentLevel.getDescendDirDirectoryListingIter().hasNext()) {
                File descendDirPath = _currentLevel.getDescendDirDirectoryListingIter().next();
                _addLevel(descendDirPath);  // This call changes '_currentLevel'
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (!_isInitDone) {
            _addLevel(_dirPath);  // This call changes '_currentLevel'
            _descend();  // This call changes '_currentLevel'
            _isInitDone = true;
        }
        while (null != _currentLevel && !_currentLevel.getAscendDirectoryListingIter().hasNext()) {
            _removeLevel();  // This call changes '_currentLevel'
            _descend();  // This call changes '_currentLevel'
        }
        if (null == _currentLevel) {
            return false;
        }
        return _currentLevel.getAscendDirectoryListingIter().hasNext();
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        File path = _currentLevel.getAscendDirectoryListingIter().next();
        return path;
    }

    //TODO: @Override
    // Maybe add to a new interface... TraversePathIterator?
    public int depth() {
        return _levelList.size() - 1;
    }
}
