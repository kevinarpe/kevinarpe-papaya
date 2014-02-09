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

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
* @author Kevin Connor ARPE (kevinarpe@gmail.com)
*/
// TODO: Really need to expose this class?  Probably not.  And all others like it.
// Package-private is OK.
final class SLF4JLogLevelOff
//implements SLF4JLogLevel {
extends AbstractSLF4JLogLevel {

//    public static final SLF4JLogLevelEnum ENUM = SLF4JLogLevelEnum.OFF;

    public SLF4JLogLevelOff() {
        super("OFF");
    }

    /**
     * @return always {@code false}
     */
    @Override
    public boolean isEnabled(Logger logger) {
        return false;
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String msg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object arg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object arg1, Object arg2) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String format, Object... argArr) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, String msg, Throwable t) {
    }

    /**
     * @return always {@code false}
     */
    @Override
    public boolean isEnabled(Logger logger, Marker marker) {
        return false;
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String msg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object arg1, Object arg2) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String format, Object... argArr) {
    }

    /**
     * Does nothing
     */
    @Override
    public void log(Logger logger, Marker marker, String msg, Throwable t) {
    }
}
