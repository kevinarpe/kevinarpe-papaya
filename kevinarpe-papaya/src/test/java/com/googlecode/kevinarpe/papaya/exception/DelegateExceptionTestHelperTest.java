package com.googlecode.kevinarpe.papaya.exception;

/*
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class DelegateExceptionTestHelperTest {

    private static class GoodDelegateException
    extends Exception {

        public GoodDelegateException() {
            super();
        }

        public GoodDelegateException(String message) {
            super(message);
        }

        public GoodDelegateException(String message, Throwable cause) {
            super(message, cause);
        }

        public GoodDelegateException(Throwable cause) {
            super(cause);
        }
    }

    private static class BadDelegateException
    extends Exception {

        public BadDelegateException() {
            super(UUID.randomUUID().toString());
        }

        public BadDelegateException(String message) {
            super(UUID.randomUUID().toString());
        }

        public BadDelegateException(String message, Throwable cause) {
            super(UUID.randomUUID().toString(), new Exception("other"));
        }

        public BadDelegateException(Throwable cause) {
            super(new Exception("other"));
        }

        @Override
        public String toString() {
            return UUID.randomUUID().toString();
        }
    }

    private DelegateExceptionTestHelper.ClassProxy mockClassProxy;
    private DelegateExceptionTestHelper.ConstructorProxy mockConstructorProxy;
    private DelegateExceptionTestHelper goodClassUnderTestWithoutMocks;
    private DelegateExceptionTestHelper goodClassUnderTestWithMocks;
    private DelegateExceptionTestHelper badClassUnderTestWithoutMocks;

    @BeforeMethod
    public void beforeEachTest() {
        mockClassProxy = mock(DelegateExceptionTestHelper.ClassProxy.class);
        mockConstructorProxy = mock(DelegateExceptionTestHelper.ConstructorProxy.class);
        goodClassUnderTestWithoutMocks =
            DelegateExceptionTestHelper.newInstance(GoodDelegateException.class);
        goodClassUnderTestWithMocks =
            DelegateExceptionTestHelper.newInstance(
                GoodDelegateException.class, mockClassProxy, mockConstructorProxy);
        badClassUnderTestWithoutMocks =
            DelegateExceptionTestHelper.newInstance(BadDelegateException.class);
    }

    @Test
    public void testCtorVoid_Pass() {
        goodClassUnderTestWithoutMocks.testCtorVoid();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testCtorVoid_Fail() {
        badClassUnderTestWithoutMocks.testCtorVoid();
    }

    @Test
    public void testCtorString_Pass() {
        goodClassUnderTestWithoutMocks.testCtorString();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testCtorString_Fail() {
        badClassUnderTestWithoutMocks.testCtorString();
    }

    @Test
    public void testCtorStringThrowable_Pass() {
        goodClassUnderTestWithoutMocks.testCtorStringThrowable();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testCtorStringThrowable_Fail() {
        badClassUnderTestWithoutMocks.testCtorStringThrowable();
    }

    @Test
    public void testCtorThrowable_Pass() {
        goodClassUnderTestWithoutMocks.testCtorThrowable();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testCtorThrowable_Fail() {
        badClassUnderTestWithoutMocks.testCtorThrowable();
    }
}
