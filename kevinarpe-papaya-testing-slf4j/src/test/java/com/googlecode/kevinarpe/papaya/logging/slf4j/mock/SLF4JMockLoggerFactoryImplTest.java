package com.googlecode.kevinarpe.papaya.logging.slf4j.mock;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
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
}
