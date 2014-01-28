package com.googlecode.kevinarpe.papaya.exception;

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

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.exception.ClassResourceNotFoundException;

public final class ClassResourceNotFoundExceptionTest {

    @DataProvider
    public static Object[][] _Pass_Data() {
        return new Object[][] {
                {
                    String.class,
                    "/path/to/resource",
                    UUID.randomUUID().toString(),
                    new Exception(
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                {
                    Integer.class,
                    "/another/path/to/resource",
                    UUID.randomUUID().toString(),
                    new Exception(
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
        };
    }
    
    @DataProvider
    public static Object[][] _Fail_Data() {
        return new Object[][] {
                ///////////////////////////////////////////////////////////////
                // Group w/o null optCause
                //
                { NullPointerException.class,
                    null,  // classForResource (bad)
                    "/path/to/resource",  // resourceName
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    String.class,  // classForResource
                    null,  // resourceName (bad)
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "",  // resourceName (bad)
                    UUID.randomUUID().toString(),  // message
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { NullPointerException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    null,  // message (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    "",  // message (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    "   ",  // message (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString()))
                },

                ///////////////////////////////////////////////////////////////
                // Group w/o null optCause
                //
                { NullPointerException.class,
                    null,  // classForResource (bad)
                    "/path/to/resource",  // resourceName
                    UUID.randomUUID().toString(),  // message
                    null  // optCause
                },
                { NullPointerException.class,
                    String.class,  // classForResource
                    null,  // resourceName (bad)
                    UUID.randomUUID().toString(),  // message
                    null  // optCause
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "",  // resourceName (bad)
                    UUID.randomUUID().toString(),  // message
                    null  // optCause
                },
                { NullPointerException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    null,  // message (bad)
                    null  // optCause
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    "",  // message (bad)
                    null  // optCause
                },
                { IllegalArgumentException.class,
                    String.class,  // classForResource
                    "/path/to/resource",  // resourceName
                    "   ",  // message (bad)
                    null  // optCause
                },
        };
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ClassResourceNotFoundException.ctor()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void ctor_Pass(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Pass(classForResource, resourceName, message, cause);
        _ctor_Pass(classForResource, resourceName, message, optCause);
    }

    private static void _ctor_Pass(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        ClassResourceNotFoundException e1 =
            new ClassResourceNotFoundException(
                classForResource, resourceName, message, optCause);
        ClassResourceNotFoundException e2 =
            new ClassResourceNotFoundException(classForResource, resourceName, message);
        Assert.assertEquals(optCause, e1.getCause());
        Assert.assertEquals(null, e2.getCause());
    }

    @Test(dataProvider = "_Fail_Data")
    public static void ctor_Fail(
            Class<?> exceptionClass,
            Class<?> classForResource,
            String resourceName,
            String message,
            Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Fail(exceptionClass, classForResource, resourceName, message, cause);
        _ctor_Fail(exceptionClass, classForResource, resourceName, message, optCause);
    }
    
    private static void _ctor_Fail(
            Class<?> exceptionClass,
            Class<?> classForResource,
            String resourceName,
            String message,
            Throwable optCause) {
        try {
            new ClassResourceNotFoundException(classForResource, resourceName, message, optCause);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
        try {
            new ClassResourceNotFoundException(classForResource, resourceName, message);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // ClassResourceNotFoundException.copyCtor/getClassForResource()/getResourceName()
    //
    
    @Test(expectedExceptions = NullPointerException.class)
    public static void copyCtor_FailWithNull() {
        new ClassResourceNotFoundException(null);
    }
    
    @Test(dataProvider = "_Pass_Data")
    public static void copyCtorAndAccessors_Pass(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _copyCtorAndAccessors_Pass(classForResource, resourceName, message, cause);
        _copyCtorAndAccessors_Pass(classForResource, resourceName, message, optCause);
    }
    
    private static void _copyCtorAndAccessors_Pass(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        ClassResourceNotFoundException e =
            new ClassResourceNotFoundException(classForResource, resourceName, message, optCause);
        Assert.assertEquals(classForResource, e.getClassForResource());
        Assert.assertEquals(resourceName, e.getResourceName());
        Assert.assertEquals(message, e.getMessage());
        Assert.assertEquals(optCause, e.getCause());
        
        ClassResourceNotFoundException e2 = new ClassResourceNotFoundException(e);
        Assert.assertEquals(e.getClassForResource(), e2.getClassForResource());
        Assert.assertEquals(e.getResourceName(), e2.getResourceName());
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
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        ClassResourceNotFoundException[] e1 = new ClassResourceNotFoundException[2];
        String m = message;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e1[i] = new ClassResourceNotFoundException(classForResource, resourceName, m, optCause);
            m = new String(m);
        }
        // This duplicates work done by EqualsTester() below, but acts as a conveninent debug
        // breakpoint when this test fails.
        Assert.assertEquals(e1[0], e1[1]);
        Assert.assertEquals(e1[0].hashCode(), e1[1].hashCode());
        
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        ClassResourceNotFoundException[] e2 = new ClassResourceNotFoundException[2];
        m = message;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e2[i] = new ClassResourceNotFoundException(classForResource, resourceName, m, cause);
            m = new String(m);
        }
        // This duplicates work done by EqualsTester() below, but acts as a conveninent debug
        // breakpoint when this test fails.
        Assert.assertEquals(e2[0], e2[1]);
        Assert.assertEquals(e2[0].hashCode(), e2[1].hashCode());

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
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _toString_Pass(classForResource, resourceName, message, cause);
        _toString_Pass(classForResource, resourceName, message, optCause);
    }
    
    private static void _toString_Pass(
            Class<?> classForResource, String resourceName, String message, Throwable optCause) {
        ClassResourceNotFoundException e1 =
            new ClassResourceNotFoundException(classForResource, resourceName, message, optCause);
        String s1 = e1.toString();
        Assert.assertTrue(s1.contains(message));
        
        String message2 = UUID.randomUUID().toString();
        ClassResourceNotFoundException e2 =
            new ClassResourceNotFoundException(classForResource, resourceName, message,
                new Exception(message2, optCause));
        String s2 = e2.toString();
        Assert.assertTrue(s2.contains(message));
        Assert.assertTrue(s2.contains(message2));
        Assert.assertNotEquals(s1, s2);
        
        String message3 = UUID.randomUUID().toString();
        ClassResourceNotFoundException e3 =
            new ClassResourceNotFoundException(classForResource, resourceName, message,
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
        ClassResourceNotFoundException e4 =
            new ClassResourceNotFoundException(classForResource, resourceName, message,
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
