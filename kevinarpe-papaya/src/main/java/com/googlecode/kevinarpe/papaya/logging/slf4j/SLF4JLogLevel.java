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

import org.slf4j.Logger;

/**
 * Represents all the well-known logging levels in SLF4J.
 *
 * Create new loggers with this method: {@link #newLevelLogger(Logger)}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see SLF4JLevelLogger
 */
public enum SLF4JLogLevel {

    /**
     * Special case: Does not have an underlying logger and all methods do nothing.
     */
    OFF {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            return SLF4JLevelLoggerOff.INSTANCE;
        }
    },
    /**
     * All logging methods are sent to ERROR level.
     *
     * @see Logger#error(String)
     */
    ERROR {
        @Override
        public SLF4JLevelLoggerError newLevelLogger(Logger logger) {
            SLF4JLevelLoggerError x = new SLF4JLevelLoggerError(logger);
            return x;
        }
    },
    /**
     * All logging methods are sent to WARN level.
     *
     * @see Logger#warn(String)
     */
    WARN {
        @Override
        public SLF4JLevelLoggerWarn newLevelLogger(Logger logger) {
            SLF4JLevelLoggerWarn x = new SLF4JLevelLoggerWarn(logger);
            return x;
        }
    },
    /**
     * All logging methods are sent to INFO level.
     *
     * @see Logger#info(String)
     */
    INFO {
        @Override
        public SLF4JLevelLoggerInfo newLevelLogger(Logger logger) {
            SLF4JLevelLoggerInfo x = new SLF4JLevelLoggerInfo(logger);
            return x;
        }
    },
    /**
     * All logging methods are sent to DEBUG level.
     *
     * @see Logger#debug(String)
     */
    DEBUG {
        @Override
        public SLF4JLevelLoggerDebug newLevelLogger(Logger logger) {
            SLF4JLevelLoggerDebug x = new SLF4JLevelLoggerDebug(logger);
            return x;
        }
    },
    /**
     * All logging methods are sent to TRACE level.
     *
     * @see Logger#trace(String)
     */
    TRACE {
        @Override
        public SLF4JLevelLoggerTrace newLevelLogger(Logger logger) {
            SLF4JLevelLoggerTrace x = new SLF4JLevelLoggerTrace(logger);
            return x;
        }
    };

    /**
     * Creates a new logger to only log at a single level.
     *
     * @param logger
     *        underlying SLF4J logger
     *
     * @return new logger
     *
     * @throws NullPointerException
     *         if {@code logger} is {@code null}
     */
    public abstract SLF4JLevelLogger newLevelLogger(Logger logger);
}
