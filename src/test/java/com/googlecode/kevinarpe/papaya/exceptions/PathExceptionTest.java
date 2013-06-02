package com.googlecode.kevinarpe.papaya.exceptions;

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
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.args.ObjectArgs;
import com.googlecode.kevinarpe.papaya.exceptions.PathException.PathExceptionReason;

public final class PathExceptionTest {

    public static void assertPathExceptionEquals(PathException actual, PathException expected) {
        ObjectArgs.checkNotNull(actual, "actual");
        ObjectArgs.checkNotNull(expected, "expected");
        Assert.assertEquals(actual.getReason(), expected.getReason());
        Assert.assertEquals(actual.getAbsPath(), expected.getAbsPath());
        Assert.assertEquals(actual.getOptAbsParentPath(), expected.getOptAbsParentPath());
    }
    
    @DataProvider
    private static final Object[][] _Pass_Data() {
        return new Object[][] {
                { PathExceptionReason.PATH_DOES_NOT_EXIST,
                    new File(UUID.randomUUID().toString()),
                    null,
                    UUID.randomUUID().toString(),
                    new Exception(
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,
                    new File(UUID.randomUUID().toString()),
                    new File(UUID.randomUUID().toString()),
                    UUID.randomUUID().toString(),
                    new Exception(
                            UUID.randomUUID().toString(),
                            new Exception(UUID.randomUUID().toString()))
                },
        };
    }
    
    @DataProvider
    private static final Object[][] _Fail_Data() {
        return new Object[][] {
                ///////////////////////////////////////////////////////////////
                // Group 1 w/ non-null optCause
                //
                { NullPointerException.class,
                    null,  // reason (bad)
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    null,  // path (bad)
                    null,  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath (bad)
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    null,  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    "",  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    "   ",  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                
                ///////////////////////////////////////////////////////////////
                // Group 1 w/ null optCause
                //
                { NullPointerException.class,
                    null,  // reason (bad)
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { NullPointerException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    null,  // path (bad)
                    null,  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath (bad)
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { NullPointerException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    null,  // message (bad)
                    null  // cause
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    "",  // message (bad)
                    null  // cause
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath
                    "   ",  // message (bad)
                    null  // cause
                },

                ///////////////////////////////////////////////////////////////
                // Group 2 w/ non-null optCause
                //
                { NullPointerException.class,
                    null,  // reason (bad)
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    null,  // path (bad)
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath (bad)
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    null,  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    "",  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    "   ",  // message (bad)
                    new Exception(  // cause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                
                ///////////////////////////////////////////////////////////////
                // Group 2 w/ null optCause
                //
                { NullPointerException.class,
                    null,  // reason (bad)
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    null,  // path (bad)
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    null,  // optParentPath (bad)
                    UUID.randomUUID().toString(),  // message
                    null  // cause
                },
                { NullPointerException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    null,  // message (bad)
                    null  // cause
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    "",  // message (bad)
                    null  // cause
                },
                { IllegalArgumentException.class,
                    PathExceptionReason.PARENT_PATH_DOES_NOT_EXIST,  // reason
                    new File(UUID.randomUUID().toString()),  // path
                    new File(UUID.randomUUID().toString()),  // optParentPath
                    "   ",  // message (bad)
                    null  // cause
                },
        };
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathException.ctor()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void ctor_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Pass(reason, path, optParentPath, message, cause);
        _ctor_Pass(reason, path, optParentPath, message, optCause);
    }

    private static void _ctor_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        PathException e1 = new PathException(reason, path, optParentPath, message, optCause);
        PathException e2 = new PathException(reason, path, optParentPath, message);
        Assert.assertEquals(optCause, e1.getCause());
        Assert.assertEquals(null, e2.getCause());
    }

    @Test(dataProvider = "_Fail_Data")
    public static void ctor_Fail(
            Class<?> exceptionClass,
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Fail(exceptionClass, reason, path, optParentPath, message, cause);
        _ctor_Fail(exceptionClass, reason, path, optParentPath, message, optCause);
    }
    
    private static void _ctor_Fail(
            Class<?> exceptionClass,
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        try {
            new PathException(reason, path, optParentPath, message, optCause);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
        try {
            new PathException(reason, path, optParentPath, message);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathException.copyCtor/getReason()/getAbsPath()/getOptAbsParentPath()
    //
    
    @Test(expectedExceptions = NullPointerException.class)
    public static void copyCtor_FailWithNull() {
        new PathException(null);
    }
    
    @Test(dataProvider = "_Pass_Data")
    public static void copyCtorAndAccessors_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _copyCtorAndAccessors_Pass(reason, path, optParentPath, message, cause);
        _copyCtorAndAccessors_Pass(reason, path, optParentPath, message, optCause);
    }
    
    private static void _copyCtorAndAccessors_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        PathException e = new PathException(reason, path, optParentPath, message, optCause);
        Assert.assertEquals(reason, e.getReason());
        Assert.assertEquals(path.getAbsoluteFile(), e.getAbsPath());
        Assert.assertEquals(
            (null == optParentPath ? null : optParentPath.getAbsoluteFile()),
            e.getOptAbsParentPath());
        Assert.assertEquals(message, e.getMessage());
        Assert.assertEquals(optCause, e.getCause());
        
        PathException e2 = new PathException(e);
        Assert.assertEquals(e.getReason(), e2.getReason());
        Assert.assertEquals(e.getAbsPath(), e2.getAbsPath());
        Assert.assertEquals(e.getOptAbsParentPath(), e2.getOptAbsParentPath());
        Assert.assertEquals(e.getMessage(), e2.getMessage());
        Assert.assertEquals(e.getCause(), e2.getCause());
        Assert.assertTrue(e.equalsExcludingStackTrace(e2));
        Assert.assertTrue(e2.equalsExcludingStackTrace(e));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathException.hashCode()/equals()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void hashCodeAndEquals_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        PathException[] e1 = new PathException[2];
        String m = message;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e1[i] = new PathException(reason, path, optParentPath, m, optCause);
            m = new String(m);
        }
        
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        PathException[] e2 = new PathException[2];
        m = message;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e2[i] = new PathException(reason, path, optParentPath, m, cause);
            m = new String(m);
        }

        new EqualsTester()
            .addEqualityGroup(e1[0], e1[1])
            .addEqualityGroup(e2[0], e2[1])
            .testEquals();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // PathException.toString()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void toString_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _toString_Pass(reason, path, optParentPath, message, cause);
        _toString_Pass(reason, path, optParentPath, message, optCause);
    }
    
    private static void _toString_Pass(
            PathExceptionReason reason,
            File path,
            File optParentPath,
            String message,
            Throwable optCause) {
        PathException e1 = new PathException(reason, path, optParentPath, message, optCause);
        String s1 = e1.toString();
        Assert.assertTrue(s1.contains(message));
        
        String message2 = UUID.randomUUID().toString();
        PathException e2 =
            new PathException(reason, path, optParentPath, message,
                new Exception(message2, optCause));
        String s2 = e2.toString();
        Assert.assertTrue(s2.contains(message));
        Assert.assertTrue(s2.contains(message2));
        Assert.assertNotEquals(s1, s2);
        
        String message3 = UUID.randomUUID().toString();
        PathException e3 =
            new PathException(reason, path, optParentPath, message,
                new Exception(
                    message2,
                    new Exception(message3, optCause)));
        String s3 = e3.toString();
        Assert.assertTrue(s3.contains(message));
        Assert.assertTrue(s3.contains(message2));
        Assert.assertTrue(s3.contains(message3));
        Assert.assertNotEquals(s1, s2);
        Assert.assertNotEquals(s2, s3);
        
        String message4 = UUID.randomUUID().toString();
        PathException e4 =
            new PathException(reason, path, optParentPath, message,
                new Exception(
                    message2,
                    new Exception(
                        message3, 
                        new Exception(message4, optCause))));
        String s4 = e4.toString();
        Assert.assertTrue(s4.contains(message));
        Assert.assertTrue(s4.contains(message2));
        Assert.assertTrue(s4.contains(message3));
        Assert.assertTrue(s4.contains(message4));
        Assert.assertNotEquals(s1, s2);
        Assert.assertNotEquals(s2, s3);
        Assert.assertNotEquals(s3, s4);
    }
}
