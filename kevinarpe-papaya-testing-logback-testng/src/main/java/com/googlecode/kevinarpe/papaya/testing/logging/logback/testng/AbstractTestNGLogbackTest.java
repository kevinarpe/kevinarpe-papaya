package com.googlecode.kevinarpe.papaya.testing.logging.logback.testng;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.testing.logging.logback.AbstractLogbackTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Convenient base class for testing classes with TestNG that employ Log4J for logging.
 *
 * Logging events are accessible via {@link #getLoggingEventList()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractLogbackTest
 */
@FullyTested
public abstract class AbstractTestNGLogbackTest
extends AbstractLogbackTest {

    @BeforeMethod
    public final void LogbackTestBase_BeforeEachTest() {
        super.addMockAppender();
    }

    @AfterMethod
    public final void LogbackTestBase_AfterEachTest() {
        super.removeMockAppender();
    }
}
