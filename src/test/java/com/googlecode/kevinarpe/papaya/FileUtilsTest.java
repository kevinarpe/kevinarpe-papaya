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

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class FileUtilsTest {
    
    private static SystemUtils.OperatingSystemCategory _cat;

    @BeforeClass
    public void oneTimeSetup() {
        _cat = SystemUtils.checkCurrentOperatingSystemCategory(
            SystemUtils.OperatingSystemCategory.WINDOWS,
            SystemUtils.OperatingSystemCategory.UNIX);
    }
    
    @AfterClass
    public void oneTimeTearDown() {
    }
    
    private static String _convertPath(String unixPath) {
        if (_cat.equals(SystemUtils.OperatingSystemCategory.WINDOWS)) {
            String winPath = unixPath.replace('/', '\\');
            if (winPath.startsWith("\\")) {
                winPath = "C:" + winPath;
            }
            return winPath;
        }
        return unixPath;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileUtils.isRootDirectory
    //

    @DataProvider
    private static final Object[][] _dataForShouldTestIsRootDirectoryCorrectly() {
        String relativeDirPath = FileUtilsTest.class.getPackage().getName().replace('.', '/');
        return new Object[][] {
                { _convertPath(relativeDirPath), false },
                { _convertPath("."), false },
                { _convertPath("/"), true },
                { _convertPath("/blah"), false },
                { _convertPath("/blah/"), false },
                { _convertPath("/blah.txt"), false },
        };
    }
    
    @Test(dataProvider = "_dataForShouldTestIsRootDirectoryCorrectly")
    public void shouldTestIsRootDirectoryCorrectly(String dirPath, boolean expectedResult) {
        boolean result = FileUtils.isRootDirectory(dirPath);
        Assert.assertEquals(result, expectedResult);
        File dir = new File(dirPath);
        result = FileUtils.isRootDirectory(dir);
        Assert.assertEquals(result, expectedResult);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath() {
        FileUtils.isRootDirectory((String) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath2() {
        FileUtils.isRootDirectory((File) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithInvalidPath() {
        FileUtils.isRootDirectory("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // FileUtils.createDirectory
    //

    @DataProvider
    private static final Object[][] _dataForShouldCreateDirectory() {
        return new Object[][] {
                { "test_dir" },
                { "test_dir   " },
                { "   " },
        };
    }
    
    @Test(dataProvider = "_dataForShouldCreateDirectory")
    public void shouldCreateDirectory(String dirName)
    throws IOException {
        File dir = new File(dirName);
        if (!dir.delete() && dir.exists()) {
            throw new IOException(String.format("Test dir name already exists: '%s'", dirName));
        }
        try {
            FileUtils.createDirectory(dirName);
            dir.delete();
            FileUtils.createDirectory(dir);
        }
        finally {
            dir.delete();
        }
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCreateDirectoryWithNullName()
    throws IOException {
        FileUtils.createDirectory((String) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCreateDirectoryWithNullName2()
    throws IOException {
        FileUtils.createDirectory((File) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCreateDirectoryWithWithInvalidName()
    throws IOException {
        FileUtils.createDirectory("");
    }
}
