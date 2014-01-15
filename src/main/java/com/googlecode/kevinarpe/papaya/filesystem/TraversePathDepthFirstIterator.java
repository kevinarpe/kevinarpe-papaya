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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@NotFullyTested
final class TraversePathDepthFirstIterator
extends TraversePathIterator {

    private boolean _isInitDone;
    private TraversePathLevel _currentLevel;  // null when depth == 0
    private boolean _hasIteratedDirPath;
    private boolean _isNextDirPath;

    TraversePathDepthFirstIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirFilter,
            Comparator<File> optDescendFileComparator,
            PathFilter optIterateFilter,
            Comparator<File> optIterateFileComparator) {
        super(
            dirPath,
            depthPolicy,
            optDescendDirFilter,
            optDescendFileComparator,
            optIterateFilter,
            optIterateFileComparator);
        _isInitDone = false;
        _hasIteratedDirPath = false;
        _isNextDirPath = false;
    }

    private void _descendAndUpdateCurrentLevel() {
        if (null != _currentLevel) {
            while (true) {
                Iterator<File> iter = _currentLevel.getDescendDirDirectoryListingIter();
                if (!iter.hasNext()) {
                    break;
                }
                File descendDirPath = iter.next();
                _currentLevel = addLevel(descendDirPath);
            }
            // TODO: Tighter, but harder to debug.  Dou suru?
//            while (_currentLevel.getDescendDirDirectoryListingIter().hasNext()) {
//                File descendDirPath = _currentLevel.getDescendDirDirectoryListingIter().next();
//                _currentLevel = addLevel(descendDirPath);
//            }
        }
    }

    @Override
    public boolean hasNext() {
        if (!_isInitDone) {
            _currentLevel = addLevel(getDirPath());
            _descendAndUpdateCurrentLevel();
            _isInitDone = true;
        }
        while (null != _currentLevel && !_currentLevel.getIterateDirectoryListingIter().hasNext()) {
            _currentLevel = removeLevel();
            _descendAndUpdateCurrentLevel();
        }
        if (null == _currentLevel) {
            if (!_hasIteratedDirPath) {
                _isNextDirPath = true;
                return true;
            }
            return false;
        }
        return _currentLevel.getIterateDirectoryListingIter().hasNext();
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (_isNextDirPath) {
            _hasIteratedDirPath = true;
            _isNextDirPath = false;
            return getDirPath();
        }
        File path = _currentLevel.getIterateDirectoryListingIter().next();
        return path;
    }
}
