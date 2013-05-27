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
import java.util.UUID;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DirectoryUtilsTest {
    
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
    // DirectoryUtils.isRootDirectory
    //

    @DataProvider
    private static final Object[][] _dataForShouldTestIsRootDirectoryCorrectly() {
        String relativeDirPath = DirectoryUtilsTest.class.getPackage().getName().replace('.', '/');
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
        boolean result = DirectoryUtils.isRootDirectory(dirPath);
        Assert.assertEquals(result, expectedResult);
        File dir = new File(dirPath);
        result = DirectoryUtils.isRootDirectory(dir);
        Assert.assertEquals(result, expectedResult);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath() {
        DirectoryUtils.isRootDirectory((String) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath2() {
        DirectoryUtils.isRootDirectory((File) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithInvalidPath() {
        DirectoryUtils.isRootDirectory("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DirectoryUtils.makeDirectory
    //
    
    @Test
    public void makeDirectory_PassWithNewDir()
    throws IOException {
        _makeDirectory_PassWithNewDir(true);
        _makeDirectory_PassWithNewDir(false);
    }
    
    private void _makeDirectory_PassWithNewDir(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path);
            }
            Assert.assertTrue(path.isDirectory());
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectory_PassWithDirExists()
    throws IOException {
        _makeDirectory_PassWithDirExists(true);
        _makeDirectory_PassWithDirExists(false);
    }
    
    private void _makeDirectory_PassWithDirExists(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path);
            }
            Assert.assertTrue(path.isDirectory());
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
//    static final String QUOTED_PATH_EXISTS_AS_A_REGULAR_FILE = Pattern.quote(DirectoryUtils.PATH_EXISTS_AS_A_REGULAR_FILE);
    static final String QUOTED_PATH_EXISTS_AS_A_REGULAR_FILE = DirectoryUtils.PATH_EXISTS_AS_A_REGULAR_FILE;
    
    @Test(expectedExceptions = IOException.class,
            expectedExceptionsMessageRegExp = QUOTED_PATH_EXISTS_AS_A_REGULAR_FILE)
            //expectedExceptionsMessageRegExp = DirectoryUtils.PATH_EXISTS_AS_A_REGULAR_FILE)
            //expectedExceptionsMessageRegExp = Pattern.quote(DirectoryUtils.PATH_EXISTS_AS_A_REGULAR_FILE))
    public void makeDirectory_FailWithRegularFileExists()
    throws IOException {
        _makeDirectory_FailWithRegularFileExists(true);
        _makeDirectory_FailWithRegularFileExists(false);
    }
    
    private void _makeDirectory_FailWithRegularFileExists(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectory_FailWithParentIsRegularFile()
    throws IOException {
        _makeDirectory_FailWithParentIsRegularFile(true);
        _makeDirectory_FailWithParentIsRegularFile(false);
    }
    
    private void _makeDirectory_FailWithParentIsRegularFile(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path2);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectory_FailWithParentIsRegularFile2()
    throws IOException {
        _makeDirectory_FailWithParentIsRegularFile2(true);
        _makeDirectory_FailWithParentIsRegularFile2(false);
    }
    
    private void _makeDirectory_FailWithParentIsRegularFile2(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path,
            UUID.randomUUID().toString() + File.separatorChar + UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path2);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectory_FailWithParentIsDirNotWritable()
    throws IOException {
        _makeDirectory_FailWithParentIsDirNotWritable(true);
        _makeDirectory_FailWithParentIsDirNotWritable(false);
    }
    
    private void _makeDirectory_FailWithParentIsDirNotWritable(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(path.canWrite());
        path.setWritable(false);
        Assert.assertTrue(!path.canWrite());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectory(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectory(path2);
            }
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectory_FailWithNullPath()
    throws IOException {
        DirectoryUtils.makeDirectory((File) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectory_FailWithNullPathname()
    throws IOException {
        DirectoryUtils.makeDirectory((String) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void makeDirectory_FailWithEmptyPathname()
    throws IOException {
        DirectoryUtils.makeDirectory("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // DirectoryUtils.makeDirectoryAndParents
    //
    
    @Test
    public void makeDirectoryAndParents_PassWithNewDir()
    throws IOException {
        _makeDirectoryAndParents_PassWithNewDir(true);
        _makeDirectoryAndParents_PassWithNewDir(false);
    }
    
    private void _makeDirectoryAndParents_PassWithNewDir(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path);
            }
            Assert.assertTrue(path.isDirectory());
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectoryAndParents_PassWithDirExists()
    throws IOException {
        _makeDirectoryAndParents_PassWithDirExists(true);
        _makeDirectoryAndParents_PassWithDirExists(false);
    }
    
    private void _makeDirectoryAndParents_PassWithDirExists(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path);
            }
            Assert.assertTrue(path.isDirectory());
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectoryAndParents_FailWithRegularFileExists()
    throws IOException {
        _makeDirectoryAndParents_FailWithRegularFileExists(true);
        _makeDirectoryAndParents_FailWithRegularFileExists(false);
    }
    
    private void _makeDirectoryAndParents_FailWithRegularFileExists(boolean isString)
    throws IOException {
        File path = new File(UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectoryAndParents_FailWithParentIsRegularFile()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsRegularFile(true);
        _makeDirectoryAndParents_FailWithParentIsRegularFile(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsRegularFile(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path2);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectoryAndParents_FailWithParentIsRegularFile2()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsRegularFile2(true);
        _makeDirectoryAndParents_FailWithParentIsRegularFile2(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsRegularFile2(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path,
            UUID.randomUUID().toString() + File.separatorChar + UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path2);
            }
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = IOException.class)
    public void makeDirectoryAndParents_FailWithParentIsDirNotWritable()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsDirNotWritable(true);
        _makeDirectoryAndParents_FailWithParentIsDirNotWritable(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsDirNotWritable(boolean isString)
    throws IOException {
        String pathname = UUID.randomUUID().toString();
        File path = new File(pathname);
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(path.canWrite());
        path.setWritable(false);
        Assert.assertTrue(!path.canWrite());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                DirectoryUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                DirectoryUtils.makeDirectoryAndParents(path2);
            }
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectoryAndParents_FailWithNullPath()
    throws IOException {
        DirectoryUtils.makeDirectoryAndParents((File) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectoryAndParents_FailWithNullPathname()
    throws IOException {
        DirectoryUtils.makeDirectoryAndParents((String) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void makeDirectoryAndParents_FailWithEmptyPathname()
    throws IOException {
        DirectoryUtils.makeDirectoryAndParents("");
    }
}
