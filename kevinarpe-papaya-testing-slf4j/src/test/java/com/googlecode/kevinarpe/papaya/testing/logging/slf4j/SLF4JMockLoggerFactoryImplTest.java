package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import com.google.common.testing.EqualsTester;
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JMockLoggerFactoryImplTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerFactoryImpl.ctor()
    //

    @Test
    public void ctor_Pass() {
        new SLF4JMockLoggerFactoryImpl();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerFactoryImpl.ctor(SLF4JMockLoggerFactoryImpl)
    //

    @Test
    public void ctor2_Pass() {
        SLF4JMockLoggerFactoryImpl x = new SLF4JMockLoggerFactoryImpl();
        SLF4JMockLoggerImpl lx = x.getLogger(SLF4JMockLoggerFactoryImplTest.class.getName());
        SLF4JMockLoggerFactoryImpl y = new SLF4JMockLoggerFactoryImpl(x);
        SLF4JMockLoggerImpl ly = x.getLogger(SLF4JMockLoggerFactoryImplTest.class.getName());
        assertNotSame(y.getConfig(), x.getConfig());
        assertEquals(y.getConfig(), x.getConfig());
        assertSame(ly, lx);

        SLF4JMockLoggerImpl lx2 = x.getLogger(SLF4JMockLoggerFactoryImplTest.class.getName() + "2");
        SLF4JMockLoggerImpl ly2 = y.getLogger(SLF4JMockLoggerFactoryImplTest.class.getName() + "2");
        assertNotSame(ly2, lx2);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void ctor2_FailWithNull() {
        new SLF4JMockLoggerFactoryImpl((SLF4JMockLoggerFactoryImpl) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerFactoryImpl.getLogger(String)
    //

    @Test
    public void getLogger_Pass() {
        SLF4JMockLoggerFactoryImpl classUnderTest = new SLF4JMockLoggerFactoryImpl();
        assertNotNull(classUnderTest.getLogger(SLF4JMockLoggerFactoryImplTest.class.getName()));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getLogger_FailWithNull() {
        new SLF4JMockLoggerFactoryImpl().getLogger((String) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JMockLoggerFactoryImpl.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();

        equalsTester.addEqualityGroup(
            new SLF4JMockLoggerFactoryImpl(),
            new SLF4JMockLoggerFactoryImpl());

        SLF4JMockLoggerFactoryImpl x1 = new SLF4JMockLoggerFactoryImpl();
        x1.getConfig()
            .setEnabled(SLF4JLogLevel.DEBUG, !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);

        SLF4JMockLoggerFactoryImpl x2 = new SLF4JMockLoggerFactoryImpl();
        x2.getConfig()
            .setEnabled(SLF4JLogLevel.DEBUG, !SLF4JMockLoggerConfigImpl.DEFAULT_IS_ENABLED);

        equalsTester.addEqualityGroup(x1, x2);

        SLF4JMockLoggerFactoryImpl x3 = new SLF4JMockLoggerFactoryImpl();
        x3.getLogger("blah");

        SLF4JMockLoggerFactoryImpl x4 = new SLF4JMockLoggerFactoryImpl();
        x4.getLogger("blah");

        equalsTester.addEqualityGroup(x3, x4);

        equalsTester.testEquals();
    }
}
