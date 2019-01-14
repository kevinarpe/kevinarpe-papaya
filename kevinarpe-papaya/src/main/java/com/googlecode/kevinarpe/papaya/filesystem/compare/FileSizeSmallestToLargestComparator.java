package com.googlecode.kevinarpe.papaya.filesystem.compare;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

import java.io.File;
import java.util.Comparator;

/**
 * Compares two {@link File} references using {@link File#length()}.
 * <p>
 * This {@link Comparator} is stateless.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StatelessObject
 * @see #compare(File, File)
 */
@FullyTested
public final class FileSizeSmallestToLargestComparator
extends StatelessObject
implements Comparator<File> {

    /**
     * @see StatelessObject#StatelessObject()
     */
    public FileSizeSmallestToLargestComparator() {
        super();
    }

    /**
     * Compares the results of {@link File#length()}  via
     * {@link Longs#compare(long, long)}.  Directories have defined length of zero.
     * <hr>
     * {@inheritDoc}
     *
     * @param path1
     *        must not be {@code null}
     * @param path2
     *        must not be {@code null}
     *
     * @return
     * <ul>
     *     <li>-1 if {@code path1} is less than {@code path2}</li>
     *     <li>0 if {@code path1} is equal to {@code path2}</li>
     *     <li>+1 if {@code path1} is greater than {@code path2}</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code path1} or {@code path2} is {@code null}
     */
    @Override
    public int compare(File path1, File path2) {
        ObjectArgs.checkNotNull(path1, "path1");
        ObjectArgs.checkNotNull(path2, "path2");

        // From Javadocs for File.isDirectory():
        // "The return value is unspecified if this pathname denotes a directory."
        final long pathSize1 = (path1.isDirectory() ? 0 : path1.length());
        final long pathSize2 = (path2.isDirectory() ? 0 : path2.length());
        final int result = Longs.compare(pathSize1, pathSize2);
        return result;
    }
}
