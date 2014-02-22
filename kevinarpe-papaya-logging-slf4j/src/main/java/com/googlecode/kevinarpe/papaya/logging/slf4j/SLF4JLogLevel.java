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
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: LAST: Write tests for this module.
public enum SLF4JLogLevel {

    OFF {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            return SLF4JLevelLoggerOff.INSTANCE;
        }
    },
    ERROR {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            SLF4JLevelLoggerError x = new SLF4JLevelLoggerError(logger);
            return x;
        }
    },
    WARN {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            SLF4JLevelLoggerWarn x = new SLF4JLevelLoggerWarn(logger);
            return x;
        }
    },
    INFO {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            SLF4JLevelLoggerInfo x = new SLF4JLevelLoggerInfo(logger);
            return x;
        }
    },
    DEBUG {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            SLF4JLevelLoggerDebug x = new SLF4JLevelLoggerDebug(logger);
            return x;
        }
    },
    TRACE {
        @Override
        public SLF4JLevelLogger newLevelLogger(Logger logger) {
            SLF4JLevelLoggerTrace x = new SLF4JLevelLoggerTrace(logger);
            return x;
        }
    };

    public abstract SLF4JLevelLogger newLevelLogger(Logger logger);
}
