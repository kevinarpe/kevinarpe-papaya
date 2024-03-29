package com.googlecode.kevinarpe.papaya.argument;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.exception.ClassResourceNotFoundException;
import com.googlecode.kevinarpe.papaya.exception.PathException;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionReason;
import com.googlecode.kevinarpe.papaya.exception.PathExceptionTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    // Also used by ClassResourceInputSource2Test
    public static final String SAMPLE_RESOURCE_ABSOLUTE_PATHNAME = "/sample.txt";
    //private static final String SAMPLE2_RESOURCE_RELATIVE_PATHNAME = "sample2.txt";
    private static final String SAMPLE2_RESOURCE_ABSOLUTE_PATHNAME = "/com/googlecode/kevinarpe/papaya/args/sample2.txt";
    
    @DataProvider
    private static Object[][] checkResourceAsStreamExists_Pass_Data() {
        return new Object[][] {
                { SAMPLE_RESOURCE_ABSOLUTE_PATHNAME },
                //{ SAMPLE2_RESOURCE_RELATIVE_PATHNAME },
                { SAMPLE2_RESOURCE_ABSOLUTE_PATHNAME },
        };
    }
    
    @Test(dataProvider = "checkResourceAsStreamExists_Pass_Data")
    public void checkResourceAsStreamExists_Pass(String pathname)
    throws ClassResourceNotFoundException {
        Assert.assertNotNull(
            PathArgs.checkClassResourceAsStreamExists(PathArgsTest.class, pathname, "pathname"));
    }

    @Test(expectedExceptions = ClassResourceNotFoundException.class)
    public void checkResourceAsStreamExists_FailWithPathNotExists()
    throws ClassResourceNotFoundException {
        PathArgs.checkClassResourceAsStreamExists(
            PathArgsTest.class, UUID.randomUUID().toString(), "pathname");
    }
    
    @DataProvider
    private static Object[][] checkResourceAsStreamExists_FailWithNulls_Data() {
        return new Object[][] {
                { null, null, "filePath" },
                { PathArgsTest.class, null, "filePath" },
                { null, UUID.randomUUID().toString(), "filePath" },
                { null, null, "" },
                { null, null, "   " },  // ASCII spaces
                { null, null, "　　　" },  // wide Japanese spaces
        };
    }

    @Test(dataProvider = "checkResourceAsStreamExists_FailWithNulls_Data",
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
    // FileArgs.checkNotFile
    //

    @Test
    public void checkNotFile_PassWithPathNotExists()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(path == PathArgs.checkNotFile(path, "path"));
        
        Assert.assertEquals(
            new File(path.getPath()),
            PathArgs.checkNotFile(path.getPath(), "path"));
        
        Assert.assertEquals(
            path.getAbsoluteFile(),
            PathArgs.checkNotFile(path.getAbsoluteFile(), "path"));
        
        Assert.assertEquals(
            new File(path.getAbsolutePath()),
            PathArgs.checkNotFile(path.getAbsolutePath(), "path"));
    }

    @Test
    public void checkNotFile_PassWithPathIsDir()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        safeMkdir(path);
        try {
            // Two steps here: (1) call the method, (2) assert the result
            Assert.assertTrue(path == PathArgs.checkNotFile(path, "path"));
            
            Assert.assertEquals(
                new File(path.getPath()),
                PathArgs.checkNotFile(path.getPath(), "path"));
            
            Assert.assertEquals(
                path.getAbsoluteFile(),
                PathArgs.checkNotFile(path.getAbsoluteFile(), "path"));
            
            Assert.assertEquals(
                new File(path.getAbsolutePath()),
                PathArgs.checkNotFile(path.getAbsolutePath(), "path"));
        }
        finally {
            safeRmdir(path);
        }
    }

    @Test(expectedExceptions = PathException.class)
    public void checkNotFile_FailWithPathIsFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs.checkNotFile(path, "path");
        }
        finally {
            safeRm(path);
        }
    }

    @DataProvider
    private static Object[][] checkNotFile_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "checkNotFile_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotFile_FailWithNullPathname(String pathname, String argName)
    throws PathException {
        PathArgs.checkNotFile(pathname, argName);
    }
    
    @Test(dataProvider = "checkNotFile_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotFile_FailWithNullPath(File path, String argName)
    throws PathException {
        PathArgs.checkNotFile(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotFile_FailWithEmptyPath()
    throws PathException {
        PathArgs.checkNotFile("", "path");
    }
    
    public static void safeMkdir(File path) {
        if (path.exists()) {
            throw new RuntimeException(String.format("Failed to mkdir: Path exists: '%s'",
                path.getAbsolutePath()));
        }
        if (!path.mkdir()) {
            throw new RuntimeException(String.format("Failed to mkdir: '%s'",
                path.getAbsolutePath()));
        }
    }
    
    public static void safeRmdir(File path) {
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
    
    public static void safeRm(File path) {
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
    // FileArgs.checkFileExists
    //

    @Test
    public void checkFileExists_Pass()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.createNewFile();
        try {
            // Two steps here: (1) call the method, (2) assert the result
            Assert.assertTrue(path == PathArgs.checkFileExists(path, "path"));
            
            Assert.assertEquals(
                new File(pathname), PathArgs.checkFileExists(pathname, "pathname"));
            
            Assert.assertTrue(absPath == PathArgs.checkFileExists(absPath, "absPath"));
            
            Assert.assertEquals(
                new File(absPathname), PathArgs.checkFileExists(absPathname, "absPathname"));
        }
        finally {
            safeRm(path);
        }
    }
    
    @Test
    public void checkFileExists_FailWithDir() {
        File path = new File(UUID.randomUUID().toString());
        path.mkdir();
        try {
            PathArgs.checkFileExists(path, "path");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            safeRmdir(path);
        }
    }
    
    @Test
    public void checkFileExists_FailWithDir2()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.mkdir();
        try {
            PathArgs.checkFileExists(pathname, "pathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            safeRmdir(path);
        }
    }
    
    @Test
    public void checkFileExists_FailWithDir3()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.mkdir();
        try {
            PathArgs.checkFileExists(absPath, "absPath");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_DIRECTORY, absPath, null, "dummy"));
        }
        finally {
            safeRmdir(path);
        }
    }
    
    @Test
    public void checkFileExists_FailWithDir4()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.mkdir();
        try {
            PathArgs.checkFileExists(absPathname, "absPathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_DIRECTORY, absPath, null, "dummy"));
        }
        finally {
            safeRmdir(path);
        }
    }
    
    @Test
    public void checkFileExists_FailWithPathNotExists() {
        File path = new File(UUID.randomUUID().toString());
        try {
            PathArgs.checkFileExists(path, "path");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkFileExists_FailWithPathNotExists2() {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        try {
            PathArgs.checkFileExists(pathname, "pathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkFileExists_FailWithPathNotExists3()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        try {
            PathArgs.checkFileExists(absPath, "absPath");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @Test
    public void checkFileExists_FailWithPathNotExists4()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        try {
            PathArgs.checkFileExists(absPathname, "absPathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @DataProvider
    private static Object[][] checkFileExists_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "checkFileExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkFileExists_FailWithNullPathname(String pathname, String argName)
    throws PathException {
        PathArgs.checkFileExists(pathname, argName);
    }
    
    @Test(dataProvider = "checkFileExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkFileExists_FailWithNullPath(File path, String argName)
    throws PathException {
        PathArgs.checkFileExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkFileExists_FailWithEmptyPath()
    throws PathException {
        PathArgs.checkFileExists("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkNotDirectory
    //

    @Test
    public void checkNotDirectory_PassWithPathNotExists()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        // Two steps here: (1) call the method, (2) assert the result
        Assert.assertTrue(path == PathArgs.checkNotDirectory(path, "path"));
        
        Assert.assertEquals(
            new File(path.getPath()),
            PathArgs.checkNotDirectory(path.getPath(), "path"));
        
        Assert.assertEquals(
            path.getAbsoluteFile(),
            PathArgs.checkNotDirectory(path.getAbsoluteFile(), "path"));
        
        Assert.assertEquals(
            new File(path.getAbsolutePath()),
            PathArgs.checkNotDirectory(path.getAbsolutePath(), "path"));
    }

    @Test
    public void checkNotDirectory_PassWithPathIfFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            // Two steps here: (1) call the method, (2) assert the result
            Assert.assertTrue(path == PathArgs.checkNotDirectory(path, "path"));
            
            Assert.assertEquals(
                new File(path.getPath()),
                PathArgs.checkNotDirectory(path.getPath(), "path"));
            
            Assert.assertEquals(
                path.getAbsoluteFile(),
                PathArgs.checkNotDirectory(path.getAbsoluteFile(), "path"));
            
            Assert.assertEquals(
                new File(path.getAbsolutePath()),
                PathArgs.checkNotDirectory(path.getAbsolutePath(), "path"));
        }
        finally {
            safeRm(path);
        }
    }

    @Test
    public void checkNotDirectory_FailWithPathIsDir()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        safeMkdir(path);
        try {
            PathArgs.checkNotDirectory(path, "path");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_DIRECTORY, path, null, "dummy"));
        }
        finally {
            safeRmdir(path);
        }
    }

    @DataProvider
    private static Object[][] checkNotDirectory_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "checkNotDirectory_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotDirectory_FailWithNullPathname(String pathname, String argName)
    throws PathException {
        PathArgs.checkNotDirectory(pathname, argName);
    }
    
    @Test(dataProvider = "checkNotDirectory_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkNotDirectory_FailWithNullPath(File path, String argName)
    throws PathException {
        PathArgs.checkNotDirectory(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkNotDirectory_FailWithEmptyPath()
    throws PathException {
        PathArgs.checkNotDirectory("", "path");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkDirectoryExists
    //

    @Test
    public void checkDirectoryExists_Pass()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.mkdir();
        try {
            // Two steps here: (1) call the method, (2) assert the result
            Assert.assertTrue(path == PathArgs.checkDirectoryExists(path, "path"));
            
            Assert.assertEquals(
                new File(pathname), PathArgs.checkDirectoryExists(pathname, "pathname"));
            
            Assert.assertTrue(absPath == PathArgs.checkDirectoryExists(absPath, "absPath"));
            
            Assert.assertEquals(
                new File(absPathname), PathArgs.checkDirectoryExists(absPathname, "absPathname"));
        }
        finally {
            safeRmdir(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithFile()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(path, "path");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_NORMAL_FILE, path, null, "dummy"));
        }
        finally {
            safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithFile2()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(pathname, "pathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_NORMAL_FILE, path, null, "dummy"));
        }
        finally {
            safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithFile3()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(absPath, "absPath");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_NORMAL_FILE, absPath, null, "dummy"));
        }
        finally {
            safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithFile4()
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        path.createNewFile();
        try {
            PathArgs.checkDirectoryExists(absPathname, "absPathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_NORMAL_FILE, absPath, null, "dummy"));
        }
        finally {
            safeRm(path);
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        try {
            PathArgs.checkDirectoryExists(path, "path");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists2()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        String pathname = path.getPath();
        try {
            PathArgs.checkDirectoryExists(pathname, "pathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, path, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists3()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        try {
            PathArgs.checkDirectoryExists(absPath, "absPath");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @Test
    public void checkDirectoryExists_FailWithPathNotExists4()
    throws PathException {
        File path = new File(UUID.randomUUID().toString());
        File absPath = path.getAbsoluteFile();
        String absPathname = absPath.getPath();
        try {
            PathArgs.checkDirectoryExists(absPathname, "absPathname");
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_DOES_NOT_EXIST, absPath, null, "dummy"));
        }
    }
    
    @DataProvider
    private static Object[][] checkDirectoryExists_FailWithNullPath_Data() {
        return new Object[][] {
                { null, "filePath" },
                { null, "" },
                { null, "   " },  // ASCII spaces
                { null, "　　　" },  // wide Japanese spaces
        };
    }
    
    @Test(dataProvider = "checkDirectoryExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkDirectoryExists_FailWithNullPathname(String pathname, String argName)
    throws PathException {
        PathArgs.checkDirectoryExists(pathname, argName);
    }
    
    @Test(dataProvider = "checkDirectoryExists_FailWithNullPath_Data",
            expectedExceptions = NullPointerException.class)
    public void checkDirectoryExists_FailWithNullPath(File path, String argName)
    throws PathException {
        PathArgs.checkDirectoryExists(path, argName);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkDirectoryExists_FailWithEmptyPath()
    throws PathException {
        PathArgs.checkDirectoryExists("", "path");
    }
}
