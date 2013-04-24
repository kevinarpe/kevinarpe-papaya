package com.nfshost.kevinarpe.papaya.Args;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
        	FileArgs.checkRegularFileExists(file);
        	FileArgs.checkRegularFileExists(filePath);
        	FileArgs.checkRegularFileExists(absFile);
        	FileArgs.checkRegularFileExists(absFilePath);
        }
        finally {
        	file.delete();
        }
    }
	
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile()
	throws FileNotFoundException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(dir);
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
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(dirPath);
        }
        finally {
        	dir.delete();
        }
    }
	
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile3()
	throws FileNotFoundException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(absDir);
        }
        finally {
        	dir.delete();
        }
    }
	
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExistsAsRegularFile4()
	throws FileNotFoundException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
        dir.mkdir();
        try {
            FileArgs.checkRegularFileExists(absDirPath);
        }
        finally {
        	dir.delete();
        }
    }
	
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists()
	throws FileNotFoundException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
    	FileArgs.checkRegularFileExists(file);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists2()
	throws FileNotFoundException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
    	FileArgs.checkRegularFileExists(filePath);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists3()
	throws FileNotFoundException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
    	FileArgs.checkRegularFileExists(absFile);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFileExists4()
	throws FileNotFoundException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
    	FileArgs.checkRegularFileExists(absFilePath);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInput()
	throws FileNotFoundException {
    	String filePath = null;
    	FileArgs.checkRegularFileExists(filePath);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckRegularFileExistsWithNullInput2()
	throws FileNotFoundException {
    	File file = null;
    	FileArgs.checkRegularFileExists(file);
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
        	FileArgs.checkDirectoryExists(dir);
        	FileArgs.checkDirectoryExists(dirPath);
        	FileArgs.checkDirectoryExists(absDir);
        	FileArgs.checkDirectoryExists(absDirPath);
        }
        finally {
        	dir.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory()
	throws IOException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
        	FileArgs.checkDirectoryExists(file);
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
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
        	FileArgs.checkDirectoryExists(filePath);
        }
        finally {
        	file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory3()
	throws IOException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
        	FileArgs.checkDirectoryExists(absFile);
        }
        finally {
        	file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckRegularFilesExistsAsDirectory4()
	throws IOException {
        File file = new File("test_file.txt");
        String filePath = file.getPath();
        File absFile = file.getAbsoluteFile();
        String absFilePath = absFile.getPath();
        file.createNewFile();
        try {
        	FileArgs.checkDirectoryExists(absFilePath);
        }
        finally {
        	file.delete();
        }
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists()
	throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
    	FileArgs.checkDirectoryExists(dir);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists2()
	throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
    	FileArgs.checkDirectoryExists(dirPath);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists3()
	throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
    	FileArgs.checkDirectoryExists(absDir);
    }
    
    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldNotCheckDirectoryExists4()
	throws IOException {
        File dir = new File("test_dir");
        String dirPath = dir.getPath();
        File absDir = dir.getAbsoluteFile();
        String absDirPath = absDir.getPath();
    	FileArgs.checkDirectoryExists(absDirPath);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInput()
    throws FileNotFoundException {
    	String dirPath = null;
    	FileArgs.checkDirectoryExists(dirPath);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotCheckDirectoryExistsWithNullInput2()
    throws FileNotFoundException {
    	File dir = null;
    	FileArgs.checkDirectoryExists(dir);
    }
}
