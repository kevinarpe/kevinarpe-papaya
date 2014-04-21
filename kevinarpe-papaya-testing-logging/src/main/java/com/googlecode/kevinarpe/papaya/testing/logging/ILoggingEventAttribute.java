package com.googlecode.kevinarpe.papaya.testing.logging;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ILoggingEventAttribute<TLoggingEvent> {

    Object getValue(TLoggingEvent loggingEvent);

    Class<?> getValueClass();
}
