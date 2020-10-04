package com.googlecode.kevinarpe.papaya.container.policy;

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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;
import com.googlecode.kevinarpe.papaya.argument.StringArgs;

import java.util.Set;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public enum DoNotAllow {

    NullKey(
        "Null key"),

    NullValue(
        "Null value"),

    Remove(
        "Remove"),
    ;

    public final String description;

    private DoNotAllow(String description) {

        this.description = StringArgs.checkNotEmptyOrWhitespace(description, "description");
    }

    public static void
    throwIfNotAllowed(@EmptyContainerAllowed Set<? extends DoNotAllow> doNotAllowSet,
                      DoNotAllow doNotAllow) {
        // Paranoid
        ObjectArgs.checkNotNull(doNotAllow, "doNotAllow");

        if (doNotAllowSet.contains(doNotAllow)) {

            throw new UnsupportedOperationException(doNotAllow.description + " is not allowed");
        }
    }
}
