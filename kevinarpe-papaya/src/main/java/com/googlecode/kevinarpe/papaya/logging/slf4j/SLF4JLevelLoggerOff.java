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
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
@FullyTested
final class SLF4JLevelLoggerOff
implements SLF4JLevelLogger {

    public static final SLF4JLevelLoggerOff INSTANCE = new SLF4JLevelLoggerOff();

    public SLF4JLevelLoggerOff() {
    }

    /**
     * @return {@link SLF4JLogLevel#OFF}
     */
    @Override
    public SLF4JLogLevel getLogLevel() {
        return SLF4JLogLevel.OFF;
    }

    /**
     * Always returns {@code false}.
     */
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * Does nothing.
     */
    @Override
    public void log(String msg) {
        int dummy = 0;  // debug breakpoint
    }

    /**
     * Does nothing.
     */
    @Override
    public void log(String format, Object arg) {
        int dummy = 0;  // debug breakpoint
    }

    /**
     * Does nothing.
     */
    @Override
    public void log(String format, Object arg1, Object arg2) {
        int dummy = 0;  // debug breakpoint
    }

    /**
     * Does nothing.
     */
    @Override
    public void log(String format, Object... argArr) {
        int dummy = 0;  // debug breakpoint
    }

    /**
     * Does nothing.
     */
    @Override
    public void log(String msg, Throwable t) {
        int dummy = 0;  // debug breakpoint
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public boolean isEnabled(Marker marker) {
        ObjectArgs.checkNotNull(marker, "marker");

        return false;
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg) {
        ObjectArgs.checkNotNull(marker, "marker");
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg) {
        ObjectArgs.checkNotNull(marker, "marker");
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object arg1, Object arg2) {
        ObjectArgs.checkNotNull(marker, "marker");
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String format, Object... argArr) {
        ObjectArgs.checkNotNull(marker, "marker");
    }

    /**
     * Does nothing.
     *
     * @throws NullPointerException
     *         if {@code marker} is {@code null}
     */
    @Override
    public void log(Marker marker, String msg, Throwable t) {
        ObjectArgs.checkNotNull(marker, "marker");
    }
}
