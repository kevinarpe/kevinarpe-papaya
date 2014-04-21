package com.googlecode.kevinarpe.papaya.testing.log4j;

import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerFactory;
import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerImpl;
import org.apache.log4j.spi.LoggingEvent;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class Log4JLoggingEventAnalyzerFactoryImpl
extends StatelessObject
implements LoggingEventAnalyzerFactory<LoggingEvent, Log4JLoggingEventAttribute> {

    public static final Log4JLoggingEventAnalyzerFactoryImpl INSTANCE =
        new Log4JLoggingEventAnalyzerFactoryImpl();

    @Override
    public LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute>
    newInstance(List<LoggingEvent> loggingEventList) {
        LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute> x =
            new LoggingEventAnalyzerImpl<LoggingEvent, Log4JLoggingEventAttribute>(loggingEventList);
        return x;
    }
}
