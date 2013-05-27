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
import java.io.IOException;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.args.StringArgs;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2.PathExceptionReason;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class DirectoryUtils2 {

    // Disable default constructor
    private DirectoryUtils2() {
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
    public static boolean isRootDirectory(File path) {
        ObjectArgs.checkNotNull(path, "path");
        File absPath = path.getAbsoluteFile();
        File parent = absPath.getParentFile();
        boolean b = (null == parent);
        return b;
    }
    
    public static File makeDirectory(String pathname)
    throws IOException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        File result = makeDirectory(path);
        return result;
    }
    
    public static File makeDirectory(File path)
    throws IOException {
        ObjectArgs.checkNotNull(path, "path");
        if (path.mkdir() || path.isDirectory()) {
            return path;
        }
        _throwExceptionAfterMkdirFailed(path, false);
        return path;
    }
    
    public static File makeDirectoryAndParents(String pathname)
    throws IOException {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        File result = makeDirectoryAndParents(path);
        return result;
    }
    
    public static File makeDirectoryAndParents(File path)
    throws IOException {
        ObjectArgs.checkNotNull(path, "path");
        if (path.mkdirs() || path.isDirectory()) {
            return path;
        }
        _throwExceptionAfterMkdirFailed(path, true);
        return path;
    }
    
    private static void _throwExceptionAfterMkdirFailed(File path, boolean parents)
    throws PathException2 {
        String desc = (parents ? "directory (and parents)" : "directory");
        if (path.isFile()) {
            String msg = String.format(
                "Failed to make %s: Path exists as a regular file: '%s'",
                desc,
                path.getAbsolutePath());
            throw new PathException2(
                PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, msg);
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
                throw new PathException2(
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST, path, parentPath, msg);
            }
            if (parentPath.isFile()) {
                String msg = String.format(
                    "Failed to make %s: '%s'"
                    + "%n\tParent path exists as a regular file: '%s'",
                    desc,
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException2(
                    PathExceptionReason.PARENT_PATH_IS_REGULAR_FILE, path, parentPath, msg);
            }
            if (parentPath.isDirectory() && !parentPath.canWrite()) {
                String msg = String.format(
                    "Failed to make %s: '%s'"
                    + "%n\tParent path exists as a directory, but is not writable: '%s'",
                    desc,
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException2(
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
        throw new PathException2(PathExceptionReason.UNKNOWN, path, null, msg);
    }
    
    public static void removeDirectory(File path)
    throws PathException2 {
        ObjectArgs.checkNotNull(path, "path");
        if ((path.isDirectory() && path.delete()) || !path.exists()) {
            return;
        }
        if (path.isFile()) {
            String msg = String.format(
                "Failed to remove directory: Path exists as a regular file: '%s'",
                path.getAbsolutePath());
            throw new PathException2(
                PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, msg);
        }
        else {
            File absPath = path.getAbsoluteFile();
            File parentPath = absPath.getParentFile();
            if (null == parentPath) {
                String msg = String.format(
                    "Failed to remove directory: Path exists as a root directory: '%s'",
                    path.getAbsolutePath());
                throw new PathException2(
                    PathExceptionReason.PATH_IS_ROOT_DIRECTORY, path, null, msg);
            }
            if (parentPath.isDirectory() && !parentPath.canWrite()) {
                String msg = String.format(
                    "Failed to remove directory: '%s'"
                    + "%n\tParent path exists as a directory, but is not writable: '%s'",
                    path.getAbsolutePath(),
                    parentPath.getAbsolutePath());
                throw new PathException2(
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
        throw new PathException2(PathExceptionReason.UNKNOWN, path, null, msg);
    }
    
//    /**
//     * This is a convenience method for {@link #createDirectory(File)}.
//     * 
//     * @throws NullPointerException if {@code dirPath} is {@code null}
//     * @throws IllegalArgumentException if {@code dirPath} is empty
//     */
//    public static File createDirectory(String dirPath)
//    throws IOException {
//        // We allow all whitespace strings here.  Crazy, but possible.
//        StringArgs.checkNotEmpty(dirPath, "dirPath");
//        File dir = new File(dirPath);
//        _createDirectory(dir);
//        return dir;
//    }
//    
//    /**
//     * Improved version of {@link File#mkdir()}. 
//     * Slower, but safe for race conditions -- competing threads or processes trying to create the
//     * same directory.  Also, this method provides detailed exception messages.
//     * <p> 
//     * This method does not throw an exception if directory already exists.
//     * 
//     * @param dir path for new directory
//     * @throws NullPointerException if {@code dir} is {@code null}
//     * @throws IOException if {@code dir} exists but not a directory,
//     *         <br>or if parent for {@code dir} is not a directory,
//     *         <br>or if failed to create directory at path {@code dir}
//     * @see #createDirectory(String)
//     * @see #createDirectories(File)
//     */
//    public static File createDirectory(File dir)
//    throws IOException {
//        ObjectArgs.checkNotNull(dir, "dir");
//        _createDirectory(dir);
//        return dir;
//    }
//    
//    /*
//    public static File deleteDirectory(String dirPath)
//    throws IOException {
//        // We allow all whitespace strings here.  Crazy, but possible.
//        StringArgs.checkNotEmpty(dirPath, "dirPath");
//        File dir = new File(dirPath);
//        _deleteDirectory(dir);
//        return dir;
//    }
//    
//    public static File deleteDirectory(File dir)
//    throws IOException {
//        ObjectArgs.checkNotNull(dir, "dir");
//        _deleteDirectory(dir);
//        return dir;
//    }
//    */
//    
//    /**
//     * This is a convenience method for {@link #createDirectories(File)}.
//     * 
//     * @throws NullPointerException if {@code dirPath} is {@code null}
//     * @throws IllegalArgumentException if {@code dirPath} is empty
//     */
//    public static File createDirectories(String dirPath)
//    throws IOException {
//        // We allow all whitespace strings here.  Crazy, but possible.
//        StringArgs.checkNotEmpty(dirPath, "dirPath");
//        File dir = new File(dirPath);
//        createDirectories(dir);
//        return dir;
//    }
//    
//    /**
//     * Improved version of {@link File#mkdirs()}. 
//     * Slower, but safe for race conditions -- competing threads or processes trying to create the
//     * same directory.  Also, this method provides detailed exception messages.
//     * <p>
//     * This method does not throw an exception if directory or any parents already exist.
//     * 
//     * @param dir path for new directory
//     * @throws NullPointerException if {@code dir} is {@code null}
//     * @throws IOException if {@code dir} exists but not a directory,
//     *         <br>or if parent for {@code dir} is not a directory,
//     *         <br>or if failed to create directory at path {@code dir}
//     * @see #createDirectories(String)
//     * @see #createDirectory(File)
//     */
//    public static File createDirectories(File dir)
//    throws IOException {
//        ObjectArgs.checkNotNull(dir, "dir");
//        _createDirectories(dir);
//        return dir;
//    }
//    
//    private static void _createDirectories(File dir)
//    throws IOException {
//        File absDir = dir.getAbsoluteFile();
//        File parent = absDir.getParentFile();
//        if (null != parent) {
//            _createDirectories(parent);
//        }
//        _createDirectory(dir);
//    }
//    
//    /**
//     * This is an unchecked version of {@link #createDirectory(File)}
//     */
//    private static void _createDirectory(File dir)
//    throws IOException {
//        if (dir.mkdir()) {
//            return;
//        }
//        if (dir.exists()) {
//            if (dir.isDirectory()) {
//                return;
//            }
//            else {
//                String msg = String.format(
//                    "Path exists, but is not a directory: '%s'",
//                    dir.getAbsoluteFile());
//                throw new IOException(msg);
//            }
//        }
//        else {
//            File absDir = dir.getAbsoluteFile();
//            File parent = absDir.getParentFile();
//            String msg = null;
//            if (null == parent) {
//                msg = String.format("%n\tCannot create root directories");
//            }
//            else {
//                msg = _createExceptionMessage(parent);
//            }
//            msg = String.format("Failed to create directory: '%s'%s", absDir, msg);
//            throw new IOException(msg);
//        }
//    }
//
//    private static void _deleteDirectory(File dir)
//    throws IOException {
//        _checkIfPathExistsButNotDir(dir);
//        if (dir.delete() || !dir.exists()) {
//            return;
//        }
//        _checkIfPathExistsButNotDir(dir);
//        File absDir = dir.getAbsoluteFile();
//        File parent = absDir.getParentFile();
//        String msg = null;
//        if (null == parent) {
//            msg = String.format("%n\tCannot delete root directories");
//        }
//        else {
//            msg = _createExceptionMessage(parent);
//        }
//        msg = String.format("Failed to delete directory: '%s'%s", absDir, msg);
//        throw new IOException(msg);
//    }
//    
//    private static void _checkIfPathExistsButNotDir(File dir)
//        throws IOException {
//        if (!dir.isDirectory() && dir.exists()) {
//            String msg = String.format(
//                "Path exists, but is not a directory: '%s'",
//                dir.getAbsoluteFile());
//            throw new IOException(msg);
//        }
//    }
//    
//    private static String _createExceptionMessage(File parent) {
//        String msg = null;
//        if (!parent.exists()) {
//            msg = String.format(
//                "%n\tParent directory does not exist: '%s'",
//                parent.getAbsoluteFile());
//        }
//        else if (!parent.isDirectory()) {
//            msg = String.format(
//                "%n\tParent path exists, but is not a directory: '%s'",
//                parent.getAbsoluteFile());
//        }
//        else if (!parent.canWrite()) {
//            msg = String.format(
//                "%n\tParent directory is not writable: '%s'",
//                parent.getAbsoluteFile());
//        }
//        else {
//            // The parent path is a directory,
//            // and another process or thread did NOT create the path.
//            // Method File.mkdir() failed for other unknown reasons: access/security?
//            msg = String.format("%n\tUnknown reason");
//        }
//        return msg;
//    }
}
