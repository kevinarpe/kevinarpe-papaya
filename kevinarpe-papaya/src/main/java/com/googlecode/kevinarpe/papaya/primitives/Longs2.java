package com.googlecode.kevinarpe.papaya.primitives;

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

import com.google.common.math.DoubleMath;
import com.google.common.primitives.Ints;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/**
 * Methods "missing" from Google Guava's {@link com.google.common.primitives.Longs}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Longs2 {

    private Longs2() {}

    /**
     * Safely casts a double value to long.  Inspired by {@link Ints#checkedCast(long)} and friends.
     *
     * @param value
     *        it is safe to pass a {@code float} value as this will be upcast without loss of precision
     *        to a {@code double} value
     *
     * @throws IllegalArgumentException
     *         if input value is not an integral value
     *
     * @see DoubleMath#isMathematicalInteger(double)
     */
    public static long
    checkedCastFromDouble(final double value) {

        if (DoubleMath.isMathematicalInteger(value)) {

            final long x = (long) value;
            return x;
        }
        else {
            throw new IllegalArgumentException("Not an integral value: " + value);
        }
    }
}
