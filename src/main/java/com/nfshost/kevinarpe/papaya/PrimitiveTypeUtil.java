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

package com.nfshost.kevinarpe.papaya;

public final class PrimitiveTypeUtil {

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
	}
}
