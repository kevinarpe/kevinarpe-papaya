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
import com.googlecode.kevinarpe.papaya.object.StatelessObject;
import com.googlecode.kevinarpe.papaya.string.joiner.Joiner2Utils;

/**
 * Formats a value using {@code String.valueOf(Object)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see Formatter2
 * @see Joiner2Utils#DEFAULT_ELEMENT_FORMATTER
 * @see Joiner2Utils#DEFAULT_FINAL_FORMATTER
 * @see Joiner2Utils#DEFAULT_KEY_FORMATTER
 * @see Joiner2Utils#DEFAULT_VALUE_FORMATTER
 */
@FullyTested
public final class DefaultFormatter2
extends StatelessObject
implements Formatter2 {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final DefaultFormatter2 INSTANCE = new DefaultFormatter2();

    @Override
    public String format(Object value) {
        String x = String.valueOf(value);
        return x;
    }
}
