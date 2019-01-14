package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import com.googlecode.kevinarpe.papaya.PrimitiveTypeUtils;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JForwardingLoggerTest {

    private Logger mockLogger;
    private Marker mockMarker;
    private _SLF4JForwardingLogger classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        classUnderTest = new _SLF4JForwardingLogger(mockLogger);
    }

    private static class _SLF4JForwardingLogger
    extends SLF4JForwardingLogger {

        private final Logger _logger;

        private _SLF4JForwardingLogger(Logger logger) {
            this._logger = ObjectArgs.checkNotNull(logger, "logger");
        }

        @Override
        protected Logger delegate() {
            return _logger;
        }
    }

    @Test
    public void getName_Pass() {
        when(mockLogger.getName()).thenReturn("dummy");
        assertEquals(classUnderTest.getName(), "dummy");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // trace
    //

    @Test
    public void isTraceEnabled_Pass() {
        when(mockLogger.isTraceEnabled()).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isTraceEnabled(), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void traceString_Pass() {
        classUnderTest.trace("dummy");
        verify(mockLogger).trace("dummy");
    }

    @Test
    public void traceStringObject_Pass() {
        Object x = new Object();
        classUnderTest.trace("dummy", x);
        verify(mockLogger).trace("dummy", x);
    }

    @Test
    public void traceStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.trace("dummy", x, y);
        verify(mockLogger).trace("dummy", x, y);
    }

    @Test
    public void traceStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.trace("dummy", x);
        verify(mockLogger).trace("dummy", x);
    }

    @Test
    public void traceStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.trace("dummy", x);
        verify(mockLogger).trace("dummy", x);
    }

    @Test
    public void isTraceEnabledMarker_Pass() {
        when(mockLogger.isTraceEnabled(mockMarker)).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isTraceEnabled(mockMarker), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void traceMarkerString_Pass() {
        classUnderTest.trace(mockMarker, "dummy");
        verify(mockLogger).trace(mockMarker, "dummy");
    }

    @Test
    public void traceMarkerStringObject_Pass() {
        Object x = new Object();
        classUnderTest.trace(mockMarker, "dummy", x);
        verify(mockLogger).trace(mockMarker, "dummy", x);
    }

    @Test
    public void traceMarkerStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.trace(mockMarker, "dummy", x, y);
        verify(mockLogger).trace(mockMarker, "dummy", x, y);
    }

    @Test
    public void traceMarkerStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.trace(mockMarker, "dummy", x);
        verify(mockLogger).trace(mockMarker, "dummy", x);
    }

    @Test
    public void traceMarkerStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.trace(mockMarker, "dummy", x);
        verify(mockLogger).trace(mockMarker, "dummy", x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // debug
    //

    @Test
    public void isDebugEnabled_Pass() {
        when(mockLogger.isDebugEnabled()).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isDebugEnabled(), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void debugString_Pass() {
        classUnderTest.debug("dummy");
        verify(mockLogger).debug("dummy");
    }

    @Test
    public void debugStringObject_Pass() {
        Object x = new Object();
        classUnderTest.debug("dummy", x);
        verify(mockLogger).debug("dummy", x);
    }

    @Test
    public void debugStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.debug("dummy", x, y);
        verify(mockLogger).debug("dummy", x, y);
    }

    @Test
    public void debugStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.debug("dummy", x);
        verify(mockLogger).debug("dummy", x);
    }

    @Test
    public void debugStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.debug("dummy", x);
        verify(mockLogger).debug("dummy", x);
    }

    @Test
    public void isDebugEnabledMarker_Pass() {
        when(mockLogger.isDebugEnabled(mockMarker)).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isDebugEnabled(mockMarker), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void debugMarkerString_Pass() {
        classUnderTest.debug(mockMarker, "dummy");
        verify(mockLogger).debug(mockMarker, "dummy");
    }

    @Test
    public void debugMarkerStringObject_Pass() {
        Object x = new Object();
        classUnderTest.debug(mockMarker, "dummy", x);
        verify(mockLogger).debug(mockMarker, "dummy", x);
    }

    @Test
    public void debugMarkerStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.debug(mockMarker, "dummy", x, y);
        verify(mockLogger).debug(mockMarker, "dummy", x, y);
    }

    @Test
    public void debugMarkerStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.debug(mockMarker, "dummy", x);
        verify(mockLogger).debug(mockMarker, "dummy", x);
    }

    @Test
    public void debugMarkerStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.debug(mockMarker, "dummy", x);
        verify(mockLogger).debug(mockMarker, "dummy", x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // info
    //

    @Test
    public void isInfoEnabled_Pass() {
        when(mockLogger.isInfoEnabled()).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isInfoEnabled(), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void infoString_Pass() {
        classUnderTest.info("dummy");
        verify(mockLogger).info("dummy");
    }

    @Test
    public void infoStringObject_Pass() {
        Object x = new Object();
        classUnderTest.info("dummy", x);
        verify(mockLogger).info("dummy", x);
    }

    @Test
    public void infoStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.info("dummy", x, y);
        verify(mockLogger).info("dummy", x, y);
    }

    @Test
    public void infoStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.info("dummy", x);
        verify(mockLogger).info("dummy", x);
    }

    @Test
    public void infoStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.info("dummy", x);
        verify(mockLogger).info("dummy", x);
    }

    @Test
    public void isInfoEnabledMarker_Pass() {
        when(mockLogger.isInfoEnabled(mockMarker)).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isInfoEnabled(mockMarker), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void infoMarkerString_Pass() {
        classUnderTest.info(mockMarker, "dummy");
        verify(mockLogger).info(mockMarker, "dummy");
    }

    @Test
    public void infoMarkerStringObject_Pass() {
        Object x = new Object();
        classUnderTest.info(mockMarker, "dummy", x);
        verify(mockLogger).info(mockMarker, "dummy", x);
    }

    @Test
    public void infoMarkerStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.info(mockMarker, "dummy", x, y);
        verify(mockLogger).info(mockMarker, "dummy", x, y);
    }

    @Test
    public void infoMarkerStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.info(mockMarker, "dummy", x);
        verify(mockLogger).info(mockMarker, "dummy", x);
    }

    @Test
    public void infoMarkerStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.info(mockMarker, "dummy", x);
        verify(mockLogger).info(mockMarker, "dummy", x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // warn
    //

    @Test
    public void isWarnEnabled_Pass() {
        when(mockLogger.isWarnEnabled()).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isWarnEnabled(), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void warnString_Pass() {
        classUnderTest.warn("dummy");
        verify(mockLogger).warn("dummy");
    }

    @Test
    public void warnStringObject_Pass() {
        Object x = new Object();
        classUnderTest.warn("dummy", x);
        verify(mockLogger).warn("dummy", x);
    }

    @Test
    public void warnStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.warn("dummy", x, y);
        verify(mockLogger).warn("dummy", x, y);
    }

    @Test
    public void warnStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.warn("dummy", x);
        verify(mockLogger).warn("dummy", x);
    }

    @Test
    public void warnStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.warn("dummy", x);
        verify(mockLogger).warn("dummy", x);
    }

    @Test
    public void isWarnEnabledMarker_Pass() {
        when(mockLogger.isWarnEnabled(mockMarker)).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isWarnEnabled(mockMarker), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void warnMarkerString_Pass() {
        classUnderTest.warn(mockMarker, "dummy");
        verify(mockLogger).warn(mockMarker, "dummy");
    }

    @Test
    public void warnMarkerStringObject_Pass() {
        Object x = new Object();
        classUnderTest.warn(mockMarker, "dummy", x);
        verify(mockLogger).warn(mockMarker, "dummy", x);
    }

    @Test
    public void warnMarkerStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.warn(mockMarker, "dummy", x, y);
        verify(mockLogger).warn(mockMarker, "dummy", x, y);
    }

    @Test
    public void warnMarkerStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.warn(mockMarker, "dummy", x);
        verify(mockLogger).warn(mockMarker, "dummy", x);
    }

    @Test
    public void warnMarkerStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.warn(mockMarker, "dummy", x);
        verify(mockLogger).warn(mockMarker, "dummy", x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // error
    //

    @Test
    public void isErrorEnabled_Pass() {
        when(mockLogger.isErrorEnabled()).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isErrorEnabled(), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void errorString_Pass() {
        classUnderTest.error("dummy");
        verify(mockLogger).error("dummy");
    }

    @Test
    public void errorStringObject_Pass() {
        Object x = new Object();
        classUnderTest.error("dummy", x);
        verify(mockLogger).error("dummy", x);
    }

    @Test
    public void errorStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.error("dummy", x, y);
        verify(mockLogger).error("dummy", x, y);
    }

    @Test
    public void errorStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.error("dummy", x);
        verify(mockLogger).error("dummy", x);
    }

    @Test
    public void errorStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.error("dummy", x);
        verify(mockLogger).error("dummy", x);
    }

    @Test
    public void isErrorEnabledMarker_Pass() {
        when(mockLogger.isErrorEnabled(mockMarker)).thenReturn(!PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
        assertEquals(classUnderTest.isErrorEnabled(mockMarker), !PrimitiveTypeUtils.DEFAULT_BOOLEAN_VALUE);
    }

    @Test
    public void errorMarkerString_Pass() {
        classUnderTest.error(mockMarker, "dummy");
        verify(mockLogger).error(mockMarker, "dummy");
    }

    @Test
    public void errorMarkerStringObject_Pass() {
        Object x = new Object();
        classUnderTest.error(mockMarker, "dummy", x);
        verify(mockLogger).error(mockMarker, "dummy", x);
    }

    @Test
    public void errorMarkerStringObjectObject_Pass() {
        Object x = new Object();
        Object y = new Object();
        classUnderTest.error(mockMarker, "dummy", x, y);
        verify(mockLogger).error(mockMarker, "dummy", x, y);
    }

    @Test
    public void errorMarkerStringObjectArr_Pass() {
        Object[] x = new Object[0];
        classUnderTest.error(mockMarker, "dummy", x);
        verify(mockLogger).error(mockMarker, "dummy", x);
    }

    @Test
    public void errorMarkerStringThrowable_Pass() {
        Throwable x = new Throwable();
        classUnderTest.error(mockMarker, "dummy", x);
        verify(mockLogger).error(mockMarker, "dummy", x);
    }
}
