package com.googlecode.kevinarpe.papaya.compare;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Comparator;

/**
 * Controls how strings are compared: whether or not to ignore case differences.  For most
 * languages of origin <i>outside</i> Western European, this distinction is irrelevant.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractLexicographicalComparator
 * @see #compare(String, String)
 */
@FullyTested
public enum CaseSensitive
implements Comparator<String> {

    /**
     * Do <b>not</b> ignore case during {@link String} comparisons.
     *
     *  @see String#compareTo(String)
     */
    YES {
        @Override
        public int compare(String left, String right) {
            ObjectArgs.checkNotNull(left, "left");
            ObjectArgs.checkNotNull(right, "right");

            int result = left.compareTo(right);
            result = ComparatorUtils.normalizeCompareResult(result);
            return result;
        }
    },

    /**
     * Ignore case during {@link String} comparisons.
     *
     * @see String#compareToIgnoreCase(String)
     */
    NO {
        @Override
        public int compare(String left, String right) {
            ObjectArgs.checkNotNull(left, "left");
            ObjectArgs.checkNotNull(right, "right");

            int result = left.compareToIgnoreCase(right);
            result = ComparatorUtils.normalizeCompareResult(result);
            return result;
        }
    };

    /**
     * @return opposite value, e.g., {@link #YES} to {@link #NO} and {@link #NO} to {@link #YES}
     */
    public CaseSensitive invert() {
        return (this == YES ? NO : YES);
    }

    /**
     * Calls {@link String#compareTo(String)} or {@link String#compareToIgnoreCase(String)}.
     *
     * @param left
     *        must not be {@code null}
     * @param right
     *        must not be {@code null}
     *
     * @return strictly normalized compare result: -1, 0 or +1
     *
     * @throws NullPointerException
     *         if {@code left} or {@code right} is {@code null}
     *
     * @see ComparatorUtils#normalizeCompareResult(int)
     */
    public abstract int compare(String left, String right);
}
