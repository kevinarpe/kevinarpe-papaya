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
import java.util.*;

// TODO: Builder vs Iterator.  How does this work?  Public vs package-private?
public class DepthFirstPathIterator
extends UnmodifiableIterator {

    // TODO: Need two policy enums
    // (1) depth-first vs depth-last
    // (2) file-first vs dir-first

    // TODO: What about ArrayAsIterable, ArrayIterator, ArrayUnmodifiableIterator

    private boolean _isInitDone;
    private final File _dirPath;
    private final PathFilter _optDescendDirFilter;
    private final List<Comparator<File>> _descendFileComparatorList;
    private final PathFilter _optAscendFilter;
    private final List<Comparator<File>> _ascendFileComparatorList;
    private final LinkedList<_Level> _levelList;
    private _Level _currentLevel;

    private _Level _addLevel(File dirPath) {
        try {
            _currentLevel = new _Level(dirPath, _levelList.size());
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
        private final PathList _origPathList;
        private PathList _descendDirPathList;
        private Iterator<File> _descendDirPathListIter;
        private PathList _ascendPathList;
        private Iterator<File> _ascendPathListIter;

        // TODO: How to handle missing dirs?
        // Many initial listFiles() shows a dir... but later, does not exist.  Handle well.
        private _Level(File dirPath, int depth)
        throws PathException {
            _origPathList = PathList.from(dirPath);
            _depth = depth;
        }

        public PathList getDescendDirPathList() {
            if (null == _descendDirPathList) {
                // TODO: Maybe PathList should be immutable... or have an immutable equivalent?
                _descendDirPathList = _origPathList.filterInfoNewList(new FileFilter() {
                    @Override
                    public boolean accept(File path) {
                        if (!path.isDirectory()) {
                            return false;
                        }
                        if (null == DepthFirstPathIterator.this._optDescendDirFilter) {
                            return true;
                        }
                        return DepthFirstPathIterator.this._optDescendDirFilter.accept(path, _depth);
                    }
                });
                _descendDirPathList.sortInPlace(DepthFirstPathIterator.this._descendFileComparatorList);
            }
            return _descendDirPathList;
        }

        public Iterator<File> getDescendDirPathListIter() {
            if (null == _descendDirPathListIter) {
                PathList descendDirPathList = getDescendDirPathList();
                _descendDirPathListIter = descendDirPathList.iterator();
            }
            return _descendDirPathListIter;
        }

        public PathList getAscendPathList() {
            if (null == _ascendPathList) {
                if (null == DepthFirstPathIterator.this._optAscendFilter) {
                    _ascendPathList = new PathList(_origPathList);
                }
                else {
                    _ascendPathList = _origPathList.filterInfoNewList(new FileFilter() {
                        @Override
                        public boolean accept(File path) {
                            if (null == DepthFirstPathIterator.this._optAscendFilter) {
                                return true;
                            }
                            return DepthFirstPathIterator.this._optAscendFilter.accept(path, _depth);
                        }
                    });
                }
                _ascendPathList.sortInPlace(DepthFirstPathIterator.this._ascendFileComparatorList);
            }
            return _ascendPathList;
        }

        public Iterator<File> getAscendPathListIter() {
            if (null == _ascendPathListIter) {
                PathList ascendPathList = getAscendPathList();
                _ascendPathListIter = ascendPathList.iterator();
            }
            return _ascendPathListIter;
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
            while (_currentLevel.getDescendDirPathListIter().hasNext()) {
                File descendDirPath = _currentLevel.getDescendDirPathListIter().next();
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
        while (null != _currentLevel && !_currentLevel.getAscendPathListIter().hasNext()) {
            _removeLevel();  // This call changes '_currentLevel'
            _descend();  // This call changes '_currentLevel'
        }
        if (null == _currentLevel) {
            return false;
        }
        return _currentLevel.getAscendPathListIter().hasNext();
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        File path = _currentLevel.getAscendPathListIter().next();
        return path;
    }

    //TODO: @Override
    // Maybe add to a new interface... TraversePathIterator?
    public int depth() {
        return _levelList.size() - 1;
    }
}
