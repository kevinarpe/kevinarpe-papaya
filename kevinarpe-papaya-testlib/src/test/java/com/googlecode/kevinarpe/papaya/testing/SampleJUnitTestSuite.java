package com.googlecode.kevinarpe.papaya.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.regex.Pattern;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@RunWith(SampleJUnitTestSuite.class)
public class SampleJUnitTestSuite
extends Suite {

    private static final Class[] TEST_CLASS_ARR =
        new TestClassFinder()
            .includeByAbsolutePathPattern(
                Pattern.compile("^.*/kevinarpe-papaya-testlib/src/test/.*Test\\.java$"))
            .findAsArray();

    public SampleJUnitTestSuite(Class<?> clazz) throws InitializationError {
        super(clazz, TEST_CLASS_ARR);
    }
}
