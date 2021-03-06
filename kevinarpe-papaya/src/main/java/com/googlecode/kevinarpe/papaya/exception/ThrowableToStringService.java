package com.googlecode.kevinarpe.papaya.exception;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import javax.annotation.concurrent.ThreadSafe;

/**
 * ThreadSafe?  Implementations must be thread-safe.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@ThreadSafe
public interface ThrowableToStringService {

    /**
     * Calls {@link ThrowableUtils#toStringWithStackTrace(Throwable)}.
     * <br>If result is unique, then return exception class, message, and full stack trace.
     * <br>If result is not unique, then return {@link Throwable#toString()} -- exception class and message only.
     * <p>
     * This method is useful to reduce log noise when an exception is throw, caught, and logged multiple times.
     * Usually, the full stack trace is only useful to see once in the log.
     * <p>
     * Example of first exception instance:
     * <pre>{@code
     * [ID:31] java.lang.Exception: blah
     * 	at com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceImplTest.toStringWithUniqueStackTrace_Pass(ThrowableToStringServiceImplTest.java:43)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.lang.reflect.Method.invoke(Method.java:498)
     * 	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:134)
     * 	...
     * }</pre>
     * <p>
     * Example of second (and later) exception instance:
     * <pre>{@code
     * [ID:31] java.lang.Exception: blah at com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceImplTest.toStringWithUniqueStackTrace_Pass(ThrowableToStringServiceImplTest.java:43)
     * }</pre>
     *
     * @throws NullPointerException
     *         if {@code throwable} is {@code null}
     */
    String toStringWithUniqueStackTrace(Throwable throwable);
}
