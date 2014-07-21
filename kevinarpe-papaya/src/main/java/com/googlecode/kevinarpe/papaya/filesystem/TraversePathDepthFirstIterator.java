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
import com.googlecode.kevinarpe.papaya.filesystem.filter.PathFilter;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
final class TraversePathDepthFirstIterator
extends AbstractTraversePathIteratorImpl {

    private boolean _isInitDone;
    private TraversePathLevel _currentLevel;  // null when depth == 0
    private boolean _hasIteratedDirPath;
    private boolean _isNextElementDirPath;

    TraversePathDepthFirstIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        super(
            dirPath,
            depthPolicy,
            exceptionPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optIteratePathFilter,
            optIteratePathComparator);
        _isInitDone = false;
        _currentLevel = null;  // Pedantic.
        _hasIteratedDirPath = false;
        _isNextElementDirPath = false;
    }

    private void _descendAndUpdateCurrentLevel() {
        if (null != _currentLevel) {
            while (true) {
                if (!_currentLevel.descendDirListingIter_hasNext()) {
                    break;
                }
                File descendDirPath = _currentLevel.descendDirListingIter_next();
                TraversePathLevel newCurrentLevel = tryAddLevel(descendDirPath);
                if (null != newCurrentLevel) {
                    _currentLevel = newCurrentLevel;
                }
            }
        }
    }

    private void _doInit() {
        if (!_isInitDone) {
            _currentLevel = tryDescendDirPath();
            _descendAndUpdateCurrentLevel();
            _isInitDone = true;
        }
    }

    @Override
    public boolean hasNext() {
        _doInit();
        while (null != _currentLevel && !_currentLevel.iterateDirListingIter_hasNext()) {
            _currentLevel = tryRemoveAndGetNextLevel();
            _descendAndUpdateCurrentLevel();
        }
        if (null == _currentLevel) {
            if (!_hasIteratedDirPath) {
                if (canIterateDirPath()) {
                    _isNextElementDirPath = true;
                    return true;
                }
                else {
                    _hasIteratedDirPath = true;
                    return false;
                }
            }
            return false;
        }
        return _currentLevel.iterateDirListingIter_hasNext();
    }

    @Override
    public File next() {
        assertHasNext();
        if (_isNextElementDirPath) {
            _hasIteratedDirPath = true;
            _isNextElementDirPath = false;
            return withRootDirPath();
        }
        File path = _currentLevel.iterateDirListingIter_next();
        return path;
    }
}
