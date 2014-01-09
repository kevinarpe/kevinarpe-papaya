package com.googlecode.kevinarpe.papaya.filesystem;

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

import java.io.File;
import java.io.FileFilter;

/**
 * Extends {@link FileFilter} to add {@code depth} parameter to {@link #accept(File, int)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see DirectoryListing
 * @see DepthFirstPathIterator
 */
public interface PathFilter {

    /**
     * Tests whether or not {@code path} at {@code depth} should be included in a path list.
     *
     * @param path
     *        filesystem path to test.  Not guaranteed to exist when this method is called.
     *
     * @param depth
     *        number of levels below the parent directory.  Always >= 1.
     *
     * @return {@code true} to include in the path list or {@code false} to exclude
     *
     * @see DirectoryListing
     * @see DepthFirstPathIterator
     */
    boolean accept(File path, int depth);
}
