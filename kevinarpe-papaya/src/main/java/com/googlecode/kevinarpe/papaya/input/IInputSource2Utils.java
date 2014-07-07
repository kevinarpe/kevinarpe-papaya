package com.googlecode.kevinarpe.papaya.input;

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

import java.io.IOException;
import java.util.Collection;

/**
 * For those projects that require full, static-free mocking capabilities, use this interface.
 * Else, the concrete implementation {@link InputSource2Utils} or {@link InputSource2Utils#INSTANCE}
 * will suffice.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see InputSource2Utils
 */
public interface IInputSource2Utils {

    /**
     * Tests if an {@code InputSource2} has exactly one byte- or character-stream.
     *
     * @param inputSource
     *        an input source reference
     * @param argName
     *        argument name for {@code inputSource}, e.g., "strList" or "searchRegex"
     *
     * @throws NullPointerException
     *         if {@code inputSource} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code inputSource} has as {@code null} both byte- and character-streams</li>
     *     <li>if {@code inputSource} has as {@code null} neither byte- and character-streams</li>
     * </ul>
     *
     * @see #checkValid(Collection, String)
     */
    void checkValid(InputSource2 inputSource, String argName);

    /**
     * Tests if a {@code Collection} of {@code InputSource2} is not empty and has valid elements
     * (non-null, and each with exactly one byte- or character-stream).
     *
     * @param inputSourceCollection
     *        a collection of input sources reference
     * @param argName
     *        argument name for {@code inputSourceCollection}, e.g., "strList" or "searchRegex"
     *
     * @throws NullPointerException
     *         if {@code inputSourceCollection} (or any element) is {@code null}
     * @throws IllegalArgumentException
     *         if number of elements in {@code inputSourceCollection} is zero
     *
     * @see #checkValid(InputSource2, String)
     */
    void checkValid(Collection<? extends InputSource2> inputSourceCollection, String argName);

    /**
     * Closes an {@code InputSource2}.
     *
     * @param inputSource
     *        an input source to close
     *
     * @throws NullPointerException
     *         if {@code inputSource} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code inputSource} has as {@code null} both byte- and character-streams</li>
     *     <li>if {@code inputSource} has as {@code null} neither byte- and character-streams</li>
     * </ul>
     * @throws IOException
     *         if an I/O error occurs when closing {@code inputSource}
     *
     * @see #checkValid(InputSource2, String)
     * @see #closeQuietly(InputSource2)
     */
    void close(InputSource2 inputSource)
    throws IOException;

    /**
     * Closes an {@code InputSource2} and suppresses any {@code IOException}s.
     *
     * @param inputSource
     *        an input source to close
     *
     * @throws NullPointerException
     *         if {@code inputSource} is {@code null}
     * @throws IllegalArgumentException
     * <ul>
     *     <li>if {@code inputSource} has as {@code null} both byte- and character-streams</li>
     *     <li>if {@code inputSource} has as {@code null} neither byte- and character-streams</li>
     * </ul>
     *
     * @see #checkValid(InputSource2, String)
     * @see #close(InputSource2)
     */
    void closeQuietly(InputSource2 inputSource);
}
