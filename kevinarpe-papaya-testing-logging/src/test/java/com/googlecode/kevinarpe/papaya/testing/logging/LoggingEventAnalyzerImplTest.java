package com.googlecode.kevinarpe.papaya.testing.logging;

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
