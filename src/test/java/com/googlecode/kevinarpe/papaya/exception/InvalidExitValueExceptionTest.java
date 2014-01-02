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

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.primitives.Ints;
import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.exception.InvalidExitValueException;

public final class InvalidExitValueExceptionTest {

    @DataProvider
    public static Object[][] _Pass_Data() {
        return new Object[][] {
                {
                    0,  // exitValue
                    Ints.asList(1),  // validExitValueCollection
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    "Random: %s",  // format
                    new Object[] { UUID.randomUUID().toString() },  // optArgArr
                },
                {
                    99,  // exitValue
                    Ints.asList(97, 98, 100, 101),  // validExitValueCollection
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
        };
    }
    
    @DataProvider
    public static Object[][] _Fail_Data() {
        return new Object[][] {
                ///////////////////////////////////////////////////////////////
                // Group 1 w/ non-null optCause
                //
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    null,  // validExitValueCollection (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(0, 1, 2, null),  // validExitValueCollection (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(0, 1, 2, null),  // validExitValueCollection
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    null,  // format (bad)
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(),  // validExitValueCollection (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(3, 2, 1, 0),  // validExitValueCollection (bad)
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(1),  // validExitValueCollection
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    "",  // format (bad)
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(1),  // validExitValueCollection
                    new Exception(  // optCause
                        UUID.randomUUID().toString(),
                        new Exception(UUID.randomUUID().toString())),
                    "   ",  // format (bad)
                    new Object[] { },  // optArgArr
                },
                ///////////////////////////////////////////////////////////////
                // Group 2 w/ null optCause
                //
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    null,  // validExitValueCollection (bad)
                    null,  // optCause
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(0, 1, 2, null),  // validExitValueCollection (bad)
                    null,  // optCause
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    NullPointerException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(0, 1, 2, null),  // validExitValueCollection
                    null,  // optCause
                    null,  // format (bad)
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(),  // validExitValueCollection (bad)
                    null,  // optCause
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(3, 2, 1, 0),  // validExitValueCollection (bad)
                    null,  // optCause
                    UUID.randomUUID().toString(),  // format
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(1),  // validExitValueCollection
                    null,  // optCause
                    "",  // format (bad)
                    new Object[] { },  // optArgArr
                },
                {
                    IllegalArgumentException.class,  // exceptionClass
                    0,  // exitValue
                    Arrays.asList(1),  // validExitValueCollection
                    null,  // optCause
                    "   ",  // format (bad)
                    new Object[] { },  // optArgArr
                },
        };
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // InvalidExitValueException.ctor()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void ctor_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Pass(exitValue, validExitValueCollection, cause, format, optArgArr);
        _ctor_Pass(exitValue, validExitValueCollection, optCause, format, optArgArr);
    }

    private static void _ctor_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        InvalidExitValueException e1 =
            new InvalidExitValueException(
                exitValue, validExitValueCollection, optCause, format, optArgArr);
        InvalidExitValueException e2 =
            new InvalidExitValueException(exitValue, validExitValueCollection, format, optArgArr);
        Assert.assertEquals(optCause, e1.getCause());
        Assert.assertEquals(null, e2.getCause());
    }

    @Test(dataProvider = "_Fail_Data")
    public static void ctor_Fail(
            Class<?> exceptionClass,
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _ctor_Fail(
            exceptionClass, exitValue, validExitValueCollection, cause, format, optArgArr);
        _ctor_Fail(
            exceptionClass, exitValue, validExitValueCollection, optCause, format, optArgArr);
    }
    
    private static void _ctor_Fail(
            Class<?> exceptionClass,
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        try {
            new InvalidExitValueException(
                exitValue, validExitValueCollection, optCause, format, optArgArr);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
        try {
            new InvalidExitValueException(exitValue, validExitValueCollection, format, optArgArr);
        }
        catch (Exception e) {
            Assert.assertEquals(e.getClass(), exceptionClass);
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // InvalidExitValueException.copyCtor()/.getExitValue()/.getValidExitValueList()
    //
    
    @Test(expectedExceptions = NullPointerException.class)
    public static void copyCtor_FailWithNull() {
        new InvalidExitValueException(null);
    }
    
    @Test(dataProvider = "_Pass_Data")
    public static void copyCtorAndAccessors_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _copyCtorAndAccessors_Pass(
            exitValue, validExitValueCollection, cause, format, optArgArr);
        _copyCtorAndAccessors_Pass(
            exitValue, validExitValueCollection, optCause, format, optArgArr);
    }
    
    private static void _copyCtorAndAccessors_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        InvalidExitValueException e = new InvalidExitValueException(
            exitValue, validExitValueCollection, optCause, format, optArgArr);
        Assert.assertEquals(exitValue, e.getExitValue());
        Assert.assertEquals(validExitValueCollection, e.getValidExitValueList());
        String message = String.format(format, optArgArr);
        Assert.assertTrue(e.getMessage().startsWith(message));
        Assert.assertEquals(optCause, e.getCause());
        
        InvalidExitValueException e2 = new InvalidExitValueException(e);
        Assert.assertEquals(e.getExitValue(), e2.getExitValue());
        Assert.assertEquals(e.getValidExitValueList(), e2.getValidExitValueList());
        Assert.assertTrue(e.getMessage().startsWith(e2.getMessage()));
        Assert.assertEquals(e.getCause(), e2.getCause());
        
        Assert.assertTrue(e.equalsExcludingStackTrace(e2));
        Assert.assertTrue(e2.equalsExcludingStackTrace(e));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // InvalidExitValueException.hashCode()/.equals()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void hashCodeAndEquals_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        InvalidExitValueException[] e1 = new InvalidExitValueException[2];
        String f = format;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e1[i] =
                new InvalidExitValueException(
                    exitValue, validExitValueCollection, optCause, f, optArgArr);
            f = new String(f);
        }
        
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        InvalidExitValueException[] e2 = new InvalidExitValueException[2];
        f = format;
        // We need this weird for loop to guarantee the stack traces are identical.
        for (int i = 0; i < 2; ++i) {
            e2[i] =
                new InvalidExitValueException(
                    exitValue, validExitValueCollection, cause, f, optArgArr);
            f = new String(f);
        }

        new EqualsTester()
            .addEqualityGroup(e1[0], e1[1])
            .addEqualityGroup(e2[0], e2[1])
            .testEquals();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // InvalidExitValueException.toString()
    //
    
    @Test(dataProvider = "_Pass_Data")
    public static void toString_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        Throwable cause = (null == optCause ? new Exception("blah") : null);
        _toString_Pass(
            exitValue, validExitValueCollection, cause, format, optArgArr);
        _toString_Pass(
            exitValue, validExitValueCollection, optCause, format, optArgArr);
    }
    
    private static void _toString_Pass(
            int exitValue,
            Collection<Integer> validExitValueCollection,
            Throwable optCause,
            String format,
            Object[] optArgArr) {
        String message = String.format(format, optArgArr);
        InvalidExitValueException e1 =
            new InvalidExitValueException(
                exitValue, validExitValueCollection, optCause, format, optArgArr);
        String s1 = e1.toString();
        Assert.assertTrue(s1.contains(message));
        
        String message2 = UUID.randomUUID().toString();
        InvalidExitValueException e2 =
            new InvalidExitValueException(
                exitValue,
                validExitValueCollection,
                new Exception(message2, optCause),
                format,
                optArgArr);
        String s2 = e2.toString();
        Assert.assertTrue(s2.contains(message));
        Assert.assertTrue(s2.contains(message2));
        Assert.assertNotEquals(s1, s2);
        
        String message3 = UUID.randomUUID().toString();
        InvalidExitValueException e3 =
            new InvalidExitValueException(
                exitValue,
                validExitValueCollection,
                new Exception(
                    message2,
                    new Exception(message3, optCause)),
                format,
                optArgArr);
        String s3 = e3.toString();
        Assert.assertTrue(s3.contains(message));
        Assert.assertTrue(s3.contains(message2));
        Assert.assertTrue(s3.contains(message3));
        Assert.assertNotEquals(s1, s2);
        Assert.assertNotEquals(s2, s3);
        
        String message4 = UUID.randomUUID().toString();
        InvalidExitValueException e4 =
            new InvalidExitValueException(
                exitValue,
                validExitValueCollection,
                new Exception(
                    message2,
                    new Exception(
                        message3, 
                        new Exception(message4, optCause))),
                format,
                optArgArr);
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
