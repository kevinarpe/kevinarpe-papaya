package com.googlecode.kevinarpe.papaya.container;

/*-
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

import com.googlecode.kevinarpe.papaya.annotation.EmptyContainerAllowed;
import com.googlecode.kevinarpe.papaya.annotation.ReadOnlyContainer;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
// package-private
final class _FullEnumMaps {

    private _FullEnumMaps() {
        // Empty
    }

    // package-private
    static <TEnumKey2 extends Enum<TEnumKey2>, TValue2>
    void _assertAllKeysFound(Class<TEnumKey2> enumClass,
                             @EmptyContainerAllowed Map<TEnumKey2, ? extends TValue2> map,
                             IsEmptyEnumAllowed isEmptyEnumAllowed) {
        @ReadOnlyContainer final TEnumKey2[] enumValueArr = _getEnumArrOrThrow(enumClass, isEmptyEnumAllowed);
        if (enumValueArr.length == map.size()) {
            return;
        }
        final StringBuilder sb =
            new StringBuilder("Missing values from enum class ").append(enumClass.getSimpleName()).append(": ");

        String delim = "";
        for (final TEnumKey2 enumValue : enumValueArr) {

            if (map.containsKey(enumValue)) {
                continue;
            }
            sb.append(delim).append(enumValue.name());
            delim = ", ";
        }
        final String sbs = sb.toString();
        throw new IllegalArgumentException(sbs);
    }

    private static <TEnumKey2 extends Enum<TEnumKey2>>
    TEnumKey2[]
    _getEnumArrOrThrow(Class<TEnumKey2> enumClass,
                       IsEmptyEnumAllowed isEmptyEnumAllowed) {
        @Nullable
        @ReadOnlyContainer final TEnumKey2[] enumValueArr = enumClass.getEnumConstants();
        if (null == enumValueArr) {
            throw new IllegalStateException("Unreachable code");
        }
        if (IsEmptyEnumAllowed.NO.equals(isEmptyEnumAllowed) && 0 == enumValueArr.length) {

            final String msg = "Enum class " + enumClass.getSimpleName() + " has zero values";
            throw new IllegalArgumentException(msg);
        }
        return enumValueArr;
    }
}
