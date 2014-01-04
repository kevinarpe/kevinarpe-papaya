package com.googlecode.kevinarpe.papaya.compare;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;

import java.util.Comparator;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class ComparatorUtils {

    /**
     * Converts a result from {@link Comparator#compare(Object, Object)} to -1, 0, or +1.
     *
     * @param result
     *        raw result, e.g., -17, 0, +17, etc.
     *
     * @return normalized compare result: -1, 0, or +1
     */
    public static int normalizeCompareResult(int result) {
        if (0 != result) {
            result /= Math.abs(result);
        }
        return result;
    }
}
