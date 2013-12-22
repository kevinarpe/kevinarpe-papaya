package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import java.io.File;
import java.util.Comparator;

public class FileSizeComparator
implements Comparator<File> {

    @Override
    public int compare(File path1, File path2) {
        // "The return value is unspecified if this pathname denotes a directory."
        final long pathSize1 = (path1.isDirectory() ? 0 : path1.length());
        final long pathSize2 = (path2.isDirectory() ? 0 : path2.length());

        return Long.compare(pathSize1, pathSize2);
    }
}
