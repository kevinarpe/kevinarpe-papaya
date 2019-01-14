package com.googlecode.kevinarpe.papaya.testing;

/*
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

/**
 * Factory to construct instances of {@link TestClassFinder}.  Unless an additional layer of
 * indirection is required for mocking or testing, it is usually sufficient to call
 * {@link TestClassFinderUtils#newInstance()}.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see TestClassFinderUtils#newFactory()
 * @see TestClassFinderUtils#newInstance()
 */
public interface TestClassFinderFactory {

    /**
     * Constructs a new instance of {@link TestClassFinder}.
     *
     * @return new instance
     */
    TestClassFinder newInstance();
}
