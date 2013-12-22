package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import java.io.File;
import java.util.Comparator;

public class FileLastModifiedComparator
implements Comparator<File> {

    @Override
    public int compare(File path1, File path2) {
        return Long.compare(path1.lastModified(), path2.lastModified());
    }
}
