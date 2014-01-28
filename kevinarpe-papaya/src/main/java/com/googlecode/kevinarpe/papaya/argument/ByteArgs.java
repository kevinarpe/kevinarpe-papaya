package com.googlecode.kevinarpe.papaya.argument;

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

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

/**
 * Static methods to check {@code byte} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ByteArgs {

    // Disable default constructor
    private ByteArgs() {
    }

    /**
     * This is a convenience method for {@link LongArgs#checkPositive(long, String)}.
     * 
     * @see #checkNotPositive(byte, String)
     * @see #checkNegative(byte, String)
     * @see #checkNotNegative(byte, String)
     */
    @FullyTested
    public static byte checkPositive(byte value, String argName) {
        LongArgs.checkPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotPositive(long, String)}.
     * 
     * @see #checkPositive(byte, String)
     * @see #checkNegative(byte, String)
     * @see #checkNotNegative(byte, String)
     */
    @FullyTested
    public static byte checkNotPositive(byte value, String argName) {
        LongArgs.checkNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNegative(long, String)}.
     * 
     * @see #checkPositive(byte, String)
     * @see #checkNotPositive(byte, String)
     * @see #checkNotNegative(byte, String)
     */
    @FullyTested
    public static byte checkNegative(byte value, String argName) {
        LongArgs.checkNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotNegative(long, String)}.
     * 
     * @see #checkPositive(byte, String)
     * @see #checkNotPositive(byte, String)
     * @see #checkNegative(byte, String)
     */
    @FullyTested
    public static byte checkNotNegative(byte value, String argName) {
        LongArgs.checkNotNegative(value, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(byte, byte, String)
     * @see #checkMaxValue(byte, byte, String)
     * @see #checkExactValue(byte, byte, String)
     */
    @FullyTested
    public static byte checkValueRange(
            byte value, byte minValue, byte maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(byte, byte, byte, String)
     * @see #checkMaxValue(byte, byte, String)
     * @see #checkExactValue(byte, byte, String)
     */
    @FullyTested
    public static byte checkMinValue(byte value, byte minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(byte, byte, byte, String)
     * @see #checkMinValue(byte, byte, String)
     * @see #checkExactValue(byte, byte, String)
     */
    @FullyTested
    public static byte checkMaxValue(byte value, byte maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(byte, byte, byte, String)
     * @see #checkMinValue(byte, byte, String)
     * @see #checkMaxValue(byte, byte, String)
     */
    @FullyTested
    public static byte checkExactValue(byte value, byte exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkNotExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(byte, byte, byte, String)
     * @see #checkMinValue(byte, byte, String)
     * @see #checkMaxValue(byte, byte, String)
     */
    @FullyTested
    public static byte checkNotExactValue(byte value, byte exactValue, String argName) {
        ComparableArgs.checkNotExactValue(value, exactValue, argName);
        return value;
    }
}
