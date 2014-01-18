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

/**
 * How to handle exceptions thrown when retrieving directory listings.  When traversing a directory
 * hierarchy, descending a directory requires two directory listings.  The first discovers
 * the directory, and the second attempts to descend it.  During the second listing, there is no
 * guarantee the directory will still exist or be executable (able to traverse).
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #IGNORE
 * @see #THROW
 * @see TraversePathIterable
 */
public enum TraversePathExceptionPolicy {

    /**
     * Suppress exceptions thrown during directory listings.  Use this option with care.  It is
     * possible to have the root directory not exist.  This will result in only a single path for
     * iteration: the root directory.
     *
     * @see #THROW
     */
    IGNORE,

    /**
     * Do <b>not</b> suppress exceptions thrown during directory listings.
     *
     * @see #IGNORE
     */
    THROW
}
