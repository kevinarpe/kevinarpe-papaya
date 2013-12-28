package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.googlecode.kevinarpe.papaya.comparator.AbstractStatelessComparator;

import java.io.File;

// TODO: Remove 'SmallestToLargest' from class name?
public final class FileSizeSmallestToLargestComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        // From Javadocs for File.isDirectory():
        // "The return value is unspecified if this pathname denotes a directory."
        final long pathSize1 = (path1.isDirectory() ? 0 : path1.length());
        final long pathSize2 = (path2.isDirectory() ? 0 : path2.length());
        final int result = Long.compare(pathSize1, pathSize2);
        return result;
    }
}
