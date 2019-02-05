package com.googlecode.kevinarpe.papaya.logging.slf4j;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * Intentional: {@code WARNING} does not exist.  This is an intentional design decision.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public enum LoggerLevel {

    /**
     * SLF4J info level
     *
     * @see org.slf4j.Logger#info(java.lang.String)
     */
    INFO,
    /**
     * SLF4J info level
     *
     * @see org.slf4j.Logger#error(java.lang.String)
     */
    ERROR,
    /**
     * SLF4J info level
     *
     * @see org.slf4j.Logger#debug(java.lang.String)
     */
    DEBUG,
    /**
     * SLF4J info level
     *
     * @see org.slf4j.Logger#trace(java.lang.String)
     */
    TRACE,
}
