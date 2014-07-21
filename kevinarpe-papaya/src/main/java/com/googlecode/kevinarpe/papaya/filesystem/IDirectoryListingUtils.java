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

import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;

import java.io.File;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link DirectoryListingUtils} or
 * {@link DirectoryListingUtils#INSTANCE} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see DirectoryListingUtils
 */
public interface IDirectoryListingUtils {

    /**
     * Retrieves the global instance that implements interface {@link DirectoryListingFactory}.
     *
     * @return global instance
     *
     * @see #newInstance(File)
     */
    DirectoryListingFactory getFactory();

    /**
     * Builds a list of child paths for a directory.  Access the list of child paths via
     * {@link DirectoryListing#getChildPathList()}.
     *
     * @param dirPath
     *        path to directory used to obtain a listing of child paths
     *
     * @throws NullPointerException
     *         if {@code path} or {@code listClass} is {@code null}
     * @throws PathException
     * <ul>
     *     <li>with reason {@link PathExceptionReason#PATH_DOES_NOT_EXIST}
     *     if {@code path} does not exist</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_NORMAL_FILE}
     *     if {@code path} exists, but is not a directory</li>
     *     <li>with reason {@link PathExceptionReason#PATH_IS_NON_EXECUTABLE_DIRECTORY}
     *     if {@code path} exists as a directory, but is not accessible for listing</li>
     *     <li>with reason {@link PathExceptionReason#UNKNOWN}
     *     if reason for error is unknown</li>
     * </ul>
     * @throws IllegalArgumentException
     *         if calling method {@link Class#newInstance()} fails for {@code listClass}
     *
     * @see DirectoryListing#getDirPath()
     * @see DirectoryListing#getChildPathList()
     */
    DirectoryListing newInstance(File dirPath)
    throws PathException;
}
