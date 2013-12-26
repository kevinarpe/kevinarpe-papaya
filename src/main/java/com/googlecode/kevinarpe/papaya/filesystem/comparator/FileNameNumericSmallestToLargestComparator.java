package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import com.googlecode.kevinarpe.papaya.comparator.AbstractStatelessComparator;
import com.googlecode.kevinarpe.papaya.string.NumericPrefix;

import java.io.File;

public final class FileNameNumericSmallestToLargestComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        final String fileName1 = path1.getName();
        final String fileName2 = path2.getName();

        final NumericPrefix numericPrefix1 = new NumericPrefix(fileName1);
        final NumericPrefix numericPrefix2 = new NumericPrefix(fileName2);

        if (!numericPrefix1.hasNumericValue() || !numericPrefix2.hasNumericValue()) {
            final int result = fileName1.compareTo(fileName2);
            return result;
        }

        final long numericFileName1 = numericPrefix1.getNumericValue();
        final long numericFileName2 = numericPrefix2.getNumericValue();
        final int result = Long.compare(numericFileName1, numericFileName2);
        return result;
    }
}
