package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Move me somewhere else (up).
// TODO: Use this interface and some heavy mocking to allow full mocking on SLF4J.
public interface SLF4JLoggingEvent {

    Logger getLogger();
    SLF4JLogLevel getLevel();
    Marker getMarker();
    String getMessage();
    Object[] getMessageFormatArgArr();
    // TODO: LAST: Maybe replace with ThrowbleProxy?
    // TODO: If we use ThrowbleProxy, can we throw a synthetic Throwable(Impl)?
    // Maybe no need, as we will have the original?
    // Can we safely mock Throwable?  If so, whatev this proxy stuff.  R&D, please.
    Throwable getThrowable();
    String getThreadName();
    long getTimeStamp();
    // TODO: How to format the message?
    String getFormattedMessage();
//        ch.qos.logback.classic.Logger
}
