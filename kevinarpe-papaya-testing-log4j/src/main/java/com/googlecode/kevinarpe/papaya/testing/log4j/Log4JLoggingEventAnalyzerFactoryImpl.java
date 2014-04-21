package com.googlecode.kevinarpe.papaya.testing.log4j;

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

import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerFactory;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerImpl;
import org.apache.log4j.spi.LoggingEvent;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class Log4JLoggingEventAnalyzerFactoryImpl
extends StatelessObject
implements LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute> {

    public static final Log4JLoggingEventAnalyzerFactoryImpl INSTANCE =
        new Log4JLoggingEventAnalyzerFactoryImpl();

    @Override
    public LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute>
    newInstance(List<LoggingEvent> loggingEventList) {
        LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute> x =
            new LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute>(loggingEventList);
        return x;
    }
}
