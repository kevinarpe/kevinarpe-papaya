package com.googlecode.kevinarpe.papaya.exception;

/*-
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

import java.util.HashSet;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public final class ThrowableToStringServiceImpl
implements ThrowableToStringService {

    private final HashSet<String> set;

    public ThrowableToStringServiceImpl() {

        this.set = new HashSet<>();
    }

    /** {@inheritDoc} */
    @Override
    public String
    toStringWithUniqueStackTrace(Throwable throwable) {

        ObjectArgs.checkNotNull(throwable, "throwable");

        final String s = ThrowableUtils.toStringWithStackTrace(throwable);
        if (set.add(s)) {
            return s;
        }
        else {
            final String x = throwable.toString();
            return x;
        }
    }
}
