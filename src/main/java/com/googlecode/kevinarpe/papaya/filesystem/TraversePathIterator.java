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
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.exception.PathException;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Traverses a directory hierarchy similar to the UNIX command line tool {@code find}.  Use the
 * builder class {@link TraversePathIterable} to construct path iterators.
 * <p>
 * Terminology:
 * <ul>
 *     <li>For "depth last" iterators, paths in each directory are iterated <i>before</i> descending
 *     any directories</li>
 *     <li>For "depth first" iterators, paths in each directory are iterated <i>after</i> descending
 *     any directories</li>
 * </ul>
 * <p>
 * Example directory structure:
 * <pre>{@code
 *       topDir
 *         |
 * file1 file2 dir1
 *              |
 *      file3 file4 dir2
 *                    |
 *                  file5
 * }</pre>
 * <p>
 * Directories are traversed in this order:
 * <ul>
 *     <li>For "depth last" iterators: topDir, dir1, dir2</li>
 *     <li>For "depth first" iterators: dir2, dir1, topDir</li>
 * </ul>
 * Possible path iterations:
 * <ul>
 *     <li>For "depth last" iterators: topDir, file1, file2, dir1, file3, file4 dir2, file5</li>
 *     <li>For "depth first" iterators: file5, file3, file4, dir2, file1, file2, dir1, topDir</li>
 * </ul>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see BaseTraversePathIter
 */
@NotFullyTested
public abstract class TraversePathIterator
extends BaseTraversePathIter
implements Iterator<File> {

    private final LinkedList<TraversePathLevel> _levelList;

    TraversePathIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIterateFileComparator) {
        super(
            dirPath,
            depthPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optIteratePathFilter,
            optIterateFileComparator);
        _levelList = Lists.newLinkedList();
    }

    protected final TraversePathLevel addLevel(File dirPath) {
        final int depth = 1 + _levelList.size();
        TraversePathLevel level = null;
        try {
            level = new TraversePathLevel(this, dirPath, depth);
        }
        catch (PathException e) {
            // TODO: Fixme
            throw new RuntimeException(e);
        }
        _levelList.add(level);
        return level;
    }

    protected final TraversePathLevel removeLevel() {
        _levelList.removeLast();
        TraversePathLevel level = (_levelList.isEmpty() ? null :_levelList.getLast());
        return level;
    }

    /**
     * Tests if a next path exists for iteration.  The first call to this method is comparatively
     * more expensive than future calls.
     * <hr/>
     * {@inheritDoc}
     *
     * @see #next()
     */
    @Override
    public abstract boolean hasNext();

    /**
     * Returns the next path for iteration.
     *
     * @throws NoSuchElementException
     *         if there is not next path for iteration
     * <hr/>
     * {@inheritDoc}
     *
     * @see #hasNext()
     */
    @Override
    public abstract File next();

    /**
     * Always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException(String.format(
            "Class is unmodifiable: %s", this.getClass().getName()));
    }

    /**
     * @return number of levels below {@link #getDirPath()}.  Minimum value is zero.
     */
    public final int depth() {
        return _levelList.size();
    }
}
