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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.Formatter;

/**
 * Base class for formatters that use {@link String#format(String, Object...)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see StringFormatter2
 * @see CountingStringFormatter2
 */
@FullyTested
public abstract class AbstractStringFormatter2 {

    private final String _format;

    /**
     * Creates a new abstract formatter with a format string.
     *
     * @param format
     * <ul>
     *     <li>Format string for {@link String#format(String, Object...)}</li>
     *     <li>Must not be {@code null}, empty, or only whitespace</li>
     *     <li>Example 1: {@code "%.6f"}</li>
     *     <li>Example 2: {@code "[%s]"}</li>
     *     <li>Example 3: {@code "[%1$s] '%1$s'"}</li>
     * </ul>
     *
     * @throws NullPointerException
     *         if {@code format} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code format} is empty or only whitespace
     *
     * @see Formatter
     * @see #getFormat()
     */
    protected AbstractStringFormatter2(String format) {
        _format = StringArgs.checkNotEmptyOrWhitespace(format, "format");
    }

    /**
     * @return format string passed to constructor
     */
    public final String getFormat() {
        return _format;
    }

    @Override
    public String toString() {
        String x = String.format(
            "%s: { getFormat(): '%s' }", this.getClass().getName(), _format);
        return x;
    }
}
