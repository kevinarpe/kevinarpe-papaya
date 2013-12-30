package com.googlecode.kevinarpe.papaya.filesystem.comparator;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */

public class FileAbsolutePathLexographicalComparatorTest {

    // TODO: Convert this to a base test class to share with other comparators

    private FileAbsolutePathLexographicalComparator classUnderTest;

    @BeforeMethod
    private void beforeEachTestMethod() {
        classUnderTest = new FileAbsolutePathLexographicalComparator();
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private File mockPath1;

    @Mock
    private File mockPath2;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private int compareStrings(String left, String right) {
        int result = left.compareTo(right);
        if (0 != result) {
            result /= Math.abs(result);
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // compare()
    //

    @DataProvider
    private static Object[][] compare_Pass_Data() {
        return new Object[][] {
            { "abc/123", "def/456" },
        };
    }

    @Test(dataProvider = "compare_Pass_Data")
    public void compare_Pass(String pathname1, String pathname2) {
        when(mockPath1.getAbsolutePath()).thenReturn(pathname1);
        when(mockPath2.getAbsolutePath()).thenReturn(pathname2);

        assertEquals(
            classUnderTest.compare(mockPath1, mockPath2),
            compareStrings(pathname1, pathname2));

        assertEquals(
            classUnderTest.compare(mockPath2, mockPath1),
            compareStrings(pathname2, pathname1));

        assertEquals(
            classUnderTest.compare(mockPath1, mockPath1),
            compareStrings(pathname1, pathname1));

        assertEquals(
            classUnderTest.compare(mockPath1, mockPath1),
            0);

        assertEquals(
            classUnderTest.compare(mockPath2, mockPath2),
            compareStrings(pathname2, pathname2));

        assertEquals(
            classUnderTest.compare(mockPath2, mockPath2),
            0);
    }

    @DataProvider
    private static Object[][] compare_Fail_Data() {
        File path = new File("abc");
        return new Object[][] {
            { path, null },
            { null, path },
            { null, null },
        };
    }

    @Test(expectedExceptions = NullPointerException.class,
            dataProvider = "compare_Fail_Data")
    public void compare_Fail(File path1, File path2) {
        classUnderTest.compare(path1, path2);
    }
}
