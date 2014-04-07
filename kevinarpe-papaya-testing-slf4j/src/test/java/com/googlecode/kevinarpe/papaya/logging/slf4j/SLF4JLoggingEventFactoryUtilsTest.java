package com.googlecode.kevinarpe.papaya.logging.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventFactoryUtilsTest {

    private SLF4JLoggingEventFactory mockSLF4JLoggingEventFactory;

    private SLF4JLoggingEventFactoryUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockSLF4JLoggingEventFactory = mock(SLF4JLoggingEventFactory.class);
        classUnderTest = new SLF4JLoggingEventFactoryUtils();
    }

    private void _assertLoggingEventEquals(
            SLF4JLoggingEvent loggingEvent,
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable) {
        assertEquals(loggingEvent.getLogger(), logger);
        assertEquals(loggingEvent.getLevel(), logLevel);
        assertEquals(loggingEvent.getMarker(), marker);
        assertEquals(loggingEvent.getMessage(), message);
        assertEquals(loggingEvent.getMessageFormatArgArr(), optionalFormatArgArr);
        assertEquals(loggingEvent.getThrowable(), optionalThrowable);
    }

    // TODO: LAST
}
