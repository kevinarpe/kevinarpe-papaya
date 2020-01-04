package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import org.slf4j.Logger;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Strangely, SLF4J does not provide an interface method to log by level parameter, e.g., "info" or "error".  This tiny
 * interface enhances SLF4J's {@link Logger}.
 * <p>
 * ThreadSafe?  Implementations must be thread-safe.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@ThreadSafe
public interface LoggerService {

    /**
     * Important: The message is not formatted before logging.  This is sometimes very useful when you are logging odd
     * text that is also a valid format string, e.g., anything with a percent sign!
     *
     * @throws NullPointerException
     *         if {@code message} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code message} is empty or only whitespace
     */
    void log(Logger logger,
             LoggerLevel loggerLevel,
             String message);

    /**
     * This is a convenience method to call
     * {@link com.googlecode.kevinarpe.papaya.string.MessageFormatter#format(String, Object...)}, then pass the result
     * to {@link #log(Logger, LoggerLevel, String)}.
     * <p>
     * The result of {@code MessageFormatter.format(...)} must not be empty or all whitespace.
     */
    void formatThenLog(Logger logger,
                       LoggerLevel loggerLevel,
                       String format,
                       @EmptyContainerAllowed
                       @NullableElements
                       Object... argArr);

    static final String DEFAULT_THROWABLE_MESSAGE = "Unexpected error";

    /**
     * Logs an exception ("throwable"), but provides control over inclusion of stack trace.  Frequently, only the first
     * instance of a stack trace is useful.
     *
     * @throws NullPointerException
     *         if {@code message} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code message} is empty or only whitespace
     */
    void logThrowable(Logger logger,
                      LoggerLevel loggerLevel,
                      IncludeStackTrace includeStackTrace,
                      Throwable throwable,
                      String message);

    /**
     * This is a convenience method to call
     * {@link com.googlecode.kevinarpe.papaya.string.MessageFormatter#format(String, Object...)}, then pass the result
     * to {@link #logThrowable(Logger, LoggerLevel, IncludeStackTrace, Throwable, String)}.
     * <p>
     * The result of {@code MessageFormatter.format(...)} must not be empty or all whitespace.
     */
    void formatThenLogThrowable(Logger logger,
                                LoggerLevel loggerLevel,
                                IncludeStackTrace includeStackTrace,
                                Throwable throwable,
                                String format,
                                @EmptyContainerAllowed
                                @NullableElements
                                Object... argArr);

    /**
     * This is a convenience method to call
     * {@link #logThrowable(Logger, LoggerLevel, IncludeStackTrace, Throwable, String)}
     * where {@code message} is {@link #DEFAULT_THROWABLE_MESSAGE}.
     */
    default void logThrowableWithDefaultMessage(Logger logger,
                                                LoggerLevel loggerLevel,
                                                IncludeStackTrace includeStackTrace,
                                                Throwable throwable) {

        logThrowable(logger, loggerLevel, includeStackTrace, throwable, DEFAULT_THROWABLE_MESSAGE);
    }
}
