package com.googlecode.kevinarpe.papaya.logging.slf4j.mock;

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

import com.googlecode.kevinarpe.papaya.logging.slf4j.ISLF4JLoggingEventFactoryUtils;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactory;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JMockLoggerImplTest {

    private static final String msg = "msg";
    private static final String format = "format";
    private static final Object arg = new Object();
    private static final Object arg2 = new Object();
    private static final Object arg3 = new Object();
    private static final Object[] argArr = new Object[] { arg, arg2, arg3 };
    private static final Throwable throwable = new Throwable();

    private Marker mockMarker;
    private SLF4JMockLoggerConfig mockConfig;
    private SLF4JMockLoggerConfig mockConfigCopy;
    private SLF4JLoggingEventFactory mockFactory;
    private ISLF4JLoggingEventFactoryUtils mockFactoryUtils;
    private SLF4JLoggingEvent mockLoggingEvent;

    private SLF4JMockLoggerImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMarker = mock(Marker.class);
        mockConfig = mock(SLF4JMockLoggerConfig.class);
        mockConfigCopy = mock(SLF4JMockLoggerConfig.class);
        when(mockConfig.copy()).thenReturn(mockConfigCopy);
        mockFactory = mock(SLF4JLoggingEventFactory.class);
        mockFactoryUtils = mock(ISLF4JLoggingEventFactoryUtils.class);
        classUnderTest =
            new SLF4JMockLoggerImpl(
                SLF4JMockLoggerImplTest.class.getName(), mockConfig, mockFactory, mockFactoryUtils);
        mockLoggingEvent = mock(SLF4JLoggingEvent.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.getConfig()
    //

    @Test
    public void getConfig_Pass() {
        assertSame(classUnderTest.getConfig(), mockConfigCopy);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.getConfig()
    //

    @Test
    public void getName_Pass() {
        assertEquals(classUnderTest.getName(), SLF4JMockLoggerImplTest.class.getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    private void _whenIsEnabled(Marker marker, SLF4JLogLevel logLevel) {
        when(mockConfigCopy.isEnabled(marker, logLevel)).thenReturn(Boolean.TRUE);
    }

    private void _whenLogString(Marker marker, SLF4JLogLevel logLevel) {
        _whenIsEnabled(marker, logLevel);
        when(mockFactoryUtils.newInstance(mockFactory, classUnderTest, logLevel, marker, msg))
            .thenReturn(mockLoggingEvent);
        assertTrue(classUnderTest.getLoggingEventList().isEmpty());
    }

    private void _whenLogStringObject(Marker marker, SLF4JLogLevel logLevel) {
        _whenIsEnabled(marker, logLevel);
        when(mockFactoryUtils.newInstance(mockFactory, classUnderTest, logLevel, marker, format, arg))
            .thenReturn(mockLoggingEvent);
        assertTrue(classUnderTest.getLoggingEventList().isEmpty());
    }

    private void _whenLogStringObjectObject(Marker marker, SLF4JLogLevel logLevel) {
        _whenIsEnabled(marker, logLevel);
        when(mockFactoryUtils.newInstance(mockFactory, classUnderTest, logLevel, marker, format, arg, arg2))
            .thenReturn(mockLoggingEvent);
        assertTrue(classUnderTest.getLoggingEventList().isEmpty());
    }

    private void _whenLogStringObjectArr(Marker marker, SLF4JLogLevel logLevel) {
        _whenIsEnabled(marker, logLevel);
        when(mockFactoryUtils.newInstance(mockFactory, classUnderTest, logLevel, marker, format, argArr))
            .thenReturn(mockLoggingEvent);
        assertTrue(classUnderTest.getLoggingEventList().isEmpty());
    }

    private void _whenLogStringThrowable(Marker marker, SLF4JLogLevel logLevel) {
        _whenIsEnabled(marker, logLevel);
        when(mockFactoryUtils.newInstance(mockFactory, classUnderTest, logLevel, marker, msg, throwable))
            .thenReturn(mockLoggingEvent);
        assertTrue(classUnderTest.getLoggingEventList().isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isTraceEnabled()
    //

    @Test
    public void isTraceEnabled_Pass() {
        _whenIsEnabled(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        assertTrue(classUnderTest.isTraceEnabled());
        assertTrue(classUnderTest.isTraceEnabled(SLF4JMarkerNone.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(String)
    //

    @Test
    public void traceString_Pass() {
        _whenLogString(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        classUnderTest.trace(msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(String, Object)
    //

    @Test
    public void traceStringObject_Pass() {
        _whenLogStringObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        classUnderTest.trace(format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(String, Object, Object)
    //

    @Test
    public void traceStringObjectObject_Pass() {
        _whenLogStringObjectObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        classUnderTest.trace(format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(String, Object...)
    //

    @Test
    public void traceStringObjectArr_Pass() {
        _whenLogStringObjectArr(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        classUnderTest.trace(format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(String, Throwable)
    //

    @Test
    public void traceStringThrowable_Pass() {
        _whenLogStringThrowable(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.TRACE);
        classUnderTest.trace(msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isTraceEnabled(Marker)
    //

    @Test
    public void isTraceEnabledMarker_Pass() {
        _whenIsEnabled(mockMarker, SLF4JLogLevel.TRACE);
        assertTrue(classUnderTest.isTraceEnabled(mockMarker));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isTraceEnabledMarker_FailWithNullMarker() {
        classUnderTest.isTraceEnabled((Marker) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(Marker, String)
    //

    @Test
    public void traceMarkerString_Pass() {
        _whenLogString(mockMarker, SLF4JLogLevel.TRACE);
        classUnderTest.trace(mockMarker, msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void traceMarkerString_FailWithNullMarker() {
        classUnderTest.trace((Marker) null, msg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(Marker, String, Object)
    //

    @Test
    public void traceMarkerStringObject_Pass() {
        _whenLogStringObject(mockMarker, SLF4JLogLevel.TRACE);
        classUnderTest.trace(mockMarker, format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void traceMarkerStringObject_FailWithNullMarker() {
        classUnderTest.trace((Marker) null, format, arg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(Marker, String, Object, Object)
    //

    @Test
    public void traceMarkerStringObjectObject_Pass() {
        _whenLogStringObjectObject(mockMarker, SLF4JLogLevel.TRACE);
        classUnderTest.trace(mockMarker, format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void traceMarkerStringObjectObject_FailWithNullMarker() {
        classUnderTest.trace((Marker) null, format, arg, arg2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(Marker, String, Object...)
    //

    @Test
    public void traceMarkerStringObjectArr_Pass() {
        _whenLogStringObjectArr(mockMarker, SLF4JLogLevel.TRACE);
        classUnderTest.trace(mockMarker, format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void traceMarkerStringObjectArr_FailWithNullMarker() {
        classUnderTest.trace((Marker) null, format, argArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.trace(Marker, String, Throwable)
    //

    @Test
    public void traceMarkerStringThrowable_Pass() {
        _whenLogStringThrowable(mockMarker, SLF4JLogLevel.TRACE);
        classUnderTest.trace(mockMarker, msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void traceMarkerStringThrowable_FailWithNullMarker() {
        classUnderTest.trace((Marker) null, msg, throwable);
    }

    // TODO: LAST: Write tests for debug, etc.
}
