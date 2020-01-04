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

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.List;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
class StringFormatterHelperImpl
implements StringFormatterHelper {

    public static final StringFormatterHelperImpl INSTANCE = new StringFormatterHelperImpl();

    public static final String NULL_VALUE_AS_STRING = String.format("%s", new Object[] { null });

    @Override
    public String format(String description, String format, Object... argArr) {
        StringArgs.checkNotEmptyOrWhitespace(description, "description");
        StringArgs.checkNotEmptyOrWhitespace(format, "format");
        ObjectArgs.checkNotNull(argArr, "argArr");

        try {
            String x = String.format(format, argArr);
            return x;
        }
        catch (Exception e) {
            List<String> argStringList = Lists.newArrayListWithCapacity(argArr.length);
            for (Object arg : argArr) {
                String argString = valueToString(arg);
                argStringList.add(argString);
            }
            String msg = String.format(
                "Failed to format %s: String.format([%s]%s%s)",
                description,
                format,
                (0 == argArr.length ? "" : ", "),
                Joiner.on(", ").join(argStringList));
            throw new IllegalArgumentException(msg, e);
        }
    }

    // package-private for testing
    String valueToString(Object value) {
        if (null == value) {
            return NULL_VALUE_AS_STRING;
        }
        String leftQuote = "";
        String rightQuote = "";
        if (Character.class.isAssignableFrom(value.getClass())) {
            leftQuote = "'";
            rightQuote = "'";
        }
        else if (!Number.class.isAssignableFrom(value.getClass())
                    && !Boolean.class.isAssignableFrom(value.getClass())) {
            leftQuote = "[";
            rightQuote = "]";
        }
        String x = String.format(
            "%s: %s%s%s", value.getClass().getSimpleName(), leftQuote, value, rightQuote);
        return x;
    }
}
