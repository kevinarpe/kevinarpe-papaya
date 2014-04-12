package com.googlecode.kevinarpe.papaya.logging.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLoggingEventFactoryUtilsTest {

    private SLF4JLoggingEventFactory mockSLF4JLoggingEventFactory;
    private SLF4JLoggingEvent mockSLF4JLoggingEvent;
    private Logger mockLogger;
    private Marker mockMarker;
    private Throwable mockThrowable;

    private SLF4JLoggingEventFactoryUtils classUnderTest;

    @BeforeMethod
    public void beforeEachTestMethod() {
        mockSLF4JLoggingEventFactory = mock(SLF4JLoggingEventFactory.class);
        mockSLF4JLoggingEvent = mock(SLF4JLoggingEvent.class);
        mockLogger = mock(Logger.class);
        mockMarker = mock(Marker.class);
        mockThrowable = mock(Throwable.class);
        classUnderTest = new SLF4JLoggingEventFactoryUtils();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance1(String)
    //

    @Test
    public void newInstance1_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                SLF4JLoggingEventFactoryUtils.EMPTY_FORMAT_ARG_ARR,
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory, mockLogger, SLF4JLogLevel.INFO, mockMarker, "message");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance2(String, Object)
    //

    @Test
    public void newInstance2_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance3(String, Object, Object)
    //

    @Test
    public void newInstance3_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg", "formatArg2" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg",
                "formatArg2");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance4(String, Object...)
    //

    @Test
    public void newInstance4_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                new Object[] { "formatArg", "formatArg2", "formatArg3" },
                (Throwable) null))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                "formatArg",
                "formatArg2",
                "formatArg3");
        assertSame(event, mockSLF4JLoggingEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SLF4JLoggingEventFactoryUtils.newInstance5(String, Throwable)
    //

    @Test
    public void newInstance5_Pass() {
        when(
            mockSLF4JLoggingEventFactory.newInstance(
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                SLF4JLoggingEventFactoryUtils.EMPTY_FORMAT_ARG_ARR,
                mockThrowable))
            .thenReturn(mockSLF4JLoggingEvent);
        SLF4JLoggingEvent event =
            classUnderTest.newInstance(
                mockSLF4JLoggingEventFactory,
                mockLogger,
                SLF4JLogLevel.INFO,
                mockMarker,
                "message",
                mockThrowable);
        assertSame(event, mockSLF4JLoggingEvent);
    }
}
