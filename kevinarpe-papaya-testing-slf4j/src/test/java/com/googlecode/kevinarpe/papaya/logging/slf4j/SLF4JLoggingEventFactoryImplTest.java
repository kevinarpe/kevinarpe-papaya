package com.googlecode.kevinarpe.papaya.logging.slf4j;

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
