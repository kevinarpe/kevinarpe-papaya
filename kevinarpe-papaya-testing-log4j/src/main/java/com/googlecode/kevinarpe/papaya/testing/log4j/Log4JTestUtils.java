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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see <a href="http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/"
 *      >http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/</a>
 */
@NotFullyTested
public final class Log4JTestUtils {

    private static final Appender mockAppender = mock(Appender.class);

    public static void addMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(mockAppender);
    }

    public static void removeMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.removeAppender(mockAppender);
        ArgumentCaptor<LoggingEvent> x = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender).doAppend(x.capture());
        List<LoggingEvent> loggingEventList = x.getAllValues();
    }

    public static List<LoggingEvent> getLoggingEventList() {
        ArgumentCaptor<LoggingEvent> argumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        verify(mockAppender).doAppend(argumentCaptor.capture());
        List<LoggingEvent> loggingEventList = argumentCaptor.getAllValues();
        return loggingEventList;
    }

    public static LoggingEventAnalysis getLoggingEventAnalysis() {
        List<LoggingEvent> loggingEventList = getLoggingEventList();
        LoggingEventAnalysis x = new LoggingEventAnalysisImpl(loggingEventList);
        return x;
    }
}
