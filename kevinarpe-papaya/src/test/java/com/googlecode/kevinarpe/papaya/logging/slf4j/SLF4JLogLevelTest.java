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

import org.slf4j.Logger;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLogLevelTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLogLevel.newLevelLogger()
    //

    @Test
    public void newLevelLogger_Pass() {
        _newLevelLogger_Pass(SLF4JLogLevel.OFF, SLF4JLevelLoggerOff.class);
        _newLevelLogger_Pass(SLF4JLogLevel.ERROR, SLF4JLevelLoggerError.class);
        _newLevelLogger_Pass(SLF4JLogLevel.WARN, SLF4JLevelLoggerWarn.class);
        _newLevelLogger_Pass(SLF4JLogLevel.INFO, SLF4JLevelLoggerInfo.class);
        _newLevelLogger_Pass(SLF4JLogLevel.DEBUG, SLF4JLevelLoggerDebug.class);
        _newLevelLogger_Pass(SLF4JLogLevel.TRACE, SLF4JLevelLoggerTrace.class);
    }

    private void _newLevelLogger_Pass(SLF4JLogLevel logLevel, Class<? extends SLF4JLevelLogger> clazz) {
        Logger mockLogger = mock(Logger.class);
        assertSame(logLevel.newLevelLogger(mockLogger).getClass(), clazz);
    }
}
