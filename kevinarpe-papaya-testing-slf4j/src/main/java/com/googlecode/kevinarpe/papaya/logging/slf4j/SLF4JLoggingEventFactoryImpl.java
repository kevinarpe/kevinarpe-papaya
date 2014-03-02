package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Check final carefully everywhere.
public final class SLF4JLoggingEventFactoryImpl
implements SLF4JLoggingEventFactory {

    public static final SLF4JLoggingEventFactoryImpl INSTANCE = new SLF4JLoggingEventFactoryImpl();

    private SLF4JLoggingEventFactoryImpl() {
        // Empty.
    }

    @Override
    public SLF4JLoggingEventImpl newInstance(
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable) {
        SLF4JLoggingEventImpl x =
            new SLF4JLoggingEventImpl(
                logger, logLevel, marker, message, optionalFormatArgArr, optionalThrowable);
        return x;
    }
}
