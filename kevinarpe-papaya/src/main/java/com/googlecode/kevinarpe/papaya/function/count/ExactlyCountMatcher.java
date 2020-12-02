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
import com.googlecode.kevinarpe.papaya.argument.IntArgs;

import java.util.Objects;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ExactlyCountMatcher
extends AbstractCountMatcher {

    public static final ExactlyCountMatcher ONE = new ExactlyCountMatcher(1);

    public final int exact;

    public ExactlyCountMatcher(final int exact) {

        super();
        this.exact = IntArgs.checkMinValue(exact, 0, "exact");
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(final int rowCount) {

        final boolean x = (rowCount == exact);
        return x;
    }

    @Override
    protected int
    hash() {
        final int x = Objects.hash(exact);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final ExactlyCountMatcher other = (ExactlyCountMatcher) obj;
        final boolean x = (this.exact == other.exact);
        return x;
    }

    @Override
    protected String
    describe() {

        final String x = "exactly " + exact;
        return x;
    }
}
