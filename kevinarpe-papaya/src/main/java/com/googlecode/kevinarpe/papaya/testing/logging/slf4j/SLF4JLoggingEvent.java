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

import com.googlecode.kevinarpe.papaya.logging.slf4j.SLF4JLogLevel;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * Acts as a generic interface for SLF4J logging events.  Unlike Log4J's LoggingEvent, SLF4J did not
 * expose a public interface for logging events.  This interface attempts to fill the hole.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see SLF4JLoggingEventAttribute#getValue(Object)
 */
public interface SLF4JLoggingEvent {

    /**
     * Retrieves the logger associated with this logging event.
     *
     * @return SLF4J logger
     */
    Logger getLogger();

    /**
     * Retrieves the log level associated with this logging event.
     *
     * @return SLF4J log level
     */
    SLF4JLogLevel getLevel();

    /**
     * Retrieves the SLF4J marker (if any) associated with this logging event.
     *
     * @return SLF4J marker
     */
    Marker getMarker();

    /**
     * Retrieves the message (or message format) associated with this logging event.
     *
     * @return message (or message format)
     *
     * @see #getMessageFormatArgArr()
     * @see #getFormattedMessage()
     */
    String getMessage();

    /**
     * Retrieves the arguments for the message format associated with this logging event.
     *
     * @return arguments for the message format
     *
     * @see #getMessage()
     * @see #getFormattedMessage()
     */
    Object[] getMessageFormatArgArr();

    /**
     * Retrieves the exception (or error) associated with this logging event.
     *
     * @return exception (or error)
     */
    Throwable getThrowable();

    /**
     * Retrieves the name of the thread associated with this logging event.
     *
     * @return name of thread
     */
    String getThreadName();

    /**
     * Retrieves the time (number of milliseconds since epoch @ UTC) associated with this
     * logging event.
     *
     * @return time of logging event
     *
     * @see System#currentTimeMillis()
     */
    long getTimeStamp();

    /**
     * Retrieves the formatted message built from the message format and arguments.
     *
     * @return formatted message
     *
     * @see #getMessage()
     * @see #getMessageFormatArgArr()
     */
    String getFormattedMessage();

    /**
     * Retrieves a value for a logging event attribute.
     *
     * @param attribute
     *        attribute to retrieve its value
     * @param <T>
     *        type of attribute value
     *
     * @return value for attribute
     *
     * @throws ClassCastException
     *         if {@code T} is unreasonable
     *
     * @see ISLF4JLoggingEventAttribute#getValueClass()
     */
    <T> T getAttributeValue(ISLF4JLoggingEventAttribute attribute);
}
