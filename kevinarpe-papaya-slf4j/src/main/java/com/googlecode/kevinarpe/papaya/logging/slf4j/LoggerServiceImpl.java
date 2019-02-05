package com.googlecode.kevinarpe.papaya.logging.slf4j;

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.EmptyStringAllowed;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NullableElements;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringService;
import com.googlecode.kevinarpe.papaya.exception.ThrowableToStringServiceFactory;
import com.googlecode.kevinarpe.papaya.exception.ThrowableUtils;
import com.googlecode.kevinarpe.papaya.string.MessageFormatter;
import org.slf4j.Logger;

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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class LoggerServiceImpl
implements LoggerService {

    // Intentional: Package-private for testing
    static final String SLF4J_FORMAT = "{}: {}";
    private final ThrowableToStringService throwableToStringService;
    private final MessageFormatter messageFormatter;

    public LoggerServiceImpl(ThrowableToStringServiceFactory throwableToStringServiceFactory,
                             MessageFormatter messageFormatter) {

        ObjectArgs.checkNotNull(throwableToStringServiceFactory, "throwableToStringServiceFactory");
        this.throwableToStringService = throwableToStringServiceFactory.newInstance();
        this.messageFormatter = ObjectArgs.checkNotNull(messageFormatter, "messageFormatter");
    }

    /** {@inheritDoc} */
    @Override
    public void log(Logger logger,
                    LoggerLevel loggerLevel,
                    String message) {

        StringArgs.checkNotEmptyOrWhitespace(message, "message");

        switch (loggerLevel) {
            case INFO: {
                logger.info(message);
                break;
            }
            case ERROR: {
                logger.error(message);
                break;
            }
            case DEBUG: {
                logger.debug(message);
                break;
            }
            case TRACE: {
                logger.trace(message);
                break;
            }
            default: {
                throw new IllegalStateException(
                    "Unreachable code: Unknown " + loggerLevel.getClass().getSimpleName() + ": " + loggerLevel.name());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void formatThenLog(Logger logger,
                              LoggerLevel loggerLevel,
                              @EmptyStringAllowed
                              String format,
                              @EmptyContainerAllowed
                              @NullableElements
                              Object... argArr) {

        final String msg = messageFormatter.format(format, argArr);
        log(logger, loggerLevel, msg);
    }

    /** {@inheritDoc} */
    @Override
    public void logThrowable(Logger logger,
                             LoggerLevel loggerLevel,
                             IncludeStackTrace includeStackTrace,
                             String message,
                             Throwable throwable) {

        StringArgs.checkNotEmptyOrWhitespace(message, "message");

        final String s = _throwableToString(throwable, includeStackTrace);

        switch (loggerLevel) {
            case INFO: {
                logger.info(SLF4J_FORMAT, message, s);
                break;
            }
            case ERROR: {
                logger.error(SLF4J_FORMAT, message, s);
                break;
            }
            case DEBUG: {
                logger.debug(SLF4J_FORMAT, message, s);
                break;
            }
            case TRACE: {
                logger.trace(SLF4J_FORMAT, message, s);
                break;
            }
            default: {
                throw new IllegalStateException(
                    "Unreachable code: Unknown " + loggerLevel.getClass().getSimpleName() + ": " + loggerLevel.name());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void formatThenLogThrowable(Logger logger,
                                       LoggerLevel loggerLevel,
                                       IncludeStackTrace includeStackTrace,
                                       Throwable throwable,
                                       @EmptyStringAllowed
                                       String format,
                                       @EmptyContainerAllowed
                                       @NullableElements
                                       Object... argArr) {

        final String msg = messageFormatter.format(format, argArr);
        logThrowable(logger, loggerLevel, includeStackTrace, msg, throwable);
    }

    private String
    _throwableToString(Throwable throwable,
                       IncludeStackTrace includeStackTrace) {

        switch (includeStackTrace) {
            case YES: {
                final String x = ThrowableUtils.toStringWithStackTrace(throwable);
                return x;
            }
            case UNIQUE_ONLY: {
                final String x = throwableToStringService.toStringWithUniqueStackTrace(throwable);
                return x;
            }
            default:
                throw new IllegalStateException(
                    "Unreachable code: Unknown " + includeStackTrace.getClass().getSimpleName() + ": "
                        + includeStackTrace.name());
        }
    }
}
