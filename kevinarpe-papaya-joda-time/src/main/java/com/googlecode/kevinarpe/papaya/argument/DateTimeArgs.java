package com.googlecode.kevinarpe.papaya.argument;

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

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

/**
 * Static methods to check {@link DateTime} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class DateTimeArgs {
    
    // Disable default constructor
    private DateTimeArgs() {
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueInsideRange(Comparable, Comparable, Comparable, String)}.
     *
     * @see #checkValueOutsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMinValue(DateTime, DateTime, String)
     * @see #checkMaxValue(DateTime, DateTime, String)
     * @see #checkExactValue(DateTime, DateTime, String)
     * @see #checkNotExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkValueInsideRange(
        DateTime ref, DateTime minRangeValue, DateTime maxRangeValue, String argName) {
        ComparableArgs.checkValueInsideRange(
            (ReadableInstant) ref,
            (ReadableInstant) minRangeValue,
            (ReadableInstant) maxRangeValue,
            argName);
        return ref;
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueOutsideRange(Comparable, Comparable, Comparable, String)}.
     *
     * @see #checkValueInsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMinValue(DateTime, DateTime, String)
     * @see #checkMaxValue(DateTime, DateTime, String)
     * @see #checkExactValue(DateTime, DateTime, String)
     * @see #checkNotExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkValueOutsideRange(
        DateTime ref, DateTime minRangeValue, DateTime maxRangeValue, String argName) {
        ComparableArgs.checkValueOutsideRange(
            (ReadableInstant) ref,
            (ReadableInstant) minRangeValue,
            (ReadableInstant) maxRangeValue,
            argName);
        return ref;
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueInsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkValueOutsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMaxValue(DateTime, DateTime, String)
     * @see #checkExactValue(DateTime, DateTime, String)
     * @see #checkNotExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkMinValue(DateTime dt, DateTime minDt, String argName) {
        ComparableArgs.checkMinValue((ReadableInstant) dt, (ReadableInstant) minDt, argName);
        return dt;
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     *
     * @see #checkValueInsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkValueOutsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMinValue(DateTime, DateTime, String)
     * @see #checkExactValue(DateTime, DateTime, String)
     * @see #checkNotExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkMaxValue(DateTime dt, DateTime maxDt, String argName) {
        ComparableArgs.checkMaxValue((ReadableInstant) dt, (ReadableInstant) maxDt, argName);
        return dt;
    }

    // TODO: Create a helper to build toString() arguments.

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueInsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkValueOutsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMinValue(DateTime, DateTime, String)
     * @see #checkMaxValue(DateTime, DateTime, String)
     * @see #checkNotExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkExactValue(DateTime dt, DateTime exactDt, String argName) {
        ComparableArgs.checkExactValue((ReadableInstant) dt, (ReadableInstant) exactDt, argName);
        return dt;
    }

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkNotExactValue(Comparable, Comparable, String)}.
     *
     * @see #checkValueInsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkValueOutsideRange(DateTime, DateTime, DateTime, String)
     * @see #checkMinValue(DateTime, DateTime, String)
     * @see #checkMaxValue(DateTime, DateTime, String)
     * @see #checkExactValue(DateTime, DateTime, String)
     */
    @FullyTested
    public static DateTime checkNotExactValue(DateTime dt, DateTime exactDt, String argName) {
        ComparableArgs.checkNotExactValue((ReadableInstant) dt, (ReadableInstant) exactDt, argName);
        return dt;
    }
}
