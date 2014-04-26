package com.googlecode.kevinarpe.papaya.testing.logging.slf4j;

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

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link SLF4JLevelLoggerUtils} will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public interface ISLF4JLevelLoggerUtils {

    /**
     * This is a convenience method to call
     * {@link #newInstance(ILoggerFactory, SLF4JLogLevel, Class)} with the default global SLF4J
     * {@link ILoggerFactory}.
     */
    SLF4JLevelLogger newInstance(SLF4JLogLevel logLevel, Class<?> clazz);

    /**
     * Creates a new logger than logs all messages at a single level.
     *
     * @param loggerFactory
     *        usually {@link LoggerFactory#getILoggerFactory()}, but in rare cases may be different
     * @param logLevel
     *        the single level at which the new logging shall log, e.g., {@link SLF4JLogLevel#INFO}
     * @param clazz
     *        used to set the name of the new logger via {@link Class#getName()}
     *
     * @return new instance that implements inteface {@link SLF4JLevelLogger}
     *
     * @throws NullPointerException
     *         if any argument is {@code null}
     */
    SLF4JLevelLogger newInstance(
            ILoggerFactory loggerFactory, SLF4JLogLevel logLevel, Class<?> clazz);
}
