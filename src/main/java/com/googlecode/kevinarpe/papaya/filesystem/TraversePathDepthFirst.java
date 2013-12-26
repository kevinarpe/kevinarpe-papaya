package com.googlecode.kevinarpe.papaya.filesystem;

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.argument.CollectionArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.File;
import java.util.Comparator;
import java.util.List;

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
