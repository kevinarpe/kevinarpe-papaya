package com.googlecode.kevinarpe.papaya.filesystem;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.filesystem.compare.FileTypeComparator;

import java.io.File;
import java.io.IOException;

/**
 * Classification for files: {@link #NORMAL_FILE} and {@link #DIRECTORY}.  Currently, Java 6 has
 * no further file types, but this may increase in future versions.
 * <p>
 * For Windows users, the term "file" may be ambiguous.  UNIX terminology refers to all file system
 * entries as files with classifications such as block special, character special, directory,
 * regular, symbolic link, named pipe, and socket.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #from(File)
 * @see #is(File)
 * @see FileTypeComparator
 */
@FullyTested
public enum FileType {

    /**
     * Files with this type return {@code true} from method {@link File#isFile()}.
     * <p>
     * The term "normal" is particular to Java.  In UNIX terminology, usually "regualar" is used,
     * but the Java filesystem interface is limited to two file types: directories and
     * non-directories.  This type refers to non-directories.
     *
     * @see #DIRECTORY
     * @see #is(File)
     * @see #from(File)
     */
    NORMAL_FILE {
        @Override
        protected boolean _is(File path) {
            return path.isFile();
        }
    },

    /**
     * Files with this type return {@code true} from method {@link File#isDirectory()}.
     *
     * @see #NORMAL_FILE
     * @see #is(File)
     * @see #from(File)
     */
    DIRECTORY {
        @Override
        protected boolean _is(File path) {
            return path.isDirectory();
        }
    };

    /**
     * Finds the file type for a file path.
     *
     * @param path
     *        file path to find a file type.  Must not be {@code null}.
     *
     * @return matching file type for {@code path}
     *
     * @throws NullPointerException
     *         if {@code path} is {@code null}
     * @throws IOException
     *         if {@code path} fails to match any known file types
     *
     * @see #is(File)
     * @see #NORMAL_FILE
     * @see #DIRECTORY
     */
    public static FileType from(File path)
    throws IOException {
        ObjectArgs.checkNotNull(path, "path");

        for (FileType fileType : FileType.values()) {
            if (fileType._is(path)) {
                return fileType;
            }
        }
        throw new IOException(String.format(
            "Failed to find %s for path: '%s'",
            FileType.class.getSimpleName(), path.getAbsolutePath()));
    }

    protected abstract boolean _is(File path);

    /**
     * Tests if a path matches a file type.
     *
     * @param path
     *        must not be {@code null}
     *
     * @return {@code true} if type of {@code path} matches this {@link FileType}
     *
     * @throws NullPointerException
     *         if {@code path} is {@code null}
     *
     * @see #from(File)
     */
    public boolean is(File path) {
        ObjectArgs.checkNotNull(path, "path");

        return _is(path);
    }
}
