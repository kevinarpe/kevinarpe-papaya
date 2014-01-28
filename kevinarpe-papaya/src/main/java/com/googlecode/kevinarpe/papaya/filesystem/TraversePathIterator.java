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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathRuntimeException;

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
 * topDir
 *   |
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
 * <p>
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
@FullyTested
public abstract class TraversePathIterator
extends BaseTraversePathIter
implements Iterator<File> {

    static interface Factory {

        TraversePathLevel newInstance(TraversePathIterator parent, File dirPath, int depth)
        throws PathException;
    }

    static final class FactoryImpl
    implements Factory {

        static final FactoryImpl INSTANCE = new FactoryImpl();

        @Override
        public TraversePathLevel newInstance(TraversePathIterator parent, File dirPath, int depth)
        throws PathException {
            return new TraversePathLevel(parent, dirPath, depth);
        }
    }

    private final Factory _factory;
    private final LinkedList<TraversePathLevel> _levelList;

    TraversePathIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        this(
            dirPath,
            depthPolicy,
            exceptionPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optIteratePathFilter,
            optIteratePathComparator,
            FactoryImpl.INSTANCE);
    }

    TraversePathIterator(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            TraversePathExceptionPolicy exceptionPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator,
            Factory factory) {
        super(
            dirPath,
            depthPolicy,
            exceptionPolicy,
            optDescendDirPathFilter,
            optDescendDirPathComparator,
            optIteratePathFilter,
            optIteratePathComparator);
        _factory = ObjectArgs.checkNotNull(factory, "factory");
        _levelList = Lists.newLinkedList();
    }

    protected final TraversePathLevel tryDescendDirPath() {
        TraversePathLevel currentLevel = null;
        File dirPath = getRootDirPath();
        PathFilter optDescendDirFilter = getOptionalDescendDirPathFilter();
        if (null == optDescendDirFilter || optDescendDirFilter.accept(dirPath, 0)) {
            // If initial directory listing fails, but exceptions are ignored, '_currentLevel'
            // will remain null.
            currentLevel = tryAddLevel(dirPath);
        }
        return currentLevel;
    }

    protected final boolean canIterateDirPath() {
        File dirPath = getRootDirPath();
        PathFilter optIteratePathFilter = getOptionalIteratePathFilter();
        boolean result = (null == optIteratePathFilter || optIteratePathFilter.accept(dirPath, 0));
        return result;
    }

    /**
     * Descend a directory by creating a directory listing.  If result is not {@code null}, the
     * depth increases by one.
     *
     * @param dirPath
     *        directory path to descend
     *
     * @return new deepest level or {@code null} if directory listing throws exception and exception
     *         policy dictates to ignore it
     *
     * @throws PathRuntimeException
     *         if directory listing throws exception and exception policy dictates to
     *         rethrow as a runtime (unchecked) exception
     *
     * @see #tryRemoveAndGetNextLevel()
     * @see #getDepth()
     */
    protected final TraversePathLevel tryAddLevel(File dirPath)
    throws PathRuntimeException {
        final int depth = 1 + _levelList.size();
        TraversePathLevel level = null;
        try {
            level =  _factory.newInstance(this, dirPath, depth);
        }
        catch (PathException e) {
            if (TraversePathExceptionPolicy.THROW == getExceptionPolicy()) {
                throw new PathRuntimeException(e);
            }
        }
        if (null != level) {
            _levelList.add(level);
            return level;
        }
        return null;
    }

    /**
     * Remove the deepest level and return the next deepest level.  If result is not {@code null},
     * the depth decreases by one.
     *
     * @return new deepest level or {@code null} if no remaining levels after removal
     *
     * @see #tryAddLevel(File)
     * @see #getDepth()
     */
    protected final TraversePathLevel tryRemoveAndGetNextLevel() {
        if (_levelList.isEmpty()) {
            return null;
        }
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
     * @throws PathRuntimeException
     *         if an exception is thrown during a directory listing, and if exception policy
     *         dictates to throw the exception ({@link TraversePathExceptionPolicy#THROW}).
     *
     * @see #next()
     * @see #getExceptionPolicy()
     */
    @Override
    public abstract boolean hasNext()
    throws PathRuntimeException;

    /**
     * Returns the next path in the iteration.
     * <hr/>
     * {@inheritDoc}
     *
     * @throws NoSuchElementException
     *         if the iteration is complete.  This is only thrown if {@link #hasNext()} returns
     *         {@code false}.
     * @throws PathRuntimeException
     *         if an exception is thrown during a directory listing, and if exception policy
     *         dictates to throw the exception ({@link TraversePathExceptionPolicy#THROW}).
     *
     * @see #hasNext()
     * @see #getExceptionPolicy()
     */
    @Override
    public abstract File next()
    throws PathRuntimeException;

    /**
     * Always throws {@link UnsupportedOperationException}.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException(String.format(
            "Class is unmodifiable: %s", this.getClass().getName()));
    }

    /**
     * @return number of levels below {@link #getRootDirPath()}.  Minimum value is zero.
     */
    public final int getDepth() {
        return _levelList.size();
    }

    protected final void assertHasNext() {
        if (!hasNext()) {
            throw new NoSuchElementException(
                "Method hasNext() returns false: There is no next path to iterate");
        }
    }
}
