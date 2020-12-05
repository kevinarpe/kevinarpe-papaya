package com.googlecode.kevinarpe.papaya.function.retry;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
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

import javax.annotation.concurrent.ThreadSafe;

/**
 * ThreadSafe?  Implementations must be thread-safe.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@ThreadSafe
public interface CollectionIndexMatcher {

    /**
     * Predicate to match a collection index
     *
     * @param index
     *        range: zero to {@code (size - 1)}
     *
     * @param size
     *        always one larger than maximum index
     *
     * @return {@code true} if the index is matched
     *
     * @see #FIRST_ONLY
     * @see #LAST_ONLY
     * @see #FIRST_AND_LAST_ONLY
     * @see #ALL
     */
    boolean isMatch(int index, int size);

    /**
     * Only match the first index (zero)
     */
    public static final CollectionIndexMatcher FIRST_ONLY =
        (final int index, final int size) -> (0 == index);

    /**
     * Only match the last index (size - 1)
     */
    public static final CollectionIndexMatcher LAST_ONLY =
        (final int index, final int size) -> ((size - 1) == index);

    /**
     * Only match the first index (zero) and the last index (size - 1)
     */
    public static final CollectionIndexMatcher FIRST_AND_LAST_ONLY =
        (final int index, final int size) -> ((0 == index) || ((size - 1) == index));

    /**
     * Match any index
     */
    public static final CollectionIndexMatcher ALL =
        (final int index, final int size) -> true;
}
