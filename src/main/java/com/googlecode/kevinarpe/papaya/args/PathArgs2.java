package com.googlecode.kevinarpe.papaya.args;

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
import java.io.InputStream;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;
import com.googlecode.kevinarpe.papaya.exceptions.DirectoryFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.DirectoryNotFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.exceptions.RegularFileFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.RegularFileNotFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.ClassResourceNotFoundException;

/**
 * Static methods to check {@link File} (path) arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class PathArgs2 {

    // Disable default constructor
    private PathArgs2() {
    }
    
    /**
     * Tests if a resource exists via {@link Class#getResourceAsStream(String)}.  Resources are
     * usually configuration files with text or binary data, embedded in a JAR file, and accessible
     * with a UNIX/POSIX-like path.
     * <p>
     * For projects using Maven, resources are normally stored in two locations:
     * <ul>
     *   <li>Class resources: {@code src/main/resources}</li>
     *   <li>Test class resources: {@code src/test/resources}</li>
     * </ul>
     * <p>
     * Example: Internet proxy settings may be stored in a properties file.  In the project, the
     * properties file is saved to path {@code "src/main/resources/proxy.properties"}.  This file
     * is then embedded as a resource in a JAR and accessible at path: {@code "/proxy.properties"}
     * <p>
     * Since {@link Class#getResourceAsStream(String)} is used to find the resource, the handling
     * of relative and absolute paths is surprisingly different.  (Absolute paths have a leading
     * "/" character, and relative paths do not.)  Relative paths have the class package name added
     * as a prefix.
     * <p>
     * Example: For class {@code foo.bar.Baz}, relative resource path {@code "data/xyz.txt"}
     * becomes absolute resource path {@code "/foo/bar/data/xyz.txt"}
     * 
     * @param clazz ref to class object to find resource
     * @param pathname relative path (without leading "/") to resource,
     *        e.g., {@code "proxy.properties"},
     *        <br>or absolute path (with leading "/") to resource,
     *        e.g., {@code "/proxy.properties"}
     * @param pathnameArgName argument name for {@code pathname}, e.g., "outputFile" or "inputFile"
     * @return result from {@link Class#getResourceAsStream(String)}
     * @throws NullPointerException if {@code clazz} or {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     * @throws ClassResourceNotFoundException if resource is not found 
     */
    @FullyTested
    public static InputStream checkClassResourceAsStreamExists(
            Class<?> clazz, String pathname, String pathnameArgName)
    throws ClassResourceNotFoundException {
        ObjectArgs.checkNotNull(clazz, "clazz");
        StringArgs.checkNotEmpty(pathname, "pathname");
        // Ref: http://fuyun.org/2009/11/how-to-read-input-files-in-maven-junit/
        // Ref: http://stackoverflow.com/questions/6608795/what-is-the-difference-between-class-getresource-and-classloader-getresource
        InputStream istream = clazz.getResourceAsStream(pathname);
        if (null == istream) {
            String w = StringArgs._getArgNameWarning(pathnameArgName, "pathnameArgName");
            String msg = String.format(
                "Argument '%s': Resource for class %s does not exist: '%s'%s",
                pathnameArgName, clazz.getName(), pathname, w);
            throw new ClassResourceNotFoundException(clazz, pathname, msg);
        }
        return istream;
    }
    
    /**
     * This is a convenience method for {@link #checkRegularFileExists(File, String)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File checkRegularFileExists(String pathname, String argName)
    throws PathException2 {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        checkRegularFileExists(path, argName);
        return path;
    }
    
    /**
     * Tests if a regular file exists at a path.  Exception messages distinguish between paths that
     * (a) do not exist, and (b) exist, but are not regular files.
     * 
     * @param path path to check
     * @param argName argument name for {@code path}, e.g., "outputFile" or "inputFile"
     * @return File validated path
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws RegularFileNotFoundException if {@code path} does not exist,
     *         <br>or if {@code path} exists but is not a regular file
     * @see #checkRegularFileExists(String, String)
     * @see #checkNotRegularFile(File, String)
     * @see #checkDirectoryExists(File, String)
     */
    @FullyTested
    public static File checkRegularFileExists(File path, String argName)
    throws PathException2 {
        ObjectArgs.checkNotNull(path, "path");
        if (!path.exists()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path does not exist: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, msg);
        }
        if (path.isDirectory()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists as a directory: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_IS_DIRECTORY, path, null, msg);
        }
        return path;
    }
    
    /**
     * This is a convenience method for {@link #checkNotRegularFile(File, String)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File checkNotRegularFile(String pathname, String argName)
    throws PathException2 {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        checkNotRegularFile(path, argName);
        return path;
    }
    
    /**
     * Tests if a regular file does not exist at a path.  In other words, the path must not exist,
     * <br>or exists only as a directory.  Understanding the purpose of this method is more subtle than
     * its opposing relative, {@link #checkRegularFileExists(File, String)}.
     * <p>
     * Example: Prior to creating a directory, check the path is not a regular file.
     * 
     * @param path path to check
     * @param argName argument name for {@code path}, e.g., "outputFile" or "inputFile"
     * @return File validated path
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws RegularFileFoundException if {@code path} exists and is a regular file
     * @see #checkNotRegularFile(String, String)
     * @see #checkRegularFileExists(File, String)
     */
    @FullyTested
    public static File checkNotRegularFile(File path, String argName)
    throws PathException2 {
        ObjectArgs.checkNotNull(path, "path");
        if (path.isFile()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists as a regular file: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, msg);
        }
        return path;
    }
    
    /**
     * This is a convenience method for {@link #checkDirectoryExists(File, String)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File checkDirectoryExists(String pathname, String argName)
    throws PathException2 {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        checkDirectoryExists(path, argName);
        return path;
    }
    
    /**
     * Tests if a directory exists at a path.  Exception messages distinguish between paths that
     * (a) do not exist and (b) exist, but are not directories.
     * 
     * @param path path to check
     * @param argName argument name for {@code path}, e.g., "outputDir" or "inputDir"
     * @return File validated path
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws DirectoryNotFoundException if {@code path} does not exist,
     *         <br>or if {@code path} exists but is not a directory
     * @see #checkDirectoryExists(String, String)
     * @see #checkNotDirectory(File, String)
     * @see #checkRegularFileExists(File, String)
     */
    @FullyTested
    public static File checkDirectoryExists(File path, String argName)
    throws PathException2 {
        ObjectArgs.checkNotNull(path, "path");
        StringArgs.checkNotEmpty(argName, "argName");
        if (!path.exists()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path does not exist: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, msg);
        }
        if (path.isFile()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists as a regular file: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, msg);
        }
        return path;
    }
    
    /**
     * This is a convenience method for {@link #checkNotDirectory(File, String)}.
     * 
     * @throws NullPointerException if {@code pathname} is {@code null}
     * @throws IllegalArgumentException if {@code pathname} is empty
     */
    @FullyTested
    public static File checkNotDirectory(String pathname, String argName)
    throws PathException2 {
        StringArgs.checkNotEmpty(pathname, "pathname");
        File path = new File(pathname);
        checkNotDirectory(path, argName);
        return path;
    }
    
    /**
     * Tests if a directory does not exist at a path.  In other words, the path must not exist, or
     * exists only as a regular file.  Understanding the purpose of this method is more subtle than
     * its opposing relative, {@link #checkDirectoryExists(File, String)}.
     * <p>
     * Example: Prior to writing a regular file, check the path is not a directory.
     * 
     * @param path path to check
     * @param argName argument name for {@code path}, e.g., "outputFile" or "inputFile"
     * @return File validated path
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws DirectoryFoundException if {@code path} exists and is a directory
     * @see #checkNotDirectory(String, String)
     * @see #checkDirectoryExists(File, String)
     */
    @FullyTested
    public static File checkNotDirectory(File path, String argName)
    throws PathException2 {
        ObjectArgs.checkNotNull(path, "path");
        if (path.isDirectory()) {
            String w = StringArgs._getArgNameWarning(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists and is a directory: '%s'%s",
                argName, path.getAbsolutePath(), w);
            throw new PathException2(
                PathExceptionReason.PATH_IS_DIRECTORY, path, null, msg);
        }
        return path;
    }
}
