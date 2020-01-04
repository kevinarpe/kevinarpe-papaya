package com.googlecode.kevinarpe.papaya.testing.logging.log4j;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.testing.logging.ILoggingEventAttribute;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * Attributes for a Log4J logging event.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ILoggingEventAttribute
 * @see LoggingEvent
 */
@FullyTested
public enum Log4JLoggingEventAttribute
implements ILoggingEventAttribute<LoggingEvent> {

    /**
     * Corresponds to {@link LoggingEvent#getLogger()}
     */
    LOGGER(Logger.class) {
        @Override
        public Logger getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Logger x = (Logger) loggingEvent.getLogger();
            return x;
        }
    },

    /**
     * Corresponds to {@link LoggingEvent#getLevel()}
     */
    LEVEL(Level.class) {
        @Override
        public Level getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Level x = loggingEvent.getLevel();
            return x;
        }
    },

    /**
     * Corresponds to {@link LoggingEvent#getMessage()}
     */
    MESSAGE(String.class) {
        @Override
        public Object getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Object x = loggingEvent.getMessage();
            return x;
        }
    },

    /**
     * Corresponds to {@link LoggingEvent#getThrowableInformation()}, followed by
     * {@link ThrowableInformation#getThrowable()}
     */
    THROWABLE(Throwable.class) {
        @Override
        public Throwable getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            ThrowableInformation ti = loggingEvent.getThrowableInformation();
            Throwable x = (null == ti ? null : ti.getThrowable());
            return x;
        }
    },

    /**
     * Corresponds to {@link LoggingEvent#getThreadName()}
     */
    THREAD_NAME(String.class) {
        @Override
        public String getValue(LoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            String x = loggingEvent.getThreadName();
            return x;
        }
    },

    /**
     * Corresponds to {@link LoggingEvent#getTimeStamp()}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getValueClass() {
        return _valueClass;
    }
}
