package com.googlecode.kevinarpe.papaya.filesystem;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class BaseTraversePathIter {

    private final File _dirPath;
    private final TraversePathDepthPolicy _depthPolicy;
    private final PathFilter _optDescendDirPathFilter;
    private final List<Comparator<File>> _descendDirPathComparatorList;
    private final PathFilter _optIteratePathFilter;
    private final List<Comparator<File>> _iteratePathComparatorList;

    protected BaseTraversePathIter(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            List<Comparator<File>> descendDirPathComparatorList,
            PathFilter optIteratePathFilter,
            List<Comparator<File>> iteratePathComparatorList) {
        _dirPath = dirPath;
        _depthPolicy = depthPolicy;
        _optDescendDirPathFilter = optDescendDirPathFilter;
        _descendDirPathComparatorList = descendDirPathComparatorList;
        _optIteratePathFilter = optIteratePathFilter;
        _iteratePathComparatorList = iteratePathComparatorList;
    }

    public final File getDirPath() {
        return _dirPath;
    }

    public TraversePathDepthPolicy getDepthPolicy() {
        return _depthPolicy;
    }

    public final PathFilter getOptionalDescendDirPathFilter() {
        return _optDescendDirPathFilter;
    }

    public final List<Comparator<File>> getDescendDirPathComparatorList() {
        return _descendDirPathComparatorList;
    }

    public final PathFilter getOptionalIteratePathFilter() {
        return _optIteratePathFilter;
    }

    public final List<Comparator<File>> getIteratePathComparatorList() {
        return _iteratePathComparatorList;
    }

    // Do not override hashCode() and equals() here.  It doesn't make sense to support this
    // operation for iterators.
}
