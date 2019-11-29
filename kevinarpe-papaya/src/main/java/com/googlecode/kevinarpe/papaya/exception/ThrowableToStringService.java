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
     *
     * @throws NullPointerException
     *         if {@code throwable} is {@code null}
     */
    String toStringWithUniqueStackTrace(Throwable throwable);
}
