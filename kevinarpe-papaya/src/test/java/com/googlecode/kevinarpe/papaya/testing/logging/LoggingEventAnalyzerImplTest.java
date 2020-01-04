package com.googlecode.kevinarpe.papaya.testing.logging;

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

import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.mock;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class LoggingEventAnalyzerImplTest {

    public static final String MESSAGE1 = "message1";
    public static final String MESSAGE2 = "message2";

    interface TestLoggingEvent {
        // Empty.
    }

    private TestLoggingEvent mockLoggingEvent1;
    private TestLoggingEvent mockLoggingEvent2;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLoggingEvent1 = mock(TestLoggingEvent.class);
        mockLoggingEvent2 = mock(TestLoggingEvent.class);
    }

}
