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

package com.nfshost.kevinarpe.papaya;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.base.Joiner;

public final class FileUtil {
	
	private static final FileSystem _DEFAULT_FILE_SYSTEM;
	private static final Charset _DEFAULT_CHARSET;
	
	static {
		_DEFAULT_FILE_SYSTEM = FileSystems.getDefault();
		_DEFAULT_CHARSET = Charset.defaultCharset();
	}
	
	/**
	 * Tests if a regular file exists at a path.  Exception messages distinguish between (a) path
	 * does not exist and (b) path exists but is not a regular file.
	 * 
	 * @param filePath path to check
	 * @param linkOptionArr optional list of {@link LinkOption}
	 * @return Path ref for input path
	 * @throws NullPointerException if {@code filePath} is null,
	 *         or if {@code linkOptionArr} contains nulls
	 * @throws IllegalArgumentException if {@code filePath} is empty
	 * @throws FileNotFoundException if {@code filePath} does not exist,
	 *         or if {@code filePath} exists but is not a regular file
	 */
	public static Path staticCheckRegularFileExists(
			String filePath, LinkOption... linkOptionArr)
	throws FileNotFoundException {
		ArgUtil.staticCheckStringNotEmpty(filePath, "filePath");
		ArgUtil.staticCheckArrayElementsNotNull(linkOptionArr, "linkOptionArr");
		
		Path file = _DEFAULT_FILE_SYSTEM.getPath(filePath);
		BasicFileAttributes fileAttr = null;
		try {
			fileAttr = Files.readAttributes(file, BasicFileAttributes.class, linkOptionArr);
		}
		catch (IOException e) {
			String s = String.format(
				"Regular file does not exist: '%s'%s",
				filePath, _staticLinkOptionArrToString(linkOptionArr));
			throw new FileNotFoundException(s);
		}
		if (!fileAttr.isRegularFile()) {
			String s = String.format(
				"Path exists, but is not a regular file: '%s'%s",
				filePath, _staticLinkOptionArrToString(linkOptionArr));
			throw new FileNotFoundException(s);
		}
		return file;
	}
	
	private static String _staticLinkOptionArrToString(LinkOption[] linkOptionArr) {
		if (0 == linkOptionArr.length) {
			return "";
		}
		String s = String.format("%n\tLinkOption[]: %s", Joiner.on(", ").join(linkOptionArr));
		return s;
	}
	
	/**
	 * Tests if a directory exists at a path.  Exception messages distinguish between (a) path
	 * does not exist and (b) path exists but is not a directory.
	 * 
	 * @param dirPath path to check
	 * @param linkOptionArr optional list of {@link LinkOption}
	 * @return Path ref for input path
	 * @throws NullPointerException if {@code dirPath} is null,
	 *         or if {@code linkOptionArr} contains nulls
	 * @throws IllegalArgumentException if {@code dirPath} is empty
	 * @throws FileNotFoundException if {@code dirPath} does not exist,
	 *         or if {@code dirPath} exists but is not a regular file
	 */
	public static Path staticCheckDirectoryExists(String dirPath, LinkOption... linkOptionArr)
	throws FileNotFoundException {
		ArgUtil.staticCheckStringNotEmpty(dirPath, "path");
		ArgUtil.staticCheckArrayElementsNotNull(linkOptionArr, "linkOptionArr");
		
		Path dir = _DEFAULT_FILE_SYSTEM.getPath(dirPath);
		BasicFileAttributes fileAttr = null;
		try {
			fileAttr = Files.readAttributes(dir, BasicFileAttributes.class, linkOptionArr);
		}
		catch (IOException e) {
			String s = String.format(
				"Directory does not exist: '%s'%s",
				dirPath, _staticLinkOptionArrToString(linkOptionArr));
			throw new FileNotFoundException(s);
		}
		if (!fileAttr.isDirectory()) {
			String s = String.format(
				"Path exists, but is not a directory: '%s'%s",
				dirPath, _staticLinkOptionArrToString(linkOptionArr));
			throw new FileNotFoundException(s);
		}
		return dir;
	}
	
