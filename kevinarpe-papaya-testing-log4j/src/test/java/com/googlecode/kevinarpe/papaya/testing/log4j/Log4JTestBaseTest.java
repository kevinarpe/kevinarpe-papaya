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

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Log4JTestBaseTest
extends Log4JTestBase {

    private static class TestClass {

        final Logger _logger = Logger.getLogger(TestClass.class);

        public TestClass(Level level, String message) {
            super();
            _logger.log(level, message);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JTestBase.getLoggingEventList()
    //

    @Test
    public void pass() {
        super.addMockAppender();
        new TestClass(Level.DEBUG, "dummy");
        new TestClass(Level.INFO, "dummy2");
        super.removeMockAppender();
        List<LoggingEvent> loggingEventList = super.getLoggingEventList();
        assertEquals(loggingEventList.size(), 2);
        LoggingEvent loggingEvent0 = loggingEventList.get(0);
        assertEquals(loggingEvent0.getLevel(), Level.DEBUG);
        assertEquals(loggingEvent0.getMessage(), "dummy");
        LoggingEvent loggingEvent1 = loggingEventList.get(1);
        assertEquals(loggingEvent1.getLevel(), Level.INFO);
        assertEquals(loggingEvent1.getMessage(), "dummy2");
    }

    @Test
    public void failWithZeroAppenders() {
        new TestClass(Level.DEBUG, "dummy");
        List<LoggingEvent> loggingEventList = super.getLoggingEventList();
        assertEquals(loggingEventList.size(), 0);
    }

    @Test
    public void failWithZeroAppenders2() {
        super.addMockAppender();
        super.removeMockAppender();
        new TestClass(Level.DEBUG, "dummy");
        List<LoggingEvent> loggingEventList = super.getLoggingEventList();
        assertEquals(loggingEventList.size(), 0);
    }

    @Test
    public void failWithAnotherAppender() {
        Appender appender = mock(Appender.class);
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(appender);
        new TestClass(Level.DEBUG, "dummy");
        List<LoggingEvent> loggingEventList = super.getLoggingEventList();
        assertEquals(loggingEventList.size(), 0);
    }
}
