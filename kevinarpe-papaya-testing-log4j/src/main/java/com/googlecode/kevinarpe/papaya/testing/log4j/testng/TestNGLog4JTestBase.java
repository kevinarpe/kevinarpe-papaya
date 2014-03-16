package com.googlecode.kevinarpe.papaya.testing.log4j.testng;

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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.testing.log4j.Log4JTestUtils;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@NotFullyTested
public class TestNGLog4JTestBase {

    @BeforeMethod
    public final void Log4JTestBase_BeforeEachTest() {
        Log4JTestUtils.addMockAppender();
    }

    @AfterMethod
    public final void Log4JTestBase_AfterEachTest() {
        Log4JTestUtils.removeMockAppender();
    }

    public final List<LoggingEvent> getLoggingEventList() {
        List<LoggingEvent> x = Log4JTestUtils.getLoggingEventList();
        return x;
    }
}