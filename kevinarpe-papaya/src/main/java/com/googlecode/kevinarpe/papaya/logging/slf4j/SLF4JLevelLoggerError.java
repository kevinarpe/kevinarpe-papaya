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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@FullyTested
final class SLF4JLevelLoggerError
implements SLF4JLevelLogger {

    private final Logger _logger;

    public SLF4JLevelLoggerError(Logger logger) {
        _logger = ObjectArgs.checkNotNull(logger, "logger");
    }

    /**
     * @return {@link SLF4JLogLevel#ERROR}
     */
    @Override
    public SLF4JLogLevel getLogLevel() {
        return SLF4JLogLevel.ERROR;
    }

    /**
     * Calls {@link Logger#isErrorEnabled()}.
     */
    @Override
    public boolean isEnabled() {
        boolean b = _logger.isErrorEnabled();
        return b;
    }

    /**
     * Calls {@link Logger#error(String)}.
     */
    @Override
    public void log(String msg) {
        _logger.error(msg);
    }

    /**
     * Calls {@link Logger#error(String, Object)}.
     */
    @Override
    public void log(String format, Object arg) {
        _logger.error(format, arg);
    }

    /**
     * Calls {@link Logger#error(String, Object, Object)}.
     */
    @Override
    public void log(String format, Object arg1, Object arg2) {
        _logger.error(format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#error(Marker, String, Object...)}.
     */
    @Override
    public void log(String format, Object... argArr) {
        _logger.error(format, argArr);
    }

    /**
     * Calls {@link Logger#error(String, Throwable)}.
     */
    @Override
    public void log(String msg, Throwable t) {
        _logger.error(msg, t);
    }

    /**
     * Calls {@link Logger#isErrorEnabled(Marker)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public boolean isEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        boolean b = _logger.isErrorEnabled(marker);
        return b;
    }

    /**
     * Calls {@link Logger#error(Marker, String)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.error(marker, msg);
    }

    /**
     * Calls {@link Logger#error(Marker, String, Object)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.error(marker, format, arg);
    }

    /**
     * Calls {@link Logger#error(Marker, String, Object, Object)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.error(marker, format, arg1, arg2);
    }

    /**
     * Calls {@link Logger#error(Marker, String, Object...)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.error(marker, format, argArr);
    }

    /**
     * Calls {@link Logger#error(Marker, String, Throwable)}.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");

        _logger.error(marker, msg, t);
    }
}
