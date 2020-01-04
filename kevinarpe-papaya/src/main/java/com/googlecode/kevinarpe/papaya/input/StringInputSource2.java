package com.googlecode.kevinarpe.papaya.input;

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

import com.google.common.base.Splitter;
import com.googlecode.kevinarpe.papaya.StringUtils;
import com.googlecode.kevinarpe.papaya.annotation.FullyTested;
import com.googlecode.kevinarpe.papaya.argument.ObjectArgs;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * Encapsulates the characters of a file.
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 *
 * @see ClassResourceInputSource2
 * @see FileInputSource2
 */
@FullyTested
public final class StringInputSource2
implements InputSource2 {

    private final String _text;
    private final StringReader _stringReader;

    /**
     * Constructs a new {@code StringReader}.
     *
     * @param text
     *        any text, include empty
     *
     * @throws NullPointerException
     *         if {@code text} is {@code null}
     */
    public StringInputSource2(String text) {
        _text = ObjectArgs.checkNotNull(text, "text");
        _stringReader = new StringReader(_text);
    }

    /**
     * Always {@code null}.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public InputStream getByteStream() {
        return null;
    }

    /**
     * Always non-{@code null}.
     * <hr>
     * {@inheritDoc}
     */
    @Override
    public Reader getCharacterStream() {
        return _stringReader;
    }

    @Override
    public String toString() {
        String fragment = _getFragment();
        String x = String.format("String: '%s'", fragment);
        return x;
    }

    private String _getFragment() {
        String fragment = Splitter.on(StringUtils.NEW_LINE_REGEX).split(_text).iterator().next();
        boolean needsEllipsis = (fragment.length() != _text.length());
        if (fragment.length() > 256) {
            fragment = fragment.substring(0, 256);
        }
        if (needsEllipsis) {
            fragment += "...";
        }
        return fragment;
    }
}