	/**
	 * This is a convenience method for {@link #staticCreateDirectory(Path, FileAttribute...)}.
	 */
	public static Path staticCreateDirectory(String dirPath, FileAttribute<?>... fileAttrArr)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(dirPath, "dirPath");
		Path dir = _DEFAULT_FILE_SYSTEM.getPath(dirPath);
		staticCreateDirectory(dir, fileAttrArr);
		return dir;
	}
	
	/**
	 * Improved version of {@link Files#createDirectory(Path, FileAttribute...)}. 
	 * Slower, but safe for race conditions -- competing threads or processes trying to create the
	 * same directory.  Also, this method provides more detailed exception messages than the
	 * standard version from NIO2.
	 * 
	 * <p>This method does not throw an exception if directory already exists.
	 * 
	 * @param dir path for new directory
	 * @param fileAttrArr optional list of file attributes to set when new directory is created
	 * @throws NullPointerException if {@code dir} is null,
	 *         or {@code fileAttrArr} contains null elements
	 * @throws IOException if {@code dir} exists but not a directory,
	 *         or if parent for {@code dir} is not a directory,
	 *         or if failed to create directory at path {@code dir}
	 * @see #staticCreateDirectory(String, FileAttribute...)
	 * @see #staticCreateDirectories(Path, FileAttribute...)
	 */
	public static void staticCreateDirectory(Path dir, FileAttribute<?>... fileAttrArr)
	throws IOException {
		ArgUtil.staticCheckNotNull(dir, "dir");
		ArgUtil.staticCheckArrayElementsNotNull(fileAttrArr, "fileAttrArr");
		_staticCreateDirectory(dir, fileAttrArr);
	}
	
	/**
	 * This is an unchecked version of {@link #staticCreateDirectory(String, FileAttribute...)}
	 */
	private static void _staticCreateDirectory(Path dir, FileAttribute<?>[] fileAttrArr)
	throws IOException {
		// We use this twice below.
		final String PATH_EXISTS_BUT_NOT_DIR_FORMAT_STR =
			"Path exists, but is not a directory: '%s'";
		
		BasicFileAttributes fileAttr = null;
		try {
			// Mark #1
			fileAttr = Files.readAttributes(dir, BasicFileAttributes.class);
			// Only the previous line will throw an IOException.
			// We put this if..then block here for readability.
			if (!fileAttr.isDirectory()) {
				throw new IOException(String.format(
					PATH_EXISTS_BUT_NOT_DIR_FORMAT_STR, dir));
			}
		}
		// We don't know why we fail here.  Options:
		// (1) Insufficient access on file system
		// (2) Other Java security restrictions
		// (3) Path does not exist
		catch (IOException e) {
			try {
				// Mark #2
				Files.createDirectory(dir, fileAttrArr);
			}
			catch (IOException e2) {
				// Check for race condition between Mark #1 and Mark #2:
				// Another process or thread created the path.
				try {
					fileAttr = Files.readAttributes(dir, BasicFileAttributes.class);
					// Only the previous line will throw an IOException.
					// We put this if..then block here for readability.
					// Another process or thread created the path.  Confirm it is a directory.
					if (!fileAttr.isDirectory()) {
						throw new IOException(String.format(
							PATH_EXISTS_BUT_NOT_DIR_FORMAT_STR, dir));
					}
				}
				catch (IOException e3) {
					// Check if the parent path is a directory.
					Path parent = dir.getParent();
					if (null != parent) {
						BasicFileAttributes parentFileAttr = null;
						try {
							parentFileAttr =
								Files.readAttributes(parent, BasicFileAttributes.class);
						}
						catch (IOException e4) {
							String msg = String.format(
								"Failed to create directory: '%s'"
								+ "%n\tParent directory does not exist: '%s'",
								dir, parent);
							throw new IOException(msg, e2);  // <-- do not use e3 or e4 here!
						}
						if (!parentFileAttr.isDirectory()) {
							String msg = String.format(
								"Failed to create directory: '%s'"
								+ "%n\tParent path exists, but is not a directory: '%s'",
								dir, parent);
							throw new IOException(msg, e2);  // <-- do not use e3 or e4 here!
						}
					}
					// The parent path is a directory,
					// and another process or thread did NOT create the path.
					// Method Files.createDirectory() failed for other reasons: access/security?
					String msg = String.format("Failed to create directory: '%s'", dir);
					throw new IOException(msg, e2);  // <-- do not use e3 here!
				}
			}
		}
	}
	
	/**
	 * This is a convenience method for {@link #staticCreateDirectories(Path, FileAttribute...)}.
	 */
	public static Path staticCreateDirectories(String dirPath, FileAttribute<?>... fileAttrArr)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(dirPath, "dirPath");
		Path dir = _DEFAULT_FILE_SYSTEM.getPath(dirPath);
		staticCreateDirectories(dir, fileAttrArr);
		return dir;
	}
	
	/**
	 * Improved version of {@link Files#createDirectories(Path, FileAttribute...)}. 
	 * Slower, but safe for race conditions -- competing threads or processes trying to create the
	 * same directory.  Also, this method provides more detailed exception messages than the
	 * standard version from NIO2.
	 * 
	 * <p>This method does not throw an exception if directory (or parent) already exists.
	 * 
	 * @param dir path for new directory
	 * @param fileAttrArr optional list of file attributes to set when new directories are created
	 * @throws NullPointerException if {@code dir} or {@code fileAttrArr} is null,
	 *         or {@code fileAttrArr} contains null elements
	 * @throws IOException if {@code dir} exists but not a directory,
	 *         or if parent for {@code dir} is not a directory,
	 *         or if failed to create directory at path {@code dir}
	 * @see #staticCreateDirectories(String, FileAttribute...)
	 * @see #staticCreateDirectory(Path, FileAttribute...)
	 */
	public static void staticCreateDirectories(Path dir, FileAttribute<?>... fileAttrArr)
	throws IOException {
		ArgUtil.staticCheckNotNull(dir, "dir");
		ArgUtil.staticCheckArrayElementsNotNull(fileAttrArr, "fileAttrArr");
		_staticCreateDirectories(dir, fileAttrArr);
	}
	
	private static void _staticCreateDirectories(Path dir, FileAttribute<?>[] fileAttrArr)
	throws IOException {
		Path parent = dir.getParent();
		if (null != parent) {
			_staticCreateDirectories(parent, fileAttrArr);
		}
		_staticCreateDirectory(dir, fileAttrArr);
	}
	
	// TODO: Check NotRegularFile
	// TODO: Check NotDirectory
	// TODO: Read file as (x) string, (x) lines, list of maps, array of arrays, list of lists, map + list of lists
	// TODO: Write file...
	
	/**
	 * This is a convenience method for {@link #staticReadTextFileAsString(Path, Charset)}.
	 */
	public static String staticReadTextFileAsString(String filePath)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(filePath, "filePath");
		Path file = _DEFAULT_FILE_SYSTEM.getPath(filePath);
		String s = staticReadTextFileAsString(file, _DEFAULT_CHARSET);
		return s;
	}
	
	/**
	 * This is a convenience method for {@link #staticReadTextFileAsString(Path, Charset)}.
	 */
	public static String staticReadTextFileAsString(Path file)
	throws IOException {
		String s = staticReadTextFileAsString(file, _DEFAULT_CHARSET);
		return s;
	}
	
	/**
	 * Reads text from a file very safely.
	 * 
	 * @param file path for file 
	 * @param charset characer set to use for decoding
	 * @return entire file as a single string, including (unconverted) newlines
	 * @throws NullPointerException if {@code file} or {@code charset} is null
	 * @throws IOException if failure to open or read from file
	 */
	public static String staticReadTextFileAsString(Path file, Charset charset)
	throws IOException {
		ArgUtil.staticCheckNotNull(file, "file");
		ArgUtil.staticCheckNotNull(charset, "charset");
		byte[] byteArr = null;
		try {
			byteArr = Files.readAllBytes(file);
		}
		catch (IOException e) {
			String msg = String.format(
				"Failed to read to file: '%s'"
				+ "%n\tCharset: '%s'",
				file, charset);
			throw new IOException(msg, e);
		}
		String s = new String(byteArr, charset);
		return s;
	}
	
	/**
	 * This is a convenience method for {@link #staticReadTextFileAsStringList(Path, Charset)}.
	 */
	public static ArrayList<String> staticReadTextFileAsStringList(String filePath)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(filePath, "filePath");
		Path file = _DEFAULT_FILE_SYSTEM.getPath(filePath);
		ArrayList<String> lineList = staticReadTextFileAsStringList(file, _DEFAULT_CHARSET);
		return lineList;
	}
	
	/**
	 * This is a convenience method for {@link #staticReadTextFileAsStringList(Path, Charset)}.
	 */
	public static ArrayList<String> staticReadTextFileAsStringList(Path file)
	throws IOException {
		ArrayList<String> lineList = staticReadTextFileAsStringList(file, _DEFAULT_CHARSET);
		return lineList;
	}

	/**
	 * Reads text from a file very safely.
	 * 
	 * @param file path for file 
	 * @param charset characer set to use for decoding
	 * @return entire file as a modifiable list of strings, where each line (without newline)
	 *         is a separate entry
	 * @throws NullPointerException if {@code file} or {@code charset} is null
	 * @throws IOException if failure to open or read from file
	 */
	public static ArrayList<String> staticReadTextFileAsStringList(Path file, Charset charset)
	throws IOException {
		ArgUtil.staticCheckNotNull(file, "file");
		ArgUtil.staticCheckNotNull(charset, "charset");
		List<String> lineList = null;
		try {
			lineList = Files.readAllLines(file, charset);
		}
		catch (IOException e) {
			String msg = String.format(
					"Failed to read to file: '%s'"
					+ "%n\tCharset: '%s'",
					file, charset);
			throw new IOException(msg, e);
		}
		// Careful: Files.readAllLines() does not guarantee return value is modifiable.
		if (lineList instanceof ArrayList<?>) {
			return (ArrayList<String>) lineList;
		}
		ArrayList<String> lineList2 = new ArrayList<>(lineList);
		return lineList2;
	}
	
	public static String[][] staticReadTextFileAsArrayOfArrays(
			Path file, String delim, Charset charset)
	throws IOException {
		ArgUtil.staticCheckNotNull(file, "file");
		ArgUtil.staticCheckStringNotEmpty(delim, "delim");
		ArgUtil.staticCheckNotNull(charset, "charset");
		List<String> lineList = null;
		try {
			lineList = Files.readAllLines(file, charset);
		}
		catch (IOException e) {
			String msg = String.format(
				"Failed to read to file: '%s'"
				+ "%n\tCharset: '%s'",
				file, charset);
			throw new IOException(msg, e);
		}
		final String delimRegex = Pattern.quote(delim);
		final Pattern pattern = Pattern.compile(delimRegex);
		int size = lineList.size();
		int columnCount = -1;
		String[][] tokenArrArr = null;
		for (int lineIndex = 0; lineIndex < size; ++lineIndex) {
			String line = lineList.get(lineIndex);
			String[] tokenArr = pattern.split(line);
			if (-1 == columnCount) {
				if (1 == tokenArr.length) {
					String msg = String.format(
						"Failed to split first line by delim: '%s'"
						+ "%nLine: '%s'"
						+ "%nLine: '%s'",
						StringEscapeUtils.escapeJava(delim),
						line,
						StringEscapeUtils.escapeJava(line));
					throw new IOException(msg);
				}
				columnCount = tokenArr.length;
				tokenArrArr = new String[size][];
			}
			else if (columnCount != tokenArr.length) {
				String msg = String.format(
					"Reading line %d from file: '%s':"
					+ "%nExpected %d tokens, but found %d"
					+ "%nLine: '%s'"
					+ "%nLine: '%s'",
					(1 + lineIndex), file, columnCount, tokenArr.length,
					line, StringEscapeUtils.escapeJava(line));
				throw new IOException(msg);
			}
			tokenArrArr[lineIndex] = tokenArr;
		}
		return tokenArrArr;
	}
	
	/*
	public static ArrayList<ArrayList<String>> staticReadTextFileAsListOfLists(
			Path file, String delim, Charset charset) {
		return null;
	}
	*/
	
	private static Path _staticGetFilePathParent(Path file)
	throws IOException {
		ArgUtil.staticCheckNotNull(file, "file");
		Path dir = file.getParent();
		if (null == dir) {
			throw new IOException(String.format(
				"File path does not have a parent: '%s'", file));
		}
		return dir;
	}

	/**
	 * This is a convenience method for
	 * {@link #staticWriteTextFile(Path, String, Charset, OpenOption...)}.
	 */
	public static void staticWriteTextFile(
			String filePath, String text, OpenOption... openOptionArr)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(filePath, "filePath");
		Path file = _DEFAULT_FILE_SYSTEM.getPath(filePath);
		staticWriteTextFile(file, text, _DEFAULT_CHARSET, openOptionArr);
	}
	
	/**
	 * This is a convenience method for
	 * {@link #staticWriteTextFile(Path, String, Charset, OpenOption...)}.
	 */
	public static void staticWriteTextFile(
			Path file, String text, OpenOption... openOptionArr)
	throws IOException {
		staticWriteTextFile(file, text, _DEFAULT_CHARSET, openOptionArr);
	}
	
	/**
	 * This is a convenience method for
	 * {@link #staticWriteTextFile(Path, String, Charset, OpenOption...)}.
	 */
	public static void staticWriteTextFile(
			String filePath, String text, Charset charset, OpenOption... openOptionArr)
	throws IOException {
		ArgUtil.staticCheckStringNotEmpty(filePath, "filePath");
		Path file = _DEFAULT_FILE_SYSTEM.getPath(filePath);
		staticWriteTextFile(file, text, charset, openOptionArr);
	}
	
	/**
	 * Writes text to a file very safely.  Parent directories are created, if necessary.
	 * 
	 * @param file path for file 
	 * @param text text to write, can be empty, but not null
	 * @param charset characer set to use for encoding
	 * @param openOptionArr open list of {@link OpenOption}
	 * @throws NullPointerException if {@code file}, {@code text}, or {@code charset} is null,
	 *         or {@code openOptionArr} contains null elements
	 * @throws IOException if {@code file} does not have a parent,
	 *         or if failure to create parent directories,
	 *         or if failure to open or write to file
	 */
	public static void staticWriteTextFile(
			Path file, String text, Charset charset, OpenOption... openOptionArr)
	throws IOException {
		ArgUtil.staticCheckNotNull(file, "file");
		ArgUtil.staticCheckNotNull(text, "text");
		ArgUtil.staticCheckNotNull(charset, "charset");
		ArgUtil.staticCheckArrayElementsNotNull(openOptionArr, "openOptionArr");
		
		// According to Files.newBufferedWriter() Javadocs, empty openOptionArr implies
		// three StandardOpenOptions: CREATE, TRUNCATE_EXISTING, and WRITE.
		// We also add SYNC to be ridiculously safe.
		OpenOption[] openOptionArr2 = ArrayUtil.staticInsert(
			openOptionArr,
			openOptionArr.length,
			StandardOpenOption.CREATE,
			StandardOpenOption.TRUNCATE_EXISTING,
			StandardOpenOption.WRITE,
			StandardOpenOption.SYNC);
		/*
		OpenOption[] openOptionArr2 = null;
		if (0 == openOptionArr.length) {
			openOptionArr2 = new OpenOption[] {
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE,
				StandardOpenOption.SYNC };
		}
		else {
			openOptionArr2 = new OpenOption[4 + openOptionArr.length];
			System.arraycopy(openOptionArr, 0, openOptionArr2, 0, openOptionArr.length);
			openOptionArr2[0 + openOptionArr.length] = StandardOpenOption.CREATE;
			openOptionArr2[1 + openOptionArr.length] = StandardOpenOption.TRUNCATE_EXISTING;
			openOptionArr2[2 + openOptionArr.length] = StandardOpenOption.WRITE;
			openOptionArr2[3 + openOptionArr.length] = StandardOpenOption.SYNC;
		}
		*/
		
		Path dir = file.getParent();
		if (null == dir) {
			throw new IOException(String.format(
				"File path does not have a parent: '%s'", file));
		}
		
		try {
			staticCreateDirectories(dir);
		}
		catch (IOException e) {
			String msg = String.format(
				"Failed to create parent directories before writing to file: '%s'",
				file);
			throw new IOException(msg, e);
		}

		try (BufferedWriter bw = Files.newBufferedWriter(file, charset, openOptionArr2)) {
			bw.write(text);
		}
		catch (IOException e) {
			String msg = String.format(
				"Failed to write to file: '%s'"
				+ "%n\tCharset: '%s'%s",
				file, charset, _staticOpenOptionArrToString(openOptionArr2));
			throw new IOException(msg, e);
		}
	}
	
	private static String _staticOpenOptionArrToString(OpenOption[] openOptionArr) {
		if (0 == openOptionArr.length) {
			return "";
		}
		String s = String.format("%n\tOpenOption[]: %s", Joiner.on(", ").join(openOptionArr));
		return s;
	}
}
