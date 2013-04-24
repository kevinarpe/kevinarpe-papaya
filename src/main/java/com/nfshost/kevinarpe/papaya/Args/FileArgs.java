/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class FileArgs {

    /**
     * This is a convenience method for {@link #checkRegularFileExists(File)}.
     * 
     * @throws IllegalArgumentException if {@code filePath} is empty
     */
    public static File checkRegularFileExists(String filePath)
    throws FileNotFoundException {
        StringArgs.checkNotEmpty(filePath, "filePath");
        File file = new File(filePath);
        checkRegularFileExists(file);
        return file;
    }
    
    /**
     * Tests if a regular file exists at a path.  Exception messages distinguish between (a) path
     * does not exist and (b) path exists but is not a regular file.
     * 
     * @param file path to check
     * @return File input file
     * @throws NullPointerException if {@code file} is null
     * @throws FileNotFoundException if {@code file} does not exist,
     *         or if {@code file} exists but is not a regular file
     * @see #checkRegularFileExists(String)
     */
    public static File checkRegularFileExists(File file)
    throws FileNotFoundException {
        ObjectArgs.checkNotNull(file, "file");
        if (!file.exists()) {
            String s = String.format(
                "Regular file does not exist: '%s'%nAbsolute Path: '%s'",
                file, file.getAbsolutePath());
            throw new FileNotFoundException(s);
        }
        if (!file.isFile()) {
            String s = String.format(
                "Path exists, but is not a regular file: '%s'%nAbsolute Path: '%s'",
                file, file.getAbsolutePath());
            throw new FileNotFoundException(s);
        }
        return file;
    }
    
    /**
     * This is a convenience method for {@link #checkDirectoryExists(File)}.
     * 
     * @throws IllegalArgumentException if {@code dirPath} is empty
     */
    public static File checkDirectoryExists(String dirPath)
    throws FileNotFoundException {
        StringArgs.checkNotEmpty(dirPath, "dirPath");
        File dir = new File(dirPath);
        checkDirectoryExists(dir);
        return dir;
    }
    
    /**
     * Tests if a directory exists at a path.  Exception messages distinguish between (a) path
     * does not exist and (b) path exists but is not a directory.
     * 
     * @param dir path to check
     * @return File input file
     * @throws NullPointerException if {@code dir} is null
     * @throws FileNotFoundException if {@code dir} does not exist,
     *         or if {@code dir} exists but is not a directory
     */
    public static File checkDirectoryExists(File dir)
    throws FileNotFoundException {
        ObjectArgs.checkNotNull(dir, "dir");
        if (!dir.exists()) {
            String s = String.format(
                "Directory does not exist: '%s'%nAbsolute Path: '%s'",
                dir, dir.getAbsolutePath());
            throw new FileNotFoundException(s);
        }
        if (!dir.isDirectory()) {
            String s = String.format(
                "Path exists, but is not a directory: '%s'%nAbsolute Path: '%s'",
                dir, dir.getAbsolutePath());
            throw new FileNotFoundException(s);
        }
        return dir;
    }
}
