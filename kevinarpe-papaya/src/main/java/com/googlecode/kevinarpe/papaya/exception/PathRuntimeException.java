package com.googlecode.kevinarpe.papaya.exception;

/*
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

import com.googlecode.kevinarpe.papaya.annotation.NotFullyTested;
import com.googlecode.kevinarpe.papaya.filesystem.TraversePathIterator;

/**
 * Tiny runtime exception wrapper for {@link PathException}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see PathException
 * @see TraversePathIterator#hasNext()
 * @see TraversePathIterator#next()
 */
@NotFullyTested
public class PathRuntimeException
extends RuntimeException {

    /**
     * @param exception
     *        must not be {@code null}
     *
     * @throws NullPointerException
     *         if {@code exception} is {@code null}
     */
    public PathRuntimeException(PathException exception) {
        super(exception);
    }
}
