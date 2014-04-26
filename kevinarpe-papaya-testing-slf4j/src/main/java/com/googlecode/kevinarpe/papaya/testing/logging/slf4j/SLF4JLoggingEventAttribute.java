package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2014 Kevin Connor ARPE (kevinarpe@gmail.com)
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
import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Attributes for a SLF4J logging event.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ISLF4JLoggingEventAttribute
 * @see SLF4JLoggingEvent
 */
@FullyTested
public enum SLF4JLoggingEventAttribute
implements ISLF4JLoggingEventAttribute {

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getLogger()}
     */
    LOGGER(Logger.class) {
        @Override
        public Logger getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Logger x = loggingEvent.getLogger();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getLevel()}
     */
    LEVEL(SLF4JLogLevel.class) {
        @Override
        public SLF4JLogLevel getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            SLF4JLogLevel x = loggingEvent.getLevel();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getMarker()}
     */
    MARKER(Marker.class) {
        @Override
        public Marker getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Marker x = loggingEvent.getMarker();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getMessage()}
     */
    MESSAGE(String.class) {
        @Override
        public String getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            String x = loggingEvent.getMessage();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getMessageFormatArgArr()}
     */
    MESSAGE_FORMAT_ARG_ARR(Object[].class) {
        @Override
        public Object[] getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Object[] x = loggingEvent.getMessageFormatArgArr();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getThrowable()}
     */
    THROWABLE(Throwable.class) {
        @Override
        public Throwable getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Throwable x = loggingEvent.getThrowable();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getThreadName()}
     */
    THREAD_NAME(String.class) {
        @Override
        public String getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            String x = loggingEvent.getThreadName();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getTimeStamp()}
     */
    TIME_STAMP(Long.class) {
        @Override
        public Long getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            Long x = loggingEvent.getTimeStamp();
            return x;
        }
    },

    /**
     * Corresponds to {@link SLF4JLoggingEvent#getFormattedMessage()}
     */
    FORMATTED_MESSAGE(String.class) {
        @Override
        public String getValue(SLF4JLoggingEvent loggingEvent) {
            ObjectArgs.checkNotNull(loggingEvent, "loggingEvent");

            String x = loggingEvent.getFormattedMessage();
            return x;
        }
    },
    ;

    private final Class<?> _valueClass;

    private SLF4JLoggingEventAttribute(Class<?> valueClass) {
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
