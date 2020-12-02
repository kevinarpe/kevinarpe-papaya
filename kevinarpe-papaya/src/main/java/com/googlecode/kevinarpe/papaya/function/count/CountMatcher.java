package com.googlecode.kevinarpe.papaya.function.count;

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

import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.function.retry.RetryService;

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see AbstractCountMatcher
 * @see RetryService
 */
@FullyTested
public interface CountMatcher {

    /**
     * Tests if a count is a match, e.g., if the match is "at least 3" and the count is 7, there is a match.
     *
     * @param count
     *        number of items to match
     *
     * @return true if {@code count} matches
     */
    boolean isMatch(int count);

    /** {@inheritDoc} */
    @Override
    boolean equals(@Nullable Object obj);

    /** {@inheritDoc} */
    @Override
    String toString();

    /**
     * @return new instance of {@link CountMatcher} that combines {@code this} and {@code other}
     *         using logical-AND ({@code &&}).
     *
     * @see AndCountMatcher
     */
    default CountMatcher
    and(CountMatcher other) {

        final AndCountMatcher x = new AndCountMatcher(this, other);
        return x;
    }

    /**
     * @return new instance of {@link CountMatcher} that combines {@code this} and {@code other}
     *         using logical-OR ({@code ||}).
     *
     * @see OrCountMatcher
     */
    default CountMatcher
    or(CountMatcher other) {

        final OrCountMatcher x = new OrCountMatcher(this, other);
        return x;
    }

    /**
     * @return {@link CountMatcher} using {@code this} and logical-NOT ({@code !}).
     *
     * @see NotCountMatcher#create(CountMatcher)
     */
    default CountMatcher
    not() {

        final CountMatcher x = NotCountMatcher.create(this);
        return x;
    }
}
