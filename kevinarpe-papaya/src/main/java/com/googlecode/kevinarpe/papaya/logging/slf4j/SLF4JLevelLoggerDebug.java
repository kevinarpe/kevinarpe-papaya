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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@FullyTested
final class SLF4JLevelLoggerDebug
implements SLF4JLevelLogger {

    private final Logger _logger;

    public SLF4JLevelLoggerDebug(Logger logger) {
        _logger = ObjectArgs.checkNotNull(logger, "logger");
    }

    /**
     * @return {@link SLF4JLogLevel#DEBUG}
     */
    @Override
    public SLF4JLogLevel getLogLevel() {
        return SLF4JLogLevel.DEBUG;
    }

    /**
     * Calls {@link Logger#isDebugEnabled()}.
     */
    @Override
    public boolean isEnabled() {
        boolean b = _logger.isDebugEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#debug(String)}.
     */
    @Override
    public void log(String msg) {
        _logger.debug(msg);
    }

    /**
     * Calls {@link Logger#debug(String, Object)}.
     */
    @Override
    public void log(String format, Object arg) {
        _logger.debug(format, arg);
    }

    /**
     * Calls {@link Logger#debug(String, Object, Object)}.
     */
    @Override
    public void log(String format, Object arg1, Object arg2) {
        _logger.debug(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object...)}.
     */
    @Override
    public void log(String format, Object... argArr) {
        _logger.debug(format, argArr);
    }

    /**
     * Calls {@link Logger#debug(String, Throwable)}.
     */
    @Override
    public void log(String msg, Throwable t) {
        _logger.debug(msg, t);
    }

    /**
     * Calls {@link Logger#isDebugEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public boolean isEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = _logger.isDebugEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#debug(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.debug(marker, msg);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.debug(marker, format, arg);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.debug(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.debug(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#debug(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.debug(marker, msg, t);
    }
}
