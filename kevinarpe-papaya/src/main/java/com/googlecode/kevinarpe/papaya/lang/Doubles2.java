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
 * Methods "missing" from Google Guava's {@link com.google.common.primitives.Doubles}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Floats2
 */
@FullyTested
public final class Doubles2 {

    private Doubles2() {}

    /**
     * Safely casts a long value to double.  Inspired by {@link Ints#checkedCast(long)} and friends.
     * <p>
     * Sample values that will throw: {@code Long.MIN_VALUE + 1, Long.MAX_VALUE - 1}
     *
     * @throws IllegalArgumentException
     *         if input value cannot be safely cast to float
     *
     * @see Floats2#checkedCastFromInt(int)
     */
    public static double
    checkedCastFromLong(final long value) {

        final double result = (double) value;
        if ((long) result == value) {
            return result;
        }
        else {
            throw new IllegalArgumentException("Out of range: " + value);
        }
    }
}
