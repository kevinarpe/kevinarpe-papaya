/**
 * Copyright 2013 Kevin Connor ARPE
 * 
 * This file is part of Papaya.
 * 
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nfshost.kevinarpe.papaya.Args;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ByteArgs {

    /**
     * This is a convenience method for {@link LongArgs#checkPositive(long, String)}.
     * 
     * @see #checkByteNotPositive(byte, String)
     * @see #checkByteNegative(byte, String)
     * @see #checkByteNotNegative(byte, String)
     */
    public static byte checkBytePositive(byte value, String argName) {
        LongArgs.checkPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotPositive(long, String)}.
     * 
     * @see #checkBytePositive(byte, String)
     * @see #checkByteNegative(byte, String)
     * @see #checkByteNotNegative(byte, String)
     */
    public static byte checkByteNotPositive(byte value, String argName) {
        LongArgs.checkNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNegative(long, String)}.
     * 
     * @see #checkBytePositive(byte, String)
     * @see #checkByteNotPositive(byte, String)
     * @see #checkByteNotNegative(byte, String)
     */
    public static byte checkByteNegative(byte value, String argName) {
        LongArgs.checkNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotNegative(long, String)}.
     * 
     * @see #checkBytePositive(byte, String)
     * @see #checkByteNotPositive(byte, String)
     * @see #checkByteNegative(byte, String)
     */
    public static byte checkByteNotNegative(byte value, String argName) {
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
    public static byte checkExactValue(byte value, byte exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
}
