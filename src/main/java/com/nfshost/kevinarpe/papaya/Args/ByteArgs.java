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

public final class ByteArgs {

    /**
     * This is a convenience method for {@link LongArgs#staticCheckPositive(long, String)}.
     * 
     * @see #staticCheckByteNotPositive(byte, String)
     * @see #staticCheckByteNegative(byte, String)
     * @see #staticCheckByteNotNegative(byte, String)
     */
    public static byte staticCheckBytePositive(byte value, String argName) {
        LongArgs.staticCheckPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#staticCheckNotPositive(long, String)}.
     * 
     * @see #staticCheckBytePositive(byte, String)
     * @see #staticCheckByteNegative(byte, String)
     * @see #staticCheckByteNotNegative(byte, String)
     */
    public static byte staticCheckByteNotPositive(byte value, String argName) {
        LongArgs.staticCheckNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#staticCheckNegative(long, String)}.
     * 
     * @see #staticCheckBytePositive(byte, String)
     * @see #staticCheckByteNotPositive(byte, String)
     * @see #staticCheckByteNotNegative(byte, String)
     */
    public static byte staticCheckByteNegative(byte value, String argName) {
        LongArgs.staticCheckNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#staticCheckNotNegative(long, String)}.
     * 
     * @see #staticCheckBytePositive(byte, String)
     * @see #staticCheckByteNotPositive(byte, String)
     * @see #staticCheckByteNegative(byte, String)
     */
    public static byte staticCheckByteNotNegative(byte value, String argName) {
        LongArgs.staticCheckNotNegative(value, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #staticCheckMinValue(byte, byte, String)
     * @see #staticCheckMaxValue(byte, byte, String)
     * @see #staticCheckExactValue(byte, byte, String)
     */
    public static byte staticCheckValueRange(
            byte value, byte minValue, byte maxValue, String argName) {
        ComparableArgs.staticCheckValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckMinValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(byte, byte, byte, String)
     * @see #staticCheckMaxValue(byte, byte, String)
     * @see #staticCheckExactValue(byte, byte, String)
     */
    public static byte staticCheckMinValue(byte value, byte minValue, String argName) {
        ComparableArgs.staticCheckMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(byte, byte, byte, String)
     * @see #staticCheckMinValue(byte, byte, String)
     * @see #staticCheckExactValue(byte, byte, String)
     */
    public static byte staticCheckMaxValue(byte value, byte maxValue, String argName) {
        ComparableArgs.staticCheckMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#staticCheckExactValue(Comparable, Comparable, String)}.
     * 
     * @see #staticCheckValueRange(byte, byte, byte, String)
     * @see #staticCheckMinValue(byte, byte, String)
     * @see #staticCheckMaxValue(byte, byte, String)
     */
    public static byte staticCheckExactValue(byte value, byte exactValue, String argName) {
        ComparableArgs.staticCheckExactValue(value, exactValue, argName);
        return value;
    }
}
