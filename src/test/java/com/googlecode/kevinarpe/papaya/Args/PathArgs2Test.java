package com.googlecode.kevinarpe.papaya.Args;

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
import java.util.UUID;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.args.PathArgs2;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.exceptions.ClassResourceNotFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.PathException2Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PathArgs2Test {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkResourceAsStreamExists
    //
    
    private static final String SAMPLE_RESOURCE_ABSOLUTE_PATHNAME = "/sample.txt";
    //private static final String SAMPLE2_RESOURCE_RELATIVE_PATHNAME = "sample2.txt";
    private static final String SAMPLE2_RESOURCE_ABSOLUTE_PATHNAME = "/com/googlecode/kevinarpe/papaya/args/sample2.txt";
    
    @DataProvider
    private static Object[][] _checkResourceAsStreamExists_Pass_Data() {
        return new Object[][] {
                { SAMPLE_RESOURCE_ABSOLUTE_PATHNAME },
                //{ SAMPLE2_RESOURCE_RELATIVE_PATHNAME },
                { SAMPLE2_RESOURCE_ABSOLUTE_PATHNAME },
        };
    }
    
    @Test(dataProvider = "_checkResourceAsStreamExists_Pass_Data")
    public void checkResourceAsStreamExists_Pass(String pathname)
    throws ClassResourceNotFoundException {
        PathArgs2.checkClassResourceAsStreamExists(PathArgs2Test.class, pathname, "pathname");
    }

    @Test(expectedExceptions = ClassResourceNotFoundException.class)
    public void checkResourceAsStreamExists_FailWithPathNotExists()
    throws ClassResourceNotFoundException {
        PathArgs2.checkClassResourceAsStreamExists(
            PathArgs2Test.class, UUID.randomUUID().toString(), "pathname");
    }
    
    @DataProvider
    private static Object[][] _checkResourceAsStreamExists_FailWithNulls_Data() {
        return new Object[][] {
                { null, null, "filePath" },
                { PathArgs2Test.class, null, "filePath" },
                { null, UUID.randomUUID().toString(), "filePath" },
                { null, null, "" },
                { null, null, "   " },  // ASCII spaces
                { null, null, "　　　" },  // wide Japanese spaces
        };
    }

    @Test(dataProvider = "_checkResourceAsStreamExists_FailWithNulls_Data",
            expectedExceptions = NullPointerException.class)
    public void checkResourceAsStreamExists_FailWithNulls(
            Class<?> clazz, String pathname, String argName)
    throws ClassResourceNotFoundException {
        PathArgs2.checkClassResourceAsStreamExists(clazz, pathname, argName);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkResourceAsStreamExists_FailWithEmptyPathname()
    throws ClassResourceNotFoundException {
        PathArgs2.checkClassResourceAsStreamExists(PathArgs2Test.class, "", "pathname");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkNotRegularFile
    //

    @Test
    public void checkNotRegularFile_PassWithPathNotExists()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        PathArgs2.checkNotRegularFile(path, "path");
        PathArgs2.checkNotRegularFile(path.getPath(), "path");
        PathArgs2.checkNotRegularFile(path.getAbsoluteFile(), "path");
        PathArgs2.checkNotRegularFile(path.getAbsolutePath(), "path");
    }

    @Test
    public void checkNotRegularFile_PassWithPathIsDir()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        _safeMkdir(path);
        try {
            PathArgs2.checkNotRegularFile(path, "path");
            PathArgs2.checkNotRegularFile(path.getPath(), "path");
            PathArgs2.checkNotRegularFile(path.getAbsoluteFile(), "path");
            PathArgs2.checkNotRegularFile(path.getAbsolutePath(), "path");
        }
        finally {
            _safeRmdir(path);
        }
    }

    @Test(expectedExceptions = PathException2.class)
    public void checkNotRegularFile_FailWithPathIsFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs2.checkNotRegularFile(path, "path");
        }
        finally {
            _safeRm(path);
        }
    }

    @DataProvider
    private static Object[][] _checkNotRegularFile_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_checkNotRegularFile_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotRegularFile_FailWithNullPathname(String pathname, String argName)
    throws PathException2 {
        PathArgs2.checkNotRegularFile(pathname, argName);
    }
    
    @Test(dataProvider = "_checkNotRegularFile_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotRegularFile_FailWithNullPath(File path, String argName)
    throws PathException2 {
        PathArgs2.checkNotRegularFile(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotRegularFile_FailWithEmptyPath()
    throws PathException2 {
        PathArgs2.checkNotRegularFile("", "path");
    }
    
    private static void _safeMkdir(File path) {
        if (path.exists()) {
            throw new RuntimeException(String.format("Failed to mkdir: Path exists: '%s'",
                path.getAbsolutePath()));
        }
        if (!path.mkdir()) {
            throw new RuntimeException(String.format("Failed to mkdir: '%s'",
                path.getAbsolutePath()));
        }
    }
    
    private static void _safeRmdir(File path) {
        if (!path.isDirectory()) {
            throw new RuntimeException(String.format(
                "Failed to rmdir: Path is not a directory: '%s'",
                path.getAbsolutePath()));
        }
        if (!path.delete()) {
            throw new RuntimeException(String.format("Failed to rmdir: '%s'",
                path.getAbsolutePath()));
        }
    }
    
    private static void _safeRm(File path) {
        if (!path.isFile()) {
            throw new RuntimeException(String.format(
                "Failed to rm: Path is not a file: '%s'",
                path.getAbsolutePath()));
        }
        if (!path.delete()) {
            throw new RuntimeException(String.format("Failed to rm: '%s'",
                path.getAbsolutePath()));
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkRegularFileExists
    //

    @Test
    public void checkRegularFileExists_Pass()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.createNewFile();
        try {
            PathArgs2.checkRegularFileExists(path, "path");
            PathArgs2.checkRegularFileExists(pathname, "pathname");
            PathArgs2.checkRegularFileExists(absPath, "absPath");
            PathArgs2.checkRegularFileExists(absPathname, "absPathname");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithDir() {
        File path = new File(UUID.randomUUID().toString());
        path.mkdir();
        try {
            PathArgs2.checkRegularFileExists(path, "path");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithDir2()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.mkdir();
        try {
            PathArgs2.checkRegularFileExists(pathname, "pathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithDir3()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.mkdir();
        try {
            PathArgs2.checkRegularFileExists(absPath, "absPath");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_DIRECTORY, absPath, null, "dummy"));
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithDir4()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.mkdir();
        try {
            PathArgs2.checkRegularFileExists(absPathname, "absPathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_DIRECTORY, absPath, null, "dummy"));
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithPathNotExists() {
        File path = new File(UUID.randomUUID().toString());
        try {
            PathArgs2.checkRegularFileExists(path, "path");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithPathNotExists2() {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        try {
            PathArgs2.checkRegularFileExists(pathname, "pathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithPathNotExists3()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        try {
            PathArgs2.checkRegularFileExists(absPath, "absPath");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @Test
    public void checkRegularFileExists_FailWithPathNotExists4()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        try {
            PathArgs2.checkRegularFileExists(absPathname, "absPathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @DataProvider
    private static Object[][] _checkRegularFileExists_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_checkRegularFileExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkRegularFileExists_FailWithNullPathname(String pathname, String argName)
    throws PathException2 {
        PathArgs2.checkRegularFileExists(pathname, argName);
    }
    
    @Test(dataProvider = "_checkRegularFileExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkRegularFileExists_FailWithNullPath(File path, String argName)
    throws PathException2 {
        PathArgs2.checkRegularFileExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkRegularFileExists_FailWithEmptyPath()
    throws PathException2 {
        PathArgs2.checkRegularFileExists("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkNotDirectory
    //

    @Test
    public void checkNotDirectory_PassWithPathNotExists()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        PathArgs2.checkNotDirectory(path, "path");
        PathArgs2.checkNotDirectory(path.getPath(), "path");
        PathArgs2.checkNotDirectory(path.getAbsoluteFile(), "path");
        PathArgs2.checkNotDirectory(path.getAbsolutePath(), "path");
    }

    @Test
    public void checkNotDirectory_PassWithPathIfRegularFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs2.checkNotDirectory(path, "path");
            PathArgs2.checkNotDirectory(path.getPath(), "path");
            PathArgs2.checkNotDirectory(path.getAbsoluteFile(), "path");
            PathArgs2.checkNotDirectory(path.getAbsolutePath(), "path");
        }
        finally {
            _safeRm(path);
        }
    }

    @Test
    public void checkNotDirectory_FailWithPathIsDir()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        _safeMkdir(path);
        try {
            PathArgs2.checkNotDirectory(path, "path");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            _safeRmdir(path);
        }
    }

    @DataProvider
    private static Object[][] _checkNotDirectory_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_checkNotDirectory_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotDirectory_FailWithNullPathname(String pathname, String argName)
    throws PathException2 {
        PathArgs2.checkNotDirectory(pathname, argName);
    }
    
    @Test(dataProvider = "_checkNotDirectory_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotDirectory_FailWithNullPath(File path, String argName)
    throws PathException2 {
        PathArgs2.checkNotDirectory(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotDirectory_FailWithEmptyPath()
    throws PathException2 {
        PathArgs2.checkNotDirectory("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkDirectoryExists
    //

    @Test
    public void checkDirectoryExists_Pass()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.mkdir();
        try {
            PathArgs2.checkDirectoryExists(path, "path");
            PathArgs2.checkDirectoryExists(pathname, "pathname");
            PathArgs2.checkDirectoryExists(absPath, "absPath");
            PathArgs2.checkDirectoryExists(absPathname, "absPathname");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithRegularFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs2.checkDirectoryExists(path, "path");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, "dummy"));
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithRegularFile2()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.createNewFile();
        try {
            PathArgs2.checkDirectoryExists(pathname, "pathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_REGULAR_FILE, path, null, "dummy"));
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithRegularFile3()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.createNewFile();
        try {
            PathArgs2.checkDirectoryExists(absPath, "absPath");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_REGULAR_FILE, absPath, null, "dummy"));
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithRegularFile4()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.createNewFile();
        try {
            PathArgs2.checkDirectoryExists(absPathname, "absPathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_IS_REGULAR_FILE, absPath, null, "dummy"));
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        try {
            PathArgs2.checkDirectoryExists(path, "path");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists2()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        try {
            PathArgs2.checkDirectoryExists(pathname, "pathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists3()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        try {
            PathArgs2.checkDirectoryExists(absPath, "absPath");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists4()
    throws PathException2 {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        try {
            PathArgs2.checkDirectoryExists(absPathname, "absPathname");
        }
        catch (PathException2 e) {
            PathException2Test.assertPathExceptionEquals(
                e,
                new PathException2(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @DataProvider
    private static Object[][] _checkDirectoryExists_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "_checkDirectoryExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkDirectoryExists_FailWithNullPathname(String pathname, String argName)
    throws PathException2 {
        PathArgs2.checkDirectoryExists(pathname, argName);
    }
    
    @Test(dataProvider = "_checkDirectoryExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkDirectoryExists_FailWithNullPath(File path, String argName)
    throws PathException2 {
        PathArgs2.checkDirectoryExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkDirectoryExists_FailWithEmptyPath()
    throws PathException2 {
        PathArgs2.checkDirectoryExists("", "path");
    }
}
