package com.googlecode.kevinarpe.papaya.lang;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2019 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import com.google.common.primitives.Ints;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/**
 * Methods "missing" from Google Guava's {@link com.google.common.primitives.Floats}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Doubles2
 */
@FullyTested
public final class Floats2 {

    private Floats2() {}

    /**
     * Safely casts an integer value to float.  Inspired by {@link Ints#checkedCast(long)} and friends.
     * <p>
     * Sample values that will throw: {@code Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1}
     * <p>
     * To case from a {@code long} value, first safely cast to integer with {@link Ints#checkedCast(long)}.
     *
     * @throws IllegalArgumentException
     *         if input value cannot be safely cast to float
     *         
     * @see Doubles2#checkedCastFromLong(long)
     * @see Ints#checkedCast(long)
     */
    public static float
    checkedCastFromInt(final int value) {

        final float result = (float) value;
        if ((int) result == value) {
            return result;
        }
        else {
            throw new IllegalArgumentException("Out of range: " + value);
        }
    }
}
