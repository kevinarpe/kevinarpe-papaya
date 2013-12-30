package com.googlecode.kevinarpe.papaya.filesystem.comparator;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.google.common.primitives.Longs;
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
        final int result = Longs.compare(numericFileName1, numericFileName2);
        return result;
    }
}
