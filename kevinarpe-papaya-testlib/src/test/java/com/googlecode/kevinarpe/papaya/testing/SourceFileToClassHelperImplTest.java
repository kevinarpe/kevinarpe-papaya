package com.googlecode.kevinarpe.papaya.testing;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.UUID;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SourceFileToClassHelperImplTest {

    private static final File THIS_SOURCE_FILE_PATH = new File(
        String.format(
            "kevinarpe-papaya-testlib/src/test/java/com/googlecode/kevinarpe/papaya/testing/%s.java",
            SourceFileToClassHelperImplTest.class.getSimpleName()));

    private SourceFileToClassHelperImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new SourceFileToClassHelperImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SourceFileToClassHelperImplTest.getClass()
    //

    @Test
    public void getClass_Pass()
    throws ClassNotFoundException {
        Class<?> thisClass = classUnderTest.getClass(THIS_SOURCE_FILE_PATH);
        Assert.assertSame(thisClass, SourceFileToClassHelperImplTest.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getClass_FailWithNull()
    throws ClassNotFoundException {
        classUnderTest.getClass((File) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getClass_FailWithBadFileSuffix()
    throws ClassNotFoundException {
        classUnderTest.getClass(new File(UUID.randomUUID().toString()));
    }

    @DataProvider
    private static Object[][] _getClass_FailWithClassNotFoundException_Data() {
        return new Object[][] {
            {
                new File(UUID.randomUUID().toString() + SourceFileToClassHelperImpl.JAVA_SUFFIX)
            },
            {
                new File(
                    new File(UUID.randomUUID().toString()),
                    UUID.randomUUID().toString() + SourceFileToClassHelperImpl.JAVA_SUFFIX)
            },
        };
    }

    @Test(dataProvider = "_getClass_FailWithClassNotFoundException_Data",
            expectedExceptions = ClassNotFoundException.class)
    public void getClass_FailWithClassNotFoundException(File path)
    throws ClassNotFoundException {
        classUnderTest.getClass(path);
    }
}
