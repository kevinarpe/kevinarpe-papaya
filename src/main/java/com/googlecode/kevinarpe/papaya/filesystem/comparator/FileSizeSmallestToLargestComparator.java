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

import java.io.File;

// TODO: Remove 'SmallestToLargest' from class name?
public final class FileSizeSmallestToLargestComparator
extends AbstractStatelessComparator<File> {

    @Override
    public int compare(File path1, File path2) {
        // From Javadocs for File.isDirectory():
        // "The return value is unspecified if this pathname denotes a directory."
        final long pathSize1 = (path1.isDirectory() ? 0 : path1.length());
        final long pathSize2 = (path2.isDirectory() ? 0 : path2.length());
        final int result = Longs.compare(pathSize1, pathSize2);
        return result;
    }
}
