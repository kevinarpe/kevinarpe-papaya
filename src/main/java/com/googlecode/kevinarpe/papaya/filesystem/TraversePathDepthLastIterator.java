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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
final class TraversePathDepthLastIterator
extends TraversePathIterator {

    private boolean _isInitDone;
    private TraversePathLevel _currentLevel;  // null when depth == 0
    private boolean _hasIteratedDirPath;

    TraversePathDepthLastIterator(
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
        _hasIteratedDirPath = false;
    }

    private void doInit() {
        if (!_isInitDone) {
            _currentLevel = tryDescendDirPath();
            if (!canIterateDirPath()) {
                _hasIteratedDirPath = true;  // We will never iterate 'dirPath'.
            }
            _isInitDone = true;
        }
    }

    @Override
    public boolean hasNext() {
        doInit();
        if (!_hasIteratedDirPath) {
            return true;
        }
        // New
        while (null != _currentLevel) {
            final Iterator<File> iterateDirPathIter =_currentLevel.getIterateDirectoryListingIter();
            if (iterateDirPathIter.hasNext()) {
                return true;
            }
            _tryUpdateCurrentLevel();
        }
        return false;
    }

    private void _tryUpdateCurrentLevel() {
        final Iterator<File> descendDirPathIter =
            _currentLevel.getDescendDirDirectoryListingIter();
        if (descendDirPathIter.hasNext()) {
            File descendDirPath = descendDirPathIter.next();
            TraversePathLevel newCurrentLevel = tryAddLevel(descendDirPath);
            if (null != newCurrentLevel) {
                _currentLevel = newCurrentLevel;
            }
        }
        else {
            _currentLevel = tryRemoveAndGetNextLevel();
        }
    }

    @Override
    public File next() {
        assertHasNext();
        if (!_hasIteratedDirPath) {
            _hasIteratedDirPath = true;
            return getRootDirPath();
        }
        Iterator<File> iterateDirPathIter =_currentLevel.getIterateDirectoryListingIter();
        File path = iterateDirPathIter.next();
        return path;
    }
}
