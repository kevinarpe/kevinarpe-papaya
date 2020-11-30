package com.googlecode.kevinarpe.papaya.lang;

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

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Various helper methods for {@link Number}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class Numbers {

    /**
     * Carefully extracts {@code double} value from {@link Number}, including checks for NaN/Infinity/-Infinity, which
     * are frequently surprising or unexpected.
     * <p>
     * If the instance is integral, its long value is first extracted, then safely cast to {@code double} with
     * {@link Doubles2#checkedCastFromLong(long)}.
     *
     * @param number
     *        supports {@link BigInteger}, {@link Long}, {@link Integer}, {@link Short}, {@link Byte},
     *        {@link Double}, {@link Float}, {@link BigDecimal}
     *
     * @param allowNaN
     *        if {@link AllowNaN#NO}, an exception is thrown when value is {@link Double#NaN}
     *        <br>Use {@code AllowNaN.NO} when {@code Double.NaN} would be a surprising or unexpected result,
     *        such as parsing input data from configuration or user.
     *
     * @param allowInfinity
     *        if {@link AllowInfinity#NO}, an exception is thrown when value is {@link Double#POSITIVE_INFINITY}
     *        or {@link Double#NEGATIVE_INFINITY}
     *        <br>Use {@code AllowNaN.NO} when {@code Double.NaN} would be a surprising or unexpected result,
     *        such as parsing input data from configuration or user.
     *
     * @return value as {@code double}, including safe (lossless) casts from integral ({@code long}) values
     *
     * @throws ArithmeticException
     *         if {@link AllowNaN#NO} and result is {@link Double#NaN}
     *         <br>if {@link AllowInfinity#NO} and result is {@link Double#POSITIVE_INFINITY}
     *         or {@link Double#NEGATIVE_INFINITY}
     *         <br>thrown by {@link BigInteger#longValueExact()}
     *
     * @throws IllegalArgumentException
     *         thrown by {@link Doubles2#checkedCastFromLong(long)}
     *         <br>if {@code number} is an unsupported type
     */
    public static double
    doubleValue(Number number,
                AllowNaN allowNaN,
                AllowInfinity allowInfinity) {

        ObjectArgs.checkNotNull(allowNaN, "allowNaN");
        ObjectArgs.checkNotNull(allowInfinity, "allowInfinity");

        final double d = _doubleValue(number);

        if ((AllowNaN.NO.equals(allowNaN) && Double.isNaN(d))
                ||
            (AllowInfinity.NO.equals(allowInfinity) && Double.isInfinite(d))) {

            throw new ArithmeticException("Number [" + number + "] is " + d);
        }
        return d;
    }

    private static double
    _doubleValue(Number number) {

        // Roughly ordered by likelihood
        if (number instanceof Long
                || number instanceof Integer
                || number instanceof Short
                || number instanceof Byte) {

            final long L = number.longValue();
            final double x = Doubles2.checkedCastFromLong(L);
            return x;
        }
        // float can always be safely upcast to double.  BigDecimal does not have doubleValueExact(), so we do our best.
        else if (number instanceof Double || number instanceof Float || number instanceof BigDecimal) {

            // NaN and +/-Infinity will be handled by caller.
            final double x = number.doubleValue();
            return x;
        }
        else if (number instanceof BigInteger) {

            final BigInteger B = (BigInteger) number;
            final long L = B.longValueExact();
            final double x = Doubles2.checkedCastFromLong(L);
            return x;
        }
        else {
            throw new IllegalArgumentException(
                "Unsupported " + Number.class.getSimpleName() + " type: " + number.getClass().getSimpleName()
                    + " [" + number + "]");
        }
    }

    /**
     * Carefully extracts {@code long} value from {@link Number}.
     * <p>
     * If the instance is floating point, its {@code double} value is first extracted, then safely cast to {@code long}
     * with {@link Longs2#checkedCastFromDouble(double)}.
     *
     * @param number
     *        supports {@link BigInteger}, {@link Long}, {@link Integer}, {@link Short}, {@link Byte},
     *        {@link Double}, {@link Float}, {@link BigDecimal}
     *
     * @return value as {@code long}, including safe (lossless) casts from floating point ({@code double}) values
     *
     * @throws ArithmeticException
     *         thrown by {@link BigInteger#longValueExact()}
     *
     * @throws IllegalArgumentException
     *         thrown by {@link Longs2#checkedCastFromDouble(double)}
     *         <br>if {@code number} is an unsupported type
     *
     * @see Ints#checkedCast(long)
     * @see Shorts#checkedCast(long)
     * @see SignedBytes#checkedCast(long)
     * @see UnsignedBytes#checkedCast(long)
     */
    public static long
    longValue(Number number) {

        // Roughly ordered by likelihood
        if (
            number instanceof Long
                || number instanceof Integer
                || number instanceof Short
                || number instanceof Byte) {

            final long x = number.longValue();
            return x;
        }
        // float can always be safely upcast to double.  BigDecimal does not have doubleValueExact(), so we do our best.
        else if (number instanceof Double || number instanceof Float || number instanceof BigDecimal) {

            final double d = number.doubleValue();
            // Rejects NaN and +/-Infinity
            final long x = Longs2.checkedCastFromDouble(d);
            return x;
        }
        else if (number instanceof BigInteger) {

            final BigInteger B = (BigInteger) number;
            final long x = B.longValueExact();
            return x;
        }
        else {
            throw new IllegalArgumentException(
                "Unsupported " + Number.class.getSimpleName() + " type: " + number.getClass().getSimpleName()
                    + " [" + number + "]");
        }
    }
}
