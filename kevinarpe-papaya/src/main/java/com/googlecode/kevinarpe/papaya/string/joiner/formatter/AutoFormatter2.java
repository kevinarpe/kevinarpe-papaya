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
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * Automatically formats objects of any type to strings.
 * <ul>
 *     <li>{@link String} -> (double quote prefix and suffix) {@code "\"abc\""}</li>
 *     <li>{@link Character} -> (single quote prefix and suffix) {@code "'Z'"}</li>
 *     <li>{@link Boolean} -> (no prefix or suffix) {@code "true"} or {@code "false"}</li>
 *     <li>{@link Number} -> (no prefix or suffix)
 *     <li>Else -> (curly brace prefix and suffix) {@code "{<Object.toString()>}"}
 * </ul>
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see #INSTANCE
 * @see StatelessObject
 * @see Formatter2
 */
@FullyTested
public final class AutoFormatter2
extends StatelessObject
implements Formatter2 {

    /**
     * Single instance of this class provided for convenience.  Since this class is stateless, its
     * behaviour is identical between this instance and others.
     */
    public static final AutoFormatter2 INSTANCE = new AutoFormatter2();

    @Override
    public String format(Object value) {
        if (null == value) {
            return StringFormatterHelperImpl.NULL_VALUE_AS_STRING;
        }
        Class<?> clazz = value.getClass();
        String result = null;
        if (clazz.equals(String.class)) {
            result = "\"" + value + "\"";
        }
        else if (clazz.equals(Character.class)) {
            result = "'" + value + "'";
        }
        else if (clazz.equals(Boolean.class) || Number.class.isAssignableFrom(clazz)) {
            result = value.toString();
        }
        else {
            result = "{" + value.toString() + "}";
        }
        return result;
    }
}
