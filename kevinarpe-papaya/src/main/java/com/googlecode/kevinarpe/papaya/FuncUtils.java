package com.googlecode.kevinarpe.papaya;

/*
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

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class FuncUtils {

    // Disable default constructor
    private FuncUtils() {
    }
    
    /**
     * Useful for instanceof tests 
     */
    public static interface Func {
        // empty
    }

    public static interface Func0<TResult>
    extends Func {
        TResult call();
    }

    public static interface Func1<TResult, TArg1>
    extends Func {
        TResult call(TArg1 arg1);
    }

    public static interface Func2<TResult, TArg1, TArg2>
    extends Func {
        TResult call(TArg1 arg1, TArg2 arg2);
    }
    
    public static interface Func3<TResult, TArg1, TArg2, TArg3>
    extends Func {
        TResult call(TArg1 arg1, TArg2 arg2, TArg3 arg3);
    }
    
    // Yada, yada...
    
    /**
     * Calls {@link StringUtils#parseBoolean(String)}.
     */
    public static final Func1<Boolean, String> PARSE_BOOLEAN_FROM_STRING;
    
    /**
     * Calls {@link Integer#parseInt(String, int)}.
     */
    public static final Func2<Integer, String, Integer> PARSE_INTEGER_FROM_STRING;
    
    /**
     * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 8}.
     */
    public static final Func1<Integer, String> PARSE_OCTAL_INTEGER_FROM_STRING;
    
    /**
     * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 10}.
     */
    public static final Func1<Integer, String> PARSE_DECIMAL_INTEGER_FROM_STRING;
    
    /**
     * Calls {@link Integer#parseInt(String, int)}, where {@code radix = 16}.
     */
    public static final Func1<Integer, String> PARSE_HEXIDECIMAL_INTEGER_FROM_STRING;
    
    /**
     * Calls {@link Float#parseFloat(String)}.
     */
    public static final Func1<Float, String> PARSE_FLOAT_FROM_STRING;
    
    /**
     * Calls {@link Double#parseDouble(String)}.
     */
    public static final Func1<Double, String> PARSE_DOUBLE_FROM_STRING;
    
    static {
        PARSE_BOOLEAN_FROM_STRING = new Func1<Boolean, String>() {

            @Override
            public Boolean call(String str) {
                boolean b = StringUtils.parseBoolean(str);
                return b;
            }
        };
        
        PARSE_INTEGER_FROM_STRING = new Func2<Integer, String, Integer>() {

            @Override
            public Integer call(String str, Integer radix) {
                int i = Integer.parseInt(str, radix);
                return i;
            }
        };
        
        PARSE_OCTAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

            @Override
            public Integer call(String str) {
                final int radix = 8;
                int i = Integer.parseInt(str, radix);
                return i;
            }
        };
        
        PARSE_DECIMAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

            @Override
            public Integer call(String str) {
                final int radix = 10;
                int i = Integer.parseInt(str, radix);
                return i;
            }
        };
        
        PARSE_HEXIDECIMAL_INTEGER_FROM_STRING = new Func1<Integer, String>() {

            @Override
            public Integer call(String str) {
                final int radix = 16;
                int i = Integer.parseInt(str, radix);
                return i;
            }
        };
        
        PARSE_FLOAT_FROM_STRING = new Func1<Float, String>() {

            @Override
            public Float call(String str) {
                float x = Float.parseFloat(str);
                return x;
            }
        };
        
        PARSE_DOUBLE_FROM_STRING = new Func1<Double, String>() {

            @Override
            public Double call(String str) {
                double x = Double.parseDouble(str);
                return x;
            }
        };
    }
}
