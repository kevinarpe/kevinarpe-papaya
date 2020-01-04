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

import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
interface ISLF4JLoggingEventFactoryUtils {

    SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message);

    SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object formatArg);

    SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object formatArg,
            Object formatArg2);

    SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object... optionalFormatArgArr);

    SLF4JLoggingEvent newInstance(
            SLF4JLoggingEventFactory factory,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Throwable throwable);
}
