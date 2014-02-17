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
import org.slf4j.LoggerFactory;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class SLF4JLevelLoggers {

    public static SLF4JLevelLogger newInstance(SLF4JLogLevel logLevel, Class<?> clazz) {
        ObjectArgs.checkNotNull(logLevel, "logLevel");
        ObjectArgs.checkNotNull(clazz, "clazz");

        if (SLF4JLogLevel.OFF == logLevel) {
            return SLF4JLevelLoggerOff.INSTANCE;
        }
        Logger logger = LoggerFactory.getLogger(clazz);
        SLF4JLevelLogger levelLogger = logLevel.newLevelLogger(logger);
        return levelLogger;
    }
}
