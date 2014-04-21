package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventFactoryImplTest {

    private Logger mockLogger;
    private Marker mockMarker;
    private Throwable mockThrowable;

    private SLF4JLoggingEventFactoryImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        mockThrowable = mock(Throwable.class);
        classUnderTest = new SLF4JLoggingEventFactoryImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryImpl.newInstance()
    //

    @Test
    public void newInstance_Pass() {
        SLF4JLoggingEventImpl event =
            classUnderTest.newInstance(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "a", "b", "c" },
                mockThrowable);
        SLF4JLoggingEventImplTest.assertLoggingEventEquals(
            event,
            mockLogger,
            SLF4JLogLevel.ERROR,
            mockMarker,
            "message",
            new Object[] { "a", "b", "c" },
            mockThrowable);
    }
}
