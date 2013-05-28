package com.googlecode.kevinarpe.papaya;

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

import java.io.File;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;
import com.googlecode.kevinarpe.papaya.exceptions.PathException;
import com.googlecode.kevinarpe.papaya.exceptions.PathException.PathExceptionReason;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class PathUtils {

    // Disable default constructor
    private PathUtils() {
    }
    
    /**
     * TextFileReader:
     *         Set delimiter
     *             Keep/discard trailing delim
     *         Header/no header
     *         As: single string, list of lines, array of array, list of list, list of maps, "table"
     * @throws IOException 
     */
    
    /**
     * This is a convenience method for {@link #isRootDirectory(File)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static boolean isRootDirectory(String pathname) {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        boolean b = isRootDirectory(path);
        return b;
    }
    
    /**
     * Tests if a directory path is a root directory.  On Windows, there can be many root
     * directories, such A:\, B:\, C:\, D:\, etc.  On UNIX (and its variants, including Mac OS X),
     * there is only one root directory: /
     * 
     * @param path path to test
     * @return true if path is a root directory, else false
     * @throws NullPointerException if {@code path} is {@code null}
     */
    @FullyTested
    public static boolean isRootDirectory(File path) {
        ObjectArgs.checkNotNull(path, "path");
        File absPath = path.getAbsoluteFile();
        File parent = absPath.getParentFile();
        boolean b = (null == parent);
        return b;
    }
    
    /**
     * This is a convenience method for {@link #makeDirectory(File)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File makeDirectory(String pathname)
    throws PathException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        File result = makeDirectory(path);
        return result;
    }
    
    /**
     * Improved version of {@link File#mkdir()} with detailed error reporting via exceptions. 
     * <p>
     * This method does not throw an exception if directory already exists.
     * <p>
     * This method throws an exception if any parent directory does not exist.  Thus, in almost all
     * "real world" cases, you should instead call {@link #makeDirectoryAndParents(File)}.  This
     * may save some sleepness nights when a parent directory is unexpectedly missing.
     * 
     * @param path path for new directory
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws PathException with reason {@link PathExceptionReason#PATH_IS_FILE}
     *         if {@code path} exists as a file,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_DOES_NOT_EXIST} if parent
     *         directory for {@code path} does not exist,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_FILE} if parent
     *         directory for {@code path} exists as a file,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_NON_WRITABLE_DIRECTORY}
     *         if parent directory for {@code path} exists as a directory, but is not writable,
     *         <br>or with reason {@link PathExceptionReason#UNKNOWN} if reason for error
     *         is unknown
     * @see #makeDirectory(String)
     * @see #makeDirectoryAndParents(File)
     */
    @FullyTested
    public static File makeDirectory(File path)
    throws PathException {
        ObjectArgs.checkNotNull(path, "path");
        if (path.mkdir() || path.isDirectory()) {
            return path;
        }
        _throwExceptionAfterMkdirFailed(path, false);
        return path;
    }
    
    /**
     * This is a convenience method for {@link #makeDirectoryAndParents(File)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File makeDirectoryAndParents(String pathname)
    throws PathException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        File result = makeDirectoryAndParents(path);
        return result;
    }
    
    /**
     * Improved version of {@link File#mkdirs()} with detailed error reporting via exceptions. 
     * <p>
     * This method does not throw an exception if directory already exists.
     * 
     * @param path path for new directory
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws PathException with reason {@link PathExceptionReason#PATH_IS_FILE}
     *         if {@code path} exists as a file,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_FILE} if parent
     *         directory for {@code path} exists as a file,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_NON_WRITABLE_DIRECTORY}
     *         if parent directory for {@code path} exists as a directory, but is not writable,
     *         <br>or with reason {@link PathExceptionReason#UNKNOWN} if reason for error
     *         is unknown
     * @see #makeDirectoryAndParents(String)
     * @see #makeDirectory(File)
     */
    @FullyTested
    public static File makeDirectoryAndParents(File path)
    throws PathException {
        ObjectArgs.checkNotNull(path, "path");
        if (path.mkdirs() || path.isDirectory()) {
            return path;
        }
        _throwExceptionAfterMkdirFailed(path, true);
        return path;
    }
    
    private static void _throwExceptionAfterMkdirFailed(File path, boolean parents)
    throws PathException {
        String desc = (parents ? "directory (and parents)" : "directory");
        if (path.isFile()) {
            String msg = String.format(
                "Failed to make %s: Path exists as a file: '%s'",
                desc,
                path.getAbsolutePath());
            throw new PathException(
                PathExceptionReason.PATH_IS_FILE, path, null, msg);
        }
        File absPath = path.getAbsoluteFile();
        File parentPath = absPath.getParentFile();
        // If (null == parentPath), then path is a root directory.
        // But a root directory will never fail in call to mkdir/mkdirs().
        // So we don't need to check if parentPath is null here.
        do {
            if (!parents && !parentPath.exists()) {
                String msg = String.format(
                    "Failed to make %s: '%s'"
                    + "%n\tParent path does not exist: '%s'",
                    desc,
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST, path, parentPath, msg);
            }
            if (parentPath.isFile()) {
                String msg = String.format(
                    "Failed to make %s: '%s'"
                    + "%n\tParent path exists as a file: '%s'",
                    desc,
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PARENT_PATH_IS_FILE, path, parentPath, msg);
            }
            if (parentPath.isDirectory() && !parentPath.canWrite()) {
                String msg = String.format(
                    "Failed to make %s: '%s'"
                    + "%n\tParent path exists as a directory, but is not writable: '%s'",
                    desc,
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path,
                    parentPath,
                    msg);
            }
            // Prepare for next iteration.
            parentPath = parentPath.getParentFile();
        }
        while (parents && null != parentPath);
        
        String msg = String.format(
            "Failed to make %s: '%s'"
            + "%n\tUnknown reason",
            desc,
            path.getAbsolutePath());
        throw new PathException(PathExceptionReason.UNKNOWN, path, null, msg);
    }
    
    /**
     * This is a convenience method for {@link #removeDirectory(File)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static void removeDirectory(String pathname)
    throws PathException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        removeDirectory(path);
    }
    
    /**
     * Improved version of {@link File#delete()} (for directories) with detailed error reporting
     * via exceptions. 
     * <p>
     * This method does not throw an exception if directory does not exist.
     * <p>
     * This method throws an exception if the directory is not empty -- contains any files or
     * sub-directories.
     * 
     * @param path path to empty directory to remove
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws PathException with reason {@link PathExceptionReason#PATH_IS_FILE}
     *         if {@code path} exists as a file,
     *         <br>or with reason {@link PathExceptionReason#PATH_IS_ROOT_DIRECTORY}
     *         if {@code path} exists as a root directory,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_DOES_NOT_EXIST} if parent
     *         directory for {@code path} does not exist,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_NON_WRITABLE_DIRECTORY}
     *         if parent directory for {@code path} exists as a directory, but is not writable,
     *         <br>or with reason {@link PathExceptionReason#UNKNOWN} if reason for error
     *         is unknown
     * @see #removeDirectory(String)
     */
    @FullyTested
    public static void removeDirectory(File path)
    throws PathException {
        ObjectArgs.checkNotNull(path, "path");
        if ((path.isDirectory() && path.delete()) || !path.exists()) {
            return;
        }
        if (path.isFile()) {
            String msg = String.format(
                "Failed to remove directory: Path exists as a file: '%s'",
                path.getAbsolutePath());
            throw new PathException(
                PathExceptionReason.PATH_IS_FILE, path, null, msg);
        }
        else {
            File absPath = path.getAbsoluteFile();
            File parentPath = absPath.getParentFile();
            if (null == parentPath) {
                String msg = String.format(
                    "Failed to remove directory: Path exists as a root directory: '%s'",
                    path.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PATH_IS_ROOT_DIRECTORY, path, null, msg);
            }
            if (parentPath.isDirectory() && !parentPath.canWrite()) {
                String msg = String.format(
                    "Failed to remove directory: '%s'"
                    + "%n\tParent path exists as a directory, but is not writable: '%s'",
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path,
                    parentPath,
                    msg);
            }
        }
        String msg = String.format(
            "Failed to remove directory: '%s'"
            + "%n\tUnknown reason",
            path.getAbsolutePath());
        throw new PathException(PathExceptionReason.UNKNOWN, path, null, msg);
    }
    
    /**
     * This is a convenience method for {@link #removeFile(File)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static void removeFile(String pathname)
    throws PathException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        removeFile(path);
    }
    
    /**
     * Improved version of {@link File#delete()} (for files) with detailed error reporting
     * via exceptions. 
     * <p>
     * This method does not throw an exception if directory does not exist.
     * 
     * @param path path to file to remove
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws PathException with reason {@link PathExceptionReason#PATH_IS_DIRECTORY}
     *         if {@code path} exists as a directory,
     *         <br>or with reason {@link PathExceptionReason#PARENT_PATH_IS_NON_WRITABLE_DIRECTORY}
     *         if parent directory for {@code path} exists as a directory, but is not writable,
     *         <br>or with reason {@link PathExceptionReason#UNKNOWN} if reason for error
     *         is unknown
     * @see #removeFile(String)
     */
    @FullyTested
    public static void removeFile(File path)
    throws PathException {
        ObjectArgs.checkNotNull(path, "path");
        if ((path.isFile() && path.delete()) || !path.exists()) {
            return;
        }
        if (path.isDirectory()) {
            String msg = String.format(
                "Failed to remove file: Path exists as a directory: '%s'",
                path.getAbsolutePath());
            throw new PathException(
                PathExceptionReason.PATH_IS_DIRECTORY, path, null, msg);
        }
        else {
            File absPath = path.getAbsoluteFile();
            File parentPath = absPath.getParentFile();
            if (parentPath.isDirectory() && !parentPath.canWrite()) {
                String msg = String.format(
                    "Failed to remove file: '%s'"
                    + "%n\tParent path exists as a directory, but is not writable: '%s'",
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path,
                    parentPath,
                    msg);
            }
        }
        String msg = String.format(
            "Failed to remove file: '%s'"
            + "%n\tUnknown reason",
            path.getAbsolutePath());
        throw new PathException(PathExceptionReason.UNKNOWN, path, null, msg);
    }
}
