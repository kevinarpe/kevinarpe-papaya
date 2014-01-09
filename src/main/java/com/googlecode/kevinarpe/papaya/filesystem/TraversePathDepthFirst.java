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
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.util.Comparator;
import java.util.List;

// TODO: Keep / Replace with builder?
public class TraversePathDepthFirst
implements Iterable<File> {

    public static TraversePathDepthFirst on(File dirPath) {
        final PathFilter optDescendDirFilter = null;
        final List<Comparator<File>> descendFileComparatorList = ImmutableList.of();
        final PathFilter optAscendFilter = null;
        final List<Comparator<File>> ascendFileComparatorList = ImmutableList.of();
        return new TraversePathDepthFirst(
            dirPath,
            optDescendDirFilter,
            descendFileComparatorList,
            optAscendFilter,
            ascendFileComparatorList);
    }

    private final File _dirPath;
    private final PathFilter _optDescendDirFilter;
    private final List<Comparator<File>> _descendFileComparatorList;
    private final PathFilter _optAscendFilter;
    private final List<Comparator<File>> _ascendFileComparatorList;

    protected TraversePathDepthFirst(
            File dirPath,
            PathFilter optDescendDirFilter,
            List<Comparator<File>> descendFileComparatorList,
            PathFilter optAscendFilter,
            List<Comparator<File>> ascendFileComparatorList) {
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
    }

    public File getDirPath() {
        return _dirPath;
    }

    public PathFilter getOptionalDescendDirFilter() {
        return _optDescendDirFilter;
    }

    public List<Comparator<File>> getDescendFileComparatorList() {
        return _descendFileComparatorList;
    }

    public PathFilter getOptionalAscendFilter() {
        return _optAscendFilter;
    }

    public List<Comparator<File>> getAscendFileComparatorList() {
        return _ascendFileComparatorList;
    }

    public TraversePathDepthFirst withOptionalDescendDirFilter(PathFilter optDescendDirFilter) {
        return new TraversePathDepthFirst(
            _dirPath,
            optDescendDirFilter,
            _descendFileComparatorList,
            _optAscendFilter,
            _ascendFileComparatorList);
    }

    public TraversePathDepthFirst withDescendFileComparatorList(
            List<Comparator<File>> descendFileComparatorList) {
        return new TraversePathDepthFirst(
            _dirPath,
            _optDescendDirFilter,
            descendFileComparatorList,
            _optAscendFilter,
            _ascendFileComparatorList);
    }

    public TraversePathDepthFirst withOptionalAscendFilter(PathFilter optAscendFilter) {
        return new TraversePathDepthFirst(
            _dirPath,
            _optDescendDirFilter,
            _descendFileComparatorList,
            optAscendFilter,
            _ascendFileComparatorList);
    }

    public TraversePathDepthFirst withAscendFileComparatorList(
            List<Comparator<File>> ascendFileComparatorList) {
        return new TraversePathDepthFirst(
            _dirPath,
            _optDescendDirFilter,
            _descendFileComparatorList,
            _optAscendFilter,
            ascendFileComparatorList);
    }

    @Override
    public DepthFirstPathIterator iterator() {
        return new DepthFirstPathIterator(
            _dirPath,
            _optDescendDirFilter,
            _descendFileComparatorList,
            _optAscendFilter,
            _ascendFileComparatorList);
    }
}
