package com.googlecode.kevinarpe.papaya.string.joiner.formatter;

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

/**
 * Formats a value using {@code String.format()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractStringFormatter2
 * @see Formatter2
 * @see String#format(String, Object...)
 */
@FullyTested
public final class StringFormatter2
extends AbstractStringFormatter2
implements Formatter2 {

    private final StringFormatterHelper _stringFormatterHelper;

    /**
     * @see AbstractStringFormatter2#AbstractStringFormatter2(String)
     */
    public StringFormatter2(String format) {
        this(format, StringFormatterHelperImpl.INSTANCE);
    }

    StringFormatter2(String format, StringFormatterHelper stringFormatterHelper) {
        super(format);
        _stringFormatterHelper =
            ObjectArgs.checkNotNull(stringFormatterHelper, "stringFormatterHelper");
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException
     *         if {@link String#format(String, Object...)} fails
     */
    @Override
    public String format(Object value) {
        final String format = getFormat();
        String x = _stringFormatterHelper.format("value", format, value);
        return x;
    }
}
