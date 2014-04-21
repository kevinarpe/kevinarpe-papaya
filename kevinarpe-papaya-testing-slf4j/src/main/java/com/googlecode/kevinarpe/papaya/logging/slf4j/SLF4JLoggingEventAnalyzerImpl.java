package com.googlecode.kevinarpe.papaya.logging.slf4j;

import com.googlecode.kevinarpe.papaya.testing.logging.LoggingEventAnalyzerImpl;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class SLF4JLoggingEventAnalyzerImpl
extends LoggingEventAnalyzerImpl<SLF4JLoggingEvent, SLF4JLoggingEventAttribute> {

    public SLF4JLoggingEventAnalyzerImpl(List<SLF4JLoggingEvent> loggingEventList) {
        super(loggingEventList);
    }
}
