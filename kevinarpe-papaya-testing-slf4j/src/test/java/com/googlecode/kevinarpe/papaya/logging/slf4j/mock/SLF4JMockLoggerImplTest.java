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

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.logging.slf4j.ISLF4JLoggingEventFactoryUtils;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEvent;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLoggingEventFactory;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JMarkerNone;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
    private SLF4JMockLoggerConfig mockConfig2;
    private SLF4JMockLoggerConfig mockConfigCopy;
    private SLF4JLoggingEventFactory mockFactory;
    private SLF4JLoggingEventFactory mockFactory2;
    private ISLF4JLoggingEventFactoryUtils mockFactoryUtils;
    private ISLF4JLoggingEventFactoryUtils mockFactoryUtils2;
    private SLF4JLoggingEvent mockLoggingEvent;

    private SLF4JMockLoggerImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockMarker = mock(Marker.class);
        mockConfig = mock(SLF4JMockLoggerConfig.class);
        mockConfig2 = mock(SLF4JMockLoggerConfig.class);
        mockConfigCopy = mock(SLF4JMockLoggerConfig.class);
        when(mockConfig.copy()).thenReturn(mockConfigCopy);
        mockFactory = mock(SLF4JLoggingEventFactory.class);
        mockFactory2 = mock(SLF4JLoggingEventFactory.class);
        mockFactoryUtils = mock(ISLF4JLoggingEventFactoryUtils.class);
        mockFactoryUtils2 = mock(ISLF4JLoggingEventFactoryUtils.class);
        classUnderTest =
            new SLF4JMockLoggerImpl(
                SLF4JMockLoggerImplTest.class.getName(), mockConfig, mockFactory, mockFactoryUtils);
        mockLoggingEvent = mock(SLF4JLoggingEvent.class);
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
    // SLF4JMockLoggerImpl.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_Fail_Data() {
        SLF4JMockLoggerConfig mockSLF4JMockLoggerConfig = mock(SLF4JMockLoggerConfig.class);
        return new Object[][] {
            { (String) null, mockSLF4JMockLoggerConfig, NullPointerException.class },
            { "", mockSLF4JMockLoggerConfig, IllegalArgumentException.class },
            { "   ", mockSLF4JMockLoggerConfig, IllegalArgumentException.class },
            { "name", (SLF4JMockLoggerConfig) null, NullPointerException.class },
        };
    }

    @Test(dataProvider = "_ctor_Fail_Data")
    public void ctor_Fail(
            String name,
            SLF4JMockLoggerConfig config,
            Class<? extends Exception> expectedExceptionClass) {
        try {
            new SLF4JMockLoggerImpl(name, config);
        }
        catch (Exception e) {
            assertSame(e.getClass(), expectedExceptionClass);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.getConfig()
    //

    @Test
    public void getConfig_Pass() {
        assertSame(classUnderTest.getConfig(), mockConfigCopy);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.getName()
    //

    @Test
    public void getName_Pass() {
        assertEquals(classUnderTest.getName(), SLF4JMockLoggerImplTest.class.getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.getLoggingEventList()
    //

    @Test
    public void getLoggingEventList_Pass() {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isDebugEnabled()
    //

    @Test
    public void isDebugEnabled_Pass() {
        _whenIsEnabled(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        assertTrue(classUnderTest.isDebugEnabled());
        assertTrue(classUnderTest.isDebugEnabled(SLF4JMarkerNone.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(String)
    //

    @Test
    public void debugString_Pass() {
        _whenLogString(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(String, Object)
    //

    @Test
    public void debugStringObject_Pass() {
        _whenLogStringObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(String, Object, Object)
    //

    @Test
    public void debugStringObjectObject_Pass() {
        _whenLogStringObjectObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(String, Object...)
    //

    @Test
    public void debugStringObjectArr_Pass() {
        _whenLogStringObjectArr(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(String, Throwable)
    //

    @Test
    public void debugStringThrowable_Pass() {
        _whenLogStringThrowable(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isDebugEnabled(Marker)
    //

    @Test
    public void isDebugEnabledMarker_Pass() {
        _whenIsEnabled(mockMarker, SLF4JLogLevel.DEBUG);
        assertTrue(classUnderTest.isDebugEnabled(mockMarker));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isDebugEnabledMarker_FailWithNullMarker() {
        classUnderTest.isDebugEnabled((Marker) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(Marker, String)
    //

    @Test
    public void debugMarkerString_Pass() {
        _whenLogString(mockMarker, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(mockMarker, msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void debugMarkerString_FailWithNullMarker() {
        classUnderTest.debug((Marker) null, msg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(Marker, String, Object)
    //

    @Test
    public void debugMarkerStringObject_Pass() {
        _whenLogStringObject(mockMarker, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(mockMarker, format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void debugMarkerStringObject_FailWithNullMarker() {
        classUnderTest.debug((Marker) null, format, arg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(Marker, String, Object, Object)
    //

    @Test
    public void debugMarkerStringObjectObject_Pass() {
        _whenLogStringObjectObject(mockMarker, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(mockMarker, format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void debugMarkerStringObjectObject_FailWithNullMarker() {
        classUnderTest.debug((Marker) null, format, arg, arg2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(Marker, String, Object...)
    //

    @Test
    public void debugMarkerStringObjectArr_Pass() {
        _whenLogStringObjectArr(mockMarker, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(mockMarker, format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void debugMarkerStringObjectArr_FailWithNullMarker() {
        classUnderTest.debug((Marker) null, format, argArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.debug(Marker, String, Throwable)
    //

    @Test
    public void debugMarkerStringThrowable_Pass() {
        _whenLogStringThrowable(mockMarker, SLF4JLogLevel.DEBUG);
        classUnderTest.debug(mockMarker, msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void debugMarkerStringThrowable_FailWithNullMarker() {
        classUnderTest.debug((Marker) null, msg, throwable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isInfoEnabled()
    //

    @Test
    public void isInfoEnabled_Pass() {
        _whenIsEnabled(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        assertTrue(classUnderTest.isInfoEnabled());
        assertTrue(classUnderTest.isInfoEnabled(SLF4JMarkerNone.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(String)
    //

    @Test
    public void infoString_Pass() {
        _whenLogString(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        classUnderTest.info(msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(String, Object)
    //

    @Test
    public void infoStringObject_Pass() {
        _whenLogStringObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        classUnderTest.info(format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(String, Object, Object)
    //

    @Test
    public void infoStringObjectObject_Pass() {
        _whenLogStringObjectObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        classUnderTest.info(format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(String, Object...)
    //

    @Test
    public void infoStringObjectArr_Pass() {
        _whenLogStringObjectArr(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        classUnderTest.info(format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(String, Throwable)
    //

    @Test
    public void infoStringThrowable_Pass() {
        _whenLogStringThrowable(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.INFO);
        classUnderTest.info(msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isInfoEnabled(Marker)
    //

    @Test
    public void isInfoEnabledMarker_Pass() {
        _whenIsEnabled(mockMarker, SLF4JLogLevel.INFO);
        assertTrue(classUnderTest.isInfoEnabled(mockMarker));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isInfoEnabledMarker_FailWithNullMarker() {
        classUnderTest.isInfoEnabled((Marker) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(Marker, String)
    //

    @Test
    public void infoMarkerString_Pass() {
        _whenLogString(mockMarker, SLF4JLogLevel.INFO);
        classUnderTest.info(mockMarker, msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void infoMarkerString_FailWithNullMarker() {
        classUnderTest.info((Marker) null, msg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(Marker, String, Object)
    //

    @Test
    public void infoMarkerStringObject_Pass() {
        _whenLogStringObject(mockMarker, SLF4JLogLevel.INFO);
        classUnderTest.info(mockMarker, format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void infoMarkerStringObject_FailWithNullMarker() {
        classUnderTest.info((Marker) null, format, arg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(Marker, String, Object, Object)
    //

    @Test
    public void infoMarkerStringObjectObject_Pass() {
        _whenLogStringObjectObject(mockMarker, SLF4JLogLevel.INFO);
        classUnderTest.info(mockMarker, format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void infoMarkerStringObjectObject_FailWithNullMarker() {
        classUnderTest.info((Marker) null, format, arg, arg2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(Marker, String, Object...)
    //

    @Test
    public void infoMarkerStringObjectArr_Pass() {
        _whenLogStringObjectArr(mockMarker, SLF4JLogLevel.INFO);
        classUnderTest.info(mockMarker, format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void infoMarkerStringObjectArr_FailWithNullMarker() {
        classUnderTest.info((Marker) null, format, argArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.info(Marker, String, Throwable)
    //

    @Test
    public void infoMarkerStringThrowable_Pass() {
        _whenLogStringThrowable(mockMarker, SLF4JLogLevel.INFO);
        classUnderTest.info(mockMarker, msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void infoMarkerStringThrowable_FailWithNullMarker() {
        classUnderTest.info((Marker) null, msg, throwable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isWarnEnabled()
    //

    @Test
    public void isWarnEnabled_Pass() {
        _whenIsEnabled(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        assertTrue(classUnderTest.isWarnEnabled());
        assertTrue(classUnderTest.isWarnEnabled(SLF4JMarkerNone.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(String)
    //

    @Test
    public void warnString_Pass() {
        _whenLogString(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        classUnderTest.warn(msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(String, Object)
    //

    @Test
    public void warnStringObject_Pass() {
        _whenLogStringObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        classUnderTest.warn(format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(String, Object, Object)
    //

    @Test
    public void warnStringObjectObject_Pass() {
        _whenLogStringObjectObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        classUnderTest.warn(format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(String, Object...)
    //

    @Test
    public void warnStringObjectArr_Pass() {
        _whenLogStringObjectArr(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        classUnderTest.warn(format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(String, Throwable)
    //

    @Test
    public void warnStringThrowable_Pass() {
        _whenLogStringThrowable(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.WARN);
        classUnderTest.warn(msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isWarnEnabled(Marker)
    //

    @Test
    public void isWarnEnabledMarker_Pass() {
        _whenIsEnabled(mockMarker, SLF4JLogLevel.WARN);
        assertTrue(classUnderTest.isWarnEnabled(mockMarker));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isWarnEnabledMarker_FailWithNullMarker() {
        classUnderTest.isWarnEnabled((Marker) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(Marker, String)
    //

    @Test
    public void warnMarkerString_Pass() {
        _whenLogString(mockMarker, SLF4JLogLevel.WARN);
        classUnderTest.warn(mockMarker, msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void warnMarkerString_FailWithNullMarker() {
        classUnderTest.warn((Marker) null, msg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(Marker, String, Object)
    //

    @Test
    public void warnMarkerStringObject_Pass() {
        _whenLogStringObject(mockMarker, SLF4JLogLevel.WARN);
        classUnderTest.warn(mockMarker, format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void warnMarkerStringObject_FailWithNullMarker() {
        classUnderTest.warn((Marker) null, format, arg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(Marker, String, Object, Object)
    //

    @Test
    public void warnMarkerStringObjectObject_Pass() {
        _whenLogStringObjectObject(mockMarker, SLF4JLogLevel.WARN);
        classUnderTest.warn(mockMarker, format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void warnMarkerStringObjectObject_FailWithNullMarker() {
        classUnderTest.warn((Marker) null, format, arg, arg2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(Marker, String, Object...)
    //

    @Test
    public void warnMarkerStringObjectArr_Pass() {
        _whenLogStringObjectArr(mockMarker, SLF4JLogLevel.WARN);
        classUnderTest.warn(mockMarker, format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void warnMarkerStringObjectArr_FailWithNullMarker() {
        classUnderTest.warn((Marker) null, format, argArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.warn(Marker, String, Throwable)
    //

    @Test
    public void warnMarkerStringThrowable_Pass() {
        _whenLogStringThrowable(mockMarker, SLF4JLogLevel.WARN);
        classUnderTest.warn(mockMarker, msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void warnMarkerStringThrowable_FailWithNullMarker() {
        classUnderTest.warn((Marker) null, msg, throwable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isErrorEnabled()
    //

    @Test
    public void isErrorEnabled_Pass() {
        _whenIsEnabled(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        assertTrue(classUnderTest.isErrorEnabled());
        assertTrue(classUnderTest.isErrorEnabled(SLF4JMarkerNone.INSTANCE));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(String)
    //

    @Test
    public void errorString_Pass() {
        _whenLogString(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        classUnderTest.error(msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(String, Object)
    //

    @Test
    public void errorStringObject_Pass() {
        _whenLogStringObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        classUnderTest.error(format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(String, Object, Object)
    //

    @Test
    public void errorStringObjectObject_Pass() {
        _whenLogStringObjectObject(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        classUnderTest.error(format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(String, Object...)
    //

    @Test
    public void errorStringObjectArr_Pass() {
        _whenLogStringObjectArr(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        classUnderTest.error(format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(String, Throwable)
    //

    @Test
    public void errorStringThrowable_Pass() {
        _whenLogStringThrowable(SLF4JMarkerNone.INSTANCE, SLF4JLogLevel.ERROR);
        classUnderTest.error(msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.isErrorEnabled(Marker)
    //

    @Test
    public void isErrorEnabledMarker_Pass() {
        _whenIsEnabled(mockMarker, SLF4JLogLevel.ERROR);
        assertTrue(classUnderTest.isErrorEnabled(mockMarker));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void isErrorEnabledMarker_FailWithNullMarker() {
        classUnderTest.isErrorEnabled((Marker) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(Marker, String)
    //

    @Test
    public void errorMarkerString_Pass() {
        _whenLogString(mockMarker, SLF4JLogLevel.ERROR);
        classUnderTest.error(mockMarker, msg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void errorMarkerString_FailWithNullMarker() {
        classUnderTest.error((Marker) null, msg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(Marker, String, Object)
    //

    @Test
    public void errorMarkerStringObject_Pass() {
        _whenLogStringObject(mockMarker, SLF4JLogLevel.ERROR);
        classUnderTest.error(mockMarker, format, arg);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void errorMarkerStringObject_FailWithNullMarker() {
        classUnderTest.error((Marker) null, format, arg);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(Marker, String, Object, Object)
    //

    @Test
    public void errorMarkerStringObjectObject_Pass() {
        _whenLogStringObjectObject(mockMarker, SLF4JLogLevel.ERROR);
        classUnderTest.error(mockMarker, format, arg, arg2);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void errorMarkerStringObjectObject_FailWithNullMarker() {
        classUnderTest.error((Marker) null, format, arg, arg2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(Marker, String, Object...)
    //

    @Test
    public void errorMarkerStringObjectArr_Pass() {
        _whenLogStringObjectArr(mockMarker, SLF4JLogLevel.ERROR);
        classUnderTest.error(mockMarker, format, argArr);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void errorMarkerStringObjectArr_FailWithNullMarker() {
        classUnderTest.error((Marker) null, format, argArr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.error(Marker, String, Throwable)
    //

    @Test
    public void errorMarkerStringThrowable_Pass() {
        _whenLogStringThrowable(mockMarker, SLF4JLogLevel.ERROR);
        classUnderTest.error(mockMarker, msg, throwable);
        assertEquals(classUnderTest.getLoggingEventList(), Arrays.asList(mockLoggingEvent));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void errorMarkerStringThrowable_FailWithNullMarker() {
        classUnderTest.error((Marker) null, msg, throwable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerImpl.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory, mockFactoryUtils),
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory, mockFactoryUtils));
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerImpl("name2", mockConfig, mockFactory, mockFactoryUtils),
            new SLF4JMockLoggerImpl("name2", mockConfig, mockFactory, mockFactoryUtils));
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerImpl("name", mockConfig2, mockFactory, mockFactoryUtils),
            new SLF4JMockLoggerImpl("name", mockConfig2, mockFactory, mockFactoryUtils));
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory2, mockFactoryUtils),
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory2, mockFactoryUtils));
        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory, mockFactoryUtils2),
            new SLF4JMockLoggerImpl("name", mockConfig, mockFactory, mockFactoryUtils2));
        // TODO: This does not test if _loggingEventList is compared for equality.
        equalsTester.testEquals();
    }
}
