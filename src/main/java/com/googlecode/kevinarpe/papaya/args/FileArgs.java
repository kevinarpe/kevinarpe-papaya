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
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;
import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;

/**
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class FileArgs {

	// Disable default constructor
	private FileArgs() {
	}
	
	/**
	 * Tests if a resource exists.  Resources are usually a configuration file embedded in the JAR
	 * file accessible with a UNIX-like path.
	 * <p>
	 * Example: Imagine you store Internet proxy settings in a properties file.  This file is then
	 * embedded in the JAR and accessible as: {@code "src/main/resources/proxy.properties"}
	 * 
	 * @param clazz ref to class object to find resource
	 * @param resourceName path to resource, e.g., {@code "src/main/resources/proxy.properties"}
     * @param argName argument name for {@code resourceName}, e.g., "outputFile" or "inputFile"
	 * @return result from {@link Class#getResourceAsStream(String)}
	 * @throws NullPointerException if {@code clazz}, {@code resourceName},
	 *         or {@code argName} are null
	 * @throws IllegalArgumentException if {@code resourceName} is empty or whitespace
	 * @throws FileNotFoundException if resource is not found
	 */
	@NotFullyTested
	public static <T> InputStream checkResourceAsStreamExists(
			Class<T> clazz, String resourceName, String argName)
	throws FileNotFoundException {
		ObjectArgs.checkNotNull(clazz, "clazz");
		StringArgs.checkNotEmptyOrWhitespace(resourceName, "resourceName");
		InputStream istream = clazz.getResourceAsStream(resourceName);
		if (null == istream) {
			StringArgs._checkArgNameValid(argName, "argName");
			String msg = String.format(
				"Argument '%s': Resource does not exist: '%s'"
				+ "%n\tClass: %s.%s",
				argName, resourceName,
				clazz.getPackage().getName(), clazz.getSimpleName());
			throw new FileNotFoundException(msg);
		}
		return istream;
	}
	
    /**
     * This is a convenience method for {@link #checkRegularFileExists(File, String)}.
     * 
     * @throws NullPointerException if {@code filePath} is null
     * @throws IllegalArgumentException if {@code filePath} is empty
     */
	@FullyTested
    public static File checkRegularFileExists(String filePath, String argName)
    throws FileNotFoundException {
        StringArgs.checkNotEmpty(filePath, "filePath");
        File file = new File(filePath);
        checkRegularFileExists(file, argName);
        return file;
    }
    
    /**
     * Tests if a regular file exists at a path.  Exception messages distinguish between (a) path
     * does not exist and (b) path exists but is not a regular file.
     * 
     * @param file path to check
     * @param argName argument name for {@code file}, e.g., "outputFile" or "inputFile"
     * @return File input file
     * @throws NullPointerException if {@code file} or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     * @throws FileNotFoundException if {@code file} does not exist,
     *         or if {@code file} exists but is not a regular file
     * @see #checkRegularFileExists(String, String)
     * @see #checkDirectoryExists(File, String)
     */
	@FullyTested
    public static File checkRegularFileExists(File file, String argName)
    throws FileNotFoundException {
        ObjectArgs.checkNotNull(file, "file");
        if (!file.exists()) {
            StringArgs._checkArgNameValid(argName, "argName");
            String msg = String.format(
                "Argument '%s': Regular file does not exist: '%s'%nAbsolute Path: '%s'",
                argName, file, file.getAbsolutePath());
            throw new FileNotFoundException(msg);
        }
        if (!file.isFile()) {
            StringArgs._checkArgNameValid(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists, but is not a regular file: '%s'%nAbsolute Path: '%s'",
                argName, file, file.getAbsolutePath());
            throw new FileNotFoundException(msg);
        }
        return file;
    }
    
    /**
     * This is a convenience method for {@link #checkDirectoryExists(File, String)}.
     * 
     * @throws NullPointerException if {@code dirPath} is null
     * @throws IllegalArgumentException if {@code dirPath} is empty
     */
	@FullyTested
    public static File checkDirectoryExists(String dirPath, String argName)
    throws FileNotFoundException {
        StringArgs.checkNotEmpty(dirPath, "dirPath");
        File dir = new File(dirPath);
        checkDirectoryExists(dir, argName);
        return dir;
    }
    
    /**
     * Tests if a directory exists at a path.  Exception messages distinguish between (a) path does
     * not exist and (b) path exists but is not a directory.
     * 
     * @param dir path to check
     * @param argName argument name for {@code dir}, e.g., "outputDir" or "inputDir"
     * @return File input file
     * @throws NullPointerException if {@code dir} or {@code argName} is null
     * @throws IllegalArgumentException if {@code argName} is empty or whitespace
     * @throws FileNotFoundException if {@code dir} does not exist,
     *         or if {@code dir} exists but is not a directory
     * @see #checkDirectoryExists(String, String)
     * @see #checkRegularFileExists(File, String)
     */
	@FullyTested
    public static File checkDirectoryExists(File dir, String argName)
    throws FileNotFoundException {
        ObjectArgs.checkNotNull(dir, "dir");
        StringArgs.checkNotEmpty(argName, "argName");
        if (!dir.exists()) {
            StringArgs._checkArgNameValid(argName, "argName");
            String msg = String.format(
                "Argument '%s': Directory does not exist: '%s'%nAbsolute Path: '%s'",
                argName, dir, dir.getAbsolutePath());
            throw new FileNotFoundException(msg);
        }
        if (!dir.isDirectory()) {
            StringArgs._checkArgNameValid(argName, "argName");
            String msg = String.format(
                "Argument '%s': Path exists, but is not a directory: '%s'%nAbsolute Path: '%s'",
                argName, dir, dir.getAbsolutePath());
            throw new FileNotFoundException(msg);
        }
        return dir;
    }
}
