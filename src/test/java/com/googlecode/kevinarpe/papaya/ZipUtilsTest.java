package com.googlecode.kevinarpe.papaya;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.googlecode.kevinarpe.papaya.argument.PathArgsTest;
import com.googlecode.kevinarpe.papaya.exception.PathException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class ZipUtilsTest {
    
    public static final File SAMPLE_DIR_PATH = new File("src/test/resources");
    
    public static final File NOT_COMPRESSED_FILE_PATH =
        new File(SAMPLE_DIR_PATH, "sample.txt");
    
    public static final File GZIP_COMPRESSED_FILE_PATH =
        new File(SAMPLE_DIR_PATH, "sample2.txt.gz");
    
    ///////////////////////////////////////////////////////////////////////////
    // ZipUtils.isGZipFile
    //

    @DataProvider
    private static final Object[][] _isGZipFile_Pass_Data() {
        return new Object[][] {
                { GZIP_COMPRESSED_FILE_PATH, true },
                { NOT_COMPRESSED_FILE_PATH, false },
        };
    }
    
    @Test(dataProvider = "_isGZipFile_Pass_Data")
    public void isGZipFile_Pass(File path, boolean expected)
    throws IOException {
        boolean actual = ZipUtils.isGZipFile(path);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider
    private static final Object[][] _isGZipFile_Fail_Data() {
        return new Object[][] {
                { null, NullPointerException.class },
                { new File(UUID.randomUUID().toString()), PathException.class },
                { SAMPLE_DIR_PATH, PathException.class },
        };
    }
    
    @Test(dataProvider = "_isGZipFile_Fail_Data")
    public void isGZipFile_Fail(File path, Class<?> expectedExceptionClass)
    throws Exception {
        try {
            ZipUtils.isGZipFile(path);
        }
        catch (Exception e) {
            if (expectedExceptionClass != e.getClass()) {
                throw e;
            }
        }
    }
    
    @Test
    public void isGZipFile_Pass2()
    throws IOException {
        File filePath = new File(UUID.randomUUID().toString());
        FileOutputStream fout = new FileOutputStream(filePath);
        try {
            int byte0 = ZipUtils.GZIP_MAGIC_BYTE_LIST.get(0);
            fout.write(byte0);
            boolean x = ZipUtils.isGZipFile(filePath);
            Assert.assertFalse(x);
        }
        finally {
            try {
                fout.close();
            }
            catch (IOException e) {
                // ignore
            }
            PathArgsTest.safeRm(filePath);
        }
    }
    
    @Test
    public void blah()
    throws FileNotFoundException, IOException {
        // TODO: Rethink this GZIP stuff.
        // Might need this:
        // FileInputStream x = new FileInputStream(...);
        // ReplayInputStream y = new ReplayInputStream(x);
        // try {
        //     GZIPInputStream z = new GZIPInputStream(y);
        //     return z;
        // }
        // catch (Exception e) {
        //     // Maybe try other compressed streams here?
        //     y.rewind(0)
        //     return y;
        // }
        GZIPInputStream gzfin = new GZIPInputStream(new FileInputStream(GZIP_COMPRESSED_FILE_PATH));
    }
}
