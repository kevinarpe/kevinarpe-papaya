package com.googlecode.kevinarpe.papaya.logging.slf4j;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ISLF4JLoggingEventAttribute {

    Object getValue(SLF4JLoggingEvent loggingEvent);

    Class<?> getValueClass();
}
