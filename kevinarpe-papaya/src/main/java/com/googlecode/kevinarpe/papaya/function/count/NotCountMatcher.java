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
public final class NotCountMatcher
extends AbstractCountMatcher {

    /**
     * Tries to unwrap negated delegates, thus: {@code !!CountMatcher -> CountMatcher}
     */
    public static CountMatcher
    create(CountMatcher delegate) {

        ObjectArgs.checkNotNull(delegate, "delegate");
        if (delegate instanceof NotCountMatcher) {

            final NotCountMatcher ncm = (NotCountMatcher) delegate;
            return ncm.delegate;
        }
        else {
            final NotCountMatcher x = new NotCountMatcher(delegate);
            return x;
        }
    }

    private final CountMatcher delegate;

    private NotCountMatcher(CountMatcher delegate) {

        this.delegate = ObjectArgs.checkNotNull(delegate, "delegate");
    }

    /** {@inheritDoc} */
    @Override
    public boolean
    isMatch(final int count) {

        final boolean x = (false == delegate.isMatch(count));
        return x;
    }

    @Override
    protected int
    hash() {
        // The number "one" is arbitrary.
        final int x = Objects.hash(delegate.hashCode(), 1);
        return x;
    }

    @Override
    protected boolean
    isEqual(Object obj) {

        final NotCountMatcher other = (NotCountMatcher) obj;
        final boolean x = delegate.equals(other.delegate);
        return x;
    }

    @Override
    protected String
    describe() {

        final String x = "not " + delegate.toString();
        return x;
    }
}
