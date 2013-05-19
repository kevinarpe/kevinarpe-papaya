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

import com.googlecode.kevinarpe.papaya.args.PathArgs;
import com.googlecode.kevinarpe.papaya.exceptions.DirectoryFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.DirectoryNotFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.RegularFileFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.RegularFileNotFoundException;
import com.googlecode.kevinarpe.papaya.exceptions.ClassResourceNotFoundException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PathArgsTest {

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
        PathArgs.checkClassResourceAsStreamExists(PathArgsTest.class, pathname, "pathname");
    }

    @Test(expectedExceptions = ClassResourceNotFoundException.class)
    public void checkResourceAsStreamExists_FailWithPathNotExists()
    throws ClassResourceNotFoundException {
        PathArgs.checkClassResourceAsStreamExists(
            PathArgsTest.class, UUID.randomUUID().toString(), "pathname");
    }
    
    @DataProvider
    private static Object[][] _checkResourceAsStreamExists_FailWithNulls_Data() {
        return new Object[][] {
                { null, null, "filePath" },
                { PathArgsTest.class, null, "filePath" },
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
        PathArgs.checkClassResourceAsStreamExists(clazz, pathname, argName);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkResourceAsStreamExists_FailWithEmptyPathname()
    throws ClassResourceNotFoundException {
        PathArgs.checkClassResourceAsStreamExists(PathArgsTest.class, "", "pathname");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkNotRegularFile
    //

    @Test
    public void checkNotRegularFile_PassWithPathNotExists()
    throws RegularFileFoundException {
        File path = new File(UUID.randomUUID().toString());
        PathArgs.checkNotRegularFile(path, "path");
        PathArgs.checkNotRegularFile(path.getPath(), "path");
        PathArgs.checkNotRegularFile(path.getAbsoluteFile(), "path");
        PathArgs.checkNotRegularFile(path.getAbsolutePath(), "path");
    }

    @Test
    public void checkNotRegularFile_PassWithPathIsDir()
    throws RegularFileFoundException {
        File path = new File(UUID.randomUUID().toString());
        _safeMkdir(path);
        try {
            PathArgs.checkNotRegularFile(path, "path");
            PathArgs.checkNotRegularFile(path.getPath(), "path");
            PathArgs.checkNotRegularFile(path.getAbsoluteFile(), "path");
            PathArgs.checkNotRegularFile(path.getAbsolutePath(), "path");
        }
        finally {
            _safeRmdir(path);
        }
    }

    @Test(expectedExceptions = RegularFileFoundException.class)
    public void checkNotRegularFile_FailWithPathIsFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs.checkNotRegularFile(path, "path");
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
    throws RegularFileFoundException {
        PathArgs.checkNotRegularFile(pathname, argName);
    }
    
    @Test(dataProvider = "_checkNotRegularFile_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotRegularFile_FailWithNullPath(File path, String argName)
    throws RegularFileFoundException {
        PathArgs.checkNotRegularFile(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotRegularFile_FailWithEmptyPath()
    throws RegularFileFoundException {
        PathArgs.checkNotRegularFile("", "path");
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
            PathArgs.checkRegularFileExists(path, "path");
            PathArgs.checkRegularFileExists(pathname, "pathname");
            PathArgs.checkRegularFileExists(absPath, "absPath");
            PathArgs.checkRegularFileExists(absPathname, "absPathname");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithDir()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        path.mkdir();
        try {
            PathArgs.checkRegularFileExists(path, "path");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithDir2()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.mkdir();
        try {
            PathArgs.checkRegularFileExists(pathname, "pathname");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithDir3()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.mkdir();
        try {
            PathArgs.checkRegularFileExists(absPath, "absPath");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithDir4()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String absPathname = path.getAbsolutePath();
        path.mkdir();
        try {
            PathArgs.checkRegularFileExists(absPathname, "absPathname");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithPathNotExists()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        PathArgs.checkRegularFileExists(path, "path");
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithPathNotExists2()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        PathArgs.checkRegularFileExists(pathname, "pathname");
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithPathNotExists3()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        PathArgs.checkRegularFileExists(absPath, "absPath");
    }
    
    @Test(expectedExceptions = RegularFileNotFoundException.class)
    public void checkRegularFileExists_FailWithPathNotExists4()
    throws RegularFileNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String absPathname = path.getAbsolutePath();
        PathArgs.checkRegularFileExists(absPathname, "absPathname");
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
    throws RegularFileNotFoundException {
        PathArgs.checkRegularFileExists(pathname, argName);
    }
    
    @Test(dataProvider = "_checkRegularFileExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkRegularFileExists_FailWithNullPath(File path, String argName)
    throws RegularFileNotFoundException {
        PathArgs.checkRegularFileExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkRegularFileExists_FailWithEmptyPath()
    throws RegularFileNotFoundException {
        PathArgs.checkRegularFileExists("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkNotDirectory
    //

    @Test
    public void checkNotDirectory_PassWithPathNotExists()
    throws DirectoryFoundException {
        File path = new File(UUID.randomUUID().toString());
        PathArgs.checkNotDirectory(path, "path");
        PathArgs.checkNotDirectory(path.getPath(), "path");
        PathArgs.checkNotDirectory(path.getAbsoluteFile(), "path");
        PathArgs.checkNotDirectory(path.getAbsolutePath(), "path");
    }

    @Test
    public void checkNotDirectory_PassWithPathIfRegularFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs.checkNotDirectory(path, "path");
            PathArgs.checkNotDirectory(path.getPath(), "path");
            PathArgs.checkNotDirectory(path.getAbsoluteFile(), "path");
            PathArgs.checkNotDirectory(path.getAbsolutePath(), "path");
        }
        finally {
            _safeRm(path);
        }
    }

    @Test(expectedExceptions = DirectoryFoundException.class)
    public void checkNotDirectory_FailWithPathIsDir()
    throws DirectoryFoundException {
        File path = new File(UUID.randomUUID().toString());
        _safeMkdir(path);
        try {
            PathArgs.checkNotDirectory(path, "path");
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
    throws DirectoryFoundException {
        PathArgs.checkNotDirectory(pathname, argName);
    }
    
    @Test(dataProvider = "_checkNotDirectory_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotDirectory_FailWithNullPath(File path, String argName)
    throws DirectoryFoundException {
        PathArgs.checkNotDirectory(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotDirectory_FailWithEmptyPath()
    throws DirectoryFoundException {
        PathArgs.checkNotDirectory("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkDirectoryExists
    //

    @Test
    public void checkDirectoryExists_Pass()
    throws DirectoryNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.mkdir();
        try {
            PathArgs.checkDirectoryExists(path, "path");
            PathArgs.checkDirectoryExists(pathname, "pathname");
            PathArgs.checkDirectoryExists(absPath, "absPath");
            PathArgs.checkDirectoryExists(absPathname, "absPathname");
        }
        finally {
            _safeRmdir(path);
        }
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithRegularFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(path, "path");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithRegularFile2()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(pathname, "pathname");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithRegularFile3()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(absPath, "absPath");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithRegularFile4()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String absPathname = path.getAbsolutePath();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(absPathname, "absPathname");
        }
        finally {
            _safeRm(path);
        }
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithPathNotExists()
    throws DirectoryNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        PathArgs.checkDirectoryExists(path, "path");
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithPathNotExists2()
    throws DirectoryNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        PathArgs.checkDirectoryExists(pathname, "pathname");
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithPathNotExists3()
    throws DirectoryNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        PathArgs.checkDirectoryExists(absPath, "absPath");
    }
    
    @Test(expectedExceptions = DirectoryNotFoundException.class)
    public void checkDirectoryExists_FailWithPathNotExists4()
    throws DirectoryNotFoundException {
        File path = new File(UUID.randomUUID().toString());
        String absPathname = path.getAbsolutePath();
        PathArgs.checkDirectoryExists(absPathname, "absPathname");
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
    throws DirectoryNotFoundException {
        PathArgs.checkDirectoryExists(pathname, argName);
    }
    
    @Test(dataProvider = "_checkDirectoryExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkDirectoryExists_FailWithNullPath(File path, String argName)
    throws DirectoryNotFoundException {
        PathArgs.checkDirectoryExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkDirectoryExists_FailWithEmptyPath()
    throws DirectoryNotFoundException {
        PathArgs.checkDirectoryExists("", "path");
    }
}
