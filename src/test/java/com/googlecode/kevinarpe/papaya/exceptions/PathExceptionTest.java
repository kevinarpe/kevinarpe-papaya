package com.googlecode.kevinarpe.papaya.exceptions;

import org.testng.Assert;

import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

public final class PathExceptionTest {

    public static void assertPathExceptionEquals(PathException actual, PathException expected) {
        ObjectArgs.checkNotNull(actual, "actual");
        ObjectArgs.checkNotNull(expected, "expected");
        Assert.assertEquals(actual.getReason(), expected.getReason());
        Assert.assertEquals(actual.getAbsPath(), expected.getAbsPath());
        Assert.assertEquals(actual.getOptAbsParentPath(), expected.getOptAbsParentPath());
    }
}
