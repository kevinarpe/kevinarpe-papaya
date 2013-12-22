package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import java.io.File;
import java.util.Comparator;

public class FileNameComparator
implements Comparator<File> {

    @Override
    public int compare(File path1, File path2) {
        return path1.getName().compareTo(path2.getName());
    }
}
