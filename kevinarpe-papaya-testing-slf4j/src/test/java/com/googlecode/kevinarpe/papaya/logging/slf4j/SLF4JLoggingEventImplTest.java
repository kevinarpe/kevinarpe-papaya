package com.googlecode.kevinarpe.papaya.logging.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventImplTest {

    private Logger mockLogger;
    private Marker mockMarker;
    private Throwable mockThrowable;

    private SLF4JLoggingEventImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        mockThrowable = mock(Throwable.class);
        classUnderTest =
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.TRACE,
                mockMarker,
                "message arg1: {}, arg2: {}",
                new Object[] { "xyz", "abc" },
                (Throwable) null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Helpers
    //

    public static void assertLoggingEventEquals(
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
        assertEquals(loggingEvent.getThreadName(), Thread.currentThread().getName());
        assertTrue(loggingEvent.getTimeStamp() <= System.currentTimeMillis());

        assertEquals(SLF4JLoggingEventAttribute.LOGGER.getValue(loggingEvent), logger);
        assertEquals(SLF4JLoggingEventAttribute.LEVEL.getValue(loggingEvent), logLevel);
        assertEquals(SLF4JLoggingEventAttribute.MARKER.getValue(loggingEvent), marker);
        assertEquals(SLF4JLoggingEventAttribute.MESSAGE.getValue(loggingEvent), message);
        assertEquals(
            SLF4JLoggingEventAttribute.MESSAGE_FORMAT_ARG_ARR.getValue(loggingEvent),
            optionalFormatArgArr);
        assertEquals(SLF4JLoggingEventAttribute.THROWABLE.getValue(loggingEvent), optionalThrowable);
        assertEquals(
            SLF4JLoggingEventAttribute.THREAD_NAME.getValue(loggingEvent),
            Thread.currentThread().getName());
        assertTrue(
            (Long) SLF4JLoggingEventAttribute.TIME_STAMP.getValue(loggingEvent) <=
                System.currentTimeMillis());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventImpl.ctor()
    //

    @DataProvider
    private static Object[][] _ctor_Pass_Data() {
        Logger mockLogger2 = mock(Logger.class);
        Marker mockMarker2 = mock(Marker.class);
        Throwable mockThrowable2 = mock(Throwable.class);
        return new Object[][] {
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { "a", "b", "c" },
                mockThrowable2,

                new Object[] { "a", "b", "c" },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { "a", "b", "c", mockThrowable2 },
                (Throwable) null,

                new Object[] { "a", "b", "c" },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { "a", "b", "c", mockThrowable2 },
                mockThrowable2,

                new Object[] { "a", "b", "c", mockThrowable2 },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { },
                mockThrowable2,

                new Object[] { },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { mockThrowable2 },
                mockThrowable2,

                new Object[] { mockThrowable2 },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { null },
                mockThrowable2,

                new Object[] { null },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                new Object[] { mockThrowable2 },
                (Throwable) null,

                new Object[] { },
                mockThrowable2,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                (Object[]) null,
                (Throwable) null,

                new Object[] { },
                (Throwable) null,
            },
            {
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker2,
                "message",
                (Object[]) null,
                mockThrowable2,

                new Object[] { },
                mockThrowable2,
            },
        };
    }

    @Test(dataProvider = "_ctor_Pass_Data")
    public void ctor_Pass(
            Logger logger,
            SLF4JLogLevel logLevel,
            Marker marker,
            String message,
            Object[] optionalFormatArgArr,
            Throwable optionalThrowable,
            Object[] expectedOptionalFormatArgArr,
            Throwable expectedOptionalThrowable) {
        SLF4JLoggingEventImpl event =
            new SLF4JLoggingEventImpl(
                logger, logLevel, marker, message, optionalFormatArgArr, optionalThrowable);
        assertLoggingEventEquals(
            event,
            logger,
            logLevel,
            marker,
            message,
            expectedOptionalFormatArgArr,
            expectedOptionalThrowable);
    }

    @DataProvider
    private static Object[][] _ctor_FailWithNull_Data() {
        Logger mockLogger2 = mock(Logger.class);
        Marker mockMarker2 = mock(Marker.class);
        return new Object[][] {
            { (Logger) null, SLF4JLogLevel.ERROR, mockMarker2, "message" },
            { mockLogger2, (SLF4JLogLevel) null, mockMarker2, "message" },
            { mockLogger2, SLF4JLogLevel.ERROR, (Marker) null, "message" },
            { mockLogger2, SLF4JLogLevel.ERROR, mockMarker2, (String) null },
        };
    }

    @Test(dataProvider = "_ctor_FailWithNull_Data",
            expectedExceptions = NullPointerException.class)
    public void ctor_FailWithNull(
            Logger logger, SLF4JLogLevel logLevel, Marker marker, String message) {
        new SLF4JLoggingEventImpl(
            logger,
            logLevel,
            marker,
            message,
            new Object[] { "a", "b", "c" },
            mockThrowable);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventImpl.getFormattedMessage()
    //

    @Test
    public void getFormattedMessage_Pass() {
        assertEquals(classUnderTest.getFormattedMessage(), "message arg1: xyz, arg2: abc");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventImpl.getAttributeValue()
    //

    @Test(expectedExceptions = NullPointerException.class)
    public void getAttributeValue_FailWithNull() {
        classUnderTest.getAttributeValue((ISLF4JLoggingEventAttribute) null);
    }

    @Test(expectedExceptions = ClassCastException.class)
    public void getAttributeValue_FailWithClassCastException() {
        Integer x = classUnderTest.getAttributeValue(SLF4JLoggingEventAttribute.MESSAGE);
    }
}
