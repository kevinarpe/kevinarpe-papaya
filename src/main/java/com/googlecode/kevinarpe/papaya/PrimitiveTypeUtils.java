package com.googlecode.kevinarpe.papaya;

import com.googlecode.kevinarpe.papaya.annotations.NotFullyTested;

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
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class PrimitiveTypeUtils {

    // Disable default constructor
    private PrimitiveTypeUtils() {
    }

    // Ref: http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
    
    public static final byte DEFAULT_BYTE_VALUE;
    public static final short DEFAULT_SHORT_VALUE;
    public static final int DEFAULT_INT_VALUE;
    public static final long DEFAULT_LONG_VALUE;
    public static final float DEFAULT_FLOAT_VALUE;
    public static final double DEFAULT_DOUBLE_VALUE;
    public static final char DEFAULT_CHAR_VALUE;
    public static final String DEFAULT_STRING_VALUE;
    public static final Object DEFAULT_OBJECT_VALUE;
    public static final boolean DEFAULT_BOOLEAN_VALUE;
    
    public static final float EPSILON_POSITIVE_FLOAT;
    public static final float EPSILON_NEGATIVE_FLOAT;
    public static final double EPSILON_POSITIVE_DOUBLE;
    public static final double EPSILON_NEGATIVE_DOUBLE;
    
    static {
        DEFAULT_BYTE_VALUE = 0;
        DEFAULT_SHORT_VALUE = 0;
        DEFAULT_INT_VALUE = 0;
        DEFAULT_LONG_VALUE = 0L;
        DEFAULT_FLOAT_VALUE = 0.0f;
        DEFAULT_DOUBLE_VALUE = 0.0d;
        DEFAULT_CHAR_VALUE = '\u0000';
        DEFAULT_STRING_VALUE = null;
        DEFAULT_OBJECT_VALUE = null;
        DEFAULT_BOOLEAN_VALUE = false;
        
        // Ref: http://stackoverflow.com/a/3735931/257299
        EPSILON_POSITIVE_FLOAT = Math.nextAfter(0.0f, 1.0f);
        EPSILON_NEGATIVE_FLOAT = Math.nextAfter(0.0f, -1.0f);
        EPSILON_POSITIVE_DOUBLE = Math.nextAfter(0.0d, 1.0d);
        EPSILON_NEGATIVE_DOUBLE = Math.nextAfter(0.0d, -1.0d);
        { int dummy = 0; }  // debug breakpoint
    }
}
