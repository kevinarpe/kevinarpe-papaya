package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;

/*
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 Kevin Connor ARPE (kevinarpe@gmail.com)
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

/**
 * Static methods to check {@code int} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class IntArgs {

    // Disable default constructor
    private IntArgs() {
    }

    /**
     * This is a convenience method for {@link LongArgs#checkPositive(long, String)}.
     * 
     * @see #checkNotPositive(int, String)
     * @see #checkNegative(int, String)
     * @see #checkNotNegative(int, String)
     */
    @FullyTested
    public static int checkPositive(int value, String argName) {
        LongArgs.checkPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotPositive(long, String)}.
     * 
     * @see #checkPositive(int, String)
     * @see #checkNegative(int, String)
     * @see #checkNotNegative(int, String)
     */
    @FullyTested
    public static int checkNotPositive(int value, String argName) {
        LongArgs.checkNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNegative(long, String)}.
     * 
     * @see #checkPositive(int, String)
     * @see #checkNotPositive(int, String)
     * @see #checkNotNegative(int, String)
     */
    @FullyTested
    public static int checkNegative(int value, String argName) {
        LongArgs.checkNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotNegative(long, String)}.
     * 
     * @see #checkPositive(int, String)
     * @see #checkNotPositive(int, String)
     * @see #checkNegative(int, String)
     */
    @FullyTested
    public static int checkNotNegative(int value, String argName) {
        LongArgs.checkNotNegative(value, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(int, int, String)
     * @see #checkMaxValue(int, int, String)
     * @see #checkExactValue(int, int, String)
     * @see #checkNotExactValue(int, int, String)
     */
    @FullyTested
    public static int checkValueRange(
            int value, int minValue, int maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(int, int, int, String)
     * @see #checkMaxValue(int, int, String)
     * @see #checkExactValue(int, int, String)
     * @see #checkNotExactValue(int, int, String)
     */
    @FullyTested
    public static int checkMinValue(int value, int minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(int, int, int, String)
     * @see #checkMinValue(int, int, String)
     * @see #checkExactValue(int, int, String)
     * @see #checkNotExactValue(int, int, String)
     */
    @FullyTested
    public static int checkMaxValue(int value, int maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(int, int, int, String)
     * @see #checkMinValue(int, int, String)
     * @see #checkMaxValue(int, int, String)
     * @see #checkNotExactValue(int, int, String)
     */
    @FullyTested
    public static int checkExactValue(int value, int exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(int, int, int, String)
     * @see #checkMinValue(int, int, String)
     * @see #checkMaxValue(int, int, String)
     * @see #checkExactValue(int, int, String)
     */
    @FullyTested
    public static int checkNotExactValue(int value, int exactValue, String argName) {
        ComparableArgs.checkNotExactValue(value, exactValue, argName);
        return value;
    }
}
