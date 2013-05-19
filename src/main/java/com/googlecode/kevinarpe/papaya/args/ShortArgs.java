package com.googlecode.kevinarpe.papaya.args;

import com.googlecode.kevinarpe.papaya.annotations.FullyTested;

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
 * Static methods to check {@code short} arguments.
 * <p>
 * See {@link ObjectArgs} for an overview.
 * 
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ShortArgs {

    // Disable default constructor
    private ShortArgs() {
    }

    /**
     * This is a convenience method for {@link LongArgs#checkPositive(long, String)}.
     * 
     * @see #checkNotPositive(short, String)
     * @see #checkNegative(short, String)
     * @see #checkNotNegative(short, String)
     */
    @FullyTested
    public static short checkPositive(short value, String argName) {
        LongArgs.checkPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotPositive(long, String)}.
     * 
     * @see #checkPositive(short, String)
     * @see #checkNegative(short, String)
     * @see #checkNotNegative(short, String)
     */
    @FullyTested
    public static short checkNotPositive(short value, String argName) {
        LongArgs.checkNotPositive(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNegative(long, String)}.
     * 
     * @see #checkPositive(short, String)
     * @see #checkNotPositive(short, String)
     * @see #checkNotNegative(short, String)
     */
    @FullyTested
    public static short checkNegative(short value, String argName) {
        LongArgs.checkNegative(value, argName);
        return value;
    }
    
    /**
     * This is a convenience method for {@link LongArgs#checkNotNegative(long, String)}.
     * 
     * @see #checkPositive(short, String)
     * @see #checkNotPositive(short, String)
     * @see #checkNegative(short, String)
     */
    @FullyTested
    public static short checkNotNegative(short value, String argName) {
        LongArgs.checkNotNegative(value, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkValueRange(Comparable, Comparable, Comparable, String)}.
     * 
     * @see #checkMinValue(short, short, String)
     * @see #checkMaxValue(short, short, String)
     * @see #checkExactValue(short, short, String)
     */
    @FullyTested
    public static short checkValueRange(
            short value, short minValue, short maxValue, String argName) {
        ComparableArgs.checkValueRange(value, minValue, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMinValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(short, short, short, String)
     * @see #checkMaxValue(short, short, String)
     * @see #checkExactValue(short, short, String)
     */
    @FullyTested
    public static short checkMinValue(short value, short minValue, String argName) {
        ComparableArgs.checkMinValue(value, minValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkMaxValue(Comparable, Comparable, String)}.
     * 
     * @see #checkValueRange(short, short, short, String)
     * @see #checkMinValue(short, short, String)
     * @see #checkExactValue(short, short, String)
     */
    @FullyTested
    public static short checkMaxValue(short value, short maxValue, String argName) {
        ComparableArgs.checkMaxValue(value, maxValue, argName);
        return value;
    }
    
    /**
     * Convenience method to call
     * {@link ComparableArgs#checkExactValue(Comparable, Comparable, String)}
     * where {@code minValue = null} and {@code maxValue = null}.
     * 
     * @see #checkValueRange(short, short, short, String)
     * @see #checkMinValue(short, short, String)
     * @see #checkMaxValue(short, short, String)
     */
    @FullyTested
    public static short checkExactValue(short value, short exactValue, String argName) {
        ComparableArgs.checkExactValue(value, exactValue, argName);
        return value;
    }
}
