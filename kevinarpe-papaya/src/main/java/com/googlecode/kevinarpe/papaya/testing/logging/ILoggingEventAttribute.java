package com.googlecode.kevinarpe.papaya.testing.logging;

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

/**
 * Attribute for a logging event, e.g., thread name, time stamp, or logger.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ILoggingEventAttribute<TLoggingEvent> {

    /**
     * Retrieves the attribute value for a logging event.
     *
     * @param loggingEvent
     *        object to retrieve an attribute value, e.g., thread name, time stamp, or logger
     *
     * @return logging event attribute value
     *
     * @throws NullPointerException
     *         if {@code loggingEvent} is {@code null}
     */
    Object getValue(TLoggingEvent loggingEvent);

    /**
     * @return type for this logging event attribute
     */
    Class<?> getValueClass();
}
