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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Enabling this class causing NPE on JVM start-up.  Do we REALLY need this class?
// If no, delete it, and AbstractSLF4JLogLevel
public enum SLF4JLogLevelEnum {

    OFF(SLF4JLogLevel.OFF),
    ERROR(SLF4JLogLevel.ERROR),
    WARN(SLF4JLogLevel.WARN),
    INFO(SLF4JLogLevel.INFO),
    DEBUG(SLF4JLogLevel.DEBUG),
    TRACE(SLF4JLogLevel.TRACE),
    ;

    private final SLF4JLogLevel _logLevel;

    private SLF4JLogLevelEnum(SLF4JLogLevel logLevel) {
        _logLevel = ObjectArgs.checkNotNull(logLevel, "logLevel");
    }

    public SLF4JLogLevel getLogLevel() {
        return _logLevel;
    }
}
