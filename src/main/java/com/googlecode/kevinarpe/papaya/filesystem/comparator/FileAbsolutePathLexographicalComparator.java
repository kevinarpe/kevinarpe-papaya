package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.googlecode.kevinarpe.papaya.comparator.AbstractStatelessComparator;

import java.io.File;

public final class FileAbsolutePathLexographicalComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        final String absolutePath1 = path1.getAbsolutePath();
        final String absolutePath2 = path2.getAbsolutePath();
        final int result = absolutePath1.compareTo(absolutePath2);
        return result;
    }
}
