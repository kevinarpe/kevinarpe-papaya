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
public class SLF4JLevelLoggerTraceTest {

    private static final String MSG_OR_FORMAT = "MSG_OR_FORMAT";
    private static final Object ARG1 = new Object();
    private static final Object ARG2 = new Object();
    private static final Object ARG3 = new Object();
    private static final Throwable THROWABLE = new Throwable();

    private Logger mockLogger;
    private Marker mockMarker;

    @BeforeMethod
    public void beforeEachMethod() {
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.ctor(Logger)
    //

    @Test
    public void ctor_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull() {
        new SLF4JLevelLoggerTrace((Logger) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.getLogLevel()
    //

    @Test
    public void getLogLevel_Pass() {
        assertEquals(new SLF4JLevelLoggerTrace(mockLogger).getLogLevel(), SLF4JLogLevel.TRACE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.isEnabled()
    //

    @Test
    public void isEnabled_Pass() {
        for (boolean b : new boolean[] { true, false }) {
            when(mockLogger.isTraceEnabled()).thenReturn(b);
            assertEquals(new SLF4JLevelLoggerTrace(mockLogger).isEnabled(), b);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(String)
    //

    @Test
    public void logString_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(MSG_OR_FORMAT);
        verify(mockLogger).trace(MSG_OR_FORMAT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(String, Object)
    //

    @Test
    public void logStringObject_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(MSG_OR_FORMAT, ARG1);
        verify(mockLogger).trace(MSG_OR_FORMAT, ARG1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(String, Object, Object)
    //

    @Test
    public void logStringObjectObject_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(MSG_OR_FORMAT, ARG1, ARG2);
        verify(mockLogger).trace(MSG_OR_FORMAT, ARG1, ARG2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(String, Object...)
    //

    @Test
    public void logStringObjectArr_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(MSG_OR_FORMAT, ARG1, ARG2, ARG3);
        verify(mockLogger).trace(MSG_OR_FORMAT, ARG1, ARG2, ARG3);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(String, Throwable)
    //

    @Test
    public void logStringThrowable_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(MSG_OR_FORMAT, THROWABLE);
        verify(mockLogger).trace(MSG_OR_FORMAT, THROWABLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.isEnabled(Marker)
    //

    @Test
    public void isEnabledMarker_Pass() {
        for (boolean b : new boolean[] { true, false }) {
            when(mockLogger.isTraceEnabled(mockMarker)).thenReturn(b);
            assertEquals(new SLF4JLevelLoggerTrace(mockLogger).isEnabled(mockMarker), b);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(Marker, String)
    //

    @Test
    public void logMarkerString_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(mockMarker, MSG_OR_FORMAT);
        verify(mockLogger).trace(mockMarker, MSG_OR_FORMAT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(Marker, String, Object)
    //

    @Test
    public void logMarkerStringObject_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(mockMarker, MSG_OR_FORMAT, ARG1);
        verify(mockLogger).trace(mockMarker, MSG_OR_FORMAT, ARG1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(Marker, String, Object, Object)
    //

    @Test
    public void logMarkerStringObjectObject_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(mockMarker, MSG_OR_FORMAT, ARG1, ARG2);
        verify(mockLogger).trace(mockMarker, MSG_OR_FORMAT, ARG1, ARG2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(Marker, String, Object...)
    //

    @Test
    public void logMarkerStringObjectArr_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(mockMarker, MSG_OR_FORMAT, ARG1, ARG2, ARG3);
        verify(mockLogger).trace(mockMarker, MSG_OR_FORMAT, ARG1, ARG2, ARG3);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerTrace.log(Marker, String, Throwable)
    //

    @Test
    public void logMarkerStringThrowable_Pass() {
        new SLF4JLevelLoggerTrace(mockLogger).log(mockMarker, MSG_OR_FORMAT, THROWABLE);
        verify(mockLogger).trace(mockMarker, MSG_OR_FORMAT, THROWABLE);
    }
}
