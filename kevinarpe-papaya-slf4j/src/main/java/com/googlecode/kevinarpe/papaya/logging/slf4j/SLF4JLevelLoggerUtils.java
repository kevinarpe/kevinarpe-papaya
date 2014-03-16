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
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class SLF4JLevelLoggerUtils {

    public static SLF4JLevelLogger newInstance(SLF4JLogLevel logLevel, Class<?> clazz) {
        ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
        SLF4JLevelLogger x = newInstance(loggerFactory, logLevel, clazz);
        return x;
    }

    public static SLF4JLevelLogger newInstance(
            ILoggerFactory loggerFactory, SLF4JLogLevel logLevel, Class<?> clazz) {
        ObjectArgs.checkNotNull(loggerFactory, "loggerFactory");
        ObjectArgs.checkNotNull(logLevel, "logLevel");
        ObjectArgs.checkNotNull(clazz, "clazz");

        if (SLF4JLogLevel.OFF == logLevel) {
            return SLF4JLevelLoggerOff.INSTANCE;
        }
        String className = clazz.getName();
        Logger logger = loggerFactory.getLogger(className);
        SLF4JLevelLogger levelLogger = logLevel.newLevelLogger(logger);
        return levelLogger;
    }
}