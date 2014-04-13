package com.googlecode.kevinarpe.papaya.logging.slf4j;

import com.google.common.testing.EqualsTester;
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
    private Logger mockLogger2;
    private Marker mockMarker;
    private Marker mockMarker2;
    private Throwable throwable;
    private Throwable throwable2;

    private SLF4JLoggingEventImpl classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockLogger = mock(Logger.class);
        mockLogger2 = mock(Logger.class);
        mockMarker = mock(Marker.class);
        mockMarker2 = mock(Marker.class);
        throwable = new Exception("message");
            //mock(Throwable.class);
        throwable2 = new Exception("message2");
            //mock(Throwable.class);
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

    private static class SystemImpl
        implements SLF4JLoggingEventImpl.ISystem {

        private final String _currentThreadName;
        private final long _currentTimeMillis;

        private SystemImpl(String currentThreadName, long currentTimeMillis) {
            _currentThreadName = currentThreadName;
            _currentTimeMillis = currentTimeMillis;
        }

        @Override
        public String getCurrentThreadName() {
            return _currentThreadName;
        }

        @Override
        public long currentTimeMillis() {
            return _currentTimeMillis;
        }
    }

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
            throwable);
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventImpl.hashCode()/equals()
    //

    @Test
    public void hashCodeAndEquals_Pass() {
        EqualsTester equalsTester = new EqualsTester();

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger2,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.TRACE,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.TRACE,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.TRACE,
                mockMarker2,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.TRACE,
                mockMarker2,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message2",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message2",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg2" },
                throwable,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg2" },
                throwable,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable2,
                new SystemImpl("threadName", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable2,
                new SystemImpl("threadName", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName2", 1234)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName2", 1234)));

        equalsTester.addEqualityGroup(
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 12345)),
            new SLF4JLoggingEventImpl(
                mockLogger,
                SLF4JLogLevel.ERROR,
                mockMarker,
                "message",
                new Object[] { "arg" },
                throwable,
                new SystemImpl("threadName", 12345)));

        equalsTester.testEquals();
    }
}
