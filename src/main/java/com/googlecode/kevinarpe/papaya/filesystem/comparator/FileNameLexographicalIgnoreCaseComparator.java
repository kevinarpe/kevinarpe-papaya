package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.googlecode.kevinarpe.papaya.comparator.AbstractStatelessComparator;

import java.io.File;

public final class FileNameLexographicalIgnoreCaseComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        final String fileName1 = path1.getName();
        final String fileName2 = path2.getName();
        final int result = fileName1.compareToIgnoreCase(fileName2);
        return result;
    }
}
