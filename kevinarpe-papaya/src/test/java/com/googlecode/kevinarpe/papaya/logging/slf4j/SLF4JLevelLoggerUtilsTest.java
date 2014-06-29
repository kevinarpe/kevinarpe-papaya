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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLevelLoggerUtilsTest {

    private SLF4JLevelLoggerUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        classUnderTest = new SLF4JLevelLoggerUtils();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLevelLoggerUtils.newInstance(SLF4JLogLevel logLevel, Class<?> clazz)
    //

    @Test
    public void newInstance_Pass() {
        _newInstance_Pass(SLF4JLogLevel.OFF, SLF4JLevelLoggerOff.class);
        _newInstance_Pass(SLF4JLogLevel.ERROR, SLF4JLevelLoggerError.class);
        _newInstance_Pass(SLF4JLogLevel.WARN, SLF4JLevelLoggerWarn.class);
        _newInstance_Pass(SLF4JLogLevel.INFO, SLF4JLevelLoggerInfo.class);
        _newInstance_Pass(SLF4JLogLevel.DEBUG, SLF4JLevelLoggerDebug.class);
        _newInstance_Pass(SLF4JLogLevel.TRACE, SLF4JLevelLoggerTrace.class);
    }

    private void _newInstance_Pass(
            SLF4JLogLevel logLevel, Class<? extends SLF4JLevelLogger> clazz) {
        SLF4JLevelLogger x = classUnderTest.newInstance(logLevel, SLF4JLevelLoggerUtilsTest.class);
        assertSame(x.getClass(), clazz);
    }

    @DataProvider
    private static Object[][] _newInstance_Fail_Data() {
        return new Object[][] {
            { SLF4JLogLevel.OFF, (Class<?>) null },
            { (SLF4JLogLevel) null, String.class },
            { (SLF4JLogLevel) null, (Class<?>) null },
        };
    }

    @Test(dataProvider = "_newInstance_Fail_Data",
            expectedExceptions = NullPointerException.class)
    public void newInstance_Fail(SLF4JLogLevel logLevel, Class<?> clazz) {
        classUnderTest.newInstance(logLevel, clazz);
    }
}
