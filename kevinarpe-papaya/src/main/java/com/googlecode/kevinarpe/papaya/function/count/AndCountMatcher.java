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
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.util.Objects;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class AndCountMatcher
extends AbstractCountMatcher {

    private final CountMatcher left;
    private final CountMatcher right;

    public AndCountMatcher(CountMatcher left, CountMatcher right) {

        this.left = ObjectArgs.checkNotNull(left, "left");
        this.right = ObjectArgs.checkNotNull(right, "right");
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(final int count) {

        final boolean x = left.isMatch(count) && right.isMatch(count);
        return x;
    }

    @Override
    protected int
    hash() {
        final int x = Objects.hash(this.getClass(), left, right);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final AndCountMatcher other = (AndCountMatcher) obj;
        final boolean x = left.equals(other.left) && right.equals(other.right);
        return x;
    }

    @Override
    protected String
    describe() {

        final String x = "(" + left.toString() + ") and (" + right.toString() + ")";
        return x;
    }
}
