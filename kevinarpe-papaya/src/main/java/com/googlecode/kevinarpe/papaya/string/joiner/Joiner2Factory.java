package com.googlecode.kevinarpe.papaya.string.joiner;

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

/**
 * Factory to construct instances of {@link Joiner2}.  Unless an additional layer of
 * indirection is required for mocking or testing, it is usually sufficient to call
 * {@link Joiner2Utils#withSeparator(String)}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see Joiner2Utils#newJoiner2Factory(String)
 * @see Joiner2Utils#newJoiner2Factory(char)
 * @see Joiner2Utils#withSeparator(String)
 * @see Joiner2Utils#withSeparator(char)
 */
public interface Joiner2Factory {

    /**
     * Constructs a new instance of {@link Joiner2}.
     *
     * @return new instance
     */
    Joiner2 newInstance();
}
