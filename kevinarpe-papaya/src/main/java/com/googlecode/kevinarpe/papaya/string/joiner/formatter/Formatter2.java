package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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
 * Formats a value to a {@code String}.  This interface is named {@code Formatter2} to prevent name
 * clashes with existing classes named {@code Formatter}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StringFormatter2
 * @see CountingStringFormatter2
 */
// TODO: Later release: Changed this interface to not allow null values.  Use abstract base class to enforce.
public interface Formatter2 {

    /**
     * Formats a single value as a {@code String}.
     *
     * @param value
     *        any value.  May be {@code null}.
     *
     * @return value formatted as a {@link String}
     */
    String format(Object value);
}
