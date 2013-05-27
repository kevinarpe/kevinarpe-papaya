package com.googlecode.kevinarpe.papaya.exceptions;

import org.testng.Assert;

import com.googlecode.kevinarpe.papaya.args.ObjectArgs;

public final class PathException2Test {

    public static void assertPathExceptionEquals(PathException2 actual, PathException2 expected) {
        ObjectArgs.checkNotNull(actual, "actual");
        ObjectArgs.checkNotNull(expected, "expected");
        Assert.assertEquals(actual.getPath(), expected.getPath());
        Assert.assertEquals(actual.getOptParentPath(), expected.getOptParentPath());
        Assert.assertEquals(actual.getReason(), expected.getReason());
    }
}
