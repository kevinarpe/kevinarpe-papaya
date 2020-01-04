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

import com.google.common.collect.ImmutableMap;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.testing.logging.slf4j.SLF4JLoggingEventAttribute;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventAttributeTest {

    private SLF4JLoggingEvent mockSLF4JLoggingEvent;
    private Logger mockLogger;
    private Marker mockMarker;
    private Throwable throwable;

    @BeforeMethod
    private void beforeEachTestMethod() {
        mockSLF4JLoggingEvent = mock(SLF4JLoggingEvent.class);
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        throwable = new Exception("message");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.*.getValueClass()
    //

    @Test
    public void getValueClass_Pass() {
        ImmutableMap<SLF4JLoggingEventAttribute, Class<?>> map =
            ImmutableMap.<SLF4JLoggingEventAttribute, Class<?>>builder()
                .put(SLF4JLoggingEventAttribute.LOGGER, Logger.class)
                .put(SLF4JLoggingEventAttribute.LEVEL, SLF4JLogLevel.class)
                .put(SLF4JLoggingEventAttribute.MARKER, Marker.class)
                .put(SLF4JLoggingEventAttribute.MESSAGE, String.class)
                .put(SLF4JLoggingEventAttribute.MESSAGE_FORMAT_ARG_ARR, Object[].class)
                .put(SLF4JLoggingEventAttribute.THROWABLE, Throwable.class)
                .put(SLF4JLoggingEventAttribute.THREAD_NAME, String.class)
                .put(SLF4JLoggingEventAttribute.TIME_STAMP, Long.class)
                .put(SLF4JLoggingEventAttribute.FORMATTED_MESSAGE, String.class)
                .build();
        for (SLF4JLoggingEventAttribute attr : SLF4JLoggingEventAttribute.values()) {
            assertSame(attr.getValueClass(), map.get(attr));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.LOGGER.getValue()
    //

    @Test
    public void LOGGER_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getLogger()).thenReturn(mockLogger);

        assertSame(
            SLF4JLoggingEventAttribute.LOGGER.getValue(mockSLF4JLoggingEvent),
            mockLogger);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void LOGGER_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.LOGGER.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.LEVEL.getValue()
    //

    @Test
    public void LEVEL_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getLevel()).thenReturn(SLF4JLogLevel.DEBUG);

        assertSame(
            SLF4JLoggingEventAttribute.LEVEL.getValue(mockSLF4JLoggingEvent),
            SLF4JLogLevel.DEBUG);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void LEVEL_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.LEVEL.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.MARKER.getValue()
    //

    @Test
    public void MARKER_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getMarker()).thenReturn(mockMarker);

        assertSame(
            SLF4JLoggingEventAttribute.MARKER.getValue(mockSLF4JLoggingEvent),
            mockMarker);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void MARKER_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.MARKER.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.MESSAGE.getValue()
    //

    @Test
    public void MESSAGE_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getMessage()).thenReturn("message");

        assertSame(
            SLF4JLoggingEventAttribute.MESSAGE.getValue(mockSLF4JLoggingEvent),
            "message");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void MESSAGE_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.MESSAGE.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.MESSAGE_FORMAT_ARG_ARR.getValue()
    //

    @Test
    public void MESSAGE_FORMAT_ARG_ARR_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getMessageFormatArgArr()).thenReturn(new Object[] { "arg" });

        assertEquals(
            SLF4JLoggingEventAttribute.MESSAGE_FORMAT_ARG_ARR.getValue(mockSLF4JLoggingEvent),
            new Object[] { "arg" });
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void MESSAGE_FORMAT_ARG_ARR_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.MESSAGE_FORMAT_ARG_ARR.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.THROWABLE.getValue()
    //

    @Test
    public void THROWABLE_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getThrowable()).thenReturn(throwable);

        assertSame(
            SLF4JLoggingEventAttribute.THROWABLE.getValue(mockSLF4JLoggingEvent),
            throwable);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void THROWABLE_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.THROWABLE.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.THREAD_NAME.getValue()
    //

    @Test
    public void THREAD_NAME_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getThreadName()).thenReturn("name");

        assertSame(
            SLF4JLoggingEventAttribute.THREAD_NAME.getValue(mockSLF4JLoggingEvent),
            "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void THREAD_NAME_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.THREAD_NAME.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.TIME_STAMP.getValue()
    //

    @Test
    public void TIME_STAMP_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getTimeStamp()).thenReturn(1234L);

        assertEquals(
            SLF4JLoggingEventAttribute.TIME_STAMP.getValue(mockSLF4JLoggingEvent),
            1234L);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void TIME_STAMP_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.TIME_STAMP.getValue((SLF4JLoggingEvent) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventAttribute.FORMATTED_MESSAGE.getValue()
    //

    @Test
    public void FORMATTED_MESSAGE_getValue_Pass() {
        when(mockSLF4JLoggingEvent.getFormattedMessage()).thenReturn("formattedMessage");

        assertEquals(
            SLF4JLoggingEventAttribute.FORMATTED_MESSAGE.getValue(mockSLF4JLoggingEvent),
            "formattedMessage");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void FORMATTED_MESSAGE_getValue_FailWithNull() {
        SLF4JLoggingEventAttribute.FORMATTED_MESSAGE.getValue((SLF4JLoggingEvent) null);
    }
}
