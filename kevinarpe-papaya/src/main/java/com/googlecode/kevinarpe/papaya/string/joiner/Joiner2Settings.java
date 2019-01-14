package com.googlecode.kevinarpe.papaya.string.joiner;

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
 * Extends interface {@link SharedJoiner2Settings} to add settings specific to
 * {@link Joiner2}.
 * <p>
 * See {@link SharedJoiner2Settings} for an inheritance diagram.
 *
 * @param <TSelf>
 *        type that extends interface {@link SharedJoiner2Settings} (for method chaining)
 * @param <TQuotingMapJoinerSettings>
 *        type that extends {@link MapJoiner2Settings}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see SharedJoiner2Settings
 * @see Joiner2
 */
public interface Joiner2Settings
    <
        TSelf extends SharedJoiner2Settings<TSelf, TQuotingMapJoinerSettings>,
        TQuotingMapJoinerSettings extends MapJoiner2Settings<TQuotingMapJoinerSettings>
    >
extends SharedJoiner2Settings<TSelf, TQuotingMapJoinerSettings> {

    /**
     * Constructs a new instance with new text to use when joining {@code null} elements.
     *
     * @param nullText
     *        String to be used for {@code null} elements.
     *        Must not be {@code null}, but can be {@code ""} (empty string).
     *        Example: {@code "null"}
     *
     * @return new instance with new text for {@code null} elements
     *
     * @throws NullPointerException
     *         if {@code nullText} is {@code null}
     *
     * @see #useForNull(char)
     * @see #useForNull()
     * @see Joiner2Utils#DEFAULT_NULL_TEXT
     */
    TSelf useForNull(String nullText);

    /**
     * This is a convenience method to call {@link #useForNull(String)}.
     */
    TSelf useForNull(char nullText);

    /**
     * Retrieves text to use when joining {@code null} elements.  Default is {@code "null"}.
     * <p>
     * If {@link #skipNulls()} is {@code true}, this setting is irrelevant.
     *
     * @return null element text
     *
     * @see #useForNull(String)
     * @see #skipNulls()
     * @see Joiner2Utils#DEFAULT_NULL_TEXT
     */
    String useForNull();

    /**
     * Constructs a new instance with new flag to skip {@code null} elements when joining.
     *
     * @param flag
     * <ul>
     *     <li>if {@code true}, {@code null} elements are skipped when joining</li>
     *     <li>if {@code false}, {@code null} elements use text from {@link #useForNull()}</li>
     * </ul>
     *
     * @return new instance with new skip nulls flag
     *
     * @see #useForNull()
     * @see #skipNulls()
     * @see Joiner2Utils#DEFAULT_SKIP_NULLS_FLAG
     */
    TSelf skipNulls(boolean flag);

    /**
     * Tests if {@code null} elements are skipped when joining.
     *
     * @return
     * <ul>
     *     <li>if {@code true}, {@code null} elements are skipped when joining</li>
     *     <li>if {@code false}, {@code null} elements use text from {@link #useForNull()}</li>
     * </ul>
     *
     * @see #skipNulls(boolean)
     * @see Joiner2Utils#DEFAULT_SKIP_NULLS_FLAG
     */
    boolean skipNulls();
}
