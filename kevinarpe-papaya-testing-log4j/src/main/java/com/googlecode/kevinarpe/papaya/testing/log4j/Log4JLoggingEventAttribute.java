package com.googlecode.kevinarpe.papaya.testing.log4j;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.testing.logging.ILoggingEventAttribute;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public enum Log4JLoggingEventAttribute
implements ILoggingEventAttribute<LoggingEvent> {

    LOGGER(Logger.class) {
        @Override
        public Logger getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Logger x = (Logger) loggingEvent.getLogger();
            return x;
        }
    },
    LEVEL(Level.class) {
        @Override
        public Level getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Level x = loggingEvent.getLevel();
            return x;
        }
    },
    MESSAGE(String.class) {
        @Override
        public Object getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Object x = loggingEvent.getMessage();
            return x;
        }
    },
    THROWABLE(Throwable.class) {
        @Override
        public Throwable getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            ThrowableInformation ti = loggingEvent.getThrowableInformation();
            Throwable x = (null == ti ? null : ti.getThrowable());
            return x;
        }
    },
    THREAD_NAME(String.class) {
        @Override
        public String getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            String x = loggingEvent.getThreadName();
            return x;
        }
    },
    TIME_STAMP(Long.class) {
        @Override
        public Long getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Long x = loggingEvent.getTimeStamp();
            return x;
        }
    },
    ;

    private final Class<?> _valueClass;

    private Log4JLoggingEventAttribute(Class<?> valueClass) {
        _valueClass = ObjectArgs.checkNotNull(valueClass, "valueClass");
    }

    @Override
    public Class<?> getValueClass() {
        return _valueClass;
    }
}
