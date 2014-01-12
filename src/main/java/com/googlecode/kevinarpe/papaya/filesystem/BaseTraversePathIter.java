package com.googlecode.kevinarpe.papaya.filesystem;

import java.io.File;
import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class BaseTraversePathIter {

    private final File _dirPath;
    private final TraversePathDepthPolicy _depthPolicy;
    private final PathFilter _optDescendDirPathFilter;
    private final Comparator<File> _optDescendDirPathComparator;
    private final PathFilter _optIteratePathFilter;
    private final Comparator<File> _optIteratePathComparator;

    protected BaseTraversePathIter(
            File dirPath,
            TraversePathDepthPolicy depthPolicy,
            PathFilter optDescendDirPathFilter,
            Comparator<File> optDescendDirPathComparator,
            PathFilter optIteratePathFilter,
            Comparator<File> optIteratePathComparator) {
        _dirPath = dirPath;
        _depthPolicy = depthPolicy;
        _optDescendDirPathFilter = optDescendDirPathFilter;
        _optDescendDirPathComparator = optDescendDirPathComparator;
        _optIteratePathFilter = optIteratePathFilter;
        _optIteratePathComparator = optIteratePathComparator;
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

    public final Comparator<File> getOptionalDescendDirPathComparator() {
        return _optDescendDirPathComparator;
    }

    public final PathFilter getOptionalIteratePathFilter() {
        return _optIteratePathFilter;
    }

    public final Comparator<File> getOptionalIteratePathComparator() {
        return _optIteratePathComparator;
    }

    // Do not override hashCode() and equals() here.  It doesn't make sense to support this
    // operation for iterators.
}
