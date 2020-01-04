package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

/*
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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
class SLF4JLoggingEventFactoryUtils
extends StatelessObject
implements ISLF4JLoggingEventFactoryUtils {

    public static final SLF4JLoggingEventFactoryUtils INSTANCE =
        new SLF4JLoggingEventFactoryUtils();

    public SLF4JLoggingEventFactoryUtils() {
        // Empty.
    }

    static final Object[] EMPTY_FORMAT_ARG_ARR = new Object[0];

    @Override
    public SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message) {
        SLF4JLoggingEvent x =
            factory.newInstance(
                logger, logLevel, marker, message, EMPTY_FORMAT_ARG_ARR, (Throwable) null);
        return x;
    }

    @Override
    public SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object formatArg) {
        SLF4JLoggingEvent x =
            factory.newInstance(
                logger, logLevel, marker, message, new Object[] { formatArg }, (Throwable) null);
        return x;
    }

    @Override
    public SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object formatArg,
            Object formatArg2) {
        SLF4JLoggingEvent x =
            factory.newInstance(
                logger,
                logLevel,
                marker,
                message,
                new Object[] { formatArg, formatArg2 },
                (Throwable) null);
        return x;
    }

    @Override
    public SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object... optionalFormatArgArr) {
        SLF4JLoggingEvent x =
            factory.newInstance(
                logger, logLevel, marker, message, optionalFormatArgArr, (Throwable) null);
        return x;
    }

    @Override
    public SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Throwable throwable) {
        SLF4JLoggingEvent x =
            factory.newInstance(logger, logLevel, marker, message, EMPTY_FORMAT_ARG_ARR, throwable);
        return x;
    }
}
