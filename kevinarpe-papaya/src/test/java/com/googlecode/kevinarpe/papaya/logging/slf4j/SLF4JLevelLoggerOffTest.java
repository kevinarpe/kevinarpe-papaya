package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLevelLoggerOffTest {

    private static final String MSG_OR_FORMAT = "MSG_OR_FORMAT";
    private static final Object ARG1 = new Object();
    private static final Object ARG2 = new Object();
    private static final Object ARG3 = new Object();
    private static final Throwable THROWABLE = new Throwable();

    private Marker mockMarker;

    @BeforeMethod
    public void beforeEachMethod() {
        mockMarker = mock(Marker.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.ctor(Logger)
    //

    @Test
    public void ctor_Pass() {
        new SLF4JLevelLoggerOff();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.getLogLevel()
    //

    @Test
    public void getLogLevel_Pass() {
        assertEquals(new SLF4JLevelLoggerOff().getLogLevel(), SLF4JLogLevel.OFF);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.isEnabled()
    //

    @Test
    public void isEnabled_Pass() {
        assertFalse(new SLF4JLevelLoggerOff().isEnabled());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(String)
    //

    @Test
    public void logString_Pass() {
        new SLF4JLevelLoggerOff().log(MSG_OR_FORMAT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(String, Object)
    //

    @Test
    public void logStringObject_Pass() {
        new SLF4JLevelLoggerOff().log(MSG_OR_FORMAT, ARG1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(String, Object, Object)
    //

    @Test
    public void logStringObjectObject_Pass() {
        new SLF4JLevelLoggerOff().log(MSG_OR_FORMAT, ARG1, ARG2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(String, Object...)
    //

    @Test
    public void logStringObjectArr_Pass() {
        new SLF4JLevelLoggerOff().log(MSG_OR_FORMAT, ARG1, ARG2, ARG3);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(String, Throwable)
    //

    @Test
    public void logStringThrowable_Pass() {
        new SLF4JLevelLoggerOff().log(MSG_OR_FORMAT, THROWABLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.isEnabled(Marker)
    //

    @Test
    public void isEnabledMarker_Pass() {
        assertFalse(new SLF4JLevelLoggerOff().isEnabled(mockMarker));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(Marker, String)
    //

    @Test
    public void logMarkerString_Pass() {
        new SLF4JLevelLoggerOff().log(mockMarker, MSG_OR_FORMAT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(Marker, String, Object)
    //

    @Test
    public void logMarkerStringObject_Pass() {
        new SLF4JLevelLoggerOff().log(mockMarker, MSG_OR_FORMAT, ARG1);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(Marker, String, Object, Object)
    //

    @Test
    public void logMarkerStringObjectObject_Pass() {
        new SLF4JLevelLoggerOff().log(mockMarker, MSG_OR_FORMAT, ARG1, ARG2);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(Marker, String, Object...)
    //

    @Test
    public void logMarkerStringObjectArr_Pass() {
        new SLF4JLevelLoggerOff().log(mockMarker, MSG_OR_FORMAT, ARG1, ARG2, ARG3);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerOff.log(Marker, String, Throwable)
    //

    @Test
    public void logMarkerStringThrowable_Pass() {
        new SLF4JLevelLoggerOff().log(mockMarker, MSG_OR_FORMAT, THROWABLE);
    }
}
