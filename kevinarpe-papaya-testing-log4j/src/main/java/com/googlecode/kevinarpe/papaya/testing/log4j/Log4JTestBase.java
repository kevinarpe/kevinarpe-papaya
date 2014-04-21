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

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzer;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see <a href="http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/"
 *      >http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/</a>
 */
@FullyTested
public class Log4JTestBase {

    private final LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute>
        _loggingEventAnalyzerFactory;

    private final Appender _mockAppender;

    protected Log4JTestBase() {
        this(Log4JLoggingEventAnalyzerFactoryImpl.INSTANCE);
    }

    protected Log4JTestBase(
            LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute> factory) {
        _loggingEventAnalyzerFactory = ObjectArgs.checkNotNull(factory, "factory");
        _mockAppender = mock(Appender.class);
    }

    protected final void addMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(_mockAppender);
    }

    protected final void removeMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.removeAppender(_mockAppender);
    }

    /**
     * @see #newLoggingEventAnalyzer()
     */
    public final List<LoggingEvent> getLoggingEventList() {
        ArgumentCaptor<LoggingEvent> argumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        List<LoggingEvent> loggingEventList = null;
        try {
            verify(_mockAppender, atLeastOnce()).doAppend(argumentCaptor.capture());
            loggingEventList = argumentCaptor.getAllValues();
        }
        catch (Throwable ignore) {
            loggingEventList = ImmutableList.of();
        }
        return loggingEventList;
    }

    public final LoggingEventAnalyzer<LoggingEvent, Log4JLoggingEventAttribute>
    newLoggingEventAnalyzer() {
        List<LoggingEvent> loggingEventList = getLoggingEventList();
        LoggingEventAnalyzer<LoggingEvent, Log4JLoggingEventAttribute> x =
            _loggingEventAnalyzerFactory.newInstance(loggingEventList);
        return x;
    }
}
