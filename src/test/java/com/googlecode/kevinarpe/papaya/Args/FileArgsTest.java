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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.args.FileArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileArgsTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }

    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkRegularFileExists
    //

    @Test
    public void shouldCheckRegularFileExists()
    throws IOException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
            FileArgs.checkRegularFileExists(file, "file");
            FileArgs.checkRegularFileExists(filePath, "filePath");
            FileArgs.checkRegularFileExists(absFile, "absFile");
            FileArgs.checkRegularFileExists(absFilePath, "absFilePath");
        }
        finally {
            file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile()
    throws FileNotFoundException {
        File dir = new File("test_dir");
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(dir, "dir");
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile2()
    throws FileNotFoundException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(dirPath, "dirPath");
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile3()
    throws FileNotFoundException {
        File dir = new File("test_dir");
        File absDir = dir.getAbsoluteFile();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(absDir, "absDir");
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile4()
    throws FileNotFoundException {
        File dir = new File("test_dir");
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(absDirPath, "absDirPath");
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists()
    throws FileNotFoundException {
        File file = new File("test_file.txt");
        FileArgs.checkRegularFileExists(file, "file");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists2()
    throws FileNotFoundException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        FileArgs.checkRegularFileExists(filePath, "filePath");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists3()
    throws FileNotFoundException {
        File file = new File("test_file.txt");
        File absFile = file.getAbsoluteFile();
        FileArgs.checkRegularFileExists(absFile, "absFile");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists4()
    throws FileNotFoundException {
        File file = new File("test_file.txt");
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        FileArgs.checkRegularFileExists(absFilePath, "absFilePath");
    }
    
    @DataProvider
    private static Object[][] _dataForShouldNotCheckRegularFileExistsWithNullInputs() {
        return new Object[][] {
                { null, "filePath" },
                { "file.txt", null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckRegularFileExistsWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInputs(String filePath, String argName)
    throws FileNotFoundException {
        FileArgs.checkRegularFileExists(filePath, argName);
    }
    
    @DataProvider
    private static Object[][] _dataForShouldNotCheckRegularFileExistsWithNullInputs2() {
        return new Object[][] {
                { null, "filePath" },
                { new File("file.txt"), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckRegularFileExistsWithNullInputs2",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInputs2(File file, String argName)
    throws FileNotFoundException {
        FileArgs.checkRegularFileExists(file, argName);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileArgs.checkDirectoryExists
    //

    @Test
    public void shouldCheckDirectoryExists()
    throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkDirectoryExists(dir, "dir");
            FileArgs.checkDirectoryExists(dirPath, "dirPath");
            FileArgs.checkDirectoryExists(absDir, "absDir");
            FileArgs.checkDirectoryExists(absDirPath, "absDirPath");
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory()
    throws IOException {
        File file = new File("test_file.txt");
        file.createNewFile();
        try {
            FileArgs.checkDirectoryExists(file, "file");
        }
        finally {
            file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory2()
    throws IOException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        file.createNewFile();
        try {
            FileArgs.checkDirectoryExists(filePath, "filePath");
        }
        finally {
            file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory3()
    throws IOException {
        File file = new File("test_file.txt");
        File absFile = file.getAbsoluteFile();
        file.createNewFile();
        try {
            FileArgs.checkDirectoryExists(absFile, "absFile");
        }
        finally {
            file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory4()
    throws IOException {
        File file = new File("test_file.txt");
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
            FileArgs.checkDirectoryExists(absFilePath, "absFilePath");
        }
        finally {
            file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists()
    throws IOException {
        File dir = new File("test_dir");
        FileArgs.checkDirectoryExists(dir, "dir");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists2()
    throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        FileArgs.checkDirectoryExists(dirPath, "dirPath");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists3()
    throws IOException {
        File dir = new File("test_dir");
        File absDir = dir.getAbsoluteFile();
        FileArgs.checkDirectoryExists(absDir, "absDir");
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists4()
    throws IOException {
        File dir = new File("test_dir");
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        FileArgs.checkDirectoryExists(absDirPath, "absDirPath");
    }
    
    @DataProvider
    private static Object[][] _dataForShouldNotCheckDirectoryExistsWithNullInputs() {
        return new Object[][] {
                { null, "filePath" },
                { "file.txt", null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckDirectoryExistsWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInputs(String dirPath, String argName)
    throws FileNotFoundException {
        FileArgs.checkDirectoryExists(dirPath, argName);
    }
    
    @DataProvider
    private static Object[][] _dataForShouldNotCheckDirectoryExistsWithNullInputs2() {
        return new Object[][] {
                { null, "filePath" },
                { new File("file.txt"), null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckDirectoryExistsWithNullInputs2",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInputs2(File dir, String argName)
    throws FileNotFoundException {
        FileArgs.checkDirectoryExists(dir, argName);
    }
}
