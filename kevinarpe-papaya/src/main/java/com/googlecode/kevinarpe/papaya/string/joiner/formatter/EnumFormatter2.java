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

import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.object.StatelessObject;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// TODO: Test me and add javadocs
public class EnumFormatter2
extends StatelessObject
implements Formatter2 {

    public static final EnumFormatter2 INSTANCE = new EnumFormatter2();

    public EnumFormatter2() {
        // Empty
    }

    @Override
    public String format(Object value) {
        if (null == value) {
            return StringFormatterHelperImpl.NULL_VALUE_AS_STRING;
        }
        Enum<?> enumValue = ObjectArgs.checkCast(value.getClass(), Enum.class, "class of value");
        String x = enumValue.name();
        return x;
    }
}
