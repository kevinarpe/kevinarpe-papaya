package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.googlecode.kevinarpe.papaya.comparator.AbstractStatelessComparator;

import java.io.File;

public final class FileLastModifiedOldestToNewestComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        final long lastModified1 = path1.lastModified();
        final long lastModified2 = path2.lastModified();
        final int result = Long.compare(lastModified1, lastModified2);
        return result;
    }
}
