package com.googlecode.kevinarpe.papaya.testing.log4j;

import com.google.common.collect.ImmutableList;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzer;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see <a href="http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/"
 *      >http://slackhacker.com/2009/12/08/testing-logging-behaviour-in-four-code-lines-flat/</a>
 */
@FullyTested
public class Log4JTestBase {

    private final LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute>
        _loggingEventAnalyzerFactory;

    private final Appender _mockAppender;

    protected Log4JTestBase() {
        this(Log4JLoggingEventAnalyzerFactoryImpl.INSTANCE);
    }

    protected Log4JTestBase(
            LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute> factory) {
        _loggingEventAnalyzerFactory = ObjectArgs.checkNotNull(factory, "factory");
        _mockAppender = mock(Appender.class);
    }

    protected final void addMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.addAppender(_mockAppender);
    }

    protected final void removeMockAppender() {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.removeAppender(_mockAppender);
    }

    /**
     * @see #newLoggingEventAnalyzer()
     */
    public final List<LoggingEvent> getLoggingEventList() {
        ArgumentCaptor<LoggingEvent> argumentCaptor = ArgumentCaptor.forClass(LoggingEvent.class);
        List<LoggingEvent> loggingEventList = null;
        try {
            verify(_mockAppender, atLeastOnce()).doAppend(argumentCaptor.capture());
            loggingEventList = argumentCaptor.getAllValues();
        }
        catch (Throwable ignore) {
            loggingEventList = ImmutableList.of();
        }
        return loggingEventList;
    }

    public final LoggingEventAnalyzer<LoggingEvent, Log4JLoggingEventAttribute>
    newLoggingEventAnalyzer() {
        List<LoggingEvent> loggingEventList = getLoggingEventList();
        LoggingEventAnalyzer<LoggingEvent, Log4JLoggingEventAttribute> x =
            _loggingEventAnalyzerFactory.newInstance(loggingEventList);
        return x;
    }
}
