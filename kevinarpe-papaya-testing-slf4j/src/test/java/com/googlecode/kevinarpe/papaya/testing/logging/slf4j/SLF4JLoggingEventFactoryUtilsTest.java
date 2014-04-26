package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEventFactory;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEventFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventFactoryUtilsTest {

    private SLF4JLoggingEventFactory mockSLF4JLoggingEventFactory;
    private SLF4JLoggingEvent mockSLF4JLoggingEvent;
    private Logger mockLogger;
    private Marker mockMarker;
    private Throwable mockThrowable;

    private SLF4JLoggingEventFactoryUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockSLF4JLoggingEventFactory = mock(SLF4JLoggingEventFactory.class);
        mockSLF4JLoggingEvent = mock(SLF4JLoggingEvent.class);
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        mockThrowable = mock(Throwable.class);
        classUnderTest = new SLF4JLoggingEventFactoryUtils();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance1(String)
    //

    @Test
    public void newInstance1_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                SLF4JLoggingEventFactoryUtils.EMPTY_FORMAT_ARG_ARR,
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory, mockLogger, SLF4JLogLevel.INFO, mockMarker, "message");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance2(String, Object)
    //

    @Test
    public void newInstance2_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance3(String, Object, Object)
    //

    @Test
    public void newInstance3_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg", "formatArg2" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg",
                "formatArg2");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance4(String, Object...)
    //

    @Test
    public void newInstance4_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg", "formatArg2", "formatArg3" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg",
                "formatArg2",
                "formatArg3");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance5(String, Throwable)
    //

    @Test
    public void newInstance5_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                SLF4JLoggingEventFactoryUtils.EMPTY_FORMAT_ARG_ARR,
                mockThrowable))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                mockThrowable);
        assertSame(event, mockSLF4JLoggingEvent);
    }
}
