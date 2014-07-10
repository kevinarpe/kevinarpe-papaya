package com.googlecode.kevinarpe.papaya.object;

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

/**
 * Provides a default implementation of {@link Object#equals(Object)} and {@link #hashCode()} to
 * all two different stateless objects to be equal.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
@FullyTested
public class StatelessObject {

    /**
     * Empty constructor.
     */
    public StatelessObject() {
    }

    /**
     * Compares {@link Class} references for {@code this} and {@code obj}.
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        return (this == obj || (null != obj && this.getClass().equals(obj.getClass())));
    }

    /**
     * @return hash code for {@link Class} reference for {@code this}
     * <hr/>
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return this.getClass().hashCode();
    }
}
