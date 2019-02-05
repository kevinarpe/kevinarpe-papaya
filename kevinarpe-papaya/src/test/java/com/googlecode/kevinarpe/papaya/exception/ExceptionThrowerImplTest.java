package com.googlecode.kevinarpe.papaya.exception;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.googlecode.kevinarpe.papaya.string.MessageFormatter;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExceptionThrowerImplTest {

    private MessageFormatter mockMessageFormatter;
    private ExceptionThrowerImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {

        mockMessageFormatter = Mockito.mock(MessageFormatter.class);
        classUnderTest = new ExceptionThrowerImpl(mockMessageFormatter);
    }

    private static final class _Exception
    extends Exception {

        public _Exception() {
            super();
        }
    }

    private static final class _RuntimeException
    extends RuntimeException {

        public _RuntimeException() {
            super();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ExceptionThrowerImpl.ctor()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWhenArgIsNull() {

        new ExceptionThrowerImpl((MessageFormatter) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ExceptionThrowerImpl.throwCheckedException()
    //

    @Test
    public void throwCheckedException_Pass() {

        Mockito.when(mockMessageFormatter.format("abc", 123, "def")).thenReturn("xyz");
        try {
            classUnderTest.throwCheckedException(Exception.class, "abc", 123, "def");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "xyz");
            return;
        }
        throw new IllegalStateException("Unreachable code");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwCheckedException_FailWhenNoMatchingCtor()
    throws _Exception {

        classUnderTest.throwCheckedException(_Exception.class, "abc", 123, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ExceptionThrowerImpl.throwChainedCheckedException()
    //

    @Test
    public void throwChainedCheckedException_Pass() {

        Mockito.when(mockMessageFormatter.format("abc", 123, "def")).thenReturn("xyz");
        final Exception inner = new Exception();
        try {
            classUnderTest.throwChainedCheckedException(Exception.class, inner, "abc", 123, "def");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "xyz");
            Assert.assertSame(e.getCause(), inner);
            return;
        }
        throw new IllegalStateException("Unreachable code");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwChainedCheckedException_FailWhenNoMatchingCtor()
    throws _Exception {

        final Exception inner = new Exception();
        classUnderTest.throwChainedCheckedException(_Exception.class, inner, "abc", 123, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ExceptionThrowerImpl.throwRuntimeException()
    //

    @Test
    public void throwRuntimeException_Pass() {

        Mockito.when(mockMessageFormatter.format("abc", 123, "def")).thenReturn("xyz");
        try {
            classUnderTest.throwRuntimeException(RuntimeException.class, "abc", 123, "def");
        }
        catch (RuntimeException e) {
            Assert.assertEquals(e.getMessage(), "xyz");
            return;
        }
        throw new IllegalStateException("Unreachable code");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwRuntimeException_FailWhenNoMatchingCtor()
    throws _Exception {

        classUnderTest.throwRuntimeException(_RuntimeException.class, "abc", 123, "def");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ExceptionThrowerImpl.throwChainedRuntimeException()
    //

    @Test
    public void throwChainedRuntimeException_Pass() {

        Mockito.when(mockMessageFormatter.format("abc", 123, "def")).thenReturn("xyz");
        final Exception inner = new Exception();
        try {
            classUnderTest.throwChainedRuntimeException(RuntimeException.class, inner, "abc", 123, "def");
        }
        catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "xyz");
            Assert.assertSame(e.getCause(), inner);
            return;
        }
        throw new IllegalStateException("Unreachable code");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void throwChainedRuntimeException_FailWhenNoMatchingCtor()
    throws _Exception {

        final Exception inner = new Exception();
        classUnderTest.throwChainedRuntimeException(_RuntimeException.class, inner, "abc", 123, "def");
    }
}
