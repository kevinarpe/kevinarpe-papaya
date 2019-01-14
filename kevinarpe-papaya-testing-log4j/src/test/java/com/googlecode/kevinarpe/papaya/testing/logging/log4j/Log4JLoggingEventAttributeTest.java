package com.googlecode.kevinarpe.papaya.testing.logging.log4j;

/*
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

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.testing.logging.log4j.Log4JLoggingEventAttribute;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@PrepareForTest(LoggingEvent.class)
public class Log4JLoggingEventAttributeTest
extends PowerMockTestCase {

    private LoggingEvent mockLoggingEvent;
    private Logger mockLogger;
    private ThrowableInformation mockThrowableInformation;
    private Throwable throwable;

    @BeforeMethod
    private void beforeEachTestMethod() {
        // LoggingEvent.getTimeStamp() is final, so PowerMock is required!
        mockLoggingEvent = PowerMockito.mock(LoggingEvent.class);
        mockLogger = mock(Logger.class);
        mockThrowableInformation = mock(ThrowableInformation.class);
        throwable = new Exception("message");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.*.getValueClass()
    //

    @Test
    public void getValueClass_Pass() {
        ImmutableMap<Log4JLoggingEventAttribute, Class<?>> map =
            ImmutableMap.<Log4JLoggingEventAttribute, Class<?>>builder()
                .put(Log4JLoggingEventAttribute.LOGGER, Logger.class)
                .put(Log4JLoggingEventAttribute.LEVEL, Level.class)
                .put(Log4JLoggingEventAttribute.MESSAGE, String.class)
                .put(Log4JLoggingEventAttribute.THROWABLE, Throwable.class)
                .put(Log4JLoggingEventAttribute.THREAD_NAME, String.class)
                .put(Log4JLoggingEventAttribute.TIME_STAMP, Long.class)
                .build();
        for (Log4JLoggingEventAttribute attr : Log4JLoggingEventAttribute.values()) {
            assertSame(attr.getValueClass(), map.get(attr));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.LOGGER.getValue()
    //

    @Test
    public void LOGGER_getValue_Pass() {
        when(mockLoggingEvent.getLogger()).thenReturn(mockLogger);

        assertSame(
            Log4JLoggingEventAttribute.LOGGER.getValue(mockLoggingEvent),
            mockLogger);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void LOGGER_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.LOGGER.getValue((LoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.LEVEL.getValue()
    //

    @Test
    public void LEVEL_getValue_Pass() {
        when(mockLoggingEvent.getLevel()).thenReturn(Level.DEBUG);

        assertSame(
            Log4JLoggingEventAttribute.LEVEL.getValue(mockLoggingEvent),
            Level.DEBUG);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void LEVEL_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.LEVEL.getValue((LoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.MESSAGE.getValue()
    //

    @Test
    public void MESSAGE_getValue_Pass() {
        when(mockLoggingEvent.getMessage()).thenReturn("message");

        assertSame(
            Log4JLoggingEventAttribute.MESSAGE.getValue(mockLoggingEvent),
            "message");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void MESSAGE_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.MESSAGE.getValue((LoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.THROWABLE.getValue()
    //

    @Test
    public void THROWABLE_getValue_Pass() {
        when(mockLoggingEvent.getThrowableInformation()).thenReturn(mockThrowableInformation);
        when(mockThrowableInformation.getThrowable()).thenReturn(throwable);

        assertSame(
            Log4JLoggingEventAttribute.THROWABLE.getValue(mockLoggingEvent),
            throwable);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void THROWABLE_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.THROWABLE.getValue((LoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.THREAD_NAME.getValue()
    //

    @Test
    public void THREAD_NAME_getValue_Pass() {
        when(mockLoggingEvent.getThreadName()).thenReturn("name");

        assertSame(
            Log4JLoggingEventAttribute.THREAD_NAME.getValue(mockLoggingEvent),
            "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void THREAD_NAME_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.THREAD_NAME.getValue((LoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Log4JLoggingEventAttribute.TIME_STAMP.getValue()
    //

    @Test
    public void TIME_STAMP_getValue_Pass() {
        when(mockLoggingEvent.getTimeStamp()).thenReturn(1234L);

        assertEquals(
            Log4JLoggingEventAttribute.TIME_STAMP.getValue(mockLoggingEvent),
            (Long) 1234L);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void TIME_STAMP_getValue_FailWithNull() {
        Log4JLoggingEventAttribute.TIME_STAMP.getValue((LoggingEvent) null);
    }
}
