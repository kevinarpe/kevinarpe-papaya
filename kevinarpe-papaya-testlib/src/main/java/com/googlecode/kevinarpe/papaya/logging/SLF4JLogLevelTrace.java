package com.googlecode.kevinarpe.papaya.logging;

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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
final class SLF4JLogLevelTrace
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.TRACE;

    public SLF4JLogLevelTrace() {
        super("TRACE");
    }

    /**
     * Calls {@link Logger#isTraceEnabled()}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        ObjectArgs.checkNotNull(logger, "logger");

        boolean b = logger.isTraceEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#trace(String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(msg);
    }

    /**
     * Calls {@link Logger#trace(String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, arg);
    }

    /**
     * Calls {@link Logger#trace(String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(format, argArr);
    }

    /**
     * Calls {@link Logger#trace(String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");

        logger.trace(msg, t);
    }

    /**
     * Calls {@link Logger#isTraceEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = logger.isTraceEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#trace(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, msg);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, arg);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#trace(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code logger} or {@code marker} is {@code null}
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(logger, "logger");
        ObjectArgs.checkNotNull(marker, "marker");

        logger.trace(msg, t);
    }
}
