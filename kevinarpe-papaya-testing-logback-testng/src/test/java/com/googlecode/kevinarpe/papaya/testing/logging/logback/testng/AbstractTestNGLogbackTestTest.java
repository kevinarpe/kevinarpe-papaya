package com.googlecode.kevinarpe.papaya.testing.logging.logback.testng;

/*-
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
/*
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

//@author Kevin Connor ARPE (kevinarpe@gmail.com)
// Intentional: Apache Maven does allow for multiple dependency scopes in the same module.
// It is not possible to include testng as both a compile and test dependency.

public class AbstractTestNGLogbackTestTest
extends AbstractTestNGLogbackTest {

    private Logger classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {

        classUnderTest = LoggerFactory.getLogger(AbstractTestNGLogbackTestTest.class);
    }

    @Test
    public void pass() {

        classUnderTest.info("info");
        classUnderTest.error("error");

        final List<ILoggingEvent> loggingEventList = super.getLoggingEventList();
        Assert.assertEquals(2, loggingEventList.size());

        final ILoggingEvent e0 = loggingEventList.get(0);
        Assert.assertEquals(e0.getLevel(), Level.INFO);
        Assert.assertEquals(e0.getMessage(), "info");

        final ILoggingEvent e1 = loggingEventList.get(1);
        Assert.assertEquals(e1.getLevel(), Level.ERROR);
        Assert.assertEquals(e1.getMessage(), "error");
    }

    // Intentional: Check new mock appender is created before each test method.
    @Test
    public void pass2() {

        pass();
    }
}
*/
