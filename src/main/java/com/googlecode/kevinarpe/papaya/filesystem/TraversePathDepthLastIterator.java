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

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class TraversePathDepthLastIterator
extends TraversePathIterator {

    private boolean _isInitDone;
    private _Level _currentLevel;
    private boolean _hasIteratedDirPath;

    TraversePathDepthLastIterator(
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
    }

    @Override
    public boolean hasNext() {
        if (!_isInitDone) {
            _currentLevel = addLevel(getDirPath());
            _isInitDone = true;
        }
        if (!_hasIteratedDirPath) {
            return true;
        }
        while (null != _currentLevel && !_currentLevel.getIterateDirectoryListingIter().hasNext()) {
            final Iterator<File> descendDirIter = _currentLevel.getDescendDirDirectoryListingIter();
            if (descendDirIter.hasNext()) {
                _currentLevel = addLevel(descendDirIter.next());
            }
            else {
                _currentLevel = removeLevel();
            }
        }
        if (null == _currentLevel) {
            return false;
        }
        return _currentLevel.getIterateDirectoryListingIter().hasNext();
    }

    @Override
    public File next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (!_hasIteratedDirPath) {
            _hasIteratedDirPath = true;
            return getDirPath();
        }
        File path = _currentLevel.getIterateDirectoryListingIter().next();
        return path;
    }
}
