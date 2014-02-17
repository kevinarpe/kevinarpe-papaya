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

import org.slf4j.Marker;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface SLF4JLevelLogger {

    SLF4JLogLevel getLogLevel();

    boolean isEnabled();
    void log(String msg);
    void log(String format, Object arg);
    void log(String format, Object arg1, Object arg2);
    void log(String format, Object... argArr);
    void log(String msg, Throwable t);

    boolean isEnabled(Marker marker);
    void log(Marker marker, String msg);
    void log(Marker marker, String format, Object arg);
    void log(Marker marker, String format, Object arg1, Object arg2);
    void log(Marker marker, String format, Object... argArr);
    void log(Marker marker, String msg, Throwable t);
}
