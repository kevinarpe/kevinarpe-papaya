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
public class CharArgs {

    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(char, char, String)
     * @see #checkMaxValue(char, char, String)
     * @see #checkExactValue(char, char, String)
     */
    public static char checkValueRange(
            char value, char minValue, char maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(char, char, char, String)
     * @see #checkMaxValue(char, char, String)
     * @see #checkExactValue(char, char, String)
     */
    public static char checkMinValue(char value, char minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(char, char, char, String)
     * @see #checkMinValue(char, char, String)
     * @see #checkExactValue(char, char, String)
     */
    public static char checkMaxValue(char value, char maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(char, char, char, String)
     * @see #checkMinValue(char, char, String)
     * @see #checkMaxValue(char, char, String)
     */
    public static char checkExactValue(char value, char exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
}
