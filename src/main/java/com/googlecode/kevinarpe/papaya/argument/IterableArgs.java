package com.googlecode.kevinarpe.papaya.argument;

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

/**
 * Static methods to check {@code Iterable<T>} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class IterableArgs {

    // Disable default constructor
    private IterableArgs() {
    }

    /**
     * Tests if a {@link Iterable} reference is not null and each element is not null.  An empty
     * iterable will pass this test.
     * 
     * @param ref
     *        an iterable reference
     * @param argName
     *        argument name for {@code ref}, e.g., "strList" or "searchRegex"
     *
     * @return the validated iterable reference
     *
     * @throws NullPointerException
     *         if {@code ref} (or any element) is {@code null}
     *
     * @see ObjectArgs#checkNotNull(Object, String)
     * @see ArrayArgs#checkElementsNotNull(Object[], String)
     * @see CollectionArgs#checkElementsNotNull(Collection, String)
     */
    @FullyTested
    public static <TValue, TIterable extends Iterable<TValue>>
    TIterable checkElementsNotNull(TIterable ref, String argName) {
        ContainerArgs._checkElementsNotNull(ref, "Iterable", argName);
        return ref;
    }
}
