package com.googlecode.kevinarpe.papaya;

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

import java.util.Arrays;

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.argument.ArrayArgs;
import com.googlecode.kevinarpe.papaya.argument.IntArgs;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public final class ObjectUtils {

    // Disable default constructor
    private ObjectUtils() {
    }
    
    /**
     * Creates a hash code for a subclass using:
     * <ol>
     *   <li>superclass hash code</li>
     *   <li>member hash codes</li>
     * </ol>
     * <p>
     * This method follows the same algorithm used by {@link Arrays#hashCode(Object[])}.
     * 
     * @param superclassHashCode
     *        should be {@code super.hashCode()}
     * @param objectArr
     *        optional list of objects to calculate hash codes.  May be empty and contain
     *        {@code null} values
     *        
     * @return new hash code that combines hash code from superclass and all objects
     * 
     * @see #appendHashCodes(int, int...)
     * @see Arrays#hashCode(Object[])
     */
    @NotFullyTested
    public static int subclassHashCode(int superclassHashCode, Object... objectArr) {
        IntArgs.checkMinValue(superclassHashCode, 1, "superclassHashCode");
        for (int i = 0; i < objectArr.length; ++i) {
            Object object = objectArr[i];
            if (null != object) {
                Class<?> klass = object.getClass();
                if (klass.isArray()) {
                    String msg = String.format(
                        "Object (%d of %d) is an array of type %s",
                        1 + i, objectArr.length, klass.getSimpleName());
                    throw new IllegalArgumentException(msg);
                }
            }
        }
        int x = 31 * superclassHashCode + Arrays.hashCode(objectArr);
        return x;
    }
    
    /**
     * Combines two hash codes to create a new hash code.
     * <p>
     * This method follows the same algorithm used by {@link Arrays#hashCode(Object[])}.
     * 
     * @return new hash code that combines both hash codes
     * 
     * @see #subclassHashCode(int, Object...)
     * @see Arrays#hashCode(Object[])
     */
    @NotFullyTested
    public static int appendHashCodes(int hashCode, int... moreHashCodeArr) {
        IntArgs.checkNotNegative(hashCode, "hashCode");
        ArrayArgs.checkNotEmpty(moreHashCodeArr, "moreHashCodeArr");
        int newHashCode = hashCode;
        for (int i = 0; i < moreHashCodeArr.length; ++i) {
            int moreHashCode = moreHashCodeArr[i];
            if (moreHashCode < 0) {
                throw new IllegalArgumentException(String.format(
                    "Hash code (%d of %d) is negative: %d",
                    1 + i, moreHashCodeArr.length, moreHashCode));
            }
            newHashCode = 31 * newHashCode + moreHashCode;
        }
        return newHashCode;
    }
}
