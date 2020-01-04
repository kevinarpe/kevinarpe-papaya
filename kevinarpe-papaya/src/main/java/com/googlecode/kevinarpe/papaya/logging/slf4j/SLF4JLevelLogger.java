package com.googlecode.kevinarpe.papaya.logging.slf4j;

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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * A logger that can only log at a single level.  The {@link Logger} interface provide for five
 * types of logging: TRACE, DEBUG, INFO, WARN, ERROR.  This interface is effectively one fifth of
 * the original.
 *
 * To create instances that implement this inteface, see {@link SLF4JLevelLoggerUtils}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SLF4JLevelLogger {

    /**
     * @return single logging level for this logger
     */
    SLF4JLogLevel getLogLevel();

    /**
     * Tests if this logger is enabled and logging events are processed.
     *
     * @return {@code true} if enabled, else {@code false}
     *
     * @see #isEnabled(Marker)
     */
    boolean isEnabled();

    /**
     * @see Logger#info(String)
     * @see #log(Marker, String)
     */
    void log(String msg);

    /**
     * @see Logger#info(String, Object)
     * @see #log(Marker, String, Object)
     */
    void log(String format, Object arg);

    /**
     * @see Logger#info(String, Object)
     * @see #log(Marker, String, Object)
     */
    void log(String format, Object arg1, Object arg2);

    /**
     * @see Logger#info(String, Object)
     * @see #log(Marker, String, Object)
     */
    void log(String format, Object... argArr);

    /**
     * @see Logger#info(String, Object)
     * @see #log(Marker, String, Object)
     */
    void log(String msg, Throwable t);

    /**
     * Tests if this logger is enabled for a particular marker and logging events are processed.
     *
     * @return {@code true} if enabled, else {@code false}
     *
     * @see #isEnabled()
     */
    boolean isEnabled(Marker marker);

    /**
     * @see Logger#info(Marker, String)
     * @see #log(String)
     */
    void log(Marker marker, String msg);

    /**
     * @see Logger#info(Marker, String, Object)
     * @see #log(String, Object)
     */
    void log(Marker marker, String format, Object arg);

    /**
     * @see Logger#info(Marker, String, Object)
     * @see #log(String, Object)
     */
    void log(Marker marker, String format, Object arg1, Object arg2);

    /**
     * @see Logger#info(Marker, String, Object)
     * @see #log(String, Object)
     */
    void log(Marker marker, String format, Object... argArr);

    /**
     * @see Logger#info(Marker, String, Object)
     * @see #log(String, Object)
     */
    void log(Marker marker, String msg, Throwable t);
}
