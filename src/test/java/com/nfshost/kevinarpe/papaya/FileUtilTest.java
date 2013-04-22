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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nfshost.kevinarpe.papaya.FileUtil;
import com.nfshost.kevinarpe.papaya.Args.FloatArgs;

public class FileUtilTest {

    @BeforeClass
    public void classSetup() {
    }
    
    @AfterClass
    public void classTearDown() {
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileUtil.staticCheckRegularFileExists
    //

    @Test
    public void shouldCheckRegularFileExists()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        Files.createFile(file);
        try {
            FileUtil.staticCheckRegularFileExists(file);
            FileUtil.staticCheckRegularFileExists(filePath);
            FileUtil.staticCheckRegularFileExists(absFile);
            FileUtil.staticCheckRegularFileExists(absFilePath);
        }
        finally {
            Files.deleteIfExists(file);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        Files.createDirectory(dir);
        try {
            FileUtil.staticCheckRegularFileExists(dirPath);
        }
        finally {
            Files.deleteIfExists(dir);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile2()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        Files.createDirectory(dir);
        try {
            FileUtil.staticCheckRegularFileExists(absDirPath);
        }
        finally {
            Files.deleteIfExists(dir);
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile3()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        Files.createDirectory(dir);
        try {
            FileUtil.staticCheckRegularFileExists(dir);
        }
        finally {
            Files.deleteIfExists(dir);
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile4()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        Files.createDirectory(dir);
        try {
            FileUtil.staticCheckRegularFileExists(absDir);
        }
        finally {
            Files.deleteIfExists(dir);
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        FileUtil.staticCheckRegularFileExists(file);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists2()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        FileUtil.staticCheckRegularFileExists(filePath);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists3()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        FileUtil.staticCheckRegularFileExists(absFile);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists4()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        FileUtil.staticCheckRegularFileExists(absFilePath);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists5()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        FileUtil.staticCheckRegularFileExists(absFilePath, LinkOption.NOFOLLOW_LINKS);
    }

    @DataProvider
    private static final Object[][] _dataForShouldNotCheckRegularFileExistsWithNullInputs() {
        return new Object[][] {
                { null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckRegularFileExistsWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInputs(String filePath)
    throws FileNotFoundException {
        FileUtil.staticCheckRegularFileExists(filePath);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckRegularFileExistsWithNullInputs2() {
        return new Object[][] {
                { "blah", null },
                { "blah", new LinkOption[] { null } },
                { "blah", new LinkOption[] { null, null } },
                { "blah", new LinkOption[] { LinkOption.NOFOLLOW_LINKS, null } },
                { "blah", new LinkOption[] { LinkOption.NOFOLLOW_LINKS, null, null } },
                { "blah", new LinkOption[] { null, LinkOption.NOFOLLOW_LINKS } },
                { "blah", new LinkOption[] { null, LinkOption.NOFOLLOW_LINKS, null } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckRegularFileExistsWithNullInputs2",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInputs2(
            String filePath, LinkOption[] linkOptionArr)
    throws FileNotFoundException {
        FileUtil.staticCheckRegularFileExists(filePath, linkOptionArr);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileUtil.staticCheckDirectoryExists
    //

    @Test
    public void shouldCheckDirectoryExists()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        Files.createDirectory(dir);
        try {
            FileUtil.staticCheckDirectoryExists(dir);
            FileUtil.staticCheckDirectoryExists(dirPath);
            FileUtil.staticCheckDirectoryExists(absDir);
            FileUtil.staticCheckDirectoryExists(absDirPath);
        }
        finally {
            Files.deleteIfExists(dir);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        Files.createFile(file);
        try {
            FileUtil.staticCheckDirectoryExists(file);
        }
        finally {
            Files.deleteIfExists(file);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory2()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        Files.createFile(file);
        try {
            FileUtil.staticCheckDirectoryExists(filePath);
        }
        finally {
            Files.deleteIfExists(file);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory3()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        Files.createFile(file);
        try {
            FileUtil.staticCheckDirectoryExists(absFile);
        }
        finally {
            Files.deleteIfExists(file);
        }
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory4()
    throws IOException {
        Path file = FileSystems.getDefault().getPath("test_file.txt");
        String filePath = file.toString();
        Path absFile = file.toAbsolutePath();
        String absFilePath = absFile.toString();
        Files.createFile(file);
        try {
            FileUtil.staticCheckDirectoryExists(absFilePath);
        }
        finally {
            Files.deleteIfExists(file);
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        FileUtil.staticCheckDirectoryExists(dir);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists2()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        FileUtil.staticCheckDirectoryExists(dirPath);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists3()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        FileUtil.staticCheckDirectoryExists(absDir);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists4()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        FileUtil.staticCheckDirectoryExists(absDirPath);
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists5()
    throws IOException {
        Path dir = FileSystems.getDefault().getPath("test_dir");
        String dirPath = dir.toString();
        Path absDir = dir.toAbsolutePath();
        String absDirPath = absDir.toString();
        FileUtil.staticCheckDirectoryExists(absDirPath, LinkOption.NOFOLLOW_LINKS);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckDirectoryExistsWithNullInputs() {
        return new Object[][] {
                { null },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckDirectoryExistsWithNullInputs",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInputs(String filePath)
    throws FileNotFoundException {
        FileUtil.staticCheckDirectoryExists(filePath);
    }
    
    @DataProvider
    private static final Object[][] _dataForShouldNotCheckDirectoryExistsWithNullInputs2() {
        return new Object[][] {
                { "blah", null },
                { "blah", new LinkOption[] { null } },
                { "blah", new LinkOption[] { null, null } },
                { "blah", new LinkOption[] { LinkOption.NOFOLLOW_LINKS, null } },
                { "blah", new LinkOption[] { LinkOption.NOFOLLOW_LINKS, null, null } },
                { "blah", new LinkOption[] { null, LinkOption.NOFOLLOW_LINKS } },
                { "blah", new LinkOption[] { null, LinkOption.NOFOLLOW_LINKS, null } },
        };
    }
    
    @Test(dataProvider = "_dataForShouldNotCheckDirectoryExistsWithNullInputs2",
            expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInputs2(
            String filePath, LinkOption[] linkOptionArr)
    throws FileNotFoundException {
        FileUtil.staticCheckDirectoryExists(filePath, linkOptionArr);
    }
}
