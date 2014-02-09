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
public final class SLF4JLevelLoggerImpl
implements SLF4JLevelLogger {

    private final Logger _optLogger;
    private final SLF4JLogLevel _logLevel;

    public SLF4JLevelLoggerImpl(Logger optLogger, SLF4JLogLevel logLevel) {
        //_optLogger = ObjectArgs.checkNotNull(optLogger, "optLogger");
        _optLogger = optLogger;
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
    }

    @Override
    public boolean isEnabled() {
        boolean b = _logLevel.isEnabled(_optLogger);
        return b;
    }

    @Override
    public void log(String msg) {
        _logLevel.log(_optLogger, msg);
    }

    @Override
    public void log(String format, Object arg) {
        _logLevel.log(_optLogger, format, arg);
    }

    @Override
    public void log(String format, Object arg1, Object arg2) {
        _logLevel.log(_optLogger, format, arg1, arg2);
    }

    @Override
    public void log(String format, Object... argArr) {
        _logLevel.log(_optLogger, format, argArr);
    }

    @Override
    public void log(String msg, Throwable t) {
        _logLevel.log(_optLogger, msg, t);
    }

    @Override
    public boolean isEnabled(Marker marker) {
        boolean b = _logLevel.isEnabled(_optLogger, marker);
        return b;
    }

    @Override
    public void log(Marker marker, String msg) {
        _logLevel.log(_optLogger, msg);
    }

    @Override
    public void log(Marker marker, String format, Object arg) {
        _logLevel.log(_optLogger, format, arg);
    }

    @Override
    public void log(Marker marker, String format, Object arg1, Object arg2) {
        _logLevel.log(_optLogger, format, arg1, arg2);
    }

    @Override
    public void log(Marker marker, String format, Object... argArr) {
        _logLevel.log(_optLogger, format, argArr);
    }

    @Override
    public void log(Marker marker, String msg, Throwable t) {
        _logLevel.log(_optLogger, msg, t);
    }
}
