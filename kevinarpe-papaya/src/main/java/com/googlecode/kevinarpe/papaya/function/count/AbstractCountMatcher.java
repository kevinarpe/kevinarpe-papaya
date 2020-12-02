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

import javax.annotation.Nullable;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public abstract class AbstractCountMatcher
implements CountMatcher {

    protected AbstractCountMatcher() {
        // Empty
    }

    @Override
    public final int hashCode() {

        final int x = hash();
        return x;
    }

    protected abstract int
    hash();

    /** {@inheritDoc} */
    @Override
    public final boolean
    equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }
        // Note: Class.isInstance() works correctly when 'obj' is null, but use 'null == obj' to be explicit!
        if (null == obj || false == this.getClass().isInstance(obj)) {
            return false;
        }
        final boolean x = isEqual(obj);
        return x;
    }

    protected abstract boolean
    isEqual(Object obj);

    /** {@inheritDoc} */
    @Override
    public final String
    toString() {
        final String x = describe();
        return x;
    }

    protected abstract String
    describe();
}
