package com.googlecode.kevinarpe.papaya.testing.logging;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface LoggingEventAnalyzerFactory
    <
        TLoggingEvent,
        TLoggingEventAttribute extends ILoggingEventAttribute<TLoggingEvent>
    >
{
    LoggingEventAnalyzer<TLoggingEvent, TLoggingEventAttribute>
    newInstance(List<TLoggingEvent> loggingEventList);
}
