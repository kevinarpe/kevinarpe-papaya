package com.googlecode.kevinarpe.papaya.filesystem.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.compare.ComparatorUtils;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.string.NumericPrefix;

import java.io.File;
import java.util.Comparator;

/**
 * Compares two {@link File} references using the numeric prefix of {@link File#getName()}.
 * <p>
 * This {@link Comparator} is stateless.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StatelessObject
 * @see #compare(File, File)
 * @see NumericPrefix
 */
@FullyTested
public final class FileNameNumericPrefixSmallestToLargestComparator
extends StatelessObject
implements Comparator<File> {

    /**
     * @see StatelessObject#StatelessObject()
     */
    public FileNameNumericPrefixSmallestToLargestComparator() {
        super();
    }

    /**
     * Compares the numeric prefixes of {@link File#getName()}  via
     * {@link Longs#compare(long, long)}.  If either file name does not have a numeric prefix,
     * the file names are compared via {@link String#compareTo(String)}.
     * <hr/>
     * {@inheritDoc}
     *
     * @param path1
     *        must not be {@code null}
     * @param path2
     *        must not be {@code null}
     *
     * @return
     * <li>
     *     <ul>-1 if {@code path1} is less than {@code path2}</ul>
     *     <ul>0 if {@code path1} is equal to {@code path2}</ul>
     *     <ul>+1 if {@code path1} is greater than {@code path2}</ul>
     * </li>
     *
     * @throws NullPointerException
     *         if {@code path1} or {@code path2} is {@code null}
     *
     * @see NumericPrefix
     */
    @Override
    public int compare(File path1, File path2) {
        ObjectArgs.checkNotNull(path1, "path1");
        ObjectArgs.checkNotNull(path2, "path2");

        final String fileName1 = path1.getName();
        final String fileName2 = path2.getName();

        final NumericPrefix numericPrefix1 = new NumericPrefix(fileName1);
        final NumericPrefix numericPrefix2 = new NumericPrefix(fileName2);

        if (!numericPrefix1.hasNumericValue() || !numericPrefix2.hasNumericValue()) {
            int result = fileName1.compareTo(fileName2);
            result = ComparatorUtils.normalizeCompareResult(result);
            return result;
        }

        final long numericFileName1 = numericPrefix1.getNumericValue();
        final long numericFileName2 = numericPrefix2.getNumericValue();
        final int result = Longs.compare(numericFileName1, numericFileName2);
        return result;
    }
}
