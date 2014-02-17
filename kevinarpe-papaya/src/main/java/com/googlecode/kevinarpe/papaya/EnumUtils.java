package com.googlecode.kevinarpe.papaya;

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
import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import com.googlecode.kevinarpe.papaya.string.joiner.QuotingJoiners;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class EnumUtils {

    public static <TEnum extends Enum<TEnum>>
    TEnum valueOf(Class<TEnum> enumType, String name) {
        try {
            TEnum value = _fastValueOf(enumType, name);
            return value;
        }
        catch (Exception e) {
            Enum[] enumArr = enumType.getEnumConstants();
            String enumStringList = _toStringList(enumArr);
            String msg = String.format(
                "Failed to find constant '%s' in enum type %s%nAvailable constants: %s",
                name, enumType.getName(), enumStringList);
            throw new IllegalArgumentException(msg, e);
        }
    }

    private static String _toStringList(Enum[] enumArr) {
        if (0 == enumArr.length) {
            return "(none)";
        }
        String[] enumNameArr = new String[enumArr.length];
        for (int i = 0; i < enumArr.length; ++i) {
            Enum anEnum = enumArr[i];
            String name = anEnum.name();
            enumNameArr[i] = name;
        }
        String x =
            QuotingJoiners.withSeparator(", ").withQuotes("'", "'").useForNoElements("(empty)")
                .join(enumNameArr);
        return x;
    }

    private static <TEnum extends Enum<TEnum>>
    TEnum _fastValueOf(Class<TEnum> enumType, String name) {
        ObjectArgs.checkNotNull(enumType, "enumType");
        StringArgs.checkNotEmptyOrWhitespace(name, "name");

        TEnum value = Enum.valueOf(enumType, name);
        return value;
    }

    public static <TEnum extends Enum<TEnum>>
    TEnum tryValueOf(Class<TEnum> enumType, String name) {
        try {
            TEnum value = _fastValueOf(enumType, name);
            return value;
        }
        catch (Exception ignore) {
            return null;
        }
    }
}
