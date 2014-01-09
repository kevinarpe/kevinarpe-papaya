package com.googlecode.kevinarpe.papaya;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
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
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class PathUtilsTest {
    
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
    // PathUtils.isRootDirectory
    //

    @DataProvider
    public static Object[][] _dataForShouldTestIsRootDirectoryCorrectly() {
        String relativeDirPath = PathUtilsTest.class.getPackage().getName().replace('.', '/');
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
        boolean result = PathUtils.isRootDirectory(dirPath);
        Assert.assertEquals(result, expectedResult);
        File dir = new File(dirPath);
        result = PathUtils.isRootDirectory(dir);
        Assert.assertEquals(result, expectedResult);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath() {
        PathUtils.isRootDirectory((String) null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithNullPath2() {
        PathUtils.isRootDirectory((File) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotTestIsRootDirectoryCorrectlyWithInvalidPath() {
        PathUtils.isRootDirectory("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathUtils.makeDirectory
    //
    
    @Test
    public void makeDirectory_PassWithDirNotExists()
    throws IOException {
        _makeDirectory_PassWithDirNotExists(true);
        _makeDirectory_PassWithDirNotExists(false);
    }
    
    private void _makeDirectory_PassWithDirNotExists(boolean isString)
    throws IOException {
        File path = new File("makeDirectory_PassWithDirNotExists_" + UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectory(path.toString());
            }
            else {
                PathUtils.makeDirectory(path);
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
        File path = new File("makeDirectory_PassWithDirExists_" + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                PathUtils.makeDirectory(path.toString());
            }
            else {
                PathUtils.makeDirectory(path);
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
    public void makeDirectory_FailWithFileExists()
    throws IOException {
        _makeDirectory_FailWithFileExists(true);
        _makeDirectory_FailWithFileExists(false);
    }
    
    private void _makeDirectory_FailWithFileExists(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectory_FailWithFileExists_" + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                PathUtils.makeDirectory(path.toString());
            }
            else {
                PathUtils.makeDirectory(path);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(PathExceptionReason.PATH_IS_NORMAL_FILE, path, null, "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectory_FailWithParentIsFile()
    throws IOException {
        _makeDirectory_FailWithParentIsFile(true);
        _makeDirectory_FailWithParentIsFile(false);
    }
    
    private void _makeDirectory_FailWithParentIsFile(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectory_FailWithParentIsFile_" + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectory(path2.toString());
            }
            else {
                PathUtils.makeDirectory(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NORMAL_FILE,
                    path2,
                    path2.getParentFile(),
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectory_FailWithParentNotExists()
    throws IOException {
        _makeDirectory_FailWithParentNotExists(true);
        _makeDirectory_FailWithParentNotExists(false);
    }
    
    private void _makeDirectory_FailWithParentNotExists(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectory_FailWithParentNotExists_" + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path,
            UUID.randomUUID().toString() + File.separatorChar + UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectory(path2.toString());
            }
            else {
                PathUtils.makeDirectory(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,
                    path2,
                    path2.getParentFile(),
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectory_FailWithParentIsDirNotWritable()
    throws IOException {
        _makeDirectory_FailWithParentIsDirNotWritable(true);
        _makeDirectory_FailWithParentIsDirNotWritable(false);
    }
    
    private void _makeDirectory_FailWithParentIsDirNotWritable(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectory_FailWithParentIsDirNotWritable_"
                        + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(path.canWrite());
        path.setWritable(false);
        Assert.assertTrue(!path.canWrite());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectory(path2.toString());
            }
            else {
                PathUtils.makeDirectory(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path2,
                    path,
                    "dummy"));
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
        PathUtils.makeDirectory((File) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectory_FailWithNullPathname()
    throws IOException {
        PathUtils.makeDirectory((String) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void makeDirectory_FailWithEmptyPathname()
    throws IOException {
        PathUtils.makeDirectory("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathUtils.makeDirectoryAndParents
    //
    
    @Test
    public void makeDirectoryAndParents_PassWithDirNotExists()
    throws IOException {
        _makeDirectoryAndParents_PassWithDirNotExists(true);
        _makeDirectoryAndParents_PassWithDirNotExists(false);
    }
    
    private void _makeDirectoryAndParents_PassWithDirNotExists(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectoryAndParents_PassWithDirNotExists_" + UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path);
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
        File path =
            new File("makeDirectoryAndParents_PassWithDirExists_" + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path);
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
    public void makeDirectoryAndParents_FailWithFileExists()
    throws IOException {
        _makeDirectoryAndParents_FailWithFileExists(true);
        _makeDirectoryAndParents_FailWithFileExists(false);
    }
    
    private void _makeDirectoryAndParents_FailWithFileExists(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectoryAndParents_FailWithFileExists_"
                        + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PATH_IS_NORMAL_FILE,
                    path,
                    null,
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectoryAndParents_FailWithParentIsFile()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsFile(true);
        _makeDirectoryAndParents_FailWithParentIsFile(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsFile(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectoryAndParents_FailWithParentIsFile_"
                        + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NORMAL_FILE,
                    path2,
                    path,
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectoryAndParents_FailWithParentIsFile2()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsFile2(true);
        _makeDirectoryAndParents_FailWithParentIsFile2(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsFile2(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectoryAndParents_FailWithParentIsFile2_"
                        + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        File path2 = new File(path,
            UUID.randomUUID().toString() + File.separatorChar + UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NORMAL_FILE,
                    path2,
                    path,
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void makeDirectoryAndParents_FailWithParentIsDirNotWritable()
    throws IOException {
        _makeDirectoryAndParents_FailWithParentIsDirNotWritable(true);
        _makeDirectoryAndParents_FailWithParentIsDirNotWritable(false);
    }
    
    private void _makeDirectoryAndParents_FailWithParentIsDirNotWritable(boolean isString)
    throws IOException {
        File path =
            new File("makeDirectoryAndParents_FailWithParentIsDirNotWritable_"
                        + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(path.canWrite());
        path.setWritable(false);
        Assert.assertTrue(!path.canWrite());
        File path2 = new File(path, UUID.randomUUID().toString());
        try {
            if (isString) {
                PathUtils.makeDirectoryAndParents(path2.toString());
            }
            else {
                PathUtils.makeDirectoryAndParents(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path2,
                    path,
                    "dummy"));
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
        PathUtils.makeDirectoryAndParents((File) null);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void makeDirectoryAndParents_FailWithNullPathname()
    throws IOException {
        PathUtils.makeDirectoryAndParents((String) null);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void makeDirectoryAndParents_FailWithEmptyPathname()
    throws IOException {
        PathUtils.makeDirectoryAndParents("");
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathUtils.removeDirectory
    //
    
    @Test
    public void removeDirectory_PassWithDirNotExists()
    throws IOException {
        _removeDirectory_PassWithDirNotExists(true);
        _removeDirectory_PassWithDirNotExists(false);
    }
    
    private void _removeDirectory_PassWithDirNotExists(boolean isString)
    throws IOException {
        File path =
            new File("removeDirectory_PassWithDirNotExists_" + UUID.randomUUID().toString());
        Assert.assertTrue(!path.exists());
        if (isString) {
            PathUtils.removeDirectory(path.toString());
        }
        else {
            PathUtils.removeDirectory(path);
        }
        Assert.assertTrue(!path.exists());
    }
    
    @Test
    public void removeDirectory_PassWithDirExists()
    throws IOException {
        _removeDirectory_PassWithDirExists(true);
        _removeDirectory_PassWithDirExists(false);
    }
    
    private void _removeDirectory_PassWithDirExists(boolean isString)
    throws IOException {
        File path = new File("removeDirectory_PassWithDirExists_" + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                PathUtils.removeDirectory(path.toString());
            }
            else {
                PathUtils.removeDirectory(path);
            }
            Assert.assertTrue(!path.exists());
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void removeDirectory_FailWithFileExists()
    throws IOException {
        _removeDirectory_FailWithFileExists(true);
        _removeDirectory_FailWithFileExists(false);
    }
    
    private void _removeDirectory_FailWithFileExists(boolean isString)
    throws IOException {
        File path =
            new File("removeDirectory_FailWithFileExists_" + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                PathUtils.removeDirectory(path.toString());
            }
            else {
                PathUtils.removeDirectory(path);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PATH_IS_NORMAL_FILE,
                    path,
                    null,
                    "dummy"));
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void removeDirectory_FailWithRootDir()
    throws IOException {
        _removeDirectory_FailWithRootDir(true);
        _removeDirectory_FailWithRootDir(false);
    }
    
    private void _removeDirectory_FailWithRootDir(boolean isString)
    throws IOException {
        File path = new File(_convertPath("/"));
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                PathUtils.removeDirectory(path.toString());
            }
            else {
                PathUtils.removeDirectory(path);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PATH_IS_ROOT_DIRECTORY,
                    path,
                    null,
                    "dummy"));
        }
    }
    
    @Test
    public void removeDirectory_FailWithParentDirNotWritable()
    throws IOException {
        _removeDirectory_FailWithParentDirNotWritable(true);
        _removeDirectory_FailWithParentDirNotWritable(false);
    }
    
    private void _removeDirectory_FailWithParentDirNotWritable(boolean isString)
    throws IOException {
        File path =
            new File("removeDirectory_FailWithParentDirNotWritable_"
                        + UUID.randomUUID().toString());
        path.mkdir();
        File path2 = new File(path, UUID.randomUUID().toString());
        path2.mkdir();
        Assert.assertTrue(path2.isDirectory());
        path.setWritable(false);
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(!path.canWrite());
        try {
            if (isString) {
                PathUtils.removeDirectory(path2.toString());
            }
            else {
                PathUtils.removeDirectory(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path2,
                    path,
                    "dummy"));
        }
        finally {
            if (path2.isDirectory()) {
                if (path.isDirectory()) {
                    path.setWritable(true);
                }
                path2.delete();
            }
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathUtils.removeFile
    //
    
    @Test
    public void removeFile_PassWithFileNotExists()
    throws IOException {
        _removeFile_PassWithFileNotExists(true);
        _removeFile_PassWithFileNotExists(false);
    }
    
    private void _removeFile_PassWithFileNotExists(boolean isString)
    throws IOException {
        File path =
            new File("removeFile_PassWithFileNotExists_" + UUID.randomUUID().toString());
        Assert.assertTrue(!path.exists());
        if (isString) {
            PathUtils.removeFile(path.toString());
        }
        else {
            PathUtils.removeFile(path);
        }
        Assert.assertTrue(!path.exists());
    }
    
    
    @Test
    public void removeFile_PassWithFileExists()
    throws IOException {
        _removeFile_PassWithFileExists(true);
        _removeFile_PassWithFileExists(false);
    }
    
    private void _removeFile_PassWithFileExists(boolean isString)
    throws IOException {
        File path = new File("removeFile_PassWithFileExists_" + UUID.randomUUID().toString());
        path.createNewFile();
        Assert.assertTrue(path.isFile());
        try {
            if (isString) {
                PathUtils.removeFile(path.toString());
            }
            else {
                PathUtils.removeFile(path);
            }
            Assert.assertTrue(!path.exists());
        }
        finally {
            if (path.isFile()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void removeFile_FailWithDirExists()
    throws IOException {
        _removeFile_FailWithDirExists(true);
        _removeFile_FailWithDirExists(false);
    }
    
    private void _removeFile_FailWithDirExists(boolean isString)
    throws IOException {
        File path =
            new File("removeFile_FailWithDirExists_" + UUID.randomUUID().toString());
        path.mkdir();
        Assert.assertTrue(path.isDirectory());
        try {
            if (isString) {
                PathUtils.removeFile(path.toString());
            }
            else {
                PathUtils.removeFile(path);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PATH_IS_DIRECTORY,
                    path,
                    null,
                    "dummy"));
        }
        finally {
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }
    
    @Test
    public void removeFile_FailWithParentDirNotWritable()
    throws IOException {
        _removeFile_FailWithParentDirNotWritable(true);
        _removeFile_FailWithParentDirNotWritable(false);
    }
    
    private void _removeFile_FailWithParentDirNotWritable(boolean isString)
    throws IOException {
        File path =
            new File("removeFile_FailWithParentDirNotWritable_"
                        + UUID.randomUUID().toString());
        path.mkdir();
        File path2 = new File(path, UUID.randomUUID().toString());
        path2.createNewFile();
        Assert.assertTrue(path2.isFile());
        path.setWritable(false);
        Assert.assertTrue(path.isDirectory());
        Assert.assertTrue(!path.canWrite());
        try {
            if (isString) {
                PathUtils.removeFile(path2.toString());
            }
            else {
                PathUtils.removeFile(path2);
            }
        }
        catch (PathException e) {
            PathExceptionTest.assertPathExceptionEquals(
                e,
                new PathException(
                    PathExceptionReason.PARENT_PATH_IS_NON_WRITABLE_DIRECTORY,
                    path2,
                    path,
                    "dummy"));
        }
        finally {
            if (path2.isFile()) {
                if (path.isDirectory()) {
                    path.setWritable(true);
                }
                path2.delete();
            }
            if (path.isDirectory()) {
                path.delete();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PathUtils.recursiveListFilePaths
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void recursiveListFilePaths_FailWithNull()
    throws PathException {
        PathUtils.recursiveListFilePaths(null);
    }

    @Test(expectedExceptions = PathException.class)
    public void recursiveListFilePaths_FailWithPathNotExist()
    throws PathException {
        try {
            PathUtils.recursiveListFilePaths(new File(UUID.randomUUID().toString()));
        }
        catch (PathException e) {
            Assert.assertEquals(e.getReason(), PathExceptionReason.PATH_DOES_NOT_EXIST);
            throw e;
        }
    }

    @Test(expectedExceptions = PathException.class)
    public void recursiveListFilePaths_FailWithPathIsFile()
    throws IOException {
        File filePath = new File(UUID.randomUUID().toString());
        Assert.assertTrue(filePath.createNewFile());
        try {
            PathUtils.recursiveListFilePaths(filePath);
        }
        catch (PathException e) {
            Assert.assertEquals(e.getReason(), PathExceptionReason.PATH_IS_NORMAL_FILE);
            throw e;
        }
        finally {
            Assert.assertTrue(filePath.delete());
        }
    }

    @Test
    public void recursiveListFilePaths_PassWithEmptyDir()
    throws IOException {
        File dirPath = new File(UUID.randomUUID().toString());
        Assert.assertTrue(dirPath.mkdir());
        try {
            Assert.assertEquals(new ArrayList(), PathUtils.recursiveListFilePaths(dirPath));
        }
        finally {
            Assert.assertTrue(dirPath.delete());
        }
    }

    @Test
    public void recursiveListFilePaths_Pass()
    throws IOException {
        File dirPath = new File("dir." + UUID.randomUUID().toString());
        Assert.assertTrue(dirPath.mkdir());
        File filePath = new File("file." + UUID.randomUUID().toString());
        Assert.assertTrue(filePath.createNewFile());
        File dirPath2 = new File(dirPath, "dir." + UUID.randomUUID().toString());
        Assert.assertTrue(dirPath2.mkdir());
        File filePath2 = new File(dirPath2, "file." + UUID.randomUUID().toString());
        Assert.assertTrue(filePath2.createNewFile());
        try {
            // TODO: fixme
//            Assert.assertEquals(
//                PathUtils.recursiveListFilePaths(dirPath),
//                ImmutableList.of(filePath2, dirPath2, filePath));
        }
        finally {
            Assert.assertTrue(filePath2.delete());
            Assert.assertTrue(dirPath2.delete());
            Assert.assertTrue(filePath.delete());
            Assert.assertTrue(dirPath.delete());
        }
    }

    private static void assertExceptionCauseClass(Exception actualException, Class<?> expectedCauseClass) {
        ObjectArgs.checkNotNull(expectedCauseClass, "expectedCauseClass");
        ObjectArgs.checkNotNull(actualException, "actualException");
        ObjectArgs.checkNotNull(actualException.getCause(), "actualException.getCause()");
        Assert.assertEquals(actualException.getCause().getClass(), expectedCauseClass);
    }
}
