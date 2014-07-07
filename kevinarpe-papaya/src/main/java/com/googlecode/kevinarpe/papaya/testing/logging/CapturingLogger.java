package com.googlecode.kevinarpe.papaya.testing.logging;

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

import java.util.List;

/**
 * Provides access to logging events.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface CapturingLogger<TLoggingEvent> {

    /**
     * Retrieves a list of logging events.
     *
     * To perform analysis (for testing), try these strategies:
     * <pre>{@code
     * List&lt;LoggingEvent> filteredLoggingEventList =
     *     Collections2.filter(
     *         getLoggingEventList(),
     *         new AnyLoggingEventAttributeValuePredicate&lt;LoggingEvent>(
     *             Log4JLoggingEventAttribute.LEVEL,
     *             Level.DEBUG, Level.TRACE));
     * }</pre>
     *
     * Or:
     * <pre>{@code
     * List&lt;LoggingEvent> filteredLoggingEventList =
     *     Collections2.filter(
     *         getLoggingEventList(),
     *         Predicates.not(
     *             new AnyLoggingEventAttributeValuePredicate&lt;LoggingEvent>(
     *                 Log4JLoggingEventAttribute.LEVEL,
     *                 Level.ERROR, Level.FATAL));
     * }</pre>
     *
     * @return list of logging events
     */
    public List<TLoggingEvent> getLoggingEventList();
}
