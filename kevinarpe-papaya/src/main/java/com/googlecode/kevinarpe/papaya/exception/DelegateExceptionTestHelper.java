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

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class DelegateExceptionTestHelper<TException extends Exception> {

    public static <TException2 extends Exception>
    DelegateExceptionTestHelper newInstance(Class<TException2> exceptionClass) {
        DelegateExceptionTestHelper<TException2> x =
            new DelegateExceptionTestHelper<TException2>(
                exceptionClass, ClassProxyImpl.INSTANCE, ConstructorProxyImpl.INSTANCE);
        return x;
    }

    static <TException2 extends Exception>
    DelegateExceptionTestHelper newInstance(
            Class<TException2> exceptionClass,
            ClassProxy classProxy,
            ConstructorProxy constructorProxy) {
        DelegateExceptionTestHelper<TException2> x =
            new DelegateExceptionTestHelper<TException2>(
                exceptionClass, classProxy, constructorProxy);
        return x;
    }

    interface ClassProxy {

        <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypeArr)
        throws NoSuchMethodException;
    }

    interface ConstructorProxy {

        <T> T newInstance(Constructor<T> ctor, Object... argArr)
        throws IllegalAccessException, InvocationTargetException, InstantiationException;
    }

    static class ClassProxyImpl
    implements ClassProxy {

        public static final ClassProxyImpl INSTANCE = new ClassProxyImpl();

        @Override
        public <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... paramTypeArr)
        throws NoSuchMethodException {
            Constructor<T> x = clazz.getConstructor(paramTypeArr);
            return x;
        }
    }

    static class ConstructorProxyImpl
    implements ConstructorProxy {

        public static final ConstructorProxyImpl INSTANCE = new ConstructorProxyImpl();

        @Override
        public <T> T newInstance(Constructor<T> ctor, Object... argArr)
        throws IllegalAccessException, InvocationTargetException, InstantiationException {
            T x = ctor.newInstance(argArr);
            return x;
        }
    }

    private final Class<TException> _exceptionClass;
    private final ClassProxy _classProxy;
    private final ConstructorProxy _constructorProxy;

    private DelegateExceptionTestHelper(
            Class<TException> exceptionClass,
            ClassProxy classProxy,
            ConstructorProxy constructorProxy) {
        _exceptionClass = ObjectArgs.checkNotNull(exceptionClass, "exceptionClass");
        _classProxy = ObjectArgs.checkNotNull(classProxy, "classProxy");
        _constructorProxy = ObjectArgs.checkNotNull(constructorProxy, "constructorProxy");
    }

    public void runAllTests() {
        testCtorVoid();
        testCtorString();
        testCtorStringThrowable();
        testCtorThrowable();
    }

    public void testCtorVoid() {
        Constructor<TException> ctor = _getCtor();
        TException classUnderTest = _newInstance(ctor);
        _assertMessage(classUnderTest, "", null);
        _assertCause(classUnderTest, "", null);
        _assertToString(classUnderTest, "");
    }

    public void testCtorString() {
        Constructor<TException> ctor = _getCtor(String.class);
        final String message = "dummy";
        TException classUnderTest = _newInstance(ctor, message);
        _assertMessage(classUnderTest, "String", message);
        _assertCause(classUnderTest, "String", null);
    }

    public void testCtorStringThrowable() {
        Constructor<TException> ctor = _getCtor(String.class, Throwable.class);
        final String message = "dummy";
        final Throwable cause = new Exception("exception");
        TException classUnderTest = _newInstance(ctor, message, cause);
        _assertMessage(classUnderTest, "String, Throwable", message);
        _assertCause(classUnderTest, "String, Throwable", cause);
    }

    public void testCtorThrowable() {
        Constructor<TException> ctor = _getCtor(Throwable.class);
        final Throwable cause = new Exception("exception");
        TException classUnderTest = _newInstance(ctor, cause);
        _assertMessage(classUnderTest, "Throwable", cause.toString());
        _assertCause(classUnderTest, "Throwable", cause);
    }

    private Constructor<TException> _getCtor(Class<?>... paramTypeArr) {
        ArrayArgs.checkElementsNotNull(paramTypeArr, "paramTypeArr");

        try {
            Constructor<TException> x = _classProxy.getConstructor(_exceptionClass, paramTypeArr);
            return x;
        }
        catch (NoSuchMethodException e) {
            String msg = String.format(
                "Failed to find constructor: %s(%s)%nError: %s",
                _exceptionClass.getName(),
                Joiner.on(", ").join(paramTypeArr),
                e.getMessage());
            throw new AssertionError(msg);
        }
    }

    private TException _newInstance(Constructor<TException> ctor, Object... argArr) {
        ObjectArgs.checkNotNull(ctor, "ctor");
        ArrayArgs.checkElementsNotNull(argArr, "argArr");

        try {
            TException x = _constructorProxy.newInstance(ctor, argArr);
            return x;
        }
        catch (Exception e) {
            String msg = String.format(
                "Failed to construct new instance: %s(%s)%nError: %s",
                _exceptionClass.getName(),
                Joiner.on(", ").join(argArr),
                e.getMessage());
            throw new AssertionError(msg);
        }
    }

    private void _assertMessage(
            TException classUnderTest,
            String ctorParamsDescription,
            String optionalExpectedMessage) {
        if (!Objects.equal(optionalExpectedMessage, classUnderTest.getMessage())) {
            String msg = String.format(
                "Constructor %s(%s) does not create new instance with expected message:"
                    + "%nExpected: '%s'"
                    + "%nActual  : '%s'",
                _exceptionClass.getName(),
                ctorParamsDescription,
                optionalExpectedMessage,
                classUnderTest.getMessage());
            throw new AssertionError(msg);
        }
    }

    private void _assertCause(
            TException classUnderTest,
            String ctorParamsDescription,
            Throwable optionalExpectedCause) {
        if (optionalExpectedCause != classUnderTest.getCause()) {
            String msg = String.format(
                "Constructor %s(%s) does not create new instance with expected cause",
                _exceptionClass.getName(), ctorParamsDescription);
            throw new AssertionError(msg);
        }
    }

    private void _assertToString(TException classUnderTest, String ctorParamsDescription) {
        String s = classUnderTest.getClass().getName();
        String message = classUnderTest.getLocalizedMessage();
        String expected = (message != null) ? (s + ": " + message) : s;
        String actual = classUnderTest.toString();
        if (!Objects.equal(expected, actual)) {
            String msg = String.format("Unexpected result from new %s(%s).toString()"
                + "%nExpected: '%s'"
                + "%nActual  : '%s'",
                _exceptionClass.getName(),
                ctorParamsDescription,
                expected,
                actual);
            throw new AssertionError(msg);
        }
    }
}
