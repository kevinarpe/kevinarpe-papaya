package com.googlecode.kevinarpe.papaya.string.joiner;

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

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link Joiner2Utils} or {@link Joiner2Utils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Joiner2Utils
 */
public interface IJoiner2Utils {

    /**
     * Constructs a new instance of {@link Joiner2}.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     *
     * @see #withSeparator(char)
     * @see Joiner2#withSeparator(String)
     * @see Joiner2#withSeparator(char)
     */
    Joiner2 withSeparator(String separator);

    /**
     * This is a convenience method to call {@link #withSeparator(String)}.
     */
    Joiner2 withSeparator(char separator);

    /**
     * Constructs a new instance of {@link Joiner2Factory}.  Unless an additional layer of
     * indirection is required for mocking or testing, it is usually sufficient to call
     * {@link #withSeparator(String)}.
     *
     * @param separator
     *        String to insert between elements during join.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code ", "} (comma + space)
     *
     * @return new instance
     *
     * @throws NullPointerException
     *         if {@code separator} is {@code null}
     */
    Joiner2Factory newJoiner2Factory(String separator);

    /**
     * This is a convenience method to call {@link #newJoiner2Factory(String)}.
     */
    Joiner2Factory newJoiner2Factory(char separator);
}
